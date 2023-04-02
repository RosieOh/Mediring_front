package com.mediring.app.part

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mediring.app.App
import com.mediring.app.App.Companion.SERVER_IP
import com.mediring.app.model.BodyEntity
import com.mediring.app.R
import com.mediring.app.helper.EventType
import com.mediring.app.helper.FragmentEventHandler
import com.mediring.app.main.MainActivity
import com.mediring.app.model.ProductEntity
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_body_part.view.*
import kotlinx.android.synthetic.main.item_body_part.*
import kotlinx.android.synthetic.main.item_body_part.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BodyPartFragment : Fragment() {
    private lateinit var mListener: FragmentEventHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_body_part, container, false)

        mListener = activity as MainActivity

        context?.let { context ->
            HttpService.getBodyService(context).getBodyList().enqueue(object: Callback<List<BodyEntity>> {
                override fun onFailure(call: Call<List<BodyEntity>>, t: Throwable) {
                    Log.e(TAG, t.message?.let{ it } ?: run { "" })
                }

                override fun onResponse(call: Call<List<BodyEntity>>, response: Response<List<BodyEntity>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            val one = body.filter { it.line == 1 }
                            val two = body.filter { it.line == 2 }
                            val three = body.filter { it.line == 3 }
                            val four = body.filter { it.line == 4 }
                            val five = body.filter { it.line == 5 }

                            one.forEach { body ->
                                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                val view = layoutInflater.inflate(R.layout.item_body_part, null);
                                view.layoutParams = params
                                rootView.bodypart_first_line.addView(view)
                                Glide.with(rootView).load("${App.SERVER_URL}${body.icon}").into(view.item_body_part_icon)
                                view.item_body_part_title.text = body.name
                                view.setOnClickListener {
                                    body.id?.let {
                                        mListener.bodyPartClickEventListener(EventType.FRAGMENT_BODY_PRODUCT, bodyId = it, title = body.name!!)
                                    }
                                }
                            }

                            two.forEach { body ->
                                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                val view = layoutInflater.inflate(R.layout.item_body_part, null);
                                view.layoutParams = params
                                rootView.bodypart_sec_line.addView(view)
                                Glide.with(rootView).load("${App.SERVER_URL}${body.icon}").into(view.item_body_part_icon)
                                view.item_body_part_title.text = body.name
                                view.setOnClickListener {
                                    body.id?.let {
                                        mListener.bodyPartClickEventListener(EventType.FRAGMENT_BODY_PRODUCT, bodyId = it, title = body.name!!)
                                    }
                                }
                            }

                            three.forEach { body ->
                                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                val view = layoutInflater.inflate(R.layout.item_body_part, null);
                                view.layoutParams = params
                                rootView.bodypart_thr_line.addView(view)
                                Glide.with(rootView).load("${App.SERVER_URL}${body.icon}").into(view.item_body_part_icon)
                                view.item_body_part_title.text = body.name
                                view.setOnClickListener {
                                    body.id?.let {
                                        mListener.bodyPartClickEventListener(EventType.FRAGMENT_BODY_PRODUCT, bodyId = it, title = body.name!!)
                                    }
                                }
                            }

                            four.forEach { body ->
                                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                val view = layoutInflater.inflate(R.layout.item_body_part, null);
                                view.layoutParams = params
                                rootView.bodypart_four_line.addView(view)
                                Glide.with(rootView).load("${App.SERVER_URL}${body.icon}").into(view.item_body_part_icon)
                                view.item_body_part_title.text = body.name
                                view.setOnClickListener {
                                    body.id?.let {
                                        mListener.bodyPartClickEventListener(EventType.FRAGMENT_BODY_PRODUCT, bodyId = it, title = body.name!!)
                                    }
                                }
                            }

                            five.forEach { body ->
                                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                val view = layoutInflater.inflate(R.layout.item_body_part, null);
                                view.layoutParams = params
                                rootView.bodypart_fiv_line.addView(view)
                                Glide.with(rootView).load("${App.SERVER_URL}${body.icon}").into(view.item_body_part_icon)
                                view.item_body_part_title.text = body.name
                                view.setOnClickListener {
                                    body.id?.let {
                                        mListener.bodyPartClickEventListener(EventType.FRAGMENT_BODY_PRODUCT, bodyId = it, title = body.name!!)
                                    }
                                }
                            }
                        }
                    }
                }

            })
        }
        return rootView
    }

    companion object {
        private const val TAG = "BodyPartFragment"
    }
}