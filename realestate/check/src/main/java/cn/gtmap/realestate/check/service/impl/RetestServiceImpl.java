package cn.gtmap.realestate.check.service.impl;

import cn.gtmap.realestate.check.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.check.core.thread.RuleEngine;
import cn.gtmap.realestate.check.core.vo.SelectCgsjVo;
import cn.gtmap.realestate.check.service.RetestService;
import cn.gtmap.realestate.check.utils.Constants;
import cn.gtmap.realestate.check.utils.ExcelUtil;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import jxl.write.WriteException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lst
 * @version 1.0, 2018-3-13
 * @description 重检服务
 */
@Service
public class RetestServiceImpl implements RetestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetestServiceImpl.class);
    @Autowired
    private EntityMapper entityMapper;
    @Resource(name = "ruleEngine")
    private RuleEngine ruleEngine;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    private static final String EXPORT_FILENAME_PRE = "CHECK包数据成果检查结果导出";
    /**
     * 导出列标题与实体成员变量对应Map
     */
    private static final Map<String, Object> EXPORT_COLUMN = new LinkedHashMap<String,Object>();

    @Override
    public String retestErrorData(String[] xmids) {
        String msg="";
        if(xmids!=null && xmids.length>0){
            List<String> xmidList=new ArrayList<String>();
            //过滤出所有项目id
            for(String xmid:xmids){
                if(!xmidList.contains(xmid)){
                    xmidList.add(xmid);
                }
            }
            //查询要重建的项目
            Map<String,Object> parMap = new HashMap<String, Object>();
            parMap.put("xmids",xmidList.toArray());
            List<BdcXmDO> xmList = bdcXmMapper.selectAllBdcXm(parMap);
            //重检
            if(CollectionUtils.isNotEmpty(xmList)){
                msg=ruleEngine.retestTask(xmList,true, Constants.THREAD_PREFIX_RETEST,null,null);
            }else{
                msg="无检测数据";
            }
        }
        return  msg;
    }

    @Override
    public String retestTimesData(String beginTime, String finishTime) {
        String msg="无检测数据";
        if(StringUtils.isNotBlank(beginTime) || StringUtils.isNotBlank(finishTime)){
            //查询要重建的项目
            Map<String,Object> parMap = new HashMap<String, Object>();
            parMap.put("qsrq",beginTime);
            parMap.put("jsrq",finishTime);
            boolean check = true;
            // 查询数据开始条数
            int begin = 0;
            while (check){
                try{
                    parMap.put("begin",begin);
                    parMap.put("end", begin + Constants.MAX_CHECKDATA);
                    List<BdcXmDO> xmList = bdcXmMapper.selectAllBdcXm(parMap);
                    //重检
                    if(CollectionUtils.isNotEmpty(xmList)){
                        msg=ruleEngine.retestTask(xmList,true,Constants.THREAD_PREFIX_TIMES,null,null);
                        begin += Constants.MAX_CHECKDATA;
                    }else{
                        check = false;
                    }
                }catch (Exception e){
                    LOGGER.error("时段检测异常：",e);
                    check = false;
                }
            }
        }
        return  msg;
    }

    @Override
    public String retestRuleData(String beginTime, String finishTime, String gzid,String qxdm) {
        String msg="该时段内不存在检测数据";
        if(StringUtils.isNotBlank(gzid)){
            //查询要重建的项目
            Map<String,Object> parMap = new HashMap<String, Object>();
            parMap.put("qsrq",beginTime);
            parMap.put("jsrq",finishTime);
            parMap.put("qxdm",qxdm);
            boolean check = true;
            // 查询数据开始条数
            int begin = 0;
            while (check){
                try{
                    parMap.put("begin",begin);
                    parMap.put("end", begin + Constants.MAX_CHECKDATA);
                    List<BdcXmDO> xmList = bdcXmMapper.selectAllBdcXm(parMap);
                    //重检
                    if(CollectionUtils.isNotEmpty(xmList)){
                        msg=ruleEngine.retestTask(xmList,true,Constants.THREAD_PREFIX_RULES,gzid,null);
                        begin += Constants.MAX_CHECKDATA;
                    }else{
                        check = false;
                    }
                }catch (Exception e){
                    LOGGER.error("规则检测异常：",e);
                    check = false;
                }
            }
        }
        return  msg;
    }

    @Override
    public List<Map<String, Object>> queryCgsjList(SelectCgsjVo selectCgsjVo) {
        return bdcXmMapper.quertCgsjListByPage(selectCgsjVo);
    }

    @Override
    public void export(List<Map<String, Object>> list, HttpServletResponse response, String jcxx) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fileName;
        if(StringUtils.isNotBlank(jcxx)){
            // 根据规则ID查询规则描述
            Example example = new Example(CheckGzxxDO.class);
            example.createCriteria().andEqualTo("gzid",jcxx);
            List<CheckGzxxDO> bdcGzxx = entityMapper.selectByExample(example);
            String gzms ="";
            if(CollectionUtils.isNotEmpty(bdcGzxx)){
                gzms = bdcGzxx.get(0).getGzms();
            }
            fileName = gzms+ df.format(new Date()) + ".xls";
        }else{
            fileName = EXPORT_FILENAME_PRE +df.format(new Date()) + ".xls";
        }
        initColumn();
        try {
            ExcelUtil.exportExcelStandard(fileName,EXPORT_COLUMN,list,response);
        } catch (WriteException e) {
            LOGGER.error("导出excel报错",e);
        } catch (IOException e) {
            LOGGER.error("导出excel报错",e);
        }
    }
    /**
     * 初始化导出列
     */
    private static void initColumn(){
        if(EXPORT_COLUMN == null || EXPORT_COLUMN.size()==0){
            EXPORT_COLUMN.put("规则描述","GZMS");
            EXPORT_COLUMN.put("项目编号","SLBH");
            EXPORT_COLUMN.put("项目ID","XMID");
            EXPORT_COLUMN.put("不动产单元号","BDCDYH");
            EXPORT_COLUMN.put("限制文号","XZWH");
            EXPORT_COLUMN.put("发现时间","FXSJ");
            EXPORT_COLUMN.put("更新时间","GXSJ");
            EXPORT_COLUMN.put("检查信息","JCXX");
            EXPORT_COLUMN.put("解决方式","JJFS");
            EXPORT_COLUMN.put("解决状态","JJZT");
            EXPORT_COLUMN.put("检查等级","JCDJ");
            EXPORT_COLUMN.put("是否挂起","SFGQ");
            EXPORT_COLUMN.put("挂起原因","GQYY");
        }
    }
}
