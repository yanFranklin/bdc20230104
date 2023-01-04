package cn.gtmap.realestate.building.core.service.impl;

import cn.gtmap.realestate.building.core.dbs.SwitchDB;
import cn.gtmap.realestate.building.core.domain.SSjMaxBdcdyhDO;
import cn.gtmap.realestate.building.core.mapper.BdcdyMapper;
import cn.gtmap.realestate.building.core.service.BdcdyhService;
import cn.gtmap.realestate.building.core.service.FwLjzService;
import cn.gtmap.realestate.building.core.service.FwXmxxService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.building.util.Constants;
import cn.gtmap.realestate.building.util.ErrorCodeConstants;
import cn.gtmap.realestate.common.core.domain.building.FwLjzDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhScmjResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description
 */
@Service
public class BdcdyhServiceImpl implements BdcdyhService {
    @Autowired
    private FwLjzService fwLjzService;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcdyMapper bdcdyMapper;
    @Autowired
    protected MessageProvider messageProvider;
    @Autowired
    private FwXmxxService fwXmxxService;

    @Autowired
    private SwitchDB switchDB;

    @Autowired
    private Environment evn;

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过规则创建不动产单元号
     */
    @Override
    public String creatFwBdcdyhByZdzhdmAndZrzh(String zdzhdm, String zrzh) {
        String bdcdyh = "";
        if (StringUtils.isNotBlank(zdzhdm) && StringUtils.isNotBlank(zrzh) && zdzhdm.length() == Constants.DJH_LENGTH) {
            int lsh = getMaxBdcdyhLsh(zdzhdm, zrzh);
//            String lsh = getMaxLsh(zdzhdm, zrzh, 0);
            if (Objects.nonNull(lsh)) {
                //宗地宗海特征码+“F”+自然幢号+最大流水号
                bdcdyh = zdzhdm + Constants.BDCDY_DZWTZM_F + BuildingUtils.formatNumberByLengh(Integer.valueOf(zrzh), "0000") + BuildingUtils.formatNumberByLengh(lsh, "0000");
            }
        }
        return bdcdyh;
    }

    /**
     * @param
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过逻辑幢主键创建房屋不动产单元号
     */
    @Override
    public String creatFwBdcdyhByFwDcbIndex(String fwDcbIndex) {
        String bdcdyh = "";
        if (StringUtils.isNotBlank(fwDcbIndex)) {
            FwLjzDO fwLjzDO = fwLjzService.queryLjzByFwDcbIndex(fwDcbIndex);
            if (fwLjzDO != null && StringUtils.equals(fwLjzDO.getBdcdyfwlx(),Constants.BDCDYFWLX_H) && StringUtils.isNotBlank(fwLjzDO.getLszd()) && StringUtils.isNotBlank(fwLjzDO.getZrzh())) {
                bdcdyh = creatFwBdcdyhByZdzhdmAndZrzh(fwLjzDO.getLszd(), fwLjzDO.getZrzh());
            }
        }
        return bdcdyh;
    }

    /**
     * @param fwXmxxIndex
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键生成不动产单元号
     */
    @Override
    public String creatXmxxBdcdyhByFwXmxxIndex(String fwXmxxIndex) {
        String bdcdyh = "";
        if (StringUtils.isNotBlank(fwXmxxIndex)) {
            FwXmxxDO fwXmxxDO = fwXmxxService.queryXmxxByPk(fwXmxxIndex);
            if (fwXmxxDO != null && StringUtils.isNotBlank(fwXmxxDO.getLszd())) {
                bdcdyh = creatFwBdcdyhByZdzhdmAndZrzh(fwXmxxDO.getLszd(), Constants.BDCDYH_XMXX_ZRZH);
            }
        }
        return bdcdyh;
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 验证选择的不动产单元是否存在且有实测面积
     */
    @Override
    public String checkBdcdyhSfczscmj(String bdcdyh) {
        if(StringUtils.isBlank(bdcdyh)){
            throw new MissingArgumentException("请求参数bdcdyh不能为空");
        }
        BdcdyhScmjResponseDTO bdcdyhScmjResponseDTO = bdcdyMapper.queryScmjxx(bdcdyh);
        // 比较查出来的不动产单元号和实测面积数量
        if(bdcdyhScmjResponseDTO != null && bdcdyhScmjResponseDTO.getBdcdyhzs() != null &&  bdcdyhScmjResponseDTO.getScjzmjzs() != null ){
            // 不动产单元号数量大于0并且和实测面积数量相等返回有
            if( bdcdyhScmjResponseDTO.getBdcdyhzs().intValue() > 0 && bdcdyhScmjResponseDTO.getBdcdyhzs().intValue() == bdcdyhScmjResponseDTO.getScjzmjzs().intValue() ){
                return "true";
            }
        }
        return "false";
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 验证所在宗地不动产单元锁定
     */
    @Override
    public String checkBdcdyhSfsd(String bdcdyh) {
        if(StringUtils.isBlank(bdcdyh)){
            throw new MissingArgumentException("请求参数bdcdyh不能为空");
        }
        if(bdcdyh.length() != 28){
            throw new AppException("请输入正确的bdcdyh");
        }
        String zdzhdm = bdcdyh.substring(0,19);
        Integer count = bdcdyMapper.queryBdcdyhSd(zdzhdm);
        if(count.intValue()>0){
            return "true";
        }
        return "false";
    }

    /**
     * @param lszd
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据隶属宗地主键生成项目信息不动产单元号
     */
    @Override
    public String creatXmxxBdcdyhByLszd(String lszd) {
        String bdcdyh = "";
        if (StringUtils.isNotBlank(lszd)) {
            bdcdyh = creatFwBdcdyhByZdzhdmAndZrzh(lszd, Constants.BDCDYH_XMXX_ZRZH);
        }
        return bdcdyh;
    }

    /**
     * @param lszd
     * @param zrzh
     * @param num
     * @return java.util.List<java.lang.String>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 批量获取BDCDYH
     */
    @Override
    public List<String> batchCreateFwBdcdyhByLszdAndZrzh(String lszd, String zrzh, int num) {
        List<String> lshList = getBatchMaxLsh(lszd,zrzh,num,0);
        List<String> bdcdyhList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(lshList)){
            for(String lsh : lshList){
                bdcdyhList.add(lszd + Constants.BDCDY_DZWTZM_F +
                        BuildingUtils.formatNumberByLengh(Integer.valueOf(zrzh), "0000") + lsh);
            }
        }
        return bdcdyhList;
    }



    /**
     * @param zdzhdm
     * @param zrzh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取房屋最大编号
     */
    private List<String> getBatchMaxLsh(String zdzhdm, String zrzh, int num,int count) {
        SSjMaxBdcdyhDO sSjMaxBdcdyhDO = queryDo(zdzhdm, zrzh);
        if (sSjMaxBdcdyhDO.getMaxlsh() > Constants.BDCDYH_MAX_LSH) {
            throw new AppException(ErrorCodeConstants.BDCDYH_CREAT_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.BDCDYH_CREAT_ERROR));
        }
        Map map = JSONObject.parseObject(JSONObject.toJSONString(sSjMaxBdcdyhDO),Map.class);
        map.put("num",num);
        int request = bdcdyMapper.batchUpdateMaxBdcdyh(map);
        if (request == 0) {
            if (count > 10) {
                throw new AppException(ErrorCodeConstants.BDCDYH_CREAT_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.BDCDYH_CREAT_ERROR));
            } else {
                count++;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new AppException(ErrorCodeConstants.BDCDYH_CREAT_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.BDCDYH_CREAT_ERROR));
            }
            return getBatchMaxLsh(zdzhdm, zrzh, num,count);
        } else {
            int end = sSjMaxBdcdyhDO.getMaxlsh() + num;
            return formatLsh(sSjMaxBdcdyhDO.getMaxlsh(),end);
        }
    }

    /**
     * @param zdzhdm
     * @param zrzh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取房屋最大编号
     */
    private String getMaxLsh(String zdzhdm, String zrzh, int count) {
        SSjMaxBdcdyhDO sSjMaxBdcdyhDO = queryDo(zdzhdm, zrzh);
        if (sSjMaxBdcdyhDO.getMaxlsh() > Constants.BDCDYH_MAX_LSH) {
            throw new AppException(ErrorCodeConstants.BDCDYH_CREAT_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.BDCDYH_CREAT_ERROR));
        }
        int request = bdcdyMapper.updateMaxBdcdyh(sSjMaxBdcdyhDO);
        if (request == 0) {
            if (count > 10) {
                throw new AppException(ErrorCodeConstants.BDCDYH_CREAT_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.BDCDYH_CREAT_ERROR));
            } else {
                count++;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new AppException(ErrorCodeConstants.BDCDYH_CREAT_ERROR, messageProvider.getMessage(ErrorCodeConstants.ERROR + ErrorCodeConstants.BDCDYH_CREAT_ERROR));
            }
            return getMaxLsh(zdzhdm, zrzh, count);
        } else {
            return formatLsh(sSjMaxBdcdyhDO.getMaxlsh());
        }
    }

    /**
     * @param zdzhdm
     * @param zrzh
     * @return cn.gtmap.realestate.building.core.domain.SSjMaxBdcdyhDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取规则
     */
    private SSjMaxBdcdyhDO queryDo(String zdzhdm, String zrzh) {
        SSjMaxBdcdyhDO sSjMaxBdcdyhDO = new SSjMaxBdcdyhDO();
        if (StringUtils.isNotBlank(zdzhdm) && StringUtils.isNotBlank(zrzh)) {
            Example example = new Example(SSjMaxBdcdyhDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("zdzhdm", zdzhdm).andEqualTo("zrzh", zrzh);
            List<SSjMaxBdcdyhDO> sSjMaxBdcdyhDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(sSjMaxBdcdyhDOList)) {
                sSjMaxBdcdyhDO = sSjMaxBdcdyhDOList.get(0);
            } else {
                sSjMaxBdcdyhDO = initMaxBdcdyh(zdzhdm, zrzh);
            }
        }
        return sSjMaxBdcdyhDO;
    }

    /**
     * @param zdzhdm
     * @param zrzh
     * @return cn.gtmap.realestate.building.core.domain.SSjMaxBdcdyhDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 初始化规则
     */
    private SSjMaxBdcdyhDO initMaxBdcdyh(String zdzhdm, String zrzh) {
        SSjMaxBdcdyhDO sSjMaxBdcdyhDO = new SSjMaxBdcdyhDO();
        sSjMaxBdcdyhDO.setMaxbdcdyhIndex(UUIDGenerator.generate());
        sSjMaxBdcdyhDO.setZdzhdm(zdzhdm);
        sSjMaxBdcdyhDO.setZrzh(zrzh);
        //在数据库中找最大编号，如果没有的话  返回1
        int maxLsh = getMaxBdcdyhLsh(zdzhdm, zrzh);
        sSjMaxBdcdyhDO.setMaxlsh(maxLsh);
        sSjMaxBdcdyhDO.setVersion(maxLsh);
        entityMapper.insertSelective(sSjMaxBdcdyhDO);
        return sSjMaxBdcdyhDO;
    }

    /**
     * @param zdzhdm
     * @param zrzh
     * @return int
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 在数据库中找最大编号，如果没有的话  返回1
     */
    private int getMaxBdcdyhLsh(String zdzhdm, String zrzh) {
        String rule = zdzhdm + Constants.BDCDY_DZWTZM_F + BuildingUtils.formatNumberByLengh(Integer.valueOf(zrzh), "0000");
        String maxBdcdyh = bdcdyMapper.queryMaxBdcdyhByRule(rule);
        int maxLsh = 1;
        if (StringUtils.isNotBlank(maxBdcdyh)) {
            String maxLshString = maxBdcdyh.substring(maxBdcdyh.length() - 4);
            if (BuildingUtils.isInteger(maxLshString.substring(0, 1))) {
                maxLsh = Integer.valueOf(maxLshString) + 1;
            } else {
                //如果第一位是字母 特殊处理
                int lsh = Integer.valueOf(maxLshString.substring(1));
                int aiis = Integer.valueOf(BuildingUtils.stringToAscii(maxLshString.substring(0, 1))) - 97;
                maxLsh = (aiis * Constants.BDCDY_LSH_NEW_RULR) + Constants.BDCDY_LSH_W + lsh;
            }
        }
        return maxLsh;
    }

    /**
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 格式化不动产单元最后四位流水号
     */
    private static List<String> formatLsh(final Integer begin, final Integer end) {
        List<String> lshList = new ArrayList<>();
        for (int i = begin+1 ; i <= end ; i++){
            if (begin >= 10000) {
                lshList.add(disposeLsh(i)) ;
            } else {
                lshList.add(BuildingUtils.formatNumberByLengh(i, "0000"));
            }
        }
        return lshList;
    }


    /**
     * @param lsh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 格式化不动产单元最后四位流水号
     */
    private static String formatLsh(final Integer lsh) {
        if (lsh >= 10000) {
            return disposeLsh(lsh);
        } else {
            return BuildingUtils.formatNumberByLengh(lsh, "0000");
        }
    }

    /**
     * @param lsh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 处理不动产单元号9999以后变为a001 ...a999,b001...b999....z999
     */
    private static String disposeLsh(Integer lsh) {
        String finallyLsh = "";
        if (lsh != null) {
            String lshs = String.valueOf(lsh);
            int ascii = Integer.parseInt(lshs.substring(0, 2)) + 87;
            int maxLsh = Integer.parseInt(lshs.substring(2));
            if (maxLsh + (ascii - 95) > 1000) {
                ascii = ascii + 1;
                finallyLsh = BuildingUtils.asciiToString(String.valueOf(ascii))
                        + BuildingUtils.formatNumberByLengh(Integer.valueOf(String.valueOf(maxLsh + (ascii - 96)).substring(1)), "000");
            } else {
                finallyLsh = BuildingUtils.asciiToString(String.valueOf(ascii))
                        + BuildingUtils.formatNumberByLengh(maxLsh + (ascii - 96), "000");
            }
        }
        return finallyLsh;
    }


}