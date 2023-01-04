package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcDrExcelCxRestService;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.inquiry.service.DtcxViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/7/2 16:05
 * @description 导入excele查询
 */
@RestController
@Api(tags = "自定义查询")
public class BdcDrExcelCxController implements BdcDrExcelCxRestService{
    @Autowired
    DtcxViewService viewService;
    @Autowired
    private Repo repo;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("读取excel")
    @ApiImplicitParams({@ApiImplicitParam(name = "stringListMap", value = "数据", required = true, paramType = "query"),
            @ApiImplicitParam(name = "cxid", value = "sqlId", required = true, paramType = "query")})
    public Map excelCx(@RequestBody Map<String,List<String>> stringListMap){
        // 查询类型 例如 精确 模糊
        List<String> cxlxList = stringListMap.get("cxlx");
        // 输入值 如有多个用&&进行连接
        List<String> srzList = stringListMap.get("srz");
        // 查询类型的数据库对应字段 里面的值例如  坐落:zl
        Map<String,String> zwmcMap = new HashMap<>();
        List<String> cumNameList = stringListMap.get("cumName");
        for(int i = 0; i < cumNameList.size(); i++){
            if(StringUtils.isNotEmpty(cumNameList.get(i))){
                String[] temp = cumNameList.get(i).split(":");
                zwmcMap.put(temp[0],temp[1]);
            }
        }
        // 查询字段的中文名称   理论上  cxlxList  srzList zwmcList 三个list的长度是一致的
        List<String> zwmcList = stringListMap.get("zwmc");
        // 对值进行额外处理
        Map dataMap = new HashMap();
        for(int i = 0; i < srzList.size(); i++){
            String str = srzList.get(i);
            String mapKey = zwmcMap.get(zwmcList.get(i));
            switch(cxlxList.get(i)){
                case "1":
                    dataMap.put(mapKey,str);
                    dataMap.put(mapKey + "mh","1");
                    break;
                case "2":
                    dataMap.put(mapKey,srzList.get(i));
                    dataMap.put(mapKey + "mh","2");
                    break;
                case "3":
                    dataMap.put(mapKey,srzList.get(i));
                    dataMap.put(mapKey + "mh","3");
                    break;
                case "4":
                    dataMap.put(mapKey,srzList.get(i));
                    dataMap.put(mapKey + "mh","4");
                    break;
                default:
                    break;
            }
        }
        // 加入批量查询得flag，在后续页面上使用
        dataMap.put("plcxflag","true");
        return dataMap;
    }


}
