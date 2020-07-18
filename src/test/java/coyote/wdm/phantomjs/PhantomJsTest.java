package coyote.wdm.phantomjs;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.BrowserTestParent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class PhantomJsTest extends BrowserTestParent {

  @BeforeClass
  public static void setupClass() {
    WebDriverManager.phantomjs().setup();
  }

  @Before
  public void setupTest() {
    driver = new PhantomJSDriver();
  }

}
