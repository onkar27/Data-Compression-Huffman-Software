

import java.io.*;
import java.util.*;

import javax.swing.*;

public class Engine {
	Vector <Node> dlist;
	Map <String,String> code;
	Vector <item> clist;
	File file ;
	Engine(){
		dlist = new Vector <Node>();
		code = new HashMap <String,String>();
		clist = new Vector <item>(); 
		file = null;
	}
	void autoinsert(Node t){
		 int i;
		 for(i=0;i<dlist.size();i++)
		 {
			 Node d = (Node) dlist.get(i);
			 if(d.get_freq()>=t.get_freq())
			 {
				 Node temp = d;
				 dlist.set(i,t);
				 t = temp;
			 }
		 }
		 dlist.add(t);
	}
	void createlink(){
		Node pNew = new Node(),left = new Node(),right = new Node();
		Node l =(Node)dlist.get(0),r = (Node)dlist.get(1);
		pNew.setData("",l.get_freq()+r.get_freq());
		left = l;
		right = r;
		pNew.setChild(left, right);
		dlist.remove(0);
		dlist.remove(0);
		autoinsert(pNew);
	}
	void get_code(Node top,String code)
	{
		String tmp = new String (code);
	    if (top.get_left()!=null)
	    {
	        code+= "0";
	        get_code(top.get_left(),code);
	        code = tmp;
	    }
	    if (top.get_right()!=null)
	    {
	        code+= '1';
	        get_code(top.get_right(),code);
	        code = tmp;
	    }
	    if (top.get_ch()!="")
	    {
	        String d = new String(code);
	        this.code.put(top.get_ch(),d);
	        //System.out.println(d + " " + top.get_ch());
	    }
	}
	String compress(String src){
		int i=0;
		src+=""+(char)65535;
		String desti="";
		String des ="";
		for(i=0;i<src.length();i++){
			des+=code.get(""+src.charAt(i));
			//System.out.print(code.get(""+src.charAt(i)));
		}
		//System.out.println("\n"+des);
		int k = 16;
		while(des.length()>16){
			String letter = des.substring(0,k);
			des = des.substring(k,des.length());
			//System.out.println(letter);
			desti+=""+(char)Integer.parseInt(letter,2);
		}
		while(des.length()<16)
			des+="0";

		desti+=""+(char)Integer.parseInt(des,2);
		
		return desti;
	}
	String decompress(String src){
		int i=0;
		String des = "";
		String letter = "";
		for(i=0;i<src.length();i++){
		    letter = "";
			letter = Integer.toBinaryString((int)src.charAt(i));
			while(letter.length()<16){
				letter = "0" + letter;
			}
			des += letter;
		}
		//System.out.println(des);
		return des;
	}
	String extract(String src){
		String des="";
		int i=0;
		String letter="";
		L:for(i=0;i<src.length();i++){
			letter+=""+src.charAt(i);
			if(code.containsValue(letter)==true){
				for(Object o : code.keySet()){
					if(code.get(o).equals(letter)){
						if(o.equals(""+(char)65535)){
							break L;
						}
						des+=""+o;
						letter = "";
						break;
					}
				}
			}
		}
		return des;
	}
    String setup() throws Exception{
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
	        file = fileChooser.getSelectedFile();
	        JOptionPane.showMessageDialog(null,""+"File Selected : " + file.getName(),null, JOptionPane.INFORMATION_MESSAGE, null);
	    }
	     else{
	    	 JOptionPane.showMessageDialog(null,""+"Open command cancelled by user.",null, JOptionPane.INFORMATION_MESSAGE, null);  
	    	 return null;
	    }      
		String src ="";
		int j=0,cnt= 0;
		FileInputStream f = new FileInputStream(file.getAbsolutePath());
		
		while(true){
			j = f.read();
			if(cnt==file.length())break;
			src +=""+(char)j;
			cnt +=1;
		}
		f.close();
		return src;
	}
    void show(){
    	for(Object o :code.keySet()){
    		System.out.println(o+"\t"+code.get(o));
    	}
    }
    void LoadKey(String s) throws FileNotFoundException{
    	FileInputStream fis = new FileInputStream(s);
		DataInputStream dis = new DataInputStream(fis);
		
		while(true){
			try {
				int t =dis.readInt();
				char c =dis.readChar();
				item x = new item(c,t);
				clist.addElement(x);
				Node pNew = new Node();
				pNew.setData(""+c,t);
				autoinsert(pNew);
			} 
			catch (IOException e) {
				break;
			}
		}
		try {
			dis.close();
		}
		catch (IOException e) {
		}
		while(dlist.size()!=1)
	        createlink();
		
		String src="";
		get_code((Node )dlist.get(0),src);
		//System.out.println("Loaded");
		//show();
    }
	void init(String s){
		int array[] = new int[65536];
		int i=0;
		s+=""+(char)65535;
		for(i=0;i<s.length();i++){
			array[s.charAt(i)]++;
		}
		for(i=0;i<array.length;i++){
			if(array[i]!=0){
				Node pNew = new Node();
				pNew.setData(""+(char)i,array[i]);
				item x = new item((char)i,array[i]);
				clist.add(x);
				autoinsert(pNew);
				//System.out.println(x.gChar()+"\t"+x.gInt());
			}
		}
		while(dlist.size()!=1)
	        createlink();
		
		String src="";
		get_code((Node )dlist.get(0),src);
		System.out.println(""+code);
		//show();
	}
	/*public static void main(String []arf){
		Engine E = new Engine();
		String s = "";
		try {
			s = E.setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
		E.init(s);
		String com = E.compress(s);
		String decom = E.decompress(com);
		String exc = E.extract(decom);
		System.out.println(s+ "\n" + exc+"\n"+s.equals(exc));
	}*/
}