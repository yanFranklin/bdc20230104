package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlLzrQzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcFzjlZsDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcFzjlPdfDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.register.BdcQzxxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcSzxxVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/15
 * @description
 */
public interface BdcFzjlRestService {
    /**
     * @param xmid       主项目ID
     * @param sfhb       是否合并批量更新
     * @param bdcFzjlDTO 发证记录信息
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新发证记录信息（领证人和备注信息）
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/{xmid}/{sfhb}", method = RequestMethod.PUT)
    int updateFzjl(@PathVariable(name = "xmid") String xmid, @PathVariable(name = "sfhb") boolean sfhb, @RequestBody BdcFzjlDTO bdcFzjlDTO);

    /**
     * @param zsid 证书ID
     * @return BdcFzjlDTO 发证记录信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前证书的发证记录
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/zsFzjl/{zsid}", method = RequestMethod.GET)
    BdcFzjlDTO queryBdcZsFzjl(@PathVariable(name = "zsid") String zsid);
    /**
     * @param xmid 项目ID
     * @return BdcFzjlDTO 查询的发证记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询当前项目的发证记录
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/{xmid}", method = RequestMethod.GET)
    BdcFzjlDTO queryFzjl(@PathVariable(name = "xmid") String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @param bdcXmDOList
     * @return 流程的发证记录
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 合并显示流程的发证记录
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/hbfzjl/{gzlslid}", method = RequestMethod.POST)
    BdcFzjlDTO queryHbFzjl(@PathVariable(name = "gzlslid") String gzlslid, @RequestBody List<BdcXmDO> bdcXmDOList);


    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXmDOList 流程所有的项目信息
     * @return 发证记录信息（一个发证记录）
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程发一个发证记录（基本信息取一个项目，证书信息获取流程所有的证书信息）
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/lc/one/{gzlslid}", method = RequestMethod.POST)
    BdcFzjlDTO queryLcOneFzjl(@PathVariable(name = "gzlslid") String gzlslid, @RequestBody List<BdcXmDO> bdcXmDOList);


    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param gzlslid 工作流实例ID
     * @param sfhb 是否合并显示
     * @return List<BdcFzjlDTO> 发证记录list (流程所有项目的fzjl)
     * @description 查询流程中所有
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/{gzlslid}/{sfhb}", method = RequestMethod.GET)
    List<BdcFzjlDTO> listBdcFzjl(@PathVariable(name = "gzlslid") String gzlslid, @PathVariable(name = "sfhb") boolean sfhb);


    /**
     * @param gzlslid 工作流实例ID
     * @return int 更新记录的数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程的发证人
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/{gzlslid}/fzr", method = RequestMethod.PUT)
    int updateFzr(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value = "userName") String userName);

    /**
     * @param gzlslid 工作流实例ID
     * @param isNullUpdate 只有当发证人为空的时候更新（true则做发证人是否为空的判断，否则直接更新发证信息）
     * @return int 更新记录的数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新流程的发证人
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/{gzlslid}/fzr", method = RequestMethod.POST)
    int updateFzr(@PathVariable(name = "gzlslid") String gzlslid, @RequestBody UserDto userDto, @RequestParam(name = "isNullUpdate", required = false) Boolean isNullUpdate);

    /**
     * @param gzlslid  工作流实例ID
     * @param userName 当前用户名/账户名
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存缮证人，缮证时间
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/{gzlslid}/szr", method = RequestMethod.PUT)
    BdcSzxxVO updateSzr(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value = "userName") String userName);

    /**
     * @param bdcZsQO 证书信息
     * @return int 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新指定证书的缮证人信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/szr", method = RequestMethod.POST)
    BdcSzxxVO updateSzr(@RequestBody BdcZsQO bdcZsQO);

    /**
     * @param gzlslid  工作流实例ID
     * @param userName 当前用户名/账户名
     * @return int 更新数据量
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 更新缮证人信息为当前用户
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/{gzlslid}/updateszr", method = RequestMethod.PUT)
    BdcSzxxVO updateSzrByGzlslid(@PathVariable(name = "gzlslid") String gzlslid, @RequestParam(value = "userName") String userName);

    /**
     * @param bdcPrintDTO 打印对象
     * @return String 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取发证记录的打印xml
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/fzjl/print/xml")
    String fzjlPrintXml(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 打印对象
     * @return String 发证记录打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 按流程生成发证记录，获取发证记录的打印xml
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/fzjl/one/print/xml")
    String fzjlOnePrintXml(@RequestBody BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcFzjlZsDTO 发证记录证书信息
     * @return int 执行数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新领证人信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/lzr", method = RequestMethod.PUT)
    int updateLzrxx(@RequestBody BdcFzjlZsDTO bdcFzjlZsDTO);

    /**
     * @param bdcFzjlZsDTO 发证记录证书信息
     * @return int 执行数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据工作流实例ID集合更新领证人信息
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/pl/lzr", method = RequestMethod.POST)
    void plUpldateLzrxx(@RequestBody BdcFzjlZsDTO bdcFzjlZsDTO);

    /**
     * 检查领证人信息（南通）
     * @param xmid       项目ID
     * @param bdcFzjlDTO 发证记录对象
     * @return String 提示信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @RequestMapping(value = "/realestate-certificate/rest/v1.0/fzjl/lzr/check", method = RequestMethod.POST)
    String checkLzr(@RequestParam(name = "xmid") String xmid, @RequestBody BdcFzjlDTO bdcFzjlDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlLzrQzxxDTO 领证人签字信息
     * @return {BdcQzxxDO} 入库签字信息
     * @description  保存领证人签字图片信息
     */
    @PostMapping("/realestate-certificate/rest/v1.0/fzjl/lzr/qzxx")
    BdcQzxxDO saveFzjlLzrQzxx(@RequestBody BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO);

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcFzjlLzrQzxxDTO 领证人签字信息
     * @return {BdcQzxxDO} 入库签字信息
     * @description  批量保存领证人签字图片信息
     */
    @PostMapping("/realestate-certificate/rest/v1.0/fzjl/lzr/pl/qzxx")
    List<BdcQzxxDO> plSaveFzjlLzrQzxx(@RequestBody BdcFzjlLzrQzxxDTO bdcFzjlLzrQzxxDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param xmid 项目ID
     * @param zsid 证书ID
     * @return {String} 领证人签字图片Base64字符串
     * @description  获取领证人签字图片信息
     */
    @GetMapping("/realestate-certificate/rest/v1.0/fzjl/lzr/qzxx")
    String getFzjlLzrQzxx(@RequestParam("xmid") String xmid, @RequestParam(value="zsid",required = false) String zsid);

    /**
     * 获取领证人签字图片信息（如果指定顺序号则查询项目指定顺序号签字信息，否则查询项目所有签字信息）
     * @param bdcQzxxQO 签字信息查询参数
     * @return {List} 发证记录领证人签字信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/realestate-certificate/rest/v1.0/fzjl/lzr/qzxxs")
    List<BdcQzxxDO> getFzjlLzrQzxxs(@RequestBody BdcQzxxQO bdcQzxxQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcFzjlPdfDTO PDF及项目信息
     * @return {String} 文件存储ID
     * @description  保存发证记录文件到大云并关联到当前项目作为附件
     */
    @PostMapping("/realestate-certificate/rest/v1.0/fzjl/pdf")
    String saveFzjlPdfFile(@RequestBody BdcFzjlPdfDTO bdcFzjlPdfDTO);
}
