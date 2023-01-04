package cn.gtmap.realestate.certificate.core.service;



/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, 2019/1/17
 * @description 不动产电子证照证照库业务
 */

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzJzjxxDO;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxDO;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxxYsj;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface BdcDzzzZzxxService {


    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 通过zzid 获取证照库数据
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxx(String zzid);

    /**
     * @param zsid
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 通过zsid查询证照信息
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxxByZsid(String zsid);


    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 通过bdcqzh查询证照信息
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxxByBdcqzh(String bdcqzh);

    /**
     * @param zzbs
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 通过zzbs查询证照信息
     */
    BdcDzzzZzxxDO queryBdcDzzzZzxxByZzbs(String zzbs);

    /**
     * @param bdcDzzzZzxx
     * @return
     * @throws DataAccessException
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 证照信息入库
     */
    DzzzResponseModel insertBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx) throws DataAccessException;

    /**
     * zhangyu 将父类数据转移到子类
     *
     * @param bdcDzzzZzxx
     * @return
     */
    BdcDzzzZzxxDO getBdcDzzzZzxxDOFromBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * zhangyu 将子类数据转移到父类
     *
     * @param bdcDzzzZzxxDO
     * @return
     */
    BdcDzzzZzxx getBdcDzzzZzxxFromBdcDzzzZzxxDO(BdcDzzzZzxxDO bdcDzzzZzxxDO, BdcDzzzZzxx bdcDzzzZzxx);


    /**
     * zhangyu 将元数据xml类数据转移到bdcDzzzZzxx
     *
     * @param bdcDzzzZzxxYsj
     * @return bdcDzzzZzxx
     */
    BdcDzzzZzxx getBdcDzzzZzxxFromBdcDzzzZzxxYsj(BdcDzzzZzxxYsj bdcDzzzZzxxYsj, BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * zhangyu 将bdcDzzzZzxx数据转移到BdcDzzzZzxxYsj
     *
     * @param bdcDzzzZzxx
     * @return bdcDzzzZzxxYsj
     */
    BdcDzzzZzxxYsj getBdcDzzzZzxxYsjFromBdcDzzzZzxx(BdcDzzzZzxxYsj bdcDzzzZzxxYsj, BdcDzzzZzxx bdcDzzzZzxx);


    /**
     * zhangyu 将父类数据转移到子类
     *
     * @param bdcDzzzZzxx
     * @return
     */
    BdcDzzzJzjxxDO getBdcDzzzJzjxxDOFromBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * zhangyu 将BdcDzzzZzxx数据转到BdcDzzzMlxxDO实体
     *
     * @param bdcDzzzZzxx
     * @return
     */
    BdcDzzzMlxxDO getBdcDzzzMlxxDOFromBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx);


    /**
     * @param map
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 查询不动产电子证照信息
     */
    List<BdcDzzzZzxxDO> listBdcDzzzZzxx(Map map);

    /**
     * @param zzid
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 通过zzid生成不动产电子证照PDF
     */
    DzzzResponseModel createPdfBdcDzzzByZzid(String zzid);

    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 更新证照信息
     */
    int updateBdcDzzzByZzid(BdcDzzzZzxx bdcDzzzZzxx);


    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description
     */
    DzzzResponseModel deleteBdcDzzz(BdcDzzzZzxx bdcDzzzZzxx);

    /**
     * @param zzqzlj
     * @param zzid
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 更新证照状态
     */
    int updateBdcDzzzZzqzlj(String zzqzlj, String zzid);



    /**
     * @param zzlxdm 证照类型代码
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 通过照类型代码转换为证书类型
     */

    String getBdcDzzzZzxxZstypeByZzlxdm(String zzlxdm);

    /**
     * @param map
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 统计证照信息各地区分布数量
     */
    String countDzzzQuantitativeDistribution(Map map);


    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn bdcDzzzZzxx
     * @description 完善BdcDzzzZzxx 实体数据
     */

    BdcDzzzZzxx getPerfectBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx);


    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 组织数据 验证数据
     */
    DzzzResponseModel checkBdcDzzzZzxx(BdcDzzzZzxx bdcDzzzZzxx, List<String> resultList);

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 组织数据 验证数据
     */
    DzzzResponseModel checkBdcDzzzZzxxCreate(BdcDzzzZzxx bdcDzzzZzxx, List<String> resultList);


    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 获取证照编号
     */
    String getZzbhByDwdmAndLsh(String dwdm, String lsh);

    /**
     * @param map
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 统计证照信息
     */
    List<Map> countBdcDzzzZzxx(Map map);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzZzxxDO
     * @description 删除电子证照信息附件
     */
    void deleteBdcDzzzDO(BdcDzzzZzxxDO bdcDzzzZzxxDO);

    /**
     * @param map
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 统计证照签发和注销信息
     */
    String countZzxxZxAndQf(Map map);
}
