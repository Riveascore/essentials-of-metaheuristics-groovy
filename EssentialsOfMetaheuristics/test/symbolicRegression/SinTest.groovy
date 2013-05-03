package symbolicRegression

import static org.junit.Assert.*
import spock.lang.*

class SinTest extends Specification{

    def "Test Random"(){
        given:

		Tree tree = new Tree(7,7)
		
		Tree clonedTree = tree.cloneTree()

		GeneticOperators geneticOperators = new GeneticOperators()
		Tree mutatedTree = geneticOperators.mutate(tree)
    }
}
