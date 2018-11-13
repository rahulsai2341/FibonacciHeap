package com.searchengine;
import java.util.ArrayList;
import java.util.Hashtable;

class Node {
	int degree = 0;
	Node child = null;
	int data = 0;
	Node leftNode = null;
	Node rightNode = null;
	Node parent = null;
	Character childCut = 'F';
	String keyword = null;
}

public class FibonacciHeap {
	Node max;
	Hashtable<String, Node> h1 = new Hashtable<String, Node>();

	public void insertOrIncreaseKey(String keyWord, int n) {
		if (!h1.containsKey(keyWord)) {
			insertIntoHeap(keyWord, n);
		} else {
			if (h1.containsKey(keyWord)) {
				increaseKey(keyWord, n);
			}
		}
	}

	// Insert method
	public void insertIntoHeap(String keyWord, int n) {
		// When a new keyword is inserted
		// If the top level circular list is empty
		if (max == null) {
			h1.put(keyWord, new Node());
			Node n1 = h1.get(keyWord);
			n1.keyword = keyWord;
			n1.child = null;
			n1.data = n;
			n1.childCut = 'F';
			n1.parent = null;
			n1.leftNode = n1;
			n1.rightNode = n1;
			n1.degree = 0;
			max = n1;
		}
		// Inserting a new node in the top level list
		else {
			h1.put(keyWord, new Node());
			Node n1 = h1.get(keyWord);
			n1.keyword = keyWord;
			n1.data = n;
			n1.degree = 0;
			n1.leftNode = max;
			n1.rightNode = max.rightNode;
			max.rightNode.leftNode = n1;
			max.rightNode = n1;
			// update max pointer
			if (max.data < n1.data) {
				max = n1;
			}
		}

	}

	// Increase key method
	public void increaseKey(String keyWord, int n) {
		// When an existing keyword's value is increased
		Node n1 = h1.get(keyWord);
		n1.data = n1.data + n;
		// When we increment the value of a node in top level chain
		if (n1.parent == null) {
			if (max.data < n1.data) {
				max = n1;
			}
			return;
		}
		// When the new value is less than parent value
		if (n1.parent.data > n1.data) {
			return;
		}
		// When the above conditions fail, remove the node and add it to top level list.
		Node n1_parent = n1.parent;
		do {
			// Update the child pointer of the parent if it was pointing to this node
			if (n1.parent.child == n1) {
				if(n1.leftNode==n1 && n1.rightNode==n1) {n1.parent.child=null;}
				if (n1.leftNode != n1) {
					n1.parent.child = n1.leftNode;
				} else if (n1.rightNode != n1) {
					n1.parent.child = n1.rightNode;
				}
			}
			// Updating sibling pointers
			n1.leftNode.rightNode = n1.rightNode;
			n1.rightNode.leftNode = n1.leftNode;
			// Set parent pointer to null as it will be added to top level chain
			n1.parent = null;
			// Update ChildCut value
			n1.childCut = 'F';
			// Updating degree value of the parent
			n1_parent.degree--;
			// Adding this node near the max node
			n1.leftNode = max;
			n1.rightNode = max.rightNode;
			max.rightNode = n1;
			n1.rightNode.leftNode = n1;
			// Updating the max pointer
			if (max.data < n1.data) {
				max = n1;
			}
			n1 = n1_parent;
			n1_parent = n1_parent.parent;
		} // Cascading Cut
		while (n1.childCut == 'T');
		// update childCut value as this node lost a child
		if (n1.parent != null) {
			n1.childCut = 'T';
		}
	}

	
	// get top n most searched keywords
	public ArrayList<String> getTopNKeywords(int n) {
		if (max == null) {
			return null;
		}
		// Remove the max element and perform pairwise combine.Repeat this n times
		ArrayList<Node> outArray = new ArrayList<Node>();
		for (int i = 1; i <= n; i++) {
			outArray.add(max);
			// Add each child of max to top level list
			addEachChildToTopLevelList();
			// Update child pointer of max node to null and degree to 0
			max.child = null;
			max.degree = 0;
			Node tempMax = max.leftNode;
			// Remove max node from top level list
			// If there is only one element in the top level list
			if (max.leftNode == max && max.rightNode == max) {
				max = null;
				break;
			} else {
				max.leftNode.rightNode = max.rightNode;
				max.rightNode.leftNode = max.leftNode;
				max.leftNode = null;
				max.rightNode = null;
				// PAIRWISE COMBINE the remaining nodes in the top level list using tempMax
				pairWiseCombineTopLevelList(tempMax);
			}

		} // first for loop
		ArrayList<String> mostSearchedWords = new ArrayList<String>();
		for (Node node : outArray) {
			mostSearchedWords.add(node.keyword);
		}
		reinsertNodesIntoFibonacciHeap(outArray);
		return mostSearchedWords;

	}
	public void reinsertNodesIntoFibonacciHeap(ArrayList<Node> outArray) {
		for(Node n:outArray) {
			insertIntoHeap(n.keyword, n.data);
		}
	}
	
	public void pairWiseCombineTopLevelList(Node tempMax) {
		int maxdegree = 0;
		Node listIterator1 = tempMax;
		// Find max degree
		int sizeOfList = 1;
		while (listIterator1.rightNode != tempMax) {
			if (listIterator1.degree > maxdegree) {
				maxdegree = listIterator1.degree;
			}
			sizeOfList++;
			listIterator1 = listIterator1.rightNode;
		}
		// Create a treeTable with maxDegree as size of it
		Node[] treeTable = new Node[100];
		Node listIterator = tempMax;
		// Traverse through the array to find trees which have same degree
		do {
			Node n1 = listIterator;
			Node n2 = listIterator;
			listIterator = listIterator.rightNode;
			while (true) {
				// If treeTable value at the index is null, insert this node
				if (treeTable[n1.degree] == null) {
					treeTable[n1.degree] = n1;
					break;
				}
				// Else we found two trees with same degree
				else {
					n2 = treeTable[n1.degree];
					// Update treeTable
					treeTable[n2.degree] = null;
					if(n1.data>n2.data) {
					combineTwoTreesWithSameDegree(n1, n2);}
					else{
						Node temp = n1;
						n1 = n2;
						n2 = temp;
						combineTwoTreesWithSameDegree(n1, n2);
					}
				} // else found nodes with same degrees
			}
			
		} while (listIterator != tempMax);
		// Forming the new top level list using the treeTable and updating max pointer
		max = null;
		for(Node currentNode:treeTable) {
			if(currentNode!=null) {
			currentNode.leftNode=currentNode;
			currentNode.rightNode = currentNode;
			}
		}
		for (Node currentNode : treeTable) {
			if (currentNode != null) {
				if (max == null) {
					max = currentNode;
				} else {
					currentNode.leftNode = max;
					currentNode.rightNode = max.rightNode;
					max.rightNode.leftNode = currentNode;
					max.rightNode = currentNode;					
					if (max.data < currentNode.data) {
						max = currentNode;
					}
				}
			}
		}
	}


	// Add each child of max to top level list
	public void addEachChildToTopLevelList() {
		int degree = max.degree;
		for (int j = 1; j <= degree; j++) {
			Node childOfmax = max.child;
			childOfmax.parent = null;
			// update siblings
			childOfmax.leftNode.rightNode = childOfmax.rightNode;
			childOfmax.rightNode.leftNode = childOfmax.leftNode;
			// change child pointer to remaining child
			max.child = childOfmax.leftNode;
			// Add this node to top level list
			childOfmax.rightNode = max.rightNode;
			childOfmax.leftNode = max;
			max.rightNode.leftNode = childOfmax;
			max.rightNode = childOfmax;
			childOfmax.childCut = 'F';
		}
	}

	public void combineTwoTreesWithSameDegree(Node n1, Node n2) {
		// n1 has bigger data make it as the parent of n2
			n2.parent = n1;
			// Make n2 child of n1. Update sibling pointers to ifself.
			// degree is 0
			if (n1.child == null) {
				n1.child = n2;
				n2.leftNode = n2;
				n2.rightNode = n2;
			} // Add n2 to the child siblings linked list of n1.
			else {
				n2.leftNode = n1.child;
				n2.rightNode = n1.child.rightNode;
				n1.child.rightNode.leftNode = n2;
				n1.child.rightNode = n2;
			}
			n1.childCut='F';
			n1.degree++;
	}
}
