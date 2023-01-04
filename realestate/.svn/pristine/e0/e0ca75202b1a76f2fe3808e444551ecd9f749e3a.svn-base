package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.realestate.common.core.domain.certificate.BdcGdxxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmGdxxDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcGdxxQO;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/22
 * @description 归档信息服务
 */
public interface BdcGdxxRestService {

    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @param  archiveUrl
     * @param archiveXml
     * @return responseXml
     * @description 调用档案接口并获取归档结果xml
     */
    @RequestMapping(value="/realestate-certificate/rest/v1.0/gdxx/archive", method = RequestMethod.POST)
    String postArchiveInfo(@RequestParam(name = "archiveUrl") String archiveUrl,@RequestParam(name = "archiveXml") String archiveXml);

    /**
     * @param archiveResponseXml
     * @return List<BdcGdxxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据档案返回结果初始化归档信息实体对象的集合
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/init", method = RequestMethod.POST)
    List<BdcGdxxDO> initBdcGdxx(@RequestParam(name = "archiveResponseXml") String archiveResponseXml);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 解析档案返回xml插入归档信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/batch", method = RequestMethod.POST)
    void insertBdcGdxxList(@RequestBody String archiveResponseXml,@RequestParam(name = "currentUserName") String currentUserName);

    /**
     * @param bdcGdxxQO 归档查询对象
     * @return pageable  分页查询参数
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产归档信息分页
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/page", method = RequestMethod.POST)
    Page<BdcXmGdxxDTO> listBdcGdxxByPage(Pageable pageable, @RequestParam(name = "bdcGdxxQO",required = false) String bdcGdxxQO);

    /**
     * @param bdcGdxxQO 归档查询对象
     * @return List<BdcGdxxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询归档信息集合
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/list",method = RequestMethod.GET)
    List<BdcGdxxDO> listBdcGdxx(@RequestBody BdcGdxxQO bdcGdxxQO);

    /**
     * @param gdxxid
     * @return BdcGdxxDO
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据主键查询归档信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/{gdxxid}",method = RequestMethod.GET)
    BdcGdxxDO queryBdcGdxx(@NotBlank(message = "不能为空") @PathVariable(name = "gdxxid") String gdxxid);


    /**
     * @param gzlslid
     * @param archiveUrl
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据归档配置生成归档xml并归档
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/{gzlslid}/archive/batch", method = RequestMethod.POST)
    String postArchiveByPz(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "archiveUrl", required = false) String archiveUrl, @RequestParam(value = "xmid", required = false) String xmid, @RequestParam(value = "currentUserName") String currentUserName);


    /**
     * @param gzlslid
     * @return 不动产档案url
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据工作流实例id查询gdxx,拼装不动产档案url
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/{gzlslid}", method = RequestMethod.POST)
    String getBdcGdxxByGzlslid(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "damx", required = false)String damx,
                               @RequestParam(value = "xmid" ,required = false) String xmid);

    /**
     * @param xmid
     * @return daid
     * @author <a href ="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据xmid查询对应的daid
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/daid/{xmid}",method = RequestMethod.GET)
    String queryBdcDaid(@NotBlank(message = "不能为空") @PathVariable(name = "xmid") String xmid);

    /**
     * @param xmid
     * @return
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据xmid删除归档信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/gdxx/deletebyxmid/{xmid}",method = RequestMethod.GET)
    int deleteBdcGdxxByXmid(@NotBlank(message = "不能为空") @PathVariable(name = "xmid") String xmid);

    /**
     * 获取移交单展示台站配置
     * @return
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/gdxx/getGdxxType")
    String getGdxxType();
}
