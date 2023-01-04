package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzWcqlsgxDTO;
import cn.gtmap.realestate.common.core.dto.init.DyhGzXmDTO;
import cn.gtmap.realestate.common.core.qo.init.DyhGzQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcDyhGzRestService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.LogActionConstans;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.init.core.service.BdcDyhGzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/9/21
 * @description 单元号更正服务
 */
@RestController
@Api(tags = "不动产权利人信息服务接口")
public class BdcDyhGzRestController implements BdcDyhGzRestService {

    @Autowired
    BdcDyhGzService bdcDyhGzService;

    @Override
    @ApiOperation(value = "批量更新不动产单元号")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "批量更新不动产单元号", action = LogActionConstans.OTHER)
    public void updateBdcdyhPl(@RequestBody DyhGzQO dyhGzQO){
        bdcDyhGzService.updateBdcdyhPl(dyhGzQO);

    }

    @Override
    @ApiOperation(value = "根据项目ID获取所关联的所有项目数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "根据项目ID获取所关联的所有项目数据", action = LogActionConstans.OTHER)
    public DyhGzXmDTO queryDyhGzXmDTO(@RequestParam("xmid") String xmid,@RequestParam("xmtype") String xmtype){
        return bdcDyhGzService.queryDyhGzXmDTO(xmid,xmtype);

    }

    @Override
    @ApiOperation(value = "验证选择的数据是否存在疑似关联数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "验证选择的数据是否存在疑似关联数据", action = LogActionConstans.OTHER)
    public List<BdcGzyzVO> yzXzxx(@RequestBody DyhGzQO dyhGzQO){
        return bdcDyhGzService.yzXzxx(dyhGzQO);

    }

    @Override
    @ApiOperation(value = "根据已选项目单元号获取无产权历史关系数据")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "根据已选项目单元号获取无产权历史关系数据", action = LogActionConstans.OTHER)
    public DyhGzWcqlsgxDTO queryDyhGzWcqlsgxDTO(@RequestBody List<BdcXmDO> bdcXmDOList){
        return bdcDyhGzService.queryDyhGzWcqlsgxDTO(bdcXmDOList);
    }

    @Override
    @ApiOperation(value = "替换外联产权对应的限制权利单元号")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "替换外联产权对应的限制权利单元号", action = LogActionConstans.OTHER, custom = LogConstans.LOG_TYPE_WLCQXZQLCL)
    public void updateWlcqXzql(@RequestBody List<DyhGzQO> dyhGzQOList,@RequestParam(value = "processInsId",required = false) String processInsId, @RequestParam(value = "currentUserName",required = false) String currentUserName){
        bdcDyhGzService.updateWlcqXzql(dyhGzQOList);

    }

}
