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
        
//        TreePrinter.printNode(ourTree.root)
//        println "Non terminal nodes: " + ourTree.nonTerminalNodes
//        ourTree.pickRandomNode()
//        println "Random node value: " + ourTree.desiredNode.value
        
        expect:
        true
    }
    
    def "Replace subtree in a generated tree"(){
        given:
        Tree ourTree = new Tree('x', 4)
        Tree replacementBranch = new Tree('x', 4)
        
        println "original tree:"
        TreePrinter.printNode(ourTree.root)
        println "Non terminal nodes: " + ourTree.numberOfNodes
        
        Node newBranch = ourTree.pickRandomNode()
        println "Random node value: " + ourTree.selectedNode.value
        
        println "replacement branch:"
        TreePrinter.printNode(replacementBranch.root)
        
        ourTree.replaceNode(ourTree.selectedNode, replacementBranch.root)
        println "changed tree:"
        TreePrinter.printNode(ourTree.root)
        
        
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
