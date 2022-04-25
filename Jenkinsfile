pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew clean build'
      }
    }
    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('Sonar') {
          sh './gradlew jacocoTestReport sonarqube -Dsonar.login=b82de7b91b30c7479f84869133f3db9e881e6e0f'
        }

      }
    }
    stage("Quality Gate") {
      steps {
        timeout(time: 2, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
  }
  post {
      always {
        mail (bcc: '${CHANGE_AUTHOR_EMAIL}', body: '${env.BUILD_URL} has result ${currentBuild.result}', cc: '${CHANGE_AUTHOR_EMAIL}', from: 'jenkinssmtp635@gmail.com', subject: 'build', to: '${CHANGE_AUTHOR_EMAIL}')
        junit 'build/test-results/**/*.xml'
      }
    }
  tools {
    jdk 'JDK_17_new'
  }
}