package com.mediring.app.utils

import android.app.Activity
import android.widget.Toast

class BackPressHandler(activity: Activity) {
    private val mActivity: Activity = activity
    private var backPressedTime: Long = 0
    private lateinit var toast: Toast

    fun onBackPressed() {
        toast  = Toast.makeText(mActivity, "\"뒤로\" 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis()
            toast.show()
            return
        }
        if (System.currentTimeMillis() <= backPressedTime + 2000) {
            mActivity.finish()
            toast.cancel()
        }
    }
}