package di.implicitparam

/**
 * http://qiita.com/takezoux2@github/items/a2b607cdfedd21974687
 */
trait UserRepository {
  def getUser(id: Long): Option[UserDao]
}

class RealUserRepository extends UserRepository {
  def getUser(id: Long): Option[UserDao] = Option(UserDao(id))
}

trait MailService {
  def send(emailAddress: String): Unit
}

class RealMailService extends MailService {
  def send(emailAddress: String): Unit = println(s"sending to $emailAddress")
}

case class UserDao(id: Long) {
  def setAndSaveNickname(nickname: String): UserDao = {
    println(s"new nickname: $nickname")
    this
  }
}

class UserFacade()(implicit repo: UserRepository, mail: MailService) {
  def changeNickname(id: Long, nickname: String): Option[UserDao] = repo.getUser(id).map {
    mail.send("kudo@example.com")
    _.setAndSaveNickname(nickname)
  }
}

trait Repositories {
  implicit def userRepo: UserRepository

  implicit def mailService: MailService
}

class Factory(repos: Repositories) {

  import repos._

  lazy val userFacade = new UserFacade()

}

object ImplicitParameterSample {

  object RepositoriesForProduction extends Repositories {
    // lazy valにしておくと、不要なコンポーネントが初期化されない
    lazy val userRepo = new RealUserRepository
    lazy val mailService = new RealMailService
  }

  def main(args: Array[String]) {
    f1
  }

  def f1: Unit = {
    val factory = new Factory(RepositoriesForProduction)
    val userFacade = factory.userFacade
    userFacade.changeNickname(1, "クドリャフカ")
  }
}
