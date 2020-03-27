package com.posppay.newpay.modules.xposp.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private JsonUtils() {
    }

    /**
     * json转对象实例
     * <p>如果json中存在，对象中没有，则自动忽略该属性
     * <p>失败返回null
     *
     * @param json  json
     * @param clazz 类对象
     * @return 成功则返回实例，否则返回null
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        return innerJsonToObject(json, clazz);
    }

    /**
     * 对象实例转json
     * <p>如果对象属性为null，则自动忽略该属性
     * <p>失败返回null
     *
     * @param object 对象实例
     * @return 成功则返回对象的json字节数组，否则返回
     */
    public static <T> String objectToJson(T object) {
        return innerObjectToJson(MAPPER, object);
    }

    public static <T> String objectToJson(T object, JsonInclude.Include include) {
        ObjectMapper mapper = new ObjectMapper(MAPPER.getFactory())
                .setSerializationInclusion(include);
        return innerObjectToJson(mapper, object);
    }

    /**
     * 对象实例转json
     * <p>格式化输出json
     * <p>如果对象属性为null，则自动忽略该属性
     * <p>失败返回null
     *
     * @param object 对象实例
     * @return 成功则返回对象的json字节数组，否则返回
     */
    public static <T> String objectToJsonWithPretty(T object) {
        ObjectMapper mapper = new ObjectMapper(MAPPER.getFactory());
        return innerObjectToJson(mapper, object);
    }

    private static <T> T innerJsonToObject(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            log.warn("JsonToObject failed: ", e);
        }
        return null;
    }

    private static <T> String innerObjectToJson(ObjectMapper mapper, T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.info("ObjectToJson failed: ", e);
        }
        return null;
    }

}
