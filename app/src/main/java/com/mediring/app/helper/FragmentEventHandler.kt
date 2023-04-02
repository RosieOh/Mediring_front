package com.mediring.app.helper

import com.mediring.app.model.ShoppingBasketEntity

interface FragmentEventHandler {
    fun buttonClickEventListener(eventType: Int)
    fun productClickEventListener(eventType: Int, productId: Int)
    fun bodyPartClickEventListener(eventType: Int, bodyId: Int, title: String)
    fun paymentCompletedEventListener(eventType: Int, price: Int)
}