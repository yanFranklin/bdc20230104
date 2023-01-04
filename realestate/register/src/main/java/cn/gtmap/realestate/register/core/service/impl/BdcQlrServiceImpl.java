package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.mapper.BdcQlrMapper;
import cn.gtmap.realestate.register.core.service.BdcQlrService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/25
 * @description 权利人表更新服务实现接口
 */
@Service
public class BdcQlrServiceImpl implements BdcQlrService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcQlrServiceImpl.class);

    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = BdcQlrServiceImpl.class.getName();

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcQlrMapper bdcQlrMapper;

    /**
     * @param bdcGyqkDTO 不动产共有情况DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将共有情况更新到权利人表中
     */
    @Override
    public int updateQlrGyqk(BdcGyqkDTO bdcGyqkDTO) {
        if (CollectionUtils.isNotEmpty(bdcGyqkDTO.getQlrGyqk())) {
            Integer count = 0;
            for (Map<String, Object> map : bdcGyqkDTO.getQlrGyqk()) {
                for (Map.Entry entry : map.entrySet()) {
                    if (StringUtils.isNotBlank(entry.getValue().toString())) {
                        BdcQlrDO bdcQlrDO = new BdcQlrDO();
                        bdcQlrDO.setQlrid(entry.getKey().toString());
                        bdcQlrDO.setGyqk(entry.getValue().toString());
                        count += bdcQlrMapper.updateQlrGyqk(bdcQlrDO);
                    }
                }
            }
            return count;
        }
        return -1;
    }

    /**
     * @param bdcRyzdDTO 冗余字段
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新权利人表冗余字段
     */
    @Override
    public int updateQlrRyzd(BdcRyzdDTO bdcRyzdDTO) {
        // 获取权利人ID、不动产权证号关联关系集合
        Map<String, Object> bdcqzhMap = bdcRyzdDTO.getBdcqzh();
        if (MapUtils.isEmpty(bdcqzhMap)) {
            LOGGER.warn("{}：更新项目{}权利人表终止，原因：没有不动产权证号内容！", CLASS_NAME, bdcRyzdDTO.getXmid());
            return -1;
        }

        Integer count = 0;
        // 循环更新权利人表：设置不动产权证号
        for (Map.Entry<String, Object> entry : bdcqzhMap.entrySet()) {
            if (null == entry || null == entry.getValue() || StringUtils.isBlank(entry.getKey()) || StringUtils.isBlank(String.valueOf(entry.getValue()))) {
                LOGGER.warn("{}：更新项目{}对应权利人出现问题，权利人或者不动产权证号为空！", CLASS_NAME, bdcRyzdDTO.getXmid());
                continue;
            }

            BdcQlrDO bdcQlrDO = new BdcQlrDO();
            bdcQlrDO.setQlrid(entry.getKey());
            bdcQlrDO.setBdcqzh((String) entry.getValue());
            count += entityMapper.updateByPrimaryKeySelective(bdcQlrDO);
        }
        return count;
    }

    /**
     * @param bdcGyqkDTO 共有情况DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权利人的共有情况（权利人名称一致）
     */
    @Override
    public int updateQlrGyqkPl(BdcGyqkDTO bdcGyqkDTO) {
        if (StringUtils.isBlank(bdcGyqkDTO.getGzlslid()) && CollectionUtils.isEmpty(bdcGyqkDTO.getXmidList())) {
            throw new MissingArgumentException("缺失工作流实例ID或缺失xmidList！");
        }
        List<Map<String, Object>> qlrGyqkList = bdcGyqkDTO.getQlrGyqk();
        if (CollectionUtils.isEmpty(qlrGyqkList)) {
            LOGGER.info("参数中没有需要更新的权利人共有情况信息！");
            return -1;
        }
        Integer count = 0;
        for (Map<String, Object> qlrGyqk : qlrGyqkList) {
            for (Map.Entry entry : qlrGyqk.entrySet()) {
                String qlrid = entry.getKey().toString();
                if (StringUtils.isBlank(qlrid)) {
                    LOGGER.info("参数中没有需要更新的权利人ID信息！");
                    return -1;
                }
                BdcQlrDO bdcQlrDO = entityMapper.selectByPrimaryKey(BdcQlrDO.class, qlrid);
                if (null == bdcQlrDO) {
                    LOGGER.info("{}未查询到对应的权利人信息", qlrid);
                    return -1;
                }
                String qlrmc = bdcQlrDO.getQlrmc();
                qlrGyqk.put("qlrmc", qlrmc);
                qlrGyqk.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
                qlrGyqk.put("gyqk", entry.getValue().toString());

                if (StringUtils.isNotBlank(bdcGyqkDTO.getGzlslid())) {
                    // 工作流实例有值时，按照工作流实例更新
                    qlrGyqk.put("gzlslid", bdcGyqkDTO.getGzlslid());
                    count += bdcQlrMapper.updateQlrGyqkPl(qlrGyqk);
                } else if (CollectionUtils.isNotEmpty(bdcGyqkDTO.getXmidList())) {
                    // 需要更新的项目List有值时，根据xmidList更新
                    List<List> xmidLists = ListUtils.subList(bdcGyqkDTO.getXmidList(), 800);
                    for (List<String> xmidList : xmidLists) {
                        qlrGyqk.put("xmidList", xmidList);
                        count += bdcQlrMapper.batchUpdateQlrGyqk(qlrGyqk);
                    }
                }

            }
        }
        return count;
    }
}
