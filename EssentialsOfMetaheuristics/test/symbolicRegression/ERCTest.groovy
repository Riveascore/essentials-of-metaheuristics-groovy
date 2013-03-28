package symbolicRegression

import spock.lang.Specification;

class ERCTest extends Specification{

    def "Random double -5 to 5 range"(){
        given:

        Tree tree = new Tree('x', 6, 6)

        Double erc
        Boolean inRange = true
        1000.times{
            erc = tree.ERC()
            if(erc < -5.0 || erc > 5.0){
                inRange = false
                println "broken erc: " + erc
            }
        }
        expect:
        inRange
    }

    def "printing trees with ERCs"(){
        given:

        Tree tree = new Tree('x', 6, 6)
        
        println "root " + tree.root.value
        tree.printTree()
    }
}
