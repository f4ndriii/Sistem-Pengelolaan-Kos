/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfandri.sistempengelolaankos.model;

/**
 *
 * @author USER
 */
public class KamarStandard extends Kamar {

    public static final double HARGA_DEFAULT = 800_000;

    public KamarStandard(String nomorKamar) {
        super(nomorKamar, HARGA_DEFAULT);
    }

    public KamarStandard(String nomorKamar, double hargaDasar) {
        super(nomorKamar, hargaDasar);
    }

    @Override
    public String getTipe() {
        return "Standard";
    }

    @Override
    public double hitungHargaSewa() {
        return getHargaDasar();
    }

    @Override
    public String getFasilitas() {
        return "Kasur, lemari, meja belajar, kipas angin, kamar mandi luar";
    }
}
