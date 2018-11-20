package com.example.zk.demo.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author xghuang
 * @date 2018/11/20
 * @time 14:01
 * @desc:
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient("119.23.240.32:2181",50000);
        System.out.println("zk client has connected...");
        String path = "/zk-book";

        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println(s+"'s children change,current childs :"+list);
            }
        });

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s+"|"+o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s+"|deleted");
            }
        });
        zkClient.createPersistent(path);
        zkClient.writeData(path,"helloworld");
        Thread.sleep(1000);
        System.out.println(zkClient.getChildren(path));
        Thread.sleep(1000);
        zkClient.createPersistent(path+"/c1");
        Thread.sleep(1000);
        zkClient.delete(path+"/c1");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);

    }
}
