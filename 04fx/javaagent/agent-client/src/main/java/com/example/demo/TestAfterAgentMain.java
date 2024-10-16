//package com.example.demo;
//
//import com.sun.tools.attach.*;
//
//import java.io.IOException;
//import java.util.List;
//
///**
// * @Auther: hqlv
// * @Date: 2022/9/15 00:19
// * @Description:
// */
//public class TestAfterAgentMain {
//
//    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
//        //获取当前系统中所有 运行中的 虚拟机
//        System.out.println("running JVM start ");
//        List<VirtualMachineDescriptor> list = VirtualMachine.list();
//        for (VirtualMachineDescriptor vmd : list) {
//            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
//            //然后加载 agent.jar 发送给该虚拟机
//            System.out.println(vmd.displayName());
//            if (vmd.displayName().endsWith("com.example.demo.TestAfterAgentMain")) {
//                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
//                virtualMachine.loadAgent("C:\\temp\\agent.jar");
//                virtualMachine.detach();
//            }
//        }
//    }
//
//}
