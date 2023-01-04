package cn.gtmap.realestate.exchange.web;


import cn.gtmap.realestate.exchange.core.annotations.LayuiPageable;
import cn.gtmap.realestate.exchange.core.dto.RegisterLogDTO;
import cn.gtmap.realestate.exchange.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.ex.ErrorCode;
import cn.gtmap.realestate.exchange.core.qo.BdcDbrzQO;
import cn.gtmap.realestate.exchange.core.qo.RegisterLogQO;
import cn.gtmap.realestate.exchange.core.service.RegisterLogService;
import cn.gtmap.realestate.exchange.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogService;
import cn.gtmap.realestate.exchange.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.Objects;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0
 * @description 登簿日志上报日志台账
 */
@Controller
@RequestMapping("/registerLog")
public class RegisterLogController extends BaseController {

    @Autowired
    RegisterLogService registerLogService;

    @Autowired
    AccessLogService accessLogService;
    @Autowired
    Repo repo;

    /**
     * @param pageable 分页
     * @return
     * @description 页面展示
     */
    @ResponseBody
    @RequestMapping(value = "/getRegisterLogPagesJson")
    public Object getAccessJson(@LayuiPageable Pageable pageable, RegisterLogQO registerLogQO) {
        if (Objects.isNull(registerLogQO)) {
           throw new AppException(ErrorCode.MISSING_ARG, "缺少查询参数");
       }
       if(StringUtils.isNotBlank(registerLogQO.getType()) && StringUtils.equals("export", registerLogQO.getType())){
           return this.registerLogService.listRegisterLog(registerLogQO);
       }
       Page<RegisterLogDTO> pagelist =  this.registerLogService.listRegisterLogByPages(pageable, registerLogQO);
       return super.addLayUiCode(pagelist);
    }

    /**
     * 查看登簿日志明细
     */
    @ResponseBody
    @GetMapping(value = "/dbrz/mx")
    public Object dbrzMx(String id) {
        if (StringUtils.isBlank(id)){
            throw new AppException("缺失参数！");
        }
        return accessLogService.dbrzMx(id);
    }

    /**
     * 上报登簿日志
     */
    @GetMapping(value = "/dbrz")
    @ResponseStatus(HttpStatus.OK)
    public void accessLog(String date, String qxdm) {
        LOGGER.info("登簿日志上报手动触发开始了！");
        if (StringUtils.isAnyBlank(date, qxdm)) {
            throw new AppException("缺失参数！");
        }
        Date accessDate = new Date();
        if (StringUtils.isNotBlank(date)) {
            Date paramDate = DateUtils.formatDate(date);
            accessDate = DateUtils.dealDate(paramDate, "23:59:59");
        }
        LOGGER.info("登簿日志上报手动触发开始调用方法！");

        accessLogService.accessLog(accessDate, qxdm);
    }

    /**
     * @param pageable
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取登簿日志详情信息
     * @date : 2022/7/27 16:01
     */
    @ResponseBody
    @GetMapping("/dbrzxq/page")
    public Object listSbxzByPage(@LayuiPageable Pageable pageable, BdcDbrzQO bdcDbrzQO) {
        return repo.selectPaging("listDbrzxqByPage", bdcDbrzQO, pageable);
    }
}
