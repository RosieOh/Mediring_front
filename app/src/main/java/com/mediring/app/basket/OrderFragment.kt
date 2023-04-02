package com.mediring.app.basket

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.helper.EventType
import com.mediring.app.helper.FragmentEventHandler
import com.mediring.app.model.BuyListEntity
import com.mediring.app.model.ShoppingBasketEntity
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_order.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderFragment : Fragment() {
    private lateinit var mListener: FragmentEventHandler
    private lateinit var rootView: View
    private var buyList: List<ShoppingBasketEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mListener = activity as FragmentEventHandler

        rootView = inflater.inflate(R.layout.fragment_order, container, false)

        context?.apply {
            HttpService.getProductService(this).getBaskets(App.user.id!!).enqueue(object: Callback<List<ShoppingBasketEntity>> {
                override fun onFailure(call: Call<List<ShoppingBasketEntity>>, t: Throwable) {
                    t.message?.let {
                        Log.e(TAG, it)
                    }
                }

                override fun onResponse(call: Call<List<ShoppingBasketEntity>>, response: Response<List<ShoppingBasketEntity>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            buyList = body

                            rootView.order_product_title.setText(body[0].refProductEntity?.title)
                            rootView.order_user_info.setText("${App.user.name}, ${App.user.phone}")
                            rootView.order_user_address.setText("${App.user.addresses!![0].postDetail}")

                            val price = body.sumBy { it.qty!!.times(it.refProductEntity!!.price!!) }
                            rootView.order_price.setText("$price")
                            rootView.order_total_price.setText("${price + 3000}")
                        }
                    }
                }

            })

            rootView.order_payment_button.setOnClickListener {
                if (!rootView.order_checkbox.isChecked) {
                    Toast.makeText(context, "개인정보 수집, 이용 및 처리 동의해주세요!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                buyList?.let {
                    HttpService.getProductService(this).storeBuyList(it).enqueue(object: Callback<Boolean> {
                        override fun onFailure(call: Call<Boolean>, t: Throwable) {
                            t.message?.let { Log.e(TAG, it) }
                        }

                        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                            if (response.isSuccessful) {
                                mListener.paymentCompletedEventListener(EventType.FRAGMENT_PAYMENT_COMPLETE, 0)
                            }
                        }
                    })
                }
            }
        }

        return rootView
    }

    companion object {
        private const val TAG = "OrderFragment"
    }
}