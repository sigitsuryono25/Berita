package com.project.lauwba.berita

import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.project.lauwba.androidjsondemo.libs.RequestHandler
import org.json.JSONArray
import org.json.JSONObject

class DetailBerita : AppCompatActivity() {

    lateinit var gambar: ImageView
    lateinit var tanggal: TextView
    lateinit var judul: TextView
    lateinit var isi: TextView
    lateinit var pd: ProgressDialog
    lateinit var req: RequestHandler
    lateinit var id_berita: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Detail Berita"

        gambar = findViewById(R.id.gambarBerita)
        tanggal = findViewById(R.id.tanggaBerita)
        judul = findViewById(R.id.judul)
        isi = findViewById(R.id.content)

        req = RequestHandler()

        var i: Intent = intent

        if (i.getStringExtra(ConfigApp.ID_BERITA).toString().equals("")) {
            Toast.makeText(this@DetailBerita, "Tidak ada ID berita terpilih", Toast.LENGTH_SHORT).show()
        } else {
            id_berita = i.getStringExtra(ConfigApp.ID_BERITA).toString()

            getDetailBerita().execute()
        }
    }

    inner class getDetailBerita : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog.show(this@DetailBerita, "", "Memuat halaman", false, false)
        }

        override fun doInBackground(vararg params: String?): String {
            var result = req.sendGetRequest(ConfigApp.URL_BERITA_DETAIL + id_berita)

            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            pd.dismiss()
            try {
                var jsonObject = JSONObject(result)
                var jsonArray: JSONArray = jsonObject.getJSONArray(ConfigApp.DATA_ARRAY)
                if (jsonArray.length() > 0) {
                    for (i in 0..jsonArray.length()) {
                        var content: JSONObject = jsonArray.getJSONObject(i)

                        Glide
                                .with(this@DetailBerita)
                                .load(ConfigApp.URL_GAMBAR + content.getString("gambar"))
                                .into(gambar)

                        tanggal.text = content.getString("tgl_berita")
                        judul.text = content.getString("judul")
                        isi.text = Html.fromHtml(content.getString("isi_berita"))

                    }
                } else {
                    Toast.makeText(this@DetailBerita, "Tidak ada berita", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}