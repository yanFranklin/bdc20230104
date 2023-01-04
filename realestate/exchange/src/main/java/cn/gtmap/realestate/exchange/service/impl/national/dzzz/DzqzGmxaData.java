package cn.gtmap.realestate.exchange.service.impl.national.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.GmxaDzzzDataParam;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request.GmxaDzzzSignatories;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * 一个可用的请求实例
 *
 * @author wangyinghao
 */
@Component
public class DzqzGmxaData {

    GmxaDzzzDataParam gmxaDzzzDataParam;

    String url;

    public GmxaDzzzDataParam getGmxaDzzzConfig() {
        return gmxaDzzzDataParam;
    }

    public void setGmxaDzzzConfig(GmxaDzzzDataParam gmxaDzzzDataParam) {
        this.gmxaDzzzDataParam = gmxaDzzzDataParam;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class DzqzGmxaDataBuilder {

        private GmxaDzzzDataParam gmxaDzzzDataParam;
        /**
         * 用户唯一标识
         */
        @ApiModelProperty("url")
        private String url;

        /**
         * 时间值。取当前时间的毫秒数。
         */
        @ApiModelProperty("时间值。取当前时间的毫秒数。")
        Long timestamp;

        /**
         * 文件名称
         */
        @ApiModelProperty("文件名称")
        String documentName;

        /**
         * 文件系统地址的相对路径
         */
        @ApiModelProperty("文件系统地址的相对路径")
        String documentPath;

        /**
         * 文件系统地址的相对路径
         */
        @ApiModelProperty("下载文件系统地址的相对路径")
        String downloadDocumentPath;

        /**
         * 待签章文件。将文件读取为byte数组后使用BASE64编码为String。
         */
        @ApiModelProperty("待签章文件。将文件读取为byte数组后使用BASE64编码为String。")
        String file;

        /**
         * 单位 1
         */
        @ApiModelProperty("单位")
        String positionUnitType;

        /**
         * 为文档设置的标题。对接业务系统可通过该参数为每个文档设置不同的标题，以便后续对文档进行检索。
         */
        @ApiModelProperty("为文档设置的标题。对接业务系统可通过该参数为每个文档设置不同的标题，以便后续对文档进行检索。")
        String title;

        /**
         * 文件类型（可传文件pdf、png）1-pdf  2-ofd  3-png 4-jpg
         */
        @ApiModelProperty("文件类型（可传文件pdf、png）1-pdf  2-ofd  3-png 4-jpg")
        String fileType;

        /**
         *
         */
        @ApiModelProperty("待签章文件。将文件读取为byte数组后使用BASE64编码为String")
        List<String> fileDatas;

        /**
         * 签章信息
         */
        @ApiModelProperty("签章信息")
        List<GmxaDzzzSignatories> signatoriesList;

        /**
         * 签章关键字
         */
        @ApiModelProperty("签章关键字")
        String keyword;

        private DzqzGmxaDataBuilder() {
        }

        public static DzqzGmxaDataBuilder anDzqzGmxaDataBuilder() {
            return new DzqzGmxaDataBuilder();
        }

        /**
         * 无参数，读取配置项
         */
        public DzqzGmxaDataBuilder withBaseConfig(String url,
                                                  String deptId,
                                                  String sealCode,
                                                  String sealId,
                                                  String outUserId,
                                                  String sealName,
                                                  String siteId,
                                                  String userId
                                                  ) {
            this.url = url;
            GmxaDzzzDataParam gmxaDzzzDataParam = new GmxaDzzzDataParam();
            gmxaDzzzDataParam.setDeptId(deptId);
            gmxaDzzzDataParam.setSealCode(sealCode);
            gmxaDzzzDataParam.setOutUserId(outUserId);
            gmxaDzzzDataParam.setSealName(sealName);
            gmxaDzzzDataParam.setSealId(sealId);
            gmxaDzzzDataParam.setSiteId(siteId);
            gmxaDzzzDataParam.setUserId(userId);
            this.gmxaDzzzDataParam = gmxaDzzzDataParam;
            return this;
        }

        public DzqzGmxaDataBuilder withTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DzqzGmxaDataBuilder withDocumentName(String documentName) {
            this.documentName = documentName;
            return this;
        }

        public DzqzGmxaDataBuilder withDocumentPath(String documentPath) {
            this.documentPath = documentPath;
            return this;
        }

        public DzqzGmxaDataBuilder withDownloadDocumentPath(String downloadDocumentPath) {
            this.downloadDocumentPath = downloadDocumentPath;
            return this;
        }

        public DzqzGmxaDataBuilder withFile(String file) {
            this.file = file;
            return this;
        }

        public DzqzGmxaDataBuilder withPositionUnitType(String positionUnitType) {
            this.positionUnitType = positionUnitType;
            return this;
        }

        public DzqzGmxaDataBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public DzqzGmxaDataBuilder withFileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public DzqzGmxaDataBuilder withFileDatas(String uploadFilePath) {
            if (Objects.isNull(this.fileDatas)) {
                this.fileDatas = new ArrayList<>();
            }
            this.fileDatas.add(uploadFilePath);
            return this;
        }

        public void withSignatoriesList(List<GmxaDzzzSignatories> signatoriesList) {
            this.signatoriesList = signatoriesList;
        }

        public DzqzGmxaDataBuilder withKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public DzqzGmxaData build() throws Exception {
            DzqzGmxaData dzqzGmxaData = new DzqzGmxaData();
            dzqzGmxaData.setUrl(url);
            if (Objects.nonNull(timestamp)) {
                gmxaDzzzDataParam.setTimestamp(timestamp);
            }

            if (StrUtil.isNotBlank(documentName)) {
                gmxaDzzzDataParam.setDocumentName(documentName);
            }

            if (StrUtil.isNotBlank(documentPath)) {
                gmxaDzzzDataParam.setDocumentPath(documentPath);
            }

            if (StrUtil.isNotBlank(file)) {
                gmxaDzzzDataParam.setFile(file);
            }

            if (StrUtil.isNotBlank(positionUnitType)) {
                gmxaDzzzDataParam.setPositionUnitType(positionUnitType);
            }

            if (StrUtil.isNotBlank(title)) {
                gmxaDzzzDataParam.setTitle(title);
            }

            if (StrUtil.isNotBlank(fileType)) {
                gmxaDzzzDataParam.setFileType(fileType);
            }

            //需要上传的文件
            if (CollUtil.isNotEmpty(fileDatas)) {
                Map<String, String> datas = new HashMap<>();
                for (int i = 0; i < fileDatas.size(); i++) {
                    String filePath = fileDatas.get(i);
                    File file = new File(filePath);
                    if (!file.exists()) {
                        throw new AppException("未找到对应文件:" + filePath);
                    }

                    if ("1".equals(fileType) || "2".equals(fileType)) {
                        datas.put("0", Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(file)));
                        break;
                    }
                    datas.put(String.valueOf(i + 1), Base64Utils.encodeByteToBase64Str(FileUtils.readFileToByteArray(file)));
                }
                gmxaDzzzDataParam.setFileDatas(datas);
            }

            //下载文件
            if(StrUtil.isNotBlank(downloadDocumentPath)){
                gmxaDzzzDataParam.setDocumentPath(downloadDocumentPath);
            }

            gmxaDzzzDataParam.setSignatoriesList(new ArrayList<>());
            if (CollectionUtil.isNotEmpty(signatoriesList)) {
                gmxaDzzzDataParam.getSignatoriesList().addAll(signatoriesList);
            }

            //关键字签章
            if (StrUtil.isNotBlank(keyword)) {
                GmxaDzzzSignatories gmxaDzzzSignatories = new GmxaDzzzSignatories();
                gmxaDzzzSignatories.setPage(1);
                gmxaDzzzSignatories.setKeyWord(keyword);
                gmxaDzzzSignatories.setLocalType(0);
                gmxaDzzzSignatories.setSealId(gmxaDzzzDataParam.getSealId());
                gmxaDzzzDataParam.getSignatoriesList().add(gmxaDzzzSignatories);
            }
            dzqzGmxaData.setGmxaDzzzConfig(gmxaDzzzDataParam);
            return dzqzGmxaData;
        }
    }
}
