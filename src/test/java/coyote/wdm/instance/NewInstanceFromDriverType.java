package coyote.wdm.instance;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.DriverManagerType;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewInstanceFromDriverType  {

    private static DriverManagerType driverManagerType = DriverManagerType.CHROME;
    private static WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.getInstance(driverManagerType).setup();
    }

    @Before
    public void setupTest() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> driverClass =  Class.forName(driverManagerType.browserClass());
        driver = (WebDriver) driverClass.newInstance();
    }

    @Test
    public void createNewChromeInstanceFromDriverManagerType() {
        assertThat(driver, instanceOf(ChromeDriver.class));
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
