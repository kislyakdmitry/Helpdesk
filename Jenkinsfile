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
        withSonarQubeEnv('Sonar') {
          sh './gradlew sonarqube -Dsonar.login=b82de7b91b30c7479f84869133f3db9e881e6e0f'
        }

      }
    }

  }
  tools {
    jdk 'JDK_17'
  }
  environment {
    SONAR_TOKEN = 'b82de7b91b30c7479f84869133f3db9e881e6e0f'
  }
  post {
    always {
      junit 'build/test-results/**/*.xml'
    }

  }
}