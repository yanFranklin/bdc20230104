package cn.gtmap.realestate.register.ui.web.rest.xxbl;


import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.LogMsgDTO;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.certificate.BdcBdcqzhDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCshXtPzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcZsInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblShFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcInitRestService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.LogCompareUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.register.ui.core.enums.BdcBlZslxEnum;
import cn.gtmap.realestate.register.ui.util.Constants;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 不动产信息补录证书服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2020/01/08 18:59
 */
@RestController
@RequestMapping("/rest/v1.0/blxx/zs")
public class BdcBlzsController extends BaseController {

    private static final String QLLX = "qllx";
    private static final String GYFS = "gyfs";
    private static final String ZSLX = "zslx";
    @Autowired
    private BdcXxblZsFeignService bdcXxblZsFeignService;
    @Autowired
    private BdcZsInitFeignService bdcZsInitFeignService;
    @Autowired
    private BdcZsFeignService bdcZsFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcXxblShFeignService bdcXxblShFeignService;

    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;

    @Autowired
    BdcInitRestService bdcInitFeignService;

    @Autowired
    LogMessageClient logMessageClient;
    @Value("#{'${xxbl.modify.gzldyid:}'.split(',')}")
    private List<String> xxblXgGzldyidList;

    @Autowired
    BdcCshXtPzFeignService bdcCshXtPzFeignService;
    /**
     * 生成证书
     *
     * @param gzlslid 工作流实例 ID
     * @param xmid    项目 ID
     * @param bdcqzh  不动产
     * @return {Object} 如果初始化成功返回证书或证明的集合，否则返回一个 null
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/initZs")
    public Object initZs(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "xmid") String xmid, @RequestParam(value = "bdcqzh") String bdcqzh) throws Exception {
        if (StringUtils.isAnyBlank(gzlslid, xmid)) {
            throw new MissingArgumentException("必要参数缺失！");
        }
        // 如果传入的 bdcqzh 为空
        if (StringUtils.isBlank(bdcqzh)) {
            // 查询原不动产权证号
            List<BdcZsDO> bdcZsDOS = bdcZsFeignService.queryBdcZsByXmid(xmid);
            if (CollectionUtils.isNotEmpty(bdcZsDOS) && StringUtils.isNotBlank(bdcZsDOS.get(0).getBdcqzh())) {
                // 已经录入了证号，重新覆盖
                bdcqzh = bdcZsDOS.get(0).getBdcqzh();
            }
        }else{
            int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
            if(sfscdbz(gzlslid) && CommonConstantUtils.LCLX_PL.equals(lclx)){
                bdcqzh = "";
            }
        }
        // 重新生成证书前先查询证书锁定信息
        List<BdcZssdDO> bdcZssdDOS = bdcXxblZsFeignService.listZssdByXmid(xmid, CommonConstantUtils.SDZT_SD);
        LOGGER.warn("当前xmid{}生成证书时获取到的证书锁定记录{}", xmid, JSON.toJSONString(bdcZssdDOS));
        LOGGER.warn("补录生成项目:{} 的证书，生成证号为：{}", xmid, bdcqzh);
        // 1. 初始化证书
        List<BdcZsDO> bdcZsDOS = bdcZsInitFeignService.initBdcqzsSjbl(gzlslid, false);
        LOGGER.warn("补录生成证书信息为：{}", bdcZsDOS);

        // 2. 如果在选择不动产单元时就填写了产权证就在生成证书后自动填写上证号
        if (StringUtils.isNotBlank(bdcqzh)) {
            // 判断证号是否重复
            // 判断是否是补录修改数据，如果是修改数据允许证号使用之前的，把原证号拼接上(补)字段
            boolean xxblzs = false;
            if (CollectionUtils.isNotEmpty(xxblXgGzldyidList)) {
                BdcXmQO bdcXmQO = new BdcXmQO(xmid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && xxblXgGzldyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                    xxblzs = true;
                }
            }
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setBdcqzh(bdcqzh);
            List<BdcZsDO> zhzs = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(zhzs)) {
                List<String> zsids = zhzs.stream().map(BdcZsDO::getZsid).collect(Collectors.toList());
                boolean repeat = false;
                for (BdcZsDO bdcZsDO : bdcZsDOS) {
                    if (!xxblzs) {
                        if (!zsids.contains(bdcZsDO.getZsid())) {
                            repeat = true;
                            break;
                        }
                    }

                }
                if (repeat) {
                    throw new AppException("当前证号已经使用！");
                }
                if (xxblzs) {
                    BdcZsDO bdcZsDO = zhzs.get(0);
                    String slbh = "";
                    List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                    if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                        slbh = bdcXmDTOList.get(0).getSlbh();
                    }
                    bdcZsDO.setBdcqzh(bdcZsDO.getBdcqzh() + "(补_" + slbh + ")");
                    bdcZsFeignService.updateBdcZs(bdcZsDO);
                    List<BdcXmDO> yXmList = bdcZsFeignService.queryZsXmByZsid(bdcZsDO.getZsid());
                    if (CollectionUtils.isNotEmpty(yXmList)) {
                        Map<String, List<BdcBdcqzhDTO>> bdcqzhMap = Maps.newHashMap();
                        List<BdcBdcqzhDTO> bdcBdcqzhDTOS = Lists.newArrayList();
                        BdcBdcqzhDTO bdcBdcqzhDTO = new BdcBdcqzhDTO();
                        bdcBdcqzhDTO.setBdcqzh(bdcZsDO.getBdcqzh());
                        bdcBdcqzhDTO.setXmid(yXmList.get(0).getXmid());
                        bdcBdcqzhDTO.setZsid(bdcZsDO.getZsid());
                        bdcBdcqzhDTOS.add(bdcBdcqzhDTO);
                        bdcqzhMap.put(yXmList.get(0).getXmid(), bdcBdcqzhDTOS);
                        bdcRyzdFeignService.updateRyzdBdcqzh(bdcqzhMap);
                    }

                }
            }
            bdcXxblZsFeignService.updateBlBdcqzh(xmid, bdcqzh);
        }

        if (CollectionUtils.isNotEmpty(bdcZssdDOS)) {
            // 更新锁定状态
            bdcXxblZsFeignService.updateZssd(xmid, bdcZssdDOS);
        }
        return judgeZslx(bdcZsDOS);
    }

    /**
     * 保存证书 权利其他状况、附记以及不动产权证号
     *
     * @param zsid   证书 id
     * @param xmid   项目 id
     * @param qlqtzk 权利其他状况
     * @param bdcqzh 不动产权证号
     * @param fj     附记
     */
    @GetMapping("/zsxx")
    public void updateBfZsxx(@RequestParam(value = "zsid") String zsid,
                           @RequestParam(value = "xmid") String xmid, @RequestParam(value = "qlqtzk") String qlqtzk,
                           @RequestParam(value = "bdcqzh") String bdcqzh, @RequestParam(value = "fj") String fj) throws Exception {
        if (StringUtils.isAnyBlank(zsid, xmid)) {
            throw new MissingArgumentException("xmid,zsid");
        }
        // 如果信息都为空则不更新直接返回
        if (StringUtils.isBlank(qlqtzk) && StringUtils.isBlank(fj) && StringUtils.isBlank(bdcqzh)) {
            return;
        }
        BdcYwxxDTO bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        // 判断证号是否在证书表中已经重复
        if (StringUtils.isNotBlank(bdcqzh)) {
            String bdcdyh = "";
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setBdcqzh(bdcqzh);
            List<BdcZsDO> zhzs = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(zhzs)) {
                //判断是否信息补录修改流程
                boolean xxblzs = false;
                String slbh = "";
                if (CollectionUtils.isNotEmpty(xxblXgGzldyidList)) {
                    BdcXmQO bdcXmQO = new BdcXmQO(xmid);
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(bdcXmDOList) && xxblXgGzldyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                        bdcdyh = bdcXmDOList.get(0).getBdcdyh();
                        slbh = bdcXmDOList.get(0).getSlbh();
                        xxblzs = true;
                    }
                }
                //判断当前填写的证号是否被使用，如果被使用判断不动产单元号和当前信息补录修改的单元号是否一致，一致的在原证号后面加上(补)，否则给出异常提示
                if (!zhzs.get(0).getZsid().equals(zsid)) {
                    if (xxblzs) {
                        //根据证号对应的zsid 查项目数据，看单元号和当前补录数据单元号是否一致
                        List<BdcXmDO> bdcXmDOList = bdcZsFeignService.queryZsXmByZsid(zhzs.get(0).getZsid());
                        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                            if (StringUtils.equals(bdcXmDOList.get(0).getBdcdyh(), bdcdyh)) {
                                //更新之前的证号
                                BdcZsDO bdcZsDO = zhzs.get(0);
                                bdcZsDO.setBdcqzh(bdcZsDO.getBdcqzh() + "(补_" + slbh + ")");
                                bdcZsFeignService.updateBdcZs(bdcZsDO);
                                //更新冗余字段
                                Map<String, List<BdcBdcqzhDTO>> bdcqzhMap = Maps.newHashMap();
                                List<BdcBdcqzhDTO> bdcBdcqzhDTOS = Lists.newArrayList();
                                BdcBdcqzhDTO bdcBdcqzhDTO = new BdcBdcqzhDTO();
                                bdcBdcqzhDTO.setBdcqzh(bdcZsDO.getBdcqzh());
                                bdcBdcqzhDTO.setXmid(bdcXmDOList.get(0).getXmid());
                                bdcBdcqzhDTO.setZsid(bdcZsDO.getZsid());
                                bdcBdcqzhDTOS.add(bdcBdcqzhDTO);
                                bdcqzhMap.put(bdcXmDOList.get(0).getXmid(), bdcBdcqzhDTOS);
                                bdcRyzdFeignService.updateRyzdBdcqzh(bdcqzhMap);
                            } else {
                                throw new AppException("当前修改的证号" + bdcqzh + "已经存在单元号数据" + bdcXmDOList.get(0).getBdcdyh() + "和当前修改的单元号不符");
                            }
                        } else {
                            LOGGER.error("当前修改的产权证号{}未找到对应的项目信息", bdcqzh);
                        }
                    } else {
                        throw new AppException("当前证号已经使用！");
                    }
                }
            }
        }
        bdcXxblZsFeignService.updateBfZsxx(zsid, xmid, qlqtzk, bdcqzh, fj);
        BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(xmid);
        Map<String, String> data = LogCompareUtils.initDataString(xmid, bdcYwxxDTOBefore, bdcYwxxDTOAfter);
        LOGGER.warn("当前项目id{}修改前业务信息{}修改后业务信息{}", xmid, JSON.toJSONString(bdcYwxxDTOBefore), JSON.toJSONString(bdcYwxxDTOAfter));
        if (StringUtils.isNotBlank(RSAEncryptUtils.decrypt(data.get("change").toString()))) {
            LogMsgDTO logMsgDTO = new LogMsgDTO();
            logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
            logMsgDTO.setTags(data);
            logMsgDTO.setEvent(CommonConstantUtils.XXBL);
            LOGGER.warn("xmid={}信息补录修改证书信息开始更新记录日志", xmid);
            logMessageClient.save(logMsgDTO);
        }
    }


    /**
     * 关联证书
     * @param xmid
     * @param bdcqzh
     */
    @GetMapping("/zsgl")
    public void updateZsGlxx(@RequestParam(value = "xmid") String xmid,
                             @RequestParam(value = "bdcqzh") String bdcqzh,
                             @RequestParam(value = "zsid") String zsid) {
        if (StringUtils.isAnyBlank(bdcqzh, xmid)) {
            throw new MissingArgumentException("bdcqzh,xmid");
        }
        // 判断项目和证书是否已经关联
        if (StringUtils.isNotBlank(bdcqzh)) {
            List<BdcZsDO> zhzs = bdcZsFeignService.queryBdcZsByXmid(xmid);
            if (CollectionUtils.isNotEmpty(zhzs)) {
                throw new AppException("已关联其他证书！");
            }
        }

        List<BdcXmDO> bdcXmDOS = bdcXmFeignService.queryXmByZsBdcqzh(bdcqzh);
        //关联证书的glzslid
        String gzlslid = null;
        if(CollectionUtils.isNotEmpty(bdcXmDOS)){
            gzlslid = bdcXmDOS.get(0).getGzlslid();
        }

        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(xmid);
        bdcZsQO.setBdcqzh(bdcqzh);
        bdcZsQO.setZsid(zsid);
        bdcXxblZsFeignService.addXmZsConnection(bdcZsQO);
        //更新项目表信息，更新gzlslid，更新bdcqzh
        List<BdcZsDO> zhzs = bdcZsFeignService.queryBdcZsByXmid(xmid);
        if (CollectionUtils.isEmpty(zhzs)) {
            throw new AppException("关联证书失败！");
        }
        //关联证书的ID
        String glzsId = zhzs.get(0).getZsid();
        //关联证书的bdcqzh
        String glzsBdcqzh = zhzs.get(0).getBdcqzh();
        //更新权利人表的zsid和bdcqzh
        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setXmid(xmid);
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                bdcQlrDO.setZsid(glzsId);
                bdcQlrDO.setBdcqzh(glzsBdcqzh);
                bdcQlrFeignService.updateBdcQlr(bdcQlrDO);
            }
        }
        //更新项目表的bdcqzh
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setXmid(xmid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            bdcXmDO.setBdcqzh(bdcqzh);
            bdcXmDO.setGzlslid(gzlslid);
            bdcXmFeignService.updateBdcXm(bdcXmDO);
        }
        //更新信息补录审核信息工作流实例id
        BdcBlShDO bdcBlShDO = new BdcBlShDO();
        bdcBlShDO.setXmid(xmid);
        List<BdcBlShDO> bdcBlShDOS = bdcXxblShFeignService.queryBlshxxByXmid(xmid);
        if(CollectionUtils.isNotEmpty(bdcBlShDOS)){
            for (BdcBlShDO blShDO : bdcBlShDOS) {
                blShDO.setGzlslid(gzlslid);
                bdcXxblShFeignService.updateBlshxx(blShDO);
            }
        }

    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [zsid, xmid, bdcqzh]
     * @return void
     * @description 更新不动产权证号
     */
    @GetMapping("/zsxx/bdcqzh")
    public void updateBdcqzh(@RequestParam(value = "zsid") String zsid, @RequestParam(value = "xmid") String xmid,
                             @RequestParam(value = "bdcqzh") String bdcqzh) {
        if (StringUtils.isAnyBlank(zsid, xmid)) {
            throw new MissingArgumentException("xmid,zsid");
        }
        // 如果信息都为空则不更新直接返回
        if (StringUtils.isBlank(bdcqzh)) {
            return;
        }
        // 判断证号是否在证书表中已经重复
        if (StringUtils.isNotBlank(bdcqzh)) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setBdcqzh(bdcqzh);
            List<BdcZsDO> zhzs = bdcZsFeignService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(zhzs)) {
                if (!zhzs.get(0).getZsid().equals(zsid)) {
                    throw new AppException("当前证号已经使用！");
                }
            }
        }
        bdcXxblZsFeignService.updateBdcqzh(zsid, xmid, bdcqzh);
    }

    /**
     * 根据 gzlslid 查询证书类型
     *
     * @param gzlslid 工作流实例 id
     * @return {String} BdcBlZslxEnum 的 value
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/zslx")
    public String queryZslx(@RequestParam("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("工作流定义 id 缺失");
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        return judgeZslx(bdcZsDOS);
    }
    /**
     * 获取证书信息
     *
     * @param xmid 项目 id
     * @return bdczs 证书对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping
    public Object queryBdcZs(@RequestParam("xmid") String xmid, @RequestParam("zsid") String zsid, @RequestParam(value = "bdcqzh",required = false) String bdcqzh) {
        BdcZsQO bdcZsQO = new BdcZsQO();
        if (StringUtils.isNotBlank(zsid)) {
            bdcZsQO.setZsid(zsid);
        }
        bdcZsQO.setXmid(xmid);
        //关联证书的情况下可能会有bdcqzh
        if(StringUtils.isNotBlank(bdcqzh)){
            bdcZsQO.setBdcqzh(bdcqzh);
        }
        List<BdcZsDO> bdcZsDOS = bdcZsFeignService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            return null;
        }
        BdcZsVO bdcZsVO = new BdcZsVO();
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        BeanUtils.copyProperties(bdcZsDOS.get(0), bdcZsVO);
        bdcZsVO.setSzrq(bdcZsDOS.get(0).getSzsj());
        bdcZsVO.setSzrqDay(bdcZsDOS.get(0).getSzsj());
        bdcZsVO.setSzrqMonth(bdcZsDOS.get(0).getSzsj());
        bdcZsVO.setSzrqYear(bdcZsDOS.get(0).getSzsj());
        bdcZsVO.setZslxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVO.getZslx(), zdMap.get(ZSLX)));
        bdcZsVO.setQllxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVO.getQllx(), zdMap.get(QLLX)));
        bdcZsVO.setGyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVO.getGyfs(), zdMap.get(GYFS)));
        return bdcZsVO;
    }

    /**
     *
     * 获取证书信息
     *
     * @param xmid 项目 id
     * @return bdczs 证书对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/list")
    public Object queryBdcZsList(@LayuiPageable Pageable pageable, @RequestParam(value = "xmid",required = false) String xmid, @RequestParam(value = "gzlslid",required = false) String gzlslid) {
        if(StringUtils.isBlank(xmid) && StringUtils.isBlank(gzlslid) ){
            throw new MissingArgumentException("未获取查询参数。");
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setXmid(xmid);
        bdcZsQO.setGzlslid(gzlslid);
        Sort sort = "UNSORTED".equals(String.valueOf(pageable.getSort())) ? null : pageable.getSort();
        Page<BdcZsVO> bdcZsVOPage = bdcXxblZsFeignService.bdcZsByPageJson(pageable.getPageNumber(), pageable.getPageSize(), sort, bdcZsQO);
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        for (int i = 0; i < bdcZsVOPage.getContent().size(); i++) {
            // 转换证书类型字典项
            bdcZsVOPage.getContent().get(i).setZslxmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVOPage.getContent().get(i).getZslx(), zdMap.get(Constants.ZSLX)));
            bdcZsVOPage.getContent().get(i).setGyfsmc(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsVOPage.getContent().get(i).getGyfs(), zdMap.get(Constants.GYFS)));
        }
        return addLayUiCode(bdcZsVOPage);
    }


    /**
     * 判断补录的证书类型
     * @param bdcZsDOS 证书集合
     * @return BdcBlZslxEnum 枚举类的 value
     */
    private String judgeZslx(List<BdcZsDO> bdcZsDOS){
        if (CollectionUtils.isEmpty(bdcZsDOS)) {
            return BdcBlZslxEnum.NO.getValue();
        } else if (bdcZsDOS.size() > 1) {
            // 补录不支持批量，只需要考虑分别持证
            return BdcBlZslxEnum.FBCZ.getValue();
        } else {
            if (CommonConstantUtils.ZSLX_ZS.equals(bdcZsDOS.get(0).getZslx())) {
                return BdcBlZslxEnum.ZS.getValue();
            } else {
                return BdcBlZslxEnum.ZM.getValue();
            }
        }
    }
    /**
     * 判断生成证书数量
     * @param gzlslid 工作流实例ID
     * @return
     */
    private boolean sfscdbz(String gzlslid){
        boolean sfscdbz;
        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
        List<BdcCshFwkgSlDO> nullZsxhList = bdcCshFwkgSlDOList.stream().filter(fwkgsl -> Objects.isNull(fwkgsl.getZsxh())).collect(Collectors.toList());

        List<BdcCshFwkgSlDO> zsxhList = bdcCshFwkgSlDOList.stream().filter(fwkgsl -> Objects.nonNull(fwkgsl.getZsxh())).collect(Collectors.toList());
        Map<Integer, List<BdcCshFwkgSlDO>> zsxhMapList = zsxhList.stream().collect(Collectors.groupingBy(BdcCshFwkgSlDO::getZsxh));
        if (CollectionUtils.isNotEmpty(nullZsxhList) || zsxhMapList.size() > 1) {
            sfscdbz = true;
        } else {
            sfscdbz = false;
        }
        LOGGER.info("gzlslid={}生成证书，是否生成多本证:{}", gzlslid,sfscdbz);
        return sfscdbz;
    }
}
