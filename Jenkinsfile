pipeline {
  agent any
  stages {
    stage('error') {
      steps {
        sh './gradlew clean build'
      }
    }

  }
  environment {
    JAVA_HOME = '/root/jdk-17.0.2'
  }
}