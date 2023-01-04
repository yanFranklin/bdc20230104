package cn.gtmap.realestate.etl.web.main;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.etl.core.qo.TdsqQO;
import cn.gtmap.realestate.etl.core.service.TdDataService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2021/11/1,1.0
 * @description 土地系统数据读取Rest服务
 */
@RestController
@RequestMapping("/realestate-etl/td")
@Api(tags = "土地系统数据读取")
public class TdConvertController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TdConvertController.class);
    @Autowired
    TdDataService tdDataService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    /**
     * 分页查询土地信息
     * @param pageable 分页参数
     * @param tdsqQO 土地查询信息
     * @return 土地分页数据
     */
    @GetMapping("/page")
    public Object listTdxxByPage(@LayuiPageable Pageable pageable, TdsqQO tdsqQO) {
        Page<Map> tdxxList = this.tdDataService.listTdxxByPage(pageable, tdsqQO);
        return super.addLayUiCode(tdxxList);
    }

    /**
     * 导入土地数据至登记系统
     * @param tdsqQO
     */
    @LogNote(value = "导入土地信息", action = "OTHER",custom = LogConstans.LOG_TYPE_TDDR)
    @GetMapping("/import/data")
    public void importDataToDj(TdsqQO tdsqQO){
        if(StringUtils.isBlank(tdsqQO.getXmid())){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到项目ID");
        }
        logger.info("导入请求数据："+tdsqQO.toString());
        try{
            this.tdDataService.importData(tdsqQO);
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 判断是否已经导入过数据
     * @param bdcqzh
     */
    @GetMapping("/sfyjdr")
    public String getTdXmxx(String bdcqzh){
        if(StringUtils.isBlank(bdcqzh)){
            throw new AppException(ErrorCode.MISSING_ARG, "bdcqzh不能为空");
        }
        String result = "false";
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(bdcqzh);
        List<BdcZsDO> bdcZsDOList =  bdcZsFeignService.listBdcZs(bdcZsQO);
        if(CollectionUtils.isNotEmpty(bdcZsDOList)){
            result = "true";
        }
        return result;
    }

}
