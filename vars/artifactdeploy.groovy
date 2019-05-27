def call(def server, def pom, def user, def path, def dockeruser) {
pomvalues = readMavenPom file: "${pom}"
imagename = "${pomvalues.artifactId.toLowerCase()}"
//sh "scp ${WORKSPACE}/${BUILD_NUMBER}/${pomvalues.artifactId}-${pomvalues.version}.${pomvalues.packaging} ${user}@${server}:${path}${pomvalues.artifactId}.${pomvalues.packaging}"
sh "ssh ${user}@${server} \'sudo docker kill \$(sudo docker ps -q)\'"
sh "ssh ${user}@${server} \'docker run -d -p 8090:8080 ${dockeruser}/${imagename}:${BUILD_NUMBER}\'"
sh "ssh ${user}@${server} \'docker run -d -p 8092:8080 ${dockeruser}/${imagename}:${BUILD_NUMBER}\'"
}
