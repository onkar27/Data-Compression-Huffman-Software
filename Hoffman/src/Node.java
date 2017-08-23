
class Node{
	String ch;
	int freq;
	Node left,right;
	
	Node(){
		ch = "";
		freq = 0;
		left = right = null;
	}
	void setData(String ch,int freq){
		this.ch = ch;
		this.freq = freq;
	}
	void setChild(Node left,Node right){
		this.left = left;
		this.right = right;
	}
	String  get_ch(){
		return ch;
	}
	int get_freq(){
		return freq;
	}
	Node get_left(){
		return left;
	}
	Node get_right(){
		return right;
	}
	public String toString(){
		return new String (ch+"-"+freq+"#");
	}
	void show(){
		System.out.println(toString());
	}
}
