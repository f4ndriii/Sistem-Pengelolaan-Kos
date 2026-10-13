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

    private static final Scanner input = new Scanner(System.in);
    private static final KosManager manager = new KosManager();

    public static void main(String[] args) {

        int pilihan;

        do {
            System.out.println("\n================================");
            System.out.println(" SISTEM PENGELOLAAN KOS");
            System.out.println("================================");
            System.out.println("1. Tambah Kamar");
            System.out.println("2. Tampilkan Daftar Kamar");
            System.out.println("3. Daftarkan Penghuni");
            System.out.println("4. Tempatkan Penghuni");
            System.out.println("5. Pembayaran Sewa");
            System.out.println("6. Riwayat Pembayaran");
            System.out.println("7. Daftar Penghuni");
            System.out.println("0. Keluar");
            System.out.println("================================");

            pilihan = bacaInt("Pilih menu: ");

            try {
                switch (pilihan) {
                    case 1:
                        tambahKamar();
                        break;
                    case 2:
                        tampilkanKamar();
                        break;
                    case 3:
                        daftarkanPenghuni();
                        break;
                    case 4:
                        tempatkanPenghuni();
                        break;
                    case 5:
                        pembayaranSewa();
                        break;
                    case 6:
                        riwayatPembayaran();
                        break;
                    case 7:
                        daftarPenghuni();
                        break;
                    case 0:
                        System.out.println("Terima kasih!");
                        break;
                    default:
                        System.out.println("Pilihan tidak tersedia.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal: " + e.getMessage());
            }

        } while (pilihan != 0);

        input.close();
    }

    private static void tambahKamar() {
        System.out.println("\n--- TAMBAH KAMAR ---");
        System.out.println("1. Standard");
        System.out.println("2. Premium");

        int tipe = bacaInt("Pilih tipe kamar: ");
        Kamar kamar;

        if (tipe == 1) {
            kamar = new KamarStandard();
        } else if (tipe == 2) {
            kamar = new KamarPremium();
        } else {
            System.out.println("Tipe kamar tidak tersedia.");
            return;
        }

        manager.tambahKamar(kamar);

        System.out.println("Kamar berhasil ditambahkan!");
        System.out.println("Nomor kamar: " + kamar.getNomorKamar());
        System.out.println("Tipe kamar : " + kamar.getTipe());
        System.out.println("Harga sewa : "
                + Pembayaran.formatRupiah(kamar.hitungHargaSewa()));
    }

    private static void tampilkanKamar() {
        System.out.println("\n--- DAFTAR KAMAR ---");

        if (manager.getDaftarKamar().isEmpty()) {
            System.out.println("Belum ada kamar.");
            return;
        }

        for (Kamar kamar : manager.getDaftarKamar()) {
            System.out.println("----------------------------");
            System.out.println(kamar.getInfoDetail());
        }
    }

    private static void daftarkanPenghuni() {
        System.out.println("\n--- PENDAFTARAN PENGHUNI ---");

        System.out.print("Nama        : ");
        String nama = input.nextLine().trim();

        System.out.print("Nomor HP    : ");
        String noHp = input.nextLine().trim();

        Penghuni penghuni = new Penghuni( nama, noHp);
        manager.tambahPenghuni(penghuni);

        System.out.println("Penghuni berhasil didaftarkan!");
    }

    private static void tempatkanPenghuni() {
        System.out.println("\n--- TEMPATKAN PENGHUNI ---");

        if (manager.getDaftarPenghuni().isEmpty()) {
            System.out.println("Daftarkan penghuni terlebih dahulu.");
            return;
        }

        tampilkanKamar();

        System.out.print("\nMasukkan ID penghuni: ");
        int id = input.nextInt();

        Penghuni penghuni = null;

        for (Penghuni p : manager.getDaftarPenghuni()) {
            if (p.getId() == id) {
                penghuni = p;
                break;
            }
        }

        if (penghuni == null) {
            System.out.println("Penghuni tidak ditemukan.");
            return;
        }

        int nomor = bacaInt("Nomor kamar: ");

        manager.tempatkanPenghuni(penghuni, nomor);

        System.out.println("Penghuni berhasil ditempatkan.");
    }

    private static void pembayaranSewa() {
        System.out.println("\n--- PEMBAYARAN SEWA ---");

        if (manager.getDaftarKamar().isEmpty()) {
            System.out.println("Belum ada kamar.");
            return;
        }

        tampilkanKamar();

        int nomor = bacaInt("\nNomor kamar: ");

        Kamar kamar = manager.cariKamar(nomor);

        if (kamar == null) {
            System.out.println("Kamar tidak ditemukan.");
            return;
        }

        if (!kamar.isTerisi()) {
            System.out.println("Kamar belum ditempati penghuni.");
            return;
        }

        System.out.println("Penghuni: " + kamar.getPenghuni().getNama());
        System.out.println("Tagihan : "
                + Pembayaran.formatRupiah(kamar.hitungHargaSewa()));

        System.out.print("Bulan pembayaran: ");
        String bulan = input.nextLine().trim();

        double jumlah = bacaDouble("Jumlah pembayaran (Rp): ");

        String id = "BYR" + (manager.getRiwayatPembayaran().size() + 1);

        Pembayaran pembayaran = manager.prosesPembayaran(
                id, nomor, bulan, jumlah
        );

        System.out.println("\nTransaksi berhasil dicatat!");
        System.out.println(pembayaran.getInfoDetail());
    }

    private static void riwayatPembayaran() {
        System.out.println("\n--- RIWAYAT PEMBAYARAN ---");

        if (manager.getRiwayatPembayaran().isEmpty()) {
            System.out.println("Belum ada riwayat pembayaran.");
            return;
        }

        for (Pembayaran pembayaran : manager.getRiwayatPembayaran()) {
            System.out.println("----------------------------");
            System.out.println(pembayaran.getInfoDetail());
        }
    }

    private static void daftarPenghuni() {
        System.out.println("\n--- DAFTAR PENGHUNI ---");

        if (manager.getDaftarPenghuni().isEmpty()) {
            System.out.println("Belum ada penghuni.");
            return;
        }

        for (Penghuni penghuni : manager.getDaftarPenghuni()) {
            System.out.println("----------------------------");
            System.out.println(penghuni.getInfoDetail());
        }
    }

    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                int nilai = Integer.parseInt(input.nextLine().trim());
                return nilai;
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    private static double bacaDouble(String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                double nilai = Double.parseDouble(
                        input.nextLine().trim()
                );

                if (!Double.isFinite(nilai) || nilai < 0) {
                    System.out.println(
                            "Nominal harus berupa angka nonnegatif.");
                    continue;
                }

                return nilai;
            } catch (NumberFormatException e) {
                System.out.println("Masukkan nominal yang valid.");
            }
        }
    }
}

