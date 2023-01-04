package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.annotations.SendMsg;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzLcZhgzGxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.engine.*;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzLcZhgzGxQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzCheckRelatedQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzZhGzRestService;
import cn.gtmap.realestate.engine.core.service.BdcGzZhGzService;
import cn.gtmap.realestate.engine.service.BdcGzZhgzCheckService;
import cn.gtmap.realestate.engine.util.Constants;
import cn.gtmap.realestate.engine.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/5
 * @description 不动产组合规则服务
 */
@RestController
@Api(tags = "不动产规则组合规则服务接口")
public class BdcGzZhGzRestController extends BaseController implements BdcGzZhGzRestService{
    @Autowired
    private BdcGzZhGzService bdcGzZhGzService;

    @Autowired
    private BdcGzZhgzCheckService bdcGzCheckService;


    /**
     * @param zhmc
     * @param zhbs
     * @param pageable
     * @return List<BdcGzZhgz>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 分页查询组合规则信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "分页查询规则组合信息", notes = "分页查询规则组合信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "zhbs", value = "组合标识",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zhmc", value = "组合名称",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lcmc", value = "流程名称",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "glgx", value = "关联关系",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzid", value = "子规则id",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zhsm", value = "组合说明",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcGzZhgzDO> listBdcGzZhgzPage(String zhmc, String zhbs, String zhsm, String lcmc, String glgx, String gzid, Pageable pageable) {
        Map<String, String> map = new HashMap(16);
        if(StringUtils.isNotBlank(zhbs)){
            map.put("zhbs",zhbs);
        }
        if(StringUtils.isNotBlank(zhmc)){
            map.put("zhmc",zhmc);
        }
        if(StringUtils.isNotBlank(zhsm)){
            map.put("zhsm",zhsm);
        }
        if(StringUtils.isNotBlank(lcmc)){
            // 流程对应的组合规则标识都以 KEY_后缀形式
            map.put("lcmc", lcmc + "_");
        }
        if(StringUtils.isNotBlank(glgx)){
            map.put("glgx",glgx);
        }
        if(StringUtils.isNotBlank(gzid)){
            map.put("gzid",gzid);
        }
        return bdcGzZhGzService.listBdcXzLwByPage(pageable,map);
    }

    /**
     * @param  zhidList 组合id集合
     * @param  gzid 子规则id
     * @return int 新增关联关系记录数
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     * @description 子规则关联批量组合规则
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("关联关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "zhidList", value = "zhidList", required = true, paramType = "String[]"),
            @ApiImplicitParam(name = "gzid", value = "gzid", dataType = "String", required = true, paramType = "query")})
    public  int glgx(@RequestBody String[] zhidList, @RequestParam(value ="gzid", required = true) String gzid) {
        return bdcGzZhGzService.glgx(zhidList, gzid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable  分页参数
     * @param zhgzParamJson  组合规则参数信息
     * @description 分页获取组合规则关联的子规则信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页获取组合规则关联的子规则信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zhgzParamJson", value = "组合规则参数信息", required = false, paramType = "query")})
    public Page<BdcGzZgzDO> listBdcZhgzZgzPage(Pageable pageable, @RequestParam(name = "zhgzParamJson", required = false)String zhgzParamJson) {
        return bdcGzZhGzService.listBdcZhgzZgzPage(pageable, JSON.parseObject(zhgzParamJson, BdcGzZhGzQO.class));
    }

    /**
     * @param bdcGzZhgzDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 新增规则组合信息
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增组合规则信息", notes = "新增组合规则信息服务")
    @LogNote(value = "新增组合规则信息", action = Constants.LOG_ACTION_GZCZ)
    @SendMsg(description = "保存组合规则信息", action = Constants.ZHGZ_ACTION_SAVE, gzlx = Constants.GZLX_ZHGZ, excutePoint = Constants.EXCUTEPOINT_DOAFTER)
    public BdcGzZhgzDO insertBdcGzZhGz(@RequestBody BdcGzZhgzDO bdcGzZhgzDO) {
        return bdcGzZhGzService.insertBdcGzZhGz(bdcGzZhgzDO);
    }

    /**
     * @param zhid
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据组合id删除组合规则记录
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据组合id删除组合规则信息", notes = "根据组合id删除组合规则信息服务")
    @ApiImplicitParam(name = "zhid", value = "组合id", required = true, dataType = "String", paramType = "query")
    @LogNote(value = "删除组合规则信息", action = Constants.LOG_ACTION_GZCZ)
    @SendMsg(description = "删除组合规则信息", action = Constants.ZHGZ_ACTION_DELETE, gzlx = Constants.GZLX_ZHGZ,excutePoint = Constants.EXCUTEPOINT_DOBEFORE)
    public void delBdcGzZhGz(String zhid) {
        bdcGzZhGzService.delBdcGzZhGz(zhid);
    }

    /**
     * @param bdcGzZhGzQO
     * @return bdcGzZhgzDOList
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过bdcGzZhGzQO查询组合规则list
     */
    @Override
    @ApiOperation(value = "查询组合规则信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcGzZhgzDO> queryBdcGzZhGzDOList(@RequestBody BdcGzZhGzQO bdcGzZhGzQO) {
        BdcGzZhgzDO bdcGzZhgzDO = new BdcGzZhgzDO();
        if(StringUtils.isNotBlank(bdcGzZhGzQO.getZhbs())){
            bdcGzZhgzDO.setZhbs(bdcGzZhGzQO.getZhbs());
        }
        if(StringUtils.isNotBlank(bdcGzZhGzQO.getZhid())){
            bdcGzZhgzDO.setZhid(bdcGzZhGzQO.getZhid());
        }
        if(StringUtils.isNotBlank(bdcGzZhGzQO.getZhmc())){
            bdcGzZhgzDO.setZhmc(bdcGzZhGzQO.getZhmc());
        }
        return bdcGzZhGzService.queryBdcGzZhGzList(bdcGzZhgzDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZhGzQO 验证查询参数
     * @return {List} 组合标识集合
     * @description 查询组合规则标识信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询组合规则标识信息")
    @ApiImplicitParam(name = "bdcGzZhGzQO", value = "组合规则查询参数", dataType = "BdcGzZhGzQO", paramType = "body")
    public List<String> listBdcGzZhgzBs(@RequestBody BdcGzZhGzQO bdcGzZhGzQO) {
        return bdcGzZhGzService.listBdcGzZhgzBs(bdcGzZhGzQO);
    }

    /**
     * @param bdcGzZhgzDO
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 更新不动产组合规则信息
     */
    @Override
    @ApiOperation(value = "更新组合规则信息")
    @ApiImplicitParam(name = "bdcGzZhgzDO", value = "组合规则对象", dataType = "BdcGzZhgzDO", paramType = "body")
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "更新组合规则信息", action = Constants.LOG_ACTION_GZCZ)
    @SendMsg(description = "更新组合规则信息", action = Constants.ZHGZ_ACTION_SAVE, gzlx = Constants.GZLX_ZHGZ,excutePoint = Constants.EXCUTEPOINT_DOAFTER)
    public int updateBdcGzZhGz(@RequestBody BdcGzZhgzDO bdcGzZhgzDO) {
        return bdcGzZhGzService.updateBdcGzZhGz(bdcGzZhgzDO);
    }

    /**
     * 查询组合标识是否唯一
     *
     * @param bdcGzZhgzDO bdcGzZhgzDO
     * @return int 条数
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证组合标识是否唯一")
    @ApiImplicitParam(name = "bdcGzZhgzDO", value = "组合规则DO", dataType = "BdcGzZhgzDO", paramType = "body")
    public int countZhbs(@RequestBody BdcGzZhgzDO bdcGzZhgzDO) {
        return bdcGzZhGzService.countZhbs(bdcGzZhgzDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLcZhgzGxDO 流程规则关系
     * @return 操作数据记录数
     * @description 保存流程和组合规则对照关系
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存流程和组合规则对照关系")
    @ApiImplicitParam(name = "bdcGzLcZhgzGxDO", value = "流程规则关系DO", dataType = "BdcGzLcZhgzGxDO", paramType = "body")
    @LogNote(value = "保存流程和组合规则对照关系", action = Constants.LOG_ACTION_GZCZ)
    public int saveBdcLcZhgzGx(@RequestBody BdcGzLcZhgzGxDO bdcGzLcZhgzGxDO) {
        return bdcGzZhGzService.saveBdcLcZhgzGx(bdcGzLcZhgzGxDO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable             分页对象
     * @param bdcLcZhgzGxParamJson 查询条件
     * @return 流程和规则关系列表
     * @description 查询流程和组合规则对照关系信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询流程和组合规则对照关系信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcLcZhgzGxParamJson", value = "流程和组合规则关系查询参数JSON", required = false, paramType = "query")})
    public Page<BdcGzLcZhgzGxDO> listBdcLcZhgzGx(Pageable pageable,
                                                 @RequestParam(name = "bdcLcZhgzGxParamJson", required = false) String bdcLcZhgzGxParamJson) {
        return bdcGzZhGzService.listBdcLcZhgzGx(pageable, JSON.parseObject(bdcLcZhgzGxParamJson, BdcGzLcZhgzGxQO.class));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLcZhgzGxDOList 待删除记录
     * @return 删除记录数
     * @description 删除流程和组合规则对照关系信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除流程和组合规则对照关系信息")
    @ApiImplicitParam(name = "bdcGzLcZhgzGxDOList", value = "流程规则关系集合", dataType = "List", paramType = "body")
    @LogNote(value = "删除流程和组合规则对照关系信息", action = Constants.LOG_ACTION_GZCZ)
    public int deleteBdcLcZhgzGx(@RequestBody List<BdcGzLcZhgzGxDO> bdcGzLcZhgzGxDOList) {
        return bdcGzZhGzService.deleteBdcLcZhgzGx(bdcGzLcZhgzGxDOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO 规验验证参数
     * @return {BdcGzZhgzTsxxDTO} 提示信息
     * @description 组合规则验证，获取对应提示信息（参数bdcGzYzQO传值：zhbs、paramMap）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "组合规则验证，获取对应提示信息")
    @ApiImplicitParam(name = "bdcGzYzQO", value = "规验验证参数", dataType = "BdcGzYzQO", paramType = "body")
    public BdcGzZhgzTsxxDTO getZhgzYzTsxx(@RequestBody BdcGzYzQO bdcGzYzQO) {
        return bdcGzCheckService.getZhgzYzTsxx(bdcGzYzQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  验证查询参数
     * @return {List} 批量验证提示信息
     * @description 批量组合规则验证，获取对应提示信息
     *  说明：
     *   1、例如传递多个BDCDYH验证查封，返回每个BDCDYH对应的验证提示信息
     *   2、参数bdcGzYzQO传值：zhbs、bdcGzYzsjDTOList
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量组合规则验证，获取对应提示信息")
    @ApiImplicitParam(name = "bdcGzYzQO", value = "验证查询参数", dataType = "BdcGzYzQO", paramType = "body")
    public List<BdcGzYzTsxxDTO> listBdcGzYzTsxx(@RequestBody BdcGzYzQO bdcGzYzQO){
        return bdcGzCheckService.listBdcGzYzTsxx(bdcGzYzQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzQO  验证查询参数
     * @return {List} 批量验证提示信息
     * @description   批量规则验证（传入任意参数）
     *   说明：
     *   1、参数bdcGzYzQO传值：zhbs、paramList
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量规则验证（传入任意参数）")
    @ApiImplicitParam(name = "bdcGzYzQO", value = "验证查询参数", dataType = "BdcGzYzQO", paramType = "body")
    public List<BdcGzYzTsxxDTO> listBdcGzYzTsxxOfAnyParam(@RequestBody BdcGzYzQO bdcGzYzQO){
        return bdcGzCheckService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: zhid 组合规则id
     * @return: ｛String｝复制后新的组合规则ID
     * @description 复制当前组合规则的信息，并创建一个新的组合规则
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "复制组合规则信息")
    @ApiImplicitParam(name = "zhid" ,value = "组合规则id", dataType = "String" ,required = true, paramType = "path")
    @LogNote(value = "复制组合规则信息", action = Constants.LOG_ACTION_GZCZ)
    public String copyBdcGzZhGZ(@PathVariable("zhid") String zhid) {
        if(StringUtils.isBlank(zhid)){
            throw new NullPointerException("子规则系统V2.0：组合规则id为空！");
        }
        return this.bdcGzZhGzService.copyBdcGzZhgz(zhid);
    }

    /**
     * @author: <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @param: String zhgzJSON
     * @return: BdcCommonResponse
     * @description 导入组合规则
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "导入组合规则信息")
    public BdcCommonResponse importZhgz(@RequestParam(name = "zhgzJSON", required = true) String zhgzJSON) {
        return this.bdcGzZhGzService.importZhgz(zhgzJSON);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 组合规则集合
     * @description 获取系统配置的所有强制验证项
     *
     * 说明：强制验证项组合规则标识  流程标识_QZYZ
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取系统配置的所有强制验证项")
    public List<BdcGzZhgzDO> listBdcGzZhgzQzyz() {
        return bdcGzZhGzService.listBdcGzZhgzQzyz();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 组合规则集合
     * @description 获取系统配置的所有强制验证信息（包括关联的关联关系、子规则）
     *
     * 说明：强制验证项组合规则标识  流程标识_QZYZ
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取系统配置的所有强制验证信息（包括关联的关联关系、子规则）")
    public BdcGzQzyzDTO listBdcGzQzyzDTO() {
        return bdcGzZhGzService.listBdcGzQzyzDTO();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {BdcGzQzyzYzDTO} 验证结果
     * @description 验证强制验证配置内容
     *
     * 强制验证项组合规则标识：流程标识_QZYZ
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证强制验证配置内容")
    public BdcGzQzyzYzDTO checkQzyz() {
        return bdcGzZhGzService.checkQzyz();
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "校验哪些流程没有配置关联的 流程转发、新建等组合规则，并且配置了的话是否存在没有关联子规则")
    public BdcGzZhGzCheckRelatedDTO checkRelated(@RequestBody BdcGzZhGzCheckRelatedQO bdcGzZhGzCheckRelatedQO) {
        return bdcGzZhGzService.checkRelated(bdcGzZhGzCheckRelatedQO);
    }
}
