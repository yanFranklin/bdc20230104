package cn.gtmap.realestate.init.service.tshz;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.service.InitService;

import java.util.Collections;
import java.util.List;

/**
 * 特殊后置服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/07/30.
 * @description
 */
public abstract class InitBdcTsHzAbstractService implements InitService {

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    /**
     *删除数据父接口
     *@param sfzqlpbsj 是否抽取楼盘表数据
     * @param plDel 是否批量业务删除
     * @param list 要删除的项目
     * @return 返回数据结构
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public List<Object> delete(List<BdcXmDO> list, Boolean sfzqlpbsj,Boolean sfdzbflpbsj,Boolean plDel) throws Exception {
        return Collections.emptyList();
    }

    /**
     * 当前项目业务数据查询接口
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcXmDO 查询的项目信息
     *@param initServiceDTO 返回数据
     *@return   返回数据结构
     *@description
     */
    @Override
    public InitServiceDTO query(BdcXmDO bdcXmDO, InitServiceDTO initServiceDTO) throws Exception{
        return initServiceDTO;
    }
}
