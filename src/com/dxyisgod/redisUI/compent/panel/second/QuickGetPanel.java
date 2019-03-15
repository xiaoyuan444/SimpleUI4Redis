package com.dxyisgod.redisUI.compent.panel.second;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.dxyisgod.redisUI.Application;
import com.dxyisgod.redisUI.util.RedisUtil;

public class QuickGetPanel extends JPanel implements KeyListener{

	private static final long serialVersionUID = -5559812222101135791L;
	JLabel titleLabel;
	JTextField keyField;
	JLabel keyLabel;
	JButton okBtn;
	public QuickGetPanel() {
		this.setLayout(null);
		
		titleLabel = new JLabel("快速查询");
		keyLabel = new JLabel("key：");
		keyField = new JTextField();
		okBtn = new JButton("查询");
		titleLabel.setBounds(110, 10, 80, 40);
		keyLabel.setBounds(30, 50, 40, 30);
		keyField.setBounds(75, 50, 200, 30);
		okBtn.setBounds(120, 90, 60, 30);
		
		keyField.addKeyListener(this);
		okBtn.addActionListener(e->query());
		
		this.add(titleLabel);
		this.add(keyLabel);
		this.add(keyField);
		this.add(okBtn);
		
		this.setPreferredSize(new Dimension(300, 300));
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
	}
	
	private void query() {
		Application.mainUI.dataPanel.resultPanel.show(RedisUtil.getWithoutType(keyField.getText()));
		Application.mainUI.logPanel.log("获取"+keyField.getText());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()==KeyEvent.VK_ENTER && !"".equals(keyField.getText().trim())) {
			query();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
