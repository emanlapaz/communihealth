package com.example.communityhealth.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize

data class UserModel(
    var userId: Long,
    var username: String,
    var password: String ): Parcelable
