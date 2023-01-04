package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.init.BdcQjtbxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcSjdzDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcSynchRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.other.BdcSynchService;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/5/6.
 * @description
 */
@RestController
@Api(tags = "不动产同步数据服务接口")
public class BdcSynchRestController extends BaseController implements BdcSynchRestService {

    @Autowired
    private BdcSynchService bdcSynchService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private EntityMapper entityMapper;

    /**
     * 同步更新业务数据的原证号字段
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gzlslid 工作流实例ID
     *@return
     *@description
     */
    @ApiOperation(value ="同步更新业务数据的原证号字段")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "gzlslid",value = "工作流实例ID", paramType = "path", dataType = "String")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void synchYzh(@PathVariable(name = "gzlslid") String gzlslid) throws Exception {
        bdcSynchService.synchLcYzh(gzlslid);
    }

    /**
     * 流程同步权籍数据
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="流程同步权籍数据")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "gzlslid",value = "工作流实例ID", paramType = "path", dataType = "String")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void synchLpbDataToLc(@PathVariable(name = "gzlslid") String gzlslid) throws Exception {
        bdcSynchService.synchLpbDataToLc(gzlslid);
    }

    /**
     * 项目同步权籍数据
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="项目同步权籍数据")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "xmid",value = "项目ID", paramType = "path", dataType = "String")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void synchLpbDataToXm(@PathVariable(name = "xmid") String xmid) throws Exception {
        bdcSynchService.synchLpbDataToXm(xmid);
    }


    /**
     * 项目同步权籍数据后的数据
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="项目同步权籍数据后的数据")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "xmid",value = "项目ID", paramType = "path", dataType = "String")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcYwxxDTO querySynchLpbData(@PathVariable(name = "xmid") String xmid) throws Exception {
        BdcYwxxDTO bdcYwxxDTO=new BdcYwxxDTO();
        InitServiceDTO initServiceDTO=bdcSynchService.querySynchLpbData(xmid);
        BeanUtils.copyProperties(initServiceDTO, bdcYwxxDTO);
        return bdcYwxxDTO;
    }

    /**
     * 项目同步权籍数据对照信息
     *
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="项目同步权籍数据对照信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "xmid",value = "项目ID", paramType = "path", dataType = "String")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcQjtbxxDTO> queryLpbDataDzxx(@PathVariable(name = "xmid") String xmid) throws Exception {
        return bdcSynchService.queryLpbDataDzxx(xmid);
    }
    /**
     * 项目数据对照信息(原项目数据只支持qlsjly为2的)
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @description
     */
    @ApiOperation(value ="项目数据对照信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "xmid",value = "项目ID", paramType = "path", dataType = "String")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcSjdzDTO> queryLpbAndYxmDataDzxx(@PathVariable(name = "xmid") String xmid) throws Exception {
        return bdcSynchService.queryLpbAndYxmDataDzxx(xmid);
    }

    /**
     * 根据前台传递的对照信息进行同步
     * @param list
     * @param xmid 项目ID
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="根据前台传递的对照信息进行同步")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "list", value = "配置集合", required = true, dataType = "List<BdcQjtbxxDTO>"),
            @ApiImplicitParam(name = "xmid",value = "项目ID", paramType = "path", dataType = "String")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void synchLpbDataToXm(@RequestBody List<BdcQjtbxxDTO> list, @PathVariable(name = "xmid") String xmid) throws Exception {
        if(CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(xmid)){
            bdcSynchService.synchLpbDzDataToXm(list,xmid);
        }
    }

    /**
     * 同步部分解押和解封的受理编号到附记
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="同步部分解押和解封的受理编号到宗地查封/抵押的附记")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "gzlslid",value = "工作流实例ID", paramType = "path", dataType = "String")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void synchBfjfjySlbhToFj(@PathVariable(name = "gzlslid") String gzlslid) throws Exception {
        if(StringUtils.isBlank(gzlslid)){
            throw new IllegalArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        BdcXmDTO bdcXmDTO=bdcXmService.getXmBfxxOnlyOne(gzlslid,null);
        if(bdcXmDTO!=null){
            BdcCshFwkgSlDO bdcCshFwkgSlDO=bdcXmService.queryCshFwkgSl(bdcXmDTO.getXmid());
            //不生成权利 注销类的
            if(!CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql())){
                Class target=null;
                //抵押
                if(CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDTO.getQllx())){
                    target= BdcDyaqDO.class;
                //查封
                }else if(CommonConstantUtils.QLLX_CF.equals(bdcXmDTO.getQllx())){
                    target= BdcCfDO.class;
                }
                if(target!=null && StringUtils.isNotBlank(bdcXmDTO.getBdcdyh()) && bdcXmDTO.getBdcdyh().length()==28){
                    Example example=new Example(target);
                    example.createCriteria().andEqualTo("bdcdyh", BdcdyhToolUtils.convertToW(bdcXmDTO.getBdcdyh().substring(0,19)))
                            .andEqualNvlTo("qszt",1,0);
                    List<BdcQl> list=entityMapper.selectByExample(example);
                    if(CollectionUtils.isNotEmpty(list)){
                        for(BdcQl o:list){
                            if(StringUtils.isNotBlank(o.getFj())){
                                o.setFj(o.getFj()+"\n"+bdcXmDTO.getSlbh());
                            }else{
                                o.setFj(bdcXmDTO.getSlbh());
                            }
                            entityMapper.updateByPrimaryKeySelective(o);
                        }
                    }
                }
            }
        }
    }
}
