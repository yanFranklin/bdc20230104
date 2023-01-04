package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcYztService;
import cn.gtmap.realestate.common.core.domain.accept.BdcVYztGyGdxmDkDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcGdspxxQO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收费信息数据服务
 */
@Service
public class BdcYztServiceImpl implements BdcYztService {
    @Autowired
    private Repo repo;


    /**
     * @param pageable
     * @param bdcGdspxxQOJson
     * @return Page<BdcVYztGyGdxmDkDO>
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 分页查询供地审批信息
     */
    @Override
    public Page<BdcVYztGyGdxmDkDO> listBdcGdspxxByPage(Pageable pageable, String bdcGdspxxQOJson) {
        BdcGdspxxQO bdcGdspxxQO = JSON.parseObject(bdcGdspxxQOJson, BdcGdspxxQO.class);
        return repo.selectPaging("listGdspxxByPage", bdcGdspxxQO, pageable);
    }
}
