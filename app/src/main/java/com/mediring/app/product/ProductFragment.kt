package com.mediring.app.product

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.helper.EventType
import com.mediring.app.helper.FragmentEventHandler
import com.mediring.app.main.MainActivity
import com.mediring.app.model.ProductEntity
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.item_body_part.view.*
import kotlinx.android.synthetic.main.item_product_part.view.*
import kotlinx.coroutines.channels.consumesAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProductFragment : Fragment() {
    private lateinit var mListener: FragmentEventHandler

    private lateinit var rootView: View
    private lateinit var adapter: ProductRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_product, container, false)
        mListener = activity as MainActivity

        context?.let { context ->
            adapter = ProductRecyclerAdapter(context, products = Collections.emptyList()) {
                // 상품 클릭
                it.id?.let { id ->
                    mListener.productClickEventListener(EventType.FRAGMENT_PRODUCT_INFO, id)
                }
            }
            rootView.product_list_viewer.layoutManager = GridLayoutManager(context, 3)
            rootView.product_list_viewer.adapter = adapter

            HttpService.getProductService(context).getProductList().enqueue(object: Callback<List<ProductEntity>> {
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
        private const val TAG = "ProductFragment"
    }

    class ProductRecyclerAdapter(val context: Context, var products: List<ProductEntity>, val itemClick: (ProductEntity) -> Unit): RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View, itemClick: (ProductEntity) -> Unit): RecyclerView.ViewHolder(itemView) {
            val imgView = itemView.item_product_part_img
            val nameView = itemView.item_product_part_name
            val typeView = itemView.item_product_part_type
            val priceView = itemView.item_product_part_price

            fun bind(context: Context, product: ProductEntity) {
                product.thumb?.let {
                    Glide.with(this.itemView).load("${App.SERVER_URL}${it}").into(imgView)
                }
                nameView.text = product.title
                typeView.text = product.type
                priceView.text = "${product.price}"

                itemView.setOnClickListener {
                    itemClick(product)
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_part, parent, false), itemClick = itemClick)
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