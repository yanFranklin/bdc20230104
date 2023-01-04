
package cn.gtmap.realestate.exchange.util.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>exchangeInfo complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="exchangeInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="args0" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exchangeInfo", propOrder = {
    "args0"
})
public class ExchangeInfo {

    @XmlElement(namespace = "http://loushang.ws")
    protected String args0;
    
    /**
     * 获取args0属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getArgs0() {
        return args0;
    }
    
    /**
     * 设置args0属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setArgs0(String value) {
        this.args0 = value;
    }

}
