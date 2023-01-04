package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXxblFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskCustomExtendClient;
import cn.gtmap.gtc.workflow.domain.common.RequestCondition;
import cn.gtmap.gtc.workflow.enums.manage.QueryJudge;
import cn.gtmap.realestate.accept.ui.config.PropsConfig;
import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcMatchDataPageDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlPageResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcLzQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcBdcdyFeignService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.IPUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/2
 * @description 数据匹配
 */
@Controller
@RequestMapping("/matchData")
public class BdcMatchDataController extends BaseController {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 匹配台账页面类型, 土地证/不动产单元号
     */
    private static final String PPTZ_LX_TDZ = "tdz";

    private static final String BDCDYH = "bdcdyh";

    @Autowired
    private BdcPpFeignService bdcPpFeignService;
    @Autowired
    private PropsConfig propsConfig;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    BdcLsgxFeignService bdcLsgxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    private ProcessTaskCustomExtendClient processTaskCustomExtendClient;
    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    BdcBdcdyFeignService bdcBdcdyFeignService;
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;
    @Autowired
    BdcXxblFeignService bdcXxblFeignService;

    @Value("${matchdata.tdzzxyy:房产证为历史，土地证为现势}")
    private String tdzzxyy;

    /**
     * @param bdcLzQO  落宗QO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 匹配不动产单元/落宗
     */
    @ResponseBody
    @PostMapping("/matchDyh")
    public void matchDyh(BdcLzQO bdcLzQO, HttpServletRequest httpServletRequest) throws Exception {
        if (StringUtils.isBlank(bdcLzQO.getLsdyh()) || StringUtils.isBlank(bdcLzQO.getBdcdyh())) {
            throw new MissingArgumentException("lsdyh,bdcdyh");
        }
        bdcLzQO.setIp(IPUtils.getRequestClientRealIP(httpServletRequest));
        bdcPpFeignService.lz(bdcLzQO);
        //匹配单元号同步更新掉受理库中的数据
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setBdcdyh(bdcLzQO.getLsdyh());
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                bdcSlXmDO.setBdcdyh(bdcLzQO.getBdcdyh());
                bdcSlXmFeignService.updateBdcSlXm(bdcSlXmDO);
            }
        }
        //更新大云的冗余字段数据
        hxXndyh(bdcLzQO.getLsdyh(), bdcLzQO.getBdcdyh());
    }

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 取消匹配不动产/落宗
     */
    @ResponseBody
    @PostMapping("/dismatchDyh")
    public void dismatchDyh(@RequestParam("bdcdyh") String bdcdyh, @RequestParam("xnxmid") String xnxmid,@RequestParam(name = "qjgldm", required = false) String qjgldm) throws Exception {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException(BDCDYH);
        }
        List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(xnxmid);
        if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
            for (BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO : bdcXnbdcdyhGxDOList) {
                if (bdcXnbdcdyhGxDO != null && StringUtils.equals(bdcdyh, bdcXnbdcdyhGxDO.getBdcdyh())) {
                    //取消匹配单元号同步更新掉受理库中的数据
                    BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
                    bdcSlXmQO.setBdcdyh(bdcdyh);
                    List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
                    if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                            bdcSlXmDO.setBdcdyh(bdcXnbdcdyhGxDO.getXnbdcdyh());
                            bdcSlXmFeignService.updateBdcSlXm(bdcSlXmDO);
                        }
                    }
                    //更新大云的冗余字段数据
                    hxXndyh(bdcdyh, bdcXnbdcdyhGxDO.getXnbdcdyh());
                }
            }
        }

        //取消登记库的匹配内容
        bdcPpFeignService.qxlz(bdcdyh, xnxmid,qjgldm);
    }

    /**
     * @param xnxmid
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自动取消匹配不动产/落宗
     */
    @ResponseBody
    @PostMapping("/autodismatchDyh")
    public void autodismatchDyh(@RequestParam("xnxmid") String xnxmid,@RequestParam("jbxxid") String jbxxid,@RequestParam(name = "qjgldm", required = false) String qjgldm) throws Exception {
        if (StringUtils.isBlank(xnxmid)) {
            throw new MissingArgumentException("xnxmid");
        }

        List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(xnxmid);
        List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(xnxmid);
        if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
            for (BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO : bdcXnbdcdyhGxDOList) {
                //取消匹配
                dismatchDyh(bdcXnbdcdyhGxDO.getBdcdyh(), xnxmid,qjgldm);
            }
        }
        if(CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
            for(BdcFctdPpgxDO bdcFctdPpgxDO : bdcFctdPpgxDOList) {
                dismatchTdzToNt(xnxmid,bdcFctdPpgxDO.getTdcqxmid(),jbxxid);
            }
        }
    }

    /**
     * @param fwxmid 房屋项目id
     * @param tdxmid 土地项目id
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 匹配土地证
     */
    @ResponseBody
    @PostMapping("/matchTdz")
    public void matchTdz(@RequestParam("fwxmid") String fwxmid,
                         @RequestParam("tdxmid") String tdxmid,
                         HttpServletRequest httpServletRequest
                         ) throws Exception {
        if (StringUtils.isBlank(fwxmid) || StringUtils.isBlank(tdxmid)) {
            throw new MissingArgumentException("fwxmid,tdxmid");
        }
        String requestClientRealIP = IPUtils.getRequestClientRealIP(httpServletRequest);
        bdcPpFeignService.pptdwithip(fwxmid, tdxmid,requestClientRealIP);
    }

    /**
     * @param fwxmid 房屋项目id
     * @param tdxmid 土地项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 匹配土地证
     */
    @ResponseBody
    @PostMapping("/matchTdz/nt")
    public void matchTdzToNt(@RequestParam("fwxmid") String fwxmid,
                             @RequestParam("tdxmid") String tdxmid,
                             @RequestParam("jbxxid") String jbxxid,
                             @RequestParam(value = "confirmZxTdz",required = false) Boolean confirmZxTdz
    ) throws Exception {
        if (StringUtils.isBlank(fwxmid) || StringUtils.isBlank(tdxmid)) {
            throw new MissingArgumentException("fwxmid,tdxmid");
        }
        bdcPpFeignService.pptd(fwxmid, tdxmid);
        //先添加和先匹配，如果是先添加后匹配，需要建立新的历史关系

        if (StringUtils.isNotBlank(jbxxid)) {
            //1.根据原xmid（fwxmid）和jbxxid找到当前这条数据的xmid
            BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
            bdcSlXmQO.setJbxxid(jbxxid);
            bdcSlXmQO.setXmid(fwxmid);
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.queryBdcSlXmByLsgx(bdcSlXmQO);
            // 2.根据当前xmid，和tdxmid且wlxm=1 查bdc_sl_xm_lsgx，找不到数据建立新的历史关系
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), tdxmid, CommonConstantUtils.SF_S_DM);
                    if (CollectionUtils.isEmpty(bdcSlXmLsgxDOList)) {
                        //关联土地证后插入历史关系作为外联
                        bdcSlXmLsgxFeignService.insertBdcSlXmLsgxByTdz(fwxmid, tdxmid, bdcSlXmDO);
                    }
                }
            }
        }

        //如果产证为历史，土地证为现势，且页面确认要注销，则将土地证更新为历史
        if(Objects.nonNull(confirmZxTdz) && confirmZxTdz){
            LOGGER.info("产证{}为历史，土地证{}为现势，且页面确认{}要注销",fwxmid,tdxmid,confirmZxTdz);
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(tdxmid);
            List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            bdcXmQO.setXmid(fwxmid);
            List<BdcXmDO> bdcFwXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
            if(CollectionUtils.isEmpty(bdcXmDOS) || CollectionUtils.isEmpty(bdcFwXmDOS)){
                LOGGER.info("产证{}为历史，土地证{}为现势,没有找到对应土地信息或房屋信息",fwxmid,tdxmid);
                return;
            }
            //验证状态
            if(CommonConstantUtils.QSZT_VALID.equals(bdcXmDOS.get(0).getQszt()) &&
                    CommonConstantUtils.QJDJ_HISTORY.equals(bdcFwXmDOS.get(0).getQszt())
            ){
                LOGGER.info("产证{}为历史，土地证{}为现势,可以注销",JSON.toJSONString(bdcFwXmDOS.get(0)),
                        JSON.toJSONString(bdcXmDOS.get(0)));
                Map data = new HashMap();
                data.put("产证",JSON.toJSONString(bdcFwXmDOS.get(0)));
                data.put("土地证",JSON.toJSONString(bdcXmDOS.get(0)));
                AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(),"房产证为历史，土地证为现势", data);
                zipkinAuditEventRepository.add(auditEvent);
                //注销土地证
                BdcZxQO bdcZxQO = new BdcZxQO();
                bdcZxQO.setXmid(tdxmid);
                bdcZxQO.setXmidList(Arrays.asList(tdxmid));
                bdcZxQO.setZxyy(tdzzxyy);
                bdcZxQO.setQszt(CommonConstantUtils.QSZT_HISTORY);
                bdcDbxxFeignService.zxQl(bdcZxQO, userManagerUtils.getCurrentUserName());
                // 更新注销权利的附记
                bdcXxblFeignService.updateZxqlFj(bdcZxQO.getXmidList());
            }

        }
    }

    /**
     * @param fwxmid 房屋项目id
     * @param tdxmid 土地项目id
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 取消匹配土地证
     */
    @ResponseBody
    @PostMapping("/dismatchTdz")
    public void dismatchTdz(@RequestParam("fwxmid") String fwxmid, @RequestParam("tdxmid") String tdxmid) throws Exception {
        if (StringUtils.isBlank(fwxmid)) {
            throw new MissingArgumentException("fwxmid");
        }
        bdcPpFeignService.qxpptd(fwxmid);
    }

    /**
     * @param fwxmid 房屋项目id
     * @param tdxmid 土地项目id
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 取消匹配土地证
     */
    @ResponseBody
    @PostMapping("/dismatchTdz/nt")
    public void dismatchTdzToNt(@RequestParam("fwxmid") String fwxmid, @RequestParam("tdxmid") String tdxmid, @RequestParam("jbxxid") String jbxxid) throws Exception {
        if (StringUtils.isBlank(fwxmid)) {
            throw new MissingArgumentException("fwxmid");
        }
        bdcPpFeignService.qxpptd(fwxmid);
        if (StringUtils.isNotBlank(jbxxid)) {
            //1.根据原xmid（fwxmid）和jbxxid找到当前这条数据的xmid
            BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
            bdcSlXmQO.setJbxxid(jbxxid);
            bdcSlXmQO.setXmid(fwxmid);
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.queryBdcSlXmByLsgx(bdcSlXmQO);
            // 2.根据当前xmid，和tdxmid且wlxm=1 查bdc_sl_xm_lsgx，存在删除对应的历史关系
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), tdxmid, CommonConstantUtils.SF_S_DM);
                    if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                        //取消匹配需要删除对应的外联历史关系
                        bdcSlXmLsgxFeignService.deleteBdcSlXmLsgxByGxid(bdcSlXmLsgxDOList.get(0).getGxid());
                    }
                    //同时需要更新bdc_sl_xm表的ybdcqzh字段
                    String bdcqzh = "";
                    String ycqzh = "";
                    List<String> removeList = new ArrayList<>();
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setXmid(tdxmid);
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(bdcXmDOList)) {
                        bdcqzh = bdcXmDOList.get(0).getBdcqzh();
                    }
                    ycqzh = bdcSlXmDO.getYbdcqz();
                    List<String> ycqzhList = new ArrayList<>(Arrays.asList(StringUtils.split(ycqzh,CommonConstantUtils.ZF_YW_DH)));
                    if(StringUtils.isNotBlank(bdcqzh) && CollectionUtils.isNotEmpty(ycqzhList) && ycqzhList.contains(bdcqzh)) {
                        removeList.add(bdcqzh);
                    }
                    ycqzhList.removeAll(removeList);
                    bdcSlXmDO.setYbdcqz(StringUtils.join(ycqzhList,CommonConstantUtils.ZF_YW_DH));
                    bdcSlXmFeignService.updateBdcSlXm(bdcSlXmDO);
                }
            }
        }
    }

    /**
     * @param lx 页面类型,不动产单元/土地证
     * @return 配置结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取匹配页面条件配置
     */
    @ResponseBody
    @PostMapping("/pz")
    public Map pz(@RequestParam("lx") String lx, @RequestParam(name = "fwxmid", required = false) String fwxmid, @RequestParam(name = "xnxmid", required = false) String xnxmid) {
        Map map = Maps.newHashMap();
        if (StringUtils.equals(PPTZ_LX_TDZ, lx)) {
            String qllxs = propsConfig.getDataPicTdzQllxs();
            map.put("qllx", qllxs);
            //查询匹配关系
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(fwxmid);
            if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                map.put("pptdxmid", bdcFctdPpgxDOList.get(0).getTdcqxmid());
            }else{
                //不存在匹配关系，查询是否存在相同单元号的土地证，存在默认展示相同单元号的土地证
                BdcXmQO bdcXmQO =new BdcXmQO();
                bdcXmQO.setXmid(fwxmid);
                List<BdcXmDO> fwXmList =bdcXmFeignService.listBdcXm(bdcXmQO);
                if(CollectionUtils.isNotEmpty(fwXmList)) {
                    bdcXmQO =new BdcXmQO();
                    bdcXmQO.setBdcdyh(fwXmList.get(0).getBdcdyh());
                    bdcXmQO.setBdclx(CommonConstantUtils.DYBDCLX_CTD);
                    bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                    List<BdcXmDO> tdXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(tdXmList)) {
                        map.put("gltdxmid", tdXmList.get(0).getXmid());
                    }
                }

            }

        } else if (StringUtils.equals(BDCDYH, lx)) {
            List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(xnxmid);
            if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
                map.put("ppbdcdyh", bdcXnbdcdyhGxDOList.get(0).getBdcdyh());
            }

        }
        return map;
    }

    /**
     * @param tdxmid 土地项目id
     * @return 创建项目的数量
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据tdxmid获取到对应的匹配的房产的数据，根据xmlsgx判断是否办理过登记
     */
    @ResponseBody
    @GetMapping("/sfcjxm")
    public Object checkSfcjXm(String tdxmid) {
        if (StringUtils.isBlank(tdxmid)) {
            throw new AppException("缺失查询土地匹配关系的tdxmid");
        }
        //1.根据tdxmid找到之前的匹配关系
        List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgxByTdxmid(tdxmid);
        if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
            //2.根据匹配的fwcqxmid作为原项目id 查询项目历史关系，存在说明办理过登记
            BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
            bdcXmLsgxQO.setYxmid(bdcFctdPpgxDOList.get(0).getFwcqxmid());
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcLsgxFeignService.listXmLsgxByXmid(bdcXmLsgxQO);
            if (CollectionUtils.isEmpty(bdcXmLsgxDOList)) {
                return 0;
            }
            return bdcXmLsgxDOList.size();
        } else {
            return 0;
        }
    }

    /**
     * @param tdxmid 土地项目id
     * @return
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 重新建立房地匹配关系，匹配之前要先解除之前的土地证匹配关系
     */
    @ResponseBody
    @GetMapping("/rematch")
    public void reMatchTdz(@RequestParam(value = "fwcqxmid") String fwcqxmid,
                           @RequestParam(value = "tdxmid") String tdxmid,
                           @RequestParam(value = "jbxxid") String jbxxid,
                           @RequestParam(value = "confirmZxTdz", required = false) Boolean confirmZxTdz) throws Exception {
        //1.根据tdxmid找到之前的匹配关系
        List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgxByTdxmid(tdxmid);
        if(CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
            //2.取消之前的匹配关系
            dismatchTdzToNt(bdcFctdPpgxDOList.get(0).getFwcqxmid(), tdxmid, jbxxid);
            //3.匹配当前的房产证
            matchTdzToNt(fwcqxmid, tdxmid, jbxxid, confirmZxTdz);
        }
    }

    /**
     * @param bdcQlQO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询匹配数据(房产证,土地证)
     */
    @ResponseBody
    @GetMapping("/listMatchDataByPageJson")
    public Object listMatchDataByPageJson(@LayuiPageable Pageable pageable, BdcQlQO bdcQlQO) {
        bdcQlQO.setBdcdyh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdyh()));
        bdcQlQO.setBdcqzh(StringUtils.deleteWhitespace(bdcQlQO.getBdcqzh()));
        bdcQlQO.setZl(StringUtils.deleteWhitespace(bdcQlQO.getZl()));
        bdcQlQO.setQlrmc(StringUtils.deleteWhitespace(bdcQlQO.getQlrmc()));
        bdcQlQO.setBdcdywybh(StringUtils.deleteWhitespace(bdcQlQO.getBdcdywybh()));
        List<BdcMatchDataPageDTO> matchDataPageDTOList =new ArrayList<>();
        bdcQlQO.setQszt(CommonConstantUtils.QSZT_VALID);
        //xmly为2,3
        bdcQlQO.setXmly(Arrays.asList(CommonConstantUtils.XMLY_QT_DM, CommonConstantUtils.XMLY_CLGD_DM));
        bdcQlQO.setClgdsj(true);
        //区县代码
        bdcQlQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("ppcxtz", "", ""));
        Page<BdcQlPageResponseDTO> pageResponseDTOS = bdcXmFeignService.bdcQlPageByPageJson(pageable, JSON.toJSONString(bdcQlQO));
        pageResponseDTOS.getContent().forEach(bdcQlPageResponseDTO->{
            BdcMatchDataPageDTO bdcMatchDataPageDTO =new BdcMatchDataPageDTO();
            BeanUtils.copyProperties(bdcQlPageResponseDTO, bdcMatchDataPageDTO);
            //落宗状态
            if (StringUtils.isNotBlank(bdcQlPageResponseDTO.getBdcdyh()) &&BdcdyhToolUtils.checkXnbdcdyh(bdcQlPageResponseDTO.getBdcdyh())) {
                bdcMatchDataPageDTO.setClfsjlzzt(CommonConstantUtils.SF_F_DM);
            }else{
                bdcMatchDataPageDTO.setClfsjlzzt(CommonConstantUtils.SF_S_DM);
            }
            //匹配状态
            bdcMatchDataPageDTO.setClfsjppzt(CommonConstantUtils.SF_F_DM);
            if(StringUtils.isNotBlank(bdcQlPageResponseDTO.getXmid())) {
                List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgx(bdcQlPageResponseDTO.getXmid());
                if(CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)){
                    bdcMatchDataPageDTO.setClfsjppzt(CommonConstantUtils.SF_S_DM);
                }else if(StringUtils.isNotBlank(bdcQlPageResponseDTO.getBdcdyh()) &&StringUtils.equals(CommonConstantUtils.BDCLX_TZM_F,BdcdyhToolUtils.getDzwTzm(bdcQlPageResponseDTO.getBdcdyh()))){
                    //房产证与土地证存在相同单元号,为已匹配
                    BdcXmQO bdcXmQO =new BdcXmQO();
                    bdcXmQO.setBdcdyh(bdcQlPageResponseDTO.getBdcdyh());
                    bdcXmQO.setBdclx(CommonConstantUtils.DYBDCLX_CTD);
                    bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                    List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                        bdcMatchDataPageDTO.setClfsjppzt(CommonConstantUtils.SF_S_DM);
                    }

                }


            }
            matchDataPageDTOList.add(bdcMatchDataPageDTO);

        });

        return addLayUiCode(new PageImpl(matchDataPageDTOList, pageable, pageResponseDTOS.getTotalElements()));
    }



    /**
     * @param lsdyh-历史单元号,xsdyh-现势单元号
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 回写虚拟单元号
     * @date : 2021/10/14 10:52
     */
    private void hxXndyh(String lsdyh, String xsdyh) {
        List<RequestCondition> list = new ArrayList<>();
        RequestCondition requestCondition = new RequestCondition();
        requestCondition.setRequestJudge(QueryJudge.LIKE.value());
        requestCondition.setRequestKey("bdcdyh");
        requestCondition.setRequestValue(StringUtils.deleteWhitespace(lsdyh));
        list.add(requestCondition);
        Page<Map<String, Object>> allProcessInsPage = processTaskCustomExtendClient.queryProcessInsWithProject(list, 0, 500, 0);
        if (CollectionUtils.isNotEmpty(allProcessInsPage.getContent())) {
            //循环数据，根据工作流实例id更新冗余字段
            for (Map map : allProcessInsPage.getContent()) {
                String newdyh = xsdyh;
                Map<String, Object> processInsExtendDto;
                String gzlslid = String.valueOf(map.get("processInstanceId"));
                //先获取
                List<Map<String, Object>> processInsCustomExtendList = processInsCustomExtendClient.getProcessInsCustomExtend(gzlslid);
                boolean update = false;
                if (CollectionUtils.isNotEmpty(processInsCustomExtendList)) {
                    processInsExtendDto = processInsCustomExtendList.get(0);
                    update = true;
                } else {
                    processInsExtendDto = new HashMap();
                }
                String dyh = String.valueOf(processInsExtendDto.get("BDCDYH"));
                if (StringUtils.isNotBlank(dyh) && dyh.contains(CommonConstantUtils.SUFFIX_PL)) {
                    newdyh = xsdyh + CommonConstantUtils.SUFFIX_PL;
                }
                processInsExtendDto.put("BDCDYH", newdyh);
                if (update) {
                    LOGGER.info("开始更新回写大云虚拟单元号{}，匹配后单元号{}", lsdyh, xsdyh);
                    processInsCustomExtendClient.updateProcessInsCustomExtend(gzlslid, processInsExtendDto);
                } else {
                    LOGGER.error("未找到大云业务数据信息，无法更新");
                }
            }
        }
    }

    /**
     * @param bdcdyh
     * @param gltdXmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 解除关联关系, 逻辑 数据库中存在房屋和土地相同单元号，但是不存在房屋土地匹配关系的数据，
     * 需要解除这种关系，把土地的单元号编新的虚拟单元号，更新项目表以及权利表的单元号信息
     * @date : 2021/12/30 14:23
     */

    @ResponseBody
    @GetMapping("/qxgl")
    public void qxglTdxx(String gltdXmid, String fwXmid, String bdcdyh) throws Exception {
        if (StringUtils.isNoneBlank(gltdXmid, fwXmid, bdcdyh)) {
            String xndyh = "";
            //1. 先判断是否确实不存在房屋土地匹配关系，存在关系不能直接解除关系，要走取消匹配的逻辑
            List<BdcFctdPpgxDO> bdcFctdPpgxDOList = bdcPpFeignService.queryBdcFctdPpgxByTdxmid(gltdXmid);
            if (CollectionUtils.isNotEmpty(bdcFctdPpgxDOList)) {
                throw new AppException("存在土地匹配关系无法解除关联，请先取消匹配");
            }
            //2. 根据单元号去生成土地的虚拟号
            if (StringUtils.length(bdcdyh) == CommonConstantUtils.BDCDYH_LENGTH) {
                String qxdm = bdcdyh.substring(0, 6);
                String zdzhtzm = bdcdyh.substring(12, 14);
                xndyh = bdcBdcdyFeignService.createXndyh(qxdm, zdzhtzm, CommonConstantUtils.BDCLX_TZM_W);
                //3. 更新项目表和权利表等数据信息
                if (StringUtils.isNotBlank(xndyh)) {
                    List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXmByXmids(Collections.singletonList(gltdXmid));
                    BdcXmQO bdcXmQO =new BdcXmQO();
                    bdcXmQO.setXmid(gltdXmid);
                    List<BdcXmAndFbDTO> bdcXmFbDOS =bdcXmFeignService.listBdcXmAndFb(bdcXmQO);
                    bdcPpFeignService.updateBdcdyh(bdcXmDOList, xndyh,CollectionUtils.isNotEmpty(bdcXmFbDOS)?bdcXmFbDOS.get(0).getQjgldm():"");
                }
            }
            //4. 记录日志
            HashMap map = new HashMap(4);
            String message = "当前单元号" + bdcdyh + "对应产权xmid:" + fwXmid + "对应土地项目id:" + gltdXmid + "解除关联关系,土地编虚拟单元号:" + xndyh;
            UserDto userDto = userManagerUtils.getCurrentUser();
            map.put(CommonConstantUtils.VIEW_TYPE, Constants.ACCEPT_UI);
            map.put(CommonConstantUtils.VIEW_TYPE_NAME, "房产证和土地证取消匹配服务");
            map.put(CommonConstantUtils.ALIAS, userDto != null ? userDto.getAlias() : userManagerUtils.getCurrentUserName());
            map.put(Constants.HLNR, message);
            LOGGER.warn(message);
            AuditEvent auditEvent = new AuditEvent(userManagerUtils.getCurrentUserName(), "房产证和土地证取消匹配服务", map);
            zipkinAuditEventRepository.add(auditEvent);
        }
    }

    /**
     * @param xmid
     * @author <a href="mailto:wangfangfang@gtmap.cn">wangfangfang</a>
     * @description 一证多房情况下，搜索不动产项目id会出现多条项目信息，其中有些是属于一本证书
     */
    @ResponseBody
    @PostMapping("/zsxm/count")
    public int count(@RequestParam("xmid") String xmid){
        List<String> xmidList= bdcXmFeignService.listYbzXmByXmid(xmid,null);
        return CollectionUtils.isNotEmpty(xmidList)? xmidList.size():0;
    }



    /**
     * @param bdcLzQO  落宗QO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 历史单元匹配/落宗
     */
    @ResponseBody
    @PostMapping("/matchLsDyh")
    public void matchLsDyh(BdcLzQO bdcLzQO, HttpServletRequest httpServletRequest) throws Exception {
        if (StringUtils.isBlank(bdcLzQO.getLsdyh()) || StringUtils.isBlank(bdcLzQO.getBdcdyh())) {
            throw new MissingArgumentException("lsdyh,bdcdyh");
        }
        bdcLzQO.setIp(IPUtils.getRequestClientRealIP(httpServletRequest));
        bdcPpFeignService.lslz(bdcLzQO);
        //匹配单元号同步更新掉受理库中的数据
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setBdcdyh(bdcLzQO.getLsdyh());
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                bdcSlXmDO.setBdcdyh(bdcLzQO.getBdcdyh());
                bdcSlXmFeignService.updateBdcSlXm(bdcSlXmDO);
            }
        }
        //更新大云的冗余字段数据
        hxXndyh(bdcLzQO.getLsdyh(), bdcLzQO.getBdcdyh());
    }

    /**
     * @param bdcdyh 不动产单元号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 取消历史单元匹配/落宗
     */
    @ResponseBody
    @PostMapping("/dismatchLsDyh")
    public void dismatchLsDyh(@RequestParam("bdcdyh") String bdcdyh, @RequestParam("xnxmid") String xnxmid,@RequestParam(name = "qjgldm", required = false) String qjgldm) throws Exception {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException(BDCDYH);
        }
        List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhGxByXmid(xnxmid);
        if (CollectionUtils.isNotEmpty(bdcXnbdcdyhGxDOList)) {
            for (BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO : bdcXnbdcdyhGxDOList) {
                if (bdcXnbdcdyhGxDO != null && StringUtils.equals(bdcdyh, bdcXnbdcdyhGxDO.getBdcdyh())) {
                    //取消匹配单元号同步更新掉受理库中的数据
                    BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
                    bdcSlXmQO.setBdcdyh(bdcdyh);
                    List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
                    if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                        for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                            bdcSlXmDO.setBdcdyh(bdcXnbdcdyhGxDO.getXnbdcdyh());
                            bdcSlXmFeignService.updateBdcSlXm(bdcSlXmDO);
                        }
                    }
                    //更新大云的冗余字段数据
                    hxXndyh(bdcdyh, bdcXnbdcdyhGxDO.getXnbdcdyh());
                }
            }
        }

        //取消登记库的匹配内容
        bdcPpFeignService.qxlslz(bdcdyh, xnxmid,qjgldm);
    }


}
