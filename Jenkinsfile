@Library('jenkinsfile-shared-library@testmacc2') _
if ((env.BRANCH_NAME =~ '.*feature.*').matches()) {
    integracioncontinua.call(
        VERSION:"0.0.16"
    )
}