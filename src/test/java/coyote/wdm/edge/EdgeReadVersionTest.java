package coyote.wdm.edge;

import coyote.wdm.WebDriverManager;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class EdgeReadVersionTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Test
  public void edgeVersionsTest() {
    String[] expectedVersions = {"75.0.139.20", "76.0.183.0", "77.0.237.0",
            "78.0.277.0", "79.0.313.0", "80.0.361.111", "81.0.409.0",
            "82.0.459.1"};
    List<String> driverVersions = WebDriverManager.edgedriver()
            .getDriverVersions();

    log.debug("Expected edge versions: {}",
            Arrays.asList(expectedVersions));
    log.debug("Edge versions read from the repository: {}", driverVersions);

    assertThat(driverVersions, hasItems(expectedVersions));
  }

}
