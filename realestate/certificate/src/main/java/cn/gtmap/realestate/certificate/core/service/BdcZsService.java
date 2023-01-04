package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.certificate.core.bo.BdcBdcqzhBO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import cn.gtmap.realestate.common.core.dto.certificate.*;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsXmAndQlrQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZssdQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcZsqdVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/2
 * @description 不动产证书业务类
 */
public interface BdcZsService {

    /**
     * @param zsid 证书id
     * @return BdcZsDO 不动产权证
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书
     */
    BdcZsDO queryBdcZs(String zsid);

    /**
     * @param zsids 证书ids
     * @return BdcZsDO 不动产权证
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 获取不动产权证书集合
     */
    List<BdcZsDO> queryBdcZss(String zsids);

    /**
     * @param xmid 项目id
     * @return List<BdcZsDO>
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    List<BdcZsDO> queryBdcZsByXmid(String xmid);

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 获取不动产权证书列表
     */
    List<BdcZsDO> listBdcZs(BdcZsQO bdcZsQO);

    /**
     * @param bdcZsDO
     * @return
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description 修改证书信息
     */
    int updateBdcZs(BdcZsDO bdcZsDO);

    /**
     * @param bdcFzjlZsDTO 证书DTO对象
     * @return 更新结果
     */
    int updateLzr(BdcFzjlZsDTO bdcFzjlZsDTO);

    /**
     * @param zsidList     证书IDList
     * @param fzr          发证人信息
     * @param isNullUpdate 只有当发证人为空的时候更新（true则做发证人是否为空的判断，否则直接更新发证信息）
     * @return int 更新数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证人，发证时间
     */
    int updateFzr(List<String> zsidList, UserDto fzr, Boolean isNullUpdate);

    /**
     * @param zsidList 证书idList
     * @param szr      缮证人信息
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新缮证人信息
     */
    BdcSzxxVO updateSzr(List<String> zsidList, UserDto szr);

    /**
     * @param zsidList 证书idList
     * @param szr      缮证人信息
     * @return int 更新数据量
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 更新缮证人信息(不管证书表中是否存在缮证人信息都更新)
     */
    BdcSzxxVO updateSzrByGzlslid(List<String> zsidList, UserDto szr);

    /**
     * @param bdcZsQO 证书查询对象QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据配置设置要查询的证书权属状态
     */
    void renderZsQszt(BdcZsQO bdcZsQO);

    /**
     * @param bdcBdcqzhBO 证号查询实体
     * @return {Integer} 库中当前最大顺序号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取数据库中当前最大顺序号
     */
    int queryMaxSxh(BdcBdcqzhBO bdcBdcqzhBO);

    /**
     * @param bdcBdcqzhBO 证号查询实体
     * @return {Integer}  证号数量
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询指定初始顺序号到最大顺序号之间顺序号
     */
    LinkedHashSet<Integer> querySxh(BdcBdcqzhBO bdcBdcqzhBO);

    /**
     * @param bdcZsQO 证书查询对象
     * @return int 证书数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 条件查询的证书数量
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
     * @param bdcZsQO 查询QO
     * @return List<String> 证书ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据条件查询zsid
     */
    List<String> queryZsid(BdcZsQO bdcZsQO);

    /**
     * @param gzlslid 工作流实例ID
     * @return List<String> 证书ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程所有的证书ID
     */
    List<String> queryGzlZsid(String gzlslid);

    /**
     * @param xmid 项目ID
     * @return List<String> 证书ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前项目的证书ID
     */
    List<String> queryXmZsid(String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前流程所有的证书类型
     */
    List<String> queryGzlZslx(String gzlslid, String xmid);

    /**
     * @param bdcZsQO 证书查询QO
     * @return BdcZsQsztDTO 证书状态DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的项目权属状态
     */
    List<BdcZsQsztDTO> queryBdcZsQszt(BdcZsQO bdcZsQO);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 获取当前流程关联的所有证书信息
     */
    List<BdcBdcqzhDTO> listBdcZhxx(String gzlslid);

    /**
     * @param zsidList 需要更新的证书ID
     * @return 更新的数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书的打印状态
     */
    int updateBdcZsDyzt(List zsidList, Integer dyzt);

    /**
     * @param bdcZsQO 证书查询QO
     * @return List<BdcZsDO> 证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询原项目的证书
     */
    List<BdcZsDO> listYxmZs(BdcZsQO bdcZsQO);


    /**
     * 查询需要补偿制证的证书id
     *
     * @return
     */
    List<String> listSyncZzZsids();

    /**
     * 查询需要补偿制证的存量证书id
     *
     * @return
     */
    List<String> listSyncZzClZsids(Map<String, Object> map);

    /**
     * @param gzlslid 工作流实例ID
     * @param qllx    权利类型
     * @return {List} 证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 查询原项目证书信息集合，证书需要按照下一手项目ID排序
     */
    List<BdcZsDO> queryYxmZsSortByXmid(String gzlslid, Integer qllx);

    /**
     * 查询未办结的缮证人名称为指定名称的项目 </br>
     * 针对需求 42397 提供的补偿接口服务
     *
     * @param szrid 缮证人 id
     * @return {List} 证书项目相关信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcZsXmDTO> listWbjywxx(String szrid);

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
     * @param bdcZsDO 证书信息
     * @param zsyl    是否证书预览
     * @param xmid    项目ID (zsyl为true时,xmid必填)
     * @return 共有情况
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * 是否分别持证为否，且产权人共有方式为共同共有--共同共有
     * 是否分别持证为否，且产权人共有方式为按份共有--按份共有 张三 50% 按份共有 李四 50% ....
     * 是否分别持证为是，且产权人共有方式为共同共有--共同共有
     * 是否分别持证为是，且产权人共有方式为按份共有--当前持证产权人名称 当前持证产权人的权利份额
     */
    String dealZsGyqk(BdcZsDO bdcZsDO, boolean zsyl, String xmid);

    /**
     * 获取不动产单元号关联的锁定证书信息
     *
     * @param bdcZssdQO 查询实体
     * @return java.util.List<BdcZssdDO> 证书锁定信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcZssdDO> listBdcZssdxx(BdcZssdQO bdcZssdQO);

    /**
     * @param bdcZsQO 证书查询对象
     * @return List<BdcZsDO> 不动产权证list 盐城自助打证机
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 获取不动产权证书列表
     */
    List<Map<String, Object>> listBdcZsForZzdzj(BdcZsQO bdcZsQO);

    /**
     * 拆除登记回写证书附记
     *
     * @param processInsId
     */
    void updateZsFj(String processInsId);

    /**
     * 查询项目以及关联的证书信息
     *
     * @param bdcXmDTOList 单元信息等
     * @return 项目以及关联的证书信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcZsXmDTO> listBdcXmZs(List<BdcXmDTO> bdcXmDTOList);

    /**
     * @param bdcZsQO xmid:生成多本证且未生成证书时必传,xmid为当前证书对应的xmid集合
     *                 gzlslid:必传
     *                 zsid:生成多本证,且证书已经生成时必传
     *                 qlr:证书权利人,主要用于分别持证时候区分当前证书的权利人数据
     * @return 校验证书信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 组织校验证书信息
     */
    BdcZsVO initJyZsxx(BdcZsQO bdcZsQO) throws Exception;


    /**
     * 添加项目和已有证书的关联关系
     * @param bdcZsQO
     * @author <a href="mailto:liaoxiang@gtmap.cn">wangyinghao</a>
     * @description 添加项目和已有证书的关联关系
     */
    void addXmZsConnection(String zsid,String xmid);

    List<BdcZsXmAndQlrDTO> listDjxx(BdcZsXmAndQlrQO qo);

    /**
     * 查询证明关联的产权证书信息
     * @param zsid 证书ID
     * @return {List} 证书信息
     * @Author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     **/
    List<BdcZsDO> listBdcZsByZmid(String zsid);


    /**
     * 更新注销流程的证书信息
     *
     * @param processInsId
     * @throws Exception
     */
    public void gxzhzt(String processInsId);

    /**
     * @param zsidList 证书ID
     * @return List<BdcXmDO> 项目信息
     * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
     * @description 按照zsid获取当前证书清单
     */
    List<BdcZsqdVO> queryZsQdByZsid(List<String> zsidList,String gzlslid);
}
