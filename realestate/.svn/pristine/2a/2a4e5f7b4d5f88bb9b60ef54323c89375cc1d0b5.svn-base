package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhcxDyaDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.Object2MapUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcYhcxMapper;
import cn.gtmap.realestate.inquiry.service.BdcYhcxService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/11/28
 * @description
 */
@Service
public class BdcYhcxServiceImpl implements BdcYhcxService{
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcYhcxMapper bdcYhcxMapper;

    @Override
    public List<BdcXmDO> listBdcXmByZsid(String zsid) {
        return bdcYhcxMapper.listZsXmByZsid(zsid);
    }

    @Override
    public List<BdcCfDO> listBdcCfByBdcdyh(List<String> bdcdyhList) {
        List<BdcCfDO> bdcCfDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            for (String bdcdyh : bdcdyhList) {
                Example example = new Example(BdcCfDO.class);
                Example.Criteria criteria = example.createCriteria().andEqualTo("bdcdyh",bdcdyh);
                criteria.andEqualTo("qszt", 1);
                List<BdcCfDO> bdcCfDOS = entityMapper.selectByExample(example);
                // 当存在查封信息时 存入查封list
                if (CollectionUtils.isNotEmpty(bdcCfDOS)){
                    bdcCfDOList.addAll(bdcCfDOS);
                }
            }
        }
        return bdcCfDOList;
    }

    @Override
    public List<BdcYhcxDyaDTO> listBdcDyaByBdcdyh(List<String> bdcdyhList) {
        List<BdcYhcxDyaDTO> bdcDyaqDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            for (String bdcdyh : bdcdyhList) {
                List<BdcYhcxDyaDTO> bdcYhcxDyaDTOList = bdcYhcxMapper.listYhcxDyaByBdcdyh(bdcdyh);
                // 当存在查封信息时 存入查封list
                if (CollectionUtils.isNotEmpty(bdcYhcxDyaDTOList)){
                    bdcDyaqDOList.addAll(bdcYhcxDyaDTOList);
                }
            }
        }
        return bdcDyaqDOList;
    }

    @Override
    public List<BdcYyDO> listBdcYyByBdcdyh(List<String> bdcdyhList) {
        List<BdcYyDO> bdcYyDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            for (String bdcdyh : bdcdyhList) {
                Example example = new Example(BdcYyDO.class);
                Example.Criteria criteria = example.createCriteria().andEqualTo("bdcdyh",bdcdyh);
                criteria.andEqualTo("qszt", 1);
                List<BdcYyDO> bdcYyDOS = entityMapper.selectByExample(example);
                // 当存在查封信息时 存入查封list
                if (CollectionUtils.isNotEmpty(bdcYyDOS)){
                    bdcYyDOList.addAll(bdcYyDOS);
                }
            }
        }
        return bdcYyDOList;
    }

    @Override
    public List<String> listBdcSdxxByBdcdyh(List<String> bdcdyhList) {
        List<String> bdcSdxxDOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            for (String bdcdyh : bdcdyhList) {
                List<String> bdcYhcxSdxxList = bdcYhcxMapper.listYhcxSdxxByBdcdyh(bdcdyh);
                // 当存在锁定信息时 存入锁定list
                if (CollectionUtils.isNotEmpty(bdcYhcxSdxxList)){
                    bdcSdxxDOList.addAll(bdcYhcxSdxxList);
                }
            }
        }
        return bdcSdxxDOList;
    }

    @Override
    public List<BdcZsDO> listBdcZs(BdcZsQO bdcZsQO) {
        Map map = Object2MapUtils.object2MapExceptNull(bdcZsQO);
        return bdcYhcxMapper.listBdcZs(map);
    }
}
