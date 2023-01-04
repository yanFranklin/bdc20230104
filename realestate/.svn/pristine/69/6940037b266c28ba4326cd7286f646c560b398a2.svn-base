package cn.gtmap.realestate.etl.service.impl;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/10/12
 * @description 南通一张网推送数据到前置机
 */


import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.exchange.yzw.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcLzrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLzrFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.etl.core.domian.nantong.*;
import cn.gtmap.realestate.etl.service.PushQlygDataService;
import cn.gtmap.realestate.etl.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PushQlygDataServiceImpl implements PushQlygDataService {
    @Resource(name = "dozerMapper")
    private Mapper dozerMapper;
    @Autowired
    @Qualifier("entityMapper")
    private EntityMapper entityMapper;
    @Autowired
    @Qualifier("yzwEntityMapper")
    private EntityMapper yzwEntityMapper;
    @Value("${yzw.zzxx.enable:false}")
    private boolean zzxx;
    @Autowired
    HttpClient httpClient;
    @Autowired
    FileUtil fileUtil;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int AREA_LENGTH = 12;

    //证件类型
    private static String ZJLX_ZDBS = "DSF_ZD_ZJLX";
    private static String SQRLX_ZDBS = "DSF_ZD_SQRLX";
    private static String DSFXTBS = "yzw";
    private static String APPLICANT_GR = "01";

    @Override
    public void pushInfApply(List<InfApplyDO> infApplyList, Map<String, BnInfSpare> map) {
        if (CollectionUtils.isNotEmpty(infApplyList)) {
            for (InfApplyDO infApply : infApplyList) {
                try {
                    insertQzk(infApply, map.get(infApply.getInternalNo()));
                } catch (Exception e) {
                    logger.error(null, e);
                    logger.info("******推送错误业务号：" + infApply.getInternalNo());
                }
            }
        }
    }

    /**
     * @param infApply 办件基本信息
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @rerutn
     * @description 推送前置库
     */
    @Transactional(value = "yzw", rollbackFor = Exception.class)
    public void insertQzk(InfApplyDO infApply, BnInfSpare bnInfSpare) {
        if (StringUtils.isNotBlank(infApply.getInternalNo()) && StringUtils.isNotBlank(infApply.getDeptYwRegNo())) {
            BnInfApply bnInfApply = dozerMapper.map(infApply, BnInfApply.class);
            //参数处理 -- 联系电话为空直接传空值
            if (StringUtils.isNotBlank(bnInfApply.getLinkmanPhone()) && CommonConstantUtils.YZW_DHMRZ.equals(bnInfApply.getLinkmanPhone())) {
                bnInfApply.setLinkmanPhone("");
            }
            if (StringUtils.isNotBlank(bnInfApply.getLinkmanMobile()) && CommonConstantUtils.YZW_DHMRZ.equals(bnInfApply.getLinkmanMobile())) {
                bnInfApply.setLinkmanMobile("");
            }
            //补0到12位
            if (StringUtils.isNotBlank(bnInfApply.getAreaNo()) && (bnInfApply.getAreaNo().length() < AREA_LENGTH)) {
                bnInfApply.setAreaNo(StringUtils.rightPad(bnInfApply.getAreaNo(),12,"0"));
            }

            //转换证件种类
            Map<String, String> dsfZjlxMap = queryDsfZdMap(ZJLX_ZDBS,DSFXTBS);
            if (StringUtils.isNotBlank(bnInfApply.getLinkmanPaperType())
                    && MapUtils.isNotEmpty(dsfZjlxMap)
                    && StringUtils.isNotBlank(dsfZjlxMap.get(bnInfApply.getLinkmanPaperType()))
            ) {
                bnInfApply.setLinkmanPaperType(dsfZjlxMap.get(bnInfApply.getLinkmanPaperType()));
            }
            if (StringUtils.isNotBlank(bnInfApply.getApplicantPaperType())
                    && MapUtils.isNotEmpty(dsfZjlxMap)
                    && StringUtils.isNotBlank(dsfZjlxMap.get(bnInfApply.getApplicantPaperType()))
            ) {
                bnInfApply.setApplicantPaperType(dsfZjlxMap.get(bnInfApply.getApplicantPaperType()));
            }


            //转换申请人类型
            Map<String, String> dsfSqrlxMap = queryDsfZdMap(SQRLX_ZDBS,DSFXTBS);
            if (StringUtils.isNotBlank(bnInfApply.getApplicantType())
                    && MapUtils.isNotEmpty(dsfSqrlxMap)
                    && StringUtils.isNotBlank(dsfSqrlxMap.get(bnInfApply.getApplicantType()))
            ) {
                bnInfApply.setApplicantType(dsfSqrlxMap.get(bnInfApply.getApplicantType()));
                //成功转换权利人类型后判断权利人类型不是个人时，取领证人电话，如果没有成功转换则不判断
                if(!APPLICANT_GR.equals(bnInfApply.getApplicantType())){
                    BdcLzrDO bdcLzrDO = queryLzr(bnInfSpare.getXmid());
                    if(Objects.nonNull(bdcLzrDO)){
                        bnInfApply.setLinkmanMobile(bdcLzrDO.getLzrdh());
                    }
                }
            }
            //备用字段
            if (bnInfSpare == null) {
                bnInfSpare = new BnInfSpare();
            }
            dozerMapper.map(bnInfSpare, bnInfApply);
            bnInfApply.setWapplyDate(bnInfApply.getApplyDate());
            Example bnInfApplyExample = new Example(BnInfApply.class);
            bnInfApplyExample.createCriteria().andEqualTo("internalNo", bnInfApply.getInternalNo()).andEqualTo("deptYwRegNo", bnInfApply.getDeptYwRegNo());
            List<BnInfApply> bnInfApplyList = yzwEntityMapper.selectByExampleNotNull(bnInfApplyExample);
            if (CollectionUtils.isNotEmpty(bnInfApplyList)) {
                yzwEntityMapper.updateByExampleSelectiveNotNull(bnInfApply, bnInfApplyExample);
            } else {
                yzwEntityMapper.insertSelective(bnInfApply);
            }

            //过程表
            String internal_no = infApply.getInternalNo();
            if (StringUtils.isNotBlank(internal_no)) {

                //再插入过程表
                Example example = new Example(InfApplyProcessDO.class);
                Example.Criteria criteria = example.createCriteria();
                example.setOrderByClause("to_number(no_ord)");
                criteria.andEqualTo("internalNo", internal_no);
                List<InfApplyProcessDO> infApplyProcessList = entityMapper.selectByExample(InfApplyProcessDO.class, example);
                pushInfApplyProcess(infApplyProcessList, null, bnInfSpare);

                //结果表
                example = new Example(InfApplyResultDO.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("internalNo", internal_no);
                List<InfApplyResultDO> infApplyResultList = entityMapper.selectByExample(InfApplyResultDO.class, example);
                pushInfApplyResult(infApplyResultList, bnInfSpare);

                //申报材料信息表
                example = new Example(InfApplyMaterialDO.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("internalNo", internal_no);
                List<InfApplyMaterialDO> infApplyMaterialList = entityMapper.selectByExample(InfApplyMaterialDO.class, example);
                pushInfApplyMaterial(infApplyMaterialList, bnInfSpare);
                if (!zzxx) {
                    //结果证照信息表
                    example = new Example(InfApplyJgzzDO.class);
                    criteria = example.createCriteria();
                    criteria.andEqualTo("internalNo", internal_no);
                    List<InfApplyJgzzDO> infApplyJgzzList = entityMapper.selectByExample(InfApplyJgzzDO.class, example);
                    pushInfApplyJgzz(infApplyJgzzList);
                }
            }
        }
    }

    @Override
    public void pushInfApplyProcess(List<InfApplyProcessDO> infApplyProcessList, List<InfApplyWlxxDO> infApplyWlxxList, BnInfSpare bnInfSpare) {
        if (CollectionUtils.isNotEmpty(infApplyProcessList) && StringUtils.isNotBlank(infApplyProcessList.get(0).getInternalNo())) {
            Example example = new Example(BnInfApplyProcess.class);
            example.createCriteria().andEqualTo("internalNo", infApplyProcessList.get(0).getInternalNo());
            yzwEntityMapper.deleteByExampleNotNull(example);

            for (InfApplyProcessDO infApplyProcess : infApplyProcessList) {
                //根据dozer转换
                BnInfApplyProcess bnInfApplyProcess = dozerMapper.map(infApplyProcess, BnInfApplyProcess.class);
                dozerMapper.map(bnInfSpare, bnInfApplyProcess);
                yzwEntityMapper.insertSelective(bnInfApplyProcess);
            }
        }
    }

    @Override
    public void pushInfApplyResult(List<InfApplyResultDO> infApplyResultList, BnInfSpare bnInfSpare) {
        if (CollectionUtils.isNotEmpty(infApplyResultList) && StringUtils.isNotBlank(infApplyResultList.get(0).getInternalNo())) {
            Example example = new Example(BnInfApplyResult.class);
            example.createCriteria().andEqualTo("internalNo", infApplyResultList.get(0).getInternalNo());
            yzwEntityMapper.deleteByExampleNotNull(example);

            for (InfApplyResultDO infApplyResult : infApplyResultList) {
                //根据dozer转换
                BnInfApplyResult bnInfApplyResult = dozerMapper.map(infApplyResult, BnInfApplyResult.class);
                dozerMapper.map(bnInfSpare, bnInfApplyResult);
                yzwEntityMapper.insertSelective(bnInfApplyResult);
            }
        }
    }

    @Override
    public void pushInfApplyJgzz(List<InfApplyJgzzDO> infApplyJgzzList) {
        if (CollectionUtils.isNotEmpty(infApplyJgzzList) && StringUtils.isNotBlank(infApplyJgzzList.get(0).getZzBh())) {
            for (InfApplyJgzzDO infApplyJgzz : infApplyJgzzList) {
               /* //根据存地址下载证照pdf文件
                byte[] zzFile = fileUtil.downloadZz(infApplyJgzz.getZzFile());//证照根据证照编号来更新*/
                Example example = new Example(TZzZmInfo.class);
                example.createCriteria().andEqualTo("zzBh", infApplyJgzz.getZzBh());
                yzwEntityMapper.deleteByExampleNotNull(example);
                TZzZmInfo tZzZmInfo = dozerMapper.map(infApplyJgzz, TZzZmInfo.class);
                tZzZmInfo.setStatus("2");
                if (StringUtils.isNotEmpty(infApplyJgzz.getZzFile())) {
                    tZzZmInfo.setZzFile(infApplyJgzz.getZzFile().getBytes());
                }
                yzwEntityMapper.insertSelective(tZzZmInfo);
            }
        }
    }


    @Override
    public void pushInfApplyWlxx(List<InfApplyWlxxDO> infApplyWlxxList) {
    }

    @Override
    public void pushInfApplyWlxxToProcess(List<InfApplyWlxxDO> infApplyWlxxList) {
    }

    @Override
    public void pushInfApplyMaterial(List<InfApplyMaterialDO> infApplyMaterialList, BnInfSpare bnInfSpare) {
        if (CollectionUtils.isNotEmpty(infApplyMaterialList) && StringUtils.isNotBlank(infApplyMaterialList.get(0).getInternalNo())) {
            Example example = new Example(TYwblAttachment.class);
            example.createCriteria().andEqualTo("internalNo", infApplyMaterialList.get(0).getInternalNo());
            yzwEntityMapper.deleteByExampleNotNull(example);

            for (InfApplyMaterialDO infApplyMaterial : infApplyMaterialList) {
                //根据dozer转换
                TYwblAttachment tYwblAttachment = dozerMapper.map(infApplyMaterial, TYwblAttachment.class);
                dozerMapper.map(bnInfSpare, tYwblAttachment);
                yzwEntityMapper.insertSelective(tYwblAttachment);
            }
        }
    }

    @Override
    public void pushInfApplyAccept(List<InfApplyProcessDO> infApplyProcessList) {
    }

    @Override
    public void pushInfApplyVerify(List<InfApplyVerifyDO> infApplyVerifyList) {

    }

    private Map<String, String> queryDsfZdMap(String zdbs,String dsfxtbs) {
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfxtbs);
        List<BdcZdDsfzdgxDO> bdcZdDsfzdgxDOS = bdcZdFeignService.queryDsfZdxBybs(bdcZdDsfzdgxDO);
        if(CollectionUtils.isEmpty(bdcZdDsfzdgxDOS)) {
          return new HashMap<>();
        }
        Map<String, String> fjlxZdMap = bdcZdDsfzdgxDOS
                .stream().collect(Collectors.toMap(BdcZdDsfzdgxDO::getBdczdz, BdcZdDsfzdgxDO::getDsfzdz, (v1, v2) -> v2));
        return fjlxZdMap;
    }


    private BdcLzrDO queryLzr(String xmid){
        BdcLzrDO bdcLzrDO = new BdcLzrDO();
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setXmid(xmid);
        List<BdcLzrDO> bdcLzrDOS = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
        //非空做处理
        if (CollectionUtils.isNotEmpty(bdcLzrDOS)) {
            bdcLzrDO = bdcLzrDOS.get(0);
            if(StringUtils.isBlank(bdcLzrDO.getLzrdh())){
                bdcLzrDO.setLzrdh("");
            }
        }
        return bdcLzrDO;
    }
}
