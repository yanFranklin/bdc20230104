package cn.gtmap.realestate.register.ui.web.rest.xxbl;

import cn.gtmap.gtc.clients.ElementClient;
import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AuditLogDto;
import cn.gtmap.gtc.sso.domain.dto.DataValue;
import cn.gtmap.gtc.sso.domain.dto.LogMsgDTO;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcBlxxDTO;
import cn.gtmap.realestate.common.core.ex.EntityNotFoundException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxlDjyyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcHfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGzyzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.*;
import cn.gtmap.realestate.common.core.service.rest.init.BdcInitRestService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.LogCompareUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.register.ui.util.Constants;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 不动产信息补录主页服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.1, 2019/12/17 11:00
 */
@RestController
@RequestMapping("/rest/v1.0/blxx")
public class BdcXxblController extends BaseController {
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcXxblFeignService bdcXxblFeignService;
    @Autowired
    private BdcGzyzFeignService bdcGzyzFeignService;
    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    private BdcInitRestService bdcInitFeignService;
    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    ElementClient elementClient;
    @Autowired
    LogMessageClient logMessageClient;
    @Value("${html.version:}")
    private String version;
    @Autowired
    BdcYwsjHxFeignService bdcYwsjHxFeignService;

    /**
     * 信息补录流程基本信息页面lc_home.html是否需要自动保存登簿人信息
     */
    @Value("${xxbl.lc.sfxydbr: true}")
    private Boolean sfxydbr;

    /**
     * 信息补录 查询 项目来源 默认为3 其他
     */
    @Value("${xxbl.query.xmly:3}")
    private String XXBL_QUERY_XMLY;

    /**
     * 信息补录界面需要隐藏修改按钮的角色
     */
    @Value("${xxbl.ycxg.roleid:}")
    private String ycxgRoleIds;

    @Value("${xxbl.xxblyz:false}")
    private boolean xxbllcyz;

    /*
     * 信息补录修改的流程定义id
     * */

    @Value("#{'${xxbl.modify.gzldyid:}'.split(',')}")
    private List<String> xxblXgGzldyidList;

    /*
     * 恢复
     * */
    @Value("#{'${xxbl.rollback.gzldyid:}'.split(',')}")
    private List<String> xxblHfGzldyid;

    /**
     * 删除补数据
     *
     * @param xmid 项目 ID
     * @param all  是否删除全部
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping
    public boolean deleteBlxx(@RequestParam("xmid") String xmid, @RequestParam("all") boolean all) throws Exception {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("xmid");
        }
        return bdcXxblFeignService.deleteBlxx(xmid, all);
    }

    /**
     * 获取补录分页查询数据
     *
     * @param pageable 分页对象
     * @param bdcQlQO  分页查询不动产权利页面参数封装对象
     * @return 分页信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/page")
    public Object listBlxx(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO) {
        bdcQlQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("xxbl","",""));
        if(StringUtils.isNotBlank(XXBL_QUERY_XMLY)) {
            List<String> xmlyList = Arrays.asList(XXBL_QUERY_XMLY.split(","));
            List<Integer> xmlyIntList = new ArrayList<>();
            xmlyList.stream().forEach(xmly -> xmlyIntList.add(Integer.parseInt(xmly)));
            bdcQlQO.setXmly(xmlyIntList);
        }
        if (StringUtils.isNotBlank(bdcQlQO.getBdcdyh())) {
            bdcQlQO.setBdcdyh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdyh()));
        }
        if (StringUtils.isBlank(bdcQlQO.getZlmh())) {
            bdcQlQO.setZlmh("4");
        }
        //流程不同查询的权属状态不同
        //修改只能选现势的数据，恢复只能选注销的数据，删除没有权属状态限制
        if (CollectionUtils.isNotEmpty(xxblXgGzldyidList) && xxblXgGzldyidList.contains(bdcQlQO.getGzldyid())) {
            bdcQlQO.setQszt(1);
        }
        if (CollectionUtils.isNotEmpty(xxblHfGzldyid) && xxblHfGzldyid.contains(bdcQlQO.getGzldyid())) {
            bdcQlQO.setQszt(2);
        }
        bdcQlQO.setGzldyid("");
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        return addLayUiCode(bdcXmFeignService.bdcQlPageByPageJson(pageable, bdcQlQoStr));
    }

    /**
     * 获取补录分页查询数据
     * 南通版本
     *
     * @param pageable 分页对象
     * @param bdcQlQO  分页查询不动产权利页面参数封装对象
     * @return 分页信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/page/nantong")
    public Object listBlxxNt(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO) {
        // 设置项目来源为 3 和 2
        bdcQlQO.setXmly(Arrays.asList(CommonConstantUtils.XMLY_QT_DM, 2));
        if (StringUtils.isNotBlank(bdcQlQO.getBdcdyh())) {
            bdcQlQO.setBdcdyh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdyh()));
        }
        String bdcQlQoStr = JSON.toJSONString(bdcQlQO);
        return addLayUiCode(bdcXmFeignService.bdcQlPageByPageJson(pageable, bdcQlQoStr));
    }

    /**
     * 查询不动产单元现势状态
     *
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/bdcdyzt")
    public Object queryBdcdyZt(String bdcdyh,String qjgldm) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            return bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(StringUtils.deleteWhitespace(bdcdyh),qjgldm);
        } else {
            return null;
        }
    }

    /**
     * 初始化补录数据
     *
     * @param bdcBlxxDTO 不动产补录信息 DTO
     * @return {Object} 如果初始化成功返回 BdcXmDO 集合，否则返回一个 null 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/csh")
    public Object cshBlxx(@RequestBody BdcBlxxDTO bdcBlxxDTO) throws Exception {
        if (StringUtils.isAnyBlank(bdcBlxxDTO.getGzldyid(), bdcBlxxDTO.getLcmc())) {
            throw new MissingArgumentException("补录数据传入参数缺失！");
        }
        return bdcXxblFeignService.cshBlxx(bdcBlxxDTO);
    }

    /**
     * 复制初始化数据
     *
     * @param yxmid  被复制的项目 id
     * @param bdcdyh 需要复制的不动产单元
     * @return {Object} 如果初始化成功返回 BdcXmDO 集合，否则返回一个 null 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/copy")
    public Object copyBlxx(@RequestParam("yxmid") String yxmid, @RequestParam("bdcdyh") String bdcdyh) throws Exception {
        if (StringUtils.isAnyBlank(yxmid, bdcdyh)) {
            throw new MissingArgumentException("缺少参数 xmid 或 bdcdyh");
        }
        String dyh = StringUtils.deleteWhitespace(bdcdyh);
        return bdcXxblFeignService.copyBlxx(yxmid, dyh);
    }

    /**
     * 挂接主房信息
     *
     * @param yxmid  被挂接的项目 id
     * @param bdcdyh 需要挂接的不动产单元
     * @return {BdcXmDO} 挂接后生成的项目信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 将传入的不动产单元号挂接到 yxmid 对应的项目 <br>
     * 场景：车库挂接主房
     * 1. 生成项目、fdcq、权利人和证书关系并且插入 cshfwkgsl <strong> zlcsh = 1 作为挂接项目的标识<strong/>
     * 2. 和 bdcdy 相关信息取权籍，其余取自原项目数据
     * 3. 受理人相关信息获取当前用户，受理时间取系统时间
     */
    @GetMapping("/gjxx")
    public Object gjZfxx(@RequestParam("yxmid") String yxmid, @RequestParam("bdcdyh") String bdcdyh, @RequestParam(value = "qjgldm",required = false) String qjgldm) throws Exception {
        if (StringUtils.isAnyBlank(yxmid, bdcdyh)) {
            throw new MissingArgumentException("yxmid 或 bdcdyh");
        }
        String dyh = StringUtils.deleteWhitespace(bdcdyh);
        return bdcXxblFeignService.gjZfxx(yxmid, dyh,qjgldm);
    }

    /**
     * 根据 xmid 查询是否生成证书
     * 如果不存在 BdcCshFwkgSlDO 则手动插入一条数据
     *
     * @param xmid 项目 id
     * @return {int} 是否生成证书， 0：否  1：是
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("sfsczs")
    public int querySfsczs(@RequestParam("xmid") String xmid, @RequestParam("djxl") String djxl) {
        if (StringUtils.isBlank(xmid) && StringUtils.isBlank(djxl)) {
            throw new MissingArgumentException("缺失必要参数 XMID");
        }
        return bdcXxblFeignService.querySfsczs(xmid, djxl);
    }

    /**
     * 保存不动产项目信息, 同时记录操作日志
     *
     * @param bdcXmDO 不动产项目 DO 对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/xmxx")
    public void updateBdcXm(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "xgyy", required = false) String xgyy) throws Exception {
        String xmid = bdcXmDO.getXmid();
        // 查询出修改前的业务信息
        BdcYwxxDTO bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        // 如果日期没有变化不更新改字段
        if (bdcXmDO.getDjsj() != null) {
            BdcXmDO xm = bdcYwxxDTOBefore.getBdcXm();
            if (xm.getDjsj() != null) {
                boolean sameDay = DateUtils.isSameDay(xm.getDjsj(), bdcXmDO.getDjsj());
                if (sameDay) {
                    bdcXmDO.setDjsj(null);
                }
            }
        }
        // 盐城 信息补录保存时如果登记时间和登簿人为空，默认填入当前时间和当前人（为了审核时能正常审核）
        if(StringUtils.isNotBlank(version) && StringUtils.equals(version,"yancheng")){
            if(bdcXmDO.getDjsj() == null){
                if(bdcYwxxDTOBefore.getBdcXm() != null && bdcYwxxDTOBefore.getBdcXm().getDjsj() == null){
                    bdcXmDO.setDjsj(new Date());
                }
            }
            if(StringUtils.isBlank(bdcXmDO.getDbr()) && sfxydbr) {
                if(bdcYwxxDTOBefore.getBdcXm() != null && StringUtils.isBlank(bdcYwxxDTOBefore.getBdcXm().getDbr())){
                    bdcXmDO.setDbr(userManagerUtils.getCurrentUserName());
                }
            }
        }

        // 更新业务信息
        bdcXmFeignService.updateBdcXm(bdcXmDO);
        // 查询过修改后的结果
        BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(xmid);
        Map<String, String> data = LogCompareUtils.initDataString(xmid, bdcYwxxDTOBefore, bdcYwxxDTOAfter);
        if (StringUtils.isNotBlank(xgyy) && StringUtils.isNotBlank(version) && StringUtils.equals(version, "yancheng")) {
            data.put("xgyy", xgyy);
        }
        if (StringUtils.isNotBlank(RSAEncryptUtils.decrypt(data.get("change").toString()))) {
            LogMsgDTO logMsgDTO = new LogMsgDTO();
            logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
            logMsgDTO.setTags(data);
            logMsgDTO.setEvent(CommonConstantUtils.XXBL);
            logMessageClient.save(logMsgDTO);
        }
        //回写大云
        bdcYwsjHxFeignService.saveBdcYwsj(bdcXmDO.getGzlslid());
    }

    /**
     * 关联产权数据验证（判断是否可以关联产权）
     *
     * @param gzlslid 当前工作流实例 id
     * @param yxmid   上一手项目id
     * @return {String} bdcdy：关联bdcdyh 不同， success：可以关联，
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/glsjyz")
    public String glsjyz(@RequestParam("gzlslid") String gzlslid, @RequestParam("yxmid") String yxmid) {
        if (StringUtils.isAnyBlank(yxmid, gzlslid)) {
            throw new MissingArgumentException("缺少参数 yxmid 或 gzlslid");
        }
        return bdcXxblFeignService.glsjyz(gzlslid, yxmid);
    }

    /**
     * 关联产权
     *
     * @param gzlslid 当前工作流实例 id
     * @param xmid    上一手项目id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * 产证修改增加关联上一手
     */
    @GetMapping("/glcq")
    public void glcq(@RequestParam("gzlslid") String gzlslid, @RequestParam("xmid") String xmid,
                     @RequestParam("blxmid") String blxmid ) {
        bdcXxblFeignService.glcq(gzlslid, xmid,blxmid);
    }

    /**
     * 更新权利冗余字段
     *
     * @param json         权利
     * @param xmid         单个传 xmid
     * @param processInsId 批量传 processInsId，不传 xmid
     */
    @PatchMapping("/updateQlRyzd")
    public void updateQlRyzd(@RequestBody String json, @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "processInsId", required = false) String processInsId) {
        if (StringUtils.isBlank(xmid) && StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺少参数：xmid,processInsId");
        }
        if (StringUtils.isBlank(xmid)) {
            //批量更新
            List<BdcQl> bdcQlList = bdcQllxFeignService.listQlxxByProcessInsId(processInsId);
            for (BdcQl bdcQl : bdcQlList) {
                updateQl(bdcQl, json);
            }
        } else {
            // 如果日期没有变化不更新改字段
//            BdcXmQO bdcXmQO = new BdcXmQO();
//            bdcXmQO.setXmid(xmid);
//            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
//            BdcXmDO bdcXmDO = bdcXmDOS.get(0);
//            if (bdcXmDO.getDjsj() != null) {
//                JSONObject obj = JSONObject.parseObject(json);
//                if (obj.get("djsj") != null) {
//                    Date djsj = cn.gtmap.realestate.common.util.DateUtils.formatDate(obj.get("djsj").toString(), "yyyy-MM-dd hh:mm:ss");
//                    boolean sameDay = DateUtils.isSameDay(djsj, bdcXmDO.getDjsj());
//                    if (sameDay) {
//                        obj.remove("djsj");
//                        json = JSONObject.toJSONString(obj);
//                    }
//                }
//            }
            //单个更新
            BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
            updateQl(bdcQl, json);
        }
    }

    /**
     * @param bdcQl 不动产权利
     * @param json  json集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新权利
     */
    private void updateQl(BdcQl bdcQl, String json) {
        if (bdcQl == null) {
            throw new MissingArgumentException("缺少参数：未找到对应的权利");
        }
        JSONObject obj = JSONObject.parseObject(json);
        obj.put("qlid", bdcQl.getQlid());
        json = JSONObject.toJSONString(obj);
        bdcEntityFeignService.updateByJsonEntity(json, bdcQl.getClass().getName());

    }

    /**
     * 保存权利信息, 同时记录操作日志
     *
     * @param json      权力信息 json 格式
     * @param className 对应的权利类的 class
     * @param xmid      项目 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/qlxx")
    public void updateQlxx(@RequestBody String json, @RequestParam(value = "className") String className, @RequestParam(value = "xmid") String xmid) throws Exception {
        className = "cn.gtmap.realestate.common.core.domain." + className;
        BdcYwxxDTO bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        // 更新权力信息
        JSONObject jsonObject = JSON.parseObject(json);
        bdcEntityFeignService.updateByJsonEntity(jsonObject.toJSONString(), className);
        BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(xmid);
        Map<String, String> data = LogCompareUtils.initDataString(xmid, bdcYwxxDTOBefore, bdcYwxxDTOAfter);
        LOGGER.warn("当前项目id{}修改前业务信息{}修改后业务信息{}", xmid, JSON.toJSONString(bdcYwxxDTOBefore), JSON.toJSONString(bdcYwxxDTOAfter));
        if (StringUtils.isNotBlank(RSAEncryptUtils.decrypt(data.get("change").toString()))) {
            LogMsgDTO logMsgDTO = new LogMsgDTO();
            logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
            logMsgDTO.setTags(data);
            logMsgDTO.setEvent(CommonConstantUtils.XXBL);
            LOGGER.warn("xmid={}信息补录权利开始更新记录日志", xmid);
            logMessageClient.save(logMsgDTO);
        }
    }

    /**
     * 根据工作流实例 id 获取项目信息
     *
     * @param processInstanceId 工作流实例 id
     * @return bdcXmDOList
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/xmxx")
    public BdcXmDO queryXmxxByProcessInsId(@RequestParam(value = "processInstanceId") String processInstanceId,
                                           @RequestParam(value = "xmid") String xmid) {
        if (StringUtils.isBlank(processInstanceId)) {
            throw new MissingArgumentException("查询项目信息缺少 gzlslid");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processInstanceId);
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            return bdcXmDOList.get(0);
        }
        return new BdcXmDO();
    }

    /**
     * 根据项目 id 获取权利信息
     * 目前只考虑「地役权」的情况
     *
     * @param xmid 项目 id
     * @return BdcQl
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/qlxx")
    public BdcQl queryQlxxByXmid(@RequestParam(value = "xmid") String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new MissingArgumentException("查询项目信息缺少 xmid");
        }
        return bdcQllxFeignService.queryQlxx(xmid);
    }


    /**
     * @param bdcDjxlDjyyQO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据登记小类获取登记原因集合
     */
    @GetMapping("djyy")
    public Object queryDjxlDjyy(BdcDjxlDjyyQO bdcDjxlDjyyQO) {
        return bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO);
    }

    /**
     * 一键注销原权利
     *
     * @param bdcZxQO 不动产注销 QO 对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/zxql")
    public String zxQl(@RequestBody BdcZxQO bdcZxQO) {
        if (bdcZxQO == null || CollectionUtils.isEmpty(bdcZxQO.getXmidList())) {
            throw new MissingArgumentException("xmid");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcZxQO.getXmidList().get(0));
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new EntityNotFoundException("未查询到项目信息");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        if (CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDO.getQszt())) {
            return "history";
        }
        bdcZxQO.setQszt(CommonConstantUtils.QSZT_HISTORY);
        bdcDbxxFeignService.zxQl(bdcZxQO, userManagerUtils.getCurrentUserName());
        // 更新注销权利的附记
        bdcXxblFeignService.updateZxqlFj(bdcZxQO.getXmidList());
        return "success";
    }

    /**
     * 注销还原成现势
     *
     * @param bdcHfQO 恢复权利信息 QO 对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/hyql")
    public String hyql(@RequestBody BdcHfQO bdcHfQO) {
        if (bdcHfQO == null || CollectionUtils.isEmpty(bdcHfQO.getXmidList())) {
            throw new MissingArgumentException("xmid");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(bdcHfQO.getXmidList().get(0));
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOS)) {
            throw new EntityNotFoundException("未查询到项目信息");
        }
        BdcXmDO bdcXmDO = bdcXmDOS.get(0);
        String yzxx = yzHfQlxx(bdcXmDO);
        if (!StringUtils.equals(yzxx, "success")) {
            return yzxx;
        }
        if (CommonConstantUtils.QSZT_VALID.equals(bdcXmDO.getQszt())) {
            return "valid";
        }
        bdcHfQO.setQszt(CommonConstantUtils.QSZT_VALID);
        try {
            int count = bdcDbxxFeignService.hfQl(bdcHfQO);
        } catch (Exception e) {
            LOGGER.error("还原权利信息异常：{}", e.getMessage());
            return "还原权利信息异常";
        }
        return "success";
    }

    /**
     * 信息补录办结状态修改
     * 更新案件状态为2已完成状态，并更新项目结束时间
     * 修改项目和权利的 qszt
     *
     * @param gzlslid 工作流实例 id
     */
    @GetMapping("/end")
    public void changeAjzt(@RequestParam("gzlslid") String gzlslid) {
        bdcXxblFeignService.endLc(gzlslid, userManagerUtils.getCurrentUserName());
    }

    /**
     * 其他规则验证
     *
     * @param bdcGzYzQO 规则验证查询实体
     * @return {List} 验证结果
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/bdcGzyz")
    public List<Map<String, Object>> yzBdcgz(@RequestBody BdcGzYzQO bdcGzYzQO) {
        return bdcGzyzFeignService.yzBdcgz(bdcGzYzQO);

    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息补录是走信息补录流程的规则验证还是走选择的流程的验证
     * @date : 2022/4/20 16:03
     */
    @GetMapping("/xxblyz")
    public boolean getXxblYzfs() {
        return xxbllcyz;
    }

    /**
     * 流程规则验证
     *
     * @param bdcGzYzQO 规则验证查询实体
     * @return {List} 验证结果
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @PostMapping("/qtyz")
    public List<Map<String, Object>> qtyz(@RequestBody BdcGzYzQO bdcGzYzQO) {
        return bdcGzyzFeignService.qtyz(bdcGzYzQO);
    }

    /**
     * 根据项目id查询相关信息,用于信息补录-新增日志记录
     * @param xmind
     * @throws Exception
     */
    @GetMapping("/searchInfoById")
    public Map<String,String> searchInfoById(@RequestParam("xmid") String xmind) throws Exception {

        BdcYwxxDTO bdcYwxxDTO = bdcInitFeignService.queryYwxx(xmind);

        Map<String,String> map = new ConcurrentHashMap<>();

        if(Objects.nonNull(bdcYwxxDTO.getBdcXm())){
            BdcXmDO bdcXmDO = bdcYwxxDTO.getBdcXm();
            StringBuffer sb = new StringBuffer();
            sb.append("项目ID:").append(bdcXmDO.getXmid() == null ? "" : bdcXmDO.getXmid()).append(",")
                    .append("不动产权证号:").append(bdcXmDO.getBdcqzh() == null ? "" : bdcXmDO.getBdcqzh()).append(",")
                    .append("受理编号:").append(bdcXmDO.getSlbh() == null ? "" : bdcXmDO.getSlbh()).append(",")
                    .append("权利类型:").append(bdcXmDO.getQllx() == null ? "" : bdcXmDO.getQllx()).append(",")
                    .append("登记类型:").append(bdcXmDO.getDjlx() == null ? "" : bdcXmDO.getDjlx()).append(",")
                    .append("登记原因:").append(bdcXmDO.getDjyy() == null ? "" : bdcXmDO.getDjyy()).append(",")
                    .append("申请证书版式:").append(bdcXmDO.getSqzsbs() == null ? "" : bdcXmDO.getSqzsbs()).append(",")
                    .append("原不动产单元号:").append(bdcXmDO.getYbdcdyh() == null ? "" : bdcXmDO.getYbdcdyh()).append(",")
                    .append("申请分别持证:").append(bdcXmDO.getSqfbcz() == null ? "" : bdcXmDO.getSqfbcz()).append(",")
                    .append("备注:").append(bdcXmDO.getBz() == null ? "" : bdcXmDO.getBz()).append(",")
                    .append("申请人说明:").append(bdcXmDO.getSqrsm() == null ? "" : bdcXmDO.getSqrsm()).append(",")
                    .append("案件状态:").append(bdcXmDO.getAjzt() == null ? "" : bdcXmDO.getAjzt()).append(",")
                    .append("是否问题案件:").append(bdcXmDO.getSfwtaj() == null ? "" : bdcXmDO.getSfwtaj()).append(",")
                    .append("工作流节点名称:").append(bdcXmDO.getGzljdmc() == null ? "" : bdcXmDO.getGzljdmc()).append(",")
                    .append("区县代码:").append(bdcXmDO.getQxdm() == null ? "" : bdcXmDO.getQxdm()).append(",")
                    .append("受理时间:").append(bdcXmDO.getSlsj() == null ? "" : bdcXmDO.getSlsj()).append(",")
                    .append("受理人ID:").append(bdcXmDO.getSlrid() == null ? "" : bdcXmDO.getSlrid()).append(",")
                    .append("受理人:").append(bdcXmDO.getSlr() == null ? "" : bdcXmDO.getSlr()).append(",")
                    .append("项目来源:").append(bdcXmDO.getXmly() == null ? "" : bdcXmDO.getXmly()).append(",")
                    .append("结束时间:").append(bdcXmDO.getJssj() == null ? "" : bdcXmDO.getJssj()).append(",")
                    .append("登记小类:").append(bdcXmDO.getDjxl() == null ? "" : bdcXmDO.getDjxl()).append(",")
                    .append("不动产类型:").append(bdcXmDO.getBdclx() == null ? "" : bdcXmDO.getBdclx()).append(",")
                    .append("原产权证号:").append(bdcXmDO.getYcqzh() == null ? "" : bdcXmDO.getYcqzh()).append(",")
                    .append("原房产证号:").append(bdcXmDO.getYfczh() == null ? "" : bdcXmDO.getYfczh()).append(",")
                    .append("原土地证号:").append(bdcXmDO.getYtdzh() == null ? "" : bdcXmDO.getYtdzh()).append(",")
                    .append("工作流实例ID:").append(bdcXmDO.getGzlslid() == null ? "" : bdcXmDO.getGzlslid()).append(",")
                    .append("审批系统业务号:").append(bdcXmDO.getSpxtywh() == null ? "" : bdcXmDO.getSpxtywh()).append(",")
                    .append("所属乡镇:").append(bdcXmDO.getSsxz() == null ? "" : bdcXmDO.getSsxz()).append(",")
                    .append("登记机构:").append(bdcXmDO.getDjjg() == null ? "" : bdcXmDO.getDjjg()).append(",")
                    .append("审批系统办理状态:").append(bdcXmDO.getSpxtblzt() == null ? "" : bdcXmDO.getSpxtblzt()).append(",")
                    .append("审批系统办理状态名称:").append(bdcXmDO.getSpxtblztmc() == null ? "" : bdcXmDO.getSpxtblztmc()).append(",")
                    .append("不动产单元号:").append(bdcXmDO.getBdcdyh() == null ? "" : bdcXmDO.getBdcdyh()).append(",")
                    .append("不动产唯一编码:").append(bdcXmDO.getBdcdywybh() == null ? "" : bdcXmDO.getBdcdywybh()).append(",")
                    .append("共有方式:").append(bdcXmDO.getGyfs() == null ? "" : bdcXmDO.getGyfs()).append(",")
                    .append("登记时间:").append(bdcXmDO.getDjsj() == null ? "" : bdcXmDO.getDjsj()).append(",")
                    .append("登簿人:").append(bdcXmDO.getDbr() == null ? "" : bdcXmDO.getDbr()).append(",")
                    .append("权属状态:").append(bdcXmDO.getQszt() == null ? "" : bdcXmDO.getQszt()).append(",")
                    .append("坐落:").append(bdcXmDO.getZl() == null ? "" : bdcXmDO.getZl()).append(",")
                    .append("定着物面积:").append(bdcXmDO.getDzwmj() == null ? "" : bdcXmDO.getDzwmj()).append(",")
                    .append("定着物用途:").append(bdcXmDO.getDzwyt() == null ? "" : bdcXmDO.getDzwyt()).append(",")
                    .append("定着物用途2:").append(bdcXmDO.getDzwyt2() == null ? "" : bdcXmDO.getDzwyt2()).append(",")
                    .append("定着物用途3:").append(bdcXmDO.getDzwyt3() == null ? "" : bdcXmDO.getDzwyt3()).append(",")
                    .append("用海类型A:").append(bdcXmDO.getYhlxa() == null ? "" : bdcXmDO.getYhlxa()).append(",")
                    .append("用海类型B:").append(bdcXmDO.getYhlxb() == null ? "" : bdcXmDO.getYhlxb()).append(",")
                    .append("构筑物类型:").append(bdcXmDO.getGzwlx() == null ? "" : bdcXmDO.getGzwlx()).append(",")
                    .append("林种:").append(bdcXmDO.getLz() == null ? "" : bdcXmDO.getLz()).append(",")
                    .append("面积单位:").append(bdcXmDO.getMjdw() == null ? "" : bdcXmDO.getMjdw()).append(",")
                    .append("宗地宗海面积:").append(bdcXmDO.getZdzhmj() == null ? "" : bdcXmDO.getZdzhmj()).append(",")
                    .append("权利人:").append(bdcXmDO.getQlr() == null ? "" : bdcXmDO.getQlr()).append(",")
                    .append("权利人证件号:").append(bdcXmDO.getQlrzjh() == null ? "" : bdcXmDO.getQlrzjh()).append(",")
                    .append("义务人:").append(bdcXmDO.getYwr() == null ? "" : bdcXmDO.getYwr()).append(",")
                    .append("义务人证件号:").append(bdcXmDO.getYwrzjh() == null ? "" : bdcXmDO.getYwrzjh()).append(",")
                    .append("交易合同号:").append(bdcXmDO.getJyhth() == null ? "" : bdcXmDO.getJyhth()).append(",")
                    .append("不动产单元房屋类型:").append(bdcXmDO.getBdcdyfwlx() == null ? "" : bdcXmDO.getBdcdyfwlx()).append(",")
                    .append("不动产权证号:").append(bdcXmDO.getBdcqzh() == null ? "" : bdcXmDO.getBdcqzh()).append(",")
                    .append("权利性质:").append(bdcXmDO.getQlxz() == null ? "" : bdcXmDO.getQlxz()).append(",")
                    .append("存量数据匹配状态:").append(bdcXmDO.getClsjppzt() == null ? "" : bdcXmDO.getClsjppzt()).append(",")
                    .append("承诺期限:").append(bdcXmDO.getCnqx() == null ? "" : bdcXmDO.getCnqx()).append(",")
                    .append("工作流定义名称:").append(bdcXmDO.getGzljdmc() == null ? "" : bdcXmDO.getGzljdmc()).append(",")
                    .append("工作流定义ID:").append(bdcXmDO.getGzldyid() == null ? "" : bdcXmDO.getGzldyid()).append(",")
                    .append("权利人证件种类:").append(bdcXmDO.getQlrzjzl() == null ? "" : bdcXmDO.getQlrzjzl()).append(",")
                    .append("义务人证件种类:").append(bdcXmDO.getYwrzjzl() == null ? "" : bdcXmDO.getYwrzjzl()).append(",")
                    .append("权利人类型:").append(bdcXmDO.getQlrlx() == null ? "" : bdcXmDO.getQlrlx()).append(",")
                    .append("原土地用途名称:").append(bdcXmDO.getYtdytmc() == null ? "" : bdcXmDO.getYtdytmc()).append(",")
                    .append("登记部门代码:").append(bdcXmDO.getDjbmdm() == null ? "" : bdcXmDO.getDjbmdm()).append(",")
                    .append("宗地宗海用途:").append(bdcXmDO.getZdzhyt() == null ? "" : bdcXmDO.getZdzhyt()).append(",")
                    .append("宗地宗海用途2:").append(bdcXmDO.getZdzhyt2() == null ? "" : bdcXmDO.getZdzhyt2()).append(",")
                    .append("宗地宗海用途3:").append(bdcXmDO.getZdzhyt3() == null ? "" : bdcXmDO.getZdzhyt3()).append(",")
                    .append("部分权利其他状况:").append(bdcXmDO.getBfqlqtzk() == null ? "" : bdcXmDO.getBfqlqtzk()).append(",")
                    .append("房产预售房屋编码:").append(bdcXmDO.getYsfwbm() == null ? "" : bdcXmDO.getYsfwbm()).append(",")
                    .append("政府行政审批编号:").append(bdcXmDO.getZfxzspbh() == null ? "" : bdcXmDO.getZfxzspbh()).append(",")
                    .append("原系统产权证号:").append(bdcXmDO.getYxtcqzh() == null ? "" : bdcXmDO.getYxtcqzh()).append(",")
                    .append("审批来源:").append(bdcXmDO.getSply() == null ? "" : bdcXmDO.getSply()).append(",")
                    .append("税务房源编号，缴税时发票提供的房源编号:").append(bdcXmDO.getSwfybh() == null ? "" : bdcXmDO.getSwfybh()).append(",")
                    .append("是否一并申请增量房转移业务:").append(bdcXmDO.getZydjybsq() == null ? "" : bdcXmDO.getZydjybsq());
            map.put("bdcxmxx",sb.toString());
        }

        if(CollectionUtils.isNotEmpty(bdcYwxxDTO.getBdcXmLsgxList())){
            StringBuffer sb = new StringBuffer();
            bdcYwxxDTO.getBdcXmLsgxList().forEach( bdcXmLsgxDO ->{
                if(!StringUtils.isEmpty(sb.toString())){
                    sb.append(";");
                }
                sb.append("主键ID:").append(bdcXmLsgxDO.getGxid() == null ? "" : bdcXmLsgxDO.getGxid()).append(",")
                        .append("项目ID:").append(bdcXmLsgxDO.getXmid() == null ? "" : bdcXmLsgxDO.getXmid()).append(",")
                        .append("原项目ID:").append(bdcXmLsgxDO.getYxmid() == null ? "" : bdcXmLsgxDO.getYxmid()).append(",")
                        .append("是否注销原权利(0:否;1:是):").append(bdcXmLsgxDO.getZxyql() == null ? "" : bdcXmLsgxDO.getZxyql()).append(",")
                        .append("是否外联项目(0:否;1:是):").append(bdcXmLsgxDO.getWlxm() == null ? "" : bdcXmLsgxDO.getWlxm());
            });
            map.put("bdcxmlsgxlist",sb.toString());
        }

        if(CollectionUtils.isNotEmpty(bdcYwxxDTO.getBdcQlrList())){
            StringBuffer sb = new StringBuffer();
            bdcYwxxDTO.getBdcQlrList().forEach( bdcQlrDO ->{
                if(!StringUtils.isEmpty(sb.toString())){
                    sb.append(";");
                }
                sb.append("权利人ID:").append(bdcQlrDO.getQlrid() == null ? "" : bdcQlrDO.getQlrid() ).append(",")
                        .append("项目ID:").append(bdcQlrDO.getXmid() == null ? "" : bdcQlrDO.getXmid()).append(",")
                        .append("权利人名称:").append(bdcQlrDO.getQlrmc() == null ? "" : bdcQlrDO.getQlrmc()).append(",")
                        .append("证件种类:").append(bdcQlrDO.getZjzl() == null ? "" : bdcQlrDO.getZjzl()).append(",")
                        .append("证件号:").append(bdcQlrDO.getZjh() == null ? "" : bdcQlrDO.getZjh()).append(",")
                        .append("通讯地址:").append(bdcQlrDO.getTxdz() == null ? "" : bdcQlrDO.getTxdz()).append(",")
                        .append("邮编:").append(bdcQlrDO.getYb() == null ? "" : bdcQlrDO.getYb()).append(",")
                        .append("性别:").append(bdcQlrDO.getXb() == null ? "" : bdcQlrDO.getXb()).append(",")
                        .append("法人名称:").append(bdcQlrDO.getFrmc() == null ? "" : bdcQlrDO.getFrmc()).append(",")
                        .append("法人电话:").append(bdcQlrDO.getFrdh() == null ? "" : bdcQlrDO.getFrdh()).append(",")
                        .append("法人证件种类:").append(bdcQlrDO.getFrzjzl() == null ? "" : bdcQlrDO.getFrzjzl()).append(",")
                        .append("法人证件号:").append(bdcQlrDO.getFrzjh() == null ? "" : bdcQlrDO.getFrzjh()).append(",")
                        .append("代理人名称:").append(bdcQlrDO.getDlrmc() == null ? "" : bdcQlrDO.getDlrmc()).append(",")
                        .append("代理人电话:").append(bdcQlrDO.getDlrdh() == null ? "" : bdcQlrDO.getDlrdh()).append(",")
                        .append("代理人证件种类:").append(bdcQlrDO.getDlrzjzl() == null ? "" : bdcQlrDO.getDlrzjzl()).append(",")
                        .append("代理人证件号:").append(bdcQlrDO.getDlrzjh() == null ? "" : bdcQlrDO.getDlrzjh()).append(",")
                        .append("代理机构:").append(bdcQlrDO.getDljg() == null ? "" : bdcQlrDO.getDljg()).append(",")
                        .append("权利人类型:").append(bdcQlrDO.getQlrlx() == null ? "" : bdcQlrDO.getQlrlx()).append(",")
                        .append("权利人类别:").append(bdcQlrDO.getQlrlb() == null ? "" : bdcQlrDO.getQlrlb()).append(",")
                        .append("权利比例:").append(bdcQlrDO.getQlbl() == null ? "" : bdcQlrDO.getQlbl()).append(",")
                        .append("共有方式:").append(bdcQlrDO.getGyfs() == null ? "" : bdcQlrDO.getGyfs()).append(",")
                        .append("共有情况:").append(bdcQlrDO.getGyqk() == null ? "" : bdcQlrDO.getGyqk()).append(",")
                        .append("电话:").append(bdcQlrDO.getDh() == null ? "" : bdcQlrDO.getDh()).append(",")
                        .append("所属行业:").append(bdcQlrDO.getSshy() == null ? "" : bdcQlrDO.getSshy()).append(",")
                        .append("备注:").append(bdcQlrDO.getBz() == null ? "" : bdcQlrDO.getBz()).append(",")
                        .append("是否持证人:").append(bdcQlrDO.getSfczr() == null ? "" : bdcQlrDO.getSfczr()).append(",")
                        .append("顺序号:").append(bdcQlrDO.getSxh() == null ? "" : bdcQlrDO.getSxh()).append(",")
                        .append("不动产权证书ID:").append(bdcQlrDO.getZsid() == null ? "" : bdcQlrDO.getZsid()).append(",")
                        .append("权利人分摊面积:").append(bdcQlrDO.getQlrftmj() == null ? "" : bdcQlrDO.getQlrftmj()).append(",")
                        .append("领证人:").append(bdcQlrDO.getLzr() == null ? "" : bdcQlrDO.getLzr()).append(",")
                        .append("领证人电话:").append(bdcQlrDO.getLzrdh() == null ? "" : bdcQlrDO.getLzrdh()).append(",")
                        .append("领证人证件种类:").append(bdcQlrDO.getLzrzjzl() == null ? "" : bdcQlrDO.getLzrzjzl()).append(",")
                        .append("领证人证件号:").append(bdcQlrDO.getLzrzjh() == null ? "" : bdcQlrDO.getLzrzjh()).append(",")
                        .append("存储第三方系统从其他系统查询出来的权利人状态信息:").append(bdcQlrDO.getQlrwbzt() == null ? "" : bdcQlrDO.getQlrwbzt());
            });
            map.put("bdcqlrlist",sb.toString());
        }
        return map;
    }

    @GetMapping("/getCurrentUserName")
    public String getCurrentUserName() {
        return userManagerUtils.getCurrentUserName();
    }

    /**
     * 查询是否不需要是审核
     * @return
     */
    @GetMapping("/bxysh")
    public boolean bxysh() {
        return bdcXxblFeignService.bxysh();
    }

    /**
     * 查询工作流定义ID
     * @return
     */
    @GetMapping("/getGzldyid")
    public String getGzldyid(@RequestParam("xmid") String xmid) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmDOS)){
            return bdcXmDOS.get(0).getGzldyid();
        }
        return "";
    }

    /**
     * 批量补录
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @GetMapping("/plbl")
    public Object plBl(@RequestParam("yxmid") String yxmid, @RequestParam("xmidList") List<String> xmidList) throws Exception {
        if (StringUtils.isBlank(yxmid) || CollectionUtils.isEmpty(xmidList)) {
            throw new MissingArgumentException("缺少参数 yxmid 或 xmidList");
        }
        String xmidListStr = JSON.toJSONString(xmidList);
        List<BdcXmDO> bdcXmDOList = bdcXxblFeignService.plbl(yxmid, xmidListStr);
        return bdcXmDOList;
    }

    /**
     * 选择不动产单元号批量补录
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @PostMapping("/selectBdcdyhPlbl/{yxmid}")
    public Object selectBdcdyhPlbl(@PathVariable("yxmid") String yxmid, @RequestBody List<Map> bdcdyhList) throws Exception {
        if (StringUtils.isBlank(yxmid) || CollectionUtils.isEmpty(bdcdyhList)) {
            throw new MissingArgumentException("缺少参数 yxmid 或 bdcdyhList");
        }
        String bdcdyhListStr = JSON.toJSONString(bdcdyhList);
        List<BdcXmDO> bdcXmDOList = bdcXxblFeignService.selectBdcdyhPlbl(yxmid, bdcdyhListStr);
        return bdcXmDOList;
    }

    private String yzHfQlxx(BdcXmDO bdcXmDO) {
        if (!CommonConstantUtils.QSZT_HISTORY.equals(bdcXmDO.getQszt())) {
            return "仅支持恢复权属状态为历史的数据！";
        }
        if (bdcXmDO.getQllx() != null && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDO.getQllx())) {
            if (StringUtils.isBlank(bdcXmDO.getBdcqzh())) {
                return "项目不动产权证号数据为空！";
            }
            // 根据 bdcdyh 查询是否已经有现实产权
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
            if (CommonConstantUtils.BDCLX_TD_DM.equals(bdcXmDO.getBdclx().toString())) {
                bdcXmQO.setQllxs(Arrays.asList(1, 2, 3, 5, 7));
            } else {
                bdcXmQO.setQllxs(Arrays.asList(4, 6, 8));
            }
            bdcXmQO.setBdcdyh(bdcXmDO.getBdcdyh());
            List<BdcXmDO> results = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(results)) {
                return "不动产单元号：" + bdcXmDO.getBdcdyh() + "已经存在现势产权";
            }
            return "success";
        } else {
            return "仅支持产权的恢复";
        }
    }
    @ResponseBody
    @GetMapping("/queryXgLog")
    public Object queryXgLog(String gzlslid) {
        List<QueryLogCondition> conditions = Lists.newArrayList();
        QueryLogCondition queryLogCondition = new QueryLogCondition();
        queryLogCondition.setType(CommonConstantUtils.TYPE_EQUAL);
        queryLogCondition.setKey(Constants.SJXG_PARAMCH);
        queryLogCondition.setValue(gzlslid);
        conditions.add(queryLogCondition);
        Object obj = null;
        Page<AuditLogDto> auditLogDtoPage = logMessageClient.listLogs(0, 1, Constants.SJXG, userManagerUtils.getCurrentUserName(), null, null, null, conditions);
        LOGGER.warn("当前流程实例id{}查询信息补录修改日志结果{}", gzlslid, JSON.toJSONString(auditLogDtoPage));
        if (auditLogDtoPage.hasContent()) {
            //解析数据
            for (AuditLogDto auditLogDto : auditLogDtoPage) {
                //默认只存在一条
                List<DataValue> dataValueList = auditLogDto.getBinaryAnnotations();
                for (DataValue dataValue : dataValueList) {
                    if (StringUtils.equalsIgnoreCase(dataValue.getKey(), "change")) {
                        obj = dataValue;
                        break;
                    }
                }
            }
        }
        return obj;
    }

    @ResponseBody
    @PostMapping("/addXgLog")
    public void addXgLog(@RequestBody String json, String gzlslid) throws Exception {
        Map<String, String> data = initData(gzlslid, json);
//        AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), Constants.SJXG, data);
        LogMsgDTO logMsgDTO = new LogMsgDTO();
        logMsgDTO.setTags(data);
        logMsgDTO.setEvent(Constants.SJXG);
        logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
        LOGGER.warn("当前流程实例id{}新增修改日志{}", gzlslid, JSON.toJSONString(logMsgDTO));
        logMessageClient.save(logMsgDTO);
//        zipkinAuditEventRepository.add(auditEvent);
    }

    /**
     * @param json    数据修改内容
     * @param gzlslid 工作流
     * @return {Map} 返回日志保存需要的对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化日志data数据
     */
    public static Map<String, String> initData(String gzlslid, String json) throws Exception {
        Map<String, String> data = new HashMap<>(4);
        data.put(Constants.SJXG_PARAMCH, gzlslid);
        data.put(Constants.SJXG_CHANGE, json);
        data.put(CommonConstantUtils.VIEW_TYPE_NAME, "受理页面修改信息");
        data.put("eventName", Constants.SJXG);
        return data;
    }

}
