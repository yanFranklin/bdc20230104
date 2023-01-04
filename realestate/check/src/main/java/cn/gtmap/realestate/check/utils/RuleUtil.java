package cn.gtmap.realestate.check.utils;

import java.util.Arrays;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/10.
 * @description 工具类
 */
public class RuleUtil {
    /**
     * 比较两个数组内容是否完全相同
     * @param array1
     * @param array2
     * @return
     */
    public static boolean compareArrays(String[] array1,String[] array2){
        boolean isEqual = false;
        Arrays.sort(array1);
        Arrays.sort(array2);
        if(array1 != null && array2 != null && Arrays.equals(array1,array2)){
            isEqual = true;
        }
        return isEqual;
    }
}
