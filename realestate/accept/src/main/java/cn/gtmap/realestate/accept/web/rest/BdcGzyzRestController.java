package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSfxxService;
import cn.gtmap.realestate.accept.service.BdcGzyzService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcXzWlzsDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcXzXmDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcGzyzRestService;
import cn.gtmap.realestate.common.core.service.rest.building.FwHsRestService;
import cn.gtmap.realestate.common.core.service.rest.building.ZdRestService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/22
 * @description 不动产规则验证rest服务
 */
@RestController
@Api(tags = "不动产规则验证rest服务")
public class BdcGzyzRestController extends BaseController implements BdcGzyzRestService {
    @Autowired
    BdcGzyzService bdcGzyzService;

    @Autowired
    ZdRestService zdRestService;

    @Autowired
    FwHsRestService fwHsRestService;

    @Autowired
    BdcSlSfxxService bdcSlSfxxService;

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @return 验证结果（包含入参）
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 流程规则验证
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "流程规则验证", notes = "流程规则验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzYzQO", value = "验证查询参数", required = true, dataType = "BdcGzYzQO", paramType = "query")})
    public List<Map<String, Object>> yzBdcgz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO) {
        return bdcGzyzService.yzBdcgz(bdcGzYzQO);

    }

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 除流程验证外其他验证, 如匹配验证, 页面按钮操作验证等
     * @date : 2020/7/10 14:13
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "流程规则验证", notes = "流程规则验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzYzQO", value = "验证查询参数", required = true, dataType = "BdcGzYzQO", paramType = "query")})
    public List<Map<String, Object>> qtyz(@RequestBody(required = false)BdcGzYzQO bdcGzYzQO) {
        return bdcGzyzService.qtyz(bdcGzYzQO);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcdywybh] 不动产唯一编码 房屋编号或地籍
     * @return: Boolean
     * @description 验证不动产单元号、唯一编号与权籍是否一致
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证不动产单元号、唯一编号与权籍是否一致", notes = "规则验证服务")
    @ApiImplicitParam(name = "bdcdywybh", value = "不动产唯一编码", required = true, paramType = "path")
    public Boolean checkBdcdyhAndBdcdywybhConsistent(@PathVariable(value = "bdcdywybh") String bdcdywybh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        if (StringUtils.isBlank(bdcdywybh)) {
            throw new NullPointerException("不动产唯一编号为空。");
        }
        ZdDjdcbDO zdDjdcbDO = this.zdRestService.queryZdByDjh(bdcdywybh,qjgldm);
        List<FwHsDO> fwHsDOList = fwHsRestService.listFwhsByFwbm(bdcdywybh,qjgldm);
        if (CollectionUtils.isEmpty(fwHsDOList) && null == zdDjdcbDO) {
            return true;
        }
        return false;
    }

    /**
     * @param processInsId 工作流实例ID
     * @return 规则验证信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证流程的收费状态
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "流程规则验证", notes = "流程规则验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public BdcGzyzVO checkLcSfzt(@PathVariable(name = "processInsId") String processInsId) {
        return bdcSlSfxxService.checkLcSfzt(processInsId);
    }

    /**
     * @param jbxxid 基本信息id
     * @return 查封文号数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证查封文号是否重复（批量解封不允许不重复）
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "流程创建规则验证", notes = "流程创建规则验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息id", required = true, dataType = "String", paramType = "path")})
    public Integer checkCfwhSfcf(@PathVariable(name = "jbxxid") String jbxxid) {
        return bdcGzyzService.yzCfwhSl(jbxxid);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型的逗号分隔字符串
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: Boolean
     * @description 验证该不动产单元在登记系统是否已生成权 利
     */
    @Override
    public List<BdcXmDO> checkBdcdyScQl(@PathVariable(name = "bdcdyh") String bdcdyh, @PathVariable(name = "qllx") String qllx) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new NullPointerException("参数缺失，缺失不动产单元号！");
        }
        if (StringUtils.isBlank(qllx)) {
            throw new NullPointerException("参数缺失，缺失权利信息！");
        }
        return this.bdcGzyzService.checkBdcdyScQl(bdcdyh, qllx);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "申请人首套房验证", notes = "申请人首套房验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")})
    public BdcSlxxDTO checkStfFwtc(@PathVariable(name = "xmid") String xmid) {
        return bdcGzyzService.checkStfFwtc(xmid);


    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "是否房查验证", notes = "是否房查验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")})
    public Boolean checkSfFwcxForYcslByXmid(@PathVariable(name = "xmid") String xmid) {
        return bdcGzyzService.checkSfFwcxForYcslByXmid(xmid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证当前不动产单元号是否正在办理其他非登记流程", notes = "验证当前不动产单元号是否正在办理其他非登记流程服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")})
    public BdcSlJbxxDO checkIsblQtfdjlc(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "gzlslid", required = false) String gzlslid) {
        return bdcGzyzService.checkIsblQtfdjlc(bdcdyh, gzlslid);

    }

    /**
     * @param gzlslid 工作流实例ID
     * @param zhbs    组合规则标识
     * @return 提示信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 非登记流程规则验证:流程转发/退回/
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "非登记流程转发规则验证", notes = "非登记流程转发规则验证服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path"), @ApiImplicitParam(name = "zhbs", value = "组合规则标识", required = true, dataType = "String", paramType = "path")})
    public List<BdcGzYzTsxxDTO> fdjlcGzyz(@PathVariable(name = "gzlslid") String gzlslid, @PathVariable(name = "zhbs") String zhbs) {
        return bdcGzyzService.fdjlcGzyz(gzlslid, zhbs);
    }


    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:chneyucheng@gtmap.cn">chneyucheng</a>
     * @description 验证一证多房的情况下，是否都都选全
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证一证多房的情况下，是否都都选全", notes = "验证一证多房的情况下，是否都都选全")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息id", required = true, dataType = "String",
            paramType = "path")})
    public boolean checkYzdfIsInclude(@PathVariable(name = "jbxxid") String jbxxid) {
        return bdcGzyzService.checkYzdfIsInclude(jbxxid);
    }

    /**
     * @param jbxxid 基本信息ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证商品房交易信息权利人是否一致
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证商品房交易信息权利人是否一致", notes = "验证商品房交易信息权利人是否一致服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息id", required = true, dataType = "String",
            paramType = "path")})
    public boolean checkQlrSfyz(@PathVariable(name = "jbxxid") String jbxxid) {
        return bdcGzyzService.checkQlrSfyz(jbxxid);
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否线上缴费成功 成功状态 sfzt=2&& jkfs=2
     * @date : 2020/3/6 8:39
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证是否线上缴费成功", notes = "验证是否线上缴费成功服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String",
            paramType = "path")})
    public boolean checkSfxsJfcg(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcGzyzService.checkSfxsJfcg(gzlslid);
    }

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取当前添加购物车的数据的匹配落宗状态
     * @date : 2020/3/13 14:27
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取当前添加购物车的数据的匹配落宗状态", notes = "获取当前添加购物车的数据的匹配落宗状态服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息id", required = true, dataType = "String",
            paramType = "path")})
    public Map checkPplzzt(@PathVariable(value = "jbxxid") String jbxxid, @RequestParam(name = "xztzlx") String xztzlx) {
        return bdcGzyzService.checkPplzzt(jbxxid, xztzlx);
    }

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 用于创建验证当前流程是否存在外联证书
     * @result 1 表示外联了证书 0 表示没有外联证书 2 表示外联了证书但没有注销
     * @date : 2020/4/15 9:17
     */
    @Override
    public Integer checkSfwlzs(@PathVariable(name = "jbxxid") String jbxxid) {
        return bdcGzyzService.checkSfwlzs(jbxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID, 验证当前流程是否存在外联证书", notes = "根据工作流实例ID, 验证当前流程是否存在外联证书")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Integer checkWlzs(@PathVariable(value="gzlslid") String gzlslid) {
        return bdcGzyzService.checkWlzs(gzlslid);
    }

    /**
     * 用于校验外联土地证是否存在限制状态
     * @param xmid            项目ID
     * @param processDefKey  流程定义ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "校验外联土地证是否存在限制状态", notes = "校验外联土地证是否存在限制状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "processDefKey", value = "流程定义ID", required = true, dataType = "String", paramType = "query")})
    public boolean checkWltdzXzQl(@RequestParam(value="xmid") String xmid,
                                  @RequestParam(value="processDefKey") String processDefKey) {
        if(StringUtils.isBlank(xmid)){
            throw new MissingArgumentException("未获取到项目ID信息。");
        }
        if(StringUtils.isBlank(processDefKey)){
            throw new MissingArgumentException("未获取到流程定义ID。");
        }
        return bdcGzyzService.checkWltdzXzQl(xmid, processDefKey);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据bdcdywybh查询权籍数据关系是否一致
     * @date : 2020/6/9 16:56
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证权籍数据关系是否一致", notes = "验证权籍数据关系是否一致服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String",
            paramType = "path")})
    public Boolean checkQjGxSfyz(@PathVariable(value = "bdcdyh") String bdcdyh) {
        return bdcGzyzService.checkQjGxSfyz(bdcdyh);
    }

    /**
     * @param jbxxid 基本信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断转移换证流程是否登记小类都有选择
     * @result 判断转移换证流程是否登记小类都有选择
     * @date : 2020/6/30 9:17
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "判断转移换证流程是否登记小类都有选择", notes = "判断转移换证流程是否登记小类都有选择服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息id", required = true, dataType = "String",
            paramType = "path")})
    public Boolean checkZyhzDjxl(@PathVariable(name = "jbxxid") String jbxxid) {
        return bdcGzyzService.checkZyhzDjxl(jbxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证流程是否为商品房流程", notes = "验证流程是否为商品房流程")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", required = true, dataType = "String",
            paramType = "path")})
    public Boolean checkSpfLc(@PathVariable(name = "gzldyid") String gzldyid) {
        return bdcGzyzService.checkSpfLc(gzldyid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证选择的承包方和发包方是否一致", notes = "验证选择的承包方和发包方是否一致")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String",
            paramType = "path")})
    public boolean checkCbfFbfSfyz(@PathVariable(name = "jbxxid") String jbxxid){
        return bdcGzyzService.checkCbfFbfSfyz(jbxxid);
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证该xm是否满足省直房改办的状态", notes = "验证该xm是否满足省直房改办的状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String",
            paramType = "path")})
    public boolean checkSfszfgf(@PathVariable(name = "bdcdyh") String bdcdyh){
        return bdcGzyzService.checkSfszfgf(bdcdyh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证承包方合同对应的所有地块是否全部添加", notes = "验证承包方合同对应的所有地块是否全部添加")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String",
            paramType = "path")})
    public boolean checkCbfDkqbtj(@PathVariable(name = "jbxxid") String jbxxid){
        return bdcGzyzService.checkCbfDkqbtj(jbxxid);

    }

    /**
     * @param jbxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证承包经营权做变更或者转移时是否外联了经营权证书
     * @date : 2020/11/4 17:18
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证承包经营权做变更或者转移时是否外联了经营权证书", notes = "验证承包经营权做变更或者转移时是否外联了经营权证书服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String",
            paramType = "path")})
    public boolean checkCbjyqSfwl(@PathVariable(name = "jbxxid") String jbxxid) {
        return bdcGzyzService.checkCbjyqSfwlzs(jbxxid);
    }

    /**
     * @param bdcSlXmDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 验证外网申请的数据是否和登记一致
     * @date : 2020/11/18 17:18
     */
    @Override
    public boolean checkWwsqDjSjSfyz(@RequestBody BdcSlXmDTO bdcSlXmDTO,@PathVariable(name = "lclx") String lclx) {
        return bdcGzyzService.checkWwsqDjSjSfyz(bdcSlXmDTO,lclx);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "表单必填项验证", notes = "表单必填项验证")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Object checkFormBtxYz(@PathVariable(name="gzlslid")String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new AppException("缺失参数：工作流实例ID");
        }
        return bdcGzyzService.checkFormBtxYz(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证存量房是否已经存在备案状态合同信息", notes = "验证存量房是否已经存在备案状态合同信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")})
    public Object checkClfWqxxYz(@PathVariable(name = "xmid") String xmid) {
        return bdcGzyzService.checkClfWqxxYz(xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证收费信息是否已作废", notes = "验证收费信息是否已作废")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Object checkSfzfYz(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcGzyzService.checkSfzfYz(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证收费信息是否已冲红", notes = "验证收费信息是否已冲红")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Object checkSfchYz(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcGzyzService.checkSfchYz(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证流程的已推送完税状态", notes = "验证流程的已推送完税状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Boolean checkLcYtsswzt(@PathVariable(name = "processInsId") String processInsId) {
        return bdcGzyzService.checkLcYtsswzt(processInsId);
    }

    /**
     * 是否申报核税
     *
     * @param htbh
     * @return boolean true 已申报 false 未申报
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "是否申报核税", notes = "是否申报核税")
    @ApiImplicitParams({@ApiImplicitParam(name = "htbh", value = "合同编号", required = true, dataType = "String", paramType = "path")})
    public boolean checkSfSbhs(@PathVariable(name = "htbh") String htbh) {
        return bdcGzyzService.checkSfSbhs(htbh);
    }

    /**
     * 获取配置的pplzztBdcqzhBs 用于验证匹配落宗状态
     *
     * @return
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取配置的pplzztBdcqzhBs 用于验证匹配落宗状态", notes = "获取配置的pplzztBdcqzhBs 用于验证匹配落宗状态")
    public List pplzztBdcqzhBs() {
        return bdcGzyzService.pplzztBdcqzhBs();
    }


    /*
     * 根据单元号查询现势产权是否备案
     * */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据单元号查询现势产权是否备案", notes = "根据单元号查询现势产权是否备案")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "合同编号", required = true, dataType = "String", paramType = "path")})
    public Object checkWqBazt(@PathVariable(name = "bdcdyh") String bdcdyh) {
        return bdcGzyzService.checkWqbaZt(bdcdyh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证一窗受理比对信息", notes = "验证一窗受理比对信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", required = true, dataType = "Boolean", paramType = "query")})
    public boolean checkYcslBdxx(@RequestParam(value="xmid") String xmid,@RequestParam(value="spflc") Boolean spflc){
        return bdcGzyzService.checkYcslBdxx(xmid,spflc);

    }

    /**
     * @param htbh    合同编号
     * @return 接口结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 带入合同备案信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "带入合同备案信息", notes = "带入合同备案信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "htbh", value = "合同编号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", required = true, dataType = "String", paramType = "query")})
    public Object drHtbaxx(@PathVariable(name = "htbh") String htbh, @PathVariable(name = "gzldyid") String gzldyid, @RequestParam(value = "cxlx", required = false) String cxlx) {
        return bdcGzyzService.drHtbaxx(htbh, gzldyid, cxlx);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证受理收件材料必传是否已上传", notes = "验证受理收件材料必传是否已上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public Object checkSjclSfsc(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcGzyzService.checkSjclSfsc(gzlslid);
    }

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否资金监管
     * @date : 2021/7/22 15:06
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证是否资金监管", notes = "验证是否资金监管")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    public String sfzjjg(@PathVariable(value = "xmid") String xmid) {
        return bdcGzyzService.checkSfzjjg(xmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证资金监管是否办理撤销流程", notes = "验证资金监管是否办理撤销流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "String", paramType = "path")})
    public boolean sfblZjjgcx(@PathVariable(name = "xmid") String xmid) {
        return bdcGzyzService.sfblZjjgcx(xmid);
    }

    /**
     * @param yxmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证是否资金监管,优先查询常州市存量房交易一体化平台
     * @date : 2021/7/22 15:06
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证是否资金监管,优先查询常州市存量房交易一体化平台", notes = "验证是否资金监管,优先查询常州市存量房交易一体化平台")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "htbh", value = "合同编号", required = true, dataType = "String", paramType = "path")
    })
    public String sfzjjgYthpt(@PathVariable(value = "yxmid") String yxmid,
                               @PathVariable(name = "htbh") String htbh) {
        return bdcGzyzService.sfzjjgYthpt(yxmid,htbh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证土地证是否关联商品房首次登记", notes = "验证是否资金监管,验证土地证是否关联商品房首次登记")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yxmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    })
    public Integer sfglSpfscdj(@PathVariable(value = "yxmid") String yxmid) {
        return bdcGzyzService.sfglSpfscdj(yxmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "验证土地证的户室是否存在预告", notes = "验证土地证的户室是否存在预告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")
    })
    public Object checkTdHsCzyg(@PathVariable(value = "bdcdyh") String bdcdyh) {
        return bdcGzyzService.checkTdHsCzyg(bdcdyh);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "选择不动产单元验证，验证一证多房数据是否全部选择", notes = "选择不动产单元验证，验证一证多房数据是否全部选择")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcXzXmDTO", value = "选择项目实体", required = true, dataType = "String", paramType = "body")
    })
    public boolean checkXzYzdf(@RequestBody BdcXzXmDTO bdcXzXmDTO) {
        return bdcGzyzService.checkXzYzdf(bdcXzXmDTO);
    }

    /**
     * @param jbxxid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 传入单元号，工作流定义id，qjgldm对当前数据进行权籍的xsd 校验
     * @date : 2022/11/21 9:15
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "创建验证，对当前流程数据进行权籍xsd校验", notes = "创建验证，对当前流程数据进行权籍xsd校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jbxxid", value = "基本信息id", required = true, dataType = "String", paramType = "query")
    })
    public Object qjsjXsdJy(@RequestParam(name = "jbxxid") String jbxxid) {
        return bdcGzyzService.qjsjXsdJy(jbxxid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "单元号更正流程,外联证书验证", notes = "单元号更正流程,外联证书验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcXzWlzsDTO", value = "选择外联证书实体", required = true, dataType = "String", paramType = "body")
    })
    public String checkDyhgzWlzs(@RequestBody BdcXzWlzsDTO bdcXzWlzsDTO){
        return bdcGzyzService.checkDyhgzWlzs(bdcXzWlzsDTO);

    }

}
