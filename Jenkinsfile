@Library('jenkinsfile-shared-library@testmacc2') _
if ((env.BRANCH_NAME =~ '.*feature.*').matches()) {
    integracioncontinua.call(
        VERSION:"0.0.16"
    )
} else if ((env.BRANCH_NAME =~ '.*release.*').matches()) {
    desplieguecontinuo.call(
        VERSION:"0.0.16"
    )
} else {
    echo "Su rama tiene formato erroneo o esta intentando ejecutar desde la rama master."
}