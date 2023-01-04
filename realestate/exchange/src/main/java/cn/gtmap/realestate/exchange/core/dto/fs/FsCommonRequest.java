package cn.gtmap.realestate.exchange.core.dto.fs;

import java.io.Serializable;

public class FsCommonRequest implements Serializable {

    private static final long serialVersionUID = 5296926495999102899L;

    private String method;
    private String app_id;
    private String security;
    private String format;
    private String datetime;
    private String version;
    private String region_code;
    private String agency_code;
    private String encryption;
    private String message_id;
    private String message;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getAgency_code() {
        return agency_code;
    }

    public void setAgency_code(String agency_code) {
        this.agency_code = agency_code;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }


    public static final class FsCommonRequestBuilder {
        private String method;
        private String app_id;
        private String security;
        private String format;
        private String datetime;
        private String version;
        private String region_code;
        private String agency_code;
        private String encryption;
        private String message_id;
        private String message;

        private FsCommonRequestBuilder() {
        }

        public static FsCommonRequestBuilder aFsCommonRequest() {
            return new FsCommonRequestBuilder();
        }

        public FsCommonRequestBuilder withMethod(String method) {
            this.method = method;
            return this;
        }

        public FsCommonRequestBuilder withApp_id(String app_id) {
            this.app_id = app_id;
            return this;
        }

        public FsCommonRequestBuilder withSecurity(String security) {
            this.security = security;
            return this;
        }

        public FsCommonRequestBuilder withFormat(String format) {
            this.format = format;
            return this;
        }

        public FsCommonRequestBuilder withDatetime(String datetime) {
            this.datetime = datetime;
            return this;
        }

        public FsCommonRequestBuilder withVersion(String version) {
            this.version = version;
            return this;
        }

        public FsCommonRequestBuilder withRegion_code(String region_code) {
            this.region_code = region_code;
            return this;
        }

        public FsCommonRequestBuilder withAgency_code(String agency_code) {
            this.agency_code = agency_code;
            return this;
        }

        public FsCommonRequestBuilder withEncryption(String encryption) {
            this.encryption = encryption;
            return this;
        }

        public FsCommonRequestBuilder withMessage_id(String message_id) {
            this.message_id = message_id;
            return this;
        }

        public FsCommonRequestBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public FsCommonRequest build() {
            FsCommonRequest fsCommonRequest = new FsCommonRequest();
            fsCommonRequest.setMethod(method);
            fsCommonRequest.setApp_id(app_id);
            fsCommonRequest.setSecurity(security);
            fsCommonRequest.setFormat(format);
            fsCommonRequest.setDatetime(datetime);
            fsCommonRequest.setVersion(version);
            fsCommonRequest.setRegion_code(region_code);
            fsCommonRequest.setAgency_code(agency_code);
            fsCommonRequest.setEncryption(encryption);
            fsCommonRequest.setMessage_id(message_id);
            fsCommonRequest.setMessage(message);
            return fsCommonRequest;
        }
    }
}
