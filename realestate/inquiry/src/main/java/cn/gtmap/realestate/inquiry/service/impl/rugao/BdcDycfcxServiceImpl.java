package cn.gtmap.realestate.inquiry.service.impl.rugao;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.rugao.BdcCqCfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.rugao.BdcCqDyxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.rugao.BdcCqxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.rugao.BdcDyCfCqCxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcCfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDyafeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.service.interfaces.CustomConverter;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.DoubleUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcDycfcxMapper;
import cn.gtmap.realestate.inquiry.service.rugao.BdcCqCxService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/01/19
 * @description
 */
@Service
public class BdcDycfcxServiceImpl implements BdcCqCxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDycfcxServiceImpl.class);

    @Autowired
    private Repository repository;

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcCfxxFeignService bdcCfxxFeignService;

    @Autowired
    private BdcDyafeignService bdcDyafeignService;

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcDycfcxMapper bdcDycfcxMapper;

    @Override
    public Page<BdcCqxxDTO> dycfcqCx(Pageable pageable, String bdcDyCfCqcxQOStr) {

        BdcDyCfCqCxQO bdcDyCfCqcxQO = JSON.parseObject(bdcDyCfCqcxQOStr, BdcDyCfCqCxQO.class);
        Map<String,Object> map = new HashMap<>(16);
        if(StringUtils.isNotEmpty(bdcDyCfCqcxQO.getQlrzjh())){
            map.put("qlrzjh",Arrays.asList(StringUtils.split(bdcDyCfCqcxQO.getQlrzjh(),",")));
        }
        if(StringUtils.isNotEmpty(bdcDyCfCqcxQO.getQlrmc())){
            map.put("qlrmc",Arrays.asList(StringUtils.split(bdcDyCfCqcxQO.getQlrmc(),",")));
        }
        map.put("cxlx", bdcDyCfCqcxQO.getCxlx());
        map.put("qszt", bdcDyCfCqcxQO.getQszt());
        Map<String, Object> paramMap = PageUtils.pageableSort(pageable,map);

        LOGGER.info("---查询参数:{}", JSON.toJSONString(paramMap));
        Page<BdcCqxxDTO> result = repository.selectPaging("listCqCx", paramMap, pageable);
        List<BdcCqxxDTO> bdcCqxxDTOS = result.getContent().stream().distinct().collect(Collectors.toList());
        if(CollectionUtils.isEmpty(bdcCqxxDTOS)){
            return result;
        }

        Map<String,BdcCqxxDTO> bdcCqxxDTOMap = bdcCqxxDTOS.stream().collect(Collectors.toMap(BdcCqxxDTO::getXmid,v -> v ,(v1,v2) -> v2));
        List<String> xmidList = bdcCqxxDTOS.stream().map(BdcCqxxDTO::getXmid).distinct().filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(xmidList)){
            return result;
        }

        /**
         * 不考虑限制权利
         */
        List<BdcQl> bdcQls = bdcQllxFeignService.listQlxxByXmids(xmidList);
        if(CollectionUtils.isNotEmpty(bdcQls)){
            bdcQls.forEach(bdcQl -> {
                String xmid = bdcQl.getXmid();
                if(Objects.nonNull(bdcCqxxDTOMap.get(xmid))){
                    BdcCqxxDTO bdcCqxxDTO = bdcCqxxDTOMap.get(xmid);
                    if(bdcQl instanceof BdcTdsyqDO){
                        BdcTdsyqDO bdcTdsyqDO = (BdcTdsyqDO) bdcQl;
                        double sum = DoubleUtils.sum(bdcTdsyqDO.getNydmj(),bdcTdsyqDO.getGdmj(),bdcTdsyqDO.getNtmj(),
                                bdcTdsyqDO.getLdmj(),bdcTdsyqDO.getCdmj(),bdcTdsyqDO.getQtnydmj(),bdcTdsyqDO.getJsydmj(),
                                bdcTdsyqDO.getWlydmj());
                        LOGGER.info("---土地使用权总面积:{}",sum);
                        bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(sum,DoubleUtils.df2));
                    }else if(bdcQl instanceof BdcJsydsyqDO){
                        BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                        bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(bdcJsydsyqDO.getSyqmj(),DoubleUtils.df2));
                    }else if(bdcQl instanceof BdcFdcqDO){
                        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                        bdcCqxxDTO.setFwmj(DoubleUtils.doubleToString(bdcFdcqDO.getJzmj(),DoubleUtils.df2));
                    }else if(bdcQl instanceof BdcHysyqDO){
                        BdcHysyqDO bdcHysyqDO = (BdcHysyqDO) bdcQl;
                        bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(bdcHysyqDO.getSyqmj(),DoubleUtils.df2));
                    }else if(bdcQl instanceof BdcGjzwsyqDO){
                        BdcGjzwsyqDO bdcGjzwsyqDO = (BdcGjzwsyqDO) bdcQl;
                        bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(bdcGjzwsyqDO.getGjzwmj(),DoubleUtils.df2));
                    }else if (bdcQl instanceof BdcLqDO) {
                        BdcLqDO bdcLqDO = (BdcLqDO) bdcQl;
                        bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(bdcLqDO.getSyqmj(),DoubleUtils.df2));
                    }
                }
            });
        }

        List<String> bdcdyhList = bdcCqxxDTOS.stream().map(BdcCqxxDTO::getBdcdyh).distinct().filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.toList());

        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(bdcdyhList,"");
        if(CollectionUtils.isNotEmpty(bdcdyhZtDTOList)){
            LOGGER.info("bdcdyhZtDTOList的size:" + bdcdyhZtDTOList.size());
        }
        Map<String, BdcdyhZtResponseDTO> bdcdyhZtDTOMap = bdcdyhZtDTOList.stream().collect(Collectors.toMap(BdcdyhZtResponseDTO::getBdcdyh, v -> v ,(v1,v2) -> v2));
        Page<BdcCqxxDTO> dtoPage = result.map(new CustomConverter<BdcCqxxDTO, BdcCqxxDTO>() {
            @Override
            public BdcCqxxDTO doConvert(BdcCqxxDTO bdcCqxxDTO) {
                BdcdyhZtResponseDTO bdcdyhZtResponseDTO = bdcdyhZtDTOMap.get(bdcCqxxDTO.getBdcdyh());
                if(Objects.nonNull(bdcdyhZtResponseDTO)){
                    bdcCqxxDTO.setCfzt(bdcdyhZtResponseDTO.getCf());
                    bdcCqxxDTO.setDyzt(bdcdyhZtResponseDTO.getDya());
                }
                if(Objects.nonNull(bdcCqxxDTOMap.get(bdcCqxxDTO.getXmid()))){
                    bdcCqxxDTO.setFwmj(bdcCqxxDTOMap.get(bdcCqxxDTO.getXmid()).getFwmj());
                    bdcCqxxDTO.setTdmj(bdcCqxxDTOMap.get(bdcCqxxDTO.getXmid()).getTdmj());
                }
                return bdcCqxxDTO;
            }
        });
        return dtoPage;
    }

    @Override
    public List<BdcCqxxDTO> listCqxxByBdchyhList(List<String> bdcdyhList) {
        if(CollectionUtils.isEmpty(bdcdyhList)){
            return Lists.newArrayList();
        }
        List<BdcCqxxDTO> bdcCqxxDTOList = bdcDycfcxMapper.listCqxxByBdchyhList(bdcdyhList);
        if(CollectionUtils.isEmpty(bdcCqxxDTOList)){
            return bdcCqxxDTOList;
        }

        List<String> xmidList = bdcCqxxDTOList.stream().map(BdcCqxxDTO::getXmid).distinct().filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.toList());
        Map<String,BdcCqxxDTO> bdcCqxxDTOMap = bdcCqxxDTOList.stream().collect(Collectors.toMap(BdcCqxxDTO::getXmid,v -> v ,(v1,v2) -> v2));
        if(CollectionUtils.isNotEmpty(xmidList)){
            /**
             * 不考虑限制权利
             */
            List<BdcQl> bdcQls = bdcQllxFeignService.listQlxxByXmids(xmidList);
            if(CollectionUtils.isNotEmpty(bdcQls)){
                bdcQls.forEach(bdcQl -> {
                    String xmid = bdcQl.getXmid();
                    if(Objects.nonNull(bdcCqxxDTOMap.get(xmid))){
                        BdcCqxxDTO bdcCqxxDTO = bdcCqxxDTOMap.get(xmid);
                        if(bdcQl instanceof BdcTdsyqDO){
                            BdcTdsyqDO bdcTdsyqDO = (BdcTdsyqDO) bdcQl;
                            double sum = DoubleUtils.sum(bdcTdsyqDO.getNydmj(),bdcTdsyqDO.getGdmj(),bdcTdsyqDO.getNtmj(),
                                    bdcTdsyqDO.getLdmj(),bdcTdsyqDO.getCdmj(),bdcTdsyqDO.getQtnydmj(),bdcTdsyqDO.getJsydmj(),
                                    bdcTdsyqDO.getWlydmj());
                            LOGGER.info("---土地使用权总面积:{}",sum);
                            bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(sum,DoubleUtils.df2));
                        }else if(bdcQl instanceof BdcJsydsyqDO){
                            BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                            bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(bdcJsydsyqDO.getSyqmj(),DoubleUtils.df2));
                        }else if(bdcQl instanceof BdcFdcqDO){
                            BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                            bdcCqxxDTO.setFwmj(DoubleUtils.doubleToString(bdcFdcqDO.getJzmj(),DoubleUtils.df2));
                        }else if(bdcQl instanceof BdcHysyqDO){
                            BdcHysyqDO bdcHysyqDO = (BdcHysyqDO) bdcQl;
                            bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(bdcHysyqDO.getSyqmj(),DoubleUtils.df2));
                        }else if(bdcQl instanceof BdcGjzwsyqDO){
                            BdcGjzwsyqDO bdcGjzwsyqDO = (BdcGjzwsyqDO) bdcQl;
                            bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(bdcGjzwsyqDO.getGjzwmj(),DoubleUtils.df2));
                        }else if (bdcQl instanceof BdcLqDO) {
                            BdcLqDO bdcLqDO = (BdcLqDO) bdcQl;
                            bdcCqxxDTO.setTdmj(DoubleUtils.doubleToString(bdcLqDO.getSyqmj(),DoubleUtils.df2));
                        }
                    }
                });
            }
        }

        List<BdcCfDO> bdcCfDOS = bdcCfxxFeignService.listBdcCfByBdcdyhs(bdcdyhList);
        List<BdcDyaqDO> bdcDyaqDOS = bdcDyafeignService.listBdcDyaqByBdcdyhs(bdcdyhList);

        Map<String,String> dyzhmMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(bdcDyaqDOS)){
            List<String> dyYxmid = bdcDyaqDOS.stream().filter(x -> 1 == x.getQszt()).map(BdcDyaqDO::getXmid).distinct().collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(dyYxmid)){
                List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXmByXmids(dyYxmid);
                if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                    dyzhmMap = bdcXmDOS.stream().filter(x -> Objects.nonNull(x.getBdcqzh())).collect(Collectors.toMap(BdcXmDO::getXmid,BdcXmDO::getBdcqzh,(v1,v2) -> v2));
                }
            }
        }

        Map<String,List<BdcCfDO>> bdcCfDOMap = bdcCfDOS.stream().filter(x -> 1 == x.getQszt()).collect(Collectors.groupingBy(BdcCfDO::getBdcdyh));
        Map<String,List<BdcDyaqDO>> bdcDyaqDOMap = bdcDyaqDOS.stream().filter(x -> 1 == x.getQszt()).collect(Collectors.groupingBy(BdcDyaqDO::getBdcdyh));

        List<BdcCqxxDTO> dataList = new ArrayList<>();

        for(BdcCqxxDTO bdcCqxxDTO : bdcCqxxDTOList){

            BdcCqxxDTO bdcCqxx = new BdcCqxxDTO();
            BeanUtils.copyProperties(bdcCqxxDTO,bdcCqxx);
            dataList.add(bdcCqxx);

            String bdcdyh = bdcCqxxDTO.getBdcdyh();

            bdcCqxxDTO.setFwmj(bdcCqxxDTOMap.get(bdcCqxxDTO.getXmid()).getFwmj());
            bdcCqxxDTO.setTdmj(bdcCqxxDTOMap.get(bdcCqxxDTO.getXmid()).getTdmj());

            if(CollectionUtils.isNotEmpty(bdcCfDOS) && Objects.nonNull(bdcCfDOMap.get(bdcdyh))){
                List<BdcCfDO> bdcCfDOList = bdcCfDOMap.get(bdcdyh);
                if(CollectionUtils.isNotEmpty(bdcCfDOList)){
                    bdcCfDOList.forEach(bdcCfDO -> {
                        BdcCqCfxxDTO bdcCqCfxxDTO = new BdcCqCfxxDTO();

                        BdcCqxxDTO bdcCqxxDTO1 = new BdcCqxxDTO();
                        BeanUtils.copyProperties(bdcCqxxDTO,bdcCqxxDTO1);
                        bdcCqCfxxDTO.setCfwh(bdcCfDO.getCfwh());
                        bdcCqCfxxDTO.setCfkssj(DateUtils.formateDateToString(bdcCfDO.getCfqssj(),DateUtils.DATE_FORMAT_2));
                        bdcCqCfxxDTO.setCfjssj(DateUtils.formateDateToString(bdcCfDO.getCfjssj(),DateUtils.DATE_FORMAT_2));
                        bdcCqxxDTO1.setCfzt(true);
                        bdcCqxxDTO1.setBdcCqCfxxDTO(bdcCqCfxxDTO);
                        dataList.add(bdcCqxxDTO1);
                    });
                }
            }
            if(CollectionUtils.isNotEmpty(bdcDyaqDOS) && Objects.nonNull(bdcDyaqDOMap.get(bdcdyh))){
                List<BdcDyaqDO> bdcDyaqDOList = bdcDyaqDOMap.get(bdcdyh);
                if(CollectionUtils.isNotEmpty(bdcDyaqDOList)){
                    for(BdcDyaqDO bdcDyaqDO : bdcDyaqDOList){
                        BdcCqDyxxDTO bdcCqDyxxDTO = new BdcCqDyxxDTO();

                        BdcCqxxDTO bdcCqxxDTO1 = new BdcCqxxDTO();
                        BeanUtils.copyProperties(bdcCqxxDTO,bdcCqxxDTO1);

                        bdcCqDyxxDTO.setDyje(DoubleUtils.doubleToString(bdcDyaqDO.getBdbzzqse(),DoubleUtils.df2));
                        bdcCqDyxxDTO.setDykssj(DateUtils.formateDateToString(bdcDyaqDO.getZwlxqssj(),DateUtils.DATE_FORMAT_2));
                        bdcCqDyxxDTO.setDyjssj(DateUtils.formateDateToString(bdcDyaqDO.getZwlxjssj(),DateUtils.DATE_FORMAT_2));
                        bdcCqDyxxDTO.setDyzmh(dyzhmMap.get(bdcDyaqDO.getXmid()));
                        bdcCqxxDTO1.setDyzt(true);
                        bdcCqxxDTO1.setBdcCqDyxxDTO(bdcCqDyxxDTO);
                        dataList.add(bdcCqxxDTO1);
                    }
                }
            }
        }
        return dataList;
    }

}
