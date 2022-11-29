pipeline {
    agent any
    stages {
        stage('Build'){
            steps{
                echo 'Building...'
                slackSend color: "warning", message: "Building... branch: ${GIT_BRANCH}"
                sh './mvnw clean compile -e'

            }
            post {
                success {
                    echo 'Build Success'
                    slackSend color: "good", message: "Build Success"
                }
                failure {
                    echo 'Build Failed'
                    slackSend color: "danger", message: "Build Failed"
                }
            }
        }
        stage('Test'){
            steps{
                echo 'Testing...'
                slackSend color: "warning", message: "Testing..."
                sh './mvnw test -e'
            }
            post {
                success {
                    echo 'Test Success'
                    slackSend color: "good", message: "Test Success"
                }
                failure {
                    echo 'Test Failed'
                    slackSend color: "danger", message: "Test Failed"
                }
            }
        }
        stage('Package'){
            steps{
                echo 'Packaging...'
                slackSend color: "warning", message: "Packaging..."
                sh './mvnw package -e'
            }
            post {
                success {
                    echo 'Package Success'
                    slackSend color: "good", message: "Package Success"
                }
                failure {
                    echo 'Package Failed'
                    slackSend color: "danger", message: "Package Failed"
                }
            }
        }
        stage('Run'){
            steps{
                echo 'Running...'
                slackSend color: "warning", message: "Running..."
                sh '#./mvnw spring-boot:run -e'
            }
            post {
                success {
                    echo 'Run Success'
                    slackSend color: "good", message: "Run Success"   
                    cleanWs()                 
                }
                failure {
                    echo 'Run Failed'
                    slackSend color: "danger", message: "Run Failed"
                }
            }
        }
    }
}
