package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.BdcDzqzPdfService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzService;
import cn.gtmap.realestate.certificate.core.service.sign.BdcDzqzService;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 电子签章pdf 实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 10:07
 **/
@Service
public class BdcDzqzServiceImpl implements BdcDzqzPdfService {
    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 验证电子签章信息
     * @date : 2022/3/10 10:04
     */
    @Autowired
    private BdcDzqzService bdcDzqzService;
    @Autowired
    BdcDzzzService bdcDzzzService;

    @Override
    public DzzzResponseModel checkBdcDzqz(BdcDzzzZzxx bdcDzzzZzxx) {
        //数据组织与数据验证
        List<String> resultList = new ArrayList<>(2);
        resultList.add("zzbh");
        resultList.add("zzbs");
        return bdcDzqzService.checkBdcDzzzQzxx(bdcDzzzZzxx, resultList);
    }

    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增电子签章信息
     * @date : 2022/3/10 11:33
     */
    @Override
    public DzzzResponseModel insertBdcDzqz(BdcDzzzZzxx bdcDzzzZzxx) {
        DzzzResponseModel insertBdcDzzzZzxxModel = bdcDzqzService.insertBdcDzzzQzxx(bdcDzzzZzxx);
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), insertBdcDzzzZzxxModel.getHead().getStatus())) {
            return insertBdcDzzzZzxxModel;
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put("zzbs", bdcDzzzZzxx.getZzbs());
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), map);
    }
}
