package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcJsydsyqDO;
import cn.gtmap.realestate.common.core.domain.BdcJzqDO;
import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFcdaDyaqDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFdcqDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFwqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcFwJbxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/9
 * @description 住房信息查询Mapper
 */
public interface BdcZfxxMapper {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description 根据权利人名称、证件号查询房产信息
     */
    List<BdcZfxxDTO> listBdcZfxxDTO(BdcZfxxQO bdcZfxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description （南通）根据权利人名称、证件号查询房产信息
     */
    List<BdcZfxxDTO> listBdcNtZfxxDTO(BdcZfxxQO bdcZfxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @param qjgldm 权籍
     * @return {List} 抵押信息
     * @description 根据不动产单元号获取对应的抵押信息
     */
    List<BdcFcdaDyaqDTO> listBdcFcdaDyaqDTO(@Param("bdcdyh") String bdcdyh,@Param("qjgldm") String qjgldm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @param qjgldm 权籍
     * @return {List} 抵押信息
     * @description 根据不动产单元号获取对应的房产信息
     */
    List<BdcFdcqDTO> listBdcFdcqDTO(@Param("bdcdyh") String bdcdyh,@Param("qjgldm") String qjgldm);


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {List} 查封信息
     * @description 根据不动产单元号获取对应的查封信息
     */
    List<BdcCfDO> listBdcFcdaCfxx(@Param("bdcdyh") String bdcdyh,@Param("qjgldm") String qjgldm);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {List} 查封信息
     * @description 根据不动产单元号获取对应的异议信息
     */
    List<BdcYyDO> listBdcFcdaYyxx(@Param("bdcdyh") String bdcdyh,@Param("qjgldm") String qjgldm);

    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param bdcFwqlQO 查询参数
     * @return 房屋权利
     * @description  蚌埠 查询机  查询房屋权利
     */
    List<BdcFwqlDTO> listBdcFwqlDTO(BdcFwqlQO bdcFwqlQO);

    /**
     * （盐城）查询房屋权属信息（以物为主）
     * @param bdcFwQsxxQO 查询参数
     * @return {List} 房屋权属信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcFwJbxxDTO> listBdcFwQsxxDTO(BdcFwQsxxQO bdcFwQsxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description （盐城）根据权利人名称、证件号查询历史房产信息
     */
    List<BdcZfxxDTO> listBdcLsZfxxDTO(BdcZfxxQO bdcZfxxQO);

    /**
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @param xmid 项目id
     * @return {List} 建设用地使用权、宅基地使用权
     * @description 根据XMID查询BdcJsydsyqDO
     */
    List<BdcJsydsyqDO> listJsydsyqDOByXmid(String xmid);


    /**
     * 获取居住权
     * @param bdcdyh
     * @param qjgldm
     * @return
     */
    List<BdcJzqDO> listBdcJzqxx(@Param("bdcdyh") String bdcdyh, @Param("qjgldm") String qjgldm);


    /**
     * 查询个人住房合同信息
     * @param bdcZfxxQO
     * @return
     */
    List<BdcZfxxDTO> listBdcHtZfxxDTO(BdcZfxxQO bdcZfxxQO);

    /**
     * 查询合同备案信息
     * @param bdcZfxxQO
     * @return
     */
    List<BdcZfxxDTO> lisSpfhtbhxx(BdcZfxxQO bdcZfxxQO);
}
