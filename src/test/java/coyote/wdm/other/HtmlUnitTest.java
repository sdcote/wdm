package coyote.wdm.other;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.BrowserTestParent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class HtmlUnitTest extends BrowserTestParent {

  private static Class<? extends WebDriver> webDriverClass;

  @BeforeClass
  public static void setupClass() {
    webDriverClass = HtmlUnitDriver.class;
    WebDriverManager.getInstance(webDriverClass).setup();
  }

  @Before
  public void htmlUnitTest()
          throws InstantiationException, IllegalAccessException {
    driver = webDriverClass.newInstance();
  }

}
