package com.cheetah.netty.jdknio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
public class NioClient {

	public static void main(String[] args) throws Exception{
		NioClient client = new NioClient();
		client.testSendfile();
	}

	public void testSendfile() throws IOException {
		String host = "localhost";
		int port = 9026;
		SocketAddress sad = new InetSocketAddress(host, port);//创建socket
		SocketChannel sc = SocketChannel.open();//打开管道
		sc.connect(sad);//连接socket管道
		sc.configureBlocking(true);//设置阻塞

		String fname = "F:\\framework\\cheetah-rpc\\rpc-netty\\src\\test\\resouces\\AppTest.java";//文件名
		long fsize = 183678375L, sendzise = 4094;//文件大小，发送大小

		FileChannel fc = new FileInputStream(fname).getChannel();//通过文件名获取文件管道
		long start = System.currentTimeMillis();
		long nsent = 0, curnset = 0;
		curnset = fc.transferTo(0, fsize, sc);//向管道发送0-fsize大小的文件，从一个kernel的chanel放到scoket的chanel
		System.out.println("total bytes transferred--"+curnset+" and time taken in MS--"+(System.currentTimeMillis() - start));
		//fc.close();
	}
}
