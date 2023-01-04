package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzLogMapper;
import cn.gtmap.realestate.certificate.core.model.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzLogDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.sign.DzzzSignRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzJestClientService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzLogService;
import cn.gtmap.realestate.certificate.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019/1/22
 * @description 日志实现类
 */
@Service
public class BdcDzzzLogServiceImpl implements BdcDzzzLogService {

    private final Logger logger = LoggerFactory.getLogger(BdcDzzzLogServiceImpl.class);
    @Resource
    private BdcDzzzLogMapper bdcDzzzLogMapper;
    @Resource
    private BdcDzzzJestClientService bdcDzzzJestClientService;

    @Override
    public void insertBdcDzzzLog(BdcDzzzLogDO bdcDzzzLogDO) {
        //BdcDzzzLogDO bdcDzzzLogDO1 = (BdcDzzzLogDO) PublicUtil.shallowCloneObject(bdcDzzzLogDO);
        bdcDzzzLogDO.setParamJson(bdcDzzzLogDO.getParamJson().length() > 3000 ? bdcDzzzLogDO.getParamJson().substring(0, 3000) : bdcDzzzLogDO.getParamJson());
        bdcDzzzLogDO.setMessage(bdcDzzzLogDO.getReason());
        bdcDzzzLogDO.setReason("");
        bdcDzzzLogMapper.insertBdcDzzzLog(bdcDzzzLogDO);
        //insertBdcDzzzEsLog(bdcDzzzLogDO);
    }

    @Override
    public void insertAsyncBdcDzzzLog(BdcDzzzLogDO bdcDzzzLogDO) {
        BdcDzzzLogDO bdcDzzzLogDO1 = (BdcDzzzLogDO) PublicUtil.shallowCloneObject(bdcDzzzLogDO);
        bdcDzzzLogDO1.setParamJson(StringUtils.isNotEmpty(bdcDzzzLogDO1.getParamJson()) && bdcDzzzLogDO1.getParamJson().length() > 3000 ? bdcDzzzLogDO1.getParamJson().substring(0, 3000) : bdcDzzzLogDO1.getParamJson());
        bdcDzzzLogDO1.setMessage(StringUtils.isNotEmpty(bdcDzzzLogDO1.getReason()) && bdcDzzzLogDO1.getReason().length() > 3000 ? bdcDzzzLogDO1.getReason().substring(0, 3000) : bdcDzzzLogDO1.getReason());
        bdcDzzzLogDO1.setReason("");
        bdcDzzzLogMapper.insertBdcDzzzLog(bdcDzzzLogDO1);

        insertBdcDzzzEsLog(bdcDzzzLogDO);
    }

    @Override
    @Async
    public void insertBdcDzzzLog(JoinPoint joinPoint, String url, String yymc, Object obj) {
        BdcDzzzLogDO bdcDzzzLogDO = new BdcDzzzLogDO();
        int paramJsonLength = joinPoint.getArgs().length;
        String paramJson = null;
        if (paramJsonLength > 0) {
            paramJson = joinPoint.getArgs()[paramJsonLength - 1].toString();

            paramJson = handleParamJson(paramJson);
        }
        bdcDzzzLogDO.setLogId(StringUtil.getUUID32());
        bdcDzzzLogDO.setCzrq(DateUtil.now());
        bdcDzzzLogDO.setController(url);
        //bdcDzzzLogDO.setStatus(0);
        bdcDzzzLogDO.setUserName(yymc);
        bdcDzzzLogDO.setParamJson(paramJson);
        initRequestData(bdcDzzzLogDO, paramJson, url);
        //getClientIpInfo(request, bdcDzzzLogDO);
        //return bdcDzzzLogDO;
        getResponseData(bdcDzzzLogDO, JSON.toJSONString(obj));
        insertBdcDzzzLog(bdcDzzzLogDO);
    }

    @Override
    public void insertBdcDzzzEsLog(BdcDzzzLogDO bdcDzzzLogDO) {
        bdcDzzzJestClientService.insert("logindex", "log", bdcDzzzLogDO);
    }

    private String handleParamJson(String paramJson){
        //签发接口记录日志去除sign
        try {
            if (StringUtils.isNotEmpty(paramJson) && paramJson.contains("\"sign\"")) {
                //包含
                DzzzSignRequestModel dzzzRequestModel = JSON.parseObject(paramJson, DzzzSignRequestModel.class);
                paramJson = JSON.toJSONString(dzzzRequestModel.getData());
            }
        } catch (Exception e) {
            logger.error("BdcDzzzLogServiceImpl.getRequestData 截取sign失败", e);
        }
        return paramJson;
    }

    @Override
    public BdcDzzzLogDO getRequestData(String paramJson, String url, String yymc) {
        BdcDzzzLogDO bdcDzzzLogDO = new BdcDzzzLogDO();
        bdcDzzzLogDO.setLogId(StringUtil.getUUID32());
        bdcDzzzLogDO.setCzrq(DateUtil.now());
        bdcDzzzLogDO.setController(url);
        //bdcDzzzLogDO.setStatus(0);
        bdcDzzzLogDO.setUserName(yymc);
        bdcDzzzLogDO.setParamJson(handleParamJson(paramJson));
        initRequestData(bdcDzzzLogDO, paramJson, url);
        //getClientIpInfo(request, bdcDzzzLogDO, ip);
        return bdcDzzzLogDO;
    }

    /*private String getRequestParamJson(HttpServletRequest request, String method, String args) {
        StringBuilder requestLog = new StringBuilder();
        requestLog.append("请求方式:").append(method);
        if (StringUtils.isNotBlank(args)) {
            requestLog.append(",").append("请求参数:").append(args);
        }
        String token = request.getParameter(Constants.REQUEST_PARAM_TOKEN);
        if (StringUtils.isNotBlank(token)) {
            requestLog.append(",").append("请求token:").append(token);
        }
        return requestLog.toString();
    }*/


    /*@Override
    public BdcDzzzLogDO getRequestData(HttpServletRequest request, String flag, JoinPoint joinPoint) {
        int paramJsonLength = joinPoint.getArgs().length;
        String paramJson = null;
        if (paramJsonLength > 0) {
            paramJson = joinPoint.getArgs()[paramJsonLength - 1].toString();
        }
        return getRequestData(request, flag, paramJson);
    }*/

    /*@Override
    public BdcDzzzLogDO getRequestData(HttpServletRequest request, String flag, String paramJson) {
        return getRequestData(request, flag, request.getMethod(), paramJson, request.getRequestURL().toString(), ClientInfoUtil.getIpAddr(request));
    }*/

    /*private void getClientIpInfo(HttpServletRequest request, BdcDzzzLogDO bdcDzzzLogDO, String ip) {
        //String ip = ClientInfoUtil.getIpAddr(request);
        bdcDzzzLogDO.setIp(ip);
        if (BdcDzzzPdfUtil.SAVE_LOG_MAC) {
            bdcDzzzLogDO.setComputerName(ClientInfoUtil.getComputerName(ip));
            bdcDzzzLogDO.setMac(ClientInfoUtil.getMACAddress(ip));
        }
    }*/

    private void initRequestData(BdcDzzzLogDO bdcDzzzLogDO, String paramJson, String controller) {
        if (StringUtils.isNotBlank(paramJson) && StringUtils.isNotBlank(controller)) {
            try {
                JSONObject param = JSON.parseObject(paramJson);
                if (null != param) {
                    JSONObject data = param.getJSONObject("data");
                    if (null != data) {
                        initRequestData(bdcDzzzLogDO, data);
                    } else {
                        initRequestData(bdcDzzzLogDO, param);
                    }
                }
            } catch (Exception e) {
                logger.error("BdcDzzzLogServiceImpl:initRequestData");
            }
        }
    }

    private void initRequestData(BdcDzzzLogDO bdcDzzzLogDO, JSONObject data) {
        if (data != null) {
            String ywh = data.getString("ywh");
            String bdcqzh = data.getString("bdcqzh");
            String zzbs = data.getString("zzbs");
            String dwdm = data.getString("dwdm");
            ywh = StringUtils.isNotBlank(ywh) ? ywh : data.getString("YWH");
            bdcqzh = StringUtils.isNotBlank(bdcqzh) ? bdcqzh : data.getString("BDCQZH");
            zzbs = StringUtils.isNotBlank(zzbs) ? zzbs : data.getString("ZZBS");
            dwdm = StringUtils.isNotBlank(dwdm) ? dwdm : data.getString("DWDM");
            if (StringUtils.isNotBlank(ywh)) {
                bdcDzzzLogDO.setYwh(ywh);
            }
            if (StringUtils.isNotBlank(bdcqzh)) {
                bdcDzzzLogDO.setBdcqzh(bdcqzh);
            }
            if (StringUtils.isNotBlank(zzbs)) {
                bdcDzzzLogDO.setZzbs(zzbs);
            }
            if (StringUtils.isBlank(bdcDzzzLogDO.getUserName())) {
                bdcDzzzLogDO.setUserName(data.getString("yymc"));
            }
            if (StringUtils.isNotBlank(dwdm)) {
                bdcDzzzLogDO.setDwdm(dwdm);
            }
        }
    }

    /*@Override
    public void insertResponseData(String paramJson, String flag, String args) {
        BdcDzzzLogDO bdcDzzzLogDO = bdcDzzzLogMapper.queryBdcDzzzLogByLogid(flag);
        if (bdcDzzzLogDO == null) {
            bdcDzzzLogDO = new BdcDzzzLogDO();
            bdcDzzzLogDO.setLogId(flag);
        }
        bdcDzzzLogDO.setParamJson(paramJson);
        if (StringUtils.isNotBlank(args)) {
            DzzzResponseModel responseModel = JSON.parseObject(args, DzzzResponseModel.class);
            try {
                if (null != responseModel) {
                    if (StringUtils.equals(ResponseEnum.FALSE.getCode(), responseModel.getHead().getStatus())) {
                        bdcDzzzLogDO.setStatus(1);
                    }
                    if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), responseModel.getHead().getStatus())) {
                        //文件验证返回结果判断
                        JSONObject responseData = (JSONObject) responseModel.getData();
                        if (null != responseData && StringUtils.isNotBlank(responseData.getString("result")) && !StringUtils.equals("0", responseData.getString("result"))) {
                            bdcDzzzLogDO.setStatus(1);
                        }
                        //文件下载不保存PDFbase64数据
                        if (null != responseData && StringUtils.isNotBlank(responseData.getString("content"))) {
                            responseData.put("content", "");
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("BdcDzzzLogServiceImpl:getResponseData");
            }
            bdcDzzzLogDO.setMessage(responseModel.getHead().getMessage());
            bdcDzzzLogMapper.updateBdcDzzzLogByLogid(bdcDzzzLogDO);

            //bdcDzzzLogDO.setMessage(JSON.toJSONString(responseModel));
            //insertBdcDzzzEsLog(bdcDzzzLogDO);
        }
    }*/

    @Override
    public void getResponseData(BdcDzzzLogDO bdcDzzzLogDO, String args) {
        Integer status = 1;
        if (null != bdcDzzzLogDO && StringUtils.isNotBlank(args)) {
            DzzzResponseModel responseModel = JSON.parseObject(args, DzzzResponseModel.class);
            try {
                if (null != responseModel) {
                    if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), responseModel.getHead().getStatus())) {
                        status = 0;
                    }
                    if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), responseModel.getHead().getStatus())) {
                        //文件验证返回结果判断
                        JSONObject responseData = (JSONObject) responseModel.getData();
                        if (null != responseData && StringUtils.isNotBlank(responseData.getString("result")) && StringUtils.equals("0", responseData.getString("result"))) {
                            status = 0;
                        }
                        //文件下载不保存PDFbase64数据
                        if (null != responseData && StringUtils.isNotBlank(responseData.getString("content"))) {
                            responseData.put("content", "");
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("BdcDzzzLogServiceImpl:getResponseData");
            }
            bdcDzzzLogDO.setStatus(status);
            bdcDzzzLogDO.setReason(StringUtils.isNotEmpty(responseModel.getHead().getMessage()) && responseModel.getHead().getMessage().length() > 3000 ? responseModel.getHead().getMessage().substring(0, 3000) : responseModel.getHead().getMessage());
            bdcDzzzLogDO.setMessage(JSON.toJSONString(responseModel));
        }
    }
}
