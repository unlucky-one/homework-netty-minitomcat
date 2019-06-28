package com.raiden.homework.netty.minitomcat.tomcat.handler;

import com.raiden.homework.netty.minitomcat.tomcat.http.MyHttpServlet;
import com.raiden.homework.netty.minitomcat.tomcat.http.MyRequest;
import com.raiden.homework.netty.minitomcat.tomcat.http.MyResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Author: Raiden
 * Date: 2019/6/27
 */
public class MyTomcatHandler extends ChannelInboundHandlerAdapter {

    MyHttpServlet servlet = new MyHttpServlet();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof DefaultHttpRequest) {
            DefaultHttpRequest request = (DefaultHttpRequest) msg;
            if (!request.decoderResult().isSuccess()) {
                return;
            }
            MyRequest myRequest = new MyRequest();
            myRequest.setMethod(request.method().name());
            myRequest.setUrl(request.uri());
            MyResponse myResponse=new MyResponse();

            servlet.service(myRequest, myResponse);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer( myResponse.getResult().getBytes("utf-8")));
            response.headers().set(CONTENT_TYPE, "text/plain;charset=UTF-8");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaderNames.CONNECTION, KEEP_ALIVE);
            ctx.write(response);
            ctx.flush();
        }
    }

}
