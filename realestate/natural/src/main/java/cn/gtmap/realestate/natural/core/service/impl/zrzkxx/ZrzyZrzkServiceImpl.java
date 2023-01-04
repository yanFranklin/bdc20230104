package cn.gtmap.realestate.natural.core.service.impl.zrzkxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyZrzk;
import cn.gtmap.realestate.common.core.enums.natural.ZrzyZrzklxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.natural.core.service.zrzkxx.ZrzyZrzkService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源自然状况服务
 */
@Service
public class ZrzyZrzkServiceImpl implements ZrzyZrzkService {

    @Autowired
    private EntityMapper entityMapper;

    @Override
    public  <T extends ZrzyZrzk> List<T> listZrzyZrzkList(String xmid, Class<?> zrzkClass){
        if(StringUtils.isNotBlank(xmid) &&zrzkClass != null){
            Example zrzkExample = new Example(zrzkClass);
            zrzkExample.createCriteria().andEqualTo("xmid", xmid);
            List<T> zrzyZrzkList = entityMapper.selectByExample(zrzkExample);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                return zrzyZrzkList;
            }

        }
        return Collections.emptyList();

    }

    @Override
    public ZrzyZrzk queryZrzk(String zkxxid,String zrzklx){
        if(StringUtils.isNoneBlank(zkxxid,zrzklx)) {
            //确定类型
            Class<?> zrzkClass = ZrzyZrzklxEnum.getZrzkClassByCode(zrzklx);
            if(zrzkClass ==null){
                throw new AppException("自然状况类型错误,请检查");
            }
            Example zrzkExample = new Example(zrzkClass);
            zrzkExample.createCriteria().andEqualTo("zkxxid", zkxxid);
            return (ZrzyZrzk) entityMapper.selectByPrimaryKey(zrzkClass,zkxxid);
        }
        return null;

    }



}
