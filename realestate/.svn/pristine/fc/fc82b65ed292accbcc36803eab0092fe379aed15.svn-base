package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtZsbhmbDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.natural.core.service.ZrzyXtZsbhmbService;
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
 * @author wyh
 * @version 1.0
 * @date 2021/11/16 14:44
 */
@Service
public class ZrzyXtZsbhmbServiceImpl implements ZrzyXtZsbhmbService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyXtZsbhmbServiceImpl.class);

    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = ZrzyXtZsbhmbServiceImpl.class.getName();

    /**
     * ORM操作
     */
    @Autowired
    private EntityMapper entityMapper;

    /**
     * 证号是否区分区县代码，如果没有配置则默认1，即默认是需要按照区县代码区分
     */
    @Value("${zhqfqxdm:1}")
    private Integer zhqfqxdm;

    /**
     * @param zrzyXmDO 不动产项目信息实体
     * @return {BdcXtZsbhmbDO} 证书编号模板配置信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取证书编号模板
     */
    @Override
    public ZrzyXtZsbhmbDO queryZrzyXtZsbhmb(ZrzyXmDO zrzyXmDO) {
        if (null == zrzyXmDO || StringUtils.isBlank(zrzyXmDO.getQxdm())) {
            LOGGER.warn("{}：生成证号：获取证书编号模板终止，因为项目不存在或者项目区县代码QXDM字段为空！", CLASS_NAME);
            return null;
        }

        // 获取当前年份
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        // 查询证号模板
        Example example = new Example(ZrzyXtZsbhmbDO.class);
        Example.Criteria criteria = example.createCriteria();
        //criteria.andEqualTo("nf", year);

        // 判断证号是否区分区县代码，0 表示不区分部分，查询模板时不需要按照区县代码区分
        if (null == zhqfqxdm || 1 == zhqfqxdm) {
           // criteria.andEqualTo("qxdm", zrzyXmDO.getQxdm());
        }

        List<ZrzyXtZsbhmbDO> zsbhmbDOList = entityMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(zsbhmbDOList)) {
            return null;
        }
        return (ZrzyXtZsbhmbDO) CollectionUtils.get(zsbhmbDOList, 0);
    }
}
