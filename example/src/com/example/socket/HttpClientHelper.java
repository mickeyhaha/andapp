package com.example.socket;

import java.util.ArrayList;
import java.util.List;

/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
*/
public class HttpClientHelper {
	/*
	String urlStr = "";
	public HttpClientHelper(String urlstr)
	{
		this.urlStr = urlstr;
	}
	public String HttpGetMethod()
	{
		String result = "";
		try
		{
		HttpGet httpRequest = new HttpGet(urlStr);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
		{
			result = EntityUtils.toString(httpResponse.getEntity());
		}
		else
		{
			result = "null";
		}
		return result;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public String HttpPostMethod(String key,String value)
	{
		String result = "";
		try
		{
		// HttpPost���Ӷ���
		HttpPost httpRequest = new HttpPost(urlStr);
		// ʹ��NameValuePair������Ҫ���ݵ�Post����
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// ���Ҫ���ݵĲ���
		params.add(new BasicNameValuePair(key, value));
		// �����ַ���
		HttpEntity httpentity = new UrlEncodedFormEntity(params, "Utf-8");
		// ����httpRequest
		httpRequest.setEntity(httpentity);
		// ȡ��Ĭ�ϵ�HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		// ȡ��HttpResponse
		HttpResponse httpResponse = httpclient.execute(httpRequest);
		// HttpStatus.SC_OK��ʾ���ӳɹ�
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// ȡ�÷��ص��ַ���
			result = EntityUtils.toString(httpResponse.getEntity());
			return result; 
		} else {
			 return "null";
		}
		}
		catch(Exception e)
		{
			return null;
		}
	}*/

	public static void main(String[] args) {
	    /*
        HttpClientHelper clientHelper = new HttpClientHelper("http://www.baidu.com/s?cl=3");
        System.out.println(clientHelper.HttpGetMethod());
        HttpURLConnectionHelper httpHelper = new HttpURLConnectionHelper("http://127.0.0.1");
        try {
            System.out.println(httpHelper.HttpGetMethod());
            tv.setText(httpHelper.HttpGetMethod());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
