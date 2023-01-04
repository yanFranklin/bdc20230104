package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.service.GjZdService;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import cn.gtmap.realestate.common.core.service.rest.building.FwLjzRestService;
import cn.gtmap.realestate.common.util.CheckEntityUtils;
import cn.gtmap.realestate.common.util.LogConstans;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/1
 * @description 逻辑幢信息控制类
 */
@RestController
@Api(tags = "逻辑幢服务接口")
public class FwLjzRestController extends BaseController implements FwLjzRestService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 房屋逻辑幢service
     */
    @Autowired
    private FwLjzService fwLjzService;

    @Autowired
    private GjZdService gjZdService;

    /**
     * @param pageable
     * @param paramJson
     * @return java.lang.Object
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 逻辑幢分页查询方法
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询逻辑幢信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @LogNote(value = "选择逻辑撞", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    public Page<Map> listLjzByPageJson(Pageable pageable,
                                       @RequestParam(name = "paramJson", required = false) String paramJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson, Map.class);
        }
        return fwLjzService.listLjzByPage(pageable, map);
    }

    /**
     * @param pageable
     * @param paramJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询预测逻辑幢
     * @date : 2020/12/28 17:05
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询逻辑幢信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listYcLjzByPageJson(Pageable pageable, String paramJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson, Map.class);
        }
        return fwLjzService.listYcLjzByPage(pageable, map);
    }

    /**
     * @param fwDcbIndex
     * @return java.lang.Integer
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除逻辑幢信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据主键删除逻辑幢信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public Integer deleteLjzByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.deleteLjzByFwDcbIndex(fwDcbIndex,true);
    }

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.LjzResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键查询逻辑幢
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键查询逻辑幢")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "主键id", required = true, dataType = "String", paramType = "path")})
    public FwLjzDO queryLjzByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
    }

    /**
     * @param fwLjzDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 新增逻辑幢
     */
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "新增逻辑幢信息")
    public FwLjzDO insertFwLjz(@RequestBody FwLjzDO fwLjzDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkFk(fwLjzDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        } else {
            return fwLjzService.insertLjz(fwLjzDO);
        }
        return null;
    }

    /**
     * @param fwLjzDO
     * @param updateNull true表示空字段更新，false，表示空字段不更新
     * @return java.lang.Integer
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 修改逻辑幢
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "修改逻辑幢信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "updateNull", value = "空字段不更新", dataType = "boolean", paramType = "query")})
    public Integer updateFwLjz(@RequestBody FwLjzDO fwLjzDO, @RequestParam(name = "updateNull", required = false) boolean updateNull,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (!CheckEntityUtils.checkPkAndFk(fwLjzDO)) {
            errorException(ErrorCodeConstants.PARAM_ERROR);
        }
        return fwLjzService.updateFwLjzWithChangeBdcdyh(fwLjzDO, updateNull);
    }

    /**
     * 根据BDCDYH 查询逻辑幢基本信息
     *
     * @param bdcdyh bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public FwLjzDO queryLjzByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.queryLjzByBdcdyh(bdcdyh);
    }


    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询房逻辑幢流程状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询房逻辑幢流程状态")
    public List<String> queryLjzLcztByBdcdyh(@RequestBody List<String> bdcdyhList) {
        return fwLjzService.queryLjzLcztByBdcdyh(bdcdyhList);
    }

    /**
     * @param djh
     * @param zrzh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据宗地和自然幢号查询逻辑幢列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据宗地和自然幢号查询逻辑幢列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zrzh", value = "自然幢号", dataType = "String", paramType = "query")})
    public List<FwLjzDO> listLjzByDjhAndZrzh(@PathVariable("djh") String djh, @RequestParam(name = "zrzh", required = false) String zrzh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.listLjzByDjhAndZrzh(djh, zrzh);
    }

    /**
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 逻辑幢挂接宗地
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "逻辑幢挂接宗地")
    public void fwljzRelevanceZd(@RequestBody FwLjzDO fwLjzDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        gjZdService.ljzGjzd(fwLjzDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成逻辑幢号")
    public String creatLjzh(@RequestParam(name = "lszd", required = false) String lszd,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.creatLjzh(lszd,0);
    }

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 取消挂接宗地
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "逻辑幢取消挂接宗地")
    public void fwljzQxgjzd(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        gjZdService.ljzQxgjzd(fwDcbIndex);
    }

    /**
     * @param fwLjzDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢实体查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据逻辑幢实体查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwLjzDO(@RequestBody FwLjzDO fwLjzDO,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.listValidBdcdyhByFwLjzDO(fwLjzDO);
    }

    /**
     * @param fwDcbIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢主键集合查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据逻辑幢主键集合查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwLjzIndexList(@RequestBody List<String> fwDcbIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.listValidBdcdyhByFwLjzIndexList(fwDcbIndexList);
    }

    /**
     * @param fwDcbIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwDcbIndex查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据fwDcbIndex查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.listValidBdcdyhByFwDcbIndex(fwDcbIndex);
    }

    /**
     * @param fwlxBgRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋类型变更dto查询有效的不动产单元号（因为逻辑幢变更所以写这里）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据房屋类型变更dto查询有效的不动产单元号")
    public List<String> listValidBdcdyhByFwlxBgRequestDTO(@RequestBody FwlxBgRequestDTO fwlxBgRequestDTO) {
        return fwLjzService.listValidBdcdyhByFwlxBgRequestDTO(fwlxBgRequestDTO);
    }
    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据json查询有效的不动产单元号")
    public List<String> listValidBdcdyhByJson(@RequestBody String jsonData,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return fwLjzService.listValidBdcdyhByJson(jsonData);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [slbh] 受理编号
     * @return: String 匹配过后的受理编号
     * @description 当受理编号为CH开头时，通过<code>fw_ljz</code>和<code>s_sj_bgsh</code>关联查询，采用<code>s_sj_bgsh</code>的slbh
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "当受理编号为CH开头时，通过fw_ljz和s_sj_bgsh关联查询，采用s_sj_bgsh的slbh")
    public String fwljzLinkBgsh(@PathVariable("slbh") String slbh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if(StringUtils.isBlank(slbh)){
            throw new NullPointerException("未获取到受理编号信息。");
        }
        return fwLjzService.queryfwljzLinkBgsh(slbh);
    }


}