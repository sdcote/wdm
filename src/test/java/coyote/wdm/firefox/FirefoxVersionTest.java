package coyote.wdm.firefox;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.VersionTestParent;
import org.junit.Before;

public class FirefoxVersionTest extends VersionTestParent {

    @Before
    public void setup() {
        browserManager = WebDriverManager.firefoxdriver();
        specificVersions = new String[] { "0.8.0", "0.19.1" };
    }

}
