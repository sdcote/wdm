package coyote.wdm.edge;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.VersionTestParent;
import org.junit.Before;
import org.openqa.selenium.edge.EdgeDriver;

import static coyote.wdm.config.OperatingSystem.WIN;

public class EdgeVersionTest extends VersionTestParent {

    @Before
    public void setup() {
        browserManager = WebDriverManager.getInstance(EdgeDriver.class);
        os = WIN;
        specificVersions = new String[] { "75.0.139.20", "76.0.183.0",
                "77.0.237.0", "78.0.277.0", "79.0.313.0", "80.0.361.111",
                "81.0.409.0", "82.0.459.1" };
    }

}
