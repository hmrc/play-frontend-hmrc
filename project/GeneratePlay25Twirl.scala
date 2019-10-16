import sbt._

object GeneratePlay25Twirl {

  /**
    * Remove '@this' declaration from template and all comments before the template signature
    * because the Play 2.5 Twirl compiler does not compile if there are comments just before the signature(!)
    * @param templateFile
    * @return
    */
  def convertToPlay25Template(templateFile: File): String = {
    val content               = IO.read(templateFile)
    val thisDeclarationRegex   = """(?s)@this\(.*?\)""" //matches @this declaration
    val commentRegex           = """(?s)@\*.*?\*@""" //matches Twirl comments
    val templateSignatureRegex = """@\(""" //matches template signature beginning

    val templateSignatureStart  = templateSignatureRegex.r.findFirstMatchIn(content).map(m => m.start).getOrElse(0)
    val contentsBeforeSignature = content.substring(0, templateSignatureStart)

    contentsBeforeSignature
      .replaceAll(thisDeclarationRegex, "")
      .replaceAll(commentRegex, "")
      .concat(content.substring(templateSignatureStart))
  }

  /**
    * Generates Play 2.5 templates from Play 2.6 ones.
    * It works on the assumption that the injected template variables in the Play 2.6 templates match the template names
    * i.e. if a Play 2.6 template has <code>@this(myTemplate: SomeTemplate)</code> the Twirl 2.6 template source file that
    * implements <code>SomeTemplate</code> is <code>mytemplate.scala.html</code>.
    *
    * @param play26Templates a [[Set[File]]] as per
    * [[sbt.FileFunction#cached(java.io.File, sbt.FilesInfo.Style, sbt.FilesInfo.Style, scala.Function1)]] requirements
    * @return [[Set[File]]]
    */
  def generatePlay25Templates(play26Templates: Set[File]): Set[File] =
    play26Templates.map { play26Source =>
      val content = convertToPlay25Template(play26Source)
      val file     = new File(play26Source.getPath.replace("play-26", "play-25"))

      if (!file.getParentFile.exists()) {
        file.getParentFile.mkdirs()
      }

      IO.write(file = file, content = content, append = false)

      file
    }
}
