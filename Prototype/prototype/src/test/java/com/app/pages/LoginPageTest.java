package com.app.pages;

import com.app.controllers.UserController;
import com.app.models.User.User;
import io.javalin.Javalin;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTest {

    private Javalin app;
    private int port = 8000;

    @BeforeEach
    void setup() {
        app = Javalin.create().start(port);
        UserController.registerRoutes(app);
    }

    @AfterEach
    void teardown() {
        app.stop();
    }

    // Vérifier qu'un utilisateur existant est trouvée par la fonction findUserByEmail.
    @Test
    public void findUserByEmailTest() {
        Document user = UserController.findUserByEmail("anthony@videotron.com");
        assertFalse(user.isEmpty());
    }

    // Vérifier qu'en essayant de se connecter à un compte qui n'a pas été créé,
    // le système retourne "null".
    @Test
    public void unsuccessfulLoginTest() {
        System.setIn(System.in);
        String simulatedInput = "1\n1";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        User user = LoginPage.loginPage();
    }

    // Vérifier que la fonction findUserByCityId réussit à trouver un intervenant
    // avec le cityId fourni.
    @Test
    public void findUserByCityIdTest() {
        Document user = UserController.findUserByCityId("87656475");
        assertFalse(user.isEmpty());
    }

}
