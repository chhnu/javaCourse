package com.ch.test;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import com.ch.RSA.Base64Utils;
import com.ch.RSA.RSAUtils;
import com.ch.RSA.T;
import com.ch.registerGuide.RegisterGuide;
import com.ch.registerGuide.Registery;
import com.ch.registerSoftware.LOCALMAC;
import com.ch.registerSoftware.RegisterSoftware;
//主测试程序，主要实现各个类的监听器
public class Test {
	
	private static RegisterGuide registerGuide;
	private static Registery registery;
	private static RegisterSoftware registerSoftware;
	private static LOCALMAC localmac; 
	
	public static boolean isCharOrDight(char c){
		if((c>='0'&&c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z'))
			return true;
		else
			return false;	
	}
	//获取光标，即点击注册码编辑区的时候会自动粘贴剪贴板的内容
	static FocusListener focusListener=new FocusListener() {
		public void focusLost(FocusEvent e) {
		}
		public void focusGained(FocusEvent e) {
			String string = null,string1,string2,string3,string4;
        		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
				try {
					string=(String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);
				} catch (UnsupportedFlavorException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(string.length()==19)
				{
					string1=string.substring(0,4);
					string2=string.substring(5,9);
					string3=string.substring(10,14);
					string4=string.substring(15,19);
					registerSoftware.getRegisterCodeTextField1().setText(string1);
					registerSoftware.getRegisterCodeTextField2().setText(string2);
					registerSoftware.getRegisterCodeTextField3().setText(string3);
					registerSoftware.getRegisterCodeTextField4().setText(string4);
				}
            }
	};
	//比对方法，负责获取文件中的公钥私钥用户名注册码，解密并比对
	public static void check(){
		
		//用户名、序列号、MAC地址的获取
		String userName=registerSoftware.getUserNameTextField().getText();
		String serial1=registerSoftware.getRegisterCodeTextField1().getText();
		String serial2=registerSoftware.getRegisterCodeTextField2().getText();
		String serial3=registerSoftware.getRegisterCodeTextField3().getText();
		String serial4=registerSoftware.getRegisterCodeTextField4().getText();
		try {
			InetAddress ia;
			localmac=new LOCALMAC();
			ia = InetAddress.getLocalHost();
			String macaddress=localmac.getMACAddress();
			System.out.println(macaddress);
			File MACFile=new File("MAC.txt");
			@SuppressWarnings("resource")
			FileOutputStream fileOutputStream=new FileOutputStream(MACFile);
			fileOutputStream.write(macaddress.getBytes());
			fileOutputStream.flush();
		} catch (Exception e1) {
			System.out.println("MAC地址获取失败");
			e1.printStackTrace();
		}
		
		//加解密秘钥的获取
//		byte[] publicKeyBytes=new byte[1500];
//		byte[] privateKeyBytes=new byte[1500];
		byte[] userNameBytes=new byte[1500];
		byte[] userCodeBytes=new byte[1500];
		String publicKey = null,privateKey = null,userName1=null,userCode1=null;
//		File publicKeyFile=new File("publicKey.txt");
//		File privateKeyFile=new File("privateKey.txt");
//		File userNameFile=new File("userNameFile.txt");
//		File userCodeFile=new File("userCodeFile.txt");
//		FileInputStream publicKeyStream;
//		FileInputStream privateKeyStream;
//		FileInputStream userNameStream;
//		FileInputStream userCodeStream;
		if(true){
			try {
//				publicKeyStream=new FileInputStream(publicKeyFile);
//				privateKeyStream=new FileInputStream(privateKeyFile);
//				userNameStream=new FileInputStream(userNameFile);
//				userCodeStream=new FileInputStream(userCodeFile);
//				publicKeyStream.read(publicKeyBytes);
//				privateKeyStream.read(privateKeyBytes);
//				userNameStream.read(userNameBytes);
//				userCodeStream.read(userCodeBytes);
//				publicKey=new String(publicKeyBytes);
//				privateKey=new String(privateKeyBytes);
//				publicKeyStream.close();
//				privateKeyStream.close();
//				userNameStream.close();
//				userCodeStream.close();
				publicKey = T.readFile("publicKey.txt");// 读取的公钥内容
				privateKey= T.readFile("privateKey.txt");// 读取的秘钥内容
				userName1= T.readFile("userName.txt");
				userCode1= T.readFile("userCode.txt");
				userNameBytes = Base64Utils.decode(userName1);
				byte[] user = RSAUtils.decryptByPrivateKey(userNameBytes, privateKey);
				userCodeBytes = Base64Utils.decode(userCode1);
				byte[] code = RSAUtils.decryptByPrivateKey(userCodeBytes, privateKey);
				userName1=new String(user);
				userCode1=new String(code);
				
				System.out.println("用户名:"+userName);
				System.out.println("序列号:"+serial1+" "+serial2+" "+serial3+" "+serial4);
//				rsaTest=new RSATest();
//				rsaTest.setPublicKey(publicKey);
//				rsaTest.setPrivateKey(privateKey);
//				System.out.println(publicKey);
//				System.out.println(privateKey);
//				System.out.println(new String(userNameBytes));
//				System.out.println(new String(userCodeBytes));
//				userName1=rsaTest.decoder(userNameBytes);
//				userCode1=rsaTest.decoder(userCodeBytes);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//验证
				if(userName.equals(userName1)&&(serial1+" "+serial2+" "+serial3+" "+serial4).equals(userCode1))
				{
					System.out.println("软件注册成功");
					registery.writeValue("state", "registerfinish");
					registery.writeValue("registerTime", new Date().toString());
					
				}
				else
				{
					System.out.println("注册失败，请检查用户名和序列号");
				}
			}
	}
	
	public static void main(String[] args)
	{
		registerGuide=new RegisterGuide();
		
		registerGuide.getContinueButton().addActionListener(new ActionListener() {
			String string=null;
			public void actionPerformed(ActionEvent e) {
				//启动时检查状态
				registery=new Registery();
				if(registery.getRegedit("state").equals("registerfinish"))
				{
					System.out.println("软件已经注册，不必再次启动此程序");
					System.out.println("软件注册时间"+registery.getRegedit("registerTime"));
					System.exit(1);
				}
				if (registerGuide.getSelectRegister().isSelected()) {
					string="init_register";
					registerGuide.setRadioText(registerGuide.getSelectRegister().getText());
					System.out.println(registerGuide.getRadioText());
				} else if (registerGuide.getSelectTryout().isSelected()) {
					string="init_tryout";
					registerGuide.setRadioText(registerGuide.getSelectTryout().getText());
					System.out.println(registerGuide.getRadioText());
					System.out.println("软件试用中。。。");
				} else {
					System.out.println("请选择注册或者使用！");
				}
				if(string=="init_tryout")
				{
//					registery=new Registery();
					registery.tryout(new Date(), 30, string);
					System.exit(0);
				}
				if(string=="init_register")
				{
					registerGuide.dispose();
//					registery=new Registery();
					registery.register(new Date(), 30, string);
					registerSoftware=new RegisterSoftware();
					registerSoftware.requestFocus();
					//为编辑区添加光标事件监听
					registerSoftware.getRegisterCodeTextField1().addFocusListener(focusListener);
					registerSoftware.getRegisterCodeTextField2().addFocusListener(focusListener);
					registerSoftware.getRegisterCodeTextField3().addFocusListener(focusListener);
					registerSoftware.getRegisterCodeTextField4().addFocusListener(focusListener);
					//后退按钮监听
					registerSoftware.getBackButton().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							registerGuide.setVisible(true);
							registerSoftware.setVisible(false);
							
						}
					});
					//前进按钮监听
					registerSoftware.getForwardButton().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int i;
							boolean flag=true;
							String string=registerSoftware.getUserNameTextField().getText();
							for(i=0;i<string.length();i++)
								if(isCharOrDight(string.charAt(i))==false)
								{
									flag=false;
								}
							if(registerSoftware.getUserNameTextField().getText().equals(""))
								flag=false;
							if(registerSoftware.getUserNameTextField().getText().length()<5)
								flag=false;
							if(flag==false)
								System.out.println("用户名不合法！");
							else{
									check();
								}
						}
					});
				}
			}
		});
	}
}
