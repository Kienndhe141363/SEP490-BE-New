package com.g96.ftms.util;


import io.micrometer.common.util.StringUtils;

public final class SqlBuilderUtils {
    public static String createKeywordFilter(String keyword) {
        if (StringUtils.isNotEmpty(keyword)) {
            return "%" + keyword + "%";
        }
        return null;
    }
}