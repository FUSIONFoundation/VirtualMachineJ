package org.fusion.vm.core;


public class Stack {

    private int top = -1;
    private StackItem[] items;

    public Stack(int capacity) {
        items = new StackItem[capacity];
    }

    void clear() {
        top = -1;
    }

    public void push(StackItem item) throws VMException {
        if (top == items.length - 1) {
            throwException();
        }
        items[++top] = item;
    }

    public StackItem pop() throws VMException {
        if (top == -1) {
            throwException();
        }
        return items[top--];
    }

    StackItem get(int index) throws VMException {
        if (top - index < 0 || index < 0) {
            throwException();
        }
        return items[top - index];
    }

    void set(int index, StackItem item) throws VMException {
        if (top - index < 0 || index < 0) {
            throwException();
        }
        items[top - index] = item;
    }

    private void throwException() throws VMException {
        throw new VMException(VMException.ExceptionType.STACK_OVERFLOW);
    }

}
