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
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			BufferedWriter wr = new BufferedWriter(new FileWriter("output.txt"));
			String s = br.readLine();
			System.out.println("String read = " + s);
			Pattern p = Pattern.compile("([$])(\\w+)(\\s)(\\d+)");
			Pattern p1 = Pattern.compile("(\\d+)");
			FibonacciHeap f1 = new FibonacciHeap();
			while (!s.equals("stop")) {
				Matcher m = p.matcher(s);
				Matcher m1 = p1.matcher(s);
				if (m.find()) {
					String keyWord = m.group(2);
					int key = Integer.parseInt(m.group(4));
					f1.insertOrIncreaseKey(keyWord, key);

				} else {
					if (m1.find()) {
						int number = Integer.parseInt(m1.group(1));
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
