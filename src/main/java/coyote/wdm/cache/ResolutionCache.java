package coyote.wdm.cache;

import coyote.wdm.config.Config;
import coyote.wdm.config.WebDriverManagerException;
import org.slf4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.invoke.MethodHandles.lookup;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.slf4j.LoggerFactory.getLogger;

public class ResolutionCache {

  static final String TTL = "-ttl";
  static final String RESOLUTION_CACHE_INFO = "WebDriverManager Resolution Cache (relationship between browsers and drivers versions previously resolved)";
  final Logger log = getLogger(lookup().lookupClass());
  Properties props = new Properties() {
    private static final long serialVersionUID = -1207802549347023842L;

    @Override
    public synchronized Enumeration<Object> keys() {
      return Collections.enumeration(new TreeSet<Object>(super.keySet()));
    }
  };

  SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy z");
  Config config;
  File resolutionCacheFile;

  public ResolutionCache(Config config) {
    this.config = config;

    // Create cache folder if not exits
    File cachePath = new File(config.getCachePath());
    if (!cachePath.exists()) {
      cachePath.mkdirs();
    }

    this.resolutionCacheFile = new File(config.getCachePath(), config.getResolutionCache());
    try {
      if (!resolutionCacheFile.exists()) {
        boolean createNewFile = resolutionCacheFile.createNewFile();
        if (createNewFile) {
          log.debug("Created new resolution cache file at {}", resolutionCacheFile);
        }
      }
      try (InputStream fis = new FileInputStream(resolutionCacheFile)) {
        props.load(fis);
      }
    } catch (Exception e) {
      throw new WebDriverManagerException("Exception reading resolution cache as a properties file", e);
    }
  }

  public String getValueFromResolutionCache(String key) {
    return props.getProperty(key, null);
  }

  private Date getExpirationDateFromResolutionCache(String key) {
    Date result = new Date(0);
    try {
      result = dateFormat.parse(props.getProperty(getExpirationKey(key)));
      return result;
    } catch (Exception e) {
      log.warn("Exception parsing date ({}) from resolution cache {}", key, e.getMessage());
    }
    return result;
  }

  public void putValueInResolutionCacheIfEmpty(String key, String value,
                                               int ttl) {
    if (ttl > 0 && getValueFromResolutionCache(key) == null) {
      props.put(key, value);
      long now = new Date().getTime();
      Date expirationDate = new Date(now + SECONDS.toMillis(ttl));
      String expirationDateStr = formatDate(expirationDate);
      props.put(getExpirationKey(key), expirationDateStr);
      if (log.isDebugEnabled()) {
        log.debug("Storing resolution {}={} in cache (valid until {})", key, value, expirationDateStr);
      }
      storeProperties();
    }
  }

  private synchronized void storeProperties() {
    try (OutputStream fos = new FileOutputStream(resolutionCacheFile)) {
      props.store(fos, RESOLUTION_CACHE_INFO);
    } catch (Exception e) {
      log.warn("Exception writing resolution cache as a properties file {}", e.getClass().getName());
    }
  }

  private void clearFromResolutionCache(String key) {
    props.remove(key);
    props.remove(getExpirationKey(key));
  }

  public void clear() {
    log.info("Clearing WebDriverManager resolution cache");
    props.clear();
    storeProperties();
  }

  private boolean checkValidity(String key, String value, Date expirationDate) {
    long now = new Date().getTime();
    long expirationTime = expirationDate != null ? expirationDate.getTime() : 0;
    boolean isValid = value != null && expirationTime != 0 && expirationTime > now;
    if (!isValid) {
      log.debug("Removing resolution {}={} from cache (expired on {})", key, value, expirationDate);
      clearFromResolutionCache(key);
    }
    return isValid;
  }

  private String formatDate(Date date) {
    return date != null ? dateFormat.format(date) : "";
  }

  private String getExpirationKey(String key) {
    return key + TTL;
  }

  public boolean checkKeyInResolutionCache(String key) {
    String valueFromResolutionCache = getValueFromResolutionCache(key);
    boolean valueInResolutionCache = valueFromResolutionCache != null && !valueFromResolutionCache.isEmpty();
    if (valueInResolutionCache) {
      Date expirationDate = getExpirationDateFromResolutionCache(key);
      valueInResolutionCache &= checkValidity(key, valueFromResolutionCache, expirationDate);
      if (valueInResolutionCache) {
        String strDate = formatDate(expirationDate);
        log.debug("Resolution {}={} in cache (valid until {})", key, valueFromResolutionCache, strDate);
      }
    }
    return valueInResolutionCache;
  }

}
