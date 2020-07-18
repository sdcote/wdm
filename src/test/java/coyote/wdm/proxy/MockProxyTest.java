package coyote.wdm.proxy;

import coyote.wdm.config.Config;
import coyote.wdm.online.Downloader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockserver.integration.ClientAndServer;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;

import static coyote.wdm.WebDriverManager.chromedriver;
import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.junit.Assert.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(MockitoJUnitRunner.class)
public class MockProxyTest {

  final Logger log = getLogger(lookup().lookupClass());

  @InjectMocks
  public Downloader downloader;

  @Spy
  public Config config = new Config();

  private ClientAndServer proxy;
  private int proxyPort;

  @Before
  public void setup() throws IOException {
    File wdmCache = new File(downloader.getCachePath());
    log.debug("Cleaning local cache {}", wdmCache);
    cleanDirectory(wdmCache);

    try (ServerSocket serverSocket = new ServerSocket(0)) {
      proxyPort = serverSocket.getLocalPort();
    }
    log.debug("Starting mock proxy on port {}", proxyPort);
    proxy = startClientAndServer(proxyPort);
  }

  @After
  public void teardown() {
    log.debug("Stopping mock proxy on port {}", proxyPort);
    proxy.stop();
  }

  @Test
  public void testMockProx() throws MalformedURLException {
    chromedriver().proxy("localhost:" + proxyPort).proxyUser("")
            .proxyPass("")
            .driverRepositoryUrl(
                    new URL("https://chromedriver.storage.googleapis.com/"))
            .setup();
    File binary = new File(chromedriver().getBinaryPath());
    assertTrue(binary.exists());
  }

}