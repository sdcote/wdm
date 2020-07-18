package coyote.wdm.online

import java.util.*
import javax.xml.namespace.NamespaceContext

/**
 * Namespace context for S3 buckets.
 */
class S3NamespaceContext : NamespaceContext {
    override fun getNamespaceURI(prefix: String): String {
        if (S3_PREFIX == prefix) {
            return S3_BUCKET_LIST_NS
        }
        throw IllegalArgumentException("Unsupported prefix")
    }

    override fun getPrefix(namespaceURI: String): String {
        if (S3_BUCKET_LIST_NS == namespaceURI) {
            return S3_PREFIX
        }
        throw IllegalArgumentException("Unsupported namespace URI")
    }

    override fun getPrefixes(namespaceURI: String): Iterator<String?> {
        return if (S3_BUCKET_LIST_NS == namespaceURI) {
            listOf(S3_PREFIX).iterator()
        } else {
            Collections.emptyIterator<String>()
        }
    }

    companion object {
        private const val S3_BUCKET_LIST_NS = "http://doc.s3.amazonaws.com/2006-03-01"
        private const val S3_PREFIX = "s3"
    }
}