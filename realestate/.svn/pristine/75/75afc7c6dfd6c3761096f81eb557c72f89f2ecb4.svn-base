package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.SZdTdsyqlxDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.FwBdcdyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-08
 * @description 权籍不动产单元信息服务
 */
public interface BdcdyRestService {

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询实测户室不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/bdcdy/sc/page")
    Page<Map> listScFwHsBdcdy(Pageable pageable,
                              @RequestParam(name = "paramJson",required = false) String paramJson);

    /**
     * @param pageable
     * @param paramJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询预测户室不动产单元信息
     */
    @PostMapping("/building/rest/v1.0/bdcdy/yc/page")
    Page<Map> listYcFwHsBdcdy(Pageable pageable,
                              @RequestParam(name = "paramJson", required = false) String paramJson);


    /**
     * @param bdcdyh
     * @param bdcdyfwlx
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.building.BdcdyPageResponseDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据BDCDYH查询不动单元基本信息
     */
    @GetMapping("/building/rest/v1.0/bdcdy/{bdcdyh}")
    BdcdyResponseDTO queryBdcdy(@PathVariable("bdcdyh") String bdcdyh,
                                @RequestParam(name = "bdcdyfwlx", required = false) String bdcdyfwlx, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwbm
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @description 根据Fwbm查询不动单元基本信息(包含FW_HS  FW_YCHS FW_LJZ FW_XMXX)
     */
    @GetMapping("/building/rest/v1.0/bdcdy/byfwbm")
    BdcdyResponseDTO queryBdcdyByFwbm(@RequestParam("fwbm") String fwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ysfwbm
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @description  根据ysFwbm查询户室不动单元基本信息(包含FW_HS  FW_YCHS)
     */
    @GetMapping("/building/rest/v1.0/bdcdy/byysfwbm")
    BdcdyResponseDTO queryHsBdcdyByYsfwbm(@RequestParam("ysfwbm") String ysfwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param ysfwbm
     * @param hslx
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据ysFwbm查询户室不动单元基本信息(可根据户室类型做判断)
     */
    @GetMapping("/building/rest/v1.0/bdcdy/byysfwbmwithhslx")
    BdcdyResponseDTO queryHsBdcdyByYsfwbmWithHslx(@RequestParam(value = "ysfwbm", required = false) String ysfwbm,
                                                  @RequestParam(name = "hslx", required = false) String hslx,
                                                  @RequestParam(name = "zl", required = false) String zl,
                                                  @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.FwHsDO
     * @description 根据BDCDYH查询户室信息
     */
    @GetMapping("/building/rest/v1.0/bdcdy/hs/{bdcdyh}")
    FwHsDO queryHsByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,
                           @RequestParam(name = "hslx", required = false) String hslx, @RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param fwbm
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.building.FwBdcdyDTO>
     * @description 根据FWBM查询户室类型的不动产单元
     */
    @GetMapping("/building/rest/v1.0/bdcdy/hs/listbyfwbm")
    List<FwBdcdyDTO> listFwBdcdyByFwbm(@RequestParam(name = "fwbm") String fwbm,@RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ybdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.BdcdyResponseDTO
     * @description 合肥版本中 FW_HS FW_LJZ 表中包含ybdcdyh
     */
    @GetMapping("/building/rest/v1.0/bdcdy/hs/listbyybdcdyh")
    BdcdyResponseDTO queryFwHsAndFwLjzByYbdcdyh(@RequestParam(name = "ybdcdyh") String ybdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable, paramJson
     * @return org.springframework.data.domain.Page<java.util.Map>
     * @description 为外网提供的 分页查询房产信息
     */
    @PostMapping("/building/rest/v1.0/bdcdyh/hs/wwsq/listbypage")
    Page<Map> listHsForWwsqByPage(Pageable pageable,@RequestParam(name = "paramJson",required = false)String paramJson);

    /**
     * @param pageable, paramJson
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询户室信息，实测预测
     * @date : 2020/12/16 18:20
     */
    @PostMapping("/building/rest/v1.0/bdcdyh/hs/scyc/listbypage")
    Page<Map> listScYcHsxxByPage(Pageable pageable, @RequestParam(name = "paramJson",required = false)String paramJson);

    /**
     * @param bdcdyResponseDTO
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化建设用地量化信息
     */
    @PostMapping("/building/rest/v1.0/bdcdy/jsydlhxx")
    BdcdyResponseDTO initJsydLhxx(@RequestBody BdcdyResponseDTO bdcdyResponseDTO);

    /**
     * @param bdcdyh 不动产单元号
     * @return 不动产单元房屋类型
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据bdcdyh查询bdcdyfwlx
     */
    @GetMapping("/building/rest/v1.0/bdcdy/bdcdyfwlx/{bdcdyh}")
    String queryBdcdyfwlx(@PathVariable(name = "bdcdyh") String bdcdyh, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
     * @param houseid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据house——id先查户室数据，再查相关信息
     * @date : 2022/5/16 15:08
     */
    @GetMapping("/building/rest/v1.0/bdcdy/houseid")
    BdcdyResponseDTO queryBdcdyByHoseId(@RequestParam(value = "bdcdyh", required = false) String bdcdyh, @RequestParam(value = "houseid", required = false) String houseid,
                                        @RequestParam(name = "bdcdyfwlx", required = false) String bdcdyfwlx, @RequestParam(name = "qjgldm", required = false) String qjgldm);

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 根据houseId、坐落或是bdcdyh获取户室数据
    */
    @GetMapping("/building/rest/v1.0/bdcdy/getHssj")
    List<BdcdyResponseDTO> queryBdcdyByHouseId(@RequestParam(name = "houseid",required = false) String houseid,
                                         @RequestParam(name = "bdcdyh",required = false) String bdcdyh,
                                         @RequestParam(name = "zl",required = false) String zl);

    /**
    * @return
    * @author <a href = "mailto:fanghao@gtmap.cn">fanghao</a>
    * @description 获取tdsyqlx字典
     * @param dm
    */
    @GetMapping("/building/rest/v1.0/bdcdy/getTdsyqlxZd")
    List<SZdTdsyqlxDO> getTdsyqlxZd(@RequestParam(name = "dm") String dm);
}
