/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfandri.sistempengelolaankos.model;

/**
 *
 * @author USER
 */
public class KamarPremium extends Kamar {
    
    public static final double HARGA_DEFAULT = 1_200_000;
    /** Biaya tambahan untuk fasilitas premium (WiFi + AC + kamar mandi dalam). */
    public static final double BIAYA_FASILITAS = 300_000;

    public KamarPremium(String nomorKamar) {
        super(nomorKamar, HARGA_DEFAULT);
    }

    public KamarPremium(String nomorKamar, double hargaDasar) {
        super(nomorKamar, hargaDasar);
    }

    @Override
    public String getTipe() {
        return "Premium";
    }

    @Override
    public double hitungHargaSewa() {
        return getHargaDasar() + BIAYA_FASILITAS;
    }

    @Override
    public String getFasilitas() {
        return "Kasur, lemari, meja belajar, AC, kamar mandi dalam, WiFi";
    }
}
