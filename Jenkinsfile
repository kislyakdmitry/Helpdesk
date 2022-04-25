pipeline {
  agent any

  environment {
  env.GIT_COMMITTER_EMAIL = sh(
     script: "git --no-pager show -s --format='%ae'",
     returnStdout: true
  ).trim()
  }

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
            emailext
            to: "${GIT_COMMITTER_EMAIL}",
            subject: "jenkins build:${currentBuild.currentResult}: ${env.JOB_NAME}",
            from: 'jenkinssmtp635@gmail.com',
            body: '${env.BUILD_URL} has result ${currentBuild.result}'

        junit 'build/test-results/**/*.xml'
      }
    }
  tools {
    jdk 'JDK_17_new'
  }
}