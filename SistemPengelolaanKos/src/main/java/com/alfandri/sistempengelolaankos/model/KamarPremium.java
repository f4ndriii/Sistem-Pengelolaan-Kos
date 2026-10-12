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
    private final double biayaFasilitas;

    public KamarPremium(String nomorKamar) {
        this(nomorKamar, HARGA_DEFAULT, BIAYA_FASILITAS);
    }

    public KamarPremium(String nomorKamar, double hargaDasar) {
        this(nomorKamar, hargaDasar, BIAYA_FASILITAS);
    }

    public KamarPremium(String nomorKamar, double hargaDasar, double biayaFasilitas) {
        super(nomorKamar, hargaDasar);
        if (biayaFasilitas < 0) {
            throw new IllegalArgumentException("Biaya fasilitas tidak boleh negatif");
        }
        this.biayaFasilitas = biayaFasilitas;
    }

    public double getBiayaFasilitas() {
        return biayaFasilitas;
    }

    @Override
    public String getTipe() {
        return "Premium";
    }

    @Override
    public double hitungHargaSewa() {
        return getHargaDasar() + biayaFasilitas;
    }

    @Override
    public String getFasilitas() {
        return "Kasur, lemari, meja belajar, AC, kamar mandi dalam, WiFi";
    }
}
