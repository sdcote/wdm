package coyote.wdm.config;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public enum OperatingSystem {
  WIN(emptyList()),
  LINUX(emptyList()),
  MAC(asList("osx"));

  List<String> osLabels;

  OperatingSystem(List<String> osLabels) {
    this.osLabels = osLabels;
  }

  public Stream<String> osLabelsStream() {
    return this.osLabels.stream();
  }

  public boolean matchOs(String os) {
    return osLabelsStream().anyMatch(os::contains) || os.contains(this.name().toLowerCase());
  }
}
