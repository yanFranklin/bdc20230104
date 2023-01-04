package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.mapper.BdcSlDyaqMapper;
import cn.gtmap.realestate.accept.core.service.BdcSlDyaqService;
import cn.gtmap.realestate.accept.core.service.BdcSlQlxxService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlDyaqDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlDeleteCsDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/30
 * @description 受理抵押权服务
 */
@Service
public class BdcSlDyaqServiceImpl implements BdcSlDyaqService,BdcSlQlxxService {

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcSlDyaqMapper bdcSlDyaqMapper;

    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        set.add(BdcSlDyaqDO.class.getSimpleName());
        set.add(CommonConstantUtils.QLLX_DYAQ_DM.toString());
        return set;
    }

    @Override
    public BdcSlQl queryBdcSlQl(String xmid){
        return queryBdcSlDyaqByXmid(xmid);
    }

    @Override
    public BdcSlDyaqDO queryBdcSlDyaqByXmid(String xmid){
        BdcSlDyaqDO bdcSlDyaqDO =null;
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcSlDyaqDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("xmid", xmid);
            List<BdcSlDyaqDO> bdcSlDyaqDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlDyaqDOList)) {
                bdcSlDyaqDO =bdcSlDyaqDOList.get(0);
            }
        }

        return bdcSlDyaqDO;

    }

    @Override
    public void deleteBdcSlDyaq(BdcSlDeleteCsDTO bdcSlDeleteCsDTO){
        if (!CheckParameter.checkAnyParameter(bdcSlDeleteCsDTO)) {
            throw new AppException("删除参数为空" + JSONObject.toJSONString(bdcSlDeleteCsDTO));
        }
        bdcSlDyaqMapper.deleteBdcSlDyaq(bdcSlDeleteCsDTO);

    }

    @Override
    public void updateBdcSlDyaqRyzdPl(Map map){
        if(MapUtils.isNotEmpty(map) &&map.get("xmidList") != null &&map.get("zwr") != null){
            bdcSlDyaqMapper.updateBdcSlDyaqRyzdPl(map);
        }


    }

    @Override
    public List<BdcSlDyaqDO> listSlDyaqPl(List<String> xmidList){
        List<BdcSlDyaqDO> bdcSlDyaqDOList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(xmidList)){
            List<List> lists = ListUtils.subList(xmidList, 500);
            for (List partXmids : lists) {
                Example example =new Example(BdcSlDyaqDO.class);
                example.createCriteria().andInWithListString("xmid",partXmids);
                List<BdcSlDyaqDO> slDyaqDOS=entityMapper.selectByExampleNotNull(example);
                if(CollectionUtils.isNotEmpty(slDyaqDOS)){
                    bdcSlDyaqDOList.addAll(slDyaqDOS);
                }
            }
        }
        return bdcSlDyaqDOList;
    }
}
