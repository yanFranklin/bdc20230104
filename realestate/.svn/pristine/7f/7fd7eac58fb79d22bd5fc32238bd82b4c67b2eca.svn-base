package cn.gtmap.realestate.register.web.rest;


import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.register.BdcXmZsDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.BdcXmXmfbDTO;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import cn.gtmap.realestate.common.core.dto.register.XxblDbDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcHfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcDbxxRestService;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.rabbitMq.SynQjBdcdyztMqService;
import cn.gtmap.realestate.register.service.BdcDbxxService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.service.BdcdyZtService;
import cn.gtmap.realestate.register.util.Constants;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/2
 * @description 登簿信息业务Controller
 */
@RestController
@Api(tags = "不动产登簿信息服务接口")
public class BdcDbxxRestController extends BaseController implements BdcDbxxRestService {
    /**
     * REST请求路径中gzlslid变量为空默认值
     */
    private static final String PATH_GLZSLID = "{gzlslid}";

    @Autowired
    BdcDbxxService bdcDbxxService;
    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    SynQjBdcdyztMqService synQjBdcdyztMqService;
    @Autowired
    BdcdyZtService bdcdyZtService;
    @Autowired
    BdcdyZtRestController bdcdyZtRestController;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    BdcZsFeignService bdcZsFeignService;
    @Autowired
    EntityMapper entityMapper;

    /**
     * 不动产单元现势状态来源：bdcsjgl:权籍 bdcdj：登记
     */
    @Value("${bdcdyxszt.ly:bdcsjgl}")
    private String dyxsztly;

    @Value("${zsxt.sfgx:false}")
    private boolean sfgxZsxt;


    /**
     * @param xxblDbDTO 信息补录登簿对象
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("信息补录登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态")
    @ApiImplicitParam(name = "xxblDbDTO", value = "信息补录登簿对象", required = true, dataType = "XxblDbDTO", paramType = "body")
    @Override
    public void updateBdcDbxxAndQszt(@RequestBody XxblDbDTO xxblDbDTO) {
        bdcDbxxService.updateBdcDbxxAndQszt(xxblDbDTO);
    }

    /**
     * @param bdcZxQO 项目注销信息
     * @return {code int} 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新项目和权利的注销信息，同步权籍
     * qszt{@code 1}清空注销人和注销时间，对登簿人和登记时间不做修改；{@code 2}同时更新注销人和注销时间
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新项目和权利的权属状态，同步权籍")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZxQO", value = "注销QO", required = true, dataType = "BdcZxQO", paramType = "body")})
    @Override
    public int updateXmAndQlZxxxAndSynQjBdcdyzt(@RequestBody BdcZxQO bdcZxQO) {
        bdcDbxxService.updateXmAndQlZxxx(bdcZxQO);
        // 同步权籍单元状态
        bdcdyZtService.syncBdcdyztByXmid(bdcZxQO.getXmidList());
        return 0;
    }

    /**
     * @param bdcZxQO         不动产注销信息
     * @param currentUserName 当前账户
     * @author <a href ="mailto:zhangwentao@gtmap.cn">songhaowen</a>
     * @description 更新注销权利信息，并同步权籍单元号的状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新注销权利信息，并同步权籍单元号的状态")
    @ApiImplicitParam(name = "bdcZxQO", value = "注销信息", required = true, dataType = "BdcZxQO", paramType = "body")
    public int zxQl(@RequestBody BdcZxQO bdcZxQO, @RequestParam(value = "currentUserName") String currentUserName) {
        try {
            // 流程转发中没有用户认证信息，所以用此方法获取
            UserDto userDto = userManagerUtils.getUserByName(currentUserName);
            if (null != userDto) {
                bdcZxQO.setZxdbr(userDto.getAlias());
            }
        } catch (Exception e) {
            LOGGER.error("{}:审核登簿子系统——注销权利信息报错：调用大云用户信息失败", this.getClass());
        }
        bdcZxQO.setZxdjsj(new Date());
        return this.updateXmAndQlZxxxAndSynQjBdcdyzt(bdcZxQO);
    }

    /**
     * 恢复权利
     *
     * @param bdcHfQO 不动产注销信息
     * @author <a href ="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("恢复权利信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHfQO", value = "恢复信息", required = true, dataType = "BdcHfQO", paramType = "body")})
    public int hfQl(@RequestBody BdcHfQO bdcHfQO) {
        int i = bdcDbxxService.updateXmAndQlHfxx(bdcHfQO);
        // 同步权籍单元状态
        LOGGER.warn("恢复权利，更新权籍基本信息和单元状态！{}", bdcHfQO.getXmidList());
        bdcdyZtService.syncBdcdyztByXmid(bdcHfQO.getXmidList());
        return i;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param qszt    权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原注销权利的登簿信息和权属状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新原注销权利的登簿信息和权属状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "qszt", value = "权属状态", required = true, dataType = "Integer", paramType = "path")})
    @Override
    public void updateYzxqlDbxxAndQszt(@PathVariable(name = "gzlslid") String gzlslid, @PathVariable(name = "qszt") Integer qszt) {
        bdcDbxxService.updateYzxqlDbxxAndQszt(gzlslid, qszt);
    }

    /**
     * @param processInsId    工作流实例id
     * @param currentUserName 当前账户
     * @author <a href ="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description AOP规则验证后，验证通过则登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态
     */
    @ApiOperation("AOP规则验证后，验证通过则登簿时更新登簿信息以及权属状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "currentUserName", value = "当前账户", required = true, dataType = "string", paramType = "query")})
    @Override
    public List<BdcGzyzVO> updateDbxxQsztGzyzAOP(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) {
        return bdcDbxxService.updateDbxxQsztGzyzAOP(processInsId, currentUserName);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return {int} 更新的记录行数
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新案件状态为2已完成状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新案件状态为2已完成状态并更新项目结束时间")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public void changeAjzt(@PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid) || PATH_GLZSLID.equals(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        // 更新案件状态为完结
        bdcXmxxService.updateAjztWjByGzlslid(gzlslid);
        // 处理证书形态
        if (sfgxZsxt) {
            List<BdcXmZsDTO> bdcXmZsDTOList = bdcXmxxService.queryZsByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmZsDTOList)) {
                List<String> dzzxmidList = new ArrayList<>(1);
                List<String> zzzxmidList = new ArrayList<>(1);
                for (BdcXmZsDTO bdcXmZsDTO : bdcXmZsDTOList) {
                    if (StringUtils.isNotBlank(bdcXmZsDTO.getQzysxlh())
                            && !StringUtils.equals("/", bdcXmZsDTO.getQzysxlh())) {
                        zzzxmidList.add(bdcXmZsDTO.getXmid());
                    }
                    else {
                        dzzxmidList.add(bdcXmZsDTO.getXmid());
                    }
                }
                if (CollectionUtils.isNotEmpty(dzzxmidList)) {
                    bdcXmxxService.updateBdcXmfbZsxt(dzzxmidList, 1);
                }
                if (CollectionUtils.isNotEmpty(zzzxmidList)) {
                    bdcXmxxService.updateBdcXmfbZsxt(zzzxmidList, 2);
                }
            }
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 同步权籍不动产单元状态(不包含锁定)
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("同步权籍不动产单元状态(不包含锁定)")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    public void synQjBdcdyzt(@PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid) || PATH_GLZSLID.equals(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
//        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
//
//            BdcXmGxQO bdcXmGxQO = new BdcXmGxQO();
//            bdcXmGxQO.setGzlslid(gzlslid);
//            bdcXmGxQO.setZxyql(CommonConstantUtils.SF_S_DM);
//            List<BdcXmDO> bdcYxmDOList = bdcXmxxService.getListYbdcXm(bdcXmGxQO);
//            if (CollectionUtils.isNotEmpty(bdcYxmDOList)) {
//                bdcXmDOList.addAll(bdcYxmDOList);
//            }
//
//            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
//                Set<String> setBdcdyh = new HashSet<>();
//                for (BdcXmDO bdcXmDO : bdcXmDOList) {
//                    setBdcdyh.add(bdcXmDO.getBdcdyh());
//                }
//            // 批量同步不动产单元状态
//            bdcdyZtService.syncBdcdyZtByBdcdyh(new ArrayList<>(setBdcdyh));
//        }
        List<String> bdcdyhList = bdcdyZtService.querySyncQjBdcdyh(gzlslid, Collections.EMPTY_LIST);
        if (CollectionUtils.isNotEmpty(bdcdyhList)) {
            BdcXmXmfbDTO bdcXmXmfbDTO =bdcXmxxService.getOnlyOneXm(gzlslid,"");
            bdcdyZtService.syncBdcdyZtByBdcdyh(bdcdyhList,bdcXmXmfbDTO != null ?bdcXmXmfbDTO.getQjgldm():"");
        }
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 同步权籍基本信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("同步权籍基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public void synQjJbxx(@PathVariable(name = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid) || PATH_GLZSLID.equals(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        this.syncBdcdyxxByBdcXm(bdcXmDOList);
    }

    /**
     * @param bdcXmDOList 需要同步的项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 不动产项目同步权籍基本信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("不动产项目同步权籍基本信息")
    @Override
    public void syncBdcdyxxByBdcXm(@RequestBody List<BdcXmDO> bdcXmDOList) {
        bdcdyZtService.syncBdcdyxxByBdcXm(bdcXmDOList);
    }

    /**
     * @param bdcdyhList 不动产单元号
     * @param sdzt       锁定状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将登记的锁定状态同步到权籍
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("将登记的锁定状态同步到权籍")
    @Override
    public void synQjBdcdyztSd(@RequestBody List<String> bdcdyhList, @RequestParam(name = "sdzt") Integer sdzt) {
        BatchBdcdyhSyncZtRequestDTO batchBdcdyhSyncZtRequestDTO = bdcDbxxService.getBdcdySdztRequestDTO(bdcdyhList, sdzt);
        if (batchBdcdyhSyncZtRequestDTO != null && CollectionUtils.isNotEmpty(batchBdcdyhSyncZtRequestDTO.getBdcdyhZtList())) {
            LOGGER.warn("同步不动产单元信息！{}", JSONObject.toJSONString(batchBdcdyhSyncZtRequestDTO));
            //更新登记锁定状态
            if(StringUtils.equals("bdcdj",dyxsztly)){
                bdcdyZtService.saveBdcdjSdZtWithDTO(batchBdcdyhSyncZtRequestDTO);
                LOGGER.info("{}同步登记库锁定状态结束！",JSONObject.toJSONString(batchBdcdyhSyncZtRequestDTO));
            }
            synQjBdcdyztMqService.send(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), RabbitMqEnum.QueueEnum.BDCDYSDZTQUEUE.getCode(), JSONObject.toJSONString(batchBdcdyhSyncZtRequestDTO));
        } else {
            AuditEvent auditEvent = new AuditEvent("同步权籍：将登记的锁定状态同步到权籍", "不动产项目同步权籍基本信息:未查询到不动产单元锁定信息", bdcdyhList.toString());
            zipkinAuditEventRepository.add(auditEvent);
            LOGGER.info("不动产项目同步权籍基本信息:未查询到不动产单元锁定信息！");
        }
    }

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 撤销流程，修改权属状态和案件状态
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("撤销流程，修改权属状态和案件状态")
    @Override
    public void cancelProcessQsztAndAjzt(@PathVariable(value = "gzlslid") String gzlslid) {
        bdcDbxxService.cancelProcessQsztAndAjzt(gzlslid);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcZxQl]
     * @return void
     * @description 直接传入需要注销的权利 更新附记注销信息
     */
    @Override
    public void generateAndUpdateZxqlFj(@RequestBody BdcQl bdcZxQl) {
        bdcDbxxService.generateAndUpdateZxqlFj(bdcZxQl);
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押注销流程增加回传功能
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("招行流程抵押注销流程增加回传功能")
    public void zsyhDyzxHcfw(String processInsId) {
        bdcDbxxService.zsyhDyzxHcfw(processInsId);
    }

    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 招行流程抵押流程增加回传功能
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("招行流程抵押流程增加回传功能")
    public void zsyhDyHcfw(String processInsId) {
        bdcDbxxService.zsyhDyHcfw(processInsId);
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [processInsId]
     * @return void
     * @description 更新当前流程所注销权利的附记 通过工作流配置
     */
    public void updateZxqlfj(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("工作流实例id为空，无法更新注销权利附记！");
        }
        bdcDbxxService.updateZxqlfj(processInsId);
    }


    /**
     * @param processInsId 工作流实例ID
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">sunyuzhuang</a>
     * @description 商品房首次登记更新外联证书的权利附记
     */
    @Override
    public void updateSpfscdjWlzsQlfj(@RequestParam(value = "processInsId") String processInsId) throws Exception {
        bdcDbxxService.updateSpfscdjWlzsQlfj(processInsId);
    }
}
