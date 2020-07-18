package coyote.wdm.cache;

import coyote.wdm.WebDriverManager;
import org.junit.Test;

import java.io.File;

import static coyote.wdm.WebDriverManager.chromedriver;
import static org.junit.Assert.assertTrue;

public class ResolutionCacheTest {

    @Test
    public void testEmptyTtl() {
        WebDriverManager.main(new String[] { "clear-resolution-cache" });
        chromedriver().ttl(0).ttlBrowsers(0).setup();
        String binaryPath = chromedriver().getBinaryPath();
        File binary = new File(binaryPath);
        assertTrue(binary.exists());
    }

}
