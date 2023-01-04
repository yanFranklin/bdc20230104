package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.*;

/**
 * 历史关系限制权力模型
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public class LsgxXzqlModelDTO {

    /** 产权关系模型*/
    private List<LsgxModelDTO> allCqLsgxModel;

    /** 抵押关系模型*/
    private List<LsgxModelDTO> dyLsgxModel;
    /** 查封关系模型*/
    private List<LsgxModelDTO> cfLsgxModel;

    /** 预查封关系模型*/
    private List<LsgxModelDTO> ycfLsgxModel;

    /** 异议关系模型*/
    private List<LsgxModelDTO> yyLsgxModel;
    /** 预告关系模型*/
    private List<LsgxModelDTO> ygLsgxModel;
    /** 地役关系模型*/
    private List<LsgxModelDTO> dyiLsgxModel;
    /**预告（不含预抵押）**/
    private List<LsgxModelDTO> ygbhydyLsgxModel;
    /**预告（只有预抵押）**/
    private List<LsgxModelDTO> ydyLsgxModel;
    /**锁定（包括单元锁定、证书锁定）**/
    private List<LsgxModelDTO> sdLsgxModel;
    /**居住权**/
    private List<LsgxModelDTO> jzLsgxModel;


    private Map<String,List<LsgxModelDTO>> modelMap;


    public List<LsgxModelDTO> getAllCqLsgxModel() {
        if(allCqLsgxModel == null){
            allCqLsgxModel = new ArrayList<>();
        }
        return allCqLsgxModel;
    }

    public void setAllCqLsgxModel(List<LsgxModelDTO> allCqLsgxModel) {
        this.allCqLsgxModel = allCqLsgxModel;
    }

    public List<LsgxModelDTO> getDyLsgxModel() {
        if(dyLsgxModel==null){
            dyLsgxModel=new ArrayList<>();
        }
        return dyLsgxModel;
    }

    public void setDyLsgxModel(List<LsgxModelDTO> dyLsgxModel) {
        this.dyLsgxModel = dyLsgxModel;
    }


    public List<LsgxModelDTO> getJzLsgxModel() {
        if(jzLsgxModel==null){
            jzLsgxModel=new ArrayList<>();
        }
        return jzLsgxModel;
    }

    public void setJzLsgxModel(List<LsgxModelDTO> jzLsgxModel) {
        this.jzLsgxModel = jzLsgxModel;
    }



    public List<LsgxModelDTO> getCfLsgxModel() {
        if(cfLsgxModel==null){
            cfLsgxModel=new ArrayList<>();
        }
        return cfLsgxModel;
    }

    public void setCfLsgxModel(List<LsgxModelDTO> cfLsgxModel) {
        this.cfLsgxModel = cfLsgxModel;
    }

    public List<LsgxModelDTO> getYcfLsgxModel() {
        if(ycfLsgxModel==null){
            ycfLsgxModel=new ArrayList<>();
        }
        return ycfLsgxModel;
    }

    public void setYcfLsgxModel(List<LsgxModelDTO> ycfLsgxModel) {
        this.ycfLsgxModel = ycfLsgxModel;
    }

    public List<LsgxModelDTO> getYyLsgxModel() {
        if(yyLsgxModel==null){
            yyLsgxModel=new ArrayList<>();
        }
        return yyLsgxModel;
    }

    public void setYyLsgxModel(List<LsgxModelDTO> yyLsgxModel) {
        this.yyLsgxModel = yyLsgxModel;
    }

    public List<LsgxModelDTO> getYgLsgxModel() {
        if(ygLsgxModel==null){
            ygLsgxModel=new ArrayList<>();
        }
        return ygLsgxModel;
    }

    public void setYgLsgxModel(List<LsgxModelDTO> ygLsgxModel) {
        this.ygLsgxModel = ygLsgxModel;
    }

    public List<LsgxModelDTO> getDyiLsgxModel() {
        if(dyiLsgxModel==null){
            dyiLsgxModel=new ArrayList<>();
        }
        return dyiLsgxModel;
    }

    public void setDyiLsgxModel(List<LsgxModelDTO> dyiLsgxModel) {
        this.dyiLsgxModel = dyiLsgxModel;
    }


    public List<LsgxModelDTO> getYgbhydyLsgxModel() {
        if(ygbhydyLsgxModel == null){
            ygbhydyLsgxModel = new ArrayList<>();
        }
        return ygbhydyLsgxModel;
    }

    public void setYgbhydyLsgxModel(List<LsgxModelDTO> ygbhydyLsgxModel) {
        this.ygbhydyLsgxModel = ygbhydyLsgxModel;
    }

    public List<LsgxModelDTO> getYdyLsgxModel() {
        if(ydyLsgxModel == null){
            ydyLsgxModel = new ArrayList<>();
        }
        return ydyLsgxModel;
    }

    public void setYdyLsgxModel(List<LsgxModelDTO> ydyLsgxModel) {
        this.ydyLsgxModel = ydyLsgxModel;
    }

    public List<LsgxModelDTO> getSdLsgxModel() {
        if(sdLsgxModel == null){
            sdLsgxModel = new ArrayList<>();
        }
        return sdLsgxModel;
    }

    public void setSdLsgxModel(List<LsgxModelDTO> sdLsgxModel) {
        this.sdLsgxModel = sdLsgxModel;
    }

    public List<LsgxModelDTO> getModelByQllx(String qllx){
        if(MapUtils.isEmpty(modelMap)){
            initModelMap();
        }
        return modelMap.get(qllx);
    }

    public void initModelMap(){
        modelMap = new HashMap<>();
        modelMap.put(CommonConstantUtils.XZQL_MODEL_KEY_CF,getCfLsgxModel());
        modelMap.put(CommonConstantUtils.XZQL_MODEL_KEY_DY,getDyLsgxModel());
        modelMap.put(CommonConstantUtils.XZQL_MODEL_KEY_YY,getYyLsgxModel());
        modelMap.put(CommonConstantUtils.XZQL_MODEL_KEY_YG,getYgLsgxModel());
        modelMap.put(CommonConstantUtils.XZQL_MODEL_KEY_DYI,getDyiLsgxModel());
        modelMap.put(CommonConstantUtils.XZQL_MODEL_KEY_JZQ,getJzLsgxModel());
    }

    /**
     * 处理详细信息展示字段
     * @return LsgxXzqlModelDTO
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public LsgxXzqlModelDTO resolveLsgxXzqlXsxx(Map<String, List<Map>> map) {
        // 抵押
        List<Map> listQsztZd = map.get("qszt");
        List<Map> listDjlxZd = map.get("djlx");

        if (CollectionUtils.isNotEmpty(this.getDyLsgxModel())) {
            for(LsgxModelDTO dyaqLsgx : this.getDyLsgxModel()) {
                String xsxx =  this.getQsztMc(listQsztZd,dyaqLsgx) + "_" + this.getQlrmc(dyaqLsgx) + "_" + this.getDjsj(dyaqLsgx);
                dyaqLsgx.setXsxx(xsxx);
            }
        }
        // 居住权
        if (CollectionUtils.isNotEmpty(this.getJzLsgxModel())) {
            for(LsgxModelDTO jzqLsgx : this.getJzLsgxModel()) {
                jzqLsgx.setXsxx(this.getCommonXsxx(listQsztZd,listDjlxZd,jzqLsgx));
            }
        }

        // 查封
        if (CollectionUtils.isNotEmpty(this.getCfLsgxModel())) {
            for(LsgxModelDTO cfLsgx : this.getCfLsgxModel()) {
                cfLsgx.setXsxx(this.getQsztMc(listQsztZd,cfLsgx) + "_" + this.getCfwh(cfLsgx) + "_" + this.getCfjg(cfLsgx) + "_" + this.getDjsj(cfLsgx));
            }
        }

        // 预查封
        if (CollectionUtils.isNotEmpty(this.getYcfLsgxModel())) {
            for(LsgxModelDTO cfLsgx : this.getYcfLsgxModel()) {
                cfLsgx.setXsxx(this.getQsztMc(listQsztZd,cfLsgx) + "_" + this.getCfwh(cfLsgx) + "_" + this.getCfjg(cfLsgx) + "_" + this.getDjsj(cfLsgx));
            }
        }

        // 地役
        if (CollectionUtils.isNotEmpty(this.getDyiLsgxModel())) {
            for(LsgxModelDTO dyiLsgx : this.getDyiLsgxModel()) {
                dyiLsgx.setXsxx(this.getCommonXsxx(listQsztZd,listDjlxZd,dyiLsgx));
            }
        }

        // 预告
        if (CollectionUtils.isNotEmpty(this.getYgLsgxModel())) {
            List<LsgxModelDTO> listYg = new ArrayList<>();
            List<LsgxModelDTO> listYdy = new ArrayList<>();
            for(LsgxModelDTO ygLsgx : this.getYgLsgxModel()) {
                if(null == ygLsgx || null == ygLsgx.getBdcQl()) {
                    continue;
                }
                Integer ygdjzl = ((BdcYgDO) ygLsgx.getBdcQl()).getYgdjzl();
                ygLsgx.setXsxx(this.getQsztMc(listQsztZd,ygLsgx) + "_" + this.getQlrmc(ygLsgx) + "_" + this.getDjsj(ygLsgx));
                if(Arrays.asList(CommonConstantUtils.YG_YGDJZL_ARR).contains(ygdjzl)) {
                    // 预告
                    listYg.add(ygLsgx);
                } else {
                    // 预抵押
                    listYdy.add(ygLsgx);
                }
            }
            this.setYdyLsgxModel(listYdy);
            this.setYgLsgxModel(listYg);
        }

        // 异议
        if (CollectionUtils.isNotEmpty(this.getYyLsgxModel())) {
            for(LsgxModelDTO yyLsgx : this.getYyLsgxModel()) {
                String xsxx =  this.getQsztMc(listQsztZd,yyLsgx) + "_" + this.getQlrmc(yyLsgx) +  "_" + this.getDjsj(yyLsgx);
                yyLsgx.setXsxx(xsxx);
            }
        }

        // 盐城产权的数据
        if (CollectionUtils.isNotEmpty(this.getAllCqLsgxModel())) {
            for(LsgxModelDTO lsgxModelDTO : this.getAllCqLsgxModel()) {
                lsgxModelDTO.setXsxx(this.getCommonXsxx(listQsztZd,listDjlxZd,lsgxModelDTO));
            }
        }

        return this;
    }

    /**
     * 处理锁定信息内容
     * @return LsgxXzqlModelDTO
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public LsgxXzqlModelDTO resolveSdxx(List<BdcDysdDO> dysdDOList, List<BdcZssdDO> zssdDOList,String cqid) {
        if(CollectionUtils.isNotEmpty(dysdDOList)) {
            for(BdcDysdDO dysdDO : dysdDOList) {
                LsgxModelDTO lsgxModelDTO = new LsgxModelDTO();
                lsgxModelDTO.setXsxx(this.getSdztMc(dysdDO.getSdzt()) + "_" + (StringUtils.isNotBlank(dysdDO.getSdr())?dysdDO.getSdr():"无") + "_" + this.getSdsj(dysdDO.getSdsj()));
                lsgxModelDTO.setId(dysdDO.getDysdid());
                lsgxModelDTO.setQszt(dysdDO.getSdzt());
                if(StringUtils.isNotBlank(cqid)){
                    lsgxModelDTO.setCqid(cqid);
                }
                this.getSdLsgxModel().add(lsgxModelDTO);
            }
        }

        if(CollectionUtils.isNotEmpty(zssdDOList)) {
            for(BdcZssdDO zssdDO : zssdDOList) {
                LsgxModelDTO lsgxModelDTO = new LsgxModelDTO();
                lsgxModelDTO.setXsxx(this.getSdztMc(zssdDO.getSdzt()) + "_" + (StringUtils.isNotBlank(zssdDO.getSdr())?zssdDO.getSdr():"无") + "_" + this.getSdsj(zssdDO.getSdsj()));
                lsgxModelDTO.setId(zssdDO.getZssdid());
                lsgxModelDTO.setQszt(zssdDO.getSdzt());
                if(StringUtils.isNotBlank(cqid)){
                    lsgxModelDTO.setCqid(cqid);
                }
                this.getSdLsgxModel().add(lsgxModelDTO);
            }
        }
        // 单元锁定作为 每个限制权力的锁定 不能去重 去重后只会留下最后一个限制权力的锁定
       /* List<LsgxModelDTO> sdLsgxModelDTO = this.getSdLsgxModel().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))), ArrayList::new));
        this.setSdLsgxModel(sdLsgxModelDTO);*/
        return this;
    }

    /**
     * 公共详细信息处理
     * @param lsgxModelDTO 历史关系信息
     * @return 详细信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getCommonXsxx(List<Map> qsztZdlist,List<Map> djlxZdlist,LsgxModelDTO lsgxModelDTO) {
        return this.getQsztMc(qsztZdlist,lsgxModelDTO) + "_" + this.getQlrmc(lsgxModelDTO) + "_" + getDjlxMc(djlxZdlist,lsgxModelDTO)+ "_" + this.getDjsj(lsgxModelDTO);
    }

    /**
     * 处理登记时间
     * @param lsgx 历史关系信息
     * @return 登记时间
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getDjsj(LsgxModelDTO lsgx) {
        if(null == lsgx || null == lsgx.getBdcXmDO() || null == lsgx.getBdcXmDO().getDjsj()) {
            return "无";
        }
        return DateFormatUtils.format(lsgx.getBdcXmDO().getDjsj(), "yyyy-MM-dd");
    }

    /**
     * 获取权利人
     * @param lsgx 历史关系信息
     * @return 权利人名称
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getQlrmc(LsgxModelDTO lsgx) {
        if(null == lsgx || null == lsgx.getBdcXmDO() || StringUtils.isBlank(lsgx.getBdcXmDO().getQlr())) {
            return "无";
        }
        return lsgx.getBdcXmDO().getQlr();
    }

    /**
     * 获取权属状态汉字
     * @param lsgx 历史关系信息
     * @return 权属状态汉字
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getQsztMc(List<Map> list,LsgxModelDTO lsgx) {
        if(null == lsgx || null == lsgx.getBdcXmDO() || null == lsgx.getBdcXmDO().getQszt()) {
            return "无";
        }

        return StringToolUtils.convertBeanPropertyValueOfZd(lsgx.getBdcXmDO().getQszt(), list);
    }

    /**
     * 获取权属状态汉字
     * @param lsgx 历史关系信息
     * @return 权属状态汉字
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getDjlxMc(List<Map> list,LsgxModelDTO lsgx) {
        if(null == lsgx || null == lsgx.getBdcXmDO() || null == lsgx.getBdcXmDO().getDjlx()) {
            return "无";
        }

        return StringToolUtils.convertBeanPropertyValueOfZd(lsgx.getBdcXmDO().getDjlx(), list);

    }

    /**
     * 获取锁定状态汉字
     * @param sdzt 锁定状态
     * @return 锁定状态汉字
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getSdztMc(Integer sdzt) {
        if(null == sdzt) {
            return "无";
        }

        // 这里锁定状态保持和权属状态一致展示
        switch (sdzt.intValue()) {
            case 0: return "正常";
            case 1: return "锁定";
        }
        return "";
    }

    /**
     * 处理锁定时间
     * @param sdsj 锁定时间
     * @return 锁定时间格式化字符串
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getSdsj(Date sdsj) {
        if(null == sdsj) {
            return "无";
        }
        return DateFormatUtils.format(sdsj, "yyyy-MM-dd");
    }

    /**
     * 获取查封文号
     * @param cfLsgx 历史关系信息
     * @return 查封文号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getCfwh(LsgxModelDTO cfLsgx) {
        if(null == cfLsgx || null == cfLsgx.getBdcQl()) {
            return "无";
        }

        BdcCfDO bdcCfDO = (BdcCfDO) cfLsgx.getBdcQl();
        return StringUtils.isNotBlank(bdcCfDO.getCfwh())?bdcCfDO.getCfwh():"无";
    }


    /**
     * 获取查封机构
     * @param cfLsgx 历史关系信息
     * @return 查封机构
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getCfjg(LsgxModelDTO cfLsgx) {
        if(null == cfLsgx || null == cfLsgx.getBdcQl()) {
            return "无";
        }

        BdcCfDO bdcCfDO = (BdcCfDO) cfLsgx.getBdcQl();
        return StringUtils.isNotBlank(bdcCfDO.getCfjg())?bdcCfDO.getCfjg():"无";
    }
}
