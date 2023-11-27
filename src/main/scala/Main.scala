import scala.concurrent.Future

import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}
import scala.scalajs.js.JSConverters.*

import org.scalablytyped.runtime.StringDictionary
import typings.fermyonSpinSdk.libModulesHandlerFunctionMod.*

implicit val ec: scala.concurrent.ExecutionContext =
  scala.concurrent.ExecutionContext.global

// spin-http requires an object "spin" with "handleRequest" or "handler" function
@JSExportTopLevel(name = "spin") @JSExportAll
object Spin {
  val handleRequest: HandleRequest = (req: HttpRequest) => {
    val headers: typings.std.Record[String, String] =
      StringDictionary[String]("content-type" -> "text/plain")
    Future(
      HttpResponse(200).setHeaders(headers).setBody("Hello from Scala.js")
    ).toJSPromise
  }
}
