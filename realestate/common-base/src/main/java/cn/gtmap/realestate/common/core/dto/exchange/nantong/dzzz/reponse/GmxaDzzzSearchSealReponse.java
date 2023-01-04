package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangyinghao
 */
public class GmxaDzzzSearchSealReponse extends GmxaDzzzReponse {
    @ApiModelProperty("文件")
    List<SealItem> data;

    public static class SealItem {
        /**
         * 印章编号
         */
        @ApiModelProperty("印章编号")
        String sealCode;

        /**
         * 印章名称
         */
        @ApiModelProperty("印章名称")
        String sealName;

        /**
         * 印章图片
         */
        @ApiModelProperty("印章图片")
        String sealImage;

        /**
         * 印章图片后缀
         */
        @ApiModelProperty("印章图片后缀")
        String sealImageType;

        /**
         * 印章所属机构名称
         */
        @ApiModelProperty("印章所属机构名称")
        String useSealUnitName;

        /**
         * 印章所属机构统一社会信用代码
         */
        @ApiModelProperty("印章所属机构统一社会信用代码")
        String useSealUnitCode;

        /**
         * 生效日期
         */
        @ApiModelProperty("生效日期")
        String sealValidStartTime;

        /**
         * 失效日期
         */
        @ApiModelProperty("失效日期")
        String sealValidEndTime;

        /**
         * 印章宽度
         */
        @ApiModelProperty("印章宽度")
        String sealWidth;

        /**
         * 印章高度
         */
        @ApiModelProperty("印章高度")
        String sealHeight;

        /**
         * 印章证书
         */
        @ApiModelProperty("印章证书")
        String sealCert;

        /**
         * 印章状态
         */
        @ApiModelProperty("印章状态")
        String sealStatus;

        /**
         * 印章密钥索引
         */
        @ApiModelProperty("印章密钥索引")
        String sealIndex;

        /**
         * 印章信息修改时间
         */
        @ApiModelProperty("印章信息修改时间")
        String updateTime;

        /**
         * 印章所属机构id
         */
        @ApiModelProperty("印章所属机构id")
        String deptId;

        /**
         * 印章已授权的用户id
         */
        @ApiModelProperty("印章已授权的用户id")
        String outUserIds;

        public String getSealCode() {
            return sealCode;
        }

        public void setSealCode(String sealCode) {
            this.sealCode = sealCode;
        }

        public String getSealName() {
            return sealName;
        }

        public void setSealName(String sealName) {
            this.sealName = sealName;
        }

        public String getSealImage() {
            return sealImage;
        }

        public void setSealImage(String sealImage) {
            this.sealImage = sealImage;
        }

        public String getSealImageType() {
            return sealImageType;
        }

        public void setSealImageType(String sealImageType) {
            this.sealImageType = sealImageType;
        }

        public String getUseSealUnitName() {
            return useSealUnitName;
        }

        public void setUseSealUnitName(String useSealUnitName) {
            this.useSealUnitName = useSealUnitName;
        }

        public String getUseSealUnitCode() {
            return useSealUnitCode;
        }

        public void setUseSealUnitCode(String useSealUnitCode) {
            this.useSealUnitCode = useSealUnitCode;
        }

        public String getSealValidStartTime() {
            return sealValidStartTime;
        }

        public void setSealValidStartTime(String sealValidStartTime) {
            this.sealValidStartTime = sealValidStartTime;
        }

        public String getSealValidEndTime() {
            return sealValidEndTime;
        }

        public void setSealValidEndTime(String sealValidEndTime) {
            this.sealValidEndTime = sealValidEndTime;
        }

        public String getSealWidth() {
            return sealWidth;
        }

        public void setSealWidth(String sealWidth) {
            this.sealWidth = sealWidth;
        }

        public String getSealHeight() {
            return sealHeight;
        }

        public void setSealHeight(String sealHeight) {
            this.sealHeight = sealHeight;
        }

        public String getSealCert() {
            return sealCert;
        }

        public void setSealCert(String sealCert) {
            this.sealCert = sealCert;
        }

        public String getSealStatus() {
            return sealStatus;
        }

        public void setSealStatus(String sealStatus) {
            this.sealStatus = sealStatus;
        }

        public String getSealIndex() {
            return sealIndex;
        }

        public void setSealIndex(String sealIndex) {
            this.sealIndex = sealIndex;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getOutUserIds() {
            return outUserIds;
        }

        public void setOutUserIds(String outUserIds) {
            this.outUserIds = outUserIds;
        }
    }

    public List<SealItem> getData() {
        return data;
    }

    public void setData(List<SealItem> data) {
        this.data = data;
    }
}
