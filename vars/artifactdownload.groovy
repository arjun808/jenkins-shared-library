def call(def pom) {
pomvalues = readMavenPom file: "${pom}"
rtDownload (
    serverId: "Artifactory",
    spec:
        """{
          "files": [
            {
              "pattern": "generic-snapshot/${BUILD_NUMBER}/*/${pomvalues.artifactId}-${pomvalues.version}.${pomvalues.packaging}",
              "target": "${WORKSPACE}/${JOB_NAME}/${BUILD_NUMBER}/",
              "flat": "true"
            }
         ]
        }"""
)
}
