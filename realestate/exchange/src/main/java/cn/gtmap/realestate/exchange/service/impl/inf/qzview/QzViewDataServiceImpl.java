package cn.gtmap.realestate.exchange.service.impl.inf.qzview;

import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.domain.DvBzcxDo;
import cn.gtmap.realestate.exchange.core.domain.DvHlwDo;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.ydjxt.DvHlwQlrResponseDto;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.ydjxt.DvHlwResponseDto;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.service.inf.qzview.QzViewDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/6/2
 * @description
 */
@Service
public class QzViewDataServiceImpl implements QzViewDataService {
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public DvBzcxDo dvBzcxDoByHlwslbh(String hlwslbh) {
        if (StringUtils.isBlank(hlwslbh)) {
            throw new MissingArgumentException("查询受理编号不能为空");
        }
        List<DvBzcxDo> bzcxDoList = null;
        Example example = new Example(DvBzcxDo.class);
        example.createCriteria().andEqualTo("wregnum", hlwslbh);
        bzcxDoList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bzcxDoList)) {
            return bzcxDoList.get(0);
        }
        return new DvBzcxDo();
    }

    @Override
    public DvBzcxDo dvBzcxDoByNwslbh(String nwslbh) {
        if (StringUtils.isBlank(nwslbh)) {
            throw new MissingArgumentException("查询受理编号不能为空");
        }
        List<DvBzcxDo> bzcxDoList = null;
        Example example = new Example(DvBzcxDo.class);
        example.createCriteria().andEqualTo("nregnum", nwslbh);
        bzcxDoList = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(bzcxDoList)) {
            return bzcxDoList.get(0);
        }
        return new DvBzcxDo();
    }

    @Override
    public List<DvHlwResponseDto> dvHlwListByParamMap(GrdacxRequestBody param) {
        List<DvHlwResponseDto> dvHlwDoList = new ArrayList<>();
        if (CheckParameter.checkAnyParameter(param, "qlrmc", "qlrzjh", "zl", "roomid", "sgbh", "bdcqzh")) {
            Example example = new Example(DvHlwDo.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(param.getQlrmc())) {
                criteria.andEqualTo("qlrmc", param.getQlrmc());
            }
            if (StringUtils.isNotBlank(param.getQlrzjh())) {
                criteria.andEqualTo("qlrzjh", param.getQlrzjh());
            }
            if (StringUtils.isNotBlank(param.getRoomid())) {
                criteria.andEqualTo("roomid", param.getRoomid());
            }
            if (StringUtils.isNotBlank(param.getBdcqzh())) {
                criteria.andEqualTo("cqzh", param.getBdcqzh());
            }
            if (StringUtils.isNotBlank(param.getZl())) {
                criteria.andEqualTo("fczl", param.getZl());
            }
            if (StringUtils.isNotBlank(param.getCxlx())) {
                criteria.andEqualTo("dylx", StringUtils.defaultString(param.getCxlx()));
            }
            if (StringUtils.isNotBlank(param.getSgbh())) {
                criteria.andEqualTo("sgbh", StringUtils.defaultString(param.getSgbh()));
            }
            if (StringUtils.isNotBlank(param.getLikeBdcdyh())) {
                criteria.andLike("bdcdyh", "%" + param.getLikeBdcdyh() + "%");
            }
            if (StringUtils.isNotBlank(param.getLikeZl())) {
                criteria.andLike("fczl", "%" + param.getLikeZl() + "%");
            }
            List<DvHlwDo> dvHlwDos = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(dvHlwDos)) {
                Map<String, DvHlwResponseDto> dvHlwResponseDtoMap = new HashMap<>();
                for (DvHlwDo dvHlwDo : dvHlwDos) {
                    if (dvHlwResponseDtoMap.get(dvHlwDo.getRoomid()) == null) {
                        DvHlwResponseDto dvHlwResponseDto = new DvHlwResponseDto();
                        BeanUtils.copyProperties(dvHlwDo, dvHlwResponseDto);
                        dvHlwResponseDtoMap.put(dvHlwDo.getRoomid(), dvHlwResponseDto);
                    }
                }
                if (MapUtils.isNotEmpty(dvHlwResponseDtoMap)) {
                    for (Map.Entry<String, DvHlwResponseDto> hlwResponseDtoEntry : dvHlwResponseDtoMap.entrySet()) {
                        example.clear();
                        example.createCriteria().andEqualTo("roomid", hlwResponseDtoEntry.getKey());
                        List<DvHlwDo> dvHlwDoTList = entityMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(dvHlwDoTList)) {
                            List<DvHlwQlrResponseDto> qlrResponseDto = new ArrayList<>();
                            for (DvHlwDo dvHlwDoT : dvHlwDoTList) {
                                DvHlwQlrResponseDto dvHlwQlrResponseDto = new DvHlwQlrResponseDto();
                                BeanUtils.copyProperties(dvHlwDoT, dvHlwQlrResponseDto);
                                dvHlwQlrResponseDto.setCqzh(hlwResponseDtoEntry.getValue().getCqzh());
                                qlrResponseDto.add(dvHlwQlrResponseDto);
                            }
                            hlwResponseDtoEntry.getValue().setQlrs(qlrResponseDto);
                        }
                    }
                    dvHlwDoList.addAll(dvHlwResponseDtoMap.values());
                }
            }
        }

        return dvHlwDoList;
    }
}
