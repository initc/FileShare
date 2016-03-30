package com.jie.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.jie.xml.XMLTools;


public class SendXMLToWeb {
	
	public static String sendXMLToWeb(String path, String XML) {
		if (path == null || XML == null)
			return null;
		// 获取连接
		HttpURLConnection con = null;
		try {
			byte[] data = XML.getBytes("utf-8");
			//byte[] data=URLEncoder.encode(XML, "utf-8").getBytes();
			if (data.length == 0)
				return null;
			URL url = new URL(path);
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setConnectTimeout(5000);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			con.setRequestProperty("Content-Length", "" + data.length);
			OutputStream out = con.getOutputStream();
			//BufferedOutputStream buffPut=new BufferedOutputStream(out);
			//buffPut.write(data);
			out.write(data);
			out.flush();
			//buffPut.flush();
			
			
			
			if (con.getResponseCode() != 200) {

				return null;
			}
			StringBuilder strResult = new StringBuilder();
			InputStream in = con.getInputStream();
			byte[] temp = new byte[1024];
			int len = 0;
			while ((len = in.read(temp)) != -1) {

				String t = new String(temp, 0, len);
				strResult.append(t);
			}
			return strResult.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (con != null)
				con.disconnect();
		}

	}

}
