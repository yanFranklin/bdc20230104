package cn.gtmap.realestate.inquiry.service.bengbu.impl;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcNwCjRzDTO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.inquiry.core.mapper.BdcNwYwCjRzMapper;
import cn.gtmap.realestate.inquiry.service.bengbu.BdcNwCjRzService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href ="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021/01/25
 * @description 蚌埠_互联网+业务内网创建日志查询
 */
@Service
public class BdcNwCjRzServiceImpl implements BdcNwCjRzService {

    @Autowired
    private BdcNwYwCjRzMapper bdcNwYwCjRzMapper;

    @Autowired
    private Repository repository;

    @Override
    public Page<BdcNwCjRzDTO> listNwCjRz(Pageable pageable, String bdcNwCjRzQOStr) {
        Map<String, Object> paramMap = PageUtils.pageableSort(pageable, JSONObject.parseObject(bdcNwCjRzQOStr, HashMap.class));
        Page<BdcNwCjRzDTO> result = repository.selectPaging("listNwCjRzByPage", paramMap, pageable);
        return result;
    }

    @Override
    public int countFailedRecord() {
        return bdcNwYwCjRzMapper.countFailedRecord();
    }
}
