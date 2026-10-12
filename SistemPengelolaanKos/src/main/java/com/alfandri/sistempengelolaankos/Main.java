package com.alfandri.sistempengelolaankos;

import com.alfandri.sistempengelolaankos.model.Kamar;
import com.alfandri.sistempengelolaankos.model.KamarPremium;
import com.alfandri.sistempengelolaankos.model.KamarStandard;
import com.alfandri.sistempengelolaankos.model.KosManager;
import com.alfandri.sistempengelolaankos.model.Pembayaran;
import com.alfandri.sistempengelolaankos.model.Penghuni;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Scanner;

/**
 * Program konsol Sistem Pengelolaan Kos.
 */
public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final KosManager kos = new KosManager();

    public static void main(String[] args) {
        isiDataAwal();
        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            String pilihan = baca("Pilih menu");
            try {
                switch (pilihan) {
                    case "1" -> daftarKamar();
                    case "2" -> tambahKamar();
                    case "3" -> hapusKamar();
                    case "4" -> checkIn();
                    case "5" -> checkOut();
                    case "6" -> daftarPenghuni();
                    case "7" -> catatPembayaran();
                    case "8" -> riwayatPembayaran();
                    case "9" -> tunggakan();
                    case "10" -> laporan();
                    case "0" -> jalan = false;
                    default -> System.out.println("Menu tidak valid.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        System.out.println("Terima kasih. Sampai jumpa!");
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== SISTEM PENGELOLAAN KOS =====");
        System.out.println(" 1. Daftar kamar");
        System.out.println(" 2. Tambah kamar");
        System.out.println(" 3. Hapus kamar");
        System.out.println(" 4. Check-in penghuni");
        System.out.println(" 5. Check-out penghuni");
        System.out.println(" 6. Daftar penghuni");
        System.out.println(" 7. Catat pembayaran");
        System.out.println(" 8. Riwayat pembayaran");
        System.out.println(" 9. Tunggakan bulan ini");
        System.out.println("10. Laporan pendapatan");
        System.out.println(" 0. Keluar");
    }

    private static void isiDataAwal() {
        kos.tambahKamar(new KamarStandard("101", 800_000));
        kos.tambahKamar(new KamarStandard("102", 800_000));
        kos.tambahKamar(new KamarPremium("201", 1_200_000, 400_000));
        kos.tambahKamar(new KamarPremium("202", 1_200_000, 500_000));
    }

    // ---------- Input helper ----------
    private static String baca(String label) {
        System.out.print(label + ": ");
        return in.hasNextLine() ? in.nextLine().trim() : "0";
    }

    private static double bacaAngka(String label) {
        String s = baca(label).replace(".", "").replace(",", ".");
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Angka tidak valid: " + s);
        }
    }

    private static Month bacaBulan(String label, Month bawaan) {
        String s = baca(label + " (1-12, kosong = bulan ini)");
        if (s.isEmpty()) {
            return bawaan;
        }
        try {
            return Month.of(Integer.parseInt(s));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Bulan harus 1-12");
        }
    }

    private static Year bacaTahun(String label, Year bawaan) {
        String s = baca(label + " (kosong = tahun ini)");
        if (s.isEmpty()) {
            return bawaan;
        }
        try {
            return Year.of(Integer.parseInt(s));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Tahun tidak valid");
        }
    }

    private static String namaBulan(Month bulan) {
        return bulan.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.forLanguageTag("id-ID"));
    }

    // ---------- Fitur ----------
    private static void daftarKamar() {
        List<Kamar> semua = kos.getSemuaKamar();
        if (semua.isEmpty()) {
            System.out.println("Belum ada kamar.");
            return;
        }
        for (Kamar k : semua) {
            System.out.println("-----------------------------");
            k.tampilkanINfo();
        }
        System.out.println("-----------------------------");
        System.out.println("Terisi: " + kos.getJumlahTerisi() + " / " + semua.size());
    }

    private static void tambahKamar() {
        String nomor = baca("Nomor kamar");
        String tipe = baca("Tipe (1 = Standard, 2 = Premium)");
        double harga = bacaAngka("Harga dasar per bulan");
        if (tipe.equals("1")) {
            kos.tambahKamar(new KamarStandard(nomor, harga));
        } else if (tipe.equals("2")) {
            double fasilitas = bacaAngka("Biaya fasilitas khusus");
            kos.tambahKamar(new KamarPremium(nomor, harga, fasilitas));
        } else {
            throw new IllegalArgumentException("Tipe kamar tidak valid");
        }
        System.out.println("Kamar " + nomor + " berhasil ditambahkan.");
    }

    private static void hapusKamar() {
        String nomor = baca("Nomor kamar");
        kos.hapusKamar(nomor);
        System.out.println("Kamar " + nomor + " dihapus.");
    }

    private static void checkIn() {
        List<Kamar> kosong = kos.getKamarKosong();
        if (kosong.isEmpty()) {
            System.out.println("Tidak ada kamar kosong.");
            return;
        }
        System.out.println("Kamar kosong:");
        for (Kamar k : kosong) {
            System.out.println(" - " + k.getNomorKamar() + " (" + k.getTipe() + ") "
                    + Pembayaran.formatRupiah(k.hitungHargaSewa()));
        }
        String nomor = baca("Nomor kamar");
        String nama = baca("Nama penghuni");
        String hp = baca("No. HP");
        Penghuni p = kos.checkIn(nama, hp, nomor);
        System.out.println("Check-in berhasil:");
        p.tampilkanINfo();
    }

    private static void checkOut() {
        String nomor = baca("Nomor kamar");
        Penghuni p = kos.checkOut(nomor);
        System.out.println(p.getNama() + " telah check-out dari kamar " + nomor + ".");
    }

    private static void daftarPenghuni() {
        List<Penghuni> semua = kos.getSemuaPenghuni();
        if (semua.isEmpty()) {
            System.out.println("Belum ada penghuni.");
            return;
        }
        for (Penghuni p : semua) {
            System.out.println("-----------------------------");
            p.tampilkanINfo();
            System.out.println("Kamar     : " + kos.cariKamarPenghuni(p.getId()).getNomorKamar());
        }
    }

    private static void catatPembayaran() {
        LocalDate now = LocalDate.now();
        String nomor = baca("Nomor kamar");
        Month bulan = bacaBulan("Bulan", now.getMonth());
        Year tahun = bacaTahun("Tahun", Year.of(now.getYear()));
        Pembayaran pay = kos.bayar(nomor, bulan, tahun);
        System.out.println("Pembayaran tercatat:");
        pay.tampilkanINfo();
    }

    private static void riwayatPembayaran() {
        List<Pembayaran> semua = kos.getSemuaPembayaran();
        if (semua.isEmpty()) {
            System.out.println("Belum ada pembayaran.");
            return;
        }
        for (Pembayaran pay : semua) {
            System.out.println("-----------------------------");
            pay.tampilkanINfo();
        }
    }

    private static void tunggakan() {
        LocalDate now = LocalDate.now();
        Month bulan = now.getMonth();
        Year tahun = Year.of(now.getYear());
        List<Kamar> belum = kos.getTunggakan(bulan, tahun);
        if (belum.isEmpty()) {
            System.out.println("Semua penghuni sudah membayar bulan ini.");
            return;
        }
        System.out.println("Belum membayar (" + namaBulan(bulan) + " " + tahun + "):");
        for (Kamar k : belum) {
            System.out.println(" - Kamar " + k.getNomorKamar() + " | " + k.getPenghuni().getNama()
                    + " | " + Pembayaran.formatRupiah(k.hitungHargaSewa()));
        }
    }

    private static void laporan() {
        LocalDate now = LocalDate.now();
        Month bulan = bacaBulan("Bulan", now.getMonth());
        Year tahun = bacaTahun("Tahun", Year.of(now.getYear()));
        System.out.println("Pendapatan " + namaBulan(bulan) + " " + tahun + " : "
                + Pembayaran.formatRupiah(kos.getPendapatan(bulan, tahun)));
        System.out.println("Total seluruh pendapatan   : " + Pembayaran.formatRupiah(kos.getTotalPendapatan()));
        System.out.println("Kamar terisi               : " + kos.getJumlahTerisi() + " / " + kos.getSemuaKamar().size());
    }
}
