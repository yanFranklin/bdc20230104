package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlJbxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlXmService;
import cn.gtmap.realestate.accept.core.service.BdcSlYcslDjywDzbService;
import cn.gtmap.realestate.accept.service.*;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYcslxxVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 为页面提供获取、新增、更新、删除受理信息服务
 */
@RestController
@Api(tags = "为页面提供获取、新增、更新、删除受理信息服务")
public class BdcSlRestController extends BaseController implements BdcSlRestService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlRestController.class);

    @Autowired
    private List<BdcSlService> bdcSlServiceList;
    @Autowired
    private BdcInitFeignService bdcInitFeignService;
    @Autowired
    private CshBdcSlXmService cshBdcSlXmService;
    @Autowired
    private BdcSlBdclxService bdcSlBdclxService;
    @Autowired
    private BdcSlJbxxService bdcSlJbxxService;
    @Autowired
    private BdcSlXmService bdcSlXmService;
    @Autowired
    private WwsqCjBdcXmService wwsqCjBdcXmService;
    @Autowired
    private BdcNslxdService bdcNslxdService;
    @Autowired
    private BdcYcslManageService bdcYcslManageService;
    @Autowired
    private BdcSlYcslDjywDzbService bdcSlYcslDjywDzbService;



    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据基本信息ID获取受理信息", notes = "根据基本信息ID获取受理信息服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public BdcSlxxDTO queryBdcSlxx(@PathVariable(value = "jbxxid") String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            errorException(Constants.PARAM_ERROR);
        }
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        if (CollectionUtils.isNotEmpty(bdcSlServiceList)) {
            for (int i = 0; i < bdcSlServiceList.size(); i++) {
                BdcSlService bdcSlService = bdcSlServiceList.get(i);
                bdcSlxxDTO = bdcSlService.queryBdcSlxx(jbxxid, bdcSlxxDTO,null);
            }
        }
        return bdcSlxxDTO;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据基本信息ID获取增量受理信息", notes = "根据基本信息ID获取增量受理信息服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public BdcSlxxDTO queryBdcZlSlxx(@PathVariable(value = "jbxxid") String jbxxid) {
        if (StringUtils.isBlank(jbxxid)) {
            errorException(Constants.PARAM_ERROR);
        }
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        if (CollectionUtils.isNotEmpty(bdcSlServiceList)) {
            for (int i = 0; i < bdcSlServiceList.size(); i++) {
                BdcSlService bdcSlService = bdcSlServiceList.get(i);
                bdcSlxxDTO = bdcSlService.queryBdcSlxx(jbxxid, bdcSlxxDTO,CommonConstantUtils.SF_S_DM);
            }
        }
        return bdcSlxxDTO;
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新受理信息", notes = "更新受理信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlxxDTO", value = "受理信息", required = true, dataType = "BdcSlxxDTO")
    })
    public void updateBdcSlxx(@RequestBody BdcSlxxDTO bdcSlxxDTO) {
        if (CollectionUtils.isNotEmpty(bdcSlServiceList)) {
            for (int i = 0; i < bdcSlServiceList.size(); i++) {
                BdcSlService bdcSlService = bdcSlServiceList.get(i);
                bdcSlService.updateBdcSlxx(bdcSlxxDTO);
            }
        }
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据工作流实例ID删除受理信息", notes = "根据工作流实例ID删除受理信息服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public void deleteBdcSlxx(@PathVariable(value = "gzlslid") String gzlslid) {
        if (CollectionUtils.isNotEmpty(bdcSlServiceList) &&StringUtils.isNotBlank(gzlslid)) {
            String jbxxid = StringUtils.EMPTY;
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getJbxxid())) {
                jbxxid =bdcSlJbxxDO.getJbxxid();
            }
            for (int i = 0; i < bdcSlServiceList.size(); i++) {
                BdcSlService bdcSlService = bdcSlServiceList.get(i);
                bdcSlService.deleteSlxx(jbxxid,gzlslid);
            }
        }
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "初始化受理信息", notes = "初始化受理信息服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public void cshBdcSlxx(@PathVariable(value = "jbxxid") String jbxxid) throws Exception {
        Date startTime = new Date();
        BdcSlxxDTO bdcSlxxDTO = queryBdcSlxx(jbxxid);
        //处理需要初始化的数据---判断交易信息和受理项目信息是否相同条数，相同带入权利人，不相同不带入权利人
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmService.dealCshData(bdcSlxxDTO.getBdcSlXmList(),bdcSlxxDTO.getBdcSlJbxx(),false));
        long time = System.currentTimeMillis() - startTime.getTime();
        LOGGER.info("构造初始化实体基本信息ID{}，所耗时间：{}", jbxxid, time);
        LOGGER.info("初始化开始：流程基本信息ID{}", jbxxid);
        Date cshstartTime = new Date();
        LOGGER.info("初始化开始：流程基本信息{}", JSONObject.toJSONString(bdcSlxxDTO));
        bdcInitFeignService.csh(bdcSlxxDTO);
        long cshtime = System.currentTimeMillis() - cshstartTime.getTime();
        LOGGER.info("已完成流程基本信息ID{}初始化，所耗时间：{}", jbxxid, cshtime);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "初始化收件材料", notes = "初始化收件材料服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public void cshSjcl(@PathVariable(value = "gzlslid") String gzlslid) {
        cshBdcSlXmService.cshSjcl(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "初始化合同信息的电子合同信息", notes = "初始化合同信息的电子合同信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "htbh", value = "工作流定义ID", required = true, dataType = "String", paramType = "query")
    })
    public void cshHtxxSjcl(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "htbh") String htbh) {
        cshBdcSlXmService.cshHtxxSjcl(gzlslid, htbh);
    }

    /**
     * @param processDefId 工作流定义ID
     * @return 登记小类配置信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据定义id和权利类型获取配置信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询登记小类配置", notes = "查询登记小类配置服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processDefId", value = "工作流定义ID", required = true, dataType = "String", paramType = "query")
    })
    public Integer queryBdcdjxlPzBdclx(@RequestParam(value = "processDefId") String processDefId, @RequestParam(value = "bdcdyh") String bdcdyh) {
        return bdcSlBdclxService.queryBdclx(processDefId, bdcdyh);
    }

    /**
     * @param wwsqCjBdcXmRequestDTO
     * @return cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请创建项目
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "外网申请创建项目", notes = "外网申请创建项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wwsqCjBdcXmRequestDTO", value = "外网申请创建项目受理信息", required = true, dataType = "wwsqCjBdcXmRequestDTO")
    })
    public WwsqCjBdcXmResponseDTO wwsqCjBdcXm(@RequestBody WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) throws Exception {
        WwsqCjBdcXmResponseDTO responseDTO = new WwsqCjBdcXmResponseDTO();
        if (CommonConstantUtils.SF_ZDBJ.equals(wwsqCjBdcXmRequestDTO.getSfzdbj())) {
            responseDTO.setSfzbbj(true);
        } else {
            responseDTO.setSfzbbj(false);
        }
        // 规则验证
        List<Map<String, Object>> gzyzResult = new ArrayList<>();
        if (wwsqCjBdcXmRequestDTO.isGzyz()) {
            gzyzResult = wwsqCjBdcXmService.yzBdcdy(wwsqCjBdcXmRequestDTO.getBdcSlxxDTO());
        }

        // 如果规则验证结果 为空
        if (CollectionUtils.isNotEmpty(gzyzResult)) {
            gzyzResult = wwsqCjBdcXmService.dsfDealWlzsXmid(wwsqCjBdcXmRequestDTO, gzyzResult);
        }
        if(CollectionUtils.isNotEmpty(gzyzResult)){
            // 验证不通过
            responseDTO.setGzyzList(gzyzResult);
        }else{
            // 验证通过
            BdcSlxxDTO bdcSlxxDTO = wwsqCjBdcXmRequestDTO.getBdcSlxxDTO();
            if(bdcSlxxDTO ==null||bdcSlxxDTO.getBdcSlJbxx() ==null){
                throw new AppException("外网申请创建受理基本信息为空");
            }
            String gzldyid =bdcSlxxDTO.getBdcSlJbxx().getGzldyid();
            if(bdcSlYcslDjywDzbService.checkIsYcslLc(gzldyid)){
                //一窗流程
                responseDTO =wwsqCjBdcXmService.cjFdjlcXm(wwsqCjBdcXmRequestDTO);
            }else{
                WwsqCjxmResponseDTO wwsqCjxmResponseDTO = wwsqCjBdcXmService.cjBdcXm(wwsqCjBdcXmRequestDTO);
                responseDTO.setBdcXmDOList(wwsqCjxmResponseDTO.getBdcXmDOList());
                responseDTO.setWwsqZhlcSjclDTOList(wwsqCjxmResponseDTO.getWwsqZhlcSjclDTOList());
            }
        }
        return responseDTO;
    }

    /**
     * @param bdcSlxxDTO
     * @return List<Map<String ,Object>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请创建项目 规则验证
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "外网申请创建项目规则验证", notes = "外网申请创建项目规则验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlxxDTO", value = "外网申请创建项目受理信息", required = true, dataType = "bdcSlxxDTO")
    })
    public List<Map<String, Object>> wwsqCjBdcXmGzyz(@RequestBody BdcSlxxDTO bdcSlxxDTO) {
        return wwsqCjBdcXmService.yzBdcdy(bdcSlxxDTO);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 外网申请自动转发
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "外网申请自动转发", notes = "外网申请自动转发")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "autoForwardTaskDTO", value = "下一节点信息", required = true, dataType = "AutoForwardTaskDTO"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    })
    public void wwsqAutoTurn(@PathVariable(value = "gzlslid") String gzlslid,@RequestBody AutoForwardTaskDTO autoForwardTaskDTO) throws Exception {
        wwsqCjBdcXmService.autoTurnWorkflow(gzlslid,autoForwardTaskDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "外网申请自动转发(包含规则验证)", notes = "外网申请自动转发(包含规则验证)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "autoForwardTaskDTO", value = "下一节点信息", required = true, dataType = "AutoForwardTaskDTO"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    })
    public Map<String, String> autoTurnWithGzyz(@PathVariable(value = "gzlslid") String gzlslid, @RequestBody AutoForwardTaskDTO autoForwardTaskDTO) throws Exception {
        return wwsqCjBdcXmService.autoTurnWithGzyz(gzlslid, autoForwardTaskDTO);

    }

    /**
     * 外网申请自动转发
     * <ul>
     *     <li>如果节点未被认领，由配置中默认用户自动认领</li>
     *     <li>自动签名：签名信息取上一个节点用户，如果未被认领则取配置中默认用户</li>
     *     <li>转发前验证：必填项和规则验证</li>
     *     <li>转发到下个节点</li>
     * </ul>
     *
     * @param gzlslid            工作流实例ID
     * @param autoForwardTaskDTO 下一节点信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "外网申请自动转发「转发验证」", notes = "外网申请自动转发")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "autoForwardTaskDTO", value = "下一节点信息", required = true, dataType = "AutoForwardTaskDTO"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    })
    public Map<String, String> autoturnZfyz(@PathVariable(value = "gzlslid") String gzlslid, @RequestBody AutoForwardTaskDTO autoForwardTaskDTO) throws Exception {
        return wwsqCjBdcXmService.autoTurn(gzlslid, autoForwardTaskDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "增量初始化受理信息", notes = "增量初始化受理信息服务")
    @ApiImplicitParam(name = "jbxxid", value = "基本信息ID", required = true, dataType = "String", paramType = "path")
    public void zlcshBdcSlxx(@PathVariable(value = "jbxxid") String jbxxid) throws Exception {
        BdcSlxxDTO bdcSlxxDTO = queryBdcZlSlxx(jbxxid);
        if (bdcSlxxDTO != null) {
            //处理需要初始化的数据
            bdcSlxxDTO.setBdcSlXmList(bdcSlXmService.dealCshData(bdcSlxxDTO.getBdcSlXmList(), bdcSlxxDTO.getBdcSlJbxx(), true));
            bdcInitFeignService.csh(bdcSlxxDTO);
        }
        //初始化成功,将受理项目中sfzlcsh置空
        BdcSlXmQO bdcSlXmQO =new BdcSlXmQO();
        bdcSlXmQO.setJbxxid(jbxxid);
        bdcSlXmQO.setSfzlcsh(CommonConstantUtils.SF_S_DM);
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmService.listBdcSlXm(bdcSlXmQO);
        if(CollectionUtils.isNotEmpty(bdcSlXmDOList)){
            for(BdcSlXmDO bdcSlXmDO:bdcSlXmDOList){
                bdcSlXmDO.setSfzlcsh(CommonConstantUtils.SF_F_DM);
                bdcSlXmService.updateBdcSlXm(bdcSlXmDO);

            }
        }
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "初始化受理申请信息(非登记业务流程)", notes = "初始化受理申请信息(非登记业务流程)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlCshDTO", value = "受理初始化对象", required = true, dataType = "BdcSlCshDTO")
    })
    public void cshBdcSlSqxx(@RequestBody BdcSlCshDTO bdcSlCshDTO) throws Exception{
        cshBdcSlXmService.cshBdcSlSqxx(bdcSlCshDTO);
    }

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processInsId 工作流实例id
     * @return: List<Map < String, String>> 纳税联系单数据
     * @description 通过工作流实例id获取纳税联系单表单数据，返回值为List类型
     */
    @Override
    public List<Map<String, String>> getNslxdData(String processInsId) throws Exception {
        return this.bdcNslxdService.getNslxdData(processInsId);
    }

    /**
     * @param bdcSlCshDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化一窗受理流程不动产单元信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "初始化一窗受理流程不动产单元信息", notes = "初始化一窗受理流程不动产单元信息服务")
    @ApiImplicitParam(name = "bdcSlCshDTO", value = "受理初始化对象", required = true, dataType = "BdcSlCshDTO", paramType = "query")
    public void cshBdcSlYcslxx(@RequestBody BdcSlCshDTO bdcSlCshDTO) throws Exception {
        BdcSlxxDTO bdcSlxxDTO = queryBdcSlxx(bdcSlCshDTO.getJbxxid());
        if (bdcSlxxDTO != null) {
            //调用初始化接口获取不动产单元信息
            List<BdcYwxxDTO> bdcYwxxDTOList =bdcInitFeignService.ycCsh(bdcSlxxDTO);
            //受理一窗信息初始化
            bdcYcslManageService.cshBdcYcslXx(bdcYwxxDTOList,bdcSlxxDTO);





        }
    }

    /**
     * @param bdcTsDjxxRequestDTO 一窗推送登记请求对象
     * @return 推送结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送一窗信息创建登记流程
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送一窗信息创建登记流程", notes = "推送一窗信息创建登记流程服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcTsDjxxRequestDTO", value = "一窗推送登记请求对象", required = true, dataType = "BdcTsDjxxRequestDTO")
    })
    public InitTsBdcDjxxResponseDTO initTsBdcDjxx(@RequestBody BdcTsDjxxRequestDTO bdcTsDjxxRequestDTO) throws Exception {
        return bdcYcslManageService.initTsBdcDjxx(bdcTsDjxxRequestDTO);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新非登记流程案件状态为已办结", notes = "更新非登记流程案件状态为已办结服务")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String",paramType = "query")
    public void changeAjztEnd(@RequestParam(value = "processInsId") String processInsId){
        if(StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException(messageProvider.getMessage("processInsId"));
        }
        bdcYcslManageService.changeAjztEnd(processInsId);


    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新业务受理状态", notes = "更新业务受理状态服务")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String",paramType = "query")
    public void updateYwslzt(@RequestParam(value = "processInsId") String processInsId,@PathVariable(value = "ywslzt") Integer ywslzt){
        if(StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException(messageProvider.getMessage("processInsId"));
        }
        bdcYcslManageService.updateYwslzt(processInsId,ywslzt);


    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "同步生成受理信息", notes = "同步生成受理信息")
    @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String",paramType = "query")
    public void syncYcslxx(@RequestParam(value = "gzlslid") String gzlslid,@RequestParam(value = "xmid",required = false) String xmid) throws Exception{
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException(messageProvider.getMessage("gzlslid"));
        }
        bdcYcslManageService.syncLcYcslxx(gzlslid,xmid);


    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "同步生成一窗受理信息", notes = "同步生成一窗受理信息服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String",paramType = "query")
    public void syncSlxx(@RequestParam(value = "gzlslid") String gzlslid,@RequestParam(value = "xmid",required = false) String xmid) throws Exception {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException(messageProvider.getMessage("gzlslid"));
        }
        bdcYcslManageService.syncLcSlxx(gzlslid,xmid);
    }

    @Override
    @ApiOperation(value = "分页一窗受理信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlXmQO", value = "查询对象", dataType = "String", paramType = "query")})
    public Page<BdcYcslxxVO> listYcslxxByPage(Pageable pageable, @RequestParam(name = "bdcSlXmQO", required = false) String bdcYcslQOStr){
        return bdcYcslManageService.listYcslxxByPage(pageable, bdcYcslQOStr);
    }

    @Override
    @ApiOperation(value = "一窗受理信息列表")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcSlXmQO", value = "查询对象", dataType = "String", paramType = "query")})
    public List<BdcYcslxxVO> listYcslxxList(@RequestParam(name = "bdcSlXmQO", required = false) String bdcYcslQOStr){
        return bdcYcslManageService.listYcslxxList(bdcYcslQOStr);
    }

    @Override
    @ApiOperation(value = "根据项目ID集合删除受理信息(购物车删除)")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xmidList", value = "项目ID集合", dataType = "body", paramType = "query")})
    public void deleteBdcSlxx(@RequestBody List<String> xmidList){
        bdcSlXmService.deleteBdcSlxx(xmidList,CommonConstantUtils.SL_DELETE_GWC);

    }

    @Override
    @ApiOperation(value = "根据登记工作流实例ID获取外网申请受理编号")
    public String getWwsqSlbhByDjGzlslid(@PathVariable(value = "gzlslid") String gzlslid){
        return bdcYcslManageService.getWwsqSlbhByDjGzlslid(gzlslid);

    }

    @Override
    @ApiOperation(value = "根据登记工作流实例ID获取银行系统申请受理编号")
    public String getYhxtSlbhByDjGzlslid(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcYcslManageService.getYhxtSlbhByDjGzlslid(gzlslid);

    }

    @Override
    public Map<String, String> cshCjBdcXm(@RequestBody BdcSlCshDTO bdcSlCshDTO) throws Exception {
        return cshBdcSlXmService.cshCjBdcXm(bdcSlCshDTO);

    }

    @Override
    public CommonResponse yzCshxxBeforeCj(@RequestBody BdcSlCshDTO bdcSlCshDTO) {
        return cshBdcSlXmService.yzCshxxBeforeCj(bdcSlCshDTO);
    }

    /**
     * @param dm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断是否主房
     * @date : 2022/7/14 15:19
     */
    @Override
    public boolean checkSfzf(@RequestParam(name = "dm") String dm) {
        return cshBdcSlXmService.checkSfzf(dm);
    }
}
