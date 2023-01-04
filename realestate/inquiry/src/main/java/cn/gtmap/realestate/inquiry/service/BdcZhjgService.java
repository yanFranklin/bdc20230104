package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhjgDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZhjgQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 综合监管
 */
public interface BdcZhjgService {


    /**
     * @param bdcZhjgQO 查询Qo
     * @param pageable 分页参数
     * @return list<BdcZhjgDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 综合监管
     */
    Page<BdcZhjgDTO> listBdcZhjgByPage(Pageable pageable, BdcZhjgQO bdcZhjgQO);

    /**
     * @param bdcZhjgQO 查询Qo
     * @return list<BdcZhjgDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 综合监管
     */
    List listBdcZhjg(BdcZhjgQO bdcZhjgQO);


}
