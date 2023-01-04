package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.config.BdcDysjPzDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcDysjPzDateQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/13
 * @description 打印数据配置
 */
public interface BdcDysjPzServce {
    /**
     * @param pageable
     * @param bdcDysjPzDateQO
     * @return 打印数据配置分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印数据配置分页数据
     */
    Page<BdcDysjPzDO> listBdcDysjPzByPage(Pageable pageable, BdcDysjPzDateQO bdcDysjPzDateQO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    BdcDysjPzDO queryBdcDysjPz(String id);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    BdcDysjZbPzDO queryBdcDysjZbPz(String id);

    /**
     * @param BdcDysjPzDO
     * @return 保存的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存打印数据配置配置
     */
    int saveBdcDysjPz(BdcDysjPzDO BdcDysjPzDO);

    /**
     * @param BdcDysjPzDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印数据配置配置
     */
    int updateBdcDysjPz(BdcDysjPzDO BdcDysjPzDO);

    /**
     * @param BdcDysjPzDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除不动产打印数据配置配置
     */
    int deleteBdcDysjPz(List<BdcDysjPzDO> BdcDysjPzDOList);


    //子表

    /**
     * @param pageable
     * @param bdcDysjZbPzDO
     * @return 打印数据配置分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印数据配置分页数据
     */
    Page<BdcDysjZbPzDO> listBdcDysjZbPzByPage(Pageable pageable, BdcDysjZbPzDO bdcDysjZbPzDO);

    /**
     * @param bdcDysjZbPzDO
     * @return 保存的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存打印数据配置配置
     */
    int saveBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO);

    /**
     * @param bdcDysjZbPzDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印数据配置配置
     */
    int updateBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO);

    /**
     * @param bdcDysjZbPzDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除不动产打印数据配置配置
     */
    int deleteBdcDysjZbPz(List<BdcDysjZbPzDO> bdcDysjZbPzDOList);

    /**
     * @param bdcDysjZbPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印子表配置数量
     */
    int countBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO);

    /**
     * @param dylx 打印类型
     * @return BdcDysjPzDTO 打印配置信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型查询主表和子表的配置信息，如果打印类型在库里重复，则会给出报错信息
     */
    BdcDysjPzDTO getPzxx(String dylx);

    /**
     * @param bdcDysjPzDTO 打印配置数据DTO
     * @return int 更新/保存的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存或更新打印配置信息
     */
    BdcDysjPzDTO saveOrUpdatePzxx(BdcDysjPzDTO bdcDysjPzDTO);

    /**
     * @param xml xml信息
     * @return String  redisKey 保存到redis中的key值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将xml信息保存到redis中，设置有效期为60秒
     */
    String sendXmlToRedis(String xml);

    /**
     * @param redisKey redis健
     * @return String xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 从redis中获取保存的xml信息
     */
    String getXmlFromRedis(String redisKey);

    /**
     * @param dylx 打印类型
     * @return BdcDysjPzDO 打印数据信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型获取打印主表配置信息
     */
    BdcDysjPzDO queryBdcDysjPzByDylx(String dylx);

    /**
     * @param dylxList 打印类型list
     * @return Map 各个打印类型的配置信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量获取各打印类型的配置信息
     */
    Map queryBdcDysjPzByDylx(List<String> dylxList);
    
    int checkDylx(String checkDylx);
}
