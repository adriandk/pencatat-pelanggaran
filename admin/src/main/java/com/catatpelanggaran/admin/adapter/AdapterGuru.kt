package com.catatpelanggaran.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.catatpelanggaran.admin.R
import com.catatpelanggaran.admin.model.Guru
import kotlinx.android.synthetic.main.item_siswa.view.*

class AdapterGuru(val guru: ArrayList<Guru>) :
    RecyclerView.Adapter<AdapterGuru.ViewHolder>() {

    var onItemClick: ((Guru) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterGuru.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_siswa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterGuru.ViewHolder, position: Int) {
        holder.bind(guru[position])
    }

    override fun getItemCount(): Int = guru.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(guruData: Guru) {
            with(itemView) {

                val absen = position + 1

                nama_siswa.text = guruData.nama
                nis_siswa.text = guruData.nip
                no_absen.text = absen.toString()
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(guru[adapterPosition])
            }
        }

    }
}