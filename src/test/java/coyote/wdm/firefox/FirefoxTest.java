package coyote.wdm.firefox;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.BrowserTestParent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.firefox.FirefoxDriver;


public class FirefoxTest extends BrowserTestParent {

  @BeforeClass
  public static void setupClass() {
    WebDriverManager.firefoxdriver().setup();
  }

  @Before
  public void setupTest() {
    driver = new FirefoxDriver();
  }

}
