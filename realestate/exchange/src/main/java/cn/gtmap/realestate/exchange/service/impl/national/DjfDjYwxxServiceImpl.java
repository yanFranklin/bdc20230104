package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.common.core.domain.exchange.DjfDjYwxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjYwxxMapper;
import cn.gtmap.realestate.exchange.service.national.DjfDjYwxxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-26
 * @description
 */
@Service
public class DjfDjYwxxServiceImpl implements DjfDjYwxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DjfDjYwxxServiceImpl.class);


    @Autowired
    private DjfDjYwxxMapper djfDjYwxxMapper;

    @Resource(name = "gxEntityMapper")
    private EntityMapper entityMapper;

    /**
     * @param xmid
     * @return cn.gtmap.realestate.common.core.domain.exchange.DjfDjYwxxDO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据XMID查询业务信息
     */
    @Override
    public DjfDjYwxxDO queryYwxxByXmid(String xmid) {
        if(StringUtils.isNotBlank(xmid)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("ywh",xmid);
            List<DjfDjYwxxDO> resultList = djfDjYwxxMapper.queryDjfDjYwxxList(paramMap);
            LOGGER.info("查询yyw信息：{}",resultList);
            if(CollectionUtils.isNotEmpty(resultList)){
                // 批量的历史关系yywh多个,要以逗号分割 added by cyc at 2020/10/8
                String yywh = "";
                if(CollectionUtils.isNotEmpty(resultList) && resultList.size() >1){
                    for(DjfDjYwxxDO djfDjYwxxDO : resultList){
                        yywh += djfDjYwxxDO.getYywh()+",";
                    }
                }
                if(StringUtils.isNotBlank(yywh)){
                    yywh = yywh.substring(0,yywh.length()-1);
                    resultList.get(0).setYywh(yywh);
                }
                return resultList.get(0);
            }

        }
        return null;
    }

    /**
     * @param djfDjYwxxDO
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存业务信息
     */
    @Override
    public void saveYwxx(DjfDjYwxxDO djfDjYwxxDO) {
        if(djfDjYwxxDO != null && StringUtils.isNotBlank(djfDjYwxxDO.getYwh())){
            djfDjYwxxDO.setUpdatetime(new Date());
            entityMapper.saveOrUpdate(djfDjYwxxDO,djfDjYwxxDO.getYwh());
        }
    }
}
