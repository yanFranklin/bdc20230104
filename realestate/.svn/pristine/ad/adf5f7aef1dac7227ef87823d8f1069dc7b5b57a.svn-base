package cn.gtmap.realestate.common.util.autogenerate;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.Base64Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.Arrays;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/8/25 16:05
 * @description 自动生成代码
 */
public class AutoGenerateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoGenerateUtil.class);

    public static void autoGenerateInterface(InterfaceInfo interfaceInfo) {
        // uiController
        String uiControllerContent = readFileContent(interfaceInfo.getUiControllerPath());
        String codeStr = generateUIControllerCode(interfaceInfo);
        String newContent = insertCode(uiControllerContent, codeStr);
        writeFileContent(interfaceInfo.getUiControllerPath(), newContent);

        // restService
        String restServiceContent = readFileContent(interfaceInfo.getRestServicePath());
        String restServiceCode = generateRestServiceCode(interfaceInfo);
        String newRestService = insertCode(restServiceContent, restServiceCode);
        writeFileContent(interfaceInfo.getRestServicePath(), newRestService);

        // restController
        String restController = readFileContent(interfaceInfo.getRestControllerPath());
        String restControllerCode = generateRestControllerCode(interfaceInfo);
        String newRestController = insertCode(restController, restControllerCode);
        writeFileContent(interfaceInfo.getRestControllerPath(), newRestController);

        // service
        String servicePath = null;
        if (StringUtils.isNotBlank(interfaceInfo.getServicePath())) {
            servicePath = interfaceInfo.getServicePath();
        } else {
            File RestControllerFile = new File(interfaceInfo.getRestControllerPath());
            String serviceName = RestControllerFile.getName().substring(0, RestControllerFile.getName().indexOf("RestController")) + "Service.java";
            servicePath = interfaceInfo.getRestControllerPath().substring(0, interfaceInfo.getRestControllerPath().indexOf("web"));
            servicePath = servicePath + "service" + File.separator + serviceName;
        }
        String service = readFileContent(servicePath);
        String serviceCode = generateServiceCode(interfaceInfo);
        String newService = insertCode(service, serviceCode);
        writeFileContent(servicePath, newService);

        // serviceImpl
        File file = new File(servicePath);
        String serviceImplName = "";
        if (file.getName().indexOf(".java") > 0) {
            serviceImplName = file.getName().substring(0, file.getName().indexOf(".java")) + "Impl.java";
        }
        String dirPath = file.getParent();
        String implPath = dirPath + File.separator + "impl" + File.separator + serviceImplName;
        String serviceImpl = readFileContent(dirPath + File.separator + "impl" + File.separator + serviceImplName);
        String ServieImplCode = generateServiceImplCode(interfaceInfo);
        String newServiceImpl = insertCode(serviceImpl, ServieImplCode);
        writeFileContent(implPath, newServiceImpl);
    }

    private static String readFileContent(String path) {
        if (StringUtils.isBlank(path)) {
            LOGGER.error("文件路径为空！");
            throw new AppException("文件路径为空！");
        }

        File file = new File(path);
        if (!file.exists()) {
            LOGGER.error("文件不存在，文件路径为{}", path);
            throw new AppException("文件不存在!");
        }

        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\r\n");
            }
        } catch (IOException e) {
            LOGGER.error("读取文件失败，文件路径为{}", path);
            throw new AppException("读取文件内容失败！");
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("输入流关闭异常", e);
                }
            }
        }
        return stringBuilder.toString();
    }

    private static String generateUIControllerCode(InterfaceInfo interfaceInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(buildExplanatoryNote(interfaceInfo));

        if (!CollectionUtils.isEmpty(interfaceInfo.getUiMethodAnno())) {
            for (Annotation anno : interfaceInfo.getUiMethodAnno()) {
                if (anno.getValue() != null) {
                    stringBuilder.append(anno.getAnnotationName() + "(\"" + anno.getValue() + "\")");
                } else {
                    stringBuilder.append(anno.getAnnotationName());
                }
                stringBuilder.append("\r\n");
                stringBuilder.append("    ");
            }
        }

        stringBuilder.append("public ");
        if (interfaceInfo.getReturnType() == null) {
            stringBuilder.append("void ");
        } else {
            stringBuilder.append(interfaceInfo.getReturnType() + " ");
        }
        stringBuilder.append(interfaceInfo.getMethodName() + "(");

        //这里有问题要多测测
        if (CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            stringBuilder.append(")");
        } else {
            stringBuilder.append(jointMethodParam(interfaceInfo));

            String tempStr = stringBuilder.toString();
            tempStr = tempStr.substring(0, tempStr.length() - 2);
            tempStr = tempStr + ")";
            stringBuilder = new StringBuilder(tempStr);
        }

        stringBuilder.append(" {")
                .append("\r\n")
                .append("        ");
        if(interfaceInfo.getReturnType() != null){
            stringBuilder.append("return ");
        }

        // 生成feignService名称
        File file = new File(interfaceInfo.getUiControllerPath());
        String feignService = file.getName().substring(0, file.getName().indexOf("Controller")) + "FeignService";
        char firstChar = (char) (feignService.charAt(0) + 32);
        feignService = firstChar + feignService.substring(1);

        stringBuilder.append(feignService + "." + interfaceInfo.getMethodName() + "(");
        if (!CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            for (ParameterInfo param : interfaceInfo.getMethodParam()) {
                stringBuilder.append(param.getName() + ", ");
            }

            String temp = stringBuilder.toString();
            temp = temp.substring(0, temp.length() - 2);
            stringBuilder = new StringBuilder(temp);
        }
        stringBuilder.append(");")
                .append("\r\n")
                .append("    }");
        return stringBuilder.toString();
    }

    public static String insertCode(String fileContent, String codeStr) {
        fileContent = fileContent.substring(0, fileContent.lastIndexOf("}") - 1);

        StringBuilder builder = new StringBuilder(fileContent);
        builder.append("\r\n");
        builder.append(codeStr);
        builder.append("\r\n");
        builder.append("}");
        return builder.toString();
    }

    private static boolean writeFileContent(String path, String content) {
        if (StringUtils.isBlank(path) || StringUtils.isBlank(content)) {
            LOGGER.warn("文件路径或生成代码为空");
            return false;
        }

        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
            LOGGER.info("自动生成的代码成功写入文件");
        } catch (IOException e) {
            LOGGER.info("自动生成的代码写入文件失败", e);
            throw new AppException("自动生成的代码写入文件失败");
        } finally {
            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    LOGGER.error("输出流关闭异常", e);
                }
            }
        }
        return true;
    }

    private static String generateRestServiceCode(InterfaceInfo interfaceInfo) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(buildExplanatoryNote(interfaceInfo));

        for (Annotation anno : interfaceInfo.getRestMethodAnno()) {
            if (anno != null) {
                stringBuilder.append(anno.getAnnotationName() + "(\"" + anno.getValue() + "\")");
            }
        }
        stringBuilder.append("\r\n    ");
        if (interfaceInfo.getReturnType() == null) {
            stringBuilder.append("void ");
        } else {
            stringBuilder.append(interfaceInfo.getReturnType() + " ");
        }
        stringBuilder.append(interfaceInfo.getMethodName() + "(");

        if (CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            stringBuilder.append(");");
            return stringBuilder.toString();
        } else {
            stringBuilder.append(jointMethodParam(interfaceInfo));
        }

        String tempStr = stringBuilder.toString();
        tempStr = tempStr.substring(0, tempStr.length() - 2);
        StringBuilder builder = new StringBuilder(tempStr);
        builder.append(");");
        return builder.toString();
    }

    private static String generateRestControllerCode(InterfaceInfo interfaceInfo) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(buildExplanatoryNote(interfaceInfo))
                .append("@Override")
                .append("\r\n")
                .append("    @ResponseStatus(code = HttpStatus.OK)")
                .append("\r\n")
                .append("    @ApiOperation(\"" + interfaceInfo.getInterfaceDesc() + "\")")
                .append("\r\n");
        if (!CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            if (interfaceInfo.getMethodParam().size() > 1) {
                stringBuilder.append("    @ApiImplicitParams({");
                for (ParameterInfo param : interfaceInfo.getMethodParam()) {
                    stringBuilder.append("@ApiImplicitParam(name = \"")
                            .append(param.getName())
                            .append("\", value = \"")
                            .append(param.getDescription())
                            .append("\", required = ")
                            .append(param.isRequired())
                            .append(", paramType = \"")
                            .append(param.getSource())
                            .append("\"), ");
                }
                String tempStr = stringBuilder.toString();
                tempStr = tempStr.substring(0, tempStr.length() - 2);
                stringBuilder = new StringBuilder(tempStr);
                stringBuilder.append("})");
            } else {
                ParameterInfo p = interfaceInfo.getMethodParam().get(0);
                stringBuilder.append("    @ApiImplicitParam(name = \"")
                        .append(p.getName())
                        .append("\", value = \"")
                        .append(p.getDescription())
                        .append("\", required = ")
                        .append(p.isRequired())
                        .append(", paramType = \"")
                        .append(p.getSource())
                        .append("\")");
            }
        }
        stringBuilder.append("\r\n    ")
                .append("public ");
        if (interfaceInfo.getReturnType() == null) {
            stringBuilder.append("void ");
        } else {
            stringBuilder.append(interfaceInfo.getReturnType() + " ");
        }
        stringBuilder.append(interfaceInfo.getMethodName())
                .append("(");

        if (CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            stringBuilder.append(")");
        } else {
            stringBuilder.append(jointMethodParam(interfaceInfo));
            String tempStr = stringBuilder.toString();
            tempStr = tempStr.substring(0, tempStr.length() - 2);
            stringBuilder = new StringBuilder(tempStr);
            stringBuilder.append(")");
        }
        stringBuilder.append(" {");
        stringBuilder.append("\r\n        ");

        File file = new File(interfaceInfo.getRestControllerPath());
        String service = file.getName().substring(0, file.getName().indexOf("RestController")) + "Service";
        char firstChar = (char) (service.charAt(0) + 32);
        service = firstChar + service.substring(1);

        if(interfaceInfo.getReturnType() != null){
            stringBuilder.append("return ");
        }

        stringBuilder.append(service + "." + interfaceInfo.getMethodName() + "(");
        if (!CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            for (ParameterInfo param : interfaceInfo.getMethodParam()) {
                stringBuilder.append(param.getName() + ", ");
            }

            String temp = stringBuilder.toString();
            temp = temp.substring(0, temp.length() - 2);
            stringBuilder = new StringBuilder(temp);
        }
        stringBuilder.append(");");
        stringBuilder.append("\r\n    ");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static String generateServiceCode(InterfaceInfo interfaceInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(buildExplanatoryNote(interfaceInfo));

        if (interfaceInfo.getReturnType() == null) {
            stringBuilder.append("void ");
        } else {
            stringBuilder.append(interfaceInfo.getReturnType() + " ");
        }
        stringBuilder.append(" " + interfaceInfo.getMethodName() + "(");

        if (!CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            for (ParameterInfo p : interfaceInfo.getMethodParam()) {
                stringBuilder.append(p.getType() + " " + p.getName() + ", ");
            }
            String tempStr = stringBuilder.toString();
            tempStr = tempStr.substring(0, tempStr.length() - 2);
            stringBuilder = new StringBuilder(tempStr);

        }
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    private static String generateServiceImplCode(InterfaceInfo interfaceInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(buildExplanatoryNote(interfaceInfo));

        stringBuilder.append("public ");
        if (interfaceInfo.getReturnType() == null) {
            stringBuilder.append("void ");
        } else {
            stringBuilder.append(interfaceInfo.getReturnType() + " ");
        }
        stringBuilder.append(interfaceInfo.getMethodName() + "(");

        if (!CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            for (ParameterInfo p : interfaceInfo.getMethodParam()) {
                stringBuilder.append(p.getType() + " " + p.getName() + ", ");
            }
            String tempStr = stringBuilder.toString();
            tempStr = tempStr.substring(0, tempStr.length() - 2);
            stringBuilder = new StringBuilder(tempStr);
        }
        stringBuilder.append(")");
        stringBuilder.append(" {")
                .append("\r\n        ");
        if(interfaceInfo.getReturnType() != null){
            stringBuilder.append("return null;");
        }
        stringBuilder.append("\r\n    ")
                .append("}");
        return stringBuilder.toString();
    }

    /**
     * @return java.lang.String
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [interfaceInfo]
     * @description 生成注释
     */
    private static String buildExplanatoryNote(InterfaceInfo interfaceInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    /**")
                .append("\r\n")
                .append("     * @author <a href=\"mailto:" + interfaceInfo.getAuthor() + "@gtmap.cn\">" + interfaceInfo.getAuthor() + "</a>")
                .append("\r\n");
        if (!CollectionUtils.isEmpty(interfaceInfo.getMethodParam())) {
            for (ParameterInfo param : interfaceInfo.getMethodParam()) {
                stringBuilder.append("     * @param " + param.getName() + " " + param.getDescription())
                        .append("\r\n");
            }
        }
        if (interfaceInfo.getReturnType() != null) {
            stringBuilder.append("     * @return {" + interfaceInfo.getReturnType() + "} " + interfaceInfo.getReturnDesc());
            stringBuilder.append("\r\n");
        }
        stringBuilder.append("     * @description " + interfaceInfo.getInterfaceDesc());
        stringBuilder.append("\r\n");
        stringBuilder.append("     */");
        stringBuilder.append("\r\n");
        stringBuilder.append("    ");

        return stringBuilder.toString();
    }

    private static String jointMethodParam(InterfaceInfo interfaceInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ParameterInfo p : interfaceInfo.getMethodParam()) {
            if (p.getAnnotation() != null) {
                stringBuilder.append(p.getAnnotation().getAnnotationName());
                if (p.getAnnotation().getValue() != null) {
                    stringBuilder.append("(value = \"" + p.getAnnotation().getValue() + "\")");
                }

                if (!p.isRequired()) {
                    if (p.getAnnotation().getValue() != null) {
                        String tempStr = stringBuilder.toString();
                        tempStr = tempStr.substring(0, tempStr.length() - 1);
                        tempStr += ", required = false)";
                        stringBuilder = new StringBuilder(tempStr);
                    } else {
                        stringBuilder.append("(required = false)");
                    }
                }
                stringBuilder.append(" ");
            }
            stringBuilder.append(p.getType() + " " + p.getName() + ", ");
        }
        return stringBuilder.toString();
    }
}
