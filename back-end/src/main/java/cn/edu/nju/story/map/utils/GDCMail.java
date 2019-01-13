package cn.edu.nju.story.map.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuan
 * @create 2018-10-18 00:22
 **/
public class GDCMail {


    // TODO 2018-10-18 缺少一个excel解析工具
    public static void main(String[]args){

        List<User> users = new ArrayList<>();

//        users.add(new User("神璇哥", "mf1832062@smail.nju.edu.cn"));
//        users.add(new User("迪璇","1533704796@qq.com"));


        users.add(new User("惠学洁", "leohxj@gmail.com"));
        users.add(new User("姜枫","jeff.jf@alibaba-inc.com"));
        users.add(new User("赵东禹", "json.zdy@alibaba-inc.com"));
        users.add(new User("石天鑫", "shitianxin@gmail.com"));
        users.add(new User("黄涛", "ht.anglenx@gmail.com"));
        users.add(new User("王之琳", "i@sh.gg"));
        users.add(new User("秦", "joan_qin@126.com"));
        users.add(new User("徐晟峰", "hi@xusf.xyz"));
        users.add(new User("王成席", "962707144@qq.com"));
        users.add(new User("卢元晨", "luyuanchen@migu.cn"));

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            //获取模板html文档
            document = reader.read(GDCMail.class.getResource("../CDGInvitation.html").getPath());


            Element root = document.getRootElement();
            Element name = getNodes(root,"id","receiveName");

            for(User user : users) {
                name.setText(user.username + "前辈：");

                String str = document.asXML();

                MailSender mailSender = new MailSender.Builder()
                        .debug("true")
                        .subject("GCD in NJU Coach Invitation(女性编程日南大行教练邀请)")
                        .auth("true")
                        .host("smtp.qq.com")
                        .fromPerson("CGC南大教练管理员")
                        .fromAddress("1533704796@qq.com")
                        .fromPassword("0Huang&0Zhong")
                        .toAddress(user.email)
                        .mailContent(str)
                        .enableSSL("true")
                        .protocol("smtp")
                        .port("465")
                        .build();
                mailSender.send();
            }

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

    @Data
    @AllArgsConstructor
    static class User{

        private String username;
        private String email;


    }






}
