package ru.stqa.mantis.tests;
// Для запуска почтового сервера
// java -Dworking.directory=. -jar james-server-jpa-app.jar
// Для проверки пользователей
// "java -cp "james-server-jpa-app.lib/*" org.apache.james.cli.ServerCmd ListUsers"

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

public class JamesTests extends TestBase {
    @Test
    public void canCreateUser() {
        app.jamesCli().addUser(
                String.format("%s@localhost", CommonFunctions.randomString(8)),
                "password");
    }
}
