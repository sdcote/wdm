package coyote.wdm.other;

import coyote.wdm.base.BrowserTestParent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.safari.SafariDriver;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.junit.Assume.assumeTrue;


public class SafariTest extends BrowserTestParent {

  @BeforeClass
  public static void setupClass() {
    assumeTrue(IS_OS_MAC);
  }

  @Before
  public void setupTest() {
    driver = new SafariDriver();
  }
}
