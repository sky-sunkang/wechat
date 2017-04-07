package com.sunkang.utils;

import com.sunkang.common.Constants;
import com.sunkang.test.Person;
import com.sunkang.test.Work;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 孙康
 * @date 2017/4/6
 * Describe：xml与object转换工具类
 */
public class XmlObjectUtils {

    /**
     * 节点内容加上<![CDATA[  ]]>
     */
    private static XStream xStream=new XStream(new XppDriver(){

        public HierarchicalStreamWriter createWriter(Writer out){
            return new PrettyPrintWriter(out){
                //对所有xml节点的转换都加上CDATA标记
                boolean cdata= Constants.IS_ADD_CDATA;

                @Override
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if(cdata){
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }else {
                        writer.write(text);
                    }
                }


            };
        }


    });

    /**
     * xml转换成实体类,该方法需要传入的字符串类的标签头字母大写,因为getSimpleName获取的类名大写开头
     * @param xml xml字符串
     * @param clas xml里面代表实体类的标签
     * @return 转换后的实体类
     */
    public static Object xml2Object(String xml,Class... clas ){
        //定义别名
        for(Class cla:clas){
            //getSimpleName获得不带包名的类名
            xStream.alias(cla.getSimpleName(),cla);
        }
        return xStream.fromXML(xml) ;
    }


    /**
     * 实体类转换成xml
     * @param object 要转换的对象
     * @param alias 设置类的别名
     * @return 转换后的字符串
     */
    public static String object2Xml(Object object, Map<String,Class> alias){
        if(alias!=null){
            //设置类别名
            for (Map.Entry<String,Class> entry:alias.entrySet()){
                xStream.alias(entry.getKey(),entry.getValue());
            }
        }
//        //设置成员变量为标签上的属性
//        xStream.aliasAttribute(Work.class,"id","ID");
//        //设置成员变量别名
//        xStream.aliasField("AGE",Person.class,"age");
        return xStream.toXML(object);
    }

    /**
     * 测试该工具类
     * @param args
     */
    public static void main(String[] args) {
        StringBuffer sb=new StringBuffer("");
        sb.append("<Person>");
        sb.append("<id>1</id><name>孙康</name><age>25</age>");
        sb.append("<address><string>深圳</string><string>武汉</string></address>");
        sb.append("<otherWorks>");
        sb.append("<Work>");
        sb.append("<id>1</id><name>程序员1</name>");
        sb.append("</Work>");
        sb.append("<Work>");
        sb.append("<id>2</id><name>程序员2</name>");
        sb.append("</Work>");
        sb.append("</otherWorks>");
        sb.append("<oneWork>");
        sb.append("<id>12</id><name>程序员</name>");
        sb.append("</oneWork>");
        sb.append("</Person>");
        Person person= (Person) xml2Object(sb.toString(),Person.class,Work.class);
        Map<String,Class> map=new HashMap<>();
        map.put("person",Person.class);
        map.put("item",Work.class);
        System.out.println(object2Xml(person,map));
    }
}
