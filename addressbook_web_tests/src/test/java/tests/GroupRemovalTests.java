package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        app.openGroupsPage();
        if (!app.isGroupPresent(By.name("selected[]"))) {
            app.createGroup(new GroupData("new group", "group header", "group footer"));
        }
        app.selectGroup(By.name("selected[]"));
        app.removeGroups();
    }

    @Test
    public void canRemoveSeveralGroups() {
        app.openGroupsPage();
        if (!app.isGroupPresent(By.name("selected[]"))) {
            app.createGroup(new GroupData().withName("group 1"));
        }
        if (!app.isGroupPresent(By.xpath("(//input[@name=\'selected[]\'])[2]"))) {
            app.createGroup(new GroupData().withName("group 2"));
        }
        app.selectGroup(By.name("selected[]"));
        app.selectGroup(By.xpath("(//input[@name=\'selected[]\'])[2]"));
        app.removeGroups();
    }
}
