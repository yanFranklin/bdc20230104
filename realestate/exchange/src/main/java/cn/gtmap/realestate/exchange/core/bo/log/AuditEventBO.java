package cn.gtmap.realestate.exchange.core.bo.log;

import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import jodd.util.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-04
 * @description 业务系统日志查询台账 记录日志实体
 */
public class AuditEventBO implements Serializable {

    private static final long serialVersionUID = 5205386649965332844L;

    private static Logger LOGGER = LoggerFactory.getLogger(AuditEventBO.class);

    // 功能名称
    private String viewTypeName;

    // 请求参数
    private String request;

    // 响应结构
    private String response;

    // 受理编号
    private String slbh;

    // 项目ID
    private String xmid;

    // 异常实体
    @JSONField(serialize = false)
    private Exception exception;

    // 异常信息 exception.getMessage
    private String ycxx;

    // 请求者
    private String requestr;

    // 响应者
    private String responser;

    // 第三方标志
    private String dsfFlag;

    // 第三方地址
    private String dsfdz;

    // 接口响应时间
    private long useTime;

    // 用户名--给那些拦截不到用户名，但是在参数中传用户名的方法记录用户名
    private String username;

    /*
     *  别称（中文姓名）
     * */
    private String alias;

    /**
     * 区县代码
     */
    private String qxdm;

    private boolean simpleInterface;

    //是否允许过大数据存储
    private boolean bigDataFlag;

    //日志类型
    private String rzlx;

    //日志id
    private String rzid;

    public String getRzid() {
        return rzid;
    }

    public void setRzid(String rzid) {
        this.rzid = rzid;
    }

    public String getRzlx() {
        return rzlx;
    }

    public void setRzlx(String rzlx) {
        this.rzlx = rzlx;
    }

    public boolean isBigDataFlag() {
        return bigDataFlag;
    }

    public void setBigDataFlag(boolean bigDataFlag) {
        this.bigDataFlag = bigDataFlag;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @JSONField(serialize = false)
    private transient InterfaceRequestBuilder builder;
    @JSONField(serialize = false)
    private LogBO logBO;

    public AuditEventBO() {

    }

    public AuditEventBO(LogBO logBO){
        this.logBO = logBO;
        init();
    }

    public AuditEventBO(LogBO logBO,InterfaceRequestBuilder builder){
        this.logBO = logBO;
        this.builder = builder;
        init();
    }

    private void init(){
        if(logBO != null){
            this.viewTypeName = logBO.getLogEventName();
            this.requestr = logBO.getRequester();
            this.responser = logBO.getResponser();
            this.dsfFlag = logBO.getDsfFlag();
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(logBO));
            // 判断 存在URL 则 赋值给 第三方地址字段
            if(jsonObject.containsKey("url")){
                this.dsfdz = jsonObject.getString("url");
            }
        }

        if(builder != null && builder.getOriginalRequestObject() != null && !(builder.getOriginalRequestObject() instanceof String)){
            try{
                Object originalRequestObject = builder.getOriginalRequestObject();
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(originalRequestObject));
                // 需要获取 参数最外层的 slbh
                if(StringUtil.isNotBlank(json.getString("slbh"))){
                    this.slbh = json.getString("slbh");
                }
                // 需要获取 参数最外层的 xmid
                if(StringUtil.isNotBlank(json.getString("xmid"))){
                    this.xmid = json.getString("xmid");
                }
                if(StringUtil.isBlank(dsfdz) && builder.getRequestInfo().containsKey("url")){
                    dsfdz = MapUtils.getString(builder.getRequestInfo(),"url");
                }
            }catch (Exception e){
                LOGGER.debug("新建日志实体获取受理编号异常{}", e.toString());
            }
        }
        if (builder != null && builder.getResponseBody() != null && !(builder.getResponseBody() instanceof String)) {
            try {
                Object originalResponseObject = builder.getResponseBody();
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(originalResponseObject));
                // 需要获取 参数最外层的 slbh
                if (StringUtil.isNotBlank(json.getString("ywslbh"))) {
                    this.slbh = json.getString("ywslbh");
                }
            } catch (Exception e) {
                LOGGER.debug("新建日志实体获取受理编号异常{}", e.getMessage());
            }
        }

        // 响应时间 处理
        if (builder != null && builder.getAuditEventBO() != null) {
            this.useTime = builder.getAuditEventBO().useTime;
        }

        if (this.builder != null && this.logBO != null) {
            this.logBO.setBeanId(this.builder.getExchangeBean().getId());
        }
    }

    public boolean isSimpleInterface() {
        return simpleInterface;
    }

    public void setSimpleInterface(boolean simpleInterface) {
        this.simpleInterface = simpleInterface;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getViewTypeName() {
        return viewTypeName;
    }

    public void setViewTypeName(String viewTypeName) {
        this.viewTypeName = viewTypeName;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
        if(exception != null){
            this.ycxx = Constants.EXCHANGE_RZ_YCXX_PRE + exception.getMessage();
        }
    }

    public String getRequestr() {
        return requestr;
    }

    public void setRequestr(String requestr) {
        this.requestr = requestr;
    }

    public String getResponser() {
        return responser;
    }

    public void setResponser(String responser) {
        this.responser = responser;
    }

    public String getDsfFlag() {
        return dsfFlag;
    }

    public void setDsfFlag(String dsfFlag) {
        this.dsfFlag = dsfFlag;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDsfdz() {
        return dsfdz;
    }

    public void setDsfdz(String dsfdz) {
        this.dsfdz = dsfdz;
    }

    public InterfaceRequestBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(InterfaceRequestBuilder builder) {
        this.builder = builder;
    }

    public String getYcxx() {
        return ycxx;
    }

    public void setYcxx(String ycxx) {
        this.ycxx = ycxx;
    }

    public LogBO getLogBO() {
        return logBO;
    }

    public void setLogBO(LogBO logBO) {
        this.logBO = logBO;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }
}
