pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew clean build --no-daemon'
      }
    }

    stage('test') {
      steps {
        sh './gradlew test --no-daemon'
      }
    }
    stage('SonarQube Analysis') {
        steps {
            withSonarQubeEnv() {
                sh "./gradlew sonarqube --no-daemon"
            }
        }
    }

  }

  tools {
    jdk 'JDK_17'
  }
  post {
    always {
      junit 'build/test-results/**/*.xml'
    }

  }
}