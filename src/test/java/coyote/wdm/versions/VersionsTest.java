package coyote.wdm.versions;

import coyote.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;

import static coyote.wdm.WebDriverManager.*;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(Parameterized.class)
public class VersionsTest {

  final Logger log = getLogger(lookup().lookupClass());

  @Parameter
  public WebDriverManager driverManager;

  @Parameters(name = "{index}: {0}")
  public static Collection<Object[]> data() {
    return asList(new Object[][]{
            {chromedriver()}, {firefoxdriver()},
            {operadriver()}, {edgedriver()}, {iedriver()},
            {phantomjs()}});

  }

  @Test
  public void testChromeDriverVersions() {
    List<String> versions = driverManager.getDriverVersions();
    log.debug("Versions of {} {}", driverManager.getClass().getSimpleName(), versions);
    assertThat(versions, notNullValue());
    assertThat(versions, not(empty()));
  }

}
