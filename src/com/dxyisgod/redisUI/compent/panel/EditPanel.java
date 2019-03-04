package com.dxyisgod.redisUI.compent.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import com.dxyisgod.redisUI.Application;

public class EditPanel extends JPanel implements KeyListener{

	private static final long serialVersionUID = -7797378767419724024L;

	public JTextArea editArea;
	
	public EditPanel() {
		this.setLayout(new BorderLayout());
		
		editArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(editArea);
		editArea.addKeyListener(this);
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.isControlDown() && e.getKeyChar()==KeyEvent.VK_ENTER) {
			Application.mainUI.toolPanel.commitBtn.doClick();
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
