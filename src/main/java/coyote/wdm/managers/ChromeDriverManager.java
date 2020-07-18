package coyote.wdm.managers;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.DriverManagerType;

import javax.xml.namespace.NamespaceContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static coyote.wdm.config.DriverManagerType.CHROME;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;

public class ChromeDriverManager extends WebDriverManager {

  @Override
  public DriverManagerType getDriverManagerType() {
    return CHROME;
  }

  @Override
  protected String getDriverName() {
    return "chromedriver";
  }

  @Override
  protected String getDriverVersion() {
    return config().getChromeDriverVersion();
  }

  @Override
  protected String getBrowserVersion() {
    return config().getChromeVersion();
  }

  @Override
  protected void setDriverVersion(String driverVersion) {
    config().setChromeDriverVersion(driverVersion);
  }

  @Override
  protected void setBrowserVersion(String browserVersion) {
    config().setChromeVersion(browserVersion);
  }

  @Override
  protected URL getDriverUrl() {
    return getDriverUrlCkeckingMirror(config().getChromeDriverUrl());
  }

  @Override
  protected Optional<URL> getMirrorUrl() {
    return Optional.of(config().getChromeDriverMirrorUrl());
  }

  @Override
  protected Optional<String> getExportParameter() {
    return Optional.of(config().getChromeDriverExport());
  }

  @Override
  protected void setDriverUrl(URL url) {
    config().setChromeDriverUrl(url);
  }

  @Override
  protected List<URL> getDriverUrls() throws IOException {
    Optional<URL> mirrorUrl = getMirrorUrl();
    if (mirrorUrl.isPresent() && config().isUseMirror()) {
      return getDriversFromMirror(mirrorUrl.get());
    } else {
      return getDriversFromXml(getDriverUrl(), "//s3:Contents/s3:Key", getS3NamespaceContext());
    }
  }

  @Override
  protected String getCurrentVersion(URL url) {
    if (config().isUseMirror()) {
      int i = url.getFile().lastIndexOf(SLASH);
      int j = url.getFile().substring(0, i).lastIndexOf(SLASH) + 1;
      return url.getFile().substring(j, i);
    } else {
      return super.getCurrentVersion(url);
    }
  }

  @Override
  protected Optional<String> getBrowserVersionFromTheShell() {
    String[] programFilesEnvs = {getOtherProgramFilesEnv(), "LOCALAPPDATA", getProgramFilesEnv()};
    String[] winBrowserNames = {"\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe"};
    Optional<String> browserVersion = versionDetector.getDefaultBrowserVersion(programFilesEnvs, winBrowserNames, "google-chrome", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", "--version", getDriverManagerType().toString());

    if (IS_OS_WINDOWS && !browserVersion.isPresent()) {
      log.debug("Chrome version not discovered using wmic... trying reading the registry");
      browserVersion = versionDetector.getBrowserVersionFromWinRegistry("HKCU\\Software\\Google\\Chrome\\BLBeacon", "version");
    }

    return browserVersion;
  }

  @Override
  protected Optional<String> getLatestDriverVersionFromRepository() {
    if (config().isUseBetaVersions() || config().isAvoidReadReleaseFromRepository()) {
      return empty();
    } else {
      return getDriverVersionFromRepository(empty());
    }
  }

  @Override
  protected Charset getVersionCharset() {
    return StandardCharsets.UTF_8;
  }

  @Override
  protected NamespaceContext getNamespaceContext() {
    return S3_NAMESPACE_CONTEXT;
  }

  @Override
  protected Optional<URL> buildUrl(String driverVersion) {
    Optional<URL> optionalUrl = empty();
    if (!config().isUseMirror()) {
      String downloadUrlPattern = config().getChromeDownloadUrlPattern();
      String os = config().getOs().toLowerCase();
      String arch = os.contains("win") ? "32" : "64";
      String builtUrl = String.format(downloadUrlPattern, driverVersion, os, arch);
      log.debug("Using URL built from repository pattern: {}", builtUrl);
      try {
        optionalUrl = Optional.of(new URL(builtUrl));
      } catch (MalformedURLException e) {
        log.warn("Error building URL from pattern {} {}", builtUrl, e.getMessage());
      }
    }
    return optionalUrl;
  }

}
