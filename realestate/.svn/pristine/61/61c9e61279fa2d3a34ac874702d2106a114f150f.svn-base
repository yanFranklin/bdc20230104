package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcCqBgLsDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxModelDTO;
import cn.gtmap.realestate.common.core.dto.init.LsgxXzqlModelDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcLsgxRestService;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.core.service.BdcXmLsgxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.core.service.LsgxModelService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史关系
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/3/7.
 * @description
 */
@RestController
@Api(tags = "历史关系数据接口")
public class BdcLsgxRestController extends BaseController implements BdcLsgxRestService{

    @Autowired
    private LsgxModelService lsgxModelService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcXmLsgxService bdcXmLsgxService;
    @Autowired
    private BdcQllxService bdcQllxService;


    /**
     * 根据bdcdyh查询主线所有产权(只查询产权信息)
     * @param bdcdyh
     * @return 树结构
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "根据bdcdyh查询主线所有产权(只查询产权信息)")
    @ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<LsgxModelDTO> getCqList(@PathVariable(name = "bdcdyh") String bdcdyh) throws ReflectiveOperationException {
        return lsgxModelService.getBdcCqLsgxListByBdcdyh(bdcdyh);
    }

    /**
     * @param xmid
     * @return 结构数据
     * @author lst
     * @description 根据项目ID查询一层限制权力信息
     */
    @ApiOperation(value = "根据项目ID查询一层限制权力信息")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public LsgxXzqlModelDTO getXzqlList(@PathVariable(name = "xmid") String xmid,@RequestParam(name = "wlxm",required = false) Integer wlxm) {
        return lsgxModelService.initLsgxXzqlModel(xmid,false,wlxm);
    }

    /**
     * @param xmid@return 结构数据
     * @author lst
     * @description 根据xmid查询所有的限制权力信息
     */
    @ApiOperation(value = "根据项目ID查询所有的限制权力信息")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public LsgxXzqlModelDTO getAllXzqlList(@PathVariable(name = "xmid") String xmid,@RequestParam(name = "wlxm",required = false) Integer wlxm) {
        return lsgxModelService.initLsgxXzqlModel(xmid,true,wlxm);
    }

    /**
     * @param position 位置   top  bottom
     * @param xmid
     * @return
     * @author lst
     * @description 根据xmid和扩展位置查询主线扩展产权(只查询产权信息)
     */
    @ApiOperation(value = "根据xmid和扩展位置查询主线扩展产权(只查询产权信息)")
    @ApiImplicitParams({@ApiImplicitParam(name = "position", value = "扩展位置(top、bottom)", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<LsgxModelDTO> getChangeBdcdyhList(@PathVariable(name = "position") String position, @PathVariable(name = "xmid") String xmid) throws ReflectiveOperationException {
        List<LsgxModelDTO> lsgxModelList = new ArrayList();
        if (StringUtils.isNoneBlank(position, xmid)) {
            BdcXmDO bdcXm = bdcXmService.queryBdcXmByPrimaryKey(xmid);
            if (StringUtils.equals(position, "top")) {
                //如果是最顶的产权，则查询上一手数据
                List<BdcXmLsgxDO> bdcXmRelList = bdcXmLsgxService.queryBdcXmLsgxByXmid(xmid,"b.slsj asc");
                if (CollectionUtils.isNotEmpty(bdcXmRelList)) {
                    for (BdcXmLsgxDO bdcXmRel : bdcXmRelList) {
                        BdcXmDO ybdcxm = bdcXmService.queryBdcXmByPrimaryKey(bdcXmRel.getYxmid());
                        if (ybdcxm != null && !StringUtils.equals(bdcXm.getBdcdyh(), ybdcxm.getBdcdyh())) {
                            lsgxModelList.addAll(lsgxModelService.getBdcCqLsgxListByBdcdyh(ybdcxm.getBdcdyh()));
                        }
                    }
                }
            } else {
                //如果是最底的产权，则查询下一手数据
                List<BdcXmLsgxDO> bdcXmRelList = bdcXmLsgxService.queryBdcXmNextLsgxByXmid(xmid,"b.slsj asc",null);
                if (CollectionUtils.isNotEmpty(bdcXmRelList)) {
                    for (BdcXmLsgxDO bdcXmRel : bdcXmRelList) {
                        BdcXmDO nowBdcxm = bdcXmService.queryBdcXmByPrimaryKey(bdcXmRel.getXmid());
                        if (nowBdcxm != null && !StringUtils.equals(bdcXm.getBdcdyh(), nowBdcxm.getBdcdyh())) {
                            lsgxModelList.addAll(lsgxModelService.getBdcCqLsgxListByBdcdyh(nowBdcxm.getBdcdyh()));
                        }
                    }
                }
            }
        }else{
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return lsgxModelList;
    }

    /**
     * @param bdcXmLsgxQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询项目的一级历史关系数据
     */
    @Override
    @ApiOperation(value = "查询项目的一级历史关系数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmLsgxQO", value = "历史关系参数", dataType = "BdcXmLsgxQO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXmLsgxDO> listXmLsgxByXmid(@RequestBody BdcXmLsgxQO bdcXmLsgxQO) {
        return bdcXmLsgxService.listBdcXmLsgx(bdcXmLsgxQO);
    }


    /**
     * 批量查询项目的一级历史关系数据
     * @param bdcXmLsgxQO
     * @return
     */
    @Override
    @ApiOperation(value = "批量查询项目的一级历史关系数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmLsgxQO", value = "历史关系参数", dataType = "BdcXmLsgxQO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXmLsgxDO> listXmLsgxByXmids(@RequestBody BdcXmLsgxQO bdcXmLsgxQO) {
        return bdcXmLsgxService.listBdcXmLsgxs(bdcXmLsgxQO);
    }

    /**
     * @param slbh
     * @param gzlslid
     * @param zxyql
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 查询项目的一级历史关系数据
     */
    @Override
    @ApiOperation(value = "根据受理编号或者工作流实例id查询项目的一级历史关系数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXmLsgxDO> listXmLsgxBySlid(@RequestParam(name = "gzlslid") String gzlslid) {
        return bdcXmLsgxService.queryBdcXmLsgxList("", gzlslid, null);
    }

    /**
     * @param gxids
     * @return 数量
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 删除历史关系
     */
    @Override
    @ApiOperation(value = "删除历史关系")
    @ApiImplicitParams({@ApiImplicitParam(name = "gxids", value = "历史关系ID",allowMultiple = true,dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public int deleteLsgxs(String[] gxids) {
        return bdcXmLsgxService.deleteLsgxByIds(gxids);
    }

    /**
     * 嵌套获取下手项目关系信息(获取限制权利)
     *
     * @param xmid
     * @param list
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "嵌套获取下手项目关系信息(获取限制权利)")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "list", value = "list", required = false, dataType = "list", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXmLsgxDO> nextBdcXmLsgx(@RequestParam(name = "xmid") String xmid, @RequestBody List<BdcXmLsgxDO> list,@RequestParam(name = "wlxm",required = false) Integer wlxm) {
        return bdcXmLsgxService.nextBdcXmLsgx(xmid,list,wlxm,false);
    }

    @Override
    @ApiOperation(value = "根据不动产单元号获取产权变更历史")
    @ResponseStatus(code = HttpStatus.OK)
    public List<BdcCqBgLsDTO> listBdcCqBgLs(@PathVariable("bdcdyh") String bdcdyh){
        return lsgxModelService.listBdcCqBgLs(bdcdyh);

    }


}
