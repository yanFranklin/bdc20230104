package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.BdcGzyzTsxxUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/03/10
 * @description 规则验证
 */
@RestController
@RequestMapping("/rest/v1.0/gzyz")
public class BdcGzYzController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzYzController.class);

    @Autowired
    BdcGzZhGzFeignService bdcGzZhGzFeignService;

    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @param bdcGzYzQO 规则验证查询实体
     * @return 验证结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 其他规则验证（非流程）
     */
    @ResponseBody
    @PostMapping("/qtyz")
    public List<BdcGzyzVO> qtyz(@RequestBody BdcGzYzQO bdcGzYzQO) {
        List<BdcGzyzVO> resultList = Lists.newArrayList();
        BdcGzZhgzTsxxDTO bdcGzZhgzTsxxDTO = null;
        try {
            bdcGzZhgzTsxxDTO = bdcGzZhGzFeignService.getZhgzYzTsxx(bdcGzYzQO);
        } catch (Exception e) {
            //获取规则的时候会抛出异常，当code为  时表示未配置验证项直接返回空集合
            GtFeignException gte = null;
            if (e.getCause() instanceof GtFeignException) {
                gte = (GtFeignException) e.getCause();
                if (gte != null) {
                    String responseBody = gte.getMsgBody();
                    Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
                    if (MapUtils.getInteger(bodyMap, "code") != null && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                        Integer errorCode = MapUtils.getInteger(bodyMap, "code");
                        if (errorCode == 101) {
                            return Collections.emptyList();
                        } else {
                            LOGGER.error("规则验证异常！",e);
                            throw new AppException(e.getMessage());
                        }
                    }
                }
            } else {
                LOGGER.error("规则验证异常！",e);
                throw new AppException(e.getMessage());
            }
        }
        if (bdcGzZhgzTsxxDTO != null && CollectionUtils.isNotEmpty(bdcGzZhgzTsxxDTO.getZgzTsxxDTOList())) {
            resultList = BdcGzyzTsxxUtils.checkZgzTsxx(bdcGzZhgzTsxxDTO.getZgzTsxxDTOList());
        }
        return resultList;
    }

    @ResponseBody
    @PostMapping("/addIgnoreLog")
    public void addIgnoreLog(@RequestBody String data) {
        UserDto userDto = userManagerUtils.getCurrentUser();
        Map<String, Object> map = new HashMap<>();
        map.put(CommonConstantUtils.VIEW_TYPE, CommonConstantUtils.CONFIG_UI);
        map.put(CommonConstantUtils.VIEW_TYPE_NAME, CommonConstantUtils.HLLX);
        map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
        map.put(CommonConstantUtils.HLNR, data);
        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), CommonConstantUtils.GZYZ_HL, map);
        zipkinAuditEventRepository.add(auditEvent);
    }

}
