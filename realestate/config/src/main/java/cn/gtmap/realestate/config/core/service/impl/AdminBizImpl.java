package cn.gtmap.realestate.config.core.service.impl;


import cn.gtmap.realestate.common.job.biz.AdminBiz;
import cn.gtmap.realestate.common.job.biz.model.HandleCallbackParam;
import cn.gtmap.realestate.common.job.biz.model.RegistryParam;
import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.config.job.thread.JobCompleteHelper;
import cn.gtmap.realestate.config.job.thread.JobRegistryHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  2017-07-27 21:54:20
 */
@Service
public class AdminBizImpl implements AdminBiz {


    @Override
    public ReturnT<String> callback(List<HandleCallbackParam> callbackParamList) {
        return JobCompleteHelper.getInstance().callback(callbackParamList);
    }

    @Override
    public ReturnT<String> registry(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registry(registryParam);
    }

    @Override
    public ReturnT<String> registryRemove(RegistryParam registryParam) {
        return JobRegistryHelper.getInstance().registryRemove(registryParam);
    }

}
