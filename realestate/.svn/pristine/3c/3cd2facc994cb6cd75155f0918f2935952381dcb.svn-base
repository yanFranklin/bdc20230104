package cn.gtmap.realestate.exchange.core.mapper.server;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0, 2016/9/22.
 * @auto <a href="mailto:zhouwanqing@gtmap.cn">zhouwanqing</a>
 * @description 无锡用于房产和土地相关信息搜索
 */
@Component
public interface GdFwTdxxMapper {
    //根据房产接件号和权利人名称获取权利人证件号和证件种类
    List<HashMap> getZjzlAndZjh(HashMap hashMap);

    //根据djh获取宗地空间属性
    List<HashMap> getZdkSx(String djh);

    //根据房产接件号获取自然幢号
    List<String> getZrzhByYwh(String ywh);

    //根据房产接件号获取幢号
    List<HashMap> getZhAndZrzhByYwh(String ywh);

    //根据房产接件号获取房屋编码和房间号
    List<HashMap> getFwbmAndFjh(String ywh);

    //获取土地的界址标识
    List<String> getJzbs(String djh);

    /**
     * @param
     * @return
     * @auto <a href="mailto:zhouwanqing@gtmap.cn">zhouwanqing</a>
     * @description 无锡获取房产信息，即获取原业务号
     **/
    List<String> getFcScywh(String ywh);

    /**
     * @param
     * @return
     * @auto <a href="mailto:zhouwanqing@gtmap.cn">zhouwanqing</a>
     * @description 无锡获取土地原业务号
     **/
    List<HashMap> getTdYywxx(HashMap hashMap);

    /**
     * @param
     * @return
     * @auto <a href="mailto:zhouwanqing@gtmap.cn">zhouwanqing</a>
     * @description 无锡证件种类的转换
     **/
    List<String> getXzjlxByMap(HashMap hashMap);

    /**
     * @param
     * @return
     * @auto <a href="mailto:zhouwanqing@gtmap.cn">zhouwanqing</a>
     * @description 江阴获取土地原证号
     **/
    List<HashMap> getJyTdYywxx(HashMap hashMap);
}
