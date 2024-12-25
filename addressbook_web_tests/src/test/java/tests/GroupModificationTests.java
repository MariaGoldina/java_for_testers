package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class GroupModificationTests extends TestBase {
    @Test
    void canModifyGroup() {
        if (app.hbm().getGroupsDBCount() == 0) {
            app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
        }
        var oldGroups = app.hbm().getGroupsDBList();
        var index = new Random().nextInt(oldGroups.size());
        var testData = new GroupData().withName("modified name");
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.hbm().getGroupsDBList();
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.set(index, testData.withId(oldGroups.get(index).id()));

        newGroups.sort(app.groups().compareById);
        expectedGroups.sort(app.groups().compareById);
        Assertions.assertEquals(newGroups, expectedGroups);
    }

    @Test
    void canModifyGroupWithAllFields() {
        if (app.hbm().getGroupsDBCount() == 0) {
            app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
        }
        var oldGroups = app.hbm().getGroupsDBList();
        var index = new Random().nextInt(oldGroups.size());
        var testData = new GroupData("", "modified name", "modified header", "modified footer");
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.hbm().getGroupsDBList();
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.set(index, testData.withId(oldGroups.get(index).id()));

        newGroups.sort(app.groups().compareById);
        expectedGroups.sort(app.groups().compareById);
        Assertions.assertEquals(newGroups, expectedGroups);
    }
}
