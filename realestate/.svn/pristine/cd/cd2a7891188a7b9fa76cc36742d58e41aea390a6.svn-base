package cn.gtmap.realestate.natural.core.service.impl.pzxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLcpzDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.natural.core.mapper.ZrzyXmMapper;
import cn.gtmap.realestate.natural.core.service.pzxx.ZrzyXtLcpzService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/26
 * @description 流程配置服务
 */
@Service
public class ZrzyXtLcpzServiceImpl implements ZrzyXtLcpzService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ZrzyXmMapper zrzyXmMapper;

    @Override
    public ZrzyXtLcpzDO queryZrzyXtLcpz(String gzldyid){
        if(StringUtils.isNotBlank(gzldyid)){
            return entityMapper.selectByPrimaryKey(ZrzyXtLcpzDO.class,gzldyid);
        }
        return null;
    }

    @Override
    public List<ZrzyXtLcpzDO> listZrzlcshPz(String gzlslid){
        if(StringUtils.isNotBlank(gzlslid)){
            return zrzyXmMapper.listZrzlcshPz(gzlslid);
        }
        return null;
    }
}
