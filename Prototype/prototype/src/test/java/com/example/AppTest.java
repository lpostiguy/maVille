package com.example;

import com.app.MongoDBConnectionTest;
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
        MongoDBConnectionTest mongoTestClass = new MongoDBConnectionTest();
        mongoTestClass.connexionDoitRetournerUneBaseDeDonneesNonNulle();
        mongoTestClass.connexionDoitPointerVersLaBonneBaseDeDonnees();
        mongoTestClass.connexionDoitEtreResilienteAuxErreurs();

        RegexCheckerTest regexCheckerTestClass = new RegexCheckerTest();
        regexCheckerTestClass.testIsValidDateFormat();
        regexCheckerTestClass.testIsValidPhoneNumberFormat();

        PasswordEncryptionTest passwordEncryptionTestClass =
            new PasswordEncryptionTest();
        passwordEncryptionTestClass.encryptPasswordTest();
        passwordEncryptionTestClass.encryptDifferentPasswordsTest();

        JsonFormattingTest jsonFormattingTestClass = new JsonFormattingTest();
        jsonFormattingTestClass.jsonToTextFormatterJsonEnTexteSansId();

        InscriptionUtilsTest inscriptionUtilsTestClass =
            new InscriptionUtilsTest();
        inscriptionUtilsTestClass.isAbove16Test();
        inscriptionUtilsTestClass.isValidPasswordTest();
        inscriptionUtilsTestClass.isSamePasswordTest();

        AgeFinderTest ageFinderTestClass = new AgeFinderTest();
        ageFinderTestClass.ageFinderTest();

        LoginPageTest loginPageTestClass = new LoginPageTest();
        loginPageTestClass.loginIntervenantTest();

        System.out.println("All tests completed");
    }
}
