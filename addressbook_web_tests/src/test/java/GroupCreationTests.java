import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void canCreateGroup() {
        openGroupsPage();
        createGroup(new GroupData("new group", "group header", "group footer"));
    }

    @Test
    public void canCreateGroupWithEmptyName() {
        openGroupsPage();
        createGroup(new GroupData());
    }

    @Test
    public void canCreateGroupWithNumbers() {
        openGroupsPage();
        createGroup(new GroupData("12345", "12345", "12345"));
    }

    @Test
    public void canCreateGroupWithNameOnly() {
        openGroupsPage();
        createGroup(new GroupData().withName("some name"));
    }
}
