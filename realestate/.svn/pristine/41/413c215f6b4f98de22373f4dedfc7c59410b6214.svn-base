package cn.gtmap.realestate.init.core.service;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/19
 * @description 不动产单元信息服务
 */
public interface BdcBdcdyService {
    /**
     * 通过不动产单元号判断当前不动产类型是否是房屋（是返回true，否返回false）
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcdyh
     *@return boolean 是否是房屋
     *@description 通过不动产单元号判断当前不动产类型是否是房屋（是返回true，否返回false）
     */
    Boolean judgeIsFwByBdcdyh(String bdcdyh);

    /**
     * 判断不动产单元号的宗地类型
     * @param bdcdyh
     * @param zdlxArr
     * @return
     */
    Boolean judgeZdlxInSourceByBdcdyh(String bdcdyh,String[] zdlxSource);
    /**
     * 回写状态到权籍
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcdyh
     *@description 回写状态到权籍
     */
    void syncBdcdyZtByBdcdyh(@NotBlank(message = "不动产单元号不能为空") String bdcdyh,String qjgldm);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 回写状态到权籍
     */
    void syncBdcdyZtByXmid(@NotBlank(message = "项目ID不能为空") String xmid);
}
