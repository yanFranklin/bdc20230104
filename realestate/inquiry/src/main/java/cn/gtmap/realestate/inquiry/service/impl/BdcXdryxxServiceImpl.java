package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXdryxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXdryQO;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.core.mapper.BdcXdryxxMapper;
import cn.gtmap.realestate.inquiry.service.BdcXdryxxService;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: 3.0
 * @description: 限定人员信息方法实现层
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-03 13:33
 **/
@Service
public class BdcXdryxxServiceImpl implements BdcXdryxxService {

    @Autowired
    BdcXdryxxMapper bdcXdryxxMapper;
    @Autowired
    EntityMapper entityMapper;

    /**
     * @param bdcXdryQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询限定人员信息
     * @date : 2021/8/3 13:30
     */
    @Override
    public List<BdcXdryxxDO> listBdcXdyrxx(BdcXdryQO bdcXdryQO) {
        return bdcXdryxxMapper.listBdcXdyrxxByPage(bdcXdryQO);
    }

    /**
     * @param idList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除限定人员信息
     * @date : 2021/8/3 13:32
     */
    @Override
    public void deletXdryxx(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            Map paramMap = new HashMap(1);
            paramMap.put("idList", idList);
            bdcXdryxxMapper.deleteXdryxx(paramMap);
        }

    }

    /**
     * @param bdcXdryxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增限定人员信息
     * @date : 2021/8/3 13:33
     */
    @Override
    public BdcXdryxxDO addBdcXdryxx(BdcXdryxxDO bdcXdryxxDO) {
        if (bdcXdryxxDO == null) {
            throw new MissingArgumentException("保存限定人员信息缺失必要参数");
        }
        if (StringUtils.isBlank(bdcXdryxxDO.getId())) {
            bdcXdryxxDO.setId(UUIDGenerator.generate16());
        }
        entityMapper.insertSelective(bdcXdryxxDO);
        return bdcXdryxxDO;
    }

    /**
     * @param bdcXdryxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新限定人员信息
     * @date : 2021/8/3 13:33
     */
    @Override
    public BdcXdryxxDO updateBdcXdryxx(BdcXdryxxDO bdcXdryxxDO) {
        if (Objects.nonNull(bdcXdryxxDO) && StringUtils.isNotBlank(bdcXdryxxDO.getId())) {
            entityMapper.updateByPrimaryKeySelective(bdcXdryxxDO);
        }
        return bdcXdryxxDO;
    }
}
