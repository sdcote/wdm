package coyote.wdm.versions;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.DriverManagerType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;

import static coyote.wdm.config.DriverManagerType.*;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CurrentVersionTest {

  @Parameter(0)
  public DriverManagerType driverManagerType;

  @Parameter(1)
  public String url;

  @Parameter(2)
  public String expectedVersion;

  @Parameters(name = "{index}: {0} {1} {2}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{{CHROME,
            "https://chromedriver.storage.googleapis.com/81.0.4044.69/chromedriver_linux64.zip",
            "81.0.4044.69"},
            {EDGE, "https://msedgedriver.azureedge.net/81.0.416.64/edgedriver_win64.zip",
                    "81.0.416.64"},
            {OPERA, "https://github.com/operasoftware/operachromiumdriver/releases/download/v.81.0.4044.113/operadriver_win64.zip",
                    "81.0.4044.113"},
            {FIREFOX,
                    "https://github.com/mozilla/geckodriver/releases/download/v0.26.0/geckodriver-v0.26.0-win64.zip",
                    "0.26.0"},
            {PHANTOMJS,
                    "https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.5.0-beta-linux-ubuntu-trusty-x86_64.tar.gz",
                    "2.5.0-beta"}});

  }

  @Test
  public void testCurrentVersion() throws Exception {
    WebDriverManager browserManager = WebDriverManager
            .getInstance(driverManagerType);

    Method method = WebDriverManager.class
            .getDeclaredMethod("getCurrentVersion", URL.class);
    method.setAccessible(true);

    String currentVersion = (String) method.invoke(browserManager,
            new URL(url));

    assertThat(currentVersion, equalTo(expectedVersion));
  }

}
