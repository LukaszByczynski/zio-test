//import java.nio.ByteBuffer
//
//import zio._
//import zio.stream._
//import zio.console._
//import com.zaxxer.nuprocess.{NuAbstractProcessHandler, NuProcess, NuProcessBuilder}
//import java.util
//
//import zio.clock.Clock
//
//import scala.util.Try
//
//object TestApp extends App {
//  override def run(args: List[String]) = {
//
//    def testStream: ZStream[Console, Throwable, Int] = {
//      def releaseProcess(stateRef: Ref[Option[NuProcess]]): URIO[Console, Unit] = {
//        val resultAction = stateRef.set(None)
//
//        stateRef.get.flatMap {
//          case Some(process) if (process.isRunning) =>
//            putStrLn("Killing") *> Task(process.destroy(false)).either *> resultAction
//          case _ => resultAction
//        }
//      }
//
//      ZStream.fromEffect(Ref.make[Option[NuProcess]](None)).flatMap { ref =>
//        ZStream.managed(
//          Task(new NuProcessBuilder(util.Arrays.asList("tree", "/")))
//            .toManaged(_ => releaseProcess(ref))
//        ).flatMap { processBuilder =>
//          ZStream.effectAsyncM[Console, Throwable, Int] { cb =>
//
//            val logger = new NuAbstractProcessHandler {
//              override def onExit(statusCode: Int): Unit = {
//                Try(cb(putStrLn("Process exited") *> releaseProcess(ref) *> IO.fail(None)))
//              }
//
//              override def onStdout(buffer: ByteBuffer, closed: Boolean): Unit = {
//                val bytes = new Array[Byte](buffer.limit())
//                buffer.get(bytes)
//                buffer.position(buffer.limit())
//                Try(cb(putStrLn(new String(bytes)) *> Task.succeed(1)))
//              }
//            }
//
//            for {
//              _ <- Task(processBuilder.setProcessListener(logger))
//              process <- Task(processBuilder.start())
//              _ <- ref.set(Some(process))
//            } yield ()
//          }
//        }
//      }
//    }
//
//    testStream
//      .take(1)
//      .runDrain
//      .flatMap { e =>
//        import zio.duration._
//        Clock.Live.clock.sleep(3.seconds)
//      }
//      .fold(_ => -1, _ => 0)
//  }
//}
