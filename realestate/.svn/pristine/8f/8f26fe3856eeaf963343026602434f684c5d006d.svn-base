package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.config.FdjywConfig;
import cn.gtmap.realestate.accept.core.service.*;
import cn.gtmap.realestate.accept.service.BdcLshService;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlFghysfDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/10/22/9:39
 * @Description:
 */
@Service
public class BdcSlFgyhsfServiceImpl implements BdcSlFgyhsfService,BdcSlFdjywService {

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    FdjywConfig fdjywConfig;

    @Autowired
    BdcLshService bdcLshService;

    @Autowired
    BdcSlXmLsgxService bdcSlXmLsgxService;

    @Autowired
    BdcSlXmService bdcSlXmService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlJbxxService bdcSlJbxxService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;
    /**
     * @param bdcSlFgyhsfDO 不动产受理房改优惠售房信息
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 新增不动产受理房屋信息
     */
    @Override
    public Integer saveOrUpdateBdcSlFgyhsf(BdcSlFgyhsfDO bdcSlFgyhsfDO) {
        if (bdcSlFgyhsfDO == null) {
            throw new MissingArgumentException("缺失参数");
        }
        if (StringUtils.isBlank(bdcSlFgyhsfDO.getFgyhsfid())) {
            bdcSlFgyhsfDO.setFgyhsfid(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcSlFgyhsfDO, bdcSlFgyhsfDO.getFgyhsfid());
    }


    @Override
    public void initFdjywxx(BdcSlCshDTO bdcSlCshDTO) {
        BdcSlFgyhsfDO bdcSlFgyhsfDO = new BdcSlFgyhsfDO();
        bdcSlFgyhsfDO.setGzlslid(bdcSlCshDTO.getGzlslid());
        bdcSlFgyhsfDO.setFgyhsfid(UUIDGenerator.generate16());
        bdcSlFgyhsfDO.setSlbh(bdcSlCshDTO.getSlbh());
        String xzbh = bdcLshService.queryLsh("fgyhsf", "ALL")+"";
        bdcSlFgyhsfDO.setXzbh(xzbh);
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlCshDTO.getBdcSlXmDOList();
        if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
            BdcSlXmDO bdcSlXmDO =bdcSlCshDTO.getBdcSlXmDOList().get(0);
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList =bdcSlXmLsgxService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(),"",null);
            if(CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)){
                String yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                BdcXmQO ybdcXmQO = new BdcXmQO();
                ybdcXmQO.setXmid(yxmid);
                List<BdcXmDO> yBdcXmDOList = bdcXmFeignService.listBdcXm(ybdcXmQO);
                if (CollectionUtils.isNotEmpty(yBdcXmDOList)) {
                    bdcSlFgyhsfDO.setYfwsyzh( yBdcXmDOList.get(0).getBdcqzh());
                    bdcSlFgyhsfDO.setFwsyqr(yBdcXmDOList.get(0).getQlr());
                    bdcSlFgyhsfDO.setYxmid(yBdcXmDOList.get(0).getXmid());
                }
            }
        }
        entityMapper.insert(bdcSlFgyhsfDO);
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 根据工作流实例id获取不动产受理房屋信息
     */
    @Override
    public BdcSlFgyhsfDO getBdcSlFgyhsf(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失参数gzlslid");
        }
        Example example = new Example(BdcSlFgyhsfDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzlslid", gzlslid);
        List <BdcSlFgyhsfDO> listBdcSlFgyhsfDO =  entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(listBdcSlFgyhsfDO)){
            return listBdcSlFgyhsfDO.get(0);
        }
        return null;
    }

    @Override
    public BdcSlFghysfDTO getBdcSlFghysfDTO(String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("缺失参数gzlslid");
        }
        BdcSlFghysfDTO bdcSlFghysfDTO = new BdcSlFghysfDTO();
        Example example = new Example(BdcSlFgyhsfDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzlslid", gzlslid);
        List <BdcSlFgyhsfDO> listBdcSlFgyhsfDO =  entityMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(listBdcSlFgyhsfDO)){
            BdcSlFgyhsfDO bdcSlFghysfDO = listBdcSlFgyhsfDO.get(0);
            bdcSlFghysfDTO.setBdcSlFgyhsfDO(bdcSlFghysfDO);
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if(bdcSlJbxxDO != null) {
                bdcSlFghysfDTO.setBdcSlJbxxDO(bdcSlJbxxDO);
            }
            if(StringUtils.isNotBlank(bdcSlFghysfDO.getYxmid())) {
                BdcXmQO ybdcXmQO = new BdcXmQO();
                ybdcXmQO.setXmid(bdcSlFghysfDO.getYxmid());
                List<BdcXmDO> yBdcXmDOList = bdcXmFeignService.listBdcXm(ybdcXmQO);
                if (CollectionUtils.isNotEmpty(yBdcXmDOList)) {
                    bdcSlFghysfDTO.setYxmxx(yBdcXmDOList.get(0));
                }
                BdcXmFbQO bdcXmFbQO =new BdcXmFbQO();
                bdcXmFbQO.setXmid(bdcSlFghysfDO.getYxmid());
                List<BdcXmFbDO> bdcXmFbDOList =bdcXmfbFeignService.listBdcXmFb(bdcXmFbQO);
                if(CollectionUtils.isNotEmpty(bdcXmFbDOList)){
                    bdcSlFghysfDTO.setFwxmfb(bdcXmFbDOList.get(0));
                }else{
                    bdcSlFghysfDTO.setFwxmfb(new BdcXmFbDO());
                }
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcSlFghysfDO.getYxmid());
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if(CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    bdcSlFghysfDTO.setQlrDOList(bdcQlrDOList);
                }
                BdcQl bdcQl = bdcQllxFeignService.queryQlxx(bdcSlFghysfDO.getYxmid());
                if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO BdcFdcqDO = (BdcFdcqDO) bdcQl;
                    bdcSlFghysfDTO.setBdcFdcqDO(BdcFdcqDO);
                }
            }
        }
        return bdcSlFghysfDTO;
    }


    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        List<String> fgyhsfGzldyidList = fdjywConfig.getFdjywlcDyidList("fgyhsf");
        if (CollectionUtils.isNotEmpty(fgyhsfGzldyidList)) {
            set.addAll(fgyhsfGzldyidList);
        } else {
            set.add("fgyhsf");
        }
        return set;
    }

}
