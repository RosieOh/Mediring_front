package com.mediring.app.model

open class AddressEntity {
    var id: Int? = null
    var userId: Int? = null
    var postCode: String? = null
    var postText: String? = null
    var postDetail: String? = null
    var isActive: Boolean? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "userId = $userId " +
                "postCode = $postCode " +
                "postText = $postText " +
                "postDetail = $postDetail " +
                "isActive = $isActive " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AddressEntity

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (postCode != other.postCode) return false
        if (postText != other.postText) return false
        if (postDetail != other.postDetail) return false
        if (isActive != other.isActive) return false

        return true
    }

}

