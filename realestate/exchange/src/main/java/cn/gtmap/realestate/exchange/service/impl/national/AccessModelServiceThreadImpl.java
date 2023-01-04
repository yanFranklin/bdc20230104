package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.AccessModelServiceThread;
import cn.gtmap.realestate.exchange.service.national.AccesssModelHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessModelServiceThreadImpl implements AccessModelServiceThread {

    @Autowired
    private AccesssModelHandlerService accesssModelHandlerService;

    /**
     * @param xmidList
     * @return void
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 根据项目主键List 回报单个项目
     */
    @Override
    public void autoAccessByXmidList(List<String> xmidList) {
        accesssModelHandlerService.autoAccessByXmidList(xmidList);
    }
}
