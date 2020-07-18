package coyote.wdm.cache;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.Architecture;
import coyote.wdm.config.Config;
import coyote.wdm.config.DriverManagerType;
import coyote.wdm.config.OperatingSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static coyote.wdm.config.Architecture.DEFAULT;
import static coyote.wdm.config.DriverManagerType.CHROME;
import static coyote.wdm.config.DriverManagerType.FIREFOX;
import static coyote.wdm.config.OperatingSystem.LINUX;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(Parameterized.class)
public class CacheTest {

  static final Logger log = getLogger(lookup().lookupClass());

  @Parameter(0)
  public DriverManagerType driverManagerType;

  @Parameter(1)
  public String driverName;

  @Parameter(2)
  public String driverVersion;

  @Parameter(3)
  public Architecture arch;

  @Parameter(4)
  public OperatingSystem os;

  @Parameters(name = "{index}: {0} {1}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{
            {CHROME, "chromedriver", "81.0.4044.69", DEFAULT, LINUX},
            {FIREFOX, "geckodriver", "0.26.0", DEFAULT, LINUX}});
  }

  @Before
  @After
  public void cleanCache() throws IOException {
    cleanDirectory(new File(new Config().getCachePath()));
  }

  @Test
  public void testCache() throws Exception {
    WebDriverManager browserManager = WebDriverManager
            .getInstance(driverManagerType);
    browserManager.forceDownload().operatingSystem(os)
            .driverVersion(driverVersion).setup();

    CacheHandler cacheHandler = new CacheHandler(new Config());
    Optional<String> driverFromCache = cacheHandler.getDriverFromCache(
            driverVersion, driverName, driverManagerType, arch, os.name());

    log.debug("Driver from cache: {}", driverFromCache);
    assertThat(driverFromCache.get(), notNullValue());
  }

}
