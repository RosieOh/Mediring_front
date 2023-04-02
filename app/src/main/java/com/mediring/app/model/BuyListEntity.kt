package com.mediring.app.model

import java.time.LocalDateTime

open class BuyListEntity {
    var id: Int? = null
    var productId: Int? = null
    var userId: Int? = null
    var boughtAt: String? = null
    var quantity: Int? = null
    var refProductEntity: ProductEntity? = null
    var refUserEntity: UserEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "productId = $productId " +
                "userId = $userId " +
                "boughtAt = $boughtAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as BuyListEntity

        if (id != other.id) return false
        if (productId != other.productId) return false
        if (userId != other.userId) return false
        if (boughtAt != other.boughtAt) return false

        return true
    }

}

