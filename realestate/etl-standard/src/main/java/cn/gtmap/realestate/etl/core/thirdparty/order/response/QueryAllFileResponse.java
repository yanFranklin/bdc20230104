package cn.gtmap.realestate.etl.core.thirdparty.order.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "root")
public class QueryAllFileResponse implements Serializable {

    private static final long serialVersionUID = -1384769556081967855L;

    private Data data;

    @XmlElement(name = "data")
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static final class QueryAllFileResponseBuilder {
        private Data data;

        private QueryAllFileResponseBuilder() {
        }

        public static QueryAllFileResponseBuilder aQueryAllFileResponse() {
            return new QueryAllFileResponseBuilder();
        }

        public QueryAllFileResponseBuilder withData(Data data) {
            this.data = data;
            return this;
        }

        public QueryAllFileResponse build() {
            QueryAllFileResponse queryAllFileResponse = new QueryAllFileResponse();
            queryAllFileResponse.setData(data);
            return queryAllFileResponse;
        }
    }
}
