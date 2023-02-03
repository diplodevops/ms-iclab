import groovy.json.JsonSlurperClassic
def jsonParse(def json) {
    new groovy.json.JsonSlurperClassic().parseText(json)
}
pipeline {
    agent any
    environment {
        NEXUS_CREDENTIALS=credentials('nexus-connect-lab-mod4')
        channel='D044QHARRPH'
    }
    stages {
        stage("Step 1: Compile Code"){
            steps {
                script {
                env.STAGE='Compile'
                env.GROUP='Grupo Nro. 5'
                sh "echo 'Compile Code'"
                sh "./mvnw clean compile -e"
                }
            }
            post{
				success{
					slackSend color: 'good', message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Éxito/Success].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
				failure{
					slackSend color: 'danger',  message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Error/Fail].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
			}
        }
        stage("Step 2: Testing"){
            steps {
                script {
                env.STAGE='Testing'
                sh "echo 'Test Code!'"
                sh "./mvnw clean test -e"
                }
            }
            post{
				success{
					slackSend color: 'good', message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Éxito/Success].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
				failure{
					slackSend color: 'danger',  message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Error/Fail].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
			}
        }
        stage("Step 3: Build .Jar"){
            steps {
                script {
                env.STAGE='Build Jar'
                sh "echo 'Build .Jar!'"
                sh "./mvnw clean package -e"
                }
            }
            post{
				success{
					slackSend color: 'good', message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Éxito/Success].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
				failure{
					slackSend color: 'danger',  message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Error/Fail].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
			}
        }
        stage("Step 4: SonarQube Analysis"){
            steps {
                script{
                    env.STAGE='SonarQube'
                    withSonarQubeEnv('sonarqube') {
                        sh "echo 'Calling sonar Service in another docker container!'"
                        // Run Maven on a Unix agent to execute Sonar.
                        sh './mvnw clean verify sonar:sonar -Dsonar.projectKey=project-laboratorio-mod4 -Dsonar.projectName=project-laboratorio-mod4'
                    }
                }    
            }
            post{
				success{
					slackSend color: 'good', message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Éxito/Success].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
				failure{
					slackSend color: 'danger',  message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Error/Fail].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
			}
        }
        stage("Step 5: Upload Artifact to Nexus"){
            steps {
                script{
                    env.STAGE='UploadNexus'
                    nexusPublisher nexusInstanceId: 'nexus',
                        nexusRepositoryId: 'repository-lab-mod4',
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
                                    version: '0.0.1'
                                ]
                            ]
                        ]
                }
            }
            post{
				success{
					slackSend color: 'good', message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Éxito/Success].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
				failure{
					slackSend color: 'danger',  message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Error/Fail].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
			}
        }
        stage("Step 6: Download from Nexus"){
            steps {
                script{
                    env.STAGE='DownloadNexus'
                    sh 'curl -X GET -u ${NEXUS_CREDENTIALS} "http://nexus:8081/repository/repository-lab-mod4/com/devopsusach2020/DevOpsUsach2020/0.0.1/DevOpsUsach2020-0.0.1.jar" -O'
                }
            }
            post{
				success{
					slackSend color: 'good', message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Éxito/Success].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
				failure{
					slackSend color: 'danger',  message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Error/Fail].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
			}
        }
         stage("Step 7: Raise Artifact Jar on Jenkins server"){
            steps {
                script{
                    env.STAGE='RaiseArtifact'
                    sh 'nohup java -jar DevOpsUsach2020-0.0.1.jar & >/dev/null'
                }
            }
            post{
				success{
					slackSend color: 'good', message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Éxito/Success].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
				failure{
					slackSend color: 'danger',  message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Error/Fail].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
			}
        }
          stage("Step 8: Test Artifact - Sleep(Wait 20s)"){
            steps {
                script{
                    env.STAGE='TestArtifact'
                    sh "sleep 20 && curl -X GET 'http://localhost:8081/rest/mscovid/test?msg=testing'"
                }
            }
            post{
				success{
					slackSend color: 'good', message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Éxito/Success].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
				failure{
					slackSend color: 'danger',  message: "[${env.GROUP}][Pipeline IC/CD][Rama: ${BRANCH_NAME}][Stage: ${env.STAGE}][Resultado: Error/Fail].", teamDomain: 'devopsusach20-lzc3526', tokenCredentialId: 'token-slack-lab-mod4'
				}
			}
        }
    }
    post {
        always {
            sh "echo 'fase always executed post'"
        }
        success {
            sh "echo 'fase success'"
        }
        failure {
            sh "echo 'fase failure'"
        }
    }
}


