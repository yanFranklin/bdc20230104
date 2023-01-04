package cn.gtmap.realestate.building.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.building.config.ftp.FtpConfig;
import cn.gtmap.realestate.building.config.ftp.ZdtFtpConfig;
import cn.gtmap.realestate.building.service.ReadZdtService;
import cn.gtmap.realestate.building.util.BuildingUtils;
import cn.gtmap.realestate.common.core.domain.building.SSjZdtDO;
import cn.gtmap.realestate.common.core.service.InterfaceCode;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-21
 * @description 读取宗地图的抽象类
 */
public abstract class ReadZdtAbstractServiceImpl implements ReadZdtService{

    // 上传至国图大云的 文件名称 前缀 和 后缀
    protected final static String ZDT_PICNAME_PRE = "宗地图";
    protected final static String ZDT_PIC_NAME_SUF = ".jpg";

    @Autowired
    protected StorageClientMatcher storageClient;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    private EntityMapper entityMapper;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param djh
     * @return cn.gtmap.realestate.common.core.domain.building.SSjZdtDO
     * @description 根据djh查询宗地图实体
     */
    protected SSjZdtDO queryZdtByDjh(String djh){
        if(StringUtils.isNotBlank(djh)){
            Example example = new Example(SSjZdtDO.class);
            example.createCriteria().andEqualTo("djh",djh);
            example.setOrderByClause("scsj desc  NULLS last");
            List<SSjZdtDO> zdtList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(zdtList)){
                return zdtList.get(0);
            }
        }
        return null;
    }

    /**
     * @param djh
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 DJH 读取 国图大云的ID
     */
    @Override
    public String getZdtIdByDjh(@NotBlank(message = "地籍号不能为空") String djh) throws IOException {
        String zdtid = "";
        String picName = ZDT_PICNAME_PRE + djh + ZDT_PIC_NAME_SUF;
        List<StorageDto> storageDtoList = storageClient.listStoragesByName(null, null, null, picName, null, 2);
        if (CollectionUtils.isNotEmpty(storageDtoList)) {
            StorageDto storageDto = storageDtoList.get(0);
            zdtid = storageDto.getId();
        }
        // 如果国图大云上不存在  则重新获取 上传
        if(StringUtils.isBlank(zdtid)){
            String baseCode = "data:image/jpeg;base64,";
            baseCode += this.readBase64ByDjh(djh);
            if (StringUtils.isNotBlank(baseCode)) {
                MultipartFile file = Base64Utils.base64ToMultipart(baseCode);
                if (file != null) {
                    UserDto userDto = userManagerUtils.getCurrentUser();
                    MultipartDto multipartDto = BuildingUtils.getUploadParamDto(userDto.getId(), file,picName);
                    StorageDto storageDto = storageClient.multipartUpload(multipartDto);
                    if (storageDto != null) {
                        zdtid = storageDto.getId();
                    }
                }
            }
        }
        return zdtid;
    }

    /**
     * @author <a href="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @param listBeans InterfaceCode接口实现类的集合
     * @param code   查找的唯一标识码
     * @return 具体实现类
     * @description
     */
    public static FtpConfig getBean(Set<FtpConfig> listBeans, String code, Map<String, String> zdtFtpMap){
        if (CollectionUtils.isEmpty(listBeans) || StringUtils.isBlank(code)){
            return null;
        }

        InterfaceCode interfaceCode;
        for (Object interfaceCodeTemp:listBeans){
            if(interfaceCodeTemp instanceof InterfaceCode){
                interfaceCode = (InterfaceCode)interfaceCodeTemp;

                /**
                 * 接口唯一标识码类型改为Set以支持多个标识码对应同一个实现类
                 * modified by <a href="mailto:zhuyong@gtmap.cn"> 2018/11/20
                 */
                Set<String> codes = interfaceCode.getInterfaceCode();
                if(CollectionUtils.isEmpty(codes)){
                    continue;
                }

                for (String beanCode : codes){
                    if(StringUtils.equals(beanCode, code)){
                        // 直接return返回，避免break只跳出当前循环还需要标识判断
                        return (FtpConfig)interfaceCode;
                    }
                }
            }
        }

        //如果没有从直接实现中找到配置，则尝试在map中查找
        if (MapUtils.isNotEmpty(zdtFtpMap) && zdtFtpMap.containsKey(code)){
            List<String> config = Arrays.asList(zdtFtpMap.get(code).split(","));
            ZdtFtpConfig zdtFtpConfig = new ZdtFtpConfig();
            zdtFtpConfig.setIp(config.get(0));
            zdtFtpConfig.setPort(config.get(1));
            zdtFtpConfig.setUsername(config.get(2));
            zdtFtpConfig.setPassword(config.get(3));
            zdtFtpConfig.setPath(config.get(4));
            zdtFtpConfig.setPathF(config.get(5));
            return zdtFtpConfig;
        }

        return null;
    }
}
