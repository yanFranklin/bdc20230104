package cn.gtmap.realestate.init.service.sendMsg.impl;

import cn.gtmap.realestate.init.mapper.RunSqlMapper;
import cn.gtmap.realestate.init.service.sendMsg.BdcRunSqlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BdcRunSqlServiceImpl implements BdcRunSqlService {

    @Autowired
    private RunSqlMapper bdcRunSqlMapper;
    /**
     * @param
     * @return
     * @author <a href ="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 执行sql
     */
    @Override
    public List<HashMap> runSql(String sql) {
        if(StringUtils.isNotBlank(sql)){
            Map map=new HashMap();
            map.put("sql",sql);
            return bdcRunSqlMapper.runSql(map);
        }
        return Collections.emptyList();
    }
}
