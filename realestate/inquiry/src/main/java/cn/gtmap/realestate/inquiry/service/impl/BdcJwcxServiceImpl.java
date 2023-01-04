package cn.gtmap.realestate.inquiry.service.impl;

import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcJwcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcJwcxQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.inquiry.service.BdcJwcxService;
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
public class BdcJwcxServiceImpl implements BdcJwcxService {

    @Autowired
    private Repo repo;
    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    /**
     * 分页纪委查询
     * @param pageable
     * @param bdcJwcxQO
     * @return Page
     */
    @Override
    public Page<BdcJwcxDTO> listBdcJwcxByPage(Pageable pageable, BdcJwcxQO bdcJwcxQO) {
        Page<BdcJwcxDTO> bdcJwcxDTOPage = repo.selectPaging("listBdcJwcxByPage", bdcJwcxQO, pageable);
        if (null == bdcJwcxDTOPage || CollectionUtils.isEmpty(bdcJwcxDTOPage.getContent())) {
            return bdcJwcxDTOPage;
        }
        // 获取不动产单元状态
        List<BdcJwcxDTO> bdcdyxxDTOList = bdcJwcxDTOPage.getContent();
        /// 1、先获取不动产单元号集合
        List<String> bdcdyhList = new ArrayList<>(bdcdyxxDTOList.size());
        for (BdcJwcxDTO bdcJwcxDTO : bdcdyxxDTOList) {
            bdcdyhList.add(bdcJwcxDTO.getBdcdyh());
        }
        /// 2、调用权籍获取状态
        List<BdcdyhZtResponseDTO> bdcdyhZtDTOList = bdcdyZtFeignService.commonListBdcdyhZtBybdcdyh(bdcdyhList,"");
        if (CollectionUtils.isEmpty(bdcdyhZtDTOList)) {
            return bdcJwcxDTOPage;
        }
        /// 3、匹配设置不动产单元状态
        for (BdcJwcxDTO bdcJwcxDTO : bdcdyxxDTOList) {
            for (BdcdyhZtResponseDTO bdcdyhZtDTO : bdcdyhZtDTOList) {
                if (StringUtils.equals(bdcJwcxDTO.getBdcdyh(), bdcdyhZtDTO.getBdcdyh())) {
                    bdcJwcxDTO.setBdcdyZtDTO(bdcdyhZtDTO);
                }
            }
        }
        return bdcJwcxDTOPage;
    }

}
