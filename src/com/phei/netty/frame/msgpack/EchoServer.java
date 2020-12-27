package com.phei.netty.frame.msgpack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: shanghang
 * @Project:nettyStudy
 * @description:netty服务端-分隔符解码器-解决粘包
 * @Date: 2020.12.26 15:25
 **/
public class EchoServer {
    public static void main(String[] args) {
        int port = 8080;
        if(args!=null && args.length>0){
            try{
                port = Integer.parseInt(args[0]);
            }catch (Exception e){

            }
        }
        new EchoServer().bind(port);
    }

    EchoServer(){

    }

    private void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100)
                .childHandler(new ChildChannelHandler());
        try {
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            //等待服务器监听端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

//        @Override
//        protected void initChannel(SocketChannel ch) throws Exception {
//            //创建分割词
//            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
//            ch.pipeline().addLast(new StringDecoder());
//            ch.pipeline().addLast(new EchoServerHandler());
//        }

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            //创建分割词
            ch.pipeline().addLast("msgpack decoder",new MsgPackDecoder());
            ch.pipeline().addLast("msgpack encoder",new MsgPackEncoder());
            ch.pipeline().addLast(new EchoServerHandler());
        }
    }
}
