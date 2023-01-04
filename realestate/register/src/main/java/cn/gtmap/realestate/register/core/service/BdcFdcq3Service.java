package cn.gtmap.realestate.register.core.service;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.qo.register.BdcFdcq3QO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/11
 * @description 建筑物区分所有权共有部分
 */
public interface BdcFdcq3Service {
    /**
     * @param xmid 项目ID
     * @return BdcFdcq3DO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询建筑物区分所有权业主共有部分登记信息
     */
    List<BdcFdcq3DO> queryListBdcFdcq3(String xmid);


    /**
     * @param bdcFdcq3GyxxQO 建筑物所有权及业主共有信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 建筑物所有权及业主共有信息更新权利人
     */
    int updateFdcq3Qlr(BdcFdcq3QO bdcFdcq3GyxxQO);
}
