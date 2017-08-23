
class item implements Comparable<item>
{
	char c;
	int f;
	item(){
		c = 0;
		f = 0;
	}
	item(char a,int  b){
		c = a;
		f = b;
	}
	int gInt(){
		return f;
	}
	char gChar(){
		return c;
	}
	public int compareTo(item a){
		return gInt()-a.gInt();
	}
}
