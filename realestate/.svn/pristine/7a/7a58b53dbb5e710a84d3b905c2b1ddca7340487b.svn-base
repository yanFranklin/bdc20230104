package cn.gtmap.realestate.inquiry.web.rest;


import cn.gtmap.realestate.common.core.domain.inquiry.BdcDsffjXxItemDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSlFjtzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcSlFjtzRestService;
import cn.gtmap.realestate.inquiry.service.BdcFjtzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BdcFjtzController implements BdcSlFjtzRestService {
    @Autowired
    BdcFjtzService bdcFjtzService;

    /**
     * 新增附件台账
     *
     * @param bdcSlFjtzDTO
     * @return
     */
    @Override
    public BdcDsffjXxItemDO addFjxx(@RequestBody BdcSlFjtzDTO bdcSlFjtzDTO) {
        if (StringUtils.isAnyBlank(bdcSlFjtzDTO.getBdcdyh(), bdcSlFjtzDTO.getDkbh(), bdcSlFjtzDTO.getZl())) {
            throw new AppException("缺失参数！");
        }
        return bdcFjtzService.addFjxx(bdcSlFjtzDTO);
    }
}
