package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJfxxQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcCfxxRestService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.service.BdcCfxxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 查封信息查询
 */
@RestController
public class BdcCfxxRestController implements BdcCfxxRestService {

    @Autowired
    private BdcCfxxService bdcCfxxService;
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @param pageable
     * @param bdcCfxxQOJson
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询查封信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询查封信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "bdcCfxxQOJson", value = "查封信息参数JSON", required = false, paramType = "query")})
    public Page<BdcCfxxDTO> listBdcCfByPage(Pageable pageable, String bdcCfxxQOJson) {
        BdcCfxxQO bdcCfxxQO = new BdcCfxxQO();
        if (StringUtils.isNotBlank(bdcCfxxQOJson)) {
            bdcCfxxQO = JSONObject.parseObject(bdcCfxxQOJson, BdcCfxxQO.class);
        }
        return bdcCfxxService.listBdcCfxxByPage(pageable, bdcCfxxQO);
    }


    /**
     * @param bdcXfxxQOJson
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询续封信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询查封信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCfxxQOJson", value = "查封信息参数JSON", required = false, paramType = "query")})
    public List<Map> listBdcXf(String bdcXfxxQOJson) {
        BdcCfxxQO bdcxfxxQO = new BdcCfxxQO();
        if (StringUtils.isNotBlank(bdcXfxxQOJson)) {
            bdcxfxxQO = JSONObject.parseObject(bdcXfxxQOJson, BdcCfxxQO.class);
        }
        return bdcCfxxService.listBdcXfxx(bdcxfxxQO);
    }

    /**
     * @param bdcCfxxQOJson
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询查封到期信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询查封到期信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcCfxxQOJson", value = "查封信息参数JSON", required = false, paramType = "query")})
    public Object bdcCfDqList(Pageable pageable, String bdcCfxxQOJson) {
        BdcCfxxQO bdccfxxQO = new BdcCfxxQO();
        if (StringUtils.isNotBlank(bdcCfxxQOJson)) {
            bdccfxxQO = JSONObject.parseObject(bdcCfxxQOJson, BdcCfxxQO.class);
        }
        return bdcCfxxService.bdcCfDqList(pageable, bdccfxxQO);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询查封
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public List listBdcCfByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcCfxxService.listBdcCfByXmid(xmid);
    }

    /**
     * @param bdcdyh
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询查封信息
     */
    @Override
    public List listBdccfByBdcdyh(@RequestParam(name = "bdcdyh") String bdcdyh) {
        return bdcCfxxService.listBdcCfByBdcdyh(bdcdyh);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询查封信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public List<BdcCfDO> listBdcCfByBdcdyhs(@RequestBody List<String> bdcdyhList) {
        return bdcCfxxService.listBdcCfByBdcdyhs(bdcdyhList);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public List<BdcCfDTO> listBdcCfxx(@RequestBody BdcCfxxQO bdcCfxxQO) {
        return bdcCfxxService.listBdcCfxx(bdcCfxxQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public List<BdcCfDTO> listCfxxYcfbh(@RequestBody BdcCfxxQO bdcCfxxQO) {
        return bdcCfxxService.getBdcCfxxAndYcfbh(bdcCfxxQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public List<BdcCfDO> listYcfxxByGzlslid(@RequestParam(name = "gzlslid") String gzlslid) {
        return bdcCfxxService.listYcfxxByGzlslid(gzlslid);

    }

    /**
     * @param bdcCfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查封查询罗辑调整-根据查封顺序和查封起始时间排序
     * @date : 2021/11/1 10:58
     */
    @Override
    public List<BdcCfDTO> listBdcCfxxByCfsx(@RequestBody BdcCfxxQO bdcCfxxQO) {
        return bdcCfxxService.listBdcCfxxByCfsx(bdcCfxxQO);
    }

    /**
     * @param jfxxQO
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新解封查封数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新解封查封数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "jfxxQO", value = "jfxxQO信息参数JSON", required = false, paramType =
            "upate")})
    public void jfBdcCf(String jfxxQO) {
        BdcJfxxQO bdcJfxxQO = new BdcJfxxQO();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter DATE_FORMATHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String nowTimeStr = localDateTime.format(DATE_FORMATHMS);
        String userName = userManagerUtils.getCurrentUserName();
        String orgName = userManagerUtils.getOrganizationByUserName(userName);
        String userAlias =  userManagerUtils.getCurrentUser().getAlias();

        if (StringUtils.isNotBlank(jfxxQO)) {
            bdcJfxxQO = JSONObject.parseObject(jfxxQO, BdcJfxxQO.class);
            bdcJfxxQO.setJfdjsj(nowTimeStr);
            bdcJfxxQO.setJfsj(nowTimeStr);
            bdcJfxxQO.setJfdbr(userAlias);
            bdcJfxxQO.setJfjg(orgName);
        }
        if(StringUtils.isEmpty(bdcJfxxQO.getXmid())){
            throw new MissingArgumentException("缺少项目id");
        }
        bdcCfxxService.jfBdcCf(bdcJfxxQO);
    }


    /**
     * @param jfxxQO
     * @return org.springframework.data.domain.Page
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新查封数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新查封数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "jfxxQO", value = "jfxxQO信息参数JSON", required = false, paramType =
            "upate")})
    public void editBdcCf(String jfxxQO) {
        BdcJfxxQO bdcJfxxQO = new BdcJfxxQO();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter DATE_FORMATHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String nowTimeStr = localDateTime.format(DATE_FORMATHMS);
        String userName = userManagerUtils.getCurrentUserName();
        String orgName = userManagerUtils.getOrganizationByUserName(userName);

        if (StringUtils.isNotBlank(jfxxQO)) {
            bdcJfxxQO = JSONObject.parseObject(jfxxQO, BdcJfxxQO.class);
            bdcJfxxQO.setJfdjsj(nowTimeStr);
            bdcJfxxQO.setJfsj(nowTimeStr);
            bdcJfxxQO.setJfdbr(userName);
            bdcJfxxQO.setJfjg(orgName);
        }
        if(StringUtils.isEmpty(bdcJfxxQO.getXmid())){
            throw new MissingArgumentException("缺少项目id");
        }
        bdcCfxxService.editBdcCf(bdcJfxxQO);
    }


    /**
     * 通过bdcdyh查询续封未超期的数量
     * @param bdcdyhs
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public int queryWdqXfByBdcyhs(String bdcdyhs,String xmids){
        if(StringUtils.isNotEmpty(bdcdyhs)){
            List list = Arrays.asList(bdcdyhs.split(","));
            List xmidlist = Arrays.asList(xmids.split(","));
            Map map = new HashMap();
            map.put("bdcdyh",list);
            map.put("xmid",xmidlist);
            return bdcCfxxService.queryWdqXfByBdcyhs(map);
        }
        return 0;
    }

}
