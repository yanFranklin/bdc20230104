package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.exchange.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021/07/14
 * @description 上报获取权籍数据接口
 */
public interface AccessBuildingRestService {

    @GetMapping("/building/rest/v1.0/access/query/fwc")
    List<KttFwCDO> queryKttFwCList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    @GetMapping("/building/rest/v1.0/access/query/fwcDz")
    List<KttFwCDO> queryKttFwCListDz(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    @GetMapping("/building/rest/v1.0/access/query/fwzrz")
    List<Map> queryKttFwZrzList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    @GetMapping("/building/rest/v1.0/access/query/fwh")
    List<KttFwHDO> queryKttFwHList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam("sfdz") boolean sfdz, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    @GetMapping("/building/rest/v1.0/access/query/zd")
    List<KttGyJzdDO> queryKttGyJzdList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    @GetMapping("/building/rest/v1.0/access/query/jzx")
    List<KttGyJzxDO> queryKttGyJzxList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    @GetMapping("/building/rest/v1.0/access/query/fwljz")
    List<KttFwLjzDO> queryKttFwLjzList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    @GetMapping("/building/rest/v1.0/access/query/zhk")
    List<ZhKDO> queryZhkList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * 根据宗地代码查询宗地变更记录表，按照更新时间倒序
     *
     * @param bh 宗地代码
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/building/rest/v1.0/access/query/zdbgjlb")
    List<Map> queryZdbgjlbList(@RequestParam("bh") String bh, @RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * 根据宗地代码查询宗地宗地空间属性
     *
     * @param bh 宗地代码
     * @return map
     * @Date 2022/4/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/building/rest/v1.0/access/query/zdk")
    List<ZdKDO> queryZdList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);
}
