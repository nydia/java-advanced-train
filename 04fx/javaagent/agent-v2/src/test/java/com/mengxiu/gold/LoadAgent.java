package com.mengxiu.gold;

import net.bytebuddy.agent.VirtualMachine;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/2/21 23:48
 * @Description: LoadAgent
 */
public class LoadAgent {

    public static void main(String[] args) throws Exception {
        try {
            VirtualMachine vm = VirtualMachine.ForOpenJ9.attach("210568");
            vm.loadAgent("D:\\workspace\\idea-tech\\gold\\gold-server\\gold-apm-agent\\target\\glod-apm-agent.jar", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
