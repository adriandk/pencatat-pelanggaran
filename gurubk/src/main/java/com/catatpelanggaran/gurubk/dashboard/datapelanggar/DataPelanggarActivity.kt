package com.catatpelanggaran.gurubk.dashboard.datapelanggar

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.catatpelanggaran.gurubk.R
import com.catatpelanggaran.gurubk.adapter.AdapterDataPelanggar
import com.catatpelanggaran.gurubk.adapter.AdapterSiswa
import com.catatpelanggaran.gurubk.dashboard.catat.CatatPelanggaranActivity
import com.catatpelanggaran.gurubk.model.Catat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_catat_pelanggaran.*
import kotlinx.android.synthetic.main.activity_catat_pelanggaran.back_button
import kotlinx.android.synthetic.main.activity_data_pelanggar.*
import kotlinx.android.synthetic.main.activity_siswa.*
import kotlinx.android.synthetic.main.activity_siswa.progress_bar
import kotlinx.android.synthetic.main.activity_siswa.siswa_empty

class DataPelanggarActivity : AppCompatActivity() {

    companion object {
        const val DATA_SISWA = "dataSiswa"
    }

    var listSiswa: ArrayList<Catat>? = null

    lateinit var searchManager: SearchManager
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pelanggar)
        setSupportActionBar(toolbar_datapel)

        back_button.setOnClickListener {
            onBackPressed()
        }

        getData(null)
    }

    override fun onResume() {
        super.onResume()
        getData(null)
    }

    private fun getData(query: String?) {
        progress_bar.visibility = View.VISIBLE
        list_datapel.layoutManager = LinearLayoutManager(this)
        list_datapel.hasFixedSize()
        val database = FirebaseDatabase.getInstance().reference

        if (query != null){
            listSiswa = arrayListOf<Catat>()
            database.child("Pelanggar").orderByChild("nama_siswa").startAt(query).endAt(query + "\uf8ff")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@DataPelanggarActivity, "Somethings wrong", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            listSiswa!!.clear()
                            for (i in snapshot.children) {
                                val siswa = i.getValue(Catat::class.java)
                                listSiswa!!.add(siswa!!)
                            }
                            val adapter = AdapterDataPelanggar(listSiswa!!)
                            list_datapel.adapter = adapter
                            progress_bar.visibility = View.GONE
                            siswa_empty.visibility = View.GONE
                            list_datapel.visibility = View.VISIBLE

                            adapter.onItemClick = { selectedSiswa ->
                                val intent = Intent(this@DataPelanggarActivity, CatatPelanggaranActivity::class.java)
                                intent.putExtra(
                                    CatatPelanggaranActivity.DATA_PELANGGAR, selectedSiswa
                                )
                                startActivity(intent)

                            }

                        }
                        else {
                            siswa_empty.visibility = View.VISIBLE
                            list_datapel.visibility = View.GONE
                        }
                    }
                })
        }
        else {
            listSiswa = arrayListOf<Catat>()
            database.child("Pelanggar").orderByChild("nama_siswa")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@DataPelanggarActivity, "Somethings wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            listSiswa!!.clear()
                            for (i in snapshot.children) {
                                val siswa = i.getValue(Catat::class.java)
                                listSiswa!!.add(siswa!!)
                            }
                            val adapter = AdapterDataPelanggar(listSiswa!!)
                            list_datapel.adapter = adapter
                            progress_bar.visibility = View.GONE
                            siswa_empty.visibility = View.GONE
                            list_datapel.visibility = View.VISIBLE

                            adapter.onItemClick = { selectedSiswa ->
                                val intent = Intent(this@DataPelanggarActivity, CatatPelanggaranActivity::class.java)
                                intent.putExtra(
                                    CatatPelanggaranActivity.DATA_PELANGGAR,
                                    selectedSiswa
                                )
                                startActivity(intent)

                            }

                        }
                        else {
                            siswa_empty.visibility = View.VISIBLE
                            list_datapel.visibility = View.GONE
                        }
                    }
                })

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}