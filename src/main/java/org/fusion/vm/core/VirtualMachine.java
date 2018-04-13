package org.fusion.vm.core;

public class VirtualMachine {

    private Context context;
    private SystemService service;
    private Stack stack;
    private State state;
    private Memory memory;

    public VirtualMachine(Context context, SystemService service, Stack stack) {
        this.context = context;
        this.service = service;
        this.stack = stack;
        this.state = new State();
        this.memory = new Memory();
    }

    public State execute() {
        while (!state.hasFlag(State.HALT) && !state.hasFlag(State.REVERT)) {
            try {
                byte opCodeValue = context.getOpCodeVal();
                OpCode opCode = OpCode.fromByte(opCodeValue);
                if (opCode == null) {
                    if ((opCodeValue & 0xf0) == 0xf0) {
                        state.setFlag(service.systemCall(opCodeValue, stack, this).getValue());
                    } else {
                        throw new VMException(VMException.ExceptionType.OPCODE_ERROR);
                    }
                } else {
                    executeOp(opCode);
                }

            } catch (Exception e) {
                byte code = VMException.ExceptionType.SYSTEM_ERROR.getValue();
                if (e instanceof VMException) {
                    code = ((VMException) e).getCode();
                }
                StackItem stackItem = new StackItem(code);
                stack.clear();
                try {
                    stack.push(stackItem);
                } catch (VMException vme) {
                    //Impossible,unless person is joking, set the stack capacity to zero
                    vme.printStackTrace();
                }
                state.setFlag(State.REVERT);
            }
        }
        return state;
    }

    public void executeOp(OpCode opCode) throws VMException {
        switch (opCode) {
            case NOP: {
            }
            break;
            case JMP: {
                StackItem offset = stack.pop();
                context.setProgramCounter(offset.intVal());
            }
            break;
            case JMPIF: {
                StackItem offset = stack.pop();
                StackItem condition = stack.pop();
                if (condition.isTrue()) {
                    context.setProgramCounter(offset.intVal());
                }
            }
            break;
            case JMPIFNOT: {
                StackItem offset = stack.pop();
                StackItem condition = stack.pop();
                if (condition.isFalse()) {
                    context.setProgramCounter(offset.intVal());
                }
            }
            break;
            case CALL: {
                StackItem offset = stack.pop();
                context.call(offset.intVal());
            }
            break;
            case RET: {
                if (!context.ret()) {
                    state.setFlag(State.HALT);
                }
            }
            break;
            case SYSCALL: {
                StackItem funcVal = stack.pop();
                service.systemCall(funcVal.byteValue(), stack, this);
            }
            break;
            case MLOAD: {
                StackItem address = stack.pop();
                StackItem item = memory.load(address);
                if (item == null) {
                    throw new VMException(VMException.ExceptionType.MEMORY_ERROR);
                } else {
                    stack.push(item);
                }
            }
            break;
            case MSTORE: {
                StackItem item = stack.pop();
                StackItem address = stack.pop();
                memory.save(address, item);
            }
            break;
            case INC: {
                StackItem x1 = stack.pop();
                x1.inc();
                stack.push(x1);
            }
            break;
            case DEC: {
                StackItem x1 = stack.pop();
                x1.dec();
                stack.push(x1);
            }
            break;
            case NEG: {
                StackItem x1 = stack.pop();
                x1.neg();
                stack.push(x1);
            }
            break;
            case ABS: {
                StackItem x1 = stack.pop();
                x1.abs();
                stack.push(x1);
            }
            break;
            case ADD: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.add(x2);
                stack.push(x1);
            }
            break;
            case SUB: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.sub(x2);
                stack.push(x1);
            }
            break;
            case MUL: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.mul(x2);
                stack.push(x1);
            }
            break;
            case DIV: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.div(x2);
                stack.push(x1);
            }
            break;
            case MOD: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.mod(x2);
                stack.push(x1);
            }
            break;
            case EXP: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.exp(x2);
                stack.push(x1);
            }
            break;
            case MAX: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                StackItem value = x1.compareTo(x2) > 0 ? x1 : x2;
                stack.push(value);
            }
            break;
            case MIN: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                StackItem value = x1.compareTo(x2) < 0 ? x1 : x2;
                stack.push(value);
            }
            break;
            case EQ: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                StackItem value = new StackItem(x1.compareTo(x2) == 0);
                stack.push(value);
            }
            case LT: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                StackItem value = new StackItem(x1.compareTo(x2) < 0);
                stack.push(value);
            }
            break;
            case GT: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                StackItem value = new StackItem(x1.compareTo(x2) > 0);
                stack.push(value);
            }
            break;
            case LTE: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                StackItem value = new StackItem(x1.compareTo(x2) <= 0);
                stack.push(value);
            }
            break;
            case GTE: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                StackItem value = new StackItem(x1.compareTo(x2) >= 0);
                stack.push(value);
            }
            break;
            case AND: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.and(x2);
                stack.push(x1);
            }
            break;
            case OR: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.or(x2);
                stack.push(x1);
            }
            break;
            case XOR: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.xor(x2);
                stack.push(x1);
            }
            break;
            case NOT: {
                StackItem x1 = stack.pop();
                x1.not();
                stack.push(x1);
            }
            break;
            case BYTE: {
                StackItem x2 = stack.pop();
                StackItem x1 = stack.pop();
                x1.doByte(x2);
                stack.push(x1);
            }
            break;
            case PUSH1:
            case PUSH2:
            case PUSH3:
            case PUSH4:
            case PUSH5:
            case PUSH6:
            case PUSH7:
            case PUSH8:
            case PUSH9:
            case PUSH10:
            case PUSH11:
            case PUSH12:
            case PUSH13:
            case PUSH14:
            case PUSH15:
            case PUSH16:
            case PUSH17:
            case PUSH18:
            case PUSH19:
            case PUSH20:
            case PUSH21:
            case PUSH22:
            case PUSH23:
            case PUSH24:
            case PUSH25:
            case PUSH26:
            case PUSH27:
            case PUSH28:
            case PUSH29:
            case PUSH30:
            case PUSH31:
            case PUSH32: {
                int length = opCode.getValue() - OpCode.PUSH1.getValue() + 1;
                byte[] data = context.getData(length);
                context.addProgramCounter(length);
                StackItem value = new StackItem(data);
                stack.push(value);
            }
            break;
            case POP: {
                stack.pop();
            }
            break;
            case DUP1:
            case DUP2:
            case DUP3:
            case DUP4:
            case DUP5:
            case DUP6:
            case DUP7:
            case DUP8:
            case DUP9:
            case DUP10:
            case DUP11:
            case DUP12:
            case DUP13:
            case DUP14:
            case DUP15:
            case DUP16: {
                int index = opCode.getValue() - OpCode.DUP1.getValue() + 1;
                StackItem value = stack.get(index).deepCopy();
                stack.push(value);
            }
            break;
            case SWAP1:
            case SWAP2:
            case SWAP3:
            case SWAP4:
            case SWAP5:
            case SWAP6:
            case SWAP7:
            case SWAP8:
            case SWAP9:
            case SWAP10:
            case SWAP11:
            case SWAP12:
            case SWAP13:
            case SWAP14:
            case SWAP15:
            case SWAP16:
                int index = opCode.getValue() - OpCode.SWAP1.getValue() + 1;
                StackItem value = stack.get(index);
                StackItem top = stack.get(0);
                stack.set(index, top);
                stack.set(0, value);
                break;
        }
    }
}
