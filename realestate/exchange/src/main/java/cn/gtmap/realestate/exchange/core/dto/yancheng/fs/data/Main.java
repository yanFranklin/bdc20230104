package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlType(propOrder = {"eInvoiceName", "eInvoiceCode", "eInvoiceNumber", "randomNumber", "eInvoiceSpecimenCode", "supervisorAreaCode", "totalAmount", "issueDate", "issueTime", "invoicingParty",
        "payerParty", "payMode", "bizCode", "currencyType", "exchangeRate", "remark", "handlingPerson", "checker", "supervisorRemark", "mainExt", "invoicingPartySeal", "supervisorPartySeal"})
public class Main implements Serializable {

    private static final long serialVersionUID = -1713120552374490626L;

    private String eInvoiceName;
    private String eInvoiceCode;
    private String eInvoiceNumber;
    private String randomNumber;
    private String eInvoiceSpecimenCode;
    private String supervisorAreaCode;
    private Double totalAmount;
    private String issueDate;
    private String issueTime;
    @XmlElement(name = "InvoicingParty",nillable = true)
    private InvoicingParty invoicingParty;
    @XmlElement(name = "PayerParty", nillable = true)
    private PayerParty payerParty;
    private String payMode;
    private String bizCode;
    private String currencyType;
    private BigDecimal exchangeRate;
    private String remark;
    private String handlingPerson;
    private String checker;
    private String supervisorRemark;
    @XmlElement(name = "MainExt", nillable = true)
    private MainExt mainExt;
    @XmlElement(name = "InvoicingPartySeal", nillable = true)
    private InvoicingPartySeal invoicingPartySeal;
    @XmlElement(name = "SupervisorPartySeal", nillable = true)
    private SupervisorPartySeal supervisorPartySeal;

    @XmlElement(name = "EInvoiceName", nillable = true)
    public String geteInvoiceName() {
        return eInvoiceName;
    }

    public void seteInvoiceName(String eInvoiceName) {
        this.eInvoiceName = eInvoiceName;
    }

    @XmlElement(name = "EInvoiceCode",nillable = true)
    public String geteInvoiceCode() {
        return eInvoiceCode;
    }

    public void seteInvoiceCode(String eInvoiceCode) {
        this.eInvoiceCode = eInvoiceCode;
    }

    @XmlElement(name = "EInvoiceNumber",nillable = true)
    public String geteInvoiceNumber() {
        return eInvoiceNumber;
    }

    public void seteInvoiceNumber(String eInvoiceNumber) {
        this.eInvoiceNumber = eInvoiceNumber;
    }

    @XmlElement(name = "RandomNumber",nillable = true)
    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    @XmlElement(name = "EInvoiceSpecimenCode",nillable = true)
    public String geteInvoiceSpecimenCode() {
        return eInvoiceSpecimenCode;
    }

    public void seteInvoiceSpecimenCode(String eInvoiceSpecimenCode) {
        this.eInvoiceSpecimenCode = eInvoiceSpecimenCode;
    }

    @XmlElement(name = "SupervisorAreaCode",nillable = true)
    public String getSupervisorAreaCode() {
        return supervisorAreaCode;
    }

    public void setSupervisorAreaCode(String supervisorAreaCode) {
        this.supervisorAreaCode = supervisorAreaCode;
    }

    @XmlElement(name = "TotalAmount",nillable = true)
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @XmlElement(name = "IssueDate",nillable = true)
    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    @XmlElement(name = "IssueTime",nillable = true)
    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    @XmlElement(name = "PayMode",nillable = true)
    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    @XmlElement(name = "BizCode",nillable = true)
    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    @XmlElement(name = "CurrencyType",nillable = true)
    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    @XmlElement(name = "ExchangeRate",nillable = true)
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @XmlElement(name = "Remark",nillable = true)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlElement(name = "HandlingPerson",nillable = true)
    public String getHandlingPerson() {
        return handlingPerson;
    }

    public void setHandlingPerson(String handlingPerson) {
        this.handlingPerson = handlingPerson;
    }

    @XmlElement(name = "Checker",nillable = true)
    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    @XmlElement(name = "SupervisorRemark",nillable = true)
    public String getSupervisorRemark() {
        return supervisorRemark;
    }

    public void setSupervisorRemark(String supervisorRemark) {
        this.supervisorRemark = supervisorRemark;
    }

    @XmlTransient
    public InvoicingParty getInvoicingParty() {
        return invoicingParty;
    }

    public void setInvoicingParty(InvoicingParty invoicingParty) {
        this.invoicingParty = invoicingParty;
    }

    @XmlTransient
    public PayerParty getPayerParty() {
        return payerParty;
    }

    public void setPayerParty(PayerParty payerParty) {
        this.payerParty = payerParty;
    }

    @XmlTransient
    public MainExt getMainExt() {
        return mainExt;
    }

    public void setMainExt(MainExt mainExt) {
        this.mainExt = mainExt;
    }

    @XmlTransient
    public InvoicingPartySeal getInvoicingPartySeal() {
        return invoicingPartySeal;
    }

    public void setInvoicingPartySeal(InvoicingPartySeal invoicingPartySeal) {
        this.invoicingPartySeal = invoicingPartySeal;
    }

    @XmlTransient
    public SupervisorPartySeal getSupervisorPartySeal() {
        return supervisorPartySeal;
    }

    public void setSupervisorPartySeal(SupervisorPartySeal supervisorPartySeal) {
        this.supervisorPartySeal = supervisorPartySeal;
    }


    public static final class MainBuilder {
        private String eInvoiceName;
        private String eInvoiceCode;
        private String eInvoiceNumber;
        private String randomNumber;
        private String eInvoiceSpecimenCode;
        private String supervisorAreaCode;
        private Double totalAmount;
        private String issueDate;
        private String issueTime;
        private String payMode;
        private String bizCode;
        private String currencyType;
        private BigDecimal exchangeRate;
        private String remark;
        private String handlingPerson;
        private String checker;
        private String supervisorRemark;
        private InvoicingParty invoicingParty;
        private PayerParty payerParty;
        private MainExt mainExt;
        private InvoicingPartySeal invoicingPartySeal;
        private SupervisorPartySeal supervisorPartySeal;

        private MainBuilder() {
        }

        public static MainBuilder aMain() {
            return new MainBuilder();
        }

        public MainBuilder withEInvoiceName(String eInvoiceName) {
            this.eInvoiceName = eInvoiceName;
            return this;
        }

        public MainBuilder withEInvoiceCode(String eInvoiceCode) {
            this.eInvoiceCode = eInvoiceCode;
            return this;
        }

        public MainBuilder withEInvoiceNumber(String eInvoiceNumber) {
            this.eInvoiceNumber = eInvoiceNumber;
            return this;
        }

        public MainBuilder withRandomNumber(String randomNumber) {
            this.randomNumber = randomNumber;
            return this;
        }

        public MainBuilder withEInvoiceSpecimenCode(String eInvoiceSpecimenCode) {
            this.eInvoiceSpecimenCode = eInvoiceSpecimenCode;
            return this;
        }

        public MainBuilder withSupervisorAreaCode(String supervisorAreaCode) {
            this.supervisorAreaCode = supervisorAreaCode;
            return this;
        }

        public MainBuilder withTotalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public MainBuilder withIssueDate(String issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public MainBuilder withIssueTime(String issueTime) {
            this.issueTime = issueTime;
            return this;
        }

        public MainBuilder withPayMode(String payMode) {
            this.payMode = payMode;
            return this;
        }

        public MainBuilder withBizCode(String bizCode) {
            this.bizCode = bizCode;
            return this;
        }

        public MainBuilder withCurrencyType(String currencyType) {
            this.currencyType = currencyType;
            return this;
        }

        public MainBuilder withExchangeRate(BigDecimal exchangeRate) {
            this.exchangeRate = exchangeRate;
            return this;
        }

        public MainBuilder withRemark(String remark) {
            this.remark = remark;
            return this;
        }

        public MainBuilder withHandlingPerson(String handlingPerson) {
            this.handlingPerson = handlingPerson;
            return this;
        }

        public MainBuilder withChecker(String checker) {
            this.checker = checker;
            return this;
        }

        public MainBuilder withSupervisorRemark(String supervisorRemark) {
            this.supervisorRemark = supervisorRemark;
            return this;
        }

        public MainBuilder withInvoicingParty(InvoicingParty invoicingParty) {
            this.invoicingParty = invoicingParty;
            return this;
        }

        public MainBuilder withPayerParty(PayerParty payerParty) {
            this.payerParty = payerParty;
            return this;
        }

        public MainBuilder withMainExt(MainExt mainExt) {
            this.mainExt = mainExt;
            return this;
        }

        public MainBuilder withInvoicingPartySeal(InvoicingPartySeal invoicingPartySeal) {
            this.invoicingPartySeal = invoicingPartySeal;
            return this;
        }

        public MainBuilder withSupervisorPartySeal(SupervisorPartySeal supervisorPartySeal) {
            this.supervisorPartySeal = supervisorPartySeal;
            return this;
        }

        public Main build() {
            Main main = new Main();
            main.setRandomNumber(randomNumber);
            main.setSupervisorAreaCode(supervisorAreaCode);
            main.setTotalAmount(totalAmount);
            main.setIssueDate(issueDate);
            main.setIssueTime(issueTime);
            main.setPayMode(payMode);
            main.setBizCode(bizCode);
            main.setCurrencyType(currencyType);
            main.setExchangeRate(exchangeRate);
            main.setRemark(remark);
            main.setHandlingPerson(handlingPerson);
            main.setChecker(checker);
            main.setSupervisorRemark(supervisorRemark);
            main.setInvoicingParty(invoicingParty);
            main.setPayerParty(payerParty);
            main.setMainExt(mainExt);
            main.setInvoicingPartySeal(invoicingPartySeal);
            main.setSupervisorPartySeal(supervisorPartySeal);
            main.eInvoiceName = this.eInvoiceName;
            main.eInvoiceCode = this.eInvoiceCode;
            main.eInvoiceSpecimenCode = this.eInvoiceSpecimenCode;
            main.eInvoiceNumber = this.eInvoiceNumber;
            return main;
        }
    }
}
