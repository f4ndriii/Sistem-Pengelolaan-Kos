package com.alfandri.sistempengelolaankos.model;

/**
 * Kamar tipe Premium: harga sewa = harga dasar + biaya fasilitas khusus.
 */
public class KamarPremium extends Kamar {

    private final double biayaFasilitasKhusus;

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
        return "Kasur, lemari, AC, WiFi, kamar mandi dalam";
    }

    @Override
    public String getInfoDetail() {
        return super.getInfoDetail() + "\n"
             + "Rincian     : dasar " + Pembayaran.formatRupiah(getHargaDasar())
             + " + fasilitas " + Pembayaran.formatRupiah(biayaFasilitasKhusus);
    }
}
