package coyote.wdm.forced;

import coyote.wdm.config.Config;
import coyote.wdm.config.OperatingSystem;
import coyote.wdm.online.Downloader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static coyote.wdm.WebDriverManager.chromedriver;
import static coyote.wdm.config.OperatingSystem.*;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(Parameterized.class)
public class ForceOsTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Parameter
  public OperatingSystem operatingSystem;

  @InjectMocks
  public Downloader downloader;

  @Spy
  public Config config = new Config();

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{{WIN}, {LINUX}, {MAC}});
  }

  @Before
  public void setup() throws IOException {
    openMocks(this);
    cleanDirectory(new File(downloader.getCachePath()));
  }

  @After
  public void teardown() throws IOException {
    cleanDirectory(new File(downloader.getCachePath()));
  }

  @Test
  public void testForceOs() {
    chromedriver().operatingSystem(operatingSystem).setup();
    File binary = new File(chromedriver().getBinaryPath());
    log.debug("OS {} - binary path {}", operatingSystem, binary);
    assertTrue(binary.exists());
  }

}