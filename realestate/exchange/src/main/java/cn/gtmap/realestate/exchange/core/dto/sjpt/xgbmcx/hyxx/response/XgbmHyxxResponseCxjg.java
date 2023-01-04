package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxx.response;

import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-09
 * @description
 */
@IgnoreCast
public class XgbmHyxxResponseCxjg  implements Comparable<XgbmHyxxResponseCxjg>{

    @JSONField(name = "DEPT_NAME")
    private String DEPT_NAME;

    @JSONField(name = "REG_DETAIL")
    private String REG_DETAIL;

    @JSONField(name = "OP_DATE")
    private String OP_DATE;

    @JSONField(name = "MARRY_STATUS")
    private String MARRY_STATUS;

    @JSONField(name = "SEX")
    private String SEX;

    @JSONField(name = "OP_TYPE")
    private String OP_TYPE;

    @JSONField(name = "SPOUSE_ID_TYPE")
    private String SPOUSE_ID_TYPE;

    @JSONField(name = "SPOUSE_NAME")
    private String SPOUSE_NAME;

    @JSONField(name = "ID_TYPE")
    private String ID_TYPE;

    @JSONField(name = "NAME")
    private String NAME;

    @JSONField(name = "SPOUSE_CERT_TYPE")
    private String SPOUSE_CERT_TYPE;

    @JSONField(name = "CERT_NO")
    private String CERT_NO;

    @JSONField(name = "RN")
    private String RN;

    @JSONField(name = "CERT_NUM")
    private String CERT_NUM;

    @JSONField(name = "SPOUSE_CERT_NUM")
    private String SPOUSE_CERT_NUM;

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public String getREG_DETAIL() {
        return REG_DETAIL;
    }

    public void setREG_DETAIL(String REG_DETAIL) {
        this.REG_DETAIL = REG_DETAIL;
    }

    public String getOP_DATE() {
        return OP_DATE;
    }

    public void setOP_DATE(String OP_DATE) {
        this.OP_DATE = OP_DATE;
    }

    public String getMARRY_STATUS() {
        return MARRY_STATUS;
    }

    public void setMARRY_STATUS(String MARRY_STATUS) {
        this.MARRY_STATUS = MARRY_STATUS;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getOP_TYPE() {
        return OP_TYPE;
    }

    public void setOP_TYPE(String OP_TYPE) {
        this.OP_TYPE = OP_TYPE;
    }

    public String getSPOUSE_ID_TYPE() {
        return SPOUSE_ID_TYPE;
    }

    public void setSPOUSE_ID_TYPE(String SPOUSE_ID_TYPE) {
        this.SPOUSE_ID_TYPE = SPOUSE_ID_TYPE;
    }

    public String getSPOUSE_NAME() {
        return SPOUSE_NAME;
    }

    public void setSPOUSE_NAME(String SPOUSE_NAME) {
        this.SPOUSE_NAME = SPOUSE_NAME;
    }

    public String getID_TYPE() {
        return ID_TYPE;
    }

    public void setID_TYPE(String ID_TYPE) {
        this.ID_TYPE = ID_TYPE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSPOUSE_CERT_TYPE() {
        return SPOUSE_CERT_TYPE;
    }

    public void setSPOUSE_CERT_TYPE(String SPOUSE_CERT_TYPE) {
        this.SPOUSE_CERT_TYPE = SPOUSE_CERT_TYPE;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public void setCERT_NO(String CERT_NO) {
        this.CERT_NO = CERT_NO;
    }

    public String getRN() {
        return RN;
    }

    public void setRN(String RN) {
        this.RN = RN;
    }

    public String getCERT_NUM() {
        return CERT_NUM;
    }

    public void setCERT_NUM(String CERT_NUM) {
        this.CERT_NUM = CERT_NUM;
    }

    public String getSPOUSE_CERT_NUM() {
        return SPOUSE_CERT_NUM;
    }

    public void setSPOUSE_CERT_NUM(String SPOUSE_CERT_NUM) {
        this.SPOUSE_CERT_NUM = SPOUSE_CERT_NUM;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(XgbmHyxxResponseCxjg o) {
        if(StringUtils.isBlank(o.getOP_DATE())){
            return -1;
        }
        if(StringUtils.isBlank(this.OP_DATE)){
            return 1;
        }
        try {
            Date temp1 = DateUtils.formatDate(o.getOP_DATE(),DateUtils.sdf_ymdhms);
            Date temp2 = DateUtils.formatDate(this.OP_DATE,DateUtils.sdf_ymdhms);
            if(temp1.before(temp2)){
                return -1;
            }else{
                return 1;
            }
        }catch (Exception e){
            return 0;
        }
    }
}
