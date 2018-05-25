package com.cn.oddcn.entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class StructEntity {
    public String structName;
    public Map<String, String> structKeyValue;

    public StructEntity(String structName) {
        this.structName = structName;
        structKeyValue = new LinkedHashMap<>();
    }
}
