def call() {
def qualitygate=waitForQualityGate()
if(qualitygate.status!='OK'){
error "pipeline aborted due to quality gate failure : ${qualitygate.status}"
}
}
