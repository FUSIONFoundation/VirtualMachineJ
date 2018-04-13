package org.fusion.vm.core;


import java.util.HashMap;
import java.util.Map;

class Memory {


    private Map<StackItem, StackItem> store = new HashMap<>();

    void save(StackItem address, StackItem data) {
        store.put(address, data);
    }

    StackItem load(StackItem address) throws VMException {
        if (!store.containsKey(address)) {
            throw new VMException(VMException.ExceptionType.MEMORY_ERROR);
        }
        return store.get(address);
    }
}
