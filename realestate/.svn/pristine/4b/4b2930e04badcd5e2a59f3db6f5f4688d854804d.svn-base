package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/12/30
 * @description  文件密文枚举类
 */
public enum FileContentTypeEnum {

    JPG("jpg", "image/jpg;charset=utf-8"),
    PNG("png", "image/png;charset=utf-8"),
    PDF("pdf", "application/msword"),
    DOC("doc", "application/msword"),
    DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    XLS("xls", "application/vnd.ms-excel"),
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");


    private String dm;


    private String mc;

    private String getDm() {
        return dm;
    }

    private void setDm(String dm) {
        this.dm = dm;
    }

    private String getMc() {
        return mc;
    }

    private void setMc(String mc) {
        this.mc = mc;
    }

    FileContentTypeEnum(String dm, String mc) {
        this.dm = dm;
        this.mc = mc;
    }

    public static String getMcByDm(String dm) {
        String mc = "application/octet-stream";
        if (StringUtils.isNotBlank(dm)) {
            for (FileContentTypeEnum fileContentTypeEnum : FileContentTypeEnum.values()) {
                if (StringUtils.equals(dm, fileContentTypeEnum.getDm())) {
                    mc = fileContentTypeEnum.getMc();
                    break;
                }
            }
        }
        return mc;
    }
}
