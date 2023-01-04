package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.register.BdcBlxxDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 不动产信息补录相关服务定义
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.1, 2020/03/16 18:22
 */
public interface BdcXxblRestService {

    /**
     * @param xmid xmid
     * @param all  是否删除全部，true：删除全部，false：只删除当前项目「用于删除挂接项目」
     * @return {boolean} 是否删除成功
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除补录数据，补录台账删除数据 <br>
     * 删除业务数据和补录审核数据
     */
    @DeleteMapping(value = "/realestate-register/rest/v1.0/blxx")
    boolean deleteBlxx(@RequestParam("xmid") String xmid, @RequestParam("all") boolean all);

    /**
     * 初始化补录数据
     *
     * @param bdcBlxxDTO 补录传输对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/csh")
    List<BdcXmDO> cshBlxx(@RequestBody BdcBlxxDTO bdcBlxxDTO) throws Exception;

    /**
     * 初始化补录流程数据
     *
     * @param bdcBlxxDTO  补录传输对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/bllc/csh")
    Object cshLcxx(@RequestBody BdcBlxxDTO bdcBlxxDTO) throws Exception;

    /**
     * 初始化批量信息补录流程数据
     *
     * @param bdcBlxxDTOS  补录传输对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/bllc/cshPlxxbl")
    Object cshPlxxblLcxx(@RequestBody List<BdcBlxxDTO> bdcBlxxDTOS) throws Exception;

    /**
     * 初始化补录流程数据
     *
     * @param bdcBlxxDTO 补录传输对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/bllc/modify")
    Object cshModify(@RequestBody BdcBlxxDTO bdcBlxxDTO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息补录删除流程接口，只初始化受理项目，基本信息和受理项目历史关系
     * @date : 2022/8/30 15:00
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/bllc/delete")
    Object cshDeletelc(@RequestBody BdcBlxxDTO bdcBlxxDTO);

    /**
     * @param processInsId 修改流程的工作流实例 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 删除补录修改流程数据 <br>
     * 1. 删除证书锁定数据 <br>
     * 2. 同步权籍证书锁定状态 <br>
     * 3. 还原补录修改流程数据
     */
    @DeleteMapping(value = "/realestate-register/rest/v1.0/blxx/{processInsId}/modify")
    void deleteBllcModify(@PathVariable("processInsId") String processInsId) throws Exception;

    /**
     * 补录流程办结事件(一般补录的)
     *
     * @param processInsId 实例 id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 登簿时更新当前项目（现势）和原项目（历史）的登簿信息以及权属状态, 更新案件状态为2已完成状态，并更新项目结束时间
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/bllc/{processInsId}/{currentUserName}/end")
    void endLc(@PathVariable(value = "processInsId") String processInsId, @PathVariable(value = "currentUserName") String currentUserName);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 信息修改结束后判断上一手数据是否存在匹配关系或者被匹配关系，如果存在同样需要更新关联关系
     * @date : 2022/9/20 16:33
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/bllc/{processInsId}/{currentUserName}/ppgx")
    void endSjxgPpgx(@PathVariable(value = "processInsId") String processInsId, @PathVariable(value = "currentUserName") String currentUserName) throws Exception;

    /**
     * 补录修改流程办结事件
     *
     * @param gzlslid 实例 id
     * @param jsyy    解锁原因
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/bllc/modify/{gzlslid}/end")
    void endModify(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "jsyy", required = false) String jsyy);

    /**
     * 复制初始化数据
     *
     * @param yxmid  被复制的项目 id
     * @param bdcdyh 需要复制的不动产单元
     * @return {Object} 如果初始化成功返回 BdcXmDO 集合，否则返回一个 null 集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/copy")
    List<BdcXmDO> copyBlxx(@RequestParam("yxmid") String yxmid, @RequestParam("bdcdyh") String bdcdyh) throws Exception;


    /**
     * 关联上一手产权（根据配置决定是否继承上一手产权证号）
     *
     * @param gzlslid 当前工作流实例 id
     * @param xmid    上一手项目id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     **/
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/glcq")
    void glcq(@RequestParam("gzlslid") String gzlslid, @RequestParam("xmid") String xmid,@RequestParam("blxmid")
            String blxmid);

    /**
     * 关联产权数据验证（判断是否可以关联产权）
     *
     * @param gzlslid 当前工作流实例 id
     * @param yxmid    上一手项目id
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/glsjyz")
    String glsjyz(@RequestParam("gzlslid") String gzlslid, @RequestParam("yxmid") String yxmid);

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
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/gj")
    BdcXmDO gjZfxx(@RequestParam("yxmid") String yxmid, @RequestParam("bdcdyh") String bdcdyh, @RequestParam(value = "qjgldm",required = false) String qjgldm) throws Exception;

    /**
     * 根据 xmid 查询是否生成证书
     * 如果不存在 BdcCshFwkgSlDO 则手动插入一条数据
     *
     * @param xmid 项目 id
     * @param djxl 登记小类
     * @return {int} 是否生成证书， 0：否  1：是
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/sfsczs")
    int querySfsczs(@RequestParam("xmid") String xmid, @RequestParam("djxl") String djxl);

    /**
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @param xmid xmid
     * @return {BdcXmDTO} 项目对象
     * @description 判断项目是否生成权利 <br>
     *     生成返回当前项目的 gzlslid 和 xmid <br>
     *     不生成返回上一手项目的 gzlslid 和 xmid <br>
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/sfscql")
    BdcXmDO querySfscQl(@RequestParam("xmid") String xmid);

    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/updateZxqlFj")
    void updateZxqlFj(@RequestBody List<String> xmidList);

    /**
     *
     *查询是否不需要是审核
     * @return
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/bxysh")
    boolean bxysh();

    /**
     *
     *信息补录批量补录
     * @return
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/plbl")
    List<BdcXmDO> plbl(@RequestParam("yxmid") String yxmid, @RequestParam("xmidList") String xmidList) throws Exception;

    /**
     *
     *信息补录选择不动产单元批量补录,
     * @return
     */
    @GetMapping(value = "/realestate-register/rest/v1.0/blxx/selectBdcdyhPlbl")
    List<BdcXmDO> selectBdcdyhPlbl(@RequestParam("yxmid") String yxmid, @RequestParam("bdcdyhList") String bdcdyhList) throws Exception;

    /**
     * 初始化删除信息补录流程
     *
     * @param bdcBlxxDTO 补录传输对象
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    @PostMapping(value = "/realestate-register/rest/v1.0/blxx/bllc/cshDeleteXxblLc")
    Object cshDeleteXxblLc(@RequestBody BdcBlxxDTO bdcBlxxDTO)  throws Exception;
}
