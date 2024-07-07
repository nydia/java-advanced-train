package com.nydia.memorydb.calcite;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.calcite.schema.Function;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;

import java.util.Map;

/**
 * Author : j.fan
 * Date   : 2024/7/3
 * Desc   :
 */
public class MemorySchema extends AbstractSchema {

    private Map<String, Table> tableMap = Maps.newHashMap();
    private Multimap<String, Function> functionMap = LinkedListMultimap.create();

    public MemorySchema(){
        MemoryTable dual = new MemoryTable(Lists.newArrayList(), Lists.newArrayList());
        tableMap.put("dual", dual);
    }

    public void addFunction(String name,Function function) { functionMap.put(name, function);}

    @Override
    public Map<String, Table> getTableMap(){
        return tableMap;
    }

    @Override
    public Multimap<String, Function> getFunctionMultimap() {
        return functionMap;
    }

}

