package cn.gtmap.realestate.inquiry.web.rest.changzhou;

import cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou.BdcFpDyRestService;
import cn.gtmap.realestate.inquiry.service.changzhou.BdcFpDyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 发票打印数据源服务<strong>常州<strong/>
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 6:16 下午 2020/11/30
 */
@RestController
@Api(tags = "常州发票打印数据源服务")
public class BdcFpDyRestController implements BdcFpDyRestService {
    @Autowired
    private BdcFpDyService bdcFpDyService;

    /**
     * 常州缴款书主表 数据源
     *
     * @param sfxxid sfxxid
     * @param sfxmdm sfxmdm「逗号隔开」
     * @return 主表数据
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("常州缴款书主表 数据源服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "fplb", value = "发票类别「1，9」", required = true, paramType = "query")
    })
    public Object getJksZbSjy(@RequestParam String sfxxid, @RequestParam String fplb) {
        return bdcFpDyService.getJksZbSjy(sfxxid, fplb);
    }

    /**
     * 常州缴款书子表 数据源
     *
     * @param sfxxid sfxxid
     * @param fplb   fplb「1，9」
     * @return 子表数据
     */
    @Override
    @ApiOperation("常州缴款书子表 数据源服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfxxid", value = "收费信息id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "fplb", value = "发票类别「1，9」", required = true, paramType = "query")
    })
    public Object getJksZb(@RequestParam String sfxxid, @RequestParam String fplb) {
        return bdcFpDyService.getJksZb(sfxxid, fplb);
    }

    /**
     * 查询收费信息「单个」<br>
     * 基于 sfxx 基础上额外查询 qlrmc，gzldymc 和 收费项目合计金额
     *
     * @param slbh   slbh
     * @param sfxmdm sfxmdm 「逗号隔开」
     * @return {List} 收费信息集合
     */
    @Override
    @ApiOperation("查询收费信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "sfxmdm", value = "收费项目代码「逗号隔开」", required = true, paramType = "query")
    })
    public Object queryFpcxSfxx(@RequestParam String slbh, @RequestParam String sfxmdm) {
        return bdcFpDyService.queryFpcxSfxx(slbh, sfxmdm);
    }

    /**
     * 批量查询收费信息<br>
     * 区分权利人，查询 jkfs 为 扫码支付并且 jspzfph 和 fssrfph 均为空的收费信息
     *
     * @param qlrlb qlrlb
     * @param fplb  fplb
     * @return {sfxx} 收费信息
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @Override
    public Object listFpcxSfxx(@RequestParam(name = "qlrlb") String qlrlb, @RequestParam(name = "fplb") String fplb) {
        return bdcFpDyService.listFpcxSfxx(qlrlb, fplb);
    }

    /**
     * 批量查询收费信息<br>
     * 核对页面用
     *
     * @param sfxxidList
     * @param fplb       fplb
     * @return {sfxx} 收费信息
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    @Override
    public Object listFpcxSfxx(@RequestBody List<String> sfxxidList, @RequestParam(name = "fplb") String fplb) {
        return bdcFpDyService.listFpcxSfxx(sfxxidList, fplb);
    }
}
