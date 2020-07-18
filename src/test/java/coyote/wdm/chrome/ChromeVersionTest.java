package coyote.wdm.chrome;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.VersionTestParent;
import org.junit.Before;


public class ChromeVersionTest extends VersionTestParent {

    @Before
    public void setup() {
        browserManager = WebDriverManager.chromedriver();
        specificVersions = new String[] { "2.10", "2.33" };
    }

}
