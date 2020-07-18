package coyote.wdm.versions;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import java.io.File;
import java.util.Arrays;

import static java.lang.String.join;
import static java.lang.invoke.MethodHandles.lookup;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Command line executor.
 */
public class Shell {

  static final Logger log = getLogger(lookup().lookupClass());

  private Shell() {
    throw new IllegalStateException("Utility class");
  }

  public static String runAndWait(String... command) {
    return runAndWaitArray(new File("."), command);
  }

  public static String runAndWait(File folder, String... command) {
    return runAndWaitArray(folder, command);
  }

  public static String runAndWaitArray(File folder, String[] command) {
    String commandStr = Arrays.toString(command);
    log.debug("Running command on the shell: {}", commandStr);
    String result = runAndWaitNoLog(folder, command);
    log.debug("Result: {}", result);
    return result;
  }

  public static String runAndWaitNoLog(File folder, String... command) {
    String output = "";
    try {
      Process process = new ProcessBuilder(command).directory(folder).redirectErrorStream(false).start();
      process.waitFor();
      output = IOUtils.toString(process.getInputStream(), UTF_8);
    } catch (Exception e) {
      if (log.isDebugEnabled()) {
        log.debug("There was a problem executing command <{}> on the shell: {}", join(" ", command), e.getMessage());
      }
    }
    return output.trim();
  }

  public static String getVersionFromWmicOutput(String output) {
    int i = output.indexOf('=');
    int j = output.indexOf('.');
    return i != -1 && j != -1 ? output.substring(i + 1, j) : output;
  }

  public static String getVersionFromPosixOutput(String output, String driverType) {
    // Special case: using Chromium as Chrome
    if (output.contains("Chromium")) {
      driverType = "Chromium";
    }
    int i = output.indexOf(driverType);
    int j = output.indexOf('.');
    return i != -1 && j != -1 ? output.substring(i + driverType.length(), j).trim() : output;
  }

  public static String getVersionFromPowerShellOutput(String output) {
    int i = output.indexOf("Version");
    int j = output.indexOf(':', i);
    int k = output.indexOf('.', j);
    return i != -1 && j != -1 && k != -1 ? output.substring(j + 1, k).trim() : output;
  }

}
