@Library('jenkinsfile-shared-library@testmacc2') _
if ((env.BRANCH_NAME =~ '.*feature.*').matches() || (env.BRANCH_NAME =~ '.*develop.*').matches() ) {
    echo "Rama Feature o develop"
    integracioncontinua.call(
        VERSION:"0.0.16"
    )
} else if ((env.BRANCH_NAME =~ '.*release.*').matches()) {
    echo "Rama Release"
    desplieguecontinuo.call(
        VERSION:"0.0.16"
    )
} else {
    echo "Su rama tiene formato erroneo o esta intentando ejecutar desde la rama master."
}