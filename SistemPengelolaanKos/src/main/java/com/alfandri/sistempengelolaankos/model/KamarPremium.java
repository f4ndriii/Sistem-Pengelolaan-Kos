package com.alfandri.sistempengelolaankos.model;

/**
 * Kamar tipe Premium: harga sewa = harga dasar + biaya fasilitas khusus.
 */
public class KamarPremium extends Kamar {

    public static final double HARGA_DEFAULT = 1_200_000;
    /** Biaya tambahan untuk fasilitas premium (WiFi + AC + kamar mandi dalam). */
    public static final double BIAYA_FASILITAS = 300_000;

    private final double biayaFasilitasKhusus;

    public KamarPremium(String nomorKamar) {
        this(nomorKamar, HARGA_DEFAULT, BIAYA_FASILITAS);
    }

    public KamarPremium(String nomorKamar, double hargaDasar) {
        this(nomorKamar, hargaDasar, BIAYA_FASILITAS);
    }

    public KamarPremium(String nomorKamar, double hargaDasar, double biayaFasilitasKhusus) {
        super(nomorKamar, hargaDasar);
        if (biayaFasilitasKhusus < 0) {
            throw new IllegalArgumentException("Biaya fasilitas tidak boleh negatif");
        }
        this.biayaFasilitasKhusus = biayaFasilitasKhusus;
    }

    public double getBiayaFasilitasKhusus() {
        return biayaFasilitasKhusus;
    }

    @Override
    public String getTipe() {
        return "Premium";
    }

    @Override
    public double hitungHargaSewa() {
        return getHargaDasar() + biayaFasilitasKhusus;
    }

    @Override
    public String getFasilitas() {
        return "Kasur, lemari, meja belajar, AC, kamar mandi dalam, WiFi";
    }

    @Override
    public String getInfoDetail() {
        return super.getInfoDetail() + "\n"
             + "Rincian     : dasar " + Pembayaran.formatRupiah(getHargaDasar())
             + " + fasilitas " + Pembayaran.formatRupiah(biayaFasilitasKhusus);
    }
}
