pipeline {
    agent any
    environment {
        pomVersion = readMavenPom().getVersion()
    }
    stages {
        stage('Versioning and tag'){
            steps{
                sh './mvnw -B build-helper:parse-version versions:set -DnewVersion=\\${parsedVersion.majorVersion}.\\${parsedVersion.minorVersion}.\\${parsedVersion.nextIncrementalVersion} versions:commit '
                
                script{
                    env.STAGE='Versioning and tag'
                    VERSION = readMavenPom().getVersion()
                }
                withCredentials([usernamePassword(credentialsId: 'Github_acon_token_bfal', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    //config golbal
                    sh 'git config --global user.name \"jenkins\"'
                    sh 'git config --global user.email \"b.arancibia.f.l@gmail.com\"'
                    //commit and push versioning
                    sh 'git add .'
                    sh 'git commit -m \"pushing version \${VERSION}\"'
                    sh "echo ${env.GIT_BRANCH}"
                    sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/g3-usach-2022/ms-iclab.git HEAD:${env.GIT_BRANCH}"
                    //create tag
                    sh "git tag ${VERSION}"
                    //push tag a remoto
                    sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/g3-usach-2022/ms-iclab.git ${VERSION}"
                }
            }
        }

        stage('Compile Code') {
            steps {
                script{
                   env.STAGE='Compile Code'
                   sh "./mvnw clean compile -e -DskipTest"
                }
            }
            post{
                failure{
                    slackSend color: 'danger', message: "[Grupo 3] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.STAGE}]"
                }
            }
        }
        stage('Test Code') {
            steps {
                    script {
                    env.STAGE='Test Code'
                    sh "./mvnw clean test -e"
                    }
            }
            post{
                failure{
                    slackSend color: 'danger', message: "[Grupo 3] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.STAGE}]"
                }
            }
        }
        stage('Jar Code') {
            steps {
                script {
                    env.STAGE='Jar Code'
                    sh "./mvnw clean package -e -DskipTest"
                    }
            }
            post{
                failure{
                    slackSend color: 'danger', message: "[Grupo 3] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.STAGE}]"
                }
            }
        }
        stage('Run Jar') {
            steps {
                script {
                    env.STAGE='Run Jar'
                    sh "./mvnw spring-boot:run &"
                    }
            }
            post{
                failure{
                    slackSend color: 'danger', message: "[Grupo 3] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.STAGE}]"
                }
            }
        }
        stage('Sonar') {
            steps {
                script {
                    env.STAGE='Sonar'
                    }
                withSonarQubeEnv('sonarqube') {
                    sh "echo 'Calling sonar Service in another docker container!'"
                    sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=grupo-3 -Dsonar.projectName=Grupo3-Lab4'
                }
            }
            post{
                failure{
                    slackSend color: 'danger', message: "[Grupo 3] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.STAGE}]"
                }
            }
        }
        stage('Nexus'){        
            steps {
                script{
                    nPomVersion = readMavenPom().getVersion()
                    env.STAGE='Nexus'
                }
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'nexus:8081',
                    groupId: 'Grupo3',
                    version: "${nPomVersion}",
                    repository: 'maven-releases-g3',
                    credentialsId: 'artefactos-admin',
                    artifacts: [
                        [artifactId: "DevOpsUsach2020",
                        classifier: 'lab5',
                         file: 'build/DevOpsUsach2020-'+ "${nPomVersion}" + '.jar',
                        type: 'jar']
                    ]
                )
            }
            post{
                failure{
                    slackSend color: 'danger', message: "[Grupo 3] [${env.JOB_NAME}] [${BUILD_TAG}] Ejecucion fallida en stage [${env.STAGE}]"
                }
            }
        }
        stage("Download Artifact Nexus"){
            
            steps {
                script{
                    nPomVersion = readMavenPom().getVersion()
                }
                withCredentials([usernamePassword(credentialsId: 'artefactos-admin', passwordVariable: 'NXS_PASSWORD', usernameVariable: 'NXS_USERNAME')]) {
                    sh ' curl -X GET -u $NXS_USERNAME:$NXS_PASSWORD "http://nexus:8081/repository/maven-releases-g3/Grupo3/DevOpsUsach2020/'+"${nPomVersion}"+'/DevOpsUsach2020-'+"${nPomVersion}"+'-lab5.jar" -O'
                }
            }
        }
         stage("Run Artifact in Jenkins"){
            steps {
                script{
                    nPomVersion = readMavenPom().getVersion()
                }
                script{
                    sh 'nohup java -jar DevOpsUsach2020-'+"${nPomVersion}"+'-lab5.jar & >/dev/null'
                }
            }
        }
          stage("Testear Artefacto - Dormir(Esperar 20sg) "){
            steps {
                script{
                    sh "sleep 20 && newman run my-sclab-test.postman_collection.json"
                }
            }
        }
        stage("Detener Atefacto jar en Jenkins server"){
            steps {
                sh '''
                    echo 'Process Java .jar: ' $(pidof java | awk '{print $1}')  
                    sleep 20
                    kill -9 $(pidof java | awk '{print $1}')
                '''
            }
        }
    }

    post {
            always {
                    slackSend color: '#ADD8E6', message: "[Grupo 3] - [Resultado: Always] - [Profe un 7 plz]- (<${env.BUILD_URL}|Open>)"
                }
            success {
                    slackSend color: 'good', message: "[Grupo 3][Pipeline CI/CD][Rama: ${env.JOB_NAME}][Stage: ${env.BUILD_NUMBER}][Resultado: Success]- (<${env.BUILD_URL}|Open>)"
                }
            failure {
                    slackSend color: 'danger', message:"[Grupo 3][Pipeline CI/CD][Rama: ${env.JOB_NAME}][Stage: ${env.BUILD_NUMBER}][Resultado: Failed]- (<${env.BUILD_URL}|Open>)"
                }
    }
    
}
