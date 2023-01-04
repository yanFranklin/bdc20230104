package cn.gtmap.realestate.engine.core.bo;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/8
 * @description 规则子系统 规则判断表达式元素BO对象（用于表达式分析）
 *  例如： 对于表达式 ( list2 不为空 ) ，那么各属性对应内容为：
 *      startIndex       1
 *      preElement       list2
 *      preElementType   java.util.List
 *      preElementObj   （需要获取）
 *      curOperator     不为空
 *      nextElement      )
 *      length           2
 *      expression      null != list2
 */
public class BdcGzBdsYsBO {
    /**
     * 元素起始位置
     */
    private Integer startIndex;
    /**
     * 上一个元素
     */
    private String preElement;
    /**
     * 上一个元素组成
     */
    private BdcGzBdsYszcBO bdcGzBdsYszcBO;
    /**
     * 上一个元素结构类型（1、List<Map>  2、List<Object> 3、Map  4、Object）
     */
    private String preElementType;
    /**
     * 上一个元素值对应的Java类型
     */
    private String preElementJavaType;
    /**
     * 上一个元素对应实际Java对象
     */
    private Object preElementObj;
    /**
     * 当前操作符
     */
    private String curOperator;
    /**
     * 下一个元素
     */
    private String nextElement;
    /**
     * 元素簇中元素个数
     */
    private Integer length;
    /**
     * 元素簇解析后对应的Drools表达式
     */
    private String expression;


    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public String getPreElement() {
        return preElement;
    }

    public void setPreElement(String preElement) {
        this.preElement = preElement;
    }

    public String getPreElementType() {
        return preElementType;
    }

    public void setPreElementType(String preElementType) {
        this.preElementType = preElementType;
    }

    public Object getPreElementObj() {
        return preElementObj;
    }

    public void setPreElementObj(Object preElementObj) {
        this.preElementObj = preElementObj;
    }

    public String getCurOperator() {
        return curOperator;
    }

    public void setCurOperator(String curOperator) {
        this.curOperator = curOperator;
    }

    public String getNextElement() {
        return nextElement;
    }

    public void setNextElement(String nextElement) {
        this.nextElement = nextElement;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public BdcGzBdsYszcBO getBdcGzBdsYszcBO() {
        return bdcGzBdsYszcBO;
    }

    public void setBdcGzBdsYszcBO(BdcGzBdsYszcBO bdcGzBdsYszcBO) {
        this.bdcGzBdsYszcBO = bdcGzBdsYszcBO;
    }

    public String getPreElementJavaType() {
        return preElementJavaType;
    }

    public void setPreElementJavaType(String preElementJavaType) {
        this.preElementJavaType = preElementJavaType;
    }

    @Override
    public String toString() {
        return "BdcGzBdsYsBO{" +
                "startIndex=" + startIndex +
                ", preElement='" + preElement + '\'' +
                ", bdcGzBdsYszcBO=" + bdcGzBdsYszcBO +
                ", preElementType='" + preElementType + '\'' +
                ", preElementJavaType='" + preElementJavaType + '\'' +
                ", preElementObj=" + preElementObj +
                ", curOperator='" + curOperator + '\'' +
                ", nextElement='" + nextElement + '\'' +
                ", length=" + length +
                ", expression='" + expression + '\'' +
                '}';
    }
}
