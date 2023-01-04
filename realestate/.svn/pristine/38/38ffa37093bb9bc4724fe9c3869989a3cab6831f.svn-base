package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.workflow.clients.define.v1.WorkDayClient;
import cn.gtmap.gtc.workflow.domain.define.WorkDay;
import cn.gtmap.gtc.workflow.domain.define.WorkDayRelation;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcYyQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcYyXxCxFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcYyVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import cn.gtmap.realestate.inquiry.ui.config.HtmlVersionConfig;
import cn.gtmap.realestate.inquiry.ui.util.IpUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-07-12
 * @description
 */
@RestController
@RequestMapping(value = "/rest/v1.0/bdcYyXx")
public class BdcYyXxCxController extends BaseController {
    @Autowired
    BdcYyXxCxFeignService bdcYyXxCxFeignService;
    @Autowired
    WorkDayClient workDayClient;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    private BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    private HtmlVersionConfig htmlVersionConfig;

    /**
     * @description Excel全部导出的导出条数
     */
    @Value("${excel.qxdcts:1000}")
    private Integer dcts;

    /**
     * 淮安房产交易查询台账推送
     */
    @Value("${huaian.fcjy.cxtzts:false}")
    private boolean cxtzts;


    /**
     * @param pageable
     * @param bdcYyQO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页获取异议信息
     */
    @GetMapping("/page")
    public Object listBdcYyByPage(@LayuiPageable Pageable pageable, BdcYyQO bdcYyQO, HttpServletRequest request) {
        bdcYyQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("yycx","",""));
        Page<BdcYyVO> bdcYyDOPage = null;
        if(StringUtils.isNotBlank(bdcYyQO.getType()) && bdcYyQO.getType().equals("export")) {
            // 导出全部
            String bdcYyQOJson = JSON.toJSONString(bdcYyQO);
            return bdcYyXxCxFeignService.listBdcYy(bdcYyQOJson);
        }else{
            String ip = IpUtils.getIpFromRequest(request);
            bdcYyQO.setClientIp(ip);
            String bdcYyQOJson = JSON.toJSONString(bdcYyQO);
            bdcYyDOPage = bdcYyXxCxFeignService.listBdcYyByPage(pageable, bdcYyQOJson);
        }
        this.handlerBdcYyData(bdcYyDOPage);
        return super.addLayUiCode(bdcYyDOPage);
    }

    private void handlerBdcYyData(Page<BdcYyVO> bdcYyDOPage){
        List<BdcYyVO> bdcYyVOList = bdcYyDOPage.getContent();
        if(CollectionUtils.isNotEmpty(bdcYyVOList)){
            BdcXmQO bdcXmDO = new BdcXmQO();
            bdcYyVOList.forEach(bdcYy->{
                bdcXmDO.setXmid(bdcYy.getXmid());
                List<BdcXmDO> bdcXmDOList=bdcXmFeignService.listBdcXm(bdcXmDO);
                if(CollectionUtils.isEmpty(bdcXmDOList)){
                    return;
                }
                String gzlslid = bdcXmDOList.get(0).getGzlslid();
                if(StringUtils.isBlank(gzlslid)){
                    return;
                }
                WorkDayRelation workDayRelation = workDayClient.findWorkDayRelation(gzlslid, 0);
                if(workDayRelation==null){
                    return;
                }
                // 对登记时间判空
                List<WorkDay> workDayList = new ArrayList<>();
                if(bdcYy.getDjsj() != null){
                    workDayList=workDayClient.getWorkDays(workDayRelation.getWorkId(), DateUtils.format(bdcYy.getDjsj(), "yyyy-MM-dd"), DateUtils.format(new Date(), "yyyy-MM-dd"));
                }
                if(CollectionUtils.isEmpty(workDayList)){
                    return;
                }
                bdcYy.setQx(CommonConstantUtils.BDC_YY_QX - workDayList.size());
            });
        }
    }

    /**
     * 常州，拥有同综合查询一样多查询条件的 异议查询台账
     */
    @GetMapping("/page/cz")
    public Object listBdcYyByPageCz(@LayuiPageable Pageable pageable, BdcYyQO bdcYyQO, HttpServletRequest request) {
        bdcYyQO.setQxdmList(Container.getBean(BdcConfigUtils.class).qxdmFilter("yycx","",""));
        Page<BdcYyVO> bdcYyDOPage = null;
        if(StringUtils.equals("export", bdcYyQO.getType())) {
            // 导出全部
            String bdcYyQOJson = JSON.toJSONString(bdcYyQO);
            return bdcYyXxCxFeignService.listBdcYy(bdcYyQOJson);
        }else{
            String ip = IpUtils.getIpFromRequest(request);
            bdcYyQO.setClientIp(ip);
            String bdcYyQOJson = JSON.toJSONString(bdcYyQO);
            bdcYyDOPage = bdcYyXxCxFeignService.listBdcYyByPageCz(pageable, bdcYyQOJson);
        }
        this.handlerBdcYyData(bdcYyDOPage);
        return super.addLayUiCode(bdcYyDOPage);
    }

    /**
     * @param bdcYyDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 批量更新不动产异议信息
     */
    @PostMapping
    public void updateBdcYy(@RequestBody List<BdcYyDO> bdcYyDOList){
        if(CollectionUtils.isEmpty(bdcYyDOList)){
            throw new AppException("不动产异议信息不能为空！");
        }
        List<BdcXmDO> bdcXmDOListAll=Lists.newArrayListWithExpectedSize(bdcYyDOList.size());
        bdcYyDOList.forEach(bdcYyDO -> {
            UserDto userDto = userManagerUtils.getUserByName(userManagerUtils.getCurrentUserName());
            bdcYyDO.setZxyydbr(userDto.getAlias());
            bdcYyDO.setZxyydjsj(new Date());
            bdcYyDO.setQszt(CommonConstantUtils.QSZT_HISTORY);
            if(StringUtils.isNotBlank(bdcYyDO.getXmid())){
                BdcXmQO bdcXmQO=new BdcXmQO();
                bdcXmQO.setXmid(bdcYyDO.getXmid());
                bdcXmDOListAll.addAll(bdcXmFeignService.listBdcXm(bdcXmQO));
            }
        });
        /**
         * 注销异议
         */
        bdcYyXxCxFeignService.updateBdcYy(bdcYyDOList);
        /**
         * 修改项目权属状态
         */
        if(CollectionUtils.isNotEmpty(bdcXmDOListAll)){
            bdcXmDOListAll.forEach(bdcXmDO -> {
                bdcXmDO.setQszt(CommonConstantUtils.QSZT_HISTORY);
                bdcXmFeignService.updateBdcXm(bdcXmDO);
            });
        }

        // 淮安异议注销时需要向住建推送交易信息
        if (cxtzts) {
            try {
                // 获取项目信息
                if (CollectionUtils.isNotEmpty(bdcXmDOListAll)) {
                    List<String> xmidList = bdcXmDOListAll.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                    String xmids = StringUtils.join(xmidList, ",");
                    bdcSlJyxxFeignService.tsHaFcjyTsYwxx("", "1", xmids);
                }
            } catch (Exception e) {
                LOGGER.error("推送淮安房产交易通知业务信息失败", e);
            }


        }
    }

    /**
     * @description 获取综合查询日志配置项,excel导出全部的导出条数
     * @return
     */
    @GetMapping("/yyxx/pz")
    public Integer getYyxxPz(){
        return dcts;
    }
}
