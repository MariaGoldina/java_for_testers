package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

@Feature("Groups")
public class GroupRemovalTests extends TestBase {
    @Story("Remove group")
    @Test
    public void canRemoveGroup() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getGroupsDBCount() == 0) {
                        app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
                    }
                });
        var oldGroups = app.hbm().getGroupsDBList();
        var index = new Random().nextInt(oldGroups.size());
        app.groups().reloadGroupsPage();
        app.groups().selectGroup(oldGroups.get(index));
        app.groups().removeGroups();
        var newGroups = app.hbm().getGroupsDBList();
        Allure.step("Validating results", step -> {
            var expectedGroups = new ArrayList<>(oldGroups);
            expectedGroups.remove(index);
            Assertions.assertEquals(newGroups, expectedGroups);
        });
    }

    @Story("Remove several groups")
    @Test
    public void canRemoveSeveralGroups() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getGroupsDBCount() < 2) {
                        app.hbm().createGroupInDB(new GroupData().withName("group 1"));
                        app.hbm().createGroupInDB(new GroupData().withName("group 2"));
                    }
                });
        var oldGroups = app.hbm().getGroupsDBList();
        app.groups().reloadGroupsPage();
        app.groups().selectGroup(oldGroups.get(0));
        app.groups().selectGroup(oldGroups.get(1));
        app.groups().removeGroups();
        var newGroups = app.hbm().getGroupsDBList();
        Allure.step("Validating results", step -> {
            var expectedGroups = new ArrayList<>(oldGroups);
            expectedGroups.remove(0);
            expectedGroups.remove(0);
            Assertions.assertEquals(newGroups, expectedGroups);
        });
    }

    @Story("Remove all group")
    @Test
    public void canRemoveAllSelectedGroups() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getGroupsDBCount() == 0) {
                        app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
                    }
                });
        app.groups().reloadGroupsPage();
        app.groups().removeAllSelectedGroups();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(0, app.hbm().getGroupsDBCount());
            Assertions.assertEquals(0, app.groups().getGroupsCount());
        });
    }
}
