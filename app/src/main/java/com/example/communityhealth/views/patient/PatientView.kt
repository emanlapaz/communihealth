package com.example.communityhealth.views.patient

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.communityhealth.R
import com.example.communityhealth.databinding.ActivityPatientBinding
import com.example.communityhealth.models.PatientModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import timber.log.Timber.Forest.i

class PatientView : AppCompatActivity() {

    private lateinit var binding: ActivityPatientBinding
    private lateinit var presenter: PatientPresenter
    var patient = PatientModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = PatientPresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cachePatient(binding.patientMRN.text.toString(), binding.patientFirstName.text.toString(), binding.patientLastName.text.toString())
            presenter.doSelectImage()
        }

        binding.patientLocation.setOnClickListener {
            presenter.cachePatient(binding.patientMRN.text.toString(), binding.patientFirstName.text.toString(), binding.patientLastName.text.toString())
            presenter.doSetLocation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.patient_menu, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (binding.patientMRN.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_patient_MRN, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    // presenter.cachePlacemark(binding.placemarkTitle.text.toString(), binding.description.text.toString())
                    presenter.doAddOrSave(binding.patientMRN.text.toString(), binding.patientFirstName.text.toString(), binding.patientLastName.text.toString())
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showPatient(patient: PatientModel) {
        binding.patientMRN.setText(patient.MRN)
        binding.patientFirstName.setText(patient.firstName)
        binding.patientLastName.setText(patient.lastName)
        Picasso.get()
            .load(patient.image)
            .into(binding.patientImage)
        if (patient.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_patient_image)
        }

    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.patientImage)
        binding.chooseImage.setText(R.string.change_patient_image)
    }

}