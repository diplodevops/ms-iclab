import groovy.json.JsonSlurperClassic
import hudson.Util;
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
def current_stage
def build_duration_msg = "\n *Detail by Stage* \n"
pipeline {
    agent any
    environment {
        GITHUB_EMAIL = credentials('GITHUB_EMAIL')
        GITHUB_USERNAME = credentials('GITHUB_USERNAME')
        GRUPO_CHANNEL = 'D045253KTEZ'
        NOMBRE_GRUPO = 'Grupo 7'
    }
    stages {
        stage("Paso 1: Compilar"){
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 1: Compiling code!'"
                    sh "./mvnw clean compile -e"
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage("Paso 2: Testear"){
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 2: Testing code!'"                    
                    sh "./mvnw clean test -e"
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg + "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage("Paso 3: Build .Jar"){
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 3: Building .Jar file!'"
                    sh "./mvnw clean package -e"
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage("Paso 4: Run .Jar"){
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 4: Running .Jar file!'"
                    sh "nohup bash ./mvnw spring-boot:run  & >/dev/null"
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage("Paso 5: Testing deploy"){
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 5: Testing deploy!'"
                    sh "sleep 10"
                    sh "curl -X GET 'http://localhost:8081/rest/mscovid/estadoPais?pais=Chile'"
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage("Paso 6: stop Testing deploy"){
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 6: Stopping jar running'"
                    sh "sleep 10"
                    sh '''kill -9 $(pidof java | awk '{print $1}')'''
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage("Paso 7: Analizar en Sonar"){
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    withSonarQubeEnv('sonarqube') {                   
                        sh "echo 'Stage 7: Static code analysis in Sonar'"
                        sh 'sh mvnw clean verify sonar:sonar -Dsonar.projectKey=com.devopsusach2020:DevOpsUsach2020'
                    }
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage("Paso 8: Release Subir Artefacto a Nexus"){
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 8:Uploading artifact to Nexus'"
                    MY_VERSION = env.BRANCH_NAME.substring(8)
                    nexusPublisher nexusInstanceId: 'maven-ceres-repository',
                        nexusRepositoryId: 'maven-usach-ceres',
                        packages: [
                            [$class: 'MavenPackage',
                                mavenAssetList: [
                                    [classifier: '',
                                    extension: 'jar',
                                    filePath: 'build/DevOpsUsach2020-0.0.1.jar'
                                ]
                            ],
                                mavenCoordinate: [
                                    artifactId: 'DevOpsUsach2020',
                                    groupId: 'com.devopsusach2020',
                                    packaging: 'jar',
                                    version: MY_VERSION
                                ]
                            ]
                        ]
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage('Paso 9: Descargar ultima version jar de Nexus') {
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 9:Downloading artifact from Nexus'"                    
                    MY_VERSION = env.BRANCH_NAME.substring(8)
                    MY_EXTENSION = ".jar"                    
                    sh "curl -X GET 'http://nexus:8081/repository/maven-usach-ceres/com/devopsusach2020/DevOpsUsach2020/$MY_VERSION/DevOpsUsach2020-$MY_VERSION$MY_EXTENSION' -O"
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage('Paso 10: Levantar y testear Artefacto Jar obtenido de nexus') {
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 10:Testing artifact downloaded from Nexus'"                    
                    sh "nohup java -jar DevOpsUsach2020-$MY_VERSION$MY_EXTENSION & >/dev/null"
                    sh "sleep 20 && curl -X GET 'http://jenkins:8081/rest/mscovid/test?msg=testing'"
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage('Paso 11:Detener Atefacto jar en Jenkins server') {
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 11: Stopping artifact'"
                    sh "sleep 10"
                    sh '''kill -9 $(pidof java | awk '{print $1}')'''
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage('Paso 12: Desplegar en Produccion') {
            when { anyOf { branch 'release*' } }
            steps {
                script {
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 12: Deploying on production'"
                    sh "echo 'enviado a produccion $MY_VERSION'"
                    end = System.currentTimeMillis()
                    build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                }
            }
        }
        stage('Paso 13: Merge y Tag en Github') {
            when { anyOf { branch 'release/*' } }
            steps {
            withCredentials([
                gitUsernamePassword(credentialsId: 'github-jenkins', gitToolName: 'Default')
                ]) {
                script{
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 13: Merging branch release on main and develop and create tag'"
                    //Release branch  has been merged into '$GITHUB_EMAIL'
                    sh "git config --global user.email '$GITHUB_EMAIL'"
                    sh "git config --global user.name '$GITHUB_USERNAME'"
                    sh "git checkout ${env.BRANCH_NAME}"
                    sh "git pull origin"

                    sh "git checkout main"
                    sh "git fetch origin"
                    sh "git pull origin main"
                    sh "git merge ${env.BRANCH_NAME}"
                    sh "git push origin main"
                
                    //traer el ultimo tag del origin
                    MY_VERSION_TAG = sh(returnStdout: true, script: 'git tag --sort=-creatordate | head -n 1').trim()
                    if (MY_VERSION_TAG == MY_VERSION) {
                        sh "git tag -d ${MY_VERSION}"
                        sh "git push --delete origin ${MY_VERSION}"
                        sh "git tag -a $MY_VERSION -m 'update release from Jenkins'"
                        sh "git push origin $MY_VERSION"
                    } else {
                        sh "git tag -a $MY_VERSION -m 'update release from Jenkins'"
                        sh "git push origin $MY_VERSION"
                    }
                
                    //Release tag has been back-merged into 'develop'
                    sh "git checkout develop"
                    sh "git pull origin develop"
                    sh "git merge ${env.BRANCH_NAME}"
                    sh "git push origin develop"

                    //Release branch  has been remotely deleted from 'origin'
                     sh "git push origin --delete ${env.BRANCH_NAME}"
                     end = System.currentTimeMillis()
                     build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                    }

            }
            }
        }
        stage('Paso 14: Merge con develop en Github') {
            when { branch 'feature/*' }
            steps {
            withCredentials([
                gitUsernamePassword(credentialsId: 'github-jenkins', gitToolName: 'Default')
                ]) {
                script{
                    start = System.currentTimeMillis()
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Stage 13: Merging branch feature on develop'"
                    //Release branch  has been merged into '$GITHUB_EMAIL'
                    sh "git config --global user.email '$GITHUB_EMAIL'"
                    sh "git config --global user.name '$GITHUB_USERNAME'"
                    sh "git checkout ${env.BRANCH_NAME}"
                    sh "git pull origin"
                    
                    //merge into develop
                    sh "git checkout develop"
                    sh "git pull origin develop"
                    sh "git merge ${env.BRANCH_NAME}"
                    sh "git push origin develop"

                    //Release branch  has been remotely deleted from 'origin'
                     //sh "git push origin --delete ${env.BRANCH_NAME}"
                     end = System.currentTimeMillis()
                     build_duration_msg = build_duration_msg +  "*" + current_stage + "*" + " : "  + Util.getTimeSpanString(end - start) +"\n"
                    }

            }
            }
        }
    }
    post{
        always{
            script{
                build_duration_msg = build_duration_msg + "\n *Total build time:* " +  "${currentBuild.durationString}".replaceAll(' and counting', "")
            }            
        }
        success{
            script{
                    current_stage = "Post Build"
                    slackSend color: 'good', message: "[${NOMBRE_GRUPO}] [${env.JOB_NAME}][Rama : ${env.BRANCH_NAME}] [Stage :${current_stage}][Resultado: ${currentBuild.result}](<${env.BUILD_URL}|Detalle>)${build_duration_msg}", tokenCredentialId: 'id-token-slack'
                }
            }
        failure{
            slackSend color: 'danger', message: "[${NOMBRE_GRUPO}] [${env.JOB_NAME}][Rama : ${env.BRANCH_NAME}] [Stage :${current_stage}][Resultado:${currentBuild.result}](<${env.BUILD_URL}|Detalle>)${build_duration_msg}", tokenCredentialId: 'id-token-slack'
        }
    }
}
