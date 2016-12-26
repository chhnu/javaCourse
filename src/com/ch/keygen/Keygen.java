package com.ch.keygen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ch.RSA.Base64Utils;
import com.ch.RSA.RSAUtils;
import com.ch.RSA.T;
import com.ch.registerSoftware.JTextFieldLimit;

//注册机
public class Keygen extends JFrame{
	
	private JLabel infoLabel;//输入提示信息
	private JLabel userNameLabel;//用户名标签
	private JTextField userNameTextField;//用户名编辑框
	private JLabel registerCodeLabel;//注册码标签
	private JTextField registerCodeTextField1;//注册码1区
	private JTextField registerCodeTextField2;//注册码2区
	private JTextField registerCodeTextField3;//注册码3区
	private JTextField registerCodeTextField4;//注册码4区
	private JButton generateButton;//前进按钮
	private JPanel northPanel;//北方面板
	private JPanel centerPanel;//中间面板
	private String publicKey;
	private String privateKey;
	
	
	public Keygen(){
		this.setTitle("注册机");
		this.setSize(320,240);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		userNameLabel=new JLabel("用户名：");
		userNameTextField=new JTextField(10);
		userNameTextField.setDocument(new JTextFieldLimit(5));
		registerCodeLabel=new JLabel("注册码：");
		registerCodeTextField1=new JTextField(4);
		registerCodeTextField1.setDocument(new JTextFieldLimit(4));
		registerCodeTextField2=new JTextField(4);
		registerCodeTextField2.setDocument(new JTextFieldLimit(4));
		registerCodeTextField3=new JTextField(4);
		registerCodeTextField3.setDocument(new JTextFieldLimit(4));
		registerCodeTextField4=new JTextField(4);
		registerCodeTextField4.setDocument(new JTextFieldLimit(4));
		infoLabel=new JLabel("<html>由字母和数字组成<br/>长度为5的字符串");
		generateButton=new JButton("生成注册码");
		generateButton.setBounds(100, 140, 120, 30);
		
		northPanel=new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.setBounds(10, 10, 320, 60);
		centerPanel=new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPanel.setBounds(10, 70, 320, 60);
		northPanel.add(userNameLabel);
		northPanel.add(userNameTextField);
		northPanel.add(infoLabel);
		centerPanel.add(registerCodeLabel);
		centerPanel.add(registerCodeTextField1);
		centerPanel.add(registerCodeTextField2);
		centerPanel.add(registerCodeTextField3);
		centerPanel.add(registerCodeTextField4);
		
		//生成注册码按钮监听器
		generateButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int i;
				boolean flag=true;
				String userName=userNameTextField.getText();
				for(i=0;i<userName.length();i++)
					if(isCharOrDight(userName.charAt(i))==false)
					{
						flag=false;
					}
				if(userName.equals(""))
					flag=false;
				if(userName.length()<5)
					flag=false;
				if(flag==false)
					System.out.println("用户名不合法！");
				else
				{
					saveKey();
				}
			}
		});
		
		this.add(BorderLayout.NORTH,northPanel);
		this.add(BorderLayout.SOUTH,generateButton);
		this.add(BorderLayout.CENTER,centerPanel);
		this.setVisible(true);
	}
	//保存公钥私钥，以及加密后的用户名和注册码
	public void saveKey(){
		int i;
//		File userNameFile=new File("userNameFile.txt");
//		File userCodeFile=new File("userCodeFile.txt");
//		FileOutputStream userNameStream;
//		FileOutputStream userCodeStream;
		Random random=new Random();
		int[] number=new int[16];
		if(true)
		{
			//写入公有秘钥和私有秘钥
			try {
				//写入公有秘钥和私有秘钥
//				rsaTest=new RSATest();
//				rsaTest.generateKey();
//				publicKey=rsaTest.getPublicKey();
//				privateKey=rsaTest.getPrivateKey();
				
				//生成公钥私钥，并获取到字符串当中，并保存
				T.generate();
				publicKey=T.getPublicKey();
				privateKey=T.getPrivateKey();
				PrintWriter pw1 = new PrintWriter(new FileOutputStream("publicKey.txt"));
				PrintWriter pw2 = new PrintWriter(new FileOutputStream("privateKey.txt"));
				pw1.print(publicKey);
				pw2.print(privateKey);
				pw1.close();
				pw2.close();
				
				//产生注册码，并对用户名和注册码加密,这里用了随机数（这个无所谓啦）
				for(i=0;i<16;i++)
				{
					number[i]=random.nextInt(10);
				}
				registerCodeTextField1.setText(""+number[0]+number[1]+number[2]+number[3]);
				registerCodeTextField2.setText(""+number[4]+number[5]+number[6]+number[7]);
				registerCodeTextField3.setText(""+number[8]+number[9]+number[10]+number[11]);
				registerCodeTextField4.setText(""+number[12]+number[13]+number[14]+number[15]);
				String string1=""+number[0]+number[1]+number[2]+number[3]+" "+
						+number[4]+number[5]+number[6]+number[7]+" "+
						+number[8]+number[9]+number[10]+number[11]+" "+
						+number[12]+number[13]+number[14]+number[15];
				String string=string1+"   复制？";
				//弹窗，提供一键复制
				if(JOptionPane.showConfirmDialog(null,string)==JOptionPane.YES_OPTION)
				{
					string=string.substring(0,19);
					//拷贝到剪贴板
					Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
					Transferable transferable=new StringSelection(string);
					clipboard.setContents(transferable, null);
				}
				//用户名注册码加密并保存
				byte[] userNameBytes=RSAUtils.encryptByPublicKey(userNameTextField.getText().getBytes(),publicKey);
				T.writeFile("userName.txt", Base64Utils.encode(userNameBytes).getBytes("UTF-8"));
				byte[] userCodeBytes=RSAUtils.encryptByPublicKey(string1.getBytes(),publicKey);
				T.writeFile("userCode.txt", Base64Utils.encode(userCodeBytes).getBytes("UTF-8"));
//				userNameFile.createNewFile();
//				userCodeFile.createNewFile();
//				userNameStream=new FileOutputStream(userNameFile);
//				userCodeStream=new FileOutputStream(userCodeFile);
//				userNameStream.write(userNameBytes);
//				userCodeStream.write(userCodeBytes);
//				userNameFile.setReadable(true);
//				userNameFile.setWritable(false);
//				userCodeFile.setReadable(true);
//				userCodeFile.setWritable(false);
//				userNameStream.flush();	
//				userCodeStream.flush();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean isCharOrDight(char c){
		if((c>='0'&&c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z'))
			return true;
		else
			return false;
	}
	public static void main(String[] args){
		new Keygen();
	}

}
