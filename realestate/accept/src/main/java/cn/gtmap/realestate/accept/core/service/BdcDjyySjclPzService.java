package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcDjyySjclPzDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcDjyySjclPzQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: realestate
 * @description: 登记原因收件材料配置service
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-11 14:35
 **/
public interface BdcDjyySjclPzService {

    /**
     * @param bdcDjyySjclPzQO 查询QO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/11 14:41
     */
    List<BdcDjyySjclPzDO> querySjclPz(BdcDjyySjclPzQO bdcDjyySjclPzQO);

    /**
     * @param bdcDjyySjclPzJson 登记原因收件材料配置
     * @return 登记原因收件材料配置分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记原因收件材料配置分页
     */
    Page<BdcDjyySjclPzDO> listBdcDjyySjclPzByPage(Pageable pageable, String bdcDjyySjclPzJson);

    /**
     * @param bdcDjyySjclPzDO 不动产登记原因收件材料配置信息
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产登记原因收件材料配置信息
     */
    int saveBdcDjyySjclPz(BdcDjyySjclPzDO bdcDjyySjclPzDO);

    /**
     * @param bdcDjyySjclPzDOList 不动产登记原因收件材料配置信息
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产登记原因收件材料配置信息
     */
    int deleteBdcDjyySjclPzList(List<BdcDjyySjclPzDO> bdcDjyySjclPzDOList);

    /**
     * @param bdcDjyySjclPzDO 登记原因收件材料配置
     * @return 最大序号
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取收件材料登记原因配置最大序号
     */
    int queryDjyySjclPzMaxSxh(BdcDjyySjclPzDO bdcDjyySjclPzDO);
}
