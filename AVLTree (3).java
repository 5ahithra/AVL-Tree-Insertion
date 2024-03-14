//Sahithra Kesavan
//THIS file is to be a AVL Tree that has all the methods to show and replicate a correct AVL Tree

public class AVLTree <E extends Comparable<E>> extends SearchTree<E>
{
    
    public boolean add(E data)
    {
        return insert(data);
    }

    // This method should use a recursive helper.  It returns
	// the tree that results from inserting data 
	// into t. It has a similar structure to add from BST.  It
	// starts by asking if tree is empty, and if so it sets the
	// a new leaf node as the root. If tree is not empty you can compare
	// data to the current node's data and take action based on this comparison.
	// When you create a new leaf node you should 
	// increment the tree's size variable. When you return from a 
	// recursive call that updates one of t's children
	// (e.g. curr.leftChild = insertHelper(data, curr.left))
	// you should check the AVL property and possibly 
	// call updateHeight(), and then recompute curr's height.
    public boolean insert(E value) //insert method with use of insertHelper method
    {
        if (contains(value)) 
        {
            return false;
        }
        
        
        overallRoot = insertHelper((AVLNode<E>) overallRoot, value); //calling recursive method
        size++; //increasing tree size
        return true;
    }
	
   //Recursive insert helper
    private AVLNode<E> insertHelper(AVLNode<E> node, E value)
    {
        if (node == null) 
        {
            return new AVLNode<E> (value); //creating new node if current node is null
        }
    
        if (value.compareTo(node.data)<0)  //recursively insert value into correct subtree
        {
            node.left = insertHelper((AVLNode<E>) node.left, value);
        } 
        else 
        {
            node.right = insertHelper((AVLNode<E>) node.right, value);
        }

        if(node.bf()>1) //checking bf and preforming rotations
        {
        	if(((AVLNode<E>)node.right).bf()<0)
        	{
        		node.right = rotateRight((AVLNode<E>)node.right);
        	}
        	return rotateLeft(node);
        }
        else if(node.bf()<-1) 
        {
        	if(((AVLNode<E>)node.left).bf()>0)
        	{
        		node.left = rotateLeft((AVLNode<E>)node.left);
        	}
        	return rotateRight(node);
        }
        node.updateHeight(); //updating the height of current noode.
 
        return node;
    }

    // TODO: IMPLEMENT THE FOLLOWING METHODS BASED ON THE JAVADOC COMMENTS
  
    /**
     * Perform a single rotation to the right of a tree rooted at the current node.
     * Consider the following illustrations (called on the node A):
     *
     *        A       =>     B
     *       / \      =>    / \
     *      B   T3    =>  T1   A
     *     / \        =>      / \
     *   T1   T2      =>    T2   T3
     *
     * Note that A's original parent (if it exists) will need to become B's new
     * parent. 
     *
     * @return The new root of this subtree (node B).
     */
    
    
    // TODO: Implement this method.  Return the new root B.
    // Do not forget to change A's parent (if it exists) to be
    // aware of B as the new root by returning the new root and setting the
    // parent's pointer when we call rotateRight(node).
   
    public AVLNode<E> rotateRight(AVLNode<E> oldRoot) //performing right rotation 
    {
        AVLNode<E> nR = (AVLNode<E>) oldRoot.left; //root B
        oldRoot.left = nR.right; // setting the root A left child to T2
        nR.right = oldRoot;
        oldRoot.updateHeight(); //updating heights of old and new roots
        nR.updateHeight();
        return nR; //returning the new root
    }

    /**
     * Perform a single rotation to the left of a tree rooted at the current node.
     * Consider the following illustrations (called on the node A):
     *
     *      A         =>       B
     *     / \        =>      / \
     *   T1   B       =>     A   T3
     *       / \      =>    / \
     *     T2   T3    =>  T1   T2
     *
     * Note that A's original parent (if it exists) will need to become B's new
     * parent. 
     *
     * @return The new root of this subtree (node B).
     */
    

    public AVLNode<E> rotateLeft(AVLNode<E> oldRoot) //preforming left rotation
    {
    	System.out.println("left");
    	
    	AVLNode<E> nR = (AVLNode<E>) oldRoot.right; //root B
        oldRoot.right = nR.left; //setting node A's right child to T2
        nR.left = oldRoot;
        oldRoot.updateHeight(); //updating new and old root geights
        nR.updateHeight();
        return nR; //returnign new root
    }

    // WAIT TO WORK ON THIS!!!
    public boolean remove(E num) //removing given elements from the tree
    {
		if (!contains(num))
		{
            return false; //element isnt in the tree
        }
		
		size--; //decreasing tree size
		removeHelper((AVLNode<E>) overallRoot, num); //calling remove recrusive helper method
		return true;
	}
	
    //recursive Helper method for remove
	public AVLNode<E> removeHelper(AVLNode<E> node, E value)
	{
		//different cases for removing node
		if (node.data == value) 
		{
			
			if(node.left==null&&node.right==null) //node is a leaf node (has no chuldren)
			{
				node = null;
			}
			
		
			else if (node.left!=null&&node.right==null) //node has a left child
			{
				node = (AVLNode<E>) node.left;
				node.left = null;
			}
			
		
			else if (node.left==null&&node.right!=null) //node has right child
			{
				node = (AVLNode<E>) node.right;
				node.right = null;
			}
			
		
			else //node has both left and right child
			{
				
				node.data = minNode(node).data; //find min node in right subtree so we can replace the current node
				node.right.left = minNode(node).right; //removing duplicate node from the righ tsubtree
				
			}
			
		
            return node;
        }


        if (value.compareTo(node.data)<0) //recursively remove the value from the correct subtree :)
        {
            node.left = removeHelper(node.left, value);
        } else 
        {
            node.right = removeHelper(node.right, value);
        }

		return node;
	}

    // This will help you with debugging. It prints the keys
   // on each level of the tree.
   public void treePrinter() {
    for (int level = 0; level < ((AVLNode<E>)overallRoot).height(); level++ ) {
        System.out.printf("Level %d: ", level);
        printLevel(level, (AVLNode<E>)overallRoot);
        System.out.println();
    }
}

    public void printLevel(int level, AVLNode<E> t) {
        if (t != null) {
            if (level == 0)
                System.out.printf(  "%s ", t.data);
            else {
                printLevel(level-1, (AVLNode<E>)t.left);
                printLevel(level-1, (AVLNode<E>)t.right);
            }
        }
    }
}



