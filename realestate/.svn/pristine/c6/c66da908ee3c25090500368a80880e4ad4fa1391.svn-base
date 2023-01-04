package cn.gtmap.realestate.etl.core.mapper.td;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.etl.core.qo.TdsqQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TdMapper {

    /**
     * 分页查询土地数据
     * @param tdsqQO 土地信息
     * @return
     */
    List<BdcXmDO> listTdxxByTdPageOrder(TdsqQO tdsqQO);

    /**
     * 根据 projectid 将更新TBL_GYQDJK 的 isLogoutz 字段更改为 5
     * @param projectid 项目ID
     */
    void updateTblGyqdjkZt(@Param(value ="projectid") String projectid);

    /**
     * 根据查询新的地籍号信息
     */
    List<String> queryDjh(@Param(value="djh") String djh);

    /**
     * 查询国有土地使用证项目信息
     */
    List<BdcXmDO> queryGytdsyzXmxx(TdsqQO tdsqQO);
    /**
     * 查询他项权利证明书项目信息
     */
    List<BdcXmDO> queryTxqlzmsXmxx(TdsqQO tdsqQO);
    /**
     * 查询查封项目信息
     */
    List<BdcXmDO> queryCfXmxx(TdsqQO tdsqQO);
    /**
     * 查询集体土地使用证项目信息
     */
    List<BdcXmDO> queryJttdsyzXmxx(TdsqQO tdsqQO);

    /**
     * 查询国有土地使用证-建设用地使用权信息
     */
    List<BdcJsydsyqDO> queryGytdsyzJsydsyqxx(TdsqQO tdsqQO);
    /**
     * 查询集体土地使用证-建设用地使用权信息
     */
    List<BdcJsydsyqDO> queryJttdsyzJsydsyqxx(TdsqQO tdsqQO);
    /**
     * 查询国有土地使用证-查封信息
     */
    List<BdcCfDO> queryGytdsyzCfxx(TdsqQO tdsqQO);
    /**
     * 查询集体土地使用证-查封信息
     */
    List<BdcCfDO> queryJttdsyzCfxx(TdsqQO tdsqQO);
    /**
     * 查询国有土地使用证-抵押权信息
     */
    List<BdcDyaqDO> queryGytdsyzDyaqxx(TdsqQO tdsqQO);
    /**
     * 查询集体土地使用证-抵押权信息
     */
    List<BdcDyaqDO> queryJttdsyzDyaqxx(TdsqQO tdsqQO);
    /**
     * 查询国有土地使用证权利人信息
     */
    List<BdcQlrDO> queryGytdsyzQlrxx(TdsqQO tdsqQO);
    /**
     * 查询集体土地使用证权利人信息
     */
    List<BdcQlrDO> queryJttdsyzQlrxx(TdsqQO tdsqQO);
    /**
     * 查询他项权利证明书权利人信息
     */
    List<BdcQlrDO> queryTxqlzmsQlrxx(TdsqQO tdsqQO);
    /**
     * 查询他项权利证明书义务人信息
     */
    List<BdcQlrDO> queryTxqlzmsYwrxx(TdsqQO tdsqQO);
    /**
     * 查询查封权利人信息
     */
    List<BdcQlrDO> queryCfQlrxx(TdsqQO tdsqQO);
    /**
     * 查询查封义务人信息
     */
    List<BdcQlrDO> queryCfYwrxx(TdsqQO tdsqQO);
    /**
     * 查询国有土地使用证-项目证书关系信息
     */
    List<BdcXmZsGxDO> queryGytdsyzXmZsGxxx(TdsqQO tdsqQO);
    /**
     * 查询集体土地使用证-项目证书关系信息
     */
    List<BdcXmZsGxDO> queryJttdsyzXmZsGxxx(TdsqQO tdsqQO);
    /**
     * 查询他项权利证明书-项目证书关系信息
     */
    List<BdcXmZsGxDO> queryTxqlzmsXmZsGxxx(TdsqQO tdsqQO);
    /**
     * 查询国有土地使用证-证书信息
     */
    List<BdcZsDO> queryGytdsyzZsxx(TdsqQO tdsqQO);
    /**
     *  查询集体土地使用证-证书信息
     */
    List<BdcZsDO> queryJttdsyzZsxx(TdsqQO tdsqQO);
    /**
     * 查询国有土地使用证-证书信息
     */
    List<BdcZsDO> queryTxqlzmsZsxx(TdsqQO tdsqQO);
}
