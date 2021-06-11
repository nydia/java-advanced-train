package nio.netty_train;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @Auther: hqlv
 * @Date: 2021/5/25 23:14
 * @Description: Netty的单线程模式
 */
public class NettyServerForSingleThread {

    public static void main(String[] args) {
        new NettyServerForSingleThread().bind(90);
    }

    @SuppressWarnings("Duplicates")
    public void bind(int port) {
        EventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap b = new ServerBootstrap();
        b.group(group);
    }


}
