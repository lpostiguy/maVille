package Prototype;

import Prototype.Pages.Redirect;
import Prototype.Auth.PasswordEncryption;
import Prototype.User.Resident;

public class Main {

    public static void main(String[] args) {

        Redirect.redirect();


        // This is an example:
//
//        PasswordEncryption passwordEncryption = new PasswordEncryption();
//        int encryptedPassword = passwordEncryption.encrypt("test12345");
//
//        Resident testResident = new Resident("Louis-Philippe", "Ostiguy",
//                "ostilo22@hotmail.com", "514-811-5109", "2008-08-29",
//                "300 " + "49e " + "Avenue, Lachine", encryptedPassword);
//
//        System.out.println("FirstName : " + testResident.getFirstName());
//        System.out.println("lastName : " + testResident.getLastName());
//        System.out.println("email : " + testResident.getEmail());
//        System.out.println("phoneNumber : " + testResident.getPhoneNumber());
//        System.out.println("dateOfBirth : " + testResident.getDateOfBirth());
//        System.out.println("homeAddress : " + testResident.getHomeAddress());
//        System.out.println("password : " + testResident.getPassword());
//        System.out.println("isAbove16 : " + testResident.isAbove16());
    }
}
