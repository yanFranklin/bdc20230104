package cn.gtmap.realestate.common.core.service.rest.building;


import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.dto.building.FwlxBgRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-08
 * @description 逻辑幢相关服务
 */
public interface FwLjzRestService {

    /**
     * @param pageable
     * @param paramJson
     * @return java.lang.Object
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 逻辑幢分页查询方法
     */
    @PostMapping("/building/rest/v1.0/ljz/page")
    Page<Map> listLjzByPageJson(Pageable pageable,
                                @RequestParam(name = "paramJson", required = false) String paramJson);

    /**
     * @param paramJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询预测逻辑幢
     * @date : 2020/12/28 17:05
     */
    @PostMapping("/building/rest/v1.0/ljz/yc/page")
    Page<Map> listYcLjzByPageJson(Pageable pageable,
                                  @RequestParam(name = "paramJson", required = false) String paramJson);

    /**
     * @param fwDcbIndex
     * @return void
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键删除逻辑幢信息
     */
    @DeleteMapping("/building/rest/v1.0/ljz/{fwDcbIndex}")
    Integer deleteLjzByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwDcbIndex
     * @return cn.gtmap.realestate.common.core.dto.building.LjzResponseDTO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据主键查询逻辑幢
     */
    @GetMapping("/building/rest/v1.0/ljz/{fwDcbIndex}")
    FwLjzDO queryLjzByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwLjzDO
     * @return FwLjzDO
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 新增逻辑幢
     */
    @PostMapping("/building/rest/v1.0/ljz")
    FwLjzDO insertFwLjz(@RequestBody FwLjzDO fwLjzDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwLjzDO
     * @param updateNull true表示空字段更新，false，表示空字段不更新
     * @return void
     * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
     * @description 修改逻辑幢
     */
    @PutMapping("/building/rest/v1.0/ljz")
    Integer updateFwLjz(@RequestBody FwLjzDO fwLjzDO, @RequestParam(name = "updateNull", required = false) boolean updateNull,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * 根据BDCDYH 查询逻辑幢基本信息
     *
     * @param bdcdyh bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping("/building/rest/v1.o/ljz/bdcdyh/{bdcdyh}")
    FwLjzDO queryLjzByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param bdcdyhList
     * @return
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 根据BDCDYH查询房逻辑幢流程状态
     */
    @PostMapping("/building/rest/v1.0/ljz/lczt")
    List<String> queryLjzLcztByBdcdyh(@RequestBody List<String> bdcdyhList);

    /**
     * @param djh
     * @param zrzh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.FwLjzDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据宗地和自然幢号查询逻辑幢列表
     */
    @GetMapping("/building/rest/v1.0/ljz/list/{djh}")
    List<FwLjzDO> listLjzByDjhAndZrzh(@PathVariable("djh") String djh, @RequestParam(name = "zrzh", required = false) String zrzh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param fwLjzDO
     * @return cn.gtmap.realestate.common.core.domain.building.FwLjzDO
     * @description 逻辑幢挂接宗地
     */
    @PutMapping("/building/rest/v1.0/ljz/relevancezd")
    void fwljzRelevanceZd(@RequestBody FwLjzDO fwLjzDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param lszd
     * @return java.lang.String
     * @description 生成逻辑幢号
     */
    @PutMapping("/building/rest/v1.0/ljz/creatljzh")
    String creatLjzh(@RequestParam(name = "lszd", required = false) String lszd,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwDcbIndex
     * @return void
     * @description 取消挂接宗地
     */
    @GetMapping("/building/rest/v1.0/ljz/qxgjzd/{fwDcbIndex}")
    void fwljzQxgjzd(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwLjzDO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢实体查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/ljz/validbdcdyh/fwljzdo")
    List<String> listValidBdcdyhByFwLjzDO(@RequestBody FwLjzDO fwLjzDO,@RequestParam(name = "qjgldm", required = false) String qjgldm);
    /**
     * @param fwDcbIndexList
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢主键集合查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/ljz/validbdcdyh/indexlist")
    List<String> listValidBdcdyhByFwLjzIndexList(@RequestBody List<String> fwDcbIndexList,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwDcbIndex
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据fwDcbIndex查询有效的不动产单元号
     */
    @GetMapping("/building/rest/v1.0/ljz/vaildbdcdyh/{fwDcbIndex}")
    List<String> listValidBdcdyhByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param fwlxBgRequestDTO
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据房屋类型变更dto查询有效的不动产单元号（因为逻辑幢变更所以写这里）
     */
    @PostMapping("/building/rest/v1.0/ljz/validbdcdyh/fwlxbgdto")
    List<String> listValidBdcdyhByFwlxBgRequestDTO(@RequestBody FwlxBgRequestDTO fwlxBgRequestDTO);

    /**
     * @param jsonData
     * @return List<String>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据json查询有效的不动产单元号
     */
    @PostMapping("/building/rest/v1.0/ljz/vaildbdcdyh/json")
    List<String> listValidBdcdyhByJson(@RequestBody String jsonData,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [slbh] 受理编号
     * @return: String 匹配过后的受理编号
     * @description 当受理编号为CH开头时，通过fw_ljz和s_sj_bgsh关联查询，采用s_sj_bgsh的slbh
     */
    @GetMapping("/building/rest/v1.o/ljz/link/{slbh}")
    String fwljzLinkBgsh(@PathVariable("slbh")String slbh,@RequestParam(name = "qjgldm", required = false) String qjgldm);

}
