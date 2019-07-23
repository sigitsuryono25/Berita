package com.project.lauwba.berita

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class BeritaAdapter(context: Context, beritaList: ArrayList<BeritaModel>) : RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {


    var context: Context = context
    var beritaList: ArrayList<BeritaModel>

    init {
        this.beritaList = beritaList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View
        var layoutInflater: LayoutInflater

        layoutInflater = LayoutInflater.from(parent.context)
        v = layoutInflater.inflate(R.layout.berita_adapter, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return beritaList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var berita: BeritaModel = beritaList.get(position)

        holder.tanggal.text = berita._tanggal
        holder.judul.text = berita._title
        holder.id_berita.text = berita._id
        Glide
                .with(context)
                .load(ConfigApp.URL_GAMBAR + berita._gambar)
                .into(holder.gambar)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tanggal: TextView
        var judul: TextView
        var gambar: ImageView
        var id_berita: TextView

        init {
            tanggal = itemView.findViewById(R.id.tanggal)
            judul = itemView.findViewById(R.id.judul)
            gambar = itemView.findViewById(R.id.gambarBerita)
            id_berita = itemView.findViewById(R.id.id_berita)


        }
    }
}