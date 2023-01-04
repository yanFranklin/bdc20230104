package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.util.autogenerate.Annotation;
import cn.gtmap.realestate.common.util.autogenerate.AutoGenerateUtil;
import cn.gtmap.realestate.common.util.autogenerate.InterfaceInfo;
import cn.gtmap.realestate.common.util.autogenerate.ParameterInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/8/25 16:09
 * @description 代码自动生成
 * 1.在test()中配置接口必要信息和相关接口文件路径，运行test即可自动生成代码
 * 2.本工具类适用于相关接口文件已存在，在文件内新增一接口情况，暂不支持新增接口文件
 * 3.根据本项目命名习惯，路径规范编写的自动生成模板，符合大多数情况。如有特殊情况，请代码生成后自行按需修改
 */
//@SpringBootTest(classes = InquiryApp.class)
//@RunWith(SpringRunner.class)
public class AutoGenerateTest {

    @Test
    public void test(){
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        // 第一部分：相关文件本地路径
        // 新增接口的UIController本地路径（必填）
        interfaceInfo.setUiControllerPath("D:\\GTMAP\\realestate-3\\inquiry-ui\\src\\main\\java\\cn\\gtmap\\realestate\\inquiry\\ui\\web\\rest\\BdcZszmCxController.java");

        // 新增接口的RestService本地路径（必填）
        interfaceInfo.setRestServicePath("D:\\GTMAP\\realestate-3\\common\\src\\main\\java\\cn\\gtmap\\realestate\\common\\core\\service\\rest\\inquiry\\BdcZszmCxRestService.java");

        // 新增接口的RestController本地路径（必填）
        interfaceInfo.setRestControllerPath("D:\\GTMAP\\realestate-3\\inquiry\\src\\main\\java\\cn\\gtmap\\realestate\\inquiry\\web\\rest\\BdcZszmCxRestController.java");

        // 新增接口的Service本地路径（非必填）
        // 不填此项，则会根据RestController本地路径(xxx/web/rest/某某RestController.java)，自动寻找(xxx/service/某某Service.java)
        // 符合路径规律可不填此项，否则可指定Service路径
        //interfaceInfo.setServicePath("D:\\GTMAP\\realestate-3\\inquiry\\src\\main\\java\\cn\\gtmap\\realestate\\inquiry\\service\\BdcZszmCxService.java");

        // 第二部分：接口必要信息
        // 作者（必填）
        interfaceInfo.setAuthor("wangyaqing");

        // 接口描述（必填）
        interfaceInfo.setInterfaceDesc("接口描述");

        // 方法名称（必填）
        interfaceInfo.setMethodName("listBdcZszmTest7");

        // UIContoller方法的注解 可加多个注解 按需填写
        // 以下供复制
        // @RequestMapping @GetMapping @PostMapping @DeleteMapping @PutMapping @@ResponseBody
        Annotation uiAnno1 = new Annotation();
        uiAnno1.setAnnotationName("@GetMapping");
        uiAnno1.setValue("/zszm");

        Annotation uiAnno2 = new Annotation();
        uiAnno2.setAnnotationName("@ResponseBody");
        List<Annotation> annoList = new ArrayList<>();
        annoList.add(uiAnno1);
        annoList.add(uiAnno2);
        interfaceInfo.setUiMethodAnno(annoList);

        // 返回值类型 无返回值则不填
        interfaceInfo.setReturnType("String");

        // 返回值说明
        interfaceInfo.setReturnDesc("返回值说明");

        // 方法入参 按需填写
        // 参数一示例
        ParameterInfo param = new ParameterInfo();
        param.setDescription("参数1说明"); //参数说明 必填
        param.setName("pageable"); //参数名 必填
        param.setType("Pageable"); //参数类型 必填
        param.setRequired(false); //参数是否必传 默认为true
        param.setSource("body"); //参数来源 必填 可填body或query或path
        //参数可加一个注解
        //@RequestBody @LayuiPageable @RequestParam  @PathVariable(此注解请与方法注解值对应使用)
        Annotation a = new Annotation();
        a.setAnnotationName("@RequestBody");
        param.setAnnotation(a);

        // 参数二示例
        ParameterInfo p = new ParameterInfo();
        p.setName("bdcZszmQO");
        p.setDescription("参数2说明");
        p.setType("BdcZszmQO");
        //p.setRequired(false);
        p.setSource("body");
        Annotation b = new Annotation();
        b.setAnnotationName("@RequestParam");
        b.setValue("bdcZszmQO");
        p.setAnnotation(b);
        List<ParameterInfo> parameterInfos = new ArrayList<>();
        parameterInfos.add(param);
        parameterInfos.add(p);

        interfaceInfo.setMethodParam(parameterInfos);

        // RestService注解 必填
        Annotation annotation3 = new Annotation();
        annotation3.setAnnotationName("@GetMapping");
        annotation3.setValue("/realestate-inquiry/rest/v1.0/zszm/xmxx");
        List<Annotation> annotations = new ArrayList<>();
        annotations.add(annotation3);
        interfaceInfo.setRestMethodAnno(annotations);

        AutoGenerateUtil.autoGenerateInterface(interfaceInfo);
    }


    //RestService-->RestController
//        File f = new File(interfaceInfo.getServicePath());
//        String restService = f.getName().substring(0, f.getName().indexOf(".java"));
//        try {
//            Class<?> clazz = Class.forName(restService);
//            Class<?>[] interfaces = clazz.getInterfaces();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
}
