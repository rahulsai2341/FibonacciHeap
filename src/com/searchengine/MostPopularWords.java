package com.searchengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostPopularWords {
	public static void main(String[] args) throws IOException {
		String path = "C:\\Users\\Rahul\\eclipse-workspace\\MaxSearchedWord\\millionInput.txt";
		//String fileName = args[0];
		try {
			//Initiate buffered reader
			BufferedReader br = new BufferedReader(new FileReader(path));
			//Initiate buffered writer
			BufferedWriter wr = new BufferedWriter(new FileWriter("output_file.txt"));
			//Compile patterns for Reg-Ex
			Pattern p = Pattern.compile("([$])(\\w+)(\\s)(\\d+)");
			Pattern p1 = Pattern.compile("(\\d+)");
			//Initiate Fibonacci heap object
			FibonacciHeap f1 = new FibonacciHeap();
			
			//Read line by line from input file
			String s = br.readLine();
			while (!s.equals("stop")) {
				//Match for patterns in input line
				Matcher m = p.matcher(s);
				Matcher m1 = p1.matcher(s);
				//If we find a keyWord
				if (m.find()) {
					String keyWord = m.group(2);
					int key = Integer.parseInt(m.group(4));
					//Insert or increase key of this keyWord
					f1.insertOrIncreaseKey(keyWord, key);

				} else {
					//If we find a query
					if (m1.find()) {
						int number = Integer.parseInt(m1.group(1));
						//Get top n searched words
						ArrayList<String> output = f1.getTopNKeywords(number);
						StringBuilder sb = new StringBuilder();
						for (String n : output) {
							sb.append(n);
							sb.append(",");

						}
						sb.deleteCharAt(sb.length() - 1);
						wr.write(sb.toString());
						wr.newLine();
					}
				}
				s = br.readLine();
			}
		} catch (Exception e) {
			System.out.println((e.toString()));
		}

	}
}
