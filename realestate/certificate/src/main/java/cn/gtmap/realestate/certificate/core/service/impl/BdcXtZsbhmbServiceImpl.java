package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.service.BdcXtZsbhmbService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/4
 * @description 证书编号模板配置表DAO操作
 */
@Service
public class BdcXtZsbhmbServiceImpl implements BdcXtZsbhmbService{
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXtZsbhmbServiceImpl.class);

    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = BdcXtZsbhmbServiceImpl.class.getName();

    /**
     * ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;

    /**
     * 证号是否区分部门，如果没有配置则默认1，即默认是需要按照登记部门区分
     */
    @Value("${zhqfbm:1}")
    private Integer zhqfbm;

    /**
     * 证号是否区分区县代码，如果没有配置则默认1，即默认是需要按照区县代码区分
     */
    @Value("${zhqfqxdm:1}")
    private Integer zhqfqxdm;

    /**
     * @param bdcXmDO 不动产项目信息实体
     * @return {BdcXtZsbhmbDO} 证书编号模板配置信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取证书编号模板
     */
    @Override
    public BdcXtZsbhmbDO queryBdcXtZsbhmb(BdcXmDO bdcXmDO) {
        if (null == bdcXmDO || StringUtils.isBlank(bdcXmDO.getQxdm())) {
            LOGGER.warn("{}：生成证号：获取证书编号模板终止，因为项目不存在或者项目区县代码QXDM字段为空！", CLASS_NAME);
            return null;
        }

        // 获取当前年份
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        // 查询证号模板
        Example example = new Example(BdcXtZsbhmbDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("nf", year);

        // 判断证号是否区分区县代码，0 表示不区分部分，查询模板时不需要按照区县代码区分
        if (null == zhqfqxdm || 1 == zhqfqxdm) {
            criteria.andEqualTo("qxdm", bdcXmDO.getQxdm());
        }
        // 判断证号是否区分部门，0 表示不区分部分，查询模板时不需要按照部门区分
        if (null == zhqfbm || 1 == zhqfbm) {
            criteria.andEqualTo("djbmdm", bdcXmDO.getDjbmdm());
        }

        List<BdcXtZsbhmbDO> zsbhmbDOList = entityMapper.selectByExampleNotNull(example);
        if (CollectionUtils.isEmpty(zsbhmbDOList)) {
            return null;
        }
        return (BdcXtZsbhmbDO) CollectionUtils.get(zsbhmbDOList, 0);
    }
}
