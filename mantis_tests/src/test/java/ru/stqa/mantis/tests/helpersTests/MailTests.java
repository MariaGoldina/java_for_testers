package ru.stqa.mantis.tests.helpersTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.tests.TestBase;

import java.time.Duration;
import java.util.regex.Pattern;

public class MailTests extends TestBase {
    @Test
    public void canDrainInbox() {
        app.mail().drain("user1@localhost", "password");

    }

    @Test
    public void canRecieveEmail() {
        var messages = app.mail().recieve("user1@localhost", "password", Duration.ofSeconds(10));
        Assertions.assertEquals(1, messages.size());
        System.out.println(messages);
    }

    @Test
    public void canExtractUrl() {
        var messages = app.mail().recieve("user1@localhost", "password", Duration.ofSeconds(10));
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            var url = text.substring(matcher.start(), matcher.end());
            System.out.println(url);
        }
    }
}
