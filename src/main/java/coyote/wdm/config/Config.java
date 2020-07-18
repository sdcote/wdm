package coyote.wdm.config;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

import static coyote.wdm.config.OperatingSystem.*;
import static java.lang.String.join;
import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.commons.lang3.SystemUtils.*;
import static org.slf4j.LoggerFactory.getLogger;

public class Config {

  final Logger log = getLogger(lookup().lookupClass());

  static final String HOME = "~";

  ConfigKey<String> properties = new ConfigKey<>("wdm.properties", String.class, "webdrivermanager.properties");
  ConfigKey<String> cachePath = new ConfigKey<>("wdm.cachePath", String.class);
  ConfigKey<Boolean> forceDownload = new ConfigKey<>("wdm.forceDownload", Boolean.class);
  ConfigKey<Boolean> useMirror = new ConfigKey<>("wdm.useMirror", Boolean.class);
  ConfigKey<Boolean> useBetaVersions = new ConfigKey<>("wdm.useBetaVersions", Boolean.class);
  ConfigKey<Boolean> avoidExport = new ConfigKey<>("wdm.avoidExport", Boolean.class);
  ConfigKey<Boolean> avoidOutputTree = new ConfigKey<>("wdm.avoidOutputTree", Boolean.class);
  ConfigKey<Boolean> avoidBrowserDetection = new ConfigKey<>("wdm.avoidBrowserDetection", Boolean.class);
  ConfigKey<Boolean> avoidAutoReset = new ConfigKey<>("wdm.avoidAutoReset", Boolean.class);
  ConfigKey<Boolean> avoidFallback = new ConfigKey<>("wdm.avoidFallback", Boolean.class);
  ConfigKey<Boolean> avoidResolutionCache = new ConfigKey<>("wdm.avoidResolutionCache", Boolean.class);
  ConfigKey<Boolean> avoidReadReleaseFromRepository = new ConfigKey<>("wdm.avoidReadReleaseFromRepository", Boolean.class);
  ConfigKey<Integer> timeout = new ConfigKey<>("wdm.timeout", Integer.class);
  ConfigKey<Boolean> versionsPropertiesOnlineFirst = new ConfigKey<>("wdm.versionsPropertiesOnlineFirst", Boolean.class);
  ConfigKey<URL> versionsPropertiesUrl = new ConfigKey<>("wdm.versionsPropertiesUrl", URL.class);
  ConfigKey<Boolean> clearResolutionCache = new ConfigKey<>("wdm.clearResolutionCache", Boolean.class);
  ConfigKey<String> architecture = new ConfigKey<>("wdm.architecture", String.class, defaultArchitecture());
  ConfigKey<String> os = new ConfigKey<>("wdm.os", String.class, defaultOsName());
  ConfigKey<String> proxy = new ConfigKey<>("wdm.proxy", String.class);
  ConfigKey<String> proxyUser = new ConfigKey<>("wdm.proxyUser", String.class);
  ConfigKey<String> proxyPass = new ConfigKey<>("wdm.proxyPass", String.class);
  ConfigKey<String> ignoreVersions = new ConfigKey<>("wdm.ignoreVersions", String.class);
  ConfigKey<String> gitHubTokenName = new ConfigKey<>("wdm.gitHubTokenName", String.class);
  ConfigKey<String> gitHubTokenSecret = new ConfigKey<>("wdm.gitHubTokenSecret", String.class);
  ConfigKey<String> localRepositoryUser = new ConfigKey<>("wdm.localRepositoryUser", String.class);
  ConfigKey<String> localRepositoryPassword = new ConfigKey<>("wdm.localRepositoryPassword", String.class);
  ConfigKey<String> chromeDriverVersion = new ConfigKey<>("wdm.chromeDriverVersion", String.class);
  ConfigKey<String> chromeVersion = new ConfigKey<>("wdm.chromeVersion", String.class);
  ConfigKey<String> chromeDriverExport = new ConfigKey<>("wdm.chromeDriverExport", String.class);
  ConfigKey<URL> chromeDriverUrl = new ConfigKey<>("wdm.chromeDriverUrl", URL.class);
  ConfigKey<URL> chromeDriverMirrorUrl = new ConfigKey<>("wdm.chromeDriverMirrorUrl", URL.class);
  ConfigKey<String> chromeDownloadUrlPattern = new ConfigKey<>("wdm.chromeDownloadUrlPattern", String.class);
  ConfigKey<String> edgeDriverVersion = new ConfigKey<>("wdm.edgeDriverVersion", String.class);
  ConfigKey<String> edgeVersion = new ConfigKey<>("wdm.edgeVersion", String.class);
  ConfigKey<String> edgeDriverExport = new ConfigKey<>("wdm.edgeDriverExport", String.class);
  ConfigKey<URL> edgeDriverUrl = new ConfigKey<>("wdm.edgeDriverUrl", URL.class);
  ConfigKey<String> edgeDownloadUrlPattern = new ConfigKey<>("wdm.edgeDownloadUrlPattern", String.class);

  ConfigKey<String> geckoDriverVersion = new ConfigKey<>("wdm.geckoDriverVersion", String.class);
  ConfigKey<String> firefoxVersion = new ConfigKey<>("wdm.firefoxVersion", String.class);
  ConfigKey<String> firefoxDriverExport = new ConfigKey<>("wdm.geckoDriverExport", String.class);
  ConfigKey<URL> firefoxDriverUrl = new ConfigKey<>("wdm.geckoDriverUrl", URL.class);
  ConfigKey<URL> firefoxDriverMirrorUrl = new ConfigKey<>("wdm.geckoDriverMirrorUrl", URL.class);

  ConfigKey<String> internetExplorerDriverVersion = new ConfigKey<>("wdm.internetExplorerDriverVersion", String.class);
  ConfigKey<String> internetExplorerDriverExport = new ConfigKey<>("wdm.internetExplorerDriverExport", String.class);
  ConfigKey<URL> internetExplorerDriverUrl = new ConfigKey<>("wdm.internetExplorerDriverUrl", URL.class);

  ConfigKey<String> operaDriverVersion = new ConfigKey<>("wdm.operaDriverVersion", String.class);
  ConfigKey<String> operaVersion = new ConfigKey<>("wdm.operaVersion", String.class);
  ConfigKey<String> operaDriverExport = new ConfigKey<>("wdm.operaDriverExport", String.class);
  ConfigKey<URL> operaDriverUrl = new ConfigKey<>("wdm.operaDriverUrl", URL.class);
  ConfigKey<URL> operaDriverMirrorUrl = new ConfigKey<>("wdm.operaDriverMirrorUrl", URL.class);

  ConfigKey<String> phantomjsDriverVersion = new ConfigKey<>("wdm.phantomjsDriverVersion", String.class);
  ConfigKey<String> phantomjsDriverExport = new ConfigKey<>("wdm.phantomjsDriverExport", String.class);
  ConfigKey<URL> phantomjsDriverUrl = new ConfigKey<>("wdm.phantomjsDriverUrl", URL.class);
  ConfigKey<URL> phantomjsDriverMirrorUrl = new ConfigKey<>("wdm.phantomjsDriverMirrorUrl", URL.class);

  ConfigKey<String> chromiumDriverVersion = new ConfigKey<>("wdm.chromiumDriverVersion", String.class);
  ConfigKey<String> chromiumVersion = new ConfigKey<>("wdm.chromiumVersion", String.class);
  ConfigKey<String> chromiumDriverSnapPath = new ConfigKey<>("wdm.chromiumDriverSnapPath", String.class);
  ConfigKey<String> seleniumServerStandaloneVersion = new ConfigKey<>("wdm.seleniumServerStandaloneVersion", String.class);
  ConfigKey<URL> seleniumServerStandaloneUrl = new ConfigKey<>("wdm.seleniumServerStandaloneUrl", URL.class);
  ConfigKey<Integer> serverPort = new ConfigKey<>("wdm.serverPort", Integer.class);
  ConfigKey<String> binaryPath = new ConfigKey<>("wdm.binaryPath", String.class);
  ConfigKey<Integer> ttl = new ConfigKey<>("wdm.ttl", Integer.class);
  ConfigKey<Integer> ttlForBrowsers = new ConfigKey<>("wdm.ttlForBrowsers", Integer.class);
  ConfigKey<String> resolutionCache = new ConfigKey<>("wdm.resolutionCache", String.class);

  private <T> T resolve(ConfigKey<T> configKey) {
    String name = configKey.getName();
    T tValue = configKey.getValue();
    Class<T> type = configKey.getType();
    return resolver(name, tValue, type);
  }

  private <T> T resolver(String name, T tValue, Class<T> type) {
    String strValue;
    strValue = System.getenv(name.toUpperCase().replace(".", "_"));
    if (strValue == null) {
      strValue = System.getProperty(name);
    }
    if (strValue == null && tValue != null) {
      return tValue;
    }
    if (strValue == null) {
      strValue = getProperty(name);
    }
    return parse(type, strValue);
  }

  @SuppressWarnings("unchecked")
  private <T> T parse(Class<T> type, String strValue) {
    T output = null;
    if (type.equals(String.class)) {
      output = (T) strValue;
    } else if (type.equals(Integer.class)) {
      output = (T) Integer.valueOf(strValue);
    } else if (type.equals(Boolean.class)) {
      output = (T) Boolean.valueOf(strValue);
    } else if (type.equals(URL.class)) {
      try {
        output = (T) new URL(strValue);
      } catch (Exception e) {
        throw new WebDriverManagerException(e);
      }
    } else {
      throw new WebDriverManagerException("Type " + type.getTypeName() + " cannot be parsed");
    }
    return output;
  }

  private String getProperty(String key) {
    String value = null;
    String propertiesValue = "/" + getProperties();
    String defaultProperties = "/webdrivermanager.properties";
    try {
      value = getPropertyFrom(propertiesValue, key);
      if (value == null) {
        value = getPropertyFrom(defaultProperties, key);
      }
    } finally {
      if (value == null) {
        value = "";
      }
    }
    return value;
  }

  private String getPropertyFrom(String properties, String key) {
    Properties props = new Properties();
    try (InputStream inputStream = Config.class.getResourceAsStream(properties)) {
      props.load(inputStream);
    } catch (IOException e) {
      log.trace("Property {} not found in {}", key, properties);
    }
    return props.getProperty(key);
  }

  public void reset() {
    for (Field field : this.getClass().getDeclaredFields()) {
      if (field.getType() == ConfigKey.class) {
        try {
          ((ConfigKey<?>) field.get(this)).reset();
        } catch (Exception e) {
          log.warn("Exception resetting {}", field);
        }
      }
    }
  }

  private String defaultOsName() {
    String osName = System.getProperty("os.name").toLowerCase();
    if (IS_OS_WINDOWS) {
      osName = WIN.name();
    } else if (IS_OS_LINUX) {
      osName = LINUX.name();
    } else if (IS_OS_MAC) {
      osName = MAC.name();
    }
    return osName;
  }

  private String defaultArchitecture() {
    return "X" + System.getProperty("sun.arch.data.model");
  }

  public static boolean isNullOrEmpty(String string) {
    return string == null || string.isEmpty();
  }

  public boolean isExecutable(File file) {
    return resolve(os).equalsIgnoreCase("win")            ? file.getName().toLowerCase().endsWith(".exe")            : file.canExecute();
  }

  // Getters and setters

  public String getProperties() {
    return resolve(properties);
  }

  public Config setProperties(String properties) {
    this.properties.setValue(properties);
    return this;
  }

  public String getCachePath() {
    String resolved = resolve(cachePath);
    String path = null;

    if (resolved != null) {
      path = resolved;
      // Partial support for Bash tilde expansion:
      // http://www.gnu.org/software/bash/manual/html_node/Tilde-Expansion.html
      if (path.startsWith(HOME + '/')) {
        path = Paths.get(System.getProperty("user.home"), path.substring(1)).toString();
      } else if (path.equals(".")) {
        path = Paths.get("").toAbsolutePath().toString();
      }
    }
    return path;
  }

  public Config setCachePath(String value) {
    this.cachePath.setValue(value);
    return this;
  }

  public boolean isForceDownload() {
    return resolve(forceDownload);
  }

  public Config setForceDownload(boolean value) {
    this.forceDownload.setValue(value);
    return this;
  }

  public boolean isUseMirror() {
    return resolve(useMirror);
  }

  public Config setUseMirror(boolean value) {
    this.useMirror.setValue(value);
    return this;
  }

  public boolean isUseBetaVersions() {
    return resolve(useBetaVersions);
  }

  public Config setUseBetaVersions(boolean value) {
    this.useBetaVersions.setValue(value);
    return this;
  }

  public boolean isAvoidExport() {
    return resolve(avoidExport);
  }

  public Config setAvoidExport(boolean value) {
    this.avoidExport.setValue(value);
    return this;
  }

  public boolean isAvoidOutputTree() {
    return resolve(avoidOutputTree);
  }

  public Config setAvoidOutputTree(boolean value) {
    this.avoidOutputTree.setValue(value);
    return this;
  }

  public boolean isAvoidBrowserDetection() {
    return resolve(avoidBrowserDetection);
  }

  public Config setAvoidBrowserDetection(boolean value) {
    this.avoidBrowserDetection.setValue(value);
    return this;
  }

  public boolean isAvoidAutoReset() {
    return resolve(avoidAutoReset);
  }

  public Config setAvoidAutoReset(boolean value) {
    this.avoidAutoReset.setValue(value);
    return this;
  }

  public boolean isAvoidFallback() {
    return resolve(avoidFallback);
  }

  public Config setAvoidFallback(boolean value) {
    this.avoidFallback.setValue(value);
    return this;
  }

  public boolean isAvoidingResolutionCache() {
    return resolve(avoidResolutionCache);
  }

  public Config setAvoidResolutionCache(boolean value) {
    this.avoidResolutionCache.setValue(value);
    return this;
  }

  public boolean isAvoidReadReleaseFromRepository() {
    return resolve(avoidReadReleaseFromRepository);
  }

  public Config setAvoidReadReleaseFromRepository(boolean value) {
    this.avoidReadReleaseFromRepository.setValue(value);
    return this;
  }

  public int getTimeout() {
    return resolve(timeout);
  }

  public Config setTimeout(int value) {
    this.timeout.setValue(value);
    return this;
  }

  public boolean getVersionsPropertiesOnlineFirst() {
    return resolve(versionsPropertiesOnlineFirst);
  }

  public Config setVersionsPropertiesOnlineFirst(boolean value) {
    this.versionsPropertiesOnlineFirst.setValue(value);
    return this;
  }

  public URL getVersionsPropertiesUrl() {
    return resolve(versionsPropertiesUrl);
  }

  public Config setVersionsPropertiesUrl(URL value) {
    this.versionsPropertiesUrl.setValue(value);
    return this;
  }

  public boolean getClearingResolutionCache() {
    return resolve(clearResolutionCache);
  }

  public Config setClearResolutionCache(Boolean value) {
    this.clearResolutionCache.setValue(value);
    return this;
  }

  public Architecture getArchitecture() {
    String architectureString = resolve(architecture);
    if ("32".equals(architectureString)) {
      return Architecture.X32;
    }
    if ("64".equals(architectureString)) {
      return Architecture.X64;
    }
    return Architecture.valueOf(architectureString);
  }

  public Config setArchitecture(Architecture value) {
    this.architecture.setValue(value.name());
    return this;
  }

  public String getOs() {
    return resolve(os);
  }

  public Config setOs(String value) {
    this.os.setValue(value);
    return this;
  }

  public String getProxy() {
    return resolve(proxy);
  }

  public Config setProxy(String value) {
    this.proxy.setValue(value);
    return this;
  }

  public String getProxyUser() {
    return resolve(proxyUser);
  }

  public Config setProxyUser(String value) {
    this.proxyUser.setValue(value);
    return this;
  }

  public String getProxyPass() {
    return resolve(proxyPass);
  }

  public Config setProxyPass(String value) {
    this.proxyPass.setValue(value);
    return this;
  }

  public String[] getIgnoreVersions() {
    String ignored = resolve(ignoreVersions);
    String[] out = {};
    if (!isNullOrEmpty(ignored)) {
      out = ignored.split(",");
    }
    return out;
  }

  public Config setIgnoreVersions(String... value) {
    this.ignoreVersions.setValue(join(",", value));
    return this;
  }

  public String getGitHubTokenName() {
    return resolve(gitHubTokenName);
  }

  public Config setGitHubTokenName(String value) {
    this.gitHubTokenName.setValue(value);
    return this;
  }

  public String getGitHubTokenSecret() {
    return resolve(gitHubTokenSecret);
  }

  public Config setGitHubTokenSecret(String value) {
    this.gitHubTokenSecret.setValue(value);
    return this;
  }

  public String getLocalRepositoryUser() {
    return resolve(localRepositoryUser);
  }

  public Config setLocalRepositoryUser(String value) {
    this.localRepositoryUser.setValue(value);
    return this;
  }

  public String getLocalRepositoryPassword() {
    return resolve(localRepositoryPassword);
  }

  public Config setLocalRepositoryPassword(String value) {
    this.localRepositoryPassword.setValue(value);
    return this;
  }

  public int getServerPort() {
    return resolve(serverPort);
  }

  public Config setServerPort(int value) {
    this.serverPort.setValue(value);
    return this;
  }

  public int getTtl() {
    return resolve(ttl);
  }

  public Config setTtl(int value) {
    this.ttl.setValue(value);
    return this;
  }

  public int getTtlForBrowsers() {
    return resolve(ttlForBrowsers);
  }

  public Config setTtlForBrowsers(int value) {
    this.ttlForBrowsers.setValue(value);
    return this;
  }

  public String getResolutionCache() {
    return resolve(resolutionCache);
  }

  public Config setResolutionCache(String value) {
    this.resolutionCache.setValue(value);
    return this;
  }

  public String getBinaryPath() {
    return resolve(binaryPath);
  }

  public Config setBinaryPath(String value) {
    this.binaryPath.setValue(value);
    return this;
  }

  public String getChromeDriverVersion() {
    return resolve(chromeDriverVersion);
  }

  public Config setChromeDriverVersion(String value) {
    this.chromeDriverVersion.setValue(value);
    return this;
  }

  public String getChromeVersion() {
    return resolve(chromeVersion);
  }

  public Config setChromeVersion(String value) {
    this.chromeVersion.setValue(value);
    return this;
  }

  public String getChromeDriverExport() {
    return resolve(chromeDriverExport);
  }

  public Config setChromeDriverExport(String value) {
    this.chromeDriverExport.setValue(value);
    return this;
  }

  public URL getChromeDriverUrl() {
    return resolve(chromeDriverUrl);
  }

  public Config setChromeDriverUrl(URL value) {
    this.chromeDriverUrl.setValue(value);
    return this;
  }

  public URL getChromeDriverMirrorUrl() {
    return resolve(chromeDriverMirrorUrl);
  }

  public Config setChromeDriverMirrorUrl(URL value) {
    this.chromeDriverMirrorUrl.setValue(value);
    return this;
  }

  public String getChromeDownloadUrlPattern() {
    return resolve(chromeDownloadUrlPattern);
  }

  public Config setChromeDownloadUrlPattern(String value) {
    this.chromeDownloadUrlPattern.setValue(value);
    return this;
  }

  public String getEdgeDriverVersion() {
    return resolve(edgeDriverVersion);
  }

  public Config setEdgeDriverVersion(String value) {
    this.edgeDriverVersion.setValue(value);
    return this;
  }

  public String getEdgeVersion() {
    return resolve(edgeVersion);
  }

  public Config setEdgeVersion(String value) {
    this.edgeVersion.setValue(value);
    return this;
  }

  public String getEdgeDriverExport() {
    return resolve(edgeDriverExport);
  }

  public Config setEdgeDriverExport(String value) {
    this.edgeDriverExport.setValue(value);
    return this;
  }

  public URL getEdgeDriverUrl() {
    return resolve(edgeDriverUrl);
  }

  public Config setEdgeDriverUrl(URL value) {
    this.edgeDriverUrl.setValue(value);
    return this;
  }

  public String getEdgeDownloadUrlPattern() {
    return resolve(edgeDownloadUrlPattern);
  }

  public Config setEdgeDownloadUrlPattern(String value) {
    this.edgeDownloadUrlPattern.setValue(value);
    return this;
  }

  public String getGeckoDriverVersion() {
    return resolve(geckoDriverVersion);
  }

  public Config setGeckoDriverVersion(String value) {
    this.geckoDriverVersion.setValue(value);
    return this;
  }

  public String getFirefoxVersion() {
    return resolve(firefoxVersion);
  }

  public Config setFirefoxVersion(String value) {
    this.firefoxVersion.setValue(value);
    return this;
  }

  public String getFirefoxDriverExport() {
    return resolve(firefoxDriverExport);
  }

  public Config setFirefoxDriverExport(String value) {
    this.firefoxDriverExport.setValue(value);
    return this;
  }

  public URL getFirefoxDriverUrl() {
    return resolve(firefoxDriverUrl);
  }

  public Config setFirefoxDriverUrl(URL value) {
    this.firefoxDriverUrl.setValue(value);
    return this;
  }

  public URL getFirefoxDriverMirrorUrl() {
    return resolve(firefoxDriverMirrorUrl);
  }

  public Config setFirefoxDriverMirrorUrl(URL value) {
    this.firefoxDriverMirrorUrl.setValue(value);
    return this;
  }

  public String getInternetExplorerDriverVersion() {
    return resolve(internetExplorerDriverVersion);
  }

  public Config setInternetExplorerDriverVersion(String value) {
    this.internetExplorerDriverVersion.setValue(value);
    return this;
  }

  public String getInternetExplorerDriverExport() {
    return resolve(internetExplorerDriverExport);
  }

  public Config setInternetExplorerDriverExport(String value) {
    this.internetExplorerDriverExport.setValue(value);
    return this;
  }

  public URL getInternetExplorerDriverUrl() {
    return resolve(internetExplorerDriverUrl);
  }

  public Config setInternetExplorerDriverUrl(URL value) {
    this.internetExplorerDriverUrl.setValue(value);
    return this;
  }

  public String getOperaDriverVersion() {
    return resolve(operaDriverVersion);
  }

  public Config setOperaDriverVersion(String value) {
    this.operaDriverVersion.setValue(value);
    return this;
  }

  public String getOperaVersion() {
    return resolve(operaVersion);
  }

  public Config setOperaVersion(String value) {
    this.operaVersion.setValue(value);
    return this;
  }

  public String getOperaDriverExport() {
    return resolve(operaDriverExport);
  }

  public Config setOperaDriverExport(String value) {
    this.operaDriverExport.setValue(value);
    return this;
  }

  public URL getOperaDriverUrl() {
    return resolve(operaDriverUrl);
  }

  public Config setOperaDriverUrl(URL value) {
    this.operaDriverUrl.setValue(value);
    return this;
  }

  public URL getOperaDriverMirrorUrl() {
    return resolve(operaDriverMirrorUrl);
  }

  public Config setOperaDriverMirrorUrl(URL value) {
    this.operaDriverMirrorUrl.setValue(value);
    return this;
  }

  public String getPhantomjsDriverVersion() {
    return resolve(phantomjsDriverVersion);
  }

  public Config setPhantomjsDriverVersion(String value) {
    this.phantomjsDriverVersion.setValue(value);
    return this;
  }

  public String getPhantomjsDriverExport() {
    return resolve(phantomjsDriverExport);
  }

  public Config setPhantomjsDriverExport(String value) {
    this.phantomjsDriverExport.setValue(value);
    return this;
  }

  public URL getPhantomjsDriverUrl() {
    return resolve(phantomjsDriverUrl);
  }

  public Config setPhantomjsDriverUrl(URL value) {
    this.phantomjsDriverUrl.setValue(value);
    return this;
  }

  public URL getPhantomjsDriverMirrorUrl() {
    return resolve(phantomjsDriverMirrorUrl);
  }

  public Config setPhantomjsDriverMirrorUrl(URL value) {
    this.phantomjsDriverMirrorUrl.setValue(value);
    return this;
  }

  public String getChromiumDriverVersion() {
    return resolve(chromiumDriverVersion);
  }

  public Config setChromiumDriverVersion(String value) {
    this.chromiumDriverVersion.setValue(value);
    return this;
  }

  public String getChromiumVersion() {
    return resolve(chromiumVersion);
  }

  public Config setChromiumVersion(String value) {
    this.chromiumVersion.setValue(value);
    return this;
  }

  public String getChromiumDriverSnapPath() {
    return resolve(chromiumDriverSnapPath);
  }

  public Config setChromiumDriverSnapPath(String value) {
    this.chromiumDriverSnapPath.setValue(value);
    return this;
  }

  public String getSeleniumServerStandaloneVersion() {
    return resolve(seleniumServerStandaloneVersion);
  }

  public Config setSeleniumServerStandaloneVersion(String value) {
    this.seleniumServerStandaloneVersion.setValue(value);
    return this;
  }

  public URL getSeleniumServerStandaloneUrl() {
    return resolve(seleniumServerStandaloneUrl);
  }

  public Config setSeleniumServerStandaloneUrl(URL value) {
    this.seleniumServerStandaloneUrl.setValue(value);
    return this;
  }

}
