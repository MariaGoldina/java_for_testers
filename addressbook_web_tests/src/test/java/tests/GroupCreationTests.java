package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void canCreateGroup() {
        app.openGroupsPage();
        app.createGroup(new GroupData("new group", "group header", "group footer"));
    }

    @Test
    public void canCreateGroupWithEmptyName() {
        app.openGroupsPage();
        app.createGroup(new GroupData());
    }

    @Test
    public void canCreateGroupWithNumbers() {
        app.openGroupsPage();
        app.createGroup(new GroupData("12345", "12345", "12345"));
    }

    @Test
    public void canCreateGroupWithNameOnly() {
        app.openGroupsPage();
        app.createGroup(new GroupData().withName("some name"));
    }
}
