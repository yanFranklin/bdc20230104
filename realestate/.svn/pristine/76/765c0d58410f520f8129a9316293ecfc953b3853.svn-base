package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcFctdPpgxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXnbdcdyhGxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcLzQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcPpRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.service.BdcBdcdyService;
import cn.gtmap.realestate.init.core.service.BdcFctdPpgxService;
import cn.gtmap.realestate.init.core.service.BdcXnbdcdyhGxService;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不动产匹配和落宗相关接口
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/2/28.
 * @description
 */
@RestController
@Api(tags = "不动产匹配服务接口")
public class BdcPpRestController extends BaseController implements BdcPpRestService {

    @Autowired
    private BdcXnbdcdyhGxService bdcXnbdcdyhGxService;
    @Autowired
    private BdcFctdPpgxService bdcFctdPpgxService;
    @Autowired
    private BdcBdcdyService bdcBdcdyService;

    /**
     * 通过传入参数进行落宗处理
     * @param bdcLzQO 单元号落宗QO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "落宗服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcLzQO", value = "单元号落宗QO", required = true, dataType = "BdcLzQO", paramType = "body"),
    })
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void lz(@RequestBody BdcLzQO bdcLzQO) throws Exception {
        boolean lzcg = bdcXnbdcdyhGxService.lz(
                bdcLzQO.getLsdyh(),
                bdcLzQO.getBdcdyh(),
                null,
                bdcLzQO.getGxbdcdyfwlx(),
                bdcLzQO.getIp(),
                CommonConstantUtils.GXLB_DYHLZ
        );
        if(lzcg){
            //回写状态到权籍
            bdcBdcdyService.syncBdcdyZtByBdcdyh(bdcLzQO.getBdcdyh(),bdcLzQO.getQjgldm());
        }
    }

    /**
     * 通过传入参数进行取消落宗处理
     *
     * @param bdcdyh   不动产单元号
     * @param qxlzxmid 取消落宗的项目ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "取消落宗服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qxlzxmid", value = "取消落宗的项目ID", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void qxlz(@PathVariable(name = "bdcdyh") String bdcdyh, @PathVariable(name = "qxlzxmid") String qxlzxmid,@RequestParam(name = "qjgldm", required = false) String qjgldm) throws Exception {
        boolean qxlzcg = bdcXnbdcdyhGxService.qxlz(bdcdyh,qxlzxmid);
        if(qxlzcg){
            //回写状态到权籍
            bdcBdcdyService.syncBdcdyZtByBdcdyh(bdcdyh,qjgldm);
        }
    }

    /**
     * 房产证和土地证进行匹配记录关系
     *
     * @param fcxmid 房产项目id
     * @param tdxmid 土地项目id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "房产证和土地证匹配服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "fcxmid", value = "房产项目ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tdxmid", value = "土地项目ID", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public void pptd(@RequestParam(name = "fcxmid") String fcxmid, @RequestParam(name = "tdxmid") String tdxmid) throws Exception {
        bdcFctdPpgxService.pptd(fcxmid,tdxmid,null);
        //回写状态到权籍
        bdcBdcdyService.syncBdcdyZtByXmid(fcxmid);
    }

    @Override
    public void pptdwithip(@RequestParam(name = "fcxmid") String fcxmid,
                           @RequestParam(name = "tdxmid") String tdxmid,
                           @RequestParam(name = "requestClientRealIP",required = false) String requestClientRealIP) throws Exception {
        bdcFctdPpgxService.pptd(fcxmid,tdxmid,requestClientRealIP);
        //回写状态到权籍
        bdcBdcdyService.syncBdcdyZtByXmid(fcxmid);
    }

    /**
     * 取消房产证和土地证匹配关系
     * @param fcxmid 房产项目id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "房产证和土地证取消匹配服务")
    @ApiImplicitParam(name = "fcxmid", value = "房产项目ID",  required = true, dataType = "String", paramType = "query")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void qxpptd(@RequestParam(name = "fcxmid") String fcxmid) throws Exception {
        bdcFctdPpgxService.qxpptd(fcxmid);
        //回写状态到权籍
        bdcBdcdyService.syncBdcdyZtByXmid(fcxmid);
    }


    /**
     * 根据房产项目ID去获取匹配关系
     * @param fcxmid
     * @return List<BdcFctdPpgxDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "根据房产项目ID获取匹配关系")
    @ApiImplicitParam(name = "fcxmid", value = "房产项目ID",  required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcFctdPpgxDO> queryBdcFctdPpgx(@PathVariable(name = "fcxmid") String fcxmid) {
        return bdcFctdPpgxService.queryBdcFctdPpgx(fcxmid);
    }

    /**
     * 根据房产项目ID去获取匹配关系
     *
     * @param tdxmid
     * @return List<BdcFctdPpgxDO>
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     */
    @Override
    @ApiOperation(value = "根据土地项目ID获取匹配关系")
    @ApiImplicitParam(name = "tdxmid", value = "土地项目id",  required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    public List<BdcFctdPpgxDO> queryBdcFctdPpgxByTdxmid(@PathVariable(name = "tdxmid")String tdxmid) {
        return bdcFctdPpgxService.queryBdcFctdPpgxByTdxmid(tdxmid);
    }

    /**
     * 根据虚拟单元号项目id去获取虚拟单元号关系
     *
     * @param xndyhxmid
     * @return List<BdcXnbdcdyhGxDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "根据虚拟单元号项目id去获取虚拟单元号关系")
    @ApiImplicitParam(name = "xndyhxmid", value = "临时单元号",  required = true, dataType = "String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXnbdcdyhGxDO> queryBdcXnbdcdyhGxByXmid(@PathVariable(name = "xndyhxmid") String xndyhxmid) {
        return bdcXnbdcdyhGxService.queryBdcXnbdcdyhGx(xndyhxmid);
    }

    /**
     * 根据条件查询
     *@author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     *@param bdcXnbdcdyhGxDO
     *@return List<BdcXnbdcdyhGxDO>
     *@description
     */
    @Override
    @ApiOperation(value = "根据条件查询虚拟不动产单元号信息")
    @ResponseStatus(HttpStatus.OK)
    public List<BdcXnbdcdyhGxDO> queryBdcXnbdcdyhByCondition(@RequestBody BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO) {
        return bdcXnbdcdyhGxService.queryBdcXnbdcdyhByCondition(bdcXnbdcdyhGxDO);
    }

    /**
     * @param bdcXmDOList
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新单元号信息, 更新权利表，项目表，证书表单元号
     * @date : 2021/12/30 17:11
     */
    @Override
    public void updateBdcdyh(@RequestBody List<BdcXmDO> bdcXmDOList, @RequestParam(value = "bdcdyh") String bdcdyh, @RequestParam(value = "qjgldm",required = false) String qjgldm) throws Exception {
        bdcXnbdcdyhGxService.updateBdcdyh(bdcXmDOList, bdcdyh,qjgldm, CommonConstantUtils.SF_S_DM,null);
    }

    @Override
    public void lslz(@RequestBody BdcLzQO bdcLzQO) throws Exception {
        bdcXnbdcdyhGxService.lz(
                bdcLzQO.getLsdyh(),
                bdcLzQO.getBdcdyh(),
                null,
                bdcLzQO.getGxbdcdyfwlx(),
                bdcLzQO.getIp(),
                CommonConstantUtils.GXLB_DYHLZ
        );
    }

    @Override
    public void qxlslz(@PathVariable(name = "bdcdyh") String bdcdyh,
                       @PathVariable(name = "qxlzxmid") String qxlzxmid,
                       @RequestParam(name = "qjgldm", required = false) String qjgldm) throws Exception {
        bdcXnbdcdyhGxService.qxlz(bdcdyh,qxlzxmid);
    }

    @Override
    @ApiOperation(value = "插入单元号匹配关系")
    @ResponseStatus(HttpStatus.OK)
    public void insertXndyhGx(@RequestBody List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOS){
        bdcXnbdcdyhGxService.insertXndyhGx(bdcXnbdcdyhGxDOS);

    }

}
