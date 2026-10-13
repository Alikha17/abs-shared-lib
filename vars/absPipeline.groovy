def call(Map config = [:]) {
    def appName = config.appName ?: 'unknown-app'

    pipeline {
        agent any
        stages {
            stage('Build and Test') {
                steps {
                    sh 'mvn -B clean verify'
                }
            }
            stage('Package') {
                steps {
                    archiveArtifacts artifacts: 'target/*.jar'
                }
            }
        }
        post {
            success { echo "${appName}: build OK" }
            failure { echo "${appName}: build FAILED" }
        }
    }
}
