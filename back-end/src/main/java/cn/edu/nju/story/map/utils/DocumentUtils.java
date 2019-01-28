package cn.edu.nju.story.map.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuan
 * @create 2018-10-18 00:22
 **/
public class DocumentUtils {

    public static void main(String[]args){

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            //获取模板html文档
            document = reader.read(new ClassPathResource("static/IStoryInvitation.html").getPath());


            Element root = document.getRootElement();
            Element name = getNodes(root,"id","receiveName");
            name.setText("前辈：");

            String str = document.asXML();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取html的需要替换的数据节点
     * @param node
     * @param attrName
     * @param attrValue
     * @return
     */
    public static Element getNodes(Element node, String attrName, String attrValue) {

        final List<Attribute> listAttr = node.attributes();// 当前节点的所有属性
        for (final Attribute attr : listAttr) {// 遍历当前节点的所有属性
            final String name = attr.getName();// 属性名称
            final String value = attr.getValue();// 属性的值
            if(attrName.equals(name) && attrValue.equals(value)){
                return node;
            }
        }
        // 递归遍历当前节点所有的子节点
        final List<Element> listElement = node.elements();// 所有一级子节点的list
        for (Element e : listElement) {// 遍历所有一级子节点
            Element temp = getNodes(e,attrName,attrValue);
            // 递归
            if(temp != null){
                return temp;
            };
        }

        return null;
    }







}
