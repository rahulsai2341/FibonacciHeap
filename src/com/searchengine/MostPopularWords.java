package com.searchengine;

import java.util.ArrayList;

public class MostPopularWords {
public static void main(String[] args) {
	int key = 5;
	String keyWord = "Google"; 
	FibonacciHeap f1 = new FibonacciHeap();
	f1.increaseKey(keyWord,key);
	f1.increaseKey("Yahoo",10);
	f1.increaseKey("amazon",3);
	ArrayList<Node> output = f1.getTopNKeywords(3);
	System.out.println(output.get(2).data);
}
}
