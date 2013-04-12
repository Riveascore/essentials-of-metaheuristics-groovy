package applications.robocode

import spock.lang.*

class MessAroundTest extends Specification{
	
	def "Groovy string built in vars"(){
		given:
		thing = ["id" : id]
		
		BattleRunner ourBattleRunner = new BattleRunner("templates/battle.template")
		ourBattleRunner.buildBattleFile("5")
		
		
		
		expect:
		true
	}
}
