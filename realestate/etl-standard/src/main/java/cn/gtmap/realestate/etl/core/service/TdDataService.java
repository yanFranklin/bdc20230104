package cn.gtmap.realestate.etl.core.service;

import cn.gtmap.realestate.etl.core.qo.TdsqQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2021/11/25,1.0
 * @description 土地系统数据Serice
 */
public interface TdDataService {

    /**
     * @param pageable 分页信息
     * @param tdsqQO 土地查询信息
     * @return org.springframework.data.domain.Page<Map>
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 分页查询土地信息
     */
    Page<Map> listTdxxByPage(Pageable pageable, TdsqQO tdsqQO);

    /**
     * 导入土地信息数据
     * @param tdsqQO 土地申请QO
     */
    void importData(TdsqQO tdsqQO);

    /**
     * 根据项目ID 更新导入项目的状态
     * @param projectid 项目ID
     */
    void modifyImportStatus(String projectid);
}
