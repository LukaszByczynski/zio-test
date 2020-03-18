import zio._
import zio.stream._

object TestZStream extends App {

  def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] = {

    ZManaged.switchable

    ZStream.app

    val c = ZStream
      .range(0, 100)
      .process

  }

}
