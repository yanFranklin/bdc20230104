package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/7/15 19:44
 * @description 共有信息部分查询接口
 */
public interface BdcFdcq3GyxxRestService {
    /**
     * @param xmid
     * @return List<BdcFdcq3GyxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建筑物区分所有权业主共有部分登记信息_共有部分
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/bdcQlByXmid", method = RequestMethod.GET)
    List<BdcFdcq3GyxxDO> queryListBdcQlByXmid(@RequestParam(name = "xmid") String xmid);

    /**
     * @param bdcdyh 不动产单元号（或地籍号）
     * @return List<BdcFdcq3GyxxDO> 查询现势的共有信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据单元号或者地籍号，查询现势的共有信息
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/djb/fdcq3Gyxx", method = RequestMethod.GET)
    List<BdcFdcq3GyxxDO> queryListFdcq3Gyxx(String bdcdyh);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return xmid 项目ID
     * @description 获取建筑物区分所有权业主共有部分登记权利人信息
     */
    @GetMapping("/realestate-register/rest/v1.0/djb/fdcq3Gyxx/qlr")
    String getFdcq3Qlr(@RequestParam("xmid") String xmid);

}
