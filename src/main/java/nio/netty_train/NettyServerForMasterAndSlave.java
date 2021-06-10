package nio.netty_train;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Auther: hqlv
 * @Date: 2021/5/25 23:14
 * @Description: Netty的主从模式
 */
public class NettyServerForMasterAndSlave {

    public static void main(String[] args) {
        new NettyServerForMasterAndSlave().bind(90);
    }

    @SuppressWarnings("Duplicates")
    public void bind(int port) {

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //主从多线程模式
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChildChannelHandler());

            Channel f = serverBootstrap.bind(port).sync().channel();
            f.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }

    private class ChildChannelHandler extends ChannelInitializer<io.netty.channel.socket.SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline()
                    .addLast(new HttpServerCodec())
                    .addLast(new HttpObjectAggregator(1024 * 1024))
                    .addLast(new MyHandler());
        }
    }

    private class MyHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }


        @SuppressWarnings("Duplicates")
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            FullHttpResponse response = null;
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            try {
                String value = "hello netty";
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
                response.headers().set("Content-Type", "application/json");
                response.headers().setInt("Content-Length", response.content().readableBytes());
            } catch (Exception e) {
                System.out.println("处理出错:" + e.getMessage());
                response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            } finally {
                if (fullRequest != null) {
                    if (!HttpUtil.isKeepAlive(fullRequest)) {
                        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                    } else {
                        response.headers().set(CONNECTION, KEEP_ALIVE);
                        ctx.write(response);
                    }
                }
            }

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
