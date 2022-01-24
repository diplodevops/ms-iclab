@Library('jenkinsfile-shared-library@testmacc2') _
if ((env.BRANCH_NAME =~ '.*feature.*').matches()) {
    integracioncontinua.call(
        VERSION:"0.0.16"
    )
} else if ((env.BRANCH_NAME =~ '.*release.*').matches()) {
    echo "en release"
} else {
    echo "mal formato"
}