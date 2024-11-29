import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GroupCreationTests {
    private static WebDriver driver;

    @BeforeEach
    public void setUp() {
        if (driver == null) {
            driver = new ChromeDriver();
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get("http://localhost/addressbook/");
            driver.manage().window().setSize(new Dimension(1054, 808));
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
        }
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
    public void CanCreateGroup() {
        if (!isElementPresent(By.name("group_name"))) {
            driver.findElement(By.linkText("groups")).click();
        }
        driver.findElement(By.xpath("//input[@name=\'new\']")).click();
        driver.findElement(By.name("group_name")).sendKeys("new group");
        driver.findElement(By.name("group_header")).sendKeys("group header");
        driver.findElement(By.name("group_footer")).sendKeys("group footer");
        driver.findElement(By.name("submit")).click();
        driver.findElement(By.linkText("groups")).click();
    }

    @Test
    public void CanCreateGroupWithEmptyName() {
        if (!isElementPresent(By.name("group_name"))) {
            driver.findElement(By.linkText("groups")).click();
        }
        driver.findElement(By.xpath("//input[@name=\'new\']")).click();
        driver.findElement(By.name("group_name")).sendKeys("");
        driver.findElement(By.name("group_header")).sendKeys("");
        driver.findElement(By.name("group_footer")).sendKeys("");
        driver.findElement(By.name("submit")).click();
        driver.findElement(By.linkText("groups")).click();
    }

    @Test
    public void CanCreateGroupWithNumbers() {
        if (!isElementPresent(By.name("group_name"))) {
            driver.findElement(By.linkText("groups")).click();
        }
        driver.findElement(By.xpath("//input[@name=\'new\']")).click();
        driver.findElement(By.name("group_name")).sendKeys("12345");
        driver.findElement(By.name("group_header")).sendKeys("12345");
        driver.findElement(By.name("group_footer")).sendKeys("12345");
        driver.findElement(By.name("submit")).click();
        driver.findElement(By.linkText("groups")).click();
    }
}
