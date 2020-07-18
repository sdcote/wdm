package coyote.wdm.properties;

import coyote.wdm.WebDriverManager;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class PropertiesTest {

  @Test
  public void testCustomProperties() {
    WebDriverManager chromedriver = WebDriverManager.chromedriver();
    chromedriver.config().setProperties("wdm-test.properties");
    chromedriver.setup();
    String binaryPath = chromedriver.getBinaryPath();
    File binary = new File(binaryPath);
    assertTrue(binary.exists());
  }

  @Test
  public void testEmptyProperties() {
    WebDriverManager.chromedriver().properties("").setup();
    assertTrue(true);
  }

}
