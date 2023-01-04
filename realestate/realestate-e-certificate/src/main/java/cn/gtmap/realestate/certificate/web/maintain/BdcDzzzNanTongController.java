package cn.gtmap.realestate.certificate.web.maintain;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzCxtjMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzCxtjDo;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.web.main.DzzzController;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/18
 */
@Controller
@Api("电子证照南通")
@RequestMapping(value = "/realestate-e-certificate/Nt")
public class BdcDzzzNanTongController extends DzzzController {

    @Autowired
    private UserManagerUtils userManagerUtils;
    @Autowired
    private BdcDzzzCxtjMapper bdcDzzzCxtjMapper;


    /*@RequestMapping(value = "/toZzxxNtYhList", method = RequestMethod.GET)
    @ApiOperation(value = "南通电子证照银行台账展示", notes = "南通电子证照银行台账展示")
    public String toZzxxCzList(HttpServletRequest request, Model model) {
        logger.info("南通电子证照银行台账展示：{}，请求时间：{}", request.getRequestURI(), DateUtil.now());
        return "dzzzgl/nantong/bdcDzzzZzxxYhList";
    }*/

    @ResponseBody
    @RequestMapping(value = "/listNtYhZzxxByPageJson", method = RequestMethod.POST)
    @ApiOperation(value = "南通电子证照银行台账数据搜索", notes = "南通电子证照银行台账数据搜索")
    public Object listNtYhZzxxByPageJson(@LayuiPageable Pageable pageable, String bdcqzh, String zl, String bdcdyh, String czzt, String sort) {
        Map paramMap = new HashMap<>();

        boolean queryFlag = false;
        paramMap.put("zzlxdm", Constants.BDC_ZZLXDM_ZM);

        UserDto userDto = userManagerUtils.getCurrentUser();
        logger.info("南通电子证照银行台账展示，登录用户：{}", userDto);
        if (null != userDto) {
            logger.info("南通电子证照银行台账展示，登录用户名称：{}", userDto.getUsername());
            BdcDzzzCxtjDo bdcDzzzCxtjDo = bdcDzzzCxtjMapper.queryBdcDzzzCxtjByUserName(userDto.getUsername());
            if (null != bdcDzzzCxtjDo) {
                String czzts = bdcDzzzCxtjDo.getCzzt();
                if (StringUtils.isNotBlank(czzts)) {
                    paramMap.put("czztArr", czzts.split(","));
                    queryFlag = true;
                }
            }
        }

        if (!queryFlag) {
            return new PageImpl(new ArrayList<>());
        }

        if (StringUtils.isNotBlank(bdcqzh)) {
            paramMap.put("bdcqzh", StringUtils.deleteWhitespace(bdcqzh));
        }
        if (StringUtils.isNotBlank(zl)) {
            paramMap.put("zl", StringUtils.deleteWhitespace(zl));
        }
        if (StringUtils.isNotBlank(bdcdyh)) {
            paramMap.put("bdcdyh", StringUtils.deleteWhitespace(bdcdyh));
        }
        if (StringUtils.isNotBlank(czzt)) {
            paramMap.put("czzt", StringUtils.deleteWhitespace(czzt));
        }
        if (StringUtils.isNotBlank(sort)) {
            paramMap.put("sort", StringUtils.deleteWhitespace(sort));
        }
        return bdcDzzzService.selectPaging("getBdcDzzzZzxxByPage", paramMap, pageable);
    }

}
