package com.posppay.newpay.modules.sdk.pingan.util;

import com.posppay.newpay.modules.sdk.pingan.constant.PinganConstant;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XML的工具方法
 * @author MaoNing
 * @date 2019/4/15
 */
@Slf4j
public class XmlUtils {

    /**UTF-8字符集*/
    private static final String ENCODING_UTF_8 = "UTF-8";
    /**XML数据保护表达式*/
    private static final String CDATA_STRING_LEFT = "<![CDATA[";
    private static final String CDATA_STRING_RIGHT = "]]>";



    /**
     * 将javabean转换为XML格式字符串
     * @param clazz  javabean类
     * @param bean    要转换的实例对象
     * @param fragment  true:表示不创建XML文件头信息，false:表示创建XML文件头信息,即<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * @return  String
     */
    public static String parseBeanToXml(Class clazz,Object bean,boolean fragment) throws IOException, XMLStreamException {
        log.info("开始实体类转XML");
        log.info("实体类：{}",bean.toString());
        StringWriter writer = new StringWriter();
        XMLStreamWriter streamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
        XMLStreamWriter cdataStreamWriter = (XMLStreamWriter) Proxy.newProxyInstance(
                streamWriter.getClass().getClassLoader(),
                streamWriter.getClass().getInterfaces(),
                new ProtectDataHandler(streamWriter)
        );
        String xml = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
            marshaller.setProperty(Marshaller.JAXB_ENCODING,ENCODING_UTF_8);
            marshaller.marshal(bean, cdataStreamWriter);
            xml = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }finally {
            cdataStreamWriter.flush();
            streamWriter.flush();
            writer.flush();
            cdataStreamWriter.close();
            streamWriter.close();
            writer.close();
        }
        log.info("XML:{}",xml);
        return xml;
    }


    /**
     * 将XML转换为javabean
     * @param clazz   要转换的javabean
     * @param xml    XML格式字符串
     * @param <T>    泛型，根据传入参数的不同返回不同的参数
     * @return   T
     */
    public static <T> T parseXmlToBean(Class<T> clazz,String xml){
        log.info("开始XML转实体类");
        log.info("xml:{}",xml);
        T t = null;
        StringReader reader = new StringReader(xml);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            reader.close();
        }
        return t;
    }

    /**
     * 当XML节点需要<![CDATA[]]>时通过在javabean字段加上注解@XmlJavaTypeAdapter指定此类即可
     */
    public static class ProtectDataAdapter extends XmlAdapter<String,String> {
        @Override
        public String unmarshal(String v) throws Exception {
            return v;
        }

        @Override
        public String marshal(String v) throws Exception {
            return new StringBuffer(CDATA_STRING_LEFT).append(v).append(CDATA_STRING_RIGHT).toString();
        }
    }


    /**
     * 通过动态代理XMLStreamWriter解决写入xml时<![CDATA[xxx]]>被强制转变为&lt;![CDATA[xxx]]&gt;的问题
     */
    public static class ProtectDataHandler implements InvocationHandler{

        private static  Method gWriteCharactersMethod = null;
        static {
            try {
                gWriteCharactersMethod = XMLStreamWriter.class.getDeclaredMethod("writeCharacters", String.class);
            }catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        private XMLStreamWriter writer;

        public ProtectDataHandler(XMLStreamWriter writer) {
            this.writer = writer;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (gWriteCharactersMethod.getName().equals(method.getName())){
                String text="";
                if (args[0]instanceof char[]){
                    text=new String((char[]) args[0]);
                }else {
                    text=(String)args[0];
                }
//                text = new String((char[]) args[0]);
                if (text != null && text.startsWith(CDATA_STRING_LEFT) && text.endsWith(CDATA_STRING_RIGHT)){
                    writer.writeCData(text.substring(CDATA_STRING_LEFT.length(),text.length()-CDATA_STRING_RIGHT.length()));
                    return null;
                }
            }
            return method.invoke(writer,args);
        }
    }

    /**
     * XML转map
     * @param xmlBytes
     * @param charset
     * @return
     * @throws Exception
     */
    public static Map<String, String> toMap(byte[] xmlBytes, String charset) throws Exception {
         charset="UTF8";
        SAXReader reader = new SAXReader(false);
        InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
        source.setEncoding(charset);
        Document doc = reader.read(source);
        Map<String, String> params = XmlUtils.toMap(doc.getRootElement());
        return params;
    }

    /**
     * 转MAP
     * @param element
     * @return
     */
    public static Map<String, String> toMap(Element element){
        Map<String, String> rest = new HashMap<String, String>(16);
        List<Element> els = element.elements();
        for(Element el : els){
            rest.put(el.getName().toLowerCase(), el.getText().trim());
        }
        return rest;
    }
/*    public static String parseXML(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">" + parameters.get(k) + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }


    public static String toXml(Map<String, String> params){
        StringBuilder buf = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        buf.append("<xml>");
        for(String key : keys){
            buf.append("<").append(key).append(">");
            buf.append("<![CDATA[").append(params.get(key)).append("]]>");
            buf.append("</").append(key).append(">\n");
        }
        buf.append("</xml>");
        return buf.toString();
    }*/


    public static void main(String[] args) throws Exception {
        String data = "<xml><appid><![CDATA[wxa01b47d4de98c1e9]]></appid>\n" +
                "<charset><![CDATA[UTF-8]]></charset>\n" +
                "<code><![CDATA[04195_1211037]]></code>\n" +
                "<err_code><![CDATA[USERPAYING]]></err_code>\n" +
                "<err_msg><![CDATA[等待用户输入支付密码]]></err_msg>\n" +
                "<mch_id><![CDATA[530560041866]]></mch_id>\n" +
                "<need_query><![CDATA[Y]]></need_query>\n" +
                "<nonce_str><![CDATA[2EFzDB3ih881RYcs]]></nonce_str>\n" +
                "<result_code><![CDATA[1]]></result_code>\n" +
                "<sign><![CDATA[FA583B962819ED8E4C2CD08B7E30F80B]]></sign>\n" +
                "<sign_agentno><![CDATA[530530041352]]></sign_agentno>\n" +
                "<sign_type><![CDATA[MD5]]></sign_type>\n" +
                "<status><![CDATA[0]]></status>\n" +
                "<transaction_id><![CDATA[530560041866201906258091263869]]></transaction_id>\n" +
                "<version><![CDATA[2.0]]></version>\n" +
                "</xml>";
        Map<String,String> param = XmlUtils.toMap(data.getBytes(), PinganConstant.DEFAULT_CHARSET);
    }
}

