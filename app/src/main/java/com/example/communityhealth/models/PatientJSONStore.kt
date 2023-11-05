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

//GSON Config
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<PatientModel>>() {}.type

// generates a random ID
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

    // retrieves all patients
    override fun findAll(): MutableList<PatientModel> {
        logAll()
        return patients
    }

    // retrieves patient by ID
    override fun findById(id:Long) : PatientModel? {
        val foundPlacemark: PatientModel? = patients.find { it.id == id }
        return foundPlacemark
    }

    //creates patient data and save to JSON file (patientJSON)
    override fun create(patient: PatientModel) {
        val sharedPreferences = context.getSharedPreferences("YourPrefName", Context.MODE_PRIVATE)
        val loggedInUserName = sharedPreferences.getString("LoggedInUserNameKey", "defaultUserName") ?: "defaultUserName"

        patient.id = generateRandomId()
        patient.userName = loggedInUserName
        patients.add(patient)
        serialize()
    }

    //update the existing data
    override fun update(patient: PatientModel) {
        val patientsList = findAll() as ArrayList<PatientModel>
        var foundPatient: PatientModel? = patientsList.find { p -> p.id == patient.id }
        if (foundPatient != null) {
            foundPatient.MRN = patient.MRN
            foundPatient.firstName = patient.firstName
            foundPatient.lastName = patient.lastName
            foundPatient.image = patient.image
            foundPatient.lat = patient.lat
            foundPatient.lng = patient.lng
            foundPatient.zoom = patient.zoom
            foundPatient.road = patient.road
            foundPatient.town = patient.town
            foundPatient.eircode = patient.eircode
            foundPatient.userName = patient.userName
        }
        serialize()
    }

    //retrieves patients based on userName
    fun findByUsername(userName: String): List<PatientModel> {
        return patients.filter { it.userName == userName }
    }

    override fun delete(patient: PatientModel) {
        patients.remove(patient)
        serialize()
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


