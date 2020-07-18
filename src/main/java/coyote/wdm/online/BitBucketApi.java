package coyote.wdm.online;

import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Plain-Old Java Object to parse JSON BitBucket API (e.g.
 * https://bitbucket.org/api/2.0/repositories/ariya/phantomjs/downloads) by
 * means of GSON.
 */
public class BitBucketApi {

  private int pagelen;
  private int size;
  private List<BitBucketValue> values;

  public int getPagelen() {
    return pagelen;
  }

  public int getSize() {
    return size;
  }

  public List<BitBucketValue> getValues() {
    return values;
  }

  public List<URL> getUrls() throws MalformedURLException {
    List<URL> urls = new ArrayList<>();
    for (BitBucketValue value : this.getValues()) {
      urls.add(new URL(value.getLinks().getSelf().getHref()));
    }
    return urls;
  }

  class BitBucketValue {
    private String name;
    private BitBucketLink links;
    private int downloads;
    @SerializedName("created_on")
    private String createOn;
    private Object user;
    private String type;
    private int size;

    public String getName() {
      return name;
    }

    public BitBucketLink getLinks() {
      return links;
    }

    public int getDownloads() {
      return downloads;
    }

    public String getCreateOn() {
      return createOn;
    }

    public Object getUser() {
      return user;
    }

    public String getType() {
      return type;
    }

    public int getSize() {
      return size;
    }
  }

  class BitBucketLink {
    private BitBucketSelf self;
    public BitBucketSelf getSelf() {
      return self;
    }
  }

  class BitBucketSelf {
    private String href;
    public String getHref() {
      return href;
    }
  }

}
