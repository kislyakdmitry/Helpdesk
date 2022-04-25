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
            emailext to: "${env.CHANGE_AUTHOR_EMAIL}",
            subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
            from: 'jenkinssmtp635@gmail.com'
            body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} More Info can be found here: ${env.BUILD_URL}"

        junit 'build/test-results/**/*.xml'
      }
    }
  tools {
    jdk 'JDK_17_new'
  }
}