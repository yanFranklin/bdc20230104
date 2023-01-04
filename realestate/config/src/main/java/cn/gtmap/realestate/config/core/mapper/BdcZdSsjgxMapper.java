package cn.gtmap.realestate.config.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcZdSsjGxDO;
import cn.gtmap.realestate.common.core.qo.config.BdcZdSsjGxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0, 2021/08/25
 * @description 省市级共享字典表查询
 */
public interface BdcZdSsjgxMapper {

    List<BdcZdSsjGxDO> listSsjgx(BdcZdSsjGxQO bdcZdSsjGxQO);

    BdcZdSsjGxDO getSsjgxByZmldm(@Param(value="zmldm")String zmldm);

    List<BdcZdSsjGxDO> getSsjgxByFmldms(@Param("fmldms")List<String> fmldms);
}
