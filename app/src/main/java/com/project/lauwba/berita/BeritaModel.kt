package com.project.lauwba.berita

class BeritaModel(id: String, gambar: String, title: String, content: String, tanggal: String) {

    var _gambar: String = gambar
        get() = field
        set(value) {
            field = value
        }

    var _title: String = title
        get() = field
        set(value) {
            field = value
        }
    var _content: String = content
        get() = field
        set(value) {
            field = value
        }
    var _tanggal: String = tanggal
        get() = field
        set(value) {
            field = value
        }

    var _id: String = id
        get() = field
        set(value) {
            field = value
        }
}