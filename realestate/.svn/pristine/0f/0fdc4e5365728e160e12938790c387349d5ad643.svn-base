package cn.gtmap.realestate.exchange.service.impl.inf.build;

import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-16
 * @description 发送请求
 */
@Service(value = "sendRequest")
public class BuildSendRequestServiceImpl implements BuildRequestService {

    /**
     * @param builder
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过builder 处理 请求和响应
     */
    @Override
    public void build(InterfaceRequestBuilder builder) {
        InterfaceRequestService requestService = builder.getInterfaceRequestService();
        if(requestService != null){
            requestService.wrapSendRequest(builder);
        }
    }
}
