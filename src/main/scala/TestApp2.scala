import zio.{IO, Runtime, Task}

object TestApp2 extends App {

  val runtime = Runtime.default

  val program= IO.fail(new RuntimeException("haha")).orDie

  val runP = for {
    _ <- program
    _ <- Task(println("ok"))
  } yield ()

  println(runtime.unsafeRunSync(runP.either))

}
