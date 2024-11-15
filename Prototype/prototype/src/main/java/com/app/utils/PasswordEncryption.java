package com.app.utils;

public class PasswordEncryption {

    public int encrypt(String password) {
        return password.hashCode();
    }
}
