package Prototype.User;

public class Resident extends User {

    public Resident(String firstname, String lastname, String email,
                    int password) {
        setFirstname(firstname);
        setLastname(lastname);
        setEmail(email);
        setPassword(password);
        setRole("Resident");
    }
}
