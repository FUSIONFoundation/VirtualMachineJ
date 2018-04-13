package org.fusion.vm;

import org.fusion.vm.core.Context;
import org.fusion.vm.core.StackItem;

public interface SystemContext {

    int getLevel();

    Context getContext();

    int getGas();

    boolean transfer(StackItem from, StackItem to, StackItem value, StackItem[] data);

    byte[] balanceOf(StackItem address);

    byte[] getSender();

    byte[] getReceiver();

    byte[] getSendValue();

    byte[] getTimestamp();

    byte[] getBlockHash();

    long getBlockNumber();

    long getDiff();

    StackItem load(StackItem address);

    void save(StackItem address, StackItem item);

    void flush();

    SystemContext buildChildContext(int gas, StackItem address);
}
