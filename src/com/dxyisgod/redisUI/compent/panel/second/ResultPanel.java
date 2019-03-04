package com.dxyisgod.redisUI.compent.panel.second;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class ResultPanel extends JPanel {
	
	private static final long serialVersionUID = -2052528175988173024L;
	
	private static final String GREENLINE = "<font color=\"green\">---------------------------------------------------</font><br>";
	
	public JEditorPane dataArea;
	
	public ResultPanel() {
		this.setLayout(new BorderLayout());
		
		dataArea = new JEditorPane();
		dataArea.setEditable(false);
		dataArea.setContentType("text/html");
		dataArea.setText("<html>"+"</html>");
//		dataArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(dataArea);
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.setPreferredSize(new Dimension(300, 300));
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setBackground(Color.WHITE);
	}
	
	public void show(Object data) {
		dataArea.setText("<html>"+process(data)+"</html>");
	}
	
	public void showWithoutClear(Object data) {
		String orgnText = dataArea.getText();
		dataArea.setText(orgnText.substring(0, orgnText.lastIndexOf("</body>")) + GREENLINE + process(data) + "<br></body>");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String process(Object data) {
		if(data instanceof Map) {
			StringBuffer sb = new StringBuffer("");
			((Map) data).forEach((k,v)->{
				sb.append("<font color=\"red\">");
				sb.append(k);
				sb.append("</font>");
				sb.append(" = ");
				sb.append(v);
				sb.append("<br>");
			});
			sb.delete(sb.length()-4, sb.length());
			return sb.toString();
		}
		return ""+data;
		
	}
}
