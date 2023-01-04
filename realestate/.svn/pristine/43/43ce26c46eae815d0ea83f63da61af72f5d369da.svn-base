package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.exchange.PageDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcBaxxCxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcPlcxQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcPlcxRestService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.service.BdcPlcxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 查封信息查询
 */
@RestController
@Api(value = "批量查询服务")
public class BdcPlcxRestController implements BdcPlcxRestService {

    @Autowired
    private BdcPlcxService bdcPlcxService;

    /**
     * @param pageDTOJson
     * @param bdcPlcxQO
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询批量信息
     *
     * 修改bdcPlcxQO通过@RequestBody获取参数，避免get请求头过大问题
     * 修改分页对象为PageDTO，解决Pageable无法传参问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量查询#PLCX")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageDTOJson", value = "分页参数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "bdcPlcxQOJson", value = "查询信息参数JSON", required = false, paramType = "body")})
    public Page<BdcPlcxDTO> listBdcPlcxByPage(@RequestParam(name = "pageDTOJson") String pageDTOJson,
                                              @RequestBody BdcPlcxQO bdcPlcxQO) {
        // 默认分页参数为0,10
        Pageable pageable = new PageRequest(0, 10, null);
        if(StringUtils.isNotBlank(pageDTOJson)) {
            PageDTO pageDTO = JSONObject.parseObject(pageDTOJson, PageDTO.class);
            pageable = PageUtils.pageDtoTOPageable(pageDTO);
        }
        // 当有逗号分隔后，说明是导入的方式，则清空单个的条件，sql用in方式
        if (bdcPlcxQO.getSingleQlr().indexOf(",") > -1) {
            bdcPlcxQO.setQlr(Arrays.asList(bdcPlcxQO.getSingleQlr().split(",")));
            bdcPlcxQO.setSingleQlr("");

        }
        if (bdcPlcxQO.getSingleZjh().indexOf(",") > -1) {
            bdcPlcxQO.setZjh(Arrays.asList(bdcPlcxQO.getSingleZjh().split(",")));
            List<String> zjh15 = new ArrayList<>();
            for (int i = 0; i < bdcPlcxQO.getZjh().size(); i++) {
                if (bdcPlcxQO.getZjh().get(i).length() > 15) {
                    zjh15.add(CardNumberTransformation.idCard18to15(bdcPlcxQO.getZjh().get(i)));
                } else {
                    zjh15.add(bdcPlcxQO.getZjh().get(i));
                }
            }
            bdcPlcxQO.setZjh15(zjh15);
            bdcPlcxQO.setSingleZjh("");
        } else {//如果是单个证件号，则赋值15位的
            if (bdcPlcxQO.getSingleZjh().length() > 15) {
                bdcPlcxQO.setSingleZjh15(CardNumberTransformation.idCard18to15(bdcPlcxQO.getSingleZjh()));
            } else {
                bdcPlcxQO.setSingleZjh15(bdcPlcxQO.getSingleZjh());
            }
        }
        //增加权籍区县过滤
        bdcPlcxQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("plcx","", bdcPlcxQO.getModuleCode()));

        // 产权的权利人，排除掉权利人表的权利人，剩下的就是共有人
        Page<BdcPlcxDTO> page = bdcPlcxService.listBdcPlcxByPage(pageable, bdcPlcxQO);
        for (int i = 0; i < page.getContent().size(); i++) {
            String cqqlr = page.getContent().get(i).getCqqlr();
            String qlr = page.getContent().get(i).getQlr();
            if (StringUtils.isNotEmpty(cqqlr) && StringUtils.isNotEmpty(qlr)) {
                if (!cqqlr.equals(qlr)) {
                    // 替换的共有人前后多余的逗号
                    String gyr = cqqlr.replace(qlr, "").replaceAll("^,*|,*$", "").replace(",", "/");
                    page.getContent().get(i).setGyrxm(gyr);
                }
            }
        }
        return page;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcPlcxQOJson]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO>
     * @description 盐城 批量查询
     *
     * 修改bdcPlcxQOJson通过@RequestBody获取参数，避免get请求头过大问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "盐城 批量查询#PLCX")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcPlcxQOJson", value = "查询信息参数JSON", required = false, paramType = "body")})
    public List<BdcYcPlcxDTO> listBdcYcPlcx(@RequestBody String bdcPlcxQOJson) {
        BdcPlcxQO bdcPlcxQO = new BdcPlcxQO();
        if (StringUtils.isNotBlank(bdcPlcxQOJson)) {
            bdcPlcxQO = JSONObject.parseObject(bdcPlcxQOJson, BdcPlcxQO.class);
        }
        boolean importFlag = false;
        // 当有逗号分隔后，说明是导入的方式，则清空单个的条件，sql用in方式
        if (bdcPlcxQO.getSingleQlr().indexOf(",") > -1) {
            bdcPlcxQO.setQlr(Arrays.asList(bdcPlcxQO.getSingleQlr().split(",")));
            bdcPlcxQO.setSingleQlr("");

        }
        if (bdcPlcxQO.getSingleZjh().indexOf(",") > -1) {
            importFlag = true;
            bdcPlcxQO.setZjh(Arrays.asList(bdcPlcxQO.getSingleZjh().split(",")));
            List<String> zjh15 = new ArrayList<>();
            for (int i = 0; i < bdcPlcxQO.getZjh().size(); i++) {
                if (bdcPlcxQO.getZjh().get(i).length() > 15) {
                    zjh15.add(CardNumberTransformation.idCard18to15(bdcPlcxQO.getZjh().get(i)));
                } else {
                    zjh15.add(bdcPlcxQO.getZjh().get(i));
                }
            }
            bdcPlcxQO.setZjh15(zjh15);
            bdcPlcxQO.setSingleZjh("");
        } else {//如果是单个证件号，则赋值15位的
            importFlag = false;
            if (bdcPlcxQO.getSingleZjh().length() > 15) {
                bdcPlcxQO.setSingleZjh15(CardNumberTransformation.idCard18to15(bdcPlcxQO.getSingleZjh()));
            } else {
                bdcPlcxQO.setSingleZjh15(bdcPlcxQO.getSingleZjh());
            }
        }
        List<BdcYcPlcxDTO> resultList = bdcPlcxService.listBdcYcPlcx(bdcPlcxQO, importFlag);
        return resultList;
    }

    /**
     * 分页查询备案信息
     *
     * @param pageDTOJson
     * @param bdcBaxxCxQO
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO ;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *
     * 修改bdcBaxxCxQO通过@RequestBody获取参数，避免get请求头过大问题
     * 修改分页对象为PageDTO，解决Pageable无法传参问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "备案信息批量查询#BAXXPLCX")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageDTOJson", value = "分页参数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "bdcBaxxCxQO", value = "查询信息参数JSON", required = false, paramType = "body")})
    public Page<BdcBaxxPlcxDTO> listBdcBaxxPlcxByPage(@RequestParam(name = "pageDTOJson") String pageDTOJson,
                                                      @RequestBody BdcBaxxCxQO bdcBaxxCxQO) {
        // 默认分页参数为0,10
        Pageable pageable = new PageRequest(0, 10, null);
        if(StringUtils.isNotBlank(pageDTOJson)) {
            PageDTO pageDTO = JSONObject.parseObject(pageDTOJson, PageDTO.class);
            pageable = PageUtils.pageDtoTOPageable(pageDTO);
        }
        // 当有逗号分隔后，说明是导入的方式，则清空单个的条件，sql用in方式
        if (bdcBaxxCxQO.getSingleMsr().indexOf(",") > -1) {
            bdcBaxxCxQO.setQlr(Arrays.asList(bdcBaxxCxQO.getSingleMsr().split(",")));
            bdcBaxxCxQO.setSingleMsr("");

        }
        if (bdcBaxxCxQO.getSingleGmrzjhm().indexOf(",") > -1) {
            bdcBaxxCxQO.setZjh(Arrays.asList(bdcBaxxCxQO.getSingleGmrzjhm().split(",")));
            bdcBaxxCxQO.setSingleGmrzjhm("");
        }

        if (bdcBaxxCxQO.getZdyxhs().indexOf(",") > -1) {
            bdcBaxxCxQO.setZdyxhList(Arrays.asList(bdcBaxxCxQO.getZdyxhs().split(",")));
            bdcBaxxCxQO.setZdyxhs("");
        }
        return bdcPlcxService.listBdcBaxxPlcxByPage(pageable, bdcBaxxCxQO);

    }


    /**
     * 查询备案信息
     *
     * @param bdcBaxxCxQO
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO ;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "备案信息批量查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcBaxxCxQO", value = "bdcBaxxCxQO", required = true, paramType = "body")})
    public List<BdcBaxxPlcxDTO> listBdcBaxxPlcx(@RequestBody BdcBaxxCxQO bdcBaxxCxQO) {

        // 当有逗号分隔后，说明是导入的方式，则清空单个的条件，sql用in方式
        if (bdcBaxxCxQO.getSingleMsr().indexOf(",") > -1) {
            bdcBaxxCxQO.setQlr(Arrays.asList(bdcBaxxCxQO.getSingleMsr().split(",")));
            bdcBaxxCxQO.setSingleMsr("");

        }
        if (bdcBaxxCxQO.getSingleGmrzjhm().indexOf(",") > -1) {
            bdcBaxxCxQO.setZjh(Arrays.asList(bdcBaxxCxQO.getSingleGmrzjhm().split(",")));
            bdcBaxxCxQO.setSingleGmrzjhm("");
        }
        if (bdcBaxxCxQO.getZdyxhs().indexOf(",") > -1) {
            bdcBaxxCxQO.setZdyxhList(Arrays.asList(bdcBaxxCxQO.getZdyxhs().split(",")));
            bdcBaxxCxQO.setZdyxhs("");
        }
        return bdcPlcxService.listBdcBaxxPlcx(bdcBaxxCxQO);

    }

}
