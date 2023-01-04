package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzZzxxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmZsDTO;
import cn.gtmap.realestate.common.core.dto.register.DzzzQzkDzDTO;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.etl.DzzzQzkTsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcZzxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.register.service.BdcDzzzService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/08/24
 * @description
 */
@Service
public class BdcDzzzServiceImpl implements BdcDzzzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDzzzServiceImpl.class);
    @Autowired
    BdcXmMapper bdcXmMapper;
    @Autowired
    BdcZzxxFeignService bdcZzxxFeignService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    ECertificateFeignService eCertificateFeignService;
    @Autowired
    DzzzQzkTsFeignService dzzzQzkTsFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    /**
     * 证照颁发机构代码
     */
    @Value("#{${eCertificate.zzbfjgdm: null}}")
    private Map<String, String> zzbfjgdm;

    @Override
    public void pushDzzzDatas(String gzlslid) {
        //通过gzlslid获取证书信息
        List<BdcXmZsDTO> zsList = bdcXmMapper.queryZsByGzlslid(gzlslid);
        for (BdcXmZsDTO bdcXmZsDTO : zsList){
            if (StringUtils.isBlank(bdcXmZsDTO.getZzbs())){
                continue;
            }
            BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcZzxxFeignService.getZzxxByzzbs(bdcXmZsDTO);
            DzzzQzkDzDTO dzzzQzkDzDTO = new DzzzQzkDzDTO();
            dzzzQzkDzDTO.setRowguid(bdcDzzzZzxxDO.getZzid());
            dzzzQzkDzDTO.setCertno(bdcDzzzZzxxDO.getZzbh());
            dzzzQzkDzDTO.setCertname(bdcDzzzZzxxDO.getZzmc());
            dzzzQzkDzDTO.setCertlevel("A");
            dzzzQzkDzDTO.setCertawarddept(bdcDzzzZzxxDO.getZzbfjg());
            dzzzQzkDzDTO.setCertawarddeptorgcode(bdcDzzzZzxxDO.getZzbfjgdm());
            dzzzQzkDzDTO.setCertownername(bdcDzzzZzxxDO.getCzzt());

            if ("111".equals(bdcDzzzZzxxDO.getCzztdmlxdm())){
                dzzzQzkDzDTO.setCertownertype("1");
            }else {
                dzzzQzkDzDTO.setCertownertype("2");
            }
            //公民身份号码转为22 统一社会信用代码转16 其余转99
            if (CommonConstantUtils.GMSFZHM.equals(bdcDzzzZzxxDO.getCzztdmlx()) || CommonConstantUtils.TYSHXYDM.equals(bdcDzzzZzxxDO.getCzztdmlx())){
                dzzzQzkDzDTO.setCertownercerttype(CommonConstantUtils.GMSFZHM.equals(bdcDzzzZzxxDO.getCzztdmlx())?"22":"16");
            }else {
                dzzzQzkDzDTO.setCertownercerttype("99");
            }
            //由于电子证照库证件号加密，取登记库数据处理证件号
            Example example1 = new Example(BdcQlrDO.class);
            example1.createCriteria().andEqualTo("xmid",bdcXmZsDTO.getXmid()).andEqualTo("qlrlb",CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> qlrList = entityMapper.selectByExample(example1);
            StringBuilder zjh = new StringBuilder();
            for (BdcQlrDO bdcQlrDO : qlrList){
                if (StringUtils.isNotBlank(bdcQlrDO.getZjh())){
                    zjh.append(bdcQlrDO.getZjh()).append(CommonConstantUtils.ZF_GZH);
                }
            }
            dzzzQzkDzDTO.setCertownerno(zjh.substring(0,zjh.lastIndexOf(CommonConstantUtils.ZF_GZH)));
            dzzzQzkDzDTO.setAwarddate(bdcDzzzZzxxDO.getZzbfrq());
            if (StringUtils.isNotBlank(MapUtils.getString(zzbfjgdm,bdcXmZsDTO.getDjbmdm(),""))){
                dzzzQzkDzDTO.setOrgcode(MapUtils.getString(zzbfjgdm,bdcXmZsDTO.getDjbmdm(),""));
            }

            //获取电子证照base64数据以及文件名
            Map<String,String> dzzzMap = new HashMap();
            Map<String,Object> map = (Map<String, Object>) eCertificateFeignService.queryDzzzByZzbs(bdcDzzzZzxxDO.getZzbs());
            dzzzMap.put("attachname",MapUtils.getString(map,"fileName",""));
            dzzzMap.put("content", MapUtils.getString(map,"base64",""));
            if (bdcXmZsDTO.getZslx()==1){
                dzzzQzkDzDTO.setCertcatalogid("da730ed3-7d88-412a-a921-33ee1447681b");
            }
            if (bdcXmZsDTO.getZslx()==2){
                dzzzQzkDzDTO.setCertcatalogid("d6b814aa-4129-4d59-bd38-1b3a9d5477ff");
            }
            dzzzQzkDzDTO.setCreatedate(new Date());
            dzzzQzkDzDTO.setCertinfoextension(JSON.toJSONString(dzzzMap));
            LOGGER.info("推送数据信息:{}",dzzzQzkDzDTO.toString());
            //判断是否存在注销上一首电子证照，存在则更新前置库对应数据操作类型为D
            BdcCshFwkgSlDO bdcCshFwkgSl =bdcXmFeignService.queryFwkgsl(bdcXmZsDTO.getXmid());
            if (bdcCshFwkgSl.getSfzxyql() == 1){
                Example example = new Example(BdcXmLsgxDO.class);
                example.createCriteria().andEqualTo("xmid",bdcXmZsDTO.getXmid());
                List<BdcXmLsgxDO> bdcXmLsgxList = entityMapper.selectByExample(example);
                for (BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxList){
                    List<BdcXmZsDTO> yzsList = bdcXmMapper.queryZsByXmid(bdcXmLsgxDO.getYxmid());
                    for (BdcXmZsDTO bdcXmzs : yzsList){
                        if (StringUtils.isBlank(bdcXmzs.getZzbs())){
                            continue;
                        }
                        BdcDzzzZzxxDO bdcYdzzzZzxxDO = bdcZzxxFeignService.getZzxxByzzbs(bdcXmzs);
                        dzzzQzkTsFeignService.updateYdzzzByZzid(bdcYdzzzZzxxDO.getZzid());
                    }
                }
            }
            dzzzQzkDzDTO.setOperatetype("A");

            dzzzQzkTsFeignService.insertDzzzQzk(dzzzQzkDzDTO);
        }
    }
}
