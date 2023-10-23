package com.example.communityhealth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.communityhealth.databinding.CardPatientBinding
import com.example.communityhealth.models.PatientModel
import com.squareup.picasso.Picasso

interface PatientListener {
    fun onPatientClick(patient: PatientModel)
}
class PatientAdapter constructor(private var patients: List<PatientModel>,
                                 private val listener: PatientListener) :
    RecyclerView.Adapter<PatientAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPatientBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val patient = patients[holder.adapterPosition]
        holder.bind(patient, listener)
    }
    override fun getItemCount(): Int = patients.size

    class MainHolder(private val binding : CardPatientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(patient: PatientModel, listener: PatientListener) {
            binding.patientMRN.text = patient.MRN
            binding.patientLastName.text = patient.lastName
            binding.patientFirstName.text = patient.firstName
            Picasso.get().load(patient.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onPatientClick(patient) }
        }
    }
}