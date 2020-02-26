package com.example.cablocationtracker.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var userName:String?, var email:String?,var id:String?, var isSuperUser: Boolean?): Parcelable {
    constructor():this("","","",false)  //empty constructor needed for firebase
}