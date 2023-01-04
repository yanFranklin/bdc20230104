package cn.gtmap.realestate.common.util;


import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/10.
 * @description
 */
public class ListUtils {




    /**
     * 根据设置大小对集合进行分组
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param dataList 数据集合
     *@param size  限制大小
     *@return 分组后数据
     *@description
     */
    public static List<List> subList(List dataList,Integer size){
        List<List> list=new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dataList) && size >0) {
            Integer dataSize = dataList.size();
            //分批数
            int part =(int)Math.ceil((double)dataSize / size);
            for (int i = 0; i < part; i++) {
                List listPart=null;
                if(i+1==part){
                    listPart = dataList.subList(i*size, dataSize);
                }else{
                    listPart = dataList.subList(i*size, (i+1)*size);
                }
                if(CollectionUtils.isNotEmpty(listPart)){
                    list.add(listPart);
                }
            }
        }
       return list;
    }

}
