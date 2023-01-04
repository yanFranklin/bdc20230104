package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.service.BdcYjdService;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.*;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmFilesDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.ResultCode;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYjdQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcYjdRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/1
 * @description 移交单rest服务
 */
@RestController
@Api(tags = "移交单服务接口")
public class BdcYjdRestController extends BaseController implements BdcYjdRestService {
    @Autowired
    BdcYjdService bdcYjdService;

    @Autowired
    EntityMapper entityMapper;

    @Value("${yjdqcGly:}")
    protected String yjdqcGly;

    /**
     * 归档是否要查询受理的数据
     */
    @Value("${gdxx.cxsl:false}")
    Boolean gdcxsl;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    /**
     * @param pageable 分页对象
     * @param bdcYjdQO 移交单查询对象
     * @return 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询移交单移交移交单内容
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询移交单内容")
    @Override
    public Page<BdcYjdDTO> listBdcYjdByPage(Pageable pageable, @RequestParam(name = "bdcYjdQO", required = false) String bdcYjdQO) {
        if (StringUtils.isBlank(bdcYjdQO)) {
            throw new AppException("查询参数不能为空！");
        }
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(bdcYjdQO, HashMap.class));
        if (MapUtils.isNotEmpty(map) && map.containsKey("qssj") && null != map.get("qssj") && map.containsKey("jzsj") && null != map.get("jzsj")) {
            if (map.get("qssj").equals(map.get("jzsj"))) {
                map.put("yjsj", DateFormatUtils.format(new Date(MapUtils.getLongValue(map, "qssj")), "yyyy-MM-dd"));
                map.put("qssj", null);
                map.put("jzsj", null);
            } else {
                map.put("qssj", DateFormatUtils.format(new Date(MapUtils.getLongValue(map, "qssj")), "yyyy-MM-dd"));
                map.put("jzsj", DateFormatUtils.format(new Date(MapUtils.getLongValue(map, "jzsj")), "yyyy-MM-dd"));
            }
        }
        UserDto currentUser = userManagerUtils.getCurrentUser();
        String qxdm = "";
        if(!"haimen".equals(map.get("version")) && !CommonConstantUtils.SYSTEM_VERSION_BB.equals(map.get("version"))) {
            List<OrganizationDto> listOrgDto = currentUser.getOrgRecordList();
            if(CollectionUtils.isNotEmpty(listOrgDto)){
                qxdm = listOrgDto.get(0).getRegionCode();
            }
        }
        //如果有受理编号则转换为xmid进行处理--查询受理的时候
        //查询移交单时，如果是需要查询受理的话则将页面的受理编号查询条件转换为xmid条件
        // ，因为受理xm不在当前库，用xm表的slbh无法查出对应的移交单，将受理和登记的slbh都转换为xmid
        if(gdcxsl && map.containsKey("slbh") && StringUtils.isNotBlank((String) map.get("slbh"))){
            List<String> xmids = new ArrayList<>();
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setSlbh((String) map.get("slbh"));
            if(StringUtils.isNotBlank(qxdm)){
                bdcXmQO.setQxdm(qxdm);
            }
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOS)){
                xmids.addAll(bdcXmDOS.stream().map(BdcXmDO::getXmid).collect(Collectors.toList()));
            }
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh((String) map.get("slbh"), null);
            if(Objects.nonNull(bdcSlJbxxDO)) {
                List<BdcSlXmDO> bdcSlXmDOS = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                if(CollectionUtils.isNotEmpty(bdcSlXmDOS)){
                    xmids.addAll(bdcSlXmDOS.stream().map(BdcSlXmDO::getXmid).collect(Collectors.toList()));
                }
            }

            if(CollectionUtils.isNotEmpty(xmids)){
                map.put("xmidList",xmids);
                map.remove("slbh");
            }
        }

        //查询受理时是left join
        map.put("gdcxsl",gdcxsl);

        if (StringUtils.isNotBlank(yjdqcGly)) {
            List<String> roleList = Arrays.asList(yjdqcGly.split(","));
            List<RoleDto> roleRecordList = currentUser.getRoleRecordList();
            // 页面上显示的编码对应字段中的 name
            for (RoleDto roleDto : roleRecordList) {
                if (roleList.contains(roleDto.getName())) {
                    return repo.selectPaging("listBdcYjdByPage", map, pageable);
                }
            }
        }

        // 当前用户只能看到 本组织的移交单，用区县代码过滤
        // 查询受理是不用qxdm，已经转换为xmid了，查询xmid时带上了qxdm条件了
        if(!"haimen".equals(map.get("version"))
                && !CommonConstantUtils.SYSTEM_VERSION_BB.equals(map.get("version"))
                && !gdcxsl
        ) {
           map.put("qxdm",qxdm);
        }
        return repo.selectPaging("listBdcYjdByPage", map, pageable);
    }


    /**
     * @param pageable 分页对象
     * @return 分页查询结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询移交单核验归档信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询移交单内容")
    @Override
    public Page<BdcYjdGdxxDTO> listBdcYjdGdxx(Pageable pageable, @RequestParam(name = "bdcYjdQO", required = false) String bdcYjdQO) {
        if (StringUtils.isBlank(bdcYjdQO)) {
            throw new AppException("查询参数不能为空！");
        }
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(bdcYjdQO, HashMap.class));
        if(gdcxsl) {
            return bdcYjdService.listBdcYjdGdxx(pageable, new HashMap<>(map));
        }else {
            return repo.selectPaging("listBdcYjdGdxxByPage", map, pageable);
        }
    }

    /**
     * @param gxid 关系id
     * @return 删除归档信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除归档信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除移交单归档信息")
    @Override
    public void delGdxx(String gxid) {
        entityMapper.deleteByPrimaryKey(BdcYjdGdxxGxDTO.class, gxid);
    }

    /**
     * @param bdcYjdQO
     * @return 移交单编号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成移交单编号
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成移交单编号")
    @Override
    public BdcYjdDO generateYjdBh(@RequestBody BdcYjdQO bdcYjdQO) {
        return bdcYjdService.generateYjdBh(bdcYjdQO);
    }

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return BdcYjdDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成并保存移交单信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成并保存移交单信息")
    @Override
    public BdcYjdDO generateAndSaveYjdxx(@RequestBody BdcYjdQO bdcYjdQO) {
        return bdcYjdService.generateAndSaveYjdxx(bdcYjdQO);
    }

    /**
     * @param bdcYjdDO 移交单DO
     * @return 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新移交单信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新移交单信息（空值也会更新）")
    @Override
    public int updateBdcYjd(@RequestBody BdcYjdDO bdcYjdDO) {
        return bdcYjdService.updateBdcYjd(bdcYjdDO);
    }

    /**
     * @param bdcXmYjdGxDOList 项目移交单关系列表
     * @return 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新项目移交单关系
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量更新项目移交单关系")
    @Override
    public int updateXmYjdGx(@RequestBody List<BdcXmYjdGxDO> bdcXmYjdGxDOList) {
        return 0;
    }

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 移交单信息查询
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "移交单包含项目信息查询")
    @Override
    public List<BdcYjdDTO> listYjdAndXm(@RequestBody BdcYjdQO bdcYjdQO) {
        return bdcYjdService.listYjdAndXm(bdcYjdQO);
    }

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询拥有移交单的项目
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询拥有移交单的项目")
    @Override
    public List<BdcXmYjdDTO> listXmOwnYjd(@RequestBody BdcYjdQO bdcYjdQO) {
        return bdcYjdService.listXmOwnYjd(bdcYjdQO);
    }

    /**
     * @param yjdid 移交单ID
     * @param dylx
     * @param bdcUrlDTO 地址对象
     * @return 打印xml字符串
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取移交单的打印xml
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取移交单的打印xml")
    @Override
    public String yjdPrintXml(@PathVariable(name = "yjdid") String yjdid, @PathVariable(name = "dylx") String dylx, @RequestBody BdcUrlDTO bdcUrlDTO) {
        return bdcYjdService.yjdPrintXml(yjdid, dylx, bdcUrlDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页信息
     * @param bdcYjdQO 移交单项目信息查询参数
     * @description  查询流程项目信息（用于海门新增移交单场景，根据流程移交）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询流程项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcYjdQO", value = "移交单项目信息查询参数", required = false, paramType = "query")})
    public Page<BdcYjdXmxxDTO> listBdcYjdXmxx(Pageable pageable, @RequestParam(name = "bdcYjdQO", required = false) String bdcYjdQO) {
        return bdcYjdService.listBdcYjdXmxx(pageable, JSON.parseObject(bdcYjdQO, BdcYjdQO.class));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdXmxxDTO 项目信息
     * @return wjy : 没有移交过； yyj : 已经移交过
     * @description  核查当前项目是否已经移交过
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("核查当前项目是否已经移交过")
    @ApiImplicitParam(name = "bdcYjdXmxxDTO", value = "项目信息", required = true, paramType = "body")
    public String checkState(@RequestBody BdcYjdXmxxDTO bdcYjdXmxxDTO) {
        return bdcYjdService.checkState(bdcYjdXmxxDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbhList 受理编号集合
     * @return 移交单信息
     * @description  （海门）生成移交单
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("（海门）生成移交单")
    @ApiImplicitParam(name = "slbhList", value = "受理编号集合", required = true, paramType = "body")
    public BdcYjdDO generateBdcYjd(@RequestBody List<String> slbhList) {
        return bdcYjdService.generateBdcYjd(slbhList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdDOList 移交单集合
     * @return  操作返回信息
     * @description （海门）进行移交单移交到档案
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("（海门）进行移交单移交到档案")
    @ApiImplicitParam(name = "bdcYjdDOList", value = "移交单集合", required = true, paramType = "body")
    public String executeYj(@RequestBody List<BdcYjdDO> bdcYjdDOList) {
        return bdcYjdService.executeYj(bdcYjdDOList);
    }

    /**
     * @param slbhList 受理编号集合
     * @return 受理编号相关文件信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description （海门）提供受理编号相关文件信息给档案
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("（海门）提供受理编号相关文件信息给档案")
    @ApiImplicitParam(name = "slbhList", value = "受理编号集合", required = true, paramType = "body")
    public List<BdcYjdXmFilesDTO> getFilesBySlbhList(@RequestBody List<String> slbhList) {
        return bdcYjdService.getFilesBySlbhList(slbhList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param yjxx 移交信息
     * @return {ResultCode} 返回状态信息实体
     * @description （海门）提供给档案接口，档案系统接收档案后回调该接口进行更新接收人等信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("（海门）提供给档案接口，档案系统接收档案后回调该接口进行更新接收人等信息")
    @ApiImplicitParam(name = "yjxx", value = "移交信息", required = true, paramType = "body")
    public ResultCode updateYjxx(@RequestBody List<BdcYjdDO> yjxx) {
        return bdcYjdService.updateYjxx(yjxx);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 上一手受理编号
     * @description （海门提供给档案接口）根据受理编号获取上一手项目受理编号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("（海门提供给档案接口）根据受理编号获取上一手项目受理编号")
    @ApiImplicitParam(name = "slbh", value = "上一手受理编号", required = true, paramType = "param")
    public List<String> listPreSlbh(@RequestParam("slbh") String slbh) {
        return bdcYjdService.listPreSlbh(slbh);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 下一手受理编号
     * @description （海门提供给档案接口）根据受理编号获取下一手项目受理编号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("（海门提供给档案接口）根据受理编号获取下一手项目受理编号")
    @ApiImplicitParam(name = "slbh", value = "下一手受理编号", required = true, paramType = "param")
    public List<String> listNextSlbh(@RequestParam("slbh") String slbh) {
        return bdcYjdService.listNextSlbh(slbh);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcHaimenYhYjdDTO
     * @description 海门银行系统提供移交单接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public void saveHaimenYhYjd(@RequestBody BdcHaimenYhYjdDTO bdcHaimenYhYjdDTO) {
        bdcYjdService.saveHaimenYhYjd(bdcHaimenYhYjdDTO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcYjdTaskGxDOList]
     * @return int
     * @description 保存移交单任务关系 蚌埠
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存移交单任务关系")
    @ApiImplicitParam(name = "bdcYjdTaskGxDOList", value = "移交单任务关系集合", required = true, paramType = "body")
    public int saveBdcYjdTaskGx(@RequestBody List<BdcYjdTaskGxDO> bdcYjdTaskGxDOList) {
        return entityMapper.insertBatchSelective(bdcYjdTaskGxDOList);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [taskids] 任务id
     * @return int
     * @description 更新移交单打印状态 蚌埠
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新移交单打印状态")
    @ApiImplicitParam(name = "taskids", value = "任务id", required = true, paramType = "body")
    public int updateYjdDyztBytaskid(@RequestBody List<String> taskids) {
        return bdcYjdService.updateYjdDyztByTaskid(taskids);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [paramMap]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO>
     * @description 查询移交单任务关系 蚌埠
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询移交单任务关系")
    @ApiImplicitParam(name = "paramMap", value = "查询参数", required = true, paramType = "body")
    public List<BdcYjdTaskGxDO> getYjdTaskGx(@RequestBody Map paramMap) {
        return bdcYjdService.getYjdTaskGx(paramMap);
    }
}
