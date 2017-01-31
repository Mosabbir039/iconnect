package utils.huffman;

public class HuffmanTreeNode  implements Comparable<HuffmanTreeNode>{
	
	int pixel;
	int frequency;
	HuffmanTreeNode left, right;
	
	/**set the node's value and set null for left and right child**/
	public HuffmanTreeNode(int pixel, int frequency) {
		this.pixel = pixel;
		this.frequency = frequency;
	}
	public HuffmanTreeNode(int pixel, int frequency, HuffmanTreeNode left, HuffmanTreeNode right) {
		this(pixel, frequency);
		this.left = left;
		this.right = right;
	}
	
	
	@Override	//used for sorting
	public int compareTo(HuffmanTreeNode h) {
		
		return  this.frequency -h.frequency;	//ascending based on frequency 
	}
	
	@Override
	public String toString() {
		return "["+pixel+"]="+frequency;
		
	}
	
}
