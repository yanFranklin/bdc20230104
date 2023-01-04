package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.certificate.*;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZzfsxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZssdQO;

import cn.gtmap.realestate.common.core.qo.register.BdcQzxxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsqdVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/11/12
 * @description 证书Mapper
 */
public interface BdcZsMapper {
    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param map
     * @return List<BdcZsDO>
     * @description 根据map获取证书列表
     */
    List<BdcZsDO> listBdcZs(Map map);

    /**
     * @param bdcZsQO 查询QO
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据条件查询zsid
     */
    List<String> queryZsid(BdcZsQO bdcZsQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcBdcqzhBO 证号查询实体
     * @return {Integer} 库中当前最大顺序号
     * @description 获取数据库中当前最大顺序号
     */
    int queryMaxSxh(BdcBdcqzhBO bdcBdcqzhBO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcBdcqzhBO 证号查询实体
     * @return {Integer}  证号数量
     * @description 查询指定初始顺序号到最大顺序号之间顺序号
     */
    LinkedHashSet<Integer> querySxh(BdcBdcqzhBO bdcBdcqzhBO);

    /**
     * @param bdcZsQO 证书查询对象
     * @return int 证书数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取条件查询的证书的总数量
     */
    int countBdcZs(BdcZsQO bdcZsQO);

    /**
     * @param zsid 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前证书相关的项目信息
     */
    List<BdcXmDO> queryZsXmByZsid(String zsid);

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<String> 证书类型list
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书类型
     */
    List<String> queryZslx(BdcZsQO bdcZsQO);

    /**
     * @param bdcZsDO 证书信息
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键更新证书信息
     */
    int updateBdcZsByZsid(BdcZsDO bdcZsDO);

    /**
     * @param bdcZsQO 证书查询QO
     * @return BdcZsQsztDTO 证书状态DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的项目权属状态
     */
    List<BdcZsQsztDTO> queryBdcZsQszt(BdcZsQO bdcZsQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  gzlslid 工作流实例ID
     * @description  获取当前流程关联的所有证书项目信息
     */
    List<BdcBdcqzhDTO> listBdcZhxx(@Param("gzlslid") String gzlslid);

    /**
     * @param bdcFzjlZsDTO 发证记录证书DTO
     * @return int  更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将相同的领证人信息更新到证书表中
     */
    int updateZsCommonLzrxx(BdcFzjlZsDTO bdcFzjlZsDTO);

    /**
     * 批量更新领证人信息更新至证书表
     * @param bdcFzjlZsDTO 发证记录所需的证书信息
     * @return int 更新数量
     */
    int plUpdateZsLzrxxByZsid(BdcFzjlZsDTO bdcFzjlZsDTO);

    /**
     * @param map 查询参数
     * @return 证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原项目的证书
     */
    List<BdcZsDO> listYxmZs(Map map);


    /**
     * 查询需要补偿制证的证书id
     * @return
     */
    List<String> listSyncZzZsids();

    /**
     * 查询需要补偿制证的存量证书id
     * @return
     */
    List<String> listSyncZzClZsids(Map<String, Object> map);

    /**
     * @param gzlslid 工作流实例ID
     * @return {List} 证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询原项目证书信息集合
     */
    List<BdcZsXmidDTO> queryYxmZs(@Param("gzlslid") String gzlslid, @Param("qllx") Integer qllx);

    /**
     * 查询未办结的缮证人名称为指定名称的项目 </br>
     * 针对需求 42397 提供的补偿接口服务
     *
     * @param szrid 缮证人 id
     * @return {List} 证书项目相关信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcZsXmDTO> listWbjywxx(@Param("szrid") String szrid);

    /**
     * 平台办结后执行更新事件 </br>
     * 针对需求 42397 提供的补偿接口服务 </br>
     * <p>
     * 1. 更新发证时间
     *
     * @param bdcZsXmDTOS 证书项目集合
     * @return {int} 更新条数
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    int updateWbjxm(List<BdcZsXmDTO> bdcZsXmDTOS);

    /**
     * 获取不动产单元号关联的锁定证书信息
     *
     * @param bdcZssdQO 查询实体
     * @return java.util.List<BdcZssdDO> 证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcZssdDO> listBdcZssdxx(BdcZssdQO bdcZssdQO);

    /**
     * 不动产权证书list 盐城自助打证机
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> listBdcZsForZzdzj(Map<String, Object> map);

    /**
     * 批量更新证书附记
     * @param zsids
     */
    void updateZsFj(List<String> zsids);

    /**
     * 获取领证人签字图片信息（如果指定顺序号则查询项目指定顺序号签字信息，否则查询项目所有签字信息）
     * @param bdcQzxxQO 签字信息查询参数
     * @return {List} 发证记录领证人签字信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcQzxxDO> queryFzjlLzrQzxxs(BdcQzxxQO bdcQzxxQO);

    /**
     * 查询项目以及关联的证书信息
     * @param bdcXmDTOList 单元信息等
     * @return 项目以及关联的证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcZsXmDTO> listBdcXmZs(List<BdcXmDTO> bdcXmDTOList);

    /**
     * 清空证书表中电子签章字段
     * @param zsid 证书ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    void updateBdcZsZzqzNull(@Param("zsid") String zsid);

    /**
     * 查询证号跳号
     *
     * @param qo 查询参数实体
     * @return {List} 跳号集合
     */
    List<String> listBdcZhth(BdcBdcqzhBO qo);

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //根据身份证号查询不动产登记信息
     * @Date 2022/5/12 16:16
     **/
    List<BdcZsXmAndQlrDTO> listDjxx(@Param("bdcdyh") String bdcdyh, @Param("qlrmc") String qlrmc, @Param("sfzh") String sfzh);


    /**
     * @param zsidList 证书idList
     * @return int 更新数据数量
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 批量更新
     */
    int batchUpdateSzr(@Param("zsidList") List<String> zsidList, @Param("szr") String szr, @Param("szrid") String szrid, @Param("szsj") Date szsj);

    /**
     * @param zsidList 证书idList
     * @return {List} 证书信息
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 查询证书，缮证人不为空
     */
    List<String> queryUpdateZsid(@Param("zsidList") List<String> zsidList);

    /**
     * 查询抵押对应的产权信息
     * @param zsid 证书id
     * @return {List} 抵押产权信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<EZzfsxxDTO> queryDyaqCqxx(@Param("zsid")String zsid);

    /**
     * 查询证明关联的产权证书信息
     * @param zsid 证书ID
     * @return {List} 证书信息
     * @Author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     **/
    List<BdcZsDO> listBdcZsByZmid(@Param("zsid")String zsid);

    /**
     * @param zsidList 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 按照zsid获取当前证书清单
     */
    List<BdcZsqdVO> queryZsQdByZsid(@Param("zsidList") List<String> zsidList);
}
