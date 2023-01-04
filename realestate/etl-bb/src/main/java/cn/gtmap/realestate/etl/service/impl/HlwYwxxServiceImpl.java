package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.etl.core.domian.wwsq.*;
import cn.gtmap.realestate.etl.core.service.HlwYwxxDataService;
import cn.gtmap.realestate.etl.service.HlwYwxxService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/11
 * @description
 */
@Service
public class HlwYwxxServiceImpl implements HlwYwxxService {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HlwYwxxServiceImpl.class);
    @Autowired
    private Repo hlwRepository;
    @Resource(name = "dozerMapper")
    private DozerBeanMapper dozerMapper;
    @Autowired
    private HlwYwxxDataService hlwYwxxDataService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Override
    public Page<Map> listHlwYwxxByPage(Pageable pageable, Map map) {
        Page<Map> result = hlwRepository.selectPaging("listHlwYwxxByPageOrder", map, pageable);
        if (result != null && CollectionUtils.isNotEmpty(result.getContent())) {
            for (Map data : result.getContent()) {
                BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                bdcZdDsfzdgxDO.setZdbs("BDC_ZD_SQLX");
                bdcZdDsfzdgxDO.setDsfzdz(MapUtils.getString(map, "SQLX"));
                bdcZdDsfzdgxDO.setDsfxtbs("wwsq");
                data.put("GZLDYID", bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO));
            }
        }
        return result;
    }

    @Override
    public JSONObject queryHlwJsonByHlwXmid(String hlwxmid) {
        JSONObject jsonObject = new JSONObject();
        GxWwSqxmDo gxWwSqxmDo = hlwYwxxDataService.gxWwsqxmByHlwXmid(hlwxmid);
        List<GxWwSqxxDo> gxWwSqxxDoList = hlwYwxxDataService.listGxWwSqxxByXmid(hlwxmid);
        if (gxWwSqxmDo == null || CollectionUtils.isEmpty(gxWwSqxxDoList)) {
            throw new AppException("根据互联网主键项目id无法查询互联网项目！");
        }
        jsonObject.put("spxtywh", gxWwSqxmDo.getSqslbh());
        List<FjclDTO> fjclDTOList = new ArrayList();
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        //处理根目录
        dozerMapper.map(gxWwSqxmDo, wwsqCjBdcXmRequestDTO);

        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        //处理基本信息
        dozerMapper.map(gxWwSqxmDo, bdcSlJbxxDO);

        //循环添加BdcSlXmDTO
        List<BdcSlXmDTO> xmDTOList = new ArrayList<>();
        bdcSlxxDTO.setBdcSlXmList(xmDTOList);
        List<GxWwSqxxClxxDo> gxWwSqxxClxxDoList = new ArrayList<>();
        int sxh = 0;
        for (GxWwSqxxDo gxWwSqxxDo : gxWwSqxxDoList) {
            sxh++;
            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();

            BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
            dozerMapper.map(gxWwSqxxDo, bdcSlXmDO);
            bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);

            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
            dozerMapper.map(gxWwSqxmDo, dsfSlxxDTO);
            dozerMapper.map(gxWwSqxxDo, dsfSlxxDTO);
            dsfSlxxDTO.setSxh(sxh);
            if (StringUtils.isNotBlank(gxWwSqxxDo.getBdbzzqse())) {
                dsfSlxxDTO.setGlYxm(true);
            }
            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);

            BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
            dozerMapper.map(gxWwSqxxDo, bdcSlJyxxDO);
            bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);

            // 处理项目历史关系
            if (StringUtils.isNotBlank(gxWwSqxxDo.getZsxmid())) {
                BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
                bdcSlXmLsgxDO.setYxmid(gxWwSqxxDo.getZsxmid());
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOS = new ArrayList<>();
                bdcSlXmLsgxDOS.add(bdcSlXmLsgxDO);
                bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOS);
            }
            //权利人
            List<GxWwSqxxQlrDo> gxWwSqxxQlrDoList = hlwYwxxDataService.listGxWwSqxxQlrBySqxxid(gxWwSqxxDo.getSqxxid());
            if (CollectionUtils.isNotEmpty(gxWwSqxxQlrDoList)) {
                bdcSlXmDTO.setBdcSlSqrDOList(new ArrayList<>());
                for (GxWwSqxxQlrDo qlrxx : gxWwSqxxQlrDoList) {
                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                    dozerMapper.map(qlrxx, bdcSlSqrDO);
                    if (CheckParameter.checkAnyParameter(bdcSlSqrDO)) {
                        bdcSlXmDTO.getBdcSlSqrDOList().add(bdcSlSqrDO);
                    }
                }
            }
            //附件
            List<GxWwSqxxClxxDo> wwSqxxClxxDoList = hlwYwxxDataService.listGxWwSqxxClxxBySqxxid(gxWwSqxxDo.getSqxxid());
            if (CollectionUtils.isNotEmpty(wwSqxxClxxDoList)) {
                gxWwSqxxClxxDoList.addAll(wwSqxxClxxDoList);
            }
            //税务信息
            List<GxWwSwxxDo> gxWwSwxxDoList = hlwYwxxDataService.listGxWwSwxxBySqxxid(gxWwSqxxDo.getSqxxid());
            if (CollectionUtils.isNotEmpty(gxWwSwxxDoList)) {
                wwsqCjBdcXmRequestDTO.setSfss(true);
                bdcSlXmDTO.setBdcSwxxDTOList(new ArrayList<>());
                for (GxWwSwxxDo gxWwSwxxDo : gxWwSwxxDoList) {
                    BdcSwxxDTO bdcSwxxDTO = new BdcSwxxDTO();
                    BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                    dozerMapper.map(gxWwSwxxDo, bdcSlHsxxDO);
                    bdcSwxxDTO.setBdcSlHsxxDO(bdcSlHsxxDO);
                    if (CollectionUtils.isNotEmpty(gxWwSwxxDo.getSwmx())) {
                        bdcSwxxDTO.setBdcSlHsxxMxDOList(new ArrayList<>());
                        for (GxWwSwmxDo gxWwSwmxDo : gxWwSwxxDo.getSwmx()) {
                            BdcSlHsxxMxDO bdcSlHsxxMxDO = new BdcSlHsxxMxDO();
                            dozerMapper.map(gxWwSwmxDo, bdcSlHsxxMxDO);
                            bdcSwxxDTO.getBdcSlHsxxMxDOList().add(bdcSlHsxxMxDO);
                        }
                    }
                    if (CheckParameter.checkAnyParameter(bdcSwxxDTO)) {
                        bdcSlXmDTO.getBdcSwxxDTOList().add(bdcSwxxDTO);
                    }
                }
            }
            xmDTOList.add(bdcSlXmDTO);
        }
        if (CollectionUtils.isNotEmpty(gxWwSqxxClxxDoList)) {
            for (GxWwSqxxClxxDo gxWwSqxxClxxDo : gxWwSqxxClxxDoList) {
                FjclDTO fjclDTO = new FjclDTO();
                dozerMapper.map(gxWwSqxxClxxDo, fjclDTO);
                if (CollectionUtils.isNotEmpty(gxWwSqxxClxxDo.getFjxx())) {
                    fjclDTO.setFjclmxDTOList(new ArrayList<>());
                    for (GxWwSqxxFjxxDo gxWwSqxxFjxxDo : gxWwSqxxClxxDo.getFjxx()) {
                        FjclmxDTO fjclmxDTO = new FjclmxDTO();
                        dozerMapper.map(gxWwSqxxFjxxDo, fjclmxDTO);
                        fjclDTO.getFjclmxDTOList().add(fjclmxDTO);
                    }
                }
                fjclDTOList.add(fjclDTO);
            }
        }
        jsonObject.put("fjclList", JSONObject.toJSONString(fjclDTOList));
        jsonObject.put("acceptCjParam", JSONObject.toJSONString(wwsqCjBdcXmRequestDTO));
        return jsonObject;
    }
}
