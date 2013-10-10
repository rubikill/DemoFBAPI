import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "DemoFBAPI"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "org.facebook4j" % "facebook4j-core" % "2.0.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
    javaSource in Compile <<= baseDirectory / "java/main/src",
    
    // Assets
        playAssetsDirectories := Seq.empty[File],
        playAssetsDirectories <+= baseDirectory / "java/main/resource"
  )

}
