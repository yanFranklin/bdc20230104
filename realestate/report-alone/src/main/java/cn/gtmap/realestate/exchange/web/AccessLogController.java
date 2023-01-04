package cn.gtmap.realestate.exchange.web;

import cn.gtmap.realestate.exchange.core.annotations.LayuiPageable;
import cn.gtmap.realestate.exchange.core.qo.JgWxzBwxxQO;
import cn.gtmap.realestate.exchange.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.exchange.service.AccessModelHandlerService;
import cn.gtmap.realestate.exchange.service.national.access.AccessDefaultValueService;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import cn.gtmap.realestate.exchange.service.national.access.CheckAccessDataService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0
 * @description 上报日志台账
 */
@Controller
@RequestMapping("/accessLog")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;
    @Autowired
    private CheckAccessDataService checkAccessDataService;

    @Autowired
    private AccessDefaultValueService accessDefaultValueService;

    @Autowired
    Repo repo;

    @Autowired
    AccessModelHandlerService accessModelHandlerService;

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
                                String qxdm, String sbzt, String kssj, String jssj) {
        HashMap map = new HashMap();
        if (StringUtils.isNotBlank(sbzt)) {
            map.put("sbzt", sbzt);
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
        return accessLogService.listAccessLogByPages(pageable, map);
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
    public boolean refreshDefaultTableList() {
        accessDefaultValueService.refreshbdcExchangeDefaultValueDOList();
        return true;
    }

    /**
     * @param jgWxzBwxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上报销账台账
     * @date : 2022/7/25 14:44
     */
    @ResponseBody
    @GetMapping("/sbxz/page")
    public Object listSbxzByPage(@LayuiPageable Pageable pageable, JgWxzBwxxQO jgWxzBwxxQO) {
        return repo.selectPaging("listWxzBwxxByPage", jgWxzBwxxQO, pageable);
    }


    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid 上报
     * @date : 2022/6/21 14:31
     */
    @ResponseBody
    @PostMapping("/sb")
    void accessByXmid(@RequestBody List<String> xmidList) {
        if (CollectionUtils.isNotEmpty(xmidList)) {
            accessModelHandlerService.autoAccessByXmidList(xmidList);
            //上报后更新xzzt为 2
            accessLogService.updateXzzt(xmidList, "2");
        }
    }

    /*
     * 上报时间线
     * */
    @GetMapping("/sbsjx")
    @ResponseBody
    public Object querySbsjx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            return accessLogService.queryJrCzrz(xmid);
        }
        return "";
    }
}
