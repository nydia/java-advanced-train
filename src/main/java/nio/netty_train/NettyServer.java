package nio.netty_train;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Auther: hqlv
 * @Date: 2021/5/25 23:14
 * @Description:
 */
public class NettyServer {

    public static void main(String[] args) {
        new NettyServer().bind(8080);
    }

    public void bind(int port) {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    //初始化服务端可连接队列
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChildChannelHandler());

            ChannelFuture f = serverBootstrap.bind().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new MyHandler());
        }
    }

    private class MyHandler extends ChannelInboundHandlerAdapter {

    }

}
