import org.scalamock.proxy.ProxyMockFactory
import org.scalatest.FlatSpec

/**
 * http://eed3si9n.com/ja/real-world-scala-dependency-injection-di
 */
class DependencyInjectionSample extends FlatSpec with ProxyMockFactory {

  object MockComponentRegistry extends UserServiceComponent with UserRepositoryComponent {
    val userRepository = new UserRepository
    val userService = new UserService
  }

  "mock" should "return" in {
    val userService = MockComponentRegistry.userService
    userService.authenticate("idfoo", "pwfoo")
  }
}

case class User(username: String, password: String)

trait UserRepositoryComponent {
  val userRepository: UserRepository
  class UserRepository {
    def authenticate(user: User): User = {
      println(s"authenticating user: $user")
      user
    }

    def create(user: User) = println(s"creating user: $user")

    def delete(user: User) = println(s"deleting user: $user")
  }
}

trait UserServiceComponent {
  this: UserRepositoryComponent =>
  val userService: UserService
  class UserService {
    def authenticate(username: String, password: String): User = userRepository.authenticate(User(username, password))
    def create(username: String, password: String) = userRepository.create(User(username, password))
    def delete(user: User) = userRepository.delete(user)
  }

}

object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent {
  val userRepository = new UserRepository
  val userService = new UserService
}

