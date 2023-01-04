package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;

import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/13
 * @description 规则验证的服务
 */
public interface BdcGzyzService {
    /**
     * @param gzlslid 工作流实例ID
     * @return List 查询结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 规则验证-房改房-是否允许办理查询
     */
    BdcGzyzVO checkFgfSfyxbl(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 规则验证-税务-建设银行缴库入库状态（未入库，则补退）
     */
    @Deprecated
    BdcGzyzVO checkYhjkrk(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 返回验证结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿前验证税费缴库入库状态
     */
    BdcGzyzVO checkSfjkrk(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 登簿前验证线下缴费是否已上传税费缴纳凭证
     */
    BdcGzyzVO checkXxJfPz(String gzlslid);


    /**
     * @param gzlslid 工作流实例ID
     * @return 验证结果信息
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 验证抵押权首次登记的抵押权人是否全部是签约银行
     */
    Map<String, String> checkSfxyjg(String gzlslid);


    /**
     * @param gzlslid 工作流实例ID
     * @return 返回是否自动转发或自动办结，满足要求返回code=1
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自动转发或自动办结验证地址
     */
    Map<String, String> checkZdzfzdbj(String gzlslid);
}
