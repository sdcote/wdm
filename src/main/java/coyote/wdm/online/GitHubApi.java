package coyote.wdm.online;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

/**
 * Plain-Old Java Object to parse JSON GitHub API (e.g.
 * https://api.github.com/repos/operasoftware/operachromiumdriver/releases) by
 * means of GSON.
 */
public class GitHubApi {

  @SerializedName("tag_name")
  private String tagName;

  private String name;
  private List<LinkedTreeMap<String, Object>> assets;

  public String getTagName() {
    return tagName;
  }

  public String getName() {
    return name;
  }

  public List<LinkedTreeMap<String, Object>> getAssets() {
    return assets;
  }

}
