package cn.gtmap.realestate.etl.core.thirdparty.order.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "root")
public class QueryFileInfoResponse implements Serializable {

    private static final long serialVersionUID = 9145675271050339620L;

    private Data data;

    @XmlElement(name = "data")
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public static final class QueryFileInfoResponseBuilder {
        private Data data;

        private QueryFileInfoResponseBuilder() {
        }

        public static QueryFileInfoResponseBuilder aQueryFileInfoResponse() {
            return new QueryFileInfoResponseBuilder();
        }

        public QueryFileInfoResponseBuilder withData(Data data) {
            this.data = data;
            return this;
        }

        public QueryFileInfoResponse build() {
            QueryFileInfoResponse queryFileInfoResponse = new QueryFileInfoResponse();
            queryFileInfoResponse.setData(data);
            return queryFileInfoResponse;
        }
    }
}
