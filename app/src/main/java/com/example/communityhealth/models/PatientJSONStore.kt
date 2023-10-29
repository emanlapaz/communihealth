package com.example.communityhealth.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.example.communityhealth.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "patients.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<PatientModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PatientJSONStore(private val context: Context) : PatientStore {

    var patients = mutableListOf<PatientModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PatientModel> {
        logAll()
        return patients
    }

    override fun create(patient: PatientModel) {
        patient.id = generateRandomId()
        patients.add(patient)
        serialize()
    }

    override fun update(patient: PatientModel) {
        // todo
    }
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(patients, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        patients = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        patients.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

