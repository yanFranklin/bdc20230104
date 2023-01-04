package cn.gtmap.interchange.service.impl;
/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, ${Date}
 * @description
 */

import cn.gtmap.interchange.core.domain.*;
import cn.gtmap.interchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.interchange.core.support.mybatis.mapper.Example;
import cn.gtmap.interchange.service.InfApplyService;
import cn.gtmap.interchange.service.PushQlygDataService;
import cn.gtmap.interchange.util.Constants;
import cn.gtmap.interchange.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PushQlygDataServiceImpl implements PushQlygDataService {

    private Logger logger = LoggerFactory.getLogger(PushQlygDataServiceImpl.class);

    @Resource(name = "dozerMapper")
    private Mapper dozerMapper;

    @Autowired
    @Qualifier("gxEntityMapper")
    private EntityMapper gxEntityMapper;

    @Autowired
    @Qualifier("yzwEntityMapper")
    private EntityMapper yzwEntityMapper;

    @Autowired
    InfApplyService infApplyService;

    @Override
    public void pushInfApplyData(List<InfApply> infApplyList) {
        if(CollectionUtils.isNotEmpty(infApplyList)){
            for (InfApply infApply : infApplyList) {
                String synczt = Constants.SYNC_ZT_SUCCESS;
                try {
                    this.insertQzk(infApply);
                } catch (Exception e) {
                    synczt = Constants.SYNC_ZT_FAIL;
                    infApply.setSync_error_desc(e.getCause().getMessage());
                    logger.error("推送共享数据至前置库失败，业务号为：{} 。 {}", infApply.getInternal_no(), e);
                }finally {
                    // 更新数据同步状态、同步时间
                    infApply.setSync_sign(synczt);
                    infApply.setSync_date(new Date());
                    this.infApplyService.modifyInfApply(infApply);
                }
            }
        }
    }

    /**
     * @param infApply 办件基本信息
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @rerutn
     * @description 推送前置库
     */
    @Override
    @Transactional(value = "yzw", rollbackFor = Exception.class)
    public void insertQzk(InfApply infApply) {
        if (StringUtils.isNotBlank(infApply.getInternal_no()) && StringUtils.isNotBlank(infApply.getDept_yw_reg_no())) {
            List<InfApplyResult> infApplyResultList = null;
            String internalNo = infApply.getInternal_no();
            if (StringUtils.isNotBlank(internalNo)) {

                //插入过程表
                Example example = new Example(InfApplyProcess.class);
                Example.Criteria criteria = example.createCriteria();
                example.setOrderByClause("to_number(no_ord)");
                criteria.andEqualTo("internal_no", internalNo);
                List<InfApplyProcess> infApplyProcessList = gxEntityMapper.selectByExample(InfApplyProcess.class, example);
                pushInfApplyProcess(infApplyProcessList, null);

                //结果表
                example = new Example(InfApplyResult.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("internal_no", internalNo);
                infApplyResultList = gxEntityMapper.selectByExample(InfApplyResult.class, example);
                pushInfApplyResult(infApplyResultList);


                //申报材料信息表
                example = new Example(InfApplyMaterial.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("internal_no", internalNo);
                List<InfApplyMaterial> infApplyMaterialList = gxEntityMapper.selectByExample(InfApplyMaterial.class, example);
                pushInfApplyMaterial(infApplyMaterialList);

            }

            BnInfApply bnInfApply = dozerMapper.map(infApply, BnInfApply.class);
            bnInfApply.setSyncSign("0");
            bnInfApply.setSyncDate(new Date());
            Date beginTime = infApply.getApply_date();
            //设置办结总天数
            if (CollectionUtils.isNotEmpty(infApplyResultList)) {
                Date endTime = infApplyResultList.get(0).getFinish_date() == null ? DateUtils.getCurDateHMS() : infApplyResultList.get(0).getCreate_date();
                int totalDays = getday(beginTime, endTime);
                bnInfApply.setTotalWorkdays(totalDays);
            } else {
                bnInfApply.setTotalWorkdays(30);
            }
            Example bnInfApplyExample = new Example(BnInfApply.class);
            bnInfApplyExample.createCriteria().andEqualTo("internalNo", bnInfApply.getInternalNo()).andEqualTo("deptYwRegNo", bnInfApply.getDeptYwRegNo());
            List<BnInfApply> bnInfApplyList = yzwEntityMapper.selectByExampleNotNull(bnInfApplyExample);
            if (CollectionUtils.isNotEmpty(bnInfApplyList)) {
                bnInfApply.setNo(bnInfApplyList.get(0).getNo());
                yzwEntityMapper.updateByExampleSelectiveNotNull(bnInfApply, bnInfApplyExample);
            } else {
                yzwEntityMapper.insertSelective(bnInfApply);
            }
        }
    }


    @Override
    public void pushInfApplyProcess(List<InfApplyProcess> infApplyProcessList, List<InfApplyWlxx> infApplyWlxxList) {
        if (CollectionUtils.isNotEmpty(infApplyProcessList) && StringUtils.isNotBlank(infApplyProcessList.get(0).getInternal_no())) {
            int eventNumber = 1;
            for (InfApplyProcess infApplyProcess : infApplyProcessList) {
                //设置办结天数
                Date beginTime = infApplyProcess.getStart_time();
                Date endTime = infApplyProcess.getEnd_time() == null ? DateUtils.getCurDateHMS() : infApplyProcess.getEnd_time();
                int totalDays = getday(beginTime, endTime);
                //根据dozer转换
                BnInfApplyProcess bnInfApplyProcess = dozerMapper.map(infApplyProcess, BnInfApplyProcess.class);
                bnInfApplyProcess.setSyncDate(new Date());
                bnInfApplyProcess.setWorkDays(totalDays);

                bnInfApplyProcess.setEventNumber(eventNumber);
                bnInfApplyProcess.setSyncSign("0");
                Example example = new Example(BnInfApplyProcess.class);
                example.createCriteria().andEqualTo("internalNo", infApplyProcess.getInternal_no())
                        .andEqualTo("eventName", infApplyProcess.getEvent_name());
                List<BnInfApplyProcess> bnInfApplyProcessList = yzwEntityMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isNotEmpty(bnInfApplyProcessList)) {
                    bnInfApplyProcess.setNo(bnInfApplyProcessList.get(0).getNo());
                    yzwEntityMapper.updateByExampleSelectiveNotNull(bnInfApplyProcess, example);
                } else {
                    yzwEntityMapper.insertSelective(bnInfApplyProcess);
                }
                eventNumber = ++eventNumber;
            }
        }
    }

    @Override
    public void pushInfApplyResult(List<InfApplyResult> infApplyResultList) {
        if (CollectionUtils.isNotEmpty(infApplyResultList) && StringUtils.isNotBlank(infApplyResultList.get(0).getInternal_no())) {
            for (InfApplyResult infApplyResult : infApplyResultList) {
                //根据dozer转换
                BnInfApplyResult bnInfApplyResult = dozerMapper.map(infApplyResult, BnInfApplyResult.class);
                bnInfApplyResult.setSyncDate(new Date());
                bnInfApplyResult.setSyncSign("0");
                Example example = new Example(BnInfApplyResult.class);
                example.createCriteria().andEqualTo("internalNo", infApplyResultList.get(0).getInternal_no());
                List<BnInfApplyResult> bnInfApplyResultList = yzwEntityMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isNotEmpty(bnInfApplyResultList)) {
                    bnInfApplyResult.setNo(bnInfApplyResultList.get(0).getNo());
                    yzwEntityMapper.updateByExampleSelectiveNotNull(bnInfApplyResult, example);
                } else {
                    yzwEntityMapper.insertSelective(bnInfApplyResult);
                }
            }
        }
    }

    @Override
    public void pushInfApplyMaterial(List<InfApplyMaterial> infApplyMaterialList) {
        if (CollectionUtils.isNotEmpty(infApplyMaterialList) && StringUtils.isNotBlank(infApplyMaterialList.get(0).getInternal_no())) {
            for (InfApplyMaterial infApplyMaterial : infApplyMaterialList) {
                //根据dozer转换
                TYwblAttachment tYwblAttachment = dozerMapper.map(infApplyMaterial, TYwblAttachment.class);
                tYwblAttachment.setSyncDate(new Date());
                tYwblAttachment.setSyncSign("1");
                Example example = new Example(TYwblAttachment.class);
                example.createCriteria().andEqualTo("internalNo", infApplyMaterial.getInternal_no());
                example.createCriteria().andEqualTo("sjFileName", infApplyMaterial.getSj_file_name());
                List<TYwblAttachment> tYwblAttachmentList = yzwEntityMapper.selectByExampleNotNull(example);
                if (CollectionUtils.isNotEmpty(tYwblAttachmentList)) {
                    tYwblAttachment.setNo(tYwblAttachmentList.get(0).getNo());
                    yzwEntityMapper.updateByExampleSelectiveNotNull(tYwblAttachment, example);
                } else {
                    yzwEntityMapper.insertSelective(tYwblAttachment);
                }
            }
        }
    }

    private int getday(Date beginTime, Date endTime) {
        int days = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String bDate = sdf.format(beginTime);
            String eDate = sdf.format(endTime);
            beginTime = sdf.parse(bDate);
            endTime = sdf.parse(eDate);
            days = (int) ((endTime.getTime() - beginTime.getTime()) / (24 * 3600 * 1000));
            long yushu = (endTime.getTime() - beginTime.getTime()) % (24 * 3600 * 1000);
            if (String.valueOf(yushu) != null) {
                days += 1;
            }
        } catch (ParseException e) {
            logger.error("error:", e);
        }
        return days;
    }

}
