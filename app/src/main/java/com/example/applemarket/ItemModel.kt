package com.example.applemarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemModel(
    val itemImg: Int,
    val title:String,
    val description:String,
    val name:String,
    val price: Int,
    val address: String,
    var heart:Int,
    val comment:Int,
    var heartClicked : Boolean
): Parcelable