package com.example.ana_anlume.pertemuan_2

import kotlin.math.pow

class Calcu {

    fun luasSegitiga(alas: Double, tinggi: Double): Double {
        return 0.5 * alas * tinggi
    }

    fun volumeBola(r: Double): Double {
        return (4.0 / 3.0) * Math.PI * r.pow(3)
    }
}