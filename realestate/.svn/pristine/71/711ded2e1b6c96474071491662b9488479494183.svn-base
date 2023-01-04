package cn.gtmap.realestate.init.service.delete.impl;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.core.service.BdcLzrService;
import cn.gtmap.realestate.init.service.delete.InitBdcOtherDeleteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 领证人删除服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/5.
 * @description
 */
@Service
public class BdcLzrDeleteServiceImpl implements InitBdcOtherDeleteService {

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcLzrService bdcLzrService;

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
        //抓取楼盘表数据不做此处理
        if(CollectionUtils.isNotEmpty(list) && !sfzqlpbsj){
            //批量删除
            if(plDel || sfAllDel){
                String gzlslid=list.get(0).getGzlslid();
                if(StringUtils.isNotBlank(gzlslid)){
                    //删除领证人信息
                    bdcLzrService.deleteBdcLzrsByLzrxx(gzlslid,null,null);
                }
            }else{
                //删除对象的初始化实例表
                for(BdcXmDO bdcXmDO:list){
                    BdcLzrDO bdcLzrDO=new BdcLzrDO();
                    bdcLzrDO.setXmid(bdcXmDO.getXmid());
                    //删除领证人
                    List<BdcLzrDO> listLzr=bdcLzrService.queryBdcLzr(bdcLzrDO,null);
                    if(CollectionUtils.isNotEmpty(listLzr)){
                        deleteList.addAll(listLzr);
                    }
                }
            }
        }
        return deleteList;
    }
}
