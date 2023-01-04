package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXzxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXzxxPlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlShxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxlDjyyQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblZsFeignService;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 2021/2/25
 * @description 修正流程
 */
@Controller
@RequestMapping("/xzxx")
public class SlymXzxxController extends BaseController {

    @Autowired
    BdcSlXzxxFeignService bdcSlXzxxFeignService;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcSlShxxFeignService bdcSlShxxFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcYwsjHxFeignService bdcYwsjHxFeignService;

    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    private ProcessInstanceClientMatcher processInstanceClient;

    @Autowired
    BdcSfxxController bdcSfxxController;

    @Autowired
    private BdcXxblZsFeignService bdcXxblZsFeignService;
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;

    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcCshXtPzFeignService bdcCshXtPzFeignService;

    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 配置修正流程的节点名称
     * @date : 2021/11/23 11:01
     */
    @Value("#{'${xzlc.jdmc:档案室核查,审核,土地修正,修正}'.split(',')}")
    private List<String> xzJdmcList;

    /**
     * 根据gzlslid查询流程修正信息
     *
     * @param gzlslid
     * @return 流程修正
     */
    @ResponseBody
    @GetMapping("/getXzxx/{gzlslid}")
    public Object getXqxx(@PathVariable(value = "gzlslid") String gzlslid, String xzxxid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isEmpty(bdcSlXmDOList)) {
                throw new AppException("未查询到受理项目数据,实例id：" + gzlslid);
            }

            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            if (null == bdcSlJbxxDO) {
                throw new AppException("未查询到受理基本信息数据,实例id：" + gzlslid);
            }

            BdcSlXzxxDTO bdcSlXzxxDTO = new BdcSlXzxxDTO();

            bdcSlXzxxDTO.setBdcSlXmDO(bdcSlXmDOList.get(0));
            bdcSlXzxxDTO.setBdcSlJbxxDO(bdcSlJbxxDO);

            BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
            bdcSlXzxxQO.setGzlslid(bdcSlJbxxDO.getGzlslid());
            if (StringUtils.isNotBlank(xzxxid)) {
                bdcSlXzxxQO.setXzxxid(xzxxid);
            }
            BdcSlXzxxDO bdcSlXzxxDO = bdcSlXzxxFeignService.queryBdcSlXzxx(bdcSlXzxxQO);
            bdcSlXzxxDTO.setBdcSlXzxxDO(bdcSlXzxxDO);

            String xmid = bdcSlXzxxDO.getYxmid();
            if (StringUtils.isNotBlank(xmid)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmid);
                List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(list)) {
                    bdcSlXzxxDTO.setBdcXmDO(list.get(0));
                    //查找登记小类，登记原因关系
                    BdcDjxlDjyyQO bdcDjxlDjyyQO = new BdcDjxlDjyyQO();
                    bdcDjxlDjyyQO.setDjxl(list.get(0).getDjxl());
                    bdcDjxlDjyyQO.setGzldyid(list.get(0).getGzldyid());
                    bdcSlXzxxDTO.setBdcDjxlDjyyGxDOList(bdcXmFeignService.listBdcDjxlDjyyGxWithParam(bdcDjxlDjyyQO));
                }
            } else {
                bdcSlXzxxDTO.setBdcXmDO(new BdcXmDO());
            }

            List<BdcSlShxxDO> bdcSlShxxDOList = new ArrayList<>(CollectionUtils.size(xzJdmcList));
            BdcSlShxxQO bdcSlShxxQO = new BdcSlShxxQO();
            bdcSlShxxQO.setXzxxid(xzxxid);
            bdcSlShxxQO.setGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(xzJdmcList)) {
                for (String jdmc : xzJdmcList) {
                    bdcSlShxxQO.setJdmc(jdmc);
                    List<BdcSlShxxDO> listSlShxx = bdcSlShxxFeignService.queryBdcShxx(bdcSlShxxQO);
                    if (CollectionUtils.isNotEmpty(listSlShxx)) {
                        bdcSlShxxDOList.add(listSlShxx.get(0));
                    } else {
                        BdcSlShxxDO bdcSlShxxDO = new BdcSlShxxDO();
                        bdcSlShxxDO.setJdmc(jdmc);
                        bdcSlShxxDOList.add(bdcSlShxxDO);
                    }
                }
            }
            bdcSlXzxxDTO.setBdcSlShxxDOList(bdcSlShxxDOList);

            bdcSlXzxxDTO.setUserDto(userManagerUtils.getCurrentUser());

            bdcSlXzxxDTO.setNowDate(new Date());

            return bdcSlXzxxDTO;

        } else {
            throw new MissingArgumentException("查询流程修正信息缺失gzlslid");
        }
    }

    /**
     * @param json
     * @return 新的实体bdcSlXzxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新(插入)修正流程
     */
    @ResponseBody
    @PatchMapping("/saveBdcSlxzxx")
    public Object updateBdcSlxqxx(@RequestBody String json) throws Exception {
        BdcSlXzxxDO bdcSlXzxxDO = JSONObject.parseObject(json, BdcSlXzxxDO.class);
        bdcSlXzxxDO = bdcSlXzxxFeignService.saveBdcSlXzxx(bdcSlXzxxDO);
        if (StringUtils.isNotBlank(bdcSlXzxxDO.getXzxxid())) {
            BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
            bdcSlXzxxQO.setXzxxid(bdcSlXzxxDO.getXzxxid());
            bdcSlXzxxDO = bdcSlXzxxFeignService.queryBdcSlXzxx(bdcSlXzxxQO);
            Map<String, Object> processInsExtendDto = new HashMap<>();
            // 不需要判空 因为用户可能会清空 qlr和zl 空的也要回写
            processInsExtendDto.put("QLR", bdcSlXzxxDO.getQlr());
            processInsExtendDto.put("ZL", bdcSlXzxxDO.getZl());
            bdcYwsjHxFeignService.updateBdcYwsj(bdcSlXzxxDO.getGzlslid(), processInsExtendDto);
        }
        return bdcSlXzxxDO;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 存量数据上传大云先新增一个补充材料文件夹
     * @date : 2021/7/22 14:22
     */
    @ResponseBody
    @GetMapping("/xgsjcl")
    public void xgSjcl(String gzlslid) {
        //先判断需要修正的流程在大云是否有数据，没有说明是存量数据，新建文件夹后上传
        if (StringUtils.isNotBlank(gzlslid)) {
            ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
            List<StorageDto> storageDtoList = storageClient.listAllRootStorages(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "", "", 1, null, null, null);
            if (Objects.isNull(processInstanceData) || CollectionUtils.size(storageDtoList) == 0) {
                storageClient.createRootFolder(CommonConstantUtils.WJZX_CLIENTID, gzlslid, "补充材料", "");
            }
        }
    }

    /**
     * @param gzlslid,xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 修正流程保存后数据处理服务
     * @date : 2021/9/27 15:52
     */
    @ResponseBody
    @GetMapping("/dataservice")
    public void handleData(String gzlslid, String xmid, String xzlcymlx) throws Exception {
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(xmid)) {
            //重新计算收费项目
            try {
                bdcSfxxController.reCountSfxm(gzlslid, xmid);
            } catch (Exception e) {
                LOGGER.error("重新计算异常{}", e);
            }

            //更新证书信息
//            updateZsxx(gzlslid, xmid);
            int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            BdcXmQO bdcXmQO = new BdcXmQO(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                //在现势页面修改数据且下一手是抵押的//或者当前是临时的且是组合流程的修改产权的信息
                if (StringUtils.isNotBlank(xzlcymlx) && StringUtils.equals("xs", xzlcymlx) ||
                        ((CommonConstantUtils.LCLX_ZH.equals(lclx) || CommonConstantUtils.LCLX_PLZH.equals(lclx)) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcXmDOList.get(0).getQllx()))) {
                    updateZlxx(bdcXmDOList.get(0));
                    updateQxdm(xmid,bdcXmDOList);
                }
            }

        }
    }

    /**
     * 同步区县代码等信息
     * @param xmid
     * @param bdcXmDOList
     */
    public void updateQxdm(String xmid,List<BdcXmDO> bdcXmDOList){
        //通过xmid查询修正表，通过修正表中gzlslid得到yxmid查询项目表
        BdcXmQO bdcXmQO = new BdcXmQO();
        BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
        bdcSlXzxxQO.setYxmid(xmid);
        List<BdcSlXzxxDO> bdcSlXzxxList = bdcSlXzxxFeignService.listBdcSlXzxx(bdcSlXzxxQO);
        String bdcslGzlslid = "";
        if (CollectionUtils.isNotEmpty(bdcSlXzxxList) && StringUtils.isNotBlank(bdcSlXzxxList.get(0).getGzlslid())){
            bdcslGzlslid = bdcSlXzxxList.get(0).getGzlslid();
        }
        bdcSlXzxxQO.setGzlslid(bdcslGzlslid);
        bdcSlXzxxQO.setYxmid(null);
        List<BdcSlXzxxDO> list =  bdcSlXzxxFeignService.listBdcSlXzxx(bdcSlXzxxQO);
        for (BdcSlXzxxDO bdcSlXzxxDO : list){
            if (xmid.equals(bdcSlXzxxDO.getYxmid())){
                continue;
            }
            bdcXmQO.setXmid(bdcSlXzxxDO.getYxmid());
            List<BdcXmDO> bdcxmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcxmList)){
                BdcXmDO bdcXmDO = new BdcXmDO();
                bdcXmDO.setQxdm(bdcXmDOList.get(0).getQxdm());
                bdcXmDO.setXmid(bdcSlXzxxDO.getYxmid());
                bdcXmDO.setDzwyt(bdcXmDOList.get(0).getDzwyt());
                bdcXmFeignService.updateBdcXm(bdcXmDO);
            }

        }
    }

    /**
     * @param gzlslid,xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/2/25 15:23
     */
    @ResponseBody
    @GetMapping("/dataservice/zs")
    public void updateXzlcZsxx(String gzlslid, String xmid) throws Exception {
        if (StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(xmid)) {
            //更新证书信息
            updateZsxx(gzlslid, xmid);
        }
    }

    public void updateZsxx(String gzlslid, String xmid) throws Exception {
        String bdcqzh = "";
        String zsid = "";
        String slbh = "";
        // 查询原不动产权证号
        List<BdcZsDO> bdcZsDOList = bdcZsFeignService.queryBdcZsByXmid(xmid);
        if (CollectionUtils.isNotEmpty(bdcZsDOList) && StringUtils.isNotBlank(bdcZsDOList.get(0).getBdcqzh())) {
            BdcZsDO bdcZsDO = bdcZsDOList.get(0);
            LOGGER.info("原证书信息{}", JSON.toJSONString(bdcZsDO));
            bdcqzh = bdcZsDO.getBdcqzh();
            zsid = bdcZsDO.getZsid();
        }
        BdcXmQO bdcXmQO = new BdcXmQO(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            slbh = bdcXmDOList.get(0).getSlbh();
        }
        // 重新生成证书前先查询证书锁定信息
        List<BdcZssdDO> bdcZssdDOS = bdcXxblZsFeignService.listZssdByXmid(xmid, CommonConstantUtils.SDZT_SD);
        LOGGER.warn("修正原项目:{} 的证书，证号为：{}", xmid, bdcqzh);
        // 1. 初始化证书
        if (StringUtils.isNotBlank(bdcqzh)) {
            //证书预览逻辑，不生成新的证书信息
            List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.initBdcqzsByXz(gzlslid, xmid, true);
            LOGGER.warn("修正生成证书信息为：{}", bdcZsDOS);
            /**
             * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
             * @description 逻辑调整，常州数据很垃圾，证书表存在相同证号，无法处理数据，不再判断证号是否被使用
             * @date : 2021/11/17 21:16
             */
            //更新证书信息--不会存在原来是一本证现在修正后生成两本证的
            BdcZsDO newBdcZs = bdcZsDOS.get(0);
            BdcZsDO oldBdcZs = bdcZsDOList.get(0);
            BdcZsDO yBdcZs = bdcZsDOList.get(0);
            BeansResolveUtils.clonePropertiesValue(newBdcZs, oldBdcZs);
            oldBdcZs.setZsid(zsid);
            oldBdcZs.setEwmnr(zsid);
            bdcZsFeignService.updateBdcZs(oldBdcZs);
            bdcZsInitFeignService.updateQlqtzkFj(xmid, "1");
            //记录日志到大云
            Map map = new HashMap(5);
            map.put(CommonConstantUtils.SLBH, slbh);
            map.put(CommonConstantUtils.VIEW_TYPE, Constants.ACCEPT_UI);
            map.put(CommonConstantUtils.VIEW_TYPE_NAME, Constants.ZSXZ);
            map.put(CommonConstantUtils.ALIAS, userManagerUtils.getCurrentUserName());
            map.put(Constants.SJXG_CHANGE, "原证书信息" + JSON.toJSONString(yBdcZs) + "新证书信息" + JSON.toJSONString(oldBdcZs));
            map.put("eventName", "UPDATE");
            AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), "UPDATE", map);
            zipkinAuditEventRepository.add(auditEvent);
        }
        if (CollectionUtils.isNotEmpty(bdcZssdDOS)) {
            // 更新锁定状态
            bdcXxblZsFeignService.updateZssd(xmid, bdcZssdDOS);
        }
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询修正信息数据
     * @date : 2021/9/28 10:12
     */
    @ResponseBody
    @GetMapping("/page")
    public Object listXzxxByPage(@LayuiPageable Pageable pageable, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return addLayUiCode(new PageImpl<>(Collections.emptyList(), pageable, 0));
        }
        return addLayUiCode(bdcSlXzxxFeignService.listBdcSlxzxxByPage(pageable, gzlslid));
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询修正信息数据
     * @date : 2021/9/28 10:12
     */
    @ResponseBody
    @GetMapping("/page/pl")
    public Object listXzxxByPagePl(@LayuiPageable Pageable pageable, String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            return addLayUiCode(new PageImpl<>(Collections.emptyList(), pageable, 0));
        }
        Page<BdcSlXzxxPlDTO> bdcSlXzxxDTOPage = bdcSlXzxxFeignService.listBdcSlxzxxByPagePl(pageable, gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXzxxDTOPage.getContent())) {
            for (BdcSlXzxxPlDTO bdcSlXzxxPlDTO : bdcSlXzxxDTOPage.getContent()) {
                //查坐落，权利人
                BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
                bdcSlXzxxQO.setGzlslid(bdcSlXzxxPlDTO.getGzlslid());
                List<BdcSlXzxxDO> bdcSlXzxxDOList = bdcSlXzxxFeignService.listBdcSlXzxx(bdcSlXzxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlXzxxDOList)) {
                    //根据yxmid排序
                    bdcSlXzxxDOList.sort(Comparator.comparing(BdcSlXzxxDO::getYxmid));
//                    bdcSlXzxxPlDTO.setZl();
                    bdcSlXzxxPlDTO.setQlr(bdcSlXzxxDOList.get(0).getQlr());
                    bdcSlXzxxPlDTO.setJdmc(bdcSlXzxxDOList.get(0).getYlcjdmc());
                    if (CollectionUtils.size(bdcSlXzxxDOList) > 1) {
                        bdcSlXzxxPlDTO.setZl(bdcSlXzxxDOList.get(0).getZl() + CommonConstantUtils.SUFFIX_PL);
                    } else {
                        bdcSlXzxxPlDTO.setZl(bdcSlXzxxDOList.get(0).getZl());
                    }
                    //查流程名称，登记原因
                    BdcXmQO bdcXmQO = new BdcXmQO(bdcSlXzxxDOList.get(0).getYxmid());
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        bdcSlXzxxPlDTO.setLcmc(bdcXmDOList.get(0).getGzldymc());
                        bdcSlXzxxPlDTO.setDjyy(bdcXmDOList.get(0).getDjyy());
                    }
                }
            }
        }
        return addLayUiCode(bdcSlXzxxDTOPage);
    }


    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存修正审核意见信息
     * @date : 2021/11/23 14:30
     */
    @ResponseBody
    @PatchMapping("/xzshxx")
    public Object saveXzShxx(@RequestBody String json) {
        if (StringUtils.isNotBlank(json)) {
            //多条数据转jsaonArray
            JSONArray jsonArray = JSON.parseArray(json);
            UserDto userDto = userManagerUtils.getUserDto();
            for (Object object : jsonArray) {
                BdcSlShxxDO bdcSlShxxDO = JSON.parseObject(object.toString(), BdcSlShxxDO.class);
                bdcSlShxxDO.setShkssj(new Date());
                bdcSlShxxDO.setShryxm(userDto.getAlias());
                bdcSlShxxDO.setCzjg(1);
                bdcSlShxxDO.setShryid(userDto.getId());
                bdcSlShxxFeignService.saveOrUpdateBdcShxx(bdcSlShxxDO);
            }
        }
        return null;
    }


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 判断当前流程是否办理过修正，办理过返回修正的工作流实例id
     * @date : 2021/12/14 13:55
     */
    @ResponseBody
    @GetMapping("/xzlc")
    public Object queryXzlc(String gzlslid) {
        //当前的工作流实例id，查询项目信息，判断是否办理过修正
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                String xmid = bdcXmDTOList.get(0).getXmid();
                BdcSlXzxxQO bdcSlXzxxQO = new BdcSlXzxxQO();
                bdcSlXzxxQO.setYxmid(xmid);
                List<BdcSlXzxxDO> bdcSlXzxxDOList = bdcSlXzxxFeignService.listBdcSlXzxx(bdcSlXzxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlXzxxDOList)) {
                    return bdcSlXzxxDOList;
                }
            }
        }
        return null;
    }

    @ResponseBody
    @GetMapping("/gxzs")
    public void updateBdcqzh(String zsid, String bdcqzh, String xmid) {
        if (StringUtils.isNotBlank(zsid) && StringUtils.isNotBlank(bdcqzh) && StringUtils.isNotBlank(xmid)) {
            BdcZsDO bdcZsDO = bdcZsFeignService.queryBdcZs(zsid);
            if (Objects.nonNull(bdcZsDO)) {
                bdcZsDO.setBdcqzh(bdcqzh);
                bdcZsFeignService.updateBdcZs(bdcZsDO);
                //更新冗余字段
                Map<String, List<BdcBdcqzhDTO>> bdcqzhMap = Maps.newHashMap();
                List<BdcBdcqzhDTO> bdcBdcqzhDTOS = Lists.newArrayList();
                BdcBdcqzhDTO bdcBdcqzhDTO = new BdcBdcqzhDTO();
                bdcBdcqzhDTO.setBdcqzh(bdcZsDO.getBdcqzh());
                bdcBdcqzhDTO.setXmid(xmid);
                bdcBdcqzhDTO.setZsid(bdcZsDO.getZsid());
                bdcBdcqzhDTOS.add(bdcBdcqzhDTO);
                bdcqzhMap.put(xmid, bdcBdcqzhDTOS);
                bdcRyzdFeignService.updateRyzdBdcqzh(bdcqzhMap);
            }
        }
    }

    public void updateZlxx(BdcXmDO bdcXmDO) throws Exception {
        //现势的页面更新坐落信息
        if (Objects.nonNull(bdcXmDO)) {
            String zl = bdcXmDO.getZl();
            //查询当前现势数据的下一手数据
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setYxmid(bdcXmDO.getXmid());
            bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isNotEmpty(bdcXmLsgxDOList)) {
                BdcXmQO bdcXmQO = new BdcXmQO(bdcXmLsgxDOList.get(0).getXmid());
                List<BdcXmDO> lsBdcXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(lsBdcXmList) && CommonConstantUtils.QLLX_DYAQ_DM.equals(lsBdcXmList.get(0).getQllx())) {
                    LOGGER.warn("当前修改数据xmid={}，存在下一手为抵押项目xmid={}，修改抵押的项目信息和更新抵押的证明信息", bdcXmDO.getXmid(), lsBdcXmList.get(0).getXmid());
                    //如果下一手临时的数据是抵押权的，更新抵押权的项目数据和证书信息
                    BdcXmDO lsBdcXm = lsBdcXmList.get(0);
                    lsBdcXm.setZl(zl);
                    bdcXmFeignService.updateBdcXm(lsBdcXm);

                    BdcCshFwkgSlDO cshFwkgSlDO = new BdcCshFwkgSlDO();
                    BdcCshFwkgDO bdcCshFwkgDO = new BdcCshFwkgDO();

                    //查询是否生成了初始化实例开关数据
                    if (StringUtils.isNotBlank(lsBdcXm.getXmid())){
                        cshFwkgSlDO = bdcXmFeignService.queryFwkgsl(lsBdcXm.getXmid());
                    }
                    if (StringUtils.isNotBlank(lsBdcXm.getDjxl())){
                        bdcCshFwkgDO = bdcCshXtPzFeignService.queryBdcCshFwKgDO(lsBdcXm.getDjxl());
                        LOGGER.info("初始化实例开关数据为:{}", bdcCshFwkgDO);
                    }else {
                        //若登记小类为空，则赋默认登记小类
                        bdcCshFwkgDO = bdcCshXtPzFeignService.queryBdcCshFwKgDO("9990401");
                        LOGGER.info("初始化实例开关数据为:{}", bdcCshFwkgDO);
                    }

                    //如果当前项目没有生成初始化服务开关数据，新增一个
                    if (Objects.isNull(cshFwkgSlDO) && StringUtils.isNotBlank(lsBdcXm.getXmid())){
                        cshFwkgSlDO = new BdcCshFwkgSlDO();
                        cshFwkgSlDO.setYwrsjly(bdcCshFwkgDO.getYwrsjly());
                        cshFwkgSlDO.setSfsczs(bdcCshFwkgDO.getSfsczs());
                        cshFwkgSlDO.setDjxl(bdcCshFwkgDO.getDjxl());
                        cshFwkgSlDO.setSfzxyql(bdcCshFwkgDO.getSfzxyql());
                        cshFwkgSlDO.setQlsjly(bdcCshFwkgDO.getQlsjly());
                        cshFwkgSlDO.setSfscql(bdcCshFwkgDO.getSfscql());
                        cshFwkgSlDO.setZszl(bdcCshFwkgDO.getZszl());
                        cshFwkgSlDO.setId(lsBdcXm.getXmid());
                        bdcXmFeignService.insertBdcCshFwkgSl(cshFwkgSlDO);
                    }
                    updateZsxx(lsBdcXm.getGzlslid(), lsBdcXm.getXmid());
                }
            }
        }
    }


}
