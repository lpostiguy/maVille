package Prototype.User;

public class Intervenant extends User {

    public Intervenant(String firstname, String lastname, String email,
                       int password) {
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setPassword(password);
        setRole("Intervenant");
    }
}
