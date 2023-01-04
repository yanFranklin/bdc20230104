package cn.gtmap.realestate.exchange.core.dto.yancheng.bdcdj;

import cn.gtmap.realestate.common.core.dto.certificate.BdcZsXmAndQlrDTO;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BdcDjxxResDto {
    /**
     * 0000:成功
     */
    private String code;

    /**
     * 是否成功信息
     */
    private String msg;

    private BdcDjxxDataDto data;

    public static BdcDjxxResDto buildSuccess(BdcDjxxDataDto dataDto) {
        BdcDjxxResDto bdcDjxxResDto = new BdcDjxxResDto();
        bdcDjxxResDto.setCode("0000");
        bdcDjxxResDto.setData(dataDto);
        bdcDjxxResDto.setMsg("查询成功");
        return bdcDjxxResDto;
    }


    public static class BdcDjxxDataDto {
        private Integer count;
        private List<BdcDjxxDataItemDto> list;

        public static BdcDjxxDataDto buildList(List<BdcZsXmAndQlrDTO> list) {
            BdcDjxxDataDto dto = new BdcDjxxDataDto();
            if (CollectionUtils.isEmpty(list)) {
                dto.count = 0;
                dto.list = new ArrayList<>();
            } else {
                dto.count = list.size();
                dto.list = BdcDjxxDataItemDto.buildList(list);
            }
            return dto;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<BdcDjxxDataItemDto> getList() {
            return list;
        }

        public void setList(List<BdcDjxxDataItemDto> list) {
            this.list = list;
        }


    }

    public static class BdcDjxxDataItemDto {
        /**
         *不动产权编号
         */
        @JSONField(name = "BDCDYH")
        private String bdcdyh;
        /**
         * 不动产权证号
         */
        @JSONField(name = "BDCQZH")
        private String bdcqzh;
        /**
         * 登记机关
         */
        @JSONField(name = "DJJG")
        private String djjg;
        /**
         * 发证日期
         */
        @JSONField(name = "FZRQ",format = "yyyy-MM-dd HH:mm:ss")
        private Date fzrq;
        /**
         * 共有情况
         */
        @JSONField(name = "GYQK")
        private String gyqk;

        /**
         * 权利类型
         */
        @JSONField(name = "QLLX")
        private String qllx;
        /**
         * 权利其他状况
         */
        @JSONField(name = "QLQTZK")
        private String qlqtzk;
        /**
         * 权利人
         */
        @JSONField(name = "QLR")
        private String qlr;
        /**
         * 权利人名称
         */
        @JSONField(name = "QLRMC")
        private String qlrmc;
        /**
         * 权利人证件
         */
        @JSONField(name = "QLRZJH")
        private String qlrzjh;
        /**
         * 权利性质
         */
        @JSONField(name = "QLXZ")
        private String qlxz;
        /**
         * 使用期限
         */
        @JSONField(name = "SYQX")
        private String syqx;
        /**
         * 用途
         */
        @JSONField(name = "YT")
        private String yt;
        /**
         * 证件类型
         */
        @JSONField(name = "ZJZL")
        private String zjzl;
        /**
         * 坐落
         */
        @JSONField(name = "ZL")
        private String zl;
        /**
         * BASE64
         */
        @JSONField(name = "file_data")
        private String fileData;
        /**
         * 附件类型
         */
        @JSONField(name = "file_type")
        private String fileType;

        /**
         *
         */
        @JSONField(name = "FJ")
        private String fj;
        /**
         *
         */
        @JSONField(name = "MJ")
        private String mj;
        /**
         *
         */
        @JSONField(name = "BH")
        private String bh;
        /**
         *
         */
        @JSONField(name = "R")
        private String r;

        @JSONField(serialize = false)
        private String storageId;



        public static List<BdcDjxxDataItemDto> buildList(List<BdcZsXmAndQlrDTO> list) {
            return list.stream()
                    .map(item -> {
                        BdcDjxxDataItemDto dto = new BdcDjxxDataItemDto();
                        dto.setBdcdyh(item.getBdcdyh());
                        dto.setBdcqzh(item.getBdcqzh());
                        dto.setDjjg(item.getDjjg());
                        dto.setFzrq(item.getFzrq());
                        dto.setGyqk(item.getGyqk());
                        dto.setQllx(item.getQllx());
                        dto.setQlqtzk(item.getQlqtzk());
                        dto.setQlr(item.getQlr());
                        dto.setQlrmc(item.getQlrmc());
                        dto.setQlrzjh(item.getQlrzjh());
                        dto.setQlxz(item.getQlxz());
                        dto.setSyqx(item.getSyqx());
                        dto.setYt(item.getYt());
                        dto.setZjzl(item.getZjzl());
                        dto.setZl(item.getZl());
                        dto.setFj(item.getFj());
                        dto.setMj(item.getMj());
                        dto.setBh(item.getBh());
                        dto.setR(item.getR());
                        dto.setStorageId(item.getStorageId());
                        return dto;
                    }).collect(Collectors.toList());
        }


        public String getBdcdyh() {
            return bdcdyh;
        }

        public void setBdcdyh(String bdcdyh) {
            this.bdcdyh = bdcdyh;
        }

        public String getBdcqzh() {
            return bdcqzh;
        }

        public void setBdcqzh(String bdcqzh) {
            this.bdcqzh = bdcqzh;
        }

        public String getDjjg() {
            return djjg;
        }

        public void setDjjg(String djjg) {
            this.djjg = djjg;
        }

        public Date getFzrq() {
            return fzrq;
        }

        public void setFzrq(Date fzrq) {
            this.fzrq = fzrq;
        }

        public String getGyqk() {
            return gyqk;
        }

        public void setGyqk(String gyqk) {
            this.gyqk = gyqk;
        }

        public String getQllx() {
            return qllx;
        }

        public void setQllx(String qllx) {
            this.qllx = qllx;
        }

        public String getQlqtzk() {
            return qlqtzk;
        }

        public void setQlqtzk(String qlqtzk) {
            this.qlqtzk = qlqtzk;
        }

        public String getQlr() {
            return qlr;
        }

        public void setQlr(String qlr) {
            this.qlr = qlr;
        }

        public String getQlrmc() {
            return qlrmc;
        }

        public void setQlrmc(String qlrmc) {
            this.qlrmc = qlrmc;
        }

        public String getQlrzjh() {
            return qlrzjh;
        }

        public void setQlrzjh(String qlrzjh) {
            this.qlrzjh = qlrzjh;
        }

        public String getQlxz() {
            return qlxz;
        }

        public void setQlxz(String qlxz) {
            this.qlxz = qlxz;
        }

        public String getSyqx() {
            return syqx;
        }

        public void setSyqx(String syqx) {
            this.syqx = syqx;
        }

        public String getYt() {
            return yt;
        }

        public void setYt(String yt) {
            this.yt = yt;
        }

        public String getZjzl() {
            return zjzl;
        }

        public void setZjzl(String zjzl) {
            this.zjzl = zjzl;
        }

        public String getZl() {
            return zl;
        }

        public void setZl(String zl) {
            this.zl = zl;
        }

        public String getFileData() {
            return fileData;
        }

        public void setFileData(String fileData) {
            this.fileData = fileData;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFj() {
            return fj;
        }

        public void setFj(String fj) {
            this.fj = fj;
        }

        public String getMj() {
            return mj;
        }

        public void setMj(String mj) {
            this.mj = mj;
        }

        public String getBh() {
            return bh;
        }

        public void setBh(String bh) {
            this.bh = bh;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getStorageId() {
            return storageId;
        }

        public void setStorageId(String storageId) {
            this.storageId = storageId;
        }

        @Override
        public String toString() {
            return "BdcDjxxDataItemDto{" +
                    "bdcdyh='" + bdcdyh + '\'' +
                    ", bdcqzh='" + bdcqzh + '\'' +
                    ", djjg='" + djjg + '\'' +
                    ", fzrq=" + fzrq +
                    ", gyqk='" + gyqk + '\'' +
                    ", qllx='" + qllx + '\'' +
                    ", qlqtzk='" + qlqtzk + '\'' +
                    ", qlr='" + qlr + '\'' +
                    ", qlrmc='" + qlrmc + '\'' +
                    ", qlrzjh='" + qlrzjh + '\'' +
                    ", qlxz='" + qlxz + '\'' +
                    ", syqx='" + syqx + '\'' +
                    ", yt='" + yt + '\'' +
                    ", zjzl='" + zjzl + '\'' +
                    ", zl='" + zl + '\'' +
                    ", fileData='" + fileData + '\'' +
                    ", fileType='" + fileType + '\'' +
                    ", fj='" + fj + '\'' +
                    ", mj='" + mj + '\'' +
                    ", bh='" + bh + '\'' +
                    ", r='" + r + '\'' +
                    ", storageId='" + storageId + '\'' +
                    '}';
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BdcDjxxDataDto getData() {
        return data;
    }

    public void setData(BdcDjxxDataDto data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BdcDjxxResDto{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
