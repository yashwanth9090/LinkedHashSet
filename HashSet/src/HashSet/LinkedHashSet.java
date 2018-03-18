package HashSet;

public class LinkedHashSet {
	
	public static void main(String[] args){
		HashSet<String> newList = new HashSet<>(4);
		
		newList.insert("yashu"); 
		newList.insert("uttam");
		newList.insert("sam"); 
		//newList.print();
		// Iterator implementation
		MyIterator<String> iter = newList.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		
 
		
		
	}
	
}
