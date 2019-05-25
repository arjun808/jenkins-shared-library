def call(Map params) {
  node('master') {
stage('Clone') 
{
  def _gitBranch = '*/'+params.gitBranch
  def _gitRepo = params.gitRepo
  gitclone(_gitBranch,_gitRepo)
}


stage('SonarQube analysis') 
{
  def _sonarURL = params.SonarURL
  sonarscan(_sonarURL)
}


stage('SonarQube quality gate check')
{
  sonarqg()
}

stage('Build & Upload')
{
  def _mvnGoal = params.mvnGoal
  def _POM = params.POM
  mvnbuild(_mvnGoal,_POM)
}


stage('Artifact Download') {
def _POM = params.POM
artifactdownload(_POM)
}

stage ('Application Deployment'){
  def _deploymentServer = params.deploymentServer
  def _POM = params.POM
  artifactdeploy(_deploymentServer,_POM)
}
}
}
return this
