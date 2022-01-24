@Library('jenkinsfile-shared-library@testmacc2') _
if ((env.BRANCH_NAME =~ '.*feature.*').matches()) {
     echo "Rama Feature"
    integracioncontinua.ci(
        VERSION:"0.0.16"
    )
} else if ((env.BRANCH_NAME =~ '.*release.*').matches()) {
    echo "Rama Release"
    desplieguecontinuo.cd(
        VERSION:"0.0.16"
    )
} else {
    echo "Su rama tiene formato erroneo o esta intentando ejecutar desde la rama master."
}