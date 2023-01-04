package cn.gtmap.realestate.portal.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.dto.accept.BdcMkPjqRequestDTO;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.IPPortUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.portal.ui.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/11/7 20:16
 * @description
 */
@RestController
@RequestMapping("/rest/v1.0/pjq")
@Api(tags = "评价器服务接口")
public class BdcPjqController extends BaseController {


    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 组织用户信息返回
     *
     * @param
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/userInfo")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("组织当前用户信息传给评价器")
    public Map<String, Object> getUser() {
        Map<String, Object> resultMap = new HashMap<>(16);
        String base64 = "";
        String userName = "";
        String placename = "";
        UserDto userDto = userManagerUtils.getCurrentUser();
        if (null == userDto) {
            throw new AppException("用户信息为空，请重试！");
        }
        if (CollectionUtils.isEmpty(userDto.getOrgRecordList())) {
            throw new AppException("用户组织信息为空，请检查！");
        }
        //合肥市不动产登记中心替换为要素大市场
        if ("合肥市不动产登记中心".equals(userDto.getOrgRecordList().get(0).getName())) {
            placename = "要素大市场";
        } else {
            placename = userDto.getOrgRecordList().get(0).getName();
        }
        //判断用户是否是党员，是+“|1”
        if ("Y".equals(userDto.getPartyMember())) {
            userName = userDto.getAlias() + "|1";
        } else {
            userName = userDto.getAlias();
        }
        //获取用户照片base64
        if (StringUtils.isNoneBlank(userDto.getPictureId())) {
            BaseResultDto baseResultDto = storageClient.downloadBase64(userDto.getPictureId());
            if (baseResultDto.getCode() == 0) {
                base64 = baseResultDto.getMsg();
                if (StringUtils.isNotBlank(base64)) {
                    String[] baseArry = base64.split(",");
                    base64 = baseArry[1];
                }
            } else {
                throw new AppException("获取用户照片失败！");
            }
        }

        resultMap.put("name", userName);
        resultMap.put("no", userDto.getJobNumber());
        resultMap.put("placename", placename);
        resultMap.put("placeno", userDto.getOrgRecordList().get(0).getRegionCode());
        resultMap.put("base64", base64);


        return resultMap;
    }

    /**
     * 调用摩科评价器接口
     * @param request  HttpServletRequest
     * @param beanName 请求接口名称
     * @param bdcMkPjqRequestDTO 不动产摩科评价器请求参数
     * @return 摩科评价器接口返回值
     */
    @PostMapping("/mk/{beanName}")
    @ResponseBody
    public Object mkpjq(HttpServletRequest request, @PathVariable(value ="beanName")String beanName, @RequestBody BdcMkPjqRequestDTO bdcMkPjqRequestDTO){
        if(StringUtils.isBlank(beanName)) {
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到评价器请求参数信息");
        }
        bdcMkPjqRequestDTO.setSysIp(IPPortUtils.getClientIp(request));
        return exchangeInterfaceFeignService.requestInterface(beanName, bdcMkPjqRequestDTO);
    }

}
