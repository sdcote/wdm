package coyote.wdm.interactive;

import coyote.wdm.versions.Shell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

import static coyote.wdm.config.DriverManagerType.CHROME;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ShellTest {

  @Parameter(0)
  public String output;

  @Parameter(1)
  public String version;

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{{
            "Chromium 73.0.3683.86 Built on Ubuntu , running on Ubuntu 16.04",
            "73"},
            {"Chromium 74.0.3729.169 Built on Ubuntu , running on Ubuntu 18.04",
                    "74"},
            {"Google Chrome 75.0.3770.80", "75"}});
  }

  @Test
  public void versionFromPosixOutputTest() {
    String versionFromPosixOutput = Shell.getVersionFromPosixOutput(output,
            CHROME.toString());
    assertEquals(version, versionFromPosixOutput);
  }

}
