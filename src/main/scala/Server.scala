import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.Connection
import akka.http.scaladsl.model.HttpRequest
import akka.actor.ActorSystem
import akka.stream.{ ActorMaterializer, Materializer }
import scala.util.{ Failure, Success }

class Server(implicit system: ActorSystem, materializer: Materializer) extends Directives {
  val request = HttpRequest(uri = "http://localhost:6789/").addHeader(Connection("keep-alive"))
  val route = get {
    complete {
      Http().singleRequest(request)
    }
  }

  def bindingFuture = Http().bindAndHandle(handler = route, interface = "0.0.0.0", port = 57575)
}

object Server extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val server = new Server
  server.bindingFuture.onComplete {
    case Success(_) =>
      println(s"Server listening on port: 57575")
    case Failure(reason) =>
      println(s"Server failed to bind to port 57575.", reason)
      system.terminate()
  }
}
