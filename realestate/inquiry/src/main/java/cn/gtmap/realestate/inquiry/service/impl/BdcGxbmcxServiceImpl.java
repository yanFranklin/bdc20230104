package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxbmcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcGxbmcxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.inquiry.service.BdcGxbmcxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description
 */
@Service
public class BdcGxbmcxServiceImpl implements BdcGxbmcxService {

    @Autowired
    private Repo repo;
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    /**
     * 分页共享部门查询
     * @param pageable
     * @param bdcGxbmcxQO
     * @return Page
     */
    @Override
    public Page<BdcGxbmcxDTO> listBdcGxbmcxByPage(Pageable pageable, BdcGxbmcxQO bdcGxbmcxQO) {
        Page<BdcGxbmcxDTO> bdcGxbmcxDTOPage = repo.selectPaging("listBdcGxbmcxByPage", bdcGxbmcxQO, pageable);
        if (null == bdcGxbmcxDTOPage || CollectionUtils.isEmpty(bdcGxbmcxDTOPage.getContent())) {
            return bdcGxbmcxDTOPage;
        }
        // 获取不动产单元状态
        List<BdcGxbmcxDTO> bdcdyxxDTOList = bdcGxbmcxDTOPage.getContent();
        /// 1、先获取不动产单元号集合
        List<String> bdcdyhList = new ArrayList<>(bdcdyxxDTOList.size());
        for (BdcGxbmcxDTO bdcGxbmcxDTO : bdcdyxxDTOList) {
            bdcdyhList.add(bdcGxbmcxDTO.getBdcdyh());
        }
        /// 2、调用权籍获取状态
        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(bdcdyhList,"");
        if (CollectionUtils.isEmpty(bdcdyhZtDTOList)) {
            return bdcGxbmcxDTOPage;
        }
        /// 3、匹配设置不动产单元状态
        for (BdcGxbmcxDTO bdcGxbmcxDTO : bdcdyxxDTOList) {
            for (BdcdyhZtResponseDTO bdcdyhZtDTO : bdcdyhZtDTOList) {
                if (StringUtils.equals(bdcGxbmcxDTO.getBdcdyh(), bdcdyhZtDTO.getBdcdyh())) {
                    bdcGxbmcxDTO.setBdcdyZtDTO(bdcdyhZtDTO);
                }
            }
        }
        return bdcGxbmcxDTOPage;
    }

}
