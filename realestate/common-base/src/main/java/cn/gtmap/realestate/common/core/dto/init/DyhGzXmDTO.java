package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/9/21
 * @description 单元号更正项目集合
 */
public class DyhGzXmDTO {

    @ApiModelProperty(value = "产权关联历史关系项目集合")
    private DyhGzCqLsgxXmDTO cqglLsgxXmDTO;

    @ApiModelProperty(value = "匹配产权关联历史关系项目集合")
    private DyhGzCqLsgxXmDTO ppcqLsgxXmDTO;

    @ApiModelProperty(value = "限制权利关联历史关系项目集合")
    private LsgxXzqlModelDTO xzqlLsgxXmDTO;

    @ApiModelProperty(value = "所有的项目集合")
    private List<BdcXmDO> bdcXmDOList;

    @ApiModelProperty(value = "所有的项目ID集合")
    private List<String> xmidList;

    public DyhGzCqLsgxXmDTO getCqglLsgxXmDTO() {
        return cqglLsgxXmDTO;
    }

    public void setCqglLsgxXmDTO(DyhGzCqLsgxXmDTO cqglLsgxXmDTO) {
        this.cqglLsgxXmDTO = cqglLsgxXmDTO;
    }

    public DyhGzCqLsgxXmDTO getPpcqLsgxXmDTO() {
        return ppcqLsgxXmDTO;
    }

    public void setPpcqLsgxXmDTO(DyhGzCqLsgxXmDTO ppcqLsgxXmDTO) {
        this.ppcqLsgxXmDTO = ppcqLsgxXmDTO;
    }

    public LsgxXzqlModelDTO getXzqlLsgxXmDTO() {
        return xzqlLsgxXmDTO;
    }

    public void setXzqlLsgxXmDTO(LsgxXzqlModelDTO xzqlLsgxXmDTO) {
        this.xzqlLsgxXmDTO = xzqlLsgxXmDTO;
    }

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }
}
