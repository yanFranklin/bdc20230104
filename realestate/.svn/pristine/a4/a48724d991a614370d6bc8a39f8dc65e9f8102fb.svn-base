package cn.gtmap.realestate.accept.core.service.impl;


import cn.gtmap.realestate.accept.config.FdjywConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlCdxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlFdjywService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmLsgxService;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcCdxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 查档信息实现类
 * @date : 2021/8/10 16:19
 */
@Service
public class BdcSlCdxxServiceImpl implements BdcSlFdjywService, BdcSlCdxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlCdxxServiceImpl.class);

    @Autowired
    FdjywConfig fdjywConfig;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;

    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        List<String> cdxxGzldyidList = fdjywConfig.getFdjywlcDyidList("cdxx");
        if (CollectionUtils.isNotEmpty(cdxxGzldyidList)) {
            set.addAll(cdxxGzldyidList);
        } else {
            set.add("cdxx");
        }
        return set;
    }

    @Override
    public void initFdjywxx(BdcSlCshDTO bdcSlCshDTO) {
        LOGGER.info("开始初始化查档信息{}", JSON.toJSONString(bdcSlCshDTO));
        if (CollectionUtils.isNotEmpty(bdcSlCshDTO.getBdcSlXmDOList())) {
            List<BdcQlrDO> bdcQlrDOList = new ArrayList<>(CollectionUtils.size(bdcSlCshDTO.getBdcSlXmDOList()));
            List<BdcSlCdxxDO> bdcSlCdxxDOLsit = new ArrayList<>(CollectionUtils.size(bdcSlCshDTO.getBdcSlXmDOList()));
            for (BdcSlXmDO bdcSlXmDO : bdcSlCshDTO.getBdcSlXmDOList()) {
                BdcSlCdxxDO bdcSlCdxxDO = new BdcSlCdxxDO();
                bdcSlCdxxDO.setXmid(bdcSlXmDO.getXmid());
                bdcSlCdxxDO.setCdxxid(UUIDGenerator.generate16());
                bdcSlCdxxDO.setCdlb(bdcSlCshDTO.getCdlb());
                bdcSlCdxxDO.setXcxr(bdcSlCshDTO.getCdqlr());
                bdcSlCdxxDO.setXcxrzjh(bdcSlCshDTO.getCdqlrZjh());
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", CommonConstantUtils.SF_F_DM);
                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                    String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                    //如果证件号不为空，名称为空，则根据证件号查询权利人表的数据
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    bdcQlrQO.setXmid(yxmid);
                    bdcQlrDOList.addAll(bdcQlrFeignService.listBdcQlr(bdcQlrQO));
                }
                bdcSlCdxxDO.setCqzh(StringUtils.isNotBlank(bdcSlCshDTO.getCdcqzh()) ? bdcSlCshDTO.getCdcqzh() : bdcSlXmDO.getYbdcqz());
                bdcSlCdxxDO.setZl(StringUtils.isNotBlank(bdcSlCshDTO.getCdzl()) ? bdcSlCshDTO.getCdzl() : bdcSlXmDO.getZl());
                bdcSlCdxxDOLsit.add(bdcSlCdxxDO);
            }
            //处理名称和证件号
            String xcxr = bdcSlCshDTO.getCdqlr();
            String xcxrzjh = bdcSlCshDTO.getCdqlrZjh();
            Map<String, String> qlrMap = new HashMap(1);
            LOGGER.info("上一手权利人信息{}", JSON.toJSONString(bdcQlrDOList));
            qlrMap = bdcQlrDOList.stream().filter(bdcQlrDO -> StringUtils.isNotBlank(bdcQlrDO.getQlrmc()) && StringUtils.isNotBlank(bdcQlrDO.getZjh()))
                    .collect(Collectors.toMap(BdcQlrDO::getQlrmc, BdcQlrDO::getZjh, (key1, key2) -> key1));
            if (StringUtils.isNotBlank(bdcSlCshDTO.getCdqlrZjh()) && StringUtils.isBlank(bdcSlCshDTO.getCdqlr())) {
                //如果证件号不为空，名称为空
                qlrMap = MapUtils.invertMap(qlrMap);
                xcxr = qlrMap.get(bdcSlCshDTO.getCdqlrZjh());
            }
            //如果只有名称没有证件号，查询上一手的权利人信息
            if (StringUtils.isNotBlank(bdcSlCshDTO.getCdqlr()) && StringUtils.isBlank(bdcSlCshDTO.getCdqlrZjh())) {
                xcxrzjh = qlrMap.get(bdcSlCshDTO.getCdqlr());
            }
            //名称证件号都为空，插上一手的权利人sxh 为1 的作为xcxr
            if (StringUtils.isBlank(xcxr) && StringUtils.isBlank(xcxrzjh) &&
                    (StringUtils.isNotBlank(bdcSlCshDTO.getCdzl()) || StringUtils.isNotBlank(bdcSlCshDTO.getCdcqzh()))) {
                LOGGER.info("查询时权利人和证件号为空，坐落或者产权证不为空，查询上一手权利人sxh 为1 的作为需查询人");
                bdcQlrDOList = bdcQlrDOList.stream().filter(bdcQlrDO -> (Objects.nonNull(bdcQlrDO.getSxh()) && Objects.equals(1, bdcQlrDO.getSxh()))).collect(Collectors.toList());
                xcxr = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getQlrmc", CommonConstantUtils.ZF_YW_DH);
                xcxrzjh = StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getZjh", CommonConstantUtils.ZF_YW_DH);
            }
            for (BdcSlCdxxDO bdcSlCdxxDO : bdcSlCdxxDOLsit) {
                bdcSlCdxxDO.setXcxr(xcxr);
                bdcSlCdxxDO.setXcxrzjh(xcxrzjh);
            }
            entityMapper.insertBatchSelective(bdcSlCdxxDOLsit);
        }
    }

    /**
     * @param bdcCdxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询查档信息
     * @date : 2020/9/23 17:21
     */
    @Override
    public BdcSlCdxxDO queryBdcCdxx(BdcCdxxQO bdcCdxxQO) {
        List<BdcSlCdxxDO> bdcSlCdxxDOList = new ArrayList<>(1);
        Example example = new Example(BdcSlCdxxDO.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bdcCdxxQO.getCdxxid())) {
            return entityMapper.selectByPrimaryKey(BdcSlCdxxDO.class, bdcCdxxQO.getCdxxid());
        }
        if (StringUtils.isNotBlank(bdcCdxxQO.getXmid())) {
            criteria.andEqualTo("xmid", bdcCdxxQO.getXmid());
            bdcSlCdxxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlCdxxDOList)) {
                return bdcSlCdxxDOList.get(0);
            }
        }
        return new BdcSlCdxxDO();
    }

    /**
     * @param bdcSlCdxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存查档信息数据
     * @date : 2020/9/23 17:29
     */
    @Override
    public BdcSlCdxxDO saveBdcCdxx(BdcSlCdxxDO bdcSlCdxxDO) {
        if (StringUtils.isBlank(bdcSlCdxxDO.getCdxxid())) {
            bdcSlCdxxDO.setCdxxid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlCdxxDO);
        } else {
            entityMapper.updateByPrimaryKey(bdcSlCdxxDO);
        }
        return bdcSlCdxxDO;
    }

    /**
     * @param cdxxid
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除查档信息
     * @date : 2020/9/23 17:37
     */
    @Override
    public int deleteBdcCdxx(String cdxxid, String xmid) {
        if (StringUtils.isNotBlank(cdxxid)) {
            return entityMapper.deleteByPrimaryKey(BdcSlCdxxDO.class, cdxxid);
        } else if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlCdxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            return entityMapper.deleteByExample(example);
        } else {
            return -1;
        }
    }
}
