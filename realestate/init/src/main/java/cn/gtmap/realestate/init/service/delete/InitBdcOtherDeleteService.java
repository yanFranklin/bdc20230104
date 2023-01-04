package cn.gtmap.realestate.init.service.delete;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;

import java.util.List;

/**
 * 初始化其他数据删除服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/17.
 * @description
 */
public interface InitBdcOtherDeleteService {

    /**
     * 删除服务
     * @param list
     * @param sfzqlpbsj
     * @param sfdzbflpbsj
     * @param plDel
     * @param sfAllDel
     * @return
     * @throws Exception
     */
     List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel,Boolean sfAllDel) throws Exception;
}
