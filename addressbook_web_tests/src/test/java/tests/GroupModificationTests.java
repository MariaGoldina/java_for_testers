package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupModificationTests extends TestBase {
    @Test
    void canModifyGroup() {
        if (app.groups().getGroupsCount() == 0) {
            app.groups().createGroup(new GroupData("new group", "group header", "group footer"));
        }
        app.groups().modifyGroup(new GroupData().withName("modified name"));
    }

    @Test
    void canModifyGroupWithAllFields() {
        if (app.groups().getGroupsCount() == 0) {
            app.groups().createGroup(new GroupData("new group", "group header", "group footer"));
        }
        app.groups().modifyGroup(new GroupData("modified name", "modified header", "modified footer"));
    }
}
