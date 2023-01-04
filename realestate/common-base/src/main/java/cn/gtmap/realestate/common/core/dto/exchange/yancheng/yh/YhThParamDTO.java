package cn.gtmap.realestate.common.core.dto.exchange.yancheng.yh;

public class YhThParamDTO {

    //退回原因
    private String returnReason;

    //审批系统业务号
    private String spxtywh;

    //银行名称
    private String yhmc;

    public String getYhmc() {
        return yhmc;
    }

    public void setYhmc(String yhmc) {
        this.yhmc = yhmc;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }


    public static final class YhThParamDTOBuilder {
        //退回原因
        private String returnReason;
        //审批系统业务号
        private String spxtywh;
        //银行名称
        private String yhmc;

        private YhThParamDTOBuilder() {
        }

        public static YhThParamDTOBuilder anYhThParamDTO() {
            return new YhThParamDTOBuilder();
        }

        public YhThParamDTOBuilder withReturnReason(String returnReason) {
            this.returnReason = returnReason;
            return this;
        }

        public YhThParamDTOBuilder withSpxtywh(String spxtywh) {
            this.spxtywh = spxtywh;
            return this;
        }

        public YhThParamDTOBuilder withYhmc(String yhmc) {
            this.yhmc = yhmc;
            return this;
        }

        public YhThParamDTO build() {
            YhThParamDTO yhThParamDTO = new YhThParamDTO();
            yhThParamDTO.setReturnReason(returnReason);
            yhThParamDTO.setSpxtywh(spxtywh);
            yhThParamDTO.setYhmc(yhmc);
            return yhThParamDTO;
        }
    }
}
