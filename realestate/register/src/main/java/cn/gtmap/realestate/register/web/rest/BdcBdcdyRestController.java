package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.register.*;
import cn.gtmap.realestate.common.core.enums.BdcDyabgTypeEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDysdQO;
import cn.gtmap.realestate.common.core.qo.register.BdcBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCqQO;
import cn.gtmap.realestate.common.core.qo.register.BdcHbBdcdyQO;
import cn.gtmap.realestate.common.core.qo.register.BdcLsgxQO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcBdcdyRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcBdcdyVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDyawqdVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcJfVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.Object2MapUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.core.service.BdcdySdService;
import cn.gtmap.realestate.register.service.BdcBdcdyService;
import cn.gtmap.realestate.register.service.BdcHbBdcdyService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/2/21
 * @description 不动产单元服务处理Controller
 */
@RestController
@Api(tags = "不动产单元服务接口")
public class BdcBdcdyRestController extends BaseController implements BdcBdcdyRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcBdcdyRestController.class);

    /**
     * 南通档案调用地址
     */
    @Value("${url.dady-url:}")
    private String dadyUrl;
    @Value("${url.td-dady-url:}")
    private String tdDadyUrl;
    @Value("${url.kfq-dady-url:}")
    private String kfqDadyUrl;
    @Value("${config.lkdw:}")
    private String lkdw;

    /**
     * 南通经济开发区区划编码
     */
    @Value("${nantong.jjkfqRegionCode:}")
    private String nantongJjkfqRegionCode;


    /**
     * 海门档案调用地址
     */
    @Value("${url.haimen.dady-url:}")
    private String hmDadyUrl;

    /**
     * 批量页面不动产单元信息排序方式
     */
    @Value("#{'${dyxx.tspx.gzldyid:}'.split(',')}")
    private List<String> tspxGzldyid;

    @Value("${dyxx.tspx.pxfs:fjh,bdcdyh}")
    private String tspxPxfs;


    /**
     * 抵押物清单页面不动产单元显示顺序需要和受理一致
     */
    @Value("${bdcdyxssx.dyawqd:false}")
    private Boolean bdcdyxssx;

    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcdySdService bdcdySdService;
    @Autowired
    private BdcXmxxService bdcXmxxService;
    @Autowired
    private BdcBdcdyService bdcBdcdyService;
    @Autowired
    private BdcHbBdcdyService bdcHbBdcdyService;


    /**
     * @param pageable         分页参数
     * @param bdcLsgxParamJson 查询参数
     * @return {Page} 项目列表
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询不动产单元号对应的项目登记历史关系
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询不动产单元号对应的项目登记历史关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcLsgxParamJson", value = "历史关系查询参数JSON", required = false, paramType = "query")})
    public Page<BdcLsgxXmDTO> listBdcdyLsgx(Pageable pageable,
                                            @RequestParam(name = "bdcLsgxParamJson", required = true) String bdcLsgxParamJson) {
        return bdcBdcdyService.listBdcdyLsgx(pageable, JSON.parseObject(bdcLsgxParamJson, BdcLsgxQO.class));
    }

    /**
     * @param page    页码
     * @param size    每页的数据量
     * @param sort    排序
     * @param bdcXmDO
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询发证记录列表所需的项目信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "size", value = "一页显示几条", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "第几页", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @Override
    public Page<BdcXmDO> listXmByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO) {
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(page, size, sort);
        if (bdcXmDO != null) {
            String json = JSONObject.toJSONString(bdcXmDO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        }
        return repo.selectPaging("listXmByPage", map, pageable);
    }

    /**
     * @param page       页码
     * @param size       每页的数据量
     * @param sort       排序
     * @param bdcBdcdyQO
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询不动产单元信息
     */
    @Override
    public Page<BdcBdcdyVO> listBdcdyByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcBdcdyQO bdcBdcdyQO) {
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(page, size, sort);
        if (bdcBdcdyQO != null) {
            String json = JSONObject.toJSONString(bdcBdcdyQO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        }
        if (CollectionUtils.isNotEmpty(tspxGzldyid) && StringUtils.isNotBlank(tspxGzldyid.get(0)) && StringUtils.isNotBlank(tspxPxfs)) {
            map.put("sortParameter", tspxPxfs);
        }
        return repo.selectPaging("listBdcdyByPage", map, pageable);
    }

    /**
     * @param pageable
     * @param bdcBdcdyQOJson
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: pageable 分页参数
     * @param: bdcBdcdyQOJson 不动产单元查询实体类JSON数据
     * @return: Page<BdcBdcdyVO> 分页的不动产单元视图实体类
     * @description 分页查询不动产单元信息
     */
    @Override
    public Page<BdcBdcdyVO> listBdcdyByPage(Pageable pageable, @RequestParam(name = "bdcBdcdyQOJson", required = false) String bdcBdcdyQOJson) {
        Map<String, Object> map = new HashMap();
        if (StringUtils.isNotBlank(bdcBdcdyQOJson)) {
            map = pageableSort(pageable, JSONObject.parseObject(bdcBdcdyQOJson, HashMap.class));
        }
        return repo.selectPaging("listBdcdyByPage", map, pageable);
    }

    /**
     * @param page 页码
     * @param size 每页的数据量
     * @param sort 排序
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 分页查询流程中的不动产单元号和坐落（去重）
     */
    @Override
    public Page<BdcBdcdyVO> listDistinctBdcdyByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO) {
        if (bdcXmDO == null || StringUtils.isBlank(bdcXmDO.getGzlslid())) {
            throw new MissingArgumentException("gzlslid 不为空！");
        }
        Pageable pageable = new PageRequest(page, size, sort);
        String json = JSONObject.toJSONString(bdcXmDO);
        Map<String, Object> map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        return repo.selectPaging("listDistinctBdcdyByPage", map, pageable);
    }

    /**
     * @param bdcBdcdyQO 不动产单元查询实体
     * @return List<BdcBdcdyVO> 不动产单元信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元信息
     */
    @Override
    public List<BdcBdcdyVO> queryBdcdyList(@RequestBody BdcBdcdyQO bdcBdcdyQO) {
        String json = JSONObject.toJSONString(bdcBdcdyQO);
        Map<String, Object> map = JSONObject.parseObject(json, HashMap.class);
        return bdcBdcdyService.queryBdcdyList(map);
    }

    /**
     * @param page    页码
     * @param size    每页的数据量
     * @param sort    排序
     * @param bdcXmDO
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询抵押物清单信息
     */
    @Override
    public Page<BdcDyawqdVO> listDyawqdByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO) {
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(page, size, sort);
        if (bdcXmDO != null) {
            String json = JSONObject.toJSONString(bdcXmDO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
            //1. 传入定作物特征码
            String dzwtzm = bdcXmDO.getBdcdyh().substring(19, 20);
            map.put("dzwtzm", dzwtzm);

            // 设置是否需要和受理展示顺序一致配置项
            map.put("bdcdyxssx", bdcdyxssx);
        }

        if (map.containsKey("qllx") && CommonConstantUtils.QLLX_YG_DM == Integer.parseInt(map.get("qllx").toString())) {
            return repo.selectPaging("listYgDyawqdByPage", map, pageable);
        } else {
            return repo.selectPaging("listDyawqdByPage", map, pageable);
        }

    }

    @Override
    public Page<BdcJfVO> listJfqdByPage(Pageable pageable, @RequestParam(name = "gzlslid") String gzlslid) {
        Map map = Maps.newHashMapWithExpectedSize(1);
        map.put("gzlslid", gzlslid);
        return repo.selectPaging("listJfqdByPage", map, pageable);
    }

    /**
     * @param page    页码
     * @param size    每页的数据量
     * @param sort    排序
     * @param bdcXmDO
     * @param dyabg
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询原抵押物清单（用于注销流程的展示查询）
     */
    @Override
    public Page<BdcDyawqdVO> listYxmdyawqdByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO, @RequestParam(name = "dyabg", required = false) Integer dyabg) {
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(page, size, sort);
        if (bdcXmDO != null) {
            String json = JSONObject.toJSONString(bdcXmDO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));

            // 设置是否需要和受理展示顺序一致配置项
            map.put("bdcdyxssx", bdcdyxssx);
        }

        if (map.containsKey("qllx") && CommonConstantUtils.QLLX_YG_DM == Integer.parseInt(map.get("qllx").toString())) {
            return repo.selectPaging("listYgYdyawqdByPage", map, pageable);
        } else {
            // 抵押变更，部分注销流程参数组织
            if (dyabg != null && BdcDyabgTypeEnum.DYABG_TYPE1.getDm().equals(dyabg)) {
                map.put(Constants.YXM_QSZT, CommonConstantUtils.QSZT_VALID);
                return repo.selectPaging("queryBgYxmDyawqdByPage", map, pageable);
            } else {
                return repo.selectPaging("listYxmDyawqdByPage", map, pageable);
            }
        }

    }

    /**
     * @param page    页码
     * @param size    每页的数据量
     * @param sort    排序
     * @param bdcXmDO
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询抵押注销证明信息
     */
    @Override
    public Page<BdcXmDO> listDyaZxByPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmDO bdcXmDO) {
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(page, size, sort);
        if (bdcXmDO != null) {
            String json = JSONObject.toJSONString(bdcXmDO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        }
        return repo.selectPaging("listDyaZxByPage", map, pageable);
    }

    /**
     * @param bdcXmQO 查询参数
     * @return List<BdcDyaqDO> 抵押信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程的原抵押信息
     */
    @Override
    public List<BdcDyaqDO> queryYdyaxx(@RequestBody BdcXmQO bdcXmQO) {
        return bdcBdcdyService.queryYdyaxx(bdcXmQO);
    }

    /**
     * @param bdcXmDO 项目信息
     * @return List<BdcDyawqdVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询抵押物清单
     */
    @Override
    public List<BdcDyawqdVO> queryDyawqd(@RequestBody BdcXmDO bdcXmDO) {
        String json = JSONObject.toJSONString(bdcXmDO);
        Map<String, Object> map = JSONObject.parseObject(json, HashMap.class);
        // 设置是否需要和受理展示顺序一致配置项
        map.put("bdcdyxssx", bdcdyxssx);
        return bdcBdcdyService.queryDyawqd(map);
    }

    /**
     * @param bdcXmDO 项目信息
     * @return List<BdcDyawqdVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原抵押物清单信息
     */
    @Override
    public List<BdcDyawqdVO> queryYxmDyawqd(@RequestBody BdcXmDO bdcXmDO) {
        String json = JSONObject.toJSONString(bdcXmDO);
        Map<String, Object> map = JSONObject.parseObject(json, HashMap.class);
        // 设置是否需要和受理展示顺序一致配置项
        map.put("bdcdyxssx", bdcdyxssx);
        return bdcBdcdyService.queryYxmDyawqd(map);
    }

    /**
     * @param bdcXmDO 项目信息
     * @return List<BdcDyawqdVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 抵押变更类型1, 部分抵押变更，查询抵押物清单
     */
    @Override
    public List<BdcDyawqdVO> queryBgYxmDyawqd(@RequestBody BdcXmDO bdcXmDO) {
        Map<String, Object> map = Object2MapUtils.object2MapExceptNull(bdcXmDO);
        // 只查询原项目的现势的信息
        map.put(Constants.YXM_QSZT, CommonConstantUtils.QSZT_VALID);
        return bdcBdcdyService.queryBgYxmDyawqd(map);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {Boolean} 存在查封：true，不存在查封：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断不动产单元号对应的权利人是否存在查封
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "判断不动产单元号对应的权利人是否存在查封")
    @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "string", paramType = "path")
    public Boolean isBdcdyhQlrCf(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcBdcdyService.isBdcdyhQlrCf(bdcdyh,qjgldm);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {Boolean} 存在异议：true，不存在异议：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 判断不动产单元号对应的权利人是否存在异议
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "判断不动产单元号对应的权利人是否存在异议")
    @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "string", paramType = "path")
    public Boolean isBdcdyhQlrYy(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcBdcdyService.isBdcdyhQlrYy(bdcdyh,qjgldm);
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return {List} 锁定则返回提示信息；未锁定则返回空
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 验证所在宗地不动产单元是否锁定
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证所在宗地不动产单元是否锁定")
    @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "string", paramType = "path")
    public List<String> isZdBdcdySd(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcBdcdyService.isZdBdcdySd(bdcdyh,qjgldm);
    }

    /**
     * @param xmid 项目ID
     * @return {Boolean} 验证通过：true ；不通过：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 验证当前项目权利人权利比例之和是否为1
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证当前项目权利人权利比例之和是否为1")
    @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "path")
    public Boolean checkQlrQlbl(@PathVariable("xmid") String xmid) {
        return bdcBdcdyService.checkQlrQlbl(xmid);
    }

    /**
     * @param gzlslid   工作流实例ID
     * @param dylx      打印类型
     * @param bdcUrlDTO
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 打印xml
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取不动产单元表单打印XMl")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流示例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "打印类型", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "bdcUrlDTO", value = "需要的url参数对象", required = true, dataType = "BdcUrlDTO", paramType = "body")})
    @Override
    public String bdcdyPrintXml(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "dylx") String dylx, @RequestBody BdcUrlDTO bdcUrlDTO) {
        BdcPrintDTO bdcPrintDTO = new BdcPrintDTO();
        bdcPrintDTO.setDylx(dylx);
        bdcPrintDTO.setGzlslid(gzlslid);
        bdcPrintDTO.setBdcUrlDTO(bdcUrlDTO);

        return bdcBdcdyService.bdcdyPrintXml(bdcPrintDTO);
    }

    /**
     * @param bdcPrintDTO 打印参数
     * @return String  获取打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 打印xml
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取不动产单元表单打印XMl")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPrintDTO", value = "不动产打印DTO", required = true, dataType = "BdcPrintDTO", paramType = "body")})
    @Override
    public String bdcdyPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcBdcdyService.bdcdyPrintXml(bdcPrintDTO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取不动产单元表单打印XMl批量")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPrintDTO", value = "不动产打印DTO", required = true, dataType = "BdcPrintDTO", paramType = "body")})
    @Override
    public String bdcdyPrintXmlPl(@RequestBody BdcPrintDTO bdcPrintDTO) {
        return bdcBdcdyService.bdcdyPrintXmlPl(bdcPrintDTO);
    }

    /**
     * 查询项目来源信息
     *
     * @param xmid
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询项目来源信息")
    @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "path")
    public Map queryXmly(@RequestParam("xmid") String xmid) {
        List list = bdcBdcdyService.queryXmly(xmid);
        Map map = new HashMap();
        String userName = userManagerUtils.getCurrentUserName();
        map.put("userId", userManagerUtils.getUserByName(userName).getId());
        map.put("userName", userManagerUtils.getUserByName(userName).getUsername());

        map.put("dadyUrl", dadyUrl);
        map.put("tdDadyUrl", tdDadyUrl);
        // 新增海门档案调用地址  added by zhuyong 20200616
        map.put("hmDadyUrl", hmDadyUrl);

        /**
         * 【32413】 【南通】档案系统需求
         * 根据当前用户的部门判断跳转的地址。例如，市区的用户点击档案调用后调取市区的档案数据，开发区的用户点击档案调用后调取开发区的档案数据
         * 南通经济开发区对应行政区划代码:320691
         */
        String regionCode = userManagerUtils.getRegionCodeByUserName(userManagerUtils.getUserByName(userName).getUsername());
        LOGGER.info("===>currentUserName:{},userName:{},regionCode:{},jjkfqRegionCode:{}", userName, userManagerUtils.getUserByName(userName).getUsername(), regionCode, nantongJjkfqRegionCode);

        boolean kfqFlag = false;
        if (StringUtils.isNotEmpty(nantongJjkfqRegionCode)) {
            kfqFlag = Arrays.asList(nantongJjkfqRegionCode.split(",")).contains(regionCode);
        }


        map.put("kfqDadyUrl", kfqDadyUrl);
        map.put("kfqFlag", kfqFlag);

        if (list.size() > 0) {
            map.putAll((Map) list.get(0));
        }
        return map;
    }

    /**
     * 查询产权大证信息
     *
     * @param xmid
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询项目来源信息")
    @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "path")
    public List<BdcLsgxXmDTO> cqdz(@RequestParam("xmid") String xmid) {
        return bdcBdcdyService.queryCqdz(xmid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "计算房地产权面积之和")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public BdcdySumDTO calculatedFdcqMj(@PathVariable("gzlslid") String gzlslid, @RequestParam(name = "sfyql") Boolean sfyql, @RequestParam(name = "djxl", required = false) String djxl) {
        return bdcBdcdyService.calculatedFdcqMj(gzlslid, sfyql, djxl);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "计算抵押权面积之和")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public BdcdySumDTO calculatedDyaqMj(@PathVariable("gzlslid") String gzlslid, @RequestParam(name = "sfyql") Boolean sfyql, @RequestBody List<String> xmidList) {
        return bdcBdcdyService.calculatedDyaqMj(gzlslid, sfyql, xmidList);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "计算抵押权面积之和")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public List<BdcdySumDTO> calculatedDyaqMjGyBdclx(@PathVariable("gzlslid") String gzlslid, @RequestParam(name =
            "sfscql") Boolean sfscql) {
        return bdcBdcdyService.calculatedDyaqMjGyBdclx(gzlslid, sfscql);

    }


    /**
     * @param bdcdyh
     * @param sdzt
     * @return 锁定数据
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取单元号的锁定数据
     */
    @Override
    public List<BdcDysdDO> queryBdcDysd(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(value = "sdzt", required = false) Integer sdzt) {
        return bdcdySdService.queryBdcDysd(bdcdyh, sdzt, null);
    }

    /**
     * @param gzlslid   工作流实例ID
     * @param bdcDyaqDO 抵押信息
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原抵押信息的抵押注销申请人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新原抵押信息的抵押注销申请人")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public int saveYdyaxxZxsqrPl(@PathVariable(value = "gzlslid") String gzlslid, @RequestBody BdcDyaqDO bdcDyaqDO) {
        return bdcBdcdyService.saveYdyaxxZxsqr(gzlslid, bdcDyaqDO);
    }

    /**
     * 查询当前项目上一手的产权
     *
     * @param bdcLsgxQO 历史关系查询对象
     * @return 不动产单元列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新原抵押信息的抵押注销申请人")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public List<BdcLsgxXmDTO> listBdcdyLsgxLastCq(@RequestBody BdcLsgxQO bdcLsgxQO) {
        return bdcBdcdyService.listBdcdyLsgxLastCq(bdcLsgxQO);
    }

    /**
     * 通过上一手的证书信息查询现不动产项目信息
     * <p>不动产项目表，不动产历史关系表，不动产项目证书关系表三表关联查询项目信息</p>
     *
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [gzlslid, zsid] 工作流实例ID， 证书ID（上一手项目的证书ID）
     * @return: List<BdcXmDO> 不动产项目信息集合
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新原抵押信息的抵押注销申请人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "string", required = false, paramType = "query"),
            @ApiImplicitParam(name = "qllx", value = "权利类型", dataType = "Integer", required = false, paramType = "query")
    })
    public List<BdcXmDO> queryBdcXmByGzlslidAndZsid(@RequestParam(value = "gzlslid") String gzlslid,
                                                    @RequestParam(value = "zsid", required = false) String zsid,
                                                    @RequestParam(value = "qllx", required = false) Integer qllx) {
        return bdcXmxxService.getBdcXmByGzlslidAndZsid(gzlslid, zsid, qllx);
    }

    /**
     * @param page
     * @param size
     * @param sort
     * @param bdcXmQO 项目查询对象
     * @return Page<BdcXmDO> 分页查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前项目和原项目的单元信息，并去重
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取当前项目和原项目的单元信息，并去重")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmQO", value = "项目查询对象", dataType = "BdcXmQO", paramType = "body")})
    public Page<BdcXmXmfbDTO> getXmOrYxmDistinctByDyhPage(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size, @RequestParam(name = "sort", required = false) Sort sort, @RequestBody BdcXmQO bdcXmQO) {
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(page, size, sort);
        if (bdcXmQO != null) {
            String json = JSONObject.toJSONString(bdcXmQO);
            map = pageableSort(pageable, JSONObject.parseObject(json, HashMap.class));
        }
        return repo.selectPaging("listXmOrYxmByPage", map, pageable);
    }

    /**
     * @param bdcXmQO 抵押项目
     * @return 抵押土地面积
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询抵押土地面积：如果是产权，取所有不动产单元 产权的 fttdmj + dytdmj 之和
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询抵押土地面积")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmQO", value = "抵押项目信息", dataType = "BdcXmQO", paramType = "body")})
    public Double getDyaTdmj(@RequestBody BdcXmQO bdcXmQO) {
        return bdcBdcdyService.getDyaTdmj(bdcXmQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据房屋用途统计面积")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcBdcdyQO", value = "单元信息查询对象", dataType = "BdcBdcdyQO", paramType = "body")})
    public List<Map> getSumMjByGhyt(@RequestBody BdcBdcdyQO bdcBdcdyQO) {
        return bdcBdcdyService.getSumMjByGhyt(bdcBdcdyQO);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询预告
     */
    @Override
    public List listBdcYgByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcBdcdyService.listBdcYgByXmid(xmid);
    }

    /**
     * @param bdcXmQO 查询条件
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当前不动产单元号是否办理过抵押
     * @date : 2020/8/3 11:24
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询当前不动产单元号是否办理过抵押")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmQO", value = "查询条件", dataType = "BdcXmQO", paramType = "body")})
    public List<BdcDyaqDO> listExistBdcDyaq(@RequestBody BdcXmQO bdcXmQO) {
        return bdcBdcdyService.listExistDyaq(bdcXmQO);
    }

    /**
     * @param pageable         分页参数
     * @param hbBdcdyParamJson 查询参数
     * @return {Page} 产权信息列表
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description （海门）查询待合并不动产单元信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("（海门）查询待合并不动产单元信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "hbBdcdyParamJson", value = "查询参数", required = false, paramType = "query")})
    public Page<BdcHbBdcdyDTO> queryDhbBdcdyXx(Pageable pageable, @RequestParam(name = "hbBdcdyParamJson") String hbBdcdyParamJson) {
        return bdcHbBdcdyService.queryDhbBdcdyXx(pageable, JSON.parseObject(hbBdcdyParamJson, BdcHbBdcdyQO.class));
    }

    /**
     * @param bdcdyDTOList 待合并不动产单元信息
     * @param ppBdcdyh     匹配到的新记录待使用不动产单元号
     * @return {BdcHbBdcdyDTO} 新合并生成的不动产单元记录
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 执行不动产单元合并操作
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("执行不动产单元合并操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyDTOList", value = "待合并不动产单元信息", required = true, paramType = "body"),
            @ApiImplicitParam(name = "ppBdcdyh", value = "匹配到的新记录待使用不动产单元号", required = false, paramType = "path")})
    public BdcHbBdcdyDTO hbBdcdy(@RequestBody List<BdcHbBdcdyDTO> bdcdyDTOList, @PathVariable(value = "ppBdcdyh") String ppBdcdyh) {
        return bdcHbBdcdyService.hbBdcdy(bdcdyDTOList, ppBdcdyh);
    }

    /**
     * @param bdcdyh
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询预告
     */
    @Override
    public List listYgByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh) {
        return bdcBdcdyService.listBdcYgByBdcdyh(bdcdyh);
    }

    /**
     * @param bdcdyhList 不动产单元号集合
     * @return {List} 房屋性质
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询抵押物清单列表单元的房屋信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询抵押物清单列表单元的房屋信息")
    @ApiImplicitParam(name = "bdcdyhList", value = "不动产单元号集合", required = true, paramType = "body")
    public List<BdcDyawqdVO> listBdcDyawqdFwxx(@RequestBody List<String> bdcdyhList) {
        return bdcBdcdyService.listBdcDyawqdFwxx(bdcdyhList);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取落款单位登记机构配置")
    public String getLkdwConfig() {
        return lkdw;
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "计算建设用地使用权面积之和")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
    public BdcdySumDTO calculatedJsydsyqMj(@PathVariable("gzlslid") String gzlslid, @RequestParam(name = "sfyql") Boolean sfyql, @RequestParam(name = "djxl", required = false) String djxl) {
        return bdcBdcdyService.calculatedJsydsyqMj(gzlslid, sfyql, djxl);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询房地产权项目信息")
    @ApiImplicitParam(name = "bdcBdcdyQO", value = "bdcBdcdyQO", dataType = "BdcBdcdyQO", paramType = "body")
    public List<Map> listBdcFdcqXmxx(@RequestBody BdcCqQO bdcCqQO) {
        return bdcBdcdyService.listBdcFdcqXmxx(bdcCqQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询房地产权项目信息")
    @ApiImplicitParam(name = "bdcBdcdyQO", value = "bdcBdcdyQO", dataType = "BdcBdcdyQO", paramType = "body")
    public List<Map> listBdcJsydsyqXmxx(@RequestBody BdcCqQO bdcCqQO) {
        return bdcBdcdyService.listBdcJsydsyqXmxx(bdcCqQO);
    }

    /**
     * @param bdcDysdQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询单元锁定信息
     * @date : 2021/8/20 15:21
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询单元锁定信息")
    @ApiImplicitParam(name = "bdcDysdQO", value = "bdcDysdQO", dataType = "BdcDysdQO", paramType = "body")
    public List<BdcDysdDO> listBdcDysd(@RequestBody BdcDysdQO bdcDysdQO) {
        return bdcdySdService.listBdcDySd(bdcDysdQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据抵押权人查询抵押信息")
    @ApiImplicitParam(name = "bdcDyaqQO", value = "bdcDyaqQO", dataType = "BdcDyaQo", paramType = "body")
    public List<BdcDyaqQlrDTO> listBdcDyaqByQlrmc(@RequestBody BdcDyaQo bdcDyaqQO) {
        return bdcBdcdyService.listBdcDyqByQlrxx(bdcDyaqQO);
    }

    @Override
    public List<BdcLsgxXmDTO> listBdccfdjls(@RequestParam(name = "bdcLsgxParamJson", required = true) String bdcLsgxParamJson) {

        return bdcBdcdyService.listBdccfdjls(JSON.parseObject(bdcLsgxParamJson, BdcLsgxQO.class));
    }

    /**
     * @param qxdm
     * @param zdtzm
     * @param dzwtzm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 生成虚拟单元号
     * @date : 2021/12/30 15:39
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("执行不动产单元合并操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "qxdm", value = "区县代码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "zdtzm", value = "宗地特征码", required = true, paramType = "path"),
            @ApiImplicitParam(name = "dzwtzm", value = "定着物特征码", required = true, paramType = "path")})
    public String createXndyh(@PathVariable(value = "qxdm") String qxdm, @PathVariable(value = "zdtzm") String zdtzm, @PathVariable(value = "dzwtzm") String dzwtzm) {
        return bdcBdcdyService.createXndyh(qxdm, zdtzm, dzwtzm);
    }

    /**
     * @param gzlslid
     * @param zdmc
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房地产权使用权结束时间去重后的数据
     * @date : 2022/9/7 19:13
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询房地产权使用权结束时间去重后的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "zdmc", value = "字段名称", required = true, paramType = "query")})
    public TdSyqJssjDTO listDistinctTdsyjssj(@RequestParam("gzlslid") String gzlslid) {
        return bdcBdcdyService.listDistinctTdsyjssj(gzlslid);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成抵押物清单pdf")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPrintDTO", value = "不动产打印DTO", required = true, dataType = "BdcPrintDTO", paramType = "body")})
    @Override
    public String getDywqdPdf(@RequestBody BdcPrintDTO bdcPrintDTO){
        return bdcBdcdyService.getDywqdPdf(bdcPrintDTO);
    }


}
