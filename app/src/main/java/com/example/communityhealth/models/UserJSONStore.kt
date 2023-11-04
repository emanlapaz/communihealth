package com.example.communityhealth.models

import android.content.Context
import com.google.gson.Gson
import com.example.communityhealth.helpers.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

const val USER_JSON_FILE = "users.json"
val gsonUserBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .create()
val userListType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore(private val context: Context): UserStore {

    var users = mutableListOf<UserModel>()

    init {
        if (exists(context, USER_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.userId = generateRandomUserId()
        users.add(user)
        serialize()
    }

    override fun update(user: UserModel) {
        val usersList = findAll() as ArrayList<UserModel>
        var foundUser: UserModel? = users.find { p -> p.userId == user.userId }
        if (foundUser != null) {
            foundUser.username = user.username
            foundUser.password = user.password
        }
        serialize()
    }

    override fun findById(id:Long) : UserModel? {
        val foundUser: UserModel? = users.find { it.userId == id }
        return foundUser
    }

    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonUserBuilder.toJson(users)
        write(context, USER_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, USER_JSON_FILE)
        users = gsonUserBuilder.fromJson(jsonString, userListType)
    }
}