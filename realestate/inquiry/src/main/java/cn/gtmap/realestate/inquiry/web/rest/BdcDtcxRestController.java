package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDtcxQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcDtcxRestService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.inquiry.common.DtcxConstants;
import cn.gtmap.realestate.inquiry.service.DtcxConfigService;
import cn.gtmap.realestate.inquiry.service.DtcxViewService;
import cn.gtmap.realestate.inquiry.util.BeanHelper;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2019/07/16
 * @description:
 */
@RestController
@Api(tags = "自定义查询")
public class BdcDtcxRestController extends BaseController implements BdcDtcxRestService{
    /**
     * 是否将中文括号转换为英文括号,默认转换
     */
    @Value("${dtcx.bracket:true}")
    private boolean isReplaceBracket;

    @Autowired
    DtcxViewService viewService;
    @Autowired
    DtcxConfigService dtcxConfigService;

    @Override
    public DtcxConfigCheckDTO checkCxtj(String cxsql,String cxtj){
        List<DtcxCxtjDO> cxtjList = JSONObject.parseArray(cxtj,DtcxCxtjDO.class);

        return dtcxConfigService.checkCxtj(cxsql,cxtjList);
    }

    @Override
    public DtcxConfigCheckDTO checkCxjg(String cxsql,String cxjg){
        List<DtcxCxjgDO> cxjgList = JSONObject.parseArray(cxjg,DtcxCxjgDO.class);

        return dtcxConfigService.checkCxjg(cxsql,cxjgList);

    }

    @Override
    public void saveAll(@RequestBody BdcDtcxDTO dtcxDO,boolean fuzhiCx){
        String cxid = dtcxDO.getCxid();
        // 如果为复制的话重新生成cxid
        if (fuzhiCx || StringUtils.isBlank(cxid)){
            cxid = UUIDGenerator.generate();
            dtcxDO.setCxid(cxid);
        }
        if (StringUtils.isNotBlank(dtcxDO.getCxsql())){
            String sql = dtcxDO.getCxsql();
            while (sql.indexOf("    ") >= 0){
                sql = sql.replace("    "," ");
            }
            dtcxDO.setCxsql(sql);
        }
        dtcxConfigService.saveAllDtcxCxxx(dtcxDO,fuzhiCx);
    }

    @Override
    public void saveConfig(@RequestBody BdcDtcxDTO bdcDtcxDTO){
        dtcxConfigService.saveAllDtcxCxxx(bdcDtcxDTO,false);
    }

    @Override
    public Page<BdcDtcxDTO> listDtcxPage(@RequestBody BdcDtcxQO dtcxQO,int page,int size,Sort sort){
        Pageable pageable = new PageRequest(page,size,sort);
        return dtcxConfigService.listDtcxPage(dtcxQO,pageable);
    }

    /**
     * 自定义查询修改dataString通过@RequestBody获取参数，避免get请求头过大问题
     * @description 自定义查询查询
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/6/28 9:46
     * @param dataString
     * @param page
     * @param size
     * @param sort
     * @return Page
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "自定义查询查询操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "dataString", value = "查询参数", required = true, paramType = "body")})
    public Page listResult(@RequestBody String dataString,int page,int size,Sort sort){
        Pageable pageable = new PageRequest(page,size,sort);

        // 将json 数据转换为map
        Map dataMap = new HashMap();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(dataString)){
            JSONObject jsonData = JSONObject.parseObject(dataString);
            for (Object jsonObject : jsonData.entrySet()){
                if (((Map.Entry) jsonObject).getValue() != null){
                    String value = ((Map.Entry) jsonObject).getValue().toString();
                    if(Objects.isNull(isReplaceBracket) || isReplaceBracket){
                        value = StringToolUtils.replaceBracket(value);
                    }
                    dataMap.put(((Map.Entry) jsonObject).getKey(),value);
                }else{
                    dataMap.put(((Map.Entry) jsonObject).getKey(),((Map.Entry) jsonObject).getValue());
                }
            }
        }
        try{
            BeanHelper.beanValueTrim(dataMap);
        }catch (Exception e){
            LOGGER.error("查询参数处理出错，错误信息：{}",e.getMessage());
        }
        // 在map里查看是否有批量查询flag，如果有走正常方法拼接sql
        String sql;
        if (dataMap.containsKey("plcxflag")){
            sql = viewService.getSqlByExcelCxxx(dataMap);

        }else{
            sql = viewService.getSqlByCxxx(dataMap);
        }
        sql = sql.replaceAll("\n"," ").trim();
        LOGGER.info("结果处理后的sql：{};进行查询的参数:{}",sql,dataMap);
        if (StringUtils.isBlank(sql)){
            throw new AppException("组织sql发生错误！");
        }

        /**
         * 自定义查询支持外部传参，即查询SQL中有些参数可以从查询URL路径传递，以支持外部应用集成
         * 传参形式两种：
         * 1、URL中拼接参数，例如 ***commonCx.html?param=435B00344Z1J38L5,469F4023BV2B70AU，在自定义查询SQL中利用 #{param} 接收参数
         * 2、当传参数据过多超出URL限制或者数据在SQL中像 in (***) 时候超出限制，可以采用临时中转表的形式，将参数保存到 BDC_XT_LSCS，URL中传临时参数名称，
         *    例如：***commonCx.html?param=ZDYCX_LSCS_111， 执行自定义查询时候会在这里将SQL中 #{param} 替换为查询临时参数表数据
         */
        if (dataMap.containsKey("param")){
            String paramValue = (String) dataMap.get("param");
            if (StringUtils.isNotBlank(paramValue)){
                if (paramValue.startsWith(CommonConstantUtils.ZDYCX_LSCS)){
                    // 当传参数据过多使用临时参数表中转
                    sql = sql.replace("#{param}","SELECT csz FROM bdc_xt_lscs WHERE csmc = '" + paramValue + "'");
                }else{
                    String[] paramArray = paramValue.split(",");
                    sql = sql.replace("#{param}",StringToolUtils.appendSqlInStrWithNoBracket(Arrays.asList(paramArray)));
                }
            }
        }

        /**
         * 自定义查询支持传自定义的sql语句，即查询SQL中有些参数可以前台进行处理成可执行的SQL，后台将占位符替换为可执行的sql
         */
        if(dataMap.containsKey("executeSql")){
            String paramValue = (String) dataMap.get("executeSql");
            if (StringUtils.isNotBlank(paramValue)){
                sql = sql.replace("#{executeSql}", new String(Base64Utils.decodeBase64StrToByte(paramValue)));
            }
        }

        /**
         * 处理要加密处理的字段数据
         */
        viewService.getEncryptCxxx(dataMap);

        LOGGER.info("结果处理后的sql：{};进行查询的参数:{}",sql,dataMap);
        dataMap.put("sql",sql);
        Page result = viewService.listResultByPage(pageable, sql, dataMap);
        List<Map> content = result.getContent();

        /**
         * 处理要解密处理的字段数据
         */
        viewService.getDecryptCxxx(MapUtils.getString(dataMap, DtcxConstants.CXID),content);
        return result;
    }

    @Override
    public BdcDtcxDTO getCxxxByCxdh(@PathVariable("cxdh") String cxdh){

        return dtcxConfigService.getConfigsByCxdh(cxdh);
    }

    @Override
    public void delCxConfig(@PathVariable("cxid") String cxid){

        dtcxConfigService.deleteDtcxCx(cxid);
    }

    @Override
    public List<Map> listResultData(@RequestBody String dataString){
        // 将json 数据转换为map
        Map dataMap = new HashMap();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(dataString)){
            JSONObject jsonData = JSONObject.parseObject(dataString);
            for (Object jsonObject : jsonData.entrySet()){
                dataMap.put(((Map.Entry) jsonObject).getKey(),((Map.Entry) jsonObject).getValue());
            }
        }

        try{
            BeanHelper.beanValueTrim(dataMap);
        }catch (Exception e){
            LOGGER.error("查询参数处理出错，错误信息：{}",e.getMessage());
        }
        String sql;
        if (dataMap.containsKey("plcxflag")){
            sql = viewService.getSqlByExcelCxxx(dataMap);

        }else{
            sql = viewService.getSqlByCxxx(dataMap);

        }
        sql = sql.replaceAll("\n"," ").trim();
        if (StringUtils.isBlank(sql)){
            throw new AppException("组织sql发生错误！");
        }
        dataMap.put("sql",sql);
        List<Map> maps = viewService.listResultData(dataMap);

        /**
         * 处理要解密处理的字段数据
         */
        viewService.getDecryptCxxx(MapUtils.getString(dataMap, DtcxConstants.CXID),maps);
        return maps;
    }

    @Override
    public List<DtcxCxjgDO> getCxjgList(@PathVariable("cxid") String cxid){

        return dtcxConfigService.getCxjgList(cxid);
    }


}