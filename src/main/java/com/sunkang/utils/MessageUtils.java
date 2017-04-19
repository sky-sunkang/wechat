package com.sunkang.utils;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 孙康
 * @date 2017/4/6
 * Describe：消息的工具类
 */
public class MessageUtils {

    /**
     * 解析消息信息，并装入map中
     * @param inputStream
     * @return
     * @throws DocumentException
     */
    public static Map<String,String> parseXml(InputStream inputStream) throws DocumentException {
            Map<String,String> map=new HashMap<>();

            SAXReader saxReader=new SAXReader();
            Document document= saxReader.read(inputStream);
            //获得根节点
            Element root= document.getRootElement();
            //获得所有子节点
            List<Element> elements=root.elements();
            for(Element element:elements){
                map.put(element.getName(),element.getText());
            }
            return map;
//            String xmlStr= IOUtils.toString(is,"UTF-8");//将inputStream转换成字符串
    }
}
