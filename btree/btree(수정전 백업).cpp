#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stack>
#include <string>
#include <vector>
#include <queue>
#include <utility>
#pragma warning(disable:4996)
using namespace std;

class BTree {
public:
	FILE* binFile;
	int blockSize,rootBid,depth,entry;

	BTree(const char* fileName) {
		int* buffer = new int[3]{};
		binFile = fopen(fileName, "r+b");
		fread(buffer, 4, 3, binFile);
		blockSize = buffer[0];
		rootBid = buffer[1];
		depth = buffer[2];
		entry = (blockSize - 4) / 8;
	}
	void insert(int key, int val) {
		if (rootBid == 0) {
			int* block = new int[blockSize / 4]{};
			block[0] = key;
			block[1] = val;
			rootBid = 1;
			fseek(binFile, 12 + (rootBid - 1) * blockSize, SEEK_SET);
			fwrite(block, 4, blockSize / 4, binFile);
		}
		else {
			stack<int> st;
			int* block = new int[blockSize / 4]{};
			int tar = rootBid;

			for (int d = 0;d < depth;d++) { // insert할 leaf node 위치 찾기
				st.push(tar);
				fseek(binFile, 12 + (tar - 1) * blockSize, SEEK_SET);
				fread(block, 4, blockSize / 4, binFile);

				for (int j = 0;j <blockSize / 4;j += 2) { // non-leaf BID 비교
					if (block[j] == 0) {
						tar = block[j - 2];
						break;
					}
					else if (j == (blockSize / 4) - 1) {
						tar = block[j];
						break;
					}
					else if (key < block[j + 1]) {
						tar = block[j];
						break;
					}
				}
			}
			insert_non_leaf_node(key, val, tar, st);
		}
	}
	void insert_non_leaf_node(int key, int val, int Bid, stack<int> &st) {
		int* block = new int[blockSize / 4]{};
		bool full = true;

		fseek(binFile, 12 + (Bid - 1) * blockSize, SEEK_SET);
		fread(block, 4, blockSize / 4, binFile);

		for (int j = 0;j < (blockSize / 4)-1;j += 2) {
			if (block[j] == 0) {
				full = false;
				break;
			}
		}
		if (!full) {
			int* new_block = new int[blockSize / 4]{};
			int i;
			for (i = 0;i < (blockSize / 4) - 1;i += 2) {
				if (block[i] != 0 && block[i] < key) {
					new_block[i] = block[i];
					new_block[i+1] = block[i+1];
				}
				else break;
			
			}
			new_block[i] = key;
			new_block[i + 1] = val;
			for (i = i+2;i < (blockSize / 4) - 1;i += 2) {
				new_block[i] = block[i-2];
				new_block[i + 1] = block[i-1];
			}
			new_block[(blockSize / 4) - 1] = block[(blockSize / 4) - 1];
			fseek(binFile, 12 + (Bid - 1) * blockSize, SEEK_SET);
			fwrite(new_block, 4, blockSize / 4, binFile);
		}
		else {
			int* new_block1 = new int[blockSize / 4]{};
			int* new_block2 = new int[blockSize / 4]{};
			int* tmp_block = new int[(blockSize / 4) + 1]{};

			int i;
			for (i = 0;i < (blockSize / 4) - 1;i += 2) {
				if (block[i] < key) {
					tmp_block[i] = block[i];
					tmp_block[i + 1] = block[i + 1];
				}
				else break;

			}
			tmp_block[i] = key;
			tmp_block[i + 1] = val;
			for (i = i + 2;i < (blockSize / 4);i += 2) {
				tmp_block[i] = block[i - 2];
				tmp_block[i + 1] = block[i - 1];
			}

			for (i = 0;i < entry/2;i++) {
				new_block1[2*i] = tmp_block[2 * i];
				new_block1[2*i+1] = tmp_block[2 * i + 1];
			}
			for (i = 0;i <entry/2+1;i++) {
				new_block2[2*i] = tmp_block[2 * (i+ entry / 2)];
				new_block2[2 * i + 1] = tmp_block[2 *(i + entry / 2) + 1];
			}

			fseek(binFile, 0, SEEK_END);
			int new_Bid = (ftell(binFile) - 12) / blockSize+1;

			new_block2[(blockSize / 4) - 1] = block[(blockSize / 4) - 1];
			fwrite(new_block2, 4, blockSize / 4, binFile);

			fseek(binFile, 12 + (Bid - 1) * blockSize, SEEK_SET);
			new_block1[(blockSize / 4) - 1] = new_Bid;
			fwrite(new_block1, 4, blockSize / 4, binFile);


			if (st.size() == 0) { // new_root
				fseek(binFile, 0, SEEK_END);
				int* tmp_block = new int[blockSize / 4]{};
				tmp_block[0] = Bid;
				tmp_block[1] = new_block2[0];
				tmp_block[2] = new_Bid;
				fwrite(tmp_block, 4, blockSize / 4, binFile);
				depth++;
				rootBid = new_Bid + 1;
			}
			else {
				insert_leaf_node(new_block2[0], new_Bid, st);
			}
		}
	}
	void insert_leaf_node(int x, int chile_Bid, stack<int>& st) {
		int* block = new int[blockSize / 4]{};
		bool full = true;
		int Bid = st.top();
		st.pop();

		fseek(binFile, 12 + (Bid - 1) * blockSize, SEEK_SET);
		fread(block, 4, blockSize / 4, binFile);

		for (int j = 0;j < (blockSize / 4);j += 2) {
			if (block[j] == 0) {
				full = false;
				break;
			}
		}
		if (!full) {
			int* new_block = new int[blockSize / 4]{};
			int i;
			new_block[0] = block[0];
			for (i = 1;i < (blockSize / 4);i += 2) {
				if (block[i] != 0 && block[i] < x) {
					new_block[i] = block[i];
					new_block[i + 1] = block[i + 1];
				}
				else break;

			}
			new_block[i] = x;
			new_block[i + 1] = chile_Bid;
			for (i = i + 2;i < (blockSize / 4);i += 2) {
				new_block[i] = block[i - 2];
				new_block[i + 1] = block[i - 1];
			}
			fseek(binFile, 12 + (Bid - 1) * blockSize, SEEK_SET);
			fwrite(new_block, 4, blockSize / 4, binFile);
		}
		else {
			int* new_block1 = new int[blockSize / 4]{};
			int* new_block2 = new int[blockSize / 4]{};
			int* tmp_block = new int[(blockSize / 4) +2]{};

			int i;
			tmp_block[0] = block[0];
			for (i = 1;i < (blockSize / 4);i += 2) {
				if (block[i] < x) {
					tmp_block[i] = block[i];
					tmp_block[i + 1] = block[i + 1];
				}
				else break;

			}
			tmp_block[i] = x;
			tmp_block[i + 1] = chile_Bid;
			for (i = i + 2;i < (blockSize / 4)+2;i += 2) {
				tmp_block[i] = block[i - 2];
				tmp_block[i + 1] = block[i - 1];
			}

			new_block1[0] = tmp_block[0];
			for (i = 0;i < entry / 2;i++) {
				new_block1[2 * i + 1] = tmp_block[2 * i + 1];
				new_block1[2 * i + 2] = tmp_block[2 * i + 2];
			}
			int K = tmp_block[entry / 2 * 2 + 1];
			new_block2[0] = tmp_block[entry / 2 * 2 + 2];
			for (i = 0;i < (entry + 1) / 2 ;i++) {
				new_block2[2 * i+1] = tmp_block[entry / 2 * 2 + 1 + 2 * (i + 1)];
				new_block2[2 * i +2] = tmp_block[entry / 2 * 2 + 2 + 2 * (i + 1)];
			}


			fseek(binFile, 0, SEEK_END);
			int new_Bid = (ftell(binFile) - 12) / blockSize + 1;
			fwrite(new_block2, 4, blockSize / 4, binFile);

			fseek(binFile, 12 + (Bid - 1) * blockSize, SEEK_SET);
			fwrite(new_block1, 4, blockSize / 4, binFile);


			if (st.size() == 0) { // new_root
				fseek(binFile, 0, SEEK_END);
				int* tmp_block = new int[blockSize / 4]{};
				rootBid = (ftell(binFile) - 12) / blockSize + 1;
				tmp_block[0] = Bid;
				tmp_block[1] = K;
				tmp_block[2] = new_Bid;
				fwrite(tmp_block, 4, blockSize / 4, binFile);
				depth++;
			}
			else {
				insert_leaf_node(K, new_Bid, st);
			}
		}

	}
	void print(const char* fileName) {
		FILE* outputFile = fopen(fileName, "w");
		int* block = new int[blockSize / 4]{};
		vector<int> level1;

		if (rootBid == 0) {
			fclose(outputFile);
			return; // no node
		} 

		fputs("[level 0]\n", outputFile);
		fseek(binFile, 12 + (rootBid - 1) * blockSize, SEEK_SET);
		fread(block, 4, blockSize / 4, binFile);
		fputs(to_string(block[1]).c_str(), outputFile);

		level1.push_back(block[0]);
		level1.push_back(block[2]);
		for (int i = 1;i < entry;i++) {
			if (block[2 * i + 1] == 0) break;
			fputs(",", outputFile);
			fputs(to_string(block[2 * i + 1]).c_str(), outputFile);
			level1.push_back(block	[2 * i + 2]);
		}
		if (depth == 1) { // child 존재 & child가 leaf node
			fputs("\n\n[level 1]\n", outputFile);
			int childBid = level1[0];
			fseek(binFile, 12 + (childBid - 1) * blockSize, SEEK_SET);
			fread(block, 4, blockSize / 4, binFile);
			fputs(to_string(block[0]).c_str(), outputFile);
			for (int i = 1;i < entry;i++) {
				if (block[2 * i] == 0) break;
				fputs(", ", outputFile);
				fputs(to_string(block[2 * i]).c_str(), outputFile);
			}
			for (int j = 1;j < level1.size();j++) {
				childBid = level1[j];
				fseek(binFile, 12 + (childBid - 1) * blockSize, SEEK_SET);
				fread(block, 4, blockSize / 4, binFile);
				for (int i = 0;i < entry;i++) {
					if (block[2 * i] == 0) break;
					fputs(",", outputFile);
					fputs(to_string(block[2 * i]).c_str(), outputFile);
				}
			}
		}
		else if (depth > 1) {
			fputs("\n\n[level 1]\n", outputFile);
			int childBid = level1[0];
			fseek(binFile, 12 + (childBid - 1) * blockSize, SEEK_SET);
			fread(block, 4, blockSize / 4, binFile);
			fputs(to_string(block[1]).c_str(), outputFile);
			for (int i = 1;i < entry;i++) {
				if (block[2 * i + 1] == 0) break;
				fputs(",", outputFile);
				fputs(to_string(block[2 * i + 1]).c_str(), outputFile);
			}
			for (int j = 1;j < level1.size();j++) {
				childBid = level1[j];
				fseek(binFile, 12 + (childBid - 1) * blockSize, SEEK_SET);
				fread(block, 4, blockSize / 4, binFile);
				for (int i = 0;i < entry;i++) {
					if (block[2 * i + 1] == 0) break;
					fputs(",", outputFile);
					fputs(to_string(block[2 * i + 1]).c_str(), outputFile);
				}
			}
		}
		fclose(outputFile);
	}
	int search(int key) {
		int* block = new int[blockSize / 4]{};
		int tar = rootBid;

		for (int d = 0;d < depth;d++) { // leaf node 위치 찾기
			fseek(binFile, 12 + (tar - 1) * blockSize, SEEK_SET);
			fread(block, 4, blockSize / 4, binFile);

			for (int j = 0;j < blockSize / 4;j += 2) { // non-leaf BID 비교
				if (block[j] == 0) {
					tar = block[j - 2];
					break;
				}
				else if (j == (blockSize / 4) - 1) {
					tar = block[j];
					break;
				}
				else if (key < block[j + 1]) {
					tar = block[j];
					break;
				}
			}
		}
		fseek(binFile, 12 + (tar - 1) * blockSize, SEEK_SET); 
		fread(block, 4, blockSize / 4, binFile); // read leaf-node
		for (int i = 0;i < entry;i++) {
			if (block[2*i] == key) return block[2*i + 1];
		}
		return 0; // There is no key !!
	}
	vector<pair<int,int>> search(int start, int end) {
		int* block = new int[blockSize / 4]{};
		int tar = rootBid;
		vector<pair<int, int>> range;

		for (int d = 0;d < depth;d++) { // leaf node 위치 찾기
			fseek(binFile, 12 + (tar - 1) * blockSize, SEEK_SET);
			fread(block, 4, blockSize / 4, binFile);

			for (int j = 0;j < blockSize / 4;j += 2) { // non-leaf BID 비교
				if (block[j] == 0) {
					tar = block[j - 2];
					break;
				}
				else if (j == (blockSize / 4) - 1) {
					tar = block[j];
					break;
				}
				else if (start < block[j + 1]) {
					tar = block[j];
					break;
				}
			}
		}
		queue<pair<int,int>> Q;
		fseek(binFile, 12 + (tar - 1) * blockSize, SEEK_SET);
		fread(block, 4, blockSize / 4, binFile); // read leaf-node
		for (int i = 0;i < entry;i++) {
			if (block[2 * i] >= start) {
				Q.push(make_pair(block[2 * i], block[2 * i+1]));
			}
		}
		if (Q.empty()) {
			tar = block[blockSize / 4 - 1];
			if (tar == 0) return {};
			fseek(binFile, 12 + (tar - 1) * blockSize, SEEK_SET);
			fread(block, 4, blockSize / 4, binFile); // read leaf-node
			for (int i = 0;i < entry;i++) {
				if (block[2 * i] >= start) {
					Q.push(make_pair(block[2 * i], block[2 * i + 1]));
				}
			}
		}

		pair<int, int> tmp;
		while (!Q.empty()){
			tmp = Q.front();
			Q.pop();

			if (tmp.first > end) break;
			range.push_back(tmp);
			if (Q.empty()) {
				tar = block[blockSize / 4 - 1];
				if (tar == 0) break;
				fseek(binFile, 12 + (tar - 1) * blockSize, SEEK_SET);
				fread(block, 4, blockSize / 4, binFile); // read leaf-node
				for (int i = 0;i < entry;i++) {
					if (block[2 * i] >= start) {
						Q.push(make_pair(block[2 * i], block[2 * i + 1]));
					}
				}
			}
		}
		return range;
	}
	~BTree() {
		int buffer[] = { rootBid, depth };
		fseek(binFile, 4, SEEK_SET);
		fwrite(buffer, 4, 2, binFile);
		fclose(binFile);
	}
};

int main(int argc, char* argv[])
{
	char command = argv[1][0];

	switch (command)
	{
	case 'c':
	{
		FILE* binFile = fopen(argv[2], "wb");
		int buffer[] = { atoi(argv[3]), 0, 0};
		fwrite(buffer, 4, 3, binFile);
		fclose(binFile);
		break;
	}
	case 'i':
	{
		BTree* myBtree = new BTree(argv[2]);
		FILE* inputFile = fopen(argv[3], "r");
		char line[255];
		int key, val;

		while (fgets(line, sizeof(line), inputFile) != NULL) {
			key = atoi(strtok(line, ","));
			val = atoi(strtok(NULL, ","));
			myBtree->insert(key, val);
		}
		fclose(inputFile);
		delete myBtree;
		break;
	}
	case 's':
	{
		BTree* myBtree = new BTree(argv[2]);
		FILE* inputFile = fopen(argv[3], "r");
		FILE* outputFile = fopen(argv[4], "w");
		char line[255];
		int key,val;
		while (fgets(line, sizeof(line), inputFile) != NULL) {
			key = atoi(line);
			val = myBtree->search(key);
			fputs(to_string(key).c_str(), outputFile);
			fputs(",", outputFile);
			fputs(to_string(val).c_str(), outputFile);
			fputs("\n", outputFile);
		}
		fclose(inputFile);
		fclose(outputFile);
		break;
	}
	case 'r':
	{
		BTree* myBtree = new BTree(argv[2]);
		FILE* inputFile = fopen(argv[3], "r");
		FILE* outputFile = fopen(argv[4], "w");

		char line[255];
		int start, end;
		vector<pair<int, int>> range;
		while (fgets(line, sizeof(line), inputFile) != NULL) {
			start = atoi(strtok(line, ","));
			end = atoi(strtok(NULL, ","));

			range = myBtree->search(start, end);
			for (pair<int, int> tmp : range) {
				fputs(to_string(tmp.first).c_str(), outputFile);
				fputs(",", outputFile);
				fputs(to_string(tmp.second).c_str(), outputFile);
				fputs(" / ", outputFile);
			}
			fputs("\n", outputFile);

		}
		delete myBtree;
		fclose(outputFile);
		fclose(inputFile);
		break;
	}
	case 'p':
		BTree * myBtree = new BTree(argv[2]);
		myBtree->print(argv[3]);
		break;
	}
	return 0;
}