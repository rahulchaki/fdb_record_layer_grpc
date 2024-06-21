
import sbt.Compile

lazy val root = (project in file("."))
  .settings(
    name := "server",
    libraryDependencies += "io.fbd.record.grpc" % "stubs" % "1.0-SNAPSHOT",
    libraryDependencies += "org.foundationdb" % "fdb-java" % "7.3.43",
    libraryDependencies += "org.foundationdb" % "fdb-record-layer-core" % "3.4-SNAPSHOT" exclude("org.foundationdb", "fdb-java"),
    libraryDependencies += "org.foundationdb" % "fdb-record-layer-core" % "3.4-SNAPSHOT" % "protobuf",
    libraryDependencies += "com.google.protobuf" % "protobuf-java" % PROTO_VERSION,
    libraryDependencies += "com.google.protobuf" % "protobuf-java-util" % PROTO_VERSION,
    libraryDependencies += "io.grpc" % "grpc-netty-shaded" % "1.64.0",
    Compile / PB.targets := Seq(
      scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
    ),
    resolvers += Resolver.mavenLocal
  )

PB.protocVersion := PROTO_VERSION

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"
val PROTO_VERSION = "3.25.3"