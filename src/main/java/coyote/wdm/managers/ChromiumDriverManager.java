package coyote.wdm.managers;


import coyote.wdm.config.DriverManagerType;

import java.util.Optional;

import static coyote.wdm.config.DriverManagerType.CHROMIUM;


public class ChromiumDriverManager extends ChromeDriverManager {

  @Override
  public DriverManagerType getDriverManagerType() {
    return CHROMIUM;
  }

  @Override
  protected String getDriverVersion() {
    return config().getChromiumDriverVersion();
  }

  @Override
  protected String getBrowserVersion() {
    return config().getChromiumVersion();
  }

  @Override
  protected void setDriverVersion(String driverVersion) {
    config().setChromiumDriverVersion(driverVersion);
  }

  @Override
  protected void setBrowserVersion(String browserVersion) {
    config().setChromiumVersion(browserVersion);
  }

  @Override
  protected Optional<String> getBrowserVersionFromTheShell() {
    String[] programFilesEnvs = {"LOCALAPPDATA", getOtherProgramFilesEnv(), getProgramFilesEnv()};
    String[] winBrowserNames = {"\\\\Chromium\\\\Application\\\\chrome.exe"};
    return versionDetector.getDefaultBrowserVersion(programFilesEnvs, winBrowserNames, "chromium-browser", "/Applications/Chromium.app/Contents/MacOS/Chromium", "--version", getDriverManagerType().toString());
  }

}
