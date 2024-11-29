import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GroupRemovalTests {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost/addressbook/");
        driver.manage().window().setSize(new Dimension(1054, 808));
        driver.findElement(By.name("user")).sendKeys("admin");
        driver.findElement(By.name("pass")).sendKeys("secret");
        driver.findElement(By.xpath("//input[@value=\'Login\']")).click();

    }

    @AfterAll
    public static void tearDown() {
//        driver.findElement(By.linkText("Logout")).click();
        driver.quit();
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void canRemoveGroup() {
        if (!isElementPresent(By.name("new"))) {
            driver.findElement(By.linkText("groups")).click();
        }
        if (!isElementPresent(By.name("selected[]"))) {
            driver.findElement(By.name("new")).click();
            driver.findElement(By.name("group_name")).sendKeys("new group");
            driver.findElement(By.name("group_header")).sendKeys("group header");
            driver.findElement(By.name("group_footer")).sendKeys("group footer");
            driver.findElement(By.name("submit")).click();
            driver.findElement(By.linkText("group page")).click();
        }
        driver.findElement(By.name("selected[]")).click();
        driver.findElement(By.name("delete")).click();
        driver.findElement(By.linkText("group page")).click();
    }

    @Test
    public void canRemoveSeveralGroups() {
        if (!isElementPresent(By.name("new"))) {
            driver.findElement(By.linkText("groups")).click();
        }
        if (!isElementPresent(By.name("selected[]"))
                && !isElementPresent(By.xpath("(//input[@name=\'selected[]\'])[2]"))) {
            driver.findElement(By.name("new")).click();
            driver.findElement(By.name("group_name")).sendKeys("group1");
            driver.findElement(By.name("group_header")).sendKeys("");
            driver.findElement(By.name("group_footer")).sendKeys("");
            driver.findElement(By.name("submit")).click();
            driver.findElement(By.linkText("group page")).click();
            driver.findElement(By.name("new")).click();
            driver.findElement(By.name("group_name")).sendKeys("group2");
            driver.findElement(By.name("group_header")).sendKeys("");
            driver.findElement(By.name("group_footer")).sendKeys("");
            driver.findElement(By.name("submit")).click();
            driver.findElement(By.linkText("group page")).click();
        }
        driver.findElement(By.name("selected[]")).click();
        driver.findElement(By.xpath("(//input[@name=\'selected[]\'])[2]")).click();
        driver.findElement(By.name("delete")).click();
        driver.findElement(By.linkText("group page")).click();
    }
}
