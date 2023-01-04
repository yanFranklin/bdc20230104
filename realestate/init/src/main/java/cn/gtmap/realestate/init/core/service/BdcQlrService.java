package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcDsQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcQlrGroupDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrIdsDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/10/31
 * @description 查询不动产权利人的接口
 */
public interface BdcQlrService {

    /**
     * 根据 qlridlist 批量删除权利人信息
     *
     * @param qlridlist 权利人 ID 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void deleteBatchQlr(@NotEmpty(message = "权利人ID 集合不能为空") List qlridlist);

    /**
     * 根据 gzlslid 去查询全部 qlr 信息
     * *@param djxl 可空
     * @param qlrlb 可空
     * @param gzlslid 工作流实例 ID 与受理编号组合非空
     * @param slbh 受理编号 与gzlslid 组合非空
     * @return {List<BdcQlrDO>} 权利人集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcQlrDO> listAllBdcQlr(String gzlslid,String slbh, String qlrlb,String djxl,String xmidList);

    /**
     * 根据 bdcdyh 去查询全部现势产权 qlr 信息
     * *@param bdcdyh 非空
     * @return {List<BdcQlrDO>} 权利人集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    List<BdcQlrDO> listXsCqQlr(@NotBlank(message = "不动产单元号不能为空") String bdcdyh);

    /**
     * 通过传入权利人ID获取对应的数据
     * @param qlrid 权利人ID
     * @return BdcQlrDO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过传入权利人ID获取对应的数据
     */
    BdcQlrDO queryBdcQlrByQlrid(@NotBlank(message = "权利人ID不能为空") String qlrid);

    /**
     * 通过传入的权利人信息查询参数获取不动产权利人信息
     * @param bdcQlrDO
     * @param order    排序
     * @return List<bdcQlrDO>
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过传入的权利人信息查询参数获取不动产权利人信息
     */
    List<BdcQlrDO> queryBdcQlrByQlrxx(BdcQlrDO bdcQlrDO, String order);

    /**
     * 根据查询参数查询权利人信息，支持按模糊类型查询
     * @param bdcQlrQO 查询参数
     * @return List<bdcQlrDO> 权利人信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcQlrDO> queryBdcQlrWithMhlx(BdcQlrQO bdcQlrQO);

    /**
     * 通过传入的权利人信息查询参数获取不动产第三权利人信息
     * @param bdcDsQlrDO
     * @param order    排序
     * @return List<bdcDsQlrDO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 通过传入的权利人信息查询参数获取不动产第三权利人信息
     */
    List<BdcDsQlrDO> queryBdcDsQlr(BdcDsQlrDO bdcDsQlrDO, String order);

    /**
     * 新增不动产权利人信息
     * @param bdcQlrDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 新增不动产权利人信息
     */
    void insertBdcQlr(BdcQlrDO bdcQlrDO);

    /**
     * 新增不动产权利人信息
     * @param bdcDsQlrDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增不动产权利人信息
     */
    void insertBdcDsQlr(BdcDsQlrDO bdcDsQlrDO);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 插入新的第三权利人信息
      */
    int insertBdcDsQlrPl(List<BdcDsQlrDO> bdcDsQlrDOList);

    /**
     * 更新权利人信息
     * @param bdcQlrDO
     * @return 更新数量
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 更新权利人信息
     */
    int updateBdcQlr(BdcQlrDO bdcQlrDO);

    /**
     * 更新第三权利人信息
     * @param bdcDsQlrDO
     * @return 更新数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新第三权利人信息
     */
    int updateBdcDsQlr(BdcDsQlrDO bdcDsQlrDO);

    /**
     * 根据xmId和qlrLb删除当前项目的权利人信息
     * @param xmId
     * @param qlrLb
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据xmId和qlrLb删除当前项目的权利人信息
     */
    int deleteQlr(String xmId, String qlrLb);

    /**
     * 根据gzlslid和qlrLb删除当前业务的权利人信息
     * @param gzlslid
     * @param qlrLb
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据gzlslid和qlrLb删除当前业务的权利人信息
     */
    void deleteBatchQlr(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, String qlrLb);

    /**
     * 根据gzlslid和bdcQlrDO新增权利人信息
     * @param gzlslid
     * @param bdcQlrDO
     * @param djxl
     * @return List<BdcQlrDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据gzlslid和bdcQlrDO新增权利人信息
     */
    List<BdcQlrDO> insertBatchQlr(String gzlslid,String djxl, BdcQlrDO bdcQlrDO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcQlrDO> insertBatchBdcQlrByXmids(BdcQlrIdsDTO bdcQlrIdsDTO);

    /**
     * 根据gzlslid和bdcQlrDOList新增权利人信息
     * @param gzlslid
     * @param bdcQlrDOList
     * @return List<BdcQlrDO>
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据gzlslid和bdcQlrDOList新增权利人信息
     */
    List<BdcQlrDO> insertBatchQlr(String gzlslid, List<BdcQlrDO> bdcQlrDOList);

    /**
     * 根据qlrId(主键)删除权利人信息
     * @param qlrId
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据qlrId(主键)删除权利人信息
     */
    int deleteBdcQlrByQlrId(String qlrId);

    /**
     * 根据qlrId(主键)删除第三权利人信息
     * @param qlrId
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据qlrId(主键)删除第三权利人信息
     */
    int deleteBdcDsQlrByQlrId(String qlrId);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  根据xmid和权利人类别删除第三权利人信息
     */
    int delBdcDsQlr(String xmId, String qlrLb);

    /**
     * 根据权利人信息删除权利人
     * @param bdcQlrDO
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 根据权利人信息删除权利人
     */
    int deleteBdcQlr(BdcQlrDO bdcQlrDO);


    /**
     * 通过权利人名称和证件号批量删除流程内权利人信息
     * @param gzlslid
     * @param bdcQlrDO
     * @param djxl
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 通过权利人名称和证件号批量删除流程内权利人信息
     */
    void deleteBdcQlrsByQlrxx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, String djxl,BdcQlrDO bdcQlrDO,List<String> xmidList);

    /**
     * 根据权利人类别和原项目id继承上一手项目的权利人
     * @param yxmId
     * @param xmId
     * @param qlrLb
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据权利人类别和原项目id继承上一手项目的权利人
     */
    List<BdcQlrDO> inheritYxmBdcQlr(String yxmId, String xmId, String qlrLb);

    /**
     * 组合登记根据权利人类别和未入库的原项目权利人信息继承上一手项目的权利人
     * @param ybdcQlrDOList
     * @param xmId
     * @param qlrLb
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 组合登记根据权利人类别和未入库的原项目权利人信息继承上一手项目的权利人
     */
    List<BdcQlrDO> inheritYxmBdcQlr(List<BdcQlrDO> ybdcQlrDOList, String xmId, String qlrLb);

    /**
     * 根据权利人类别和原项目id继承上一手项目的义务人
     * @param yxmId
     * @param xmId
     * @param qlrLb
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据权利人类别和原项目id继承上一手项目的义务人
     */
    List<BdcQlrDO> inheritYxmBdcYwr(String yxmId, String xmId, String qlrLb);

    /**
     * 组合登记根据权利人类别和未入库的原项目义务人信息继承上一手项目的义务人
     * @param ybdcQlrDOList
     * @param xmId
     * @param qlrLb
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 组合登记根据权利人类别和未入库的原项目义务人信息继承上一手项目的义务人
     */
    List<BdcQlrDO> inheritYxmBdcYwr(List<BdcQlrDO> ybdcQlrDOList, String xmId, String qlrLb);

    /**
     *  根据权利人类别继承权籍权利人信息
     * @param fwFcqlrDOList
     * @param xmId
     * @param qlrLb
     * @param bdcdyh
     * @return
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据权利人类别继承权籍权利人信息
     */
    List<BdcQlrDO> inheritLpbQlr(List<Object> fwFcqlrDOList, String xmId, String bdcdyh, String qlrLb);

    /**
     * 根据权利人类别和项目id查询根据顺序号排序的权利人信息
     * @param xmid
     * @param qlrlb
     * @return
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 根据权利人类别和项目id查询根据顺序号排序的权利人信息
     */
    List<BdcQlrDO> listBdcQlrOrderBySxh(String xmid, String qlrlb);


    /**
     * 获取当前初始化业务的权利人信息
     *
     * @param initServiceQO
     * @return List<Object> 权利人数据集合
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    List<Object> queryDjQlr(InitServiceQO initServiceQO,String qlrlb);


    /**
     * 批量插入指定数量的权利人
     *
     * @param xmidlist 需要插入权利人的 项目 id 集合
     * @param bdcQlrDO 权利对象
     * @return {List<BdcQlrDO>}
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcQlrDO> insertBdcQlrByNum(BdcQlrDO bdcQlrDO, List<String> xmidlist);


    /**
     * 批量发一本证的义务人或者权利人组织
     * @param gzlslid 工作流实例id
     * @param qlrlb 权利人类别
     * @param djxl 登记小类
     * @return 组织字符串
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     */
    String queryQlrsYbzs(@NotBlank(message = "gzlslid 不能为空") String gzlslid,@NotBlank(message = "qlrlb 不能为空") String qlrlb,String djxl);

    /**
     * 根据工作流实例id更新权利人的zsid
     *@param zsid 证书id
     * @param gzlslid 工作流实例id
     * @param qlrmc 证件号
     * @param zjh 权利人名称
     */
    void updateQlrZsid(@NotBlank(message = "gzlslid 不能为空") String gzlslid,@NotBlank(message = "zsid 不能为空") String zsid,String qlrmc,String zjh);

    /**
     * 批量更新不动产权利人
     * @param bdcDjxxUpdateQO 登记信息更新对象
     * @return  更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  批量更新不动产权利人
     */
    int updateBatchBdcQlr(BdcDjxxUpdateQO bdcDjxxUpdateQO) throws ClassNotFoundException;

    /**
     * @param gzlslid 工作流实例
     * @return 返回根据证件号分组的权利人义务人分组对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 对权利人/义务人根据证件号进行分组
     */
    List<BdcQlrGroupDTO> groupQlrYwrByZjh(@NotBlank(message = "gzlslid 不能为空")String gzlslid,String qlrlb,String djxl,String xmidList);

    /**
     * @param obj 权利人信息
     * @param type 修改类型：update:更新,insert:新增，DELETE:删除
     * @return 权利人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 通过当前权利人获得与其组合同步修改的所有权利人信息
     */
    List<JSONObject> listZhBdcQlr(JSONObject obj, String type);

    /**
     * @param updateBdcQlrDOList 权利人信息  -- 用于批量,当前只会处理当前手的第三方权利人信息
     * @param type 修改类型：update:更新,insert:新增，DELETE:删除
     * @return 权利人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">wangyinghao</a>
     * @description 通过当前权利人同步修改的所有权利人信息
     */
    List<BdcQlrDO> listPlBdcQlr(List<BdcQlrDO> updateBdcQlrDOList, String type);

    /**
     * 根据条件查询权利人信息
     *
     * @param bdcQlrDO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     */
    List<BdcQlrDO> listBdcQlrByCondition(BdcQlrDO bdcQlrDO);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/2/7 10:31
     */
    void deleteDsQlrsByQlrxx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid, String djxl, BdcDsQlrDO bdcDsQlrDO, List<String> xmidList);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    List<BdcQlrDO> listBdcQlrByXmidList(List<String> xmidList,String qlrlb);

    /**
     * 查询证书（证明）关联的权利人（义务人）信息
     * @param zsid 证书ID
     * @param qlrlb 权利人类别
     * @return {List} 权利人信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    List<BdcQlrDO> listBdcQlrByZsid(String zsid, String qlrlb);
}
