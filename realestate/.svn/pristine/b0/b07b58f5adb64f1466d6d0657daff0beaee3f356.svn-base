package cn.gtmap.realestate.natural.core.thread;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.thread.CommonThread;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/7/4
 * @description
 */
public class DetailPrintThread extends CommonThread {
    /**
     * 返回结果
     */
    String detailXmlString;
    /**
     * 子表ID
     */
    String detailId;
    /**
     * 子表配置 xml
     */
    String detailXml;
    /**
     * 子表配置 row xml
     */
    String detailRowXml;
    /**
     *  子表配置的所有 字段
     */
    List<String> configKeys;
    /**
     * 子表 结果数据
     */
    Map<String,String> detailData;

    public DetailPrintThread(List<String> configKeys,String detailXml,String detailRowXml,String detailId,Map<String,String> detailData) {
        this.configKeys=configKeys;
        this.detailRowXml=detailRowXml;
        this.detailXml=detailXml;
        this.detailData=detailData;
        this.detailId=detailId;
    }

    /**
     * 默认执行方法
     */
    @Override
    public void execute() throws Exception {
        configKeys.forEach(column->{
            detailData.putIfAbsent(column,"");
        });
        if (StringUtils.isBlank(detailRowXml) || MapUtils.isEmpty(detailData)) {
            throw new AppException("xml和数据集不能为空");
        }
        for(Map.Entry<String,String> entry:detailData.entrySet()){
                detailRowXml=detailRowXml.replaceAll("\\$"+entry.getKey(),String.valueOf(entry.getValue()));
                detailRowXml=detailRowXml.replaceAll("\\$\\{["+entry.getKey()+"}]+\\}",String.valueOf(entry.getValue()));
        }
        detailXmlString=detailRowXml;
    }

    public String getDetailXmlString() {
        return detailXmlString;
    }
}
