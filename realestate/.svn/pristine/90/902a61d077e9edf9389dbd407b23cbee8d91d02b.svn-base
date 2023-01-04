package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.register.service.BdcXnBdcdyhService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 虚拟不动产单元号服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/10/09 18:17
 */
@Service
public class BdcXnBdcdyhServiceImpl implements BdcXnBdcdyhService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXnBdcdyhServiceImpl.class);
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private BdcInitFeignService bdcInitFeignService;

    /**
     * 生成虚拟不动产单元号，修改项目表中对应的 bdcdyh
     * 虚拟号设置规则： bdcdyhPrefix + 0000000X（X 表示顺序号）
     *
     * @param bdcdyhPrefix 不动产单元号前缀
     * @param bdcSlxxDTO   不动产受理信息DTO
     * @return List<BdcXmDO> 初始化后的项目信息
     */
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_XNBDCDYH, description = "获取最大虚拟不动产单元号",waitTime = 10L,leaseTime = 120L)
    @Override
    public List<BdcXmDO> updateXnbdcdyh(String bdcdyhPrefix, BdcSlxxDTO bdcSlxxDTO) throws Exception {
        String bdcdyhXn;
        // 获取数据库中最大的不动产单元号
        String bdcdyh = bdcXmMapper.queryMaxXnBdcdyh(bdcdyhPrefix);
        // 获取最后的 8 位顺序
        String order = StringUtils.substring(bdcdyh, 20, 28);
        // 数据库中没有对应的虚拟单元号 或 数据问题 没有后面 8 位，就用 0 补齐
        if (StringUtils.isBlank(bdcdyh) || StringUtils.isBlank(order)) {
            bdcdyhXn = StringUtils.rightPad(bdcdyhPrefix, 28, "0");
        } else {
            // 顺序号 + 1
            Integer number = Integer.valueOf(order) + 1;
            // 转换后避免位数不够的情况补齐 0
            String bdcdyhSuffix = number.toString();
            bdcdyhXn = bdcdyhSuffix.length() < 8 ? bdcdyhPrefix + StringUtils.leftPad(bdcdyhSuffix, 8, "0") : bdcdyhPrefix + bdcdyhSuffix;
        }
        // 设置完整的不动产单元号
        bdcSlxxDTO.getBdcSlXmList().get(0).getBdcSlXm().setBdcdyh(bdcdyhXn);
        LOGGER.error("信息补录，虚拟不动产单元号初始化：{}", JSONObject.toJSONString(bdcSlxxDTO));
        return bdcInitFeignService.csh(bdcSlxxDTO);
    }

}
