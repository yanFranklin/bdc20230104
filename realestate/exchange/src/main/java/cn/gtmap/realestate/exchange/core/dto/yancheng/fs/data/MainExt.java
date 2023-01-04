package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"businessNumber", "businessDate", "gender", "age", "address", "school", "faculty", "major", "classes", "studentID", "medicalType", "patientNumber", "medicalDate",
        "orgType", "medicalInsuranceType", "medicalInsuranceID", "fundPayAmount", "otherPayAmount", "accountPayAmount", "ownPayAmount", "selfpaymentAmount", "selfpaymentCost", "caseNumber",
        "hospitalizationNumber", "departmentName", "inHospitalDate", "outHospitalDate", "prepayAmount", "rechargeAmount", "refundAmount", "relatedInvoiceCode", "relatedInvoiceNumber", "payCode"})
public class MainExt implements Serializable {

    private static final long serialVersionUID = -329921545396871298L;

    private String relatedInvoiceCode;
    private String relatedInvoiceNumber;
    private String payCode;

    private String businessNumber;
    private String businessDate;
    private String gender;
    private String age;
    private String address;
    private String school;
    private String faculty;
    private String major;
    private String classes;
    private String studentID;
    private String medicalType;
    private String patientNumber;
    private String medicalDate;
    private String orgType;
    private String medicalInsuranceType;
    private String medicalInsuranceID;
    private String fundPayAmount;
    private String otherPayAmount;
    private String accountPayAmount;
    private String ownPayAmount;
    private String selfpaymentAmount;
    private String selfpaymentCost;

    private String caseNumber;
    private String hospitalizationNumber;
    private String departmentName;
    private String inHospitalDate;
    private String outHospitalDate;
    private String prepayAmount;
    private String rechargeAmount;
    private String refundAmount;

    private String custom1;
    private String custom2;
    private String custom3;
    private String custom4;
    private String custom5;
    private String custom6;
    private String custom7;
    private String custom8;
    private String custom9;
    private String custom10;
    private String custom11;
    private String custom12;

  /*  @XmlElement(name = "Custom1",nillable = true)
    public String getCustom1() {
        return custom1;
    }

    public void setCustom1(String custom1) {
        this.custom1 = custom1;
    }

    @XmlElement(name = "Custom2",nillable = true)
    public String getCustom2() {
        return custom2;
    }

    public void setCustom2(String custom2) {
        this.custom2 = custom2;
    }

    @XmlElement(name = "Custom3",nillable = true)
    public String getCustom3() {
        return custom3;
    }

    public void setCustom3(String custom3) {
        this.custom3 = custom3;
    }

    @XmlElement(name = "Custom4",nillable = true)
    public String getCustom4() {
        return custom4;
    }

    public void setCustom4(String custom4) {
        this.custom4 = custom4;
    }

    @XmlElement(name = "Custom5",nillable = true)
    public String getCustom5() {
        return custom5;
    }

    public void setCustom5(String custom5) {
        this.custom5 = custom5;
    }

    @XmlElement(name = "Custom6",nillable = true)
    public String getCustom6() {
        return custom6;
    }

    public void setCustom6(String custom6) {
        this.custom6 = custom6;
    }

    @XmlElement(name = "Custom7",nillable = true)
    public String getCustom7() {
        return custom7;
    }

    public void setCustom7(String custom7) {
        this.custom7 = custom7;
    }

    @XmlElement(name = "Custom8",nillable = true)
    public String getCustom8() {
        return custom8;
    }

    public void setCustom8(String custom8) {
        this.custom8 = custom8;
    }

    @XmlElement(name = "Custom9",nillable = true)
    public String getCustom9() {
        return custom9;
    }

    public void setCustom9(String custom9) {
        this.custom9 = custom9;
    }

    @XmlElement(name = "Custom10",nillable = true)
    public String getCustom10() {
        return custom10;
    }

    public void setCustom10(String custom10) {
        this.custom10 = custom10;
    }

    @XmlElement(name = "custom11",nillable = true)
    public String getCustom11() {
        return custom11;
    }

    public void setCustom11(String custom11) {
        this.custom11 = custom11;
    }

    @XmlElement(name = "Custom12",nillable = true)
    public String getCustom12() {
        return custom12;
    }

    public void setCustom12(String custom12) {
        this.custom12 = custom12;
    }*/

    @XmlElement(name = "SelfpaymentCost",nillable = true)
    public String getSelfpaymentCost() {
        return selfpaymentCost;
    }

    public void setSelfpaymentCost(String selfpaymentCost) {
        this.selfpaymentCost = selfpaymentCost;
    }

    @XmlElement(name = "RelatedInvoiceCode",nillable = true)
    public String getRelatedInvoiceCode() {
        return relatedInvoiceCode;
    }

    public void setRelatedInvoiceCode(String relatedInvoiceCode) {
        this.relatedInvoiceCode = relatedInvoiceCode;
    }

    @XmlElement(name = "RelatedInvoiceNumber",nillable = true)
    public String getRelatedInvoiceNumber() {
        return relatedInvoiceNumber;
    }

    public void setRelatedInvoiceNumber(String relatedInvoiceNumber) {
        this.relatedInvoiceNumber = relatedInvoiceNumber;
    }

    @XmlElement(name = "PayCode",nillable = true)
    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    @XmlElement(name = "BusinessNumber",nillable = true)
    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    @XmlElement(name = "BusinessDate",nillable = true)
    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    @XmlElement(name = "Gender",nillable = true)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @XmlElement(name = "Age",nillable = true)
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @XmlElement(name = "Address",nillable = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlElement(name = "School",nillable = true)
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @XmlElement(name = "Faculty",nillable = true)
    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @XmlElement(name = "Major",nillable = true)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @XmlElement(name = "Classes",nillable = true)
    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    @XmlElement(name = "StudentID",nillable = true)
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @XmlElement(name = "MedicalType",nillable = true)
    public String getMedicalType() {
        return medicalType;
    }

    public void setMedicalType(String medicalType) {
        this.medicalType = medicalType;
    }

    @XmlElement(name = "MedicalDate",nillable = true)
    public String getMedicalDate() {
        return medicalDate;
    }

    public void setMedicalDate(String medicalDate) {
        this.medicalDate = medicalDate;
    }

    @XmlElement(name = "CaseNumber",nillable = true)
    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    @XmlElement(name = "patientNumber",nillable = true)
    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    @XmlElement(name = "HospitalizationNumber",nillable = true)
    public String getHospitalizationNumber() {
        return hospitalizationNumber;
    }

    public void setHospitalizationNumber(String hospitalizationNumber) {
        this.hospitalizationNumber = hospitalizationNumber;
    }

    @XmlElement(name = "DepartmentName",nillable = true)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @XmlElement(name = "InHospitalDate",nillable = true)
    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    @XmlElement(name = "OutHospitalDate",nillable = true)
    public String getOutHospitalDate() {
        return outHospitalDate;
    }

    public void setOutHospitalDate(String outHospitalDate) {
        this.outHospitalDate = outHospitalDate;
    }

    @XmlElement(name = "PrepayAmount",nillable = true)
    public String getPrepayAmount() {
        return prepayAmount;
    }

    public void setPrepayAmount(String prepayAmount) {
        this.prepayAmount = prepayAmount;
    }

    @XmlElement(name = "RechargeAmount",nillable = true)
    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    @XmlElement(name = "RefundAmount",nillable = true)
    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    @XmlElement(name = "OrgType",nillable = true)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @XmlElement(name = "MedicalInsuranceType",nillable = true)
    public String getMedicalInsuranceType() {
        return medicalInsuranceType;
    }

    public void setMedicalInsuranceType(String medicalInsuranceType) {
        this.medicalInsuranceType = medicalInsuranceType;
    }

    @XmlElement(name = "MedicalInsuranceID",nillable = true)
    public String getMedicalInsuranceID() {
        return medicalInsuranceID;
    }

    public void setMedicalInsuranceID(String medicalInsuranceID) {
        this.medicalInsuranceID = medicalInsuranceID;
    }

    @XmlElement(name = "FundPayAmount",nillable = true)
    public String getFundPayAmount() {
        return fundPayAmount;
    }

    public void setFundPayAmount(String fundPayAmount) {
        fundPayAmount = fundPayAmount;
    }

    @XmlElement(name = "OtherPayAmount",nillable = true)
    public String getOtherPayAmount() {
        return otherPayAmount;
    }

    public void setOtherPayAmount(String otherPayAmount) {
        this.otherPayAmount = otherPayAmount;
    }

    @XmlElement(name = "AccountPayAmount",nillable = true)
    public String getAccountPayAmount() {
        return accountPayAmount;
    }

    public void setAccountPayAmount(String accountPayAmount) {
        this.accountPayAmount = accountPayAmount;
    }

    @XmlElement(name = "OwnPayAmount",nillable = true)
    public String getOwnPayAmount() {
        return ownPayAmount;
    }

    public void setOwnPayAmount(String ownPayAmount) {
        this.ownPayAmount = ownPayAmount;
    }

    @XmlElement(name = "SelfpaymentAmount",nillable = true)
    public String getSelfpaymentAmount() {
        return selfpaymentAmount;
    }

    public void setSelfpaymentAmount(String selfpaymentAmount) {
        selfpaymentAmount = selfpaymentAmount;
    }

    public static final class MainExtBuilder {
        private String relatedInvoiceCode;
        private String relatedInvoiceNumber;
        private String payCode;

        private MainExtBuilder() {
        }

        public static MainExtBuilder aMainExt() {
            return new MainExtBuilder();
        }

        public MainExtBuilder withRelatedInvoiceCode(String relatedInvoiceCode) {
            this.relatedInvoiceCode = relatedInvoiceCode;
            return this;
        }

        public MainExtBuilder withRelatedInvoiceNumber(String relatedInvoiceNumber) {
            this.relatedInvoiceNumber = relatedInvoiceNumber;
            return this;
        }

        public MainExtBuilder withPayCode(String payCode) {
            this.payCode = payCode;
            return this;
        }

        public MainExt build() {
            MainExt mainExt = new MainExt();
            mainExt.setRelatedInvoiceCode(relatedInvoiceCode);
            mainExt.setRelatedInvoiceNumber(relatedInvoiceNumber);
            mainExt.setPayCode(payCode);
            return mainExt;
        }
    }
}
