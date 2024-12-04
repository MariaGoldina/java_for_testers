package manager;

import model.GroupData;
import org.openqa.selenium.By;

public class GroupHelper extends HelperBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openGroupsPage() {
        if (!manager.groups.isElementPresent(By.name("new"))) {
            openPage(By.linkText("groups"));
        }
    }

    public boolean isGroupPresent(By locator) {
        openGroupsPage();
        return manager.groups.isElementPresent(locator);
    }

    public void createGroup(GroupData group) {
        openGroupsPage();
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupsPage();
    }

    public void selectGroup(By locator) {
        openGroupsPage();
        click(locator);
    }

    public void removeGroups() {
        initRemove();
        returnToGroupsPage();
    }

    public void modifyGroup(GroupData modifiedGroup) {
        openGroupsPage();
        selectGroup(By.name("selected[]"));
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
}
