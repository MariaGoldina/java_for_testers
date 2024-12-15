package manager;

import model.GroupData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroupHelper extends HelperBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public Comparator<GroupData> compareById = (o1, o2) -> {
        return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };

    public void openGroupsPage() {
        if (!manager.groups().isElementPresent(By.name("new"))) {
            openPage(By.linkText("groups"));
        }
    }

    public void createGroup(GroupData group) {
        openGroupsPage();
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupsPage();
    }

    public void selectGroup(GroupData group) {
        openGroupsPage();
        click(By.cssSelector(String.format("input[value='%s']", group.id())));
    }

    private void selectAllGroups() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    public void removeGroups() {
        initRemove();
        returnToGroupsPage();
    }

    public void removeAllSelectedGroups() {
        openGroupsPage();
        selectAllGroups();
        removeGroups();
    }

    public void modifyGroup(GroupData group, GroupData modifiedGroup) {
        openGroupsPage();
        selectGroup(group);
        initGroupModification();
        fillGroupForm(modifiedGroup);
        submitGroupModification();
        returnToGroupsPage();
    }

    private void initGroupModification() {
        click(By.name("edit"));
    }

    private void initGroupCreation() {
        click(By.name("new"));
    }

    private void initRemove() {
        click(By.name("delete"));
    }

    private void submitGroupModification() {
        click(By.name("update"));
    }

    private void submitGroupCreation() {
        click(By.name("submit"));
    }

    private void fillGroupForm(GroupData group) {
        typeText(By.name("group_name"), group.name());
        typeText(By.name("group_header"), group.header());
        typeText(By.name("group_footer"), group.footer());
    }

    private void returnToGroupsPage() {
        click(By.linkText("group page"));
    }

    public int getGroupsCount() {
        openGroupsPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public List<GroupData> getList() {
        openGroupsPage();
        var groups = new ArrayList<GroupData>();
        var spans = manager.driver.findElements(By.cssSelector("span.group"));
        for (var span : spans) {
            var name = span.getText();
            var checkbox = span.findElement(By.name("selected[]"));
            var id = checkbox.getAttribute("value");
            groups.add(new GroupData().withId(id).withName(name));
        }
        return groups;
    }
}
