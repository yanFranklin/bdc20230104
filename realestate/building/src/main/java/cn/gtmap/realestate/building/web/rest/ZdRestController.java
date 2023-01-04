package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.ZdQlrService;
import cn.gtmap.realestate.building.core.service.ZdService;
import cn.gtmap.realestate.building.core.service.ZdxxByJqxxService;
import cn.gtmap.realestate.building.service.TuxknrService;
import cn.gtmap.realestate.building.service.ZdtService;
import cn.gtmap.realestate.building.util.DjdcbUtils;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbJzxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdxxByJqxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.building.ZdxxByJqxxQO;
import cn.gtmap.realestate.common.core.service.rest.building.ZdRestService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
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
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/1
 * @description 宗地相关服务
 */
@RestController
@Api(tags = "宗地服务接口")
public class ZdRestController extends BaseController implements ZdRestService {
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 宗地service
     */
    @Autowired
    protected ZdService zdService;

    @Autowired
    private ZdQlrService zdQlrService;

    @Autowired
    private ZdtService zdtService;

    @Autowired
    private TuxknrService tuxknrService;

    @Autowired
    private ZdxxByJqxxService zdxxByJqxxService;
    /**
     * @param pageable
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询宗地信息
     */
    @Override
    @ApiOperation(value = "分页查询宗地信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listZdByPageJson(Pageable pageable,
                                                String paramJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson,Map.class);
        }
        return zdService.listZdByPage(pageable, map);
    }


    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据逻辑幢主键初始化宗地目录树 包括宗地 和 宗地下的自然幢列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据逻辑幢主键初始化宗地目录树(自然幢列表来源于FW_K)")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", dataType = "string", paramType = "path")})
    public ZdTreeResponseDTO initZdTreeByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdService.initZdTreeByFwDcbIndex(fwDcbIndex);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据地籍号初始化宗地目录树(自然幢列表来源于FW_LJZ)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据地籍号初始化宗地目录树(自然幢列表来源于FW_LJZ)")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "djh", dataType = "string", paramType = "path")})
    public ZdTreeResponseDTO initZdTreeByDjhAndAllZrzh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdService.initZdTreeByDjhAndAllZrzh(djh,"");
    }


    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据逻辑幢主键初始化宗地目录树 包括宗地 和 宗地下的自然幢列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据逻辑幢主键初始化宗地目录树(自然幢列表来源于FW_LJZ)")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", dataType = "string", paramType = "path")})
    public ZdTreeResponseDTO initZdTreeByFwDcbIndexAndAllZrzh(@PathVariable("fwDcbIndex")String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdService.initZdTreeByFwDcbIndexAndAllZrzh(fwDcbIndex);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询宗地地籍数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询宗地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public ZdDjdcbDO queryZdByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdService.queryZdDjdcbByBdcdyh(bdcdyh);
    }

    /**
     * @param bdcdyhList
     * @return lczt
     * @author
     * @description 根据不动产单元号查询宗地流程状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询宗地流程状态")
    public List<String> queryZdLcztByBdcdyh(@RequestBody List<String> bdcdyhList) {
        return zdService.queryZdLcztByBdcdyh(bdcdyhList);
    }

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据bdcdyh查询宗地权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询宗地权利人")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public List<ZdQlrDO> listZdQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdQlrService.listZdQlrByBdcdyh(bdcdyh);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据地籍号查询宗地地籍数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据DJH查询宗地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍号", required = true, dataType = "String", paramType = "path")})
    public ZdDjdcbDO queryZdByDjh(@PathVariable("djh")String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdService.queryZdDjdcbByDjh(djh);
    }


    /**
     * @param pageable
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询宗地界址信息信息
     */
    @Override
    @ApiOperation(value = "分页查询宗地界址信息信息")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<DjdcbJzxxResponseDTO> listZdjzxxByPageJson(Pageable pageable, String paramJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson,Map.class);
        }
        String bdcdyh=MapUtils.getString(map,"bdcdyh");
        if (StringUtils.isNotBlank(bdcdyh)) {
            String tableName= DjdcbUtils.getTableNameByBdcdyh(bdcdyh);
            map.put("djh",bdcdyh.substring(0,19));
            map.put("tableName",tableName);
            Page<DjdcbJzxxResponseDTO> result = zdService.listZdzjxxByPage(pageable, map);
            return result;
        } else {
            return null;
        }
    }

    /**
     * @param djh
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据地籍号查询有效的不动产单元号
     */
    @Override
    @ApiOperation(value = "根据地籍号查询有效的不动产单元号")
    @ResponseStatus(code = HttpStatus.OK)
    public List<String> listValidBdcdyhByDjh(@RequestParam(value = "djh", required = false) String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdService.listValidBdcdyhByDjh(djh);
    }

    /**
     * 根据地籍号和自然幢号查询有效的房屋户室不动产单元号
     * @param djh  地籍号
     * @param zrzh 自然幢号
     * @return List<String> 房屋户室不动产单元号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ApiOperation(value = "根据地籍号和自然幢号查询有效的房屋户室不动产单元号")
    @ResponseStatus(code = HttpStatus.OK)
    public List<String> listValidBdcdyhByDjhZrzh(@RequestParam(value = "djh") String djh, @RequestParam(value = "zrzh", required = false) String zrzh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdService.listValidBdcdyhByDjhZrzh(djh, zrzh);
    }

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询宗地图 JPG
     */
    @Override
    @ApiOperation(value = "根据DJH查询宗地图")
    @ResponseStatus(code = HttpStatus.OK)
    public ZdtResponseDTO queryZdtByDjh(@PathVariable(name = "djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ZdtResponseDTO zdtResponseDTO = new ZdtResponseDTO();
        zdtResponseDTO.setBase64(zdtService.getZdtByDjh(djh));
        zdtResponseDTO.setReadTuxknr(tuxknrService.needReadTuxknr());
        return zdtResponseDTO;
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据bdcdyh查询宗地图
     */
    @Override
    @ApiOperation(value = "根据bdcdyh查询宗地图")
    @ResponseStatus(code = HttpStatus.OK)
    public ZdtResponseDTO queryZdtByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ZdtResponseDTO zdtResponseDTO = new ZdtResponseDTO();
        zdtResponseDTO.setBase64(zdtService.getZdtByBdcdyh(bdcdyh,qjgldm));
        zdtResponseDTO.setReadTuxknr(tuxknrService.needReadTuxknr());
        return zdtResponseDTO;
    }

    /**
     * 根据合同编号查询承包土地图层信息
     *
     * @param htbh 合同编号
     * @return ZdtResponseDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 13:55 2020/8/24
     */
    @Override
    @ApiOperation(value = "根据htbh查询承包土地宗地图")
    @ResponseStatus(code = HttpStatus.OK)
    public ZdtResponseDTO queryCbzdsytByCbhtbh(@PathVariable(value = "htbh", required = false) String htbh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ZdtResponseDTO zdtResponseDTO = new ZdtResponseDTO();
        zdtResponseDTO.setBase64(zdtService.getCbzdtByHtbh(htbh));

        return zdtResponseDTO;
    }


    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 根据DJH查询宗地图文档中心ID

     @Override
     @ApiOperation(value = "根据DJH查询宗地图(文档中心id)")
     @ResponseStatus(code = HttpStatus.OK)
     public String queryZdtIDByDjh(@PathVariable(name = "djh") String djh) throws IOException {
     return zdtService.getZdtIDByDjh(djh);
     }*/


    /**
     * @param djh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据地籍号查询宗地权利人
     * @date : 2021/1/22 19:33
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询宗地权利人")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍号", required = true, dataType = "String", paramType = "path")})
    public List<ZdQlrDO> listZdQlrByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdQlrService.listZdQlrByDjh(djh);
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据bdcdyh查询宗地图-ftp
     */
    @Override
//    @ApiOperation(value = "根据bdcdyh查询宗地图-ftp")
    @ResponseStatus(code = HttpStatus.OK)
    public ZdtResponseDTO queryZdtByBdcdyhFtp(@PathVariable(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        ZdtResponseDTO zdtResponseDTO = new ZdtResponseDTO();
        zdtResponseDTO.setBase64(zdtService.queryZdtByBdcdyhFtp(bdcdyh));
        zdtResponseDTO.setReadTuxknr(tuxknrService.needReadTuxknr());
        return zdtResponseDTO;
    }

    /**
     * @param
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description
     * @date : 2021/8/4
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据宗地地籍号、宗地权利人等条件查找宗地list")
    public Page<Map> listZdxxByPageJson(Pageable pageable, String paramJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson,Map.class);
        }
        return zdService.listZdxxByPageJson(pageable, map);
    }

    /**
     * @param zl
     * @param qjgldm
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">huangjian</a>
     * @description 根据zl查询宗地地籍数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据ZL查询宗地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zl", value = "坐落", required = true, dataType = "String", paramType = "path")})

    public ZdDjdcbDO queryZdByZlAndBdcdyh(@RequestParam(name = "zl",required = false) String zl,@RequestParam(name = "bdcdyh", required = false) String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return zdService.queryZdDjdcbByZlAndBdcdyh(zl,bdcdyh);
    }

    /**
     * @param zdxxByJqxxQOStr
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangyongming</a>
     * @description 根据坐落、不动产权证、不动产单元号、地籍号、权利人名称查询宗地地籍数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据坐落、不动产权证、不动产单元号、地籍号、权利人名称查询宗地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdxxByJqxxQOStr", value = "参数JSON结构ZdxxByJqxxQO", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<ZdxxByJqxxDTO> findZdxxByJqxx(Pageable pageable, @RequestParam(name = "zdxxByJqxxQOStr", required = false) String zdxxByJqxxQOStr) {
        ZdxxByJqxxQO zdxxByJqxxQO = new ZdxxByJqxxQO();
        if (StringUtils.isBlank(zdxxByJqxxQOStr)) {
            throw new AppException("请增加查询条件!");
        }
        zdxxByJqxxQO = JSONObject.parseObject(zdxxByJqxxQOStr, ZdxxByJqxxQO.class);
        // 入参全为空，则返回失败信息
        if (StringUtils.isBlank(zdxxByJqxxQO.getBdcqzh())
                && StringUtils.isBlank(zdxxByJqxxQO.getBdcdyh())
                && StringUtils.isBlank(zdxxByJqxxQO.getDjh())
                && StringUtils.isBlank(zdxxByJqxxQO.getZl())
                && StringUtils.isBlank(zdxxByJqxxQO.getQlrmc())) {
            // 返回错误报文
            throw new AppException("请增加查询条件!");
        }
        return zdxxByJqxxService.findZdxxByJqxx(pageable, zdxxByJqxxQO);
    }

}