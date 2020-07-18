package coyote.wdm.config;

public enum DriverManagerType {

  CHROME("org.openqa.selenium.chrome.ChromeDriver"),
  FIREFOX("org.openqa.selenium.firefox.FirefoxDriver"),
  OPERA("org.openqa.selenium.opera.OperaDriver"),
  EDGE("org.openqa.selenium.edge.EdgeDriver"),
  PHANTOMJS("org.openqa.selenium.phantomjs.PhantomJSDriver"),
  IEXPLORER("org.openqa.selenium.ie.InternetExplorerDriver"),
  SELENIUM_SERVER_STANDALONE("org.openqa.selenium.remote.server.SeleniumServer"),
  CHROMIUM("org.openqa.selenium.chrome.ChromeDriver"),
  SAFARI("org.openqa.selenium.safari.SafariDriver");

  String browserClass;

  DriverManagerType(String browserClass) {
    this.browserClass = browserClass;
  }

  public String browserClass() {
    return browserClass;
  }

  @Override
  public String toString() {
    switch (this) {
      case CHROME:
        return "Chrome";
      case CHROMIUM:
        return "Chromium";
      case FIREFOX:
        return "Firefox";
      case OPERA:
        return "Opera";
      case EDGE:
        return "Edge";
      case PHANTOMJS:
        return "PhantomJS";
      case IEXPLORER:
        return "Internet Explorer";
      case SAFARI:
        return "Safari";
      case SELENIUM_SERVER_STANDALONE:
        return "Selenium Server Standalone";
      default:
        throw new WebDriverManagerException("Invalid driver manager type: " + this.name());
    }
  }

}
