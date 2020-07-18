package coyote.wdm.chromium;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.BrowserTestParent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.junit.Assume.assumeTrue;

public class ChromiumTest extends BrowserTestParent {

  @BeforeClass
  public static void setupClass() {
    WebDriverManager.chromiumdriver().setup();
  }

  @Before
  public void setupTest() {
    File chromium = new File(getChromiumBinary());
    assumeTrue(chromium.exists());

    ChromeOptions options = new ChromeOptions();
    options.setBinary(chromium);
    driver = new ChromeDriver(options);
  }

  private String getChromiumBinary() {
    if (IS_OS_WINDOWS) {
      String localAppDat = System.getenv("LOCALAPPDATA")
              .replaceAll("\\\\", "\\\\\\\\");
      return localAppDat + "\\Chromium\\Application\\chrome.exe";
    } else {
      return IS_OS_MAC
              ? "/Applications/Chromium.app/Contents/MacOS/Chromium"
              : "/usr/bin/chromium-browser";
    }
  }

}
