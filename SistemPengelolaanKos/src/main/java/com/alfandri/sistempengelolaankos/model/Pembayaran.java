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

/**
 *
 * @author USER
 */
public class Pembayaran implements InfoDetail{

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String idPembayaran;
    private Penghuni penghuni;
    private String nomorKamar;
    private final Month bulan;
    private final Year tahun;
    private final double jumlah;
    private final LocalDate tanggalBayar;

    public Pembayaran(String idPembayaran, Penghuni penghuni, String nomorKamar, Month bulan, Year tahun, double jumlah){
        if(jumlah <= 0){
            throw new IllegalArgumentException("Jumlah pembayaran harus lebih dari nol (0)");
        }
        if(penghuni == null || nomorKamar == null || bulan == null || tahun == null){
            throw new IllegalArgumentException("Data pembayaran tidak lengkap");
        }
        this.idPembayaran = idPembayaran;
        this.penghuni = penghuni;
        this.nomorKamar = nomorKamar;
        this.bulan = bulan;
        this.tahun = tahun;
        this.jumlah = jumlah;
        this.tanggalBayar = LocalDate.now();
    }

    public String getIdPembayaran(){
        return idPembayaran;
    }

    public Penghuni getPenghuni(){
        return penghuni;
    }

    public String getNomorKamar(){
        return nomorKamar;
    }

    public Month getBulan(){
        return bulan;
    }

    public Year getTahun(){
        return tahun;
    }

    public double getJumlah(){
        return jumlah;
    }

    public LocalDate getTanggalBayar(){
        return tanggalBayar;
    }

    public static String formatRupiah(double nilai) {
    return "Rp " + String.format(java.util.Locale.forLanguageTag("id-ID"), "%,.0f", nilai
    );
}

    @Override 
    public String getInfoDetail(){
        return "ID Bayar : " + idPembayaran + "\n"
             + "Penghuni : " + penghuni.getNama() + "\n"
             + "Kamar    : " + nomorKamar + "\n"
             + "Periode  : " + bulan.getDisplayName(java.time.format.TextStyle.FULL, Locale.forLanguageTag("id-ID"))
                              + " " + tahun + "\n"
             + "Jumlah   : " + formatRupiah(jumlah) + "\n"
             + "Tgl Bayar: " + tanggalBayar.format(FORMAT);
    }
}
