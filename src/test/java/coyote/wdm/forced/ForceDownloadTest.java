package coyote.wdm.forced;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.OperatingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ForceDownloadTest {

  static final int TIMEOUT = 20;
  static final OperatingSystem OS = OperatingSystem.WIN;

  @Parameter
  public Class<? extends WebDriver> driverClass;

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{{ChromeDriver.class},
            {FirefoxDriver.class}, {OperaDriver.class},
            {EdgeDriver.class}});
  }

  @Test
  public void testForceDownload() {
    WebDriverManager driverManager = WebDriverManager
            .getInstance(driverClass);
    driverManager.forceDownload().avoidBrowserDetection()
            .avoidReadReleaseFromRepository().timeout(TIMEOUT)
            .operatingSystem(OS).setup();
    assertThat(driverManager.getBinaryPath(), notNullValue());
  }

}
