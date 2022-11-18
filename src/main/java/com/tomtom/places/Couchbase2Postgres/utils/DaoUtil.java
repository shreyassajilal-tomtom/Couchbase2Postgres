package com.tomtom.places.Couchbase2Postgres.utils;


import com.couchbase.client.java.document.json.JsonArray;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.tomtom.places.optimus.datastore.dto.ComplexViewResult;
import org.apache.commons.collections.CollectionUtils;
import org.lightcouch.Page;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DaoUtil {
    @SuppressWarnings("serial")
    public static final Type STRING_LIST_TYPE = new TypeToken<List<String>>() {}.getType();

    public static Object[] createKey(Object... args) {
        return args;
    }

    public static Object[] createKeyWithEmptyArrayAsLastElement(Object... args) {
        ArrayList<Object> list = Lists.newArrayList(args);
        list.add(new Object[] {});
        return list.toArray();
    }

    public static Object[] createNotNullKeys(boolean addEmptyArrayAsLastElement, Object... args) {
        ArrayList<Object> list = Lists.newArrayList();
        for (Object key : args) {
            if (null != key) {
                list.add(key);
            } else {
                break;
            }
        }

        if (addEmptyArrayAsLastElement) {
            list.add(new Object[] {});
        }

        return list.toArray();
    }

    public static <T> JsonArray convertListToJsonArray(List<T> list) {
        JsonArray jsonArray = JsonArray.create();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(e -> jsonArray.add(String.valueOf(e)));
        }
        return jsonArray;
    }

    public static List<String> getIdsFromPage(Page<ComplexViewResult> page) {
        List<ComplexViewResult> results = page.getResultList();
        if (CollectionUtils.isNotEmpty(results)) {
            return results.stream().map(ComplexViewResult::getId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static <T> Set<T> getIndexKeysFromPage(Page<ComplexViewResult> page, int index) {
        return page.getResultList().stream().map(viewRow -> {
            T keyAtIndex = getKeyAtIndex(viewRow, index);
            return keyAtIndex;
        }).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getKeyAtIndex(ComplexViewResult viewRow, int index) {
        JsonArray emittedkeys = (JsonArray)viewRow.getKey();
        return (T)emittedkeys.get(index);
    }
}
