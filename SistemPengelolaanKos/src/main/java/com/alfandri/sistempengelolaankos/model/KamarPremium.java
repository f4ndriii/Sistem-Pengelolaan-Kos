package com.alfandri.sistempengelolaankos.model;

/**
 * Kamar tipe Premium: harga sewa = harga dasar + biaya fasilitas khusus.
 */
public class KamarPremium extends Kamar {

    /** Biaya tambahan untuk fasilitas premium (WiFi + AC + kamar mandi dalam). */
    public static final double BIAYA_FASILITAS = 300_000;

    public KamarPremium() {
        super();
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

    @Override
    public String getInfoDetail() {
        return super.getInfoDetail() + "\n"
             + "Rincian     : dasar " + Pembayaran.formatRupiah(getHargaDasar())
             + " + fasilitas " + Pembayaran.formatRupiah(BIAYA_FASILITAS);
    }
}
