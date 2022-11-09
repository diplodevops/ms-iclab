pipeline{
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'TODO: build'
                sh '.mvnw clean compile -e'
            }
        }

        stage('Clean') {
            steps {
                cleanWs()
            }
        }
    }
}
