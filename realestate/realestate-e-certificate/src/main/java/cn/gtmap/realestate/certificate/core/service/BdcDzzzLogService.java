package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzLogDO;
import org.aspectj.lang.JoinPoint;
import org.springframework.dao.DataAccessException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019/1/22
 * @description 电子证照日志接口
 */
public interface BdcDzzzLogService {

    /**
     *
     * @param bdcDzzzLogDO
     * @description 插入日志
     */
    void insertBdcDzzzLog(BdcDzzzLogDO bdcDzzzLogDO);

    /**
     * @param bdcDzzzLogDO
     * @description 插入日志
     */
    void insertAsyncBdcDzzzLog(BdcDzzzLogDO bdcDzzzLogDO);

    void insertBdcDzzzLog(JoinPoint joinPoint, String url, String yymc, Object obj);

    /**
     *
     * @param bdcDzzzLogDO
     * @description 插入ES日志
     */
    void insertBdcDzzzEsLog(BdcDzzzLogDO bdcDzzzLogDO);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param paramJson
     * @param url
     * @param yymc
     * @return
     * @description 组织请求日志信息
     */
    BdcDzzzLogDO getRequestData(String paramJson, String url, String yymc);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param request
     * @param flag
     * @param joinPoint
     * @return
     * @description 保存日志
     */
    //BdcDzzzLogDO getRequestData(HttpServletRequest request, String flag, JoinPoint joinPoint);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param request
     * @param flag
     * @param paramJson
     * @return
     * @description 保存日志
     */
    //BdcDzzzLogDO getRequestData(HttpServletRequest request, String flag, String paramJson);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param flag
     * @param args
     * @return
     * @description 组织响应日志信息
     */
    //void insertResponseData(String paramJson, String flag, String args);

    /**
     *
     * @param bdcDzzzLogDO
     * @param args
     * @description 获取返回日志信息
     */
    void getResponseData(BdcDzzzLogDO bdcDzzzLogDO, String args);
}
