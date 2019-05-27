def call(def pom, def srepo) {
pomvalues = readMavenPom file: "${pom}"
rtDownload (
    serverId: "Artifactory",
    spec:
        """{
          "files": [
            {
              "pattern": "${srepo}/${BUILD_NUMBER}/*/${pomvalues.artifactId}-${pomvalues.version}.${pomvalues.packaging}",
              "target": "${WORKSPACE}/${BUILD_NUMBER}/",
              "flat": "true"
            }
         ]
        }"""
)
}
