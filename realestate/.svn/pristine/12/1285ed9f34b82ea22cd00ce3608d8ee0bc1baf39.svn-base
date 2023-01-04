package cn.gtmap.realestate.inquiry.ui.web.rest.nantong;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.dazt.DaztDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.DaxxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcdyXxCxFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/1/24
 * @description 档案柜查询服务
 */
@RestController
@RequestMapping("/rest/v1.0/dag")
public class BdcDaztcxController extends BaseController {
    @Autowired
    private BdcdyXxCxFeignService bdcdyXxCxFeignService;

    /**
     * 根据gzlslid查询该流程的档案柜信息
     *
     * @param gzlslid
     * @return
     * @Date 2022/1/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public Object daxxxxListByPage(@LayuiPageable Pageable pageable, DaxxQO daxxQO) {
        if (null == daxxQO && StringUtils.isNotBlank(daxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺少gzlslid参数！");
        }
        Page<DaztDTO> daztDTOPage = bdcdyXxCxFeignService.listDagxxByPage(pageable, JSON.toJSONString(daxxQO));
        return super.addLayUiCode(daztDTOPage);
    }

}
