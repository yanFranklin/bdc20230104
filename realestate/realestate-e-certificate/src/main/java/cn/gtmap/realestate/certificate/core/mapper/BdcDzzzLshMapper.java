package cn.gtmap.realestate.certificate.core.mapper;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzLshDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 
 */
@Mapper
public interface BdcDzzzLshMapper {

    /**
     * 获取不动产编号
     *
     * @param map
     * @return
     */
    List<BdcDzzzLshDo> getBdcDzzzLsh(Map map);


    /**
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @description 保存电子证照流水号
     */
    void saveBdcDzzzLsh(BdcDzzzLshDo bdcDzzzLshDo);


    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description  删除流水号信息
     */
    int deleteBdcDzzzLsh(Map map);
}
