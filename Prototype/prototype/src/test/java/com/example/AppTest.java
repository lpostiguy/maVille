package com.example;

import com.app.MongoDBConnectionTest;
import com.app.controllers.SoumettreRequeteTravailControllerTest;
import com.app.pages.LoginPageTest;
import com.app.utils.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({MongoDBConnectionTest.class})
public class AppTest {

    @Test
    public void runAllTests() {

        SoumettreRequeteTravailControllerTest soumettreRequeteTest = new SoumettreRequeteTravailControllerTest();
        soumettreRequeteTest.soumettreRequeteTravailTest();
        soumettreRequeteTest.soumettreRequeteTravailMissingFieldTest();
        soumettreRequeteTest.soumettreRequeteTravailServerError();

        MongoDBConnectionTest mongoTestClass = new MongoDBConnectionTest();
        mongoTestClass.connexionDoitRetournerUneBaseDeDonneesNonNulle();
        mongoTestClass.connexionDoitPointerVersLaBonneBaseDeDonnees();
        mongoTestClass.connexionDoitEtreResilienteAuxErreurs();

        RegexCheckerTest regexCheckerTestClass = new RegexCheckerTest();
        regexCheckerTestClass.testIsValidDateFormat();
        regexCheckerTestClass.testIsValidPhoneNumberFormat();
        regexCheckerTestClass.testIsValidHourFormat();


        InscriptionUtilsTest inscriptionUtilsTestClass =
            new InscriptionUtilsTest();
        inscriptionUtilsTestClass.isAbove16Test();
        inscriptionUtilsTestClass.isValidPasswordTest();
        inscriptionUtilsTestClass.isSamePasswordTest();

        LoginPageTest loginPageTest = new LoginPageTest();
        loginPageTest.findUserByCityIdTest();
        loginPageTest.findUserByEmailTest();
        loginPageTest.unsuccessfulLoginTest();

        System.out.println("All tests completed");
    }
}
