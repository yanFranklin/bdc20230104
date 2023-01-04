package cn.gtmap.realestate.register.service.xxbl;

import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcBlxxDTO;

import java.util.List;

/**
 * 不动产信息补录服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.1, 2020/03/16 21:17
 */
public interface BdcXxblService {
    /**
     * 初始化补录数据
     *
     * @param bdcBlxxDTO 补录传输对象
     * @return List<BdcXmDO>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcXmDO> cshBlxx(BdcBlxxDTO bdcBlxxDTO) throws Exception;

    /**
     * 初始化补录流程数据
     *
     * @param bdcBlxxDTO 补录传输对象
     * @return List<BdcXmDO>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    TaskData cshLcxx(BdcBlxxDTO bdcBlxxDTO) throws Exception;

    /**
     * 初始化批量补录流程数据
     *
     * @param bdcBlxxDTOS 补录传输对象
     * @return List<BdcXmDO>
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    TaskData cshPlxxblLcxx(List<BdcBlxxDTO> bdcBlxxDTOS) throws Exception;

    /**
     * 初始化补录修改流程数据
     *
     * @param bdcBlxxDTO 补录传输对象
     * @return List<BdcXmDO>
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    TaskData cshModify(BdcBlxxDTO bdcBlxxDTO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息补录删除数据
     * @date : 2022/8/30 15:05
     */
    TaskData cshDeleteOrDelete(BdcBlxxDTO bdcBlxxDTO);

    /**
     * 补录流程办结事件
     *
     * @param processInstanceId 实例 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void endLc(String processInstanceId, String currentUserName);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息修改办结后判断匹配关系
     * @date : 2022/9/20 16:38
     */
    void endSjxgPpgx(String gzlslid, String userName) throws Exception;

    /**
     * @param gzlslid 补录修改流程的实例 id
     * @param jsyy    解锁原因
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 补录修改流程办结事件 <br>
     * 1. 解锁证书 <br>
     * 2. 还原被修改项目的 qszt 和 ajzt
     */
    void endModify(String gzlslid, String jsyy);

    /**
     * 复制初始化数据
     *
     * @param yxmid  被复制的项目 id
     * @param bdcdyh 需要复制的不动产单元
     * @return {Object} 如果初始化成功返回 BdcXmDO 集合，否则返回一个 null 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcXmDO> copyBlxx(String yxmid, String bdcdyh) throws Exception;

    /**
     * 挂接主房信息
     *
     * @param yxmid  被挂接的项目 id
     * @param bdcdyh 需要挂接的不动产单元
     * @return {BdcXmDO} 挂接后生成的项目信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 将传入的不动产单元号挂接到 yxmid 对应的项目 <br>
     * 场景：车库挂接主房
     * 1. 生成项目、fdcq、权利人和证书关系并且插入 cshfwkgsl <strong> zlcsh = 1 作为挂接项目的标识<strong/>
     * 2. 和 bdcdy 相关信息取权籍，其余取自原项目数据
     * 3. 受理人相关信息获取当前用户，受理时间取系统时间
     */
    BdcXmDO gjZfxx(String yxmid, String bdcdyh,String qjgldm) throws Exception;

    /**
     * 关联产权数据验证（判断是否可以关联产权）
     *
     * @param gzlslid 当前工作流实例 id
     * @param yxmid   上一手项目id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    String glsjyz(String gzlslid, String yxmid);

    /**
     * 关联上一手产权（根据配置决定是否继承上一手产权证号）
     *
     * @param gzlslid 当前工作流实例 id
     * @param xmid    上一手项目id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     **/
    void glcq(String gzlslid, String xmid, String blxmid);

    /**
     * 根据 xmid 查询是否生成证书
     * 如果不存在 BdcCshFwkgSlDO 则手动插入一条数据
     *
     * @param xmid 项目 id
     * @return {int} 是否生成证书， 0：否  1：是
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    int querySfsczs(String xmid, String djxl);

    /**
     * @param xmid xmid 用于判断删除的是挂接项目还是主项目
     * @param all  是否删除全部，true：删除全部，false：只删除当前项目「用于删除挂接项目」
     * @return {boolean} 是否删除成功
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除补录数据，补录台账删除数据 <br>
     * 删除业务数据和补录审核数据
     */
    boolean deleteBlxx(String xmid, boolean all);

    /**
     * @param xmid xmid
     * @return {BdcXmDTO} 项目对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 判断项目是否生成权利 <br>
     * 生成返回当前项目的 gzlslid 和 xmid <br>
     * 不生成返回上一手项目的 gzlslid 和 xmid <br>
     */
    BdcXmDO querySfscQl(String xmid);

    /**
     * @param processInsId 修改流程的工作流实例 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除补录修改流程数据 <br>
     * 1. 删除证书锁定数据 <br>
     * 2. 同步权籍证书锁定状态 <br>
     * 3. 还原补录修改流程数据
     */
    void deleteBllcModify(String processInsId) throws Exception;
    /**
     * 信息补录选择产权补录(仅限抵押权)
     * @param xmidListStr
     * @param yxmid
     * @return List<BdcXmDO>
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     */
    List<BdcXmDO> plbl(String yxmid, String xmidListStr) throws Exception;
    /**
     * 信息补录选择不动产单元批量补录
     * @param bdcdyhListStr
     * @param yxmid
     * @return List<BdcXmDO>
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     */
    List<BdcXmDO> selectBdcdyhPlbl(String yxmid, String bdcdyhListStr) throws Exception;
    /**
     * 初始化删除信息补录流程
     *
     * @param bdcBlxxDTO 初始化删除信息补录流程
     * @return List<BdcXmDO>
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     */
    TaskData cshDeleteXxblLc(BdcBlxxDTO bdcBlxxDTO) throws Exception;

    /**
     * 还原权利工作流事件，注销还原成现势
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     */
    void hyql(String processInsId);
    /**
     * 删除补录数据
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
     */
    void deleteBlxx(String processInsId);

}
