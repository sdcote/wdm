package coyote.wdm.cache;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.Config;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

import static java.lang.invoke.MethodHandles.lookup;
import static java.nio.file.Files.createTempDirectory;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class CustomCacheTest {

  final Logger log = getLogger(lookup().lookupClass());

  Config globalConfig;

  @Before
  public void setup() {
    globalConfig = WebDriverManager.globalConfig();
  }

  @Test
  public void testCachePath() throws IOException {
    Path tmpFolder = createTempDirectory("").toRealPath();
    globalConfig.setCachePath(tmpFolder.toString());
    log.info("Using temporary folder {} as cache", tmpFolder);
    WebDriverManager.chromedriver().forceDownload().setup();
    String binaryPath = WebDriverManager.chromedriver().getBinaryPath();
    log.info("Binary path {}", binaryPath);
    assertThat(binaryPath, startsWith(tmpFolder.toString()));
    log.info("Deleting temporary folder {}", tmpFolder);
    WebDriverManager.chromedriver().clearCache();
  }

  @Test
  public void testCachePathContainsTilde() {
    String customPath = "C:\\user\\abcdef~1\\path";
    globalConfig.setCachePath(customPath);
    String cachePath = globalConfig.getCachePath();
    log.info("Using {} got {}", customPath, cachePath);
    assertThat(cachePath, startsWith(customPath));
  }

  @Test
  public void testCachePathStartsWithTildeSlash() {
    String customPath = "~/webdrivers";
    globalConfig.setCachePath(customPath);
    String cachePath = globalConfig.getCachePath();
    log.info("Using {} got {}", customPath, cachePath);
    assertThat(cachePath, startsWith(System.getProperty("user.home")));
  }

  @Test
  public void testCachePathStartsWithTilde() {
    String customPath = "~webdrivers";
    globalConfig.setCachePath(customPath);
    String cachePath = globalConfig.getCachePath();
    log.info("Using {} got {}", customPath, cachePath);
    assertThat(cachePath, startsWith(customPath));
  }

  @After
  public void teardown() throws IOException {
    globalConfig.reset();
  }
}
