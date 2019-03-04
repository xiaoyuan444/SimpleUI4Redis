package com.dxyisgod.redisUI.compent.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dxyisgod.redisUI.Application;
import com.dxyisgod.redisUI.util.LoginRecordUtil;
import com.dxyisgod.redisUI.util.RedisUtil;

import sun.net.util.IPAddressUtil;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 5469223225275144567L;

	private JLabel nameLabel = new JLabel("名称：");
	private JComboBox<String> nameSelect = new JComboBox<String>();
	private JLabel ipLabel = new JLabel("地址：");
	private JTextField ipField = new JTextField();
	private JLabel portLabel = new JLabel("端口：");
	private JTextField portField = new JTextField("6379");
	private JLabel pwdLabel = new JLabel("密码：");
	private JPasswordField pwdField = new JPasswordField();
	private JButton okBtn = new JButton("连接");
	private JButton cancelBtn = new JButton("取消");
	
	private Properties props;
	
	public LoginDialog() {
		//窗口基本属性
		this.setTitle("连接到redis");
		this.setSize(240, 180);
		this.setModal(true);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setResizable(false);//窗口大小不可变
		
		//文字向右对齐
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);
		ipLabel.setHorizontalAlignment(JLabel.RIGHT);
		portLabel.setHorizontalAlignment(JLabel.RIGHT);
		pwdLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		//各组件位置及大小
		nameLabel.setBounds(10, 10, 50, 20);
		ipLabel.setBounds(10, 35, 50, 20);
		portLabel.setBounds(10, 60, 50, 20);
		pwdLabel.setBounds(10, 85, 50, 20);
		okBtn.setBounds(50, 110, 60, 20);
		nameSelect.setBounds(60, 10, 140, 20);
		ipField.setBounds(60, 35, 140, 20);
		portField.setBounds(60, 60, 140, 20);
		pwdField.setBounds(60, 85, 140, 20);
		cancelBtn.setBounds(130, 110, 60, 20);
		
		//各组件的设置
		nameSelect.setEditable(true);
		nameSelect.addItemListener(e->itemStateChanged(e));
		okBtn.addActionListener(e->okBtnClick(e));
		cancelBtn.addActionListener(e->this.setVisible(false));
		
		//添加到窗口中
		this.getContentPane().add(nameLabel);
		this.getContentPane().add(nameSelect);
		this.getContentPane().add(ipLabel);
		this.getContentPane().add(ipField);
		this.getContentPane().add(portLabel);
		this.getContentPane().add(portField);
		this.getContentPane().add(pwdLabel);
		this.getContentPane().add(pwdField);
		this.getContentPane().add(okBtn);
		this.getContentPane().add(cancelBtn);
		
		//初始化各栏位值
		initText();
		
		this.setVisible(true);
	}
	
	private void initText(){
		if((props = LoginRecordUtil.read())!=null) {
			int count = Integer.parseInt(props.getProperty("record_count"));
			nameSelect.addItem("新增连接");
			for(int i=1;i<=count;i++) {
				nameSelect.addItem(props.getProperty("name"+i));
			}
			nameSelect.setSelectedItem(props.getProperty("last_name"));
			ipField.setText(props.getProperty("last_ip"));
			portField.setText(props.getProperty("last_port"));
			pwdField.setText(props.getProperty("last_pwd"));
		}
	}
	
	private void saveText(){
		if(props==null) {
			return;
		}
		int index = nameSelect.getSelectedIndex();
		props.setProperty("last_name", nameSelect.getSelectedItem().toString());
		props.setProperty("last_ip", ipField.getText());
		props.setProperty("last_port", portField.getText());
		props.setProperty("last_pwd", new String(pwdField.getPassword()));
		if(index==-1) {//新增
			int newIndex = Integer.parseInt(props.getProperty("record_count"))+1;
			props.setProperty("record_count", ""+newIndex);
			props.setProperty("name"+newIndex, nameSelect.getSelectedItem().toString());
			props.setProperty("ip"+newIndex, ipField.getText());
			props.setProperty("port"+newIndex, portField.getText());
			props.setProperty("pwd"+newIndex, new String(pwdField.getPassword()));
		}else if(!ipField.getText().equals(props.getProperty("ip"+index))
				|| !portField.getText().equals(props.getProperty("port"+index))
				|| !new String(pwdField.getPassword()).equals(props.getProperty("pwd"+index))) {//修改
			props.setProperty("ip"+index, ipField.getText());
			props.setProperty("port"+index, portField.getText());
			props.setProperty("pwd"+index, new String(pwdField.getPassword()));
		}
		LoginRecordUtil.write(props);
	}
	
	void okBtnClick(ActionEvent e) {
		if(IPAddressUtil.isIPv4LiteralAddress(ipField.getText()) && portField.getText().matches("^\\d*$")) {
			//初始化
			boolean isSuccess = RedisUtil.init(ipField.getText(), Integer.parseInt(portField.getText()), new String(pwdField.getPassword()), 30000, 30000);
			if(isSuccess) {
				this.setVisible(false);
				saveText();//保存登录记录
				Application.mainUI.setTitle("SwingUI4Redis-pid"+ManagementFactory.getRuntimeMXBean().getName()+"-"+ipField.getText()+":"+portField.getText()+"-"+nameSelect.getSelectedItem());
				Application.mainUI.keysPanel.showKeys((Set<String>) RedisUtil.getInstance().keys("*"));
				Application.mainUI.logPanel.log("连接成功");
			}else {
				Application.mainUI.logPanel.log("连接失败！");
				new ErrorDialog("连接失败!");
			}
		}else{
			new ErrorDialog("请输入正确的ip和port!");
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(props==null) {
			return;
		}
		//反显选中的选项
		int index = nameSelect.getSelectedIndex();
		if(index!=0 && index!=-1) {
			ipField.setText(props.getProperty("ip"+index));
			portField.setText(props.getProperty("port"+index));
			pwdField.setText(props.getProperty("pwd"+index));
		}
	}
	
}
