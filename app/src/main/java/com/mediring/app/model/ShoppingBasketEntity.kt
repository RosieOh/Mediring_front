package com.mediring.app.model

open class ShoppingBasketEntity {
    var id: Int? = null
    var productId: Int? = null
    var userId: Int? = null
    var addedAt: String? = null
    var refProductEntity: ProductEntity? = null
    var refUserEntity: UserEntity? = null
    var qty: Int? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "productId = $productId " +
                "userId = $userId " +
                "addedAt = $addedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ShoppingBasketEntity

        if (id != other.id) return false
        if (productId != other.productId) return false
        if (userId != other.userId) return false
        if (addedAt != other.addedAt) return false

        return true
    }

}

