package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.natural.core.bo.ZrzyZscCqzhBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;

@Component
public interface ZrzyZsMapper {
    List<ZrzyZsDO> selectByXmid(@Param("xmid") String xmid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zrzyZscCqzhBO 证号查询实体
     * @return {Integer} 库中当前最大顺序号
     * @description 获取数据库中当前最大顺序号
     */
    int queryMaxSxh(ZrzyZscCqzhBO zrzyZscCqzhBO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param zrzyZscCqzhBO 证号查询实体
     * @return {Integer}  证号数量
     * @description 查询指定初始顺序号到最大顺序号之间顺序号
     */
    LinkedHashSet<Integer> querySxh(ZrzyZscCqzhBO zrzyZscCqzhBO);
}