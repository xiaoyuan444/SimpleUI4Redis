package com.dxyisgod.redisUI.compent.dialog;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class ErrorDialog extends JDialog {

	private static final long serialVersionUID = -7881283590761451978L;

	public ErrorDialog(String msg) {
		//窗口基本属性
		this.setTitle("错误");
		this.setSize(200, 150);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);//窗口大小不可变
		
		JLabel msglabel = new JLabel(msg);
		msglabel.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(msglabel);
		this.setVisible(true);
	}
	
	
}
