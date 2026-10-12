package com.alfandri.sistempengelolaankos.model;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Pengelola utama: menyimpan kamar, penghuni, dan riwayat pembayaran.
 */
public class KosManager {

    private final Map<String, Kamar> daftarKamar = new LinkedHashMap<>();
    private final Map<String, Penghuni> daftarPenghuni = new LinkedHashMap<>();
    private final List<Pembayaran> daftarPembayaran = new ArrayList<>();
    private int counterPenghuni = 0;
    private int counterPembayaran = 0;

    // ---------- Kamar ----------
    public void tambahKamar(Kamar kamar) {
        if (kamar == null) {
            throw new IllegalArgumentException("Kamar tidak boleh null");
        }
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
            }
        }
        return total;
    }

    public double getTotalPendapatan() {
        double total = 0;
        for (Pembayaran pay : daftarPembayaran) {
            total += pay.getJumlah();
        }
        return total;
    }

    public int getJumlahTerisi() {
        return daftarKamar.size() - getKamarKosong().size();
    }
}
