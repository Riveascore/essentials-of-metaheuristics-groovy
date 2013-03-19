package symbolicRegression

import java.util.List;

class NodeMaker {
	def parenthesesOffset = 0
	def left = "", right = ""
	def value = null
	def inputString
	def hold = ""
	List nonLeaves = ["+", "-", "*", "/"]

	public NodeMaker(String inputString){
		this.inputString = inputString
	}

	public makeNode(){
		if(inputString.getAt(0) == '('){
			inputString = inputString.getAt((1..inputString.size()-2))
		}

		for(int i = 0; i < inputString.size(); i++){
			hold = inputString.getAt(i)
			switch(hold){
				case '(':
					parenthesesOffset += 1
					add(hold)
					break

				case ')':
					parenthesesOffset -= 1
					add(hold)
					break

				default:
					add(hold)
					break
			}
		}

		Node node = new Node(value)
		if(left.size() != 0){
			NodeMaker nm = new NodeMaker(left)
			Node leftNode = nm.makeNode()
			node.setLeft(leftNode)
		}
		if(right.size() != 0){
			NodeMaker nm = new NodeMaker(right)
			Node rightNode = nm.makeNode()
			node.setRight(rightNode)
		}
		
//		println "full string: " + inputString
//		println "left: " + left +  "    value: " + value + "    right: " + right 
//		println ""
		node
	}

	public add(String input){
		if((parenthesesOffset == 0) && (input != ')')){
			if(nonLeaves.contains(input) || inputString.size() == 1){
				value = input
				return
			}
		}
		if(value != null){
			right += input
		}
		else{
			left += input
		}
	}
}
