package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlTfxxMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxmService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlTfxxService;
import cn.gtmap.realestate.accept.service.impl.BdcSfxxServiceImpl;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlTfxxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlTfxxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.NumberUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class BdcSlTfxxServiceImpl implements BdcSlTfxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlTfxxServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcSlTfxxMapper bdcTfxxMapper;
    @Autowired
    private Repo repo;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcSlSfxxService bdcSlSfxxService;
    @Autowired
    BdcSlSfxmService bdcSlSfxmService;

    /**
     * 汇总收费统计时 收费标准名称和代码对照关系
     */
    @Value("${sfxx.hzsfxmbz:}")
    private String hzsfxmbz;


    @Override
    public Page<BdcSlTfxxDO> listBdcSlTfxx(Pageable pageable, String json) {
        BdcSlTfxxQO bdcSlTfxxQO = JSON.parseObject(json, BdcSlTfxxQO.class);
        return repo.selectPaging("listBdcSlTfxxByPage", bdcSlTfxxQO, pageable);
    }

    @Override
    public List<BdcSlTfxxDO> queryBdcSlTfxxList(BdcSlTfxxQO bdcSlTfxxQO) {
        return bdcTfxxMapper.listBdcSlTfxxByPage(bdcSlTfxxQO);
    }

    @Override
    public List<BdcSlTfxxDO> generateTfxx(String slbh) {
        if(StringUtils.isBlank(slbh)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到受理编号信息");
        }
        if(StringUtils.isBlank(hzsfxmbz)) {
            throw new AppException(ErrorCode.CUSTOM, "未定义收费标准名称和代码对照关系");
        }
        // 收费内容和收费代码对照关系
        Map<String, String> sfxmMap = JSON.parseObject(hzsfxmbz, Map.class);
        String bdcdjfDm = sfxmMap.get("不动产登记费");
        String tdsyqjyfwfDm = sfxmMap.get("土地使用权交易服务费");

        List<BdcXmDTO> bdcXmDTOList = this.bdcXmFeignService.listBdcXmBfxxBySlbh(slbh);
        if(CollectionUtils.isEmpty(bdcXmDTOList)){
            return new ArrayList<>(1);
        }
        List<BdcSlTfxxDO> bdcSlTfxxDOList = new ArrayList<>(10);
        List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxService.listBdcSlSfxxByGzlslid(bdcXmDTOList.get(0).getGzlslid());
        if(CollectionUtils.isNotEmpty(bdcSlSfxxDOList)){
            bdcSlSfxxDOList = bdcSlSfxxDOList.stream().filter(t->t.getHj() > 0).collect(Collectors.toList());
            for (BdcSlSfxxDO sfxxDO : bdcSlSfxxDOList){
                BdcSlTfxxDO bdcSlTfxxDO = new BdcSlTfxxDO();
                BeanUtils.copyProperties(sfxxDO, bdcSlTfxxDO);
                bdcSlTfxxDO.setJkrxm(sfxxDO.getJfrxm());
                bdcSlTfxxDO.setSlbh(slbh);
                bdcSlTfxxDO.setSjsfzje(sfxxDO.getHj());
                bdcSlTfxxDO.setSqtfsj(new Date());
                List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmService.listBdcSlSfxmBySfxxid(sfxxDO.getSfxxid());
                if(CollectionUtils.isNotEmpty(bdcSlSfxmDOList)){
                    Double bdcdjf = NumberUtil.formatDigit(bdcSlSfxmDOList.stream()
                            .filter(t-> bdcdjfDm.indexOf(t.getSfxmdm())>-1).mapToDouble(BdcSlSfxmDO::getSsje).sum(), 2);
                    bdcSlTfxxDO.setDjf(bdcdjf);
                    Double tdsyqjyfwf = NumberUtil.formatDigit(bdcSlSfxmDOList.stream()
                            .filter(t-> tdsyqjyfwfDm.indexOf(t.getSfxmdm())>-1).mapToDouble(BdcSlSfxmDO::getSsje).sum(), 2);
                    bdcSlTfxxDO.setTdsyqjyfwf(tdsyqjyfwf);
                }
                bdcSlTfxxDOList.add(bdcSlTfxxDO);
            }
        }
        return bdcSlTfxxDOList;
    }

    @Override
    public void plSaveBdcSlTfxx(List<BdcSlTfxxDO> bdcSlTfxxDOList) {
        if(CollectionUtils.isEmpty(bdcSlTfxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到不动产受理退费信息");
        }
        for(BdcSlTfxxDO bdcSlTfxxDO : bdcSlTfxxDOList){
            if(StringUtils.isBlank(bdcSlTfxxDO.getTfxxid())){
                bdcSlTfxxDO.setTfxxid(UUIDGenerator.generate16());
                this.entityMapper.insertSelective(bdcSlTfxxDO);
            }else{
                this.entityMapper.updateByPrimaryKeySelective(bdcSlTfxxDO);
            }
        }
    }

    @Override
    public void plRemoveBdcSlTfxx(List<String> tfxxidList) {
        if(CollectionUtils.isNotEmpty(tfxxidList)){
            for(String tfxxId : tfxxidList){
                this.entityMapper.deleteByPrimaryKey(BdcSlTfxxDO.class, tfxxId);
            }
        }
    }
}
