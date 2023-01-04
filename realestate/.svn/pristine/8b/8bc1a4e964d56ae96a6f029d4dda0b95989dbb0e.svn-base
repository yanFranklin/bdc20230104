package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.AcceptBdcdyService;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlBdcdyhQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.building.AcceptLsBdcdyRestService;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.common.util.PageUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 为受理子系统提供
 * 根据BDCLX分页查询BDCDY服务
 */
@RestController
@Api(tags = "为受理子系统提供根据BDCLX分页查询BDCDY服务")
public class AcceptLsBdcdyRestController implements AcceptLsBdcdyRestService {

    @Autowired
    private AcceptBdcdyService acceptBdcdyService;

    @Autowired
    BdcSdFeignService bdcSdFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;


    @Override
    @LogNote(value = "选择不动产单元号", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页或列表查询不动产单元信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramJson", value = "查询参数", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loadpage", value = "是否分页", dataType = "String", paramType = "query")})
    public Object listLsBdcdyhByPageOrList(Pageable pageable,
                                           @RequestParam(name = "paramJson", required = false) String paramJson,
                                           @RequestParam(name = "loadpage", required = false) Boolean loadpage) {
        BdcSlBdcdyhQO bdcSlBdcdyhQO = new BdcSlBdcdyhQO();
        if (StringUtils.isNotBlank(paramJson)) {
            bdcSlBdcdyhQO = JSONObject.parseObject(paramJson, BdcSlBdcdyhQO.class);
        }
        if (bdcSlBdcdyhQO.getDyhcxlx() != null) {
            switch (bdcSlBdcdyhQO.getDyhcxlx()) {
                /**
                 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
                 * @description 分页查询土地及其定着物类型不动产单元信息
                 */
                case 1:
                    Page<Map> maps = listTdAndDzwBdcdyByPage(pageable, bdcSlBdcdyhQO.getQlxzAndZdtzm(), bdcSlBdcdyhQO.getZdtzm(), bdcSlBdcdyhQO.getDzwtzm(), bdcSlBdcdyhQO.getBdcdyfwlx(), JSON.toJSONString(bdcSlBdcdyhQO));
                    return PageUtils.addLayUiCodeWithQjgldm(maps, bdcSlBdcdyhQO.getQjgldm());
                default:
                    break;
            }
        }
        return null;
    }


    /**
     * 分页查询土地及定着物类型不动产单元信息
     *
     * @param pageable
     * @param qlxzAndZdtzm
     * @param zdtzm
     * @param dzwtzm
     * @param fwlx
     * @param paramJson
     * @return
     */
    public Page<Map> listTdAndDzwBdcdyByPage(Pageable pageable,
                                             String qlxzAndZdtzm,
                                             String zdtzm,
                                             String dzwtzm,
                                             String fwlx,
                                             String paramJson) {
        Map<String, Object> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson, Map.class);
        }
        return acceptBdcdyService.listLsTdAndDzwBdcdyByPage(pageable, StringUtils.upperCase(qlxzAndZdtzm),
                StringUtils.upperCase(zdtzm), StringUtils.upperCase(dzwtzm), StringUtils.lowerCase(fwlx), paramMap);
    }
}
