@Library('jenkinsfile-shared-library@testmacc2') _
if ((env.BRANCH_NAME =~ '.*feature.*').matches()) {
    stage("CI"){
        echo 'Estamos en FEATURE'
    }
}
integracioncontinua(
     VERSION:"0.0.16"
 )