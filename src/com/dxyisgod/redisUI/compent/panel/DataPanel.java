package com.dxyisgod.redisUI.compent.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.dxyisgod.redisUI.compent.panel.second.QuickGetPanel;
import com.dxyisgod.redisUI.compent.panel.second.ResultPanel;

public class DataPanel extends JPanel {

	private static final long serialVersionUID = 3158444969794053210L;

	public QuickGetPanel quickGetPanel;
	public ResultPanel resultPanel;
	
	public DataPanel() {
		this.setLayout(new BorderLayout());
		
		quickGetPanel = new QuickGetPanel();
		resultPanel = new ResultPanel();
		this.add(quickGetPanel, BorderLayout.WEST);
		this.add(resultPanel, BorderLayout.CENTER);
		
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
	}
	
}
