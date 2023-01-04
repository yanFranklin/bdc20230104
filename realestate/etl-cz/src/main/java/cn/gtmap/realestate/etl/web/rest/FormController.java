package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.matcher.FormStateClientMatcher;
import cn.gtmap.gtc.formclient.common.client.FormThirdResourceClient;
import cn.gtmap.gtc.formclient.common.dto.FormStateDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/17
 * @description 表单中心
 */
@RestController
@RequestMapping("/realestate-etl/form")
public class FormController {

    /**
     * 表单服务
     */
    @Autowired
    private FormStateClientMatcher formStateClient;


    @Autowired
    FormThirdResourceClient formThirdResourceClient;


    /**
     * @param formViewKey 表单key
     * @return Object 表单权限状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取表单中心的权限状态
     */
    @ResponseBody
    @GetMapping("/{formViewKey}/{resourceName}")
    public Object queryFormState(@PathVariable(name = "formViewKey") String formViewKey, @PathVariable(name = "resourceName") String resourceName) {
        if (StringUtils.isBlank(formViewKey) || StringUtils.isBlank(resourceName)) {
            throw new MissingArgumentException("缺失formViewKey或resourceName值!");
        }
        List<FormStateDTO> list = formStateClient.listByFormViewKey(formViewKey, null, null);
        if(CollectionUtils.isNotEmpty(list)){
            return formThirdResourceClient.getElementWithChildConfig(list.get(0).getFormStateId(), resourceName);
        }
        return null;



    }
}
