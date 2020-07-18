package coyote.wdm.instance;

import coyote.wdm.config.DriverManagerType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DriverManagerTypeTest {

  @Parameterized.Parameter
  public String browserClass;

  @Parameterized.Parameter(1)
  public DriverManagerType driverManagerType;

  @Test
  public void shouldReturnTheCorrectDriverClass() {
    assertEquals(browserClass, driverManagerType.browserClass());
  }

  @Parameterized.Parameters(name = "{index}: {1}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{
            {"org.openqa.selenium.chrome.ChromeDriver", DriverManagerType.CHROME},
            {"org.openqa.selenium.chrome.ChromeDriver", DriverManagerType.CHROMIUM},
            {"org.openqa.selenium.firefox.FirefoxDriver", DriverManagerType.FIREFOX},
            {"org.openqa.selenium.opera.OperaDriver", DriverManagerType.OPERA},
            {"org.openqa.selenium.edge.EdgeDriver", DriverManagerType.EDGE},
            {"org.openqa.selenium.phantomjs.PhantomJSDriver", DriverManagerType.PHANTOMJS},
            {"org.openqa.selenium.ie.InternetExplorerDriver", DriverManagerType.IEXPLORER},
            {"org.openqa.selenium.safari.SafariDriver", DriverManagerType.SAFARI},
            {"org.openqa.selenium.remote.server.SeleniumServer", DriverManagerType.SELENIUM_SERVER_STANDALONE}});
  }
}
