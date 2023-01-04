package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShLogDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzLwDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.engine.core.mapper.BdcGzlwMapper;
import cn.gtmap.realestate.engine.core.service.BdcGzlwService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/3/26
 * @description
 */
@Service
public class BdcGzlwServiceImpl implements BdcGzlwService{
    private Logger logger = LoggerFactory.getLogger(BdcGzlwServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    private BdcGzlwMapper bdcGzlwMapper;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Value("${html.version:}")
    private String version;

    /**
     * 保存例外审核信息
     * @param bdcGzlwShDO 例外信息
     */
    @Override
    public void addShxxData(BdcGzlwShDO bdcGzlwShDO) {
        if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(version)) {
            // 盐城对于相同件多次例外的操作只保存一次
            Example example = new Example(BdcGzlwShDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("slbh", bdcGzlwShDO.getSlbh());
            criteria.andEqualTo("bdcdyh", bdcGzlwShDO.getBdcdyh());
            criteria.andEqualTo("gzid", bdcGzlwShDO.getGzid());
            criteria.andEqualTo("xmid", bdcGzlwShDO.getXmid());
            List<BdcGzLwDO> gzLwDOList = entityMapper.selectByExampleNotNull(example);
            if(CollectionUtils.isNotEmpty(gzLwDOList) && null != gzLwDOList.get(0)) {
                logger.info("当前例外审核记录已存在，不再重复保存，对应例外审核信息：{}", JSON.toJSONString(bdcGzlwShDO));
                return;
            }
        }

        entityMapper.insertSelective(bdcGzlwShDO);
    }

    @Override
    public void updateBdcGzlwShzt(String processInsId, String currentUserName) {
        if (StringUtils.isBlank(currentUserName)) {
            currentUserName = userManagerUtils.getCurrentUserName();
        }
        if (StringUtils.isNoneBlank(processInsId, currentUserName)) {
            BdcGzlwShDO bdcGzlwShUpdate = new BdcGzlwShDO();
            bdcGzlwShUpdate.setGzlslid(processInsId);
            bdcGzlwShUpdate.setShzt(1);
            bdcGzlwShUpdate.setShr(currentUserName);
            bdcGzlwShUpdate.setShsj(new Date());
            bdcGzlwMapper.updateBdcGzlwShzt(bdcGzlwShUpdate);

            Example example = new Example(BdcGzlwShDO.class);
            example.createCriteria().andEqualTo("gzlslid", processInsId);
            List<BdcGzlwShDO> bdcGzlwShDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcGzlwShDOList)) {
                for (BdcGzlwShDO bdcGzlwShDO : bdcGzlwShDOList) {
                    Map<String, Object> logdata = new HashMap<>();
                    logdata.put("CZ", JSON.toJSONString(bdcGzlwShDO));
                    logdata.put("CZRQ", new Date());
                    AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), "GZLW_SH", logdata);
                    zipkinAuditEventRepository.add(auditEvent);
                }
            }
        }
    }

    @Override
    public BdcGzlwShDO addShxxDataPl(List<BdcGzlwShDO> bdcGzlwShDOList, String slbh, String lwyy){
        if(CollectionUtils.isEmpty(bdcGzlwShDOList)){
            throw new AppException("未获取到例外审核数据");
        }
        // 南通受理编号前要加不动产单元号特征码
        if (CommonConstantUtils.SYSTEM_VERSION_NT.equals(version)&&StringUtils.isNotBlank(bdcGzlwShDOList.get(0).getBdcdyh())) {
            String tzm =BdcdyhToolUtils.queryTzmByBdcdyh(bdcGzlwShDOList.get(0).getBdcdyh());
            if(!slbh.contains(tzm)) {
                slbh = tzm + slbh;
            }
        }
        //数据去重
        lwshDataFilter(bdcGzlwShDOList, slbh);
        List<BdcGzlwShLogDO> bdcGzlwShLogDOList = new ArrayList<>();
        //数据入库
        if(CollectionUtils.isNotEmpty(bdcGzlwShDOList)){
            for(BdcGzlwShDO bdcGzlwShDO:bdcGzlwShDOList){
                bdcGzlwShDO.setGzlwid(UUIDGenerator.generate());
                bdcGzlwShDO.setSlbh(slbh);
                bdcGzlwShDO.setLwyy(lwyy);
                bdcGzlwShDO.setShzt(CommonConstantUtils.GZLW_SHZT_XJ);
                bdcGzlwShDO.setCjr(userManagerUtils.getCurrentUserName());
                UserDto userDto = userManagerUtils.getCurrentUser();
                if (userDto != null){
                    bdcGzlwShDO.setCjrmc(userDto.getAlias());
                }
                bdcGzlwShDO.setCjsj(new Date());
                //日志
                String bdcGzlwSh = JSON.toJSONString(bdcGzlwShDO);
                String encrypt = RSAEncryptUtils.encrypt(bdcGzlwSh);
                BdcGzlwShLogDO bdcGzlwShLogDO = new BdcGzlwShLogDO();
                bdcGzlwShLogDO.setGzlwlogid(UUIDGenerator.generate());
                bdcGzlwShLogDO.setScrq(new Date());
                bdcGzlwShLogDO.setCz(encrypt);
                bdcGzlwShLogDOList.add(bdcGzlwShLogDO);

            }
            entityMapper.insertBatchSelective(bdcGzlwShDOList);
            entityMapper.insertBatchSelective(bdcGzlwShLogDOList);
            return bdcGzlwShDOList.get(0);
        }
        return null;




    }

    @Override
    public List<BdcGzlwShDO> listBdcGzlwShBySlbh(String slbh){
        if(StringUtils.isNotBlank(slbh)){
            Example exampleSbh = new Example(BdcGzlwShDO.class);
            exampleSbh.createCriteria().andEqualTo("slbh", slbh);
            return entityMapper.selectByExample(exampleSbh);
        }
        return new ArrayList<>();

    }

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  例外审核数据去重
     */
    private void lwshDataFilter(List<BdcGzlwShDO> bdcGzlwShDOList, String slbh) {
        List<BdcGzlwShDO> gzlwShDOS =listBdcGzlwShBySlbh(slbh);
        List<String> gzlwJson = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(gzlwShDOS)) {
            for (BdcGzlwShDO bdcGzlwShDO : gzlwShDOS) {
                //通过bdcdyh,gzid,xmid去重
                BdcGzlwShDO distinctLwSh = new BdcGzlwShDO();
                distinctLwSh.setBdcdyh(bdcGzlwShDO.getBdcdyh());
                distinctLwSh.setGzid(bdcGzlwShDO.getGzid());
                distinctLwSh.setXmid(bdcGzlwShDO.getXmid());
                String json = JSONObject.toJSONString(distinctLwSh);
                gzlwJson.add(json);
            }
        }
        //存储过滤后的对象
        List<BdcGzlwShDO> container = new ArrayList<>();
        List<String> ywxxJson = new ArrayList<>();
        for (BdcGzlwShDO bdcGzlwShDO : bdcGzlwShDOList) {
            //通过bdcdyh,gzid,xmid去重
            if (StringUtils.isBlank(bdcGzlwShDO.getBdcdyh())) {
                throw new AppException("添加例外不动产单元号为空");
            }
            if (StringUtils.isBlank(slbh)) {
                throw new AppException("添加例外受理编号为空");
            }
            if (StringUtils.isBlank(bdcGzlwShDO.getXmid())) {
                throw new AppException("添加例外项目id为空");
            }
            if (StringUtils.isBlank(bdcGzlwShDO.getGzid())) {
                throw new AppException("添加例外规则id为空");
            }
            //通过bdcdyh,gzid,xmid去重
            BdcGzlwShDO distinctLwSh = new BdcGzlwShDO();
            distinctLwSh.setBdcdyh(bdcGzlwShDO.getBdcdyh());
            distinctLwSh.setGzid(bdcGzlwShDO.getGzid());
            distinctLwSh.setXmid(bdcGzlwShDO.getXmid());
            String json = JSONObject.toJSONString(distinctLwSh);
            if (!gzlwJson.contains(json) && !ywxxJson.contains(json)) {
                container.add(bdcGzlwShDO);
                ywxxJson.add(JSONObject.toJSONString(distinctLwSh));
            }
        }
        bdcGzlwShDOList.clear();
        bdcGzlwShDOList.addAll(container);

    }
}
