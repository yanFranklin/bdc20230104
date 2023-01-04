package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import cn.gtmap.realestate.common.core.dto.config.BdcZdDsfzdgxDTO;
import cn.gtmap.realestate.common.core.vo.accept.ui.TreeNodeVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/6/17 16:03
 * @description 第三方字典项对照接口
 */
public interface BdcZdDsfzdgxService {

    /**
     * 查询第三方系统与登记系统字典项值对照关系
     *
     * @param bdcZdDsfzdgxDO bdcZdDsfzdgxDO
     * @return BdcZdDsfzdgxDO BdcZdDsfzdgxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    BdcZdDsfzdgxDO queryZdgx(BdcZdDsfzdgxDO bdcZdDsfzdgxDO);
    /**
     * 查询数据字典的中国省市区县的树形结构数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [] 无参数
     * @return: List<TreeNodeVO> 树形结构的List<TreeNode>
     */
    List<TreeNodeVO> queryZdZgSsqx();

    /**
     * 查询第三方字典项数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return map 字典项数据
     */
    Map<String, List<Map>> queryAllDsfZdgx();

    /**
     * 通过字典标识查询第三方字典项关系数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param zdbs     字典标识
     * @param dsfxtbs  第三方系统标识
     * @return key: zdbs,dsfxtbs  value: 第三方字典项对照关系Map结构List
     */
    Map<String, List<Map>> queryDsfZdgxBybs(String zdbs, String dsfxtbs);

    /**
     * 分页查询第三方字典项数据，按<code>zdbs</code>与<code>dsfxtbs</code>
     * 进行分组查询
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param pageable        分页参数
     * @param bdcZdDsfzdgxDO  查询参数
     * @return 按字典标识与第三方系统标识的分页数据
     */
    Page<BdcZdDsfzdgxDO> listDsfZdxByPage(Pageable pageable, BdcZdDsfzdgxDO bdcZdDsfzdgxDO);

    /**
     * 通过主键删除第三方配置项内容
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param id  配置项主键
     */
    void deleteDsfZdxById(String id);

    /**
     * 根据字典标识与第三方字典标识删除第三方字典项数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZdDsfzdgxDO 第三方字典项DO
     */
    void deleteDsfZdxBybs(BdcZdDsfzdgxDO bdcZdDsfzdgxDO);

    /**
     * 新增第三方字典项内容
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZdDsfzdgxDO 字典项配置内容
     * @return 字典项配置
     */
    BdcZdDsfzdgxDO insertDsfZdx(BdcZdDsfzdgxDO bdcZdDsfzdgxDO);

    /**
     * 修改第三方字典项内容
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZdDsfzdgxDO 字典项配置内容
     * @return 更新状态
     */
    int updateDsfZdx(BdcZdDsfzdgxDO bdcZdDsfzdgxDO);

    /**
     * 据字典标识与第三方字典标识获取第三方字典项数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZdDsfzdgxDO
     * @return 字典项数据集合
     */
    List<BdcZdDsfzdgxDO> queryDsfZdxBybs(BdcZdDsfzdgxDO bdcZdDsfzdgxDO);

    /**
     * 保存第三方字典关系数据
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcZdDsfzdgxDTO 字典项对照数据DTO
     * @return 字典项数据集合
     */
    List<BdcZdDsfzdgxDO> saveBdcZdDsfzdgx(BdcZdDsfzdgxDTO bdcZdDsfzdgxDTO);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 通过字典标识和第三方系统标识查询第三方字典项关系数据
      */
    List<Map> queryDsfZd(String zdbs,String dsfxtbs);

}
