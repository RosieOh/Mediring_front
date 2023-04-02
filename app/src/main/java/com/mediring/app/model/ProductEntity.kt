package com.mediring.app.model

open class ProductEntity {
    var id: Int? = null
    var bodyId: Int? = null
    var title: String? = null
    var origin: String? = null
    var company: String? = null
    var efficacy: String? = null
    var volume: String? = null
    var price: Int? = null
    var thumb: String? = null
    var isActive: Boolean? = null
    var type: String? = null
    var info: String? = null
    var body: BodyEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "bodyId = $bodyId " +
                "title = $title " +
                "origin = $origin " +
                "company = $company " +
                "efficacy = $efficacy " +
                "volume = $volume " +
                "price = $price " +
                "thumb = $thumb " +
                "isActive = $isActive " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ProductEntity

        if (id != other.id) return false
        if (bodyId != other.bodyId) return false
        if (title != other.title) return false
        if (origin != other.origin) return false
        if (company != other.company) return false
        if (efficacy != other.efficacy) return false
        if (volume != other.volume) return false
        if (price != other.price) return false
        if (thumb != other.thumb) return false
        if (isActive != other.isActive) return false

        return true
    }

}

