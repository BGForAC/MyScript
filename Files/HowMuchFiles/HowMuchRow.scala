import java.io.File
import java.nio.file.{Files, Paths}
import scala.io.StdIn

object HowMuchRow {
  def main(args: Array[String]): Unit = {
    def listFiles(dir: File): List[File] = {
      val files = dir.listFiles()
      files.filter(_.isFile).toList ++ files.filter(_.isDirectory).flatMap(listFiles)
    }

    println("请输入路径")
    val path = StdIn.readLine()
    println("请输入文件后缀")
    val postFix = StdIn.readLine()
    val fileList = listFiles(new File(path)).filter(_.getName.endsWith(postFix))
    println(fileList.map(file => {
      Files.lines(Paths.get(file.getPath)).count()
    }).sum)
  }
}