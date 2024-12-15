package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class GroupModificationTests extends TestBase {
    @Test
    void canModifyGroup() {
        if (app.groups().getGroupsCount() == 0) {
            app.groups().createGroup(new GroupData("", "new group", "group header", "group footer"));
        }
        var oldGroups = app.groups().getList();
        var index = new Random().nextInt(oldGroups.size());
        var testData = new GroupData().withName("modified name");
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.groups().getList();
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.set(index, testData.withId(oldGroups.get(index).id()));

        newGroups.sort(app.groups().compareById);
        expectedGroups.sort(app.groups().compareById);
        Assertions.assertEquals(newGroups, expectedGroups);
    }

    @Test
    void canModifyGroupWithAllFields() {
        if (app.groups().getGroupsCount() == 0) {
            app.groups().createGroup(new GroupData("", "new group", "group header", "group footer"));
        }
        var oldGroups = app.groups().getList();
        var index = new Random().nextInt(oldGroups.size());
        var testData = new GroupData("", "modified name", "modified header", "modified footer");
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.groups().getList();
        var expectedGroups = new ArrayList<>(oldGroups);
        expectedGroups.set(index, testData.withId(oldGroups.get(index).id()).withHeader("").withFooter(""));

        newGroups.sort(app.groups().compareById);
        expectedGroups.sort(app.groups().compareById);
        Assertions.assertEquals(newGroups, expectedGroups);
    }
}
