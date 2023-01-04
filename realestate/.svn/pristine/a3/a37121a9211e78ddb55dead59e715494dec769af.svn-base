package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcJtcyDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/8/7
 * @description 家庭成员批量保存对象
 */
@ApiModel(value = "BdcJtcySaveDTO",description = "家庭成员批量保存对象")
public class BdcJtcySaveDTO {

    @ApiModelProperty(value = "权利人关联家庭成员信息")
    private List<BdcJtcyDO> bdcJtcyDOList;

    @ApiModelProperty(value = "权利人ID")
    private String qlrid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    public List<BdcJtcyDO> getBdcJtcyDOList() {
        return bdcJtcyDOList;
    }

    public void setBdcJtcyDOList(List<BdcJtcyDO> bdcJtcyDOList) {
        this.bdcJtcyDOList = bdcJtcyDOList;
    }

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }
}
