package cn.gtmap.realestate.inquiry.ui.util;

import cn.gtmap.gtc.workflow.domain.statistics.OrgTaskStatisticsInfo;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/7/21
 * @description
 */
public final class GzltjCommonUtils {
    private static final String  PRINT_LIST_KEY = "gzltjlist";
    private static final String PRINT_MODEL_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?><fetchdatas><page><datas></datas>  \n";
    private static final String PRINT_MODEL_TAIL = "</row></detail> </page></fetchdatas>";
    public static final String MXBJL_MODEL = PRINT_MODEL_HEAD +
            "       <detail ID=\""+PRINT_LIST_KEY+"\"><row ID=\"${XMID}\">" +
            "           <data name=\"ORGNAME\" type=\"String\">$ORGNAME</data>" +
            "           <data name=\"PROCDEFNAME\" type=\"String\">$PROCDEFNAME</data>\n" +
            "           <data name=\"USERNAME\" type=\"String\">$USERNAME</data>\n" +
            "           <data name=\"REALCOUNT\" type=\"String\">$REALCOUNT</data>\n" +
            PRINT_MODEL_TAIL ;
    public static final String BMMXGZL_MODEL = PRINT_MODEL_HEAD +
            "       <detail ID=\""+PRINT_LIST_KEY+"\"><row ID=\"${orgId}\">" +
            "           <data name=\"taskName\" type=\"String\">$taskName</data>" +
            "           <data name=\"processName\" type=\"String\">$processName</data>" +
            "           <data name=\"realCount\" type=\"String\">$realCount</data>" +
            "           <data name=\"orgName\" type=\"String\">$orgName</data>" +
            PRINT_MODEL_TAIL;

    public static final String RYMXGZL_MODEL = PRINT_MODEL_HEAD +
            "       <detail ID=\""+PRINT_LIST_KEY+"\">\n" +
            "        <row ID=\"${orgId}\">\n" +
            "            <data name=\"username\" type=\"String\">$username</data>\n" +
            "            <data name=\"userAlias\" type=\"String\">$userAlias</data>\n" +
            "            <data name=\"orgName\" type=\"String\">$orgName</data>\n" +
            "            <data name=\"processName\" type=\"String\">$processName</data>\n" +
            "            <data name=\"taskName\" type=\"String\">$taskName</data>\n" +
            "            <data name=\"realCount\" type=\"String\">$realCount</data>\n" +
            PRINT_MODEL_TAIL;

    private GzltjCommonUtils() {
    }

    /**
     *内存排序
     * @param dimension 维度
     * @param resultList 排序的对象
     * @param compareStr 排序的字段
     * @return
     */
    public static void compareByCols(String dimension, List resultList,String compareStr){
        if(CollectionUtils.isEmpty(resultList)){
            return;
        }
        Collections.sort(resultList, new Comparator<Object>() {
            int result;
            @Override
            public int compare(Object o2, Object o1) {
                if(o1 instanceof Map){
                    Map o1Map = (Map)o1;
                    Map o2Map = (Map)o2;
                    if(o1Map.containsKey("realCount")){
                        result = Integer.parseInt(o1Map.get("realCount").toString()) - Integer.parseInt(o2Map.get("realCount").toString());
                    }else{
                        result = 0;
                    }
                }else if(o1 instanceof OrgTaskStatisticsInfo){
                    OrgTaskStatisticsInfo o1Bean = (OrgTaskStatisticsInfo)o1;
                    OrgTaskStatisticsInfo o2Bean = (OrgTaskStatisticsInfo)o2;
                    result = Integer.parseInt(String.valueOf(o1Bean.getRealCount())) - Integer.parseInt(String.valueOf
                            (o2Bean.getRealCount()));
                }
                return result;
            }
        });
    }

    /**
     * 组装打印fr3 dataUrl的xml模板
     * @param list 数据集
     * @param modelStr 原始模板
     * @return
     */
    public static List<BdcDysjDTO> makePrintData(List list,String modelStr){
        List<BdcDysjDTO> bdcDysjDTOList = new ArrayList<>();
        Multimap<String,List> zbMap = ArrayListMultimap.create();
        zbMap.put(PRINT_LIST_KEY,list);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj("{}");
        bdcDysjDTO.setDyzbsj(JSONObject.toJSONString(zbMap));
        bdcDysjDTO.setDyzd(modelStr);
        bdcDysjDTOList.add(bdcDysjDTO);
        return bdcDysjDTOList;
    }

}
