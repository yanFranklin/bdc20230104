package cn.gtmap.realestate.building.ui.core.vo;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-26
 * @description 房屋数据
 */
public class LpbChVO implements Comparable<LpbChVO>{

    /**
     * 每层 的所有单元房屋数据
     */
    private List<LpbDyVO> dyFwList;

    /**
     * 物理层数
     */
    private String wlcs;

    /**
     * 定义层数
     */
    private String dycs;


    public String getWlcs() {
        return wlcs;
    }

    public void setWlcs(String wlcs) {
        this.wlcs = wlcs;
    }

    public String getDycs() {
        return dycs;
    }

    public void setDycs(String dycs) {
        this.dycs = dycs;
    }

    public List<LpbDyVO> getDyFwList() {
        return dyFwList;
    }

    public void setDyFwList(List<LpbDyVO> dyFwList) {
        this.dyFwList = dyFwList;
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
    public int compareTo(LpbChVO o) {
        if(o.getWlcs() == null && this.wlcs == null){
            return 0;
        }
        if(o.getWlcs() == null){
            return -1;
        }
        if(this.wlcs == null){
            return 1;
        }

        Integer och = StringUtils.isNotBlank(o.getWlcs()) && NumberUtils.isNumber(o.getWlcs())?Integer.parseInt(o.getWlcs()):-1;
        Integer thisch = StringUtils.isNotBlank(this.wlcs) && NumberUtils.isNumber(this.wlcs)?Integer.parseInt(this.wlcs):-1;
        if (thisch > och) {
            return -1;
        } else if (thisch < och) {
            return 1;
        } else {
            return 0;
        }
    }
}
