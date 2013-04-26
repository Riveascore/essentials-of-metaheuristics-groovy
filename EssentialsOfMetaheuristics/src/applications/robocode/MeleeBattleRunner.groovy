package applications.robocode

import java.sql.ResultSet;

import groovy.text.SimpleTemplateEngine

// I'm adding a comment via GitHub - how weird is that?!?

/*
 * This assumes that there is a copy of Robocode in your home directory,
 * and that it has been configured (via the GUI) to be able to load robot
 * files from the evolved_robots directory in this project.
 */
class MeleeBattleRunner {
	def userHome = System.getProperty("user.home")
	def robotDirectory = "evolved_robots"
	def robotDirectoryAbsolute = new File(robotDirectory).absolutePath
	def template

	def MeleeBattleRunner(String templateFileName) {
		def engine = new SimpleTemplateEngine()
		template = engine.createTemplate(new File(templateFileName))
	}

	def buildBattleFile(id) {
		File battleFile = new File("${robotDirectory}/evolve.battle")
		battleFile.delete()
		battleFile.createNewFile()
		def result = template.make(["id" : id])
		battleFile << result.toString()
	}

	def runBattle(id) {
		linkJarFile(id)
		File battleFile = new File("${robotDirectory}/evolve.battle")
//		def command = "${userHome}/robocode/robocode.sh -battle ${battleFile.absolutePath}"
		def command = "${userHome}/robocode/robocode.sh -battle ${battleFile.absolutePath} -nodisplay"
		
		def proc = command.execute(null, new File(robotDirectory))
		proc.waitFor()
		assert proc.exitValue() == 0		//**** should keep ******************//
		assert proc.err.text.equals("")		//**** should keep ******************//
		def lines = proc.in.text.split("\n")
		
		
//		evolved.${id},sample.Walls,sample.VelociRobot,evolved.Individual_709887,
//		evolved.HawkOnFire,sample.RamFire,sample.PaintingRobot,sample.MyFirstJuniorRobot,sample.MyFirstRobot
		def allBots = []
		allBots += ~/evolved\.${id}\s+(\d+)/
		allBots += ~/evolved.HawkOnFire\s+(\d+)/
		//^us then HOF
		
		allBots += ~/sample.Walls\s+(\d+)/
		allBots += ~/sample.VelociRobot\s+(\d+)/
		allBots += ~/evolved.Individual_709887\s+(\d+)/
		allBots += ~/sample.RamFire\s+(\d+)/
		allBots += ~/sample.PaintingRobot\s+(\d+)/
		allBots += ~/sample.MyFirstJuniorRobot\s+(\d+)/
		allBots += ~/sample.MyFirstRobot\s+(\d+)/
		
		def results = []
		def pattern
		
		allBots.each { botPattern ->
			lines.each { line ->
				pattern = botPattern
				def m = (line =~ pattern)
				if (m) {
					results += Integer.parseInt(m[0][1])
				}
			}
		}

		results
	}
	
	def linkJarFile(id) {
		def robotDir = new File("${userHome}/robocode/robots/")
		def command = "ln -s ${robotDirectoryAbsolute}/${id}.jar ."
		def proc = command.execute(null, robotDir)
		proc.waitFor()
		assert proc.err.text.equals("")
		assert proc.exitValue() == 0
	}
}