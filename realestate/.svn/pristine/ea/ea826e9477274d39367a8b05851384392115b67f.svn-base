package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshFwkgDataDTO;
import cn.gtmap.realestate.common.core.dto.etl.BzjdDTO;
import cn.gtmap.realestate.common.core.dto.etl.XscqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.init.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.*;
import cn.gtmap.realestate.common.core.service.rest.init.BdcXmRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcXmFwzkfbVO;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.init.core.service.BdcDjxlDjyyGxService;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.web.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/10/31
 * @description 查询不动产信息接口
 */
@RestController
@Api(tags = "业务项目信息服务接口")
public class BdcXmRestController extends BaseController implements BdcXmRestService {
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    private BdcDjxlDjyyGxService bdcDjxlDjyyGxService;

    /**
     * 通过传入参数返回不动产项目信息
     *
     * @param bdcXmQO
     * @return
     */
    @ApiOperation(value = "查询项目信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXmDO> listBdcXm(@RequestBody BdcXmQO bdcXmQO) {
        return bdcXmService.listBdcXm(bdcXmQO);
    }

    /**
     * 通过传入bdcdyh返回对象的现势产权项目信息
     * @param bdcdyhList 不动产单元号集合
     * @return {List} 不动产项目信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入bdcdyh返回对象的现势产权项目信息
     */
    @ApiOperation(value = "通过传入bdcdyh集合返回对象的现势产权项目信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "bdcdyhList", value = "不动产单元集合", dataType = "List<String>",paramType = "body")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXmDO> listBdcXmXscq(@RequestBody List<String> bdcdyhList) {
        List<BdcXmDO> list=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcdyhList)){
            List<List> lists=ListUtils.subList(bdcdyhList,50);
            if(CollectionUtils.isNotEmpty(lists)){
                BdcXmQO bdcXmQO=new BdcXmQO();
                bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                bdcXmQO.setQllxs(Arrays.asList(CommonConstantUtils.QLLX_FDCQ));
                for(List data:lists){
                    bdcXmQO.setBdcdyhList(data);
                    List<BdcXmDO> results=bdcXmService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(results)){
                        list.addAll(results);
                    }
                }
            }
        }
        return list;
    }

    /**
     * @param bdcXmQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据办结日期 查询XM
     */
    @Override
    @ApiOperation(value = "根据办结日期查询XM")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXmDO> listBdcXmByBjrq(@RequestBody BdcXmQO bdcXmQO) {
        if (!CheckParameter.checkAnyParameter(bdcXmQO,"xmid","qlr","slbh","gzlslid","bjrq","bdcdyh","jyhth","spxtywh","bdcqzh")) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER)+JSONObject.toJSONString(bdcXmQO));
        }
        return bdcXmService.listBdcXmByRq(bdcXmQO);
    }

    /**
     * @param bdcXmDO 不动产项目
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 更新不动产项目信息
     */
    @ApiOperation(value = "更新项目信息")
    @ApiImplicitParam(name = "bdcXmDO", value = "项目对象", dataType = "BdcXmDO", paramType = "body")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int updateBdcXm(@RequestBody BdcXmDO bdcXmDO) {
        return bdcXmService.updateBdcXm(bdcXmDO);
    }

    /**
     * 选择不动产权利页面后台服务
     * @param pageable
     * @param bdcQlQoStr bdcQlQo的json串
     * @return Page<BdcQlPageResponseDTO> 分页查询对象
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 选择不动产权利页面后台服务
     */
    @ApiOperation(value = "选择不动产权证页面的数据服务")
    @LogNote(value = "选择不动产项目", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQlQoStr", value = "参数JSON结构BdcQlQo", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Page<BdcQlPageResponseDTO> bdcQlPageByPageJson(Pageable pageable, @RequestParam(name = "bdcQlQoStr", required = false) String bdcQlQoStr) {
        BdcQlQO bdcQlQO=new BdcQlQO();
        boolean ntHtbaXztz = false;
        if(StringUtils.isNotBlank(bdcQlQoStr)){
            bdcQlQO=JSONObject.parseObject(bdcQlQoStr,BdcQlQO.class);
            // 这种场景是 南通合同备案查询台账 根据条件查出多个合同备案 此时bdcqzh要做批量查询
            if (StringUtils.isNotBlank(bdcQlQO.getBdcqzh()) && StringUtils.isNotBlank(bdcQlQO.getHtbh()) && bdcQlQO.getBdcqzh().contains(",") && bdcQlQO.getHtbh().contains(",")) {
                bdcQlQO.setBdcqzhList(Arrays.asList(bdcQlQO.getBdcqzh().split(",")));
                bdcQlQO.setBdcqzh("");
                ntHtbaXztz = true;
            }
            // 单个bdcqzh和htbh
            if (StringUtils.isNotBlank(bdcQlQO.getHtbh()) && !bdcQlQO.getHtbh().contains(",")) {
                ntHtbaXztz = true;
            }
        }
        Page<BdcQlPageResponseDTO> result = null;
        // 分别持证返回多条项目信息   权利人和产权证号分开返回
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
        LOGGER.info("开始查询产权证数据，当前时间{},loadpage{},查询条件：{}", simpleDateFormat.format(new Date()),JSON.toJSONString(bdcQlQO));
        if (bdcQlQO.isFbczdt()) {
            result = repo.selectPaging("listBdcQlFbczDtByPage", bdcQlQO, pageable);
        } else {
            if (bdcQlQO.getHbcxjg()) {
                result = repo.selectPaging("listBdcQlHbcxjgByPageOrder", bdcQlQO, pageable);
            } else {
                result = repo.selectPaging("listBdcQlByPageOrder", bdcQlQO, pageable);
            }
        }
        LOGGER.info("查询产权证结束时间为{}", simpleDateFormat.format(new Date()));
        // 这种场景是 南通合同备案查询台账 根据条件查出多个合同备案 要把htbh作为展示的条件
        // 我们认定 bdcqzh和htbh的数量和下标是一致 当匹配了bdcqzh 取该位置的htbh作为本条数据的htbh
        if (ntHtbaXztz && CollectionUtils.isNotEmpty(result.getContent())) {
            for (BdcQlPageResponseDTO bdcQlPageResponseDTO : result) {
                if(CollectionUtils.isNotEmpty(bdcQlQO.getBdcqzhList())){
                    // 批量情况
                    for(int i = 0;i<bdcQlQO.getBdcqzhList().size();i++){
                        if(StringUtils.equals(bdcQlQO.getBdcqzhList().get(i),bdcQlPageResponseDTO.getBdcqzh())){
                            bdcQlPageResponseDTO.setHtbh(bdcQlQO.getHtbh().split(",")[i]);
                            break;
                        }
                    }
                }else{
                    // 单个情况
                    if(StringUtils.equals(bdcQlQO.getBdcqzh(),bdcQlPageResponseDTO.getBdcqzh())){
                        bdcQlPageResponseDTO.setHtbh(bdcQlQO.getHtbh());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 选择不动产权利页面后台服务(非分页)
     * @param bdcQlQO bdcQlQo
     * @return List<BdcQlPageResponseDTO> 查询对象集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 选择不动产权利页面后台服务
     */
    @ApiOperation(value = "选择不动产权证页面的数据服务(不分页)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQlQO", value = "请求参数结构",  dataType = "BdcQlQO")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcQlPageResponseDTO> bdcQlList(@RequestBody(required = false) BdcQlQO bdcQlQO) {
        Integer num=repo.selectOne("listBdcQlCount", bdcQlQO);
        if(num>10000){
            throw new AppException("查询数据量过大,请增加查询条件!");
        }
        return repo.selectList("listBdcQlByPageOrder", bdcQlQO);
    }

    /**
     * 选择查封信息页面后台服务(不分页)
     *
     * @param bdcCfQO
     * @return List<BdcCfxxPageResponseDTO> 查询对象集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 选择查封信息页面后台服务
     */
    @ApiOperation(value = "选择查封信息页面的数据服务(不分页)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCfQO", value = "请求参数结构",  dataType = "BdcCfQO")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcCfxxPageResponseDTO> bdcCfxxList(@RequestBody(required = false) BdcCfQO bdcCfQO) {
        Integer num=repo.selectOne("listBdcCfxxCount", bdcCfQO);
        if(num>10000){
            throw new AppException("查询数据量过大,请增加查询条件!");
        }
        return repo.selectList("listBdcCfxxByPage", bdcCfQO);
    }

    /**
     * 选择查封信息页面后台服务
     *
     * @param pageable
     * @param bdcCfQoStr
     * @return Page<BdcCfxxPageResponseDTO> 分页查询对象
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 选择查封信息页面后台服务
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "选择不动产查封", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    @ApiOperation(value = "选择查封信息页面的数据服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCfQoStr", value = "参数JSON结构BdcCfQo", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcCfxxPageResponseDTO> bdcCfxxPageByPageJson(Pageable pageable, @RequestParam(name = "bdcCfQoStr", required = false) String bdcCfQoStr) {
        BdcCfQO bdcCfQO = new BdcCfQO();
        if (StringUtils.isNotBlank(bdcCfQoStr)) {
            bdcCfQO = JSONObject.parseObject(bdcCfQoStr, BdcCfQO.class);
        }
        return repo.selectPaging("listBdcCfxxByPage", bdcCfQO, pageable);
    }

    /**
     * 选择查封信息页面后台服务
     *
     * @param pageable
     * @param bdcCfQoStr
     * @return Page<BdcCfxxPageResponseDTO> 分页查询对象
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 选择查封以及查封的续封信息页面后台服务
     */
    @ApiOperation(value = "选择查封信息页面的数据服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCfQoStr", value = "参数JSON结构BdcCfQo", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Page<BdcCfxxPageResponseDTO> bdcCfXfxxPageByPageJson(Pageable pageable, @RequestParam(name = "bdcCfQoStr", required = false) String bdcCfQoStr) {
        BdcCfQO bdcCfQO = new BdcCfQO();
        if (StringUtils.isNotBlank(bdcCfQoStr)) {
            bdcCfQO = JSONObject.parseObject(bdcCfQoStr, BdcCfQO.class);
        }
        return repo.selectPaging("listBdcCfXfxxByPage", bdcCfQO, pageable);
    }

    @ApiOperation(value = "选择证书锁定信息页面后台服务(不分页)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZssdDO", value = "请求参数结构", dataType = "BdcZssdDO")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcZssdPageResponseDTO> bdcZssdList(@RequestBody(required = false) BdcZssdQO bdcZssdQO) {
        Integer num = repo.selectOne("listBdcZssdCount", bdcZssdQO);
        if (num > 10000) {
            throw new AppException("查询数据量过大,请增加查询条件!");
        }
        return repo.selectList("listBdcZssdByPage", bdcZssdQO);
    }

    @ApiOperation(value = "选择单元锁定信息页面后台服务(不分页)")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZssdDO", value = "请求参数结构", dataType = "BdcZssdDO")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcDysdPageResponseDTO> bdcDysdList(@RequestBody(required = false) BdcDysdQO bdcDysdQO) {
        Integer num = repo.selectOne("listBdcDysdCount", bdcDysdQO);
        if (num > 10000) {
            throw new AppException("查询数据量过大,请增加查询条件!");
        }
        return repo.selectList("listBdcDysdByPage", bdcDysdQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "选择证书锁定", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    @ApiOperation(value = "选择证书锁定信息页面后台服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZssdQOStr", value = "参数JSON结构BdcZssdQO", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcZssdPageResponseDTO> bdcZssdPageByPageJson(Pageable pageable, @RequestParam(name = "bdcZssdQOStr", required = false)  String bdcZssdQOStr) {
        BdcZssdQO bdcZssdQO = new BdcZssdQO();
        if(StringUtils.isNotBlank(bdcZssdQOStr)){
            bdcZssdQO = JSON.parseObject(bdcZssdQOStr, BdcZssdQO.class);
        }
        return repo.selectPaging("listBdcZssdByPage", bdcZssdQO, pageable);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "选择单元锁定", action = LogConstans.LOG_TYPE_XZTZ, custom = LogConstans.LOG_TYPE_XZTZ)
    @ApiOperation(value = "选择单元锁定信息页面后台服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDysdQOStr", value = "参数JSON结构BdcDysdQO", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<BdcDysdPageResponseDTO> bdcDysdPageByPageJson(Pageable pageable, @RequestParam(name = "bdcDysdQOStr", required = false)  String bdcDysdQOStr) {
        BdcDysdQO bdcDysdQO = new BdcDysdQO();
        if(StringUtils.isNotBlank(bdcDysdQOStr)){
            bdcDysdQO = JSON.parseObject(bdcDysdQOStr, BdcDysdQO.class);
        }
        return repo.selectPaging("listBdcDysdByPage", bdcDysdQO, pageable);
    }

    /**
     * @param bdcXmLsgxDOList
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 外联证书接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "外联证书服务")
    public void batchInsertBdcXmLsgx(@RequestBody List<BdcXmLsgxDO> bdcXmLsgxDOList) {
        bdcXmLsgxService.insertBatchBdcXmLsgx(bdcXmLsgxDOList);
    }

    /**
     * 根据项目id获取开关实例数据
     *
     * @param xmid 项目id
     * @return BdcCshFwkgSlDO 实例信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "查询服务开关实例信息")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcCshFwkgSlDO queryFwkgsl(@PathVariable("xmid") String xmid) {
        return bdcXmService.queryCshFwkgSl(xmid);
    }

    /**
     * 根据项目id更新受理房屋开关服务信息
     *
     * @param bdcCshFwkgSlDO 不动产受理房屋开关服务信息
     * @return int           更新状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description
     */
    @ApiOperation(value = "更新受理房屋开关服务信息")
    @ApiImplicitParam(name = "bdcCshFwkgSlDO", value = "初始化服务开关实例表", required = true, dataType = "BdcCshFwkgSlDO", paramType = "body")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int updateCshFwkgSl(@RequestBody BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        return this.bdcXmService.updateCshFwkgSl(bdcCshFwkgSlDO);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmids, sfhz]
     * @return int
     * @description 批量更新是否换证
     */
    @ApiOperation(value = "批量更新是否换证")
    @ApiImplicitParams({@ApiImplicitParam(name = "sfhz", value = "是否换证", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmids", value = "项目ID列表", dataType = "List<String>", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int batchUpdateCshFwkgSlSfhz(@RequestBody List<String> xmids, @RequestParam(name = "sfhz") String sfhz) {
        return this.bdcXmService.batchUpdateCshFwkgSlSfhz(xmids, sfhz);
    }

    @ApiOperation(value = "通过工作流实例ID查询不动产初始化开关服务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcSlCshFwkgDataDTO> queryFwkgslByGzlslid(@RequestParam(name="gzlslid") String gzlslid,
                                                          @RequestParam(name="bdcdyh", required = false) String bdcdyh) {
        return this.bdcXmService.queryCshFwkgSlByGzlslid(gzlslid,bdcdyh);
    }

    /**
     * 获得组合的所有项目信息
     * @param xmid
     * @return List<BdcXmZhxxDTO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获得组合的所有项目信息
     */
    @ApiOperation(value = "获得组合的所有项目信息")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcXmZhxxDTO> queryBdcXmZhxx(@PathVariable("xmid") String xmid) {
        return bdcXmService.queryBdcXmZhxx(xmid,true);
    }

    /**
     * 根据工作流实例ID获取挂接附件的项目  空的话用gzlslid去获取
     * @param gzlslid
     * @return List<BdcXmDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据工作流实例ID获取挂接附件的项目  空的话用gzlslid去获取
     */
    @ApiOperation(value = "根据工作流实例ID获取挂接附件的项目")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcXmDO> queryFileXmList(@PathVariable("gzlslid") String gzlslid) {
        if(StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        List<BdcXmDO> list=bdcXmService.listBdcXm(gzlslid);
        //批量或组合的
        if(CollectionUtils.isNotEmpty(list) && list.size()>1){
            Map<String,String> bdcdyhMap=new HashMap<>();
            for (BdcXmDO bdcXmDO : list) {
                //存在此单元号为组合
                if (bdcdyhMap.containsKey(bdcXmDO.getBdcdyh())) {
                    break;
                } else {
                    bdcdyhMap.put(bdcXmDO.getBdcdyh(), bdcXmDO.getBdcdyh());
                }
            }
            //数量相等为批量流程  清空返回
            if (bdcdyhMap.size() == list.size()) {
                list = Collections.emptyList();
            }
        }else if (CollectionUtils.isEmpty(list)) {
            // 未查询到任何结果
            throw new MissingArgumentException(messageProvider.getMessage("message.noresult"));
        }
        return list;
    }

    /**
     * 根据受理编号和证件号获取项目信息
     * @param slbh
     * @param zjh
     * @return List<BdcXmDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据受理编号和证件号获取项目信息
     */
    @ApiOperation(value = "根据受理编号和证件号获取项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zjh", value = "证件号", dataType = "String", paramType = "path")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<Map> queryBjjdXx(@PathVariable("slbh") String slbh, @PathVariable("zjh") String zjh) {
        if(StringUtils.isBlank(slbh) || StringUtils.isBlank(zjh)){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        Map map=new HashMap();
        map.put("slbh",slbh);
        map.put("zjh",zjh);
        return repo.selectList("listBdcBjjd",map);
    }

    /**
     * 根据不动产权证号和权属状态获取项目信息
     *
     * @param bdcqzh 不动产权证号
     * @param qszt   权属状态
     * @return List<Map>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @ApiOperation(value = "根据不动产权证号和权属状态获取项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcqzh", value = "不动产权证号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qszt", value = "权属状态", dataType = "String", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<Map> queryBdcxmByCqzh(@RequestParam("bdcqzh") String bdcqzh, @RequestParam("qszt") String qszt,@RequestParam("bdcdyh") String bdcdyh) {
        if (StringUtils.isBlank(bdcqzh) || StringUtils.isBlank(qszt) || StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        Map map = new HashMap();
        map.put("bdcqzh", bdcqzh);
        map.put("qszt", qszt);
        map.put("bdcdyh", bdcdyh);
        return repo.selectList("listBdcxmByCqzh", map);
    }

    /**
     * @param bdcDjxlDjyyQO 登记小类登记原因QO
     * @return 登记小类登记原因集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询登记原因登记小类关系,djxl必须匹配,其余参数可匹配或配置为空
     */
    @ApiOperation(value = "查询登记原因登记小类关系,djxl必须匹配,其余参数可匹配或配置为空")
    @ApiImplicitParam(name = "bdcDjxlDjyyQO", value = "登记小类登记原因QO", dataType = "BdcDjxlDjyyQO", paramType = "query")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcDjxlDjyyGxDO> listBdcDjxlDjyyGxWithParam(@RequestBody BdcDjxlDjyyQO bdcDjxlDjyyQO){
        return bdcDjxlDjyyGxService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO);

    }

    /**
     * 根据 gzlslid 确定项目类型
     *
     * @param gzlslid 工作流实例 id
     * @return 1:普通  2：组合  3：批量 4:批量组合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    @Override
    @ApiOperation(value = "根据 gzlslid 确定项目类型")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    public int makeSureBdcXmLx(@PathVariable("gzlslid") String gzlslid) {
        return bdcXmService.bdcXmLx(gzlslid);
    }

    /**
     * 根据项目集合定项目类型
     *
     * @param list@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @return 1:普通  2：组合  3：批量  4:批量组合
     */
    @Override
    public int makeSureBdcXmLx(@RequestBody List<BdcXmDO> list) {
        if(CollectionUtils.isEmpty(list)){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        List<BdcXmDTO> listDTO=new ArrayList<>();
        for(BdcXmDO bdcXmDO:list){
            BdcXmDTO bdcXmDTO=new BdcXmDTO();
            BeanUtils.copyProperties(bdcXmDO,bdcXmDTO);
            listDTO.add(bdcXmDTO);
        }
        return bdcXmService.bdcXmLx(listDTO);
    }

    /**
     * 通过传入参数返回不动产项目部分信息
     *
     * @param gzlslid 工作流实例id
     * @return {List} 不动产项目部分信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入参数返回不动产项目部分信息
     */
    @ApiOperation(value = "通过传入工作实例ID返回不动产项目部分信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcXmDTO> listBdcXmBfxxByGzlslid(@PathVariable("gzlslid") String gzlslid) {
        return bdcXmService.listXmBfxx(gzlslid,null);
    }

    /**
     * 通过传入参数返回不动产项目部分信息
     *
     * @param slbh
     * @return {List} 不动产项目部分信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入参数返回不动产项目部分信息
     */
    @ApiOperation(value = "通过传入受理编号返回不动产项目部分信息")
    @ApiImplicitParam(name = "slbh", value = "受理编号", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcXmDTO> listBdcXmBfxxBySlbh(@PathVariable("slbh") String slbh) {
        return bdcXmService.listXmBfxx(null,slbh);
    }

    /**
     * 通过传入工作流实例id返回受理编号
     *
     * @param gzlslid 工作流实例id
     * @return 受理编号
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入工作流实例id返回受理编号
     */
    @ApiOperation(value = "通过传入工作流实例id返回受理编号")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public String querySlbh(@PathVariable("gzlslid") String gzlslid) {
        BdcXmDTO bdcXmDTO=bdcXmService.getXmBfxxOnlyOne(gzlslid,null);
        if(bdcXmDTO!=null){
            return bdcXmDTO.getSlbh();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 通过传入受理编号返回工作流实例id
     *
     * @param slbh 受理编号
     * @return 工作流实例id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入受理编号返回工作流实例id
     */
    @ApiOperation(value = "通过传入受理编号返回工作流实例id")
    @ApiImplicitParam(name = "slbh", value = "受理编号", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public String queryGzlslid(@PathVariable("slbh") String slbh) {
        BdcXmDTO bdcXmDTO=bdcXmService.getXmBfxxOnlyOne(null,slbh);
        if(bdcXmDTO!=null){
            return bdcXmDTO.getGzlslid();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 通过传入工作流实例ID和权利类型获取登记原因
     * @param gzlslid 工作流实例ID
     * @param qllx    权利类型
     * @return 登记原因
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入工作流实例ID和权利类型获取登记原因
     */
    @Override
    public String queryDjyy(@PathVariable("gzlslid") String gzlslid, @RequestParam(value = "qllx", required = false) Integer qllx) {
        return bdcXmService.queryDjyy(gzlslid,qllx);
    }

    @Override
    public String queryZxYgYdyDjyy(@PathVariable("gzlslid")String gzlslid,@RequestParam(value = "ygdjzl",required = false)List<Integer> ygdjzl){
        return bdcXmService.queryZxYgYdyDjyy(gzlslid,ygdjzl);
    }

    /**
     * 根据工作流实例ID获取开关实例数据
     * @param gzlslid 工作流实例ID
     * @return List<BdcCshFwkgSlDO> 实例集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "根据工作流实例ID获取开关实例数据")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcCshFwkgSlDO> queryFwkgslByGzlslid(@PathVariable("gzlslid") String gzlslid) {
        return bdcXmService.listBdCshSl(gzlslid);
    }

    @ApiOperation(value = "批量更新项目")
    @ApiImplicitParam(name = "bdcDjxxUpdateQO", value = "更新数量", dataType = "BdcDjxxUpdateQO", paramType = "query")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int updateBatchBdcXm(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO){
        return bdcXmService.updateBatchBdcXm(bdcDjxxUpdateQO);
    }

    /**
     * @param bdcQlQoStr 查询对象
     * @return 分页信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询原权利信息
     */
    @ApiOperation(value = "获取原权利数据服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQlQoStr", value = "参数JSON结构BdcQlQo", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public Page<BdcQlPageResponseDTO> listBdcYqlByPage(Pageable pageable, @RequestParam(name = "bdcQlQoStr", required = false) String bdcQlQoStr) {
        BdcQlQO bdcQlQO=new BdcQlQO();
        if(StringUtils.isNotBlank(bdcQlQoStr)){
            bdcQlQO=JSONObject.parseObject(bdcQlQoStr,BdcQlQO.class);
        }
        return repo.selectPaging("listBdcYqlByPage", bdcQlQO, pageable);
    }

    /**
     * @param bdcXmDO
     * @return Page<BdcXmFwzkfbVO> 查询结果
     * @author <a href="mailto:yanjiaqiang@gtmap.cn">yanjiaqiang</a>
     * @description 查询房屋状况表信息
     */
    @Override
    public List<BdcXmFwzkfbVO> listFwzkbByPage( @RequestBody BdcXmDO bdcXmDO) {
        Map<String, Object> map = new HashMap();
        if (bdcXmDO != null) {
            String json = JSONObject.toJSONString(bdcXmDO);
            map = JSONObject.parseObject(json, HashMap.class);
        }
        return repo.selectList("listFwzkbByPage", map);

    }
    /**
     * @param bdcXmDO
     * @return List<BdcXmFwzkfbVO> 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原房屋状况表信息
     */
    @Override
    public List<BdcXmFwzkfbVO> listYxmFwzkbByPage(@RequestBody BdcXmDO bdcXmDO) {
        Map<String, Object> map =  new HashMap();
        if (bdcXmDO != null) {
            String json = JSONObject.toJSONString(bdcXmDO);
            map = JSONObject.parseObject(json, HashMap.class);
        }
        return repo.selectList("listYxmFwzkbByPage", map);

    }


    /**
     * @param xmid
     * @return List<String> 项目集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询当前证书项目的涉及的项目集合
     */
    @ApiOperation(value = "查询当前证书项目的涉及的项目集合")
    @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<String> queryZsxmList(@PathVariable("xmid") String xmid) {
        return bdcXmService.queryZsxmList(xmid);
    }

    /**
     * @param bdcXmDO 项目信息
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书相关的项目的部分权利其他状况
     */
    @ApiOperation(value = "更新证书相关的项目的部分权利其他状况")
    @ApiImplicitParam(name = "bdcXmDO", value = "项目", dataType = "BdcXmDO", paramType = "body")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int updateBdcZsXmBfqlqtzk(@RequestBody BdcXmDO bdcXmDO) {
        return bdcXmService.updateBdcZsXmBfqlqtzk(bdcXmDO,null);
    }

    /**
     * @return List<String> 用途集合
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询要默认主房的用途集合
     */
    @ApiOperation(value = "根据主房配置信息，获取主房的用途集合")
    @ApiImplicitParam(name = "zfpz", value = "主房配置名称", dataType = "String", paramType = "query")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<String> getZfYt(@RequestParam(name = "zfpz") String zfpz) {
        Map<String, String> param = new HashedMap(2);
        param.put("zfpz", zfpz);
        return repo.selectList("getZfYtList", param);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcZdFwytDO>
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取房屋用途字典项
     */
    @ApiOperation(value = "根据工作流实例ID获取房屋用途字典项")
    @ApiImplicitParam(name = "gzlslid", value = "项目", dataType = "String", paramType = "path")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcZdFwytDO> queryZdFwytByGzlslid(@PathVariable("gzlslid") String gzlslid){
        return bdcXmService.queryZdFwytByGzlslid(gzlslid);

    }

    @ApiOperation(value = "根据当前工作流实例ID查询原项目")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "query")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcXmDO> listYxmByGzlslid(@RequestBody BdcXmLsgxDO bdcXmLsgxDO,@RequestParam(name = "gzlslid") String gzlslid,@RequestParam(name = "djxl",required = false) String djxl){
        return bdcXmService.listYxmByGzlslid(bdcXmLsgxDO,gzlslid,djxl);

    }

    @ApiOperation(value = "根据查询对象查询原项目")
    @ApiImplicitParam(name = "bdcYxmQO", value = "不动产原项目查询参数封装对象", dataType = "BdcYxmQO", paramType = "query")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcXmDO> listYxmByYxmQO(@RequestBody BdcYxmQO bdcYxmQO){
        return bdcXmService.listYxmByYxmQO(bdcYxmQO);

    }

    @Override
    @ApiOperation(value = "嵌套获取上手产权项目")
    public List<BdcXmDO> listPrevCqXm(@RequestParam("xmid") String xmid,@RequestParam(value = "getOneXm") boolean getOneXm){
        return bdcXmService.listPrevCqXm(xmid, new ArrayList<>(), getOneXm);

    }

    @Override
    @ApiOperation(value = "嵌套批量获取上手产权项目")
    public List<BdcXmDO> listPrevCqXmPl(@RequestBody List<String> xmidList,@RequestParam(value = "getOneXm") boolean getOneXm){
        return bdcXmService.listPrevCqXmPl(xmidList, new ArrayList<>(), getOneXm);

    }

    /**
     * @param bdcXmQO 查询条件
     * @author <a href="mailto:liaoxiang@gtmap.cn">gaolining</a>
     * @description 根据查询对象查询项目信息和附表信息
     */
    @ApiOperation(value = "查询项目信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXmAndFbDTO> listBdcXmAndFb(@RequestBody BdcXmQO bdcXmQO) {
        return bdcXmService.listXmAndFb(bdcXmQO);
    }

    /**
     *
     * @param xm 权利人名称
     * @param zjhm 权利人证件号
     * @param cqzh 不动产权证号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据条件查询产权
     * @return
     */
    @ApiOperation(value = "根据参数查询现势产权")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<XscqDTO> listXscq(@RequestParam(value = "xm") String xm, @RequestParam(value = "zjhm") String zjhm, @RequestParam(value = "cqzh") String cqzh) {
        return bdcXmService.listXscq(xm,zjhm,cqzh);
    }

    /**
     *
     * @param xm 权利人名称
     * @param zjhm 权利人证件号
     * @param htbh 合同编号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据条件查询办证进度
     * @return
     */
    @ApiOperation(value = "根据参数查询办证进度")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BzjdDTO> listBzjd(String xm, String zjhm, String htbh) {
        return bdcXmService.listBzjd(xm,zjhm,htbh);
    }

    /**
     * @param bdcQlrQO 权利人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人名称加证件号找到项目表的经营权信息
     * @date : 2020/11/5 9:19
     */
    @Override
    @ApiOperation(value = "查询项目信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXmDTO> listCbjyqXm(@RequestBody BdcQlrQO bdcQlrQO) {
        return bdcXmService.listCbjyqXm(bdcQlrQO);
    }

    @Override
    @ApiOperation(value = "查询项目信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "xmids", value = "项目ID集合", dataType = "List<String>", paramType = "body")
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXmDO> listBdcXmByXmids(@RequestBody List<String> xmids) {
        return bdcXmService.listBdcXmByXmids(xmids);
    }

    /**
     * 根据工作流实例ID获取开关实例数据
     *
     * @param gzlslid 工作流实例ID
     * @param sfzf 是否主房
     * @return List<BdcCshFwkgSlDO>  是否主房实例集合
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     */
    @ApiOperation(value = "根据工作流实例ID获取开关实例数据")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcCshFwkgSlDO> querySfzfKgslByGzlslid(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(name = "sfzf",required = false) Integer sfzf) {
        return bdcXmService.listBdCshSlSfzf(gzlslid,sfzf);
    }

    /**
     * 根据产权证号查询现势产权
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param courtYwxxcxRequestDTO
     * @return
     */
    @ApiOperation(value = "根据产权证号查询现势产权")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcXmDO> listCourtXscq(@RequestBody CourtYwxxcxRequestDTO courtYwxxcxRequestDTO) {
        return bdcXmService.listCourtXscq(courtYwxxcxRequestDTO);
    }

    @ApiOperation(value = "根据不动产单元查询现势产权项目")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List<BdcXmDO> listXscqXm(@RequestBody List<BdcXmDO> bdcXmDOList){
        return bdcXmService.listXscqXm(bdcXmDOList);

    }

    /**
     * 查询登记系统存在的所有权籍管理代码
     * @return {List} 权籍管理代码集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ApiOperation(value = "查询登记系统存在的所有权籍管理代码")
    @ResponseStatus(code = HttpStatus.OK)
    public List<String> listQjgldm() {
        return bdcXmService.listQjgldm();
    }

    /**
     * @param bdcCshFwkgSlDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增初始化开关实例数据
     * @date : 2021/9/30 14:18
     */
    @Override
    public int insertBdcCshFwkgSl(@RequestBody BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        return bdcXmService.insertBdcCshFwkgSl(bdcCshFwkgSlDO);
    }

    /**
     * 根据xmid获取历史关系
     *
     * @param xmid 不动产权证号
     * @return List<Map>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public BdcXmLsgxDTO queryBdcxmLsgxByXmid(@RequestParam("xmid") String xmid) {
        return bdcXmService.queryBdcxmLsgxByXmid(xmid);
    }

    /**
     * 根据不动产权证号获取项目信息
     *
     * @param bdcqzh 不动产权证号
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     */
    @Override
    public List<BdcXmDO> queryXmByZsBdcqzh(@RequestParam("bdcqzh") String bdcqzh) {
        return bdcXmService.queryXmByZsBdcqzh(bdcqzh);
    }

    @Override
    @ApiOperation(value = "根据工作流实例ID获取同审批系统业务号的其他项目信息")
    @ResponseStatus(code = HttpStatus.OK)
    public List<BdcXmDO> listOtherXmWithSameSpxtywh(@RequestParam("gzlslid") String gzlslid){
        return bdcXmService.listOtherXmWithSameSpxtywh(gzlslid);

    }

    /**
     * @param xmid
     * @return daywh
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid取daywh值（盐城BDC_XM表独有字段）
     */
    @Override
    @ApiOperation(value = "根据xmid取daywh值")
    @ResponseStatus(code = HttpStatus.OK)
    public String queryDaywh(@RequestParam("xmid") String xmid){
        return bdcXmService.queryDaywh(xmid);
    }

    /**
     * 模糊查询项目数据（限定数量、最新数据）
     * @param bdcXmQO 查询参数
     * @return {List} 项目数据
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ApiOperation(value = "模糊查询项目受理编号数据")
    @ApiImplicitParam(name = "bdcXmQO", value = "查询参数", dataType = "List<BdcXmDO>", paramType = "body")
    public List<BdcXmDO> listBdcXmData(@RequestBody BdcXmQO bdcXmQO) {
        return bdcXmService.listBdcXmSlbhs(bdcXmQO);
    }

    /**
     * @description 查询项目信息中同一工作流实例ID不动产单元号数量（【常州】提示查看清册和附表）
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/21 21:08
     * @param gzlslid 工作流实例ID
     * @return int 同一工作流实例ID不动产单元号数量
     */
    @Override
    @ApiOperation(value = "查询项目信息中同一工作流实例ID不动产单元号数量")
    @ResponseStatus(code = HttpStatus.OK)
    public int countBdcByGzlslidGroupBdcdyh(@PathVariable("gzlslid") String gzlslid) {
        return bdcXmService.countBdcByGzlslidGroupBdcdyh(gzlslid);
    }

    @Override
    @ApiOperation(value = "根据受理编号查询一条项目信息（权利人、坐落）")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParam(name = "slbh", value = "受理编号", dataType = "String", paramType = "path")
    public BdcXmDO queryOneBdcXmDOBySlbh(@PathVariable(value = "slbh") String slbh) {
        return this.bdcXmService.queryOneBdcXmDOBySlbh(slbh);
    }

    @Override
    @ApiOperation(value = "根据不动产项目ID查询项目信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "String", paramType = "path")
    public BdcXmDO queryBdcXmByXmid(@PathVariable(value = "xmid")  String xmid) {
        return this.bdcXmService.queryBdcXmByPrimaryKey(xmid);
    }

    /**
     * 根据不动产项目id返回不动产项目数量，对应不动产单元号数目
     *@author <a href="mailto:wangfangfang@gtmap.cn">wangfangfang</a>
     *@param xmid
     *@return int
     *@description
     */
    @Override
    @ApiOperation(value = "根据不动产项目id获取相同证书的全部项目ID集合")
    @ResponseStatus(code = HttpStatus.OK)
    public List<String> listYbzXmByXmid( @RequestParam(value = "xmid") String xmid,@RequestParam(value = "qszt",required = false) Integer qszt) {
        return bdcXmService.listYbzXmByXmid(xmid,qszt);
    }
}
