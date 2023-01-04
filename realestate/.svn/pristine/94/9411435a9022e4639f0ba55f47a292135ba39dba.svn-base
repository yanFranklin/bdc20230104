package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhjgDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZhjgQO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.inquiry.service.BdcZhjgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 皮量查询服务imp
 */
@Service
public class BdcZhjgServiceImpl implements BdcZhjgService {

    @Autowired
    private Repo repo;

    /**
     * @param pageable 分页参数
     * @param bdcZhjgQO 查询Qo
     * List<BdcZhjgDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 综合监管
     */
    @Override
    public Page<BdcZhjgDTO> listBdcZhjgByPage(Pageable pageable, BdcZhjgQO bdcZhjgQO) {
        return repo.selectPaging("listBdcZhjgByPageOrder", bdcZhjgQO, pageable);
    }

    /**
     * @param bdcZhjgQO 查询Qo
     * List<BdcZhjgDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 综合监管
     */
    @Override
    public List listBdcZhjg(BdcZhjgQO bdcZhjgQO) {
        return repo.selectList("listBdcZhjg", bdcZhjgQO);
    }

}
