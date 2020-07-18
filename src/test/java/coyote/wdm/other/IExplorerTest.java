package coyote.wdm.other;

import org.junit.Test;
import org.slf4j.Logger;

import java.io.File;

import static coyote.wdm.WebDriverManager.iedriver;
import static coyote.wdm.config.OperatingSystem.WIN;
import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

public class IExplorerTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Test
  public void testIExplorerLatest() {
    iedriver().operatingSystem(WIN).setup();
    assertIEDriverBinary();
  }

  @Test
  public void testIExplorerVersion() {
    iedriver().operatingSystem(WIN).driverVersion("3.11").setup();
    assertIEDriverBinary();
  }

  private void assertIEDriverBinary() {
    File binary = new File(iedriver().getBinaryPath());
    log.debug("Binary path for IEDriverServer {}", binary);
    assertTrue(binary.exists());
  }

}
