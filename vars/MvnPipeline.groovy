def call(Map params) {
  node('master') {
  def _gitBranch = '*/'+params.gitBranch
  def _gitRepo = params.gitRepo
  def _sonarURL = params.SonarURL
  def _mvnGoal = params.mvnGoal
  def _POM = params.POM
  def _deploymentServer = params.deploymentServer
  
stage('Clone') 
{
  gitclone(_gitBranch,_gitRepo)
}


stage('SonarQube analysis') 
{
  sonarscan(_sonarURL)
}


stage('SonarQube quality gate check')
{
  sonarqg()
}

stage('Build & Upload')
{
  mvnbuild(_mvnGoal,_POM)
}


stage('Artifact Download') {
artifactdownload(_POM)
}

stage('Application Deployment'){
  artifactdeploy(_deploymentServer,_POM)
}

stage('Docker image build'){
dockerbuild(_POM)
}
}
}
return this
