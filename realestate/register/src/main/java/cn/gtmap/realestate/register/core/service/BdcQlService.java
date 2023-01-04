package cn.gtmap.realestate.register.core.service;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.service.InterfaceCode;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcQlNumDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/13
 * @description 不动产权利公共接口，抽取一些公共方法
 */
public interface BdcQlService extends InterfaceCode{
    /**
     * @param bdcQl   权利对象
     * @param bdcZxQO 注销信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新注销登簿信息
     */
    int updateZxDbxx(BdcQl bdcQl, BdcZxQO bdcZxQO);


    /**
     * @param bdcdyh 不动产单元
     * @param qsztList
     * @return List<BdcQl>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 不动产权利信息，按照登记时间升序排序
     */
    List<BdcQl> queryListBdcQl(String bdcdyh, List qsztList);

    /**
     * @param bdcdyh 不动产单元号
     * @return list 注销权利信息
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 查询不动产单元的指定类型的注销权利信息
     */
    List<BdcQl> queryListBdcZxQl(String bdcdyh);

    /**
     *
     * @param bdcQlNumDTO
     * @param bdcdyh
     * @param qsztList 权属状态
     * @return Integer 查询结果数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询单元号办理该权利的总量
     */
    BdcQlNumDTO countBdcQl(BdcQlNumDTO bdcQlNumDTO, String bdcdyh, List qsztList);


    /**
     * @param map 查询参数
     * @return Page<BdcQl>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询各权利
     */
    Page<BdcQl> bdcQlByPageJson(Map<String, Object> map, Pageable pageable);

    /**
     * @param bdcGyqkDTO 不动产共有情况DTO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新同个权利的共有情况信息
     */
    int updateQlGyqkPl(BdcGyqkDTO bdcGyqkDTO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量流程更新项目生成的权利的登簿信息和权属状态
     */
    int udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量流程，更新上一手权利的权属状态（注销类限制权利还要更新注销登簿信息）
     */
    int udpateYqlZxDbxxAndQsztPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权利的权属状态
     */
    int updateBdcQlQsztPl(DbxxQO dbxxQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新权利表权利人冗余字段
     */
    void updateRyzdQlr(BdcRyzdDTO bdcRyzdDTO);

    /**
      * @param bdcQl 权利信息
      * @return 使用期限信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 生成使用期限
      */
    BdcSyqxDTO generateQlSyqx(BdcQl bdcQl);

    /**
     * @param jsonObject 权利信息
     * @return 使用期限信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 生成使用期限
     */
    BdcSyqxDTO generateQlSyqxPl(JSONObject jsonObject);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新权利登簿人
     */
    int updateBdcQlDbrPl(DbxxQO dbxxQO);

    /**
     * @param dbxxQO 登簿信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新注销权利登簿人
     */
    int updateZxQlDbrPl(DbxxQO dbxxQO);
}
