package com.example.communityhealth.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PatientModel(var id: Long = 0,
                        var MRN: String = "",
                        var lastName: String = "",
                        var firstName: String = "",
                        var image: Uri = Uri.EMPTY,
                        var lat: Double = 0.0,
                        var lng: Double = 0.0,
                        var zoom: Float = 0f,
                        var road: String = "",
                        var town: String = "",
                        var eircode: String = "",
                        var userName: String = ""
) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f,
                    var road: String = "",
                    var town: String = "",
                    var eircode: String = "",
                    var userName: String = "") : Parcelable
