package cn.gtmap.realestate.exchange.web.main;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.vo.WsxNationalAccessVO;
import cn.gtmap.realestate.exchange.service.national.WsxNationalAccessService;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogTypeService;
import cn.gtmap.realestate.exchange.service.national.access.CheckAccessDataService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0
 * @description 上报日志台账
 */
@Controller
@RequestMapping("/realestate-exchange/accessLog")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;
    @Autowired
    private CheckAccessDataService checkAccessDataService;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private WsxNationalAccessService wsxNationalAccessService;

    @Autowired
    private AccessDefaultValueService accessDefaultValueService;

    @Autowired
    private AccessLogTypeService accessLogTypeService;

    /**
     * @param pageable 分页
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 页面展示
     */
    @ResponseBody
    @RequestMapping(value = "/getAccessLogPagesJson")
    public Object getAccessJson(@LayuiPageable Pageable pageable, String slbh,
                                String bdcdyh, String zl, String bdcqzh, String xyzt,
                                String qxdm, String sbzt, String kssj, String jssj, String xybm, String type, Boolean wlxm) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(sbzt)) {
            String[] s = sbzt.split(",");
            map.put("sbzt", s);
        }
        if (StringUtils.isNotBlank(slbh)) {
            map.put("slbh", slbh);
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            map.put("bdcdyh", bdcdyh);
        }
        if (StringUtils.isNotBlank(zl)) {
            map.put("zl", zl);
        }
        if (StringUtils.isNotBlank(bdcqzh)) {
            map.put("bdcqzh", bdcqzh);
        }
        if (StringUtils.isNotBlank(qxdm)) {
            map.put("qxdm", qxdm);
        }
        if (StringUtils.isNotBlank(xyzt)) {
            map.put("xyzt", xyzt);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(xybm)) {
            map.put("xybm", xybm);
        }
        if (StringUtils.isNotBlank(type)) {
            map.put("type", type);
        }
        if (Objects.nonNull(wlxm) && wlxm) {
            map.put("wlxm", true);
        }
        return accessLogService.listAccessLogByPages(pageable, map);
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.lang.String
     * @description 获取当前用户行政区划代码
     */
    @ResponseBody
    @RequestMapping("queryregioncode")
    public Map<String,Object> queryCurrentUserRegionCode(){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("code","admin");
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(userDto != null && userDto.getAdmin() != 1){
            String regionCode = userManagerUtils.getRegionCodeByUserName(userDto.getUsername());
            // 判断如果是合肥四个区的用户
            if(CommonUtil.isHefeiFcAccessQxdm(regionCode)){
                resultMap.put("code","hefei");
                List<Map<String,String>> qxList = accessLogService.queryAccessWsxList(Constants.HEFEI_ACCESS_TYPE);
                resultMap.put("qxList",qxList);
            }else{
                //
                resultMap.put("code",regionCode);
            }
        }
        // 判断如果是 需要显示下拉框的场景
        if(StringUtils.equals("admin", MapUtils.getString(resultMap,"code"))){
            List<Map<String,String>> qxList = accessLogService.queryAccessWsxList(Constants.HEFEI_ACCESS_TYPE);
            qxList.addAll(accessLogService.queryAccessWsxList(Constants.WSX_ACCESS_TYPE));
            resultMap.put("qxList",qxList);
        }
        return resultMap;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param ywh
     * @param bdcdyh
     * @param zl
     * @param bdcqzh
     * @param sbzt
     * @param regionCode
     * @return java.lang.Object
     * @description 分页查询外市县上报日志
     */
    @ResponseBody
    @RequestMapping("querywsxaccesslog")
    public Object queryWsxAccessLog(@LayuiPageable Pageable pageable,
                                    String ywh, String bdcdyh,
                                    String zl, String bdcqzh,
                                    String kssj,String jssj,
                                    String sbzt,@NotBlank(message = "区县代码不能为空") String regionCode,
                                    String qxdm,String xyzt){
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(sbzt)) {
            map.put("sbzt", sbzt);
        }
        if (StringUtils.isNotBlank(ywh)) {
            map.put("ywh", ywh);
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            map.put("bdcdyh", bdcdyh);
        }
        if (StringUtils.isNotBlank(zl)) {
            map.put("zl", zl);
        }
        if (StringUtils.isNotBlank(bdcqzh)) {
            map.put("bdcqzh", bdcqzh);
        }
        if (StringUtils.isNotBlank(kssj)) {
            map.put("kssj", kssj);
        }
        if (StringUtils.isNotBlank(jssj)) {
            map.put("jssj", jssj);
        }
        if (StringUtils.isNotBlank(xyzt)) {
            map.put("xyzt", xyzt);
        }
        if(StringUtils.isBlank(qxdm)){
            qxdm = regionCode;
        }else{
            regionCode = qxdm;
        }
        // 区县代码过滤条件
        String[] qxdmArr = qxdm.split(",");
        map.put("qxdmArr", Arrays.asList(qxdmArr));

        // 视图条件
        if(StringUtils.isNotBlank(regionCode)
                && CommonUtil.isHefeiFcAccessQxdm(regionCode)){
            map.put("viewCode", Constants.HEFEI_ACCESS_QXDM);
        } else {
            map.put("viewCode", regionCode);
        }
        return accessLogService.listWsxAccessLogByPage(pageable,map);
    }

    /**
     * @param xmid
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 上报日志台账数据校验
     */
    @ResponseBody
    @RequestMapping(value = "/checkDataByXmid")
    public Boolean checkDataByXmid(String xmid) {
        return checkAccessDataService.checkAccessData(xmid);
    }


    @ResponseBody
    @RequestMapping("/checkWlxmData")
    public Boolean checkWlxmData(String xmid) {
        return checkAccessDataService.checkWlxmAccessData(xmid);
    }


    /**
     * @param qxdm
     * @param ywh
     * @param bdcdyh
     * @return java.lang.Boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据YWH 和 BDCDYH 检查外市县数据
     */
    @ResponseBody
    @RequestMapping(value = "/checkwsxaccessdata")
    public Boolean checkWsxAccessData(String qxdm,String ywh,String bdcdyh){
        return checkAccessDataService.checkWsxAccessData(qxdm,ywh,bdcdyh);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param accessvo
     * @return void
     * @description
     */
    @ResponseBody
    @RequestMapping(value = "/wsx/plnationalaccess")
    public void plNationalAccess(@NotBlank String accessvo){
        wsxNationalAccessService.wsxPlNationalAccess(JSONObject.parseObject(accessvo,WsxNationalAccessVO.class));
    }


    /**
     * @param xmidList 包含xmid和type（国家上报或省级上报）
     * @param logType
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取响应报文
     */
    @ResponseBody
    @RequestMapping(value = "/getAccessResponse")
    public String getAccessResponse(@RequestParam(name = "xmidList") List<String> xmidList,
                                    @RequestParam(name = "logType") String logType) {
        return accessLogService.getAccessResponse(xmidList,logType);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @description 查看响应报文
     */
    @ResponseBody
    @RequestMapping(value = "/queryXybw")
    public String queryXybw(@NotBlank(message = "业务号不能为空") String ywh, String logType){
        return accessLogService.queryXybw(ywh,logType);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @description 查看响应报文
     */
    @ResponseBody
    @RequestMapping(value = "/queryJrbw")
    public String queryJrbw(@NotBlank(message = "业务号不能为空") String ywh, String logType){
        return accessLogService.queryJrbw(ywh,logType);
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return boolean
     * @description 刷新默认值配置表
     */
    @ResponseBody
    @RequestMapping(value = "/refreshDefaultTable")
    public boolean refreshDefaultTableList(){
        accessDefaultValueService.refreshbdcExchangeDefaultValueDOList();
        return true;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param yjlx 预警类型
     * @param slbh 受理编号
     * @description 发送上报预警消息（这里主要为了测试）
     */
    @ResponseBody
    @GetMapping("/yjxx")
    public void sendMsg(@RequestParam("yjlx")String yjlx, @RequestParam("slbh")String slbh) {
        MsgNoticeDTO msgNoticeDTO = new MsgNoticeDTO();
        msgNoticeDTO.setYjlx(yjlx);
        msgNoticeDTO.setSlbh(slbh);
        accessLogTypeService.sendMsgByMsgType(msgNoticeDTO);
    }
}
