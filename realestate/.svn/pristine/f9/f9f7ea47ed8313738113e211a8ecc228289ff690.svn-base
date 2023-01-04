package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.service.BdcBdcqzhService;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsQsztDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmAndQlrDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsXmAndQlrQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZssdQO;
import cn.gtmap.realestate.common.core.service.rest.certificate.BdcZsRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsqdVO;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>"
 * @version 1.0, 2018/11/10
 * @description 证书服务
 */
@RestController
@Api(tags = "不动产证书服务接口")
public class BdcZsRestController extends BaseController implements BdcZsRestService {
    @Autowired
    BdcZsService bdcZsService;
    @Autowired
    BdcBdcqzhService bdcBdcqzhService;
    /**
     * Redis操作
     */
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @param xmids 项目ids
     * @return {String} 保存的Redis key
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description xmids，保存至Redis中
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("证书附表预览的xmids，保存至Redis中")
    @ApiImplicitParam(name = "listXmids", value = "xmids", required = true, paramType = "body")
    public String saveListXmidsToRedis(@RequestBody String xmids) {
        // 保存至Redis
        return redisUtils.addStringValue(CommonConstantUtils.REDIS_BATCH_ZSZM_PRINT + UUIDGenerator.generate(),
                xmids, 120);
    }

    /**
     * @param zsid 证书id
     * @return BdcZsDO 不动产权证
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据证书id查询证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "string", paramType = "path")})
    @Override
    public BdcZsDO queryBdcZs(@PathVariable(value = "zsid") String zsid) {
        return bdcZsService.queryBdcZs(zsid);
    }

    /**
     * @param zsids 证书ids
     * @return BdcZsDO 不动产权证
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 获取不动产权证书集合
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据证书ids查询证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "string", paramType = "path")})
    @Override
    public List<BdcZsDO> queryZsByZsids(@PathVariable(value = "zsids") String zsids) {
        return bdcZsService.queryBdcZss(zsids);
    }

    /**
     * @param zsid 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前证书相关的项目信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取当前证书相关的项目信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "string", paramType = "path")})
    @Override
    public List<BdcXmDO> queryZsXmByZsid(@PathVariable(value = "zsid") String zsid) {
        return bdcZsService.queryZsXmByZsid(zsid);
    }

    /**
     * @param xmid 项目id
     * @return List<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目id查询证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "path")})
    public List<BdcZsDO> queryBdcZsByXmid(@PathVariable("xmid") String xmid) {
        return bdcZsService.queryBdcZsByXmid(xmid);
    }

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据封装的证书查询对象查询证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "证书查询对象", dataType = "BdcZsQO", paramType = "body")})
    @Override
    public List<BdcZsDO> listBdcZs(@RequestBody BdcZsQO bdcZsQO) {
        if (!CheckParameter.checkAnyParameter(bdcZsQO)) {
            throw new MissingArgumentException(messageProvider.getMessage("Constants.MESSAGE_NOPARAMETER") + JSONObject.toJSONString(bdcZsQO));
        }
        return bdcZsService.listBdcZs(bdcZsQO);
    }

    /**
     * @param bdcZsQO 证书查询对象
     * @return Integer 查询到的证书数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据条件查询证书，返回查询到的结果集
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询证书的数量")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "证书查询对象", dataType = "BdcZsQO", paramType = "body")})
    @Override
    public Integer countBdcZs(@RequestBody BdcZsQO bdcZsQO) {
        return bdcZsService.countBdcZs(bdcZsQO);
    }


    /**
     * @param bdcZsQO 证书查询对象
     * @param page    分页查询页数
     * @param size    分页查询行数
     * @param sort    分页查询排序
     * @return Page<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书分页
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "size", value = "一页显示几条", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "第几页", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @Override
    public Page<BdcZsVO> bdcZsByPageJson(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcZsQO bdcZsQO) {
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(page, size, sort);
        if (bdcZsQO != null) {
            // 根据不同的访问资源，设置要查询权属状态
            bdcZsService.renderZsQszt(bdcZsQO);
            String json = JSONObject.toJSONString(bdcZsQO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        }
        return repo.selectPaging("listBdcZsByPage", map, pageable);
    }

    /**
     * @param bdcZsQO 查询条件对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return　 List<String>　zsid列表
     * @description 依据条件查询zsid
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据条件查询zsid")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "查询对象", required = true, dataType = "BdcZsQO", paramType = "body")})
    @Override
    public List<String> queryZsid(@RequestBody BdcZsQO bdcZsQO) {
        return bdcZsService.queryZsid(bdcZsQO);
    }

    /**
     * @param gzlslid 工作流实例
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @return　 List<String>　zsid列表
     * @description 查询当前流程所有的zsid
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询当前流程所有的zsid")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    @Override
    public List<String> queryGzlZsid(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcZsService.queryGzlZsid(gzlslid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid
     * @return List<String>  zsid列表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前工作流所有的证书类型
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询当前流程所有的证书类型", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")})
    @Override
    public List<String> queryGzlZslx(@RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid) {
        return bdcZsService.queryGzlZslx(gzlslid, xmid);
    }

    /**
     * @param bdcZsQO 证书查询QO
     * @return BdcZsQsztDTO 证书状态DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的项目权属状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询证书的项目权属状态", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "证书查询QO", required = true, dataType = "BdcZsQO", paramType = "body")})
    @Override
    public List<BdcZsQsztDTO> queryBdcZsQszt(@RequestBody BdcZsQO bdcZsQO) {
        return bdcZsService.queryBdcZsQszt(bdcZsQO);
    }

    /**
     * @param zsidList 需要更新的证书IDList
     * @param dyzt     打印状态（对应字典表 是否字典表）
     * @return 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书的打印状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新证书的打印状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsidList", value = "证书IDList", required = true, dataType = "BdcZsQO", paramType = "body"),
            @ApiImplicitParam(name = "dyzt", value = "打印状态", required = true, dataType = "boolean", paramType = "path")})
    @Override
    public int updateBdcZsDyzt(@RequestBody List<String> zsidList, @PathVariable(value = "dyzt") Integer dyzt) {
        return bdcZsService.updateBdcZsDyzt(zsidList, dyzt);
    }

    /**
     * 查询原项目的证书信息
     *
     * @param gzlslid
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: gzlslid 工作流实例ID
     * @param: qllx    权利类型
     * @return: 原项目证书信息集合
     */
    @Override
    public List<BdcZsDO> queryYxmBdcqzhByGzlslid(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "qllx", required = false) Integer qllx) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺失工作流实例ID参数。");
        }
        BdcZsQO param = new BdcZsQO();
        param.setGzlslid(gzlslid);
        param.setQllx(qllx);
        return this.bdcZsService.listYxmZs(param);
    }

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 原项目的证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询原项目的证书信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("条件查询原项目的证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZsQO", value = "证书查询QO", required = true, dataType = "BdcZsQO", paramType = "body")})
    public List<BdcZsDO> queryYxmZs(@RequestBody BdcZsQO bdcZsQO) {
        return bdcZsService.listYxmZs(bdcZsQO);
    }

    /**
     * @param bdcZsDO
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 修改证书信息（权利其他状况和附记内容）
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @ApiOperation(value = "更新证书信息")
    @Override
    public int updateBdcZs(@RequestBody BdcZsDO bdcZsDO) {
        return bdcZsService.updateBdcZs(bdcZsDO);
    }

    /**
     * @param xmid 项目ID
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成单个项目不动产权证书（明）号
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成单个项目不动产权证书（明）号")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    @Override
    public List<BdcBdcqzhDTO> generateBdcqzh(@PathVariable("xmid") String xmid) {
        return bdcBdcqzhService.generateBdcqzh(xmid);
    }

    /**
     * @param processInsId 流程实例ID
     * @return {List} 不动产权证号信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成流程对应的不动产权证书（明）号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成流程对应的不动产权证书（明）号")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "流程实例ID", required = true, dataType = "String", paramType = "query")})
    public void generateBdcqzhOfPro(@RequestParam("processInsId") String processInsId) {
        bdcBdcqzhService.generateBdcqzhOfPro(processInsId);
    }

    /**
     * 获取保存的redis中的zsids
     *
     * @param key
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取保存的redis中的xmids")
    @ApiImplicitParam(name = "key", value = "redis key", required = true, paramType = "body")
    public List<String> getXmidsByKey(String key) {
        String xmidStr = redisUtils.getStringValue(key);
        if (StringUtils.isNotEmpty(xmidStr)) {
            List<String> listXmid = Arrays.asList(StringUtils.split(xmidStr, CommonConstantUtils.ZF_YW_DH));
            return listXmid;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return {List} 证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询原项目证书信息集合，证书需要按照下一手项目ID排序
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询上一手证书和当前项目关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "qllx", value = "权利类型", required = true, dataType = "Integer", paramType = "param")
    })
    public List<BdcZsDO> queryYxmZsSortByXmid(@PathVariable("gzlslid") String gzlslid, @RequestParam(name = "qllx", required = false) Integer qllx) {
        return bdcZsService.queryYxmZsSortByXmid(gzlslid, qllx);
    }

    /**
     * 查询未办结的缮证人名称为指定名称的项目 </br>
     * 针对需求 42397 提供的补偿接口服务
     *
     * @param szrid 缮证人 id
     * @return {List} 证书项目相关信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询未办结的缮证人名称为指定名称的项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "szrid", value = "缮证人 id", required = true, dataType = "String", paramType = "query")
    })
    public List<BdcZsXmDTO> listWbjywxx(@RequestParam("szrid") String szrid) {
        return bdcZsService.listWbjywxx(szrid);
    }

    /**
     * 平台办结后执行更新事件 </br>
     * 针对需求 42397 提供的补偿接口服务 </br>
     * <p>
     * 1. 更新发证时间
     *
     * @param bdcZsXmDTOS 证书项目集合
     * @return {int} 更新条数
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("平台办结后执行更新事件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "szrid", value = "缮证人 id", required = true, dataType = "String", paramType = "query")
    })
    public int updateWbjxm(@RequestBody List<BdcZsXmDTO> bdcZsXmDTOS) {
        return bdcZsService.updateWbjxm(bdcZsXmDTOS);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("处理证书共有情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcZsDO", value = "证书信息", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zsyl", value = "证书预览", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "query")
    })
    public String dealZsGyqk(@RequestBody BdcZsDO bdcZsDO, @RequestParam("zsyl") boolean zsyl, @RequestParam(value = "xmid", required = false) String xmid) {
        return bdcZsService.dealZsGyqk(bdcZsDO, zsyl, xmid);

    }

    /**
     * 获取不动产单元号关联的锁定证书信息
     *
     * @param bdcZssdQO 查询实体
     * @return java.util.List<BdcZssdDO> 证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取不动产单元号关联的锁定证书信息")
    @ApiImplicitParam(name = "bdcZssdQO", value = "查询实体", required = true, dataType = "BdcZssdDO", paramType = "body")
    public List<BdcZssdDO> listBdcZssdxx(@RequestBody BdcZssdQO bdcZssdQO) {
        return bdcZsService.listBdcZssdxx(bdcZssdQO);
    }

    /**
     * 获取证书信息，盐城自助打证机调用
     *
     * @param bdcZsQO
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取证书信息，盐城自助打证机调用")
    @ApiImplicitParam(name = "bdcZsQO", value = "查询实体", required = true, dataType = "BdcZsDO", paramType = "body")
    public CommonResponse<List<Map<String, Object>>> queryBdcZsListForZzdzj(@RequestBody BdcZsQO bdcZsQO) {

        //39206 【盐城】自助机查询证书信息接口调整-针对外网受理 增加sply，queryBj为1 时，sply必须有值
        if (CommonConstantUtils.QUERYBJ.equals(bdcZsQO.getQueryBj())) {
            if (StringUtils.isBlank(bdcZsQO.getSply())){
                return CommonResponse.fail(CommonResponse.ERROR_CODE_WITHOUT_DATA,"查询已办结数据时，sply不可为空！");
            }
        }
        List<Map<String, Object>> listBdcZsForZzdzj = bdcZsService.listBdcZsForZzdzj(bdcZsQO);
        if (CollectionUtils.isNotEmpty(listBdcZsForZzdzj)) {
            return CommonResponse.ok(listBdcZsForZzdzj);
        } else {
            return CommonResponse.fail("9999", "未查询到相关数据");
        }
    }

    /**
     * 拆除登记回写证书附记
     *
     * @param processInsId
     */
    @Override
    public void updateZsFj(String processInsId) {
        bdcZsService.updateZsFj(processInsId);
    }

    @Override
    public BdcZsVO initJyZsxx(@RequestBody BdcZsQO bdcZsQO) throws Exception{
        return bdcZsService.initJyZsxx(bdcZsQO);

    }

    /**
     * 添加项目和已有证书的关联关系
     *
     * @param zsid
     * @param xmid
     */
    @Override
    public void generateXmZsConnection(@RequestParam("zsid") String zsid, @RequestParam("xmid") String xmid) {
        bdcZsService.addXmZsConnection(zsid,xmid);
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //根据身份证号查询不动产登记信息
     * @Date 2022/5/12 16:16
     **/
    @Override
    public List<BdcZsXmAndQlrDTO> listDjxx(@RequestBody BdcZsXmAndQlrQO qo) {
        return bdcZsService.listDjxx(qo);
    }

    /**
     * 查询证明关联的产权证书信息
     * @param zsid 证书ID
     * @return {List} 证书信息
     * @Author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     **/
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询证明关联的产权证书信息")
    @ApiImplicitParam(name = "zsid", value = "证书ID", required = true, dataType = "String", paramType = "query")
    public List<BdcZsDO> listBdcZsByZmid(@RequestParam("zsid")String zsid) {
        return bdcZsService.listBdcZsByZmid(zsid);
    }

    /**
     * 更新注销流程的证书信息
     *
     * @param processInsId
     * @throws Exception
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新注销流程的证书信息")
    @ApiImplicitParam(name = "processInsId", value = "工作流ID", required = true, dataType = "String", paramType = "query")
    public void gxzhzt(@RequestParam(value = "processInsId") String processInsId) {
        bdcZsService.gxzhzt(processInsId);
    }

    /**
     * @param zsidList 证书IDList
     * @return List<BdcZsqdDO> 项目信息
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 按照zsid获取当前证书清单
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "按照zsid获取当前证书清单", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "zsidList", value = "证书IDList", dataType = "List", paramType = "body")})
    @Override
    public List<BdcZsqdVO> queryZsQdByZsid(@RequestBody  List<String> zsidList,@RequestParam("gzlslid") String gzlslid) {
        return bdcZsService.queryZsQdByZsid(zsidList,gzlslid);
    }
}
