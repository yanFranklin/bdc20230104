package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.mapper.BdcYzhMapper;
import cn.gtmap.realestate.certificate.core.service.BdcYzhScService;
import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/10/9
 * @description 生成证书所需印制号的服务接口，实现类
 */
@Service
public class BdcYzhScServiceImpl implements BdcYzhScService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYzhScServiceImpl.class);
    @Autowired
    BdcYzhMapper bdcYzhMapper;
    @Autowired
    EntityMapper entityMapper;

    @Value("${zsbd.yzZszmQxdmAndUserQxdm:true}")
    private Boolean yzZszmQxdmAndUserQxdm;


    /**
     * @param num 需要获取的印制号的数量
     * @return List<BdcYzhDTO> 印制号的信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量获取印制号(采用redis锁), 获取后直接先更新印制号的使用情况（专用于证书印制号的自动获取）
     */
    @Transactional(rollbackFor = Exception.class)
    @RedissonLock(lockKey = CommonConstantUtils.REDISSON_LOCK_BDCYZH, description = "获取印制号")
    @Override
    public List<BdcYzhDTO> queryBatchYzh(int num, BdcYzhQO bdcYzhQO) {
        // 对象中num参数对外隐藏，所以这里需要手动赋值
        bdcYzhQO.setNum(num);
        // 此处查询QO里面不应该有zsid的值
        bdcYzhQO.setZsid(null);
        bdcYzhQO.setSyqk(CommonConstantUtils.SYQK_YLY);
        // 设置一个印制号临时使用状态，避免并发查询
        int state = CommonConstantUtils.SYQK_QT;

        // 当前配置 不验证 区县代码时  设置qxdm查询条件为空
        if(!yzZszmQxdmAndUserQxdm){
            bdcYzhQO.setQxdm(null);
        }

        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhMapper.queryBdcYzh(bdcYzhQO);
        // 同时更新获取到的印制号为已使用
        if (CollectionUtils.isNotEmpty(bdcYzhDTOList) && bdcYzhDTOList.size() != num) {
            LOGGER.info("可用的印制号数量不足，剩余数量：{}",bdcYzhDTOList.size());
            return new ArrayList<>();
        }

        if (CollectionUtils.isNotEmpty(bdcYzhDTOList)) {
            for (BdcYzhDTO bdcYzhDTO : bdcYzhDTOList) {
                BdcYzhDO bdcYzhDO = new BdcYzhDO();
                bdcYzhDO.setYzhid(bdcYzhDTO.getYzhid());
                bdcYzhDO.setSyqk(state);
                entityMapper.updateByPrimaryKeySelective(bdcYzhDO);
            }
        }
        LOGGER.warn("查询印制号成功，查询条件：{}——(此时库中正常状态syqk={})，查询结果{}：", bdcYzhQO, state, bdcYzhDTOList);
        return bdcYzhDTOList;
    }

    /**
     * @param bdcYzhQO 印制号查询对象
     * @return BdcYzhDTO 最小的印制号对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询获取最小的印制号
     */
    public BdcYzhDTO getMinBdcYzh(BdcYzhQO bdcYzhQO) {
        // 当前配置 不验证 区县代码时  设置qxdm查询条件为空
        if(!yzZszmQxdmAndUserQxdm && null != bdcYzhQO){
            bdcYzhQO.setQxdm(null);
        }
        List<BdcYzhDTO> bdcYzhDTOList = bdcYzhMapper.queryBdcYzh(bdcYzhQO);
        if (CollectionUtils.isNotEmpty(bdcYzhDTOList) && null != bdcYzhDTOList.get(0)) {
            return bdcYzhDTOList.get(0);
        }
        return null;
    }
}
