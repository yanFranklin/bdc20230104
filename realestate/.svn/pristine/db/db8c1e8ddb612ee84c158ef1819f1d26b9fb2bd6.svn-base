package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.accept.service.BdcBgxxDbService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcBgxxDbMxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcBgxxDbVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcBgxxQlrVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/10/21.
 * @description
 */
@Service
public class BdcBgxxDbServiceImpl implements BdcBgxxDbService {

    @Autowired
    BdcInitFeignService bdcInitFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    LogMessageClient logMessageClient;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Override
    public void addBdcYwxxToEs(String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            Page<AuditLogDto> auditLogDtoPage = this.queryEsLog(gzlslid);
            if(null != auditLogDtoPage && auditLogDtoPage.hasContent()){
                return;
            }
            // es中未存储业务数据时，获取业务数据并存储
            List<BdcBgxxDbVO> bdcBgxxDbVOList = this.getBdcYwxx(null, gzlslid);
            String encryptLog = JSON.toJSONString(bdcBgxxDbVOList);
            this.addEsLog(encryptLog, gzlslid);
        }
    }

    @Override
    public List<BdcBgxxDbVO> getBdcYwxx(String xmid, String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException("未获取到工作流实例ID");
        }
        List<BdcBgxxDbVO> bdcBgxxDbVOList = new ArrayList<>(10);
        if(StringUtils.isNotBlank(xmid)){
            BdcBgxxDbVO bdcBgxxDbVO = this.getBdcxx(xmid);
            if(Objects.nonNull(bdcBgxxDbVO)){
                bdcBgxxDbVOList.add(bdcBgxxDbVO);
            }
        }else{
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            for(BdcXmDTO bdcXmDTO:bdcXmDTOList){
                BdcBgxxDbVO bdcBgxxDbVO = this.getBdcxx(bdcXmDTO.getXmid());
                if(Objects.nonNull(bdcBgxxDbVO)){
                    bdcBgxxDbVOList.add(bdcBgxxDbVO);
                }
            }
        }
        return bdcBgxxDbVOList;
    }

    /**
     * 获取不动产项目、 不动产权利、 权利人信息
     */
    private BdcBgxxDbVO getBdcxx(String xmid){
        if(StringUtils.isNotBlank(xmid)){
            BdcBgxxDbVO bdcBgxxDbVO = new BdcBgxxDbVO();
            // 获取不动产项目信息
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                BdcXmDO bdcxm = bdcXmDOList.get(0);
                bdcBgxxDbVO.setBdcXm(bdcXmDOList.get(0));
                bdcBgxxDbVO.withJbxx(xmid, bdcxm.getQllx(), bdcxm.getBdcdyh());
            }
            // 获取权利信息
            BdcQl bdcQl = this.bdcQllxFeignService.queryQlxx(xmid);
            bdcBgxxDbVO.setBdcQl(bdcQl);

            // 获取申请人信息
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            List<BdcQlrDO> bdcQlrQOList = this.bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            bdcBgxxDbVO.setBdcQlrList(bdcQlrQOList);
            return bdcBgxxDbVO;
        }
        return null;
    }

    @Override
    public Object compareYwxx(String gzlslid) {
        String historyYwxx = this.getEsLog(gzlslid);
        if(StringUtils.isBlank(historyYwxx)){
            throw new MissingArgumentException("未记录变更前的业务信息");
        }
        List<BdcBgxxDbVO> currentBgxxList = this.getBdcYwxx(null, gzlslid);
        if(CollectionUtils.isEmpty(currentBgxxList)){
            throw new MissingArgumentException("未获取到当前流程的业务信息");
        }
        String currentYwxx = JSON.toJSONString(currentBgxxList);
        if(historyYwxx.compareTo(currentYwxx) == 0){
            // 对比信息相同
            return null;
        }else{
            // 对比信息不同，进行数据过滤
            List<BdcBgxxDbVO> historyBgxxList = this.convertToBdcBgxx(historyYwxx);
            return filterDiffVO(currentBgxxList, historyBgxxList);
        }
    }

    /**
     * 对比当前流程业务数据与原始流程业务存在的差异，并返回差异对象
     */
    private Object filterDiffVO(List<BdcBgxxDbVO> currentList, List<BdcBgxxDbVO> historyList){
        Map<String,BdcBgxxDbVO> historyBgxxMap = historyList.stream().collect(Collectors.toMap(BdcBgxxDbVO::getXmid, BdcBgxxDbVO->BdcBgxxDbVO));
        List<BdcBgxxDbMxVO> bdcBgxxDbMxVOList = new ArrayList<>(currentList.size());
        for(BdcBgxxDbVO current: currentList) {
            BdcBgxxDbVO history = historyBgxxMap.get(current.getXmid());
            if (Objects.nonNull(history)) {
                BdcBgxxDbMxVO dbMxVO = new BdcBgxxDbMxVO(current.getBdcdyh(), current.getQllx());
                //比对项目数据，存在差异时，将原不动产项目与现不动产项目，加入到差异的Map中
                if (!equalsObj(current.getBdcXm(), history.getBdcXm())) {
                    dbMxVO.setBdcXm(current.getBdcXm());
                    dbMxVO.setYbdcXm(history.getBdcXm());
                }
                if (!equalsObj(current.getBdcQl(), history.getBdcQl())) {
                    dbMxVO.setTableName(StringUtils.lowerCase(current.getBdcQl().getClass().getAnnotation(Table.class).name()));
                    dbMxVO.setBdcQl(current.getBdcQl());
                    dbMxVO.setYbdcQl(history.getBdcQl());
                }
                // 判断权利人是否为空情况
                if (!Objects.deepEquals(current.getBdcQlrList(), history.getBdcQlrList())) {
                    dbMxVO.setBdcBgxxQlrVOList(this.compareQlrxx(current.getBdcQlrList(), history.getBdcQlrList()));
                }
                bdcBgxxDbMxVOList.add(dbMxVO);
            }
        }
        return bdcBgxxDbMxVOList;
    }

    /**
     * 对象比对方法，采用Objects.deepEquals方法比对，对象（toString）方法生成的String
     */
    private boolean equalsObj(Object a, Object b){
        if(null == a && null == b){
            return true;
        }
        if(null != a && null != b){
            return Objects.deepEquals(a.toString(), b.toString());
        }
        return false;
    }

    /**
     * 比对权利人信息，返回存在差异的权利人信息
     */
    private List<BdcBgxxQlrVO> compareQlrxx(List<BdcQlrDO> currentList,List<BdcQlrDO> historyList){
        List<BdcBgxxQlrVO> bdcBgxxQlrVOList = new ArrayList<>(10);
        // 现有权利人数据为空时，则现有权利人数据为删除
        if(CollectionUtils.isEmpty(currentList)){
            historyList.stream().forEach(item->{
                BdcBgxxQlrVO bdcBgxxQlrVO = new BdcBgxxQlrVO.Builder().type(BdcBgxxQlrVO.RYBGLX_DELETE)
                        .ybdcQlrDO(item).build();
                bdcBgxxQlrVOList.add(bdcBgxxQlrVO);
            });
        }
        // 历史权利人数据为空时，则现有权利人为新增
        if(CollectionUtils.isEmpty(historyList)){
            currentList.stream().forEach(item->{
                BdcBgxxQlrVO bdcBgxxQlrVO = new BdcBgxxQlrVO.Builder().type(BdcBgxxQlrVO.RYBGLX_ADD)
                        .bdcQlrDO(item).build();
                bdcBgxxQlrVOList.add(bdcBgxxQlrVO);
            });
        }

        Map<String,BdcQlrDO> historyQlrMap = historyList.stream().collect(Collectors.toMap(BdcQlrDO::getQlrid, BdcQlrDO->BdcQlrDO));
        // 比对历史和现在的权利人，将相同权利人id，不同的权利人信息的添加到变更信息中
        for(BdcQlrDO currentQlr:currentList){
            BdcQlrDO historyQlr = historyQlrMap.get(currentQlr.getQlrid());
            historyQlrMap.remove(currentQlr.getQlrid());
            if(!equalsObj(currentQlr, historyQlr)){
                BdcBgxxQlrVO bdcBgxxQlrVO = new BdcBgxxQlrVO.Builder().type(BdcBgxxQlrVO.RYBGLX_UPDATE)
                        .bdcQlrDO(currentQlr).ybdcQlrDO(historyQlr).build();
                if(Objects.isNull(historyQlr)){
                    bdcBgxxQlrVO.setType(BdcBgxxQlrVO.RYBGLX_ADD);
                }
                bdcBgxxQlrVOList.add(bdcBgxxQlrVO);
            }
        }
        if(MapUtils.isNotEmpty(historyQlrMap)){
            for(Map.Entry<String, BdcQlrDO> entry: historyQlrMap.entrySet()){
                BdcBgxxQlrVO bdcBgxxQlrVO = new BdcBgxxQlrVO.Builder().type(BdcBgxxQlrVO.RYBGLX_DELETE)
                        .ybdcQlrDO(entry.getValue()).build();
                bdcBgxxQlrVOList.add(bdcBgxxQlrVO);
            }
        }
        return this.sort(bdcBgxxQlrVOList);
    }

    private List<BdcBgxxQlrVO> sort(List<BdcBgxxQlrVO> bdcBgxxQlrVOList){
        if(CollectionUtils.isNotEmpty(bdcBgxxQlrVOList)){
            Collections.sort(bdcBgxxQlrVOList,new Comparator<BdcBgxxQlrVO>() {
                @Override
                public int compare(BdcBgxxQlrVO o1, BdcBgxxQlrVO o2) {
                    BdcQlrDO opt1 = Optional.ofNullable(o1.getBdcQlrDO()).orElse(o1.getYbdcQlrDO());
                    BdcQlrDO opt2 = Optional.ofNullable(o2.getBdcQlrDO()).orElse(o2.getYbdcQlrDO());
                    if(Integer.parseInt(opt1.getQlrlb())< Integer.parseInt(opt2.getQlrlb())){
                        return -1;
                    }else if(Integer.parseInt(opt1.getQlrlb())> Integer.parseInt(opt2.getQlrlb())){
                        return 1;
                    }else{
                        Integer sxh1 = Optional.ofNullable(opt1.getSxh()).orElse(0);
                        Integer sxh2 = Optional.ofNullable(opt2.getSxh()).orElse(0);
                        if(sxh1 < sxh2){
                            return -1;
                        }else if(sxh1 > sxh2){
                            return 1;
                        }else{
                            return 0;
                        }
                    }
                }
            });
        }
        return bdcBgxxQlrVOList;
    }

    /**
     * 将json数据转换为BdcBgxxDbVO对象
     */
    private List<BdcBgxxDbVO> convertToBdcBgxx(String json){
        JSONArray jsonArray = JSON.parseArray(json);
        List<BdcBgxxDbVO> bdcBgxxDbVOList = new ArrayList<>(jsonArray.size());
        for(int i =0; i<jsonArray.size(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            BdcBgxxDbVO bdcBgxxDbVO = jsonObject.toJavaObject(BdcBgxxDbVO.class);
            JSONObject qlObject = jsonObject.getJSONObject("bdcQl");
            if(null != qlObject){
                String qllx = Optional.ofNullable(bdcBgxxDbVO.getBdcXm().getQllx().toString()).orElse("");
                if(StringUtils.isNotBlank(qllx)){
                    BdcQl bdcQl = bdcQllxFeignService.makeSureQllx(qllx);
                    bdcBgxxDbVO.setBdcQl(JSON.parseObject(jsonObject.getString("bdcQl"), bdcQl.getClass()));
                }
            }
            bdcBgxxDbVOList.add(bdcBgxxDbVO);
        }
        return bdcBgxxDbVOList;
    }

    /**
     * 根据ES的Key值查询存储的信息
     */
    private String getEsLog(String gzlslid){
        String ywxx = "";
        Page<AuditLogDto> auditLogDtoPage = this.queryEsLog(gzlslid);
        if (auditLogDtoPage != null && auditLogDtoPage.hasContent()) {
            // 取第一条数据
            List<DataValue> dataValueList = auditLogDtoPage.getContent().get(0).getBinaryAnnotations();
            if (CollectionUtils.isNotEmpty(dataValueList)) {
                for (DataValue dataValue : dataValueList) {
                    String key = dataValue.getKey().toLowerCase();
                    if ("ywxx".equals(key)) {
                        ywxx = dataValue.getValue();
                    }
                }
            }
        }
        return StringUtils.isBlank(ywxx) ? "": RSAEncryptUtils.decrypt(ywxx);
    }

    /**
     * 添加ES日志
     */
    private void addEsLog(String ywxx, String key){
        Map<String, Object> data = new HashMap<>(8);
        data.put("ywxx", RSAEncryptUtils.encrypt(ywxx));
        data.put(Constants.ES_PARAMCH, key);
        data.put(CommonConstantUtils.VIEW_TYPE_NAME, "流程初始化数据");
        data.put("eventName",  Constants.YWSJ);
        AuditEvent auditEvent = new AuditEvent("", Constants.YWSJ, data);
        zipkinAuditEventRepository.add(auditEvent);
    }

    /**
     * 查询es日志
     */
    private Page<AuditLogDto> queryEsLog(String gzlslid){
        List<QueryLogCondition> conditions = Lists.newArrayList();
        QueryLogCondition queryLogCondition = new QueryLogCondition();
        queryLogCondition.setType(CommonConstantUtils.TYPE_EQUAL);
        queryLogCondition.setKey(Constants.ES_PARAMCH);
        queryLogCondition.setValue(gzlslid);
        conditions.add(queryLogCondition);
        return logMessageClient.listLogs(0, 1, Constants.YWSJ, null, null, null, null, conditions);
    }

}
