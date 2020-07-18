package coyote.wdm.online;

import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

import static java.util.Collections.emptyIterator;
import static java.util.Collections.singletonList;

public class S3NamespaceContext implements NamespaceContext {

  private static final String S3_BUCKET_LIST_NS = "http://doc.s3.amazonaws.com/2006-03-01";
  private static final String S3_PREFIX = "s3";

  @Override
  public String getNamespaceURI(String prefix) {
    if (S3_PREFIX.equals(prefix)) {
      return S3_BUCKET_LIST_NS;
    }
    throw new IllegalArgumentException("Unsupported prefix");
  }

  @Override
  public String getPrefix(String namespaceURI) {
    if (S3_BUCKET_LIST_NS.equals(namespaceURI)) {
      return S3_PREFIX;
    }
    throw new IllegalArgumentException("Unsupported namespace URI");
  }

  @Override
  public Iterator<String> getPrefixes(String namespaceURI) {
    if (S3_BUCKET_LIST_NS.equals(namespaceURI)) {
      return singletonList(S3_PREFIX).iterator();
    } else {
      return emptyIterator();
    }
  }

}
