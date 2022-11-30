import groovy.json.JsonSlurperClassic
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
def current_stage
pipeline {
    agent any
    environment {
        GRUPO_CHANNEL = 'D045253KTEZ'
        NOMBRE_GRUPO = 'Grupo 7'
    }
    stages {
        stage("Paso 1: Compilar"){
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Compile Code $TAG_NAME!'"
                    // Run Maven on a Unix agent.
                    sh "./mvnw clean compile -e"
                }
            }
        }
        stage("Paso 2: Testear"){
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Test Code!'"
                    //sh "plsql" descomentar para fallo
                    sh "./mvnw clean test -e"
                }
            }
        }
        stage("Paso 3: Build .Jar"){
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Build .Jar!'"
                    // Run Maven on a Unix agent.
                    sh "./mvnw clean package -e"
                }
            }
        }
        stage("Paso 4: Run .Jar"){
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    sh "echo 'Running .Jar file!'"
                    sh 'nohup bash ./mvnw spring-boot:run  & >/dev/null'
                }
            }
        }
        stage("Paso 5: Testing deploy"){
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    sh "sleep 10"
                    sh "curl -X GET 'http://jenkins:8081/rest/mscovid/estadoPais?pais=Chile'"
                }
            }
        }
        stage("Paso 6: stop Testing deploy"){
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    sh '''
                        echo 'Process Spring Boot Java: ' $(pidof java | awk '{print $1}')
                        sleep 20
                        kill -9 $(pidof java | awk '{print $1}')   '''
                }
            }
        }
        stage("Paso 7: Analizar en Sonar"){
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    withSonarQubeEnv('sonarqube') {                   
                        sh "echo 'Llamando a sonar Service!!'"
                        sh 'sh mvnw clean verify sonar:sonar -Dsonar.projectKey=com.devopsusach2020:DevOpsUsach2020'
                    }
                }
            }
        }
        stage("Paso 8: Release Subir Artefacto a Nexus"){
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    //obteniendo version del release
                    MY_VERSION = env.BRANCH_NAME.substring(8)
                    sh "echo ${MY_VERSION}"

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
                }
            }
        }
        stage('Paso 9: Descargar ultima version jar de Nexus') {
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    MY_VERSION = env.BRANCH_NAME.substring(8)
                    sh "echo ${MY_VERSION}"
                    //sh ' curl -X GET -u $NEXUS_PASSWORD "http://nexus:8081/repository/maven-usach-ceres/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar" -O'
                    //DESCARGANDO LA ULTIMA VERSION JAR pero no me muestra el nombre del jar
                    //sh 'curl -L -X GET -u $NEXUS_PASSWORD "http://nexus:8081/service/rest/v1/search/assets/download?sort=version&repository=maven-usach-ceres&maven.groupId=com.devopsusach2020&maven.artifactId=DevOpsUsach2020&maven.extension=jar" --output myLastJar.jar'
                    //otra opcion es obtener la version desde el ultimo tag
                    //MY_VERSION = sh(returnStdout: true, script: 'git tag --sort=-creatordate | head -n 1').trim()
                    MY_EXTENSION = ".jar"                    
                    //-u $NEXUS_PASSWORD 
                    sh "curl -X GET 'http://nexus:8081/repository/maven-usach-ceres/com/devopsusach2020/DevOpsUsach2020/$MY_VERSION/DevOpsUsach2020-$MY_VERSION$MY_EXTENSION' -O"

                }
            }
        }
        stage('Paso 10: Levantar y testear Artefacto Jar obtenido de nexus') {
            steps {
                script {
                    NOMBRE_STAGE = 'Levantar y testear Artefacto Jar obtenido de nexus'
                    sh "nohup java -jar DevOpsUsach2020-$MY_VERSION$MY_EXTENSION & >/dev/null"
                    sh "sleep 20 && curl -X GET 'http://jenkins:8081/rest/mscovid/test?msg=testing'"
                }
            }
        }
        stage('Paso 11:Detener Atefacto jar en Jenkins server') {
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    sh '''
                    echo 'Process Java .jar: ' $(pidof java | awk '{print $1}')
                    sleep 20
                    kill -9 $(pidof java | awk '{print $1}')
                '''
                }
            }
        }
        stage('Paso 12: Deploy Prod') {
            steps {
                script {
                    current_stage =env.STAGE_NAME 
                    sh "echo 'enviado a PROD $MY_VERSION'"
                }
            }
        }
    }
    post{
        success{
            slackSend color: 'good', message: "[${NOMBRE_GRUPO}] [${env.JOB_NAME}][Rama : ${env.BRANCH_NAME}] [Stage :${current_stage}][Resultado: Éxito/Success](<${env.BUILD_URL}|Detalle>)", tokenCredentialId: 'id-token-slack'
        }
        failure{
            slackSend color: 'danger', message: "[${NOMBRE_GRUPO}] [${env.JOB_NAME}][Rama : ${env.BRANCH_NAME}] [Stage :${current_stage}][Resultado:Error/Fail](<${env.BUILD_URL}|Detalle>)", tokenCredentialId: 'id-token-slack'
        }
    }
}
