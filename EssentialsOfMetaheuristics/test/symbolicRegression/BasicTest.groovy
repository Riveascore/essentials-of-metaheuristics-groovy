package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*


class BasicTest extends Specification{
    
	Random random = new Random()
	
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
	
	def "matingSeason Test"(){
		given:
		
		Population generation1 = new Population('x', 3, 10, 6)
		generation1.createPopulation()
		
		DataSet dataSet = new DataSet(20)
		dataSet.createData()
		generation1.generateFitness(dataSet.data)
		
		Population generation2 = generation1.matingSeason()
		
//		println "OLD TREEEEEEES: "
//		generation1.populationSize.times{
//			generation1.population.get(it).printTree()
//		}
//		
//		
//		println "------------"
//		println "NEW TREEEES: "
//		generation2.populationSize.times{
//			generation2.population.get(it).printTree()
//		}
		
		expect:
		generation2.populationSize == generation1.populationSize
	}
	
	def "test clone node"(){
		given:
		Node first = new Node('x')
		Node clone = first.cloneNode()
		
		first.setValue('y')
		
		expect:
		first.value == 'y'
		clone.value == 'x'
	}
	
	@Ignore
	def "Test if changing a tree changes is globally"(){
		given:
		
		Tree tree = new Tree('x', 3)
		tree.createTree()
		
		Node changedNode = new Node('Changed Node')
		
		println "Original Tree before"
		TreePrinter.printNode(tree.root)
		
		Tree clonedTree = new Tree(tree.terminalValue, tree.depthLimit)
		clonedTree.root = tree.root.cloneNode()
		
		//replace a node
		tree.replaceNode(tree.pickRandomNode(), changedNode)
		
		println "Original Tree after"
		TreePrinter.printNode(tree.root)
		
		println "copied tree"
		TreePrinter.printNode(clonedTree.root)
		
		expect:
		true
	}
	
	@Ignore
	def "Test mutation on a tree"(){
		given:
		
		Population generation0 = new Population('x', 3, 10, 5)
		generation0.createPopulation(generation0.populationSize)
		
		println "Original Tree before"
		generation0.population.get(0).printTree()
		
		Tree mutatedTree = generation0.mutate(generation0.population.get(0))
		
		println "Original Tree after"
		generation0.population.get(0).printTree()
		
		println "Final mutated tree!!!!!!!"
		mutatedTree.printTree()
		
		expect:
		true
	}
	
	@Ignore
	def "Crossover Test"(){
		given:
		Population population = new Population('x', 3, 10, 5)
		population.createPopulation()
		
		DataSet dataSet = new DataSet(20)
		dataSet.createData()
		population.generateFitness(dataSet.data)

		Tree crossedOverTree = population.crossover()
		
		println "originalTree: "
		crossedOverTree.printTree()
		
		println "crossedOverTree: "
		crossedOverTree.printTree()
		
		expect:
		true
	}
	
	@Ignore
	def "Tournament Selection Test"(){
		given:
		Population population = new Population('x', 3, 10, 5)
		population.createPopulation()
		
		DataSet dataSet = new DataSet(20)
		dataSet.createData()
		population.generateFitness(dataSet.data)
		
		Tree tournamentWinner = population.tournamentSelection(population.populationSize)
		
		println "tournamentWinner: "
		tournamentWinner.printTree()
		
		expect:
		true
	}
	
	@Ignore
	def "Test make population"(){
		given:
		Population population = new Population('x', 3, 10, 5)
		DataSet dataSet = new DataSet(20)
		dataSet.createData()
		population.createPopulation()
		
		println "Original Trees"
		population.populationSize.times{
			population.population.get(it).printTree()
		}
		
		expect:
		true
	}
}
