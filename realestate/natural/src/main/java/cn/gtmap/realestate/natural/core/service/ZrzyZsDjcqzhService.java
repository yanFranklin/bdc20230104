package cn.gtmap.realestate.natural.core.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyCqzhDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsQO;
import cn.gtmap.realestate.natural.core.bo.ZrzyZscCqzhBO;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/4
 * @description 证书生成关联证号接口定义
 */
public interface ZrzyZsDjcqzhService {
    /**
     * @param bdcBdcqzhBO 证号业务BO
     * @return {BdcBdcqzhDTO} 不动产权证号信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成单个不动产项目证书（明）号
     */
    public ZrzyCqzhDTO resolveZrzycqzh(ZrzyZscCqzhBO bdcBdcqzhBO);
}
