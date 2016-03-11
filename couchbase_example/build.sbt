lazy val root = (project in file(".")).
  settings(
    name := "hello"
  )

resolvers += "Couchbase Maven Repository" at "http://files.couchbase.com/maven2"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "Typesafe Maven Releases" at "http://repo.typesafe.com/typesafe/maven-releases"

libraryDependencies += "com.couchbase.client" % "java-client" % "2.2.5"
