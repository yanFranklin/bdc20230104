package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.AccessStatsDto;
import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.QueryLogCondition;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhcxDyzmTjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcZhcxDyzmTjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZhcxTjFeignService;
import cn.gtmap.realestate.common.property.inquiryui.PrintTypeConfig;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.inquiry.ui.config.HtmlVersionConfig;
import cn.gtmap.realestate.inquiry.ui.core.qo.BdcXtOrgQO;
import cn.gtmap.realestate.inquiry.ui.web.rest.config.ZtreeController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/29
 * @description 查询子系统：综合查询统计
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcZhcxTjController {
    @Autowired
    private PrintTypeConfig printTypeConfig;
    @Autowired
    private HtmlVersionConfig htmlVersionConfig;
    @Autowired
    private LogMessageClient logMessageClient;
    @Autowired
    private BdcZhcxTjFeignService bdcZhcxTjFeignService;

    @Value("${cxtj.sfglbm:false}")
    private boolean sfglbm;

    @Autowired
    ZtreeController ztreeController;


    /**
     * 综合查询打印证明统计
     * @param bdcZhcxDyzmTjQO 查询条件
     */
    @PostMapping("/zhcx/dyzm")
    public BdcZhcxDyzmTjDTO zhcxDyzmTj(@RequestBody BdcZhcxDyzmTjQO bdcZhcxDyzmTjQO) {
        if(null == bdcZhcxDyzmTjQO){
            throw new AppException("综合查询打印证明统计异常：未指定查询参数");
        }
        if(sfglbm && StringUtils.isBlank(bdcZhcxDyzmTjQO.getBmmc())){
            List<OrganizationDto> organizationDtos = ztreeController.queryGlOrgs(new BdcXtOrgQO());
            if (CollectionUtils.isNotEmpty(organizationDtos)){
                bdcZhcxDyzmTjQO.setBmmc(organizationDtos.stream().map(OrganizationDto::getName).collect(Collectors.joining(",")));
            }
        }
        return bdcZhcxTjFeignService.zhcxDyzmTj(bdcZhcxDyzmTjQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZhcxDyzmTjQO 查询条件
     * @param zmtype 证明类型
     * @description  统计指定证明类型打印数量
     */
    private AccessStatsDto statistic(BdcZhcxDyzmTjQO bdcZhcxDyzmTjQO, String zmtype){
        List<QueryLogCondition> conditions = new ArrayList<>();
        // 指定查询某个证明日志
        QueryLogCondition condition = new QueryLogCondition();
        condition.setKey("printType");
        condition.setValue(zmtype);
        condition.setType("equal");
        conditions.add(condition);

        // 指定时间区间
        Long beginTime = null, endTime = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getKssj())){
                beginTime = simpleDateFormat.parse(bdcZhcxDyzmTjQO.getKssj()).getTime();
            }
            if(StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getJzsj())){
                endTime = simpleDateFormat.parse(bdcZhcxDyzmTjQO.getJzsj()).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        // 先查询指定证明对应所有部门或者人员的打印数量
        AccessStatsDto accessStatsDto = logMessageClient.statisticLog(bdcZhcxDyzmTjQO.getTjwd(), "PRINTLOG", null, beginTime, endTime,conditions);
        if(null == accessStatsDto || MapUtils.isEmpty(accessStatsDto.getDetails())){
            return null;
        }

        // 再根据指定的结构、人员筛选结果
        /// 统计维度：机构
        if(StringUtils.equals("organization", bdcZhcxDyzmTjQO.getTjwd()) && StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getBmmc())){
            String[] bmmcArray = bdcZhcxDyzmTjQO.getBmmc().split(",");

            Iterator<Map.Entry<String, Integer>> iterator = accessStatsDto.getDetails().entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Integer> entry = iterator.next();
                // 如果不是指定要查询的机构则删除
                if(!StringToolUtils.existItemEquals(entry.getKey(), bmmcArray)){
                    iterator.remove();
                }
            }
        }

        /// 统计维度：人员
        if(StringUtils.equals("alias", bdcZhcxDyzmTjQO.getTjwd()) && StringUtils.isNotBlank(bdcZhcxDyzmTjQO.getRymc())){
            String[] rymcArray = bdcZhcxDyzmTjQO.getRymc().split(",");

            Iterator<Map.Entry<String, Integer>> iterator = accessStatsDto.getDetails().entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Integer> entry = iterator.next();
                // 如果不是指定要查询的人员则删除
                if(!StringToolUtils.existItemEquals(entry.getKey(), rymcArray)){
                    iterator.remove();
                }
            }
        }

        return accessStatsDto;
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn"></a>
     * @description 根据版本获取打印类型
     * @return List<String>
     */
    @GetMapping("/search/list/printType")
    public List<String> searchPrintTypeListByVersion(){
        switch (htmlVersionConfig.getVersion()){
            case "nantong" :
                return printTypeConfig.getNantong();
            case "heifei" :
                return printTypeConfig.getHeifei();
            case "standard" :
                return printTypeConfig.getStandard();
            default:
                return printTypeConfig.getStandard();
        }
    }
}
