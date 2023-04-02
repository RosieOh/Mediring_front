package com.mediring.app.comparison

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.model.ProductEntity
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_part_compare.view.*
import kotlinx.android.synthetic.main.item_part_compare_product.view.*
import kotlinx.android.synthetic.main.item_product_part.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartCompareFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var adapter: ProductRecyclerAdapter

    private var partId: Int = 0
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString("title", "")
            partId = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_part_compare, container, false)

        context?.apply {
            rootView.part_compare_title.setText("${title} 제품 비교")

            adapter = ProductRecyclerAdapter(this, listOf()) {

            }
            rootView.part_compare_content_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rootView.part_compare_content_view.adapter = adapter

            HttpService.getBodyService(this).getPartList(partId).enqueue(object: Callback<List<ProductEntity>> {
                override fun onFailure(call: Call<List<ProductEntity>>, t: Throwable) {
                    t.message?.let {
                        Log.e(TAG, it)
                    }
                }

                override fun onResponse(
                    call: Call<List<ProductEntity>>,
                    response: Response<List<ProductEntity>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            adapter.notification(body)
                        }
                    }
                }

            })
        }

        return rootView
    }

    companion object {
        private const val TAG = "PartCompareFragment"
    }

    class ProductRecyclerAdapter(val context: Context, var products: List<ProductEntity>, val itemClick: (ProductEntity) -> Unit): RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View, itemClick: (ProductEntity) -> Unit): RecyclerView.ViewHolder(itemView) {
            val imgView = itemView.part_compare_product_thumb
            val nameView = itemView.part_compare_product_title
            val typeView = itemView.part_compare_product_type
            val companyView = itemView.part_compare_product_company

            fun bind(context: Context, product: ProductEntity) {
                product.thumb?.let {
                    Glide.with(this.itemView).load("${App.SERVER_URL}${it}").into(imgView)
                }
                nameView.text = product.title
                typeView.text = product.type
                companyView.text = product.company

                itemView.setOnClickListener {
                    itemClick(product)
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_part_compare_product, parent, false), itemClick = itemClick)
        }

        override fun getItemCount(): Int {
            return products.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(context, product = products[position])
        }

        fun notification(products: List<ProductEntity>) {
            this.products = products
            this.notifyDataSetChanged()
        }
    }
}