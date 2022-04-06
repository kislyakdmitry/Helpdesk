pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh './gradlew clean build'
      }
    }
    stage('test') {
      steps {
        sh './gradlew test'
      }
    }
    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('Sonar') {
          sh './gradlew sonarqube -Dsonar.login=b82de7b91b30c7479f84869133f3db9e881e6e0f'
        }

      }
    }
    stage("Quality Gate") {
      steps {
        timeout(time: 1, unit: 'HOURS') {
          // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
          // true = set pipeline to UNSTABLE, false = don't
          waitForQualityGate abortPipeline: true
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