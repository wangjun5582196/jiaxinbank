package com.ai.indeed.email;

import org.apache.commons.lang3.StringUtils;

import javax.mail.internet.MimeUtility;
import javax.mail.internet.ParseException;
import java.util.StringTokenizer;

/**
 * @author lizl32567
 * @date 20220524
 */
public class MailUtil {

    /**
     * 从邮件正文中获取的主题信息，在这里做拼接转换操作
     * @param mainSubject
     * @return
     */
    public static String deCode(String mainSubject) {
        String subject = "";
        try{
            if (StringUtils.isNotBlank(mainSubject) && mainSubject.contains("?==?")) {
                mainSubject = mainSubject.replace("?==?","?=\n=?");
            }
            // 清除任何未转义且后跟空格的换行符
            String unfold = MimeUtility.unfold(mainSubject);
            if (unfold.indexOf("=?") == -1) {
                return unfold;
            }
            // 几种可能出现的分隔符
            String lwsp = " \t\n\r";
            // StringTokenizer用作分隔
            StringTokenizer st = new StringTokenizer(unfold, lwsp, true);
            // 用于承接编码声明内的内容
            StringBuffer sb = new StringBuffer();
            // 用于承接结果内容
            StringBuffer resultBuff = new StringBuffer();

            String lastCharSet = "";

            String lastEncoding = "";

            // 遍历分隔后的token对象
            while (st.hasMoreTokens()) {

                String charset = "";

                String encoding = "";

                char c;
                String s = st.nextToken();
                // If whitespace, append it to the whitespace buffer
                if (((c = s.charAt(0)) == ' ') || (c == '\t') ||
                        (c == '\r') || (c == '\n')) {

                } else {
                    // 头部固定匹配 =?
                    if (!s.startsWith("=?")) {
                        throw new ParseException(
                                "encoded word does not include charset: " + s);
                    }
                    // 获取 头部信息中封装的 charSet 信息
                    int start = 2;
                    int pos;
                    if ((pos = s.indexOf('?', start)) == -1) {
                        throw new ParseException(
                                "encoded word does not include charset: " + s);
                    }
                    charset = s.substring(start, pos);
                    if (StringUtils.isBlank(lastCharSet)) {
                        lastCharSet = charset;
                    }
                    // 获取头部信息中的 encoding 信息
                    start = pos+1;
                    if ((pos = s.indexOf('?', start)) == -1) {
                        throw new ParseException(
                                "encoded word does not include encoding: " + s);
                    }
                    encoding = s.substring(start, pos);
                    start = pos+1;
                    if (StringUtils.isBlank(lastEncoding)) {
                        lastEncoding = encoding;
                    }
                    if ((pos = s.indexOf("?=", start)) == -1) {
                        throw new ParseException(
                                "encoded word does not end with \"?=\": " + s);
                    }
                    if ((StringUtils.isNotBlank(lastCharSet) && !lastCharSet.equals(charset))
                            ||(StringUtils.isNotBlank(lastEncoding) && !lastEncoding.equals(encoding))) {
                        /**
                         * 1.需要将之前的数据全部拼接上头尾
                         * 2.拼入结果字符串中
                         * 3.将lastEncoding 与 lastCharset 重新赋值
                         */
                        String subSubject =  "=?" + lastCharSet + "?" + lastEncoding + "?" + sb.toString() + "?=\n";
                        // 结果集赋值
                        resultBuff.append(subSubject);
                        // 重新初始化
                        lastCharSet = charset;
                        lastEncoding = encoding;
                        sb.setLength(0);
                    }
                    String sub = s.substring(start, pos);
                    // base64编码一般以=号做补位，如果出现 = 在非末尾段 则使用原生方法解析
                    if (sub.contains("=") && st.hasMoreTokens() && encoding.equalsIgnoreCase("B")) {
                            return MimeUtility.decodeText(mainSubject);
                    }
                    sb.append(sub);
                    // 如果当前段不是以？= 结尾，则调整
                    if (!s.endsWith("?=")) {
                        String tailConttext = s.substring(s.lastIndexOf("?=")+2);
                        String subSubject =  "=?" + lastCharSet + "?" + lastEncoding + "?" + sb.toString() + "?=" + tailConttext + "\n";
                        // 结果集赋值
                        resultBuff.append(subSubject);
                        // 重新初始化
                        lastCharSet = charset;
                        lastEncoding = encoding;
                        sb.setLength(0);
                    }
                }
            }
            // 根据现有信息返回一个不做分隔的完整字符串
            String currentSubject = "=?" + lastCharSet + "?" + lastEncoding + "?" + sb.toString() + "?=";
            resultBuff.append(currentSubject);
            // 返回解码后的主题信息
            subject = MimeUtility.decodeText(resultBuff.toString());
        } catch (Exception e){

        }

        return subject;
    }


}
