package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcHzdytjDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcYbbdytjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcHzdytjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcHzdytjRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.service.BdcHzdytjService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 汇总抵押统计
 */
@RestController
public class BdcHzdytjRestController implements BdcHzdytjRestService {

    @Autowired
    private BdcHzdytjService bdcHzdytjService;

    /**
     * @param bdcHzdytjQOJson
     * @return list
     * @author <a href="mailto:cucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "汇总抵押统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHzdytjQO", value = "查询信息参数JSON", required = false, paramType =
            "body")})
    public List listBdcHzdytj(@RequestParam(name="bdcHzdytjQOJson") String bdcHzdytjQOJson) {
        List list = bdcHzdytjService.listBdcHzdytj(bdcHzdytjQOJson);
        List listCount = bdcHzdytjService.listBdcCount(bdcHzdytjQOJson);
        List listYjgCount = bdcHzdytjService.listYjgCount(bdcHzdytjQOJson);
        BdcHzdytjDTO bdcHzdytjDTO = new BdcHzdytjDTO();
        bdcHzdytjDTO.setId(UUIDGenerator.generate());
        BdcHzdytjQO bdcHzdytjQO = JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class);
        if(StringUtils.isNotBlank(bdcHzdytjQO.getBmmc())){
            bdcHzdytjDTO.setQx(bdcHzdytjQO.getBmmc());
        }else{
            bdcHzdytjDTO.setQx("南通市");
        }
        for (int i=0;i<list.size();i++){
            Map map = (Map)list.get(i);
            // 在建工程 4
            if(CommonConstantUtils.DYBDCLX_ZJJZW == Integer.parseInt((map.get("DYBDCLX").toString()))){
                bdcHzdytjDTO.setZjgcavg(Float.parseFloat(String.valueOf(map.get("AVGJEMJ"))));
                bdcHzdytjDTO.setZjgcdyje(Float.parseFloat(String.valueOf(map.get("DYJE"))));
                bdcHzdytjDTO.setZjgcmj(Float.parseFloat(String.valueOf(map.get("MJ"))));
                for(int j=0;j<listCount.size();j++){
                    Map mapTemp = (Map)listCount.get(j);
                    if(CommonConstantUtils.DYBDCLX_ZJJZW == Integer.parseInt((mapTemp.get("DYBDCLX").toString()))){
                        bdcHzdytjDTO.setZjgccount(Integer.parseInt(String.valueOf(mapTemp.get("COUNT"))));
                        break;
                    }
                }
            }
            // 纯土地 1
            if(CommonConstantUtils.DYBDCLX_CTD == Integer.parseInt((map.get("DYBDCLX").toString()))){
                bdcHzdytjDTO.setCtdavgjemj((Float.parseFloat(String.valueOf(map.get("AVGJEMJ"))))/0.0015f);
                bdcHzdytjDTO.setCtddyje(Float.parseFloat(String.valueOf(map.get("DYJE"))));
                bdcHzdytjDTO.setCtdmj(Float.parseFloat(String.valueOf(map.get("MJ")))*0.0015f);
                for(int j=0;j<listCount.size();j++){
                    Map mapTemp = (Map)listCount.get(j);
                    if(CommonConstantUtils.DYBDCLX_CTD == Integer.parseInt((mapTemp.get("DYBDCLX").toString()))){
                        bdcHzdytjDTO.setCtdcount(Integer.parseInt(String.valueOf(mapTemp.get("COUNT"))));
                        break;
                    }
                }
            }

            // 房地一体 2
            if(CommonConstantUtils.DYBDCLX_FDYT == Integer.parseInt((map.get("DYBDCLX").toString()))){
                if(CollectionUtils.isNotEmpty(listYjgCount) && listYjgCount.size() == 2 ){
                    float je = Float.parseFloat(((Map)listYjgCount.get(0)).get("COUNT").toString());
                    float mj = Float.parseFloat(((Map)listYjgCount.get(1)).get("COUNT").toString());
                    bdcHzdytjDTO.setFdytavg(je/mj);
                    bdcHzdytjDTO.setFdytdyje(je);
                    bdcHzdytjDTO.setFdytmj(mj);
                }else{
                    bdcHzdytjDTO.setFdytavg(Float.parseFloat(String.valueOf(map.get("AVGJEMJ"))));
                    bdcHzdytjDTO.setFdytdyje(Float.parseFloat(String.valueOf(map.get("DYJE"))));
                    bdcHzdytjDTO.setFdytmj(Float.parseFloat(String.valueOf(map.get("MJ"))));
                }

                for(int j=0;j<listCount.size();j++){
                    Map mapTemp = (Map)listCount.get(j);
                    if(CommonConstantUtils.DYBDCLX_FDYT == Integer.parseInt((mapTemp.get("DYBDCLX").toString()))){
                        bdcHzdytjDTO.setFdytcount(Integer.parseInt(String.valueOf(mapTemp.get("COUNT"))));
                        break;
                    }
                }
            }
        }
        list.clear();
        list.add(bdcHzdytjDTO);
        return list;
    }


    /**
     * @param bdcHzdytjQOJson
     * @return list
     * @author <a href="mailto:cucheng@gtmap.cn">chenyucheng</a>
     * @description 汇总抵押统计
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "汇总抵押统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHzdytjQO", value = "查询信息参数JSON", required = false, paramType =
            "body")})
    public List listBdcHzdytjBb(@RequestParam(name="bdcHzdytjQOJson") String bdcHzdytjQOJson) {
        List list = bdcHzdytjService.listBdcHzdytjBb(bdcHzdytjQOJson);
        List listCount = bdcHzdytjService.listBdcCountBb(bdcHzdytjQOJson);

        // 抵押的数量和面积金额等信息组合到一起
        for(Object mapTempObj : listCount){
            Map mapTemp = (Map)mapTempObj;
            // 抵押总数和在押总数
            for(Object mapTempMainObj : list){
                Map mapTempMain = (Map)mapTempMainObj;
                // 抵押金额和抵押面积和平均值
                if(mapTemp.containsKey("DYBDCLX") && mapTempMain.containsKey("DYBDCLX")
                        && null != mapTemp.get("DYBDCLX") && null != mapTempMain.get("DYBDCLX")
                        && mapTemp.containsKey("QSZT") && null != mapTemp.get("QSZT")){
                    if(StringUtils.equals(mapTemp.get("DYBDCLX").toString(),mapTempMain.get("DYBDCLX").toString())){
                        if(null != mapTemp.get("COUNT")){
                            // 在押
                            if((CommonConstantUtils.QSZT_VALID + "").equals(mapTemp.get("QSZT").toString())){
                                mapTempMain.put("COUNT_DY",mapTemp.get("COUNT") == null?0:mapTemp.get("COUNT"));
                            }
                            // 总数
                            int countTemp = Integer.parseInt(mapTemp.get("COUNT").toString());
                            int countCurrent = mapTempMain.containsKey("COUNT")?Integer.parseInt(mapTempMain.get("COUNT").toString()):0;
                            mapTempMain.put("COUNT",countCurrent+countTemp);
                        }
                    }
                }
            }
        }

        return bqSj(list);
    }


    /**
     * @param bdcHzdytjQOJson
     * @return list
     * @author <a href="mailto:cucheng@gtmap.cn">chenyucheng</a>
     * @description 月报表抵押统计
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "月报表抵押统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcHzdytjQO", value = "查询信息参数JSON", required = false, paramType =
            "body")})
    public List listBdcYbbdytj(@RequestParam(name="bdcHzdytjQOJson") String bdcHzdytjQOJson) {
        List list = bdcHzdytjService.listBdcYbbdytj(bdcHzdytjQOJson);
        BdcYbbdytjDTO bdcYbbdytjDTO = new BdcYbbdytjDTO();
        bdcYbbdytjDTO.setId(UUIDGenerator.generate());
        BdcHzdytjQO bdcHzdytjQO = JSON.parseObject(bdcHzdytjQOJson, BdcHzdytjQO.class);
        if(StringUtils.isNotBlank(bdcHzdytjQO.getBmmc())){
            bdcYbbdytjDTO.setQx(bdcHzdytjQO.getBmmc());
        }else{
            bdcYbbdytjDTO.setQx("南通市");
        }
        int gylCount = 0;
        int zzlCount = 0;
        float gyljecount = 0f;
        float zzljecount = 0f;
        // 遍历结果 统计count值
        for (int i=0;i<list.size();i++) {
            Map map = (Map) list.get(i);
            if (CommonConstantUtils.TDYT_GYL.equals(map.get("YT"))) {
                // 工业类count值统计
                gylCount += Integer.parseInt(String.valueOf(map.get("COUNT")));
                // 工业类的金额统计
                gyljecount += Float.parseFloat(String.valueOf(map.get("DYJE")));

                if (CommonConstantUtils.DYBDCLX_CTD == Integer.parseInt(map.get("DYBDCLX").toString())) {
                    bdcYbbdytjDTO.setGylmj(Float.parseFloat(String.valueOf(map.get("MJ"))));
                }
                if (CommonConstantUtils.DYBDCLX_FDYT == Integer.parseInt(map.get("DYBDCLX").toString())) {
                    bdcYbbdytjDTO.setGyljg(Float.parseFloat(String.valueOf(map.get("MJ"))));
                }
                if (CommonConstantUtils.DYBDCLX_ZJJZW == Integer.parseInt(map.get("DYBDCLX").toString())) {
                    bdcYbbdytjDTO.setGylzj(Float.parseFloat(String.valueOf(map.get("MJ"))));
                }
            }
            if (CommonConstantUtils.TDYT_ZZL.equals(map.get("YT"))) {
                // 工业类count值统计
                zzlCount += Integer.parseInt(String.valueOf(map.get("COUNT")));
                // 工业类的金额统计
                zzljecount += Float.parseFloat(String.valueOf(map.get("DYJE")));
                if (CommonConstantUtils.DYBDCLX_CTD == Integer.parseInt(map.get("DYBDCLX").toString())) {
                    bdcYbbdytjDTO.setZzlmj(Float.parseFloat(String.valueOf(map.get("MJ"))));
                }
                if (CommonConstantUtils.DYBDCLX_FDYT == Integer.parseInt(map.get("DYBDCLX").toString())) {
                    bdcYbbdytjDTO.setZzljg(Float.parseFloat(String.valueOf(map.get("MJ"))));
                }
                if (CommonConstantUtils.DYBDCLX_ZJJZW == Integer.parseInt(map.get("DYBDCLX").toString())) {
                    bdcYbbdytjDTO.setZzlzj(Float.parseFloat(String.valueOf(map.get("MJ"))));
                }
            }
        }
        bdcYbbdytjDTO.setGylcount(gylCount);
        bdcYbbdytjDTO.setGyldyje(gyljecount);
        bdcYbbdytjDTO.setZzlcount(zzlCount);
        bdcYbbdytjDTO.setZzldyje(zzljecount);
        list.clear();
        list.add(bdcYbbdytjDTO);
        return list;
    }

    /**
     * 页面补全数据 统计不到的bdclx补一个空的集合，用于页面展示
     * @param list
     * @return
     */
    public List bqSj(List list){
        List result = new ArrayList();
        result.addAll(list);
        List<String> listBdclx = Arrays.asList(CommonConstantUtils.DYTJ_BDCLX);
        String bdclx = "";
        for(Object mapObj : list){
            Map map = (Map)mapObj;
            bdclx += map.get("DYBDCLX").toString() + ",";

        }
        for(String tjBdclx : listBdclx){
            if(bdclx.indexOf(tjBdclx) == -1){
                Map map = new HashMap();
                map.put("DYBDCLX",tjBdclx);
                result.add(map);
            }
        }

        return result;
    }
}
