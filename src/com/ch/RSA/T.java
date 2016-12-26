package com.ch.RSA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;

import javax.annotation.Generated;

public class T {
	static String publicKey;
	static String privateKey;
	public static void generate(){
		try {
			Map<String, Object> keyMap = RSAUtils.genKeyPair();
			publicKey = RSAUtils.getPublicKey(keyMap);
			privateKey = RSAUtils.getPrivateKey(keyMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static String getPublicKey() {
		return publicKey;
	}
	public static void setPublicKey(String publicKey) {
		T.publicKey = publicKey;
	}
	public static String getPrivateKey() {
		return privateKey;
	}
	public static void setPrivateKey(String privateKey) {
		T.privateKey = privateKey;
	}
	public T() throws Exception {
		
	}
	public static String readFile(String filePath) throws Exception {
		File inFile = new File(filePath);
		long fileLen = inFile.length();
		Reader reader = new FileReader(inFile);
		char[] content = new char[(int) fileLen];
		reader.read(content);
//		System.out.println("读取到的内容为：" + new String(content));
		return new String(content);
	}
	public static void writeFile(String filePath, byte[] content)throws Exception {
//		System.out.println("待写入文件的内容为：" + new String(content));
		File outFile = new File(filePath);
		OutputStream out = new FileOutputStream(outFile);
		out.write(content);
		if (out != null)
			out.close();
	}
//	public static void main(String[] args) throws Exception {
//		// 保存密钥，名字分别为publicKey。txt 和privateKey。txt;
//		T t=new T();
//		t.generate();
//		PrintWriter pw1 = new PrintWriter(new FileOutputStream("publicKey.txt"));
//		PrintWriter pw2 = new PrintWriter(new FileOutputStream("privateKey.txt"));
//		pw1.print(publicKey);
//		pw2.print(privateKey);
//		pw1.close();
//		pw2.close();
//		// 从保存的目录读取刚才的保存的公钥，
//		String pubkey = readFile("publicKey.txt");// 读取的公钥内容；
//		String data ="你好"; // 需要公钥加密的文件的内容(如D:/1.txt)
//		byte[] encByPubKeyData = RSAUtils.encryptByPublicKey(data.getBytes(),pubkey);
//		// 将加密数据base64后写入文件
//		writeFile("Encfile.txt", Base64Utils.encode(encByPubKeyData).getBytes("UTF-8"));
//		// 加密后的文件保存在
//		String prikey = readFile("privateKey.txt");// 从保存的目录读取刚才的保存的私钥，
//		String Encdata = readFile("Encfile.txt");// 刚才加密的文件的内容;
//		byte[] encData = Base64Utils.decode(Encdata);
//		byte[] decByPriKeyData = RSAUtils.decryptByPrivateKey(encData, prikey);
//		// 解密后后的文件保存在D:/Decfile.txt
//		writeFile("Decfile.txt", decByPriKeyData);
//	}
}