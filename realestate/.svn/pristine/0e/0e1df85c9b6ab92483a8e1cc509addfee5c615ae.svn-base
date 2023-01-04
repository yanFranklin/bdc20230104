package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.exchange.MessageModel;
import cn.gtmap.realestate.common.core.dto.exchange.access.QjsjjcDTO;
import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzVO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.AccessByTimeIntervalQO;
import cn.gtmap.realestate.common.core.service.rest.exchange.NationalAccessRestService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcShxxRestService;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.national.ProvinceAccess;
import cn.gtmap.realestate.exchange.service.impl.national.access.AccessLogImpl;
import cn.gtmap.realestate.exchange.service.jrsb.BdcYwhjSbService;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import cn.gtmap.realestate.exchange.util.AsyncDealUtils;
import cn.gtmap.realestate.exchange.util.XsdUtil;
import cn.gtmap.realestate.exchange.util.enums.JrRzCgbsEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@RestController
@Api(tags = "不动产数据汇交")
public class NationalAccessRestController implements NationalAccessRestService {

    @Autowired
    private AccesssModelHandlerService accesssModelHandlerService;

    @Autowired
    private AccessLogImpl accessLog;
    @Autowired
    BdcShxxRestService bdcShxxRestService;
    @Autowired
    AsyncDealUtils asyncDealUtils;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    BdcYwhjSbService bdcYwhjSbService;

    @Autowired
    XsdUtil xsdUtil;

    private static Logger LOGGER = LoggerFactory.getLogger(NationalAccessRestController.class);

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据项目主键汇交当前项目
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键汇交当前项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目主键", required = true, dataType = "String", paramType = "query")})
    public void autoAccessByXmid(String xmid) {
        accesssModelHandlerService.autoAccessByXmid(xmid,false);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param xmidList 项目主键
     * @return void
     * @description 根据项目主键汇交当前项目
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键汇交当前项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmidList", value = "项目主键", required = true, dataType = "List", paramType = "query")})
    public void autoAccessByXmidList(@RequestParam(name = "xmidList") List<String> xmidList) {
        accesssModelHandlerService.autoAccessByXmidList(xmidList);
    }

    /**
     * @param sbxzDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据xmid进行业务汇交，并更新销账状态
     * @date : 2022/12/13 16:47
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键汇交当前项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmidList", value = "项目主键", required = true, dataType = "List", paramType = "query")})
    public void autoAccessBySbxz(@RequestBody List<SbxzVO> sbxzVOList) {
        accesssModelHandlerService.actoAccessBySbxz(sbxzVOList);
    }

    /**
     * @param xmidList@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外联项目上报
     * @date : 2022/12/6 13:43
     */
    @Override
    public void autoAccessWithWlxmByXmidList(@RequestBody List<String> xmidList) {
        //存入一个redis数据表示正在进行汇交任务，存在时间3小时
        redisUtils.addStringValue("SJJRHJPLSB", "SJJRHJPLSB", 3 * 3600);
        try {
            accesssModelHandlerService.autoAccessByXmidList(xmidList);
            //处理外联项目上报
            accesssModelHandlerService.autoAccessWlxmByXmidList(xmidList);
        } catch (Exception e) {
            LOGGER.error("业务汇交存在异常" + e);
            throw new AppException("业务汇交存在异常" + e.getMessage());
        } finally {
            //汇交结束后删除
            redisUtils.deleteKey("SJJRHJPLSB");
        }
    }

    /**
     * @param xmidList@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 外联项目的接入汇交
     * @date : 2022/12/6 14:32
     */
    @Override
    public void autoAccessWlxmByXmidList(@RequestBody List<String> xmidList) {
        //存入一个redis数据表示正在进行汇交任务，存在时间3小时
        redisUtils.addStringValue("WLXMSJJRHJPLSB", "SJJRHJPLSB", 3 * 3600);
        try {
            accesssModelHandlerService.autoAccessWlxmByXmidList(xmidList);
        } catch (Exception e) {
            LOGGER.error("业务汇交存在异常" + e);
            throw new AppException("业务汇交存在异常" + e.getMessage());
        } finally {
            //汇交结束后删除
            redisUtils.deleteKey("WLXMSJJRHJPLSB");
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据时间区间上报
     */
    @Override
    @GetMapping("/realestate-exchange/rest/v1.0/access/time/interval")
    public void autoAccessByTimeInterval(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate,@RequestParam("type")String type,@RequestParam(required = false,value = "xmly") String xmly) {
        //获取时间区间的xmList
        List<String> xmidList = accesssModelHandlerService.getXmListToAccessByTimeInterval(startDate, endDate,type,xmly);
        if (CollectionUtils.isNotEmpty(xmidList)){
            LOGGER.info("开始多线程处理该时间段内的项目信息:" + startDate + "~" + endDate);
            accesssModelHandlerService.autoAccessByTimeInterval(xmidList);
        }else {
            LOGGER.info("未查询到需要处理的项目信息");
        }
    }

    /**
     * @param accessByTimeIntervalQO
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据时间区间上报
     */
    @Override
    @PostMapping("/realestate-exchange/rest/v1.0/access/time/interval/post")
    public void autoAccessByTimeInterval(@RequestBody AccessByTimeIntervalQO accessByTimeIntervalQO) {
        accessByTimeIntervalQO.checkParam();
        //获取时间区间的xmList
        List<String> xmidList = accesssModelHandlerService.getXmListToAccessByTimeInterval(accessByTimeIntervalQO.getStartDate(), accessByTimeIntervalQO.getEndDate(),accessByTimeIntervalQO.getType(),accessByTimeIntervalQO.getXmly());
        if (CollectionUtils.isNotEmpty(xmidList)){
            LOGGER.info("开始多线程处理该时间段内的项目信息:" + accessByTimeIntervalQO.getStartDate() + "~" + accessByTimeIntervalQO.getEndDate());
            accesssModelHandlerService.autoAccessByTimeInterval(xmidList);
        }else {
            LOGGER.info("未查询到需要处理的项目信息");
        }
    }

    /**
     * @param xmid 项目主键
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据项目主键汇交同一流程所有项目
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目主键汇交同一流程所有项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目主键", required = true, dataType = "String", paramType = "query")})
    public void autoAccessAllXmByXmid(String xmid) {
        accesssModelHandlerService.autoAccessByXmid(xmid,true);
    }
    /**
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据工作流主键汇交
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流主键汇交")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query")})
    public void autoAccessByProcessInsId(@RequestParam(name = "processInsId") String processInsId) {
        accesssModelHandlerService.autoAccessByProcessInsId(processInsId);
    }

    /**
     * @param processInsId@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id外联注销项目
     * @date : 2022/12/2 9:55
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流主键汇交外联注销项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query")})
    public void autoAccessWlzxXm(@RequestParam(name = "processInsId") String processInsId) {
        accesssModelHandlerService.autoAccessWlzxXm(processInsId);
    }

    /**
     * @param messageModel 汇交数据实体
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 汇交数据实体
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "汇交数据实体")
    public boolean autoAccessByMessageModel(@RequestBody MessageModel messageModel) {
        if(messageModel != null
                && messageModel.getHeadModel() != null
                && messageModel.getDataModel() != null ) {
            String ywh = messageModel.getHeadModel().getRecFlowID();
            LOGGER.info("接收汇交实体上报，ywh:{},先存入一条数据到bdc_jr_sjjl", ywh);
            asyncDealUtils.saveJrCzrz(ywh, 1, "服务接收到需要上报的项目信息，下一步进行配置文件校验", "", new Date(), "1");
            // 处理 biz msg id
            if (StringUtils.isBlank(messageModel.getHeadModel().getBizMsgID())) {
                String areaCode = messageModel.getHeadModel().getAreaCode();
                String bizMsgId = accesssModelHandlerService.getBizMsgId(areaCode);
                messageModel.getHeadModel().setBizMsgID(bizMsgId);
            }
            BdcAccessLog bdcAccessLog = new ProvinceAccess();
            if (CollectionUtils.isNotEmpty(messageModel.getDataModel().getDjtDjSlsqList())) {
                LOGGER.warn("当前报文id{}直接更新实际接入时间{}", messageModel.getHeadModel().getBizMsgID(), messageModel.getDataModel().getDjtDjSlsqList().get(0).getJssj());
                bdcAccessLog.setSjjrrq(messageModel.getDataModel().getDjtDjSlsqList().get(0).getJssj());
            }
            accesssModelHandlerService.saveBdcAccessByMessageModel(bdcAccessLog, JrRzCgbsEnum.BEGIN_WSB.getBs(), messageModel, "");
            LOGGER.debug("处理 biz msg id，ywh:{}", ywh);
            //根据 YWDM 过滤掉不需要汇交的DATA结构
            accesssModelHandlerService.filterDataModel(messageModel);
//            LOGGER.info("根据 YWDM 过滤掉不需要汇交的DATA结构，ywh:{}", messageModel.toString());
            return accesssModelHandlerService.autoAccessByMessageModel(messageModel);
        }
        return false;
    }

    /**
     * @param date yyyy-mm-dd格式日期
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 手动触发上报登簿日志
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public boolean accessLog(String date) {
        Date accessDate = new Date();
        if(StringUtils.isNotBlank(date)){
            Date paramDate = DateUtils.formatDate(date);
            accessDate = DateUtils.dealDate(paramDate,"23:59:59");
        }
        accessLog.accessLog(accessDate,null);
        return true;
    }
	
	@Override
	public boolean accessLogNt(String date, String qxdm) {
		Date accessDate = new Date();
		if(StringUtils.isNotBlank(date)){
			Date paramDate = DateUtils.formatDate(date);
			accessDate = DateUtils.dealDate(paramDate,"23:59:59");
		}
		accessLog.accessLog(accessDate, qxdm);
        return true;
    }

    @Override
    public Map<String, Object> dbrzMx(String id) {
        if (StringUtils.isBlank(id)) {
            throw new AppException("参数缺失！");
        }
        return accessLog.dbrzMx(id);
    }

    /**
     * @param date
     * @param qxdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 登簿日志明细预览
     * @date : 2022/10/13 9:16
     */
    @Override
    public Map<String, Object> dbrzMxyl(String date, String qxdm) {
        Date accessDate = new Date();
        if (StringUtils.isNotBlank(date)) {
            Date paramDate = DateUtils.formatDate(date);
            accessDate = DateUtils.dealDate(paramDate, "23:59:59");
        }
        return accessLog.dbrzmxyl(accessDate, qxdm);
    }

    /**
     * @param processInsId 工作流主键
     * @return void
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据工作流主键上交, 生成审核信息
     */
    @Override
    public void shxxWithAccess(@RequestParam(name = "processInsId") String processInsId) {
        LOGGER.info("自动办结流程配置该事件，gzlslid{}，", processInsId);
        LOGGER.info("开始生成审核信息，gzlslid{}，", processInsId);
        List<BdcShxxDO> shxxDOS = bdcShxxRestService.generateShxxOfProInsId(processInsId);
        LOGGER.info("生成审核信息结束，生成条数{}，", shxxDOS.size());
        LOGGER.info("开始上报：gzlslid:{}", processInsId);
        autoAccessByProcessInsId(processInsId);
        LOGGER.info("上报结束：gzlslid:{}", processInsId);
    }

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新销账状态
     * @date : 2022/6/22 10:35
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据xmid 更新销账状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmidList", value = "项目主键", required = true, dataType = "String", paramType = "body")})
    public void updateXzzt(@RequestBody List<String> xmidList, @RequestParam(name = "xzzt", required = false) String xzzt) {
        accesssModelHandlerService.updateWxzBwxxXzzt(xmidList, xzzt);
    }

    /**
     * @param idList 主键集合
     * @param xzzt
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据主键更新销账状态
     * @date : 2022/9/29 9:02
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键id 更新销账状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "idList", value = "主键", required = true, dataType = "String", paramType = "body")})
    public void updateXzztById(@RequestBody List<String> idList, @RequestParam(name = "xzzt", required = false) String xzzt) {
        accesssModelHandlerService.updateXzztByid(idList, xzzt);
    }

    /**
     * @param xmidList
     * @param clzt
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新未接入表数据clzt=1
     * @date : 2022/6/30 13:54
     */
    @Override
    public void updateWjrZt(@RequestBody List<String> xmidList, @RequestParam(name = "clzt") Integer clzt) {
        accesssModelHandlerService.updateWjrClzt(xmidList, clzt);
    }

    /**
     * @param redisKey@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取redis 的值
     * @date : 2022/11/10 17:28
     */
    @Override
    public Boolean queryRedisValue(@RequestParam(name = "redisKey") String redisKey) {
        if (StringUtils.isNotBlank(redisKey)) {
            return StringUtils.isNotBlank(redisUtils.getStringValue(redisKey));
        }
        return false;
    }

    /**
     * @param qjsjjcDTOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证权籍数据KTT的是否符合XSD校验
     * @date : 2022/11/18 17:54
     */
    @Override
    public Object checkQjsj(@RequestBody List<QjsjjcDTO> qjsjjcDTOList) {
        return bdcYwhjSbService.checkQjsjCjyz(qjsjjcDTOList);
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 执行更新销账状态，和定时任务对比一个逻辑
     * @date : 2022/11/25 8:38
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = " 更新销账状态")
    public void updateXzzt() {
        accesssModelHandlerService.updateXzzt();
        accesssModelHandlerService.updateClsjXzzt();
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 刷新配置表数据
     * @date : 2022/11/28 11:35
     */
    @Override
    public void refreshXsdjyPz() {
        xsdUtil.listXsddz();
    }
}
