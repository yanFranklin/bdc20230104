
package cn.gtmap.realestate.exchange.util.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


@XmlRegistry
public class ObjectFactory {

    private final static QName _ExchangeInfoResponse_QNAME = new QName("http://loushang.ws", "exchangeInfoResponse");
    private final static QName _UploadDataResponse_QNAME = new QName("http://loushang.ws", "uploadDataResponse");
    private final static QName _ExchangeInfo_QNAME = new QName("http://loushang.ws", "exchangeInfo");
    private final static QName _UploadData_QNAME = new QName("http://loushang.ws", "uploadData");

    public ObjectFactory() {
    }

    public UploadData createUploadData() {
        return new UploadData();
    }

    public ExchangeInfo createExchangeInfo() {
        return new ExchangeInfo();
    }

    public UploadDataResponse createUploadDataResponse() {
        return new UploadDataResponse();
    }

    public ExchangeInfoResponse createExchangeInfoResponse() {
        return new ExchangeInfoResponse();
    }

    @XmlElementDecl(namespace = "http://loushang.ws", name = "exchangeInfoResponse")
    public JAXBElement<ExchangeInfoResponse> createExchangeInfoResponse(ExchangeInfoResponse value) {
        return new JAXBElement<ExchangeInfoResponse>(_ExchangeInfoResponse_QNAME, ExchangeInfoResponse.class, null, value);
    }

    @XmlElementDecl(namespace = "http://loushang.ws", name = "uploadDataResponse")
    public JAXBElement<UploadDataResponse> createUploadDataResponse(UploadDataResponse value) {
        return new JAXBElement<UploadDataResponse>(_UploadDataResponse_QNAME, UploadDataResponse.class, null, value);
    }

    @XmlElementDecl(namespace = "http://loushang.ws", name = "exchangeInfo")
    public JAXBElement<ExchangeInfo> createExchangeInfo(ExchangeInfo value) {
        return new JAXBElement<ExchangeInfo>(_ExchangeInfo_QNAME, ExchangeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://loushang.ws", name = "uploadData")
    public JAXBElement<UploadData> createUploadData(UploadData value) {
        return new JAXBElement<UploadData>(_UploadData_QNAME, UploadData.class, null, value);
    }

}
