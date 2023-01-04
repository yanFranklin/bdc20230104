package cn.gtmap.realestate.exchange.core.dto.wwsq.tdqsxxcj.req;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class TdqsInfoCollectRequestHeadDTO {

    @JSONField(name = "channel_id")
    private String channelId;
    @JSONField(name = "tran_date")
    private String tranDate;
    @JSONField(name = "tran_id")
    private String tranId;
    @JSONField(name = "tran_seq")
    private String tranSeq;

    private List<ExpandDTO> expand;


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getTranSeq() {
        return tranSeq;
    }

    public void setTranSeq(String tranSeq) {
        this.tranSeq = tranSeq;
    }

    public List<ExpandDTO> getExpand() {
        return expand;
    }

    public void setExpand(List<ExpandDTO> expand) {
        this.expand = expand;
    }

    @Override
    public String toString() {
        return "TdqsInfoCollectRequestHeadDTO{" +
                "channelId='" + channelId + '\'' +
                ", tranDate='" + tranDate + '\'' +
                ", tranId='" + tranId + '\'' +
                ", tranSeq='" + tranSeq + '\'' +
                ", expand=" + expand +
                '}';
    }

    public static class ExpandDTO {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "ExpandDTO{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
