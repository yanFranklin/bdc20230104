package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlqtzkFjQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcZsInitRestService;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.core.service.BdcXtQlqtzkFjPzService;
import cn.gtmap.realestate.init.core.service.BdcZsService;
import cn.gtmap.realestate.init.core.thread.InitZsQlqtzkFjThread;
import cn.gtmap.realestate.init.service.other.BdcSynchService;
import cn.gtmap.realestate.init.service.other.InitBdcZsService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.util.DozerUtils;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成证书证明
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/14.
 * @description
 */
@RestController
@Api(tags = "不动产证书服务接口")
public class BdcZsInitRestController extends BaseController implements BdcZsInitRestService {
    @Autowired
    private InitBdcZsService initBdcZsService;
    @Autowired
    private BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private ThreadEngine threadEngine;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private BdcSynchService bdcSynchService;

    /**
     * 初始化不动产权证
     *
     * @param processInsId
     * @throws Exception
     */
    @ApiOperation(value = "生成证书")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "生成成功"), @ApiResponse(code = 500, message = "生成失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zsyl", value = "证书预览", required = false, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public List<BdcZsDO> initBdcqzs(@PathVariable("processInsId") String processInsId,@RequestParam(value = "zsyl",required = false)boolean zsyl) throws Exception {
        return initBdcZsService.initBdcZs(bdcXmService.listBdCshSl(processInsId),zsyl,false,null,false);
    }
    /**
     * 初始化不动产权证（数据补录）  不生成权利其他状况和附记
     * @param processInsId
     * @throws Exception
     */
    @ApiOperation(value = "生成证书(数据补录)")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "生成成功"), @ApiResponse(code = 500, message = "生成失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zsyl", value = "证书预览", required = false, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public List<BdcZsDO> initBdcqzsSjbl(@PathVariable("processInsId") String processInsId,@RequestParam(value = "zsyl",required = false)boolean zsyl) throws Exception {
        return initBdcZsService.initBdcZs(bdcXmService.listBdCshSl(processInsId),zsyl,false,null,true);
    }
    /**
     * 初始化不动产权证
     *
     * @param xmid
     * @throws Exception
     */
    @ApiOperation(value = "生成证书")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "生成成功"), @ApiResponse(code = 500, message = "生成失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zsyl", value = "证书预览", required = false, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public List<BdcZsDO> initBdcqz(@PathVariable("xmid") String xmid, @RequestParam(value = "zsyl", required = false) boolean zsyl) throws Exception {
        List<BdcCshFwkgSlDO> list = new ArrayList<>();
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmService.queryCshFwkgSl(xmid);
        //判断当前流程是否有多个流程生成一本正 08-02 wzjStart
        BdcXmDO bdcXmDO = bdcXmService.queryBdcXmByPrimaryKey(xmid);
        boolean plybz=true;
        if (bdcXmDO != null && StringUtils.isNotBlank(bdcXmDO.getGzlslid())) {
            if(bdcCshFwkgSlDO.getZsxh()==null){
                list.add(bdcCshFwkgSlDO);
            }else{
                List<BdcCshFwkgSlDO> listBdCshSl = bdcXmService.listBdCshSl(bdcXmDO.getGzlslid());
                if (CollectionUtils.isNotEmpty(listBdCshSl)) {
                    for (BdcCshFwkgSlDO slDOdo : listBdCshSl) {
                        //sly 1、当前xmid证书必须有 2、顺序号为空则不判断是否生成一本正
                        if (StringUtils.equals(slDOdo.getId(), xmid) || (bdcCshFwkgSlDO.getZsxh() != null && bdcCshFwkgSlDO.getZsxh().equals(slDOdo.getZsxh()))) {
                            list.add(slDOdo);
                        }else{
                            plybz=false;
                        }
                    }
                }
            }
        }
        //wzjEnd
        return initBdcZsService.initBdcZs(list, zsyl, false,plybz,false);
    }

    @ApiOperation(value = "生成默认附记和权利其他状况")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "更新成功"), @ApiResponse(code = 500, message = "更新失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "mode", value = "更新模式（1.全部更新 2.仅更新权利其他状况 3.仅更新证书附记）", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void updateQlqtzkFj(@PathVariable("xmid") String xmid, @PathVariable("mode") String mode) {
        Boolean updateFlag = bdcXtQlqtzkFjPzService.updateQlqtzkFjByMode(xmid, mode);
        // 如果没有更新数据
        if (!updateFlag) {
            throw new IllegalArgumentException(messageProvider.getMessage("message.noparameter"));
        }
    }

    /**
     * @param processInsId 工作流实例ID
     * @param mode         模式(1:全部更新 2:仅更新权利其他状况 3:仅更新证书附记)
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入工作流实例ID更新权利其他状况以及附记
     */
    @ApiOperation(value = "生成默认附记和权利其他状况")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "更新成功"), @ApiResponse(code = 500, message = "更新失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "mode", value = "更新模式（1.全部更新 2.仅更新权利其他状况 3.仅更新证书附记）", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void updateQlqtzkFjPl(@PathVariable("processInsId") String processInsId, @PathVariable("mode") String mode) {
        List<BdcXmDTO> list=bdcXmService.listXmBfxx(processInsId,null);
        if(CollectionUtils.isNotEmpty(list)){
            boolean sfbjs=list.size()>1 ? false : true;
            //循环初始化附记
            List<CommonThread> listThread = new ArrayList();
            //业务循环
            for (BdcXmDTO bdcXmDO : list) {
                InitZsQlqtzkFjThread initZsQlqtzkFjThread=new InitZsQlqtzkFjThread(bdcXtQlqtzkFjPzService,bdcXmDO.getXmid(),mode);
                initZsQlqtzkFjThread.setSfbjs(sfbjs);
                listThread.add(initZsQlqtzkFjThread);
            }
            //多线程处理操作
            threadEngine.excuteThread(listThread, true,null);
        }
    }

    /**
     * 初始化不动产权证数量
     * @param processInsId
     * @return 数量
     * @throws Exception
     */
    @ApiOperation(value = "获取生成证书数量")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "生成成功"), @ApiResponse(code = 500, message = "生成失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int initLcBdcqzSl(@PathVariable("processInsId") String processInsId) throws Exception {
        List<BdcZsDO> list=initBdcZsService.initBdcZs(bdcXmService.listBdCshSl(processInsId),false,true,null,false);
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        return list.size();
    }

    /**
     * @param: processInsId 流程实例id
     * @return: int 证书数量
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 通过流程实例id获取当前流程的证书数量
     */
    @ApiOperation(value = "通过流程实例id获取当前流程的拥有房地产权利类型的证书数量")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParam(name = "processInsId", value = "流程实例id", required = true, dataType = "java.lang.String", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int countLcBdcqzWithFdcq(@PathVariable("processInsId") String processInsId) throws Exception {
        return initBdcZsService.countBdcZsWithFdcq(processInsId);
    }

    /**
     * 初始化不动产权证数量
     * @param xmid
     * @return 数量
     * @throws Exception
     */
    @ApiOperation(value = "获取生成证书数量")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "生成成功"), @ApiResponse(code = 500, message = "生成失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public int initXmBdcqzSl(@PathVariable("xmid") String xmid) throws Exception {
        List<BdcCshFwkgSlDO> list = new ArrayList<>();
        BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmService.queryCshFwkgSl(xmid);
        if (bdcCshFwkgSlDO != null) {
            list.add(bdcCshFwkgSlDO);
        }
        List<BdcZsDO> listZs=initBdcZsService.initBdcZs(list,false,true,null,false);
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        return listZs.size();
    }

    /**
     * 通过传入processInsId清空项目表的权利其他状况(清空抵押项目)
     * @param processInsId 工作流实例id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入processInsId清空项目表的权利其他状况(清空抵押项目)
     */
    @Override
    public void clearDyaXmQlqtzkAndYzh(@PathVariable("processInsId") String processInsId) {
        if(StringUtils.isBlank(processInsId)){
            throw new MissingArgumentException("缺失参数异常");
        }
        BdcXmDO param=new BdcXmDO();
        param.setGzlslid(processInsId);
        param.setQllx(CommonConstantUtils.QLLX_DYAQ_DM);
        BdcXmDO value=new BdcXmDO();
        value.setBfqlqtzk(StringUtils.EMPTY);
        value.setYcqzh(StringUtils.EMPTY);
        initBdcZsService.updateXmVal(param,value);
    }

    /**
     * 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里(南通  抵押权处理)
     * @param processInsId 工作流实例id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里
     */
    @Override
    public void initYbdcqzhToQlqtzk(@PathVariable("processInsId") String processInsId) {
        initBdcZsService.updateQlqtzk(processInsId,false);
    }
    /**
     * 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里(海门  抵押权处理)
     * @param processInsId 工作流实例id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入processInsId将原不动产权证号追加到项目和证书的权利其他状况里（大于等于两条显示详见抵押清单）
     */
    @Override
    public void initOnlyOneYbdcqzhToQlqtzk(@PathVariable("processInsId") String processInsId) {
        initBdcZsService.updateQlqtzk(processInsId,true);
    }

    @Override
    public void initCommonYbdcqzhToQlqtzk(@PathVariable("processInsId") String processInsId){
        initBdcZsService.updateCommonQlqtzk(processInsId);

    }

    /**
     * 通过传入项目id清空权利附记
     *
     * @param xmid 项目id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入项目id清空权利附记
     */
    @ApiOperation(value = "通过传入项目id清空权利附记")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "成功"), @ApiResponse(code = 500, message = "失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void clearQlFj(@PathVariable("xmid") String xmid) {
        bdcQllxService.updateQlFj(xmid,null);
    }

    /**
     * 通过传入工作流实例ID生成权利附记
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入工作流实例ID生成权利附记
     */
    @ApiOperation(value = "通过传入工作流实例ID生成权利附记")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "更新成功"), @ApiResponse(code = 500, message = "更新失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path") })
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void updateQlFj(@PathVariable("gzlslid") String gzlslid) {
        List<BdcXmDTO> list=bdcXmService.listXmBfxx(gzlslid,null);
        if(CollectionUtils.isNotEmpty(list)){
            //业务循环
            for (BdcXmDTO bdcXmDO : list) {
                String fj=queryQlqtzkFj(bdcXmDO.getXmid(), CommonConstantUtils.XT_QLQTZK_FJ_MODE_FJ);
                if(StringUtils.isNotBlank(fj)){
                    bdcQllxService.updateQlFj(bdcXmDO.getXmid(),fj);
                }
            }
        }
    }

    /**
     * 通过传入项目id获取权利其他状况以及附记
     * @param xmid 项目id
     * @param mode 模式(2:权利其他状况 3:附记)
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入项目id获取权利其他状况以及附记
     */
    @ApiOperation(value = "通过传入项目id获取权利其他状况以及附记")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "mode", value = "模式（2.权利其他状况 3.附记）", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public String queryQlqtzkFj(@PathVariable("xmid") String xmid, @PathVariable("mode") String mode) {
        String mb="";
        if(StringUtils.isBlank(xmid) || StringUtils.isBlank(mode)){
            throw new MissingArgumentException("缺失参数异常");
        }
        if(Constants.XT_QLQTZK_FJ_MODE_QLQTZK.equals(mode)){
            mb=bdcXtQlqtzkFjPzService.getQlqtzkMessageByXmid(xmid);
        }else if(Constants.XT_QLQTZK_FJ_MODE_FJ.equals(mode)){
            mb=bdcXtQlqtzkFjPzService.getFjMessageByXmid(xmid);
        }
        return mb;
    }

    /**
     * @param bdcQlqtzkFjQO 权利其他和附记操作QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据证书信息更新项目的权利其他状况或权利附记
     */
    @ApiOperation(value = "根据证书信息更新项目的权利其他状况或权利附记")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcQlqtzkFjQO", value = "权利其他和附记操作QO", required = true, dataType = "BdcQlqtzkFjQO", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void updateQlqtzkAndFj(@RequestBody BdcQlqtzkFjQO bdcQlqtzkFjQO) {
        bdcXtQlqtzkFjPzService.updateQlqtzkFjByQO(bdcQlqtzkFjQO);
    }

    /**
     * 查询生成的不动产权证
     * @param xmid
     * @return 集合
     */
    @ApiOperation(value = "通过传入项目id获取证书信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcZsDO> queryBdcqz(@PathVariable("xmid") String xmid){
        return bdcZsService.queryBdcZsByXmid(xmid);
    }

    /**
     * 通过传入processInsId删除证书
     * @param processInsId 工作流实例id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入processInsId删除证书
     */
    @ApiOperation(value = "通过传入processInsId删除证书")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "path") })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcZs(@PathVariable("processInsId") String processInsId) throws Exception {
        //删除证书
        bdcZsService.deleteZs(processInsId);
        //删除证书关系数据
        bdcZsService.deleteZsXmGx(processInsId);
        //清空相关证号
        bdcSynchService.clearBdcqzhAndYcqzh(processInsId);
    }

    /**
     * @param xmid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description xmid 删除证书及相关关系
     * @date : 2022/12/27 10:45
     */
    @ApiOperation(value = "通过传入xmid删除证书")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdczsByXmid(@RequestParam(value = "xmid", required = true) String xmid) throws Exception {
        //删除证书
        bdcZsService.deleteZsByxmid(xmid);
        //删除证书关系数据
        bdcZsService.deleteXmzsGx(xmid);
        //清空相关证号
        bdcSynchService.clearBdcqzhAndYcqzhByXmid(xmid);
    }

    @Override
    public String initYcqzhPl(String processInsId, String djxl) throws Exception {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInsId);
        bdcXmQO.setDjxl(djxl);
        // 查询不动产项目列表
        List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXm(bdcXmQO);

        return initBdcZsService.initYcqzhPl(bdcXmDOList);
    }

    @ApiOperation(value = "追加附记内容至上一手的附记中去")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void zjsysFj(@PathVariable("processInsId") String processInsId) {
        this.initBdcZsService.zjsysFj(processInsId);
    }

    /**
     * 初始化不动产权证（修正流程）  不生成权利其他状况和附记
     *
     * @param processInsId
     * @param xmid
     * @param zsyl         是否预览
     * @return 集合
     * @throws Exception
     */
    @ApiOperation(value = "生成证书(修正流程)")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "生成成功"), @ApiResponse(code = 500, message = "生成失败")})
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zsyl", value = "证书预览", required = false, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public List<BdcZsDO> initBdcqzsByXz(@PathVariable("processInsId") String processInsId, @RequestParam("xmid") String xmid, boolean zsyl) throws Exception {
        return initBdcZsService.initBdcZs(bdcXmService.listBdCshSlById(processInsId, xmid), zsyl, false, null, true);
    }

}
