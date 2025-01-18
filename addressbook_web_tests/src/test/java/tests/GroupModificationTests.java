package tests;

import common.CommonFunctions;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

@Feature("Groups")
public class GroupModificationTests extends TestBase {
    @Story("Modify general group fields")
    @Test
    void canModifyGroup() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getGroupsDBCount() == 0) {
                        app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
                    }
                });
        var oldGroups = app.hbm().getGroupsDBList();
        var index = new Random().nextInt(oldGroups.size());
        var testData = new GroupData().withName("modified name" + CommonFunctions.randomStringWithNumbers(1000));
        app.groups().reloadGroupsPage();
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.hbm().getGroupsDBList();
        Allure.step("Validating results", step -> {
            var expectedGroups = new ArrayList<>(oldGroups);
            expectedGroups.set(index, testData.withId(oldGroups.get(index).id()));
            Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedGroups));
        });
    }

    @Story("Modify all group fields")
    @Test
    void canModifyGroupWithAllFields() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getGroupsDBCount() == 0) {
                        app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
                    }
                });
        var oldGroups = app.hbm().getGroupsDBList();
        var index = new Random().nextInt(oldGroups.size());
        var testData = new GroupData("", "modified name" + CommonFunctions.randomStringWithNumbers(1000),
                "modified header" + CommonFunctions.randomStringWithNumbers(1000),
                "modified footer" + CommonFunctions.randomStringWithNumbers(1000));
        app.groups().reloadGroupsPage();
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.hbm().getGroupsDBList();
        Allure.step("Validating results", step -> {
            var expectedGroups = new ArrayList<>(oldGroups);
            expectedGroups.set(index, testData.withId(oldGroups.get(index).id()));
            Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedGroups));
        });
    }
}
