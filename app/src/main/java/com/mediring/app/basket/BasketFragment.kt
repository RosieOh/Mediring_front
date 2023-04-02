package com.mediring.app.basket

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
import com.mediring.app.helper.EventType
import com.mediring.app.helper.FragmentEventHandler
import com.mediring.app.model.ProductEntity
import com.mediring.app.model.ShoppingBasketEntity
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_basket.view.*
import kotlinx.android.synthetic.main.item_basket.view.*
import kotlinx.android.synthetic.main.item_like_product.view.*
import kotlinx.android.synthetic.main.item_like_product.view.item_like_product_name
import kotlinx.android.synthetic.main.item_like_product.view.item_like_product_price
import kotlinx.android.synthetic.main.item_like_product.view.item_like_product_thumb
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var adapter: ProductRecyclerAdapter
    private lateinit var mListener: FragmentEventHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_basket, container, false)

        mListener = activity as FragmentEventHandler

        context?.apply {
            adapter = ProductRecyclerAdapter(this, listOf()) {

            }

            rootView.basket_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            rootView.basket_recycler_view.adapter = adapter

            val client = HttpService.getProductService(this)
            client.getBaskets(App.user.id!!).enqueue(object: Callback<List<ShoppingBasketEntity>> {
                override fun onFailure(call: Call<List<ShoppingBasketEntity>>, t: Throwable) {
                    t.message?.let {
                        Log.e(TAG, it)
                    }
                }

                override fun onResponse(
                    call: Call<List<ShoppingBasketEntity>>,
                    response: Response<List<ShoppingBasketEntity>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            val price = body.sumBy { it.refProductEntity?.price!! * it.qty!! }
                            rootView.basket_price.setText("${price}원")
                            rootView.basket_total_price.setText("${price + 3000}원")
                            adapter.notification(body)
                        }
                    }
                }

            })
        }

        rootView.basket_order_button.setOnClickListener {
            mListener.buttonClickEventListener(EventType.FRAGMENT_ORDER)
        }

        return rootView
    }

    companion object {
        private const val TAG = "BasketFragment"
    }

    class ProductRecyclerAdapter(val context: Context, var products: List<ShoppingBasketEntity>, val itemClick: (ShoppingBasketEntity) -> Unit): RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View, itemClick: (ShoppingBasketEntity) -> Unit): RecyclerView.ViewHolder(itemView) {
            val imgView = itemView.item_like_product_thumb
            val nameView = itemView.item_like_product_name
            val priceView = itemView.item_like_product_price
            val removeButton = itemView.basket_remove_button
            val minusButton = itemView.basket_minus
            val plusButton = itemView.basket_plus
            val qtyView = itemView.basket_quantity

            fun bind(context: Context, basket: ShoppingBasketEntity) {
                basket.refProductEntity?.thumb?.let {
                    Glide.with(this.itemView).load("${App.SERVER_URL}${it}").into(imgView)
                }
                nameView.text = basket.refProductEntity?.title
                priceView.text = "${basket.refProductEntity?.price}"
                qtyView.setText("${basket.qty}")

                minusButton.setOnClickListener {
                    val qty = "${qtyView.text}".toInt()
                    if (qty > 0) {
                        qtyView.setText("${qty - 1}")
                    }
                }
                plusButton.setOnClickListener {
                    val qty = "${qtyView.text}".toInt()
                    qtyView.setText("${qty + 1}")
                }
                removeButton.setOnClickListener {
                    // 장바구니 삭제
                    HttpService.getProductService(context).deleteBasket(basket.id!!).enqueue(object: Callback<List<ShoppingBasketEntity>> {
                        override fun onFailure(call: Call<List<ShoppingBasketEntity>>, t: Throwable) {
                            t.message?.let {
                                Log.e(TAG, it)
                            }
                        }

                        override fun onResponse(call: Call<List<ShoppingBasketEntity>>, response: Response<List<ShoppingBasketEntity>>) {
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    this@ProductRecyclerAdapter.notification(it)
                                }
                            }
                        }

                    })
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_basket, parent, false), itemClick = itemClick)
        }

        override fun getItemCount(): Int {
            return products.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(context, basket = products[position])
        }

        fun notification(products: List<ShoppingBasketEntity>) {
            this.products = products
            this.notifyDataSetChanged()
        }
    }
}