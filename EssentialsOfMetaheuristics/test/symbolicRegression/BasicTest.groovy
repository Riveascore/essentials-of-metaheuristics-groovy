package symbolicRegression

import static org.junit.Assert.*
import spock.lang.Specification


class BasicTest extends Specification{
    
    def "Test Terminal"() {
        given:
        Node root = new Node('+')
        root.setLeft(new Node('x'))
        root.setRight(new Node('x')) 
        
        expect:
        root.left.isTerminal()==true
        root.isTerminal()==false
    }
    
    def "Test if nodes (of all the same values) are EQUAL"(){
        given:
        Node node1 = new Node('+')
        Node node2 = new Node('+')
        
        expect:
        node1 == node1
        node1 != node2
    }
    
    def "Test Tree"(){
        given:
        Tree ourTree = new Tree('x', 6)
        
        TreePrinter.printNode(ourTree.root)
        println "Non terminal nodes: " + ourTree.nonTerminalNodes
        ourTree.pickRandomNode()
        println "Random node value: " + ourTree.desiredNode.value
//        println "random child value: " + ourTree.pickRandomNode().value
//        println "Tree's evaluation: " + ourTree.evaluateTree(5)
        
        expect:
        true
    }
    
    def "Test SymbolicRegression"(){
        given:
        SymbolicRegression sr = new SymbolicRegression(20)
        sr.createData()
        
        expect:
        true
    }
   
    
    
}
