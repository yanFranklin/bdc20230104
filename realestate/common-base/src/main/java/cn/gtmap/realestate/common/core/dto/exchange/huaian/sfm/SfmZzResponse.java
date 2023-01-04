package cn.gtmap.realestate.common.core.dto.exchange.huaian.sfm;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/5/10
 * @description 苏服码返回结构DTOP
 */
public class SfmZzResponse {
    /**
     * {
     *     "code":"000001",
     *     "message":"处理成功",
     *     "data":[
     *         {
     *         "name": "中华人民共和国职业资格证书",
     * "pdf":"JVBERi0xLjQKJeLjz9MKNCAwIG9iago8PC9MZW5ndGgxIDIxNz......",
     *         "liscenseInfo": {
     * 			"creation_time": "2020-10-20 00:00:00",
     * 			"issuer": "省人社",
     * 			"holder_identity_type": "10",
     * 			"issue_org_code": "113200012332674U",
     * 			"issue_date": "2013-01-01 00:00:00",
     * 			"holder_identity_num": "32048119990101618",
     * 			"data_fields": "{'ZZMC':'中华人民共和国职业资格证书','ZZHM':'13111111111','CYRMC':'xx','CYRSFZJLX':'10','CYRSFZJHM':'320582111111111111','FZRQ':'2013-01-01','XB':'1','CSRQ':'1994-06-10','ZYJDJ':'汽车维修工','LLZSKSCJ':'60','CZJNKHCJ':'74','ZYDJ':'4','FZJGMC':'江苏省人力资源和社会保障厅','FZJGZZJGDM':'113201232674U','FZJGSSXZQHDM':'320000'}",
     * 			"license_item_code": "100002101",
     * 			"division_code": "320000",
     * 			"creator": "省人社",
     * 			"license_type": "CERTIFICATE",
     * 			"biz_num": "20171111113",
     * 			"id_code": "1310021002400352",
     * 			"license_status": "ISSUED",
     * 			"issue_org_name": "江苏省人力资源和社会保障厅",
     * 			"name": "中华人民共和国职业资格证书",
     * 			"license_code": "32000020200018P0WR",
     *                },
     *         }
     * ],
     * “agent”:"{"agentInfo":"JVBERi0xLjQKJeLjz9MKNCAwIG9iago8PC9MZW5ndGgxIDIxNz......"}",
     * "extra": "38_1642141338126 1"
     * }
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * {
         *         "name": "中华人民共和国职业资格证书",
         * "pdf":"JVBERi0xLjQKJeLjz9MKNCAwIG9iago8PC9MZW5ndGgxIDIxNz......",
         *         "liscenseInfo": {
         * 			"creation_time": "2020-10-20 00:00:00",
         * 			"issuer": "省人社",
         * 			"holder_identity_type": "10",
         * 			"issue_org_code": "113200012332674U",
         * 			"issue_date": "2013-01-01 00:00:00",
         * 			"holder_identity_num": "32048119990101618",
         * 			"data_fields": "{'ZZMC':'中华人民共和国职业资格证书','ZZHM':'13111111111','CYRMC':'xx','CYRSFZJLX':'10','CYRSFZJHM':'320582111111111111','FZRQ':'2013-01-01','XB':'1','CSRQ':'1994-06-10','ZYJDJ':'汽车维修工','LLZSKSCJ':'60','CZJNKHCJ':'74','ZYDJ':'4','FZJGMC':'江苏省人力资源和社会保障厅','FZJGZZJGDM':'113201232674U','FZJGSSXZQHDM':'320000'}",
         * 			"license_item_code": "100002101",
         * 			"division_code": "320000",
         * 			"creator": "省人社",
         * 			"license_type": "CERTIFICATE",
         * 			"biz_num": "20171111113",
         * 			"id_code": "1310021002400352",
         * 			"license_status": "ISSUED",
         * 			"issue_org_name": "江苏省人力资源和社会保障厅",
         * 			"name": "中华人民共和国职业资格证书",
         * 			"license_code": "32000020200018P0WR",
         *                },
         *         }
         */

        private LiscenseInfoBean liscenseInfo;
        private String pdf;
        private String name;

        public LiscenseInfoBean getLiscenseInfo() {
            return liscenseInfo;
        }

        public void setLiscenseInfo(LiscenseInfoBean liscenseInfo) {
            this.liscenseInfo = liscenseInfo;
        }

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

        public static class LiscenseInfoBean {
            private Date creation_time;
            private String issuer;
            private String holder_identity_num;
            private String holder_identity_type;

            private String holder_name;
            private String issue_org_code;
            private String issue_date;
            private String data_fields;
            private String license_type;
            private String id_code;
            private String license_status;
            private String issue_org_name;
            private String name;
            private String license_code;
            private String license_item_code;
            private String division_code;
            private String last_modification_time;
            private String certificate_type_code;
            private String creator;
            private String biz_num;

            public Date getCreation_time() {
                return creation_time;
            }

            public void setCreation_time(Date creation_time) {
                this.creation_time = creation_time;
            }

            public String getIssuer() {
                return issuer;
            }

            public void setIssuer(String issuer) {
                this.issuer = issuer;
            }

            public String getHolder_identity_num() {
                return holder_identity_num;
            }

            public void setHolder_identity_num(String holder_identity_num) {
                this.holder_identity_num = holder_identity_num;
            }

            public String getHolder_identity_type() {
                return holder_identity_type;
            }

            public void setHolder_identity_type(String holder_identity_type) {
                this.holder_identity_type = holder_identity_type;
            }

            public String getIssue_org_code() {
                return issue_org_code;
            }

            public void setIssue_org_code(String issue_org_code) {
                this.issue_org_code = issue_org_code;
            }

            public String getIssue_date() {
                return issue_date;
            }

            public void setIssue_date(String issue_date) {
                this.issue_date = issue_date;
            }

            public String getData_fields() {
                return data_fields;
            }

            public void setData_fields(String data_fields) {
                this.data_fields = data_fields;
            }

            public String getLicense_type() {
                return license_type;
            }

            public void setLicense_type(String license_type) {
                this.license_type = license_type;
            }

            public String getId_code() {
                return id_code;
            }

            public void setId_code(String id_code) {
                this.id_code = id_code;
            }

            public String getLicense_status() {
                return license_status;
            }

            public void setLicense_status(String license_status) {
                this.license_status = license_status;
            }

            public String getIssue_org_name() {
                return issue_org_name;
            }

            public void setIssue_org_name(String issue_org_name) {
                this.issue_org_name = issue_org_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLicense_code() {
                return license_code;
            }

            public void setLicense_code(String license_code) {
                this.license_code = license_code;
            }

            public String getLicense_item_code() {
                return license_item_code;
            }

            public void setLicense_item_code(String license_item_code) {
                this.license_item_code = license_item_code;
            }

            public String getDivision_code() {
                return division_code;
            }

            public void setDivision_code(String division_code) {
                this.division_code = division_code;
            }

            public String getLast_modification_time() {
                return last_modification_time;
            }

            public void setLast_modification_time(String last_modification_time) {
                this.last_modification_time = last_modification_time;
            }

            public String getCertificate_type_code() {
                return certificate_type_code;
            }

            public void setCertificate_type_code(String certificate_type_code) {
                this.certificate_type_code = certificate_type_code;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public String getBiz_num() {
                return biz_num;
            }

            public void setBiz_num(String biz_num) {
                this.biz_num = biz_num;
            }

            public String getHolder_name() {
                return holder_name;
            }

            public void setHolder_name(String holder_name) {
                this.holder_name = holder_name;
            }
        }
    }
}
