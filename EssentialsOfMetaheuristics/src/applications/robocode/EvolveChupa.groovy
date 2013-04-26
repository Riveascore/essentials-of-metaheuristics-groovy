package applications.robocode

import java.awt.Robot.RobotDisposer;

import symbolicRegression.Tree

class EvolveChupa {


	static Random random = new Random()

	static def userHome = System.getProperty("user.home")
	static def evolved_robots = "${userHome}/git/essentials-of-metaheuristics-groovy/EssentialsOfMetaheuristics/evolved_robots"
	static def evolved = "${evolved_robots}/evolved"

	static def bestMeleeScore
	static def bestMeleeFunction
	static def currentMeleeScore
	static def currentMeleeFunction

	//	def bestMeleeFuction
	//	def bestMeleeScore

	static main(args) {
		
		
		1.times {
			melee()
		}
	}
	
	public static def melee(){
		def id = "Chupa"
		def robotBuilder
		def battleRunner
		def functionString
		def scores
		def command
		def bestOneVOneFuction
		def bestOneVOneScore
		
		def totalScore

		Tree tree = new Tree(6, 6)
		functionString = tree.root.stringForm()
		currentMeleeFunction = functionString

		//^not need these two

		def values = ["id" : id, "functionString" : functionString]

		robotBuilder = new RobotBuilder("templates/ChupaCabra.template")
		robotBuilder.buildJarFile(values)
		battleRunner = new MeleeBattleRunner("templates/melee.template")

		battleRunner.buildBattleFile(id)

		scores = battleRunner.runBattle(id)
		
		println "Robot ${id}'s eval function:"
		println "${functionString}"
		println "Score for ${id} is: ${scores[0]}"
		println "Score for HawkOnFire is: ${scores[1]}\n"

		command = "rm ${evolved_robots}/${id}.jar"
		command.execute()
		//remove symbolic link to .jar
		command = "rm ${userHome}/robocode/robots/${id}.jar"
		command.execute()
		
		
		//Cuz apparently rm Word* doesn't work...
		command = "rm ${evolved}/${id}.java"
		command.execute()
		command = "rm ${evolved}/${id}.class"
		command.execute()
		command = "rm ${evolved}/${id}\$microEnemy.class"
		command.execute()
		command = "rm ${evolved}/${id}.properties"
		command.execute()
		
		return scores
	}
	
	
	
	
	public static def oneVone(){
		def id = "Chupa"
		def robotBuilder
		def battleRunner
		def functionString
		def scores
		def command
		def bestOneVOneFuction
		def bestOneVOneScore

		Tree tree = new Tree(6, 6)
		functionString = tree.root.stringForm()

//		functionString = "((calcAngle(en.pos, p)*en.energy)*(p.distanceSq(en.pos)*((calcAngle(myPos, p)*(myEnergy*p.distanceSq(en.pos)))+0.830591254201666)))"

		println "Robot ${id}'s eval function: ${functionString}"
		//^not need these two

		def values = ["id" : id, "functionString" : functionString]

		robotBuilder = new RobotBuilder("templates/ChupaCabra.template")
		robotBuilder.buildJarFile(values)
		battleRunner = new OurBattleRunner("templates/oneVone.template")

		battleRunner.buildBattleFile(id)

		scores = battleRunner.runBattle(id)

		command = "rm ${evolved_robots}/${id}.jar"
		command.execute()
		//remove symbolic link to .jar
		command = "rm ${userHome}/robocode/robots/${id}.jar"
		command.execute()
		
		
		//Cuz apparently rm Word* doesn't work...
		command = "rm ${evolved}/${id}.java"
		command.execute()
		command = "rm ${evolved}/${id}.class"
		command.execute()
		command = "rm ${evolved}/${id}\$microEnemy.class"
		command.execute()
		command = "rm ${evolved}/${id}.properties"
		command.execute()
		
		return scores
	}
}
