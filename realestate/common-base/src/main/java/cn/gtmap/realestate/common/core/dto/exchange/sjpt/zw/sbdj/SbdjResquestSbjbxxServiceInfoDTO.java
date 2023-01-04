package cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdj;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/25
 * @description 5.10    申报基本信息定义实体
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SbdjResquestSbjbxxServiceInfoDTO {

    @XmlElement(name = "UNID")
    @ApiModelProperty(value = "由业务系统自动产生(32位UUID，唯一标识，防重，排查使用，无业务含义)", required = true)
    private String unId;

    @XmlElement(name = "PROJID")
    @ApiModelProperty(value = "可为空，首次调用时为空；部分系统存在驳回重新申报的场景，重新申报时，需要传递办件编号", required = true)
    private String projId;

    @XmlElement(name = "PROJPWD")
    @ApiModelProperty(value = "由业务系统随机自动生成的数字，如：234765，默认000000", required = true)
    private String projPwd;

    @XmlElement(name = "SERVICECODE")
    @ApiModelProperty(value = "政务服务事项的唯一标识，链接跳转时传递的sc值", required = true)
    private String serviceCode;

    @XmlElement(name = "SERVICEVERSION")
    @ApiModelProperty(value = "事项的版本号 [V1.7：默认为运行当前事项版本号]", required = true)
    private String serviceVersion;

    @XmlElement(name = "SERVICENAME")
    @ApiModelProperty(value = "事项名称，如：交通建设工程施工许可[V1.7：只需要传编码即可]", required = true)
    private String serviceName;

    @XmlElement(name = "PROJECTNAME")
    @ApiModelProperty(value = "申报名称 申请审批的项目的具体名称。如：关于XXX的交通建设工程施工许可", required = true)
    private String projectName;

    @ApiModelProperty(value = "办件类型 值为：即办件、承诺件、上报件、联办件[V1.7：只需要传编码即可]", required = true)
    private String infoType;

    @XmlElement(name = "INFOTYPE_CODE")
    @ApiModelProperty(value = "详见数据字典《办件类型》", required = true)
    private String infoTypeCode;

    @XmlElement(name = "IS_MULTI")
    @ApiModelProperty(value = "是否并联业务1是0否", required = true)
    private String isMulti;

    @XmlElement(name = "MULTI_ID")
    @ApiModelProperty(value = "并联业务ID，当业务为并联业务时候，该项必填，指为牵头事项办件的ID", required = false)
    private String multiId;

    @XmlElement(name = "APPLYNAME")
    @ApiModelProperty(value = "申报者名称 填写申报者的名称，如为个人，则填写姓名；如为法人，则填写单位名称", required = true)
    private String applyName;

    @XmlElement(name = "APPLY_CARDTYPE")
    @ApiModelProperty(value = "申报者证件类型 申报者提供的有效证件名称，包括身份证、组织机构代码证等，详见数据字典《证件类型》，存储证件类型编码。", required = true)
    private String applyCardType;

    @XmlElement(name = "APPLY_CARDNUMBER")
    @ApiModelProperty(value = "申报者证件号码 提供的有效证件的识别号。如身份证号码：340111199303222102", required = true)
    private String ApplyCardNumber;

    @XmlElement(name = "CONTACTMAN")
    @ApiModelProperty(value = "联系人/代理人姓名 如果无代理人，联系人就是申报者", required = true)
    private String contactMan;

    @XmlElement(name = "CONTACTMAN_CARDTYPE")
    @ApiModelProperty(value = "联系人/代理人证件类型 提供的有效证件名称，包括身份证、组织机构代码证等详见数据字典《证件类型》，存储证件类型编码。", required = false)
    private String contactManCardType;

    @XmlElement(name = "CONTACTMAN_CARDNUMBER")
    @ApiModelProperty(value = "联系人/代理人证件号码 提供的有效证件的识别号。如身份证号码：340111199303222102", required = false)
    private String contactManCardNumber;

    @XmlElement(name = "TELPHONE")
    @ApiModelProperty(value = "联系人手机号码 申报者的联系号码", required = false)
    private String telphone;

    @XmlElement(name = "POSTCODE")
    @ApiModelProperty(value = "邮编 申报者联系地址对应的邮政编码", required = false)
    private String postCode;

    @XmlElement(name = "ADDRESS")
    @ApiModelProperty(value = "通讯地址 申报者的联系地址", required = false)
    private String address;

    @XmlElement(name = "LEGALMAN")
    @ApiModelProperty(value = "法定代表人 对于企事业单位，填写对应的法人代", required = false)
    private String legalMan;

    @XmlElement(name = "DEPTID")
    @ApiModelProperty(value = "收件部门标识 审批事项所对应的负责部门编码。", required = true)
    private String deptId;

    @XmlElement(name = "DEPTNAME")
    @ApiModelProperty(value = "收件部门名称 审批事项所对应的负责部门名称", required = true)
    private String deptName;

    @XmlElement(name = "RECEIVE_USEID")
    @ApiModelProperty(value = "收件人标识", required = false)
    private String receiveUseId;

    @XmlElement(name = "RECEIVE_NAME")
    @ApiModelProperty(value = "收件人名称", required = false)
    private String receiveName;

    @XmlElement(name = "APPLYFROM")
    @ApiModelProperty(value = "申报来源 标识办件的申报源头，详见数据字典《数据来源》，存储编码。", required = true)
    private String applyFrom;

    @XmlElement(name = "ADDTYPE")
    @ApiModelProperty(value = "事项类型：1-个人，2-法人，存储编码。", required = true)
    private String addType;

    @ApiModelProperty(value = " 办件状态 当前办件状态，存储编码[V1.7：依据工作流确定]。", required = true)
    private String handleState;

    @XmlElement(name = "BELONGTO")
    @ApiModelProperty(value = " 项目编号 有注册项目的需要填写项目编号，同国家投资项目管理监管平台中的编号保持一致", required = false)
    private String belongTo;

    @XmlElement(name = "AREACODE")
    @ApiModelProperty(value = "所属地区编码 事项受理地区编码，根据自建业务系统需要，如果申报页面可根据业务属性切换受理地区，则值为切换后的地区编码，参考字典值；如果申报页面无切换受理地区，则值为链接跳转时传递的ac值。\n" +
            "省直部门：340000000000\n" +
            "合肥市：340100000000\n" +
            "芜湖市：340200000000\n" +
            "蚌埠市：340300000000\n" +
            "淮南市：340400000000\n" +
            "马鞍山市：340500000000\n" +
            "淮北市：340600000000\n" +
            "铜陵市：340700000000\n" +
            "安庆市：340800000000\n" +
            "黄山市：341000000000\n" +
            "滁州市：341100000000\n" +
            "阜阳市：341200000000\n" +
            "宿州市：341300000000\n" +
            "六安市：341500000000\n" +
            "亳州市：341600000000\n" +
            "池州市：341700000000\n" +
            "宣城市：341800000000", required = true)
    private String areaCode;

    @ApiModelProperty(value = "数据状态 标识办件是否为有效[V1.7：默认有效]件，默认是有效。0=作废1=有效", required = true)
    private String dataState;

    @XmlElement(name = "BELONGSYSTEM")
    @ApiModelProperty(value = "所属单位 事项对应的6位地区编码  + 9位组织机构编码 + 3位系统编码组成；默认系统编码为000，标识政务服务运行管理系统", required = true)
    private String belongSystem;


    public String getUnId() {
        return unId;
    }

    public void setUnId(String unId) {
        this.unId = unId;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfoTypeCode() {
        return infoTypeCode;
    }

    public void setInfoTypeCode(String infoTypeCode) {
        this.infoTypeCode = infoTypeCode;
    }

    public String getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
    }

    public String getMultiId() {
        return multiId;
    }

    public void setMultiId(String multiId) {
        this.multiId = multiId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplyCardType() {
        return applyCardType;
    }

    public void setApplyCardType(String applyCardType) {
        this.applyCardType = applyCardType;
    }

    public String getApplyCardNumber() {
        return ApplyCardNumber;
    }

    public void setApplyCardNumber(String applyCardNumber) {
        ApplyCardNumber = applyCardNumber;
    }

    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan;
    }

    public String getContactManCardType() {
        return contactManCardType;
    }

    public void setContactManCardType(String contactManCardType) {
        this.contactManCardType = contactManCardType;
    }

    public String getContactManCardNumber() {
        return contactManCardNumber;
    }

    public void setContactManCardNumber(String contactManCardNumber) {
        this.contactManCardNumber = contactManCardNumber;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalMan() {
        return legalMan;
    }

    public void setLegalMan(String legalMan) {
        this.legalMan = legalMan;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getReceiveUseId() {
        return receiveUseId;
    }

    public void setReceiveUseId(String receiveUseId) {
        this.receiveUseId = receiveUseId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getApplyFrom() {
        return applyFrom;
    }

    public void setApplyFrom(String applyFrom) {
        this.applyFrom = applyFrom;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getHandleState() {
        return handleState;
    }

    public void setHandleState(String handleState) {
        this.handleState = handleState;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public String getProjPwd() {
        return projPwd;
    }

    public void setProjPwd(String projPwd) {
        this.projPwd = projPwd;
    }

    @Override
    public String toString() {
        return "SbdjResquestSbjbxxServiceInfoDTO{" +
                "unId='" + unId + '\'' +
                ", projId='" + projId + '\'' +
                ", projPwd='" + projPwd + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", infoType='" + infoType + '\'' +
                ", infoTypeCode='" + infoTypeCode + '\'' +
                ", isMulti='" + isMulti + '\'' +
                ", multiId='" + multiId + '\'' +
                ", applyName='" + applyName + '\'' +
                ", applyCardType='" + applyCardType + '\'' +
                ", ApplyCardNumber='" + ApplyCardNumber + '\'' +
                ", contactMan='" + contactMan + '\'' +
                ", contactManCardType='" + contactManCardType + '\'' +
                ", contactManCardNumber='" + contactManCardNumber + '\'' +
                ", telphone='" + telphone + '\'' +
                ", postCode='" + postCode + '\'' +
                ", address='" + address + '\'' +
                ", legalMan='" + legalMan + '\'' +
                ", deptId='" + deptId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", receiveUseId='" + receiveUseId + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", applyFrom='" + applyFrom + '\'' +
                ", addType='" + addType + '\'' +
                ", handleState='" + handleState + '\'' +
                ", belongTo='" + belongTo + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", dataState='" + dataState + '\'' +
                ", belongSystem='" + belongSystem + '\'' +
                '}';
    }
}
