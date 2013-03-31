package symbolicRegression

import java.text.DecimalFormat
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

    @Ignore
    def "Trees printing and computing correctly with ERC added"(){
        given:

        Tree tree = new Tree('x', 5, 10)
        tree.allNodes.each{ println "Node value: ${it.value}" }

        println "Tree evaluated at 0.5: ${tree.root.computeNodeValue(0.5)}"
        println "Tree evaluated at 1: ${tree.root.computeNodeValue(1)}"

        tree.printTree()
    }

    @Ignore
    def "How to handle /0"(){
        given:
        Double l = 5.0
        Double r = -5.0 - -5.0
        println r

        Double zero = 0.0
        Double negZero = -0.0
        println zero == negZero
        println l / r
    }

//    @Ignore
    def "Scientific notation output Testing"(){
        given:

        Double number = 23452342343242423
//        System.out.printf("Scientific notation:   %e\n", number)
        printf("%e\n", number)
        
        File fitnessFile = new File("stuff")
        
        fitnessFile.withWriter {
            it << String.format("%e\t%e", number, number)
        }
        expect:
        true
    }
}