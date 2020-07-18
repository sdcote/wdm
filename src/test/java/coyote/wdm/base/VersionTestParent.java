package coyote.wdm.base;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.Architecture;
import coyote.wdm.config.OperatingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;

import java.util.Collection;

import static coyote.wdm.config.Architecture.*;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(Parameterized.class)
public abstract class VersionTestParent {

    @Parameter
    public Architecture architecture;

    protected WebDriverManager browserManager;
    protected String[] specificVersions;
    protected OperatingSystem os;

    final Logger log = getLogger(lookup().lookupClass());

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() {
        return asList(new Object[][] { { DEFAULT }, { X32 }, { X64 } });
    }

    @Test
    public void testLatestVersion() throws Exception {
        String osLabel = "";
        if (os != null) {
            browserManager.operatingSystem(os);
            osLabel = " os=" + os;
        }
        log.debug("Test latest {} [arch={}{}]",
                browserManager.getDriverManagerType(), architecture, osLabel);

        switch (architecture) {
        case X32:
            browserManager.arch32().setup();
            break;
        case X64:
            browserManager.arch64().setup();
            break;
        default:
            browserManager.setup();
        }

        assertThat(browserManager.getDownloadedVersion(), notNullValue());
    }

    @Test
    public void testSpecificVersions() throws Exception {
        for (String specificVersion : specificVersions) {
            if (architecture != DEFAULT) {
                browserManager.architecture(architecture);
            }
            String osLabel = "";
            if (os != null) {
                browserManager.operatingSystem(os);
                osLabel = " os=" + os;
            }
            log.debug("Test {} version={} [arch={}{}]",
                    browserManager.getDriverManagerType(), specificVersion,
                    architecture, osLabel);

            browserManager.driverVersion(specificVersion).setup();

            assertThat(browserManager.getDownloadedVersion(),
                    equalTo(specificVersion));
        }
    }

}
