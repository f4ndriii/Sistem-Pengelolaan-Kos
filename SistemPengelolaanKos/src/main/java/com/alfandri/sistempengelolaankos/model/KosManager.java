/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfandri.sistempengelolaankos.model;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author USER
 */
public class KosManager {

    private final List<Kamar> daftarKamar = new ArrayList<>();
    private final List<Penghuni> daftarPenghuni = new ArrayList<>();
    private final List<Pembayaran> daftarPembayaran = new ArrayList<>();
    private int idCounter = 1;

    public void tambahKamar(Kamar kamar) {
        if (kamar == null) {
            throw new IllegalArgumentException("Kamar tidak boleh null");
        }
        if (cariKamarOpt(kamar.getNomorKamar()).isPresent()) {
            throw new IllegalArgumentException("Kamar dengan nomor " + kamar.getNomorKamar() + " sudah ada");
        }
        daftarKamar.add(kamar);
    }

    public void hapusKamar(String nomorKamar) {
        Kamar k = cariKamar(nomorKamar);
        if (k.isTerisi()) {
            throw new IllegalArgumentException("Kamar " + nomorKamar + " sedang terisi, tidak dapat dihapus");
        }
        daftarKamar.remove(k);
    }

    public List<Kamar> getSemuaKamar() {
        return new ArrayList<>(daftarKamar);
    }

    public List<Kamar> getKamarKosong() {
        List<Kamar> kosong = new ArrayList<>();
        for (Kamar k : daftarKamar) {
            if (!k.isTerisi()) {
                kosong.add(k);
            }
        }
        return kosong;
    }

    public int getJumlahTerisi() {
        int count = 0;
        for (Kamar k : daftarKamar) {
            if (k.isTerisi()) {
                count++;
            }
        }
        return count;
    }

    public Kamar cariKamar(String nomorKamar) {
        for (Kamar k : daftarKamar) {
            if (k.getNomorKamar().equalsIgnoreCase(nomorKamar)) {
                return k;
            }
        }
        throw new IllegalArgumentException("Kamar dengan nomor " + nomorKamar + " tidak ditemukan");
    }

    private Optional<Kamar> cariKamarOpt(String nomorKamar) {
        for (Kamar k : daftarKamar) {
            if (k.getNomorKamar().equalsIgnoreCase(nomorKamar)) {
                return Optional.of(k);
            }
        }
        return Optional.empty();
    }

    public Penghuni checkIn(String nama, String hp, String nomorKamar) {
        Kamar k = cariKamar(nomorKamar);
        if (k.isTerisi()) {
            throw new IllegalArgumentException("Kamar " + nomorKamar + " sudah terisi");
        }
        String idPenghuni = String.format("P%03d", idCounter++);
        Penghuni p = new Penghuni(idPenghuni, nama, hp);
        k.tempati(p);
        daftarPenghuni.add(p);
        return p;
    }

    public Penghuni checkOut(String nomorKamar) {
        Kamar k = cariKamar(nomorKamar);
        if (!k.isTerisi()) {
            throw new IllegalArgumentException("Kamar " + nomorKamar + " sedang kosong");
        }
        Penghuni lama = k.kosongkan();
        daftarPenghuni.remove(lama);
        return lama;
    }

    public List<Penghuni> getSemuaPenghuni() {
        return new ArrayList<>(daftarPenghuni);
    }

    public Kamar cariKamarPenghuni(String penghuniId) {
        for (Kamar k : daftarKamar) {
            if (k.isTerisi() && k.getPenghuni().getId().equals(penghuniId)) {
                return k;
            }
        }
        throw new IllegalArgumentException("Penghuni dengan ID " + penghuniId + " tidak ditemukan di kamar manapun");
    }

    public Pembayaran bayar(String nomorKamar, Month bulan, Year tahun) {
        Kamar k = cariKamar(nomorKamar);
        if (!k.isTerisi()) {
            throw new IllegalArgumentException("Kamar " + nomorKamar + " sedang tidak terisi");
        }
        for (Pembayaran p : daftarPembayaran) {
            if (p.getNomorKamar().equalsIgnoreCase(nomorKamar) && p.getBulan() == bulan && p.getTahun().equals(tahun)) {
                throw new IllegalArgumentException("Kamar " + nomorKamar + " sudah membayar untuk bulan " + bulan + " " + tahun);
            }
        }
        String idBayar = String.format("PAY%03d", daftarPembayaran.size() + 1);
        Pembayaran bayar = new Pembayaran(idBayar, k.getPenghuni(), k.getNomorKamar(), bulan, tahun, k.hitungHargaSewa());
        daftarPembayaran.add(bayar);
        return bayar;
    }

    public List<Pembayaran> getSemuaPembayaran() {
        return new ArrayList<>(daftarPembayaran);
    }

    public List<Kamar> getTunggakan(Month bulan, Year tahun) {
        List<Kamar> tunggakan = new ArrayList<>();
        for (Kamar k : daftarKamar) {
            if (k.isTerisi()) {
                boolean sudahBayar = false;
                for (Pembayaran p : daftarPembayaran) {
                    if (p.getNomorKamar().equalsIgnoreCase(k.getNomorKamar()) && p.getBulan() == bulan && p.getTahun().equals(tahun)) {
                        sudahBayar = true;
                        break;
                    }
                }
                if (!sudahBayar) {
                    tunggakan.add(k);
                }
            }
        }
        return tunggakan;
    }

    public double getPendapatan(Month bulan, Year tahun) {
        double total = 0;
        for (Pembayaran p : daftarPembayaran) {
            if (p.getBulan() == bulan && p.getTahun().equals(tahun)) {
                total += p.getJumlah();
            }
        }
        return total;
    }

    public double getTotalPendapatan() {
        double total = 0;
        for (Pembayaran p : daftarPembayaran) {
            total += p.getJumlah();
        }
        return total;
    }
}
