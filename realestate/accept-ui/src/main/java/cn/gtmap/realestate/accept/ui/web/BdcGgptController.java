package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.dto.inquiry.GgptxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcGgptFeginService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/11/03
 * @description 获取工改平台信息
 */
@RestController
@RequestMapping(value = "/ggpt")
public class BdcGgptController extends BaseController {

    @Autowired
    private BdcGgptFeginService bdcGgptFeginService;


    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 获取工改平台信息
     * @date : 2022/11/03
     */
    @ResponseBody
    @PostMapping("/ggxx")
    public Object queryGgptxx(@RequestBody String param) {
        if (StringUtils.isBlank(param)) {
            throw new AppException("查询条件不能为空");
        }
        JSONObject jsonObject = JSONObject.parseObject(param);
        String page = String.valueOf(jsonObject.get("page"));
        String size = String.valueOf(jsonObject.get("limit"));
        Pageable pageable = null;
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(size)){
            pageable = new PageRequest(Integer.valueOf(page), Integer.valueOf(size));
        }
        List<GgptxxDTO> list = bdcGgptFeginService.queryGgptxx(param);
        if (CollectionUtils.isNotEmpty(list)) {
            return addLayUiCode(new PageImpl(list, pageable, list.size()));
        }
        return null;
    }

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 保存附件
     * @date : 2022/11/03
     */
    @ResponseBody
    @PostMapping("/fjxz")
    public Object downloadFile(@RequestBody GgptxxDTO ggptxxDTO, @RequestParam("gzlslid") String gzlslid) throws IOException {
        return bdcGgptFeginService.downloadGgfj(ggptxxDTO, gzlslid);
    }
}

