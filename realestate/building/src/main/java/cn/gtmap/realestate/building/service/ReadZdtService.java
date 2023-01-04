package cn.gtmap.realestate.building.service;

import org.hibernate.validator.constraints.NotBlank;

import java.io.IOException;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-21
 * @description 读取宗地图
 */
public interface ReadZdtService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.lang.String
     * @description 根据DJH 读取base 64位码
     */
    String readBase64ByDjh(@NotBlank(message = "地籍号不能为空") String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.String
     * @description 根据BDCDYH 读取base 64位码
     */
    String readBase64ByBdcdyh(String bdcdyh,String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.lang.String
     * @description 根据 DJH 读取 国图大云的ID
     */
    String getZdtIdByDjh(@NotBlank(message = "地籍号不能为空") String djh) throws IOException;

}
