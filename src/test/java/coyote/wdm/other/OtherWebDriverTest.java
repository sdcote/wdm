package coyote.wdm.other;

import coyote.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class OtherWebDriverTest {

  @Parameter(0)
  public Class<? extends WebDriver> driverClass;

  @Parameter(1)
  public Class<? extends Throwable> exception;

  protected WebDriver driver;

  @Parameters(name = "{index}: {0} {1}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{
            {SafariDriver.class, WebDriverException.class},
            {EventFiringWebDriver.class, InstantiationException.class},
            {HtmlUnitDriver.class, null},
            {RemoteWebDriver.class, IllegalAccessException.class}});
  }

  @Before
  public void setupTest() {
    WebDriverManager.getInstance(driverClass).setup();
  }

  @After
  public void teardown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void test() {
    if (exception != null) {
      assertThrows(exception, this::createInstace);
    }

  }

  private void createInstace()
          throws InstantiationException, IllegalAccessException {
    driver = driverClass.newInstance();
  }

}
