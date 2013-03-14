package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*


class BasicTest extends Specification{
    
	@Ignore
    def "Test Terminal"() {
        given:
        Node root = new Node('+')
        root.setLeft(new Node('x'))
        root.setRight(new Node('x')) 
        
        expect:
        root.left.isTerminal()==true
        root.isTerminal()==false
    }
    
	@Ignore
    def "Test if nodes (of all the same values) are EQUAL"(){
        given:
        Node node1 = new Node('+')
        Node node2 = new Node('+')
        
        expect:
        node1 == node1
        node1 != node2
    }
    
	@Ignore
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
	
	@Ignore
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
        
		Random random = new Random()
		Double dubz = 1.0
		println"random numb: " + random.nextDouble()
		
        expect:
        true
    }
    
	@Ignore
    def "Test SymbolicRegression"(){
        given:
        DataSet sr = new DataSet(20)
        sr.createData()
        
		
        expect:
        true
    }
	
	@Ignore
	def "Test tree population"(){
		given:
		Population treePopulation = new Population('x', 4, 10)
		treePopulation.createPopulation(treePopulation.populationSize)
		
		treePopulation.populationSize.times{
			TreePrinter.printNode(treePopulation.population.get(it).root)
		}
		
		expect:
		true
	}
	
	def "Test mating season"(){
		given:
		//SRs right here
		DataSet dataSet = new DataSet(20)
		dataSet.createData()
		
		Population generation1 = new Population('x', 4, 10)
		generation1.createPopulation(generation1.populationSize)
		println "Dataset: " + dataSet.data
		
		generation1.generateFitness(dataSet.data)
//		println "fitness of first: " + generation1.population.get(0).fitness
		
		
//		println "OLD TREEEEEEES: "
//		generation1.populationSize.times{
//			TreePrinter.printNode(generation1.population.get(it).root)
//		}
		
		Population generation2 = generation1.matingSeason()
//		println "------------"
//		println "NEW TREEEES: "
//		generation2.populationSize.times{
//			TreePrinter.printNode(generation2.population.get(it).root)
//		}
		
		expect:
		true
	}
}
