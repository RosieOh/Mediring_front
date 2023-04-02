package com.mediring.app.model

open class UserEntity {
    var id: Int? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var phone: String? = null
    var userRole: String? = null
    var addresses: List<AddressEntity>? = null
    var isActive: Boolean? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "name = $name " +
                "email = $email " +
                "password = $password " +
                "phone = $phone " +
                "userRole = $userRole " +
                "isActive = $isActive " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (phone != other.phone) return false
        if (userRole != other.userRole) return false
        if (isActive != other.isActive) return false

        return true
    }

}

