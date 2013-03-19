package symbolicRegression

class Node {
    def value
    Node parent, left, right
    def directionFromParent
	
    //TODO discover limit on "depth" of tree
    //TODO ^not sure if should be def?
    
    List nonTerminals = ["+", "-", "*", "/"]
    
    public Node(value){
        this.value = value
    }
	
	public Node(){
	}
	
	public stringForm(){
		if(this.nonTerminals.contains(this.value)){
			return "(" + this.left.stringForm() + this.value + this.right.stringForm() + ")"
		}
		else {
			this.value
		}
	}
	
	public cloneNode(){
		Node node = new Node(this.value)
		
		if(this.left != null){
			node.setLeft(this.left.cloneNode())
		}
		if(this.right != null){
			node.setRight(this.right.cloneNode())
		}
		return node
	}
	
	
	public getNodeHeight(){
		Integer height = 1
		Integer leftHeight =0
		Integer rightHeight =0
		Integer maxHeight = 0
		
		if(this.left != null){
			leftHeight = this.left.getNodeHeight()
		}
		else if(this.right != null){
			rightHeight = this.right.getNodeHeight()
		}
			maxHeight=Math.max(leftHeight, rightHeight)
			return height + maxHeight		
	}
	
    public setValue(def value){
        this.value = value
    }
    
    public setParent(Node parent){
        this.parent = parent
    }
    
    public setLeft(Node left){
        left.setParent(this)
		this.left = left
        this.left.directionFromParent = "left"
    }
	
	public setRight(Node right){
		right.setParent(this)
		this.right = right
		this.right.directionFromParent = "right"
	}
    
    public setDirectionFromParent(String direction){
        this.directionFromParent = direction
    }
    
    public isTerminal(){
        !nonTerminals.contains(this.value)
    }
	
	public getDepth(){
		if(this.parent != null){
			return this.parent.getDepth() + 1
		}
		0
	}
    
    public computeNodeValue(def input){
        switch(this.value){
            case '+': 
            this.left.computeNodeValue(input) + this.right.computeNodeValue(input)
            break
            
            case '-': 
            this.left.computeNodeValue(input) - this.right.computeNodeValue(input)
            break
            
            case '*': 
            this.left.computeNodeValue(input) * this.right.computeNodeValue(input)
            break
            
            case '/': 
            if(this.right.computeNodeValue(input) == 0){
                1
            }
            else{
                this.left.computeNodeValue(input) / this.right.computeNodeValue(input)
            }
            //^Used the return 1 as a guard against division by 0
            break
            default: input
            break
            //TODO possibly implement sin, cos, tan, abs, sqrt, log if we have time
        }
    }
	
	
	//TODO RULES FOR SIMPLIFICATION:
	/* x/x -> 1
	 * x-x -> 0
	 * anything/0 -> 1
	 * 
	 */
	
//	public simplify(){
//		switch(this.value){
//			case '+':
//			this.left.computeNodeValue(input) + this.right.computeNodeValue(input)
//			break
//			
//			case '-':
//			this.left.computeNodeValue(input) - this.right.computeNodeValue(input)
//			break
//			
//			case '*':
//			this.left.computeNodeValue(input) * this.right.computeNodeValue(input)
//			break
//			
//			case '/':
//			if(this.right.computeNodeValue(input) == 0){
//				1
//			}
//			else{
//				this.left.computeNodeValue(input) / this.right.computeNodeValue(input)
//			}
//			//^Used the return 1 as a guard against division by 0
//			break
//			default: input
//			break
//			//TODO possibly implement sin, cos, tan, abs, sqrt, log if we have time
//		}
//	}
}


