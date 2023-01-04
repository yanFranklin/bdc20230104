package cn.gtmap.realestate.supervise.core.mapper;

import cn.gtmap.realestate.supervise.core.domain.BdcLfDczjjgZjxxDO;
import cn.gtmap.realestate.supervise.core.qo.LfZjxxQO;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/07
 * @description 廉防6：督查质检监管逻辑处理
 */
public interface LfDczjjgMapper {

    /**
     * 查询没有上传质检报告的质检信息
     * @param zjxxQO 业务查询参数
     * @return 质检信息
     */
    List<BdcLfDczjjgZjxxDO> listZjxxWithoutFj(LfZjxxQO zjxxQO);

    /**
     * 查询没有上传整改报告的质检信息
     * @param zjxxQO 业务查询参数
     * @return 没有上传整改报告的质检信息
     */
    List<BdcLfDczjjgZjxxDO> zjxxWithoutZgwj(LfZjxxQO zjxxQO);
}
