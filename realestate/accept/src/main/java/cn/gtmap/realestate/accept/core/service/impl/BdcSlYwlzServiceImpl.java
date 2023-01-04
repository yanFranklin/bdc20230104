package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.config.FdjywConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlFdjywService;
import cn.gtmap.realestate.accept.core.service.BdcSlYwlzService;
import cn.gtmap.realestate.accept.service.BdcBhService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYwlzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: realestate
 * @description: 受理业务流转方法实现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-22 10:40
 **/
@Service
public class BdcSlYwlzServiceImpl implements BdcSlYwlzService, BdcSlFdjywService {
    @Autowired
    FdjywConfig fdjywConfig;
    @Autowired
    BdcBhService bdcBhService;
    @Autowired
    EntityMapper entityMapper;

    /**
     * @param bdcSlCshDTO 受理初始化对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化非登记业务信息
     */
    @Override
    public void initFdjywxx(BdcSlCshDTO bdcSlCshDTO) {
        BdcSlYwlzDO bdcSlYwlzDO = new BdcSlYwlzDO();
        if (CollectionUtils.isNotEmpty(bdcSlCshDTO.getBdcSlXmDOList())) {
            bdcSlYwlzDO.setYwlzbh(bdcBhService.queryCommonBh(CommonConstantUtils.YWLZBH, CommonConstantUtils.ZZSJFW_YEAR, 4, ""));
            for (BdcSlXmDO bdcSlXmDO : bdcSlCshDTO.getBdcSlXmDOList()) {
                bdcSlYwlzDO.setBdcqzh(bdcSlXmDO.getYbdcqz());
                bdcSlYwlzDO.setGzlslid(bdcSlCshDTO.getGzlslid());
                bdcSlYwlzDO.setXmid(bdcSlXmDO.getXmid());
                bdcSlYwlzDO.setYwlzid(UUIDGenerator.generate16());
                //如果单元号和坐落都为空表示无数据创建，业务来源字段ywly为1（有数据创建） ，否则为2（无数据创建）
                if (StringUtils.isBlank(bdcSlXmDO.getBdcdyh()) && StringUtils.isBlank(bdcSlXmDO.getZl())) {
                    bdcSlYwlzDO.setYwly(2);
                } else {
                    bdcSlYwlzDO.setYwly(1);
                }
                entityMapper.insertSelective(bdcSlYwlzDO);
            }
        }
    }

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        List<String> ywlzGzldyidList = fdjywConfig.getFdjywlcDyidList("ywlz");
        if (CollectionUtils.isNotEmpty(ywlzGzldyidList)) {
            set.addAll(ywlzGzldyidList);
        } else {
            set.add("ywlz");
        }
        return set;
    }

    /**
     * @param bdcSlYwlzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务流转信息
     * @date : 2021/9/22 11:10
     */
    @Override
    public List<BdcSlYwlzDO> listBdcSlYwlz(BdcSlYwlzDO bdcSlYwlzDO) {
        if (CheckParameter.checkAnyParameter(bdcSlYwlzDO)) {
            return entityMapper.selectByObj(bdcSlYwlzDO);
        }
        return Collections.emptyList();
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除业务流转信息
     * @date : 2021/9/22 11:11
     */
    @Override
    public int delteBdcSlYwlz(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlYwlzDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.deleteByExample(example);
        }
        return 0;

    }
}
