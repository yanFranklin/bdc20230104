package cn.gtmap.realestate.building.service;


import cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-12
 * @description 为DOZER对照功能提供的反射方法 BdcdyxxEnum.java中调用
 */
public interface DjxxDozerService<T,S> {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return T
     * @description 判断BDCDYH，如果构筑物特征码是W 则直接查询DJDCB，
     * 否则截取地籍号搜索 针对使用构筑物BDCDYH查询宗地调查表的场景
     */
    T queryDjdcbByBdcdyhOrDjh(String bdcdyh);


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO
     * @description 根据不动产单元号查询权利人和调查表信息
     */
    DjDcbAndQlrResponseDTO queryDjdcbAndQlrByBdcdyh(String bdcdyh,boolean withQlr);

    /**
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdQsdcDO
     * @description 根据djh查询权属调查信息
     */
    ZdQsdcDO queryQsdcByDjh(String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description 根据BDCDYH判断是否需要查询DJDCB
     */
    boolean checkNeedDjdcb(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return boolean
     * @description  验证是否需要查询DJ QLR
     */
    boolean checkNeedDjQlr(String bdcdyh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @param qlr
     * @return void
     * @description 给权利人set外键
     */
    void setDjQlrFkVal(String bdcdyh,S qlr);
}
