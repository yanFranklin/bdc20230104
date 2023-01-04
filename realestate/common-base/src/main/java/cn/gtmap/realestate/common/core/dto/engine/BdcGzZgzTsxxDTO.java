package cn.gtmap.realestate.common.core.dto.engine;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/6
 * @description 规则子系统：子规则验证提示信息结果DTO
 */
public class BdcGzZgzTsxxDTO <T> implements Cloneable{
    /**
     * 子规则ID
     */
    private String gzid;
    /**
     * 子规则名称
     */
    private String gzmc;
    /**
     * 子规则优先级
     */
    private Integer yxj;
    /**
     * 验证参数
     */
    private Map<String, Object> param;
    /**
     * 数据流执行结果
     */
    private Map<String, T> sjljg;
    /**
     * 验证提示信息
     */
    private List<String> tsxx;


    public BdcGzZgzTsxxDTO () {

    }


    public BdcGzZgzTsxxDTO(Builder builder) {
        this.gzid = builder.bdcGzZgzDTO.getGzid();
        this.gzmc = builder.bdcGzZgzDTO.getGzmc();
        this.yxj = builder.bdcGzZgzDTO.getYxj();
        this.param = builder.paramMap;
        this.sjljg = builder.sjljg;
        this.tsxx = builder.tsxx;
    }

    /**
     * 子规则提示信息内部构造类
     */
    public static class Builder <T> {
        private BdcGzZgzDTO bdcGzZgzDTO;

        private Map<String, Object> paramMap;

        private Map<String, T> sjljg;

        private List<String> tsxx;

        public Builder(BdcGzZgzDTO bdcGzZgzDTO) {
            this.bdcGzZgzDTO = bdcGzZgzDTO;
        }

        public Builder paramMap(Map<String, Object> paramMap) {
            this.paramMap = paramMap;
            return this;
        }

        public Builder sjljg(Map<String, T> sjljg){
            this.sjljg = sjljg;
            return this;
        }

        public Builder tsxx(List<String> tsxx){
            this.tsxx = tsxx;
            return this;
        }

        public BdcGzZgzTsxxDTO build(){
            return new BdcGzZgzTsxxDTO(this);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getGzmc() {
        return gzmc;
    }

    public void setGzmc(String gzmc) {
        this.gzmc = gzmc;
    }

    public Integer getYxj() {
        return yxj;
    }

    public void setYxj(Integer yxj) {
        this.yxj = yxj;
    }

    public List<String> getTsxx() {
        return tsxx;
    }

    public void setTsxx(List<String> tsxx) {
        this.tsxx = tsxx;
    }

    public Map<String, T> getSjljg() {
        return sjljg;
    }

    public void setSjljg(Map<String, T> sjljg) {
        this.sjljg = sjljg;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }

        if(!(obj instanceof BdcGzZgzTsxxDTO)){
            return false;
        }

        BdcGzZgzTsxxDTO zgzTsxxDTO = (BdcGzZgzTsxxDTO) obj;
        return StringUtils.equals(zgzTsxxDTO.getGzid(), gzid)
                && StringUtils.equals(zgzTsxxDTO.getGzmc(), gzmc)
                && isIntegerEquals(zgzTsxxDTO.getYxj(), yxj)
                && isMapEquals(zgzTsxxDTO.getParam(), param)
                && isMapEquals(zgzTsxxDTO.getSjljg(), sjljg)
                && isListEquals(zgzTsxxDTO.getTsxx(), tsxx);
    }

    @Override
    public int hashCode() {
        return (StringUtils.isBlank(gzid) ? 0 : gzid.hashCode())
                + (StringUtils.isBlank(gzmc) ? 0 : gzmc.hashCode())
                + (null == yxj ? 100 : yxj.intValue() * 10);
    }

    private boolean isListEquals(List list1, List list2){
        if(null == list1){
            return null == list2;
        }
        return CollectionUtils.isEqualCollection(list1, list2);
    }

    private boolean isIntegerEquals(Integer t1, Integer t2){
        if(null == t1){
            return null == t2;
        }
        return t1.equals(t2);
    }

    private boolean isMapEquals(Map map1, Map map2){
        if(MapUtils.isEmpty(map1)){
            return map1 == map2;
        }
        return map1.equals(map2);
    }

    @Override
    public String toString() {
        return "BdcGzZgzTsxxDTO{" +
                "gzid='" + gzid + '\'' +
                ", gzmc='" + gzmc + '\'' +
                ", yxj=" + yxj +
                ", tsxx='" + tsxx + '\'' +
                '}';
    }
}
