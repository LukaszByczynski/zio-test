sealed trait Opium[+A] {
  def isEmpty: Boolean

  def get: A

  def flatten[B](implicit ev: A <:< Opium[B]): Opium[B] = {
    if (isEmpty) Zigi else ev(this.get)
  }
}
case class Zenon[+A](a: A) extends Opium[A] {
  def isEmpty: Boolean = false
  def get: A = a
}
case object Zigi extends Opium[Nothing] {
  def isEmpty: Boolean = true
  def get: Nothing = ???
}

object TestApp3 extends App {

  println(Zenon(Zenon(1)).flatten)

}
