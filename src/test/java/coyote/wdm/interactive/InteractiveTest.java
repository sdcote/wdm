package coyote.wdm.interactive;

import coyote.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;

import java.io.File;
import java.util.Collection;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(Parameterized.class)
public class InteractiveTest {

    public static final Logger log = getLogger(lookup().lookupClass());

    public static final String EXT = IS_OS_WINDOWS ? ".exe" : "";

    @Parameter(0)
    public String argument;

    @Parameter(1)
    public String driver;

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return asList(new Object[][] { { "chrome", "chromedriver" + EXT },
                { "firefox", "geckodriver" + EXT },
                { "opera", "operadriver" + EXT },
                { "phantomjs", "phantomjs" + EXT },
                { "edge", "msedgedriver.exe" },
                { "iexplorer", "IEDriverServer.exe" } });
    }

    @Test
    public void testInteractive() {
        log.debug("Running interactive wdm with arguments: {}", argument);
        WebDriverManager.main(new String[] { argument });
        File binary = new File(driver);
        boolean exists = binary.exists();
        boolean delete = binary.delete();
        assertTrue(exists && delete);
        log.debug("Interactive test with {} OK", argument);
    }

}
