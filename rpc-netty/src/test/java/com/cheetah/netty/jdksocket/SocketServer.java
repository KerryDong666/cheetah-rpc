package com.cheetah.netty.jdksocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * jdk网络通信服务端
 * @author kerry dong
 * @date 2019/4/6
 */
public class SocketServer {

	private static int port = 8081;

	public static void main(String[] args) throws Exception {
		ServerSocket server = null;
		Socket socket = null;
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			String content = in.readLine();
			System.out.println(content);
			content = content + "1";
			out.print(content);
			out.flush(); // if not flush, client can't receive data

			in.close();
			out.close();
		} finally {
			socket.close();
			server.close();
		}
	}

}
