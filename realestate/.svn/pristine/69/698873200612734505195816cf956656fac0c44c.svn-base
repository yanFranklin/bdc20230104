package cn.gtmap.realestate.certificate.util;

public class FileContentTypeUtils {
    public static String contentType(String fileName) {
        if (fileName.lastIndexOf(".") > 0) {
            String filenameExtension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (filenameExtension.equals(".BMP") || filenameExtension.equals(".bmp")
                    || filenameExtension.toUpperCase().equals(".BMP")) {
                return "image/bmp";
            }
            if (filenameExtension.equals(".GIF") || filenameExtension.equals(".gif")
                    || filenameExtension.toUpperCase().equals(".GIF")) {
                return "image/gif";
            }
            if (filenameExtension.equals(".JPEG") || filenameExtension.equals(".jpeg") || filenameExtension.equals(".JPG")
                    || filenameExtension.equals(".jpg") || filenameExtension.equals(".PNG")
                    || filenameExtension.equals(".png") || filenameExtension.toUpperCase().equals(".JPEG")
                    || filenameExtension.toUpperCase().equals(".JPG") || filenameExtension.toUpperCase().equals(".PNG")) {
                return "image/jpeg";
            }
            if (filenameExtension.equals(".HTML") || filenameExtension.equals(".html")) {
                return "text/html";
            }
            if (filenameExtension.equals(".TXT") || filenameExtension.equals(".txt")
                    || filenameExtension.toUpperCase().equals(".TXT")) {
                return "text/plain";
            }
            if (filenameExtension.equals(".VSD") || filenameExtension.equals(".vsd")
                    || filenameExtension.toUpperCase().equals(".VSD")) {
                return "application/vnd.visio";
            }
            if (filenameExtension.equals(".PPTX") || filenameExtension.equals(".pptx") || filenameExtension.equals(".PPT")
                    || filenameExtension.equals(".ppt") || filenameExtension.toUpperCase().equals(".PPTX")
                    || filenameExtension.toUpperCase().equals(".PPT")) {
                return "application/vnd.ms-powerpoint";
            }
            if (filenameExtension.equals(".DOCX") || filenameExtension.equals(".docx") || filenameExtension.equals(".DOC")
                    || filenameExtension.equals(".doc") || filenameExtension.toUpperCase().equals(".DOCX")
                    || filenameExtension.toUpperCase().equals(".DOC")) {
                return "application/msword";
            }
            if (filenameExtension.equals(".XML") || filenameExtension.equals(".xml")
                    || filenameExtension.toUpperCase().equals(".XML")) {
                return "text/xml";
            }
            if (filenameExtension.equals(".pdf") || filenameExtension.equals(".PDF")
                    || filenameExtension.toUpperCase().equals(".PDF")) {
                return "application/pdf";
            }
        }
        return null;
    }
}
