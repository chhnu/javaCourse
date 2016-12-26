package com.ch.registerSoftware;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.im.InputContext;
import java.io.IOException;
import java.security.KeyStore;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import org.omg.PortableServer.THREAD_POLICY_ID;

import com.ch.registerGuide.HomePanel;

@SuppressWarnings("serial")
public class RegisterSoftware extends JFrame{
	public JButton getForwardButton() {
		return forwardButton;
	}
	public void setForwardButton(JButton forwardButton) {
		this.forwardButton = forwardButton;
	}
	public JButton getBackButton() {
		return backButton;
	}
	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}
		//定义软件使用状态枚举变量
		enum state{
			tryout,tryoutFinish,register,regisFinish;
		}
		private JPanel westPanel;//西部面板，放置图片和提示信息
		private JPanel eastPanel;//东部面板，放置注册试用选项和按钮、图片
		private ImageIcon pImage1;//西部面板的图片
		private ImageIcon pImage2;//东部面板的图片
		private JLabel jlaImage1;//西部面板的图片标签
		private JLabel jlaImage2;//东部面板的图片标签
		private JLabel message;//西部面板的安装提示信息
		private JPanel messagePanel;//包装message的面板
		
		private JLabel infoLabel;//输入提示信息
		private JLabel userNameLabel;//用户名标签
		private JTextField userNameTextField;//用户名编辑框
		private JLabel registerCodeLabel;//注册码标签
		private JTextField registerCodeTextField1;//注册码1区
		private JTextField registerCodeTextField2;//注册码2区
		private JTextField registerCodeTextField3;//注册码3区
		private JTextField registerCodeTextField4;//注册码4区
		private JButton forwardButton;//前进按钮
		private JButton backButton;//后退按钮
		private JLabel tipLabel;//显示剩余的试用时间
		
		public RegisterSoftware() {
			// 窗体的初始化操作
			setName("软件注册程序");
			this.setTitle("注册导航窗体");
			this.setSize(558, 360);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLayout(new BorderLayout());
			// 界面组件的添加
			westPanel = new JPanel();
			westPanel.setLayout(new FlowLayout());
			westPanel.setPreferredSize(new Dimension(170, 345));
			message = new JLabel();
			message.setText("请您填写序列号");
			message.setBounds(20, 80, 150, 20);
			messagePanel=new JPanel();
			messagePanel.setPreferredSize(new Dimension(170, 220));;
			messagePanel.setLayout(null);
			messagePanel.add(message);
			
			pImage1 = new ImageIcon("pImage1.png");
			jlaImage1 = new JLabel(pImage1);
			westPanel.add(jlaImage1);
			westPanel.add(messagePanel);
			westPanel.setBorder(new LineBorder(new Color(100, 100, 100)));

			eastPanel = new HomePanel();
			eastPanel.setLayout(null);
			infoLabel=new JLabel("请输入您的用户名和密码");
			infoLabel.setBounds(10,80,200,20);
			userNameLabel=new JLabel("用户名：");
			userNameLabel.setBounds(10, 140, 70, 20);
			userNameTextField=new JTextField(20);
			userNameTextField.setDocument(new JTextFieldLimit(5));
			userNameTextField.setBounds(80, 140, 175, 20);
			registerCodeLabel=new JLabel("注册码：");
			registerCodeLabel.setBounds(10, 180, 70, 20);
			registerCodeTextField1=new JTextField(4);
			registerCodeTextField1.setDocument(new JTextFieldLimit(4));
			registerCodeTextField1.setBounds(80, 180, 40, 20);
			registerCodeTextField2=new JTextField(4);
			registerCodeTextField2.setDocument(new JTextFieldLimit(4));
			registerCodeTextField2.setBounds(125, 180, 40, 20);
			registerCodeTextField3=new JTextField(4);
			registerCodeTextField3.setDocument(new JTextFieldLimit(4));
			registerCodeTextField3.setBounds(170, 180, 40, 20);
			registerCodeTextField4=new JTextField(4);
			registerCodeTextField4.setDocument(new JTextFieldLimit(4));
			registerCodeTextField4.setBounds(215, 180, 40, 20);
			backButton=new JButton("[后退]");
			backButton.setBounds(80, 220, 82, 30);
			forwardButton=new JButton("[前进]");
			forwardButton.setBounds(172, 220, 82, 30);
			
			eastPanel.add(infoLabel);
			eastPanel.add(userNameLabel);
			eastPanel.add(userNameTextField);
			eastPanel.add(registerCodeLabel);
			eastPanel.add(registerCodeTextField1);
			eastPanel.add(registerCodeTextField2);
			eastPanel.add(registerCodeTextField3);
			eastPanel.add(registerCodeTextField4);
			eastPanel.add(backButton);
			eastPanel.add(forwardButton);
			eastPanel.setBorder(new LineBorder(new Color(100, 100, 100)));

			this.add(BorderLayout.WEST, westPanel);
			this.add(BorderLayout.CENTER, eastPanel);
			this.setVisible(true);	
			
		}
		public JTextField getRegisterCodeTextField1() {
			return registerCodeTextField1;
		}
		public void setRegisterCodeTextField1(JTextField registerCodeTextField1) {
			this.registerCodeTextField1 = registerCodeTextField1;
		}
		public JTextField getRegisterCodeTextField2() {
			return registerCodeTextField2;
		}
		public void setRegisterCodeTextField2(JTextField registerCodeTextField2) {
			this.registerCodeTextField2 = registerCodeTextField2;
		}
		public JTextField getRegisterCodeTextField3() {
			return registerCodeTextField3;
		}
		public void setRegisterCodeTextField3(JTextField registerCodeTextField3) {
			this.registerCodeTextField3 = registerCodeTextField3;
		}
		public JTextField getRegisterCodeTextField4() {
			return registerCodeTextField4;
		}
		public JTextField getUserNameTextField() {
			return userNameTextField;
		}
		public void setUserNameTextField(JTextField userNameTextField) {
			this.userNameTextField = userNameTextField;
		}
		public void setRegisterCodeTextField4(JTextField registerCodeTextField4) {
			this.registerCodeTextField4 = registerCodeTextField4;
		}
		
}
