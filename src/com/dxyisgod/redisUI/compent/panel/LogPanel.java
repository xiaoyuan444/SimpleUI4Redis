package com.dxyisgod.redisUI.compent.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class LogPanel extends JPanel{

	private static final long serialVersionUID = -3657343257568476859L;

	public JTextArea logArea;
	
	public LogPanel() {
		this.setLayout(new BorderLayout());
		
		logArea = new JTextArea();
		logArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(logArea);
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
	}
	
	public void log(String content) {
		this.logArea.append(content+"\r\n");
	}
	
}
