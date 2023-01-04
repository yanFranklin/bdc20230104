package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcGzlwService;
import cn.gtmap.realestate.accept.service.CshBdcSlXmService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcGzlwShLogDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCshSlxmDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcGzlwShQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzlwFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcGzlwRestService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/8/26
 * @description 不动产规则例外rest服务
 */
@RestController
@Api(tags = "不动产规则例外rest服务")
public class BdcGzlwRestController implements BdcGzlwRestService {
    @Autowired
    private BdcGzlwService bdcGzlwService;
    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    private BdcGzlwFeignService bdcGzlwFeignService;
    @Autowired
    private BdcGzlwFeignService bdcGzlwFeignServiceEngine;
    @Autowired
    private CshBdcSlXmService cshBdcSlXmService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcGzlwFeignService engineBdcGzlwFeignService;

    @Override
    public BdcGzlwShDO addShxxData(String data, String slbh, String xmid) {
        Map map = JSONObject.parseObject(data, Map.class);
        return bdcGzlwService.addShxxData(map, slbh, xmid);
    }

    @Override
    public void addShxxDataWithoutSlbh(@RequestBody String data, String qllx) {
        BdcCshSlxmDTO bdcCshSlxmDTO = JSONObject.parseObject(data, BdcCshSlxmDTO.class);
        bdcGzlwService.addShxxDataWithoutSlbh(bdcCshSlxmDTO, qllx);
    }

    @Override
    public Page<Map> queryBdcGzlw(Pageable pageable, String paramJson) {
        return bdcGzlwFeignServiceEngine.queryBdcGzlw(pageable,paramJson);
    }

    @Override
    public Page<Map> bdcgzlwGroupByBdcdyh(Pageable pageable, String paramJson) {
        return bdcGzlwFeignServiceEngine.bdcgzlwGroupByBdcdyh(pageable,paramJson);
    }

    @Override
    public List<BdcGzlwShDO> listBdcGzlw(String gzlslid) {
        return bdcGzlwFeignServiceEngine.queryBdcGzlwSh(gzlslid);
    }

    @Override
    public Integer updateBdcGzlwxx(@RequestBody List<BdcGzlwShDO> bdcGzlwShDOList) {
        return bdcGzlwFeignServiceEngine.updateBdcGzlwxx(bdcGzlwShDOList);
    }

    @Override
    public Integer updateBdcGzlw(String data, boolean accept, String shyj) {
        JSONArray array = JSONObject.parseArray(data);
        List<BdcGzlwShDO> bdcGzlwShDOList = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            BdcGzlwShDO bdcGzlwShDO = JSONObject.parseObject(JSONObject.toJSONString(jsonObject), BdcGzlwShDO.class);
            bdcGzlwShDOList.add(bdcGzlwShDO);
        }

        Integer updateCount = 0;
        List<BdcGzlwShLogDO> bdcGzlwShLogDOList = new ArrayList<>();
        Integer shzt =null;
        Set<String> slbhSet =new HashSet<>();
        for (BdcGzlwShDO bdcGzlwShDO : bdcGzlwShDOList) {
            if(StringUtils.isNotBlank(bdcGzlwShDO.getSlbh())) {
                slbhSet.add(bdcGzlwShDO.getSlbh());
            }
            if (accept) {
                shzt =CommonConstantUtils.GZLW_SHZT_TG;
                bdcGzlwShDO.setShzt(CommonConstantUtils.GZLW_SHZT_TG);
            } else {
                shzt =CommonConstantUtils.GZLW_SHZT_BTG;
                bdcGzlwShDO.setShzt(CommonConstantUtils.GZLW_SHZT_BTG);
            }
            bdcGzlwShDO.setShr(userManagerUtils.getCurrentUserName());
            bdcGzlwShDO.setShrmc(userManagerUtils.getUserAlias());
            bdcGzlwShDO.setShsj(new Date());
            bdcGzlwShDO.setShyj(shyj);
            updateCount += bdcGzlwFeignService.updateBdcGzlw(bdcGzlwShDO);
            String bdcGzlwSh = JSON.toJSONString(bdcGzlwShDO);
            String encrypt = RSAEncryptUtils.encrypt(bdcGzlwSh);
            Map<String, Object> logdata = new HashMap<>();
            logdata.put("CZ", encrypt);
            logdata.put("CZRQ", new Date());
            AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), "GZLW_SH", logdata);
            zipkinAuditEventRepository.add(auditEvent);

            BdcGzlwShLogDO bdcGzlwShLogDO = new BdcGzlwShLogDO();
            bdcGzlwShLogDO.setGzlwlogid(UUIDGenerator.generate());
            bdcGzlwShLogDO.setScrq(new Date());
            bdcGzlwShLogDO.setCz(encrypt);
            bdcGzlwShLogDOList.add(bdcGzlwShLogDO);
        }

        if (CollectionUtils.isNotEmpty(bdcGzlwShLogDOList)) {
            bdcGzlwFeignService.addBdcGzlwLog(bdcGzlwShLogDOList);
        }
        //审核后的处理
        cshBdcSlXmService.afterGzlwsh(new ArrayList<>(slbhSet),shzt);
        return updateCount;
    }

    @Override
    public void deleteBdcGzlwSh(String gzlwid) {
        bdcGzlwFeignServiceEngine.deleteBdcGzlwSh(gzlwid);
    }

    @Override
    public void deleteBdcGzlwShByGzlw(@RequestBody BdcGzlwShDO bdcGzlwShDO) {
        bdcGzlwFeignServiceEngine.deleteBdcGzlwShByGzlw(bdcGzlwShDO);
    }

    @Override
    public List<BdcGzlwShDO> listBdcGzlwByParam(@RequestBody BdcGzlwShQO bdcGzlwShQO) {
        return bdcGzlwFeignServiceEngine.listBdcGzlwByBdcGzlwShQO(bdcGzlwShQO);
    }

    @Override
    public List<BdcGzlwShDO> listBdcGzlwShByGzlslid(@RequestParam(value = "gzlslid") String gzlslid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            String slbh = bdcXmDOList.get(0).getSlbh();
            BdcGzlwShQO bdcGzlwShQO = new BdcGzlwShQO();
            bdcGzlwShQO.setSlbh(slbh);
            bdcGzlwShQO.setShzt(1);
            List<BdcGzlwShDO> bdcGzlwShDOList = engineBdcGzlwFeignService.listBdcGzlwByBdcGzlwShQO(bdcGzlwShQO);
            if (CollectionUtils.isNotEmpty(bdcGzlwShDOList)) {
                bdcGzlwShDOList = bdcGzlwShDOList.stream()
                        .filter(lwsh -> StringUtils.isNotBlank(lwsh.getLwyy()))
                        .collect(Collectors.collectingAndThen(Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(BdcGzlwShDO::getLwyy))), ArrayList::new));
            }
            return bdcGzlwShDOList;
        }
        return null;
    }
}
