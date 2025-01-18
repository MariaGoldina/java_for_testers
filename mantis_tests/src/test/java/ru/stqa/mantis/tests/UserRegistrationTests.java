package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.UserData;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class UserRegistrationTests extends TestBase {
    public static Stream<UserData> randomUserProvider() {
        Supplier<UserData> randomUser = () -> new UserData()
                .withUsername(CommonFunctions.randomString(5))
                .withRealname(CommonFunctions.randomString(10));
        return Stream.generate(randomUser).limit(2);

    }

    @ParameterizedTest
    @MethodSource("randomUserProvider")
    public void canRegisterUser(UserData user) {
        // Зарегистрировать новый адрес на почтовом сервере James. (JamesHelper)
        app.jamesCli().addUser(user.email(), user.password());
        //Заполнить регистрацию в Mantis. (browser)
        app.signup().signUp(user);
        Assertions.assertEquals(
                user.username() + " - " + user.email(),
                app.signup().getUserInfoOnSignupPage());
        //Mantis отправляет письмо на указанный адрес.
        // Тест должен получить это письмо. (MailHelper)
        var messages = app.mail().recieve(user.email(), user.password(), Duration.ofSeconds(10));
        // извлечь из письма ссылку для подтверждения.
        var linkUrl = app.mail().getUrlFromMail(messages.get(0));
        // пройти по этой ссылке и завершить регистрацию. (browser)
        app.signup().confirmRegistrationFromLink(linkUrl, user);
        //Затем тест должен проверить, что пользователь может войти в систему с новым паролем. (HttpHelper)
        app.http().login(user.username(), user.password());
        Assertions.assertTrue(app.http().isLoggedIn());
    }

    @ParameterizedTest
    @MethodSource("randomUserProvider")
    public void canRegisterUserFromAdmin(UserData user) {
        app.jamesApi().addUser(user.email(), user.password());

        app.rest().createUser(user);

        var messages = app.mail().recieve(user.email(), user.password(), Duration.ofSeconds(10));

        var linkUrl = app.mail().getUrlFromMail(messages.get(0));

        app.signup().confirmRegistrationFromLink(linkUrl, user);

        app.http().login(user.username(), user.password());
        Assertions.assertTrue(app.http().isLoggedIn());
    }
}
