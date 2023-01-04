package cn.gtmap.realestate.building.core.service;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description
 */
public interface BdcdyhService {
    /**
     * @param zdzhdm
     * @param zrzh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过规则创建不动产单元号
     */
    String creatFwBdcdyhByZdzhdmAndZrzh(String zdzhdm, String zrzh);

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过逻辑幢主键创建房屋不动产单元号
     */
    String creatFwBdcdyhByFwDcbIndex(String fwDcbIndex);

    /**
     * @param fwXmxxIndex
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键生成不动产单元号
     */
    String creatXmxxBdcdyhByFwXmxxIndex(String fwXmxxIndex);

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 验证选择的不动产单元是否存在且有实测面积
     */
    String checkBdcdyhSfczscmj(String bdcdyh);

    /**
     * @param bdcdyh,qjgldm
     * @return java.lang.String
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 验证所在宗地不动产单元锁定
     */
    String checkBdcdyhSfsd(String bdcdyh);

    /**
     * @param lszd
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据隶属宗地主键生成项目信息不动产单元号
     */
    String creatXmxxBdcdyhByLszd(String lszd);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param lszd
     * @param zrzh
     * @param num
     * @return java.util.List<java.lang.String>
     * @description 批量获取BDCDYH
     */
    List<String> batchCreateFwBdcdyhByLszdAndZrzh(String lszd,String zrzh,int num);
}
