package cn.gtmap.realestate.exchange.core.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/1
 * @description 查询不动产信息接口
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz")
public interface BdcQl {
    String getQlid();

    void setQlid(String qlid);

    String getSlbh();

    void setSlbh(String slbh);

    String getXmid();

    void setXmid(String xmid);

    String getGyqk();

    void setGyqk(String gyqk);

    Date getDjsj();

    void setDjsj(Date djsj);

    String getDbr();

    void setDbr(String dbr);

    String getFj();

    void setFj(String fj);

    Integer getQszt();

    void setQszt(Integer qszt);

    String getDjjg();

    void setDjjg(String djjg);

}
