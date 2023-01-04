package cn.gtmap.realestate.exchange.web.main;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.YzwDTO;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwJgxxDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.qo.GxYzwQO;
import cn.gtmap.realestate.exchange.core.vo.YzwYzsbCshxxVO;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwCheckService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwTsZjkService;
import cn.gtmap.realestate.exchange.service.inf.yzw.yancheng.YzwDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.gtmap.realestate.common.util.PageUtils.addLayUiCode;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/11/27
 * @description 一张网
 */
@RestController
@RequestMapping("/realestate-exchange/yzw")
public class YzwController {
    private static final Logger LOGGER = LoggerFactory.getLogger(YzwController.class);

    @Autowired
    private YzwService yzwService;

    @Autowired
    private YzwCheckService yzwCheckService;

    @Autowired
    private YzwDataService yzwDataService;

    @Autowired
    private YzwTsZjkService zjkService;

    @Autowired
    private BdcXmMapper bdcXmMapper;


    /**
     * @param gxYzwQO 共享一张网查询QO
     * @return 分页推送信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 分页查询一张网推送信息
     */
    @ResponseBody
    @GetMapping("/listYzwTsxxByPageJson")
    public Object listYzwTsxxByPageJson(@LayuiPageable Pageable pageable, GxYzwQO gxYzwQO, boolean loadpage) {
        if (loadpage) {
            return addLayUiCode(yzwService.listYzwTsxxByPageJson(pageable, gxYzwQO));
        } else {
            return yzwService.listYzwTsxx(gxYzwQO);

        }

    }


    /**
     * @param yzwbhList 一张网编号集合
     * @param isAll     是否验证全部
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证共享一张网数据
     */
    @ResponseBody
    @PostMapping("/check")
    public Object yzwCheck(@RequestParam("yzwbhList") List<String> yzwbhList, @RequestParam("isAll") boolean isAll) {
        return yzwCheckService.checkYzwDataPl(yzwbhList, isAll);


    }

    /**
     * @param yzwbhList 一张网编号集合
     * @param isAll     是否全部
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 推送共享一张网数据
     */
    @ResponseBody
    @PostMapping("/ts")
    public Object yzwTs(@RequestParam("yzwbhList") List<String> yzwbhList, @RequestParam("isAll") boolean isAll, @RequestParam("isToday") boolean isToday) {
        return zjkService.checkTsqzkPl(yzwbhList, isAll, isToday);

    }


    /**
     * @param gxYzwQO 共享一张网查询QO
     * @return 统计各状态的占比
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 统计信息
     */
    @ResponseBody
    @GetMapping("/tj")
    public Object tjTszt(GxYzwQO gxYzwQO) {
        return yzwService.tjTszt(gxYzwQO);

    }

    /**
     * @param tsxxid 推送信息ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询验证信息
     */
    @ResponseBody
    @GetMapping("/yzxx")
    public Object listGxYzwYzxx(String tsxxid) {
        return yzwService.listGxYzwYzmxxxByTsxxid(tsxxid);


    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取受理时间
     */
    @ResponseBody
    @GetMapping("/getSlsj")
    public Object getSlsj(String yzwbh) {
        return yzwDataService.getSlsj(yzwbh);

    }


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 修改受理时间
     */
    @ResponseBody
    @PostMapping("/editSlsj")
    public void editSlsj(String yzwbh, Date slsj) {
        yzwDataService.editSlsj(yzwbh, slsj);

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化生成办结信息
     */
    @ResponseBody
    @GetMapping("/initBjxx")
    public Object initBjxx(String yzwbh) {
        return yzwDataService.initBjxx(yzwbh);

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 修改办结时间
     */
    @ResponseBody
    @PostMapping("/editBjsj")
    public void editBjsj(String yzwbh, Date bjsj) {
        yzwDataService.editBjsj(yzwbh, bjsj);

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存结果信息
     */
    @ResponseBody
    @PostMapping("/saveYzwJgxxDTO")
    public void saveYzwJgxxDTO(YzwJgxxDTO yzwJgxxDTO) {
        yzwDataService.saveYzwJgxxDTO(yzwJgxxDTO);

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化生成退件结果信息
     */
    @ResponseBody
    @GetMapping("/initTjjgxx")
    public void initTjjgxx(String yzwbh) {
        yzwDataService.initTjjgxx(yzwbh);

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化生成过程信息
     */
    @ResponseBody
    @GetMapping("/initGcxx")
    public void initGcxx(String yzwbh) {
        yzwDataService.initGcxx(yzwbh);

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 初始化生成过程信息
     */
    @ResponseBody
    @PostMapping("/checkAndSaveSqrZjxx")
    public void checkAndSaveSqrZjxx(String yzwbh, String zjzl, String zjh) {
        yzwDataService.checkAndSaveSqrZjxx(yzwbh, zjzl, zjh);

    }

    @ResponseBody
    @PostMapping("/share/yzw/tsYzw")
    public void tsYzw(@RequestParam(name = "processInsId") String processInsId) {
        if (StringUtils.isNotBlank(processInsId)) {
            zjkService.tszjkEntrance(processInsId);
        }
    }

    @ResponseBody
    @PostMapping("/share/yzw/tsYzwbyslsj")
    public String tsYzwByTime(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("kssj", startDate);
        paramMap.put("jssj", endDate);
        String msg = "推送完成！";
        List<String> gzlslidList = bdcXmMapper.getTsGzlslidList(paramMap);
        if (CollectionUtils.isNotEmpty(gzlslidList)) {
            LOGGER.info("需要补推的数量，{}",gzlslidList.size());
            for (int i = 0; i < gzlslidList.size(); i++) {
                LOGGER.info("正在补推第{}个",i);
                try {
                    zjkService.tszjkEntrance(gzlslidList.get(i));
                }catch (Exception e){
                    LOGGER.info("在补推第{}个,出现错误,错误gzlslid为{}",i,gzlslidList.get(i));
                    msg += "第" + i+"出错,"+ "错误gzlslid为"+gzlslidList.get(i)+";";
                }
            }
            LOGGER.info("补推送全部完成");
        }
        return msg;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取一张网验证失败推送信息
     */
    @ResponseBody
    @GetMapping("/getYzwYzsbTsxx")
    public Object getYzwYzsbTsxx(String yzwbh, String tsxxid) {
        return yzwDataService.getYzwYzsbTsxx(yzwbh, tsxxid);

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存一张网验证失败推送修改信息
     */
    @ResponseBody
    @PostMapping("/saveYzwYzsbTsxgxx")
    public void saveYzwYzsbTsxgxx(@RequestBody YzwYzsbCshxxVO yzwYzsbCshxxVO) {
        yzwDataService.checkAndSaveYzwYzsbTsxgxx(yzwYzsbCshxxVO);

    }

    /**
     * 查询一张网共享中间库数据
     *
     * @param pageable
     * @param yzwDTO
     * @return
     */
    @GetMapping("/listShareData")
    public Object listShareData(@LayuiPageable Pageable pageable,
                                   @RequestParam(value = "ywbh",required = false) String ywbh,
                                   @RequestParam(value = "sqrmc",required = false) String sqrmc,
                                   @RequestParam(value = "sqrzjh",required = false) String sqrzjh
    ) {
        YzwDTO yzwDTO = new YzwDTO();
        yzwDTO.setYwbh(ywbh);
        yzwDTO.setSqrmc(sqrmc);
        yzwDTO.setSqrzjh(sqrzjh);
        Page<Map> maps = zjkService.listShareDataByPage(pageable, yzwDTO);
        return addLayUiCode(maps);
    }

    /**
     * 导入一张网信息到当前流程
     *
     * @param pageable
     * @param yzwDTO
     * @return
     */
    @PostMapping("/tbyzwsj")
    public void tbyzwsj(@RequestParam(value = "caseno") String caseno,
                                @RequestParam(value = "processInsId") String processInsId
    ) {
        zjkService.tbyzwsj(caseno, processInsId);
    }
}
