package ru.stqa.mantis.tests.helpersTests;
// Для запуска почтового сервера
// java -Dworking.directory=. -jar james-server-jpa-app.jar
// Для проверки пользователей
// java -cp "james-server-jpa-app.lib/*" org.apache.james.cli.ServerCmd ListUsers

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.tests.TestBase;

public class JamesTests extends TestBase {
    @Test
    public void canCreateRandomUser() {
        app.jamesCli().addUser(
                String.format("%s@localhost", CommonFunctions.randomString(8)),
                "password");
    }

    @Test
    public void canCreateSomeUser() {
        String user = "user2";
        app.jamesCli().addUser(
                String.format("%s@localhost", user),
                "password");
    }
}
