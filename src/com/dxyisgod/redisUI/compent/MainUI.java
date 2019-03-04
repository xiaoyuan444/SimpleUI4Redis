package com.dxyisgod.redisUI.compent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.management.ManagementFactory;

import javax.swing.JFrame;

import com.dxyisgod.redisUI.compent.panel.DataPanel;
import com.dxyisgod.redisUI.compent.panel.EditPanel;
import com.dxyisgod.redisUI.compent.panel.KeysPanel;
import com.dxyisgod.redisUI.compent.panel.LogPanel;
import com.dxyisgod.redisUI.compent.panel.ToolPanel;

/*
 * 主界面，顶级窗口
 */
public class MainUI extends JFrame{

	private static final long serialVersionUID = -2316629684741389125L;

	//左侧键值显示列表
	public KeysPanel keysPanel;
	//上方工具栏
	public ToolPanel toolPanel;
	//下方数据展示栏
	public DataPanel dataPanel;
	//中间命令编辑栏
	public EditPanel editPanel;
	//右侧日志输出栏
	public LogPanel logPanel;
	
	public MainUI() {
		this.setTitle("SwingUI4Redis-pid"+ManagementFactory.getRuntimeMXBean().getName());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		
		keysPanel = new KeysPanel();
		toolPanel = new ToolPanel();
		dataPanel = new DataPanel();
		logPanel = new LogPanel();
		editPanel = new EditPanel();
		toolPanel.setPreferredSize(new Dimension(0, 60));
		keysPanel.setPreferredSize(new Dimension(200, 0));
		logPanel.setPreferredSize(new Dimension(150, 0));
//		editPanel.setPreferredSize(new Dimension(600, 400));
		dataPanel.setPreferredSize(new Dimension(600, 300));
		
		this.getContentPane().add(keysPanel, BorderLayout.WEST);
		this.getContentPane().add(toolPanel, BorderLayout.NORTH);
		this.getContentPane().add(editPanel, BorderLayout.CENTER);
		this.getContentPane().add(logPanel, BorderLayout.EAST);
		this.getContentPane().add(dataPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
}
