import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import akka.http.scaladsl.model.headers.Connection
import akka.stream.{ ActorMaterializer, Materializer }
import scala.concurrent.duration._
import scala.concurrent.{ Future, Await }
import scala.annotation.tailrec

object Client extends App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val request = HttpRequest(uri = "http://localhost:57575/").addHeader(Connection("keep-alive"))
  def makeRequest(): Future[HttpResponse] = Http().singleRequest(request).map(r => { r.discardEntityBytes(materializer); r })
  @tailrec def run(): Unit = {
    val rsps = Await.result(Future.sequence((1 to 20).map(_ => makeRequest())), 10.seconds)
    println(s"FINISHED: ${rsps.map(_.status).mkString(", ")}")
    run()
  }

  run()
}
