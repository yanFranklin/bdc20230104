package cn.gtmap.realestate.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2019/3/19,1.0
 * @description
 */
@ApiModel(value = "BdcUrlDTO", description = "不动产页面URL地址")
public class BdcUrlDTO implements Serializable {
    private static final long serialVersionUID = -6839679500536760564L;
    @ApiModelProperty(value = "审核登簿UI页面url")
    private String registerUiUrl;
    @ApiModelProperty(value = "portal界面url")
    private String portalUrl;
    @ApiModelProperty(value = "受理页面url")
    private String acceptUiUrl;
    @ApiModelProperty(value = "签名图片地址")
    private String signImageUrl;
    @ApiModelProperty(value = "档案地址")
    private String archiveUrl;
    @ApiModelProperty(value = "证书归档地址")
    private String certificateUrl;
    @ApiModelProperty(value = "审核登簿Url")
    private String registerUrl;
    @ApiModelProperty(value = "文件中心url")
    private String storageUrl;
    @ApiModelProperty(value = "是否可修改流程期限")
    private Boolean sfxglcqx;
    @ApiModelProperty(value = "打印种类-fr3/pdf")
    private String dyzl;
    @ApiModelProperty(value = "页面逻辑版本")
    private String ymVersion;
    @ApiModelProperty(value = "受理页面不验证土地使用结束时间的规划用途代码")
    private List<String> noYzGhytList;
    @ApiModelProperty(value = "受理页面审批来源集合")
    private List<Integer> splyList;
    @ApiModelProperty(value = "金额单位")
    private String jedw;
    @ApiModelProperty("盐城-层高配置值")
    private List<String> cgList;
    @ApiModelProperty("是否持证人")
    private Integer sfczr;
    @ApiModelProperty("是否改变权利基本信息tab顺序")
    private Boolean changeQlJbxxSx = false;
    @ApiModelProperty("需要高亮显示登记小类集合")
    private String xgnrglxs;
    @ApiModelProperty("是否是组合流程分开收件材料")
    private Boolean zhsjcl;
    @ApiModelProperty("页面需要填写过户信息的登记小类")
    private List<String> sdghDjxlList;
    @ApiModelProperty("是否允许新增义务人")
    private Boolean sfxzywr = true;
    @ApiModelProperty("是否展示资金监管按钮")
    private Boolean sfzxZjjgBtn = false;

    @ApiModelProperty("受理页面自定义打印按钮")
    private Map<String, String> slymPrintBtnMap;

    @ApiModelProperty("表单是否验证必填")
    private Boolean sfyzbt;

    @ApiModelProperty("权利人页面保存后自动关闭")
    private Boolean autoClose = false;

    @ApiModelProperty("新增收件材料是否从登记原因收件材料配置获取")
    private Boolean sjclFromDjyyPz;

    @ApiModelProperty("常州注销登记的流程id")
    private String ZxdjProcessId;

    @ApiModelProperty("项目内多幢,不需要同步分幢面积之和到建筑面积的宗地特征码")
    private String nosumfzmjZdtzm;

    @ApiModelProperty("户室图请求http地址")
    private String hstHttpUrl;

    @ApiModelProperty("共有方式单个时是否验证单独所有")
    private Boolean gyfsdgyz;

    @ApiModelProperty("登记簿现势数据颜色")
    private String djbxssjys;

    @ApiModelProperty("抵押顺位后台配置数据")
    private List<Integer> dysw;

    @ApiModelProperty("删除后先保存收件材料")
    private boolean scbcsjcl = true;


    public Boolean getPrintmode() {
        return printmode;
    }

    public void setPrintmode(Boolean printmode) {
        this.printmode = printmode;
    }

    @ApiModelProperty(value = "是否开启新打印模式")
    private Boolean printmode;
    @ApiModelProperty(value = "抵押权是否禁止转让带入附记内容")
    private Map<String, String> jzzrFj;

    @ApiModelProperty(value = "自定义增量初始化选择台账配置")
    private Map<String, Map<String, String>> zdyZlcshXztzPz;

    @ApiModelProperty(value = "是否持证人根据sxh判断")
    private boolean sfczrBySxh;

    @ApiModelProperty(value = "特殊流程下不需要根据顺序号判断是否持证人")
    private List<String> noSfczrBySxhDjxlList;

    @ApiModelProperty("需要批量创建流程的工作流定义id")
    private boolean plcjlc = false;

    @ApiModelProperty("打印配置的dataUrl访问的ip")
    private String printIp;

    @ApiModelProperty("节点名称")
    private String jdmc;


    @ApiModelProperty("是否继承业务流转流程")
    private boolean jcywlz = false;

    @ApiModelProperty("合肥不同流程的不同djyy控制不同的字段是否必填")
    private Map<String, String> colIdAndDjyyValMap;

    @ApiModelProperty("蚌埠预告登记需要获取商品房合同的登记小类")
    private List<String> ygdjhqspfhtxxDjxlList;

    @ApiModelProperty("是否海域类流程")
    private Boolean hyllc;

    @ApiModelProperty("必填项背景色-redis读取")
    private String btxbjs;

    @ApiModelProperty("必填项标志字符大小")
    private Integer btxbzSize;

    @ApiModelProperty("组合流程是否分开打印")
    private Boolean zhlcfkdy;

    @ApiModelProperty("组合流程分开打印时收件单打印按钮")
    private Map<String, String> sjdBtn;
    @ApiModelProperty("组合流程分开打印时申请书打印按钮")
    private Map<String, String> sqsBtn;

    @ApiModelProperty("是否组合按揭流程")
    private Boolean zhajlc = false;

    @ApiModelProperty("是否需要进行状态颜色区分")
    private String[] zcztqf;

    @ApiModelProperty("受理页面输入框权限设置")
    private Map<String, Map<String, Map<String, List<String>>>> idAuthorityMap;

    @ApiModelProperty("规则验证忽略相同子规则")
    private boolean gzyzHlxtZgz;

    @ApiModelProperty("展示家庭成员按钮")
    private List<Integer> showJtcyQllxList;

    @ApiModelProperty("是否上传文件中心")
    private Boolean sfscwjzx;

    @ApiModelProperty("权利和对应的默认领证方式映射")
    private Map<String, Set<String>>  lzfsQldmMap;

    @ApiModelProperty("收件单签字位置")
    private String sjdqzwz;

    @ApiModelProperty("申请书签字位置")
    private String sqsqzwz;

    @ApiModelProperty("存量房买卖合同签字位置")
    private String clfmmht;

    @ApiModelProperty("确认书签字位置")
    private String qrsqzwz;

    @ApiModelProperty("交易份额默认值")
    private List<String> jyfeMrzDjxlList;

    @ApiModelProperty("只登记不登簿流程")
    private boolean zdjbdb = false;

    @ApiModelProperty("是否显示维修基金信息")
    private boolean xswxjj;

    @ApiModelProperty("不予受理或者登记流程")
    private boolean bysldj = false;


    @ApiModelProperty("受理页面是否二次确认的按钮id")
    private String sfecqrids;


    @ApiModelProperty("撤销登记")
    private boolean cxdj = false;

    @ApiModelProperty("匹配页面搜索条件模糊类型")
    private String ppMhlx;

    @ApiModelProperty("土地交易服务费")
    private String tdjyfwf;
    @ApiModelProperty("测量结果材料类型")
    private List<Map> cljgCllxZdList;

    @ApiModelProperty("材料上传exe模式")
    private boolean sjclExe = false;

    @ApiModelProperty("权利人类型证件种类对照")
    private Map<String,String> qlrlxZjzlDzMap;

    @ApiModelProperty("验证证件号是否含有特殊符号")
    private String yzzjhsfhytsfh;

    public String getTdjyfwf() {
        return tdjyfwf;
    }

    public void setTdjyfwf(String tdjyfwf) {
        this.tdjyfwf = tdjyfwf;
    }

    public String getSjdqzwz() {
        return sjdqzwz;
    }

    public void setSjdqzwz(String sjdqzwz) {
        this.sjdqzwz = sjdqzwz;
    }

    public String getSqsqzwz() {
        return sqsqzwz;
    }

    public void setSqsqzwz(String sqsqzwz) {
        this.sqsqzwz = sqsqzwz;
    }

    public String getClfmmht() {
        return clfmmht;
    }

    public void setClfmmht(String clfmmht) {
        this.clfmmht = clfmmht;
    }

    public String getQrsqzwz() {
        return qrsqzwz;
    }

    public void setQrsqzwz(String qrsqzwz) {
        this.qrsqzwz = qrsqzwz;
    }

    public String[] getZcztqf() {
        return zcztqf;
    }

    public void setZcztqf(String[] zcztqf) {
        this.zcztqf = zcztqf;
    }

    public String getDyzl() {
        return dyzl;
    }

    public void setDyzl(String dyzl) {
        this.dyzl = dyzl;
    }

    public Boolean getSfxglcqx() {
        return sfxglcqx;
    }

    public void setSfxglcqx(Boolean sfxglcqx) {
        this.sfxglcqx = sfxglcqx;
    }

    public Boolean getSfscwjzx() {
        return sfscwjzx;
    }

    public void setSfscwjzx(Boolean sfscwjzx) {
        this.sfscwjzx = sfscwjzx;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSignImageUrl() {
        return signImageUrl;
    }

    public void setSignImageUrl(String signImageUrl) {
        this.signImageUrl = signImageUrl;
    }

    public String getArchiveUrl() {
        return archiveUrl;
    }

    public void setArchiveUrl(String archiveUrl) {
        this.archiveUrl = archiveUrl;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }
    public String getRegisterUiUrl() {
        return registerUiUrl;
    }

    public void setRegisterUiUrl(String registerUiUrl) {
        this.registerUiUrl = registerUiUrl;
    }

    public String getPortalUrl() {
        return portalUrl;
    }

    public void setPortalUrl(String portalUrl) {
        this.portalUrl = portalUrl;
    }

    public String getAcceptUiUrl() {
        return acceptUiUrl;
    }

    public void setAcceptUiUrl(String acceptUiUrl) {
        this.acceptUiUrl = acceptUiUrl;
    }


    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public String getYmVersion() {
        return ymVersion;
    }

    public void setYmVersion(String ymVersion) {
        this.ymVersion = ymVersion;
    }

    public List<String> getNoYzGhytList() {
        return noYzGhytList;
    }

    public void setNoYzGhytList(List<String> noYzGhytList) {
        this.noYzGhytList = noYzGhytList;
    }

    public String getJedw() {
        return jedw;
    }

    public void setJedw(String jedw) {
        this.jedw = jedw;
    }

    public List<String> getCgList() {
        return cgList;
    }

    public void setCgList(List<String> cgList) {
        this.cgList = cgList;
    }

    public Integer getSfczr() {
        return sfczr;
    }

    public void setSfczr(Integer sfczr) {
        this.sfczr = sfczr;
    }

    public Boolean getChangeQlJbxxSx() {
        return changeQlJbxxSx;
    }

    public void setChangeQlJbxxSx(Boolean changeQlJbxxSx) {
        this.changeQlJbxxSx = changeQlJbxxSx;
    }

    public Map<String, String> getJzzrFj() {
        return jzzrFj;
    }

    public void setJzzrFj(Map<String, String> jzzrFj) {
        this.jzzrFj = jzzrFj;
    }

    public String getXgnrglxs() {
        return xgnrglxs;
    }

    public void setXgnrglxs(String xgnrglxs) {
        this.xgnrglxs = xgnrglxs;
    }

    public Boolean getZhsjcl() {
        return zhsjcl;
    }

    public void setZhsjcl(Boolean zhsjcl) {
        this.zhsjcl = zhsjcl;
    }

    public List<String> getSdghDjxlList() {
        return sdghDjxlList;
    }

    public void setSdghDjxlList(List<String> sdghDjxlList) {
        this.sdghDjxlList = sdghDjxlList;
    }

    public Boolean getSfxzywr() {
        return sfxzywr;
    }

    public void setSfxzywr(Boolean sfxzywr) {
        this.sfxzywr = sfxzywr;
    }

    public Boolean getSfzxZjjgBtn() {
        return sfzxZjjgBtn;
    }

    public void setSfzxZjjgBtn(Boolean sfzxZjjgBtn) {
        this.sfzxZjjgBtn = sfzxZjjgBtn;
    }

    public Map<String, String> getSlymPrintBtnMap() {
        return slymPrintBtnMap;
    }

    public void setSlymPrintBtnMap(Map<String, String> slymPrintBtnMap) {
        this.slymPrintBtnMap = slymPrintBtnMap;
    }

    public Boolean getSfyzbt() {
        return sfyzbt;
    }

    public void setSfyzbt(Boolean sfyzbt) {
        this.sfyzbt = sfyzbt;
    }

    public Boolean getAutoClose() {
        return autoClose;
    }

    public void setAutoClose(Boolean autoClose) {
        this.autoClose = autoClose;
    }

    public Boolean getSjclFromDjyyPz() {
        return sjclFromDjyyPz;
    }

    public void setSjclFromDjyyPz(Boolean sjclFromDjyyPz) {
        this.sjclFromDjyyPz = sjclFromDjyyPz;
    }

    public Map<String, Map<String, String>> getZdyZlcshXztzPz() {
        return zdyZlcshXztzPz;
    }

    public void setZdyZlcshXztzPz(Map<String, Map<String, String>> zdyZlcshXztzPz) {
        this.zdyZlcshXztzPz = zdyZlcshXztzPz;
    }

    public boolean isSfczrBySxh() {
        return sfczrBySxh;
    }

    public void setSfczrBySxh(boolean sfczrBySxh) {
        this.sfczrBySxh = sfczrBySxh;
    }

    public List<String> getNoSfczrBySxhDjxlList() {
        return noSfczrBySxhDjxlList;
    }

    public void setNoSfczrBySxhDjxlList(List<String> noSfczrBySxhDjxlList) {
        this.noSfczrBySxhDjxlList = noSfczrBySxhDjxlList;
    }

    public boolean getPlcjlc() {
        return plcjlc;
    }

    public void setPlcjlc(boolean plcjlc) {
        this.plcjlc = plcjlc;
    }

    public String getPrintIp() {
        return printIp;
    }

    public void setPrintIp(String printIp) {
        this.printIp = printIp;
    }

    public String getZxdjProcessId() {
        return ZxdjProcessId;
    }

    public void setZxdjProcessId(String zxdjProcessId) {
        ZxdjProcessId = zxdjProcessId;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public boolean getJcywlz() {
        return jcywlz;
    }

    public void setJcywlz(boolean jcywlz) {
        this.jcywlz = jcywlz;
    }

    public Map<String, String> getColIdAndDjyyValMap() {
        return colIdAndDjyyValMap;
    }

    public void setColIdAndDjyyValMap(Map<String, String> colIdAndDjyyValMap) {
        this.colIdAndDjyyValMap = colIdAndDjyyValMap;
    }

    public List<String> getYgdjhqspfhtxxDjxlList() {
        return ygdjhqspfhtxxDjxlList;
    }

    public void setYgdjhqspfhtxxDjxlList(List<String> ygdjhqspfhtxxDjxlList) {
        this.ygdjhqspfhtxxDjxlList = ygdjhqspfhtxxDjxlList;
    }

    public String getNosumfzmjZdtzm() {
        return nosumfzmjZdtzm;
    }

    public void setNosumfzmjZdtzm(String nosumfzmjZdtzm) {
        this.nosumfzmjZdtzm = nosumfzmjZdtzm;
    }

    public String getHstHttpUrl() {
        return hstHttpUrl;
    }

    public void setHstHttpUrl(String hstHttpUrl) {
        this.hstHttpUrl = hstHttpUrl;
    }

    public boolean isPlcjlc() {
        return plcjlc;
    }

    public boolean isJcywlz() {
        return jcywlz;
    }

    public Boolean getGyfsdgyz() {
        return gyfsdgyz;
    }

    public void setGyfsdgyz(Boolean gyfsdgyz) {
        this.gyfsdgyz = gyfsdgyz;
    }


    public String getDjbxssjys() {
        return djbxssjys;
    }

    public void setDjbxssjys(String djbxssjys) {
        this.djbxssjys = djbxssjys;
    }

    public List<Integer> getDysw() {
        return dysw;
    }

    public void setDysw(List<Integer> dysw) {
        this.dysw = dysw;
    }

    public Boolean getHyllc() {
        return hyllc;
    }

    public void setHyllc(Boolean hyllc) {
        this.hyllc = hyllc;
    }

    public String getBtxbjs() {
        return btxbjs;
    }

    public void setBtxbjs(String btxbjs) {
        this.btxbjs = btxbjs;
    }

    public Integer getBtxbzSize() {
        return btxbzSize;
    }

    public void setBtxbzSize(Integer btxbzSize) {
        this.btxbzSize = btxbzSize;
    }

    public List<Integer> getSplyList() {
        return splyList;
    }

    public void setSplyList(List<Integer> splyList) {
        this.splyList = splyList;
    }

    public Boolean getZhlcfkdy() {
        return zhlcfkdy;
    }

    public void setZhlcfkdy(Boolean zhlcfkdy) {
        this.zhlcfkdy = zhlcfkdy;
    }

    public Map<String, String> getSjdBtn() {
        return sjdBtn;
    }

    public void setSjdBtn(Map<String, String> sjdBtn) {
        this.sjdBtn = sjdBtn;
    }

    public Map<String, String> getSqsBtn() {
        return sqsBtn;
    }

    public void setSqsBtn(Map<String, String> sqsBtn) {
        this.sqsBtn = sqsBtn;
    }

    public Boolean getZhajlc() {
        return zhajlc;
    }

    public void setZhajlc(Boolean zhajlc) {
        this.zhajlc = zhajlc;
    }

    public Map<String, Map<String, Map<String, List<String>>>> getIdAuthorityMap() {
        return idAuthorityMap;
    }

    public void setIdAuthorityMap(Map<String, Map<String, Map<String, List<String>>>> idAuthorityMap) {
        this.idAuthorityMap = idAuthorityMap;
    }

    public boolean getGzyzHlxtZgz() {
        return gzyzHlxtZgz;
    }

    public void setGzyzHlxtZgz(boolean gzyzHlxtZgz) {
        this.gzyzHlxtZgz = gzyzHlxtZgz;
    }

    public List<Integer> getShowJtcyQllxList() {
        return showJtcyQllxList;
    }

    public void setShowJtcyQllxList(List<Integer> showJtcyQllxList) {
        this.showJtcyQllxList = showJtcyQllxList;
    }

    public Map<String, Set<String>> getLzfsQldmMap() {
        return lzfsQldmMap;
    }

    public void setLzfsQldmMap(Map<String, Set<String>> lzfsQldmMap) {
        this.lzfsQldmMap = lzfsQldmMap;
    }

    public List<String> getJyfeMrzDjxlList() {
        return jyfeMrzDjxlList;
    }

    public void setJyfeMrzDjxlList(List<String> jyfeMrzDjxlList) {
        this.jyfeMrzDjxlList = jyfeMrzDjxlList;
    }

    public boolean getZdjbdb() {
        return zdjbdb;
    }

    public void setZdjbdb(boolean zdjbdb) {
        this.zdjbdb = zdjbdb;
    }

    public boolean getBysldj() {
        return bysldj;
    }

    public void setBysldj(boolean bysldj) {
        this.bysldj = bysldj;
    }

    public boolean getScbcsjcl() {
        return scbcsjcl;
    }

    public void setScbcsjcl(boolean scbcsjcl) {
        this.scbcsjcl = scbcsjcl;
    }

    public String getSfecqrids() {
        return sfecqrids;
    }

    public void setSfecqrids(String sfecqrids) {
        this.sfecqrids = sfecqrids;
    }

    public boolean getCxdj() {
        return cxdj;
    }

    public void setCxdj(boolean cxdj) {
        this.cxdj = cxdj;
    }

    public String getPpMhlx() {
        return ppMhlx;
    }

    public void setPpMhlx(String ppMhlx) {
        this.ppMhlx = ppMhlx;
    }

    public boolean getXswxjj() {
        return xswxjj;
    }

    public void setXswxjj(boolean xswxjj) {
        this.xswxjj = xswxjj;
    }

    public List<Map> getCljgCllxZdList() {
        return cljgCllxZdList;
    }

    public void setCljgCllxZdList(List<Map> cljgCllxZdList) {
        this.cljgCllxZdList = cljgCllxZdList;
    }

    public boolean getSjclExe() {
        return sjclExe;
    }

    public void setSjclExe(boolean sjclExe) {
        this.sjclExe = sjclExe;
    }

    public Map<String, String> getQlrlxZjzlDzMap() {
        return qlrlxZjzlDzMap;
    }

    public void setQlrlxZjzlDzMap(Map<String, String> qlrlxZjzlDzMap) {
        this.qlrlxZjzlDzMap = qlrlxZjzlDzMap;
    }

    public String getYzzjhsfhytsfh() {
        return yzzjhsfhytsfh;
    }

    public void setYzzjhsfhytsfh(String yzzjhsfhytsfh) {
        this.yzzjhsfhytsfh = yzzjhsfhytsfh;
    }

    @Override
    public String toString() {
        return "BdcUrlDTO{" +
                "registerUiUrl='" + registerUiUrl + '\'' +
                ", portalUrl='" + portalUrl + '\'' +
                ", acceptUiUrl='" + acceptUiUrl + '\'' +
                ", signImageUrl='" + signImageUrl + '\'' +
                ", archiveUrl='" + archiveUrl + '\'' +
                ", certificateUrl='" + certificateUrl + '\'' +
                ", registerUrl='" + registerUrl + '\'' +
                ", storageUrl='" + storageUrl + '\'' +
                ", sfxglcqx=" + sfxglcqx +
                ", dyzl='" + dyzl + '\'' +
                ", ymVersion='" + ymVersion + '\'' +
                ", noYzGhytList=" + noYzGhytList +
                ", splyList=" + splyList +
                ", jedw='" + jedw + '\'' +
                ", cgList=" + cgList +
                ", sfczr=" + sfczr +
                ", changeQlJbxxSx=" + changeQlJbxxSx +
                ", xgnrglxs='" + xgnrglxs + '\'' +
                ", zhsjcl=" + zhsjcl +
                ", sdghDjxlList=" + sdghDjxlList +
                ", sfxzywr=" + sfxzywr +
                ", sfzxZjjgBtn=" + sfzxZjjgBtn +
                ", slymPrintBtnMap=" + slymPrintBtnMap +
                ", sfyzbt=" + sfyzbt +
                ", autoClose=" + autoClose +
                ", sjclFromDjyyPz=" + sjclFromDjyyPz +
                ", ZxdjProcessId='" + ZxdjProcessId + '\'' +
                ", nosumfzmjZdtzm='" + nosumfzmjZdtzm + '\'' +
                ", hstHttpUrl='" + hstHttpUrl + '\'' +
                ", gyfsdgyz=" + gyfsdgyz +
                ", djbxssjys='" + djbxssjys + '\'' +
                ", dysw=" + dysw +
                ", scbcsjcl=" + scbcsjcl +
                ", printmode=" + printmode +
                ", jzzrFj=" + jzzrFj +
                ", zdyZlcshXztzPz=" + zdyZlcshXztzPz +
                ", sfczrBySxh=" + sfczrBySxh +
                ", noSfczrBySxhDjxlList=" + noSfczrBySxhDjxlList +
                ", plcjlc=" + plcjlc +
                ", printIp='" + printIp + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", jcywlz=" + jcywlz +
                ", colIdAndDjyyValMap=" + colIdAndDjyyValMap +
                ", ygdjhqspfhtxxDjxlList=" + ygdjhqspfhtxxDjxlList +
                ", hyllc=" + hyllc +
                ", btxbjs='" + btxbjs + '\'' +
                ", btxbzSize=" + btxbzSize +
                ", zhlcfkdy=" + zhlcfkdy +
                ", sjdBtn=" + sjdBtn +
                ", sqsBtn=" + sqsBtn +
                ", zhajlc=" + zhajlc +
                ", zcztqf=" + Arrays.toString(zcztqf) +
                ", idAuthorityMap=" + idAuthorityMap +
                ", gzyzHlxtZgz=" + gzyzHlxtZgz +
                ", showJtcyQllxList=" + showJtcyQllxList +
                ", sfscwjzx=" + sfscwjzx +
                ", lzfsQldmMap=" + lzfsQldmMap +
                ", sjdqzwz='" + sjdqzwz + '\'' +
                ", sqsqzwz='" + sqsqzwz + '\'' +
                ", clfmmht='" + clfmmht + '\'' +
                ", qrsqzwz='" + qrsqzwz + '\'' +
                ", jyfeMrzDjxlList=" + jyfeMrzDjxlList +
                ", zdjbdb=" + zdjbdb +
                ", xswxjj=" + xswxjj +
                ", bysldj=" + bysldj +
                ", sfecqrids='" + sfecqrids + '\'' +
                ", cxdj=" + cxdj +
                ", ppMhlx='" + ppMhlx + '\'' +
                ", tdjyfwf='" + tdjyfwf + '\'' +
                ", cljgCllxZdList=" + cljgCllxZdList +
                ", sjclExe=" + sjclExe +
                '}';
    }
}
