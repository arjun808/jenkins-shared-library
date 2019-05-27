def call(def goal, def pom, def rrepo, def srepo) {
rtMavenDeployer (
    id: 'deployer-unique-id',
    serverId: 'Artifactory',
    releaseRepo: "${rrepo}/${BUILD_NUMBER}",
    snapshotRepo: "${srepo}/${BUILD_NUMBER}"
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
