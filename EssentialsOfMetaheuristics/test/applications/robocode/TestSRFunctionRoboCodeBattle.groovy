package applications.robocode

import spock.lang.Specification
import symbolicRegression.Tree

/*
 * This assumes that there is a copy of Robocode in your home directory,
 * and that it has been configured (via the GUI) to be able to load robot
 * files from the evolved_robots directory in this project.
 */
class TestSRFunctionRoboCodeBattle extends Specification {
	/*
	 * id : an id used in the generation of the name of the class.
	 * enemy_energy : the coefficient for the enemy's energy
	 * my_energy : the coefficient for our energy
	 * angle_diff : the coefficient for the different in angles between us and the point and then and the point
	 * distance : the coefficient for the distance between the point and the enemy
	 */
	def id
	def functionString
	def robotBuilder
	def battleRunner

	def setup() throws Exception {
		Tree tree = new Tree(5, 5)
		functionString = println tree.root.stringForm()
		
		Random random = new Random()
		id = random.nextInt(1000000)
		
//		functionString = "((1.234*mE)+((0.00349*eE)+((0.3939393*(mA-eA))+(289.3939*d))))"

		def values = ["id" : id, "functionString" : functionString]

		robotBuilder = new RobotBuilder("templates/PenaBotOS.template")
		robotBuilder.buildJarFile(values)

		battleRunner = new BattleRunner("templates/battle.template")
	}

	def "Check that the battle file is correctly constructed"() throws Exception {
		when:
		battleRunner.buildBattleFile(id)

		then:
		confirmBattleFile()
	}

	def "Check that we can run a battle and extract the scores"() throws Exception {
		given:
		battleRunner.buildBattleFile(id)

		when:
		def score = battleRunner.runBattle(id)

		then:
		score >= 0
	}

	def confirmBattleFile() throws Exception {
		File file = new File("evolved_robots/evolve.battle")
		def contents = file.readLines()
		def interestingLines = contents.findAll { line ->
			(line.indexOf("robocode.battle.selectedRobots") >= 0)
		}
		assert interestingLines.size() == 1
		assert interestingLines[0].indexOf("sample.Walls") >= 0
		assert interestingLines[0].indexOf("evolved.Individual_${id}") >= 0
		return true
	}
}