package com.alfandri.sistempengelolaankos.model;

/**
 * Kamar tipe Standard: harga sewa = harga dasar.
 */
public class KamarStandard extends Kamar {

    public KamarStandard() {
        super();
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
