package cn.gtmap.realestate.exchange.service.inf.yzw;

import cn.gtmap.realestate.exchange.core.dto.yzw.YzwCheckAndTsResultDTO;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/23
 * @description 一张网验证相关服务
 */
public interface YzwCheckService {

    /**
     * @param yzwbh 一张网编号
     * @return 验证信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证一张网数据
     */
    YzwCheckAndTsResultDTO checkYzwData(String yzwbh, List<String> yzlxList);

    /**
     * @param yzwbhList 一张网编号集合
     * @param isAll  是否验证全部
     * @return 验证失败一张网编号集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证共享一张网数据
     */
    List<String> checkYzwDataPl(List<String> yzwbhList, boolean isAll);






}
