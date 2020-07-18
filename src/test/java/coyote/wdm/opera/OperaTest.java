package coyote.wdm.opera;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.BrowserTestParent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.opera.OperaDriver;

import java.io.File;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.junit.Assume.assumeTrue;

public class OperaTest extends BrowserTestParent {

  @BeforeClass
  public static void setupClass() {
    WebDriverManager.operadriver().clearResolutionCache().setup();
  }

  @Before
  public void setupTest() {
    String operaBinary = IS_OS_WINDOWS
            ? "C:\\Users\\boni\\AppData\\Local\\Programs\\Opera\\launcher.exe"
            : IS_OS_MAC ? "/Applications/Opera.app/Contents/MacOS/Opera"
            : "/usr/bin/opera";
    File opera = new File(operaBinary);
    assumeTrue(opera.exists());

    driver = new OperaDriver();
  }

}
