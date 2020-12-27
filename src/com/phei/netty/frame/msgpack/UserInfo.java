package com.phei.netty.frame.msgpack;

import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author shanghang
 * @title: User
 * @projectName nettyStudy
 * @description: 序列化demo,msgPack pojo类必须实现Serializable和messagez注解
 * @date 2020.12.27-15:46
 */
@Data
@Message
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;

    private int userId;

    public UserInfo buildUserName(String userName){
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserId(int userId){
        this.userId = userId;
        return this;
    }

    public byte[] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        return getBytes(buffer);
    }

    public byte[] codeC(ByteBuffer buffer){
        buffer.clear();
        return getBytes(buffer);
    }

    private byte[] getBytes(ByteBuffer buffer) {
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
