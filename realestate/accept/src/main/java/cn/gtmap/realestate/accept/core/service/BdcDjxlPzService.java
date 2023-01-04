package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.qo.accept.DjxlPzCxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDjxlPzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/12
 * @description 不动产登记小类配置数据服务
 */
public interface BdcDjxlPzService {
    /**
     * @param gzldyid 工作流定义ID
     * @param dyhqllx    单元号权利类型
     * @param yqllx 原权利类型
     * @return 不动产登记小类配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产登记小类配置
     */
    List<BdcDjxlPzDO> listBdcDjxlPzByGzldyid(String gzldyid, Integer dyhqllx,Integer yqllx);

    /**
     * @param pzid 配置ID
     * @return 不动产登记小类配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置ID获取不动产登记小类配置
     */
    BdcDjxlPzDO queryBdcDjxlPzDOByPzid(String pzid);

    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产登记小类配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID获取不动产登记小类配置
     */
    List<BdcDjxlPzDO> queryBdcDjxlPzByGzldyid(String gzldyid);

    /**
     * @param bdcDjxlPzDO 查询参数
     * @return 不动产登记小类配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询条件获取不动产登记小类配置
     */
    List<BdcDjxlPzDO> listBdcDjxlPz(BdcDjxlPzDO bdcDjxlPzDO);


    /**
     * @param bdcDjxlPzDO 不动产登记小类配置
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产登记小类配置
     */
    int saveBdcDjxlPzDO(BdcDjxlPzDO bdcDjxlPzDO);

    /**
     * @param bdcDjxlPzDOList 登记小类配置
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除登记小类配置
     */
    int deleteBdcDjxlPz(List<BdcDjxlPzDO> bdcDjxlPzDOList);

    /**
     * @param bdcDjxlPzJson 登记小类配置
     * @return 登记小类配置分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记小类配置分页
     */
    Page<BdcDjxlPzDO> listBdcDjxlPzByPage(Pageable pageable, String bdcDjxlPzJson);

    /**
     * @param bdcDjxlPzDO 登记小类配置
     * @return 最大顺序号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取登记小类配置最大顺序号
     */
    int queryDjxlPzMaxSxh(BdcDjxlPzDO bdcDjxlPzDO);

    /**
     * @param gzldyid 工作流定义ID
     * @param djxl    登记小类
     * @return 登记小类配置
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类配置
     */
    BdcDjxlPzDO queryBdcDjxlPzByGzldyidAndDjxl(String gzldyid, String djxl);

    /**
     * @param pzid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上报配置校验
     * @date : 2022/7/8 10:18
     */
    int sbpzjy(String pzid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据查询条件查询流程定义id
     * @date : 2022/11/29 17:07
     */
    List<String> listLcdyids(BdcDjxlPzQO bdcDjxlPzQO);

    /**
     * @param djxlPzCxQO 登记小类配置查询参数
     * gzldyid,bdcdyh:必要字段
     * yqllx:选择登记项目时必传
     * qllx,djxl:有值则传
     * @return 登记小类配置列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据参数确定登记小类配置,为空抛异常
     */
    List<BdcDjxlPzDO> queryBdcDjxlPz(DjxlPzCxQO djxlPzCxQO);
}
