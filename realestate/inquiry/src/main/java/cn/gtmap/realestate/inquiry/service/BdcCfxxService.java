package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCfDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCfxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJfxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 不动产查封信息
 */
public interface BdcCfxxService {


    /**
     * @param bdcCfxxQO 查询Qo
     * @param pageable 分页参数
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcCfxxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询查封信息
     */
    Page<BdcCfxxDTO> listBdcCfxxByPage(Pageable pageable, BdcCfxxQO bdcCfxxQO);

    /**
     * @param bdcXfxxQO 查询Qo
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcCfxxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询续封信息
     */
    List<Map> listBdcXfxx(BdcCfxxQO bdcXfxxQO);


    /**
     * @param bdcCfxxQO 查询Qo
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcCfxxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查封到期
     */
    Object bdcCfDqList(Pageable pageable, BdcCfxxQO bdcCfxxQO);


    /**
     * @param bdcJfxxQO 查询Qo
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 解封查封信息
     */
    void jfBdcCf(BdcJfxxQO bdcJfxxQO);

    /**
     * @param bdcJfxxQO 查询Qo
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新查封信息
     */
    void editBdcCf(BdcJfxxQO bdcJfxxQO);

    /**
     * 查询未到期续封信息
     * @param map
     * @return
     */
    int queryWdqXfByBdcyhs(Map map);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 根据项目id查询查封
     */
    List listBdcCfByXmid(String xmid);

    /**
     * @param bdcdyh
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/7
     * @description 根据不动产单元号查询查封信息
     */
    List listBdcCfByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询查封信息
     */
    List<BdcCfDO> listBdcCfByBdcdyhs(List<String> bdcdyhList);

    /**
     * @param bdcCfQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询查封信息
     * @date : 2021/7/30 13:51
     */
    List<BdcCfDTO> listBdcCfxx(BdcCfxxQO bdcCfQO);

    /**
     * @param bdcCfQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询续封信息并关联上一首查封的查封编号
     * @date : 2021/7/30 13:52
     */
    List<BdcCfDTO> getBdcCfxxAndYcfbh(BdcCfxxQO bdcCfQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID查询原查封信息
     */
    List<BdcCfDO> listYcfxxByGzlslid(String gzlslid);

    /**
     * @param bdcCfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取查封信息根据查封顺序和时间排序
     * @date : 2021/11/1 11:13
     */
    List<BdcCfDTO> listBdcCfxxByCfsx(BdcCfxxQO bdcCfxxQO);
}
