package cn.gtmap.realestate.exchange.core.dto.yancheng.fs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"sealId", "sealName", "sealHash"})
public class SupervisorPartySeal implements Serializable {

    private static final long serialVersionUID = 5208364908392389447L;

    private String sealId;
    private String sealName;
    private String sealHash;

    @XmlElement(name = "SealId",nillable = true)
    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    @XmlElement(name = "SealName",nillable = true)
    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    @XmlElement(name = "SealHash",nillable = true)
    public String getSealHash() {
        return sealHash;
    }

    public void setSealHash(String sealHash) {
        this.sealHash = sealHash;
    }

    public static final class SupervisorPartySealBuilder {
        private String sealId;
        private String sealName;
        private String sealHash;

        private SupervisorPartySealBuilder() {
        }

        public static SupervisorPartySealBuilder aSupervisorPartySeal() {
            return new SupervisorPartySealBuilder();
        }

        public SupervisorPartySealBuilder withSealId(String sealId) {
            this.sealId = sealId;
            return this;
        }

        public SupervisorPartySealBuilder withSealName(String sealName) {
            this.sealName = sealName;
            return this;
        }

        public SupervisorPartySealBuilder withSealHash(String sealHash) {
            this.sealHash = sealHash;
            return this;
        }

        public SupervisorPartySeal build() {
            SupervisorPartySeal supervisorPartySeal = new SupervisorPartySeal();
            supervisorPartySeal.setSealId(sealId);
            supervisorPartySeal.setSealName(sealName);
            supervisorPartySeal.setSealHash(sealHash);
            return supervisorPartySeal;
        }
    }
}
