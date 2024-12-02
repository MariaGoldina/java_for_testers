package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        if (!app.groups().isGroupPresent(By.name("selected[]"))) {
            app.groups().createGroup(new GroupData("new group", "group header", "group footer"));
        }
        app.groups().selectGroup(By.name("selected[]"));
        app.groups().removeGroups();
    }

    @Test
    public void canRemoveSeveralGroups() {
        if (!app.groups().isGroupPresent(By.name("selected[]"))) {
            app.groups().createGroup(new GroupData().withName("group 1"));
        }
        if (!app.groups().isGroupPresent(By.xpath("(//input[@name=\'selected[]\'])[2]"))) {
            app.groups().createGroup(new GroupData().withName("group 2"));
        }
        app.groups().selectGroup(By.name("selected[]"));
        app.groups().selectGroup(By.xpath("(//input[@name=\'selected[]\'])[2]"));
        app.groups().removeGroups();
    }
}
