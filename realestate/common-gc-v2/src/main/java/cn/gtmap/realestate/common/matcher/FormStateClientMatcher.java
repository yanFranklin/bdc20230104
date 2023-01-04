package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.formclient.common.client.FormStateClient;
import cn.gtmap.gtc.formclient.common.dto.FormStateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  表单状态 V2.x版本适配
 */
@Component
public class FormStateClientMatcher {
    @Autowired
    private FormStateClient formStateClient;

    /**
     * 获取表单状态(参数按照V1.X版本组织，对业务层统一)
     * @param formViewKey
     * @param operator
     * @param url
     * @return
     */
    public List<FormStateDTO> listByFormViewKey(String formViewKey, Integer operator, String url) {
        return formStateClient.listByFormViewKey(formViewKey, operator, url);
    }

}
