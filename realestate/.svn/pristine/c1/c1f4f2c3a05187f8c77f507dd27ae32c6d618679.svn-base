package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDyaDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDyaQo;
import cn.gtmap.realestate.common.core.qo.inquiry.count.DyaTjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcDyaCxRestService;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcDyaTjVO;
import cn.gtmap.realestate.inquiry.service.BdcDyaCxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/9/3 14:13
 * @description
 */
@RestController
@Api(tags = "不动产抵押查询")
public class BdcDyaCxRestController implements BdcDyaCxRestService {
    @Autowired
    BdcDyaCxService dyaCxService;

    /**
     * 分页查询抵押不动产单元
     *
     * @param pageable
     * @param bdcDyaQOJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcDyaDTO       ;>
     * @auth o r <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "抵押查询#DYCX")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDyaQOJson", value = "参数JSON",
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量",
                    dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序",
                    dataType = "string", paramType = "query")})
    public Page<BdcDyaDTO> listBdcDyaByPage(Pageable pageable, String bdcDyaQOJson) {
        BdcDyaQo bdcDyaQo = new BdcDyaQo();
        if (StringUtils.isNotBlank(bdcDyaQOJson)) {
            bdcDyaQo = JSONObject.parseObject(bdcDyaQOJson, bdcDyaQo.getClass());
        }
        return dyaCxService.listBdcDyaByPage(pageable, bdcDyaQo);
    }

    /**
     * @param dyaTjQO 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计月报信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取抵押统计月报信息")
    @Override
    public List<BdcDyaTjVO> getDyaTjMonth(@RequestBody DyaTjQO dyaTjQO) {
        return dyaCxService.getDyaTjMonth(dyaTjQO);
    }

    /**
     * @param dyaTjQO 抵押统计查询QO
     * @return List<BdcDyaTjVO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计日报信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取抵押统计日报信息")
    @Override
    public List<BdcDyaTjVO> getDyaTjDay(@RequestBody DyaTjQO dyaTjQO) {
        return dyaCxService.getDyaTjDay(dyaTjQO);
    }

    /**
     * @param redisKey 保存redis的key值
     * @param dylx     抵押统计的打印类型
     * @return 打印xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取抵押统计的xml信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取抵押统计的xml信息")
    @Override
    public String getDyaTjXml(@RequestParam(value = "redisKey") String redisKey, @PathVariable(value = "dylx") String dylx) {
        return dyaCxService.getDyaTjXml(redisKey, dylx);
    }

    /**
     * @param dylx         打印类型
     * @param listDyatjStr 打印统计信息
     * @return 保存到redis的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存抵押提交信息到redis
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存抵押提交信息到redis")
    @Override
    public String saveDyatjXxToRedis(@PathVariable(value = "dylx") String dylx, @RequestBody String listDyatjStr) {
        return dyaCxService.saveDyatjXxToRedis(dylx, listDyatjStr);
    }

    /**
     * 抵押分页查询-标准版：查询抵押权表
     *
     * @param pageable     pageable
     * @param bdcDyaQOJson 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "抵押分页查询-标准版：查询抵押权表#DYCX")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDyaQOJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @Override
    public Page<BdcDyaDTO> listStandardDyaByPage(Pageable pageable, String bdcDyaQOJson) {
        BdcDyaQo bdcDyaQo = new BdcDyaQo();
        if (StringUtils.isNotBlank(bdcDyaQOJson)) {
            bdcDyaQo = JSONObject.parseObject(bdcDyaQOJson, BdcDyaQo.class);
        }
        return dyaCxService.listStandardDyaByPage(pageable, bdcDyaQo);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "标准版: 抵押分页查询：查询预告预抵押信息#DYCX")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDyaQOJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @Override
    public Page<BdcDyaDTO> listStandardYgDyaByPage(Pageable pageable, String bdcDyaQOJson) {
        BdcDyaQo bdcDyaQo = new BdcDyaQo();
        if (StringUtils.isNotBlank(bdcDyaQOJson)) {
            bdcDyaQo = JSONObject.parseObject(bdcDyaQOJson, BdcDyaQo.class);
        }
        return dyaCxService.listStandardYgDyaByPage(pageable, bdcDyaQo);
    }

    /**
     * 抵押分页查询-常州版：查询抵押权表
     *
     * @param pageable     pageable
     * @param bdcDyaQOJson 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "抵押分页查询-常州版：查询抵押权表#DYCX")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDyaQOJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @Override
    public Page<BdcDyaDTO> listChangzhouDyaByPage(Pageable pageable, String bdcDyaQOJson) {
        BdcDyaQo bdcDyaQo = new BdcDyaQo();
        if (StringUtils.isNotBlank(bdcDyaQOJson)) {
            bdcDyaQo = JSONObject.parseObject(bdcDyaQOJson, BdcDyaQo.class);
        }
        return dyaCxService.listChangzhouDyaByPage(pageable, bdcDyaQo);
    }

    @Override
    public List listStandardDya(@RequestBody BdcDyaQo bdcDyaQO) {
        return dyaCxService.listStandardDya(bdcDyaQO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "标准版: 抵押查询：查询预告预抵押信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDyaQO", value = "不动产抵押QO", dataType = "BdcDyaQo", paramType = "body"),
    })
    @Override
    public List listStandardYgDya(@RequestBody BdcDyaQo bdcDyaQO) {
        return dyaCxService.listStandardYgDya(bdcDyaQO);
    }

    /**
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @version 2021/11/25,1.0
     * @description 根据项目id查询抵押
     */
    @Override
    public List listChangzhouDya(@RequestBody BdcDyaQo bdcDyaQO) {
        return dyaCxService.listChangzhouDya(bdcDyaQO);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询抵押
     */
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public List listDyaByXmid(@PathVariable(value = "xmid") String xmid) {
        return dyaCxService.listDyaByXmid(xmid);
    }

    /**
     * 抵押分页查询-蚌埠版：查询抵押权表
     *
     * @param pageable     pageable
     * @param bdcDyaQOJson 抵押查询QO
     * @return 抵押查询结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "抵押分页查询-蚌埠版：查询抵押权表#DYCX")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDyaQOJson", value = "参数JSON", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    @Override
    public Page<BdcDyaDTO> listBengbuDyaByPage(Pageable pageable, String bdcDyaQOJson) {
        BdcDyaQo bdcDyaQo = new BdcDyaQo();
        if (StringUtils.isNotBlank(bdcDyaQOJson)) {
            bdcDyaQo = JSONObject.parseObject(bdcDyaQOJson, BdcDyaQo.class);
        }
        return dyaCxService.listBengbuDyaByPage(pageable, bdcDyaQo);
    }

    @Override
    public List listBengbuDya(@RequestBody BdcDyaQo bdcDyaQO) {
        return dyaCxService.listBengbuDya(bdcDyaQO);
    }

    /**
     * 查询抵押全表 qszt！=2
     *
     * @param bdcdyh
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询抵押全表 qszt！=2")
    public List listDyaByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh) {
        return dyaCxService.listDyaByBdcdyh(bdcdyh);
    }

    /**
     * 查询抵押全表
     *
     * @param bdcdyh
     * @param qszt
     * @return
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 14:22 2020/11/5
     */
    @Override
    public List listDyaByBdcdyhAndqszt(@RequestParam(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qszt") Integer qszt) {
        if (StringUtils.isBlank(bdcdyh) && null == qszt) {
            throw new MissingArgumentException("缺少参数，请检查");
        }
        return dyaCxService.listDyaByBdcdyhAndqszt(bdcdyh, qszt);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询抵押信息
     */
    @Override
    public List<BdcDyaqDO> listBdcDyaqByBdcdyhs(@RequestBody List<String> bdcdyhList) {
        return dyaCxService.listBdcDyaqByBdcdyhs(bdcdyhList);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取产权的不动产权证号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDyaDTO", value = "不动产抵押DTO", dataType = "BdcDyaDTO", paramType = "body"),
    })
    @Override
    public String getCqBdcqzhByXmxx(@RequestBody BdcDyaDTO bdcDyaDTO) {
        return dyaCxService.getCqBdcqzhByXmxx(bdcDyaDTO);
    }

    /**
     * 查询抵押权表
     *
     * @param djh 地籍号
     * @param qszt 权属状态
     * @return 抵押查询结果
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据地籍号查询抵押权表现势的在建工程抵押")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "djh", value = "地籍号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "dybdclx", value = "抵押不动产类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qszt", value = "权属状态", dataType = "string", paramType = "query")})
    @Override
    public List<BdcDyaqDO> listDyaByDjhAndQszt(@RequestParam(name = "djh") String djh, @RequestParam(name = "qszt") Integer qszt, @RequestParam(name = "dybdclx") Integer dybdclx) {
        return dyaCxService.listDyaByDjhAndQszt(djh,qszt,dybdclx);
    }


}
