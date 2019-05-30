package com.project.lauwba.androidjsondemo.libs

import android.support.constraint.Constraints.TAG
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*
import javax.net.ssl.HttpsURLConnection

class RequestHandler {

    fun sendPostRequest(requestURL: String,
                        postDataParams: HashMap<String, String>): String {
        val url: URL

        var sb = StringBuilder()
        try {
            url = URL(requestURL)

            val conn = url.openConnection() as HttpURLConnection

            conn.readTimeout = 15000
            conn.connectTimeout = 15000
            conn.requestMethod = "POST"
            conn.doInput = true
            conn.doOutput = true

            val os = conn.outputStream

            val writer = BufferedWriter(
                    OutputStreamWriter(os, "UTF-8"))
            writer.write(getPostDataString(postDataParams))

            writer.flush()
            writer.close()
            os.close()
            val responseCode = conn.responseCode

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                val br = BufferedReader(InputStreamReader(conn.inputStream))
                sb = StringBuilder()
                var line: String? = null;
                while ({ line = br.readLine(); line }() != null) { // <--- The IDE asks me to replace this line for while(true), what the...?
                    sb.append(line).append("\n")
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return sb.toString()
    }

    fun sendGetRequest(requestURL: String): String {
        val sb = StringBuilder()
        try {
            val url = URL(requestURL)
            val con = url.openConnection() as HttpURLConnection
            val bufferedReader = BufferedReader(InputStreamReader(con.inputStream))

            var line: String? = null;
            while ({ line = bufferedReader.readLine(); line }() != null) { // <--- The IDE asks me to replace this line for while(true), what the...?
               sb.append(line).append("\n")
            }
            Log.i(TAG, "sendGetRequest: $requestURL")
        } catch (e: Exception) {
        }

        return sb.toString()
    }

    fun sendGetRequestParam(requestURL: String, id: String): String {
        val sb = StringBuilder()
        try {
            val url = URL(requestURL + id)
            val con = url.openConnection() as HttpURLConnection
            val bufferedReader = BufferedReader(InputStreamReader(con.inputStream))

            var line: String? = null;
            while ({ line = bufferedReader.readLine(); line }() != null) { // <--- The IDE asks me to replace this line for while(true), what the...?
                sb.append(line).append("\n")
            }
        } catch (e: Exception) {

        }

        return sb.toString()
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getPostDataString(params: HashMap<String, String>): String {
        val result = StringBuilder()
        var first = true
        for ((key, value) in params) {
            if (first)
                first = false
            else
                result.append("&")

            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value, "UTF-8"))
        }

        return result.toString()
    }
}
