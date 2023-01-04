package script;

import java.io.*;
import java.util.Properties;

public class VersionV2 {
    private static final String GTC_PARENT_V1 = "cn.gtmap.gtc.parent.v1";
    private static final String GTC_PARENT_V2 = "cn.gtmap.gtc.parent.v2";
    private static final String COMMON_GC_V1 = "common-gc-v1";
    private static final String COMMON_GC_V2 = "common-gc-v2";

    public static void main(String[] args){
        try {
            Properties properties = readProperties();
            changeVersion(properties, "../", GTC_PARENT_V1, GTC_PARENT_V2, "version");
            changeVersion(properties, "../common/", COMMON_GC_V1, COMMON_GC_V2, "artifactId");
            changeVersion(properties, "../common/", GTC_PARENT_V1, GTC_PARENT_V2, "version");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void changeVersion(Properties properties, String pomPath, String oldVersion, String newVersion, String xmlTag) throws Exception {
        String oldVersionXml = "<" + xmlTag + ">" + properties.getProperty(oldVersion) + "</" + xmlTag + ">";

        File pomFile = new File(pomPath + "pom.xml") ;
        File tmpFile = new File(pomPath + "tmp.xml");

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pomFile)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile)));
        String line;
        while ((line = reader.readLine()) != null) {
            if(oldVersionXml.equals(line.trim())) {
                line = line.replace(properties.getProperty(oldVersion), properties.getProperty(newVersion));
            }
            writer.write(line);
            writer.newLine();
            writer.flush();
        }

        writer.close();
        reader.close();

        pomFile.delete();
        tmpFile.renameTo(pomFile);
    }

    private static Properties readProperties() throws IOException {
        Properties properties = new Properties();
        InputStream in = new FileInputStream(new File("version.properties"));
        properties.load(in);
        return properties;
    }
}