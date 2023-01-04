package cn.gtmap.realestate.natural.core.service;

import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitResultDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitServiceDTO;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/27
 * @description 初始化数据处理服务
 */
public interface ZrzyInitDataDealService {

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 将所有业务初始化的数据整合到ZrzyInitResultDTO中
      */
    ZrzyInitResultDTO dealServiceDTO(ZrzyInitServiceDTO zrzyInitServiceDTO,ZrzyInitResultDTO zrzyInitResultDTO) throws IllegalAccessException;
}
