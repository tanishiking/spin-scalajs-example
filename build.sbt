import org.scalajs.linker.interface.{ModuleKind, ESVersion}
import scala.sys.process.Process

val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .settings(
    name := "spin-scalajs-example",
    version := "0.1.0",
    scalaVersion := scala3Version,
    externalNpm := {
      Process("yarn", baseDirectory.value).!
      baseDirectory.value
    },
    scalaJSLinkerConfig ~= { conf =>
      conf.withModuleKind(ModuleKind.NoModule)
        .withESFeatures(_.withESVersion(ESVersion.ES5_1)) // it seems spin-js-engine doesn't accept ES2015
    },
  )

lazy val buildWasm =
  taskKey[File]("Build the wasm file")
buildWasm := {
  val log = streams.value.log
  val _ = (Compile / fastLinkJS).value
  val jsOut = (Compile / fastLinkJS / scalaJSLinkerOutputDirectory).value / "main.js"
  val out = target.value / "main.wasm"
  log.info(s"Running `spin js2wasm -o $out $jsOut`")
  val res: Int = Process(s"spin js2wasm -o $out $jsOut", baseDirectory.value).!
  out
}