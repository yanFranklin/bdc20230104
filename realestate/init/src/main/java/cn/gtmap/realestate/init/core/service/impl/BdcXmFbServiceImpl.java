package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.init.core.mapper.BdcXmFbMapper;
import cn.gtmap.realestate.init.core.service.BdcXmFbService;
import cn.gtmap.realestate.init.util.DataParseUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/11.
 * @description
 */
@Service
@Validated
public class BdcXmFbServiceImpl implements BdcXmFbService {
    @Autowired
    private BdcXmFbMapper bdcXmFbMapper;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public int updateBatchBdcXmFb(BdcDjxxUpdateQO bdcDjxxUpdateQO) {
        if (bdcDjxxUpdateQO == null || StringUtils.isAnyBlank(bdcDjxxUpdateQO.getJsonStr()) || MapUtils.isEmpty(bdcDjxxUpdateQO.getWhereMap())) {
            throw new NullPointerException("空参数异常！");
        }
        //获取更新json对象
        Map map = DataParseUtil.bdcDjxxUpdateQOParseSqlMap(bdcDjxxUpdateQO,BdcXmFbDO.class);
        if(MapUtils.isEmpty(map)){
            return 0;
        }else{
            return bdcXmFbMapper.updateBatchBdcXmFb(map);
        }
    }

    @Override
    public List<BdcXmFbDO> listBdcXmFb(BdcXmFbQO bdcXmFbQO) {
        return bdcXmFbMapper.listBdcXmFb(bdcXmFbQO);
    }

    /**
     * 通过不动产单元查询现势的sfszfgf状态
     * @param bdcdyh
     * @return
     */
    @Override
    public List<BdcXmDO> listBdcFgfXmByBdcdyh(String bdcdyh){
        Map map = new HashMap();
        map.put("bdcdyh",bdcdyh);
        return bdcXmFbMapper.listBdcFgfXmByBdcdyh(map);
    }

    /**
     * 根据业务信息查询关联权籍管理代码
     * @param bdcQjgldmQO 登记业务数据（例如单元号、证号等）
     * @return 权籍管理代码
     */
    @Override
    public String queryQjgldm(BdcQjgldmQO bdcQjgldmQO) {
        if(!CheckParameter.checkAnyParameter(bdcQjgldmQO)) {
            return null;
        }
        return bdcXmFbMapper.queryQjgldm(bdcQjgldmQO);
    }

    @Override
    public int updateZsrlzt(List<String> gzlslids,Integer zsrlzt){
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        Map<String, Object> map = new HashMap<>();
        map.put("gzlslids", gzlslids);
        bdcDjxxUpdateQO.setWhereMap(map);
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("zsrlzt",zsrlzt);
        bdcDjxxUpdateQO.setJsonStr(JSONObject.toJSONString(jsonObject));
        return updateBatchBdcXmFb(bdcDjxxUpdateQO);

    }
}

