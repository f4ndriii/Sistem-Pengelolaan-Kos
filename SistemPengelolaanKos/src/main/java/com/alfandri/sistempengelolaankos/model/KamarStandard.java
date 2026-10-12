package com.alfandri.sistempengelolaankos.model;

/**
 * Kamar tipe Standard: harga sewa = harga dasar.
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
