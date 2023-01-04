package cn.gtmap.realestate.certificate.core.service;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description  生成元数据xml
 */

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxxYsj;
import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;

public interface BdcDzzzZzxxYsjService {


    /**
     * @param bdcDzzzZzxx 传入的证照信息
     * @return xml 返回的xml
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 生成原数据xml
     */
    String createBdcDzzzXml(BdcDzzzZzxx bdcDzzzZzxx);


    /**
     * @param bdcDzzzZzxxDO 不动产权证号
     * @return BdcDzzzZzxx 证照信息
     * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
     * @description 解析电子证照元数据到证照实体类
     */
    BdcDzzzZzxx analyzeBdcDzzzXml(BdcDzzzZzxxDO bdcDzzzZzxxDO);


    /**
     * @param bdcDzzzZzxx 证照信息
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn zzbsStrbud 加密后的证照标识
     * @description 加密计算证照标识
     */
    String encryptCalculateZzbs(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param ysjxml
     * @return
     * @throws DocumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     * @description xml转BdcDzzzZzxxYsj对象
     */
    BdcDzzzZzxxYsj convertYsjXmlToZzxxYsj(String ysjxml);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzZzxxDO
     * @return
     * @description 获取BdcDzzzZzxxYsj对象，通过证照实体
     */
    BdcDzzzZzxxYsj getYsjByZzxxDo(BdcDzzzZzxxDO bdcDzzzZzxxDO);

}
