package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxsqsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCxsqsDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxsqsQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcCxsqsRestService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/8/22
 * @description
 */
@RestController
@RequestMapping(value = "/rest/v1.0/cxsqs")
public class BdcCxsqsController extends BaseController {

    @Autowired
    BdcCxsqsRestService bdcCxsqsRestService;
    @Autowired
    UserManagerClient userManagerClient;

    @GetMapping("/page")
    public Object listBdcCxsqs(@LayuiPageable Pageable pageable, BdcCxsqsQO bdcCxsqsQO) {
        Page<BdcCxsqsDO> bdcCxsqsDOPage = bdcCxsqsRestService.listBdcCxsqsPage(pageable, JSON.toJSONString(bdcCxsqsQO));
        return super.addLayUiCode(bdcCxsqsDOPage);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存查询申请书
     */
    @PostMapping("")
    BdcCxsqsDTO saveBdcCxsqs(@RequestBody BdcCxsqsDTO bdcCxsqsDTO) {
        try {
            UserDto userDto = userManagerClient.getCurrentUser();
            bdcCxsqsDTO.setCxry(userDto.getAlias());
        } catch (Exception e) {
            LOGGER.info("保存查询申请书信息异常，未获取到当前用户登录信息！");
        }
        bdcCxsqsDTO.setCxrq(new Date());
        return bdcCxsqsRestService.saveOrUpdateBdcCxsqs(bdcCxsqsDTO);
    }

    @GetMapping("/{sqsid}")
    BdcCxsqsDTO bdcCxsqsDTO(@PathVariable(name = "sqsid") String sqsid) {
        if (StringUtils.isBlank(sqsid)) {
            throw new MissingArgumentException("缺失查询参数：sqsid！");
        }
        BdcCxsqsQO bdcCxsqsQO = new BdcCxsqsQO();
        bdcCxsqsQO.setSqsid(sqsid);
        List<BdcCxsqsDTO> bdcCxsqsDTOList = bdcCxsqsRestService.queryBdcCxSqs(bdcCxsqsQO);
        if (CollectionUtils.isNotEmpty(bdcCxsqsDTOList)) {
            return bdcCxsqsDTOList.get(0);
        }
        return new BdcCxsqsDTO();
    }
}
