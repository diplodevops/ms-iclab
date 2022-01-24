@Library('jenkinsfile-shared-library@testmacc') _
pipeline {
    agent any
    environment{
        NEXUS_USER = credentials('usernexusadmin')
        NEXUS_PASSWORD = credentials('passnexusadmin')
        VERSION = '0.0.14'
        FINAL_VERSION = '1.0.0'
    }
	stages{
		stage('Integracion'){
			steps{
				script{
                    integracioncontinua.ci()
                }
			}
		}
    }
}
