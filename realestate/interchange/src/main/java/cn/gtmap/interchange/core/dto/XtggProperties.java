package cn.gtmap.interchange.core.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ggxx")
public class XtggProperties {
    // 不动产3.0系统获取公告信息接口，对应登记应用需要放开这个接口权限
    private String bdcdjurl;

    // 推送公告信息后回调的登记3.0接口地址，用于记录成功推送的公告信息
    private String bdcdjlogurl;

    // 局网站接收公告信息接口
    private String jwzurl;

    // 查询待推送公告信息时间范围：开始时间
    private String starttime;

    // 查询待推送公告信息时间范围：结束时间
    private String endtime;

    // 查询待推送公告信息：审批来源，默认 0，多个英文逗号分隔
    private String sply;

    // 查询待推送公告信息：公告类型，默认 1，多个英文逗号分隔
    private String gglx;

    // 查询待推送公告信息：工作流定义id，默认空，即查询所有流程的公告，多个英文逗号分隔
    private String gzldyid;

    // 推送局网站公告信息字段中栏目columnname对应值，默认 不动产登记公告
    private String columnname;

    // 推送局网站公告信息字段中栏目编码columncode对应值，默认 5783
    private String columncode;

    // 保存到公告记录表BDC_GG的公告人姓名ggrxm
    private String ggrxm;
}
