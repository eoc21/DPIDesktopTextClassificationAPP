package dpi;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TextClassificationApplication extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JTextArea dataField;
	private static JTextField resultText;
	private String resultantClassification;
	public TextClassificationApplication(){
		setTitle("Text Classification");
		this.setSize(600, 600);
		JPanel center = new JPanel( new FlowLayout(FlowLayout.LEFT));
		center.setSize(400, 500);
		JButton submit = new JButton("Classify");
        submit.addMouseListener(new ClassifyTextClickHandler());
        JButton close = new JButton("Refresh Training Data");
        close.addMouseListener(new RefreshClickHandler());
        center.add(new JLabel("Paste text to classify:"));
        dataField = new JTextArea(20,100);
        center.add(dataField);
        center.add(new JLabel("Download new training data"));
        center.add(close);
        center.add(new JLabel("Classify text"));
        center.add(submit);
        resultText = new JTextField();
        resultText.setSize(20, 5);
        center.add(resultText);
        center.setVisible(true);
        add(center);
    	setVisible(true);
	}
	
	public static String getTextAreaInformation(){
		return dataField.getText();
	}
	
	public static void setClassification(String result){
		resultText.setText(result);
	}
	public static void main (String [] args) throws IOException{
		new TextClassificationApplication();
	}

}
