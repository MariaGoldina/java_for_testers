package ru.stqa.mantis.tests.helpersTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.tests.TestBase;

public class LoginTests extends TestBase {
    @Test
    public void canLoginOnBasePage() {
        app.session().login("administrator", "root");
        Assertions.assertTrue(app.session().isLoggedIn());
    }

    @Test
    public void canLoginFromHttp() {
        app.http().login("administrator", "root");
        Assertions.assertTrue(app.http().isLoggedIn());
    }
}
