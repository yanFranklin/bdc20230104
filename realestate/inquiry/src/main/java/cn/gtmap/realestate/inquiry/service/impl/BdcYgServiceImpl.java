package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.inquiry.core.mapper.BdcYgMapper;
import cn.gtmap.realestate.inquiry.service.BdcYgService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href ="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021-01-20
 * @description 不动产预告信息查询
 */
@Service
public class BdcYgServiceImpl implements BdcYgService {

    @Autowired
    private BdcYgMapper bdcYgMapper;

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @version 2020/06/23,1.0
     * @description 更加不动产单元号批量查询预告信息
     */
    @Override
    public List<BdcYgDO> listBdcYyByBdcdyhs(List<String> bdcdyhList) {
        if(CollectionUtils.isEmpty(bdcdyhList)){
            return Lists.newArrayList();
        }
        return bdcYgMapper.listBdcYgByBdcdyhs(bdcdyhList);
    }

    /**
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @param djh 地籍号
     * @description 根据地籍号查询预告
     */
    @Override
    public List<BdcYgDO> listBdcYgByDjh(String djh) {
        if(StringUtils.isBlank(djh)){
            return Lists.newArrayList();
        }
        return bdcYgMapper.listBdcYgByDjh(djh);
    }
}
