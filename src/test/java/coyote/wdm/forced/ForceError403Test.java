package coyote.wdm.forced;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import static coyote.wdm.WebDriverManager.firefoxdriver;
import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class ForceError403Test {

  final Logger log = getLogger(lookup().lookupClass());

  static final int NUM = 40;

  @Ignore
  @Test
  public void test403() {
    for (int i = 0; i < NUM; i++) {
      log.debug("Forcing 403 error {}/{}", i + 1, NUM);
      firefoxdriver().avoidBrowserDetection().avoidResolutionCache().setup();
      assertThat(firefoxdriver().getBinaryPath(), notNullValue());
    }
  }

}
