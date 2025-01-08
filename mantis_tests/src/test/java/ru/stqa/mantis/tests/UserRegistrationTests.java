package ru.stqa.mantis.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.mantis.common.CommonFunctions;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class UserRegistrationTests extends TestBase {
    public static Stream<String> randomUserProvider() {
        Supplier<String> randomUser = () -> CommonFunctions.randomString(5);
        return Stream.generate(randomUser).limit(1);

    }

    @ParameterizedTest
    @MethodSource("randomUserProvider")
    public void canRegisterUser(String username) {
        var email = String.format("%s@localhost", username);
// Зарегистрировать новый адрес на почтовом сервере James. (JamesHelper)
//Заполнить регистрацию в Mantis. (browser)
//Mantis отправляет письмо на указанный адрес.
// Тест должен получить это письмо. (MailHelper)
// извлечь из письма ссылку для подтверждения.
// пройти по этой ссылке и завершить регистрацию. (browser)
//Затем тест должен проверить, что пользователь может войти в систему с новым паролем. (HttpHelper)
    }
}
