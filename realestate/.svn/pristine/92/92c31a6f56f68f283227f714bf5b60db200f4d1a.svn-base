package cn.gtmap.realestate.init.core.qo;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.DsfSlxxDTO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.GzwDcbResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.GzwFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.util.CommonUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化服务的参数结构
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/5.
 * @description
 */
@ApiModel(value = "InitServiceQO", description = "初始化服务的参数结构")
public class InitServiceQO {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitServiceQO.class);
    /**
     * 楼盘表服务
     */
    @JSONField(serialize = false)
    @JsonIgnore
    private BdcdyFeignService bdcdyFeignService;
    @JSONField(serialize = false)
    @JsonIgnore
    private GzwFeignService gzwFeignService;
    @JSONField(serialize = false)
    @JsonIgnore
    private DjxxFeignService djxxFeignService;
    /**
     * 版本号
     */
    private String version;

    /**
     * 构造函数
     * @param bdcdyFeignService
     * @param
     * @param gzwFeignService
     * @param djxxFeignService
     */
    public InitServiceQO(BdcdyFeignService bdcdyFeignService, GzwFeignService gzwFeignService,DjxxFeignService djxxFeignService,String version){
        this.bdcdyFeignService=bdcdyFeignService;
        this.gzwFeignService=gzwFeignService;
        this.djxxFeignService=djxxFeignService;
        this.version = version;
    }

    /**
     * 构造函数
     */
    public InitServiceQO(){
        // Do nothing
    }

    @ApiModelProperty(value = "存储项目相关参数")
    private BdcXmDO bdcXm;

    @ApiModelProperty(value = "存储项目附表相关参数")
    private BdcXmFbDO bdcXmFb;

    @ApiModelProperty(value = "存储项目历史关系相关参数")
    private List<BdcXmLsgxDO> bdcXmLsgxList;

    @ApiModelProperty(value = "存储初始化逻辑控制相关参数")
    private BdcCshFwkgSlDO bdcCshFwkgSl;

    @ApiModelProperty(value = "项目ID")
    @JSONField(serialize = false)
    @JsonIgnore
    private String xmid;

    @ApiModelProperty(value = "原项目ID")
    @JSONField(serialize = false)
    @JsonIgnore
    private String yxmid;

    @ApiModelProperty(value = "所有项目的原项目ID集合")
    @JSONField(serialize = false)
    @JsonIgnore
    private List<String> yxmids;

    @ApiModelProperty(value = "不动产单元号")
    @JSONField(serialize = false)
    @JsonIgnore
    private String bdcdyh;

    @ApiModelProperty(value = "不动产单元号唯一编号")
    @JSONField(serialize = false)
    @JsonIgnore
    private String bdcdywybh;

    @ApiModelProperty(value = "权籍权利人数据")
    @JSONField(serialize = false)
    @JsonIgnore
    private Object bdcdyDTO;

    @ApiModelProperty(value = "存储初始化的不动产权利人信息")
    private List<BdcQlrDO> bdcQlrList;

    @ApiModelProperty(value = "存储初始化的不动产义务人信息")
    private List<BdcQlrDO> bdcYwrList;

    @ApiModelProperty(value = "存储初始化的不动产代理人信息")
    private List<BdcDlrDO> bdcDlrDOList;

    @ApiModelProperty(value = "第三权利人信息")
    private List<BdcDsQlrDO> bdcDsQlrDOList;

    @ApiModelProperty(value = "存储地籍信息")
    @JSONField(serialize = false)
    @JsonIgnore
    private DjxxResponseDTO djxxResponseDTO;

    @ApiModelProperty(value = "存储初始化后的所有业务信息")
    @JSONField(serialize = false)
    @JsonIgnore
    private Map<String, InitServiceDTO> serviceDataMap;


    @ApiModelProperty(value = "存储原项目信息(包含外联)")
    @JSONField(serialize = false)
    @JsonIgnore
    private Map<String, BdcXmDO> ybdcxmDataMap;

    @ApiModelProperty(value = "存储原权利信息(包含外联)")
    @JSONField(serialize = false)
    @JsonIgnore
    private Map<String, BdcQl> yqlDataMap;

    @ApiModelProperty(value = "存储是否要初始化登记簿信息")
    private boolean sfCshDjb;

    @ApiModelProperty(value = "房屋户室信息")
    @JSONField(serialize = false)
    @JsonIgnore
    private BdcdyResponseDTO bdcdyResponseDTO;

    @ApiModelProperty(value = "存储权籍构筑物信息")
    @JSONField(serialize = false)
    @JsonIgnore
    private GzwDcbResponseDTO gzwDcbResponseDTO;

    @ApiModelProperty(value = "是否是重新抓取楼盘表数据")
    private boolean sfzqlpbsj;

    @ApiModelProperty(value = "是否是对照部分楼盘表数据")
    private boolean sfdzbflpbsj;

    @ApiModelProperty(value = "是否查询证书数量")
    private boolean zssl;

    @ApiModelProperty(value = "是否证书预览")
    private boolean zsyl;

    @ApiModelProperty(value = "是否数据补录")
    private boolean sjbl;


    @ApiModelProperty(value = "信息补录流程创建")
    private boolean xxblLccj;

    @ApiModelProperty(value = "存储配置表的数据")
    @JSONField(serialize = false)
    @JsonIgnore
    private Map<String, Map<String, Object>> lcpzMap;

    @ApiModelProperty(value = "不动产受理项目")
    @JSONField(serialize = false)
    @JsonIgnore
    private BdcSlXmDO bdcSlXmDO;

    @ApiModelProperty(value = "第三方受理")
    @JSONField(serialize = false)
    @JsonIgnore
    private DsfSlxxDTO dsfSlxxDTO;

    @ApiModelProperty(value = "受理权利信息")
    @JSONField(serialize = false)
    @JsonIgnore
    private BdcSlQl bdcSlQl;

    @ApiModelProperty(value = "更正信息")
    @JSONField(serialize = false)
    @JsonIgnore
    private BdcGzdjDO bdcGzdjDO;

    @ApiModelProperty(value = "不动产证书模板")
    @JSONField(serialize = false)
    @JsonIgnore
    private BdcZsDO bdcZsMbDO;

    @ApiModelProperty(value = "存储已存在项目的开关实例数据(增量添加时处理)")
    @JSONField(serialize = false)
    @JsonIgnore
    private Map<String, List<BdcCshFwkgSlDO>> cshFwkgSlDataMap;

    @ApiModelProperty(value = "是否为对比权籍")
    @JSONField(serialize = false)
    @JsonIgnore
    private boolean sfdbqj;

    @ApiModelProperty(value = "权籍管理代码")
    @JSONField(serialize = false)
    @JsonIgnore
    private String qjgldm;

    @ApiModelProperty(value = "是否页面点击同步权籍数据")
    private boolean sfymtbqjsj;

    @ApiModelProperty(value = "原同权利项目ID")
    private String ytqlxmid;


    public String getQjgldm() {
        if (StringUtils.isBlank(qjgldm)){
            if(bdcXmFb != null &&StringUtils.isNotBlank(bdcXmFb.getQjgldm())){
                qjgldm =bdcXmFb.getQjgldm();
            }else if (bdcSlXmDO != null && StringUtils.isNotBlank(bdcSlXmDO.getQjgldm())) {
                qjgldm = bdcSlXmDO.getQjgldm();
            }
        }
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public boolean isSfdbqj() {
        return sfdbqj;
    }

    public void setSfdbqj(boolean sfdbqj) {
        this.sfdbqj = sfdbqj;
    }



    public Map<String, List<BdcCshFwkgSlDO>> getCshFwkgSlDataMap() {
        return cshFwkgSlDataMap;
    }

    public void setCshFwkgSlDataMap(Map<String, List<BdcCshFwkgSlDO>> cshFwkgSlDataMap) {
        this.cshFwkgSlDataMap = cshFwkgSlDataMap;
    }

    public BdcZsDO getBdcZsMbDO() {
        return bdcZsMbDO;
    }

    public void setBdcZsMbDO(BdcZsDO bdcZsMbDO) {
        this.bdcZsMbDO = bdcZsMbDO;
    }

    public Map<String, Map<String, Object>> getLcpzMap() {
        if (lcpzMap == null) {
            lcpzMap = new HashMap<>();
        }
        return lcpzMap;
    }

    public void setLcpzMap(Map<String, Map<String, Object>> lcpzMap) {
        this.lcpzMap = lcpzMap;
    }

    public Map<String, InitServiceDTO> getServiceDataMap() {
        if (serviceDataMap == null) {
            serviceDataMap = new HashMap<>();
        }
        return serviceDataMap;
    }

    public Map<String, BdcXmDO> getYbdcxmDataMap() {
        if (ybdcxmDataMap == null) {
            ybdcxmDataMap = new HashMap<>();
        }
        return ybdcxmDataMap;
    }

    public void setYbdcxmDataMap(Map<String, BdcXmDO> ybdcxmDataMap) {
        this.ybdcxmDataMap = ybdcxmDataMap;
    }

    public Map<String, BdcQl> getYqlDataMap() {
        if (yqlDataMap == null) {
            yqlDataMap = new HashMap<>();
        }
        return yqlDataMap;
    }

    public void setYqlDataMap(Map<String, BdcQl> yqlDataMap) {
        this.yqlDataMap = yqlDataMap;
    }

    public BdcCshFwkgSlDO getBdcCshFwkgSl() {
        if (bdcCshFwkgSl == null) {
            bdcCshFwkgSl = new BdcCshFwkgSlDO();
        }
        return bdcCshFwkgSl;
    }

    public String getXmid() {
        if (StringUtils.isBlank(xmid) && bdcXm != null && StringUtils.isNotBlank(bdcXm.getXmid())) {
            xmid = bdcXm.getXmid();
        }
        return xmid;
    }

    public boolean isZsyl() {
        return zsyl;
    }

    public void setZsyl(boolean zsyl) {
        this.zsyl = zsyl;
    }

    public String getBdcdyh() {
        if (StringUtils.isBlank(bdcdyh)){
            if (bdcXm != null && StringUtils.isNotBlank(bdcXm.getBdcdyh())) {
                bdcdyh = bdcXm.getBdcdyh();
            } else if (StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_CZ, version) || StringUtils.equals(CommonConstantUtils.SYSTEM_VERSION_YC, version)) {
                //常州单元号为空可创建流程
                return bdcdyh;
            } else {
                throw new AppException("不动产单元号为空！");
            }
        }
        return bdcdyh;
    }

    public List<String> getYxmids() {
        return yxmids;
    }

    public void setYxmids(List<String> yxmids) {
        this.yxmids = yxmids;
    }

    public DjxxResponseDTO getDjxxResponseDTO() {
        if (djxxResponseDTO == null) {
            // 常州没有权籍库 不做查询
            if(checkBdcdyh()  && CommonUtils.sfcxQj(this)){
                String qjqlrgxid =bdcSlXmDO !=null ?bdcSlXmDO.getQjqlrgxid() :"";
                djxxResponseDTO = getDjxxFeignService().queryDjxx(getBdcdyh(), qjqlrgxid, getQjgldm());
                LOGGER.warn("不动产单元号{}获取权籍地籍信息qjgldm={},qjqlrgxid={}返回值{}", getBdcdyh(), getQjgldm(), qjqlrgxid, JSON.toJSONString(djxxResponseDTO));
                if (djxxResponseDTO == null) {
                    djxxResponseDTO = getDjxxFeignService().queryHDjxxByBdcdyh(getBdcdyh(),getQjgldm());
                    if(djxxResponseDTO == null){
                        throw new AppException("没有获取到对应的调查表权籍数据！" + getBdcdyh());
                    }
                }
            }else{
                djxxResponseDTO=new DjxxResponseDTO();
            }
        }
        return djxxResponseDTO;
    }

    public Object getBdcdyDTO() {
        // 常州没有权籍库 不做查询
        if(checkBdcdyh() && CommonUtils.sfcxQj(this)){
            //获取fwhs接口数据
            if (bdcdyDTO == null) {
                if(bdcdyResponseDTO==null){
                    String bdcdyfwlx = bdcXm.getBdcdyfwlx() != null ? bdcXm.getBdcdyfwlx().toString() : "";

                    Long begin=System.currentTimeMillis();
                    bdcdyResponseDTO = getBdcdyFeignService().queryBdcdy(getBdcdyh(), bdcdyfwlx, getQjgldm());
                    LOGGER.info("查询权籍不动产单元数据,不动产单元号:{},不动产单元房屋类型:{}用时:{}ms {},返回结果{}", getBdcdyh(), bdcdyfwlx, System.currentTimeMillis() - begin, bdcXm.getSlbh(), JSON.toJSONString(bdcdyResponseDTO));
                    //取建设用地量化信息
                    if(bdcCshFwkgSl != null &&CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSl.getSfsclhgx()) &&bdcdyResponseDTO != null){
                        bdcdyResponseDTO =getBdcdyFeignService().initJsydLhxx(bdcdyResponseDTO);
                    }
                }
                if (bdcdyResponseDTO != null) {
                    bdcdyDTO = bdcdyResponseDTO;
                }
            }
            //获取构筑物数据
            if (bdcdyDTO == null) {
                if(gzwDcbResponseDTO==null){
                    //获取的不动产单元信息为空时，通过不动产单元号获取构筑物信息
                    gzwDcbResponseDTO = getGzwFeignService().queryGzwxxByBdcdyh(getBdcdyh(),getQjgldm());
                }
                if (gzwDcbResponseDTO != null) {
                    bdcdyDTO = gzwDcbResponseDTO;
                }
            }
            //没值抛出异常
            if (bdcdyDTO == null) {
                throw new AppException("没有获取到对应的不动产单元权籍数据！" + getBdcdyh());
            }
        }else{
            bdcdyDTO=new BdcdyResponseDTO();
            ((BdcdyResponseDTO)bdcdyDTO).setBdcdyh(getBdcdyh());
        }
        return bdcdyDTO;
    }

    public BdcdyResponseDTO getBdcdyResponseDTO() {
        if (bdcdyResponseDTO == null) {
            if(checkBdcdyh()){
                //没值调用服务  bdcdyDTO如果不为空证明不是构筑物
                if (bdcdyDTO == null) {
                    String bdcdyfwlx = bdcXm.getBdcdyfwlx() != null ? bdcXm.getBdcdyfwlx().toString() : "";
                    bdcdyResponseDTO = getBdcdyFeignService().queryBdcdy(getBdcdyh(), bdcdyfwlx,getQjgldm());
                    //取建设用地量化信息
                    if(bdcCshFwkgSl != null &&CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSl.getSfsclhgx()) &&bdcdyResponseDTO != null){
                        getBdcdyFeignService().initJsydLhxx(bdcdyResponseDTO);
                    }
                }
                if (bdcdyResponseDTO == null) {
                    bdcdyResponseDTO = new BdcdyResponseDTO();
                }
            }else{
                bdcdyResponseDTO=new BdcdyResponseDTO();
            }
        }
        return bdcdyResponseDTO;
    }

    public GzwDcbResponseDTO getGzwDcbResponseDTO() {
        if (gzwDcbResponseDTO == null) {
            if(checkBdcdyh()){
                //没值调用服务  bdcdyDTO如果不为空证明不是构筑物
                if (bdcdyDTO == null) {
                    gzwDcbResponseDTO = getGzwFeignService().queryGzwxxByBdcdyh(getBdcdyh(),getQjgldm());
                }
                if (gzwDcbResponseDTO == null) {
                    gzwDcbResponseDTO = new GzwDcbResponseDTO();
                }
            }else{
                gzwDcbResponseDTO= new GzwDcbResponseDTO();
            }

        }
        return gzwDcbResponseDTO;
    }


    public BdcdyFeignService getBdcdyFeignService() {
        if(bdcdyFeignService==null){
            bdcdyFeignService=Container.getBean(BdcdyFeignService.class);
        }
        return bdcdyFeignService;
    }

    public GzwFeignService getGzwFeignService() {
        if(gzwFeignService==null){
            gzwFeignService=Container.getBean(GzwFeignService.class);
        }
        return gzwFeignService;
    }

    public DjxxFeignService getDjxxFeignService() {
        if(djxxFeignService==null){
            djxxFeignService=Container.getBean(DjxxFeignService.class);
        }
        return djxxFeignService;
    }

    /**
     * 检验单元号是否可以查询权籍
     * @return
     */
    @JSONField(serialize = false)
    @JsonIgnore
    private boolean checkBdcdyh(){
        return StringUtils.isNotBlank(getBdcdyh()) && getBdcdyh().length()==28 && !BdcdyhToolUtils.checkXnbdcdyh(getBdcdyh());
    }


    public boolean isSfCshDjb() {
        return sfCshDjb;
    }

    public void setSfCshDjb(boolean sfCshDjb) {
        this.sfCshDjb = sfCshDjb;
    }

    public boolean isSfzqlpbsj() {
        return sfzqlpbsj;
    }

    public void setSfzqlpbsj(boolean sfzqlpbsj) {
        this.sfzqlpbsj = sfzqlpbsj;
    }

    public void setBdcdyDTO(Object bdcdyDTO) {
        this.bdcdyDTO = bdcdyDTO;
    }

    public void setDjxxResponseDTO(DjxxResponseDTO djxxResponseDTO) {
        this.djxxResponseDTO = djxxResponseDTO;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public void setBdcdyResponseDTO(BdcdyResponseDTO bdcdyResponseDTO) {
        this.bdcdyResponseDTO = bdcdyResponseDTO;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public void setBdcCshFwkgSl(BdcCshFwkgSlDO bdcCshFwkgSl) {
        this.bdcCshFwkgSl = bdcCshFwkgSl;
    }

    public BdcXmDO getBdcXm() {
        return bdcXm;
    }

    public void setBdcXm(BdcXmDO bdcXm) {
        this.bdcXm = bdcXm;
    }

    public List<BdcXmLsgxDO> getBdcXmLsgxList() {
        return bdcXmLsgxList;
    }

    public void setBdcXmLsgxList(List<BdcXmLsgxDO> bdcXmLsgxList) {
        this.bdcXmLsgxList = bdcXmLsgxList;
    }

    public void setGzwDcbResponseDTO(GzwDcbResponseDTO gzwDcbResponseDTO) {
        this.gzwDcbResponseDTO = gzwDcbResponseDTO;
    }

    public void setServiceDataMap(Map<String, InitServiceDTO> serviceDataMap) {
        this.serviceDataMap = serviceDataMap;
    }

    public List<BdcQlrDO> getBdcQlrList() {
        return bdcQlrList;
    }

    public List<BdcQlrDO> getBdcYwrList() {
        return bdcYwrList;
    }

    public void setBdcYwrList(List<BdcQlrDO> bdcYwrList) {
        this.bdcYwrList = bdcYwrList;
    }

    public void setBdcQlrList(List<BdcQlrDO> bdcQlrList) {
        this.bdcQlrList = bdcQlrList;
    }

    public List<BdcDlrDO> getBdcDlrDOList() {
        return bdcDlrDOList;
    }

    public void setBdcDlrDOList(List<BdcDlrDO> bdcDlrDOList) {
        this.bdcDlrDOList = bdcDlrDOList;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public boolean isZssl() {
        return zssl;
    }

    public void setZssl(boolean zssl) {
        this.zssl = zssl;
    }

    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }

    public DsfSlxxDTO getDsfSlxxDTO() {
        return dsfSlxxDTO;
    }

    public void setDsfSlxxDTO(DsfSlxxDTO dsfSlxxDTO) {
        this.dsfSlxxDTO = dsfSlxxDTO;
    }

    public BdcSlQl getBdcSlQl() {
        return bdcSlQl;
    }

    public void setBdcSlQl(BdcSlQl bdcSlQl) {
        this.bdcSlQl = bdcSlQl;
    }

    public boolean isSfdzbflpbsj() {
        return sfdzbflpbsj;
    }

    public void setSfdzbflpbsj(boolean sfdzbflpbsj) {
        this.sfdzbflpbsj = sfdzbflpbsj;
    }

    public boolean isSjbl() {
        return sjbl;
    }

    public void setSjbl(boolean sjbl) {
        this.sjbl = sjbl;
    }

    public BdcXmFbDO getBdcXmFb() {
        return bdcXmFb;
    }

    public void setBdcXmFb(BdcXmFbDO bdcXmFb) {
        this.bdcXmFb = bdcXmFb;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<BdcDsQlrDO> getBdcDsQlrDOList() {
        if (bdcDsQlrDOList == null) {
            bdcDsQlrDOList = new ArrayList<>();
        }
        return bdcDsQlrDOList;
    }

    public void setBdcDsQlrDOList(List<BdcDsQlrDO> bdcDsQlrDOList) {
        this.bdcDsQlrDOList = bdcDsQlrDOList;
    }

    public BdcGzdjDO getBdcGzdjDO() {
        return bdcGzdjDO;
    }

    public void setBdcGzdjDO(BdcGzdjDO bdcGzdjDO) {
        this.bdcGzdjDO = bdcGzdjDO;
    }

    public boolean getXxblLccj() {
        return xxblLccj;
    }

    public void setXxblLccj(boolean xxblLccj) {
        this.xxblLccj = xxblLccj;
    }

    public boolean isSfymtbqjsj() {
        return sfymtbqjsj;
    }

    public void setSfymtbqjsj(boolean sfymtbqjsj) {
        this.sfymtbqjsj = sfymtbqjsj;
    }

    public String getYtqlxmid() {
        return ytqlxmid;
    }

    public void setYtqlxmid(String ytqlxmid) {
        this.ytqlxmid = ytqlxmid;
    }
}
