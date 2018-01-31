package com.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpURLConnectionHelper {
	String urlStr = "";
	public HttpURLConnectionHelper(String urlstr) {
		urlStr = urlstr;
	}
	public HttpURLConnection urlconn= null;
	private void Init() throws IOException
	{
		if (urlStr=="")
		{
			urlStr="http://www.baidu.com";
		}
		URL url = new URL(urlStr);
		//��һ��URL��ָ���Connection����
		urlconn = (HttpURLConnection)url.openConnection();
	}
	/**
	 * Http�е�post���󣬲���Url�и����κβ�������Щ��������ͨ��cookie����session��������ʽ�Լ�ֵ�Ե���ʽkey=value���͵��������ϣ����һ������
	 * �����URL��ʽͨ��Ϊ:"http://XXX.XXXX.com/xx.aspx"
	 * @param param ����ļ���
	 * @param value ���������ֵ
	 * @throws IOException
	 */
	public String HttpPostMethod(String key,String value) throws IOException
	{
		if (urlconn==null)
		{
			Init();
		}
		//���ø�URLConnection�ɶ�
		urlconn.setDoInput(true);
		//���ø�URLConnection��д
		urlconn.setDoOutput(true);
		//ʹ��POST��ʽ���ύ����
		urlconn.setRequestMethod("POST");
		//�����л���
		urlconn.setUseCaches(false);
		//��ʹ��POST��ʽ������������ʱ�����ǿ����ֶ�ִ��connect��������Ȼ�����������ʵ��getOutputStream()�����л�Ĭ��ִ�е�
		//������Щ����URLConnection���ԵĶ�����һ��Ҫ��connect����ִ��ǰ����Ϊһ�������Ѿ�ִ�У���Ϥ���þ�û���κ�������
		urlconn.connect();
		//ʹ��POST��ʽʱ��������Ҫ�Լ����첿��Http��������ݣ����������Ҫʹ��OutputStream����������д�����
		OutputStreamWriter writer = new OutputStreamWriter(urlconn.getOutputStream());
		
		String urlQueryStr = key+"="+URLEncoder.encode(value, "Utf-8");
		writer.write(urlQueryStr);
		
		writer.flush();
		writer.close();
		//��ȡ���ص�����
		String result = StreamDeal(urlconn.getInputStream());
		return result;
		
	}
	
	/**
	 * Http�е�get������Url�д�������Ĳ����������URL��ʽͨ��Ϊ:"http://XXX.XXXX.com/xx.aspx?param=value"
	 * ��android��Ĭ�ϵ�http����Ϊget��ʽ
	 * @return
	 * @throws IOException
	 */
	public String HttpGetMethod() throws IOException
	{
		if(urlconn == null)
		{
			Init();
		}
		String result = StreamDeal(urlconn.getInputStream());
		urlconn.disconnect();
		return result;
	}
	
	private String StreamDeal(InputStream stream) throws IOException
	{
		InputStreamReader isr = new InputStreamReader(stream);
		System.out.println(isr.toString());
		BufferedReader br = new BufferedReader(isr);
		String inputMsg= "";
		String result = "";
		while((inputMsg=br.readLine())!= null)
		{
			result+=inputMsg+"\n";
		}
		isr.close();
		return result;
	}
}
