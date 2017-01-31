package utils.huffman;

import iconnect.Capture;
import iconnect.Event;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;


public class Huffman {
	
	String rgb[][];
	
	public Huffman(BufferedImage img) {
		
		int rgb[][] = getHistogram(img);
		encodeChannel(rgb[0]);
	//	encode
		
	}
	

	/**Create the hufffman tree**/
	public void encodeChannel(int histogram[]) {
		ArrayList<HuffmanTreeNode> sortedNodes = sortAndRemoveZeros(histogram);
		System.out.println("No of distinct Pixels: "+ sortedNodes.size());
		
	}
	

	ArrayList<HuffmanTreeNode> sortAndRemoveZeros(int histogram[]){
		ArrayList<HuffmanTreeNode> sortedFrequency = new ArrayList<HuffmanTreeNode>();
		
		for(int i = 0; i < histogram.length; i++) {
			
			if(histogram[i]!=0)
				sortedFrequency.add(new HuffmanTreeNode(i, histogram[i]));
		}
		
		System.out.println("After removing zeros: " + sortedFrequency);
		Collections.sort(sortedFrequency);
		System.out.println("After sorting(desc):" +sortedFrequency);
		
		return sortedFrequency;
		
	}
	
	
	/**
	 * @param img
	 * @return calculated histo. index 0 = histo for red channel, 1 for blue
	 */
	public int[][] getHistogram(BufferedImage img) {
		//calc histogram	
		int[][] rgbHisto = new int[3][256];
		for(int row = 0; row<img.getHeight(); row ++) {
			for(int column = 0; column<img.getWidth(); column ++) {
				
				//
				int rgb = img.getRGB(column, row);
				int red = (rgb >> 16) & 0x000000FF;
				int green = (rgb >>8 ) & 0x000000FF;
				int blue = (rgb) & 0x000000FF;
				
				//increment histo
				rgbHisto[0][red]++;
				rgbHisto[1][green]++;
				rgbHisto[2][blue]++;
				
			}
				
		}
		
		
		
			return rgbHisto;
	}
	
	
	public static void main(String[] args) throws IOException {
	//	File imgFile = new File("blue.jpg");
		/*
		Capture capture = new Capture();
		//BufferedImage img = capture.getScreenSnap();
		//img = capture.resize(img, .5);
		System.out.println("capture type:"+img.getType());
		Huffman huffman = new Huffman(img);*/

			String [] e= "749 329 0".split(" ");
			System.out.println("parsed: " + e[0]+" \n"+e[1]+"\n"+e[2]);
		
	}

}
