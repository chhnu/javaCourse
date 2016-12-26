package com.ch.registerGuide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import com.ch.registerSoftware.RegisterSoftware;

public class RegisterGuide extends JFrame {
	
	//定义软件使用状态枚举变量
	enum state{
		tryout,tryoutFinish,register,registerFinish;
	}
	private JPanel westPanel;//西部面板，放置图片和提示信息
	private JPanel eastPanel;//东部面板，放置注册试用选项和按钮、图片
	private ImageIcon pImage1;//西部面板的图片
	private ImageIcon pImage2;//东部面板的图片
	private JLabel jlaImage1;//西部面板的图片标签
	private JLabel jlaImage2;//东部面板的额图片标签
	private JLabel message;//西部面板的安装提示信息
	private String radioText;//当前选中的单选按钮的内容
	private JRadioButton selectRegister;//注册单选按钮
	private JRadioButton selectTryout;//使用单选按钮
	private ButtonGroup buttonGroup;//单选按钮组，控制只能选一个
	private JButton continueButton;//继续按钮
	private JLabel tipLabel;//显示剩余的试用时间

	public RegisterGuide() {
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
		message.setText("<html><i>" + "关于注册:</i><br/>" + "注册需要用户名与注<br/>" + "册码，用户可以在软<br/>" + "件包装处进行寻找。<br/>"
				+ "如果您不想激活该软<br/>" + "件，可以在使用状态<br/>" + "下使用，并且可以再<br/>" + "试用期内随时激活本<br/>" + "软件。");
		pImage1 = new ImageIcon("pImage1.png");
		jlaImage1 = new JLabel(pImage1);
		westPanel.add(jlaImage1);
		westPanel.add(message);
		westPanel.setBorder(new LineBorder(new Color(100, 100, 100)));

		eastPanel = new HomePanel();
		eastPanel.setLayout(null);
		selectRegister = new JRadioButton("我想注册该软件");
		selectRegister.setBounds(10, 100, 150, 20);
		selectRegister.setBackground(new Color(234, 241, 247));
		selectTryout = new JRadioButton("我想试用该软件（剩余30天）");
		selectTryout.setBounds(10, 150, 200, 20);
		selectTryout.setBackground(new Color(234, 241, 247));
		buttonGroup = new ButtonGroup();
		buttonGroup.add(selectRegister);
		buttonGroup.add(selectTryout);
		continueButton = new JButton("[继续]");
		continueButton.setBounds(120, 200, 100, 25);
		eastPanel.add(selectRegister);
		eastPanel.add(selectTryout);
		eastPanel.add(continueButton);
		eastPanel.setBorder(new LineBorder(new Color(100, 100, 100)));


		this.add(BorderLayout.WEST, westPanel);
		this.add(BorderLayout.CENTER, eastPanel);
		this.setVisible(true);

		// 按钮的时间相应操作，监测单选按钮
		
	}
	//对应的set，get方法
	public JRadioButton getSelectRegister() {
		return selectRegister;
	}

	public String getRadioText() {
		return radioText;
	}

	public void setRadioText(String radioText) {
		this.radioText = radioText;
	}

	public void setSelectRegister(JRadioButton selectRegister) {
		this.selectRegister = selectRegister;
	}

	public JRadioButton getSelectTryout() {
		return selectTryout;
	}

	public void setSelectTryout(JRadioButton selectTryout) {
		this.selectTryout = selectTryout;
	}

	public JButton getContinueButton() {
		return continueButton;
	}

	public void setContinueButton(JButton continueButton) {
		this.continueButton = continueButton;
	}

}
