package cn.gtmap.realestate.register.core.domain;

import cn.gtmap.realestate.common.core.domain.building.FwJsydzrzxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 不动产建设用地量化附记信息领域模型
 */
public class BdcJsydLhFjxx {

    public static final DecimalFormat decimalFormat = new DecimalFormat(CommonConstantUtils.FORMAT_BFS);

    /**
     * 已勾选房屋建设用地自然幢信息DO集合
     */
    private List<FwJsydzrzxxDO> ygxFwJsydzrzxxList;
    /**
     * 宗地上全部自然幢信息集合
     */
    private List<FwJsydzrzxxDO> allFwJsydzrzxxList;
    /**
     * 量化首登勾选自然幢信息集合
     */
    private List<FwJsydzrzxxDO> sdgxFwJsydzrzxxList = new ArrayList<>();
    /**
     * 附记模板
     */
    private String fjModel;

    /**
     * 量化勾选楼幢
     */
    private String lhgxlz;
    /**
     * 量化未勾选楼幢
     */
    private String lhwgxlz;
    /**
     * 量化首登勾选楼幢
     */
    private String sdgxlz;
    /**
     * 量化首登未勾选楼幢
     */
    private String sdwgxlz;

    public String getFjModel() {
        return fjModel;
    }

    public void setFjModel(String fjModel) {
        this.fjModel = fjModel;
    }

    public List<FwJsydzrzxxDO> getYgxFwJsydzrzxxList() {
        return ygxFwJsydzrzxxList;
    }

    public void setYgxFwJsydzrzxxList(List<FwJsydzrzxxDO> ygxFwJsydzrzxxList) {
        this.ygxFwJsydzrzxxList = ygxFwJsydzrzxxList;
    }

    public List<FwJsydzrzxxDO> getAllFwJsydzrzxxList() {
        return allFwJsydzrzxxList;
    }

    public void setAllFwJsydzrzxxList(List<FwJsydzrzxxDO> allFwJsydzrzxxList) {
        this.allFwJsydzrzxxList = allFwJsydzrzxxList;
    }

    public BdcJsydLhFjxx(String fjModel, List<FwJsydzrzxxDO> ygxFwJsydzrzxxList, List<FwJsydzrzxxDO> allFwJsydzrzxxList){
        this.fjModel = fjModel;
        this.ygxFwJsydzrzxxList = ygxFwJsydzrzxxList;
        this.allFwJsydzrzxxList = allFwJsydzrzxxList;
    }

    public List<FwJsydzrzxxDO> getSdgxFwJsydzrzxxList() {
        if(CollectionUtils.isEmpty(sdgxFwJsydzrzxxList) && CollectionUtils.isNotEmpty(allFwJsydzrzxxList)){
            sdgxFwJsydzrzxxList = allFwJsydzrzxxList.stream().filter(t -> Objects.equals(t.getLhsdqlzt(), 1))
                    .filter(t->StringUtils.isNotBlank(t.getDh())).collect(Collectors.toList());
            sdgxFwJsydzrzxxList.addAll(ygxFwJsydzrzxxList);
        }
        return sdgxFwJsydzrzxxList;
    }

    /**
     * 计算量化勾选逻辑幢
     */
    public String getLhgxlz() {
        if(StringUtils.isBlank(lhgxlz)){
            List<String> gxlzList = this.ygxFwJsydzrzxxList.stream().filter(t->StringUtils.isNotBlank(t.getDh()))
                    .sorted(Comparator.comparing(t-> t.getDh())).map(FwJsydzrzxxDO::getDh).collect(Collectors.toList());
            lhgxlz = CollectionUtils.isNotEmpty(gxlzList)? StringUtils.join(gxlzList, ".") : "";
        }
        return lhgxlz;
    }

    /**
     * 计算量化未勾选楼幢
     */
    public String getLhwgxlz() {
        if(StringUtils.isBlank(lhwgxlz)){
            List<String> ygxFwJsydzrzxxIndexList = this.ygxFwJsydzrzxxList.stream().map(FwJsydzrzxxDO::getFwJsydzrzxxIndex).collect(Collectors.toList());
            List<String> wgxlzList = this.allFwJsydzrzxxList.stream().filter(t-> !(ygxFwJsydzrzxxIndexList.contains(t.getFwJsydzrzxxIndex())))
                    .filter(t->StringUtils.isNotBlank(t.getDh())).sorted(Comparator.comparing(t->t.getDh()))
                    .map(FwJsydzrzxxDO::getDh).collect(Collectors.toList());
            lhwgxlz = CollectionUtils.isNotEmpty(wgxlzList)? StringUtils.join(wgxlzList, "、") : "";
        }
        return lhwgxlz;
    }

    /**
     * 计算量化首登勾选楼幢
     */
    public String getSdgxlz() {
        if(StringUtils.isBlank(sdgxlz)){
            List<FwJsydzrzxxDO> sdgxFwJsydzrzxxList = this.getSdgxFwJsydzrzxxList();
            sdgxFwJsydzrzxxList.addAll(ygxFwJsydzrzxxList);
            List<String> sdgxlzList = sdgxFwJsydzrzxxList.stream().filter(t->StringUtils.isNotBlank(t.getDh()))
                    .sorted(Comparator.comparing(t-> t.getDh()))
                    .map(FwJsydzrzxxDO::getDh).distinct().collect(Collectors.toList());
            sdgxlz = CollectionUtils.isNotEmpty(sdgxlzList)? StringUtils.join(sdgxlzList, ".") : "";
        }
        return sdgxlz;
    }
    /**
     * 计算量化首登勾选楼幢
     */
    public String getSdwgxlz() {
        if(StringUtils.isBlank(sdwgxlz)){
            List<String> sdgxZrzxxIndexList = this.getSdgxFwJsydzrzxxList().stream().map(FwJsydzrzxxDO::getFwJsydzrzxxIndex).collect(Collectors.toList());
            List<String> sdwgxlzList = allFwJsydzrzxxList.stream().filter(t-> !(sdgxZrzxxIndexList.contains(t.getFwJsydzrzxxIndex())))
                    .filter(t->StringUtils.isNotBlank(t.getDh())).sorted(Comparator.comparing(t-> t.getDh()))
                    .map(FwJsydzrzxxDO::getDh).collect(Collectors.toList());
            sdwgxlz = CollectionUtils.isNotEmpty(sdwgxlzList)? StringUtils.join(sdwgxlzList, "、") : "";
        }
        return sdwgxlz;
    }

    public String generateLhfj(){
        // 获取勾选的量化楼幢幢号
        List<String> gxlzList = this.ygxFwJsydzrzxxList.stream().filter(t->StringUtils.isNotBlank(t.getDh()))
                .sorted(Comparator.comparing(t-> t.getDh())).map(FwJsydzrzxxDO::getDh).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(gxlzList)){
            throw new AppException(ErrorCode.CUSTOM,  "未获取到勾选的楼幢幢号信息，请确认楼幢是否拥有幢号信息。");
        }
        // 获取附记占位符
        Set<String> placeHolderList = this.getPlaceHolder();
        if(CollectionUtils.isEmpty(placeHolderList)){
            throw new AppException(ErrorCode.CUSTOM,  "未获取到附记模板内容，请检查附记模板占位符配置是否正确。");
        }

        Map<String, Object> lhxxMap = this.getLhxxMap(placeHolderList);

        return mbParamReplace(fjModel, lhxxMap);
    }

    /**
     * 获取量化附加替换标识符值
     */
    private Map<String, Object> getLhxxMap(Set<String> placeHolderList ){
        // 百分数格式
        DecimalFormat decimalFormat = new DecimalFormat(CommonConstantUtils.FORMAT_BFS);
        Map<String, Object> lhxxMap = new HashMap<>(10);
        for(String bs: placeHolderList){
            switch(bs){
                case "gxlz":
                    // 勾选的量化楼幢幢号
                    lhxxMap.put("gxlz", this.getLhgxlz());
                    break;
                case "gxfe":
                case "wgxfe":
                    // 勾选的量化楼幢份额、未勾选份额
                    Double gxfebl = this.ygxFwJsydzrzxxList.stream().filter(fwJsydzrzxx -> null != fwJsydzrzxx.getBzghmjbl())
                            .map(t -> new BigDecimal(String.valueOf(t.getBzghmjbl())))
                            .reduce((m,n)->m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
                    lhxxMap.put("gxfe", decimalFormat.format(gxfebl));
                    // 剩余量化楼幢份额:采用 1- gxfebl 计算
                    Double wgxfebl = new BigDecimal("1").subtract(new BigDecimal(gxfebl.toString())).doubleValue();
                    lhxxMap.put("wgxfe", decimalFormat.format(wgxfebl));
                    break;
                case "wgxlz":
                    // 获取未勾选的逻辑幢
                    lhxxMap.put("wgxlz", this.getLhwgxlz());
                    break;
                case "sdgxlz":
                    // 量化首登勾选楼幢
                    lhxxMap.put("sdgxlz", this.getSdgxlz());
                    break;
                case "sdgxfe":
                case "sdwgxfe":
                    // 量化首登勾选份额、未勾选份额
                    Double sdgxfebl = this.getSdgxFwJsydzrzxxList().stream().filter(fwJsydzrzxx -> null != fwJsydzrzxx.getBzghmjbl())
                            .map(t -> new BigDecimal(String.valueOf(t.getBzghmjbl())))
                            .reduce((m,n)->m.add(n)).map(BigDecimal::doubleValue).orElse(0d);
                    lhxxMap.put("sdgxfe", decimalFormat.format(sdgxfebl));
                    Double sdwgxfebl = new BigDecimal("1").subtract(new BigDecimal(sdgxfebl.toString())).doubleValue();
                    lhxxMap.put("sdwgxfe", decimalFormat.format(sdwgxfebl));
                    break;
                case "sdwgxlz":
                    // 量化首登未勾选楼幢幢号
                    lhxxMap.put("sdwgxlz", this.getSdwgxlz());
                    break;
                default:
            }
        }
        return lhxxMap;
    }

    /**
     * 用于匹配字符串中 #{gxfe} 的数据
     */
    private static Pattern FIND_PATTERN = Pattern.compile("\\#\\{+[a-zA-Z]+\\}");

    /**
     * 获取附记模板中的占位标识符
     */
    private Set<String> getPlaceHolder(){
        Set<String> placeHolderList = new HashSet<>(10);
        if(StringUtils.isNotBlank(this.fjModel)){
            Matcher matcher = FIND_PATTERN.matcher(this.fjModel);
            while (matcher.find()) {
                String matchValue = matcher.group();
                matchValue = matchValue.replace("#{", "").replace("}", "");
                placeHolderList.add(matchValue);
            }
        }
        return placeHolderList;
    }

    /**
     * 替换模板中 #{} 标签
     */
    private static String mbParamReplace(String mb, Map map) {
        // 获取参数
        if (map != null && StringUtils.isNotBlank(mb)) {
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                String key = object.toString();
                if (StringUtils.isNotBlank(key) && map.get(key) != null) {
                    mb = mb.replaceFirst("(?i)#\\{" + key + "\\}", map.get(key).toString());
                }
            }
        }
        return mb;
    }

}
