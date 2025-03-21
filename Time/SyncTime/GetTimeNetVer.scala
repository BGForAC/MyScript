import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import scala.sys.process._

object GetTimeNetVer {
  object HttpClient {
    def get(url: String): String = {
      val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
      connection.setRequestMethod("GET")
      val responseCode = connection.getResponseCode
      if (responseCode == HttpURLConnection.HTTP_OK) {
        val in = new BufferedReader(new InputStreamReader(connection.getInputStream))
        val response = Stream.continually(in.readLine()).takeWhile(_ != null).mkString("\n")
        in.close()
        response
      } else {
        throw new Exception(s"HTTP request failed with response code $responseCode")
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val url = "http://quan.suning.com/getSysTime.do"
    var response = ""
    var success = false
    while (!success) {
      try {
        response = HttpClient.get(url)
        success = true
      } catch {
        case e: Exception => println(e)
      }
    }
    val dateTime = response.split("\"")(3).split(" ")
    val date = dateTime(0)
    val time = dateTime(1)
    val cmd = s"cmd /c date $date && time $time"
    cmd !
  }
}