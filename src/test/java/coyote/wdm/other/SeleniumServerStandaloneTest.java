
package coyote.wdm.other;

import org.junit.Test;
import org.slf4j.Logger;

import java.io.File;

import static coyote.wdm.WebDriverManager.seleniumServerStandalone;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

public class SeleniumServerStandaloneTest {

    final Logger log = getLogger(lookup().lookupClass());

    @Test
    public void testSeleniumServerLatestStable() {
        seleniumServerStandalone().setup();
        assertBinary();
    }

    @Test
    public void testSeleniumServerLatestBeta() {
        seleniumServerStandalone().useBetaVersions().setup();
        assertBinary();
    }

    @Test
    public void testSeleniumServerVersion() {
        seleniumServerStandalone().driverVersion("3.13").setup();
        assertBinary();
    }

    private void assertBinary() {
        File binary = new File(seleniumServerStandalone().getBinaryPath());
        log.debug("Binary path for selenium-server-standalone {}", binary);
        assertTrue(binary.exists());
    }

}
