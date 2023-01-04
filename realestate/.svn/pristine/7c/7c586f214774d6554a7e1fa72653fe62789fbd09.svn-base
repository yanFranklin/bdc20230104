package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcXtYlzhService;
import cn.gtmap.realestate.certificate.service.BdcZsBdcqzhService;
import cn.gtmap.realestate.common.core.domain.BdcXtYlzhDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/10
 * @description  以预留证号方式处理不动产权证号
 */
@Service("bdcBdcqzhYlzhServiceImpl")
public class BdcBdcqzhYlzhServiceImpl implements BdcZsBdcqzhService {
    @Autowired
    protected BdcXtYlzhService bdcXtYlzhService;

    @Autowired
    private BdcBdcqzhGgServiceImpl baseBdcBdcqzhService;


    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcBdcqzhBO 证号业务BO
     * @return  {BdcBdcqzhDTO} 证号信息实体
     * @description 从预留证号中获取对应项目选取的证号
     */
    @Override
    public BdcBdcqzhDTO resolveBdcqzh(BdcBdcqzhBO bdcBdcqzhBO) {
        // 查询项目选用的预留证号
        BdcXtYlzhDO bdcXtYlzhDO = bdcXtYlzhService.queryBdcXtYlzh(bdcBdcqzhBO.getZsid());
        if(null == bdcXtYlzhDO || StringUtils.isBlank(bdcXtYlzhDO.getYlzhid())) {
            return null;
        }

        // 生成当前证号
        BdcBdcqzhDTO bdcBdcqzhDTO = baseBdcBdcqzhService.generateDqBdcBdcqzh(bdcBdcqzhBO, bdcXtYlzhDO);

        // 更新预留证号状态
        bdcXtYlzhDO.setSyqk(CommonConstantUtils.SYQK_YSY);
        bdcXtYlzhDO.setZsid(bdcBdcqzhBO.getZsid());
        bdcXtYlzhService.updateBdcXtYlzhSyqk(bdcXtYlzhDO);

        return bdcBdcqzhDTO;
    }
}
