package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlFgyhsfDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/27/14:17
 * @Description: 房改优惠售房DTO
 */
public class BdcSlFghysfDTO {
    /**
     * 房屋接轨信息
     */
    private BdcSlFgyhsfDO bdcSlFgyhsfDO;
    /**
     * 受理基本信息
     */
    private BdcSlJbxxDO bdcSlJbxxDO;
    /**
     * 权利人list
     */
    private List<BdcQlrDO>  qlrDOList;
    /**
     * 原项目信息
     */
    private BdcXmDO yxmxx;

    /**
     * 房屋项目附表
     */
    private BdcXmFbDO fwxmfb;
    /**
     * 房地产权
     */
    private BdcFdcqDO bdcFdcqDO;


    public BdcSlFgyhsfDO getBdcSlFgyhsfDO() {
        return bdcSlFgyhsfDO;
    }

    public void setBdcSlFgyhsfDO(BdcSlFgyhsfDO bdcSlFgyhsfDO) {
        this.bdcSlFgyhsfDO = bdcSlFgyhsfDO;
    }

    public BdcSlJbxxDO getBdcSlJbxxDO() {
        return bdcSlJbxxDO;
    }

    public void setBdcSlJbxxDO(BdcSlJbxxDO bdcSlJbxxDO) {
        this.bdcSlJbxxDO = bdcSlJbxxDO;
    }

    public List<BdcQlrDO> getQlrDOList() {
        return qlrDOList;
    }

    public void setQlrDOList(List<BdcQlrDO> qlrDOList) {
        this.qlrDOList = qlrDOList;
    }

    public BdcXmDO getYxmxx() {
        return yxmxx;
    }

    public void setYxmxx(BdcXmDO yxmxx) {
        this.yxmxx = yxmxx;
    }

    public BdcXmFbDO getFwxmfb() {
        return fwxmfb;
    }

    public void setFwxmfb(BdcXmFbDO fwxmfb) {
        this.fwxmfb = fwxmfb;
    }

    public BdcFdcqDO getBdcFdcqDO() {
        return bdcFdcqDO;
    }

    public void setBdcFdcqDO(BdcFdcqDO bdcFdcqDO) {
        this.bdcFdcqDO = bdcFdcqDO;
    }
}
