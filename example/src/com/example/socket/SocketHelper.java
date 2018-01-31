package com.example.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHelper {
	private static ServerSocket serverSocket = null;
	private static Socket client = null;
	private static int port = 9048;
	private static BufferedReader br= null; 
	private static BufferedWriter bw = null;
	
	/**
	 * ����һ��SocketServer������������������
	 * @throws IOException
	 */
	public static ServerSocket CreateServer() throws IOException
	{
		serverSocket = new ServerSocket(port,10);
        System.out.println("================start listening...: " +serverSocket.isBound() + ", closed: " + serverSocket.isClosed());

		return serverSocket;
	}

	public static ServerSocket CreateServer(int port) throws IOException
	{
		SocketHelper.port = port;
		serverSocket = new ServerSocket(port,10);
		System.out.println("================start listening...: " +serverSocket.isBound() + ", closed: " + serverSocket.isClosed());

		return serverSocket;
	}


	/**
	 * ����һ��Socket������������SocketServer����
	 * @param dstName Server�����ip��ַ
	 * @return 
	 * @throws IOException
	 */
	public static Socket CreateClient(String dstName) throws IOException
	{
		Socket socket = new Socket(dstName, port);
		//Socket sockets = new Socket("192.168.8.12",port);
		return socket;
	}
	
	/**
	 * ����һ���Ѿ����ӵ��������ϵ�Socket����
	 * @throws IOException
	 */
	public static void GetClinetSocket() throws IOException
	{
		client = serverSocket.accept();
		System.out.println("get a connected client");
	}
	
	/**
	 * ��socket��������ȡ�����з�������
	 * @param socket
	 * @param msg
	 * @throws IOException
	 */
	public static void SendMsg(Socket socket , String msg) throws IOException
	{
		bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		bw.write(msg);
		bw.flush();
		bw.close();
	}
	
	/**
	 * ��ȡsocket������������
	 * @param socket
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	public static String ReceiveMsg(Socket socket, String msg) throws IOException
	{
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String receiveMsg = "Receive msg:"+ br.readLine();
		br.close();
		return receiveMsg;
	}
	
	/**
	 * �ͷ�socket����
	 * @throws IOException
	 */
	public static void Close() throws IOException
	{
		if(client != null)
		{
			client.close();
		}
		if(serverSocket != null)
		{
			serverSocket.close();
		}
	}
}
