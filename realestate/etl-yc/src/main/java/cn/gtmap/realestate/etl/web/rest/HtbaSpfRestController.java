package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.gtc.clients.ElementClient;
import cn.gtmap.gtc.sso.domain.dto.OperationDto;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.building.SSjBdcdyhxsztDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaFwxxDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaSpfWqxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcWqbaLcGxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.service.rest.etl.HtbaSpfRestService;
import cn.gtmap.realestate.common.core.vo.etl.FgHtfyVO;
import cn.gtmap.realestate.common.core.vo.etl.HtbaFsssVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.etl.service.FgFyService;
import cn.gtmap.realestate.etl.service.HtbaFwxxService;
import cn.gtmap.realestate.etl.service.HtbaGzyzService;
import cn.gtmap.realestate.etl.service.HtbaSpfService;
import cn.gtmap.realestate.etl.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;


/**
 * @program: realestate
 * @description: ???????????????????????????
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-14 14:43
 **/
@Controller
@Validated
@RequestMapping("/realestate-etl/rest/v1.0/htbaxx")
public class HtbaSpfRestController extends BaseController implements HtbaSpfRestService {

    @Autowired
    HtbaSpfService htbaSpfService;

    @Autowired
    HtbaGzyzService htbaGzyzService;

    @Autowired
    HtbaFwxxService htbaFwxxService;

    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;

    @Autowired
    FgFyService fgFyService;
    @Autowired
    BdcWqbaLcGxFeignService bdcWqbaLcGxFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    ElementClient elementCient;

    /**
     * @param htbaxxQO ????????????
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????
     * @date : 2020/12/15 9:44
     */
    @ResponseBody
    @GetMapping()
    public Object listHtbaxxByPgae(@LayuiPageable Pageable pageable, HtbaxxQO htbaxxQO) {
        //???????????????????????????
        htbaxxQO.setHtbh(StringUtils.deleteWhitespace(htbaxxQO.getHtbh()));
        htbaxxQO.setQlrmc(StringUtils.deleteWhitespace(htbaxxQO.getQlrmc()));
        htbaxxQO.setSlbh(StringUtils.deleteWhitespace(htbaxxQO.getSlbh()));
        htbaxxQO.setZjh(StringUtils.deleteWhitespace(htbaxxQO.getZjh()));
        htbaxxQO.setZl(StringUtils.deleteWhitespace(htbaxxQO.getZl()));
        return addLayUiCode(htbaSpfService.listHtbaSpfxxByPage(pageable, htbaxxQO));
    }


    /**
     * @param baids
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2020/12/15 10:07
     */
    @ResponseBody
    @DeleteMapping
    public Object deleteBaxxList(@RequestParam(name = "baids") String baids) {
        return htbaSpfService.deleteBaxxList(baids);
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????id??????????????????
     * @date : 2020/12/15 10:36
     */
    @ResponseBody
    @GetMapping("/{baid}")
    public Object queryBaxx(@PathVariable("baid") String baid) {
        return htbaSpfService.queryHtbaxx(baid);
    }


    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ?????????????????????
     * @date : 2020/12/15 18:25
     */
    @ResponseBody
    @PatchMapping()
    public Object updateBaxxSpf(@RequestBody String json) {
        if(StringUtils.isBlank(json)) {
            throw new AppException("?????????????????????????????????");
        }
        return htbaSpfService.saveOrUpdatHtbaxx(json);
    }


    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2020/12/17 11:16
     */
    @ResponseBody
    @GetMapping("/sfyba/{baid}")
    public Boolean checkSfyba(@PathVariable("baid") String baid){
        boolean result = false;
        if (StringUtils.isBlank(baid)) {
            return false;
        }
        HtbaSpfDO htbaSpfDO = htbaSpfService.queryHtbapf(baid);
        //??????????????????????????????????????????????????????
        if (Objects.nonNull(htbaSpfDO) && StringUtils.isNotBlank(htbaSpfDO.getHtbh())) {
            result = true;
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/fsss")
    public Object glfsss(@RequestBody List<HtbaFsssVO> htbaFsssVOList, @RequestParam("baid") String baid) {
        //?????????????????????????????????????????????
        List<Map<String,Object>> resultList = new ArrayList<>(htbaFsssVOList.size());
        List<Map<String,Object>> paramList = new ArrayList<>(htbaFsssVOList.size());
        List<String> fwidList = new ArrayList<>(htbaFsssVOList.size());
        List<String> bdcdyhList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(htbaFsssVOList)) {
            for(HtbaFsssVO htbaFsssVO : htbaFsssVOList) {
                Map<String,Object> paramMap = new HashMap<>(2);
                paramMap.put("fwid",htbaFsssVO.getFwid());
                paramMap.put("bdcdyh",htbaFsssVO.getBdcdyh());
                fwidList.add(htbaFsssVO.getFwid());
                paramList.add(paramMap);
                bdcdyhList.add(htbaFsssVO.getBdcdyh());
            }
        }
        if(CollectionUtils.isNotEmpty(paramList)) {
            BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
            bdcGzYzQO.setZhbs("HTBA");
            bdcGzYzQO.setParamList(paramList);
            resultList = htbaGzyzService.queryGzyzResult(bdcGzYzQO);
        }
        if(CollectionUtils.isEmpty(resultList)) {
            //???????????????????????????????????????
            htbaFwxxService.glHtbaHsxx(fwidList,baid);

            // ??????????????????????????? ????????????spf???????????????
            HtbaSpfDO htbaSpfDO = htbaSpfService.queryHtbapf(baid);
            htbaSpfDO.setBazt(CommonConstantUtils.SF_S_DM);
            htbaSpfService.saveOrUpdatHtbaxx(JSONObject.toJSONString(htbaSpfDO));

            if(CollectionUtils.isNotEmpty(bdcdyhList)){
                bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyhList,"");
            }

        } else {
            return resultList;
        }
        return null;
    }


    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????
     * @date : 2020/12/18 11:19
     */
    @ResponseBody
    @GetMapping("/syncbazt/{baid}")
    public void syncBazt(@PathVariable("baid") String baid) {
        List<HtbaFwxxDO> htbaFwxxDOList = htbaFwxxService.listHtbaFwxxByBaid(baid);
        List<SSjBdcdyhxsztDO> list = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(htbaFwxxDOList)) {
            List<String> bdcdyhList = new ArrayList<>(htbaFwxxDOList.size());
            for(HtbaFwxxDO htbaFwxxDO: htbaFwxxDOList) {
                if(StringUtils.isNotBlank(htbaFwxxDO.getBdcdyh())) {
                    SSjBdcdyhxsztDO bdcdyhxsztDO = new SSjBdcdyhxsztDO();
                    bdcdyhList.add(htbaFwxxDO.getBdcdyh());
                    bdcdyhxsztDO.setSfba(CommonConstantUtils.SF_S_DM +"");
                    bdcdyhxsztDO.setBdcdyh(htbaFwxxDO.getBdcdyh());
                    list.add(bdcdyhxsztDO);
                }
            }
            if(CollectionUtils.isNotEmpty(bdcdyhList)) {
                bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyhList,"");
            }
        }
    }

    /**
     * @param bdcdyhList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????
     * @date : 2020/12/18 11:19
     */
    @ResponseBody
    @PostMapping("/syncbazt/bdcdyh")
    public void syncBaztByDyh(@RequestBody List<String> bdcdyhList) {
        if(CollectionUtils.isNotEmpty(bdcdyhList)) {
            bdcdyZtFeignService.syncBdcdyZtByBdcdyh(bdcdyhList,"");
        }
    }

    /**
     *  ???????????????????????????
     * @param gzlslid ???????????????ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return ?????????????????????????????????
     */
    @ResponseBody
    @GetMapping("/getSpfWqhtxx")
    @Override
    public List<HtbaSpfWqxxDTO> getSpfWqhtxx(@RequestParam("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("???????????????????????????ID");
        }
        return htbaSpfService.querySpfWqHtxx(gzlslid);
    }

    /**
     * @param baid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????????????????
     * @date : 2021/3/8 20:24
     */
    @ResponseBody
    @GetMapping("/fgfyxx/{baid}")
    public void syncFgfyxx(@PathVariable("baid") String baid) {
        if (StringUtils.isBlank(baid)) {
            throw new AppException("??????????????????????????????id");
        }
        HtbaSpfDO htbaSpfDO = htbaSpfService.queryHtbapf(baid);
        if (Objects.nonNull(htbaSpfDO) && StringUtils.isNotBlank(htbaSpfDO.getHtbh())) {
            Map map = new HashMap(1);
            map.put("htbabm", htbaSpfDO.getHtbh());
            List<FgHtfyVO> fgfyList = fgFyService.selectFgHtfyxx(map);
            if (CollectionUtils.isNotEmpty(fgfyList)) {
                FgHtfyVO fgHtfyVO = fgfyList.get(0);
                htbaSpfDO.setKfsmc(fgHtfyVO.getKfsmc());
                htbaSpfDO.setZzsyjzrq(fgHtfyVO.getZzsyjzrq());
                htbaSpfDO.setSysyjzrq(fgHtfyVO.getSysyjzrq());
                htbaSpfDO.setTdyt(fgHtfyVO.getTdyt());
                htbaSpfDO.setHtzjk(fgHtfyVO.getHtzjk());
                htbaSpfDO.setDkfs(fgHtfyVO.getDkfs());
                htbaSpfDO.setDksfkxx(fgHtfyVO.getDksfkxx());
                htbaSpfService.saveOrUpdatHtbaxx(JSONObject.toJSONString(htbaSpfDO));
                List<HtbaFwxxDO> htbaFwxxDOList = htbaFwxxService.listHtbaFwxxByBaid(baid);
                if (CollectionUtils.isNotEmpty(htbaFwxxDOList)) {
                    HtbaFwxxDO htbaFwxx = htbaFwxxDOList.get(0);
                    //???????????????????????????????????????
                    htbaFwxx.setCg(fgHtfyVO.getFycg());
                    htbaFwxx.setFwdj(fgHtfyVO.getFwdj());
                    htbaFwxx.setFwjg(fgHtfyVO.getFyjg());
                    htbaFwxx.setFwxz(fgHtfyVO.getFwxz());
                    htbaFwxx.setFwszc(fgHtfyVO.getMylc());
                    htbaFwxx.setFwzl(fgHtfyVO.getXmldfh());
                    htbaFwxx.setJzmj(fgHtfyVO.getJzmj());
                    htbaFwxx.setFwzcs(fgHtfyVO.getCs());
                    htbaFwxxService.saveHtbaFwxxDO(htbaFwxx);
                }
            }
        }
    }

    /**
     * @param htbh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/3/16 14:54
     */
    @ResponseBody
    @GetMapping("/wqlcgx/{htbh}")
    public Object queryWqbaxxBylc(@PathVariable(name = "htbh") String htbh, @RequestParam(name = "gzlslid", required = false) String gzlslid) {
        if (StringUtils.isAnyBlank(gzlslid, htbh)) {
            throw new AppException("??????????????????????????????????????????");
        }
        return htbaSpfService.listSpfWqxx(htbh, gzlslid);
    }

    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ??????????????????
     * @date : 2021/7/6 14:59
     */
    @ResponseBody
    @GetMapping("/synctable")
    public Object listSyncHtbaxxByPgae(@LayuiPageable Pageable pageable, HtbaxxQO htbaxxQO) {
        //???????????????????????????
        htbaxxQO.setHtbh(StringUtils.deleteWhitespace(htbaxxQO.getHtbh()));
        htbaxxQO.setBdcdyh(StringUtils.deleteWhitespace(htbaxxQO.getBdcdyh()));
        return addLayUiCode(htbaSpfService.listSyncHtbaSpfxxByPage(pageable, htbaxxQO));
    }

    /**
     * @param baxxList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????
     * @date : 2021/7/6 17:13
     */
    @ResponseBody
    @PostMapping("/sync")
    public Object syncBaxx(@NotBlank(message = "??????????????????????????????") @RequestBody String baxxList) {
        List<FgHtfyVO> fgHtfyVOList = JSON.parseArray(baxxList, FgHtfyVO.class);
        return htbaSpfService.syncBaxx(fgHtfyVOList);
    }

    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ????????????????????????
     * @date : 2021/7/6 17:27
     */
    @ResponseBody
    @GetMapping("/syncall")
    public Object syncBaxxAll(@NotNull(message = "????????????????????????????????????") HtbaxxQO htbaxxQO) {
        //?????????????????????????????????
        List<FgHtfyVO> syncList = htbaSpfService.listSyncBaxx(htbaxxQO);
        return htbaSpfService.syncBaxx(syncList);
    }

    /**
     * @param bdcdyh
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description ???????????????????????????????????????
     * @date : 2021/7/7 14:59
     */
    @ResponseBody
    @GetMapping("/synczt")
    public int querySynczt(@NotBlank(message = "????????????????????????????????????????????????") String bdcdyh) {
        int result = CommonConstantUtils.SF_F_DM;
        HtbaxxQO htbaxxQO = new HtbaxxQO();
        htbaxxQO.setBdcdyh(bdcdyh);
        List<HtbaSpfDO> htbaSpfList = htbaSpfService.listHtbaSpf(htbaxxQO);
        if (CollectionUtils.isNotEmpty(htbaSpfList)) {
            return CommonConstantUtils.SF_S_DM;
        }
        return result;
    }

    /**
     * @param moduleCode ????????????
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description ???????????????????????????
     */
    @ResponseBody
    @GetMapping("/moduleAuthority/{moduleCode}")
    public Object queryModuleState(@PathVariable(name = "moduleCode") String moduleCode) {
        String currentUserName = userManagerUtils.getCurrentUserName();
        if (StringUtils.isBlank(currentUserName)) {
            throw new MissingArgumentException("?????????????????????????????????");
        }
        return elementCient.getAuthorities(currentUserName, moduleCode);
    }


}
