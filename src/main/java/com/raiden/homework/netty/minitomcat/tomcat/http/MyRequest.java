package com.raiden.homework.netty.minitomcat.tomcat.http;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Author: Raiden
 * Date: 2019/6/27
 */
@Data
@NoArgsConstructor
public class MyRequest {
    String method;
    String url;

    public MyRequest(InputStream inputStream) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            String s = objectInputStream.readUTF();
            String[] arr = s.split("\\s");
            method = arr[0];
            url = arr[1].split("\\?")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
