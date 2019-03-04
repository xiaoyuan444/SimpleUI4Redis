package com.dxyisgod.redisUI.compent.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import com.dxyisgod.redisUI.Application;
import com.dxyisgod.redisUI.util.RedisUtil;

public class KeysPanel extends JPanel implements MouseListener, KeyListener{

	private static final long serialVersionUID = -626903610227453380L;

	private DefaultListModel<String> model;
	
	private JList<String> jlist;
	
	public KeysPanel() {
		this.setLayout(new BorderLayout());
		
		model = new DefaultListModel<>();
		jlist = new JList<>(model);
		jlist.addMouseListener(this);
		jlist.addKeyListener(this);
		JScrollPane scrollPane = new JScrollPane(jlist);
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
	}

	public void showKeys(Set<String> keys) {
		model.clear();
		new TreeSet<>(keys).forEach(o->model.addElement(o));
	}
	
	public void query() {
		Application.mainUI.dataPanel.resultPanel.show(RedisUtil.getInstance().getWithoutType(jlist.getSelectedValue()));
		Application.mainUI.logPanel.log("获取"+jlist.getSelectedValue());	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2 && e.getButton()==1) {
			query();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar()==KeyEvent.VK_ENTER) {
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
