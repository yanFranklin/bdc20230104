package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.CbzdService;
import cn.gtmap.realestate.building.util.TzmUtils;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.*;
import cn.gtmap.realestate.common.core.service.rest.building.CbzdRestService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description 承包宗地相关服务
 */
@RestController
@Api(tags = "承包宗地服务接口")
public class CbzdRestController extends BaseController implements CbzdRestService {
    @Autowired
    private CbzdService cbzdService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询承包宗地基本信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询承包宗地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public CbzdDcbDO queryCbzdByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return cbzdService.queryCdzdDcbByBdcdyh(bdcdyh);
    }

    /**
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.CbzdDcbDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据DJH查询承包宗地基本信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据DJH查询承包宗地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍号", required = true, dataType = "String", paramType = "path")})
    public CbzdDcbDO queryCbzdByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return cbzdService.queryCbzdDcbByDjh(djh);
    }

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.CbzdCbfDO>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询承包宗地承包方信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public List<CbzdCbfDO> listCbfByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return cbzdService.listCbfByBdcdyh(bdcdyh);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据承包方地块关系主键查询承包宗地承包方信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "cbzdDcbcbfrelIndex", value = "承包方与地块关系主键", required = true, dataType = "String", paramType = "path")})
    public List<CbzdCbfDO> listCbfByCbzdDcbcbfrelIndex(@PathVariable("cbzdDcbcbfrelIndex") String cbzdDcbcbfrelIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm){
        return cbzdService.listCbfByCbzdDcbcbfrelIndex(cbzdDcbcbfrelIndex);

    }

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.CbzdFbfDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询发包方信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询承包宗地发包方信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public List<CbzdFbfDO> listFbfByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        //  按照权籍现在逻辑一个不动产单元号只能查到一个发包方  暂时不改动服务返回的参数类型
        List<CbzdFbfDO> cbzdFbfDOList = new ArrayList<>();
        cbzdFbfDOList.add(cbzdService.getFbfByBdcdyh(bdcdyh));
        return cbzdFbfDOList;
    }

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.CbzdResponseDTO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询承包不动产单元
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询承包宗地不动产单元")
    @ApiImplicitParams({@ApiImplicitParam(name = "paramJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<Map> listCbzdBdcdy(Pageable pageable
            , String paramJson) {
        Map<String, String> paramMap = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            paramMap = JSONObject.parseObject(paramJson,Map.class);
        }
        return cbzdService.listCbzdBdcdy(pageable, paramMap);
    }

    /**
     * @param jtIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 通过家庭Index 查询家庭成员信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "通过家庭Index 查询家庭成员信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "jtIndex", value = "家庭成员外键", required = true, dataType = "String", paramType = "path")})
    public List<NydJtcyDO> listCbzdJtcyByJtIndex(@PathVariable("jtIndex")String jtIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return cbzdService.listJtcy(jtIndex);
    }

    /**
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydZdmjDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 用外键 查询 承包宗地 分类面积列表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "用外键查询承包宗地分类面积列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "djdcbIndex", value = "地籍调查表外键", required = true, dataType = "String", paramType = "path")})
    public List<NydZdmjDO> listNydZdmjByDjdcbIndex(@PathVariable("djdcbIndex") String djdcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return cbzdService.listNydZdmjByDjdcbIndex(djdcbIndex);
    }

    /**
     * @param bdcdyh
     * @return boolean
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 验证BDCDYH 是否 有承包信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询承包宗地发包方信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public boolean yzbdcdyh(@PathVariable("bdcdyh") String bdcdyh) {
        return TzmUtils.hasCbzdDcb(bdcdyh);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询当前地块对应承包方的所有地块关系集合")
    @ApiImplicitParams({@ApiImplicitParam(name = "cbzdDcbcbfrelIndex", value = "承包地块与承包方关系表主键", required = true, dataType = "String", paramType = "path")})
    public List<CbzdDcbcbfRelDO> listCbfCbzdDcbcbfRelList(@PathVariable("cbzdDcbcbfrelIndex")String cbzdDcbcbfrelIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm){
        return cbzdService.listCbfCbzdDcbcbfRelList(cbzdDcbcbfrelIndex);

    }
}