def call(def goal, def pom) {
rtMavenDeployer (
    id: 'deployer-unique-id',
    serverId: 'Artifactory',
    releaseRepo: "generic-local/${BUILD_NUMBER}",
    snapshotRepo: "generic-snapshot/${BUILD_NUMBER}"
)

rtMavenRun (
    tool: 'maven',
    type: 'maven',
    pom: "${pom}",
    goals: "${goal}",
    opts: '-Xms1024m -Xmx4096m',
    deployerId: 'deployer-unique-id',
)
}
