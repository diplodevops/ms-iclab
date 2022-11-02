pipeline{
    agent any

    stages {
        stage('Hello') {
            steps {
                echo 'Hello Wordld'
            }
        }

        stage('Clean') {
            steps {
                cleanWs()
            }
        }
    }
}