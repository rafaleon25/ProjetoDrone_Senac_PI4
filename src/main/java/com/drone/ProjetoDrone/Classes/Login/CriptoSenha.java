/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drone.ProjetoDrone.Classes.Login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Josué
 */
public class CriptoSenha {

    public String cripto(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                     byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
                      
                     StringBuilder hexString = new StringBuilder();
                     for (byte b : messageDigest) {
                       hexString.append(String.format("%02X", 0xFF & b));
                     }
                     String senhahex = hexString.toString();
                     return senhahex;
    }
}
