package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.certificate.core.service.BdcXtFhService;
import cn.gtmap.realestate.certificate.service.BdcZsBdcqzhService;
import cn.gtmap.realestate.common.core.domain.BdcXtZsfhDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXtZsfhDTO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/10
 * @description  回收废号方式获取不动产权证号逻辑类
 */
@Service("bdcBdcqzhFhServiceImpl")
public class BdcBdcqzhFhServiceImpl implements BdcZsBdcqzhService {
    @Autowired
    private BdcXtFhService bdcXtFhService;

    @Autowired
    private BdcBdcqzhGgServiceImpl baseBdcBdcqzhService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcBdcqzhBO 证号业务BO
     * @return {BdcBdcqzhDTO} 不动产权证号信息
     * @description
     *      从废号中获取可用证号
     */
    @Override
    public BdcBdcqzhDTO resolveBdcqzh(BdcBdcqzhBO bdcBdcqzhBO) {
        // 获取可用废号
        BdcXtZsfhDO bdcXtZsfhDO = this.getAvailableZsfh(bdcBdcqzhBO);
        if (null == bdcXtZsfhDO || StringUtils.isBlank(bdcXtZsfhDO.getFczhid())){
            return null;
        }

        // 生成证号
        BdcBdcqzhDTO bdcBdcqzhDTO = baseBdcBdcqzhService.generateDqBdcBdcqzh(bdcBdcqzhBO, bdcXtZsfhDO);

        // 更新废号状态
        bdcXtZsfhDO.setSyqk(CommonConstantUtils.SYQK_YSY);
        bdcXtFhService.updateBdcXtFhSyqk(bdcXtZsfhDO);

        return bdcBdcqzhDTO;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcBdcqzhBO 证号业务BO
     * @return  {BdcXtZsfhDO} 废号信息
     * @description  获取可回收废号
     */
    private BdcXtZsfhDO getAvailableZsfh(BdcBdcqzhBO bdcBdcqzhBO) {
        if(null == bdcBdcqzhBO){
            return null;
        }

        BdcXtZsfhDTO bdcXtZsfhDTO = new BdcXtZsfhDTO();
        bdcXtZsfhDTO.setQxdm(bdcBdcqzhBO.getQxdm());
        bdcXtZsfhDTO.setNf(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        bdcXtZsfhDTO.setZslx(bdcBdcqzhBO.getZslx());

        // 证号是否区分区县代码
        if(null != bdcBdcqzhBO.getZhqfqxdm() && 0 == bdcBdcqzhBO.getZhqfqxdm().intValue()){
            // 不区分的话直接设置空保证查询时候不走区县代码过滤
            bdcXtZsfhDTO.setQxdm(null);
        } else {
            bdcXtZsfhDTO.setQxdm(bdcBdcqzhBO.getQxdm());
        }

        // 证号是否区分登记部门
        if(null != bdcBdcqzhBO.getZhqfbm() && 0 == bdcBdcqzhBO.getZhqfbm().intValue()){
            // 不区分的话直接设置空保证查询时候不走部门过滤
            bdcXtZsfhDTO.setDjbmdm(null);
        } else {
            bdcXtZsfhDTO.setDjbmdm(bdcBdcqzhBO.getDjbmdm());
        }

        return bdcXtFhService.queryBdcXtFh(bdcXtZsfhDTO);
    }
}
