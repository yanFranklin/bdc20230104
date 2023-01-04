package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.convert.BdcZsYzhConverter;
import cn.gtmap.realestate.etl.core.domian.BdcDzjZtDO;
import cn.gtmap.realestate.etl.core.domian.BdcZsYzhLsDO;
import cn.gtmap.realestate.etl.core.dto.BdcZsYzhLsDTO;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "自助打证机")
@RequestMapping("/realestate-etl/rest/v1.0/zzdzj")
public class BdcZzdzjRestController {

    private static final Logger logger = LoggerFactory.getLogger(BdcZzdzjRestController.class);

    @Autowired
    @Qualifier("bdcEntityMapper")
    private EntityMapper bdcEntityMapper;

    @Autowired
    private BdcZsYzhConverter bdcZsYzhConverter;


    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 插入BDC_ZS_YZH_LS表接口
     */
    @ResponseBody
    @PostMapping("/save/yzh/info")
    public CommonResponse saveZsYzhInfo(@RequestBody BdcZsYzhLsDTO bdcZsYzhLsDTO) {
        logger.error("插入BDC_ZS_YZH_LS表开始:{}", JSON.toJSONString(bdcZsYzhLsDTO));
        BdcZsYzhLsDO bdcZsYzhLsDO = bdcZsYzhConverter.getBdcZsYzhLsDOByBdcZsYzhLsDTO(bdcZsYzhLsDTO);
        if (StringUtils.isBlank(bdcZsYzhLsDO.getId())){
            bdcZsYzhLsDO.setId(UUIDGenerator.generate());
        }
        try {
            int insert = bdcEntityMapper.insert(bdcZsYzhLsDO);
            if (insert > 0){
                return CommonResponse.ok();
            }
        }catch (Exception e){
            logger.error("插入BDC_ZS_YZH_LS表失败:",e);
            return CommonResponse.fail("插入BDC_ZS_YZH_LS表失败:"+e.getMessage());
        }
        return CommonResponse.fail("插入BDC_ZS_YZH_LS表失败");
    }

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 插入BDC_DZJ_ZT表接口
     */
    @ResponseBody
    @PostMapping("/save/dzj/status")
    public CommonResponse saveDzjStatus(@RequestBody BdcDzjZtDO bdcDzjZtDO) {
        logger.error("插入BDC_DZJ_ZT表开始:{}", JSON.toJSONString(bdcDzjZtDO));
        if (StringUtils.isNotBlank(bdcDzjZtDO.getDzjbh())){
            if (StringUtils.isNotBlank(bdcDzjZtDO.getYwlsh())){
                Example example = new Example(BdcDzjZtDO.class);
                example.createCriteria().andEqualTo("ywlsh",bdcDzjZtDO.getYwlsh());
                List<BdcDzjZtDO> bdcDzjZtDOS = bdcEntityMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(bdcDzjZtDOS)){
                    bdcEntityMapper.deleteByExample(example);
                }
            }
            try {
                int insert = bdcEntityMapper.insert(bdcDzjZtDO);
                if (insert > 0){
                    return CommonResponse.ok();
                }
            }catch (Exception e){
                logger.error("插入BDC_DZJ_ZT表失败:",e);
                return CommonResponse.fail("插入BDC_DZJ_ZT表失败:"+e.getMessage());
            }
        }
        return CommonResponse.fail("插入BDC_DZJ_ZT表失败:入参无dzjbh");
    }

}
