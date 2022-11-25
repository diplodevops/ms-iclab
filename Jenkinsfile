import groovy.json.JsonSlurperClassic
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
pipeline {
    agent any
    environment {
        //WORKGROUP   = credentials('workgroup-name')
    }
    stages {
        stage("Paso 1: Compilar"){
            steps {
                script {
                sh "echo 'Compile Code!'"
                // Run Maven on a Unix agent.
                sh "./mvnw clean compile -e"
                }
            }
        }
        stage("Paso 2: Testear"){
            steps {
                script {
                sh "echo 'Test Code!'"
                // Run Maven on a Unix agent.
                sh "./mvnw clean test -e"
                }
            }
        }
        stage("Paso 3: Build .Jar"){
            steps {
                script {
                sh "echo 'Build .Jar!'"
                // Run Maven on a Unix agent.
                sh "./mvnw clean package -e"
                }
            }
        }
        stage("Paso 4: Run .Jar"){
            steps {
                script {
                sh "echo 'Running .Jar file!'"
                sh "./mvnw spring-boot:run &"
                }
            }
        }
        stage("Paso 5: Build .Jar"){
            steps {
                script {
                    sh "sleep 30"
                    sh "curl -X GET 'http://localhost:8081/rest/mscovid/estadoPais?pais=Chile'"
                }
            }
        }
        stage("Paso 6: Analizar en Sonar"){
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh "echo 'Llamando a sonar Service!!'"
                    sh 'sh mvnw clean verify sonar:sonar'
                }
            }
        }
    }
    post{
        success{
            slackSend color: 'good', channel: "${env.channel}", message: "[Grupo7] [${JOB_NAME}] [${BUILD_TAG}][Resultado: Éxito/Success]", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack'
        }
        failure{
            slackSend color: 'danger',channel: "${env.channel}", message: "[Grupo7] [${env.JOB_NAME}] [${env.STAGE}][Resultado: Éxito/Success]", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack'
        }
    }
}
