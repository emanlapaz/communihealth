package com.example.communityhealth.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class PatientModel(var id: Long = 0,
                        var MRN:String = "",
                        var lastName: String = "",
                        var firstName: String = "",
                        var image: Uri = Uri.EMPTY) : Parcelable
