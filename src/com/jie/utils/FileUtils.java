package com.jie.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

public class FileUtils {

	public static boolean uploadFile(String path, Handler handler,
			Context context, String toid) {
		boolean re = false;
		try {
			int index = path.lastIndexOf('/');
			String filename = path.substring(index + 1, path.length());
			SharedPreferences sh = SpUtil.getSharePerference(context);
			String id = sh.getString("loginId", "");
			String fileUuid = UUID.randomUUID().toString().substring(0, 8);

			Map<String, String> params = new HashMap<String, String>();
			params.put("fileName", filename);
			params.put("id", id);
			params.put("toid", toid);
			params.put("fileUuid", fileUuid + filename);
			File file = new File(path);
			/*String uri = "http://192.168.191.1/FileShare/FileUpLoad?fileName="
					+ filename + "&id=" + id + "&toid=" + toid + "&fileUuid="
					+ fileUuid;*/
			post("http://192.168.191.1/FileShare/FileUpLoad", params, file, handler);

			re = true;
			return re;
		} catch (Exception e) {

			e.printStackTrace();
			return re;
		}
	}

	static Map<String, File> upfiles = new HashMap<String, File>();

	public static void post(String path, Map<String, String> params, File file,
			Handler handler) {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		URL Url;
		try {
			Url = new URL(path);
		
		HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
		conn.setReadTimeout(5 * 1000);
		conn.setDoInput(true);// ��������
		conn.setDoOutput(true);// �������
		conn.setUseCaches(false);
		conn.setRequestMethod("POST"); // Post��ʽ
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		// ������ƴ�ı����͵Ĳ���
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}
		OutputStream out=conn.getOutputStream();
		DataOutputStream outStream = new DataOutputStream(
				out);
		outStream.write(sb.toString().getBytes());
		// �����ļ�����
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ params.get("filenNme") + "\"" + LINEND);
				sb1.append("Content-Type: multipart/form-data; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				outStream.write(LINEND.getBytes());
			
		// ���������־
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// �õ���Ӧ��
		if(conn.getResponseCode() == 200){
			InputStream in = conn.getInputStream();
			InputStreamReader isReader = new InputStreamReader(in);
			BufferedReader bufReader = new BufferedReader(isReader);
			String line = null;
			StringBuilder data = new StringBuilder();
			while ((line = bufReader.readLine()) != null)
				data.append(line);

			outStream.close();
			conn.disconnect();
			handler.sendEmptyMessage(0x26);
			
		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			handler.sendEmptyMessage(0x24);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			handler.sendEmptyMessage(0x24);
		}
		
	}

	void getFile() {
		File file = new File("/sdcard/");
		File[] files = file.listFiles(new fileFilter());

		for (File f : files) {
			upfiles.put(f.getName(), new File("/sdcard/" + f.getName()));

		}
		// Toast.makeText(this, filename, Toast.LENGTH_LONG).show();

	}

	class fileFilter implements FilenameFilter {
		@Override
		public boolean accept(File dir, String filename) {
			return filename.endsWith(".mp3");
		}
	}

	// �ϴ����룬��һ��������ΪҪʹ�õ�URL���ڶ���������Ϊ�����ݣ�����������ΪҪ�ϴ����ļ��������ϴ�����ļ����������Ҫҳ��
	public static boolean post(String actionUrl, Map<String, String> params,
			Map<String, File> files) throws IOException {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000);
		conn.setDoInput(true);// ��������
		conn.setDoOutput(true);// �������
		conn.setUseCaches(false);
		conn.setRequestMethod("POST"); // Post��ʽ
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		// ������ƴ�ı����͵Ĳ���
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// �����ļ�����
		if (files != null)
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: multipart/form-data; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());
				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				outStream.write(LINEND.getBytes());
			}
		// ���������־
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// �õ���Ӧ��
		boolean success = conn.getResponseCode() == 200;
		InputStream in = conn.getInputStream();
		InputStreamReader isReader = new InputStreamReader(in);
		BufferedReader bufReader = new BufferedReader(isReader);
		String line = null;
		String data = "getResult=";
		while ((line = bufReader.readLine()) != null)
			data += line;

		outStream.close();
		conn.disconnect();
		System.out.println(data);
		return success;
	}

}
