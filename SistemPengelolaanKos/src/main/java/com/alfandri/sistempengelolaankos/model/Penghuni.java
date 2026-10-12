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
    
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final String id;
    private String nama;
    private String noHp;
    private final LocalDate tanggalMasuk;

    public Penghuni(String id, String nama, String noHp){
        this(id, nama, noHp, LocalDate.now());
    }

    public Penghuni(String id, String nama, String noHp, LocalDate tanggalMasuk){
        if (nama == null || nama.isBlank()){
            throw new IllegalArgumentException("Nama penghuni tidak boleh kosong");
        }
        this.id = id;
        this.nama = nama.trim();
        this.noHp = noHp == null ? "-" : noHp.trim();
        this.tanggalMasuk = tanggalMasuk;
    }

    public String getId(){
        return id;
    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nam){
        if (nama == null || nam.isBlank()){
            throw new IllegalArgumentException("Nama penghuni tidak boleh kosong");
        }
        this.nama = nama.trim();
    }

    public String getNoHp(){
        return noHp;
    }

    public void setNoHp(String noHp){
        this.noHp = noHp;
    }

    public LocalDate getTanggalMasuk(){
        return tanggalMasuk;
    }

    @Override 
    public String getInfoDetail(){
        return "ID      : " + id + "\n"
            + "Nama     : " + nama + "\n"
            + "No. Hp   : " + noHp + "\n"
            +"Masuk     : " + tanggalMasuk.format(FORMAT);
    }
}
