package coyote.wdm.edge;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.Config;
import coyote.wdm.online.HttpClient;
import coyote.wdm.versions.VersionDetector;
import org.junit.Test;
import org.slf4j.Logger;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import static coyote.wdm.config.OperatingSystem.WIN;
import static java.lang.invoke.MethodHandles.lookup;
import static java.nio.charset.StandardCharsets.UTF_16;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

public class EdgeLatestVersionTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Test
  public void edgeVersionTest() throws Exception {
    Config config = new Config();
    HttpClient httpClient = new HttpClient(config);
    VersionDetector versionDetector = new VersionDetector(config,
            httpClient);
    Optional<String> driverVersion = Optional.empty();
    URL driverUrl = new URL("https://msedgedriver.azureedge.net/");
    Charset versionCharset = UTF_16;
    String driverName = "msedgedriver";
    String versionLabel = "LATEST_STABLE";

    Optional<String> driverVersionFromRepository = versionDetector
            .getDriverVersionFromRepository(driverVersion, driverUrl,
                    versionCharset, driverName, versionLabel, versionLabel);
    assertTrue(driverVersionFromRepository.isPresent());
    String edgeVersion = driverVersionFromRepository.get();
    log.debug("driverVersionFromRepository {}", edgeVersion);

    WebDriverManager edgedriver = WebDriverManager.edgedriver();
    List<String> driverVersions = edgedriver.getDriverVersions();
    log.debug("All driverUrls {}", driverVersions);

    if (!driverVersions.contains(edgeVersion)) {
      log.warn("{}", String.format(
              "Stable version (%s) is not in the URL list", edgeVersion));
      edgedriver.operatingSystem(WIN).forceDownload()
              .avoidBrowserDetection().setup();
      assertThat(edgedriver.getDownloadedVersion(), notNullValue());
    }
  }

}
