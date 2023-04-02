package com.mediring.app.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.basket.BasketFragment
import com.mediring.app.basket.OrderFragment
import com.mediring.app.basket.PaymentFragment
import com.mediring.app.comparison.ComparisonFragment
import com.mediring.app.comparison.PartCompareFragment
import com.mediring.app.dashboard.DashboardFragment
import com.mediring.app.helper.EventType
import com.mediring.app.helper.FragmentEventHandler
import com.mediring.app.mypage.DeliveryFragment
import com.mediring.app.mypage.LikeProductFragment
import com.mediring.app.mypage.MyPageFragment
import com.mediring.app.mypage.UserProfileFragment
import com.mediring.app.part.BodyPartFragment
import com.mediring.app.product.BodyProductFragment
import com.mediring.app.product.ProductFragment
import com.mediring.app.product.ProductInfoFragment
import com.mediring.app.utils.BackPressHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, FragmentEventHandler {
    companion object {
        const val TAG = "MainActivity"
        const val PAGE_ID_BUY = 1
    }

    private lateinit var fragmentManager: FragmentManager
    private lateinit var transaction: FragmentTransaction

    private val dashboardFragment = DashboardFragment()
    private lateinit var bodyPartFragment: BodyPartFragment
    private lateinit var productFragment: ProductFragment
    private lateinit var myPageFragment: MyPageFragment
    private lateinit var basketFragment: BasketFragment
    private lateinit var productInfoFragment: ProductInfoFragment
    private lateinit var bodyProductFragment: BodyProductFragment
    private lateinit var userProfileFragment: UserProfileFragment
    private lateinit var likeProductFragment: LikeProductFragment
    private lateinit var paymentFragment: PaymentFragment
    private lateinit var orderFragment: OrderFragment
    private lateinit var comparisonFragment: ComparisonFragment
    private lateinit var partCompareFragment: PartCompareFragment
    private lateinit var deliveryFragment: DeliveryFragment

    private val backPressHandler = BackPressHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.main_frame, dashboardFragment).commit()

        navigation_view.menu.getItem(0)?.isChecked = false
        navigation_view.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menu: MenuItem): Boolean {
        transaction = fragmentManager.beginTransaction()

        return when (menu.itemId) {
            R.id.homeItem -> {
                transaction.replace(R.id.main_frame, dashboardFragment).commit()
                true
            }
            R.id.searchItem -> {
                // 찾기
                bodyPartFragment = BodyPartFragment()
                transaction.replace(R.id.main_frame, bodyPartFragment).commit()
                true
            }
            R.id.buyItem -> {
                // 장바구니
                basketFragment = BasketFragment()
                transaction.replace(R.id.main_frame, basketFragment).commit()
                true
            }
            R.id.mypageItem -> {
                // 마이페이지
                myPageFragment = MyPageFragment()
                transaction.replace(R.id.main_frame, myPageFragment).commit()
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount == 0) {
            backPressHandler.onBackPressed()
        } else {
            fragmentManager.popBackStack()
        }
    }

    override fun buttonClickEventListener(eventType: Int) {
        transaction = fragmentManager.beginTransaction()

        when (eventType) {
            EventType.FRAGMENT_MAIN -> {
                // 메인
            }
            EventType.FRAGMENT_SURVEY -> {
                // 설문
            }
            EventType.FRAGMENT_PRODUCT -> {
                productFragment = ProductFragment()
                transaction.replace(R.id.main_frame, productFragment).commit()
            }
            EventType.FRAGMENT_USER_PROFILE -> {
                // 프로필 수정
                userProfileFragment = UserProfileFragment()
                transaction.replace(R.id.main_frame, userProfileFragment).commit()
            }
            EventType.FRAGMENT_LIKE_PRODUCT -> {
                likeProductFragment = LikeProductFragment()
                transaction.replace(R.id.main_frame, likeProductFragment).commit()
            }
            EventType.FRAGMENT_ORDER -> {
                orderFragment = OrderFragment()
                transaction.replace(R.id.main_frame, orderFragment).commit()
            }
            EventType.FRAGMENT_COMPARE -> {
                comparisonFragment = ComparisonFragment()
                transaction.replace(R.id.main_frame, comparisonFragment).commit()
            }
            EventType.FRAGMENT_DELIVERY -> {
                deliveryFragment = DeliveryFragment()
                transaction.replace(R.id.main_frame, deliveryFragment).commit()
            }
        }
    }

    override fun productClickEventListener(eventType: Int, productId: Int) {
        transaction = fragmentManager.beginTransaction()

        when (eventType) {
            EventType.FRAGMENT_PRODUCT_INFO -> {
                productInfoFragment = ProductInfoFragment()
                val param = Bundle(1)
                param.putInt("id", productId)
                productInfoFragment.arguments = param
                transaction.replace(R.id.main_frame, productInfoFragment).commit()
            }
        }
    }

    override fun bodyPartClickEventListener(eventType: Int, bodyId: Int, title: String) {
        transaction = fragmentManager.beginTransaction()

        when (eventType) {
            EventType.FRAGMENT_BODY_PRODUCT -> {
                bodyProductFragment = BodyProductFragment()
                val param = Bundle(1)
                param.putInt("id", bodyId)
                param.putString("title", title)
                bodyProductFragment.arguments = param
                transaction.replace(R.id.main_frame, bodyProductFragment).commit()
            }
            EventType.FRAGMENT_COMPARE -> {
                partCompareFragment = PartCompareFragment()
                val param = Bundle(1)
                param.putInt("id", bodyId)
                param.putString("title", title)
                partCompareFragment.arguments = param
                transaction.replace(R.id.main_frame, partCompareFragment).commit()
            }
        }
    }

    override fun paymentCompletedEventListener(eventType: Int, price: Int) {
        transaction = fragmentManager.beginTransaction()

        when (eventType) {
            EventType.FRAGMENT_PAYMENT_COMPLETE -> {
                paymentFragment = PaymentFragment()
                val param = Bundle(1)
                param.putInt("price", price)
                paymentFragment.arguments = param
                transaction.replace(R.id.main_frame, paymentFragment).commit()
            }
        }
    }
}