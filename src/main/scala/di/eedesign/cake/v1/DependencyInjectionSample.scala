package di.eedesign.cake.v1

/**
 * http://eed3si9n.com/ja/real-world-scala-dependency-injection-di
 */
object DependencyInjectionSample {
  def main(args: Array[String]) {
    val userService = ComponentRegistry.userService
    userService.authenticate("idfoo", "pwfoo")
  }
}

case class User(username: String, password: String)

trait UserRepositoryComponent {
  val userRepository = new UserRepository
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
  val userService = new UserService
  class UserService {
    def authenticate(username: String, password: String): User = userRepository.authenticate(User(username, password))
    def create(username: String, password: String) = userRepository.create(User(username, password))
    def delete(user: User) = userRepository.delete(user)
  }

}

object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent