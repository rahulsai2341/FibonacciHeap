# FibonacciHeap
The goal of this project is to implement a system to find the n most popular keywords used in a search engine. For the scope of this 
project keywords and their frequencies are taken as an input file. Basic idea for the implementation is to use a max priority structure 
to find out the most popular keywords.

The following data structures are used:
1. Max Fibonacci heap: Use to keep track of the frequencies of keywords. 
2. Hash table (Hash Map in java): Key for the hash table is keyword and value is pointer to the corresponding node in the Fibonacci heap. 

Max-Heap is implemented in java and the address of all the nodes is stored in a Hash Map.
