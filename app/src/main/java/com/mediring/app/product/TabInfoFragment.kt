package com.mediring.app.product

import android.app.AlertDialog
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.model.LikeProductEntity
import com.mediring.app.model.ProductEntity
import com.mediring.app.model.ShoppingBasketEntity
import com.mediring.app.model.request.RequestLikeProduct
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_tab_info.view.*
import kotlinx.android.synthetic.main.item_buy_dialog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabInfoFragment : Fragment() {
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tab_info, container, false)

        val id = arguments?.getInt("id")

        context?.let { context ->
            id?.let { id ->
                val client = HttpService.getProductService(context)
                client.getProduct(id).enqueue(object:
                    Callback<ProductEntity> {
                    override fun onFailure(call: Call<ProductEntity>, t: Throwable) {
                        t.message?.let {
                            Log.e(TAG, it)
                        }
                        rootView.tab_info_buy_button.isEnabled = false
                    }

                    override fun onResponse(
                        call: Call<ProductEntity>,
                        response: Response<ProductEntity>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { body ->
                                Glide.with(rootView).load("${App.SERVER_URL}${body.thumb}").into(rootView.tab_info_thumb_view)
                                rootView.tab_info_title_view.text = body.title
                                rootView.tab_info_company_view.text = body.company
                                rootView.tab_info_price_view.text = "${body.price}"
                                body.info?.let {
                                    rootView.tab_info_content_view.text = Html.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
                                }

                                rootView.tab_info_buy_button.setOnClickListener {
                                    val dialogView = inflater.inflate(R.layout.item_buy_dialog, null)

                                    dialogView.buy_dialog_title.text = body.title
                                    dialogView.buy_dialog_price.text = "${body.price}원"

                                    val builder = AlertDialog.Builder(context)
                                    builder.setView(dialogView)
                                    val alert = builder.create()
                                    alert.show()

                                    dialogView.buy_dialog_minus.setOnClickListener {
                                        if ("${dialogView.buy_dialog_quantity.text}".toInt() > 0) {
                                            val mval = "${dialogView.buy_dialog_quantity.text}".toInt() - 1
                                            dialogView.buy_dialog_quantity.setText("$mval")

                                        }
                                    }
                                    dialogView.buy_dialog_plus.setOnClickListener {
                                        val pval = "${dialogView.buy_dialog_quantity.text}".toInt() + 1
                                        dialogView.buy_dialog_quantity.setText("$pval")
                                    }
                                    dialogView.buy_dialog_add_list_button.setOnClickListener {
                                        // 장바구니 담기
                                        if ("${dialogView.buy_dialog_quantity.text}".toInt() > 0) {
                                            val basketEntity = ShoppingBasketEntity()
                                            basketEntity.productId = body.id
                                            basketEntity.qty = "${dialogView.buy_dialog_quantity.text}".toInt()
                                            basketEntity.userId = App.user.id!!
                                            client.storeBasket(basketEntity).enqueue(object: Callback<ShoppingBasketEntity> {
                                                override fun onFailure(call: Call<ShoppingBasketEntity>, t: Throwable) {
                                                    t.message?.apply {
                                                        Log.e(TAG, this)
                                                    }
                                                }

                                                override fun onResponse(call: Call<ShoppingBasketEntity>, response: Response<ShoppingBasketEntity>) {
                                                    if (response.isSuccessful) {
                                                        response.body()?.let {
                                                            Toast.makeText(context, "장바구니에 등록되었습니다.", Toast.LENGTH_SHORT).show()
                                                            alert.dismiss()
                                                        }
                                                    }
                                                }

                                            })
                                        }
                                    }
                                }

                                rootView.tab_info_like_product_button.setOnCheckedChangeListener { buttonView, isChecked ->
                                    val serivce = HttpService.getLikeProductService(context)
                                    val request = RequestLikeProduct(App.user.id!!, body.id!!)
                                    if (isChecked) {
                                        serivce.like(request).enqueue(object: Callback<LikeProductEntity> {
                                            override fun onFailure(call: Call<LikeProductEntity>, t: Throwable) {
                                                t.message?.let {
                                                    Log.e(TAG, it)
                                                }
                                            }

                                            override fun onResponse(call: Call<LikeProductEntity>, response: Response<LikeProductEntity>) {
                                                if (response.isSuccessful) {
                                                    Toast.makeText(context, "찜한 상품 등록", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, "찜한 상품 등록 실패", Toast.LENGTH_SHORT).show()
                                                }
                                            }

                                        })
                                    } else {
                                        serivce.delete(request).enqueue(object: Callback<Boolean> {
                                            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                                                t.message?.let {
                                                    Log.e(TAG, it)
                                                }
                                            }

                                            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                                                if (response.isSuccessful) {
                                                    Toast.makeText(context, "찜한 상품 해제", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, "찜한 상품 해제 실패", Toast.LENGTH_SHORT).show()
                                                }
                                            }

                                        })
                                    }
                                }
                            }
                        }
                        else {
                            rootView.tab_info_buy_button.isEnabled = false
                        }
                    }

                })
            }
        }

        return rootView
    }

    companion object {
        private const val TAG = "TabInfoFragment"
    }
}