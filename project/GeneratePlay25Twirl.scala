import java.io.PrintWriter
import sbt._
import scala.io.Source

object GeneratePlay25Twirl {

  /**
    * Remove '@this' declaration from template and all comments before the template signature
    * because the Play 2.5 Twirl compiler does not compile if there are comments just before the signature(!)
    * @param templateFile
    * @return
    */
  def convertToPlay25Template(templateFile: File): String = {
    val contents               = Source.fromFile(templateFile).mkString
    val thisDeclarationRegex   = """(?s)@this\(.*?\)"""
    val commentRegex           = """(?s)@\*.*?\*@"""
    val templateSignatureRegex = """@\("""

    val templateSignatureStart  = templateSignatureRegex.r.findFirstMatchIn(contents).map(m => m.start).getOrElse(0)
    val contentsBeforeSignature = contents.substring(0, templateSignatureStart)

    contentsBeforeSignature
      .replaceAll(thisDeclarationRegex, "")
      .replaceAll(commentRegex, "")
      .concat(contents.substring(templateSignatureStart))
  }

  def generatePlay25Templates(play26Templates: Set[File]): Set[File] =
    play26Templates.map { play26Source =>
      val contents = convertToPlay25Template(play26Source)
      val file     = new File(play26Source.getPath.replace("play-26", "play-25"))
      if (!file.getParentFile.exists()) {
        file.getParentFile.mkdirs()
      }
      val pw       = new PrintWriter(file)
      pw.write(contents)
      pw.close() //FIXME safer closing of resources
      file
    }
}
