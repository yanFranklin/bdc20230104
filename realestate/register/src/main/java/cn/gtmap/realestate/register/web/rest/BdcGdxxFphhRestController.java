package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxFphhDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZszmQO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxDahQO;
import cn.gtmap.realestate.common.core.qo.register.BdcGdxxFphhQO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcGdxxFphhRestService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcDjlxDjxlQllxVO;
import cn.gtmap.realestate.register.service.BdcGdxxFphhService;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
 * @version 1.0, 2021/10/30
 * @description 档案分配盒号
 */
@RestController
@Api(tags = "不动产归档服务分配盒号接口")
public class BdcGdxxFphhRestController extends BaseController implements BdcGdxxFphhRestService {
    @Autowired
    BdcGdxxFphhService bdcGdxxFphhService;

    /**
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 分配盒号，查询结果中全部未分配的，进行分配操作
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分配盒号")
    public String fphh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO) {
        return bdcGdxxFphhService.fphhMethod(bdcGdxxFphhQO);
    }

    /**
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 查询是否分配盒号，数据返回不为空，则给提示语
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询已分配盒号校验")
    @Override
    public List<BdcGdxxFphhDO> sffphh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO) {
        return bdcGdxxFphhService.sffphh(bdcGdxxFphhQO);
    }

    /**
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 页面下拉框需要展示盒号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "页面下拉框展示盒号")
    public List<Integer> listhh(@RequestParam(required = false,name = "xzdm") String xzdm,
                                @RequestParam(required = false,name = "xzmc") String xzmc,
                                @RequestParam(required = false,name = "nf") String nf) {
        return bdcGdxxFphhService.listhh(xzdm, xzmc, nf);
    }

    /**
     * @param bdcGdxxFphhDO
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 新增归档分配盒子信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "新增归档分配盒子信息")
    public int insertBdcGdxxFphh(@RequestBody BdcGdxxFphhDO bdcGdxxFphhDO) {
        return bdcGdxxFphhService.insertBdcGdxxFphh(bdcGdxxFphhDO);
    }

    /**
     * @param bdcGdxxFphhDO
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 根据xmid更新归档信息, 存在则更新不存在则插入
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "单条更新分配盒号信息")
    public int updateBdcGdxxFphh(@RequestBody BdcGdxxFphhDO bdcGdxxFphhDO) {
        return bdcGdxxFphhService.updateBdcGdxxFphh(bdcGdxxFphhDO);
    }

    /**
     * @param bdcGdxxFphhDOList
     * @return
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 页面批量更新盒号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "批量更新归档分配盒号信息")
    public void batchUpdateGdxxFphh(List<BdcGdxxFphhDO> bdcGdxxFphhDOList) {
        bdcGdxxFphhService.batchUpdateGdxxFphh(bdcGdxxFphhDOList);
    }

    /**
     * @param bdcGdxxFphhQO
     * @return List<BdcGdxxFphhDO>
     * @author <a href="mailto:wnagyongming@gtmap.cn>wangyongming</a>"
     * @description 查询归档信息集合
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询归档信息分配盒号集合")
    public List<BdcGdxxFphhDO> listBdcGdxxFphh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO) {
        return bdcGdxxFphhService.listBdcGdxxFphh(bdcGdxxFphhQO);
    }

    /**
     * @param pageable
     * @return Page<BdcDjlxDjxlQllxVO>
     * @author <a href ="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 分页获取 全参数
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页获取分配盒号集合全参数")
    public Object listBdcGdxxFphhPageAll(Pageable pageable, @RequestParam(name = "paramJSON", required = false) String paramJSON) {
        Page<BdcGdxxFphhDO> gdxxTableData = bdcGdxxFphhService.listGdxxTable(pageable, JSON.parseObject(paramJSON, BdcGdxxDahQO.class));
        return super.addLayUiCode(gdxxTableData);
    }

    /**
     * @param xmid
     * @return
     * @author <a href ="mailto:wangyongming@gtmap.cn">wangyongming</a>
     * @description 根据项目ID搜索盒号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目id获取盒号")
    public BdcGdxxFphhDO getDaIdById(@RequestParam(value = "gdxxid") String gdxxid) {
       return bdcGdxxFphhService.getDaIdById(gdxxid);
    }

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGdxxFphhDO 档案信息
     * @return String 档案信息记录主键ID
     * @description 保存档案号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存档案号")
    @ApiImplicitParam(name = "bdcGdxxFphhDO", value = "档案信息", required = true, paramType = "body")
    public String saveDah(@RequestBody BdcGdxxFphhDO bdcGdxxFphhDO) {
        return bdcGdxxFphhService.saveDah(bdcGdxxFphhDO);
    }

    /**
     * @param bdcGdxxFphhQO
     * @return
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取档案号list")
    public List<BdcGdxxFphhDO> getBdcGdxxFphhDOList(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO) {
        return bdcGdxxFphhService.getBdcGdxxFphhDOList(bdcGdxxFphhQO);
    }

    /**
     * @param bdcGdxxFphhQO
     * @return
     * @author <a href ="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根获取当日最大流水号")
    public String getMaxLsh(@RequestBody BdcGdxxFphhQO bdcGdxxFphhQO){
        return bdcGdxxFphhService.getMaxLsh(bdcGdxxFphhQO);
    }

}
