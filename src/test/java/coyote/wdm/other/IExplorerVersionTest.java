package coyote.wdm.other;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.VersionTestParent;
import org.junit.Before;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static coyote.wdm.config.OperatingSystem.WIN;

public class IExplorerVersionTest extends VersionTestParent {

  @Before
  public void setup() {
    os = WIN;
    browserManager = WebDriverManager
            .getInstance(InternetExplorerDriver.class);
    specificVersions = new String[]{"2.39", "2.47"};
  }

}
