package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlJyxxService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.fcjyhtxx.zlfhtxxByzjh.HtxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.clfht.ZjClfHtxxDataDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.BaseQO;
import cn.gtmap.realestate.common.core.qo.accept.FcjyxxQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJyxxRestService;
import com.alibaba.fastjson.JSONObject;
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

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/24
 * @description 不动产受理交易信息rest服务
 */
@RestController
@Api(tags = "不动产受理交易信息服务")
public class BdcSlJyxxRestController extends BaseController implements BdcSlJyxxRestService {


    @Autowired
    BdcSlJyxxService bdcSlJyxxService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlJyxxRestController.class);

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "保存不动产受理交易信息", notes = "保存不动产受理交易信息服务")
    @ApiImplicitParam(name = "bdcSlJyxxDO", value = "不动产受理交易信息", required = true, dataType = "BdcSlJyxxDO")
    public BdcSlJyxxDO saveBdcSlJyxx(@RequestBody BdcSlJyxxDO bdcSlJyxxDO) {
        return bdcSlJyxxService.saveBdcSlJyxx(bdcSlJyxxDO);
    }

    /**
     * @param xmid 项目id
     * @return 不动产受理收费项目
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目id获取不动产受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据项目id获取不动产受理交易信息", notes = "根据项目id获取不动产受理交易信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public List<BdcSlJyxxDO> listBdcSlJyxxByXmid(@PathVariable(value = "xmid") String xmid) {
        return bdcSlJyxxService.listBdcSlJyxxByXmid(xmid);
    }

    /**
     * @param jyxxid 交易信息id
     * @return 不动产受理交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据交易信息id获取不动产受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据交易信息id获取不动产受理交易信息", notes = "根据交易信息id获取不动产受理交易信息服务")
    @ApiImplicitParam(name = "jyxxid", value = "交易信息id", required = true, dataType = "String", paramType = "path")
    public BdcSlJyxxDO queryBdcSlJyxxByJyxxid(@PathVariable(value = "jyxxid") String jyxxid) {
        return bdcSlJyxxService.queryBdcSlJyxxByJyxxid(jyxxid);
    }

    /**
     * @param bdcSlJyxxDO 不动产交易信息Do
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "新增不动产受理交易信息", notes = "新增不动产受理交易信息服务")
    @ApiImplicitParam(name = "bdcSlJyxxDO", value = "不动产受理交易信息", required = true, dataType = "BdcSlJyxxDO")
    public BdcSlJyxxDO insertBdcSlJyxx(@RequestBody BdcSlJyxxDO bdcSlJyxxDO) {
        return bdcSlJyxxService.insertBdcSlJyxx(bdcSlJyxxDO);
    }

    /**
     * @param jyxxid 交易信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据交易信息ID删除不动产受理交易信息", notes = "根据交易信息ID删除不动产受理交易信息")
    @ApiImplicitParam(name = "jyxxid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlJyxxByJyxxid(@PathVariable(name = "jyxxid") String jyxxid) {
        return bdcSlJyxxService.deleteBdcSlJyxxByJyxxid(jyxxid);
    }

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据项目ID删除不动产受理交易信息", notes = "根据项目ID删除不动产受理交易信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path")
    public Integer deleteBdcSlJyxxByXmid(@PathVariable(name = "xmid") String xmid) {
        return bdcSlJyxxService.deleteBdcSlJyxxByXmid(xmid);
    }

    /**
     * @param name
     * @param cardNo
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 房产交易限购信息接口查询
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "房产交易限购信息接口查询", notes = "房产交易限购信息接口查询服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cardNo", value = "证件号", required = true, dataType = "String", paramType = "query")})
    public XgxxHttpResponseDTO queryFcjyXgxx(String name, String cardNo) {
        return bdcSlJyxxService.queryFcjyXgxx(name,cardNo);
    }

    /**
     * @param fcjyxx 房产交易信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询房产交易合同信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询房产交易合同信息", notes = "查询房产交易合同信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fcjyxx", value = "房产交易信息", required = true, dataType = "FcjyxxQO", paramType = "body"),
    })
    public FcjyBaxxDTO queryFcjyClfHtxx(@RequestBody FcjyxxQO fcjyxx) {
        return bdcSlJyxxService.queryFcjyHtxx(fcjyxx);
    }

    /**
     * @param fcjyxx 房产交易信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 查询淮安房产交易合同信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询淮安房产交易合同信息", notes = "查询淮安房产交易合同信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "contractNo", value = "合同号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "fwlx", value = "房屋类型", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "lclx", value = "流程类型", required = true, dataType = "String", paramType = "path"),
    })
    public FcjyClfHtxxDTO queryHaFcjyxx(@RequestBody FcjyxxQO fcjyxx) {
        return bdcSlJyxxService.queryHaFcjyHtxx(fcjyxx);
    }

    /**
     * @description 查询淮安房屋信息是否签售
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/19 15:53
     * @param fwbh
     * @param xmmc
     * @param ysxkzh
     * @param qsdm
     * @param beanName
     * @return Object
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询淮安房屋信息是否签售", notes = "查询淮安房屋信息是否签售服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwbh", value = "房屋编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "xmmc", value = "项目名称", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "ysxkzh", value = "预售许可证号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qsdm", value = "区属代码", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "beanName", value = "beanName", required = true, dataType = "String", paramType = "path"),
    })
    public Object queryHaFcjyFwsfqs(@RequestParam(value = "fwbh", required = false) String fwbh, @RequestParam(value = "xmmc", required = false) String xmmc,
                                    @RequestParam(value = "ysxkzh", required = false) String ysxkzh, @RequestParam(value = "fh", required = false) String fh,
                                    @RequestParam(value = "qsdm", required = false) String qsdm, @RequestParam(value = "beanName") String beanName) {
        return bdcSlJyxxService.queryHaFcjyFwsfqs(fwbh, xmmc, ysxkzh, fh, qsdm, beanName);
    }

    /**
     * @description 推送淮安房产交易通知业务信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/31 19:36
     * @param porcessInsId 工作流实例id
     * @param ywlx 业务类型
     * @return void
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送淮安房产交易通知业务信息", notes = "推送淮安房产交易通知业务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "porcessInsId", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ywlx", required = true, dataType = "string", paramType = "query")})
    public void tsHaFcjyTsYwxx(@RequestParam(value = "processInsId") String porcessInsId,
                               @RequestParam(value = "ywlx") String ywlx,
                               @RequestParam(value = "xmids", required = false) String xmids) {
        try {
            bdcSlJyxxService.tsHaFcjyTsYwxx(porcessInsId, ywlx, xmids);
        } catch (Exception e) {
            LOGGER.error("推送淮安房产交易通知业务信息失败", e);
        }
    }

    /**
     * @description 推送淮安房产交易删除业务信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/31 19:36
     * @param porcessInsId 工作流实例id
     * @param reason 删除原因
     * @return void
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送淮安房产交易删除业务信息", notes = "推送淮安房产交易删除业务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "porcessInsId", required = true, dataType = "string", paramType = "query")})
    public void tsHaFcjyDelYwxx(@RequestParam(value = "processInsId") String porcessInsId, @RequestParam(value = "reason", required = false) String reason) {
        try {
            bdcSlJyxxService.tsHaFcjyDelYwxx(porcessInsId, reason);
        } catch (Exception e) {
            LOGGER.error("推送淮安房产交易删除业务信息失败", e);
        }

    }

    /**
     * @description 推送淮安房产交易删除业务信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/31 19:36
     * @param porcessInsId 工作流实例id
     * @return void
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送淮安房产交易还原业务信息", notes = "推送淮安房产交易还原业务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "porcessInsId", required = true, dataType = "string", paramType = "query")})
    public void tsHaFcjyHyYwxx(@RequestParam(value = "processInsId") String porcessInsId) {
        try {
            bdcSlJyxxService.tsHaFcjyHyYwxx(porcessInsId);
        } catch (Exception e) {
            LOGGER.error("推送淮安房产交易还原业务信息失败", e);
        }

    }

    /**
     * @description 补推淮安房产交易业务信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/8/31 19:36
     * @param rzid 操作日志id
     * @return boolean
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "补推淮安房产交易业务信息", notes = "补推淮安房产交易业务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "rzid", required = true, dataType = "string", paramType = "query")})
    public boolean btHaFcjyYwxx(@RequestParam(value = "rzid") String rzid) {
        if (StringUtils.isBlank(rzid)){
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return bdcSlJyxxService.btHaFcjyYwxx(rzid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param jsonStr 更新json字符串
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "批量更新受理交易信息", notes = "批量更新受理交易信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jsonStr", value = "受理交易信息对象", required = true, dataType = "String")})
    public int updateBatchBdcSlJyxx(@RequestParam("gzlslid")String gzlslid,@RequestParam(name = "jsonStr") String jsonStr,@RequestBody List<String> xmidList){
        if(StringUtils.isBlank(jsonStr) ||StringUtils.isBlank(gzlslid)){
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return bdcSlJyxxService.updateBatchBdcSlJyxx(gzlslid,jsonStr,xmidList);

    }

    /**
     * @param xmid    项目ID
     * @param jsonStr 更新json字符串
     * @param djxl    登记小类
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按照项目更新相关受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更对应项目的受理交易信息", notes = "更对应项目的受理交易信息服务")
    public int updateXmSlJyxx(@RequestParam("xmid") String xmid, @RequestParam(name = "jsonStr") String jsonStr, @RequestParam(name = "djxl", required = false) String djxl) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return bdcSlJyxxService.updateXmSlJyxx(xmid, jsonStr, djxl);
    }

    /**
     * @param xmid    项目ID
     * @param jsonStr 更新json字符串
     * @param djxl    登记小类
     * @return 更新数据量
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 按照项目id更新相关受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通过项目id更新对应项目的受理交易信息", notes = "通过项目id更新对应项目的受理交易信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "jsonStr", value = "更新实体JSON", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "djxl", value = "登记小类", required = false, dataType = "String", paramType = "query")
    })
    public int updateSlJyxxByXmid(@RequestParam("xmid") String xmid, @RequestParam(name = "jsonStr") String jsonStr, @RequestParam(name = "djxl", required = false) String djxl) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException(messageProvider.getMessage("message.noparameter"));
        }
        return bdcSlJyxxService.updateSlJyxxByXmid(xmid, jsonStr, djxl);
    }
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据xmid验证合肥维修资金缴纳状态", notes = "根据xmid验证合肥维修资金缴纳状态服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query")
    public String queryHfwxzjJnzt(@RequestParam(name = "xmid") String xmid){
        return bdcSlJyxxService.queryHfwxzjJnzt(xmid);

    }

    /**
     * @param htbh 交易合同编号
     * @return 不动产受理交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据交易合同编号获取不动产受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据合同编号获取不动产受理交易信息", notes = "根据合同编号获取不动产受理交易信息服务")
    @ApiImplicitParam(name = "htbh", value = "合同编号", required = true, dataType = "String", paramType = "path")
    public List<BdcSlJyxxDO> queryBdcSlJyxxByHtbh(@RequestParam(value = "htbh") String htbh) {
        return bdcSlJyxxService.listBdcSlJyxxByHtbh(htbh);
    }

    /**
     * 处理主房关联后 再次关联附房的逻辑
     * @param fcjyBaxxDTO
     * @param xmid
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    public void dealFsssBaxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @RequestParam(value = "xmid") String xmid) {
        bdcSlJyxxService.dealFsssBaxx(fcjyBaxxDTO,xmid);
    }


    /**
     * @param htbh 交易合同编号
     * @param fwlx 房屋类型
     * @param xmid 项目ID
     * @return 不动产受理交易信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据交易合同编号调用外部接口获取不动产受理交易信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询房产交易合同信息", notes = "查询房产交易合同信息服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "htbh", value = "合同号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fwlx", value = "房屋类型", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "String", paramType = "query"),
    })
    public Object queryFcjyHtxxByHtbh(@RequestParam(name="htbh") String htbh,
                                      @RequestParam(name="fwlx",required = false) String fwlx, @RequestParam(name="xmid")String xmid,@RequestParam(value="sfck",required = false)boolean sfck) {
        return bdcSlJyxxService.queryFcjyHtxxByHtbh(htbh, fwlx, xmid,sfck);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目ids查询受理交易信息
     */
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询房产交易合同信息", notes = "查询房产交易合同信息服务")
    @Override
    public List<BdcSlJyxxDO> listBdcSlJyxxByXmids(@RequestBody BaseQO baseQO) {
        return bdcSlJyxxService.listBdcSlJyxxByXmids(baseQO);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "校验当前合同编号是否已关联备案号", notes = "校验当前合同编号是否已关联备案号服务")
    @Override
    public boolean checkHtbhLinked(@RequestParam(value = "htbh") String htbh,@RequestParam(value = "fwyt",required = false) String fwyt){
        return bdcSlJyxxService.checkHtbhLinked(htbh, fwyt);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新交易信息", notes = "更新交易信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsonObject", value = "更新的字段集合", required = true, dataType = "JSONObject", paramType = "query"),
            @ApiImplicitParam(name = "htbh", value = "合同编号", required = true, dataType = "String", paramType = "query")
    })
    public void updateJyxxByHtbh(@RequestBody JSONObject jsonObject, @RequestParam(value = "htbh")String htbh){
        bdcSlJyxxService.updateJyxxByHtbh(jsonObject,htbh);

    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询房产交易信息并导入接口返回的交易信息", notes = "查询房产交易信息并导入接口返回的交易信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fcjyxxQO", value = "房产交易信息", required = true, dataType = "FcjyBaxxDTO", paramType = "body"),
    })
    @Override
    public FcjyBaxxDTO queryFcjyxxWithImport(@RequestBody FcjyxxQO fcjyxxQO) throws Exception {
        return this.bdcSlJyxxService.queryFcjyxxWithImport(fcjyxxQO);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "处理不动产受理交易信息，更新为房产接口中的交易信息内容", notes = "处理不动产受理交易信息，更新为房产接口中的交易信息内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fcjyBaxxDTO", value = "房产交易信息", required = true, dataType = "FcjyBaxxDTO", paramType = "body"),
            @ApiImplicitParam(name = "lclx", value = "流程类型", required = true, dataType = "string", paramType = "path"),
    })
    @Override
    public void handleFcjyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @PathVariable(value = "lclx") String lclx) throws Exception {
        this.bdcSlJyxxService.handleFcjyxx(fcjyBaxxDTO, lclx);
    }

    /**
     * @param qlrxx 权利人信息集合
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权利人是否限购，返回名称证件号和是否限购
     * @date : 2021/4/20 14:51
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询权利人是否限购，返回名称证件号和是否限购", notes = "查询权利人是否限购，返回名称证件号和是否限购")
    public XgxxDTO listQlrXgxx(@RequestBody(required = false) List<Object> qlrxx) {
        return bdcSlJyxxService.listQlrXgxx(qlrxx);
    }


    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询权利人是否限购，返回名称证件号和是否限购
     * @date : 2021/4/20 14:51
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询权利人限购信息", notes = "查询权利人限购信息")
    public List<XgxxDTO> listXgxx(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlJyxxService.listXgxx(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "处理并生成受理交易相关信息", notes = "处理并生成受理交易相关信息")
    public void dealSlJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO,@RequestParam(value = "gzlslid",required = false) String gzlslid,@PathVariable(value = "cqxmid") String cqxmid){
        bdcSlJyxxService.dealSlJyxx(fcjyBaxxDTO, gzlslid, cqxmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "处理交易获取登记信息", notes = "处理交易获取登记信息")
    public void dealNtDjxx(@RequestBody JSONObject param, @RequestParam(name = "processInsId", required = false) String processInsId) throws Exception{
        bdcSlJyxxService.dealNtDjxx(param,processInsId);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "一体化处理交易信息", notes = "一体化处理交易信息")
    public void dealSpfClfJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "uploadFj", required = false) Boolean uploadFj) {
        bdcSlJyxxService.dealSpfClfJyxx(fcjyBaxxDTO, xmid, uploadFj);

    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取预告交易信息
     * @date : 2022/1/6 16:16
     */
    @Override
    public void extendYgJyxx(@PathVariable(value = "gzlslid") String gzlslid) {
        bdcSlJyxxService.extendYgjyxx(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "南通住建交易信息", notes = "南通住建交易信息")
    public List<FcjyHtxxDTO> dealNtZjjyxx(@RequestBody List<FcjyClfHtxxDTO> fcjyClfHtxxDTOList, @RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid,
                                          @RequestParam(name = "foldName", required = false) String foldName) {
        if (CollectionUtils.isEmpty(fcjyClfHtxxDTOList)) {
            throw new AppException("查询南通住建交易信息，房产存量房合同信息为空");
        }
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(xmid) || StringUtils.isBlank(foldName)) {
            throw new AppException("查询南通住建交易信息，工作流实例id或项目id为空或文件夹名称为空");
        }
        List<FcjyHtxxDTO> list = bdcSlJyxxService.dealNtZjjyxx(fcjyClfHtxxDTOList, gzlslid, xmid, foldName);
        return list;
    }

    /**
     * @param fcjyBaxxDTO
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 一人办件处理交易信息
     * @date : 2022/8/3 17:35
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    public void dealYrbjJyxx(@RequestBody FcjyBaxxDTO fcjyBaxxDTO, @RequestParam(value = "xmid") String xmid) {
        bdcSlJyxxService.dealYrbjJyxx(fcjyBaxxDTO, xmid);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "交易核验，验证住建系统中该产权是否有抵押或者查封信息", notes = "交易核验，验证住建系统中该产权是否有抵押或者查封信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "xmid", value = "项目ID", required = false, dataType = "String", paramType = "param"),
    })
    @Override
    public Object jyhy(@RequestParam(name = "gzlslid")String gzlslid,
                       @RequestParam(name = "xmid", required = false)String xmid) {
        return bdcSlJyxxService.jyhy(gzlslid, xmid);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送反馈状态给住建交易系统", notes = "推送反馈状态给住建交易系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = false, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "htbh", value = "合同编号", required = true, dataType = "String", paramType = "param"),
            @ApiImplicitParam(name = "zjh", value = "证件号", required = false, dataType = "String", paramType = "param"),
    })
    @Override
    public void fkzjzt(@RequestParam(value = "gzlslid", required = false) String gzlslid, @RequestParam(value = "htbh") String htbh,
                       @RequestParam(value = "zjh", required = false) String zjh) {
        this.bdcSlJyxxService.fkzjzt(gzlslid, htbh, zjh);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "接收住建合同信息", notes = "接收住建合同信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zjClfHtxxDataDTO", value = "住建存量房合同信息DTO", required = true, dataType = "ZjClfHtxxDataDTO", paramType = "body"),
    })
    @Override
    public void jsZjHtxx(@RequestBody ZjClfHtxxDataDTO zjClfHtxxDataDTO) {
        this.bdcSlJyxxService.jsZjHtxx(zjClfHtxxDataDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送受理信息给住建交易系统", notes = "推送受理信息给住建交易系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "String", paramType = "param"),
    })
    @Override
    public void tsZjSlxx(@RequestParam(value = "gzlslid") String gzlslid) {
        this.bdcSlJyxxService.tsZjSlxx(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "南通住建商品房备案交易信息", notes = "南通住建商品房备案交易信息")
    public void dealNtSpfBaJyxx(@RequestBody List<JSONObject> ntSpfBaJyxxList, @RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid,
                                @RequestParam(name = "foldName", required = false) String foldName) {
        if (CollectionUtils.isEmpty(ntSpfBaJyxxList)) {
            throw new AppException("查询南通住建交易信息，商品房备案交易信息为空");
        }
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(xmid) || StringUtils.isBlank(foldName)) {
            throw new AppException("查询南通住建交易信息，工作流实例id或项目id为空或文件夹名称为空");
        }
        bdcSlJyxxService.dealNtSpfBaJyxx(ntSpfBaJyxxList, gzlslid, xmid, foldName);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "通州住建商品房备案交易信息", notes = "通州住建商品房备案交易信息")
    public void dealTzSpfBaJyxx(@RequestBody List<HtxxDTO> ntSpfBaJyxxList, @RequestParam(name = "gzlslid", required = false) String gzlslid, @RequestParam(name = "xmid", required = false) String xmid,
                                @RequestParam(name = "foldName", required = false) String foldName) {
        if (CollectionUtils.isEmpty(ntSpfBaJyxxList)) {
            throw new AppException("查询通州住建交易信息，商品房备案交易信息为空");
        }
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(xmid) || StringUtils.isBlank(foldName)) {
            throw new AppException("查询通州住建交易信息，工作流实例id或项目id为空或文件夹名称为空");
        }
        bdcSlJyxxService.dealTzSpfBaJyxx(ntSpfBaJyxxList, gzlslid, xmid, foldName);
    }

    /**
     * @param fcjyxx 房产交易信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取二手房网签合同数据
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取二手房网签合同数据", notes = "获取二手房网签合同数据")
    public String queryEsfWqHtxx(@RequestBody FcjyxxQO fcjyxx) {
        return bdcSlJyxxService.queryEsfWqHtxx(fcjyxx);
    }

    /**
     * @param gzlslid 工作流实例id
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 工作流事件，推送不动产转移和抵押登记登簿信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "推送不动产转移和抵押登记登簿信息", notes = "推送不动产转移和抵押登记登簿信息")
    public void tsBdcZyDyDjxx(@RequestParam(value = "processInsId") String gzlslid) {
        bdcSlJyxxService.tsBdcZyDyDjxx(gzlslid);
    }
}
