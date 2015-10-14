package di.cakepattern

trait TwitterAPIComponent {
  val twitterAPI: TwitterAPI
  trait TwitterAPI {
    def getProfile(id: String): String
  }
}

trait ProfileServiceComponent { this: TwitterAPIComponent =>
  class ProfileService {
    def count(id: String) = twitterAPI.getProfile(id)
  }
}

class CakePatternSample {

}
