package cn.gtmap.realestate.exchange.core.dto.bjjk.hydjxx.response;

import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description
 */
@IgnoreCast
public class BjjkHydjxxResponseRows implements Comparable<BjjkHydjxxResponseRows> {

    //登记日期
    private String op_date;

    private String dept_code;

    private String dept_name;

    private String name_woman;

    private String birth_woman;

    private String cert_no;

    private String cert_num_man;

    private String cert_num_woman;

    private String birth_man;

    private String nation_woman;

    private String nation_man;

    private String op_type;

    private String name_man;


    public String getOp_date() {
        return op_date;
    }

    public void setOp_date(String op_date) {
        this.op_date = op_date;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getName_woman() {
        return name_woman;
    }

    public void setName_woman(String name_woman) {
        this.name_woman = name_woman;
    }

    public String getBirth_woman() {
        return birth_woman;
    }

    public void setBirth_woman(String birth_woman) {
        this.birth_woman = birth_woman;
    }

    public String getCert_no() {
        return cert_no;
    }

    public void setCert_no(String cert_no) {
        this.cert_no = cert_no;
    }

    public String getCert_num_man() {
        return cert_num_man;
    }

    public void setCert_num_man(String cert_num_man) {
        this.cert_num_man = cert_num_man;
    }

    public String getCert_num_woman() {
        return cert_num_woman;
    }

    public void setCert_num_woman(String cert_num_woman) {
        this.cert_num_woman = cert_num_woman;
    }

    public String getBirth_man() {
        return birth_man;
    }

    public void setBirth_man(String birth_man) {
        this.birth_man = birth_man;
    }

    public String getNation_woman() {
        return nation_woman;
    }

    public void setNation_woman(String nation_woman) {
        this.nation_woman = nation_woman;
    }

    public String getNation_man() {
        return nation_man;
    }

    public void setNation_man(String nation_man) {
        this.nation_man = nation_man;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getName_man() {
        return name_man;
    }

    public void setName_man(String name_man) {
        this.name_man = name_man;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
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
    public int compareTo(BjjkHydjxxResponseRows o) {
        if(StringUtils.isBlank(o.getOp_date())){
            return -1;
        }
        if(StringUtils.isBlank(this.op_date)){
            return 1;
        }
        try {
            Date temp1 = DateUtils.formatDate(o.getOp_date(),DateUtils.sdf_ymdhms);
            Date temp2 = DateUtils.formatDate(this.op_date,DateUtils.sdf_ymdhms);
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
