/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alfandri.sistempengelolaankos.model;

import com.alfandri.sistempengelolaankos.interfaces.InfoDetail;

/**
 *
 * @author USER
 */
public abstract class Kamar implements InfoDetail {

    private final String nomorKamar;
    private final double hargaDasar;
    private Penghuni penghuni;

    protected Kamar(String nomorKamar, double hargaDasar){
        if(nomorKamar == null || nomorKamar.isBlank()){
            throw new IllegalArgumentException("Nomor kamar tidak boleh kosong");
        }
        if(hargaDasar <= 0){
            throw new IllegalArgumentException("Harga dasar harus lebih dari nol (0)");
        }
        this.nomorKamar = nomorKamar;
        this.hargaDasar = hargaDasar;
    }

    public abstract String getTipe();

    public abstract double hitungHargaSewa();

    public abstract String getFasilitas();

    public String getNomorKamar(){
        return nomorKamar;
    }

    public double getHargaDasar(){
        return hargaDasar;
    }

    public Penghuni getPenghuni(){
        return penghuni;
    }

    public boolean isTerisi(){
        return penghuni != null;
    }

    public void tempati(Penghuni p){
        if(isTerisi()){
            throw new IllegalArgumentException("Kamar " + nomorKamar + " sudah terisi");
        }
        if(p == null){
            throw new IllegalArgumentException("Penghuni tidak boleh bernilai null");
        }
        this.penghuni = p;
    }

    public Penghuni kosongkan(){
        if(!isTerisi()){
            throw new IllegalArgumentException("Kamar " + nomorKamar + " sudah kosong");
        }
        Penghuni lama = this.penghuni;
        this.penghuni = null;
        return lama;
    }

    @Override
    public String getInfoDetail() {
        return "Kamar       : " + nomorKamar + " (" + getTipe() + ")\n"
             + "Harga       : " + Pembayaran.formatRupiah(hitungHargaSewa()) + " / bulan\n"
             + "Fasilitas   : " + getFasilitas() + "\n"
             + "Status      : " + (isTerisi() ? "Terisi oleh " + penghuni.getNama() : "Kosong");
    }    
}
