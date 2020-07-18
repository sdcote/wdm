package coyote.wdm.managers;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.DriverManagerType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

public class VoidDriverManager extends WebDriverManager {

  @Override
  protected List<URL> getDriverUrls() throws IOException {
    return emptyList();
  }

  @Override
  protected Optional<String> getBrowserVersionFromTheShell() {
    return empty();
  }

  @Override
  protected String getDriverVersion() {
    return "";
  }

  @Override
  protected URL getDriverUrl() {
    return null;
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
  public DriverManagerType getDriverManagerType() {
    return null;
  }

  @Override
  protected String getDriverName() {
    return "";
  }

  @Override
  protected void setDriverVersion(String driverVersion) {
    // Nothing required
  }

  @Override
  protected void setDriverUrl(URL url) {
    // Nothing required
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

}
