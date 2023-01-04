package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.BdcAccessLogDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzVO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.exchange.AccessLogFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.NationalAccessFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.util.RedisUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.redisson.executor.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/3/27
 * @description 上报日志台账
 */
@Controller
@RequestMapping("/accessLog")
public class AccessLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogController.class);
    @Autowired
    private AccessLogFeignService accessLogFeignService;
    @Autowired
    private NationalAccessFeignService nationalAccessFeignService;

    @Autowired
    BdcEntityFeignService bdcEntityFeignService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查看响应报文
     */
    @ResponseBody
    @RequestMapping(value = "/queryXybw")
    public String queryXybw(@NotBlank(message = "业务号不能为空") String ywh, String logType) {
        return accessLogFeignService.queryXybw(ywh, logType);
    }

    /**
     * 获取拆分时间
     *
     * @param xmid
     */
    @GetMapping(value = "/queryxyxx/{xmid}")
    @ResponseBody
    public Object queryxyxx(@PathVariable(name = "xmid") String xmid, String logType) {
        String xyxx = "";
        BdcAccessLogDTO bdcAccessLogDTO = accessLogFeignService.queryBdcAccessLog(xmid, logType);
        if (bdcAccessLogDTO != null) {
            xyxx = bdcAccessLogDTO.getXyxx();
        }
        return xyxx;
    }
    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @description 查看响应报文
     */
    @ResponseBody
    @RequestMapping(value = "/queryJrbw")
    public String queryJrbw(@NotBlank(message = "业务号不能为空") String ywh, String logType){
        return accessLogFeignService.queryJrbw(ywh,logType);
    }

    /**
     * @param xmid
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 上报日志台账数据校验
     */
    @ResponseBody
    @RequestMapping(value = "/checkDataByXmid")
    public Boolean checkDataByXmid(String xmid) {
        return accessLogFeignService.checkDataByXmid(xmid);
    }


    /**
     * @param xmidList 包含xmid和type（国家上报或省级上报）
     * @param logType
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取响应报文
     */
    @ResponseBody
    @RequestMapping(value = "/getAccessResponse")
    public String getAccessResponse(@RequestParam(name = "xmidList") List<String> xmidList,
                                    @RequestParam(name = "logType") String logType) {
        LOGGER.warn("接入日志台账获取响应报文{}", xmidList);
        return accessLogFeignService.getAccessResponse(xmidList,logType);
    }

    /**
     * @param xmidList 项目主键集合
     * @return void
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 根据项目主键集合汇交当前项目
     */
    @ResponseBody
    @GetMapping("/xmidlist")
    public void autoAccessByXmidList(@RequestParam(name = "xmidList") List<String> xmidList) {
        LOGGER.warn("接入日志台账重新上报{}", xmidList);
        nationalAccessFeignService.autoAccessByXmidList(xmidList);
    }

    /**
     * @param sbxzVOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 上报
     * @date : 2022/6/21 14:31
     */
    @ResponseBody
    @PostMapping("/sb")
    void accessByXmid(@RequestBody List<SbxzVO> sbxzVOList) {
        if (CollectionUtils.isNotEmpty(sbxzVOList)) {
            //存入一个redis数据表示正在进行汇交任务，存在时间2小时
            redisUtils.addStringValue("SBXZPLSB", "SBXZPLSB", 2 * 3600);
            try {
                nationalAccessFeignService.autoAccessBySbxz(sbxzVOList);
            } catch (Exception e) {
                LOGGER.error("批量上报销账存在异常", e);
                throw new AppException("批量上报销账存在异常" + e.getMessage());
            } finally {
                //任务执行完删除redis数据
                redisUtils.deleteKey("SBXZPLSB");
            }
        }
    }

    /**
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 获取上报销账配置值
     */
    @ResponseBody
    @PostMapping("/pz")
    public int getPz() {
        String pz = accessLogFeignService.getPz();
        String cron = pz.replace("\"","");
        if (StringUtils.isNotBlank(cron)){
            CronExpression cronExpression = new CronExpression(cron);
            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDateTime date = cronExpression.nextLocalDateTimeAfter(dateTime);
            if (localDateTime.isBefore(date)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * @param sbxzDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 例外上报
     * @date : 2022/10/5 16:33
     */

    @ResponseBody
    @PostMapping("/lwsb")
    Object lwsb(@RequestBody SbxzDTO sbxzDTO) {
        if (Objects.nonNull(sbxzDTO) && CollectionUtils.isNotEmpty(sbxzDTO.getXmidList()) && CollectionUtils.isNotEmpty(sbxzDTO.getIdList())) {
            return accessLogFeignService.lwsb(sbxzDTO);
        }
        return 0;
    }

    @ResponseBody
    @GetMapping("/redisVal")
    Boolean queryRedisVal(String redisKey) {
        if (StringUtils.isNotBlank(redisKey)) {
            return StringUtils.isNotBlank(redisUtils.getStringValue(redisKey));
        }
        return false;
    }


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 手动更新销账状态
     * @date : 2022/11/25 8:36
     */
    @ResponseBody
    @GetMapping("/gxXzzt")
    public void updateXzzt() {
        nationalAccessFeignService.updateXzzt();
    }

}
