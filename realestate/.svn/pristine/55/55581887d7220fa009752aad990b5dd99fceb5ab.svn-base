package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
import cn.gtmap.realestate.common.core.dto.building.DjdcbJzxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.ZdxxByJqxxDTO;
import cn.gtmap.realestate.common.core.qo.building.ZdxxByJqxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/8
 * @description 宗地相关服务
 */
public interface ZdRestService {
    /**
     * @param pageable
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询宗地信息
     */
    @PostMapping("/building/rest/v1.0/zd/page")
    Page<Map> listZdByPageJson(Pageable pageable,
                               @RequestParam(name = "paramJson", required = false) String paramJson);

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据逻辑幢主键初始化宗地目录树 包括宗地 和 宗地下的自然幢列表
     */
    @GetMapping("/building/rest/v1.0/zd/tree/{fwDcbIndex}")
    ZdTreeResponseDTO initZdTreeByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @description 根据地籍号初始化宗地目录树(自然幢列表来源于FW_LJZ)
     */
    @GetMapping("/building/rest/v1.0/zd/tree/djh/{djh}")
    ZdTreeResponseDTO initZdTreeByDjhAndAllZrzh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.ZdTreeResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据逻辑幢主键初始化宗地目录树 包括宗地 和 宗地下的自然幢列表
     */
    @GetMapping("/building/rest/v1.0/zd/tree/{fwDcbIndex}/withallzrz")
    ZdTreeResponseDTO initZdTreeByFwDcbIndexAndAllZrzh(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询宗地地籍数据
     */
    @GetMapping("/building/rest/v1.0/zd/{bdcdyh}")
    ZdDjdcbDO queryZdByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyhList
     * @return lczt
     * @author
     * @description 根据不动产单元号查询宗地流程状态
     */
    @PostMapping("/building/rest/v1.0/zd/lczt")
    List<String> queryZdLcztByBdcdyh(@RequestBody List<String> bdcdyhList);

    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.ZdQlrDO>
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description
     */
    @GetMapping("/building/rest/v1.0/zd/qlr/{bdcdyh}")
    List<ZdQlrDO> listZdQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.ZdDjdcbDO
     * @description  根据地籍号查询宗地地籍数据
     */
    @GetMapping("/building/rest/v1.0/zd/djh/{djh}")
    ZdDjdcbDO queryZdByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param pageable
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询宗地界址信息信息
     */
    @PostMapping("/building/rest/v1.0/zdjzxx/page")
    Page<DjdcbJzxxResponseDTO> listZdjzxxByPageJson(Pageable pageable,
                                                    @RequestParam(name = "paramJson", required = false) String paramJson);

    /**
     * @param djh
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据地籍号查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/zd/validbdcdyh/djh")
    List<String> listValidBdcdyhByDjh(@RequestParam(value = "djh", required = false) String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * 根据地籍号和自然幢号查询有效的房屋户室不动产单元号
     * @param djh 地籍号
     * @param zrzh 自然幢号
     * @return List<String> 房屋户室不动产单元号
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping("/building/rest/v1.0/zd/validbdcdyh/djhzrzh")
    List<String> listValidBdcdyhByDjhZrzh(@RequestParam(value = "djh") String djh, @RequestParam(value = "zrzh", required = false) String zrzh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return java.lang.String
     * @description 根据DJH查询宗地图 JPG
     */
    @GetMapping("/building/rest/v1.0/zd/zdt/{djh}")
    ZdtResponseDTO queryZdtByDjh(@PathVariable(name = "djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return java.lang.String
     * @description 根据bdcdyh查询宗地图 JPG
     */
    @GetMapping("/building/rest/v1.0/zd/zdt/bdcdyh/{bdcdyh}")
    ZdtResponseDTO queryZdtByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">wangzijie</a>
     * @description 根据DJH查询宗地图文档中心ID

     @GetMapping("/building/rest/v1.0/zd/zdtid/{djh}") String queryZdtIDByDjh(@PathVariable(name = "djh") String djh) throws IOException;*/

    /**
     * 根据合同编号查询承包土地图层信息
     *
     * @param htbh 合同编号
     * @return ZdtResponseDTO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Date 13:55 2020/8/24
     */
    @GetMapping("/building/rest/v1.0/zd/zdt/cbtdsyt/{htbh}")
    ZdtResponseDTO queryCbzdsytByCbhtbh(@PathVariable(name = "htbh") String htbh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param djh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据地籍号查询宗地权利人
     * @date : 2021/1/22 19:33
     */
    @GetMapping("/building/rest/v1.0/zd/qlr/djh/{djh}")
    List<ZdQlrDO> listZdQlrByDjh(@PathVariable("djh") String djh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @param bdcdyh
     * @return java.lang.String
     * @description 根据bdcdyh查询宗地图 JPG ftp
     */
    @GetMapping("/building/rest/v1.0/zd/zdt/bdcdyh/ftp/{bdcdyh}")
    ZdtResponseDTO queryZdtByBdcdyhFtp(@PathVariable(name = "bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param pageable
     * @param paramJson
     * @return Page<Map>
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 分页查询宗地信息
     */
    @PostMapping("/building/rest/v1.0/zdxx/page")
    Page<Map> listZdxxByPageJson(Pageable pageable,
                               @RequestParam(name = "paramJson", required = false) String paramJson);

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">huangjian</a>
     * @description 根据zl查询宗地地籍数据
     */
    @GetMapping("/building/rest/v1.0/zd/zl")
    ZdDjdcbDO queryZdByZlAndBdcdyh(@RequestParam(name = "zl",required = false) String zl,@RequestParam(name = "bdcdyh", required = false) String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param zdxxByJqxxQOStr
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangyongming</a>
     * @description 根据坐落、不动产权证、不动产单元号、地籍号、权利人名称查询宗地信息
     */
    @PostMapping("/building/rest/v1.0/zd/zdxx/queryTdxxList")
    Page<ZdxxByJqxxDTO> findZdxxByJqxx(Pageable pageable, @RequestParam(name = "zdxxByJqxxQOStr",required = false) String  zdxxByJqxxQOStr);
}
