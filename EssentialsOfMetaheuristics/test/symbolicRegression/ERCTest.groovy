package symbolicRegression

import spock.lang.*

class ERCTest extends Specification{

    @Ignore
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

    @Ignore
    def "printing trees with ERCs"(){
        given:
        Tree tree = new Tree('x', 6, 6)

        tree.printTree()
        expect:
        true
    }
}
