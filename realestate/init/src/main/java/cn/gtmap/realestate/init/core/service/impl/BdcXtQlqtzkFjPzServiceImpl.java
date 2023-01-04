package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlqtzkFjQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example.Criteria;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.qo.InitQlqtzkFjDataQO;
import cn.gtmap.realestate.init.core.service.*;
import cn.gtmap.realestate.init.mapper.RunSqlMapper;
import cn.gtmap.realestate.init.service.zsxx.InitBdcqzQlqtzkFjMbService;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 生成默认附记和权利其他状况
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/13.
 * @description
 */
@Service
public class BdcXtQlqtzkFjPzServiceImpl implements BdcXtQlqtzkFjPzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXtQlqtzkFjPzServiceImpl.class);
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private RunSqlMapper runSqlMapper;
    @Autowired
    private BdcQllxService bdcQllxService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private BdcQlrService bdcQlrService;
    @Autowired
    private InitBeanFactory initBeanFactory;
    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @Date 2020/9/2
     * @param xmid
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO>
     * @description 获取BDC_XT_QLQTZK_FJ_PZ信息
     */
    private BdcXtQlqtzkFjPzDO getBdcXtQlqtzkFjPz(String xmid) {
        BdcXtQlqtzkFjPzDO qlqtzkFjPz = null;
        List<BdcXtQlqtzkFjPzDO> bdcXtQlqtzkFjPzList;
        if (StringUtils.isNotBlank(xmid)) {
            BdcXmDO bdcXm = bdcXmService.queryBdcXmByPrimaryKey(xmid);
            if (bdcXm != null && StringUtils.isNotBlank(bdcXm.getDjxl())) {
                Example example = new Example(BdcXtQlqtzkFjPzDO.class);
                Criteria criteria =  example.createCriteria().andEqualTo("djxl", bdcXm.getDjxl());
                // 判断权利类型是否为空
                if (bdcXm.getQllx() != null) {
                    criteria.andEqualTo("qllx", bdcXm.getQllx());
                }else{
                    criteria.andIsNull("qllx");
                }
                // 查询权利其他状况与附记配置
                bdcXtQlqtzkFjPzList = entityMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(bdcXtQlqtzkFjPzList)){
                    qlqtzkFjPz = bdcXtQlqtzkFjPzList.get(0);
                }
            }
        }
        return qlqtzkFjPz;
    }
    @Override
    public Map getQlqtzkFjData(InitQlqtzkFjDataQO initQlqtzkFjDataQO) {
        Map map=new HashMap();
        //服务查询数据
        List<InitBdcqzQlqtzkFjMbService> listServices=initBeanFactory.getZsQlqtzkfjMbServices();
        if(CollectionUtils.isNotEmpty(listServices)){
            for(InitBdcqzQlqtzkFjMbService initBdcqzQlqtzkFjMbService:listServices){
                Map serviceMapData=initBdcqzQlqtzkFjMbService.queryZsQlqtzkFjMbServiceData(initQlqtzkFjDataQO);
                if(MapUtils.isNotEmpty(serviceMapData)){
                    map.putAll(serviceMapData);
                }
            }
        }
        return map;
    }
    @Override
    public String getQlqtzkMessageByZsxx(String xmid,String zsid,List<BdcQlrDO> list) {
        String qlqtzk = "";
        if (StringUtils.isNotBlank(xmid) && StringUtils.isNotBlank(zsid)) {
            qlqtzk = getQlqtzkMessagePublic(buildQlqtzkFjDataQO(xmid,zsid,list));
        }
        return qlqtzk;
    }
    @Override
    public String getQlqtzkMessageByXmid(String xmid) {
        String qlqtzk = "";
        if (StringUtils.isNotBlank(xmid)) {
            qlqtzk = getQlqtzkMessagePublic(buildQlqtzkFjDataQO(xmid,null,null));
        }
        return qlqtzk;
    }

    @Override
    public String appendQlqtzk(String qlqtzk,String xmid,String zsid,List<BdcQlrDO> list){
        StringBuilder qlqtzkBuilder=new StringBuilder();
        if(StringUtils.isNotBlank(qlqtzk)) {
            qlqtzkBuilder.append(qlqtzk);
        }
        List<InitBdcqzQlqtzkFjMbService> listServices=initBeanFactory.getZsQlqtzkfjMbServices();
        if(CollectionUtils.isNotEmpty(listServices)){
            for(InitBdcqzQlqtzkFjMbService initBdcqzQlqtzkFjMbService:listServices){
                String addqlqtzk=initBdcqzQlqtzkFjMbService.appendQlqtzk(buildQlqtzkFjDataQO(xmid,zsid,list));
                if(StringUtils.isNotBlank(addqlqtzk)){
                    if(qlqtzkBuilder.indexOf(addqlqtzk) <0) {
                        qlqtzkBuilder.insert(0, "\n").insert(0, addqlqtzk);
                    }
                }
            }
        }
        return qlqtzkBuilder.toString();

    }
    /**
    * @author chenchunxue 2020/9/3
    * @param initQlqtzkFjDataQO
    * @return java.lang.String
    * @description 获取权利其他状况处理
    */
    private String getQlqtzkMessagePublic(InitQlqtzkFjDataQO initQlqtzkFjDataQO){
        String qlqtzk = "";
        String xmid = initQlqtzkFjDataQO.getXmid();
        if (StringUtils.isNotBlank(xmid)) {
            BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPz = getBdcXtQlqtzkFjPz(xmid);
            // 将模板转成小写
            if(bdcXtQlqtzkFjPz!=null && StringUtils.isNotBlank(bdcXtQlqtzkFjPz.getQlqtzkmb())){
                bdcXtQlqtzkFjPz.setQlqtzkmb(bdcXtQlqtzkFjPz.getQlqtzkmb().toLowerCase());
                //获取转换数据
                Map map=getQlqtzkFjData(initQlqtzkFjDataQO);
                //根据现有数据做模板替换
                qlqtzk=mbParamReplace(bdcXtQlqtzkFjPz.getQlqtzkmb(),map);
                // 权利其他状况
                qlqtzk = replaceMbBySjyAndXmid(qlqtzk,bdcXtQlqtzkFjPz.getQlqtzksjy(), xmid,map);
            }
        }
        return qlqtzk;
    }

    @Override
    public String getFjMessageByZsxx(String xmid,String zsid,List<BdcQlrDO> list) {
        String fj = "";
        if (StringUtils.isNotBlank(xmid)) {
            fj = getFjMessagePublic(buildQlqtzkFjDataQO(xmid,zsid,list));
        }
        return fj;
    }

    @Override
    public String getFjMessageByXmid(String xmid) {
        String fj = "";
        if (StringUtils.isNotBlank(xmid)) {
            fj = getFjMessagePublic(buildQlqtzkFjDataQO(xmid,null,null));
        }
        return fj;
    }
    /**
     * @author chenchunxue 2020/9/3
     * @param initQlqtzkFjDataQO
     * @return java.lang.String
     * @description 获取附记处理
     */
    private String getFjMessagePublic(InitQlqtzkFjDataQO initQlqtzkFjDataQO){
        String fj = "";
        String xmid = initQlqtzkFjDataQO.getXmid();
        if (StringUtils.isNotBlank(xmid)) {
            BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPz = getBdcXtQlqtzkFjPz(xmid);
            // 将模板转成小写
            if(bdcXtQlqtzkFjPz!=null && StringUtils.isNotBlank(bdcXtQlqtzkFjPz.getFjmb())){
                bdcXtQlqtzkFjPz.setFjmb(bdcXtQlqtzkFjPz.getFjmb().toLowerCase());
                //获取转换数据
                Map map=getQlqtzkFjData(initQlqtzkFjDataQO);
                //根据现有数据做模板替换
                fj=mbParamReplace(bdcXtQlqtzkFjPz.getFjmb(),map);
                // 附记
                LOGGER.info("根据模板生成附记内容为：{}", fj);
                fj = replaceMbBySjyAndXmid(fj, bdcXtQlqtzkFjPz.getFjsjy(), xmid, map);
            }
        }
        return fj;
    }
    /**
     * @author chenchunxue 2020/9/2
     * @param xmid
    * @param zsid
    * @param list
     * @return cn.gtmap.realestate.init.core.qo.InitQlqtzkFjDataQO
     * @description InitQlqtzkFjDataQO
     */
    private InitQlqtzkFjDataQO buildQlqtzkFjDataQO(String xmid, String zsid, List<BdcQlrDO> list) {
        InitQlqtzkFjDataQO initQlqtzkFjDataQO = new InitQlqtzkFjDataQO();
        initQlqtzkFjDataQO.setXmid(xmid);
        initQlqtzkFjDataQO.setZsid(zsid);
        initQlqtzkFjDataQO.setBdcQlrDOList(list);
        initQlqtzkFjDataQO.setFbczsfxsczr(initBeanFactory.isFbczSfxsczr());
        return initQlqtzkFjDataQO;
    }

    /**
    * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
    * @Date 2020/9/2
    * @param xmid
    * @param fjmb
    * @return java.lang.String
    * @description 获取附记信息
    */
    private String dealFjByMbAndQlfj(String xmid, String fjmb){
        BdcQl bdcQl=bdcQllxService.queryQllxDO(xmid);
        StringBuilder fj=new StringBuilder();
        //默认不初始化附记的追加模板
        if(!initBeanFactory.isInitFj() && StringUtils.isNotBlank(fjmb)){
            fj.append(fjmb);
        }
        // 设定附记信息
        if (bdcQl != null && StringUtils.isNotBlank(bdcQl.getFj()) && StringUtils.isNotBlank(initBeanFactory.getFjWz())) {
            if(StringUtils.equals(initBeanFactory.getFjWz(),"bottom")){
                fj.insert(0,"\n").insert(0,bdcQl.getFj());
            }else{
                fj.append("\n").append(bdcQl.getFj());
            }
        }
        return fj.toString();
    }

    /**
     * 通过数据源和项目id 更新模板信息
     * @param mb
     * @param sjy
     * @param xmid
     * @return
     */
    private String replaceMbBySjyAndXmid(String mb, String sjy, String xmid,Map paramMap) {
        if(StringUtils.isNotBlank(mb)){
            if(StringUtils.isNotBlank(sjy) && StringUtils.isNotBlank(xmid)){
                String[] sqls = sjy.split("；|;");
                for (int i = 0; i < sqls.length; i++) {
                    if (StringUtils.isNotBlank(sqls[i])) {
                        String czrqlrid="";
                        if (paramMap != null &&paramMap.get("czrqlrid") != null) {
                            czrqlrid =paramMap.get("czrqlrid").toString();
                        }
                        List<HashMap> list = runSql(sqls[i], xmid,czrqlrid);
                        // 若查询数据不为空
                        if (CollectionUtils.isNotEmpty(list)) {
                            for(HashMap map:list){
                                // 对模板中参数进行替换
                                mb = mbParamReplace(mb, map);
                            }
                        }
                    }
                }
            }
            // 将模板未赋值行删除
            mb = mbUnsetRowReplace(mb);
            // 去除最后一个换行符
            if (StringUtils.isNotBlank(mb) && StringUtils.lastIndexOf(mb, CommonConstantUtils.ZF_HH_CHAR) == StringUtils.length(mb) - 1) {
                mb = StringUtils.substring(mb, 0, StringUtils.lastIndexOf(mb, CommonConstantUtils.ZF_HH_CHAR));
            }
        }
        return mb;
    }

    /**
     * 执行SQL查询数据
     * @param sql  查询sql
     * @param xmid 项目id
     * @return 返回查询结果
     */
    private List<HashMap> runSql(String sql, String xmid,String czrqlrid) {
        Map<String, String> map = new HashMap();
        map.put("sql", sql);
        map.put("xmid", xmid);
        map.put("czrqlrid", czrqlrid);
        return runSqlMapper.runSql(map);
    }

    /**
     * 通过map对模板中的值进行替换
     * @param mb  模板
     * @param map 替换模板参数map
     * @return
     */
    private static String mbParamReplace(String mb, Map map) {
        // 获取参数
        if (map != null && StringUtils.isNotBlank(mb)) {
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                String key = object.toString();
                if (StringUtils.isNotBlank(key) && map.get(key) != null) {
                    mb = mb.replaceFirst("(?i)#\\{" + key + "\\}", map.get(key).toString());
                }
            }
        }
        return mb;
    }

    /**
     * 去除未赋值的部分
     *
     * @param mb 模板
     * @return
     */
    private static String mbUnsetRowReplace(String mb) {
        // 添加换行符
        int i = 0;
        String fgf = "\n";
        while (mb.indexOf("#{") > -1) {
            int end = mb.indexOf("#{");
            //后边换行符的位置
            int endHh = mb.indexOf(fgf, end);
            if (end > -1) {
                int begin = mb.substring(0, end).lastIndexOf(fgf);
                if (begin < 0) {
                    begin = 0;
                    endHh += 1;
                }
                if (endHh <= 0) {
                    mb = mb.substring(0, begin);
                } else {
                    mb = mb.substring(0, begin) + mb.substring(endHh);
                }
            }
            i++;
            if (i > 100) {
                break;
            }
        }
        return mb;
    }



    @Override
    public Boolean updateQlqtzkFjByMode(String xmid, String mode) {
        Boolean updateFlag=false;
        List<BdcQlrDO> listQlr=bdcQlrService.listBdcQlrOrderBySxh(xmid,null);
        //清空赋值 更新时走zsid跟权利人匹配的逻辑，查询zjzl字段
        if(CollectionUtils.isNotEmpty(listQlr)){
            for(BdcQlrDO bdcQlrDO:listQlr){
                bdcQlrDO.setBz(null);
            }
        }
        List<BdcZsDO> listZs=bdcZsService.queryBdcZsByXmid(xmid);
        if(CollectionUtils.isNotEmpty(listZs)){
            for(BdcZsDO bdcZsDO:listZs){
                String qlqtzkmb="";
                String fjmb="";
                // 判断更新模式
                if (StringUtils.equals(mode, Constants.XT_QLQTZK_FJ_MODE_ALL) || StringUtils.equals(mode, Constants.XT_QLQTZK_FJ_MODE_QLQTZK)) {
                    //非读取项目表的
                    if(!initBeanFactory.isDqXmQlqtzk()){
                        // 更新权利其他状况
                        qlqtzkmb = getQlqtzkMessageByZsxx(xmid,bdcZsDO.getZsid(),listQlr);
                    }else{
                        qlqtzkmb=bdcZsDO.getQlqtzk();
                    }
                }
                if (StringUtils.equals(mode, Constants.XT_QLQTZK_FJ_MODE_ALL) || StringUtils.equals(mode, Constants.XT_QLQTZK_FJ_MODE_FJ)) {
                    //非初始化生成附记的
                    if(!initBeanFactory.isInitFj()){
                        // 更新证书附记
                        fjmb = getFjMessageByZsxx(xmid,bdcZsDO.getZsid(),listQlr);
                    }
                }
                if (StringUtils.equals(mode, Constants.XT_QLQTZK_FJ_MODE_ALL) || StringUtils.equals(mode, Constants.XT_QLQTZK_FJ_MODE_QLQTZK)){
                    bdcZsDO.setQlqtzk(qlqtzkmb);
                }
                if(StringUtils.equals(mode, Constants.XT_QLQTZK_FJ_MODE_ALL) || StringUtils.equals(mode, Constants.XT_QLQTZK_FJ_MODE_FJ)){
                    //开始附记生成或者不回写附记的或者附记位置是空的才更新
                    if(initBeanFactory.isInitFj() || !initBeanFactory.isHxFj() || StringUtils.isBlank(initBeanFactory.getFjWz())){
                        bdcZsDO.setFj(dealFjByMbAndQlfj(xmid,fjmb));
                    }
                }
                bdcZsService.updateBdcZs(bdcZsDO);
                updateFlag = true;
            }
        }
        return updateFlag;
    }
    /**
     * @param bdcQlqtzkFjQO 权利其他状况和附记操作QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据json 更新项目相关的部分权利其他状况或附记
     */
    @Override
    public void updateQlqtzkFjByQO(BdcQlqtzkFjQO bdcQlqtzkFjQO) {
        List<String> modelList = bdcQlqtzkFjQO.getModeList();
        if (CollectionUtils.isEmpty(modelList)) {
            throw new MissingArgumentException("缺失更新模板类型参数！");
        }
        String json = bdcQlqtzkFjQO.getJsonStr();
        for (String model : modelList) {
            updateQlqtzkOrFj(json, model,bdcQlqtzkFjQO.getPlgx());
        }
    }
    /**
     * @param json 更新字符串
     * @param mode 更新模式
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 单模式更新证书权利其他状况或附记
     */
    private void updateQlqtzkOrFj(String json, String mode, Boolean plgx) {
        if (StringUtils.equals(CommonConstantUtils.XT_QLQTZK_FJ_MODE_QLQTZK, mode)) {
            bdcXmService.updateBdcZsXmBfqlqtzk(json,plgx);
        } else if (StringUtils.equals(CommonConstantUtils.XT_QLQTZK_FJ_MODE_FJ, mode)) {
            bdcQllxService.updateBdcZsQlFj(json);
        }
    }
}
