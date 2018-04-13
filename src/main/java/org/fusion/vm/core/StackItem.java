package org.fusion.vm.core;


import java.math.BigInteger;

public class StackItem {

    // 32 bytes for each stack item
    public static final int DATA_LENGTH = 32;
    private static final StackItem NUMBER_ZERO = new StackItem(0);
    private static final StackItem NUMBER_ONE = new StackItem(1);
    private static final BigInteger BIG_NUMBER_LEN = BigInteger.valueOf(32);
    private byte[] data = new byte[DATA_LENGTH];

    public StackItem(boolean value) {
        this((byte) (value ? 1 : 0));
    }

    public StackItem(byte value) {
        data[DATA_LENGTH - 1] = value;
    }

    public StackItem(int value) {
        data[DATA_LENGTH - 4] = (byte) ((value >> 24) & 0xff);
        data[DATA_LENGTH - 3] = (byte) ((value >> 16) & 0xff);
        data[DATA_LENGTH - 2] = (byte) ((value >> 8) & 0xff);
        data[DATA_LENGTH - 1] = (byte) (value & 0xff);
    }

    public StackItem(long value) {
        data[DATA_LENGTH - 8] = (byte) ((value >> 56) & 0xff);
        data[DATA_LENGTH - 7] = (byte) ((value >> 48) & 0xff);
        data[DATA_LENGTH - 6] = (byte) ((value >> 40) & 0xff);
        data[DATA_LENGTH - 5] = (byte) ((value >> 32) & 0xff);
        data[DATA_LENGTH - 4] = (byte) ((value >> 24) & 0xff);
        data[DATA_LENGTH - 3] = (byte) ((value >> 16) & 0xff);
        data[DATA_LENGTH - 2] = (byte) ((value >> 8) & 0xff);
        data[DATA_LENGTH - 1] = (byte) (value & 0xff);
    }

    public StackItem(byte[] data) throws VMException {

        if (data == null) {
            data = new byte[DATA_LENGTH];
        }

        if (data.length == DATA_LENGTH)
            this.data = data;
        else if (data.length < DATA_LENGTH) {
            System.arraycopy(data, 0, this.data, DATA_LENGTH - data.length, data.length);
        } else {
            throw new VMException(VMException.ExceptionType.ITEM_DATA_LEN_ERROR);
        }
    }

    public StackItem deepCopy() throws VMException {
        byte[] newData = new byte[data.length];
        System.arraycopy(data, 0, newData, 0, newData.length);
        return new StackItem(newData);
    }

    public byte byteValue() {
        return data[DATA_LENGTH - 1];
    }

    public int intVal() {
        return getBigInteger().intValue();
    }

    public boolean isZero() {
        for (byte tmp : data) {
            if (tmp != 0) return false;
        }
        return true;
    }

    public boolean isFalse() {
        return isZero();
    }

    public boolean isTrue() {
        return !isFalse();
    }

    public int compareTo(StackItem value) {
        return getBigInteger().compareTo(value.getBigInteger());
    }

    public void inc() {
        add(NUMBER_ONE);
    }

    public void dec() {
        sub(NUMBER_ONE);
    }

    public void neg() {
        setResult(getBigInteger().negate());
    }

    public void abs() {
        setResult(getBigInteger().abs());
    }

    public void add(StackItem value) {
        setResult(getBigInteger().add(value.getBigInteger()));
    }

    public void sub(StackItem value) {
        setResult(getBigInteger().subtract(value.getBigInteger()));
    }

    public void mul(StackItem value) {
        setResult(getBigInteger().multiply(value.getBigInteger()));
    }

    public void div(StackItem value) {
        setResult(getBigInteger().divide(value.getBigInteger()));
    }

    public void mod(StackItem value) {
        setResult(getBigInteger().mod(value.getBigInteger()));
    }

    public void exp(StackItem value) {
        setResult(getBigInteger().pow(value.getBigInteger().intValue()));
    }

    public void and(StackItem value) {
        setResult(getBigInteger().and(value.getBigInteger()));
    }

    public void or(StackItem value) {
        setResult(getBigInteger().or(value.getBigInteger()));
    }

    public void xor(StackItem value) {
        setResult(getBigInteger().xor(value.getBigInteger()));
    }

    public void not() {
        setResult(getBigInteger().not());
    }

    public void doByte(StackItem value) {
        if (value.getBigInteger().compareTo(BIG_NUMBER_LEN) < 0) {
            int intVal = value.intVal();
            byte temp = data[intVal];
            this.add(NUMBER_ZERO);
            this.data[DATA_LENGTH - 1] = temp;
        } else {
            this.data = new byte[DATA_LENGTH];
        }
    }

    public BigInteger getBigInteger() {
        return new BigInteger(data);
    }

    public byte[] getData() {
        return data;
    }

    private void setResult(BigInteger result) {
        byte[] data = result.toByteArray();

        if (data.length == DATA_LENGTH)
            this.data = data;
        else if (data.length < DATA_LENGTH) {
            System.arraycopy(data, 0, this.data, DATA_LENGTH - data.length, data.length);
        } else {
            System.arraycopy(data, data.length - DATA_LENGTH, this.data, 0, 32);
        }
    }
}
