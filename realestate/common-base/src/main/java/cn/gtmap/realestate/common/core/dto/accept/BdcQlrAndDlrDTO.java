package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcDlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理页面查询权利人和代理人的信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-21 15:03
 **/
public class BdcQlrAndDlrDTO {

    private BdcQlrDO bdcQlrDO;

    private List<BdcDlrDO> bdcDlrDOList;

    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public List<BdcDlrDO> getBdcDlrDOList() {
        return bdcDlrDOList;
    }

    public void setBdcDlrDOList(List<BdcDlrDO> bdcDlrDOList) {
        this.bdcDlrDOList = bdcDlrDOList;
    }
}
