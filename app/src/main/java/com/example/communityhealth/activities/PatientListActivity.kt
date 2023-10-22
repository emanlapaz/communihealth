package com.example.communityhealth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.communityhealth.databinding.ActivityPatientListBinding
import com.example.communityhealth.databinding.CardPatientBinding
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.PatientModel

class PatientListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPatientListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = PatientAdapter(app.patients)
    }
}

class PatientAdapter constructor(private var patients: List<PatientModel>) :
    RecyclerView.Adapter<PatientAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPatientBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val patient = patients[holder.adapterPosition]
        holder.bind(patient)
    }
    override fun getItemCount(): Int = patients.size

    class MainHolder(private val binding : CardPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: PatientModel) {
            binding.patientMRN.text = patient.MRN
            binding.patientLastName.text = patient.lastName
            binding.patientFirstName.text = patient.firstName
        }
    }
}