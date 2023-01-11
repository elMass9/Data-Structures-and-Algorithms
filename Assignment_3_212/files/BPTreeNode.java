/**
 * A B+ tree generic node
 * Abstract class with common methods and data. Each kind of node implements this class.
 * @param <TKey> the data type of the key
 * @param <TValue> the data type of the value
 */
abstract class BPTreeNode<TKey extends Comparable<TKey>, TValue> {
	
	protected Object[] keys;
	protected int keyTally;
	protected int m;
	protected BPTreeNode<TKey, TValue> parentNode;
	protected BPTreeNode<TKey, TValue> leftSibling;
	protected BPTreeNode<TKey, TValue> rightSibling;
	protected static int level = 0; // do not modify this variable's value as it is used for printing purposes. You can create another variable with a different name if you need to store the level.


	protected BPTreeNode() 
	{
		this.keyTally = 0;
		this.parentNode = null;
		this.leftSibling = null;
		this.rightSibling = null;
	}

	public int getKeyCount() 
	{
		return this.keyTally;
	}
	
	@SuppressWarnings("unchecked")
	public TKey getKey(int index) 
	{
		return (TKey)this.keys[index];
	}

	public void setKey(int index, TKey key) 
	{
		this.keys[index] = key;
	}

	public BPTreeNode<TKey, TValue> getParent() 
	{
		return this.parentNode;
	}

	public void setParent(BPTreeNode<TKey, TValue> parent) 
	{
		this.parentNode = parent;
	}	
	
	public abstract boolean isLeaf();
	
	/**
	 * Print all nodes in a subtree rooted with this node
	 */
	@SuppressWarnings("unchecked")
	public void print(BPTreeNode<TKey, TValue> node)
	{
		level++;
		if (node != null) {
			System.out.print("Level " + level + " ");
			node.printKeys();
			System.out.println();

			// If this node is not a leaf, then 
        		// print all the subtrees rooted with this node.
        		if (!node.isLeaf())
			{	BPTreeInnerNode inner = (BPTreeInnerNode<TKey, TValue>)node;
				for (int j = 0; j < (node.m); j++)
    				{
        				this.print((BPTreeNode<TKey, TValue>)inner.references[j]);
    				}
			}
		}
		level--;
	}

	/**
	 * Print all the keys in this node
	 */
	protected void printKeys()
	{
		System.out.print("[");
    		for (int i = 0; i < this.getKeyCount(); i++)
    		{
        		System.out.print(" " + this.keys[i]);
    		}
 		System.out.print("]");
	}


	////// You may not change any code above this line. You may add extra variables if need be //////

	////// Implement the functions below this line //////
	
	
	
	/**
	 * Search a key on the B+ tree and return its associated value using the index set. If the given key 
	 * is not found, null should be returned.
	 */
	/*public TValue search(TKey key) 
	{
		// Your code goes here
	}*/



	/**
	 * Insert a new key and its associated value into the B+ tree. The root node of the
	 * changed tree should be returned.
	 */
	public BPTreeNode[] extras;
	public BPTreeNode<TKey, TValue> insert(TKey key, TValue value) 
	{
		// Your code goes here
		extras = new BPTreeNode[m+1];
		BPTreeNode<TKey, TValue> root = this;
		if(this.keyTally==0){
			BPTreeLeafNode<TKey, TValue> leaf = (BPTreeLeafNode<TKey, TValue>) this;
			leaf.keys[0] = key;
			leaf.setValue(0, value);
			leaf.isLeaf();
			leaf.keyTally++;
			return this;
		}else{
			BPTreeNode<TKey, TValue> ptr = root;
			BPTreeNode<TKey, TValue> temp;
			while(!ptr.isLeaf()){
				temp = ptr;
				int i;
				for(i = 0; i<ptr.keyTally;i++){
					if(key.compareTo((TKey) ptr.keys[i])<0){
						((BPTreeInnerNode) ptr).setChild(i, ptr);
						break;
					}
					if(i==ptr.keyTally-1){
						((BPTreeInnerNode) ptr).setChild(i+1, ptr);
						break;
					}
				}
			}
			if(ptr.keyTally < m){
				int i = 0;
				while(key.compareTo((TKey) ptr.keys[i])>0 && i < ptr.keyTally){
					i++;
				}
				for(int x = ptr.keyTally; x>i; x--){
					ptr.keys[x] = ptr.keys[x-1];
				}
				ptr.keys[i] = key;
				ptr.keyTally++;
				ptr.extras[ptr.keyTally] = ptr.extras[ptr.keyTally-1];
				ptr.extras[ptr.keyTally-1] = null;
			}else{
				BPTreeLeafNode<TKey, TValue> nLeaf = new BPTreeLeafNode<TKey, TValue>(m);
				Object [] vNode = new Object[m+1];
				int z = 0, y;
				for(int i=0; i<m; i++){
					vNode[i] = ptr.keys[i];
				}
				while(key.compareTo((TKey)vNode[z])>0 && z < m){
					z++;
				}
				for(y = m+1; y>z;y--){
					vNode[y] = vNode[y-1];
				}
				vNode[z] = key;
				nLeaf.isLeaf();
				ptr.extras[nLeaf.keyTally] = ptr.extras[m];
				ptr.extras[m] = null;
				for(z=0, y =ptr.keyTally; z<nLeaf.keyTally; z++,y++){
					nLeaf.keys[z] = vNode[y];
				}
				if(ptr==root){
					BPTreeLeafNode<TKey, TValue> newNode = new BPTreeLeafNode<TKey, TValue>(m);

				}
			}
		}
		return root;
		
	}



	/**
	 * Delete a key and its associated value from the B+ tree. The root node of the
	 * changed tree should be returned.
	 */
	/*public BPTreeNode<TKey, TValue> delete(TKey key) 
	{
		// Your code goes here
	}*/



	/**
	 * Return all associated key values on the B+ tree in ascending key order using the sequence set. An array
	 * of the key values should be returned.
	 */
	/*@SuppressWarnings("unchecked")
	public TValue[] values() 
	{
		// Your code goes here
	}*/

}