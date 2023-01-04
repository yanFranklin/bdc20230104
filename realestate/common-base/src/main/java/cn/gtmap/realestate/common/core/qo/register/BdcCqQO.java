package cn.gtmap.realestate.common.core.qo.register;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/8/31
 * @description 产权信息查询对象类
 */
public class BdcCqQO {

    /**
     * 不动产单元号集合
     */
    @ApiModelProperty(value = "不动产单元号集合")
    private List<String> bdcdyhList;

    @ApiModelProperty(value = "权属状态集合")
    private List<Integer> qsztList;

    @ApiModelProperty(value = "权属状态")
    private Integer qszt;

    public List<String> getBdcdyhList() {
        return bdcdyhList;
    }

    public void setBdcdyhList(List<String> bdcdyhList) {
        this.bdcdyhList = bdcdyhList;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public List<Integer> getQsztList() {
        return qsztList;
    }

    public void setQsztList(List<Integer> qsztList) {
        this.qsztList = qsztList;
    }
}
