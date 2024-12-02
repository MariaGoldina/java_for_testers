import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void canCreateGroup() {
        openGroupsPage();
        createGroup("new group", "group header", "group footer");
    }

    @Test
    public void canCreateGroupWithEmptyName() {
        openGroupsPage();
        createGroup("", "", "");
    }

    @Test
    public void canCreateGroupWithNumbers() {
        openGroupsPage();
        createGroup("12345", "12345", "12345");
    }
}
