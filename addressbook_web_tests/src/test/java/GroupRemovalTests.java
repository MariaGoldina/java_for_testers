import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class GroupRemovalTests extends TestBase {

    @Test
    public void canRemoveGroup() {
        openGroupsPage();
        if (!isGroupPresent(By.name("selected[]"))) {
            createGroup("new group", "group header", "group footer");
        }
        selectGroup(By.name("selected[]"));
        removeGroups();
    }

    @Test
    public void canRemoveSeveralGroups() {
        openGroupsPage();
        if (!isGroupPresent(By.name("selected[]"))) {
            createGroup("group 1", "", "");
        }
        if (!isGroupPresent(By.xpath("(//input[@name=\'selected[]\'])[2]"))) {
            createGroup("group 2", "", "");
        }
        selectGroup(By.name("selected[]"));
        selectGroup(By.xpath("(//input[@name=\'selected[]\'])[2]"));
        removeGroups();
    }
}
