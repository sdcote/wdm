package coyote.wdm.managers;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.DriverManagerType;

import javax.xml.namespace.NamespaceContext;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static coyote.wdm.config.DriverManagerType.IEXPLORER;
import static java.util.Optional.empty;


public class InternetExplorerDriverManager extends WebDriverManager {

  @Override
  public DriverManagerType getDriverManagerType() {
    return IEXPLORER;
  }

  @Override
  protected String getDriverName() {
    return "IEDriverServer";
  }

  @Override
  protected String getDriverVersion() {
    return config().getInternetExplorerDriverVersion();
  }

  @Override
  protected String getBrowserVersion() {
    return "";
  }

  @Override
  protected void setDriverVersion(String driverVersion) {
    config().setInternetExplorerDriverVersion(driverVersion);
  }

  @Override
  protected void setBrowserVersion(String browserVersion) {
    // Nothing required
  }

  @Override
  protected URL getDriverUrl() {
    return config().getInternetExplorerDriverUrl();
  }

  @Override
  protected Optional<URL> getMirrorUrl() {
    return empty();
  }

  @Override
  protected Optional<String> getExportParameter() {
    return Optional.of(config().getInternetExplorerDriverExport());
  }

  @Override
  protected void setDriverUrl(URL url) {
    config().setInternetExplorerDriverUrl(url);
  }

  @Override
  protected List<URL> getDriverUrls() throws IOException {
    return getDriversFromXml(getDriverUrl(), "//s3:Contents/s3:Key", getS3NamespaceContext());
  }

  @Override
  protected Optional<String> getBrowserVersionFromTheShell() {
    return empty();
  }

  @Override
  protected Optional<String> getDriverVersionFromRepository(Optional<String> driverVersion) {
    return empty();
  }

  @Override
  protected NamespaceContext getNamespaceContext() {
    return S3_NAMESPACE_CONTEXT;
  }

}
