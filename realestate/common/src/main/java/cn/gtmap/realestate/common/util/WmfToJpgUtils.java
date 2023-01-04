package cn.gtmap.realestate.common.util;

import net.arnx.wmf2svg.gdi.svg.SvgGdi;
import net.arnx.wmf2svg.gdi.wmf.WmfParser;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-06
 * @description
 */
public class WmfToJpgUtils {

    public static void main2(String[] args) throws Exception {
//        convert("C:\\Users\\gt\\Desktop\\hst1.wmf", "C:\\Users\\gt\\Desktop\\hst1.svg");
    }

    public static byte[] convert(byte[] wmfBytes) throws Exception {
        InputStream in = new ByteArrayInputStream(wmfBytes);
        WmfParser parser = new WmfParser();
        final SvgGdi gdi = new SvgGdi(false);
        parser.parse(in, gdi);
        Document doc = gdi.getDocument();
        OutputStream out = new ByteArrayOutputStream();
        return output(doc, out);
    }

    private static byte[] output(Document doc, OutputStream out) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 1.0//EN");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd");
        transformer.transform(new DOMSource(doc), new StreamResult(out));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(doc), new StreamResult(bos));
        out.flush();
        out.close();
        /* 将svg直接转为jpg  */
        JPEGTranscoder it = new JPEGTranscoder();
        it.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(1f));
        ByteArrayOutputStream jpg = new ByteArrayOutputStream();
        it.transcode(new TranscoderInput(new ByteArrayInputStream(bos.toByteArray())), new TranscoderOutput(jpg));
        return jpg.toByteArray();
    }

}
