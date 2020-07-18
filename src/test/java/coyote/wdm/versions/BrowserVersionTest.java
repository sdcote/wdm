package coyote.wdm.versions;

import coyote.wdm.WebDriverManager;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.File;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

public class BrowserVersionTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Test
  public void testChromeVersion() {
    WebDriverManager driverManager = WebDriverManager.chromedriver();
    driverManager.browserVersion("81").setup();
    assertDriver(driverManager);
  }

  @Test
  public void testFirefoxVersion() {
    WebDriverManager driverManager = WebDriverManager.firefoxdriver();
    driverManager.browserVersion("75").setup();
    assertDriver(driverManager);
  }

  private void assertDriver(WebDriverManager driverManager) {
    File driverFile = new File(driverManager.getBinaryPath());
    log.debug("Driver path {}", driverFile);
    assertTrue(driverFile.exists());
  }

}
