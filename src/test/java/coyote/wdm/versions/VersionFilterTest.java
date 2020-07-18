package coyote.wdm.versions;

import coyote.wdm.cache.CacheHandler;
import coyote.wdm.config.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.io.File.separator;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(Parameterized.class)
public class VersionFilterTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Parameter(0)
  public String version;

  @Parameter(1)
  public int expectedVersions;

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{{"74", 1}, {"77", 1}, {"79", 2}});
  }

  @Test
  public void testFilterCacheBy() {
    CacheHandler cacheHandler = new CacheHandler(new Config());
    List<File> filteredList = cacheHandler.filterCacheBy(getInputFileList(), version, true);

    log.debug("Version {} -- Output {}", version, filteredList);
    assertEquals(filteredList.size(), expectedVersions);
  }

  private List<File> getInputFileList() {
    List<File> output = new ArrayList<>();
    File currentFolder = new File(".");
    String[] versions = {"74.0.3729.6", "75.0.3770.140", "75.0.3770.8",
            "75.0.3770.90", "76.0.3809.126", "76.0.3809.68", "77.0.3865.40",
            "78.0.3904.70", "79.0.3945.16", "79.0.3945.36"};
    for (String v : versions) {
      output.add(new File(currentFolder, v + separator + "chromedriver"));
    }
    return output;
  }

}
