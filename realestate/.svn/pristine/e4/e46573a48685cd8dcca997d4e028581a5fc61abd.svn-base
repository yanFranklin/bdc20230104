package cn.gtmap.realestate.certificate.core.service.impl.file;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2019-09-27
 * @description
 */
public class StorageHttpDelete extends HttpPost {

    public final static String METHOD_NAME = "DELETE";

    public StorageHttpDelete(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return StorageHttpDelete.METHOD_NAME;
    }
}
