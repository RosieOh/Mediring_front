package com.mediring.app.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.databinding.FragmentLikeProductBinding
import com.mediring.app.model.LikeProductEntity
import com.mediring.app.model.ProductEntity
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_like_product.view.*
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.item_like_product.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LikeProductFragment : Fragment() {
    private lateinit var binding: FragmentLikeProductBinding
    private lateinit var rootView: View
    private lateinit var adapter: ProductRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLikeProductBinding.inflate(inflater, container, false)
        rootView = binding.root

        context?.apply {
            adapter = ProductRecyclerAdapter(
                this,
                products = Collections.emptyList()
            ) {

            }
            rootView.like_product_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rootView.like_product_recycler_view.adapter = adapter

            HttpService.getLikeProductService(this).list(App.user.id!!).enqueue(object: Callback<List<LikeProductEntity>> {
                override fun onFailure(call: Call<List<LikeProductEntity>>, t: Throwable) {
                    t.message?.let {
                        Log.e(TAG, it)
                    }
                }

                override fun onResponse(
                    call: Call<List<LikeProductEntity>>,
                    response: Response<List<LikeProductEntity>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { likes ->
                            rootView.like_product_total_count_view.setText("전체 ${likes.size}개")

                            val products = likes.map {
                                it.refProductEntity!!
                            }
                            adapter.notification(products = products)
                        }
                    }
                }
            })
        }

        return rootView
    }

    companion object {
        private const val TAG = "LikeProductFragment"
    }

    class ProductRecyclerAdapter(val context: Context, var products: List<ProductEntity>, val itemClick: (ProductEntity) -> Unit): RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View, itemClick: (ProductEntity) -> Unit): RecyclerView.ViewHolder(itemView) {
            val imgView = itemView.item_like_product_thumb
            val nameView = itemView.item_like_product_name
            val priceView = itemView.item_like_product_price

            fun bind(context: Context, product: ProductEntity) {
                product.thumb?.let {
                    Glide.with(this.itemView).load("${App.SERVER_URL}${it}").into(imgView)
                }
                nameView.text = product.title
                priceView.text = "${product.price}"

            }
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_like_product, parent, false), itemClick = itemClick)
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