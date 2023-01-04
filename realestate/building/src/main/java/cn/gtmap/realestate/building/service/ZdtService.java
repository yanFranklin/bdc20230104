package cn.gtmap.realestate.building.service;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-08
 * @description 宗地图相关业务逻辑服务
 */
public interface ZdtService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.lang.String
     * @description 根据DJH查询宗地图
     */
    String getZdtByDjh(@NotBlank String djh);

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询宗地图
     */
    String getZdtByBdcdyh(String bdcdyh,String qjgldm);

    /**
     * 根据合同编号查询承包土地图层信息
     *
     * @param htbh 合同编号
     * @return ZdtResponseDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 13:55 2020/8/24
     */
    String getCbzdtByHtbh(String htbh);


    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param djh
     * @return java.lang.String
     * @description 根据DJH查询宗地图(返回文件中心id)
    String getZdtIDByDjh(@NotBlank String djh) throws IOException;*/

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据BDCDYH查询宗地图 -ftp
     */
    String queryZdtByBdcdyhFtp(String bdcdyh);
}
