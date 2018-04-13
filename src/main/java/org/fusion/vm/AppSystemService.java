package org.fusion.vm;

import org.fusion.vm.core.*;

public class AppSystemService implements SystemService {


    private SystemContext systemContext;
    private HttpUtils httpUtils;

    public AppSystemService(SystemContext systemContext) {
        this.systemContext = systemContext;
        this.httpUtils = new HttpUtils();
    }

    @Override
    public State systemCall(byte funcValue, Stack stack, VirtualMachine vm) throws VMException {
        AppSystemFunction func = AppSystemFunction.fromByte(funcValue);
        if (func == null || !(vm instanceof AppVirtualMachine)) {
            throw new VMException(VMException.ExceptionType.SYSCALL_ERROR);
        }
        return systemCall(func, stack, (AppVirtualMachine) vm);
    }

    private State systemCall(AppSystemFunction func, Stack stack, AppVirtualMachine vm) throws VMException {
        vm.useGas(vm.getCalculator().calcGas(func));
        State state = new State();
        switch (func) {
            case HTTP: {
                String url = getString(stack);
                StackItem item = new StackItem(httpUtils.httpGet(url));
                stack.push(item);
            }
            break;
            case JSON_GET: {
                int index = stack.pop().intVal();
                String key = getString(stack);
                StackItem item = new StackItem(httpUtils.getValue(index, key));
                stack.push(item);
            }
            break;
            case INVOKE: {
                int remainingGas = vm.getGas() - vm.getUsedGas();
                StackItem address = stack.pop();
                AppVirtualMachine newVm = new AppVirtualMachine(systemContext.buildChildContext(remainingGas, address), stack);
                vm.useGas(newVm.getUsedGas());
                state.setFlag(newVm.execute().getValue());
            }
            break;
            case TRANSFER: {
                StackItem from = stack.pop();
                StackItem to = stack.pop();
                StackItem value = stack.pop();
                StackItem[] data = getStackItems(stack);
                boolean success = systemContext.transfer(from, to, value, data);
                if (!success) {
                    state.setFlag(State.REVERT);
                }
            }
            break;
            case BALANCE: {
                StackItem address = stack.pop();
                stack.push(new StackItem(systemContext.balanceOf(address)));
            }
            break;
            case SENDER: {
                stack.push(new StackItem(systemContext.getSender()));
            }
            break;
            case RECEIVER: {
                stack.push(new StackItem(systemContext.getReceiver()));
            }
            break;
            case SEND_VALUE: {
                stack.push(new StackItem(systemContext.getSendValue()));
            }
            break;
            case BLOCK_HASH: {
                stack.push(new StackItem(systemContext.getBlockHash()));
            }
            break;
            case BLOCK_NUMBER: {
                stack.push(new StackItem(systemContext.getBlockNumber()));
            }
            break;
            case DIFF: {
                stack.push(new StackItem(systemContext.getDiff()));
            }
            break;
            case TIMESTAMP: {
                stack.push(new StackItem(systemContext.getTimestamp()));
            }
            break;
            case SLOAD: {
                StackItem address = stack.pop();
                StackItem item = systemContext.load(address);
                if (item == null) {
                    throw new AppVMException(AppVMException.ExceptionType.STORAGE_ERROR);
                } else {
                    stack.push(item);
                }
            }
            break;
            case SSTORE: {
                StackItem item = stack.pop();
                StackItem address = stack.pop();
                systemContext.save(address, item);
            }
            break;

        }
        return state;
    }

    private String getString(Stack stack) throws VMException {
        int length = stack.pop().intVal();
        byte[] data = new byte[length * StackItem.DATA_LENGTH];
        for (int i = 0; i < length; i++) {
            byte[] itemData = stack.pop().getData();
            System.arraycopy(itemData, 0, data, (length - i) * StackItem.DATA_LENGTH, itemData.length);
        }
        return new String(data);
    }

    private StackItem[] getStackItems(Stack stack) throws VMException {
        int length = stack.pop().intVal();
        StackItem[] data = new StackItem[length];
        for (int i = 0; i < length; i++) {
            data[i] = stack.pop();
        }
        return data;
    }

}
