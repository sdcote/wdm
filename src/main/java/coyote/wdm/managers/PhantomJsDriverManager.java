package coyote.wdm.managers;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.DriverManagerType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static coyote.wdm.config.DriverManagerType.PHANTOMJS;
import static coyote.wdm.online.UrlHandler.BETA;
import static java.io.File.separator;
import static java.util.Optional.empty;

public class PhantomJsDriverManager extends WebDriverManager {

  @Override
  public DriverManagerType getDriverManagerType() {
    return PHANTOMJS;
  }

  @Override
  protected String getDriverName() {
    return "phantomjs";
  }

  @Override
  protected String getDriverVersion() {
    return config().getPhantomjsDriverVersion();
  }

  @Override
  protected URL getDriverUrl() {
    return getDriverUrlCkeckingMirror(config().getPhantomjsDriverUrl());
  }

  @Override
  protected Optional<URL> getMirrorUrl() {
    return Optional.of(config().getPhantomjsDriverMirrorUrl());
  }

  @Override
  protected Optional<String> getExportParameter() {
    return Optional.of(config().getPhantomjsDriverExport());
  }

  @Override
  protected void setDriverVersion(String driverVersion) {
    config().setPhantomjsDriverVersion(driverVersion);
  }

  @Override
  protected void setDriverUrl(URL url) {
    config().setPhantomjsDriverUrl(url);
  }

  @Override
  protected List<URL> getDriverUrls() throws IOException {
    return getDriversFromBitBucket();
  }

  @Override
  protected String getCurrentVersion(URL url) {
    String driverName = getDriverName();
    String file = url.getFile();
    file = url.getFile().substring(file.lastIndexOf(SLASH), file.length());
    int matchIndex = file.indexOf(driverName);
    String currentVersion = file.substring(matchIndex + driverName.length() + 1);
    int dashIndex = currentVersion.indexOf('-');

    if (dashIndex != -1) {
      String beta = currentVersion.substring(dashIndex + 1, dashIndex + 1 + BETA.length());
      if (beta.equalsIgnoreCase(BETA)) {
        dashIndex = currentVersion.indexOf('-', dashIndex + 1);
      }
      currentVersion = dashIndex != -1 ? currentVersion.substring(0, dashIndex) : "";
    } else {
      currentVersion = "";
    }

    return currentVersion;
  }

  @Override
  protected String preDownload(String target, String driverVersion) {
    int iSeparator = target.indexOf(driverVersion) - 1;
    int iDash = target.lastIndexOf(driverVersion) + driverVersion.length();
    int iPoint = target.lastIndexOf(".tar") != -1 ? target.lastIndexOf(".tar") : target.lastIndexOf(".zip");
    target = target.substring(0, iSeparator + 1) + target.substring(iDash + 1, iPoint) + target.substring(iSeparator);
    target = target.replace("beta-", "");
    return target;
  }

  @Override
  protected File postDownload(File archive) {
    log.trace("PhantomJS package name: {}", archive);

    File extractFolder = archive.getParentFile().listFiles(getFolderFilter())[0];
    log.trace("PhantomJS extract folder (to be deleted): {}", extractFolder);

    File binFolder = new File(extractFolder.getAbsoluteFile() + separator + "bin");
    // Exception for older versions of PhantomJS
    int binaryIndex = 0;
    if (!binFolder.exists()) {
      binFolder = extractFolder;
      binaryIndex = 3;
    }

    log.trace("PhantomJS bin folder: {} (index {})", binFolder, binaryIndex);

    File phantomjs = binFolder.listFiles()[binaryIndex];
    log.trace("PhantomJS binary: {}", phantomjs);

    File target = new File(archive.getParentFile().getAbsolutePath(), phantomjs.getName());
    log.trace("PhantomJS target: {}", target);

    downloader.renameFile(phantomjs, target);
    downloader.deleteFolder(extractFolder);
    return target;
  }

  @Override
  protected Optional<String> getBrowserVersionFromTheShell() {
    return empty();
  }

  @Override
  protected Optional<String> getDriverVersionFromRepository(
          Optional<String> driverVersion) {
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
