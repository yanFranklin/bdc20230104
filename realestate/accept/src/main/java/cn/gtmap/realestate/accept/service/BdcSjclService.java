package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.*;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/29
 * @description 不动产受理收件材料服务
 */
public interface BdcSjclService {

    /**
     * @param gzlslid 工作流实例ID
     * @param bdcSlXmDOList 受理项目集合
     * @return 受理收件材料
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据流程初始化不动产受理收件材料
     */
    List<BdcSlSjclDO> listCshBdcSlSjclByLc(String gzlslid, List<BdcSlXmDO> bdcSlXmDOList);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid    项目ID
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 更新不动产受理收件材料页数
     */
    Integer updateSjclYs(String gzlslid, String xmid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新组合流程收件材料
     * @date : 2021/12/20 13:50
     */
    Integer updateZhlcSjclYs(String gzlslid);


    /**
     * @param sjclid 收件材料ID
     * @param czlx   操作类型
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 修改收件材料顺序号
     */
    Integer changeSjclSxh(String sjclid, String czlx);

    /**
     * @param json
     * @return 修改数量
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 保存收件材料
     */
    Integer saveSjcl(String json);

    /**
     * @param sjclid 收件材料ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 删除收件材料
     */
    void deleteSjcl(String sjclid);


    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/12 16:53
     */
    void deleteAllSjcl(String gzlslid);

    /**
     * @param gzlslid 工作流实例id
     * @param sjclIdsStr
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据gzlslid获取收件材料名称
     */
    List<String> listSjclMc(String gzlslid, String sjclIdsStr);

    /**
     * @param gzlslid 工作流实例ID
     * @return 是否验证通过
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证收件材料份数页数是否为空
     */
    Boolean checkSjclYsFs(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param bdcDsfSjclDTOList 第三方收件材料
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存第三方收件材料
     */
    void saveDsfSjcl(String gzlslid,List<BdcDsfSjclDTO> bdcDsfSjclDTOList) throws IOException;

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据登记原因重新创建收件材料
     * @date : 2019/12/12 17:47
     */
    List<BdcSlSjclDO> reCreateSjcl(String gzlslid);

    /**
     * @param slbh 受理编号
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据受理编号查询不动产受理收件材料
     */
    List<BdcSlSjclDTO> listSjclBySlbh(String slbh);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存税务税票附件
     */
    void saveSwspfj(List<BdcSwspFjDTO> bdcSwspFjDTOList, String gzlslid, String clmc);


    /**
     * @param json 收件材料信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/3/29 15:38
     */
    Integer saveZhSjcl(String json);


    /**
     * @param sjclUploadDTO 收件材料内容， 文件夹名称， 登记小类， 工作流实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 创建收件材料并上传，受理库没有对应文件夹则新增文件夹
     * @date : 2021/5/11 14:20
     */
    void createAndUploadFile(SjclUploadDTO sjclUploadDTO) throws IOException;

    /**
     * @param sjclJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 复制收件材料
     * @date : 2021/7/12 14:24
     */
    void copySjcl(String sjclJson);

    /**
     * @param sjclJson
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     * @description 复制收件材料
     * @date : 2021/7/12 14:24
     */
    void copyzhSjcl(String sjclJson);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 继承收件材料
     * @date : 2021/9/22 14:18
     */
    void extendSjcl(String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 更新是否批注
     * @return
     */
    Integer updateSjclSfpz(String gzlslid);

    /**
     * @param json    上传附件信息
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 上传一体化平台审批附件
     */
    void uploadYthSpfj(String json, String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 继承原项目的收件材料信息
     * @date : 2022/1/6 14:40
     */
    void extendYxmSjcl(String gzlslid);

    /**
     * @param gzlslid 工作流实例id
     * @return object
     * @throws IOException
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @description 从第三方获取获取委托信息文件保存到附件材料
     * @date : 2022/11/25
     */
    Boolean downloadWtcl(String gzlslid) throws IOException;

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 多测合一附件获取
     * @date : 2022/12/20 8:46
     */
    void downDchyfj(SlymDchyDTO slymDchyDTO);

}
