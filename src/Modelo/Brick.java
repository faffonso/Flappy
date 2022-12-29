/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author franc
 */
public class Brick extends Personagem implements Serializable {
        
    public Brick (String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false;
    }
}
