pipeline{
    agent any

    stages {
        
        stage('Clean') {
            steps {
                cleanWs()
            }
        }

        stage('Compile Code') {
            steps {
                ./mvnw.cmd clean compile -e
            }
        }

        stage('Test Code') {
            steps {
                ./mvnw.cmd clean test -e
            }
        }

        stage('Jar Code') {
            steps {
                ./mvnw.cmd clean package -e
            }
        }

        stage('Run Jar') {
            steps {
                ./mvnw.cmd spring-boot:run
            }
        }
    }
}