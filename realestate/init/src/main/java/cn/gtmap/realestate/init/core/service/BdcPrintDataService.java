package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/9/11.
 * @description
 */
public interface BdcPrintDataService {
    List<BdcDysjDTO> queryFdcqDzFwqdDysj(List<String> xmids,String gzlslid,String zsid,boolean hfwqd);
}
