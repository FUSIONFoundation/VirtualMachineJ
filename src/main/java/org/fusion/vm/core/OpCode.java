package org.fusion.vm.core;

/// 32 bytes for each stack item
public enum OpCode {


    // For optimization
    NOP(0x00), // not do anything

    //Flow control
    JMP(0x10),      // Always jump to the address which is kept in top item of stack
    JMPIF(0x11),    // When second item of stack is true, jump to the address which is kept in the top item of stack
    JMPIFNOT(0x12), // When second item of stack is false, jump to the address which is kept in the top item of stack
    CALL(0x13),     // Push PC point to context call stack and jump to the address which is kept in the top item of stack
    RET(0x14),      // Pop PC point from context call stack, if stack is empty, then set the vm state to halt
    SYSCALL(0x15),  // Call system function, function type is in top item of stack

    //Memory
    MLOAD(0x20),  // load an item from memory to stack, top item of stack is the address
    MSTORE(0x21), // save an item to memory, top item of stack is data, second item is the address

    //Bitwise logic
    AND(0x30),      // Push the result of top item AND second item to top of stack
    OR(0x31),       // Push the result of top item OR second item to top of stack
    XOR(0x32),      // Push the result of top item XOR second item to top of stack
    NOT(0x33),      // Push the result of NOT of top item to top of stack
    BYTE(0x34),

    //Arithmetic
    INC(0x40),  // top = top1++
    DEC(0x41),  // top = top1--;
    NEG(0x42),  // top = -top1
    ABS(0x43),  // top = abs(top1)
    ADD(0x44),  // top = top2 + top1
    SUB(0x45),  // top = top2 - top1
    MUL(0x46),  // top = top2 * top1
    DIV(0x47),  // top = top2 / top1
    MOD(0x48),  // top = top2 % top1
    EXP(0x49),  // top = top2 ^ top1
    MAX(0x4a),  // top = top2 > top1 ? top2 : top1
    MIN(0x4b),  // top = top2 < top1 ? top2 : top1
    EQ(0x4c),   // top = top2 == top1
    LT(0x4d),   // top = top2 < top1
    GT(0x4e),   // top = top2 > top1
    LTE(0x4f),  // top = top2 <= top1
    GTE(0x50),  // top = top2 >= top1

    // Constants
    PUSH1(0x60), // push 1 byte to stack
    PUSH2(0x61), // push 2 bytes to stack
    PUSH3(0x62), // push 3 bytes to stack
    PUSH4(0x63), // push 4 bytes to stack
    PUSH5(0x64), // push 5 bytes to stack
    PUSH6(0x65), // push 6 bytes to stack
    PUSH7(0x66), // push 7 bytes to stack
    PUSH8(0x67), // push 8 bytes to stack
    PUSH9(0x68), // push 9 bytes to stack
    PUSH10(0x69), // push 10 bytes to stack
    PUSH11(0x6a), // push 11 bytes to stack
    PUSH12(0x6b), // push 12 bytes to stack
    PUSH13(0x6c), // push 13 bytes to stack
    PUSH14(0x6d), // push 14 bytes to stack
    PUSH15(0x6e), // push 15 bytes to stack
    PUSH16(0x6f), // push 16 bytes to stack

    PUSH17(0x70), // push 17 bytes to stack
    PUSH18(0x71), // push 18 bytes to stack
    PUSH19(0x72), // push 19 bytes to stack
    PUSH20(0x73), // push 20 bytes to stack
    PUSH21(0x74), // push 21 bytes to stack
    PUSH22(0x75), // push 22 bytes to stack
    PUSH23(0x76), // push 23 bytes to stack
    PUSH24(0x77), // push 24 bytes to stack
    PUSH25(0x78), // push 25 bytes to stack
    PUSH26(0x79), // push 26 bytes to stack
    PUSH27(0x7a), // push 27 bytes to stack
    PUSH28(0x7b), // push 28 bytes to stack
    PUSH29(0x7c), // push 29 bytes to stack
    PUSH30(0x7d), // push 30 bytes to stack
    PUSH31(0x7e), // push 31 bytes to stack
    PUSH32(0x7f), // push 32 bytes to stack


    //Stack
    POP(0x80),      // Remove the top item in stack

    DUP1(0x90),     // Copy the second item in stack to top
    DUP2(0x91),     // Copy the third item in stack to top
    DUP3(0x92),     // Copy the fourth item in stack to top
    DUP4(0x93),     // Copy the fifth item in stack to top
    DUP5(0x94),     // Copy the sixth item in stack to top
    DUP6(0x95),     // Copy the seventh item in stack to top
    DUP7(0x96),     // Copy the eighth item in stack to top
    DUP8(0x97),     // Copy the ninth item in stack to top
    DUP9(0x98),     // Copy the tenth item in stack to top
    DUP10(0x99),    // Copy the eleventh item in stack to top
    DUP11(0x9a),    // Copy the twelfth item in stack to top
    DUP12(0x9b),    // Copy the thirteenth item in stack to top
    DUP13(0x9c),    // Copy the fourteenth item in stack to top
    DUP14(0x9d),    // Copy the fifteenth item in stack to top
    DUP15(0x9e),    // Copy the sixteenth item in stack to top
    DUP16(0x9f),    // Copy the seventeenth item in stack to top

    SWAP1(0xa0),    // Exchange top item of stack with second item in stack
    SWAP2(0xa1),    // Exchange top item of stack with third item in stack
    SWAP3(0xa2),    // Exchange top item of stack with fourth item in stack
    SWAP4(0xa3),    // Exchange top item of stack with fifth item in stack
    SWAP5(0xa4),    // Exchange top item of stack with sixth item in stack
    SWAP6(0xa5),    // Exchange top item of stack with seventh item in stack
    SWAP7(0xa6),    // Exchange top item of stack with eighth item in stack
    SWAP8(0xa7),    // Exchange top item of stack with ninth item in stack
    SWAP9(0xa8),    // Exchange top item of stack with tenth item in stack
    SWAP10(0xa9),   // Exchange top item of stack with eleventh item in stack
    SWAP11(0xaa),   // Exchange top item of stack with twelfth item in stack
    SWAP12(0xab),   // Exchange top item of stack with thirteenth item in stack
    SWAP13(0xac),   // Exchange top item of stack with fourteenth item in stack
    SWAP14(0xad),   // Exchange top item of stack with fifteenth item in stack
    SWAP15(0xae),   // Exchange top item of stack with sixteenth item in stack
    SWAP16(0xaf);   // Exchange top item of stack with seventeenth item in stack


    //0xb0 - 0xff system call function


    private byte value;

    public byte getValue() {
        return value;
    }

    OpCode(int value) {
        this.value = (byte) value;
    }

    private static final OpCode[] CODE_LIST = new OpCode[256];

    static {
        for (OpCode code : OpCode.values()) {
            CODE_LIST[code.value & 0xFF] = code;
        }
    }

    public static OpCode fromByte(byte opValue) {
        return CODE_LIST[opValue & 0xff];
    }
}
