package cn.gtmap.realestate.certificate.service;

import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcGdxxQO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/11/16
 * @description 不动产归档业务
 */
public interface BdcGdxxService {

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param bdcGdxxDO
    * @return
    * @description 新增归档信息
    */
    void insertBdcGdxx(BdcGdxxDO bdcGdxxDO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcGdxxDO
     * @return
     * @description 根据xmid更新归档信息,存在则更新不存在则插入
     */
    void updateBdcGdxx(BdcGdxxDO bdcGdxxDO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param xmId
     * @return daId
     * @description 根据xmid查询daid
     */
    String getDaIdByXmId(String xmId);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param  archiveUrl,archiveXml
     * @return responseXml
     * @description 调用档案接口并获取归档结果xml
     */
    String postArchiveInfo(String archiveUrl, String archiveXml);

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param archiveResponseXml
     * @return List<BdcGdxxDO>
     * @description 解析档案返回的xml封装至BdcGdxxDO对象
     */
    List<BdcGdxxDO> initBdcGdxx(String archiveResponseXml);

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param bdcGdxxQO
    * @return List<BdcGdxxDO>
    * @description 查询归档信息集合
    */
    List<BdcGdxxDO> listBdcGdxx(BdcGdxxQO bdcGdxxQO);


    /**
     * 查询分页数据
     * @param pageable
     * @param map
     * @return
     */
    Page<BdcXmGdxxDTO> listBdcGdxxByPage(Pageable pageable, HashMap<String, Object> param, String showGdxxType);

    /**
    * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
    * @param gdxxid
    * @return BdcGdxxDO
    * @description 根据主键查询归档信息
    */
    BdcGdxxDO queryBdcGdxx(String gdxxid);

    /**
     * @param xmid
     * @return daid
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid查询对应的daid
     */
    String queryBdcDaid(String xmid);

    /**
     * @param xmid
     * @return
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据xmid删除归档信息
     */
    int deleteBdcGdxxByXmid(String xmid);
}
