package com.github.fartherp.framework.remoting.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhjie.zhang on 2019/3/22.
 */

public class MinStack {

    private List<Integer> elementData = new ArrayList<>();
    private List<Integer> minData = new ArrayList<>();

    public MinStack() {
    }

    public void push(int data) throws Exception {
        if(elementData.size()>=Integer.MAX_VALUE){
            throw new Exception("超过容器最大范围，不允许放入，请扩容再使用");
        }else {
            elementData.add(data);
            if(minData.size()==0){
                minData.add(0);
            }else {
                if(elementData.get(minData.size()-1)>data){
                    minData.add(elementData.size()-1);
                }
            }
        }
        System.out.println(elementData);
        System.out.println(minData);
    }
    public int pop()throws Exception{
        if(elementData.size()==0){
            throw new Exception("栈为空");
        }else {
            int popData = elementData.get(elementData.size()-1);
            if(minData.get(minData.size()-1)==(elementData.size()-1)){
                minData.remove(minData.get(minData.size()-1));
                elementData.remove(elementData.size()-1);
                return popData;
            }else {
                elementData.remove(elementData.size()-1);
                return popData;
            }
        }
    }

    public int getMinStack() throws Exception {
        if(minData.size()==0){
            throw new Exception("最小值栈为空");
        }
        return elementData.get(minData.size()-1);
    }

    public static void main(String[] args)throws Exception  {
        MinStack minStack = new MinStack();
        minStack.push(2);
        minStack.push(1);
        minStack.push(1);
        minStack.push(1);
        System.out.println(minStack.getMinStack());
    }

}
