package applications.robocode

import java.util.jar.JarFile
import spock.lang.Specification


/*
 * This assumes that there is a copy of Robocode in your home directory,
 * and that it has been configured (via the GUI) to be able to load robot
 * files from the evolved_robots directory in this project.
 */
class MakeHawkOnFire extends Specification {
	/*
	 * id : an id used in the generation of the name of the class.
	 * enemy_energy : the coefficient for the enemy's energy
	 * my_energy : the coefficient for our energy
	 * angle_diff : the coefficient for the different in angles between us and the point and then and the point
	 * distance : the coefficient for the distance between the point and the enemy
	 */
	def robotBuilder
	def id
	def userHome = System.getProperty("user.home")
	def robotDirectory = "evolved_robots"
	def robotDirectoryAbsolute = new File(robotDirectory).absolutePath
	
	def setup() {
		id = "HawkOnFire"
		
		robotBuilder = new RobotBuilder("templates/HawkOnFire.template")
	}
	
	def "Confirm that we can create a Java source file for an individual"() {
		given:
		def values = ["id" : id]

		robotBuilder.buildJarFile(values)
		
		def robotDir = new File("${userHome}/robocode/robots/")
		def command = "ln -s ${robotDirectoryAbsolute}/${id}.jar ."
		def proc = command.execute(null, robotDir)
	}
}