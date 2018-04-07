package com.zblog.common.util.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gaotianlin
 * Date: 13-7-22
 * Time: ����2:56
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtil {
    private static final Log log = LogFactory.getLog(JsonUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();
	static{
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);//�������л����ã�Ϊnull�����Բ����뵽json��
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//���ݵ����� �������Ų�����json��׼ ������ʹ��
	}
	/**
	 * ������ת����json�ַ���,���ת��ʧ���򷵻�null
	 * @author zhaoyunxiao
	 * @param o ��Ҫת��Ϊjson�Ķ���
	 * @return String ת�����json�ַ���
	 *
	 *
	 * */
    public static String write2JsonStr(Object o){
    	String jsonStr = "";
    	try {
    		jsonStr = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			log.error("write2JsonStr() exception: ",e);
		}
		return jsonStr;
    }

    /**
     * ��jsonת��Ϊ���� �������ģ��Ϊ�ڲ����������⣬���Բ�Ҫʹ���ڲ���
     * @author zhaoyunxiao
     * @param json Ҫת����json
     * @param Ҫӳ�������
     * @return ת���ɵ�Ŀ��������ת��ʧ�ܷ���null
     * */
    public static Object json2Object(String json,Class<?> clazz){
    	try {
			 return mapper.readValue(json,clazz);
		} catch (JsonParseException e) {
			log.error("json2Object() parseException: " ,e);
		} catch (JsonMappingException e) {
			log.error("json2Object() mappingException: ",e);
		} catch (IOException e) {
			log.error("json2Object() IOException: " ,e);
		}
    	return null;
    }

    /**
     * ��json�ַ���ת��ΪMap
     * @author zhaoyunxiao
     * @param  json ��Ҫת��ΪMap��json�ַ��� {}��ͷ��β��
     * @return ת�����map ���ת��ʧ�ܷ���null
     * */
    @SuppressWarnings("unchecked")
	public static Map<String,Object> json2Map(String json){
    	try {
    		if(StringUtils.isBlank(json)) {
    			return new HashMap<String,Object>() ;
    		}
			return mapper.readValue(json,Map.class);
		} catch (JsonParseException e) {
			log.error("json2Map(), �����json���ݣ� ,JsonParseException: ",e);
		} catch (JsonMappingException e) {
			log.error("json2Map(), �����json���ݣ� ,JsonMappingException: " ,e);
		} catch (IOException e) {
			log.error("json2Map(), �����json����Ϊ�� ,IOException: " ,e);
		}
   	    return new HashMap<String,Object>() ;
    }

    /**
     * ��json����ת��ΪList<Map<String,Object>> json�����ʽ[{},{}]
     * @author zhaoyunxiao
     * @param  ��Ҫת����json����
     * @return ת������б�   ���ת��ʧ�ܷ���null
     * */
    @SuppressWarnings("unchecked")
	public static List<Map<String,Object>> jsonArray2List(String jsonArray){
        try {
			return mapper.readValue(jsonArray, List.class);
		} catch (JsonParseException e) {
			log.error("jsonArray2List() exception, �쳣�ַ���: " , e);
		} catch (JsonMappingException e) {
			log.error("jsonArray2List() exception, �쳣�ַ���: " , e);
		} catch (IOException e) {
			log.error("jsonArray2List() exception",e);
		}
        return new ArrayList<Map<String,Object>>();
    }

    /**
     * ��json����ת��ΪList<Map<String,Object>> json�����ʽ[{},{}]
     * @author zhaoyunxiao
     * @param  ��Ҫת����json����
     * @return ת������б�   ���ת��ʧ�ܷ���null
     * */
    @SuppressWarnings("unchecked")
	public static List<Map<String,Object>> jsonArray2List(String jsonArray,String keyword){
        try {
			return mapper.readValue(jsonArray, List.class);
		} catch (JsonParseException e) {
			log.error("JsonUtil exception, keyword: , �쳣�ַ���: " , e);
		} catch (JsonMappingException e) {
			log.error("JsonUtil exception, keyword: , �쳣�ַ���: " , e);
		} catch (IOException e) {
			log.error("JsonUtil exception",e);
		}
        return new ArrayList<Map<String,Object>>();
    }

    public static Set<?> json2ArraySet(String json, TypeReference<?> tr) {
        try {
            return mapper.readValue(json, tr);
        } catch (Exception e) {
            log.error("json2ArrayObject, JsonUtil exception, json is:" , e);
        }
        return null;
    }

    public static int obj2Int(Object obj){
    	String str = obj.toString();
        int i = 0;
        try{
        	i = Integer.parseInt(str);
        }catch(Exception e){
        	log.error("����תintʧ��",e);
        }
    	return i;
    }
    public static long obj2Long(Object obj){
    	String str = obj.toString();
        long l = 0;
        try{
        	l = Long.parseLong(str);
        }catch(Exception e){
        	log.error("����תintʧ��",e);
        }
    	return l;
    }
 /*   public static void main(String[] args) {
    	String json = "{'name':'zhaoyunxiao','age':'12'}";
    	String jsonArray = "[{\"name\":\"zhaoyunxiao\",\"age\":\"12\"},{\"name\":\"dengzhengping\",\"age\":\"13\"}]";
    	System.out.println(json);
    	TestPerson p = (TestPerson)json2Object(json, TestPerson.class);
    	System.out.println(p.getName());
    	Map<String,Object> res = json2Map(json);
    	System.out.println(res);
    	List<Map<String,Object>> l = jsonArray2List(jsonArray);
    	System.out.println(l);

	}*/
}
