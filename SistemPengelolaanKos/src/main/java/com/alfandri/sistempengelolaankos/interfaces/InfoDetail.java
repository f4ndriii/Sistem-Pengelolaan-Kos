/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.alfandri.sistempengelolaankos.interfaces;

/**
 *
 * @author USER
 */
public interface InfoDetail {
    String getInfoDetail();

    default void tampilkanInfo() {
        System.out.println(getInfoDetail());
    }

    default void tampilkanINfo(){
        tampilkanInfo();
    }
}
