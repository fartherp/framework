/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.common.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A json tools for the gson deal of the google.
 * @author CK
 * @date 2015/8/7
 */
public class JsonUtil {
    /**
     * the {@code String} convert the {@code T} class message.
     * @param json the json {@code String}
     * @param clazz Class
     * @param <T> Class
     * @return Class
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson().fromJson(json, clazz);
    }

    public static String toJson(Object obj, String... ignoreProperties) {
        return gson(ignoreProperties).toJson(obj);
    }

    public static Gson gson(String... ignoreProperties) {
        String datePattern = DateUtil.YYYY_MM_DD_HH_MM_SS;
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat(datePattern);
        ExclusionStrategy exclusionStrategy = getExclusionStrategy(ignoreProperties);
        if (exclusionStrategy != null) {
            builder.setExclusionStrategies(exclusionStrategy);
        }
        return builder.create();
    }

    public static Gson gson1(String... ignoreProperties) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new DateSerializer());
        builder.setDateFormat(DateFormat.LONG);
        ExclusionStrategy exclusionStrategy = getExclusionStrategy(ignoreProperties);
        if (exclusionStrategy != null) {
            builder.setExclusionStrategies(exclusionStrategy);
        }
        return builder.create();
    }

    public static ExclusionStrategy getExclusionStrategy(String... ignoreProperties) {
        ExclusionStrategy exclusionStrategy = null;
        final List<String> ignoreList = (ignoreProperties != null && ignoreProperties.length > 0)
			? Arrays.asList(ignoreProperties) : null;
        if (ignoreList != null) {
            exclusionStrategy = new ExclusionStrategy() {

            	@Override
                public boolean shouldSkipField(FieldAttributes fa) {
                    return ignoreList.contains(fa.getName());
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            };
        }
        return exclusionStrategy;
    }

    /**
     * the time stamp convert the {@link Date} of java
     */
    public static class DateDeserializer implements JsonDeserializer<Date> {

    	@Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    }

    /**
     * the {@link Date} of java convert the time stamp
     */
    public static class DateSerializer implements JsonSerializer<Date> {

    	@Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }
}
