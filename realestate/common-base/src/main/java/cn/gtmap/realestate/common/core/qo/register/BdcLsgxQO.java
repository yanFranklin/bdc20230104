package cn.gtmap.realestate.common.core.qo.register;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/2/21
 * @description 不动产单元历史关系查询QO对象定义
 */
public class BdcLsgxQO {
    /**
     * 工作流实例ID
     */
    private String gzlslid;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 受理编号
     */
    private String slbh;
    /**
     * 不动产权证号
     */
    private String bdcqzh;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序类型
     */
    private String order;

    /**
     * 登记小类：需要特殊处理的业务---流程下合并展示一条
     */
    private String djxl;

    /**
     * 登记小类：常州特殊化字段
     */
    private String djxlc;

    /**
     * 权利类型
     */
    private Integer qllx;

    /**
     * 权属状态
     */
    private Integer qszt;

    /**
     * 案件状态
     */
    private String ajzt;

    /**
     * 是否查询历史上下手记录： 1 查询当前这一手以及所有上一手 ； 2 只查询当前这一手； 3 查询当前这一手以及外联的上一手
     */
    private String sfcxls;

    /**
     * 登记原因
     */
    private String djyy;

    /**
     * 不查询历史关系的工作流定义ID（多个英文逗号分隔）
     */
    private String bcxlsgxgzl;

    /**
     * 登记统计展示查询类型cq:产权，xzql:限制权利
     */
    private String cxlx;

    /**
     * 限制权利
     */
    private String xzql;

    /**
     * 是否查询以当前项目为上一手的后续递归子项目： 1 查询 ， 其它不查
     */
    private String sfcxhs;

    /**
     * 不展示指定工作流定义ID
     */
    private String gzldyid;

    /**
     * 当前项目的工作流定义id
     */
    private String dangqianGzldyid;

    /**
     * 展示外联证书抵押
     */
    private String wlzsdy;


    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getSfcxhs() {
        return sfcxhs;
    }

    public void setSfcxhs(String sfcxhs) {
        this.sfcxhs = sfcxhs;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    /**
     * 展示的权力类型配置
     */
    private String qllxlist;

    /**
     * 常州新增不展示的权属状态配置
     */
    private String qsztlist;

    /**
     * 常州新增不展示的权属状态配置
     */
    private String version;

    public String getAjzt() { return ajzt; }

    public void setAjzt(String ajzt) { this.ajzt = ajzt; }

    public String getVersion() { return version; }

    public void setVersion(String version) { this.version = version; }

    public String getQsztlist() { return qsztlist; }

    public void setQsztlist(String qsztlist) { this.qsztlist = qsztlist; }

    public String getQllxlist() { return qllxlist; }

    public void setQllxlist(String qllxlist) { this.qllxlist = qllxlist; }

    public String getXzql() { return xzql; }

    public void setXzql(String xzql) { this.xzql = xzql; }

    public String getCxlx() { return cxlx; }

    public void setCxlx(String cxlx) { this.cxlx = cxlx; }

    public String getBcxlsgxgzl() {
        return bcxlsgxgzl;
    }

    public void setBcxlsgxgzl(String bcxlsgxgzl) {
        this.bcxlsgxgzl = bcxlsgxgzl;
    }

    public String getDjxlc() {
        return djxlc;
    }

    public void setDjxlc(String djxlc) {
        this.djxlc = djxlc;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getSfcxls() {
        return sfcxls;
    }

    public void setSfcxls(String sfcxls) {
        this.sfcxls = sfcxls;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getDangqianGzldyid() {
        return dangqianGzldyid;
    }

    public void setDangqianGzldyid(String dangqianGzldyid) {
        this.dangqianGzldyid = dangqianGzldyid;
    }

    public String getWlzsdy() {
        return wlzsdy;
    }

    public void setWlzsdy(String wlzsdy) {
        this.wlzsdy = wlzsdy;
    }

    @Override
    public String toString() {
        return "BdcLsgxQO{" +
                "gzlslid='" + gzlslid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", field='" + field + '\'' +
                ", order='" + order + '\'' +
                ", djxl='" + djxl + '\'' +
                ", djxlc='" + djxlc + '\'' +
                ", qllx=" + qllx +
                ", qszt=" + qszt +
                ", ajzt='" + ajzt + '\'' +
                ", sfcxls='" + sfcxls + '\'' +
                ", djyy='" + djyy + '\'' +
                ", bcxlsgxgzl='" + bcxlsgxgzl + '\'' +
                ", cxlx='" + cxlx + '\'' +
                ", xzql='" + xzql + '\'' +
                ", sfcxhs='" + sfcxhs + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", qllxlist='" + qllxlist + '\'' +
                ", qsztlist='" + qsztlist + '\'' +
                ", version='" + version + '\'' +
                ", currentGzldyid='" + dangqianGzldyid + '\'' +
                ", wlzsdy='" + wlzsdy + '\'' +
                '}';
    }
}
