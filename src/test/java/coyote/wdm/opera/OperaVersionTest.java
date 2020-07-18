package coyote.wdm.opera;

import coyote.wdm.WebDriverManager;
import coyote.wdm.base.VersionTestParent;
import org.junit.Before;


public class OperaVersionTest extends VersionTestParent {

  @Before
  public void setup() {
    browserManager = WebDriverManager.operadriver();
    specificVersions = new String[]{"0.2.2", "2.32"};
  }

}
