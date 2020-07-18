package coyote.wdm.versions;

import coyote.wdm.WebDriverManager;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;

import java.util.Collection;

import static coyote.wdm.config.OperatingSystem.WIN;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(Parameterized.class)
@Ignore
public class LatestAndBetaTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Parameter
  public Class<? extends WebDriver> driverClass;

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{{ChromeDriver.class},
            {EdgeDriver.class}});
  }

  @Test
  public void testLatestAndBetaedgedriver() {
    WebDriverManager.getInstance(driverClass).avoidResolutionCache()
            .avoidBrowserDetection().operatingSystem(WIN).setup();
    String edgedriverStable = WebDriverManager.getInstance(driverClass)
            .getDownloadedVersion();
    log.debug("edgedriver LATEST version: {}", edgedriverStable);

    WebDriverManager.getInstance(driverClass).avoidResolutionCache()
            .avoidBrowserDetection().useBetaVersions().operatingSystem(WIN)
            .setup();
    String edgedriverBeta = WebDriverManager.getInstance(driverClass)
            .getDownloadedVersion();
    log.debug("edgedriver BETA version: {}", edgedriverBeta);

    assertThat(edgedriverStable, not(equalTo(edgedriverBeta)));
  }

}
