package com.alfandri.sistempengelolaankos;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.alfandri.sistempengelolaankos.model.Kamar;
import com.alfandri.sistempengelolaankos.model.KamarPremium;
import com.alfandri.sistempengelolaankos.model.KamarStandard;
import com.alfandri.sistempengelolaankos.model.KosManager;
import com.alfandri.sistempengelolaankos.model.Pembayaran;
import com.alfandri.sistempengelolaankos.model.Penghuni;

/**
 * Program konsol Sistem Pengelolaan Kos.
 */
public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final KosManager kos = new KosManager();
    private static final Locale LOCALE_ID = Locale.forLanguageTag("id-ID");

    public static void main(String[] args) {
        isiDataAwal();
        while (true) {
            tampilkanMenu();
            String pilihan = baca("Pilih menu");
            if ("0".equals(pilihan)) break;
            try {
                switch (pilihan) {
                    case "1"  -> daftarKamar();
                    case "2"  -> tambahKamar();
                    case "3"  -> hapusKamar();
                    case "4"  -> checkIn();
                    case "5"  -> checkOut();
                    case "6"  -> daftarPenghuni();
                    case "7"  -> catatPembayaran();
                    case "8"  -> riwayatPembayaran();
                    case "9"  -> tunggakan();
                    case "10" -> laporan();
                    default   -> System.out.println("Menu tidak valid.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        System.out.println("Terima kasih. Sampai jumpa!");
    }

    private static void tampilkanMenu() {
        System.out.println("""

            ===== SISTEM PENGELOLAAN KOS =====
             1. Daftar kamar
             2. Tambah kamar
             3. Hapus kamar
             4. Check-in penghuni
             5. Check-out penghuni
             6. Daftar penghuni
             7. Catat pembayaran
             8. Riwayat pembayaran
             9. Tunggakan bulan ini
            10. Laporan pendapatan
             0. Keluar""");
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
        return in.hasNextLine() ? in.nextLine().trim() : "";
    }

    private static double bacaAngka(String label) {
        String s = baca(label).replace(".", "").replace(",", ".");
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Angka tidak valid: " + s);
        }
    }

    private static Month bacaBulan() {
        String s = baca("Bulan (1-12, kosong = bulan ini)");
        try {
            return s.isEmpty() ? LocalDate.now().getMonth() : Month.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Bulan harus 1-12");
        }
    }

    private static Year bacaTahun() {
        String s = baca("Tahun (kosong = tahun ini)");
        try {
            return s.isEmpty() ? Year.now() : Year.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tahun tidak valid");
        }
    }

    private static String namaBulan(Month bulan) {
        return bulan.getDisplayName(TextStyle.FULL, LOCALE_ID);
    }

    // ---------- Fitur ----------
    private static void daftarKamar() {
        List<Kamar> semua = kos.getSemuaKamar();
        if (semua.isEmpty()) {
            System.out.println("Belum ada kamar.");
            return;
        }
        semua.forEach(k -> {
            System.out.println("-----------------------------");
            k.tampilkanInfo();
        });
        System.out.println("-----------------------------");
        System.out.println("Terisi: " + kos.getJumlahTerisi() + " / " + semua.size());
    }

    private static void tambahKamar() {
        String nomor = baca("Nomor kamar");
        String tipe = baca("Tipe (1 = Standard, 2 = Premium)");
        double harga = bacaAngka("Harga dasar per bulan");
        Kamar k = switch (tipe) {
            case "1" -> new KamarStandard(nomor, harga);
            case "2" -> new KamarPremium(nomor, harga, bacaAngka("Biaya fasilitas khusus"));
            default -> throw new IllegalArgumentException("Tipe kamar tidak valid");
        };
        kos.tambahKamar(k);
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
        kosong.forEach(k -> System.out.println(" - " + k.getNomorKamar() + " (" + k.getTipe() + ") "
                + Pembayaran.formatRupiah(k.hitungHargaSewa())));

        String nomor = baca("Nomor kamar");
        String nama = baca("Nama penghuni");
        String hp = baca("No. HP");
        Penghuni p = kos.checkIn(nama, hp, nomor);
        System.out.println("Check-in berhasil:");
        p.tampilkanInfo();
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
        semua.forEach(p -> {
            System.out.println("-----------------------------");
            p.tampilkanInfo();
            System.out.println("Kamar     : " + kos.cariKamarPenghuni(p.getId()).getNomorKamar());
        });
    }

    private static void catatPembayaran() {
        String nomor = baca("Nomor kamar");
        Pembayaran pay = kos.bayar(nomor, bacaBulan(), bacaTahun());
        System.out.println("Pembayaran tercatat:");
        pay.tampilkanInfo();
    }

    private static void riwayatPembayaran() {
        List<Pembayaran> semua = kos.getSemuaPembayaran();
        if (semua.isEmpty()) {
            System.out.println("Belum ada pembayaran.");
            return;
        }
        semua.forEach(pay -> {
            System.out.println("-----------------------------");
            pay.tampilkanInfo();
        });
    }

    private static void tunggakan() {
        LocalDate now = LocalDate.now();
        List<Kamar> belum = kos.getTunggakan(now.getMonth(), Year.of(now.getYear()));
        if (belum.isEmpty()) {
            System.out.println("Semua penghuni sudah membayar bulan ini.");
            return;
        }
        System.out.println("Belum membayar (" + namaBulan(now.getMonth()) + " " + now.getYear() + "):");
        belum.forEach(k -> System.out.println(" - Kamar " + k.getNomorKamar() + " | "
                + k.getPenghuni().getNama() + " | " + Pembayaran.formatRupiah(k.hitungHargaSewa())));
    }

    private static void laporan() {
        Month bulan = bacaBulan();
        Year tahun = bacaTahun();
        System.out.println("Pendapatan " + namaBulan(bulan) + " " + tahun + " : "
                + Pembayaran.formatRupiah(kos.getPendapatan(bulan, tahun)));
        System.out.println("Total seluruh pendapatan   : " + Pembayaran.formatRupiah(kos.getTotalPendapatan()));
        System.out.println("Kamar terisi               : " + kos.getJumlahTerisi() + " / " + kos.getSemuaKamar().size());
    }
}
