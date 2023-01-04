package cn.gtmap.realestate.check.web.rest;


import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.realestate.check.core.thread.RuleEngine;
import cn.gtmap.realestate.check.core.vo.SelectCgsjVo;
import cn.gtmap.realestate.check.service.RetestService;
import cn.gtmap.realestate.check.service.XxSolveServer;
import cn.gtmap.realestate.check.utils.Constants;
import cn.gtmap.realestate.check.web.main.BaseController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.BdcZdQxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdSfDO;
import cn.gtmap.realestate.common.core.domain.BdcZdZjzlDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.domain.check.CheckZdGzdjDO;
import cn.gtmap.realestate.common.core.dto.BdcMapZdConvertDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 解决信息查询
 * Date: 16-12-05
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 * @author yanyong
 */
@Controller
@RequestMapping("/rest/v1.0/queryCgsjCheck")
public class QueryCgsjCheckContorller extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryCgsjCheckContorller.class);

    @Autowired
    private XxSolveServer xxSolveServer;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private RetestService retestService;
    @Autowired
    private BdcZdCache bdcZdCache;
    @Autowired
    private EntityZdConvertUtils entityZdConvertUtils;
    @Autowired
    private RuleEngine ruleEngine;
    @Autowired
    private ProcessDefinitionClient processDefinitionClient;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private BdcSlZdFeignService bdcSlZdFeignService;

    @ResponseBody
    @RequestMapping("/getCgsjList")
    public Object getCgsjList(@LayuiPageable Pageable pageable, SelectCgsjVo selectCgsjVo) {
        return addLayUiCode(repository.selectPaging("quertCgsjListByPage", selectCgsjVo, pageable));
    }

    @ResponseBody
    @RequestMapping("/xxSolve")
    public Object xxSolve(@RequestParam(value = "logid", required = false) String[] logid, String jjfs) {
        String msg = "失败";
        if (logid != null && logid.length > 0) {
            for (int i = 0; i < logid.length; i++) {
                CheckGzjcLogDO bdcGzjcLog = xxSolveServer.getGzxxSolve(logid[i]);
                if (null != bdcGzjcLog) {
                    Date date = new Date();
                    bdcGzjcLog.setJjfs(jjfs);
                    bdcGzjcLog.setJjsj(date);
                    bdcGzjcLog.setJjzt(Constants.GZJC_JJZT);
                    entityMapper.updateByPrimaryKeySelective(bdcGzjcLog);
                    msg = "成功";
                }
            }
        } else {
            msg = "无处理数据";
        }
        return msg;
    }
    @ResponseBody
    @RequestMapping("/cancelSolve")
    public Object cancelSolve(@RequestParam(value = "logid", required = false) String[] logid) {
        String msg = "失败";
        if (logid != null && logid.length > 0) {
            for (int i = 0; i < logid.length; i++) {
                CheckGzjcLogDO bdcGzjcLog = xxSolveServer.getGzxxSolve(logid[i]);
                if (null != bdcGzjcLog) {
                    bdcGzjcLog.setJjsj(null);
                    bdcGzjcLog.setJjfs("");
                    bdcGzjcLog.setJjzt(Constants.GZJC_WJJZT);
                    entityMapper.updateByPrimaryKeySelective(bdcGzjcLog);
                    msg = "成功";
                }
            }
        } else {
            msg = "无处理数据";
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping("/xxGuaQi")
    public Object xxGuaQi(@RequestParam(value = "logid", required = false) String[] logid, String sfqx, String gqyy) {
        String msg = "失败";
        Integer sfgq = Constants.GZJC_GQZT;
        if (StringUtils.equals(sfqx, "0")) {
            sfgq = Constants.GZJC_WGQZT;
            gqyy= "";
        }
        if (logid != null && logid.length > 0) {
            for (int i = 0; i < logid.length; i++) {
                CheckGzjcLogDO bdcGzjcLog = xxSolveServer.getGzxxSolve(logid[i]);
                if (null != bdcGzjcLog) {
                    Date date = new Date();
                    bdcGzjcLog.setGqsj(date);
                    bdcGzjcLog.setSfgq(sfgq);
                    bdcGzjcLog.setGqyy(gqyy);
                    entityMapper.updateByPrimaryKeySelective(bdcGzjcLog);
                    msg = "成功";
                }
            }
        } else {
            msg = "无处理数据";
        }
        return msg;
    }

    /**
     * 对错误日志数据进行重检或按设定时间段进行检测
     *
     * @param logid 重检的日志id数据
     * @param qsrq  起始日期
     * @param jsrq  结束日期
     * @return 操作结果信息
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ResponseBody
    @RequestMapping("/retest")
    public Object retest(@RequestParam(value = "xmids", required = false) String[] xmids, String qsrq, String jsrq, String gzid,String qxdm) {
        String msg = "正在检测，请稍等";
        String result="";
        try {
            if (xmids != null && xmids.length > 0) {
                // 日志重检
                result=retestService.retestErrorData(xmids);
            } else if (StringUtils.isNotBlank(gzid)) {
                // 规则检测   可以选择一个时段进行规则检测
                result=retestService.retestRuleData(qsrq,jsrq,gzid,qxdm);
            }else if (StringUtils.isNotBlank(qsrq) || StringUtils.isNotBlank(jsrq)) {
                // 时段检测
                result=retestService.retestTimesData(qsrq, jsrq);
            } else {
                msg = "无检测数据";
            }
            if(StringUtils.isNotBlank(result)){
                msg=result;
            }
        } catch (Exception e) {
            msg = "检测失败";
            LOGGER.error("手动检测功能失败:", e);
        }
        return msg;
    }

    /**
     * 导出查询结果数据
     * @param groupid
     * @param gzid
     * @param gzcode
     * @param response
     */
    @ResponseBody
    @RequestMapping("/exportCgsjList")
    public void exportCgsjList(SelectCgsjVo selectCgsjVo, HttpServletResponse response) {
        List<Map<String,Object>> list = retestService.queryCgsjList(selectCgsjVo);
        //字典转换
        List<BdcMapZdConvertDTO> convertVoList = new ArrayList<BdcMapZdConvertDTO>();
        convertVoList.add(new BdcMapZdConvertDTO("jcdj", "CHECK_ZD_GZDJ", "DM", "MC",CheckZdGzdjDO.class));
        convertVoList.add(new BdcMapZdConvertDTO("sfgq", "BDC_ZD_SF", "DM", "MC",BdcZdSfDO.class));
        for(Map<String,Object> cgsjMap:list){
            entityZdConvertUtils.convertMapToMc(cgsjMap, convertVoList);
            if(cgsjMap.containsKey("JJZT") && cgsjMap.get("JJZT")!=null){
                if(StringUtils.equals(cgsjMap.get("JJZT").toString(), CommonConstantUtils.SF_S_DM.toString())){
                    cgsjMap.put("JJZT","已解决");
                }else if(StringUtils.equals(cgsjMap.get("JJZT").toString(), CommonConstantUtils.SF_F_DM.toString())){
                    cgsjMap.put("JJZT","未解决");
                }
            }
        }
        retestService.export(list,response,selectCgsjVo.getJcxx());
    }

    /**
     * 检查情况
     * @return
     */
    @ResponseBody
    @RequestMapping("/jcqk")
    public Object jcqk() {
        return ruleEngine.getJcqk();
    }

    @ResponseBody
    @PostMapping("/bdczd")
    public Map<String, List<Map>> initzd() {
        Map<String, List<Map>> zdMap = new HashMap<>();
        Map<String, List<Map>> slZdMap = new HashMap<>();
        try {
            zdMap = bdcZdFeignService.listBdcZd();
            slZdMap = bdcSlZdFeignService.listBdcSlzd();
        } catch (Exception e) {
            LOGGER.error("字典项服务获取失败");
        }
        zdMap.putAll(slZdMap);
        return zdMap;
    }
}
