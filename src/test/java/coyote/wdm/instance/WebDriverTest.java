package coyote.wdm.instance;

import coyote.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class WebDriverTest {

    @Parameter
    public Class<? extends WebDriver> driverClass;

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return asList(new Object[][] { { ChromeDriver.class },
                { FirefoxDriver.class }, { PhantomJSDriver.class } });
    }

    @Test
    public void testWebDriver() {
        WebDriverManager.getInstance(driverClass).setup();
        String binaryPath = WebDriverManager.getInstance(driverClass)
                .getBinaryPath();
        File binary = new File(binaryPath);
        assertTrue(binary.exists());
    }

}
