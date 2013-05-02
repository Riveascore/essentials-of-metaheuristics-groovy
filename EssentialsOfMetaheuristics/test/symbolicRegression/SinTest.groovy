package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class SinTest extends Specification{

    def "Test Random"(){
        given:


		Tree tree = new Tree(7,7)
		println tree.root.stringForm()
		println "battleNodes: ${tree.battleNodes}"
		
		Tree clonedTree = tree.clone()
//		
		println "original tree battleNodes before mutation: ${tree.battleNodes}"
//		println "cloned tree battleNodes: ${clonedTree.battleNodes}"
		
		GeneticOperators geneticOperators = new GeneticOperators()
		geneticOperators.mutate(tree)
		
		println "original tree battleNodes after mutation: ${tree.battleNodes}"
		
		
        expect:
		!tree.needBattleNode()
    }
}
