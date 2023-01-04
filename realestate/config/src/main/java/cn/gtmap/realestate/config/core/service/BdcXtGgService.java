package cn.gtmap.realestate.config.core.service;

import cn.gtmap.realestate.common.core.domain.BdcGgDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtGgDO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtGgVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/19
 * @description 不动产公告配置服务
 */
public interface BdcXtGgService {

    /**
      * @param pageable 分页信息
     * @param bdcXtGgDO 公告查询参数
      * @return 公告配置分页数据
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取公告配置分页数据
      */
    Page<BdcXtGgVO> listBdcXtGgByPage(Pageable pageable, BdcXtGgDO bdcXtGgDO);

    /**
      * @param bdcXtGgDO 公告配置信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 保存更新公告配置信息
      */
    int saveOrUpdateBdcXtGg(BdcXtGgDO bdcXtGgDO);

    /**
      * @param xtggidList 需要删除的公告配置集合
      * @return 删除数量
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 删除公告配置信息
      */
    int deleteBdcXtGg(List<String> xtggidList);

    /**
      * @param bdcXtGgDO 查询参数实体
      * @return 公告配置信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 查询公告配置信息
      */
    List<BdcXtGgDO> listBdcXtGg(BdcXtGgDO bdcXtGgDO);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid 项目ID
     * @param bdcXtGgDO 公告配置信息
     * @return sql执行后的公告信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置的sql生成公告信息
     */
    BdcGgDO generateXtggBySql(String gzlslid, String xmid, BdcXtGgDO bdcXtGgDO);

    /**
     * @param xmDOS 项目集合
     * @return 公告信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量根据项目获取配置的sql生成公告信息
     */
    List<BdcGgDO> generateXtggBySqlPl(List<BdcXmDO> xmDOS, Integer sply, Integer gglx);

}
