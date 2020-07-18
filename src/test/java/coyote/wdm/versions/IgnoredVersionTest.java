package coyote.wdm.versions;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.Config;
import coyote.wdm.config.WebDriverManagerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static coyote.wdm.WebDriverManager.chromedriver;
import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class IgnoredVersionTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Before
  @After
  public void cleanCache() throws IOException {
    cleanDirectory(new File(new Config().getCachePath()));
  }

  @Test(expected = WebDriverManagerException.class)
  public void ignoredVersions() {
    String driverVersion = "81.0.4044.69";
    String[] ignoredVersions = {driverVersion};
    WebDriverManager.chromedriver().driverVersion(driverVersion)
            .ignoreDriverVersions(ignoredVersions).avoidFallback().setup();
    File binary = new File(chromedriver().getBinaryPath());
    log.debug("Using binary {} (ignoring {})", binary,
            Arrays.toString(ignoredVersions));

    for (String v : ignoredVersions) {
      assertThat(binary.getName(), not(containsString(v)));
    }
  }

}
