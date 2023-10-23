package com.example.communityhealth.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.communityhealth.R
import com.example.communityhealth.adapters.PatientAdapter
import com.example.communityhealth.adapters.PatientListener
import com.example.communityhealth.databinding.ActivityPatientListBinding
import com.example.communityhealth.main.MainApp
import com.example.communityhealth.models.PatientModel

class PatientListActivity : AppCompatActivity(), PatientListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPatientListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter
        binding.recyclerView.adapter = PatientAdapter(app.patients.findAll(),this)
        //PatientAdapter(app.patients.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, PatientActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.patients.findAll().size)
            }
        }
    override fun onPatientClick(patient: PatientModel) {
        val launcherIntent = Intent(this, PatientActivity::class.java)
        launcherIntent.putExtra("patient_edit", patient)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0,app.patients.findAll().size)
            }
        }
}
