/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfandri.sistempengelolaankos.model;

import com.alfandri.sistempengelolaankos.interfaces.InfoDetail;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author USER
 */
public class Penghuni implements InfoDetail {
    private static int jumlahPenghuni;
    
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final int id;
    private String nama;
    private String noHp;
    private final LocalDate tanggalMasuk;

    public Penghuni(String nama, String noHp){
        this.id = ++jumlahPenghuni;
        this.nama = nama;
        this.noHp = noHp;
        this.tanggalMasuk = LocalDate.now();
    }

    public int getId(){
        return id;
    }

    public String getNama(){
        return nama;
    }

    public String getNoHp(){
        return noHp;
    }

    public void setNoHp(String noHp){
        this.noHp = (noHp == null || noHp.isBlank()) ? "-" : noHp.trim();
    }

    public LocalDate getTanggalMasuk(){
        return tanggalMasuk;
    }

    @Override 
    public String getInfoDetail(){
        return "ID        : " + id + "\n"
            + "Nama      : " + nama + "\n"
            + "No. Hp    : " + noHp + "\n"
            + "Masuk     : " + tanggalMasuk.format(FORMAT);
    }
}
