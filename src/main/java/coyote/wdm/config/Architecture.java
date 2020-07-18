package coyote.wdm.config;

import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public enum Architecture {
  DEFAULT(emptyList()),
  X32(asList("i686", "x86")),
  X64(emptyList());

  List<String> archLabels;

  Architecture(List<String> archLabels) {
    this.archLabels = archLabels;
  }

  public Stream<String> archLabelsStream() {
    return this.archLabels.stream();
  }

  public boolean matchUrl(URL url) {
    return archLabelsStream().anyMatch(x -> url.getFile().contains(x)) || url.getFile().contains(this.toString());
  }

  @Override
  public String toString() {
    return this.name().contains("X") ? this.name().replace("X", "") : this.name();
  }

}
