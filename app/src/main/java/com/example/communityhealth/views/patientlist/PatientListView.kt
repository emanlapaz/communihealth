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
import com.example.communityhealth.models.PatientJSONStore
import com.example.communityhealth.models.PatientModel
import com.example.communityhealth.patient.adapters.PatientAdapter
import com.example.communityhealth.patient.adapters.PatientListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PatientListView : AppCompatActivity(), PatientListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPatientListBinding
    lateinit var presenter: PatientListPresenter
    private var position: Int = 0
    private var userName: String = ""

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
        userName = intent.getStringExtra("userName") ?: ""
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
        GlobalScope.launch(Dispatchers.Main) {
            val patientStore = PatientJSONStore(this@PatientListView)
            val patients = if (userName.isNotEmpty()) {
                patientStore.findByUsername(userName)
            } else {
                patientStore.findAll()
            }
            binding.recyclerView.adapter = PatientAdapter(patients, this@PatientListView)
            onRefresh()
        }
    }

    fun onRefresh() {
        GlobalScope.launch(Dispatchers.Main) {
            val patientStore = PatientJSONStore(this@PatientListView)
            val updatedPatients = if (userName.isNotEmpty()) {
                patientStore.findByUsername(userName)
            } else {
                patientStore.findAll()
            }
            (binding.recyclerView.adapter as? PatientAdapter)?.let { adapter ->
                adapter.patients = updatedPatients
                adapter.notifyDataSetChanged()
            }
        }
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