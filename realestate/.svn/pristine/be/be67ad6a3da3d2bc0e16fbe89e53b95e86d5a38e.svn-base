package cn.gtmap.realestate.exchange.service.impl.inf.request;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-27
 * @description
 */
public class ExchangeHttpDelete extends HttpPost {

    public final static String METHOD_NAME = "DELETE";

    public ExchangeHttpDelete(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return ExchangeHttpDelete.METHOD_NAME;
    }
}
