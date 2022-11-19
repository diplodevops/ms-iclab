pipeline {
    agent any

    stages {
        stage('Compile Code') {
            steps {
                sh "./mvnw clean compile -e -DskipTest"
            }
        }
        stage('Test Code') {
            steps {
                sh "./mvnw clean test -e"
            }
        }
        stage('Jar Code') {
            steps {
                sh "./mvnw clean package -e -DskipTest"
            }
        }
        stage('Run Jar') {
            steps {
                //sh "./mvnw spring-boot:run"
                //sh "nohup bash ./mvnw spring-boot:run &"
                sh "./mvnw spring-boot:run &"
            }
        }
        stage('sonar') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh "echo 'Calling sonar Service in another docker container!'"
                    sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=grupo-3 -Dsonar.projectName=Grupo3-Lab4'
                }
            }
        }
        stage('Nexus'){        
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'localhost:3002',
                    groupId: 'Grupo3',
                    version: '1',
                    repository: 'Grupo3-Lab4',
                    credentialsId: '18399bf6-925d-3a0f-9201-9ccd03fdfe2f',
                    artifacts: [
                        [artifactId: nexus-artifact-uploader,
                        classifier: '',
                        file: 'build/DevOpsUsach2020*' + '.jar',
                        type: 'jar']
                    ]
                )
            }
        }
        stage('Good Bye') {
            steps {
                echo 'Profe un 7 plssss'

            }
        }
    }
    post {
            success {
                    slackSend message: "[Grupo 3][Pipeline CI/CD][Rama: ${env.JOB_NAME}][Stage: ${env.BUILD_NUMBER}][Resultado: Success]- (<${env.BUILD_URL}|Open>)"
                }
            failure {
                    slackSend message:"[Grupo 3][Pipeline CI/CD][Rama: ${env.JOB_NAME}][Stage: ${env.BUILD_NUMBER}][Resultado: Failed]- (<${env.BUILD_URL}|Open>)"
                }
    }
}