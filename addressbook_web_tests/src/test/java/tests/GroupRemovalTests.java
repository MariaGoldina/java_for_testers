package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        if (app.groups().getGroupsCount() == 0) {
            app.groups().createGroup(new GroupData("", "new group", "group header", "group footer"));
        }
        var oldGroups = app.groups().getList();
        var index = new Random().nextInt(oldGroups.size());
        app.groups().selectGroup(oldGroups.get(index));
        app.groups().removeGroups();
        var newGroups = app.groups().getList();
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.remove(index);
        Assertions.assertEquals(newGroups, expectedGroups);
    }

    @Test
    public void canRemoveSeveralGroups() {
        if (app.groups().getGroupsCount() < 2) {
            app.groups().createGroup(new GroupData().withName("group 1"));
            app.groups().createGroup(new GroupData().withName("group 2"));
        }
        var oldGroups = app.groups().getList();
        app.groups().selectGroup(oldGroups.get(0));
        app.groups().selectGroup(oldGroups.get(1));
        app.groups().removeGroups();
        var newGroups = app.groups().getList();
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.remove(0);
        expectedGroups.remove(0);
        Assertions.assertEquals(newGroups, expectedGroups);
    }

    @Test
    public void canRemoveAllSelectedGroups() {
        if (app.groups().getGroupsCount() == 0) {
            app.groups().createGroup(new GroupData("", "new group", "group header", "group footer"));
        }
        app.groups().removeAllSelectedGroups();
        Assertions.assertEquals(0, app.groups().getGroupsCount());
    }
}
