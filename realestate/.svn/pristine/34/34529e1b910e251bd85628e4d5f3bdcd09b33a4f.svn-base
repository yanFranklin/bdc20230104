package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.domain.BdcTsywZdyzdxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcTsywPzDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcTsywPzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/5
 * @description 特殊业务配置服务
 */
public interface BdcTsywPzService {

    /**
     * @param pzid 配置ID
     * @return 特殊业务配置配置对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键获取YML配置
     */
    BdcTsywPzDO queryBdcTsywPzById(String pzid);

    /**
     * @param bdcTsywPzQO 不动产YML配置查询QO
     * @return 配置集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询YML配置
     */
    List<BdcTsywPzDO> listBdcTsywPz(BdcTsywPzQO bdcTsywPzQO);

    /**
     * @param bdcTsywPzQO 不动产特殊业务配置查询QO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询特殊业务配置
     */
    Page<BdcTsywPzDO> listBdcTsywPzByPage(Pageable pageable, BdcTsywPzQO bdcTsywPzQO);

    /**
     * @param bdcTsywPzQO 不动产特殊业务配置查询QO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 不分页查询特殊业务配置
     */
    List<BdcTsywPzDO> bdcTsywPzList(BdcTsywPzQO bdcTsywPzQO);

    /**
     * @param bdcTsywPzDO 更新特殊业务配置信息
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键更新YM配置信息
     */
    int updateBdcTsywPz(BdcTsywPzDO bdcTsywPzDO);

    /**
     * @param bdcTsywPzDO 特殊业务配置信息
     * @return 特殊业务配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 新增特殊业务配置置信息
     */
    BdcTsywPzDO insertBdcTsywPz(BdcTsywPzDO bdcTsywPzDO);

    /**
     * @param bdcTsywPzDOList 特殊业务配置信息
     * @return 特殊业务配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 新增特殊业务配置置信息
     */
    void insertBdcTsywPzPl(List<BdcTsywPzDO> bdcTsywPzDOList);

    /**
     * @param bdcTsywZdyzdxDO 查询对象
     * @return 自定义字典项
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询配置自定义字典项
     */
    List<BdcTsywZdyzdxDO> listZdyzdx(BdcTsywZdyzdxDO bdcTsywZdyzdxDO);

    /**
     * 获取redis中缓存的各个模块的配置内容，将配置内容持久化至数据库
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: void
     */
    void initYmlData();

    /**
     * @param pzid 配置ID
     * @return 特殊业务配置DTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过pzid获取特殊业务配置DTO
     */
    BdcTsywPzDTO queryBdcTsywPzDTO(String pzid);

    /**
     * @param bdcTsywPzDTO
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存特殊业务配置DTO
     */
    String saveBdcTsywPzDTO(BdcTsywPzDTO bdcTsywPzDTO);

    /**
     * @param pzid 特殊业务配置
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  删除特殊业务配置
     */
    int delBdcTsywPz(String pzid);

    /**
     * @param bdcTsywPzDTO 特殊业务配置DTO
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除特殊业务配置DTO
     */
    int delBdcTsywPzDTO(BdcTsywPzDTO bdcTsywPzDTO);

    /**
     * @param bdcTsywPzDO 当前配置项
     * @return 其他配置项
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据字典项标识获取当前配置项以外的配置项
     */
    List<BdcTsywPzDO> listOtherTsywPzByZdxbs(BdcTsywPzDO bdcTsywPzDO);

    /**
     * @param bdcTsywPzDOList 配置项列表
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 批量更新特殊业务配置的功能模块信息
     */
    void batchModifyTsywpzGnmk(List<BdcTsywPzDO> bdcTsywPzDOList);

    /**
     * @param ids 特殊业务配置内容ID集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 批量删除特殊业务配置内容
     */
    void batchDeleteTsywpz(List<String> ids);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    void syncGxjkmlZdyzdx();
}
