

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
public class App extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	String InputText = "";
	String OutputText = "";
	
	Engine E ; 
	void save(String s){
		if(E == null)
			E = new Engine();
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
	        E.file = fileChooser.getSelectedFile();
	        JOptionPane.showMessageDialog(null,""+"File Saved : " + E.file.getName(),null, JOptionPane.INFORMATION_MESSAGE, null);
	    }
	     else{
	    	 JOptionPane.showMessageDialog(null,""+"Open command cancelled by user.",null, JOptionPane.INFORMATION_MESSAGE, null);  
	    	 return ;
	    }  
		try {
			FileWriter f = new FileWriter(E.file);
			f.write(s);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//if(JOptionPane.showInputDialog(null,"Enter Password : ").equals("java Hoffman")){
					App frame = new App();
					frame.setVisible(true);
					/*}
					else {
						JOptionPane.showMessageDialog(null, "\n        Wrong Password" +"\n\n\n         Contact Admin !\n\n" );
					}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
				File f = new File("Com.txt");
				if(f.exists())
				f.delete();
			
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public App() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,806,500);
		contentPane = new JPanel();
		contentPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(0, 31,392,326);
		contentPane.add(textArea);
		setLocationRelativeTo ( null );
		
		TextArea textArea_1 = new TextArea();
		textArea_1.setEditable(false);
		textArea_1.setBounds(398, 31, 392, 326);
		contentPane.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		textArea_2.setBounds(143, 373, 90, 30);
		contentPane.add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setEditable(false);
		textArea_3.setBounds(596, 373, 90, 30);
		contentPane.add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setBounds(344, 410, 90, 30);
		contentPane.add(textArea_4);
		
		Button button = new Button("New");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				E = null;
				OutputText = "";
				textArea.setText("Type Here");
				textArea_1.setText(" ");
				InputText = textArea.getText();
				
				textArea_2.setText(""+InputText.length()+" Bytes");
				textArea_3.setText(" ");
				textArea_4.setText(" ");
			}
		});
		button.setBounds(0, 0, 60, 30);
		contentPane.add(button);
		Button button_1 = new Button("Open");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(" ");
				E = new Engine();
				textArea_1.setText(" ");
				textArea_2.setText(" ");
				textArea_3.setText(" ");
				textArea_4.setText(" ");
				try {
					InputText = E.setup();
					if(InputText == null)
						return ;
				} catch (Exception e) {
					e.printStackTrace();
				}
				textArea.setText(InputText);
				textArea_1.setText("");
				textArea_2.setText(""+InputText.length()+" Bytes");
				textArea_3.setText("");
				textArea_4.setText("");
			}
		});
		button_1.setBounds(60, 0, 60, 30);
		contentPane.add(button_1);
		
		Button button_6 = new Button("Save");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save(InputText);
			}
		});
		button_6.setBounds(120, 0, 60, 30);
		contentPane.add(button_6);
		
		Button button_2 = new Button("Compress");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InputText = textArea.getText();
				if(E == null)
					save(InputText);
				if(InputText.length() == 0){
					JOptionPane.showMessageDialog(null, "Please Enough Enter Data to Compress !");
					return ;
				}
				E.init(InputText);
				OutputText = E.compress(InputText);
				
				textArea.setText(InputText);
				textArea_1.setText(" ");
				JOptionPane.showMessageDialog(null, "Compressed Successfully !!");
				textArea_1.insert(OutputText,0);
				textArea_2.setText(""+InputText.length()+" Bytes");
				textArea_3.setText(""+2*OutputText.length()+" Bytes");
				textArea_4.setText(""+String.format("%1$10.2f ",(((double)OutputText.length()*2)/InputText.length())*100)+" %");
				
				try {
					String one = OutputText;
					File fin = new File("Com.txt");
					if(fin.exists())
						fin.delete();
					
					FileOutputStream fos = new FileOutputStream("Com.txt");
					DataOutputStream dos = new DataOutputStream(fos);
					for (int i = 0;i<one.length();i++){
						dos.writeChar(one.charAt(i));
					}
					dos.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		});
		button_2.setIgnoreRepaint(true);
		button_2.setActionCommand("Compress");
		button_2.setBounds(143, 410, 90, 30);
		contentPane.add(button_2);
		
		
		Button button_3 = new Button("Decompress");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File file = new File("Com.txt");
					FileInputStream fos = new FileInputStream(file);
					DataInputStream dos = new DataInputStream(fos);
					String src = new String("");
					char c=0;
					while(true){
						try{
							c = dos.readChar();
							src += c;		
						}	
						catch(IOException e ){
							break;
						}
					}
					dos.close();
					fos.close();
					file.deleteOnExit();
					OutputText = src;
				} catch (Exception e) {
					e.printStackTrace();
				}
				textArea.setText(" ");
				String st = E.decompress(OutputText);
				InputText = E.extract(st);
				JOptionPane.showMessageDialog(null, "Decompressed Successfully !!");
				textArea.setText(InputText);
				textArea_1.setText(OutputText);
				textArea_2.setText(""+InputText.length()+" Bytes");
				textArea_3.setText(""+OutputText.length()+" Bytes");
				textArea_4.setText(""+String.format("%1$10.2f ",(((double)OutputText.length()*2)/InputText.length())*100)+" %");
			}
		});
		button_3.setBounds(596, 410, 90, 30);
		contentPane.add(button_3);
		
		Label label = new Label("Compressed File");
		label.setBounds(675, 0, 105, 30);
		contentPane.add(label);
		
		Label label_1 = new Label("Original File");
		label_1.setBounds(302, 0, 90, 30);
		contentPane.add(label_1);
		
		
		Label label_2 = new Label("Original size :");
		label_2.setBounds(60, 373, 77, 30);
		contentPane.add(label_2);
		
		Label label_3 = new Label("Compressed size : ");
		label_3.setBounds(485, 373, 105, 30);
		contentPane.add(label_3);
		
		Button button_4 = new Button("Open");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File fl = null;
				
				textArea.setText(" ");
				E = new Engine();
				textArea_1.setText(" ");
				textArea_2.setText(" ");
				textArea_3.setText(" ");
				textArea_4.setText(" ");
				
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
			        fl = fileChooser.getSelectedFile();
			        JOptionPane.showMessageDialog(null,""+"File Choosed : " + fl.getName(),null, JOptionPane.INFORMATION_MESSAGE, null);
			    }
			     else{
			    	 JOptionPane.showMessageDialog(null,""+"Open command cancelled by user.",null, JOptionPane.INFORMATION_MESSAGE, null);  
			    	 return ;
			    }  
				DataInputStream dis = null;
				try {
					dis = new DataInputStream(new FileInputStream(fl));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				String src = "";
				while(true){
						try {
							char c = dis.readChar();
							src += ""+c;
						} catch (IOException e) {
							break;
						}
				}
				try {
					dis.close();
					E.LoadKey("KEYS_"+fl.getName());
					OutputText = src;
					String one = OutputText;
					
					File fin = new File("Com.txt");
					if(fin.exists())
						fin.delete();
					
					FileOutputStream fos = new FileOutputStream("Com.txt");
					DataOutputStream dos = new DataOutputStream(fos);
					for (int i = 0;i<one.length();i++){
						dos.writeChar(one.charAt(i));
					}
					dos.close();
					fos.close();	
				} catch (IOException e) {
					e.printStackTrace();
				}
				textArea.setText(" ");
				textArea_1.setText(OutputText);
				textArea_2.setText(" ");
				textArea_3.setText(""+OutputText.length()+" Bytes");
				textArea_4.setText(" ");
			}
		});
		button_4.setBounds(398, 0, 60, 30);
		contentPane.add(button_4);
		
		Button button_5 = new Button("Export");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File fl = null;
				
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showSaveDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
			        fl = fileChooser.getSelectedFile();
			        JOptionPane.showMessageDialog(null,""+"File Imported Successfully !!\n\n\t\t" + fl.getName(),null, JOptionPane.INFORMATION_MESSAGE, null);
			    }
			     else{
			    	 JOptionPane.showMessageDialog(null,""+"Open command cancelled by user.",null, JOptionPane.INFORMATION_MESSAGE, null);  
			    	 return ;
			    }  
				try {
					DataOutputStream dos = new DataOutputStream(new FileOutputStream(fl));
					for(int i = 0;i<OutputText.length();i++){
						dos.writeChar(OutputText.charAt(i));
					}
					dos.close();
					fl.setReadOnly();
					File f = new File("KEYS_"+fl.getName());
					dos = new DataOutputStream(new FileOutputStream(f));
					for(int i = 0;i<E.clist.size();i++){
						dos.writeInt(E.clist.get(i).gInt());
						dos.writeChar(E.clist.get(i).gChar());
					}
					dos.close();
					f.setReadOnly();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		button_5.setBounds(344, 373, 90, 30);
		contentPane.add(button_5);
		
		
	}
}
