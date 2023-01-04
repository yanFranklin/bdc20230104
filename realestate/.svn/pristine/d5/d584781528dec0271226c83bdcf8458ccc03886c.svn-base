package cn.gtmap.realestate.etl.service;


import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxxFjxxDo;
import cn.gtmap.realestate.etl.core.dto.WwsqDTO;
import cn.gtmap.realestate.etl.core.dto.WwsqExportDTO;
import cn.gtmap.realestate.etl.core.qo.WwsqHtmlQO;
import cn.gtmap.realestate.etl.core.qo.WwsqQO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Map;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/06/11,1.0
 * @description 互联网业务信息查询方法
 */
public interface HlwYwxxService {
    /**
     * @param pageable 分页信息
     * @param wwsqQO 外网申请查询参数
     * @return org.springframework.data.domain.Page<Map>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 分页查询互联网业务信息
     */
    Page<Map> listHlwYwxxByPage(Pageable pageable, WwsqQO wwsqQO);

    /**
     * 根据互联网xmid查询互联网业务信息，组织为创建接口需要的格式
     *
     * @param hlwxmid 互联网项目ID
     * @return
     */
    JSONObject queryHlwJsonByHlwXmid(String hlwxmid,String slr);

    /**
      * @param wwsqExportDTO 外网申请导出DTO
      * @return PDF打印数据
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 获取外网申请PDF打印数据
      */
    String getHlwSqxxPrintXml(WwsqExportDTO wwsqExportDTO);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    WwsqDTO queryWwsqDTO(WwsqHtmlQO wwsqHtmlQO);

    /**
      * @param hlwxmid 互联网项目ID
      * @return zip路径
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 导出附件
      */
    String generateWwsqFjxx(String hlwxmid, String wwslbh,String parentPath) throws Exception;

    /**
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 回写登记信息到共享
      */
    void hxDjxx(JSONObject jsonObject);

    /**
     * 回写审核状态至互联网
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     */
    void modifyHlwShzt(String processInsId);

    /**
     * 获取附件信息文件流
     * @param gxWwSqxxFjxxDo 外网申请实体do
     * @return 附件文件流信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    InputStream getHlwFjxxStream(GxWwSqxxFjxxDo gxWwSqxxFjxxDo) throws Exception;


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据外网项目ID创建登记流程
     */
    Map<String, Object> cjBdcXm(String hlwxmid,String wwslbh,String slr);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据外网项目ID创建登记流程
     */
    Map<String, Object> cjBdcXm(String hlwxmid,String wwslbh,String slr,Boolean cxcj);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 通知互联网退件
      */
    void noticeHlwSlzt(String wwxmid,String sbyy,String czrmc);

    /**
     * @param bdcdyh 不动产单元号
     * @return 验证信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 互联网办件验证
     */
    Map<String, String> checkHlwBjzt(String bdcdyh);
}
