package com.app;

import com.app.controllers.*;
import com.app.pages.PageRedirect;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8000);

        RequeteTravailController.registerRoutes(app);
        UserController.registerRoutes(app);
        NotificationsController.registerRoutes(app);
        CandidatureController.registerRoutes(app);
        PreferenceHoraireController.registerRoutes(app);

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

