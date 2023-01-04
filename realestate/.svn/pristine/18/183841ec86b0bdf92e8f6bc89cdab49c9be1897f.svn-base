package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.inquiry.core.entity.BdcHjxxLogDO;

import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/10
 * @description 综合屏数据处理
 */
public interface BdcZhpSjclService {

    /**
     * 处理队列信息
     *
     * @param jsonString
     */
    void dlxx(String jsonString);

    /**
     * 处理队列数据
     *
     * @param jsonString
     * @param zxmc
     * @param buzWindowJson
     * @param pcdate
     * @param bdcHjxxLog
     */
    void dlsjcl(String jsonString, String zxmc,String buzWindowJson, Date pcdate, BdcHjxxLogDO bdcHjxxLog);

    /**
     * 处理叫号信息
     *
     * @param jsonString
     */
    void jhxx(String jsonString);

    /**
     * 处理暂停信息
     *
     * @param jsonString
     */
    void ztxx(String jsonString);

    /**
     * 处理解除暂停信息
     *
     * @param jsonString
     */
    void jcztxx(String jsonString);

    /**
     * 处理评分信息
     *
     * @param jsonString
     */
    void pfxx(String jsonString);

    /**
     * 清空呼叫信息
     *
     */
    void qkhj();
}
