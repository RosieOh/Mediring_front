package com.mediring.app.product

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mediring.app.R
import com.mediring.app.databinding.FragmentProductInfoBinding
import com.mediring.app.model.ProductEntity
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_product_info.*
import kotlinx.android.synthetic.main.fragment_product_info.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductInfoFragment : Fragment() {
    private lateinit var rootView: View

    private var binding: FragmentProductInfoBinding? = null

    private val tabInfoFragment = TabInfoFragment()
    private val tabReviewFragment = TabReviewFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        rootView = inflater.inflate(R.layout.fragment_product_info, container, false)
        binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        rootView = binding?.root!!

        val id = arguments?.getInt("id")

        context?.let { context ->
            activity?.let {
                val productPageAdapter = ProductPageAdapter(it)
                val param = Bundle(1)
                param.putInt("id", id!!)
                tabInfoFragment.arguments = param
                tabReviewFragment.arguments = param
                productPageAdapter.addFragment(tabInfoFragment)
                productPageAdapter.addFragment(tabReviewFragment)

                rootView.product_info_content_view.apply {
                    isUserInputEnabled = false
                    adapter = productPageAdapter

                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageSelected(position: Int) {
                            super.onPageSelected(position)
                        }
                    })
                }

                TabLayoutMediator(rootView.product_info_tabs, rootView.product_info_content_view) { tab, position ->
                    when (position) {
                        0 -> tab.text = "상품설명"
                        1 -> tab.text = "후기"
                    }
                }.attach()
            }
        }

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null;
    }

    companion object {
        private const val TAG = "ProductInfoFragment"
    }

    class ProductPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
        val fragments = mutableListOf<Fragment>()

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
            notifyItemInserted(fragments.size - 1)
        }
    }
}