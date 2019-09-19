import java.io.{FileOutputStream, OutputStream, PrintWriter}
import sbt._
import scala.io.Source
import scala.util.Try

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

      safeFilePrint(new FileOutputStream(file)) { pw =>
        pw.write(contents)
      }

      file
    }

  def safeFilePrint(tf: => OutputStream)(op: PrintWriter => Unit): Try[Unit] = {
    val os = Try(tf)
    val write = {
      val writer  = os.map(f => new PrintWriter(f))
      val writeOp = writer.map(op)
      val flushOp = writer.map(_.flush)
      writeOp.flatMap(_ => flushOp)
    }
    val close = os.map(_.close)
    write.flatMap(_ => close)
  }
}
