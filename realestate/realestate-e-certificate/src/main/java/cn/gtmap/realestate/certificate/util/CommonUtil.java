package cn.gtmap.realestate.certificate.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019-12-16
 */
public class CommonUtil {

    public static boolean indexOfStrs(String[] a, String b) {
        boolean msg = false;
        if(a != null) {
            String[] arr$ = a;
            int len$ = a.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String c = arr$[i$];
                if(StringUtils.equals(c, b)) {
                    msg = true;
                    break;
                }
            }
        }

        return msg;
    }
}
