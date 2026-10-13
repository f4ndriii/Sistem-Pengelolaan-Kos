/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfandri.sistempengelolaankos.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import com.alfandri.sistempengelolaankos.interfaces.InfoDetail;
import java.text.NumberFormat;
import java.time.format.TextStyle;

/**
 *
 * @author USER
 */
public class Pembayaran implements InfoDetail {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final String idPembayaran;
    private final Penghuni penghuni;
    private final Kamar kamar;
    private final String bulanBayar;
    private final LocalDate tanggalBayar;
    private final double jumlahBayar;

    public Pembayaran(String idPembayaran, Kamar kamar,
                      String bulanBayar, double jumlahBayar) {

        if (idPembayaran == null || idPembayaran.isBlank()) {
            throw new IllegalArgumentException(
                    "ID pembayaran tidak boleh kosong");
        }

        if (kamar == null || !kamar.isTerisi()) {
            throw new IllegalArgumentException(
                    "Kamar harus terisi penghuni");
        }

        if (bulanBayar == null || bulanBayar.isBlank()) {
            throw new IllegalArgumentException(
                    "Bulan pembayaran tidak boleh kosong");
        }

        if (jumlahBayar < 0) {
            throw new IllegalArgumentException(
                    "Jumlah pembayaran tidak boleh negatif");
        }

        this.idPembayaran = idPembayaran;
        this.kamar = kamar;
        this.penghuni = kamar.getPenghuni();
        this.bulanBayar = bulanBayar;
        this.tanggalBayar = LocalDate.now();
        this.jumlahBayar = jumlahBayar;
    }

    public double hitungTagihan() {
        return kamar.hitungHargaSewa();
    }

    public String getStatus() {
        return jumlahBayar >= hitungTagihan()
                ? "LUNAS" : "BELUM LUNAS";
    }

    public static String formatRupiah(double nominal) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

        return format.format(nominal);
    }

    @Override
    public String getInfoDetail() {
        return "ID Pembayaran : " + idPembayaran + "\n"
             + "Nama Penghuni : " + penghuni.getNama() + "\n"
             + "Nomor Kamar   : " + kamar.getNomorKamar() + "\n"
             + "Tipe Kamar    : " + kamar.getTipe() + "\n"
             + "Bulan Bayar   : " + bulanBayar + "\n"
             + "Tanggal Bayar : "
             + tanggalBayar.format(FORMAT) + "\n"
             + "Tagihan       : "
             + formatRupiah(hitungTagihan()) + "\n"
             + "Jumlah Bayar  : "
             + formatRupiah(jumlahBayar) + "\n"
             + "Status        : " + getStatus();
    }
}

