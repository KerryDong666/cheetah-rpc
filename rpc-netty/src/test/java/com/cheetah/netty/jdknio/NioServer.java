package com.cheetah.netty.jdknio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author kerry dong
 * @date 2019/4/6
 */
public class NioServer {


	public static void main(String[] args) {
		NioServer server = new NioServer();
		server.mySetup();
		server.readData();
	}

	/**
	 * server socket管道
	 */
	ServerSocketChannel listener = null;

	protected void mySetup() {
		InetSocketAddress listenAddr = new InetSocketAddress(9026);//创建socket
		try {
			listener = ServerSocketChannel.open();//打开管道
			ServerSocket ss = listener.socket();//从管道中获取连接
			ss.setReuseAddress(true);//地址重用
			ss.bind(listenAddr);//绑定地址
			System.out.println("Listening on port : " + listenAddr.toString());
		} catch (IOException e) {
			System.out.println("Failed to bind, is port : " + listenAddr.toString()
					+ " already in use ? Error Msg : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void readData() {
		ByteBuffer dst = ByteBuffer.allocate(4096);//分配一个新的字节缓冲区
		try {
			while (true) {
				SocketChannel conn = listener.accept();//连接到chanel
				System.out.println("Accepted : " + conn);
				conn.configureBlocking(true);//设置阻塞
				int nread = 0;
				while (nread != -1) {
					try {
						nread = conn.read(dst);//往缓冲区里读
					} catch (IOException e) {
						e.printStackTrace();
						nread = -1;
					}
					byte[] array = dst.array();
					System.out.println(new String(array, "UTF-8"));

					dst.rewind();//重绕此缓冲区。将位置设置为 0 并丢弃标记。 在一系列通道写入或获取 操作之前调用此方法（假定已经适当设置了限制）。
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
