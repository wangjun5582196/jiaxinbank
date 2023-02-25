package com.ai.indeed.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlParaser {
    public static Map<String,String> readXml(String xml){
        Map<String,String> param = new HashMap<>();
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            while (it.hasNext()) {
                Element book = (Element) it.next();
                List<Attribute> bookAttrs = book.attributes();
//                for (Attribute attr : bookAttrs) {
//                    System.out.println("属性名：" + attr.getName() + "--属性值："
//                            + attr.getValue());
//                }
                Iterator itt = book.elementIterator();
                if(book.getName().equals("BODY")){
                    while (itt.hasNext()) {
                        Element bookChild = (Element) itt.next();
                        param.put(bookChild.getName(),bookChild.getStringValue());
//                        System.out.println("节点名：" + bookChild.getName() + "--节点值：" + bookChild.getStringValue());
                    }
                }
            }
        } catch (DocumentException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return param;
    }

    public static Map<String,String> readHeadXml(String xml){
        Map<String,String> param = new HashMap<>();
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            while (it.hasNext()) {
                Element book = (Element) it.next();
                List<Attribute> bookAttrs = book.attributes();
//                for (Attribute attr : bookAttrs) {
//                    System.out.println("属性名：" + attr.getName() + "--属性值："
//                            + attr.getValue());
//                }
                Iterator itt = book.elementIterator();
                if(!book.getName().equals("BODY")){
                    while (itt.hasNext()) {
                        Element bookChild = (Element) itt.next();
                        param.put(bookChild.getName(),bookChild.getStringValue());
//                        System.out.println("节点名：" + bookChild.getName() + "--节点值：" + bookChild.getStringValue());
                    }
                }
            }
        } catch (DocumentException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return param;
    }



    public static void main(String[] args) {
        readXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<service>" +
                "<SYS_HEAD>" +
                "<SvcCd>30220002</SvcCd>" +
                "<ScnCd>01</ScnCd>" +
                "<BsnSrINo>GMID20200618000000000001</BsnSrINo>" +
                "<TxnlnttDt>20200618</TxnlnttDt>" +
                "<TxnlnttTm>080000</TxnlnttTm>" +
                "<CnsmrSysNo>MiD</CnsmrSysNo>" +
                "<CnsmrSysSrINo>CMID20200618000000000001</CnsmrSysSrINo>" +
                "</SYS_HEAD>" +
                "<APP_HEAD>" +
                "<LglPrsnCd>666</LglPrsnCd>" +
                "</APP_HEAD>" +
                "<BODY>" +
                "<Type>系统类型</Type>" +
                "<Cdate>调用时间</Cdate>" +
                "<CertificateNum>权利证书流水号</CertificateNum>" +
                "<SerialNumList>出账流水号列表</SerialNumList>" +
                "<CertificateTitleNum>权利证书号码</CertificateTitleNum>" +
                "<BusinessType>业务类型</BusinessType>" +
                "<Mortgagee>抵押权利人</Mortgagee>" +
                "<MortgageeNum>抵押权利人证件号</MortgageeNum>" +
                "<MortgageeArea>登记地区</MortgageeArea>" +
                "<LongJson>长json串</LongJson>" +
                "<EXTFIELD1>备用字段一</EXTFIELD1>" +
                "<EXTFIELD2>备用字段二</EXTFIELD2>" +
                "<EXTFIELD3>备用字段三</EXTFIELD3>" +
                "<EXTFIELD4>备用字段四</EXTFIELD4>" +
                "<EXTFIELD5>备用字段五</EXTFIELD5>" +
                "<EXTFIELD6>备用字段六</EXTFIELD6>" +
                "<EXTFIELD7>备用字段七</EXTFIELD7>" +
                "<EXTFIELD8>备用字段八</EXTFIELD8>" +
                "<EXTFIELD9>备用字段九</EXTFIELD9>" +
                "</BODY>" +
                "</service>");
    }
}


