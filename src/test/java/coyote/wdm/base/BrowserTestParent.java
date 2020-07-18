package coyote.wdm.base;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;

/**
 * Parent class for browser based tests.
 */
public abstract class BrowserTestParent {

  protected WebDriver driver;

  @After
  public void teardown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  public void test() {
    driver.manage().timeouts().implicitlyWait(30, SECONDS);
    driver.get("https://google.com/"); // make sure the driver is functional
    assertTrue(driver.getTitle() != null);
  }

}
