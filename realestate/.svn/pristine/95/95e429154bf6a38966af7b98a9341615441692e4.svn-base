package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbQlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxPlDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcCfQO;
import cn.gtmap.realestate.common.core.qo.register.BdcCfjgQO;
import cn.gtmap.realestate.common.core.qo.register.BdcFdcq3QO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 权利信息业务类
 */
public interface BdcQlxxService {
    /**
     * @param zys 总页数
     * @param ym  当前页码
     * @return String
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据总页数和起始页码，拼接页码显示的字符串
     */
    String generateYm(Integer zys, Integer ym);

    /**
     * @param totalQl 总数
     * @return Integer 页数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 计算所需页数
     */
    Integer calculateQlYs(Integer totalQl);

    /**
     * @param objectList 对象List
     * @return Integer list的总数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取所有对象list的个数
     */
    Integer getTotalQl(List objectList);

    /**
     * @param bdcQl  权利对象
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权利的登簿信息和权属状态
     */
    void udpateBdcQlDbxxAndQszt(BdcQl bdcQl, DbxxQO dbxxQO);

    /**
     *
     * @param bdcdyh 房屋或者土地的不动产单元号
     * @param qsztList
     * @return Integer 计算所得的总页数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 计算产权的总页数（除建筑物区分业主中有部分）
     */
    Integer calculateCqYs(String bdcdyh, List<Integer> qsztList);

    /**
     * @param bdcdyh 不动产单元号
     * @return Integer 计算所得总页数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 计算建筑物区分业主共有部分页数
     */
    Integer calculateYzgyYs(String bdcdyh);
    /**
     * @param bdcdyh 房屋的不动产单元号
     * @param qsztList
     * @return List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询房屋的产权
     */
    List queryListBdcFwCq(String bdcdyh, List<Integer> qsztList);

    /**
     * @param bdcdyh 土地的不动产单元
     * @param qsztList
     * @return List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询非房屋的产权（土地，林地，海域）
     */
    List queryListBdcFfwCq(String bdcdyh, List<Integer> qsztList);

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型
     * @param qsztList
     * @return listBdcQlxx
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询不动产单元的指定类型的权利信息
     */
    List<BdcQl> listBdcQlxx(String bdcdyh, String qllx, List<Integer> qsztList);

    /**
     * @param bdcdyh 不动产单元号
     * @param qllx   权利类型
     * @return list 注销权利信息
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 查询不动产单元的指定类型的注销权利信息
     */
    List<BdcQl> listBdcZxQlxx(String bdcdyh, String qllx);

    /**
     * @param bdcdyh
     * @return List<BdcQl> 产权权利
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元号的产权权利
     */
    List<BdcQl> queryBdcCqlist(String bdcdyh);


    /**
     * @param bdcQl   权利信息
     * @param bdcZxQO 注销信息
     * @return {@code int} 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 已知权利信息的时候，更新该权利的注销信息
     */
    int updateBdcQlZxxx(BdcQl bdcQl, BdcZxQO bdcZxQO);

    /**
     *
     * @param bdcDjbQlDTO
     * @param bdcdyh
     * @param qsztList
     * @return String 权利类型
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的土地、海域、林权等其他权利类型值
     */
    BdcDjbQlDTO queryBdcQtCqQllx(BdcDjbQlDTO bdcDjbQlDTO, String bdcdyh, List<Integer> qsztList);

    /**
     * @param bdcDjbQlDTO
     * @param bdcdyh
     * @param qsztList
     * @return String 权利类型
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取不动产单元的房屋产权权利类型值
     */
    BdcDjbQlDTO queryBdcFwCqQllx(BdcDjbQlDTO bdcDjbQlDTO, String bdcdyh, List<Integer> qsztList);

    /**
     * @param qllx
     * @param map  查询参数
     * @return Page<BdcQl> 权利信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询登记簿各权利信息
     */
    Page<BdcQl> bdcQlxxByPageJson(String qllx, Map<String, Object> map, Pageable pageable);

    /**
     * @param xmid       项目ID
     * @param bdcGyqkDTO 共有情况对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新权利的共有情况
     */
    void updateQlGyqk(String xmid, BdcGyqkDTO bdcGyqkDTO);

    /**
     * @param bdcGyqkDTO 共有情况DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权利的共有情况（权利一致，权利人一致）
     */
    int updateQlGyqkPl(BdcGyqkDTO bdcGyqkDTO);

    /**
     * @param dbxxQO 登簿信息QO
     * @param qllx 权利类型
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新批量流程当前权利的登簿信息和权属状态
     */
    void udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO, String qllx);

    /**
     * @param dbxxQO 登簿信息QO
     * @param qllx   需要注销的权利
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新原权利的权属状态和注销登簿人
     */
    void updateYqlZxDbxxAndQsztPl(DbxxQO dbxxQO, String qllx);

    /**
     * @param bdcQlList 权利信息
     * @return Set
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前权利的类型
     */
    List<String> classifyQlxx(List<BdcQl> bdcQlList);

    /**
     * @param bdcCfjgQO 查封机关信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新查封信息的查封机关或解封机关信息
     */
    void updateCfjgOrJfjg(BdcCfjgQO bdcCfjgQO);

    /**
     * @param bdcFdcq3GyxxQO 建筑物所有权及业主共有信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 建筑物所有权及业主共有信息更新权利人
     */
    void updateFdcq3Qlr(BdcFdcq3QO bdcFdcq3GyxxQO);

    /**
     * @param bdcZxQO 注销信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据参数xmid，更新权利的注销信息
     */
    int updateBdcQlZxxxByXmid(BdcZxQO bdcZxQO);

    /**
     * @param bdcZxQO         不动产注销信息
     * @author <a href ="mailto:zhangwentao@gtmap.cn">songhaowen</a>
     * @description 注销权利信息 (bdcDbxxService中有updateXmAndQlZxxx方法可以使用)
     */
    @Deprecated
    void zxQl(BdcZxQO bdcZxQO);

    /**
     * @param gzlslid 当前流程的工作流实例ID
     * @param qszt    权属状态
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新当前项目的权属状态
     */
    void updateBdcQlQsztPl(String gzlslid, Integer qszt);

    /**
     * @param bdcRyzdDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 权利更新权利人冗余字段
     */
    void updateBdcQlRyzdQlr(BdcRyzdDTO bdcRyzdDTO);

    /**
     * @param bdcCfQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/8/19 17:04
     */
    List<BdcCfDO> listBdcCfxx(BdcCfQO bdcCfQO);

    /**
      * @param xmid 项目ID
      * @return 使用期限信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据项目ID组织生成使用期限
      */
    BdcSyqxDTO generateSyqx(String xmid);

    /**
     * @param bdcSyqxPlDTO
     * @return 使用期限信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新组织生成使用期限
     */
    BdcSyqxDTO generateSyqxPl(BdcSyqxPlDTO bdcSyqxPlDTO);

    /**
     * @param dbxxQO 登簿信息
     * @param qllx 权利类型
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新权利登簿信息
     */
    void updateBdcQlDbxxPl(DbxxQO dbxxQO,String qllx);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新注销权利登簿人
     */
    void updateZxQlDbrPl(DbxxQO dbxxQO,String qllx);

}
