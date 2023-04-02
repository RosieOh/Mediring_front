package com.mediring.app.product

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mediring.app.R
import com.mediring.app.model.ReviewEntity
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_tab_review.view.*
import kotlinx.android.synthetic.main.item_review.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TabReviewFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var adapter: ReviewRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tab_review, container, false)
        val id = arguments?.getInt("id")

        context?.apply {
            id?.let { id ->
                adapter = ReviewRecyclerAdapter(this, reviews = Collections.emptyList()) {

                }

                rootView.tab_review_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                rootView.tab_review_recycler.adapter = adapter

                HttpService.getProductService(this).getProductReviews(id).enqueue(object: Callback<List<ReviewEntity>> {
                    override fun onResponse(
                        call: Call<List<ReviewEntity>>,
                        response: Response<List<ReviewEntity>>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { body ->
                                val data = body.map { b ->
                                    b.addedAt = b.addedAt?.replace("T", " ")
                                    b
                                }
                                adapter.notification(data)
//                                body.forEach { Log.e(TAG, "${it}") }
                            }
                        } else {

                        }
                    }

                    override fun onFailure(call: Call<List<ReviewEntity>>, t: Throwable) {
                        t.message?.let {
                            Log.e(TAG, it)
                        }
                    }

                })
            }
        }

        return rootView
    }

    companion object {
        const val TAG = "TabReviewFragment"
    }

    class ReviewRecyclerAdapter(val context: Context, var reviews: List<ReviewEntity>, val itemClick: (ReviewEntity) -> Unit): RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View, itemClick: (ReviewEntity) -> Unit): RecyclerView.ViewHolder(itemView) {
            val nameView = itemView.item_review_user_name
            val ratingView = itemView.item_review_rating
            val dateView = itemView.item_review_date
            val contentView = itemView.item_review_content

            fun bind(context: Context, review: ReviewEntity) {
                nameView.text = review.refUserEntity?.name
                review.rate?.let {
                    ratingView.rating = it.toFloat()
                }
                dateView.text = review.addedAt
                contentView.text = review.content

                itemView.setOnClickListener {
                    itemClick(review)
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_review, parent, false), itemClick = itemClick)
        }

        override fun getItemCount(): Int {
            return reviews.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(context, review = reviews[position])
        }

        fun notification(reviews: List<ReviewEntity>) {
            this.reviews = reviews
            this.notifyDataSetChanged()
        }
    }
}