package com.dxyisgod.redisUI.compent.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dxyisgod.redisUI.Application;
import com.dxyisgod.redisUI.compent.dialog.LoginDialog;
import com.dxyisgod.redisUI.data.Paths;
import com.dxyisgod.redisUI.util.RedisUtil;

public class ToolPanel extends JPanel{

	private static final long serialVersionUID = 544429755336230875L;

	JButton connectBtn;
	JButton openBtn;
	JButton saveBtn;
	JButton settingBtn;
	JButton commitBtn;
	
	File currentFile;
	
	public ToolPanel() {
		this.setLayout(null);
		
		connectBtn = new JButton();
		connectBtn.setBounds(10, 10, 40, 40);
		connectBtn.setIcon(new ImageIcon(Paths.iconPath+"connect.PNG"));
		connectBtn.addActionListener(e->new LoginDialog());
		
		openBtn = new JButton();
		openBtn.setBounds(60, 10, 40, 40);
		openBtn.setIcon(new ImageIcon(Paths.iconPath+"open.PNG"));
		openBtn.addActionListener(e->openFile(e));
		
		saveBtn = new JButton();
		saveBtn.setBounds(110, 10, 40, 40);
		saveBtn.setIcon(new ImageIcon(Paths.iconPath+"save.PNG"));
		saveBtn.addActionListener(e->saveFile(e));
		
		settingBtn = new JButton();
		settingBtn.setBounds(160, 10, 40, 40);
		settingBtn.setIcon(new ImageIcon(Paths.iconPath+"setting.PNG"));
		settingBtn.addActionListener(e->openSetting(e));
		
		commitBtn = new JButton();
		commitBtn.setBounds(210, 10, 40, 40);
		commitBtn.setIcon(new ImageIcon(Paths.iconPath+"commit.PNG"));
		commitBtn.addActionListener(e->commit(e));
		
		this.add(connectBtn);
		this.add(openBtn);
		this.add(saveBtn);
		this.add(settingBtn);
		this.add(commitBtn);
		
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
	}

	void openFile(ActionEvent e) {
		JFileChooser jfc = new JFileChooser(Paths.rsqlPath);
		jfc.setFileFilter(new FileNameExtensionFilter("本程序专用文件(*.rsql)", "rsql"));
		jfc.setDialogTitle("打开文件");
		int actionCode = jfc.showOpenDialog(Application.mainUI);
		if(actionCode==JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			byte[] content = new byte[(int) file.length()];
			try(FileInputStream fis = new FileInputStream(file)){
				fis.read(content, 0, (int) file.length());
				Application.mainUI.editPanel.editArea.setText(new String(content,"utf-8"));
				Application.mainUI.logPanel.log("已打开"+file.getName());
				currentFile = file;
			}catch(IOException ioe) {
				ioe.printStackTrace();
				Application.mainUI.logPanel.log("打开失败！");
			}
		}
	}
	
	void saveFile(ActionEvent e) {
		try {
			if(currentFile==null) {
				JFileChooser jfc = new JFileChooser(Paths.rsqlPath);
				jfc.setFileFilter(new FileNameExtensionFilter("本程序专用文件(*.rsql)", "rsql"));
				jfc.setDialogTitle("保存文件-请选择目录且输入文件名（若文件名和已有文件相同，则直接覆盖）");
				jfc.setSelectedFile(new File(Paths.rsqlPath+"请输入文件名.rsql"));
				jfc.setApproveButtonText("保存");
				int actionCode = jfc.showOpenDialog(Application.mainUI);
				if(actionCode==JFileChooser.APPROVE_OPTION) {
					currentFile = jfc.getSelectedFile();
				}else {
					return;
				}
			}
			if(!currentFile.exists()) {
				currentFile.createNewFile();
			}
			try(FileOutputStream fos = new FileOutputStream(currentFile)){
				fos.write(Application.mainUI.editPanel.editArea.getText().getBytes("utf8"));
			}
			Application.mainUI.logPanel.log("已保存至"+currentFile.getName());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Application.mainUI.logPanel.log("保存失败！");
		}	
	}
	
	void commit(ActionEvent e) {
		Application.mainUI.dataPanel.resultPanel.show("");
		String text = Application.mainUI.editPanel.editArea.getSelectedText();
		if("".equals(text) || null==text) {
			text = Application.mainUI.editPanel.editArea.getText();
		}
		List<Object> result = RedisUtil.commit(text);
		result.forEach(r->Application.mainUI.dataPanel.resultPanel.showWithoutClear(r));
	}
	
	void openSetting(ActionEvent e) {
		
	}
}
