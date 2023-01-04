package cn.gtmap.realestate.building.ui.core.vo;

import cn.gtmap.realestate.common.core.dto.building.ResourceDTO;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-26
 * @description
 */
public class LpbDyVO implements Comparable<LpbDyVO>{

    /**
     * 单元号
     */
    private String dyh;

    /**
     * 最大户数
     */
    private int maxHs;

    /**
     * 每层 每单元 房屋数据
     */
    private List<ResourceDTO> fwhsResourceDTOList;

    private Integer hsCount;

    /*
     * 每层分组后的单元数据
     * */
    private List<List> groupfwhsResourceDTOList;

    public String getDyh() {
        return dyh;
    }

    public void setDyh(String dyh) {
        this.dyh = dyh;
    }

    public int getMaxHs() {
        return maxHs;
    }

    public void setMaxHs(int maxHs) {
        this.maxHs = maxHs;
    }

    public List<ResourceDTO> getFwhsResourceDTOList() {
        return fwhsResourceDTOList;
    }

    public void setFwhsResourceDTOList(List<ResourceDTO> fwhsResourceDTOList) {
        this.fwhsResourceDTOList = fwhsResourceDTOList;
    }

    public List<List> getGroupfwhsResourceDTOList() {
        return groupfwhsResourceDTOList;
    }

    public void setGroupfwhsResourceDTOList(List<List> groupfwhsResourceDTOList) {
        this.groupfwhsResourceDTOList = groupfwhsResourceDTOList;
    }

    public Integer getHsCount() {
        return hsCount;
    }

    public void setHsCount(Integer hsCount) {
        this.hsCount = hsCount;
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
    public int compareTo(LpbDyVO o) {
        if(o.getDyh() == null && this.dyh == null ){
            return 0;
        }
        if(o.getDyh() == null){
            return 1;
        }
        if(this.dyh == null){
            return -1;
        }
        if(NumberUtils.isNumber(o.getDyh()) && NumberUtils.isNumber(this.dyh)){
            int odyh = Integer.parseInt(o.getDyh());
            int thisdyh = Integer.parseInt(this.dyh);
            if (thisdyh > odyh) {
                return 1;
            } else if (thisdyh < odyh) {
                return -1;
            } else {
                return 0;
            }
        } else {
            this.dyh = StringUtils.isNotBlank(this.dyh) ? this.dyh : "";
            String oDyh = StringUtils.isNotBlank(o.getDyh()) ? o.getDyh() : "";
            return this.dyh.compareTo(oDyh);
        }
    }

    @Override
    public String toString() {
        return "LpbDyVO{" +
                "dyh='" + dyh + '\'' +
                ", maxHs=" + maxHs +
                ", fwhsResourceDTOList=" + fwhsResourceDTOList +
                ", hsCount=" + hsCount +
                ", groupfwhsResourceDTOList=" + groupfwhsResourceDTOList +
                '}';
    }
}
