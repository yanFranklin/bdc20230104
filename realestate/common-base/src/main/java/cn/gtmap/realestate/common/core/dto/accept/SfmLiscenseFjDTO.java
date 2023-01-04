package cn.gtmap.realestate.common.core.dto.accept;

public class SfmLiscenseFjDTO {
    private String pdf;
    private String name;
    private String holder_name;

    private String holder_identity_num;

    private String id_code;
    private String license_code;

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getLicense_code() {
        return license_code;
    }

    public void setLicense_code(String license_code) {
        this.license_code = license_code;
    }

    public String getHolder_identity_num() {
        return holder_identity_num;
    }

    public void setHolder_identity_num(String holder_identity_num) {
        this.holder_identity_num = holder_identity_num;
    }
}
