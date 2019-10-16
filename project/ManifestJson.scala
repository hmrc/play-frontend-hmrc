import play.api.libs.json.{Json, OWrites}

final case class ManifestJson(
  id: String,
  versions: Versions
)

object ManifestJson {
  implicit val writes: OWrites[ManifestJson] = Json.writes[ManifestJson]
}

final case class Versions(
  play25: ExampleRef,
  play26: ExampleRef
)

object Versions {
  implicit val writes: OWrites[Versions] = Json.writes[Versions]
}

final case class ExampleRef(
  uri: String,
  htmlChecksum: String
)

object ExampleRef {
  implicit val writes: OWrites[ExampleRef] = Json.writes[ExampleRef]
}
