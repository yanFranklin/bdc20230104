package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/08/26
 * @description 不动产锁定查询Mapper处理
 */
@Mapper
public interface BdcSdMapper {

    /**
     * 根据bdcdyh获取不动产证书锁定
     *
     * @param bdcdyh
     * @param sdzt
     * @return
     */
    List<BdcZssdDO> queryBdcZssdByBdcdyh(@Param("bdcdyh") String bdcdyh,
                                         @Param("sdzt") Integer sdzt);

    /**
     * 查询证书锁定下的所有的不动产单元号
     * @param bdcdyhList
     * @return
     */
    public List<String> queryBdcZssdBdcdyh(@Param("bdcdyhList") List<String> bdcdyhList,@Param("sdzt") Integer sdzt);
}
