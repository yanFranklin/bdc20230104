package cn.gtmap.realestate.certificate.core.service.impl;


import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzZzxxCzztMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxCzztDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzxxCzztService;
import cn.gtmap.realestate.certificate.util.SM4Util;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description  不动产电子证照权利人表
 */

@Service
public class BdcDzzzZzxxCzztServiceImpl implements BdcDzzzZzxxCzztService {

    @Autowired
    private BdcDzzzZzxxCzztMapper bdcDzzzZzxxCzztMapper;

    /**
     * @param bdcDzzzZzxx 电子证照信息实体
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 插入权利人信息
     */
    @Override
    public int insertBdcDzzzZzxxCzztDo(BdcDzzzZzxx bdcDzzzZzxx) {
        int result = 0;
        if (CollectionUtils.isNotEmpty(bdcDzzzZzxx.getQlrxx())) {
            for (BdcDzzzZzxxCzztDo bdcDzzzZzxxCzztDo : bdcDzzzZzxx.getQlrxx()) {
                bdcDzzzZzxxCzztDo.setCzztid(UUIDGenerator.generate());
                bdcDzzzZzxxCzztDo.setZzid(bdcDzzzZzxx.getZzid());
                //加密证件号
                bdcDzzzZzxxCzztDo.setCzztdm(SM4Util.encryptData_ECB(bdcDzzzZzxxCzztDo.getCzztdm()));
                result = bdcDzzzZzxxCzztMapper.insertBdcDzzzZzxxCzztDo(bdcDzzzZzxxCzztDo);
            }
        }
        return result;
    }


    /**
     * @param zzid 证照id
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 查询证照对应的持证主体
     */
    @Override
    public List<BdcDzzzZzxxCzztDo> queryBdcDzzzZzxxCzztDoByZzid(String zzid) {
        if (StringUtils.isNotEmpty(zzid)) {
            return bdcDzzzZzxxCzztMapper.queryBdcDzzzZzxxCzztDoByZzid(zzid);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @param zzid 证照id
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 删除证照对应的持证主体
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delBdcDzzzZzxxCzztDoByZzid(String zzid) {
        if (StringUtils.isNotEmpty(zzid)) {
            bdcDzzzZzxxCzztMapper.delBdcDzzzZzxxCzztDoByZzid(zzid);
        }
    }
}
