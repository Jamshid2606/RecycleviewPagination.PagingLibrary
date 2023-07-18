package com.jama.recycleviewpaginationpaginglibrary.models

import com.google.gson.annotations.SerializedName
class Data {
    @SerializedName("avatar")
    var avatar: String?=null

    @SerializedName("email")
    var email: String?=null

    @SerializedName("first_name")
    var first_name: String?=null

    @SerializedName("id")
    var id: Int?=null

    @SerializedName("last_name")
    var last_name: String?=null

    constructor(avatar: String?, email: String?, first_name: String?, id: Int?, last_name: String?) {
        this.avatar = avatar
        this.email = email
        this.first_name = first_name
        this.id = id
        this.last_name = last_name
    }

    constructor()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (avatar != other.avatar) return false
        if (email != other.email) return false
        if (first_name != other.first_name) return false
        if (id != other.id) return false
        if (last_name != other.last_name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatar?.hashCode() ?: 0
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (first_name?.hashCode() ?: 0)
        result = 31 * result + (id ?: 0)
        result = 31 * result + (last_name?.hashCode() ?: 0)
        return result
    }

}