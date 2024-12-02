package com.app;

import com.app.controllers.RequeteTravailController;
import com.app.controllers.UserController;
import com.app.pages.PageRedirect;
import io.javalin.Javalin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8000);

        RequeteTravailController.registerRoutes(app);
        UserController.registerRoutes(app);

        System.out.println("""
             __       __  ______       __     __ ______ __       __       ________\s
            |  \\     /  \\/      \\     |  \\   |  \\      \\  \\     |  \\     |        \\
            | ▓▓\\   /  ▓▓  ▓▓▓▓▓▓\\    | ▓▓   | ▓▓\\▓▓▓▓▓▓ ▓▓     | ▓▓     | ▓▓▓▓▓▓▓▓
            | ▓▓▓\\ /  ▓▓▓ ▓▓__| ▓▓    | ▓▓   | ▓▓ | ▓▓ | ▓▓     | ▓▓     | ▓▓__   \s
            | ▓▓▓▓\\  ▓▓▓▓ ▓▓    ▓▓     \\▓▓\\ /  ▓▓ | ▓▓ | ▓▓     | ▓▓     | ▓▓  \\  \s
            | ▓▓\\▓▓ ▓▓ ▓▓ ▓▓▓▓▓▓▓▓      \\▓▓\\  ▓▓  | ▓▓ | ▓▓     | ▓▓     | ▓▓▓▓▓  \s
            | ▓▓ \\▓▓▓| ▓▓ ▓▓  | ▓▓       \\▓▓ ▓▓  _| ▓▓_| ▓▓_____| ▓▓_____| ▓▓_____\s
            | ▓▓  \\▓ | ▓▓ ▓▓  | ▓▓        \\▓▓▓  |   ▓▓ \\ ▓▓     \\ ▓▓     \\ ▓▓     \\
             \\▓▓      \\▓▓\\▓▓   \\▓▓         \\▓    \\▓▓▓▓▓▓\\▓▓▓▓▓▓▓▓\\▓▓▓▓▓▓▓▓\\▓▓▓▓▓▓▓▓
                                                                                  \s
                                                                                  \s
                                                                                  \s""");
        PageRedirect.pageRedirect();
    }
}

