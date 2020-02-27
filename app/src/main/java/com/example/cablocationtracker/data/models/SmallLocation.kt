package com.example.cablocationtracker.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SmallLocation (var latitude: Double?, var longitude: Double?, var currTime: Long?): Parcelable{
    constructor(): this(0.0,0.0, 0L)
}