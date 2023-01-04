package cn.gtmap.realestate.common.core.dto.exchange.nantong.csjzzxx;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/5/9
 * @description 长三角电子证照信息返回DTO
 */
public class CsjZzxxDTO {


    /**
     * custom : {"certdatas":[{"issue_org_name":"中国（上海）自由贸易试验区临港新片区市场监督管理局","issue_date":"2021-11-12","name":"营业执照","id_code":"91310000MA7CX0HM9U","license_code":"AGd5XPQL5YOTrTvTVrbnXT7ndGTxzjDFXNhpeaOwvGnGNUrnYhCedLv9Qk7mPjoxzP_ePhCFPYJ2YjAGYBZsVn"}],"code":1,"licensedatas":[{"creation_time":"2022-04-13 14:03:59","trust_level":"A","id_code":"91310000MA7CX0HM9U","license_status":"ISSUED","division":"","issue_org_name":"中国（上海）自由贸易试验区临港新片区市场监督管理局","holder_identity_type":"001","issue_org_code":"11310000MB2F056267","issue_date":"2021-11-12 00:00:00","name":"营业执照","holder_identity_num":"91310000MA7CX0HM9U","data_fields":"[{\"valueType\":\"string\",\"name\":\"类型\",\"value\":\"有限责任公司(自然人投。。。略","holder_name":"上海微航企业管理有限公司","last_modification_time":"2022-04-13 14:03:59"}],"certfiles":[{"file_name":"AGd5XPQL5YOTrTvTVrbnXT7ndGTxzjDFXNhpeaOwvGnGNUrnYhCedLv9Qk7mPjoxzP_ePhCFPYJ2YjAGYBZsVn.pdf","file_data":"过长略"}],"text":"获取证照数据服务调用成功"}
     * status : {"code":200,"text":""}
     */

    private CustomBean custom;
    private StatusBean status;

    public CustomBean getCustom() {
        return custom;
    }

    public void setCustom(CustomBean custom) {
        this.custom = custom;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class CustomBean {
        /**
         * certdatas : [{"issue_org_name":"中国（上海）自由贸易试验区临港新片区市场监督管理局","issue_date":"2021-11-12","name":"营业执照","id_code":"91310000MA7CX0HM9U","license_code":"AGd5XPQL5YOTrTvTVrbnXT7ndGTxzjDFXNhpeaOwvGnGNUrnYhCedLv9Qk7mPjoxzP_ePhCFPYJ2YjAGYBZsVn"}]
         * code : 1
         * licensedatas : [{"creation_time":"2022-04-13 14:03:59","trust_level":"A","id_code":"91310000MA7CX0HM9U","license_status":"ISSUED","division":"","issue_org_name":"中国（上海）自由贸易试验区临港新片区市场监督管理局","holder_identity_type":"001","issue_org_code":"11310000MB2F056267","issue_date":"2021-11-12 00:00:00","name":"营业执照","holder_identity_num":"91310000MA7CX0HM9U","data_fields":"[{\"valueType\":\"string\",\"name\":\"类型\",\"value\":\"有限责任公司(自然人投。。。略","holder_name":"上海微航企业管理有限公司","last_modification_time":"2022-04-13 14:03:59"}]
         * certfiles : [{"file_name":"AGd5XPQL5YOTrTvTVrbnXT7ndGTxzjDFXNhpeaOwvGnGNUrnYhCedLv9Qk7mPjoxzP_ePhCFPYJ2YjAGYBZsVn.pdf","file_data":"过长略"}]
         * text : 获取证照数据服务调用成功
         */

        private int code;
        private String text;
        private List<CertdatasBean> certdatas;
        private List<LicensedatasBean> licensedatas;
        private List<CertfilesBean> certfiles;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<CertdatasBean> getCertdatas() {
            return certdatas;
        }

        public void setCertdatas(List<CertdatasBean> certdatas) {
            this.certdatas = certdatas;
        }

        public List<LicensedatasBean> getLicensedatas() {
            return licensedatas;
        }

        public void setLicensedatas(List<LicensedatasBean> licensedatas) {
            this.licensedatas = licensedatas;
        }

        public List<CertfilesBean> getCertfiles() {
            return certfiles;
        }

        public void setCertfiles(List<CertfilesBean> certfiles) {
            this.certfiles = certfiles;
        }

        public static class CertdatasBean {
            /**
             * issue_org_name : 中国（上海）自由贸易试验区临港新片区市场监督管理局
             * issue_date : 2021-11-12
             * name : 营业执照
             * id_code : 91310000MA7CX0HM9U
             * license_code : AGd5XPQL5YOTrTvTVrbnXT7ndGTxzjDFXNhpeaOwvGnGNUrnYhCedLv9Qk7mPjoxzP_ePhCFPYJ2YjAGYBZsVn
             */

            private String issue_org_name;
            private String issue_date;
            private String name;
            private String id_code;
            private String license_code;

            public String getIssue_org_name() {
                return issue_org_name;
            }

            public void setIssue_org_name(String issue_org_name) {
                this.issue_org_name = issue_org_name;
            }

            public String getIssue_date() {
                return issue_date;
            }

            public void setIssue_date(String issue_date) {
                this.issue_date = issue_date;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
        }

        public static class LicensedatasBean {
            /**
             * creation_time : 2022-04-13 14:03:59
             * trust_level : A
             * id_code : 91310000MA7CX0HM9U
             * license_status : ISSUED
             * division :
             * issue_org_name : 中国（上海）自由贸易试验区临港新片区市场监督管理局
             * holder_identity_type : 001
             * issue_org_code : 11310000MB2F056267
             * issue_date : 2021-11-12 00:00:00
             * name : 营业执照
             * holder_identity_num : 91310000MA7CX0HM9U
             * data_fields : [{"valueType":"string","name":"类型","value":"有限责任公司(自然人投。。。略
             * holder_name : 上海微航企业管理有限公司
             * last_modification_time : 2022-04-13 14:03:59
             */

            private String creation_time;
            private String trust_level;
            private String id_code;
            private String license_status;
            private String division;
            private String issue_org_name;
            private String holder_identity_type;
            private String issue_org_code;
            private String issue_date;
            private String name;
            private String holder_identity_num;
            private String data_fields;
            private String holder_name;
            private String last_modification_time;

            public String getCreation_time() {
                return creation_time;
            }

            public void setCreation_time(String creation_time) {
                this.creation_time = creation_time;
            }

            public String getTrust_level() {
                return trust_level;
            }

            public void setTrust_level(String trust_level) {
                this.trust_level = trust_level;
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

            public String getDivision() {
                return division;
            }

            public void setDivision(String division) {
                this.division = division;
            }

            public String getIssue_org_name() {
                return issue_org_name;
            }

            public void setIssue_org_name(String issue_org_name) {
                this.issue_org_name = issue_org_name;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHolder_identity_num() {
                return holder_identity_num;
            }

            public void setHolder_identity_num(String holder_identity_num) {
                this.holder_identity_num = holder_identity_num;
            }

            public String getData_fields() {
                return data_fields;
            }

            public void setData_fields(String data_fields) {
                this.data_fields = data_fields;
            }

            public String getHolder_name() {
                return holder_name;
            }

            public void setHolder_name(String holder_name) {
                this.holder_name = holder_name;
            }

            public String getLast_modification_time() {
                return last_modification_time;
            }

            public void setLast_modification_time(String last_modification_time) {
                this.last_modification_time = last_modification_time;
            }
        }

        public static class CertfilesBean {
            /**
             * file_name : AGd5XPQL5YOTrTvTVrbnXT7ndGTxzjDFXNhpeaOwvGnGNUrnYhCedLv9Qk7mPjoxzP_ePhCFPYJ2YjAGYBZsVn.pdf
             * file_data : 过长略
             */

            private String file_name;
            private String file_data;

            public String getFile_name() {
                return file_name;
            }

            public void setFile_name(String file_name) {
                this.file_name = file_name;
            }

            public String getFile_data() {
                return file_data;
            }

            public void setFile_data(String file_data) {
                this.file_data = file_data;
            }
        }
    }

    public static class StatusBean {
        /**
         * code : 200
         * text :
         */

        private int code;
        private String text;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
