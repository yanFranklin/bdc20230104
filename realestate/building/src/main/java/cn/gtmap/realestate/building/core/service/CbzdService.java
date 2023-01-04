package cn.gtmap.realestate.building.core.service;

import cn.gtmap.realestate.common.core.domain.building.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description
 */
public interface CbzdService {
    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询承包宗地
     */
    CbzdDcbDO queryCdzdDcbByBdcdyh(String bdcdyh);
    
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.CbzdDcbDO
     * @description 根据DJH查询承包宗地
     */
    CbzdDcbDO queryCbzdDcbByDjh(String djh);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jtIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydJtcyDO>
     * @description 根据家庭成员外键 查询家庭成员
     */
    List<NydJtcyDO> listJtcy(String jtIndex);

    /**
     *
     * @param djh
     * @return 查询备份承包调查表
     */
    HCbzdDcbDO queryHCbzdDcbByDjh(String djh);

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZhQlrDo>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 根据BDCDYH查询承包宗地承包方信息
     */
    List<CbzdCbfDO> listCbfByBdcdyh(String bdcdyh);

    /**
     * @param cbzdDcbcbfrelIndex 承包方与地块关系主键
     * @return 承包方信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据承包方地块关系主键查询承包宗地承包方信息
     */
    List<CbzdCbfDO> listCbfByCbzdDcbcbfrelIndex(String cbzdDcbcbfrelIndex);


    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.CbzdFbfDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询发包方信息
     */
    CbzdFbfDO getFbfByBdcdyh(String bdcdyh);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param paramMap
     * @param pageable
     * @return org.springframework.data.domain.Page<Map>
     * @description 分页查询承包宗地不动产单元
     */
    Page<Map> listCbzdBdcdy(Pageable pageable, Map<String, String> paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djdcbIndex
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.NydZdmjDO>
     * @description 根据外键 查询 分类面积
     */
    List<NydZdmjDO> listNydZdmjByDjdcbIndex(String djdcbIndex);

    /**
     * 根据不动产单元号查询承包方家庭
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param bdcdyh
     * @return
     */
    List<NydJtcyDO> listCbfJtcy(String bdcdyh);

    /**
     * 根据发包编码查询发包方
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param fbfbm
     * @return
     */
    CbzdFbfDO getFbfByFbfIndex(String fbfbm);
    /**
     * 根据发包编码查询发包方备份
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param fbfbm
     * @return
     */
    HCbzdFbfDO getHFbfByFbfIndex(String fbfbm);

    /**根据不动产单元号查询承包方备份
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param bdcdyh
     * @return
     */
    List<HCbzdCbfDO> listHCbfByBdcdyh(String bdcdyh);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description   查询当前地块对应承包方的所有地块关系集合
     */
    List<CbzdDcbcbfRelDO> listCbfCbzdDcbcbfRelList(String cbzdDcbcbfrelIndex);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据主键获取地块关系
     */
    CbzdDcbcbfRelDO queryCbfCbzdDcbcbfRel(String cbzdDcbcbfrelIndex);
}
