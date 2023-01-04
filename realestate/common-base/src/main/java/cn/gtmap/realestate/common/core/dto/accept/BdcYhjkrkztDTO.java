package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSysxxDO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/3
 * @description 更新银行缴库入库状态
 */
public class BdcYhjkrkztDTO {

    private String slbh;

    private Integer sfyj;

    private List<BdcSlSfxxDO> bdcSlSfxxDOList;

    private List<BdcSlHsxxDO> bdcSlHsxxDOList;

    private List<BdcSlSysxxDO> bdcSlSysxxDOList;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<BdcSlSfxxDO> getBdcSlSfxxDOList() {
        return bdcSlSfxxDOList;
    }

    public void setBdcSlSfxxDOList(List<BdcSlSfxxDO> bdcSlSfxxDOList) {
        this.bdcSlSfxxDOList = bdcSlSfxxDOList;
    }

    public List<BdcSlHsxxDO> getBdcSlHsxxDOList() {
        return bdcSlHsxxDOList;
    }

    public void setBdcSlHsxxDOList(List<BdcSlHsxxDO> bdcSlHsxxDOList) {
        this.bdcSlHsxxDOList = bdcSlHsxxDOList;
    }

    public Integer getSfyj() {
        return sfyj;
    }

    public void setSfyj(Integer sfyj) {
        this.sfyj = sfyj;
    }

    public List<BdcSlSysxxDO> getBdcSlSysxxDOList() {
        return bdcSlSysxxDOList;
    }

    public void setBdcSlSysxxDOList(List<BdcSlSysxxDO> bdcSlSysxxDOList) {
        this.bdcSlSysxxDOList = bdcSlSysxxDOList;
    }
}
