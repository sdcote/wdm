package coyote.wdm.managers;

import coyote.wdm.WebDriverManager;
import coyote.wdm.config.DriverManagerType;
import coyote.wdm.config.WebDriverManagerException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static coyote.wdm.config.DriverManagerType.OPERA;
import static java.util.Optional.empty;


public class OperaDriverManager extends WebDriverManager {

  @Override
  public DriverManagerType getDriverManagerType() {
    return OPERA;
  }

  @Override
  protected String getDriverName() {
    return "operadriver";
  }

  @Override
  protected String getDriverVersion() {
    return config().getOperaDriverVersion();
  }

  @Override
  protected String getBrowserVersion() {
    return config().getOperaVersion();
  }

  @Override
  protected void setDriverVersion(String driverVersion) {
    config().setOperaDriverVersion(driverVersion);
  }

  @Override
  protected void setBrowserVersion(String browserVersion) {
    config().setOperaVersion(browserVersion);
  }

  @Override
  protected URL getDriverUrl() {
    return getDriverUrlCkeckingMirror(config().getOperaDriverUrl());
  }

  @Override
  protected Optional<URL> getMirrorUrl() {
    return Optional.of(config().getOperaDriverMirrorUrl());
  }

  @Override
  protected Optional<String> getExportParameter() {
    return Optional.of(config().getOperaDriverExport());
  }

  @Override
  protected void setDriverUrl(URL url) {
    config().setOperaDriverUrl(url);
  }

  @Override
  protected String getCurrentVersion(URL url) {
    String currentVersion;
    if (config.isUseMirror()) {
      int i = url.getFile().lastIndexOf(SLASH);
      int j = url.getFile().substring(0, i).lastIndexOf(SLASH) + 1;
      currentVersion = url.getFile().substring(j, i);
      return currentVersion;
    } else {
      currentVersion = url.getFile().substring(url.getFile().indexOf(SLASH + "v") + 2, url.getFile().lastIndexOf(SLASH));
    }
    if (currentVersion.startsWith(".")) {
      currentVersion = currentVersion.substring(1);
    }
    return currentVersion;
  }

  @Override
  protected List<URL> getDriverUrls() throws IOException {
    return getDriversFromGitHub();
  }

  @Override
  protected File postDownload(File archive) {
    log.trace("Post processing for Opera: {}", archive);

    File extractFolder = archive.getParentFile().listFiles(getFolderFilter())[0];
    if (!extractFolder.isFile()) {
      File target;
      try {
        log.trace("Opera extract folder (to be deleted): {}", extractFolder);
        File[] listFiles = extractFolder.listFiles();
        int i = 0;
        File operadriver;
        boolean isOperaDriver;
        do {
          if (i >= listFiles.length) {
            throw new WebDriverManagerException("Driver binary for Opera not found in zip file");
          }
          operadriver = listFiles[i];
          isOperaDriver = operadriver.getName().contains(getDriverName());

          i++;
          log.trace("{} is valid: {}", operadriver, isOperaDriver);
        } while (!isOperaDriver);
        log.info("Operadriver binary: {}", operadriver);

        target = new File(archive.getParentFile().getAbsolutePath(), operadriver.getName());
        log.trace("Operadriver target: {}", target);

        downloader.renameFile(operadriver, target);
      } finally {
        downloader.deleteFolder(extractFolder);
      }
      return target;
    } else {
      return super.postDownload(archive);
    }
  }

  @Override
  protected Optional<String> getBrowserVersionFromTheShell() {
    String[] programFilesEnvs = {"LOCALAPPDATA", "PROGRAMFILES"};
    String[] winBrowserNames = {"\\\\Programs\\\\Opera\\\\launcher.exe",
            "\\\\Opera\\\\launcher.exe"};
    return versionDetector.getDefaultBrowserVersion(programFilesEnvs,
            winBrowserNames, "opera",
            "/Applications/Opera.app/Contents/MacOS/Opera", "--version",
            "");
  }

  @Override
  protected Optional<String> getDriverVersionFromRepository(
          Optional<String> driverVersion) {
    return empty();
  }

}
