package cn.gtmap.realestate.common.core.dto.exchange.sjpt.zw.sbdjfk;


public class SbdjfkResponseDTO {

    private String result;

    private String resultmsg;

    private SbdjfkResponseDataDTO data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultmsg() {
        return resultmsg;
    }

    public void setResultmsg(String resultmsg) {
        this.resultmsg = resultmsg;
    }

    public SbdjfkResponseDataDTO getData() {
        return data;
    }

    public void setData(SbdjfkResponseDataDTO data) {
        this.data = data;
    }

    public class SbdjfkResponseDataDTO{
        private String errorCode;

        private String errorMsg;

        private String unid;

        private String sc;

        private String projid;

        private String projpwd;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getUnid() {
            return unid;
        }

        public void setUnid(String unid) {
            this.unid = unid;
        }

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public String getProjid() {
            return projid;
        }

        public void setProjid(String projid) {
            this.projid = projid;
        }

        public String getProjpwd() {
            return projpwd;
        }

        public void setProjpwd(String projpwd) {
            this.projpwd = projpwd;
        }
    }
}
