package com.alfandri.sistempengelolaankos.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pengelola utama: menyimpan kamar, penghuni, dan riwayat pembayaran.
 */
public class KosManager {

    private final List<Kamar> daftarKamar;
    private final List<Penghuni> daftarPenghuni;
    private final List<Pembayaran> riwayatPembayaran;

    public KosManager() {
        daftarKamar = new ArrayList<>();
        daftarPenghuni = new ArrayList<>();
        riwayatPembayaran = new ArrayList<>();
    }

    // Menambahkan kamar
    public void tambahKamar(Kamar kamar) {
        if (kamar == null) {
            throw new IllegalArgumentException(
                    "Kamar tidak boleh null");
        }

        daftarKamar.add(kamar);
    }

    // Mendaftarkan penghuni
    public void tambahPenghuni(Penghuni penghuni) {
        if (penghuni == null) {
            throw new IllegalArgumentException(
                    "Penghuni tidak boleh null");
        }

        daftarPenghuni.add(penghuni);
    }

    // Mencari kamar berdasarkan nomor
    public Kamar cariKamar(int nomorKamar) {
        for (Kamar kamar : daftarKamar) {
            if (kamar.getNomorKamar() == nomorKamar) {
                return kamar;
            }
        }

        return null;
    }

    // Menempatkan penghuni ke kamar
    public void tempatkanPenghuni(
            Penghuni penghuni, int nomorKamar) {

        if (!daftarPenghuni.contains(penghuni)) {
            throw new IllegalArgumentException(
                    "Penghuni belum terdaftar");
        }

        Kamar kamar = cariKamar(nomorKamar);

        if (kamar == null) {
            throw new IllegalArgumentException(
                    "Kamar tidak ditemukan");
        }

        kamar.tempati(penghuni);
    }

    // Mencatat pembayaran dan menyimpan riwayatnya
    public Pembayaran prosesPembayaran(
            String idPembayaran,
            int nomorKamar,
            String bulanBayar,
            double jumlahBayar) {

        Kamar kamar = cariKamar(nomorKamar);

        if (kamar == null) {
            throw new IllegalArgumentException(
                    "Kamar tidak ditemukan");
        }

        Pembayaran pembayaran = new Pembayaran(
                idPembayaran,
                kamar,
                bulanBayar,
                jumlahBayar
        );

        riwayatPembayaran.add(pembayaran);

        return pembayaran;
    }

    // Mengakses daftar kamar
    public List<Kamar> getDaftarKamar() {
        return Collections.unmodifiableList(daftarKamar);
    }

    // Mengakses daftar penghuni
    public List<Penghuni> getDaftarPenghuni() {
        return Collections.unmodifiableList(daftarPenghuni);
    }

    // Mengakses riwayat pembayaran
    public List<Pembayaran> getRiwayatPembayaran() {
        return Collections.unmodifiableList(riwayatPembayaran);
    }
}
