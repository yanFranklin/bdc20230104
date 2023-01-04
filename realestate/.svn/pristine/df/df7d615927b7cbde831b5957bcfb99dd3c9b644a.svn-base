package cn.gtmap.realestate.common.core.dto.engine;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/13
 * @description 不动产规则验证结果提示信息（绑定某些具体业务场景使用）
 */
@ApiModel(value = "BdcGzYzTsxxDTO", description = "不动产规则验证结果提示信息")
public class BdcGzYzTsxxDTO extends BdcGzZhgzTsxxDTO{

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "调用规则时传入的参数")
    private Map<String, Object> paramMap;

    public BdcGzYzTsxxDTO() {

    }

    public BdcGzYzTsxxDTO(Builder builder) {
        this.paramMap = builder.resultEntry.getKey();
        this.zgzTsxxDTOList = builder.resultEntry.getValue();

        this.zhbs = builder.bdcGzZhgzDTO.getZhbs();
        this.zhmc = builder.bdcGzZhgzDTO.getZhmc();

        if(MapUtils.isNotEmpty(this.paramMap)){
            this.bdcdyh = MapUtils.getString(this.paramMap, "bdcdyh");
            this.xmid = MapUtils.getString(this.paramMap, "xmid");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 类构造
     */
    public static class Builder {
        /**
         * 组合规则信息
         */
        private BdcGzZhgzDTO bdcGzZhgzDTO;

        /**
         * 组合规则对应的子规则验证信息
         */
        private Map.Entry<Map, List<BdcGzZgzTsxxDTO>> resultEntry;


        public Builder addBdcGzZhgzDTO(BdcGzZhgzDTO bdcGzZhgzDTO){
            this.bdcGzZhgzDTO = bdcGzZhgzDTO;
            return this;
        }

        public Builder addResultEntry(Map.Entry paramZgzEntry){
            this.resultEntry = paramZgzEntry;
            return this;
        }

        public BdcGzYzTsxxDTO build(){
            return new BdcGzYzTsxxDTO(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if(null == obj) {
            return false;
        }

        if (!(obj instanceof BdcGzYzTsxxDTO)) {
            return false;
        }

        BdcGzYzTsxxDTO that = (BdcGzYzTsxxDTO) obj;
        return Objects.equals(bdcdyh, that.bdcdyh) &&
                Objects.equals(xmid, that.xmid) &&
                Objects.equals(paramMap, that.paramMap) &&
                Objects.equals(super.zhbs, that.zhbs) &&
                Objects.equals(super.zhmc, that.zhmc) &&
                CollectionUtils.isEqualCollection(super.zgzTsxxDTOList, that.zgzTsxxDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bdcdyh, xmid, paramMap, super.zhbs, super.zhmc);
    }


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public String toString() {
        return "BdcGzYzTsxxDTO{" +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }
}
