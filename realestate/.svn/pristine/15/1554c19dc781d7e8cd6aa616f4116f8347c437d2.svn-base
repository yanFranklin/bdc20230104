package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.inquiry.*;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcJkDtcxRestService;
import cn.gtmap.realestate.inquiry.common.DtcxCheckEnum;
import cn.gtmap.realestate.inquiry.common.DtcxConstants;
import cn.gtmap.realestate.inquiry.service.DtcxJkConfigService;
import cn.gtmap.realestate.inquiry.service.DtcxJkViewService;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2020/01/10
 * @description:
 */
@RestController
@Api(tags = "自定义查询")
public class BdcJkDtcxRestController extends BaseController implements BdcJkDtcxRestService {
    @Autowired
    DtcxJkViewService viewService;
    @Autowired
    DtcxJkConfigService dtcxConfigService;

    @Override
    public DtcxConfigCheckDTO checkCxtj(String cxjson,@RequestParam("cxtj") String cxtjjson) {
        List<DtcxCxtjDO> cxtjList = JSONObject.parseArray(cxtjjson, DtcxCxtjDO.class);

        if (org.apache.commons.lang3.StringUtils.isBlank(cxjson) || CollectionUtils.isEmpty(cxtjList)) {
            return null;
        }
        // 通过查询条件列表生成查询条件id列表
        // 判断条件字段id是否存在重复
        List cxtjidList = new ArrayList();
        List repeatTjidList = new ArrayList();
        Boolean repeatFlg = false;
        for (DtcxCxtjDO cxtj : cxtjList) {
            if (!cxtjidList.contains(cxtj.getTjzdid())) {
                cxtjidList.add(cxtj.getTjzdid());
            } else {
                repeatFlg = true;
                repeatTjidList.add(cxtj.getTjzdid());
            }
        }
        // 若存在重复 添加重复字段ID
        if (repeatFlg) {
            return new DtcxConfigCheckDTO(0, DtcxCheckEnum.CXTJ_REPEAT.getMc(), repeatTjidList);
        }

        return new DtcxConfigCheckDTO(1, DtcxConstants.SUCCESS, new ArrayList<>());
    }

    @Override
    public Object listResult(@RequestParam("dataString") String dataString, String jk,String jkid, String fhzkey, int page, int size) {
        Pageable pageable = new PageRequest(page, size);

        return viewService.listResultByPage(pageable, jk, jkid, fhzkey, JSONObject.parseObject(dataString));
    }
}