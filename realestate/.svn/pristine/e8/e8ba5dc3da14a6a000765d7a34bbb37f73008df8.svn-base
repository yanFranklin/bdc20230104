package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcGzdjDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.init.BdcGzdjQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.init.core.mapper.BdcGzdjMapper;
import cn.gtmap.realestate.init.core.service.BdcGzdjService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/10
 * @description 更正登记
 */
@Service
public class BdcGzdjServiceImpl implements BdcGzdjService {
    private static final Logger logger = LoggerFactory.getLogger(BdcGzdjServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcGzdjMapper bdcGzdjMapper;

    @Override
    public List<BdcGzdjDO> listBdcGzdjByXmid(String xmid){
        if(StringUtils.isNotBlank(xmid)){
            Example example =new Example(BdcGzdjDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            return entityMapper.selectByExample(example);
        }
        return new ArrayList<>();
    }

    @Override
    public List<BdcGzdjDO> listBdcGzdj(BdcGzdjQO bdcGzdjQO){
        if (!CheckParameter.checkAnyParameter(bdcGzdjQO,"xmid","gzid")) {
            throw new MissingArgumentException("查询更正信息缺失参数"+JSONObject.toJSONString(bdcGzdjQO));
        }
        BdcGzdjDO bdcGzdjDO = new BdcGzdjDO();
        BeanUtils.copyProperties(bdcGzdjQO,bdcGzdjDO);
        return entityMapper.selectByObj(bdcGzdjDO);

    }

    @Override
    public void deleteBdcGzdjPl(List<String> xmidList){
        if(CollectionUtils.isEmpty(xmidList)) {
            return;
        }

        if(xmidList.size() < 1000) {
            bdcGzdjMapper.deleteBdcGzdjPl(xmidList);
        } else {
            int parts = xmidList.size() / 500;
            for (int i = 0; i <= parts; i++) {
                int start = i * 500;
                if (start > xmidList.size() - 1) {
                    break;
                }
                int end = (i + 1) * 500 - 1;
                if (end > xmidList.size() - 1) {
                    end = xmidList.size() - 1;
                }

                List<String> subList = xmidList.subList(start, end + 1);
                bdcGzdjMapper.deleteBdcGzdjPl(subList);
                logger.warn("删除更正登记数据集合开始位置：{}，结束位置：{}，集合大小：{}，第一个xmid:{}", start, end, subList.size(), subList.get(0));
            }
        }
    }
}
