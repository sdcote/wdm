package coyote.wdm.cache;

import coyote.wdm.config.Architecture;
import coyote.wdm.config.Config;
import coyote.wdm.config.DriverManagerType;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static coyote.wdm.config.DriverManagerType.CHROME;
import static coyote.wdm.config.DriverManagerType.CHROMIUM;
import static java.io.File.separator;
import static java.lang.invoke.MethodHandles.lookup;
import static java.util.Collections.sort;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.io.FileUtils.listFiles;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.slf4j.LoggerFactory.getLogger;

public class CacheHandler {

  final Logger log = getLogger(lookup().lookupClass());

  private final Config config;

  public CacheHandler(Config config) {
    this.config = config;
  }

  public List<File> filterCacheBy(List<File> input, String key,
                                  boolean isVersion) {
    String pathSeparator = isVersion ? separator : "";
    List<File> output = new ArrayList<>(input);
    if (!key.isEmpty() && !input.isEmpty()) {
      output = input.stream().filter(file -> file.toString().toLowerCase().contains(pathSeparator + key.toLowerCase())).collect(toList());
    }
    log.trace("Filter cache by {} -- input list {} -- output list {} ", key, input, output);
    return output;
  }

  public List<File> getFilesInCache() {
    List<File> listFiles = (List<File>) listFiles(new File(config.getCachePath()), null, true);
    sort(listFiles);
    return listFiles;
  }

  public Optional<String> getDriverFromCache(String driverVersion, String driverName, DriverManagerType driverManagerType, Architecture arch, String os) {
    log.trace("Checking if {} exists in cache", driverName);
    List<File> filesInCache = getFilesInCache();
    if (!filesInCache.isEmpty()) {
      // Filter by name
      filesInCache = filterCacheBy(filesInCache, driverName, false);

      // Filter by version
      filesInCache = filterCacheBy(filesInCache, driverVersion, true);

      // Filter by OS
      filesInCache = filterCacheBy(filesInCache, os, false);

      if (filesInCache.size() == 1) {
        return Optional.of(filesInCache.get(0).toString());
      }

      // Filter by arch
      if (IS_OS_WINDOWS && (driverManagerType == CHROME || driverManagerType == CHROMIUM)) {
        log.trace("Avoid filtering for architecture {} with {} in Windows", arch, driverName);
      } else {
        filesInCache = filterCacheBy(filesInCache, arch.toString(), false);
      }

      if (!filesInCache.isEmpty()) {
        return Optional.of(filesInCache.get(filesInCache.size() - 1).toString());
      }
    }

    log.trace("{} not found in cache", driverName);
    return Optional.empty();
  }

}
