package Prototype.Auth;

public class PasswordEncryption {

    public int encrypt(String password) {
        return password.hashCode();
    }
}
