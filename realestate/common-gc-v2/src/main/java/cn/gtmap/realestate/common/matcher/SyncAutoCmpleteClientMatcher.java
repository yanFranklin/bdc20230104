package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.workflow.clients.manage.SyncAutoCompleteClient;
import cn.gtmap.gtc.workflow.domain.manage.AutoCompleteDto;
import cn.gtmap.realestate.common.core.dto.AutoCompleteDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云流程自动办结处理 V2.x版本适配
 */
@Component
public class SyncAutoCmpleteClientMatcher {
    @Autowired
    private SyncAutoCompleteClient syncAutoCmpleteClient;

    /**
     *  处理流程自动办结出现的问题
     * @param autoCompleteDtoList
     * @return
     */
    public void syncAutoCmpleteDataList(List<AutoCompleteDTO> autoCompleteDtoList) {
        if(CollectionUtils.isEmpty(autoCompleteDtoList)) {
            return;
        }

        List<AutoCompleteDto> params = new ArrayList<>();
        for(AutoCompleteDTO autoCompleteDTO : autoCompleteDtoList) {
            AutoCompleteDto param = new AutoCompleteDto();
            param.setProcessInsId(autoCompleteDTO.getProcessInsId());
            param.setUserId(autoCompleteDTO.getUserId());
            param.setEndTime(autoCompleteDTO.getEndTime());
            params.add(param);
        }
        syncAutoCmpleteClient.syncAutoCompleteDataList(params);
    }
}
