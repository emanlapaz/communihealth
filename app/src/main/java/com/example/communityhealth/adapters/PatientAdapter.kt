package com.example.communityhealth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.communityhealth.databinding.CardPatientBinding
import com.example.communityhealth.models.PatientModel

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