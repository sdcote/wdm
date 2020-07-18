package coyote.wdm.managers;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.DriverManagerType;

import javax.xml.namespace.NamespaceContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static coyote.wdm.config.DriverManagerType.SELENIUM_SERVER_STANDALONE;
import static java.util.Optional.empty;


public class SeleniumServerStandaloneManager extends WebDriverManager {

  @Override
  public DriverManagerType getDriverManagerType() {
    return SELENIUM_SERVER_STANDALONE;
  }

  @Override
  protected String getDriverName() {
    return "selenium-server-standalone";
  }

  @Override
  protected String getDriverVersion() {
    return config().getSeleniumServerStandaloneVersion();
  }

  @Override
  protected URL getDriverUrl() {
    return config().getSeleniumServerStandaloneUrl();
  }

  @Override
  protected Optional<URL> getMirrorUrl() {
    return empty();
  }

  @Override
  protected Optional<String> getExportParameter() {
    return empty();
  }

  @Override
  protected void setDriverVersion(String driverVersion) {
    config().setSeleniumServerStandaloneVersion(driverVersion);
  }

  @Override
  protected void setDriverUrl(URL url) {
    config().setSeleniumServerStandaloneUrl(url);
  }

  @Override
  protected File postDownload(File archive) {
    return archive;
  }

  @Override
  protected Optional<String> getBrowserVersionFromTheShell() {
    return empty();
  }

  @Override
  protected List<URL> getDriverUrls() throws IOException {
    return getDriversFromXml(getDriverUrl(), "//s3:Contents/s3:Key", getS3NamespaceContext());
  }

  @Override
  protected Optional<String> getDriverVersionFromRepository(Optional<String> driverVersion) {
    return empty();
  }

  @Override
  protected String getBrowserVersion() {
    return "";
  }

  @Override
  protected void setBrowserVersion(String browserVersion) {
    // Nothing required
  }

  @Override
  protected NamespaceContext getNamespaceContext() {
    return S3_NAMESPACE_CONTEXT;
  }

}
