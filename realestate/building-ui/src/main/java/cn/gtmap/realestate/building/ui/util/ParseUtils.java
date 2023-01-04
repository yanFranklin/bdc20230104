package cn.gtmap.realestate.building.ui.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-07
 * @description
 */
public class ParseUtils {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseUtils.class);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param blob
     * @return java.lang.String
     * @description BLOB强转Str
     */
    public static String blobToStr(Blob blob){
        if(blob != null){
            try{
                byte[] bdata = blob.getBytes(1, (int) blob.length());
                return new String(bdata);
            } catch(SQLException e) {
                LOGGER.error("BLOB强转字符串异常",e);
            }
        }
        return "";
    }
}
