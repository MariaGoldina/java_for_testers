package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        if (app.groups().getGroupsCount() == 0) {
            app.groups().createGroup(new GroupData("new group", "group header", "group footer"));
        }
        int groupCount = app.groups().getGroupsCount();
        app.groups().selectGroup(By.name("selected[]"));
        app.groups().removeGroups();
        int newGroupCount = app.groups().getGroupsCount();
        Assertions.assertEquals(groupCount - 1, newGroupCount);
    }

    @Test
    public void canRemoveSeveralGroups() {
        if (app.groups().getGroupsCount() < 2) {
            app.groups().createGroup(new GroupData().withName("group 1"));
            app.groups().createGroup(new GroupData().withName("group 2"));
        }
        int groupCount = app.groups().getGroupsCount();
        app.groups().selectGroup(By.name("selected[]"));
        app.groups().selectGroup(By.xpath("(//input[@name=\'selected[]\'])[2]"));
        app.groups().removeGroups();
        int newGroupCount = app.groups().getGroupsCount();
        Assertions.assertEquals(groupCount - 2, newGroupCount);
    }

    @Test
    public void canRemoveAllSelectedGroups() {
        if (app.groups().getGroupsCount() == 0) {
            app.groups().createGroup(new GroupData("new group", "group header", "group footer"));
        }
        app.groups().removeAllSelectedGroups();
        Assertions.assertEquals(0, app.groups().getGroupsCount());
    }
}
