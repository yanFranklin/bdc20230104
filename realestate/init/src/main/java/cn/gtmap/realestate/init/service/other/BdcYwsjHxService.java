package cn.gtmap.realestate.init.service.other;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 业务数据回写接口
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/05
 * @description
 */
public interface BdcYwsjHxService {


    /**
     * 保存或更新业务信息到平台
     * @param gzlslid
     * @return
     * @throws Exception
     */
    void saveBdcYwsj(@NotBlank(message = "参数不能为空") String gzlslid) throws Exception;

}
