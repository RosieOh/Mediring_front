package com.mediring.app.model

open class LikeProductEntity {
    var id: Int? = null
    var userId: Int? = null
    var productId: Int? = null
    var refUserEntity: UserEntity? = null
    var refProductEntity: ProductEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "userId = $userId " +
                "productId = $productId " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LikeProductEntity

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (productId != other.productId) return false

        return true
    }

}

