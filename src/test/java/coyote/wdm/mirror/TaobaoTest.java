package coyote.wdm.mirror;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.WebDriverManagerException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static coyote.wdm.WebDriverManager.chromedriver;
import static org.junit.Assert.assertTrue;

public class TaobaoTest {

  @Test
  public void testTaobao() throws Exception {
    chromedriver().config().setAvoidBrowserDetection(true).setChromeDriverMirrorUrl(new URL("http://npm.taobao.org/mirrors/chromedriver/"));
    chromedriver().useMirror().forceDownload().setup();

    File binary = new File(chromedriver().getBinaryPath());
    assertTrue(binary.exists());
  }

  @Ignore("Flaky test due to cnpmjs.org")
  @Test
  public void testOtherMirrorUrl() throws Exception {
    chromedriver().config().setAvoidBrowserDetection(true).setChromeDriverMirrorUrl(new URL("https://cnpmjs.org/mirrors/chromedriver/"));
    chromedriver().useMirror().forceDownload().setup();

    File binary = new File(chromedriver().getBinaryPath());
    assertTrue(binary.exists());
  }

  @Test(expected = WebDriverManagerException.class)
  public void testTaobaoException() {
    WebDriverManager.edgedriver().useMirror().setup();
    File binary = new File(WebDriverManager.edgedriver().getBinaryPath());
    assertTrue(binary.exists());
  }

}
