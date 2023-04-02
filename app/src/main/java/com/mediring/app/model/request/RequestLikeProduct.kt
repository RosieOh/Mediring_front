package com.mediring.app.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestLikeProduct(
        val userId: Int,
        val productId: Int
)