package cn.gtmap.realestate.building.ui.util;

import cn.gtmap.realestate.building.ui.core.vo.GnqyzVO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzsjDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-02-21
 * @description 验证拦截器
 */
@Component
public class CheckInterceptor implements HandlerInterceptor {
    @Value("${gnqyz}")
    private Integer gnqyz;

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckInterceptor.class);

    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;


    /**
     * @Description: 拦截所有请求，判断是否验证不动产单元号
     * @Param:
     * @return:
     * @Author: <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @Date:
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (GnqyzUtil.GNQYZ) {
            if (BeforeCheckUtil.getCheckResult(requestURI)) {
                BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
                bdcGzYzQO.setZhbs(requestURI);
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.putAll(request.getParameterMap());
                //根据拦截的参数获取调规则子系统取获取不动产单元号
                if (paramMap.size() != 0 && StringUtils.isBlank(MapUtils.getString(paramMap, "omit"))) {
                    bdcGzYzQO.setParamMap(paramMap);
                    BdcGzZhgzTsxxDTO bdcGzZhgzTsxxDTO;
                    try {
                        bdcGzZhgzTsxxDTO = bdcGzZhGzFeignService.getZhgzYzTsxx(bdcGzYzQO);
                    } catch (Exception e) {
                        LOGGER.error("规则子系统服务异常", e);
                        throw new AppException("规则子系统服务异常");
                    }
                    throwCheckMsg(bdcGzZhgzTsxxDTO);
                }
            }
        }
        return true;
    }

    /**
     * @Description: 将规则子系统的提示信息直接抛出
     * @Param:
     * @return:
     * @Author: <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @Date:
     */

    private void throwCheckMsg(BdcGzZhgzTsxxDTO bdcGzZhgzTsxxDTO) {
        if (bdcGzZhgzTsxxDTO != null && CollectionUtils.isNotEmpty(bdcGzZhgzTsxxDTO.getZgzTsxxDTOList())) {
            StringBuilder stringBuilder = new StringBuilder();
            int yxj = 0;
            for (BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO : bdcGzZhgzTsxxDTO.getZgzTsxxDTOList()) {
                yxj = getYxj(yxj, bdcGzZgzTsxxDTO);
                String tsxx = bdcGzZgzTsxxDTO.getTsxx().toString();
                if (StringUtils.isNotBlank(tsxx)) {
                    stringBuilder.append(tsxx);
                }
            }
            if (StringUtils.isNotBlank(stringBuilder.toString())) {
                String msg = stringBuilder.toString().replace("[", "").replace("]", "");
                if (StringUtils.isNotBlank(msg)) {
                    throw new AppException(Constants.GNQYZ_ERROR_CODE, initGnqyzVO(msg, yxj));
                }

            }
        }
    }


    /**
     * 装配页面实体
     *
     * @param msg
     * @param yxj
     * @return
     */
    private String initGnqyzVO(String msg, int yxj) {
        GnqyzVO gnqyzVO = new GnqyzVO();
        if (StringUtils.isNotBlank(msg) && yxj != 0) {
            gnqyzVO.setErrorMsg(msg);
            gnqyzVO.setMethod(getWarnMethod(yxj));
        }
        return JSONObject.toJSONString(gnqyzVO);
    }

    /**
     * 获取提示方式
     *
     * @param yxj
     * @return
     */
    private String getWarnMethod(int yxj) {
        String method;
        if (yxj > gnqyz) {
            method = "confirm";
        } else {
            method = "alert";
        }
        return method;
    }

    /**
     * @Description: 获取提示信息中最大优先级
     * @Param:
     * @return:
     * @Author: <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @Date:
     */
    private Integer getYxj(int yxj, BdcGzZgzTsxxDTO bdcGzZgzTsxxDTO) {
        if (bdcGzZgzTsxxDTO.getYxj() != null) {
            if (bdcGzZgzTsxxDTO.getYxj() > yxj) {
                return bdcGzZgzTsxxDTO.getYxj();
            } else {
                return yxj;
            }
        }
        return 0;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {
    }

}
