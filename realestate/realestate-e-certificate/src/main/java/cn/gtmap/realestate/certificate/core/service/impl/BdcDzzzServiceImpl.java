package cn.gtmap.realestate.certificate.core.service.impl;

import cn.gtmap.realestate.certificate.core.model.ResponseData;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/28
 */
@Service
public class BdcDzzzServiceImpl implements BdcDzzzService {
    @Autowired
    private Repo repository;
    /**
     * @param obj 响应数据
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn DzzzResponseModel  返回信息
     * @description 响应数据处理, 成功
     */
    @Override
    public DzzzResponseModel dzzzResponseSuccess(Object obj) {
        return dzzzResponse(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), obj);
    }

    @Override
    public DzzzResponseModel dzzzResponseSuccess(String message, Object obj){
        return dzzzResponse(ResponseEnum.SUCCESS.getCode(), message, obj);
    }

    @Override
    public DzzzResponseModel dzzzResponseFalse(String message, Object obj) {
        return dzzzResponse(ResponseEnum.FALSE.getCode(), message, obj);
    }

    @Override
    public DzzzResponseModel dzzzResponseFalse(String msg1, String msg2) {
        return dzzzResponseFalse(msg1, new ResponseData(null, msg2));
    }

    /**
     * @param message 异常信息
     * @param status  响应状态
     * @param obj     响应数据
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn DzzzResponseModel  返回信息
     * @description 响应数据处理（正常返回信息）
     */
    @Override
    public DzzzResponseModel dzzzResponse(String status, String message, Object obj) {
        DzzzResponseModel dzzzResponseModel = new DzzzResponseModel();
        ResponseHead head = new ResponseHead();
        head.setStatus(status);
        head.setMessage(message);
        dzzzResponseModel.setHead(head);
        dzzzResponseModel.setData(obj);
        return dzzzResponseModel;
    }

    @Override
    public <T> Page<T> selectPaging(String statement, Object parameter, Pageable pageable) {
        return repository.selectPaging(statement, parameter, pageable);
    }
}
