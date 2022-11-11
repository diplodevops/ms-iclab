pipeline {
    agent any
	stages {
        stage('SonarQube analysis') {
            steps{
                withSonarQubeEnv('Sonar') {
					sh 'mvn clean package sonar:sonar'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Build') {
            steps {
                echo 'Building..'
                sh "./mvnw clean compile -e"
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
		stage ('Clean'){
            steps
                {
                    cleanWs()
                }
        }

    }
}
