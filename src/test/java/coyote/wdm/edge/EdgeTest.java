package coyote.wdm.edge;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.BrowserTestParent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.edge.EdgeDriver;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.junit.Assume.assumeTrue;

public class EdgeTest extends BrowserTestParent {

  @BeforeClass
  public static void setupClass() {
    assumeTrue(IS_OS_WINDOWS || IS_OS_MAC);
    WebDriverManager.edgedriver().setup();
  }

  @Before
  public void setupTest() {
    driver = new EdgeDriver();
  }

}
