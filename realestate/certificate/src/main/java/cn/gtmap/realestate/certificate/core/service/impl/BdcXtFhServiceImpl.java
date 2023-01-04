package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcXtFhMapper;
import cn.gtmap.realestate.certificate.core.service.BdcXtFhService;
import cn.gtmap.realestate.common.core.domain.BdcXtZsfhDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXtZsfhDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/5
 * @description 不动产权证号DAO操作
 */
@Service
public class BdcXtFhServiceImpl implements BdcXtFhService {
    /**
     * ORM操作
     */
    @Autowired
    private BdcXtFhMapper bdcXtFhMapper;

    @Autowired
    private EntityMapper entityMapper;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsfhDTO 废号查询实体
     * @return {BdcXtZsfhDO} 废号信息
     * @description 获取指定区域当前年度可用废号
     */
    @Override
    public BdcXtZsfhDO queryBdcXtFh(BdcXtZsfhDTO bdcXtZsfhDTO) {
        if(null == bdcXtZsfhDTO ||  StringUtils.isBlank(bdcXtZsfhDTO.getQxdm())){
            return null;
        }

        List<BdcXtZsfhDO> bdcXtZsfhDOList = bdcXtFhMapper.queryAvailableBdcXtFh(bdcXtZsfhDTO);
        if(CollectionUtils.isEmpty(bdcXtZsfhDOList)){
            return null;
        }

        return bdcXtZsfhDOList.get(0);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcXtZsfhDO 废号信息实体
     * @return {int} 更新记录条数
     * @description 更新预留证号使用情况
     */
    @Override
    public int updateBdcXtFhSyqk(BdcXtZsfhDO bdcXtZsfhDO) {
        if(null == bdcXtZsfhDO || StringUtils.isBlank(bdcXtZsfhDO.getFczhid())){
            return 0;
        }

        bdcXtZsfhDO.setGxrq(new Date());
        return entityMapper.updateByPrimaryKeySelective(bdcXtZsfhDO);
    }
}
