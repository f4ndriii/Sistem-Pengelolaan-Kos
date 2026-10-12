package com.alfandri.sistempengelolaankos.model;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
=======
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
>>>>>>> 58e73a0883fb0b6afbd3f4c6ec69b467f04c1962

/**
 * Pengelola utama: menyimpan kamar, penghuni, dan riwayat pembayaran.
 */
public class KosManager {

<<<<<<< HEAD
    private final List<Kamar> daftarKamar = new ArrayList<>();
    private final List<Penghuni> daftarPenghuni = new ArrayList<>();
    private final List<Pembayaran> daftarPembayaran = new ArrayList<>();
    private int idCounter = 1;

=======
    private final Map<String, Kamar> daftarKamar = new LinkedHashMap<>();
    private final Map<String, Penghuni> daftarPenghuni = new LinkedHashMap<>();
    private final List<Pembayaran> daftarPembayaran = new ArrayList<>();
    private int counterPenghuni = 0;
    private int counterPembayaran = 0;

    // ---------- Kamar ----------
>>>>>>> 58e73a0883fb0b6afbd3f4c6ec69b467f04c1962
    public void tambahKamar(Kamar kamar) {
        if (kamar == null) {
            throw new IllegalArgumentException("Kamar tidak boleh null");
        }
<<<<<<< HEAD
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
=======
        if (daftarKamar.containsKey(kamar.getNomorKamar())) {
            throw new IllegalArgumentException("Kamar " + kamar.getNomorKamar() + " sudah ada");
        }
        daftarKamar.put(kamar.getNomorKamar(), kamar);
    }

    public Kamar cariKamar(String nomor) {
        Kamar k = nomor == null ? null : daftarKamar.get(nomor.trim());
        if (k == null) {
            throw new IllegalArgumentException("Kamar " + nomor + " tidak ditemukan");
        }
        return k;
    }

    public void hapusKamar(String nomor) {
        Kamar k = cariKamar(nomor);
        if (k.isTerisi()) {
            throw new IllegalArgumentException("Kamar " + nomor + " masih terisi, tidak bisa dihapus");
        }
        daftarKamar.remove(k.getNomorKamar());
    }

    public List<Kamar> getSemuaKamar() {
        return Collections.unmodifiableList(new ArrayList<>(daftarKamar.values()));
    }

    public List<Kamar> getKamarKosong() {
        List<Kamar> hasil = new ArrayList<>();
        for (Kamar k : daftarKamar.values()) {
            if (!k.isTerisi()) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    // ---------- Penghuni ----------
    /** Mendaftarkan penghuni baru ke kamar kosong. */
    public Penghuni checkIn(String nama, String noHp, String nomorKamar) {
        Kamar kamar = cariKamar(nomorKamar);
        if (kamar.isTerisi()) {
            throw new IllegalArgumentException("Kamar " + nomorKamar + " sudah terisi");
        }
        String id = String.format("P%03d", ++counterPenghuni);
        Penghuni p;
        try {
            p = new Penghuni(id, nama, noHp);
        } catch (IllegalArgumentException e) {
            counterPenghuni--;
            throw e;
        }
        kamar.tempati(p);
        daftarPenghuni.put(id, p);
        return p;
    }

    /** Mengeluarkan penghuni dari kamarnya. */
    public Penghuni checkOut(String nomorKamar) {
        Penghuni p = cariKamar(nomorKamar).kosongkan();
        daftarPenghuni.remove(p.getId());
        return p;
    }

    public List<Penghuni> getSemuaPenghuni() {
        return Collections.unmodifiableList(new ArrayList<>(daftarPenghuni.values()));
    }

    public Kamar cariKamarPenghuni(String idPenghuni) {
        for (Kamar k : daftarKamar.values()) {
            if (k.isTerisi() && k.getPenghuni().getId().equalsIgnoreCase(idPenghuni)) {
                return k;
            }
        }
        throw new IllegalArgumentException("Penghuni " + idPenghuni + " tidak ditemukan");
    }

    // ---------- Pembayaran ----------
    /** Mencatat pembayaran sewa penuh untuk kamar yang sedang terisi. */
    public Pembayaran bayar(String nomorKamar, Month bulan, Year tahun) {
        Kamar kamar = cariKamar(nomorKamar);
        if (!kamar.isTerisi()) {
            throw new IllegalArgumentException("Kamar " + nomorKamar + " tidak berpenghuni");
        }
        Penghuni p = kamar.getPenghuni();
        if (sudahBayar(p.getId(), bulan, tahun)) {
            throw new IllegalArgumentException(p.getNama() + " sudah membayar untuk periode tersebut");
        }
        String id = String.format("B%04d", ++counterPembayaran);
        Pembayaran pay = new Pembayaran(id, p, kamar.getNomorKamar(), bulan, tahun, kamar.hitungHargaSewa());
        daftarPembayaran.add(pay);
        return pay;
    }

    public boolean sudahBayar(String idPenghuni, Month bulan, Year tahun) {
        for (Pembayaran pay : daftarPembayaran) {
            if (pay.getPenghuni().getId().equals(idPenghuni)
                    && pay.getBulan() == bulan && pay.getTahun().equals(tahun)) {
                return true;
            }
        }
        return false;
    }

    public List<Pembayaran> getSemuaPembayaran() {
        return Collections.unmodifiableList(daftarPembayaran);
    }

    /** Daftar kamar terisi yang belum membayar pada periode tertentu. */
    public List<Kamar> getTunggakan(Month bulan, Year tahun) {
        List<Kamar> hasil = new ArrayList<>();
        for (Kamar k : daftarKamar.values()) {
            if (k.isTerisi() && !sudahBayar(k.getPenghuni().getId(), bulan, tahun)) {
                hasil.add(k);
            }
        }
        return hasil;
    }

    // ---------- Laporan ----------
    public double getPendapatan(Month bulan, Year tahun) {
        double total = 0;
        for (Pembayaran pay : daftarPembayaran) {
            if (pay.getBulan() == bulan && pay.getTahun().equals(tahun)) {
                total += pay.getJumlah();
>>>>>>> 58e73a0883fb0b6afbd3f4c6ec69b467f04c1962
            }
        }
        return total;
    }

    public double getTotalPendapatan() {
        double total = 0;
<<<<<<< HEAD
        for (Pembayaran p : daftarPembayaran) {
            total += p.getJumlah();
        }
        return total;
    }
=======
        for (Pembayaran pay : daftarPembayaran) {
            total += pay.getJumlah();
        }
        return total;
    }

    public int getJumlahTerisi() {
        return daftarKamar.size() - getKamarKosong().size();
    }
>>>>>>> 58e73a0883fb0b6afbd3f4c6ec69b467f04c1962
}
