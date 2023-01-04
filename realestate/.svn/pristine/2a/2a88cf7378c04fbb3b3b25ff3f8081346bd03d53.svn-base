package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcCopyReplaceYwxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcDeleteYwxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcInitRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.LogActionConstans;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.dto.InitResultDTO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.impl.BdcCxYwxxServiceImpl;
import cn.gtmap.realestate.init.service.initJw.InitBdcJwService;
import cn.gtmap.realestate.init.service.other.*;
import cn.gtmap.realestate.init.util.DozerUtils;
import cn.gtmap.realestate.init.web.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description
 */
@RestController
@Api(tags = "不动产初始化服务接口")
public class BdcInitRestController extends BaseController implements BdcInitRestService {

    /**
     * 撤销登记
     */
    @Value("#{'${cxdj.gzldyid:}'.split(',')}")
    private List<String> cxdjDyids;

    @Autowired
    RegularInitParameterService regularInitParameterService;
    @Autowired
    InitBeanFactory initBeanFactory;
    @Autowired
    InitDataDealService initDataDealService;
    @Autowired
    InitBdcZsService initBdcZsService;
    @Autowired
    InitDataService initDataService;
    @Autowired
    BdcYwsjHxService bdcYwsjHxService;
    @Autowired
    DozerUtils dozerUtils;
    @Autowired
    Set<InitBdcJwService> initBdcJwServices;
    @Autowired
    BdcCxYwxxServiceImpl bdcCxYwxxService;

    /**
     * 通过传入参数初始化所有信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcSlxxDTO
     *@return  List<BdcXmDO>
     *@description
     */
    @ApiOperation(value ="初始化相关业务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlxxDTO", value = "受理信息结构", required = true, dataType = "BdcSlxxDTO")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public List<BdcXmDO> csh(@RequestBody BdcSlxxDTO bdcSlxxDTO) throws Exception {
        String slbh=bdcSlxxDTO.getBdcSlJbxx().getSlbh();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
        int slSize=0;
        if(bdcSlxxDTO!=null && CollectionUtils.isNotEmpty(bdcSlxxDTO.getBdcSlXmList())){
            slSize=bdcSlxxDTO.getBdcSlXmList().size();
        }
        LOGGER.info("初始化开始:{} {} 受理项目数量:{}", simpleDateFormat.format(new Date()),slbh,slSize);
        List<BdcXmDO> list= new ArrayList<>();
        //数据转换
        List<InitServiceQO> listQO=regularInitParameterService.changeAcceptDTOIntoInitQO(bdcSlxxDTO);
        LOGGER.info("转换初始化参数结束:{} {}", simpleDateFormat.format(new Date()) ,slbh);
        if(CollectionUtils.isNotEmpty(listQO)){
            //判断是否为撤销登记
            boolean isCxdj =CollectionUtils.isNotEmpty(cxdjDyids) &&cxdjDyids.contains(bdcSlxxDTO.getBdcSlJbxx().getGzldyid());
            InitResultDTO initResultDTO;

            if(isCxdj) {
                //初始化所有撤销业务数据
                initResultDTO = initDataService.initCxYwxx(listQO, true);
            }else {
                //初始化所有业务数据
                initResultDTO = initDataService.init(listQO, true);
            }
            if(initResultDTO!=null){
                list=initResultDTO.getBdcXmList();
            }
            LOGGER.info("初始化相关数据结束:{} {}", simpleDateFormat.format(new Date()),slbh);
            try{
                if(!isCxdj) {
                    //初始化对应权利的附记
                    initDataService.initQlfj(list, listQO);
                    LOGGER.info("更新权利附记结束:{} {}", simpleDateFormat.format(new Date()), slbh);
                }
                //回写信息到平台
                // modified by lixin 流程项目来源为 3 并且 gzlslid 和 slbh 相同不需要回写大云数据
                if (CollectionUtils.isNotEmpty(list) && !(CommonConstantUtils.XMLY_QT_DM.equals(list.get(0).getXmly())
                        && StringUtils.equals(list.get(0).getGzlslid(), list.get(0).getSlbh()))) {
                    bdcYwsjHxService.saveBdcYwsj(list.get(0).getGzlslid());
                    LOGGER.info("回写平台结束:{} {}", simpleDateFormat.format(new Date()),slbh);
                }
                if(!isCxdj) {
                    //初始化后的服务
                    List<InitBdcJwService> listJw = initBeanFactory.getBdcJwServices();
                    if (CollectionUtils.isNotEmpty(listJw)) {
                        for (InitBdcJwService initBdcJwService : listJw) {
                            initBdcJwService.initJw(initResultDTO, listQO);
                        }
                        LOGGER.info("结尾服务处理结束:{} {}", simpleDateFormat.format(new Date()), slbh);
                    }
                }
            }catch (Exception e){
                //报错删除入库的业务数据
                if(CollectionUtils.isNotEmpty(list) && StringUtils.isNotBlank(list.get(0).getGzlslid())){
                    deleteYwxx(list.get(0).getGzlslid());
                    LOGGER.info("初始化报错后删除结束:{} {}", simpleDateFormat.format(new Date()),slbh);
                }
                throw e;
            }
        }
        LOGGER.info("初始化结束:{} {}", simpleDateFormat.format(new Date()),slbh);
        return list;
    }

    /**
     * 通过传入项目id数据去删除对应业务信息
     * @param xmids
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="删除相关业务信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "xmids",value = "项目ID数组",allowMultiple = true, paramType = "query", dataType = "string")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteYwxx(@RequestParam(name = "xmids") String[] xmids) throws Exception {
        initDataDealService.deleteYwsj(xmids);
    }

    /**
     * 通过传入工作流实例ID数据去删除对应业务信息
     *
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="删除相关业务信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "gzlslid",value = "工作流实例ID", paramType = "path", dataType = "String")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteYwxx(@PathVariable(name = "gzlslid") String gzlslid) throws Exception {
        initDataDealService.deleteYwsj(gzlslid);
    }
    /**
     * 通过传入项目id数据去查询对应业务信息
     * @param xmid 查询的项目id
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="查询相关业务信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParam(name = "xmid",value = "项目ID", paramType = "path", dataType = "string")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public BdcYwxxDTO queryYwxx(@PathVariable(name = "xmid") String xmid) throws Exception {
        BdcYwxxDTO bdcYwxxDTO=new BdcYwxxDTO();
        InitServiceDTO initServiceDTO=initDataService.queryYwsj(xmid);
        BeanUtils.copyProperties(initServiceDTO, bdcYwxxDTO);
        return bdcYwxxDTO;
    }

    /**
     * 通过传入项目id数据和对应数据结构去更新对应业务信息
     * @param xmid       项目
     * @param bdcYwxxDTO 更新数据
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="更新相关业务信息")
    @ApiImplicitParams({ @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, paramType = "path", dataType = "string"),
            @ApiImplicitParam(name = "bdcYwxxDTO", value = "更新信息结构", required = true, dataType = "BdcYwxxDTO")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void updateYwxx(@PathVariable(name = "xmid") String xmid, @RequestBody BdcYwxxDTO bdcYwxxDTO) throws Exception {
        initDataService.saveOrUpdateYwsj(xmid,bdcYwxxDTO,false);
    }
    @ApiOperation(value ="插入业务信息")
    @ApiImplicitParam(name = "bdcYwxxDTO", value = "更新信息结构", required = true, dataType = "BdcYwxxDTO")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public String insertYwxx(@RequestBody BdcYwxxDTO bdcYwxxDTO) throws Exception {
        String yxmid = "";
        // 先根据roomid（bdcdywybh）确定是否已存在上手业务信息
        if(bdcYwxxDTO.getBdcXm() != null && StringUtils.isNotBlank(bdcYwxxDTO.getBdcXm().getBdcdywybh())){
            List<BdcXmDO> bdcXmDOList = initDataService.queryYwxxByBdcdywybh(bdcYwxxDTO.getBdcXm().getBdcdywybh());
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                // 先删除 在插入
                initDataService.deleteYwsj(bdcXmDOList,false);
            }
            LOGGER.info("插入业务信息数据：{}",JSONObject.toJSONString(bdcYwxxDTO));
            BdcYwxxDTO result = initDataService.saveOrUpdateYwsj("",bdcYwxxDTO,true);
            yxmid = result.getBdcXm().getXmid();
            LOGGER.info("返回插入结果数据：{}",JSONObject.toJSONString(result));
        }
        if(StringUtils.isEmpty(yxmid)){
            throw new AppException("插入业务数据异常："+JSONObject.toJSONString(bdcYwxxDTO));
        }
        return yxmid;
    }

    @ApiOperation(value ="插入业务信息")
    @ApiImplicitParam(name = "bdcYwxxDTO", value = "更新信息结构", required = true, dataType = "BdcYwxxDTO")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcYwxxDTO> insertYwxxList(@RequestBody List<BdcYwxxDTO> bdcYwxxDTOList){
        if(CollectionUtils.isNotEmpty(bdcYwxxDTOList)) {
            LOGGER.info("插入业务信息数据：{}", JSONObject.toJSONString(bdcYwxxDTOList));
            return initDataService.saveOrUpdateYwsjPl("", bdcYwxxDTOList, true);
        }
        return new ArrayList<>();

    }

    /**
     * 通过传入原对象和目标对象，根据初始化dozer配置进行赋值转换
     * @param list   原对象和目标对象
     *  @param sourceClass 原对象的className
     *@param targetClass 目标对象的className
     * @return 整合后的目标对象
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="根据初始化dozer配置进行赋值转换")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Object ywxxDozerMap(@RequestBody List<Object> list,@RequestParam(value = "sourceClass") String sourceClass,@RequestParam(value = "targetClass")String targetClass)  throws Exception {
        if(CollectionUtils.isNotEmpty(list) && list.size()==2 && StringUtils.isNotBlank(sourceClass) && StringUtils.isNotBlank(targetClass)){
            Object sourceObj=JSONObject.parseObject(JSONObject.toJSONString(list.get(0)),Class.forName(sourceClass));
            Object targetObj=JSONObject.parseObject(JSONObject.toJSONString(list.get(1)),Class.forName(targetClass));
            dozerUtils.initBeanDateConvert(sourceObj,targetObj);
            return targetObj;
        }
        return null;
    }

    /**
     * 通过传入参数初始化相关系信息(不入库,返回数据结构)
     * @param bdcSlxxDTO bdcSlxxDTO
     * @return List<BdcXmDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value ="一窗初始化相关业务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlxxDTO", value = "受理信息结构", required = true, dataType = "BdcSlxxDTO")})
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public List<BdcYwxxDTO> ycCsh(@RequestBody BdcSlxxDTO bdcSlxxDTO) throws Exception {
        String slbh=bdcSlxxDTO.getBdcSlJbxx().getSlbh();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
        LOGGER.info("一窗初始化开始:{} {}", simpleDateFormat.format(new Date()),slbh);
        List<BdcYwxxDTO> list=null;
        //数据转换
        List<InitServiceQO> listQO=regularInitParameterService.changeAcceptDTOIntoInitQO(bdcSlxxDTO);
        LOGGER.info("一窗转换初始化参数结束:{} {}", simpleDateFormat.format(new Date()),slbh);
        if(CollectionUtils.isNotEmpty(listQO)) {
            list = new ArrayList<>(listQO.size());
            //初始化所有业务数据
            initDataService.init(listQO, false);
            if (MapUtils.isNotEmpty(listQO.get(0).getServiceDataMap())) {
                for(InitServiceDTO initServiceDTO:listQO.get(0).getServiceDataMap().values()){
                    BdcYwxxDTO bdcYwxxDTO=new BdcYwxxDTO();
                    BeanUtils.copyProperties(initServiceDTO, bdcYwxxDTO);
                    list.add(bdcYwxxDTO);
                }
            }
        }
        LOGGER.info("一窗初始化结束:{} {}", simpleDateFormat.format(new Date()),slbh);
        return list;
    }

    @ApiOperation(value ="汇总各子系统删除相关业务信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParams({ @ApiImplicitParam(name = "gzlslid",value = "工作流实例ID", paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "reason",value = "删除原因", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "slzt",value = "受理状态", paramType = "query", dataType = "String")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteYwxxAllSubsystem(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value = "reason", required = false) String reason, @RequestParam(value = "slzt", required = false) String slzt) throws Exception {
        initDataDealService.deleteYwxxAllSubsystem(gzlslid,reason,slzt);
    }

    @ApiOperation(value ="汇总各子系统删除相关业务信息")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcDeleteYwxxDTO",value = "删除业务信息DTO对象", paramType = "bodys", dataType = "String")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteYwxxAllSubsystem(@RequestBody BdcDeleteYwxxDTO bdcDeleteYwxxDTO) throws Exception {
        initDataDealService.deleteYwxxAllSubsystem(bdcDeleteYwxxDTO);
    }

    @ApiOperation(value ="复制业务信息并替换相关字段")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "请求获取成功"),@ApiResponse(code = 500,message = "请求参数错误")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bdcCopyReplaceYwxxDTOList",value = "复制并且替换业务信息参数", paramType = "bodys", dataType = "body")
    })
    @ResponseStatus(HttpStatus.OK)
    @LogNote(value = "复制业务信息并替换相关字段", action = LogActionConstans.OTHER)
    @Override
    public List<BdcYwxxDTO> copyAndReplaceYwxx(@RequestBody List<BdcCopyReplaceYwxxDTO> bdcCopyReplaceYwxxDTOList){
        return initDataService.copyAndReplaceYwxx(bdcCopyReplaceYwxxDTOList);

    }
}
