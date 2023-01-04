package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInstanceClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.realestate.common.core.domain.BdcXtQtdjYwDO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcCommonRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 省级平台查询服务实现类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2019/09/4
 */
@RestController
public class BdcCommonRestController implements BdcCommonRestService {
    /**
     * 省级平台查询服务
     */
    @Autowired
    private ProcessInstanceClient processInstanceClient;

    @Autowired
    private EntityMapper entityMapper;


    @Override
    public BdcXtQtdjYwDO getxtpz(@RequestParam(value = "gzlslid") String gzlslid) {
        ProcessInstanceData processInstance = processInstanceClient.getProcessInstance(gzlslid);
        Example example = new Example(BdcXtQtdjYwDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gzldyid", processInstance.getProcessDefinitionKey());
        List<BdcXtQtdjYwDO> listQtdjYw = entityMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(listQtdjYw)) {
            return listQtdjYw.get(0);
        }
        return null;
    }

}
