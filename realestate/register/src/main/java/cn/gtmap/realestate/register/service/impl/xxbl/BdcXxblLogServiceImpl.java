package cn.gtmap.realestate.register.service.impl.xxbl;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.enums.LogEventEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDbVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcXxblLogVO;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.LogCompareUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.service.xxbl.BdcXxblLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 信息补录日志服务实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 10:42 上午 2020/3/19
 */
@Service
public class BdcXxblLogServiceImpl implements BdcXxblLogService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXxblLogServiceImpl.class);
    /**
     * 当前类名
     */
    private static final String CLASS_NAME = BdcXxblLogServiceImpl.class.getName();
    /**
     * 日志查询 key
     */
    private static final String PARAMCHA = "paramCha";
    /**
     * 用户操作工具类
     */
    @Autowired
    protected UserManagerUtils userManagerUtils;
    /**
     * 初始化业务服务
     */
    @Autowired
    private BdcInitFeignService bdcInitFeignService;
    /**
     * zipkin 日志服务
     */
    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    /**
     * 初始化权利类型服务
     */
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    /**
     * 权利人服务
     */
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    /**
     * 大云日志相关服务
     */
    @Autowired
    private LogMessageClient logMessageClient;
    /**
     * 初始化证书服务
     */
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private CompareHandler compareHandler;

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    /**
     * 根据参数和日志类型查询日志
     *
     * @param pageable 分页参数，不传默认 page 0，size 10
     * @param paramch  查询参数
     * @param event    对应的 event 类型
     * @return {Page<AuditLogDto>} 大云分页对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public Page<AuditLogDto> listLog(Pageable pageable, String paramch, LogEventEnum event) {
        // 组织查询参数
        List<QueryLogCondition> conditions = Lists.newArrayList();
        QueryLogCondition queryLogCondition = new QueryLogCondition();
        queryLogCondition.setType(CommonConstantUtils.TYPE_EQUAL);
        queryLogCondition.setKey(PARAMCHA);
        queryLogCondition.setValue(paramch);
        conditions.add(queryLogCondition);
        // 处理分页参数
        int pageNumber = pageable != null ? pageable.getPageNumber() : 0;
        int pageSize = pageable != null ? pageable.getPageSize() : 10;

        Page<AuditLogDto> auditLogDtos = logMessageClient.listLogs(pageNumber, pageSize, event.key(),
                null, null, null, null, conditions);
        LOGGER.debug("{}: 查询日志结果：{}", CLASS_NAME, JSON.toJSONString(auditLogDtos));
        return auditLogDtos;
    }

    /**
     * 处理日志分页数据<br/>
     * 数据回溯的分页处理
     *
     * @param auditLogDtoPage 日志分页对象
     * @return 日志 VO 对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<BdcXxblLogVO> dealLogPage(Page<AuditLogDto> auditLogDtoPage) {
        List<BdcXxblLogVO> logList = Lists.newArrayList();
        if (auditLogDtoPage != null && auditLogDtoPage.hasContent()) {
            BdcXxblLogVO log;
            for (AuditLogDto auditLogDto : auditLogDtoPage) {
                // 解析数据
                log = dealAuditLogDto(auditLogDto);
                if (log != null) {
                    logList.add(log);
                }
            }
        }
        return logList;
    }

    /**
     * @param id 回退的日志 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 选择指定 id 的日志中 before 数据回退
     */
    @Override
    public void backLog(String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new MissingArgumentException("id");
        }
        BdcXxblLogVO bdcXxblLogVO = dealAuditLogDto(logMessageClient.getAuditLogDetail(id));
        if (bdcXxblLogVO != null) {
            // 退回操作也需要记录日志，此处初始化 changelist
            String bdcYwxxDTOAfter = bdcXxblLogVO.getAfter();
            String bdcYwxxDTOBefore = bdcXxblLogVO.getBefore();
            // 字符串转对象
            BdcYwxxDTO before = this.convertJsonToYwxx(bdcYwxxDTOBefore);
            BdcYwxxDTO after = this.convertJsonToYwxx(bdcYwxxDTOAfter);
            String gzlslid = after.getBdcXm().getGzlslid();
            // 先删除证书信息再还原
            bdcZsInitFeignService.deleteBdcZs(gzlslid);
            // 删除权利人信息
            bdcQlrFeignService.deleteBatchBdcQlr(gzlslid, "");

            // 更新业务信息
            bdcInitFeignService.updateYwxx(bdcXxblLogVO.getXmid(), before);
            // 回溯后记录日志
            Map<String, Object> data = LogCompareUtils.initData(bdcXxblLogVO.getXmid(), after, before);
            AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), "xxbl", data);
            zipkinAuditEventRepository.add(auditEvent);
        }
    }

    /**
     * @param json json 字符串
     * @return BdcYwxxDTO 业务信息对象
     * @throws Exception 初始化更新业务信息出现异常
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 将 json 字符串转换为 ywxx 后通过初始化接口更新到数据库中
     * <strong>bdcQl 是接口所以在转换时需要特殊处理固提取此方法</strong>
     */
    @Override
    public void backYwxxFromJson(String json) throws Exception {
        if (StringUtils.isBlank(json)) {
            throw new MissingArgumentException("json");
        }
        BdcYwxxDTO bdcYwxxDTO = this.convertJsonToYwxx(json);
        String xmid = bdcYwxxDTO.getBdcXm().getXmid();
        LOGGER.warn("{}: 回退业务信息到指定内容：{}", CLASS_NAME, json);
        if (StringUtils.isBlank(xmid)) {
            LOGGER.warn("{}: 未获取到指定的 xmid：{}", CLASS_NAME, xmid);
        }
        // 更新业务信息
        bdcInitFeignService.updateYwxx(xmid, bdcYwxxDTO);
    }

    /**
     * @param json json 字符串
     * @return BdcYwxxDTO 业务信息对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 将 json 字符串转换成 ywxx 对象 <br>
     * <strong>bdcQl 是接口所以在转换时需要特殊处理固提取此方法</strong>
     */
    @Override
    public BdcYwxxDTO convertJsonToYwxx(String json) {
        // String 字符串直接转换为 ywxxDTO
        BdcYwxxDTO ywxxDTO = JSON.parseObject(json, BdcYwxxDTO.class);
        // bdcql 由于是接口所以需要进行特殊处理
        JSONObject jsonObject = JSON.parseObject(json);
        // 确认权利类型
        BdcQl bdcQl = bdcQllxFeignService.makeSureQllx(ywxxDTO.getBdcXm().getQllx().toString());
        // 获取字符串中的权利对象
        JSONObject qlObject = jsonObject.getJSONObject("bdcQl");
        // 权利对象赋值
        try {
            if (qlObject != null) {
                String className = qlObject.getString("@Clazz");
                if (StringUtils.isNotBlank(className)) {
                    Class<?> clazz = Class.forName(className);
                    if (BdcQl.class.isAssignableFrom(clazz)) {
                        ywxxDTO.setBdcQl((BdcQl) JSON.parseObject(jsonObject.getString("bdcQl"), clazz));
                    }
                } else {
                    ywxxDTO.setBdcQl(JSON.parseObject(jsonObject.getString("bdcQl"), bdcQl.getClass()));
                }
            }
        } catch (Exception e) {
            LOGGER.warn("信息补录日志服务处理 JSON 转换 YWXX 中权利信息出现异常", e);
        }
        return ywxxDTO;
    }

    /**
     * @param xmid 当前项目 id
     * @return {String} 日志中对应的 ywxx json 字符串
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 查询当前补录修改流程的初始化日志，返回 ywxx值
     */
    @Override
    public String queryBllcModifyLog(String xmid) {
        String ywxxJson = null;
        // 查询日志
        Page<AuditLogDto> auditLogDtoPage = listLog(null, xmid, LogEventEnum.BLLC_MODIFY);
        if (auditLogDtoPage.hasContent()) {
            for (AuditLogDto auditLogDto : auditLogDtoPage) {
                List<DataValue> dataValueList = auditLogDto.getBinaryAnnotations();
                if (CollectionUtils.isNotEmpty(dataValueList)) {
                    for (DataValue dataValue : dataValueList) {
                        String key = dataValue.getKey().toLowerCase();
                        if (StringUtils.equalsIgnoreCase(key, "ywxx")) {
                            ywxxJson = dataValue.getValue();
                        }
                    }
                }
            }
            LOGGER.debug("{} :查询 xmid：{} 初始化是原项目业务信息为：{}！", CLASS_NAME, xmid, ywxxJson);
        }
        return ywxxJson;
    }

    /**
     * 解析平台获取日志接口返回数据
     *
     * @param auditLog 接口返回数据
     * @return {BdcXxblLogVO} 信息补录日志对象
     * @date 2019/6/27
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private BdcXxblLogVO dealAuditLogDto(AuditLogDto auditLog) {
        if (auditLog == null) {
            LOGGER.warn("{}: 解析平台获取日志接口返回数据中止，原因：未传入有效参数", CLASS_NAME);
            return null;
        }
        BdcXxblLogVO log = new BdcXxblLogVO();
        log.setTime(auditLog.getTimestamp_millis());
        log.setId(auditLog.getId());
        // 用户真实姓名
        if (StringUtils.isNotBlank(auditLog.getPrincipal())) {
            UserDto userDto = userManagerUtils.getUserByName(auditLog.getPrincipal());
            log.setPrincipal(userDto != null ? userDto.getAlias() : StringUtils.EMPTY);
        } else {
            log.setPrincipal("");
        }
        // 解析数据
        List<DataValue> dataValueList = auditLog.getBinaryAnnotations();
        if (CollectionUtils.isNotEmpty(dataValueList)) {
            for (DataValue dataValue : dataValueList) {
                String key = dataValue.getKey().toLowerCase();
                switch (key) {
                    case "before":
                        log.setBefore(RSAEncryptUtils.decrypt(dataValue.getValue()));
                        break;
                    case "after":
                        log.setAfter(RSAEncryptUtils.decrypt(dataValue.getValue()));
                        break;
                    case "change":
                        log.setChange(RSAEncryptUtils.decrypt(dataValue.getValue()));
                        break;
                    case "viewtypename":
                        log.setCzsm(dataValue.getValue());
                        break;
                    case "paramcha":
                        log.setXmid(dataValue.getValue());
                        break;
                    case "xgyy":
                        if(StringUtils.isNotBlank(dataValue.getValue())) {
                            List<Map> zdMapList = bdcZdFeignService.queryBdcZd("xgyy");
                            if (CollectionUtils.isNotEmpty(zdMapList)) {
                                for (Map map : zdMapList) {
                                    if (StringUtils.equals(dataValue.getValue(), MapUtils.getString(map, "DM"))) {
                                        if (Objects.nonNull(map.get("MC"))) {
                                            log.setXgyy(map.get("MC").toString());
                                        }
                                    }
                                }
                            }
                            if(StringUtils.isBlank(log.getXgyy())){
                                log.setXgyy(dataValue.getValue());
                            }
                        }
                        break;
                    default:
                }
            }
        }
        return log;
    }

    /**
     * 获取信息补录修改记录对比
     *
     * @param processInsId 工作流定义ID
     * @return {List<BdcDbVO>} 对比 VO 集合
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @Override
    public List<BdcDbVO>  getSjdb(String processInsId,String xmid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOS)){
            // 组织查询参数
            List<QueryLogCondition> conditions = Lists.newArrayList();
            QueryLogCondition queryLogCondition = new QueryLogCondition();
            queryLogCondition.setType(CommonConstantUtils.TYPE_EQUAL);
            queryLogCondition.setKey(PARAMCHA);
            queryLogCondition.setValue(bdcXmDOS.get(0).getXmid());
            conditions.add(queryLogCondition);
            LogEventEnum event = LogEventEnum.XXBL;
            Page<AuditLogDto> auditLogDtos = logMessageClient.listLogs(0, 100, event.key(),
                    null, null, null, null, conditions);
            List<BdcXxblLogVO> bdcXxblLogVOS = dealLogPage(auditLogDtos);
            if(CollectionUtils.isNotEmpty(bdcXxblLogVOS)) {
                bdcXxblLogVOS = bdcXxblLogVOS.stream().sorted(Comparator.comparing(BdcXxblLogVO::getTime)).collect(Collectors.toList());
                BdcXxblLogVO beforeBdcXxblLogVO = bdcXxblLogVOS.get(0);
                BdcXxblLogVO afterBdcXxblLogVO = bdcXxblLogVOS.get(bdcXxblLogVOS.size() - 1);
                //第一次修改前的数据字符串
                String beforeStr = beforeBdcXxblLogVO.getBefore();
                //最后一次修改后数据字符串
                String afterStr = afterBdcXxblLogVO.getAfter();
                JSONObject beforeObj = JSON.parseObject(beforeStr);
                JSONObject afterObj = JSON.parseObject(afterStr);
                BdcYwxxDTO bdcYwxxDTOBefore = JSON.parseObject(beforeStr, BdcYwxxDTO.class);
                BdcYwxxDTO bdcYwxxDTOAfter = JSON.parseObject(afterStr, BdcYwxxDTO.class);
                //权利信息特殊处理下
                BdcXmDO beforebdcXmDO = bdcYwxxDTOBefore.getBdcXm();
                BdcXmDO afterBdcxmDO = bdcYwxxDTOAfter.getBdcXm();
                if (Objects.nonNull(beforebdcXmDO) && Objects.nonNull(beforebdcXmDO.getQllx())) {
                    BdcQl beforeBdcQl = bdcQllxFeignService.makeSureQllx(String.valueOf(beforebdcXmDO.getQllx()));
                    BdcQl newQl = JSON.parseObject(JSON.toJSONString(beforeObj.getJSONObject("bdcQl")), beforeBdcQl.getClass());
                    bdcYwxxDTOBefore.setBdcQl(newQl);
                }
                if (Objects.nonNull(afterBdcxmDO) && Objects.nonNull(afterBdcxmDO.getQllx())) {
                    BdcQl afterBdcQl = bdcQllxFeignService.makeSureQllx(String.valueOf(afterBdcxmDO.getQllx()));
                    BdcQl newQl = JSON.parseObject(JSON.toJSONString(afterObj.getJSONObject("bdcQl")), afterBdcQl.getClass());
                    bdcYwxxDTOAfter.setBdcQl(newQl);
                }
                try {
                    List<BdcDbVO> bdcDbVOS = compareHandler.compareBdcYwxx(bdcYwxxDTOBefore, bdcYwxxDTOAfter);
                    return bdcDbVOS;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}