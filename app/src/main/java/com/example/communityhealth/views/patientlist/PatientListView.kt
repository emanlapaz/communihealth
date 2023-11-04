package com.example.communityhealth.views.patientlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communityhealth.R
import com.example.communityhealth.views.user.LoginView
import com.example.communityhealth.databinding.ActivityPatientListBinding
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.PatientModel
import com.example.communityhealth.patient.adapters.PatientAdapter
import com.example.communityhealth.patient.adapters.PatientListener

class PatientListView : AppCompatActivity(), PatientListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPatientListBinding
    lateinit var presenter: PatientListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = PatientListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadPatients()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddPatient() }
            R.id.item_map -> { presenter.doShowPatientsMap() }
            R.id.item_logout -> { presenter.doLogOut() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPatientClick(patient: PatientModel, position: Int) {
        this.position = position
        presenter.doEditPatient(patient, this.position)
    }

    private fun loadPatients() {
        binding.recyclerView.adapter = PatientAdapter(presenter.getPatients(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getPatients().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
    fun goToLogin() {
        val loginIntent = Intent(this, LoginView::class.java)
        startActivity(loginIntent)
        finish()
    }
}