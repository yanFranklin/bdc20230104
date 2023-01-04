package cn.gtmap.realestate.init.service.zsxx.impl.qlqtzkfjmb;


import cn.gtmap.realestate.common.core.cache.BdcZdCache;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmAndFbDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDkxxDTO;
import cn.gtmap.realestate.common.core.enums.GcjdEnum;
import cn.gtmap.realestate.common.core.qo.building.FwJsydzrzxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.building.ZdJsydLhxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcJsydLhxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcTdcbjyqNytqtsyqFeginService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcDkxxQO;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.mapper.BdcJtcyMapper;
import cn.gtmap.realestate.init.core.qo.InitQlqtzkFjDataQO;
import cn.gtmap.realestate.init.core.service.BdcCshFwkgService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.zsxx.InitBdcqzQlqtzkFjMbService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/7.
 * @description
 */
@Service
public class InitBdcqzQlqtzkFjMbServiceImpl extends InitBdcqzQlqtzkFjMbService {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String GETBZ = "getBz";

    public static final String QYGYR ="其余共有人：";
    @Autowired
    @Lazy
    BdcXmService bdcXmService;
    @Autowired
    BdcZdCache bdcZdCache;
    @Autowired
    BdcJtcyMapper bdcJtcyMapper;
    @Autowired
    BdcCshFwkgService bdcCshFwkgService;

    /**
     * 土地承包经营权服务
     */
    @Autowired
    private BdcTdcbjyqNytqtsyqFeginService nytqtsyqFeginService;

    @Autowired
    BdcJsydLhxxFeignService bdcJsydLhxxFeignService;

    @Autowired
    ZdJsydLhxxFeignService zdJsydLhxxFeignService;

    @Autowired
    EntityMapper entityMapper;

    /**
     * 版本信息
     */
    @Value("${init.dozerVersion:standard}")
    private String version;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  拼接分别持证共有人信息
      */
    @Value("${init.services.dqXmQlqtzk.fbczgyr:false}")
    private boolean dqXmQlqtzkfbczgyr;


    /**
     * 通过服务获取证书权利其他状况和附记模板
     * @param initQlqtzkFjDataQO
     * @return Map
     */
    @Override
    public Map queryZsQlqtzkFjMbServiceData(InitQlqtzkFjDataQO initQlqtzkFjDataQO) {
        Map<String, String> map = new HashMap<>();
        String xmid = initQlqtzkFjDataQO.getXmid();
        String zsid = initQlqtzkFjDataQO.getZsid();
        List<BdcQlrDO> qlrList = initQlqtzkFjDataQO.getBdcQlrDOList();
        if(StringUtils.isNotBlank(xmid) && StringUtils.isNotBlank(zsid) && CollectionUtils.isNotEmpty(qlrList)){
            BdcXmDO xm = bdcXmService.queryBdcXmByPrimaryKey(xmid);
            //持证人
            List<BdcQlrDO> czr=new ArrayList<>();
            //共有人
            List<BdcQlrDO> gyr=new ArrayList<>();
            for(BdcQlrDO bdcQlrDO:qlrList){
                //将转换后的证件种类存到bz
                if(StringUtils.isNotBlank(bdcQlrDO.getBz())){
                    String[] zjzls=bdcQlrDO.getBz().split(" ");
                    StringBuilder stringBuilder=new StringBuilder();
                    for(String str:zjzls){
                        if(NumberUtils.isNumber(str)){
                            String zjzl=bdcZdCache.getFeildValue(BdcZdZjzlDO.class.getSimpleName(),NumberUtils.toInt(str),BdcZdZjzlDO.class);
                            if(StringUtils.isNotBlank(zjzl)){
                                stringBuilder.append(zjzl);
                            }else{
                                stringBuilder.append(str);
                            }
                            stringBuilder.append(" ");
                        }else{
                            stringBuilder.append(bdcQlrDO.getBz());
                            break;
                        }
                    }
                    bdcQlrDO.setBz(stringBuilder.toString());
                } else if (bdcQlrDO.getZjzl() != null) {
                    String zjzl = bdcZdCache.getFeildValue(BdcZdZjzlDO.class.getSimpleName(), bdcQlrDO.getZjzl(), BdcZdZjzlDO.class);
                    if (StringUtils.isNotBlank(zjzl)) {
                        bdcQlrDO.setBz(zjzl);
                    } else {
                        bdcQlrDO.setBz(bdcQlrDO.getZjzl().toString());
                    }
                }
                if (StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                    //相当为持证人
                    if (StringUtils.equals(zsid, bdcQlrDO.getZsid())) {
                        czr.add(bdcQlrDO);
                        //持证人权利人ID
                        map.put("czrqlrid", bdcQlrDO.getQlrid());
                    } else {
                        gyr.add(bdcQlrDO);
                    }
                }
            }
            // 标准版是 分别持证的 附记不放持证人 分别持证的 才放共有人
            // 如果配置FbczSfxsczr = true 则持证人和共有人都显示
            boolean addCzr = true;
            boolean addGyr = true;
            if(!initQlqtzkFjDataQO.getFbczsfxsczr() && xm!=null){
                if(CommonConstantUtils.SF_S_DM.equals(xm.getSqfbcz()) && qlrList.size()>1){
                    // 分别持证的 附记不放持证人
                    addCzr = false;
                }else{
                    // 分别持证的 才放共有人
                    addGyr = false;
                }
            }
            if(CollectionUtils.isNotEmpty(czr) && addCzr){
                map.put("czr", StringToolUtils.resolveBeanToAppendStr(czr, "getQlrmc", " "));
                map.put("czrzjzl", StringToolUtils.resolveBeanToAppendStr(czr, GETBZ, " "));
                map.put("czrzjh", StringToolUtils.resolveBeanToAppendStr(czr, "getZjh", " "));
            }
            if(CollectionUtils.isNotEmpty(gyr) && addGyr){
                map.put("gyr",StringToolUtils.resolveBeanToAppendStr(gyr, "getQlrmc", " "));
                // 如果是按份共有 查询共有人权利比例
                if (CommonConstantUtils.GYFS_AFGY.equals(gyr.get(0).getGyfs())) {
                    map.put("gyrqlbl", StringToolUtils.resolveBeanToAppendStr(gyr, "getQlbl", " "));
                }
                map.put("gyrzjzl", StringToolUtils.resolveBeanToAppendStr(gyr, GETBZ, " "));
                map.put("gyrzjh", StringToolUtils.resolveBeanToAppendStr(gyr, "getZjh", " "));
            }
            // 承包方家庭成员情况 ccx 2020-08-06
            if(CollectionUtils.isNotEmpty(czr) && xm!=null && CommonConstantUtils.QLLX_TDCBNYDSYQ.equals(xm.getQllx())){
                map.putAll(getJtcyxx(xmid,xm.getSqfbcz(),czr));
            }

            //量化信息
            if (xm != null &&
                    (BdcdyhToolUtils.checkXnLhbdcdyh(xm.getBdcdyh()) || BdcdyhToolUtils.checkZdBdcdyh(xm.getBdcdyh()))) {
                map.put("lhxx", this.getLhxx(xm.getGzlslid()));
            }

            if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(version) && xm != null && CommonConstantUtils.QLLX_TDCBNYDSYQ.equals(xm.getQllx())) {
                // 常州农用地土地承包经营权证书附记需要展示地块列表
                map.put("dklb", this.getDklb(initQlqtzkFjDataQO.getXmid()));
            }

            LOGGER.info("获取证书权利其他状况和附记模板权利类型：{}",xm.getQllx());
            if(CommonConstantUtils.SYSTEM_VERSION_CZ.equals(version) && xm != null && CommonConstantUtils.QLLX_NCBDCQQ.equals(xm.getQllx())) {
                // 常州农村不动产确权登记证书附记需要家庭成员信息
                map.put("bdcJtcyxx", "");
            }

            // 土地使用权份额
            if(CommonConstantUtils.SYSTEM_VERSION_YC.equals(version) && xm != null && ArrayUtils.contains(CommonConstantUtils.QLLX_FDCQ, xm.getQllx())){
                map.put("tdsyqfe", this.getTdsyqfe(xm.getGzlslid()));
            }
        }
        return map;
    }

    @Override
    public String appendQlqtzk(InitQlqtzkFjDataQO initQlqtzkFjDataQO){
        StringBuilder qlqtzkBuilder =new StringBuilder();
        if(dqXmQlqtzkfbczgyr){
            //其余共有人
            if(StringUtils.isNotBlank(initQlqtzkFjDataQO.getZsid()) &&CollectionUtils.isNotEmpty(initQlqtzkFjDataQO.getBdcQlrDOList())) {
                //共有人
                List<BdcQlrDO> gyr=new ArrayList<>();
                for (BdcQlrDO bdcQlrDO : initQlqtzkFjDataQO.getBdcQlrDOList()) {
                    if (StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                        //相当为持证人
                        if (!StringUtils.equals(initQlqtzkFjDataQO.getZsid(), bdcQlrDO.getZsid())) {
                            gyr.add(bdcQlrDO);
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(gyr)) {
                    String gyrxx = StringToolUtils.resolveBeanToAppendStr(gyr, "getQlrmc", CommonConstantUtils.ZF_YW_DH);
                    qlqtzkBuilder.append(QYGYR).append(gyrxx);
                }
            }
        }
        return qlqtzkBuilder.toString();

    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  xmid 项目ID
     * @return {String} 地块信息字符串
     * @description 查询项目对应的地块列表
     */
    private String getDklb(String xmid) {
        if(StringUtils.isBlank(xmid)) {
            return null;
        }

        // 不管预览时候还是生成证书时候，都还没有实际证书数据，所有不好拿zsid关联查询地块
        // 用xmid找到实例化开关表中和当前证书序号一致的项目ID
        BdcDkxxQO bdcDkxxQO = new BdcDkxxQO();
        bdcDkxxQO.setXmid(xmid);
        List<BdcDkxxDTO> dkxxList = nytqtsyqFeginService.queryTdcbjyqDkxxBeforeZsInit(bdcDkxxQO);
        if(CollectionUtils.isEmpty(dkxxList)) {
            return null;
        }

        StringBuilder dkxx = new StringBuilder();
        dkxx.append("地块名称 不动产单元号 合同面积 实测面积 四至 ");
        for(BdcDkxxDTO dkxxDTO : dkxxList) {
            dkxx.append("东：").append(dkxxDTO.getZdszd()).append(" ");
            dkxx.append(dkxxDTO.getZl()).append(" ");
            dkxx.append(dkxxDTO.getBdcdyh()).append(" ");
            dkxx.append(dkxxDTO.getCbmj()).append("亩 ");
            dkxx.append(dkxxDTO.getScmj()).append("亩 ");
            dkxx.append("西：").append(dkxxDTO.getZdszx()).append(" ");
            dkxx.append("南：").append(dkxxDTO.getZdszn()).append(" ");
            dkxx.append("北：").append(dkxxDTO.getZdszb()).append(" ");
        }
        dkxx.append(";;");
        return dkxx.toString();
    }

    /**
     * 获取家庭成员信息
     * @param xmid
     * @return
     */
    private Map getJtcyxx(String xmid,Integer sqfbcz,List<BdcQlrDO> czr) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("xmid", xmid);
        param.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
        // 分别持证的 按照持证人查询家庭成员
        if (CommonConstantUtils.SF_S_DM.equals(sqfbcz)) {
            param.put("qlridList", czr.stream().map(BdcQlrDO::getQlrid).collect(Collectors.toList()));
        }
        List<BdcJtcyDO> bdcJtcyDOList = bdcJtcyMapper.queryJtcy(param);
        if (CollectionUtils.isNotEmpty(bdcJtcyDOList)) {
            for (BdcJtcyDO jtcyDO : bdcJtcyDOList) {
                jtcyDO.setYhzgx(bdcZdCache.getFeildValue(BdcZdYhzgxDO.class.getSimpleName(), jtcyDO.getYhzgx(), BdcZdYhzgxDO.class));
            }
            result.put("jtcyqk", "    " + StringToolUtils.resolveBeanToAppendStr(bdcJtcyDOList, new String[]{"getJtcymc", "getYhzgx", GETBZ}, "\r\n    ", "  "));
        }
        return result;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 量化信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据xmid组织量化信息
     */
    private String getLhxx(String gzlslid) {
        StringBuilder lhxxBuilder = new StringBuilder();
        List<FwJsydzrzxxDO> fwJsydzrzxxDOList = bdcJsydLhxxFeignService.listJsydLhxx(gzlslid);
        if (CollectionUtils.isNotEmpty(fwJsydzrzxxDOList)) {
            Map<String, List<FwJsydzrzxxDO>> gcjdFwJsydzrzxx = fwJsydzrzxxDOList.stream().collect(Collectors.groupingBy(FwJsydzrzxxDO::getGcjd,LinkedHashMap::new,Collectors.toList()));
            if (MapUtils.isNotEmpty(gcjdFwJsydzrzxx)) {
                lhxxBuilder.append("经量化测算，该项目");
                int i = 0;
                for (Map.Entry<String, List<FwJsydzrzxxDO>> entry : gcjdFwJsydzrzxx.entrySet()) {
                    if (i > 0) {
                        lhxxBuilder.append("及").append(GcjdEnum.getMcByDm(entry.getKey())).append("房屋");
                    } else {
                        lhxxBuilder.append(GcjdEnum.getMcByDm(entry.getKey())).append("房屋");
                    }
                    i++;
                    List<FwJsydzrzxxDO> fwJsydzrzxxDOS = entry.getValue();
                    //自然幢号
                    lhxxBuilder.append("（").append(StringToolUtils.resolveBeanToAppendStr(fwJsydzrzxxDOS, "getDh", CommonConstantUtils.ZF_YW_JH)).append("）");
                }
            }
            //百分数格式
            DecimalFormat decimalFormat = new DecimalFormat(CommonConstantUtils.FORMAT_BFS);
            //已登记比例
            Double ghmjjbl = fwJsydzrzxxDOList.stream().filter(fwJsydzrzxx -> null!=fwJsydzrzxx.getBzghmjbl()).map(t -> new BigDecimal(String.valueOf(t.getBzghmjbl())))
                    .reduce((m,n)->m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
            if (ghmjjbl > 0) {
                lhxxBuilder.append("占总建筑规模的比例为：").append(decimalFormat.format(ghmjjbl));
            }

            // 获取未量化剩余的逻辑幢
            FwJsydzrzxxQO fwJsydzrzxxQO = new FwJsydzrzxxQO();
            fwJsydzrzxxQO.setLszd(fwJsydzrzxxDOList.get(0).getLszd());
            List<FwJsydzrzxxDO> allFwJsydzrzxxList = zdJsydLhxxFeignService.listFwJsydzrzxx(fwJsydzrzxxQO);
            List<String> fwJsydzrzxxIndexList = fwJsydzrzxxDOList.stream().map(FwJsydzrzxxDO::getFwJsydzrzxxIndex).collect(Collectors.toList());
            allFwJsydzrzxxList = allFwJsydzrzxxList.stream().filter(t-> !(fwJsydzrzxxIndexList.contains(t.getFwJsydzrzxxIndex()))).collect(Collectors.toList());
            //剩余比例
            Double sybl = allFwJsydzrzxxList.stream().filter(t-> null != t.getBzghmjbl()).map(t -> new BigDecimal(String.valueOf(t.getBzghmjbl())))
                    .reduce((m,n)->m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
            if (sybl > 0) {
                lhxxBuilder.append("，宗地核减后的量化剩余面积占比：").append(decimalFormat.format(sybl));
            }
        }
         return lhxxBuilder.toString();
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  qlrList 权利人集合
     * @return {String} 不动产确权登记家庭成员信息
     * @description 组织不动产确权登记家庭成员信息
     */
    private String getBdcJtcyxx(List<BdcQlrDO> qlrList) {
        if (CollectionUtils.isEmpty(qlrList)) {
            return null;
        }
        List<BdcQlrDO> bdcQlrList = qlrList.stream().filter(bdcQlrDO -> StringUtils.isNotBlank(bdcQlrDO.getQlrlb()) &&
                CommonConstantUtils.QLRLB_QLR.equals(bdcQlrDO.getQlrlb())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(bdcQlrList)){
            return null;
        }
        List<BdcJtcyDO> jtcyList = new ArrayList<>();

        for (BdcQlrDO bdcQlrDO : bdcQlrList) {
            List<BdcJtcyDO> bdcJtcyDOS = bdcJtcyMapper.queryLastBbhJtcyByHzZJH(bdcQlrDO.getZjh());
            if(CollectionUtils.isNotEmpty(bdcJtcyDOS)){
                jtcyList.addAll(bdcJtcyDOS);
            }
        }
        LOGGER.info("证书附记权利人信息：{}", JSONObject.toJSONString(jtcyList));

        StringBuilder bdcJtcyxx = new StringBuilder();
        bdcJtcyxx.append(String.format("%-4s", "姓名")).append(" ");
        bdcJtcyxx.append(String.format("%-2s", "性别")).append(" ");
        bdcJtcyxx.append(String.format("%-15s", "与户主关系")).append(" ");
        bdcJtcyxx.append("证件号 \n");

        for (BdcJtcyDO bdcJtcyDO : jtcyList) {
            bdcJtcyxx.append(String.format("%-4s", bdcJtcyDO.getJtcymc())).append(" ");
            bdcJtcyxx.append(String.format("%-2s", bdcZdCache.getFeildValue(BdcZdXbDO.class.getSimpleName(), bdcJtcyDO.getXb(), BdcZdXbDO.class))).append(" ");
            bdcJtcyxx.append(String.format("%-15s", bdcZdCache.getFeildValue(BdcZdYhzgxDO.class.getSimpleName(), bdcJtcyDO.getYhzgx(), BdcZdYhzgxDO.class))).append(" ");
            bdcJtcyxx.append(bdcJtcyDO.getJtcyzjh()).append(" \n");
        }
        bdcJtcyxx.append(" ");
        LOGGER.info("证书附记家庭成员信息：{}",bdcJtcyxx.toString());
        return bdcJtcyxx.toString();
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return 土地使用权份额
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description
     */
    private String getTdsyqfe(String gzlslid){
        if(StringUtils.isBlank(gzlslid)){
            return null;
        }
        Double tdsyqfe = null;
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmAndFbDTO> bdcXmAndFbDTOList = this.bdcXmService.listXmAndFb(bdcXmQO);
        if(CollectionUtils.isNotEmpty(bdcXmAndFbDTOList)){
            tdsyqfe= bdcXmAndFbDTOList.stream().filter(t-> Objects.nonNull(t.getTdsyqfe())).mapToDouble(BdcXmAndFbDTO::getTdsyqfe).sum();

        }
        return String.valueOf(tdsyqfe);
    }

}
