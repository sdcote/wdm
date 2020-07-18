package coyote.wdm.phantomjs;

import coyote.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhantomJsBetaTest {

  @BeforeClass
  public static void setupClass() {
    WebDriverManager.phantomjs().useBetaVersions().setup();
  }

  @Test
  public void testPhantomBeta() {
    String binaryPath = WebDriverManager.phantomjs().getBinaryPath();
    assertThat(binaryPath, notNullValue());
  }
}
