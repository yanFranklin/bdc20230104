package cn.gtmap.realestate.init.service.delete.impl;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.service.delete.InitBdcOtherDeleteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 审核信息删除服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/5.
 * @description
 */
@Service
public class BdcShxxDeleteServiceImpl implements InitBdcOtherDeleteService {

    @Autowired
    private EntityMapper entityMapper;

    /**
     * 删除服务
     * @param list
     * @param sfzqlpbsj
     * @param sfdzbflpbsj
     * @param plDel
     * @return
     * @throws Exception
     */
    @Override
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj, Boolean sfdzbflpbsj, Boolean plDel,Boolean sfAllDel) throws Exception {
        List<Object> deleteList=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            String gzlslid=list.get(0).getGzlslid();
            if(StringUtils.isNotBlank(gzlslid)){
                //抓取楼盘表数据不做此处理  批量删除或者全部删除的才做此处理
                if(!sfzqlpbsj && (sfAllDel || plDel)){
                    //删除审核信息
                    Example example=new Example(BdcShxxDO.class);
                    example.createCriteria().andEqualTo("gzlslid",gzlslid);
                    entityMapper.deleteByExample(example);
                }
            }
        }
        return deleteList;
    }
}
